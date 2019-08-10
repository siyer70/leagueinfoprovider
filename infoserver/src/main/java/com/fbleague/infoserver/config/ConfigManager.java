package com.fbleague.infoserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

@Configuration
@Profile("!test")
public class ConfigManager {

    @Autowired
    private Environment env;

    public String getProperty(String key) {
        return env.getProperty(key);
    }
} 
