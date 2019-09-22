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

import com.fbleague.infoserver.model.Country;
import com.fbleague.infoserver.model.League;
import com.fbleague.infoserver.utils.TestUtils;
import com.google.common.collect.Lists;

@RunWith(MockitoJUnitRunner.class)
public class LeagueLoaderTest {

	Map<String, Map<String, ? extends Object>> cache;
	
	@Mock
	WebTarget target;
	
	@Mock
	Invocation.Builder builder;
	
	@Mock
	Response response;
	
	// Test utility for test data generation
	private TestUtils testUtils = TestUtils.getInstance();
	
	private Country countryIN = testUtils.buildCountryInstance("IN", "India");
	private List<Country> countryList = Lists.newArrayList(countryIN);
	
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
	public void shouldBeAbleToRetrieveLeaguesFromTheGivenSource() 
			throws InterruptedException, ExecutionException {
		// build test data and set expectations
		League league = testUtils.buildLeagueInstance("IN", "India", "lid", "Ligue 2");
		when(response.readEntity(new GenericType<List<League>>() {})).thenReturn(
				Lists.<League>newArrayList(league)
		);

		// execute the test 
		LeagueLoader classUnderTest = new LeagueLoader();
		List<League> actualLeagues = classUnderTest.load(target, countryList).get();
		
		//assert the results - ensure loader has returned the expected data from source
		assertThat(actualLeagues.size()).isEqualTo(1);
		assertThat(((League) actualLeagues.get(0)).toString()).isEqualTo(league.toString());
	}
	
	@Test
	public void shouldGracefullyHandleExceptionsFromSource() 
			throws InterruptedException, ExecutionException {
		// build test data and set expectations
		when(response.readEntity(new GenericType<List<League>>() {}))
			.thenThrow(new ProcessingException("Some error occurred"));
		
		// execute the test 
		LeagueLoader classUnderTest = new LeagueLoader();
		List<League> actualLeagues = classUnderTest.load(target, countryList).get();
		
		//assert the results - ensure loader hasn't loaded any object
		assertThat(actualLeagues.size()).isEqualTo(0);
	}
	
}
