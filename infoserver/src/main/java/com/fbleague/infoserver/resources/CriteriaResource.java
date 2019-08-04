package com.fbleague.infoserver.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.fbleague.infoserver.CacheManager;

@Path("/criterialist")
public class CriteriaResource {
	Logger logger = LoggerFactory.getLogger(CountryResource.class);

	@Autowired
	CacheManager cacheManager;
 
    @GET
    @Produces("application/json")
    public Response getCriteriaList() {
    	logger.info("Received criteria query");
    	Response response = Response.status(Response.Status.OK).entity(cacheManager.getSearchCombinations()).build();
    	logger.info("Response for the criteria query is {}", response.getEntity().toString());
    	return response;
    }
}
