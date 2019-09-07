package com.fbleague.infoserver.loaders;

import static com.fbleague.infoserver.loaders.LoaderConstants.LEAGUES_KEY;
import static com.fbleague.infoserver.loaders.LoaderConstants.POSITIONS_KEY;

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

import com.fbleague.infoserver.model.League;
import com.fbleague.infoserver.model.Position;

@Component
@Profile("!test")
public class PositionLoader implements Loader {
	Logger logger = LoggerFactory.getLogger(PositionLoader.class);

	@Override
	public void load(Map<String, Map<String, ? extends Object>> cache, WebTarget target) {
		logger.info("Loading Positions");
		Map<String, Position> positionMap = new HashMap<>();
		cache.put(POSITIONS_KEY, positionMap);

		Map<String, League> leagueMap = (Map<String, League>) cache.get(LEAGUES_KEY);
		
		leagueMap.values().forEach(league -> {
			logger.info("Sending request to information source for League: {}", league.getLeagueName());
	        try {
				final List<Position> positions = target.queryParam("action", "get_standings")
						.queryParam("league_id", league.getLeagueId())
						.request()
				        .accept(MediaType.APPLICATION_JSON).get().readEntity(new GenericType<List<Position>>() {});
				logger.info("Request succeeded -> positions loaded for league {} : {}", league.getLeagueName(), positions.size());

				positions.forEach(position -> {
					String key = getPositionKey(position);
					logger.info(key);
					positionMap.put(key, position);
				});
				logger.info("Loaded positions for league: {}", league.getLeagueName());
				
			} catch (ProcessingException ex) {
				logger.error("An error occurred while loading positions", ex);
			}
			
		});

	}
	
	public String getPositionKey(Position position) {
		return position.getCountryName() + "|" + 
				position.getLeagueName() + "|" +
				position.getTeamName();
	}

}
