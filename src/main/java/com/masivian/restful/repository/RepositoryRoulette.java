package com.masivian.restful.repository;


import java.util.Map;
import java.util.UUID;
import javax.annotation.PostConstruct;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.masivian.restful.objects.ObjectRoulette;

@Repository
public class RepositoryRoulette implements RepositoryRouletteRedis {
	
	private static final String KEY = "ObjectRoulette";
	private RedisTemplate<String, ObjectRoulette> redisTemplate;
	private HashOperations<String, String, ObjectRoulette> hashOperations;
		
	public RepositoryRoulette(RedisTemplate<String, ObjectRoulette> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
	@PostConstruct
	private void init() {
		hashOperations = redisTemplate.opsForHash();
	}
	@Override
	public void createRoulette() {
		ObjectRoulette objectRoulette = new ObjectRoulette ("close");
		hashOperations.put(KEY, UUID.randomUUID().toString(), objectRoulette);
	}
	@Override
	public Map<String, ObjectRoulette> findAll() {
		return hashOperations.entries(KEY);
	}
	@Override
	public void openRoulette(ObjectRoulette objectRoulette, String id) {
		hashOperations.put(KEY, id, objectRoulette);
	}
	@Override
	public void closeRoulette(ObjectRoulette objectRoulette, String id) {
		hashOperations.put(KEY, id, objectRoulette);		
	}
	@Override
	public ObjectRoulette getRoulette(String id) {
		return (ObjectRoulette) hashOperations.get(KEY, id);
	}
	@Override
	public void createBet(ObjectRoulette objectRoulette, String id) {
		hashOperations.put(KEY, id, objectRoulette);		
	}	
	
}
