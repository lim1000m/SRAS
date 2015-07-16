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
	 * ���𽺿��� ���õ� ���Ͽ� �ش��ϴ� key�� ��ȸ �� 
	 * �ش��ϴ� List���� value ����
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
				
				/** Ŀ���͸���¡  ���� **/
				doc.put("deviceName", key.split("_")[0]); //����
				doc.put("content", content); //����
				docList.add(doc);
				/** Ŀ���͸���¡  ���� **/
			}
		} 
		//���� �� ���ҽ� ��ȯ
		jedisManager.returnResource(jedis);
		return docList;
	}
	
	/**
	 * ���𽺿��� ���õ� ���Ͽ� �ش��ϴ� key�� ��ȸ �� 
	 * key�� �ش��ϴ� value�� ��ȸ
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
	 * ���𽺿��� ���õ� ���Ͽ� �ش��ϴ� key�� ��ȸ �� 
	 * key�� �ش��ϴ� values�� ��ȸ
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
			doc.put(key, jedis.get(key)); //����
			docList.add(doc);
		} 
		//���� �� ���ҽ� ��ȯ
		jedisManager.returnResource(jedis);
		return docList;
	}
}
