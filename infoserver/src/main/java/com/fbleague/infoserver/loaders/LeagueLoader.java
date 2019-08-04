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

import com.fbleague.infoserver.model.Country;
import com.fbleague.infoserver.model.League;

public class LeagueLoader extends AbstractLoader {
	Logger logger = LoggerFactory.getLogger(LeagueLoader.class);

	public LeagueLoader(Map<String, Map<String, ? extends Object>> cache, WebTarget target) {
		super(cache, target);
	}

	@Override
	public boolean load() {
		logger.info("Loading Leagues");
		Map<String, League> leagueMap = new HashMap<String, League>();
		cache.put(LEAGUES_KEY, leagueMap);

		try {
			Map<String, Country> countryMap = (Map<String, Country>) cache.get(COUNTRIES_KEY);
			
			countryMap.values().forEach(country -> {
				logger.info("Sending request to information source..");
		        final List<League> leagues = target.queryParam("action", "get_leagues")
		        		.queryParam("country_id", country.getCountry_id())
		        		.request()
		                .accept(MediaType.APPLICATION_JSON).get().readEntity(new GenericType<List<League>>() {});
				logger.info("Request succeeded -> {} leagues loaded for country {}", leagues.size(), country.getCountry_name());

				leagues.forEach(league -> {
					leagueMap.put(league.getLeague_id(), league);
				});
				
			});

			logger.info("Loaded leagues");
			return true;
		} catch (ProcessingException ex) {
			logger.error("An error occurred while loading leagues", ex);
			return false;
		}
	}

}
