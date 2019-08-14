package com.fbleague.infoserver.loaders;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.fbleague.infoserver.model.Country;

@Component
@Profile("!test")
public class CountryLoader implements Loader {
	Logger logger = LoggerFactory.getLogger(CountryLoader.class);


	@Override
	public void load(Map<String, Map<String, ? extends Object>> cache, WebTarget target) {
		logger.info("Loading countries");
		Map<String, Country> countryMap = new HashMap<String, Country>();
		cache.put(COUNTRIES_KEY, countryMap);

		try {
			logger.info("Sending request to information source..");
			final List<Country> countries = target.queryParam("action", "get_countries").request()
			        .accept(MediaType.APPLICATION_JSON).get().readEntity(new GenericType<List<Country>>() {});
			logger.info("Request succeeded -> {} countries loaded", countries.size());

			countries.forEach(country -> {
				countryMap.put(country.getCountry_name(), country);
			});
			logger.info("Loaded countries");
		} catch (ProcessingException ex) {
			logger.error("An error occurred while loading countries", ex);
		}
	}

}
