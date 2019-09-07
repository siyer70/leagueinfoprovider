package com.fbleague.infoserver.config;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class JerseyConfigTest {

	@Spy
	JerseyConfig classUnderTest = new JerseyConfig();
	
	@Test
	public void shouldBeAbleToRegisterJerSeyResources() {
		
		// just a config class, nothing much to test
		// just execute to see it doesn't crash
		// more from coverage perspective
		
		classUnderTest.client();
		classUnderTest.init();
		classUnderTest.api();
		
		verify(classUnderTest).client();
		verify(classUnderTest).init();
		verify(classUnderTest).api();
		
	}

}
