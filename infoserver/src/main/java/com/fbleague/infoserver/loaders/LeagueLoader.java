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

import com.fbleague.infoserver.model.Country;
import com.fbleague.infoserver.model.League;
import com.google.common.collect.Lists;

@Component
@Profile("!test")
public class LeagueLoader {
	Logger logger = LoggerFactory.getLogger(LeagueLoader.class);

	public CompletableFuture<List<League>> load(WebTarget target, List<Country> countryList) {
		List<CompletableFuture<List<League>>> leagueListFutures = Lists.newArrayList();
		
		countryList.forEach(country -> {
			leagueListFutures.add(load(target, country));
		});
		
		CompletableFuture<Void> allFutures = CompletableFuture.allOf(
				leagueListFutures.toArray(new CompletableFuture[leagueListFutures.size()]));
		
		CompletableFuture<List<League>> allLeagues = allFutures.thenApply(v -> {
			return leagueListFutures.stream()
			.map(future -> future.join())
			.flatMap(List::stream)
			.collect(Collectors.toList());
		});
		
		return allLeagues;
	}
	
	private CompletableFuture<List<League>> load(WebTarget target, Country country) {
		return CompletableFuture.supplyAsync(() -> {
			logger.info("Loading Leagues for country: {}", country.getCountryName());
			List<League> leagues = target.queryParam("action", "get_leagues")
	        		.queryParam("country_id", country.getCountryId())
	        		.request()
	                .accept(MediaType.APPLICATION_JSON).get().readEntity(new GenericType<List<League>>() {});
			logger.info("Request succeeded -> leagues loaded for country {} : {}", country.getCountryName(), leagues.size());
			return leagues;			
		}).handle((res, ex) -> {
			if(ex != null) {
				logger.error("An error occurred while loading leagues", ex);
				return Lists.newArrayList();
			}
			return res;
		});
	}
	

}
