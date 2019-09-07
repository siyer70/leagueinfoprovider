package com.fbleague.infoserver.loaders;

import static com.fbleague.infoserver.loaders.LoaderConstants.LEAGUES_KEY;
import static com.fbleague.infoserver.loaders.LoaderConstants.TEAMS_KEY;
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
import com.fbleague.infoserver.model.Team;
import com.fbleague.infoserver.utils.TestUtils;
import com.google.common.collect.Lists;

@RunWith(MockitoJUnitRunner.class)
public class TeamLoaderTest {

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
	public void shouldBeAbleToLoadTeamsInCache() {
		// Test utility for test data generation
		TestUtils testUtils = TestUtils.getInstance();

		// League map is a dependency for Team Loader (the class under test)
		cache.put(LEAGUES_KEY, testUtils.buildLeagueMap("IN", "India", "LID", "Ligue 2"));

		// build test data and set expectations
		String teamKey = "A1";
		Team team = testUtils.buildTeamInstance(teamKey, "India", "sun.gif");
		List<Team> teams = Lists.<Team>newArrayList(
				team);
		when(response.readEntity(new GenericType<List<Team>>() {})).thenReturn(teams);

		// execute the test 
		TeamLoader classUnderTest = new TeamLoader();
		classUnderTest.load(cache, target);

		//assert the results - ensure loader has loaded the test data in cache
		Map<String, ? extends Object> teamMap = cache.get(TEAMS_KEY);
		assertThat(teamMap.size()).isEqualTo(1);
		assertThat(teamMap.containsKey(teamKey)).isEqualTo(true);
		assertThat(((Team) teamMap.get(teamKey)).toString()).isEqualTo(team.toString());
	}

}
