package com.fbleague.infoserver.cache;

import static com.fbleague.infoserver.loaders.LoaderConstants.POSITIONS_KEY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import com.fbleague.infoserver.config.CacheReloadTimer;
import com.fbleague.infoserver.config.CacheReloadTimerTask;
import com.fbleague.infoserver.config.ConfigManager;
import com.fbleague.infoserver.loaders.CountryLoader;
import com.fbleague.infoserver.loaders.LeagueLoader;
import com.fbleague.infoserver.loaders.PositionLoader;
import com.fbleague.infoserver.model.Criteria;
import com.fbleague.infoserver.model.Position;
import com.fbleague.infoserver.utils.TestUtils;


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
	
	private final Position samplePositionObject = new TestUtils().buildPositionInstance(
													"India", "Ligue 2", "Some team", "1");
	private final String samplePositionObjectKey = "India|Ligue 2|Some team";

	@Test
	public void shouldBeAbleToRetrieveSearchCombinationsFromCache() {
		// set expectations
		Criteria expectedCriteria = new Criteria(samplePositionObject.getCountryName(),
									samplePositionObject.getLeagueName(),
									samplePositionObject.getTeamName());
		doAnswer(answer).when(positionLoader).load(any(), any());
		
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
		doAnswer(answer).when(positionLoader).load(any(), any());
		cacheManager.loadOrReloadCache();
		assertThat(cacheManager.getPosition(samplePositionObjectKey).toString()).isEqualTo(
				samplePositionObject.toString());
	}
	
	private final Answer<Object> answer = new Answer<Object>() {

		@Override
		public Object answer(InvocationOnMock invocation) throws Throwable {
			Map<String, Position> positionMap = new HashMap<String, Position>();
			positionMap.put(samplePositionObjectKey, samplePositionObject);
			Map<String, Map<String, ? extends Object>> cache = invocation.getArgument(0);
			cache.put(POSITIONS_KEY, positionMap);
			return null;
		}

	};
	
}
