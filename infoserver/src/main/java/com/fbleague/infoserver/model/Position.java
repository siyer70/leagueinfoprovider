package com.fbleague.infoserver.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Position extends League {

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
