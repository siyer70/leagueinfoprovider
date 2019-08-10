package com.fbleague.infoserver.loaders;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import com.fbleague.infoserver.model.Country;
import com.fbleague.infoserver.model.League;
import com.fbleague.infoserver.model.Position;
import com.google.common.collect.Lists;

import static com.fbleague.infoserver.loaders.Loader.COUNTRIES_KEY;
import static com.fbleague.infoserver.loaders.Loader.LEAGUES_KEY;
import static org.assertj.core.api.Assertions.assertThat;

import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PositionLoaderTest {

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
		leagueMap.put("123", new League("123", "Ligue 2", "IN", "India"));
		
		cache.put(COUNTRIES_KEY, countryMap);
		cache.put(LEAGUES_KEY, leagueMap);
	}
	
	@Test
	public void shouldBeAbleToLoadPositionsInCache() {
		PositionLoader classUnderTest = new PositionLoader(cache, target);
		List<Position> positions = Lists.<Position>newArrayList(new Position("India", "Ligue 2", "Some team", "1"));
		when(target.queryParam(any(), any()))
			.thenReturn(target);
		when(target.request()).thenReturn(builder);
		when(builder.accept(MediaType.APPLICATION_JSON)).thenReturn(builder);
		when(builder.get()).thenReturn(response);
		when(response.readEntity(new GenericType<List<Position>>() {})).thenReturn(positions);
		
		classUnderTest.load();
		
		assertThat(cache.get(Loader.POSITIONS_KEY).size()).isEqualTo(1);
	}

}
