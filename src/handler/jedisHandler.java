package handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.bson.Document;
import redis.clients.jedis.Jedis;

public class jedisHandler {

	/**
	 * 제디스에서 선택된 패턴에 해당하는 key를 조회 후 
	 * 해당하는 List에서 value 추출
	 * @param pattern
	 * @return
	 * @throws IOException
	 */
	public List<Document> selectList(String pattern) throws IOException {

		Jedis jedis = jedisManager.getConnection();

		Set<String> keys = jedis.keys(pattern);

		@SuppressWarnings("rawtypes")
		Iterator iter = keys.iterator();
		List<Document> docList = new ArrayList<Document>();

		String key = "";
		while(iter.hasNext()) {
			key = (String)iter.next();
			int size = jedis.llen(key).intValue();
			for(int i=0; i<size; i++) {
				Document doc = new Document();
				String content = jedis.lpop(key);
				
				/** 커스터마이징  시작 **/
				doc.put("deviceName", key.split("_")[0]); //장비명
				doc.put("content", content); //장비명
				docList.add(doc);
				/** 커스터마이징  종료 **/
			}
		} 
		//종료 후 리소스 번환
		jedisManager.returnResource(jedis);
		return docList;
	}
	
	/**
	 * 제디스에서 선택된 패턴에 해당하는 key를 조회 후 
	 * key에 해당하는 value를 조회
	 * @param pattern
	 * @return String
	 * @throws IOException
	 */
	public String selectValue(String pattern) throws IOException {
		
		Jedis jedis = jedisManager.getConnection();
		String value = jedis.get(pattern);
		jedisManager.returnResource(jedis);
		
		return value;
	}
	
	/**
	 * 제디스에서 선택된 패턴에 해당하는 key를 조회 후 
	 * key에 해당하는 values를 조회
	 * @param pattern
	 * @return String
	 * @throws IOException
	 */
	public List<Document> selectValues(String pattern) throws IOException {
		
		Jedis jedis = jedisManager.getConnection();

		Set<String> keys = jedis.keys(pattern);

		@SuppressWarnings("rawtypes")
		Iterator iter = keys.iterator();
		List<Document> docList = new ArrayList<Document>();

		String key = "";
		while(iter.hasNext()) {
			key = (String)iter.next();
			Document doc = new Document();
			doc.put(key, jedis.get(key)); //장비명
			docList.add(doc);
		} 
		//종료 후 리소스 번환
		jedisManager.returnResource(jedis);
		return docList;
	}
}
