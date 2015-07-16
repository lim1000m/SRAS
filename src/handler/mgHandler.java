package handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

public class mgHandler {

	/**
	 * ���� ��� �˻� - List<Document>�� ��ȯ
	 * �ƴϸ� �׳� Iterator ��ü °�� ��ȯ�ص� ��
	 * @param query
	 * @param sort
	 * @return
	 */
	public List<Document> search(BasicDBObject query, BasicDBObject sort) {
		MongoCollection<Document> dbc = mgManager.getCollection("sensor");

		MongoCursor<Document> cur = null;
		
		if(sort != null) 
			cur = dbc.find(query).sort(sort).iterator();
		else
			cur = dbc.find(query).iterator();

		List<Document> docList = new ArrayList<Document>();
		
		while(cur.hasNext())
			docList.add(cur.next());
		
		return docList;
	}
	
	/**
	 * ���� INSERT
	 * @param docList
	 * @throws IOException
	 */
	public void insertMany(List<Document> docList) throws IOException {
		MongoCollection<Document> dbc = mgManager.getCollection("sensor");
		dbc.insertMany(docList);
	}
	
	/**
	 * �ϳ���  INSERT
	 * @param docList
	 * @throws IOException
	 */
	public void insertOne(Document docList) throws IOException {
		MongoCollection<Document> dbc = mgManager.getCollection("sensor");
		dbc.insertOne(docList);
	}
}
