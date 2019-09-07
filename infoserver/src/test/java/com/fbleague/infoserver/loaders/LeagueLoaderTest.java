package com.fbleague.infoserver.loaders;

import static com.fbleague.infoserver.loaders.LoaderConstants.COUNTRIES_KEY;
import static com.fbleague.infoserver.loaders.LoaderConstants.LEAGUES_KEY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.fbleague.infoserver.model.League;
import com.fbleague.infoserver.utils.TestUtils;
import com.google.common.collect.Lists;

@RunWith(MockitoJUnitRunner.class)
public class LeagueLoaderTest {

	Map<String, Map<String, ? extends Object>> cache = new HashMap<>();
	
	@Mock
	WebTarget target;
	
	@Mock
	Invocation.Builder builder;
	
	@Mock
	Response response;
	
	@Before
	public void setup() {
		when(target.queryParam(any(), any()))
			.thenReturn(target);
		when(target.request()).thenReturn(builder);
		when(builder.accept(MediaType.APPLICATION_JSON)).thenReturn(builder);
		when(builder.get()).thenReturn(response);
	}
	
	@Test
	public void shouldBeAbleToLoadLeaguesInCache() {
		// Test utility for test data generation
		TestUtils testUtils = TestUtils.getInstance();
		
		// Country Map is required by League Loader (the class under test)
		cache.put(COUNTRIES_KEY, testUtils.buildCountryMap("IN", "India"));

		// build test data and set expectations
		String leagueKey = "lid";
		League league = testUtils.buildLeagueInstance("IN", "India", leagueKey, "Ligue 2");
		when(response.readEntity(new GenericType<List<League>>() {})).thenReturn(
				Lists.<League>newArrayList(league)
		);

		// execute the test 
		LeagueLoader classUnderTest = new LeagueLoader();
		classUnderTest.load(cache, target);
		Map<String, ? extends Object> leagueMap = cache.get(LEAGUES_KEY);
		
		//assert the results - ensure loader has loaded the test data in cache
		assertThat(leagueMap.size()).isEqualTo(1);
		assertThat(leagueMap.containsKey(leagueKey)).isEqualTo(true);
		assertThat(((League) leagueMap.get(leagueKey)).toString()).isEqualTo(league.toString());
	}

}
