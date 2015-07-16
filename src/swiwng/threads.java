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
			viewComp.lblNewLabel_1.setText("조회 완료");
			
			Iterator iter = keys.iterator();
			
			while(iter.hasNext()) {
				viewComp.textArea.append(">>>"+iter.next()+"\n\r");
			}
			
			viewComp.label_6.setText((System.currentTimeMillis()-start)/1000.0+"초");
			viewComp.textArea.append(">>>"+Integer.toString(keys.size())+"건 조회됨\n\r");
		}else if(flag.equals("2")) {
			viewComp.textArea_1.append(">>>REDIS에 저장을 시작합니다.\n\r");
			viewComp.textArea_1.append(">>>"+map.get("deviceNm")+" / "+map.get("amount")+"건\n\r");
			jpSetAuto autoSet = new jpSetAuto(jedis, map.get("deviceNm"),Integer.parseInt(map.get("amount")));
			autoSet.start();
		}else if(flag.equals("3")){
			long start = System.currentTimeMillis() ;
			viewComp.textArea_2.append(">>>REDIS에 삭제를 시작합니다.\n\r");
			jedis.flushAll();
			viewComp.textArea_2.append(">>>REDIS에 삭제가 완료되었습니다.\n\r");
			viewComp.label_13.setText((System.currentTimeMillis()-start)/1000.0+"초");
			viewComp.label_12.setText("삭제 완료");
		}else if(flag.equals("4")) {
			long start = System.currentTimeMillis() ;
			viewComp.textArea_3.append(">>>MONGODB 저장을 시작합니다.\n\r");
			
			jpGetMessage getMessage = new jpGetMessage();
			mgFunction mf = new mgFunction();
			try {
				List<Document> docList = getMessage.selectCache(map.get("saveKey"));
				if(docList.size() > 0) {
					mf.insert(docList);
					viewComp.textArea_3.append(">>>MONGODB 저장이 완료되었습니다.\n\r");
				} else {
					viewComp.textArea_3.append(">>>이관시킬 데이터가 존재하지 않습니다.\n\r");					
				}
					
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			viewComp.label_19.setText((System.currentTimeMillis()-start)/1000.0+"초");
			viewComp.label_18.setText("저장 완료");
		}else if(flag.equals("5")) {
			viewComp.textArea_4.append(">>>MONGODB 검색을 시작합니다.\n\r");
			long start = System.currentTimeMillis() ;
			
			String dateStr = map.get("startDate")+"-"+map.get("endDate")+"-"+map.get("cate");
			mgFunction mf = new mgFunction();
			Object[] obj = mf.search(dateStr, map.get("sortCol"));
			viewComp.label_25.setText((System.currentTimeMillis()-start)/1000.0+"초");
			viewComp.label_24.setText("검색 완료");
			viewComp.textArea_4.append(">>>시작시간:"+map.get("startDate")+", 종료시간:"+map.get("endDate")+", 타입:"+map.get("cate")+", 정렬:"+map.get("sortCol")+"\n\r");
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
			
			viewComp.textArea_4.append(">>>총 "+obj[0]+"건\n\r");
			viewComp.textArea_4.append(">>>MONGODB 검색이 완료되었습니다.\n\r");
		} else {
			
		}
		
		jpManager.returnResource(jedis);
	}
}
