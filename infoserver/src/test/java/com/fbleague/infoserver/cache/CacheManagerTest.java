package com.fbleague.infoserver.cache;

import static com.fbleague.infoserver.loaders.LoaderConstants.POSITIONS_KEY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.client.Client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import com.fbleague.infoserver.config.ConfigManager;
import com.fbleague.infoserver.loaders.CountryLoader;
import com.fbleague.infoserver.loaders.LeagueLoader;
import com.fbleague.infoserver.loaders.PositionLoader;
import com.fbleague.infoserver.model.Position;
import com.fbleague.infoserver.utils.TestUtils;


@RunWith(MockitoJUnitRunner.class)
public class CacheManagerTest {
	
	@InjectMocks
	CacheManagerImpl cacheManager;

	@Mock
	Client client;
	
	@Mock
	ConfigManager configManager;
	
	@Mock
	CountryLoader countryLoader;

	@Mock
	LeagueLoader leagueLoader;

	@Mock
	PositionLoader positionLoader;
	
	private final Position samplePositionObject = new TestUtils().buildPositionInstance("India", "Ligue 2", "Some team", "1");
	private final String samplePositionObjectKey = "India|Ligue 2|Some team";

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
	
	@Test
	public void shouldBeAbleToRetrieveSearchCombinationsFromCache() {
		doAnswer(answer).when(positionLoader).load(any(), any());
		cacheManager.loadOrReloadCache();
		assertThat(cacheManager.getSearchCombinations().size()).isEqualTo(1);
	}
	
	@Test
	public void shouldBeAbleToRetrievePositionFromCache() {
		doAnswer(answer).when(positionLoader).load(any(), any());
		cacheManager.loadOrReloadCache();
		assertThat(cacheManager.getPosition(samplePositionObjectKey)).isEqualTo(samplePositionObject);
	}
	
}
