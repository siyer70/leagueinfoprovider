package com.fbleague.infoserver.config;

import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fbleague.infoserver.cache.CacheManager;

@Component
public class CacheReloadTimerTask extends TimerTask {

	static Logger logger = LoggerFactory.getLogger(CacheReloadTimerTask.class);

	private CacheManager cacheManager;

 	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	@Override
	public void run() {
		logger.info("Cache reloading started..");
		cacheManager.loadOrReloadCache();
		logger.info("Cache reloading ended");
	}

}
