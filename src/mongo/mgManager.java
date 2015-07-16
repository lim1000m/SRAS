package mongo;

import org.bson.Document;

import prop.propManager;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class mgManager {

	/**
	 * 클라이언트 선언
	 */
	static MongoClient mongoClient = null;
	static MongoDatabase mDatabase = null;
	
	/**
	 * 몽고디비 클라이언트 생성(싱글톤 방식)
	 * @param ip
	 * @param port
	 */
	public static void getClient(String ip, int port) {
		if(mongoClient == null) {
			mongoClient = new MongoClient(ip, port);
		}
		getDatabase();
	}
	
	/**
	 * 생성된 데이터베이스명을 통해 데이터베이스 생성
	 * @param dbName
	 * @return
	 */
	public static void getDatabase() {
		mDatabase = mongoClient.getDatabase(propManager.get("mongo.dbnm"));
	}
	
	/**
	 * 몽고 데이터베이스 컬렉션(테이블) Document 조회 
	 * @param db
	 * @param CollectionName
	 * @return
	 */
	public static MongoCollection<Document> getCollection(String CollectionName) {
		
		//컬렉션(테이블)을 조회
		MongoCollection<Document> mCollection= mDatabase.getCollection(CollectionName);
		
		if(mCollection == null) {
			//가져올려는 컬렉션이 없으면 생성
			mDatabase.createCollection(CollectionName);
			mCollection = mDatabase.getCollection(CollectionName);
		}else {
			//있으면 조회
			mCollection = mDatabase.getCollection(CollectionName);
		}
		return mCollection;
	}
	
	/**
	 * 몽고 디비 커넥션 
	 * 연결 종료
	 */
	public static void closeMongo() {
		mongoClient.close();
	}
}
