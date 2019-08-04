package com.fbleague.infoserver;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
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
import org.springframework.stereotype.Component;

import com.fbleague.infoserver.config.ConfigManager;
import com.fbleague.infoserver.loaders.CountryLoader;
import com.fbleague.infoserver.loaders.LeagueLoader;
import com.fbleague.infoserver.loaders.Loader;
import com.fbleague.infoserver.loaders.PositionLoader;
import com.fbleague.infoserver.loaders.TeamLoader;
import com.fbleague.infoserver.model.Country;
import com.fbleague.infoserver.model.Criteria;
import com.fbleague.infoserver.model.Position;


@Component
public class CacheManagerImpl implements CacheManager {

	static Logger logger = LoggerFactory.getLogger(CacheManagerImpl.class);
	
	private final Client client;
	private final Map<String, Map<String, ? extends Object>> cache; 
	private WebTarget target=null;
	private Timer timer = null;
	private final AtomicInteger readers;
	private final AtomicBoolean isWriting;
	
	@Autowired
	private ConfigManager configManager;

	public CacheManagerImpl(Client client) {
		this.client = client;
		this.cache = new ConcurrentHashMap<String, Map<String,? extends Object>>();
		readers = new AtomicInteger();
		isWriting = new AtomicBoolean();
 	}
	
	@PostConstruct
	public void init() {
		logger.info("Post Construct is called for loader");
		this.target = client.target(getBaseURI()).queryParam("APIkey", configManager.getProperty("apikey"));
		loadOrReloadCache(); // load it initially
		startTimer();
	}
	
	
	private void startTimer() {
		TimerTask task = new CacheReloaderTimerTask(this);
		timer = new Timer(true);
		int delay = Integer.valueOf(configManager.getProperty("cacheReloadDelayInSeconds"));
		int period = Integer.valueOf(configManager.getProperty("cacheReloadPeriodInSeconds"));
		timer.schedule(task, 1000 * delay, 1000 * period);
	}

	public List<Country> getCountries() {
		return new ArrayList(cache.get(Loader.COUNTRIES_KEY).values());
	}
	
	public List<Criteria> getSearchCombinations() {
		waitForCacheUpdate();
		List<Criteria> criteriaList = new ArrayList<Criteria>();
		readers.getAndIncrement();
		Collection<Position> positions =  new ArrayList(cache.get(Loader.POSITIONS_KEY).values());
		readers.getAndDecrement();
		positions.forEach(position -> {
			criteriaList.add(new Criteria(position.getCountry_name(), position.getLeague_name(), position.getTeam_name()));
		});
		criteriaList.sort(criteriaSortComparator());
		return criteriaList;
	}

	private void waitForCacheUpdate() {
		while(isWriting.get()) {
			try {
				logger.info("Waiting for Cache update to finish..");
				Thread.sleep(100);
			} catch (InterruptedException e) {
				logger.info("Thread interrupted");
			}
		}
	}

	public Position getPosition(String key) {
		waitForCacheUpdate();
		readers.getAndIncrement();
		Position position = (Position) cache.get(Loader.POSITIONS_KEY).get(key);
		readers.getAndDecrement();
		return position;
	}

	private Comparator<Criteria> criteriaSortComparator() {
		return (Criteria c1, Criteria c2) -> (c1.getCountryName()+c1.getLeagueName()+c1.getTeamName()).compareTo(
				(c2.getCountryName()+c2.getLeagueName()+c2.getTeamName()));
	}
	
	public void loadOrReloadCache() {
		waitForReadersToRead();
		isWriting.compareAndSet(false, true);
		loadCountries();
		loadLeagues();
		loadPositions();
//		loadTeams();
		isWriting.compareAndSet(true, false);
	}
	
	private void waitForReadersToRead() {
		while(readers.intValue()>0) {
			try {
				logger.info("Waiting for readers to finish..");
				Thread.sleep(100);
			} catch (InterruptedException e) {
				logger.info("Thread interrupted");
			}
		}
	}

	private boolean loadCountries() {
		return new CountryLoader(cache, target).load();
	}
	
	private boolean loadLeagues() {
		return new LeagueLoader(cache, target).load();
	}
	
	private boolean loadTeams() {
		return new TeamLoader(cache, target).load();
	}

	private boolean loadPositions() {
		return new PositionLoader(cache, target).load();
	}

    private URI getBaseURI() {
    	String baseURL = configManager.getProperty("baseURL");
    	logger.info("BaseURL is: {}", baseURL);
        return UriBuilder.fromUri(baseURL).build();
    }
    
    static class CacheReloaderTimerTask extends TimerTask {
    	
    	private CacheManager cacheManager;

		public CacheReloaderTimerTask(CacheManager cacheManager) {
			this.cacheManager = cacheManager;
    	}

		@Override
		public void run() {
			logger.info("Cache reloading started..");
			cacheManager.loadOrReloadCache();
			logger.info("Cache reloading ended");
		}
    	
    }
	
}
