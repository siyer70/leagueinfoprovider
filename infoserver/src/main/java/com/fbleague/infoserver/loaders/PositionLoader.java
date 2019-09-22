package com.fbleague.infoserver.loaders;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.fbleague.infoserver.model.League;
import com.fbleague.infoserver.model.Position;
import com.google.common.collect.Lists;

@Component
@Profile("!test")
public class PositionLoader {
	Logger logger = LoggerFactory.getLogger(PositionLoader.class);

	public CompletableFuture<List<Position>> load(WebTarget target, List<League> leagueList) {
		List<CompletableFuture<List<Position>>> positionListFutures = Lists.newArrayList();
		
		leagueList.forEach(league -> {
			positionListFutures.add(load(target, league));
		});
		
		CompletableFuture<Void> allFutures = CompletableFuture.allOf(
				positionListFutures.toArray(new CompletableFuture[positionListFutures.size()]));
		
		CompletableFuture<List<Position>> allPositions = allFutures.thenApply(v -> {
			return positionListFutures.stream()
			.map(future -> future.join())
			.flatMap(List::stream)
			.collect(Collectors.toList());
		});
		
		return allPositions;
	}
	
	
	private CompletableFuture<List<Position>> load(WebTarget target, League league) {
		return CompletableFuture.supplyAsync(() -> {
			logger.info("Loading positions for League: {}", league.getLeagueName());
			List<Position> positions = target.queryParam("action", "get_standings")
					.queryParam("league_id", league.getLeagueId())
					.request()
			        .accept(MediaType.APPLICATION_JSON).get().readEntity(new GenericType<List<Position>>() {});
			logger.info("Request succeeded -> positions loaded for league {} : {}", league.getLeagueName(), positions.size());
			
			// set country id
			positions.forEach(position -> {
				position.setCountryId(league.getCountryId());
				logger.info(getPositionKey(position));
			});
			
			return positions;
		}).handle((res, ex) -> {
			if(ex != null) {
				logger.error("An error occurred while loading positions", ex);
				return Lists.newArrayList();
			}
			return res;
		});
	}
	
	public String getPositionKey(Position position) {
		return position.getCountryName() + "|" + 
				position.getLeagueName() + "|" +
				position.getTeamName();
	}

}
