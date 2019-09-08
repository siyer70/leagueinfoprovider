package com.fbleague.infoserver.cache;

import static com.fbleague.infoserver.loaders.LoaderConstants.POSITIONS_KEY;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.UriBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.fbleague.infoserver.config.CacheReloadTimer;
import com.fbleague.infoserver.config.CacheReloadTimerTask;
import com.fbleague.infoserver.config.ConfigManager;
import com.fbleague.infoserver.loaders.CountryLoader;
import com.fbleague.infoserver.loaders.LeagueLoader;
import com.fbleague.infoserver.loaders.PositionLoader;
import com.fbleague.infoserver.model.Criteria;
import com.fbleague.infoserver.model.Position;

@Component
@Profile("!test")
public class CacheManagerImpl implements CacheManager {

	static Logger logger = LoggerFactory.getLogger(CacheManagerImpl.class);

	@Autowired
	private Client client;

	@Autowired
	private ConfigManager configManager;

	@Autowired
	private CountryLoader countryLoader;

	@Autowired
	private LeagueLoader leagueLoader;

	@Autowired
	private PositionLoader positionLoader;

	@Autowired
	private CacheReloadTimer timer;

	@Autowired
	private CacheReloadTimerTask task;

	private final Map<String, Map<String, ? extends Object>> cache;
	private WebTarget target = null;
	private final AtomicInteger readers;
	private final AtomicBoolean isWriting;

	public CacheManagerImpl() {
		this.cache = new ConcurrentHashMap<>();
		readers = new AtomicInteger();
		isWriting = new AtomicBoolean();
	}

	@PostConstruct
	private void init() {
		logger.info("Post Construct is called for loader");
		task.setCacheManager(this);
		this.target = client.target(getBaseURI()).queryParam("APIkey", configManager.getProperty("apikey"));
		loadOrReloadCache(); // load it initially
		startTimer();
	}

	private void startTimer() {
		timer.startTimer(task);
	}

	public List<Criteria> getSearchCombinations() {
		waitForCacheUpdate();
		List<Criteria> criteriaList = new ArrayList<>();
		readers.getAndIncrement();
		Collection<Position> positions = new ArrayList(cache.get(POSITIONS_KEY).values());
		readers.getAndDecrement();
		positions.forEach(position -> criteriaList
				.add(new Criteria(position.getCountryName(), 
									position.getLeagueName(), 
									position.getTeamName())));
		criteriaList.sort(criteriaSortComparator());
		return criteriaList;
	}

	private void waitForCacheUpdate() {
		while (isWriting.get()) {
			try {
				logger.info("Waiting for Cache update to finish..");
				Thread.sleep(100);
			} catch (InterruptedException e) {
				logger.warn("Thread interrupted");
				// Restore interrupted state...
				Thread.currentThread().interrupt();
			}
		}
	}

	public Position getPosition(String key) {
		waitForCacheUpdate();
		readers.getAndIncrement();
		Position position = (Position) cache.get(POSITIONS_KEY).get(key);
		readers.getAndDecrement();
		return position;
	}

	private Comparator<Criteria> criteriaSortComparator() {
		return (Criteria c1, Criteria c2) -> (c1.getCountryName() + c1.getLeagueName() + c1.getTeamName())
				.compareTo((c2.getCountryName() + c2.getLeagueName() + c2.getTeamName()));
	}

	public void loadOrReloadCache() {
		waitForReadersToRead();
		isWriting.compareAndSet(false, true);
		loadCountries();
		loadLeagues();
		loadPositions();
		isWriting.compareAndSet(true, false);
	}

	private void waitForReadersToRead() {
		while (readers.intValue() > 0) {
			try {
				logger.info("Waiting for readers to finish..");
				Thread.sleep(100);
			} catch (InterruptedException e) {
				logger.warn("Thread interrupted");
				// Restore interrupted state...
				Thread.currentThread().interrupt();
			}
		}
	}

	private void loadCountries() {
		countryLoader.load(cache, target);
	}

	private void loadLeagues() {
		leagueLoader.load(cache, target);
	}

	private void loadPositions() {
		positionLoader.load(cache, target);
	}

	private URI getBaseURI() {
		String baseURL = configManager.getProperty("baseURL");
		logger.info("BaseURL is: {}", baseURL);
		return UriBuilder.fromUri(baseURL).build();
	}


}
