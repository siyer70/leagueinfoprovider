package com.fbleague.infoserver.cache;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.fbleague.infoserver.config.CacheReloadTimer;
import com.fbleague.infoserver.config.CacheReloadTimerTask;
import com.fbleague.infoserver.config.ConfigManager;
import com.fbleague.infoserver.loaders.CountryLoader;
import com.fbleague.infoserver.loaders.LeagueLoader;
import com.fbleague.infoserver.loaders.PositionLoader;
import com.fbleague.infoserver.model.Country;
import com.fbleague.infoserver.model.Criteria;
import com.fbleague.infoserver.model.League;
import com.fbleague.infoserver.model.Position;
import com.fbleague.infoserver.utils.TestUtils;
import com.google.common.collect.Lists;


@RunWith(MockitoJUnitRunner.class)
public class CacheManagerTest {
	
	@InjectMocks
	CacheManagerImpl cacheManager;

	@Mock
	Client client;
	
	@Mock
	WebTarget target;
	
	@Mock
	ConfigManager configManager;
	
	@Mock
	CountryLoader countryLoader;

	@Mock
	LeagueLoader leagueLoader;

	@Mock
	PositionLoader positionLoader;
	
	@Mock
	CacheReloadTimer timer;
	
	@Mock
	CacheReloadTimerTask task;
	
	private final TestUtils testUtils = new TestUtils();
	
	private final Position samplePositionObject = new TestUtils().buildPositionInstance(
													"India", "Ligue 2", "Some team", "1");
	private final String samplePositionObjectKey = "India|Ligue 2|Some team";
	
	private final CompletableFuture<List<Country>> countryResponse = 
			CompletableFuture.supplyAsync( () -> {
				Country countryIN = testUtils.buildCountryInstance("IN", "India");
				List<Country> countryList = Lists.newArrayList(countryIN);
				return countryList;
			});
	
	private final CompletableFuture<List<League>> leagueResponse = 
			CompletableFuture.supplyAsync( () -> {
				League league = testUtils.buildLeagueInstance("IN", "India", "LID", "Ligue 2");
				List<League> leagueList = Lists.newArrayList(league);
				return leagueList;
			});

	private final CompletableFuture<List<Position>> positionResponse = 
			CompletableFuture.supplyAsync( () -> {
				List<Position> positionList = Lists.newArrayList(samplePositionObject);
				return positionList;
			});
	
	@Before
	public void setup() {
		when(countryLoader.load(target)).thenReturn(countryResponse);
		when(leagueLoader.load(eq(target), any())).thenReturn(leagueResponse);
		when(positionLoader.load(eq(target), any())).thenReturn(positionResponse);
		when(positionLoader.getPositionKey(any())).thenReturn(samplePositionObjectKey);
	}
	
	@Test
	public void shouldBeAbleToRetrieveSearchCombinationsFromCache() {
		// set expectations
		Criteria expectedCriteria = new Criteria(samplePositionObject.getCountryName(),
									samplePositionObject.getLeagueName(),
									samplePositionObject.getTeamName());
		// execute test
		cacheManager.loadOrReloadCache();
		List<Criteria> result = cacheManager.getSearchCombinations();
		
		// assert the result
		assertThat(result.size()).isEqualTo(1);
		Criteria actualCriteria = result.get(0);
		
		/* used string comparison instead of hashcode-equals to please sonar robotic mind
		   to address coverage and cyclomatic complexity */
		assertThat(actualCriteria.toString()).isEqualTo(expectedCriteria.toString());
	}
	
	@Test
	public void shouldBeAbleToRetrievePositionFromCache() {
		cacheManager.loadOrReloadCache();
		assertThat(cacheManager.getPosition(samplePositionObjectKey).toString()).isEqualTo(
				samplePositionObject.toString());
	}
	
}
