package mongo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import log.logger;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

public class mgFunction {

	/**
	 * 몽고디비에 있는 데이터를 검색함
	 * @param value
	 * @param sort
	 * @return 
	 */
	public Object[] search(String value, String sort) {
		MongoCollection<Document> dbc = mgManager.getCollection("sensor");
		
		//시간-시간-종류(온도/습도) 정보를 받음
		String[] date = value.split("-");
		
		//WHERE절 구성을 위한 BasicDBObejct를 구성함
		BasicDBObject query = new BasicDBObject();

		//regTime 컬럼에 $gte(크거나같고) $lte(작거나 같은)
		query.put("regTime", new BasicDBObject("$gte",date[0]).append("$lte", date[1]));
		//cate 컬럼에 temp(온도)인지 humi(습도)인지 구분
		query.append("cate", date[2]);
		
		//정렬을 위한 BasicDBObject를 구성
		BasicDBObject sortQuery = new BasicDBObject();
		
		long start = System.currentTimeMillis();

		//커서 오라클 ResultSet이랑 비슷한거임
		MongoCursor<Document> cur = null;
		
		Object[] obj = new Object[3];
		
		if(sort.length() != 0) {
			//정렬인데 value 컬럼을 1 오름차순 -1 내림차순
			sortQuery.put(sort, -1);
			//where절로 작성된 쿼리로 찾고 sort를 정렬 (limit()이라는 옵션은 얼마만큼의 행을 리턴할건지)
			cur = dbc.find(query).sort(sortQuery).iterator();
		} else {
			cur = dbc.find(query).iterator();
		}
		
		long end = System.currentTimeMillis();

		int i=0;
		
		ArrayList<Document> docList = new ArrayList<Document>();
		
		//Iterator를 통해서 조회
		while(cur.hasNext()) {
			docList.add(cur.next());
			i++;
		}
		obj[0] = i;
		obj[1] = query;
		obj[2] = docList;
		System.out.println("TOTAL : "+i+"건");
		System.out.println("SEARCH WHERE : "+query.toString());
		System.out.println("SEARCH DATA FROM MONGO : "+i+"건, 소요시간:"+(end-start)/1000.0);
		logger.traceLog("SEARCH DATA FROM MONGO : "+i+"건, 소요시간:"+(end-start)/1000.0);
		
		/**
		 * 결과적으로 아래와 같은 쿼리가 구성됨
		 * { "regTime" : { "$gte" : "20150715143315013" , "$lte" : "20150715144815013"} , "cate" : "temp"}
		 */
		
		return obj;
	}
	
	/**
	 * 몽고디비에 대용량 INSERT
	 * @param docList
	 * @throws IOException
	 */
	public void insert(List<Document> docList) throws IOException {
		//생성된 sensor라는 컬랙션을 조회
		MongoCollection<Document> dbc = mgManager.getCollection("sensor");
		long start = System.currentTimeMillis() ;
		//컬랙션에 List<Document>형식의 데이터를 한번에 밀어넣기
		dbc.insertMany(docList);
		logger.traceLog("INSERT DATA TO MONGO : "+docList.size()+"건, 소요시간:"+(System.currentTimeMillis()-start)/1000.0);
	}
}
