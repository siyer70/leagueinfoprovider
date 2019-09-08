package com.fbleague.infoserver.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class League {
	
	// composition than inheritance
	private final Country country;
	
	public League() {
		this.country = new Country();
	}
	
	@JsonProperty("country_id")
	public String getCountryId() {
		return country.getCountryId();
	}
	
	@JsonProperty("country_id")
	public void setCountryId(String countryId) {
		country.setCountryId(countryId);
	}
	
	@JsonProperty("country_name")
	public String getCountryName() {
		return country.getCountryName();
	}

	@JsonProperty("country_name")
	public void setCountryName(String countryName) {
		country.setCountryName(countryName);
	}	

	@JsonProperty("league_id")
	private String leagueId;

	@JsonProperty("league_name")
	private String leagueName;

	public String getLeagueId() {
		return leagueId;
	}

	public void setLeagueId(String leagueId) {
		this.leagueId = leagueId;
	}

	public String getLeagueName() {
		return leagueName;
	}

	public void setLeagueName(String leagueName) {
		this.leagueName = leagueName;
	}

	@Override
	public String toString() {
		return "League [country_id=" + getCountryId() + ", country_name=" + getCountryName() + 
				", league_id=" + getLeagueId()
				+ ", league_name=" + getLeagueName() + "]";
	}

}
