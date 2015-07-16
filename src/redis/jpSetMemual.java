package redis;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import log.logger;
import prop.propManager;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import swiwng.viewComp;

public class jpSetMemual extends Thread{

	//건마다 insert 간격 조정
	static int intervalTime = Integer.parseInt(propManager.get("redis.inst"));
	
	/**
	 * 파이프라이 객체를 통해 대용량 업로드
	 * @param jedis
	 * @param name
	 * @param am
	 */
	public static void insertRedis(Jedis jedis, String name, int am) {

		//파이프라인 객체 생성
		Pipeline pipeline = jedis.pipelined();
		
		int i =1;		
		
		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat ( "yyyyMMddHHmmss", Locale.KOREA );
		
		try {
			 while(true) {
			 	Date currentTime = new Date();
			 	String mTime = mSimpleDateFormat.format ( currentTime );
				//파이프라인도 여러가지 자료형을 지원함
			 	pipeline.set(name+i,"온도:10도씨,습도:87%,기상:맑음,공기오염도:4단계,장비상태:정상,입력시간:"+mTime);
				if(i == am) 
					break;
				i++;
			 }
			
			long start = System.currentTimeMillis() ;	
			pipeline.sync();
			System.out.println();
			System.out.println("SUCCESS TO SAVE. DONE.");
			logger.traceLog("SET DATA TO REDIS : "+am+"건, 소요시간:"+(System.currentTimeMillis()-start)/1000.0);
			 
		 } catch(Exception e) {
			 e.getStackTrace();
		 }
	}
	
	
	/**
	 * 레디서 리스트 자료형 업로드
	 * @param jedis 
	 * @param name 디바이스 명
	 * @param am 입력할 건수
	 */
	public static void insertRedisList(Jedis jedis, String name, int am) {

		int i =1;		
		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat ( "yyyyMMddHHmmssSSS", Locale.KOREA );

		String startTime = "";
		String endTime = "";
		
		double persent = 0;
		int temp = 1;
		
		try {
			
			 while(true) {
			 	Date currentTime = new Date();
			 	String mTime = mSimpleDateFormat.format ( currentTime );
			 	
			 	if(i == 1) 
			 		startTime = mTime;	

			 	//레디스에 key가 있는 확인
			 	if(!jedis.exists(name)) {
			 		//키가 없으면 rpush를 통해 키를 최초 생성
			 		jedis.rpush(name, Math.round((Math.random() * (int)setCate(name) ))+"_"+mTime+"_"+name);
			 	} else {
			 		//이미 키가 존재하는 상태에서 lpush를 통해 주입
			 		jedis.lpush(name, Math.round((Math.random() * (int)setCate(name) ))+"_"+mTime+"_"+name);
			 	}

			 	Thread.sleep(intervalTime);
			 	
				 if(i == am) {
					 endTime = mTime;
					 System.out.println("");
					 break;
				 }
					 
				 i++;
			 }
			 viewComp.textArea_1.append("저장된 시작일 : "+startTime+"\n\r");
			 viewComp.textArea_1.append("저장된 종료일 : "+endTime+"\n\r");
			 viewComp.textArea_1.append("저장이 완료되었습니다.\n\r");
			
		 } catch(Exception e) {
			 e.getStackTrace();
		 }
	}
	
	/**
	 * 자료 업로드 테스트 위하여 
	 * 온도/습도에 맞는 랜덤 숫자 생성 범위 설정
	 * @param name
	 * @return
	 */
	private static int setCate(String name) {
		
		if(name.split("_")[1].equals("temp")) {
			return 30;
		} else if(name.split("_")[1].equals("humi")) {
			return 100;
		} else {
			return 1000;
		}
	}
}