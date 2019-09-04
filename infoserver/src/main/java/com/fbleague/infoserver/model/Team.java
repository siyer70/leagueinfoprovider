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

	public Team(String teamKey, String teamName, String teamBadge) {
		this.teamKey = teamKey;
		this.teamName = teamName;
		this.teamBadge = teamBadge;
	}
	
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((teamKey == null) ? 0 : teamKey.hashCode());
		result = prime * result + ((teamName == null) ? 0 : teamName.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Team other = (Team) obj;
		if (teamKey == null) {
			if (other.teamKey != null) {
				return false;
			}
		} else if (!teamKey.equals(other.teamKey)) {
			return false;
		}
		if (teamName == null) {
			if (other.teamName != null) {
				return false;
			}
		} else if (!teamName.equals(other.teamName)) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return "Team [team_key=" + teamKey + ", team_name=" + teamName + ", team_badge=" + teamBadge + "]";
	}

}
