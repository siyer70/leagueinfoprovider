package com.fbleague.infoserver.config;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.core.MultivaluedMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CorsFilterTest {
	
	@Mock
	ContainerResponseContext responseContext;
	
	@Mock
	MultivaluedMap<String, Object> headers;
	
	@InjectMocks
	CorsFilter classUnderTest;
	
	@Test
	public void shouldBeAbleToSetHeaders() throws IOException {
		when(responseContext.getHeaders()).thenReturn(headers);
		classUnderTest.filter(null, responseContext);
		verify(headers, times(3)).add(anyString(), anyString());
	}

}
