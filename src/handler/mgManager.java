package handler;

import org.bson.Document;

import prop.propManager;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * 
 * 풀을 사용한 구조가 아니라 one 클라이언트에 적합한
 * 구조임.. 서비스에 적용하려면 커넥션을 pool 구조로
 * 구성해야함
 * 하나의 클라이언트만 최초에 생성해두고 죽이지 않고 계속 씀
 * 
 * 몽고디비 데이터 구조
 *  - DBName(스키마)
 *    - Collection(컬렉션)
 *     - Document
 *     - Document
 *     - Document          
 * @author ESE
 *
 */

public class mgManager {

	/**
	 * 클라이언트 선언
	 */
	private static MongoClient mongoClient = null;
	private static MongoDatabase mDatabase = null;
	
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
		MongoCollection<Document> mCollection= mDatabase.getCollection(CollectionName);
		mCollection = mDatabase.getCollection(CollectionName);
		return mCollection;
	}
	
	/**
	 * 몽고 디비 커넥션 
	 * 연결 종료
	 */
	public void closeMongo() {
		mongoClient.close();
	}
}
