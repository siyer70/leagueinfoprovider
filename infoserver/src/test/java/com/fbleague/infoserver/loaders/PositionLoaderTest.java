package com.fbleague.infoserver.loaders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

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

import com.fbleague.infoserver.model.League;
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
	private TestUtils testUtils = TestUtils.getInstance();

	private League league = testUtils.buildLeagueInstance("IN", "India", "LID", "Ligue 2");
	private List<League> leagueList = Lists.newArrayList(league);
	
	@Before
	public void setup() {
		cache = new HashMap<>();
		
		when(target.queryParam(any(), any()))
			.thenReturn(target);
		when(target.request()).thenReturn(builder);
		when(builder.accept(MediaType.APPLICATION_JSON)).thenReturn(builder);
		when(builder.get()).thenReturn(response);
	}
	
	@Test
	public void shouldBeAbleToRetrievePositionsFromTheGivenSource() 
			throws InterruptedException, ExecutionException {
		// build test data and set expectations
		Position position = testUtils.buildPositionInstance("India", "Ligue 2", "Some team", "1");
		when(response.readEntity(new GenericType<List<Position>>() {})).thenReturn(
				Lists.<Position>newArrayList(position));

		// execute test
		PositionLoader classUnderTest = new PositionLoader();
		List<Position> actualPositions = classUnderTest.load(target, leagueList).get();
		
		// assert results - ensure that expected positions are returned
		assertThat(actualPositions.size()).isEqualTo(1);

		// used string comparison instead of hashcode-equals to please sonar robotic mind
		// to address coverage and cyclomatic complexity
		assertThat(((Position) actualPositions.get(0)).toString()).isEqualTo(position.toString());		
	}
	

	@Test
	public void shouldGracefullyHandleExceptionsFromSource() 
			throws InterruptedException, ExecutionException {
		// build test data and set expectations
		when(response.readEntity(new GenericType<List<Position>>() {})).thenThrow(
				new ProcessingException("Some error occurrred"));

		// execute test
		PositionLoader classUnderTest = new PositionLoader();
		List<Position> actualPositions = classUnderTest.load(target, leagueList).get();
		
		// assert results - ensure that no positions are returned
		assertThat(actualPositions.size()).isEqualTo(0);
	}
	
}
