package com.fbleague.infoserver.config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.env.Environment;

@RunWith(MockitoJUnitRunner.class)
public class ConfigManagerTest {

	@Mock
	Environment env;
	
	@InjectMocks
	ConfigManager classUnderTest;
	
	@Test
	public void shouldBeAbleToReturnEnvironmentVariableValueGivenTheKey() {
		String result = "some value";
		when(env.getProperty(Mockito.anyString())).thenReturn(result);
		assertThat(classUnderTest.getProperty("junk")).isEqualTo(result);
	}

}
