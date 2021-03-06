package com.fbleague.infoserver.resources;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ManagedAsync;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.fbleague.infoserver.cache.CacheManager;
import com.fbleague.infoserver.model.Position;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/position")
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "Position Resource for handling position queries", produces = "application/json")
public class PositionResource {
	private static final String UNKNOWN = "UnKnown";
	private static final String OPERATION_TIMED_OUT = "Operation Timed out";

	Logger logger = LoggerFactory.getLogger(PositionResource.class);

	@Autowired
	CacheManager cacheManager;
    
    @GET
    @ManagedAsync
    @Path("/query")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(            //Swagger Annotation
            value = "Returns the standing of the team in the league",
            response = Response.class)
    @ApiResponses(value = {       //Swagger Annotation
            @ApiResponse(code = 200, message = "Results successfully retrieved for the given query"),
            @ApiResponse(code = 404, message = "No such Country or team or league"),
            @ApiResponse(code = 503, message = OPERATION_TIMED_OUT)
    })
    public void getPosition(
    		@QueryParam("country_name") String countryName,
    		@QueryParam("league_name") String leagueName,
    		@QueryParam("team_name") String teamName,
    		@Suspended AsyncResponse asyncResponse
   		) {
    	asyncResponse.setTimeout(3000, TimeUnit.MILLISECONDS);
    	asyncResponse.setTimeoutHandler(ar -> ar.resume(
    			Response.status(Response.Status.SERVICE_UNAVAILABLE)
    			.entity(OPERATION_TIMED_OUT)
    			.build()));
    	String key = new StringBuilder(Optional.ofNullable(countryName).orElse(UNKNOWN)).append("|")
    			.append(Optional.ofNullable(leagueName).orElse(UNKNOWN)).append("|")
    			.append(Optional.ofNullable(teamName).orElse(UNKNOWN)).toString();
    	logger.info("Received position query with following key: {}", key);
    	Optional<Position> position = Optional.ofNullable(cacheManager.getPosition(key));
    	Response response = position.isPresent()
    							? Response.status(Response.Status.OK).entity(position.get()).build()
    							: Response.status(Response.Status.NOT_FOUND).entity("Invalid country or league or team").build();
    	logger.info("Response for the position query with key \"{}\" is {}", key, response.getEntity());
    	asyncResponse.resume(response);
    }

	
}
