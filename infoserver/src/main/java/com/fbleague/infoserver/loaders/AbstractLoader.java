package com.fbleague.infoserver.loaders;

import java.util.Map;

import javax.ws.rs.client.WebTarget;

public abstract class AbstractLoader implements Loader {
	
	protected Map<String, Map<String, ? extends Object>> cache;
	protected WebTarget target;

	public AbstractLoader(Map<String, Map<String, ? extends Object>> cache, WebTarget target) {
		this.cache = cache;
		this.target = target;
	}

	public abstract boolean load();

}
