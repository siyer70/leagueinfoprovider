package com.fbleague.infoserver.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Team {
	private String team_key;
	private String team_name;
	private String team_badge;
	public Team(String team_key, String team_name, String team_badge) {
		this.team_key = team_key;
		this.team_name = team_name;
		this.team_badge = team_badge;
	}
	public String getTeam_key() {
		return team_key;
	}
	public void setTeam_key(String team_key) {
		this.team_key = team_key;
	}
	public String getTeam_name() {
		return team_name;
	}
	public void setTeam_name(String team_name) {
		this.team_name = team_name;
	}
	public String getTeam_badge() {
		return team_badge;
	}
	public void setTeam_badge(String team_badge) {
		this.team_badge = team_badge;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((team_key == null) ? 0 : team_key.hashCode());
		result = prime * result + ((team_name == null) ? 0 : team_name.hashCode());
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
		if (team_key == null) {
			if (other.team_key != null)
				return false;
		} else if (!team_key.equals(other.team_key))
			return false;
		if (team_name == null) {
			if (other.team_name != null)
				return false;
		} else if (!team_name.equals(other.team_name))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Team [team_key=" + team_key + ", team_name=" + team_name + ", team_badge=" + team_badge + "]";
	}

}
