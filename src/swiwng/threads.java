package swiwng;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.bson.Document;

import mongo.mgFunction;
import redis.jpGetMessage;
import redis.jpManager;
import redis.jpSetAuto;
import redis.clients.jedis.Jedis;

public class threads extends Thread{

	String flag = "";
	HashMap<String, String> map = new HashMap<String, String>();
	
	threads(String flag, HashMap<String, String> map) {
		this.flag = flag;
		this.map = map;
		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void run() {
		Jedis jedis = jpManager.getConnection();
		if(flag.equals("1")) {
			long start = System.currentTimeMillis() ;
			Set<String> keys = jedis.keys(map.get("word"));
			viewComp.lblNewLabel_1.setText("��ȸ �Ϸ�");
			
			Iterator iter = keys.iterator();
			
			while(iter.hasNext()) {
				viewComp.textArea.append(">>>"+iter.next()+"\n\r");
			}
			
			viewComp.label_6.setText((System.currentTimeMillis()-start)/1000.0+"��");
			viewComp.textArea.append(">>>"+Integer.toString(keys.size())+"�� ��ȸ��\n\r");
		}else if(flag.equals("2")) {
			viewComp.textArea_1.append(">>>REDIS�� ������ �����մϴ�.\n\r");
			viewComp.textArea_1.append(">>>"+map.get("deviceNm")+" / "+map.get("amount")+"��\n\r");
			jpSetAuto autoSet = new jpSetAuto(jedis, map.get("deviceNm"),Integer.parseInt(map.get("amount")));
			autoSet.start();
		}else if(flag.equals("3")){
			long start = System.currentTimeMillis() ;
			viewComp.textArea_2.append(">>>REDIS�� ������ �����մϴ�.\n\r");
			jedis.flushAll();
			viewComp.textArea_2.append(">>>REDIS�� ������ �Ϸ�Ǿ����ϴ�.\n\r");
			viewComp.label_13.setText((System.currentTimeMillis()-start)/1000.0+"��");
			viewComp.label_12.setText("���� �Ϸ�");
		}else if(flag.equals("4")) {
			long start = System.currentTimeMillis() ;
			viewComp.textArea_3.append(">>>MONGODB ������ �����մϴ�.\n\r");
			
			jpGetMessage getMessage = new jpGetMessage();
			mgFunction mf = new mgFunction();
			try {
				List<Document> docList = getMessage.selectCache(map.get("saveKey"));
				if(docList.size() > 0) {
					mf.insert(docList);
					viewComp.textArea_3.append(">>>MONGODB ������ �Ϸ�Ǿ����ϴ�.\n\r");
				} else {
					viewComp.textArea_3.append(">>>�̰���ų �����Ͱ� �������� �ʽ��ϴ�.\n\r");					
				}
					
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			viewComp.label_19.setText((System.currentTimeMillis()-start)/1000.0+"��");
			viewComp.label_18.setText("���� �Ϸ�");
		}else if(flag.equals("5")) {
			viewComp.textArea_4.append(">>>MONGODB �˻��� �����մϴ�.\n\r");
			long start = System.currentTimeMillis() ;
			
			String dateStr = map.get("startDate")+"-"+map.get("endDate")+"-"+map.get("cate");
			mgFunction mf = new mgFunction();
			Object[] obj = mf.search(dateStr, map.get("sortCol"));
			viewComp.label_25.setText((System.currentTimeMillis()-start)/1000.0+"��");
			viewComp.label_24.setText("�˻� �Ϸ�");
			viewComp.textArea_4.append(">>>���۽ð�:"+map.get("startDate")+", ����ð�:"+map.get("endDate")+", Ÿ��:"+map.get("cate")+", ����:"+map.get("sortCol")+"\n\r");
			viewComp.textArea_4.append(">>>QUERY:"+obj[1]+"\n\r");
			
			List<Document> result = (List<Document>) obj[2];
			
			double cnt = 0;
			double amout = 0;
			int i = 1;
			
			for(Document doc : result) {
				viewComp.textArea_4.append(">>>"+doc.toString()+"\n\r");
				
				cnt = i;
			 	amout = result.size();
			 	
			 	double persent = (cnt/amout)*100.0;
			 	viewComp.progressBar_2.setValue((int)persent);
			 	
			 	if(i == amout) {
					 viewComp.progressBar_2.setValue(0);
					 break;
				 }
			 	i++;
				
			}
			
			viewComp.textArea_4.append(">>>�� "+obj[0]+"��\n\r");
			viewComp.textArea_4.append(">>>MONGODB �˻��� �Ϸ�Ǿ����ϴ�.\n\r");
		} else {
			
		}
		
		jpManager.returnResource(jedis);
	}
}
