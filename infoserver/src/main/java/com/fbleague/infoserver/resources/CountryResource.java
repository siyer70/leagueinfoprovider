package com.fbleague.infoserver.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.fbleague.infoserver.CacheManager;
import com.fbleague.infoserver.model.Country;

@Path("/countries")
public class CountryResource {
	Logger logger = LoggerFactory.getLogger(CountryResource.class);

	@Autowired
	CacheManager cacheManager;
    
    @GET
    @Produces("application/json")
    public List<Country> getCountries() {
        return cacheManager.getCountries();
    }

	
}
