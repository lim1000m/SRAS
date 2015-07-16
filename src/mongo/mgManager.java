package mongo;

import org.bson.Document;

import prop.propManager;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class mgManager {

	/**
	 * Ŭ���̾�Ʈ ����
	 */
	static MongoClient mongoClient = null;
	static MongoDatabase mDatabase = null;
	
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
		
		//�÷���(���̺�)�� ��ȸ
		MongoCollection<Document> mCollection= mDatabase.getCollection(CollectionName);
		
		if(mCollection == null) {
			//�����÷��� �÷����� ������ ����
			mDatabase.createCollection(CollectionName);
			mCollection = mDatabase.getCollection(CollectionName);
		}else {
			//������ ��ȸ
			mCollection = mDatabase.getCollection(CollectionName);
		}
		return mCollection;
	}
	
	/**
	 * ���� ��� Ŀ�ؼ� 
	 * ���� ����
	 */
	public static void closeMongo() {
		mongoClient.close();
	}
}
