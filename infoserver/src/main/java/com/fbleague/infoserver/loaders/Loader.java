package com.fbleague.infoserver.loaders;

import java.util.Map;

import javax.ws.rs.client.WebTarget;

public interface Loader {
	final String COUNTRIES_KEY = "countries";
	final String LEAGUES_KEY = "leagues";
	final String TEAMS_KEY = "teams";
	final String POSITIONS_KEY = "positions";

	void load(Map<String, Map<String, ? extends Object>> cache, WebTarget target);
}
