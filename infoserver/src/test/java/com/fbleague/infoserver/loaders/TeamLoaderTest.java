package com.fbleague.infoserver.loaders;

import static com.fbleague.infoserver.loaders.LoaderConstants.COUNTRIES_KEY;
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

import com.fbleague.infoserver.model.Country;
import com.fbleague.infoserver.model.League;
import com.fbleague.infoserver.model.Team;
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
		Map<String, Country> countryMap = new HashMap<>();
		countryMap.put("IN", new Country("IN", "India"));
		
		Map<String, League> leagueMap = new HashMap<>();
		leagueMap.put("LID", new League("IN", "India", "LID", "Ligue 2"));
		
		cache.put(COUNTRIES_KEY, countryMap);
		cache.put(LEAGUES_KEY, leagueMap);
		List<Team> teams = Lists.<Team>newArrayList(new Team("A1", "India", "sun.gif"));
		when(target.queryParam(any(), any()))
			.thenReturn(target);
		when(target.request()).thenReturn(builder);
		when(builder.accept(MediaType.APPLICATION_JSON)).thenReturn(builder);
		when(builder.get()).thenReturn(response);
		when(response.readEntity(new GenericType<List<Team>>() {})).thenReturn(teams);
	}
	
	@Test
	public void shouldBeAbleToLoadTeamsInCache() {
		TeamLoader classUnderTest = new TeamLoader();
		classUnderTest.load(cache, target);
		assertThat(cache.get(TEAMS_KEY).size()).isEqualTo(1);
	}

}
