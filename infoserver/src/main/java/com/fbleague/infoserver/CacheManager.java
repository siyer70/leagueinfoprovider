package com.fbleague.infoserver;

import java.util.List;

import com.fbleague.infoserver.model.Country;
import com.fbleague.infoserver.model.Criteria;
import com.fbleague.infoserver.model.Position;

public interface CacheManager {
	List<Country> getCountries();
	List<Criteria> getSearchCombinations();
	Position getPosition(String key);
	void loadOrReloadCache();
}