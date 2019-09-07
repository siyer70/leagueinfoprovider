package com.fbleague.infoserver.config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.google.common.collect.Lists;

import springfox.documentation.swagger.web.InMemorySwaggerResourcesProvider;

@RunWith(MockitoJUnitRunner.class)
public class CombinedResourceProviderTest {

	@Mock
	InMemorySwaggerResourcesProvider resourceProvider;
	
	@InjectMocks
	CombinedResourceProvider classUnderTest;
	
	@Test
	public void shouldBeAbleToReturnConcatenatedStream() {
		when(resourceProvider.get()).thenReturn(Lists.newArrayList());
		assertThat(classUnderTest.get().size()).isEqualTo(1);
	}

}
