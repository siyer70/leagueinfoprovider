package com.fbleague.infoserver.config;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.fbleague.infoserver.CorsFilter;
import com.fbleague.infoserver.resources.CountryResource;
import com.fbleague.infoserver.resources.CriteriaResource;
import com.fbleague.infoserver.resources.PositionResource;

@Component
@Profile("!test")
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig()
    {
    	register(CorsFilter.class);
        register(CountryResource.class);
        register(CriteriaResource.class);
        register(PositionResource.class);
    }
    
	@Bean
	public Client client() {
		return ClientBuilder.newClient(new ClientConfig());
	}
    

}
