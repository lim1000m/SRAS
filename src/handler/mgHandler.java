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
	 * 몽고 디비 검색 - List<Document>로 반환
	 * 아니면 그냥 Iterator 객체 째로 반환해도 됨
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
	 * 많이 INSERT
	 * @param docList
	 * @throws IOException
	 */
	public void insertMany(List<Document> docList) throws IOException {
		MongoCollection<Document> dbc = mgManager.getCollection("sensor");
		dbc.insertMany(docList);
	}
	
	/**
	 * 하나만  INSERT
	 * @param docList
	 * @throws IOException
	 */
	public void insertOne(Document docList) throws IOException {
		MongoCollection<Document> dbc = mgManager.getCollection("sensor");
		dbc.insertOne(docList);
	}
}
