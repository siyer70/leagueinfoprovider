package com.fbleague.infoserver.loaders;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fbleague.infoserver.model.Country;
import com.fbleague.infoserver.model.League;
import com.fbleague.infoserver.model.Position;

public class PositionLoader extends AbstractLoader {
	Logger logger = LoggerFactory.getLogger(PositionLoader.class);

	public PositionLoader(Map<String, Map<String, ? extends Object>> cache, WebTarget target) {
		super(cache, target);
	}

	@Override
	public boolean load() {
		logger.info("Loading Positions");
		Map<String, Position> positionMap = new HashMap<String, Position>();
		cache.put(POSITIONS_KEY, positionMap);

		try {
			Map<String, Country> countryMap = (Map<String, Country>) cache.get(COUNTRIES_KEY);
			Map<String, League> leagueMap = (Map<String, League>) cache.get(LEAGUES_KEY);
			
			leagueMap.values().forEach(league -> {
				logger.info("Sending request to information source..");
		        final List<Position> positions = target.queryParam("action", "get_standings")
		        		.queryParam("league_id", league.getLeague_id())
		        		.request()
		                .accept(MediaType.APPLICATION_JSON).get().readEntity(new GenericType<List<Position>>() {});
				logger.info("Request succeeded -> {} positions loaded for league {}", positions.size(), league.getLeague_name());

				positions.forEach(position -> {
					String key = position.getCountry_name() + "|" + 
									position.getLeague_name() + "|" +
									position.getTeam_name();
					logger.info(key);
					position.setCountry_id(Optional.ofNullable(countryMap.get(position.getCountry_name())).map(c -> c.getCountry_id()).orElse("N/A"));
					positionMap.put(key, position);
				});
				
			});

			logger.info("Loaded positions");
			return true;
		} catch (ProcessingException ex) {
			logger.error("An error occurred while loading positions", ex);
			return false;
		}
	}

}
