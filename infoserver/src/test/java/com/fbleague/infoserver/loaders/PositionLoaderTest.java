package com.fbleague.infoserver.loaders;

import static com.fbleague.infoserver.loaders.LoaderConstants.COUNTRIES_KEY;
import static com.fbleague.infoserver.loaders.LoaderConstants.LEAGUES_KEY;
import static com.fbleague.infoserver.loaders.LoaderConstants.POSITIONS_KEY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.ProcessingException;
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

import com.fbleague.infoserver.model.Position;
import com.fbleague.infoserver.utils.TestUtils;
import com.google.common.collect.Lists;

@RunWith(MockitoJUnitRunner.class)
public class PositionLoaderTest {

	Map<String, Map<String, ? extends Object>> cache = new HashMap<>();
	
	@Mock
	WebTarget target;
	
	@Mock
	Invocation.Builder builder;
	
	@Mock
	Response response;
	
	// Test utility for test data generation
	TestUtils testUtils = TestUtils.getInstance();

	@Before
	public void setup() {
		cache = new HashMap<>();
		
		// Country and League Maps are  dependencies of Position Loader (the class under test)
		cache.put(COUNTRIES_KEY, testUtils.buildCountryMap("IN", "India"));
		cache.put(LEAGUES_KEY, testUtils.buildLeagueMap("IN", "India", "LID", "Ligue 2"));
		
		when(target.queryParam(any(), any()))
			.thenReturn(target);
		when(target.request()).thenReturn(builder);
		when(builder.accept(MediaType.APPLICATION_JSON)).thenReturn(builder);
		when(builder.get()).thenReturn(response);
	}
	
	@Test
	public void shouldBeAbleToLoadPositionsInCache() {
		// build test data and set expectations
		Position position = testUtils.buildPositionInstance("India", "Ligue 2", "Some team", "1");
		when(response.readEntity(new GenericType<List<Position>>() {})).thenReturn(
				Lists.<Position>newArrayList(position));

		// execute test
		PositionLoader classUnderTest = new PositionLoader();
		String positionKey = classUnderTest.getPositionKey(position);
		classUnderTest.load(cache, target);
		
		// assert results - ensure that positions are loaded in cache
		Map<String, ? extends Object> positionMap = cache.get(POSITIONS_KEY);
		assertThat(positionMap.size()).isEqualTo(1);
		assertThat(positionMap.containsKey(positionKey)).isEqualTo(true);
		assertThat(((Position) positionMap.get(positionKey)).toString()).isEqualTo(position.toString());		
	}
	
	@Test
	public void shouldGracefullyHandleExceptions() {
		when(response.readEntity(new GenericType<List<Position>>() {})).thenThrow(
				new ProcessingException("Some error occurrred"));

		// execute test
		PositionLoader classUnderTest = new PositionLoader();
		classUnderTest.load(cache, target);
		
		// assert results - ensure that positions aren't loaded in cache
		Map<String, ? extends Object> positionMap = cache.get(POSITIONS_KEY);
		assertThat(positionMap.size()).isEqualTo(0);
	}
	

}
