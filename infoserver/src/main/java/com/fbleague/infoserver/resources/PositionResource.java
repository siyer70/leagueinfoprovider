package com.fbleague.infoserver.resources;

import java.util.Optional;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.fbleague.infoserver.cache.CacheManager;
import com.fbleague.infoserver.model.Position;

@Path("/position")
public class PositionResource {
	Logger logger = LoggerFactory.getLogger(PositionResource.class);

	@Autowired
	CacheManager cacheManager;
    
    @GET
    @Path("/query")
    @Produces("application/json")
    public Response getPosition(
    		@QueryParam("country_name") String countryName,
    		@QueryParam("league_name") String leagueName,
    		@QueryParam("team_name") String teamName
   		) {
    	String key = new StringBuilder(Optional.ofNullable(countryName).orElse("UnKnown")).append("|")
    			.append(Optional.ofNullable(leagueName).orElse("UnKnown")).append("|")
    			.append(Optional.ofNullable(teamName).orElse("UnKnown")).toString();
    	logger.info("Received position query with following key: {}", key);
    	Optional<Position> position = Optional.ofNullable(cacheManager.getPosition(key));
    	Response response = position.isPresent()
    							? Response.status(Response.Status.OK).entity(position.get()).build()
    							: Response.status(Response.Status.NOT_FOUND).entity("Invalid country or league or team").build();
    	logger.info("Response for the position query with key \"{}\" is {}", key, response.getEntity().toString());
        return response;
    }

	
}
