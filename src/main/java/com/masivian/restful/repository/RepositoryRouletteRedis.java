package com.masivian.restful.repository;

import java.util.Map;
import com.masivian.restful.objects.ObjectRoulette;

public interface RepositoryRouletteRedis {
	Map<String, ObjectRoulette> findAll();
	void openRoulette(ObjectRoulette objectRoulette, String id);
	void closeRoulette(ObjectRoulette objectRoulette, String id);
	void createRoulette ();
	ObjectRoulette getRoulette (String id);	
	void createBet (ObjectRoulette objectRoulette, String id);
}
