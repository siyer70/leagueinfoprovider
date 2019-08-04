package com.fbleague.infoserver.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import com.fbleague.infoserver.CorsFilter;
import com.fbleague.infoserver.resources.CountryResource;
import com.fbleague.infoserver.resources.CriteriaResource;
import com.fbleague.infoserver.resources.PositionResource;

@Component
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig()
    {
    	register(CorsFilter.class);
        register(CountryResource.class);
        register(CriteriaResource.class);
        register(PositionResource.class);
    }

}
