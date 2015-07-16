package console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;
import mongo.mgFunction;
import mongo.mgManager;
import redis.jpGetMessage;
import redis.jpManager;
import redis.jpSetMemual;
import redis.clients.jedis.Jedis;

public class controlRedis {

	String flag = "";
	
	controlRedis(String flag) {
		this.flag = flag;
	}
	
	public void excuteFunction() throws IOException {
		Jedis jedis = jpManager.getConnection();
		if(flag.equals("1")) {
			System.out.println("******** STARTING TO QUERY THE ALL VALUE");			
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("��ȸ�� Ű �Է�:");
			String input = br.readLine();
			Set<String> keys = jedis.keys(input+"*");
			System.out.println("DATABASE SIZE : "+keys.size());
			System.out.println("******** END TO QUERY");
		} else if(flag.equals("2")) {
			System.out.println("******** STARTING TO SAVE TO THE MONGO DATABASE");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("������ Ű �Է�:");
			String input = br.readLine();
			jpGetMessage getMessage = new jpGetMessage();
			mgFunction mf = new mgFunction();
			mf.insert(getMessage.selectCache(input));
		} else if(flag.equals("3")) {
			System.out.println("******** STARTING TO SEARCH DATA IN MONGO DATABASE");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("�˻��ð�(���۽ð�(14�ڸ�)-���ð�(14�ڸ�)-����(humi/temp):");
			String input = br.readLine();
			BufferedReader br_1 = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("�����÷�:");
			String input_1 = br_1.readLine();
			mgFunction mf = new mgFunction();
			mf.search(input, input_1);
		} else if(flag.equals("4")) {
			System.out.println("******** MENUAL INSERT TO REDIS");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("����(����_ī�װ�):");
			String input = br.readLine();
			BufferedReader br_1 = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("���� �Ǽ�:");
			String input_1 = br_1.readLine();
			jpSetMemual.insertRedisList(jedis, input,Integer.parseInt(input_1));
		} else if(flag.equals("5")){
			jedis.flushAll();
			System.out.println("SUCCESS TO DELETE ALL KEYS IN DATABASE");
		} else {
			System.out.println("=================      ����       =================");
			mgManager.closeMongo();
			System.exit(-1);
		}
		jpManager.returnResource(jedis);
	}
}
