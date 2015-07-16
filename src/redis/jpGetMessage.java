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
	 * ���𽺿��� ���õ� ���Ͽ� �ش��ϴ� key�� ��ȸ �� 
	 * key�� �ش��ϴ� value�� ��ȸ
	 * @param pattern
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	public List<Document> selectCache(String pattern) throws IOException {
		
		int intervalTime = Integer.parseInt(propManager.get("redis.inst"));
		
		viewComp.textArea_3.append(">>>REDIS���� ��ȸ�� �����մϴ�.\n\r");
		
		//���� Ǯ���� ���ҽ� ����
		Jedis jedis = jpManager.getConnection();

		//���� ��ȸ
		Set<String> keys = jedis.keys(pattern);

		Iterator iter = keys.iterator();
		Iterator iterCnt = keys.iterator();
		List<Document> docList = new ArrayList<Document>();

		int total = 0;
		
		//���α׷����ٿ��� ����ϱ� ���ؼ� ������ ������
		//����Ʈ ������ �ƴ� ����Ʈ ���� �ڷ� ����
		while(iterCnt.hasNext()) {
			total += returnSize(jedis, iterCnt.next().toString());
		}
		double cnt = total;
		int j = 1;
		
		String key = "";
		while(iter.hasNext()) {
			//Ű ��ȸ
			key = (String)iter.next();
			//LIST ������ �ڷ����� ���� LIST ����� �������� ���ؼ� SIZE ��ȸ
			int size = returnSize(jedis, key);
			//�ϳ��� key�� ������ �ִ� value ����Ʈ�� ��ȸ
			for(int i=0; i<size; i++) {
				Document doc = new Document();
				//lpop�� ���ؼ� pop�ϴ� ���� redis���� remove
				String content = jedis.lpop(key);
				String[] contents = content.split("_");
				doc.put("deviceName", key.split("_")[0]); //����
				doc.put("cate", contents[2]); //����
				doc.put("value", contents[0]); //��
				doc.put("regTime", contents[1]); //��Ͻð�
				//����Ʈ�� �߰�
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
		//���� �� ���ҽ� ��ȯ
		jpManager.returnResource(jedis);
		
		System.out.println("GET DATA FROM REDIS FOR INSRTING TO MONGO DB : "+docList.size());		
		return docList;
	}
	
	/**
	 * ����Ʈ �ڷ����� ���� ��ȸ
	 * @param jedis
	 * @param key
	 * @return
	 */
	public int returnSize(Jedis jedis, String key) {
		return jedis.llen(key).intValue();
	}
}
