package handler;

import org.bson.Document;

import prop.propManager;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * 
 * Ǯ�� ����� ������ �ƴ϶� one Ŭ���̾�Ʈ�� ������
 * ������.. ���񽺿� �����Ϸ��� Ŀ�ؼ��� pool ������
 * �����ؾ���
 * �ϳ��� Ŭ���̾�Ʈ�� ���ʿ� �����صΰ� ������ �ʰ� ��� ��
 * 
 * ������ ������ ����
 *  - DBName(��Ű��)
 *    - Collection(�÷���)
 *     - Document
 *     - Document
 *     - Document          
 * @author ESE
 *
 */

public class mgManager {

	/**
	 * Ŭ���̾�Ʈ ����
	 */
	private static MongoClient mongoClient = null;
	private static MongoDatabase mDatabase = null;
	
	/**
	 * ������ Ŭ���̾�Ʈ ����(�̱��� ���)
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
	 * ������ �����ͺ��̽����� ���� �����ͺ��̽� ����
	 * @param dbName
	 * @return
	 */
	public static void getDatabase() {
		mDatabase = mongoClient.getDatabase(propManager.get("mongo.dbnm"));
	}
	
	/**
	 * ���� �����ͺ��̽� �÷���(���̺�) Document ��ȸ 
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
	 * ���� ��� Ŀ�ؼ� 
	 * ���� ����
	 */
	public void closeMongo() {
		mongoClient.close();
	}
}
