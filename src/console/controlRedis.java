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
			System.out.print("조회할 키 입력:");
			String input = br.readLine();
			Set<String> keys = jedis.keys(input+"*");
			System.out.println("DATABASE SIZE : "+keys.size());
			System.out.println("******** END TO QUERY");
		} else if(flag.equals("2")) {
			System.out.println("******** STARTING TO SAVE TO THE MONGO DATABASE");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("저장할 키 입력:");
			String input = br.readLine();
			jpGetMessage getMessage = new jpGetMessage();
			mgFunction mf = new mgFunction();
			mf.insert(getMessage.selectCache(input));
		} else if(flag.equals("3")) {
			System.out.println("******** STARTING TO SEARCH DATA IN MONGO DATABASE");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("검색시간(시작시간(14자리)-끝시간(14자리)-종류(humi/temp):");
			String input = br.readLine();
			BufferedReader br_1 = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("정렬컬럼:");
			String input_1 = br_1.readLine();
			mgFunction mf = new mgFunction();
			mf.search(input, input_1);
		} else if(flag.equals("4")) {
			System.out.println("******** MENUAL INSERT TO REDIS");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("장비명(장비명_카테고리):");
			String input = br.readLine();
			BufferedReader br_1 = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("넣을 건수:");
			String input_1 = br_1.readLine();
			jpSetMemual.insertRedisList(jedis, input,Integer.parseInt(input_1));
		} else if(flag.equals("5")){
			jedis.flushAll();
			System.out.println("SUCCESS TO DELETE ALL KEYS IN DATABASE");
		} else {
			System.out.println("=================      종료       =================");
			mgManager.closeMongo();
			System.exit(-1);
		}
		jpManager.returnResource(jedis);
	}
}
