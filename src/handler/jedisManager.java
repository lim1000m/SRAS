package handler;

import prop.propManager;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * REDIS 데이터 구조
 *     - String(key, value)
 *     - set(key, value)
 *     - sortedSet(key, value)
 *     - Hashs(key, field, value)
 *     - List(1 key)
 *       - key, value
 *       - key, value
 *       - key, value              
 * @author ESE
 *
 */

public class jedisManager {

	static String ip = propManager.get("redis.ip");
	static int port = Integer.parseInt(propManager.get("redis.port"));
	
	static JedisPool pool = null;
	
	/**
	 * 제디스 풀 생성(싱글톤 방식)
	 * @return
	 */
	public static void getInstance() {
		if(pool == null) {
			pool = new JedisPool(ip, port);
		} 
	}
	
	/**
	 * 풀로부터 리소스 JEDIS 객체 생성
	 * @return
	 */
	public static Jedis getConnection() {
		getInstance();
		Jedis jedis = pool.getResource();
		jedis.configSet("timeout", "100");
		return jedis;
	}
	
	/**
	 * 리소스 반환
	 * 풀로부터 가져온 리소스를 돌려줌
	 * @param jedis
	 */
	public static void returnResource(Jedis jedis) {
		pool.returnResource(jedis);
	}
	
	/**
	 * 풀 삭제
	 */
	public static void destroy() {
		pool.destroy();
	}
	
}
