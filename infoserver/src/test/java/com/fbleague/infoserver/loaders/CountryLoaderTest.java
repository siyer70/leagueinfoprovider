package com.fbleague.infoserver.loaders;

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
		List<Country> countries = Lists.<Country>newArrayList(new Country("IN", "India"));
		when(target.queryParam(any(), any())).thenReturn(target);
		when(target.request()).thenReturn(builder);
		when(builder.accept(MediaType.APPLICATION_JSON)).thenReturn(builder);
		when(builder.get()).thenReturn(response);
		when(response.readEntity(new GenericType<List<Country>>() {})).thenReturn(countries);
	}
	
	@Test
	public void shouldBeAbleToLoadCountriesInCache() {
		CountryLoader classUnderTest = new CountryLoader();
		classUnderTest.load(cache, target);
		assertThat(cache.get(Loader.COUNTRIES_KEY).size()).isEqualTo(1);
	}

}
