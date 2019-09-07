package com.fbleague.infoserver.model;

public class Criteria {
	private final String countryName;
	private final String leagueName;
	private final String teamName;
	
	public Criteria(String countryName, String leagueName, String teamName) {
		this.countryName = countryName;
		this.leagueName = leagueName;
		this.teamName = teamName;
	}
	public String getCountryName() {
		return countryName;
	}
	public String getLeagueName() {
		return leagueName;
	}
	public String getTeamName() {
		return teamName;
	}
	
	@Override
	public String toString() {
		return "Criteria [countryName=" + getCountryName() 
				+ ", leagueName=" + getLeagueName() 
				+ ", teamName=" + getTeamName() + "]";
	}
	
	
}
