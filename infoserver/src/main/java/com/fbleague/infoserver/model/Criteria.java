package com.fbleague.infoserver.model;

public class Criteria {
	private String countryName;
	private String leagueName;
	private String teamName;
	public Criteria(String countryName, String leagueName, String teamName) {
		this.countryName = countryName;
		this.leagueName = leagueName;
		this.teamName = teamName;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getLeagueName() {
		return leagueName;
	}
	public void setLeagueName(String leagueName) {
		this.leagueName = leagueName;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((countryName == null) ? 0 : countryName.hashCode());
		result = prime * result + ((leagueName == null) ? 0 : leagueName.hashCode());
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
		Criteria other = (Criteria) obj;
		if (countryName == null) {
			if (other.countryName != null) {
				return false;
			}
		} else if (!countryName.equals(other.countryName)) {
			return false;
		}
		if (leagueName == null) {
			if (other.leagueName != null) {
				return false;
			}
		} else if (!leagueName.equals(other.leagueName)) {
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
		return "Criteria [countryName=" + countryName + ", leagueName=" + leagueName + ", teamName=" + teamName + "]";
	}
	
	
}
