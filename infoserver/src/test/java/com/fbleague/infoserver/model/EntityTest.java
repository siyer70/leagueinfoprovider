package com.fbleague.infoserver.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fbleague.infoserver.utils.TestUtils;

import springfox.documentation.swagger.web.SwaggerResource;

public class EntityTest {
	
	@Test
	public void shouldBeAbleToSerializeAndDeserializeCountry() throws IOException {
		TestUtils testUtils = TestUtils.getInstance();
		Country countryIndia = testUtils.buildCountryInstance("IN", "India");
		League league = testUtils.buildLeagueInstance("IN", "India", "lid", "Ligue 2");
		ObjectMapper objMapper = new ObjectMapper();
		String countryIndiaAsJsonString = objMapper.writeValueAsString(league);
		System.out.println(countryIndiaAsJsonString);
		Country deserializedCountryIndia = objMapper.readValue(
				countryIndiaAsJsonString, Country.class);
		assertThat(deserializedCountryIndia.toString()).isEqualTo(countryIndia.toString());
		
		Stream<Country> countryStream = Stream.of(countryIndia);
		countryStream.forEach(p -> System.out.println(p));
		
        SwaggerResource jerseySwaggerResource = new SwaggerResource();
        jerseySwaggerResource.setLocation("/api/swagger.json");
        jerseySwaggerResource.setSwaggerVersion("2.0");
        jerseySwaggerResource.setName("Jersey");
        
        Stream<Object> stream = Stream.of();
        
        Stream.concat(Stream.of(jerseySwaggerResource),
                stream).collect(Collectors.toList());
        
        Stream.of(jerseySwaggerResource).collect(Collectors.toList()).stream().forEach(p -> System.out.println(p.getName()));
	}

}

