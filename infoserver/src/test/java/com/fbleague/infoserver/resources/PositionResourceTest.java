package com.fbleague.infoserver.resources;

import static org.mockito.Mockito.when;

import javax.ws.rs.core.Response;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import org.mockito.junit.MockitoJUnitRunner;

import com.fbleague.infoserver.cache.CacheManager;
import com.fbleague.infoserver.model.Position;
import com.fbleague.infoserver.utils.TestUtils;

@RunWith(MockitoJUnitRunner.class)
public class PositionResourceTest {
	@Mock
	CacheManager cacheManager;
	
	@InjectMocks
	PositionResource classUnderTest;
	
	@Test
	public void shouldBeAbleToGetPosition() {
		// build test data and set expectations
		String countryName = "India";
		String leagueName = "Ligue 2";
		String teamName = "Lens";
		Position position = TestUtils.getInstance().buildPositionInstance(
				countryName, leagueName, teamName, "1");
		when(cacheManager.getPosition(any())).thenReturn(position);
		
		// execute test
		Response response = classUnderTest.getPosition(countryName, leagueName, teamName);
		
		// assert that result contains the test data in response and response is 200 OK
		assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
		assertThat(response.getEntity()).isInstanceOf(Position.class);
		assertThat(((Position)response.getEntity()).toString()).isEqualTo(position.toString());
	}

	public void shouldNotReturnPositionForInvalidSearchKey() {
		// set expectations
		when(cacheManager.getPosition(any())).thenReturn(null);
		
		// execute test
		Response response = classUnderTest.getPosition("xxxx", "xxxx", "Lens");
		
		// assert that result contains the test data in response and response is 404
		assertThat(response.getStatus()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
		assertThat(response.getEntity()).isInstanceOf(String.class);
		assertThat(((Position)response.getEntity()).toString()).isEqualTo("Invalid country or league or team");
	}
}
