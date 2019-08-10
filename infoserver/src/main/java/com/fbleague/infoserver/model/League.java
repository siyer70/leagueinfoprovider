package com.fbleague.infoserver.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class League {
	private String country_id;
	private String country_name;
	private String league_id;
	private String league_name;
	
	public League() {}
	
	public League(String country_id, String country_name, String league_id, String league_name) {
		super();
		this.country_id = country_id;
		this.country_name = country_name;
		this.league_id = league_id;
		this.league_name = league_name;
	}

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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((country_id == null) ? 0 : country_id.hashCode());
		result = prime * result + ((country_name == null) ? 0 : country_name.hashCode());
		result = prime * result + ((league_id == null) ? 0 : league_id.hashCode());
		result = prime * result + ((league_name == null) ? 0 : league_name.hashCode());
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
		League other = (League) obj;
		if (country_id == null) {
			if (other.country_id != null)
				return false;
		} else if (!country_id.equals(other.country_id))
			return false;
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
		if (league_name == null) {
			if (other.league_name != null)
				return false;
		} else if (!league_name.equals(other.league_name))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "League [country_id=" + country_id + ", country_name=" + country_name + ", league_id=" + league_id
				+ ", league_name=" + league_name + "]";
	}

	
}
