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
	 * ������ �ִ� �����͸� �˻���
	 * @param value
	 * @param sort
	 * @return 
	 */
	public Object[] search(String value, String sort) {
		MongoCollection<Document> dbc = mgManager.getCollection("sensor");
		
		//�ð�-�ð�-����(�µ�/����) ������ ����
		String[] date = value.split("-");
		
		//WHERE�� ������ ���� BasicDBObejct�� ������
		BasicDBObject query = new BasicDBObject();

		//regTime �÷��� $gte(ũ�ų�����) $lte(�۰ų� ����)
		query.put("regTime", new BasicDBObject("$gte",date[0]).append("$lte", date[1]));
		//cate �÷��� temp(�µ�)���� humi(����)���� ����
		query.append("cate", date[2]);
		
		//������ ���� BasicDBObject�� ����
		BasicDBObject sortQuery = new BasicDBObject();
		
		long start = System.currentTimeMillis();

		//Ŀ�� ����Ŭ ResultSet�̶� ����Ѱ���
		MongoCursor<Document> cur = null;
		
		Object[] obj = new Object[3];
		
		if(sort.length() != 0) {
			//�����ε� value �÷��� 1 �������� -1 ��������
			sortQuery.put(sort, -1);
			//where���� �ۼ��� ������ ã�� sort�� ���� (limit()�̶�� �ɼ��� �󸶸�ŭ�� ���� �����Ұ���)
			cur = dbc.find(query).sort(sortQuery).iterator();
		} else {
			cur = dbc.find(query).iterator();
		}
		
		long end = System.currentTimeMillis();

		int i=0;
		
		ArrayList<Document> docList = new ArrayList<Document>();
		
		//Iterator�� ���ؼ� ��ȸ
		while(cur.hasNext()) {
			docList.add(cur.next());
			i++;
		}
		obj[0] = i;
		obj[1] = query;
		obj[2] = docList;
		System.out.println("TOTAL : "+i+"��");
		System.out.println("SEARCH WHERE : "+query.toString());
		System.out.println("SEARCH DATA FROM MONGO : "+i+"��, �ҿ�ð�:"+(end-start)/1000.0);
		logger.traceLog("SEARCH DATA FROM MONGO : "+i+"��, �ҿ�ð�:"+(end-start)/1000.0);
		
		/**
		 * ��������� �Ʒ��� ���� ������ ������
		 * { "regTime" : { "$gte" : "20150715143315013" , "$lte" : "20150715144815013"} , "cate" : "temp"}
		 */
		
		return obj;
	}
	
	/**
	 * ������ ��뷮 INSERT
	 * @param docList
	 * @throws IOException
	 */
	public void insert(List<Document> docList) throws IOException {
		//������ sensor��� �÷����� ��ȸ
		MongoCollection<Document> dbc = mgManager.getCollection("sensor");
		long start = System.currentTimeMillis() ;
		//�÷��ǿ� List<Document>������ �����͸� �ѹ��� �о�ֱ�
		dbc.insertMany(docList);
		logger.traceLog("INSERT DATA TO MONGO : "+docList.size()+"��, �ҿ�ð�:"+(System.currentTimeMillis()-start)/1000.0);
	}
}
