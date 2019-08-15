package com.fbleague.infoserver.cache;

import java.util.List;

import com.fbleague.infoserver.model.Criteria;
import com.fbleague.infoserver.model.Position;

public interface CacheManager {
	List<Criteria> getSearchCombinations();
	Position getPosition(String key);
	void loadOrReloadCache();
}