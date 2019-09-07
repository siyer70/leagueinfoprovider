package com.fbleague.infoserver.loaders;

import static com.fbleague.infoserver.loaders.LoaderConstants.COUNTRIES_KEY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.IOException;
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
import com.fbleague.infoserver.utils.TestUtils;
import com.google.common.collect.Lists;

@RunWith(MockitoJUnitRunner.class)
public class CountryLoaderTest {

	Map<String, Map<String, ? extends Object>> cache = new HashMap<>();
	
	@Mock
	WebTarget target;
	
	@Mock
	Invocation.Builder builder;
	
	@Mock
	Response response;
	
	@Before
	public void setup() {
		when(target.queryParam(any(), any())).thenReturn(target);
		when(target.request()).thenReturn(builder);
		when(builder.accept(MediaType.APPLICATION_JSON)).thenReturn(builder);
		when(builder.get()).thenReturn(response);
	}
	
	@Test
	public void shouldBeAbleToLoadCountriesInCache() throws IOException {
		// Generate test data and set expectations
		String key = "India";
		Country countryIndia = new TestUtils().buildCountryInstance("IN", key);
		List<Country> countries = Lists.<Country>newArrayList(countryIndia);
		when(response.readEntity(new GenericType<List<Country>>() {})).thenReturn(countries);

		// execute the class under test
		CountryLoader classUnderTest = new CountryLoader();
		classUnderTest.load(cache, target);
		Map<String, ? extends Object> countryMap = cache.get(COUNTRIES_KEY);
		
		// assert the results - ensure that country loader loaded the test data in cache
		assertThat(countryMap.size()).isEqualTo(1);
		assertThat(countryMap.containsKey(key)).isEqualTo(true);
		assertThat(((Country) countryMap.get(key)).toString()).isEqualTo(countryIndia.toString());
	}

}
