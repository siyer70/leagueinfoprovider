package com.fbleague.infoserver.loaders;

import java.util.Map;

import javax.ws.rs.client.WebTarget;

public interface Loader {

	void load(Map<String, Map<String, ? extends Object>> cache, WebTarget target);
}
