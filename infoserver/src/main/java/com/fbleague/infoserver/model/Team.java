package com.fbleague.infoserver.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Team {

	@JsonProperty("team_key")
	private String teamKey;

	@JsonProperty("team_name")
	private String teamName;

	@JsonProperty("team_badge")
	private String teamBadge;
	
	public String getTeamKey() {
		return teamKey;
	}
	public void setTeamKey(String teamKey) {
		this.teamKey = teamKey;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public String getTeamBadge() {
		return teamBadge;
	}
	public void setTeamBadge(String teamBadge) {
		this.teamBadge = teamBadge;
	}
	
	
	@Override
	public String toString() {
		return "Team [team_key=" + getTeamKey() + ", team_name=" + getTeamName() + 
				", team_badge=" + getTeamBadge() + "]";
	}

}
