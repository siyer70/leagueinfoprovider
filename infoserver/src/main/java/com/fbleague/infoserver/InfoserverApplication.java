package com.fbleague.infoserver;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;


@SpringBootApplication(
	    scanBasePackages = {
	    		"com.fbleague.infoserver", 
	    		"com.fbleague.infoserver.config", 
	    		"com.fbleague.infoserver.resources",
	    		"io.swagger.jaxrs.listing"
	        }		
)
public class InfoserverApplication {
	static Logger logger = LoggerFactory.getLogger(InfoserverApplication.class);

	@Autowired
	private Environment environment;

	@Value("${server.port}")
	private int port;
	
	public static void main(String[] args) {
		SpringApplication.run(InfoserverApplication.class, args);	
	}
	
	@PostConstruct
	public void init() {
	    logger.info("server.port : {}", port);
	    logger.info("environment.getProperty(\"server.port\") : {}", environment.getProperty("server.port"));
	}
	
}
