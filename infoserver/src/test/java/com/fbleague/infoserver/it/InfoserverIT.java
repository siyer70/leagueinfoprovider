package com.fbleague.infoserver.it;


import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.fbleague.infoserver.model.Criteria;
import com.fbleague.infoserver.model.Position;

import io.restassured.response.Response;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class InfoserverIT {
	static Logger logger = LoggerFactory.getLogger(InfoserverIT.class);
	private final static String CRITERIA_RESOURCE = "/criterialist";
	private final static String POSITION_RESOURCE = "/position/query";

	@Value("${restassured.baseURI}")
	String baseURI;	
	
	@Value("${restassured.port}")
	int serverPort;	
	
	@Value("${restassured.basePath}")
	String basePath;	

	@Before
	public void setUp() {
		io.restassured.RestAssured.baseURI = this.baseURI;
		io.restassured.RestAssured.port = this.serverPort;
		io.restassured.RestAssured.basePath = this.basePath;
	}
	
	@Test
	public void testPositionResourceIsAbleToRetrievePositionForGivenCriteria() {
		Response criteriaResponse =   
			given()
				.log().all()
			.when()
			    .get(CRITERIA_RESOURCE)
			.then()
				.log().all()
			    .statusCode(HttpStatus.SC_OK)
			    .extract()
			    .response();
		List<Criteria> criteriaList = Arrays.asList(criteriaResponse.as(Criteria[].class));
		assertThat(criteriaList.size()).isGreaterThan(0);
		
		criteriaList.stream().forEach(criteria -> {
			final Position position = 
					given()
					.log().all()
						.queryParam("country_name", criteria.getCountryName())
						.queryParam("league_name", criteria.getLeagueName())
						.queryParam("team_name", criteria.getTeamName())
					.when()
						.get(POSITION_RESOURCE)
					.then()
						.log().all()
						.statusCode(HttpStatus.SC_OK)
						.extract()
						.response()
						.as(Position.class);
			assertThat(Integer.valueOf(position.getOverall_league_position())).isGreaterThan(0);
			
		});
			
	}


}
