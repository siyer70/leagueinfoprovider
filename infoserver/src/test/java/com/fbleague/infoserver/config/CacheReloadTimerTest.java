package com.fbleague.infoserver.config;

import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fbleague.infoserver.cache.CacheManager;

@RunWith(MockitoJUnitRunner.class)
public class CacheReloadTimerTest {
	static Logger logger = LoggerFactory.getLogger(CacheReloadTimerTest.class);

	@Mock
	ConfigManager configManager;
	
	@Mock
	CacheManager cacheManager;

	@InjectMocks
	CacheReloadTimer classUnderTest;
	
	@Test
	public void shouldBeAbleToExecuteCacheReloadTimerTask() throws InterruptedException {
		// set expectations for mocked objects
		when(configManager.getProperty("cacheReloadDelay")).thenReturn("10");
		when(configManager.getProperty("cacheReloadPeriod")).thenReturn("10");
		doNothing().when(cacheManager).loadOrReloadCache();
		
		// number of times to execute cache reload
		int numTimes = 3;

		// specify the timer task to execute
		MyTestCacheReloadTimerTask task = new MyTestCacheReloadTimerTask(this, numTimes);
		task.setCacheManager(cacheManager);
		
		// execute the timer
		classUnderTest.startTimer(task);
		
		logger.info("Timer started");
		synchronized(this) {
			// wait for the timer task to execute
			this.wait();
			
			// task executed for desired number of times - stop the timer now
			classUnderTest.stopTimer();
		}
		
		logger.info("Timer ended");
		
		// verify cache reload invocations
		verify(cacheManager, atLeast(numTimes)).loadOrReloadCache();
		
	}
	
	private static class MyTestCacheReloadTimerTask extends CacheReloadTimerTask {
		int counter = 0;
		private final Object obj;
		private final int numTimes;
		
		public MyTestCacheReloadTimerTask(Object obj, int numTimes) {
			this.obj = obj;
			this.numTimes = numTimes;
		}

		@Override
		public void run() {
			super.run();
			logger.info("Cache reloaded - Occurrence number: " + (++counter));

			if(counter==numTimes) {
				synchronized(obj) {
					obj.notify();
				}
			}
		}
		
	}
}
