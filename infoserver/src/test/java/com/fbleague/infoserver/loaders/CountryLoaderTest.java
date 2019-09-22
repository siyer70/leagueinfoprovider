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
import com.fbleague.infoserver.utils.TestUtils;
import com.google.common.collect.Lists;

@RunWith(MockitoJUnitRunner.class)
public class CountryLoaderTest {

	Map<String, Map<String, ? extends Object>> cache;
	
	@Mock
	WebTarget target;
	
	@Mock
	Invocation.Builder builder;
	
	@Mock
	Response response;
	
	@Before
	public void setup() {
		cache = new HashMap<>();
		
		when(target.queryParam(any(), any())).thenReturn(target);
		when(target.request()).thenReturn(builder);
		when(builder.accept(MediaType.APPLICATION_JSON)).thenReturn(builder);
		when(builder.get()).thenReturn(response);
	}
	
	@Test
	public void shouldBeAbleToRetrieveCountriesFromTheGivenSource() throws InterruptedException, ExecutionException {
		// Generate test data and set expectations
		Country countryIndia = new TestUtils().buildCountryInstance("IN", "India");
		List<Country> expectedCountries = Lists.<Country>newArrayList(countryIndia);
		when(response.readEntity(new GenericType<List<Country>>() {})).thenReturn(expectedCountries);

		// execute the class under test
		CountryLoader classUnderTest = new CountryLoader();
		List<Country> actualCountries = classUnderTest.load(target).get();
		
		// assert the results - ensure that country loader returned the expected data
		assertThat(actualCountries.size()).isEqualTo(1);
		assertThat(((Country) actualCountries.get(0)).toString()).isEqualTo(countryIndia.toString());
	}
	
	@Test
	public void shouldGracefullyHandleExceptionsWhileRetrievingDataFromGivenSource() 
			throws InterruptedException, ExecutionException {
		when(response.readEntity(new GenericType<List<Country>>() {}))
			.thenThrow(new ProcessingException("Some exception occurred"));

		CountryLoader classUnderTest = new CountryLoader();
		List<Country> actualCountries = classUnderTest.load(target).get();
		
		// should gracefully return an empty list
		assertThat(actualCountries.size()).isEqualTo(0);
	}
	
}
