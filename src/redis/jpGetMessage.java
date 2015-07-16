package redis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.bson.Document;

import prop.propManager;
import redis.clients.jedis.Jedis;
import swiwng.viewComp;

public class jpGetMessage {

	/**
	 * 제디스에서 선택된 패턴에 해당하는 key를 조회 후 
	 * key에 해당하는 value를 조회
	 * @param pattern
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	public List<Document> selectCache(String pattern) throws IOException {
		
		int intervalTime = Integer.parseInt(propManager.get("redis.inst"));
		
		viewComp.textArea_3.append(">>>REDIS에서 조회를 시작합니다.\n\r");
		
		//제디스 풀에서 리소스 생성
		Jedis jedis = jpManager.getConnection();

		//패턴 조회
		Set<String> keys = jedis.keys(pattern);

		Iterator iter = keys.iterator();
		Iterator iterCnt = keys.iterator();
		List<Document> docList = new ArrayList<Document>();

		int total = 0;
		
		//프로그레스바에서 사용하기 위해서 사이즈 가져옴
		//리스트 개수가 아닌 리스트 하위 자료 개수
		while(iterCnt.hasNext()) {
			total += returnSize(jedis, iterCnt.next().toString());
		}
		double cnt = total;
		int j = 1;
		
		String key = "";
		while(iter.hasNext()) {
			//키 조회
			key = (String)iter.next();
			//LIST 형태의 자료형에 하위 LIST 목록을 가져오기 위해서 SIZE 조회
			int size = returnSize(jedis, key);
			//하나의 key가 가지고 있는 value 리스트를 조회
			for(int i=0; i<size; i++) {
				Document doc = new Document();
				//lpop를 통해서 pop하는 순간 redis에서 remove
				String content = jedis.lpop(key);
				String[] contents = content.split("_");
				doc.put("deviceName", key.split("_")[0]); //장비명
				doc.put("cate", contents[2]); //종류
				doc.put("value", contents[0]); //값
				doc.put("regTime", contents[1]); //등록시간
				//리스트에 추가
				docList.add(doc);
				try {
					Thread.sleep(intervalTime);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				double persent = (j/cnt)*100.0;
				
				viewComp.progressBar_1.setValue((int)persent);
				
				if(viewComp.progressBar_1.getValue() == 100) {
					viewComp.progressBar_1.setValue(0);
				}
				j++;
			}
		} 
		//종료 후 리소스 번환
		jpManager.returnResource(jedis);
		
		System.out.println("GET DATA FROM REDIS FOR INSRTING TO MONGO DB : "+docList.size());		
		return docList;
	}
	
	/**
	 * 리스트 자료형의 길이 조회
	 * @param jedis
	 * @param key
	 * @return
	 */
	public int returnSize(Jedis jedis, String key) {
		return jedis.llen(key).intValue();
	}
}
