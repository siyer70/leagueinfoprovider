package com.fbleague.infoserver.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Position {
	private String country_id;
	private String country_name;
	private String league_id;
	private String league_name;
	private String team_id;
	private String team_name;
	private String overall_league_position;
	public String getCountry_id() {
		return country_id;
	}
	public void setCountry_id(String country_id) {
		this.country_id = country_id;
	}
	public String getCountry_name() {
		return country_name;
	}
	public void setCountry_name(String country_name) {
		this.country_name = country_name;
	}
	public String getLeague_id() {
		return league_id;
	}
	public void setLeague_id(String league_id) {
		this.league_id = league_id;
	}
	public String getLeague_name() {
		return league_name;
	}
	public void setLeague_name(String league_name) {
		this.league_name = league_name;
	}
	public String getTeam_id() {
		return team_id;
	}
	public void setTeam_id(String team_id) {
		this.team_id = team_id;
	}
	public String getTeam_name() {
		return team_name;
	}
	public void setTeam_name(String team_name) {
		this.team_name = team_name;
	}
	public String getOverall_league_position() {
		return overall_league_position;
	}
	public void setOverall_league_position(String overall_league_position) {
		this.overall_league_position = overall_league_position;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((country_name == null) ? 0 : country_name.hashCode());
		result = prime * result + ((league_id == null) ? 0 : league_id.hashCode());
		result = prime * result + ((team_id == null) ? 0 : team_id.hashCode());
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
		Position other = (Position) obj;
		if (country_name == null) {
			if (other.country_name != null)
				return false;
		} else if (!country_name.equals(other.country_name))
			return false;
		if (league_id == null) {
			if (other.league_id != null)
				return false;
		} else if (!league_id.equals(other.league_id))
			return false;
		if (team_id == null) {
			if (other.team_id != null)
				return false;
		} else if (!team_id.equals(other.team_id))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Position [country_name=" + country_name + ", league_id=" + league_id + ", league_name=" + league_name
				+ ", team_id=" + team_id + ", team_name=" + team_name + ", overall_league_position="
				+ overall_league_position + "]";
	}
	
}
