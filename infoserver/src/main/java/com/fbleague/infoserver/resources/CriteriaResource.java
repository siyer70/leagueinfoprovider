package com.fbleague.infoserver.resources;

import java.util.concurrent.TimeUnit;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ManagedAsync;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.fbleague.infoserver.cache.CacheManager;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

@Path("/criterialist")
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "Criteria Resource for retrieve country, league and team combinations", produces = "application/json")
public class CriteriaResource {
	private static final String OPERATION_TIMED_OUT = "Operation Timed out";
	Logger logger = LoggerFactory.getLogger(CriteriaResource.class);
	private CacheManager cacheManager;

	@Autowired
	public CriteriaResource(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}
 
    @GET
    @ManagedAsync
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(            //Swagger Annotation
            value = "Returns the search criteria list covering the combinations of country, league and team",
            response = Response.class)
    @ApiResponses(value = {       //Swagger Annotation
            @ApiResponse(code = 200, message = "Criteria list retrieved successfully"),
            @ApiResponse(code = 503, message = OPERATION_TIMED_OUT)
    })
    public void getCriteriaList(@Suspended AsyncResponse asyncResponse) {
    	// time out in 3 seconds 
    	asyncResponse.setTimeout(3000, TimeUnit.MILLISECONDS);
    	asyncResponse.setTimeoutHandler(ar -> ar.resume(
    			Response.status(Response.Status.SERVICE_UNAVAILABLE)
    			.entity(OPERATION_TIMED_OUT)
    			.build()));
    	logger.info("Received criteria query");
    	Response response = Response.status(Response.Status.OK).entity(cacheManager.getSearchCombinations()).build();
    	logger.info("Response for the criteria query is {}", response.getEntity());
    	asyncResponse.resume(response);
    }
}
