package com.fbleague.infoserver.config;

import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CacheReloadTimer {
	static Logger logger = LoggerFactory.getLogger(CacheReloadTimer.class);

	private final Timer timer;
	
	@Autowired
	private ConfigManager configManager;
	
	public CacheReloadTimer() {
		timer = new Timer();
	}
	
	public void startTimer(TimerTask task) {
		int delay = Integer.parseInt(configManager.getProperty("cacheReloadDelay"));
		int period = Integer.parseInt(configManager.getProperty("cacheReloadPeriod"));
		timer.schedule(task, (long) delay, (long) period);
	}
	
	public void stopTimer() {
		logger.info("Cancelling timer");
		timer.cancel();
		logger.info("Purging future timer tasks"); 
		logger.info("Timer task purge operation result - {}", timer.purge());
	}
}
