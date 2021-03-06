package com.fbleague.infoserver.utils;

import java.util.HashMap;
import java.util.Map;

import com.fbleague.infoserver.model.Country;
import com.fbleague.infoserver.model.League;
import com.fbleague.infoserver.model.Position;

public class TestUtils {
	private static TestUtils INSTANCE = new TestUtils();
	public static TestUtils getInstance() {return INSTANCE;}
	
	public Country buildCountryInstance(String code, String name) {
		Country country = new Country();
		country.setCountryId(code);
		country.setCountryName(name);
		return country;
	}
	
	public Map<String, Country> buildCountryMap(String code, String name) {
		Map<String, Country> countryMap = new HashMap<>();
		countryMap.put(code, buildCountryInstance(code,name));
		return countryMap;
	}
	
	public League buildLeagueInstance(String countryCode, String countryName,
		String leagueId, String leagueName) {
		League league = new League();
		league.setCountryId(countryCode);
		league.setCountryName(countryName);
		league.setLeagueId(leagueId);
		league.setLeagueName(leagueName);
		return league;
	}
	
	public Map<String, League> buildLeagueMap(String countryCode, String countryName,
		String leagueId, String leagueName) {
		Map<String, League> leagueMap = new HashMap<>();
		leagueMap.put(leagueId, buildLeagueInstance(countryCode, countryName,
								leagueId, leagueName));
		return leagueMap;
	}
	
	public Position buildPositionInstance(String countryName,
			String leagueName, String teamName, String overallPosition) {
			Position position = new Position();
			position.setCountryId("cid");
			position.setCountryName(countryName);
			position.setLeagueId("lid");
			position.setLeagueName(leagueName);
			position.setTeamId("tid");
			position.setTeamName(teamName);
			position.setOverallLeaguePosition(overallPosition);
			return position;
	}
	
}
