package redisio;

import redis.clients.jedis.Jedis;

public class RedisClient {

	private static RedisClient instance = null;

	private static Jedis jedis;

	protected RedisClient() {
		// Exists only to defeat instantiation.
	}

	public static RedisClient getInstance() {
		if (instance == null) {
			instance = new RedisClient();
			jedis = new Jedis("localhost");
		}
		return instance;

	}

	public void setRedis(String key, double value) {

		jedis.set(key, String.valueOf(value));
	}

	public String getRedis(String key) {

		return jedis.get(key);
	}
	public String getJedisInfo() {
		
		return jedis.info();
	}

	

}
