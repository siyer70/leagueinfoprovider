package com.fbleague.infoserver.loaders;

import static com.fbleague.infoserver.loaders.LoaderConstants.LEAGUES_KEY;
import static com.fbleague.infoserver.loaders.LoaderConstants.TEAMS_KEY;

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
import com.fbleague.infoserver.model.Team;

@Component
@Profile("!test")
public class TeamLoader implements Loader {
	Logger logger = LoggerFactory.getLogger(TeamLoader.class);

	@Override
	public void load(Map<String, Map<String, ? extends Object>> cache, WebTarget target) {
		logger.info("Loading teams");
		Map<String, Team> teamMap = new HashMap<>();
		cache.put(TEAMS_KEY, teamMap);

		Map<String, League> leagueMap = (Map<String, League>) cache.get(LEAGUES_KEY);
		
		try {
			leagueMap.values().forEach(league -> {
				logger.info("Sending request to information source for league: {}", league.getLeagueName());
			    final List<Team> teams = target.queryParam("action", "get_teams")
			    		.queryParam("league_id", league.getLeagueId())
			    		.request()
			            .accept(MediaType.APPLICATION_JSON).get().readEntity(new GenericType<List<Team>>() {});
				logger.info("Request succeeded -> teams loaded for league {} : {}", league.getLeagueName(), teams.size());

				teams.forEach(team -> 
					teamMap.put(team.getTeamKey(), team)
				);
				
				logger.info("Loaded teams for league: {}", league.getLeagueName());
			});
			
		} catch (ProcessingException ex) {
			logger.error("An error occurred while loading teams", ex);
		}

	}

}
