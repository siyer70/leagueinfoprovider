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

import com.fbleague.infoserver.model.League;
import com.fbleague.infoserver.model.Team;

public class TeamLoader extends AbstractLoader {
	Logger logger = LoggerFactory.getLogger(TeamLoader.class);

	public TeamLoader(Map<String, Map<String, ? extends Object>> cache, WebTarget target) {
		super(cache, target);
	}

	@Override
	public boolean load() {
		logger.info("Loading teams");
		Map<String, Team> teamMap = new HashMap<>();
		cache.put(TEAMS_KEY, teamMap);

		try {
			Map<String, League> leagueMap = (Map<String, League>) cache.get(LEAGUES_KEY);
			
			leagueMap.values().forEach(league -> {
				logger.info("Sending request to information source..");
		        final List<Team> teams = target.queryParam("action", "get_teams")
		        		.queryParam("league_id", league.getLeague_id())
		        		.request()
		                .accept(MediaType.APPLICATION_JSON).get().readEntity(new GenericType<List<Team>>() {});
				logger.info("Request succeeded -> {} teams loaded for league {}", teams.size(), league.getLeague_name());

				teams.forEach(team -> {
					teamMap.put(team.getTeam_key(), team);
				});
				
			});

			logger.info("Loaded teams");
			return true;
		} catch (ProcessingException ex) {
			logger.error("An error occurred while loading teams", ex);
			return false;
		}
	}

}
