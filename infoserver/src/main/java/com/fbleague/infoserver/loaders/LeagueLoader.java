package com.fbleague.infoserver.loaders;

import static com.fbleague.infoserver.loaders.LoaderConstants.COUNTRIES_KEY;
import static com.fbleague.infoserver.loaders.LoaderConstants.LEAGUES_KEY;

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
import com.fbleague.infoserver.model.League;

@Component
@Profile("!test")
public class LeagueLoader implements Loader {
	Logger logger = LoggerFactory.getLogger(LeagueLoader.class);

	@Override
	public void load(Map<String, Map<String, ? extends Object>> cache, WebTarget target) {
		logger.info("Loading Leagues");
		Map<String, League> leagueMap = new HashMap<>();
		cache.put(LEAGUES_KEY, leagueMap);

		Map<String, Country> countryMap = (Map<String, Country>) cache.get(COUNTRIES_KEY);

		countryMap.values().forEach(country -> {
			logger.info("Sending request to information source for country: {}", country.getCountryName());
			try {
		        final List<League> leagues = target.queryParam("action", "get_leagues")
		        		.queryParam("country_id", country.getCountryId())
		        		.request()
		                .accept(MediaType.APPLICATION_JSON).get().readEntity(new GenericType<List<League>>() {});
				logger.info("Request succeeded -> leagues loaded for country {} : {}", country.getCountryName(), leagues.size());

				leagues.forEach(league -> 
					leagueMap.put(league.getLeagueId(), league)
				);
				logger.info("Loaded leagues for country: {}", country.getCountryName());
			} catch (ProcessingException ex) {
				logger.error("An error occurred while loading leagues", ex);
			}
		});

	}

}
