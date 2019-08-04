package com.fbleague.infoserver.loaders;

public interface Loader {
	final String COUNTRIES_KEY = "countries";
	final String LEAGUES_KEY = "leagues";
	final String TEAMS_KEY = "teams";
	final String POSITIONS_KEY = "positions";

	boolean load();
}
