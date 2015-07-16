package handler;

import prop.propManager;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * REDIS ������ ����
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
	 * ���� Ǯ ����(�̱��� ���)
	 * @return
	 */
	public static void getInstance() {
		if(pool == null) {
			pool = new JedisPool(ip, port);
		} 
	}
	
	/**
	 * Ǯ�κ��� ���ҽ� JEDIS ��ü ����
	 * @return
	 */
	public static Jedis getConnection() {
		getInstance();
		Jedis jedis = pool.getResource();
		jedis.configSet("timeout", "100");
		return jedis;
	}
	
	/**
	 * ���ҽ� ��ȯ
	 * Ǯ�κ��� ������ ���ҽ��� ������
	 * @param jedis
	 */
	public static void returnResource(Jedis jedis) {
		pool.returnResource(jedis);
	}
	
	/**
	 * Ǯ ����
	 */
	public static void destroy() {
		pool.destroy();
	}
	
}
