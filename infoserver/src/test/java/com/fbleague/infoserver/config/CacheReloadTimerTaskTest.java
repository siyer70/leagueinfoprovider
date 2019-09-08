package com.fbleague.infoserver.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.atLeastOnce;

import com.fbleague.infoserver.cache.CacheManager;

@RunWith(MockitoJUnitRunner.class)
public class CacheReloadTimerTaskTest {

	@Mock
	CacheManager cacheManager;
	
	@InjectMocks
	CacheReloadTimerTask classUnderTest;
	
	@Test
	public void shouldVerifyCacheReloadIsInvoked() {
		classUnderTest.run();
		verify(cacheManager, atLeastOnce()).loadOrReloadCache();
	}
	
}
