package com.fbleague.infoserver.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Position {
	
	// composition than inheritance
	private final League league;
	
	public Position() {
		this.league = new League();
	}

	@JsonProperty("country_id")
	public String getCountryId() {
		return league.getCountryId();
	}
	
	@JsonProperty("country_id")
	public void setCountryId(String countryId) {
		league.setCountryId(countryId);
	}
	
	@JsonProperty("country_name")
	public String getCountryName() {
		return league.getCountryName();
	}

	@JsonProperty("country_name")
	public void setCountryName(String countryName) {
		league.setCountryName(countryName);
	}	

	@JsonProperty("league_id")
	public String getLeagueId() {
		return league.getLeagueId();
	}

	@JsonProperty("league_id")
	public void setLeagueId(String leagueId) {
		league.setLeagueId(leagueId);
	}

	@JsonProperty("league_name")
	public String getLeagueName() {
		return league.getLeagueName();
	}

	@JsonProperty("league_name")
	public void setLeagueName(String leagueName) {
		league.setLeagueName(leagueName);
	}

	@JsonProperty("team_id")
	private String teamId;

	@JsonProperty("team_name")
	private String teamName;

	@JsonProperty("overall_league_position")
	private String overallLeaguePosition;

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getOverallLeaguePosition() {
		return overallLeaguePosition;
	}

	public void setOverallLeaguePosition(String overallLeaguePosition) {
		this.overallLeaguePosition = overallLeaguePosition;
	}

	@Override
	public String toString() {
		return "Position [country_id=" + getCountryId() + ", country_name=" + getCountryName() + ", league_id=" 
				+ getLeagueId() + ", league_name=" + getLeagueName()
				+ ", team_id=" + getTeamId() + ", team_name=" + getTeamName() 
				+ ", overall_league_position=" + getOverallLeaguePosition() + "]";
	}

}
