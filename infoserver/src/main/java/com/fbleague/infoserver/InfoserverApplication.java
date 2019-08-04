package com.fbleague.infoserver;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InfoserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(InfoserverApplication.class, args);	
	}
	
	@Bean
	public Client client() {
		return ClientBuilder.newClient(new ClientConfig());
	}
	

}
