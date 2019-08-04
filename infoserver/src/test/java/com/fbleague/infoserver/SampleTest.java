package com.fbleague.infoserver;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.assertj.core.util.Maps;
import org.glassfish.jersey.internal.guava.Lists;
import org.junit.Test;

import com.fbleague.infoserver.model.Country;
import com.fbleague.infoserver.model.Position;

public class SampleTest {
	
	@Test
	public void toValidateOptional() {
		
		
		Optional<Country> optCountry = Optional.ofNullable(getCountry(2));
		
		String id = optCountry.map(c -> c.getCountry_id()).orElse("N/A");
		
		assertEquals(id, "N/A");
		
	}
	
	@Test
	public void validReference() {
		Position position = new Position();
		position.setCountry_name("India");
		Map<String, Position> posmap = new HashMap<>();
		posmap.put("somekey", position);
		Map<String, Map<String, ? extends Object>> cmap = new ConcurrentHashMap<>();
		cmap.put("positions", posmap);
		position = (Position) cmap.get("positions").get("somekey");
		cmap.put("positions", new HashMap<>());
		assertEquals(position.getCountry_name(), "India");
	}

	private Country getCountry(int id) {
		Country country = null;
		if(id==1) {
			country = new Country();
			country.setCountry_id("101");
		}
		return country;
	}

}
