package com.fbleague.infoserver.loaders;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.fbleague.infoserver.model.Country;
import com.google.common.collect.Lists;

@Component
@Profile("!test")
public class CountryLoader  {
	Logger logger = LoggerFactory.getLogger(CountryLoader.class);

	public CompletableFuture<List<Country>> load(WebTarget target) {
		return CompletableFuture.supplyAsync(() -> {
			logger.info("Loading countries");
			List<Country> countries = target.queryParam("action", "get_countries").request()
			        .accept(MediaType.APPLICATION_JSON).get().readEntity(new GenericType<List<Country>>() {});
			logger.info("Request succeeded -> countries loaded : {}", countries.size());
			return countries;			
		}).handle((res, ex) -> {
			if(ex != null) {
				logger.error("An error occurred while loading countries", ex);
				return Lists.newArrayList();
			}
			return res;
		});
	}
	
}
