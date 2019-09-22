package com.fbleague.infoserver.resources;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;

import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.core.Response;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.fbleague.infoserver.cache.CacheManager;
import com.fbleague.infoserver.model.Criteria;
import com.google.common.collect.Lists;

@RunWith(MockitoJUnitRunner.class)
public class CriteriaResourceTest {
	@Mock
	CacheManager cacheManager;
	
	@Mock
	AsyncResponse ar;
	
	@InjectMocks
	CriteriaResource classUnderTest;
	
	@Test
	public void shouldBeAbleToReturnCriteriaList() {
		// build test data and set expectations
		Criteria criteria = new Criteria("India", "Ligue 2", "Lens");
		List<Criteria> results = Lists.newArrayList(criteria);
		when(cacheManager.getSearchCombinations()).thenReturn(results);
		
		ArgumentCaptor<Response> acResponse = ArgumentCaptor.forClass(Response.class);
		
		// execute test
		classUnderTest.getCriteriaList(ar);	
		
		Mockito.verify(ar).resume(acResponse.capture());
		
		Response response = (Response) acResponse.getValue();
		
		// assert that result contains the test data in response and response is 200 OK
		assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
		assertThat(response.getEntity()).isInstanceOf(List.class);
		List<Criteria> actualResult = (List<Criteria>)response.getEntity();
		assertThat(actualResult.get(0).toString()).isEqualTo(
				criteria.toString());		
	}


}
