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

	//�Ǹ��� insert ���� ����
	static int intervalTime = Integer.parseInt(propManager.get("redis.inst"));
	
	/**
	 * ���������� ��ü�� ���� ��뷮 ���ε�
	 * @param jedis
	 * @param name
	 * @param am
	 */
	public static void insertRedis(Jedis jedis, String name, int am) {

		//���������� ��ü ����
		Pipeline pipeline = jedis.pipelined();
		
		int i =1;		
		
		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat ( "yyyyMMddHHmmss", Locale.KOREA );
		
		try {
			 while(true) {
			 	Date currentTime = new Date();
			 	String mTime = mSimpleDateFormat.format ( currentTime );
				//���������ε� �������� �ڷ����� ������
			 	pipeline.set(name+i,"�µ�:10����,����:87%,���:����,���������:4�ܰ�,������:����,�Է½ð�:"+mTime);
				if(i == am) 
					break;
				i++;
			 }
			
			long start = System.currentTimeMillis() ;	
			pipeline.sync();
			System.out.println();
			System.out.println("SUCCESS TO SAVE. DONE.");
			logger.traceLog("SET DATA TO REDIS : "+am+"��, �ҿ�ð�:"+(System.currentTimeMillis()-start)/1000.0);
			 
		 } catch(Exception e) {
			 e.getStackTrace();
		 }
	}
	
	
	/**
	 * ���� ����Ʈ �ڷ��� ���ε�
	 * @param jedis 
	 * @param name ����̽� ��
	 * @param am �Է��� �Ǽ�
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

			 	//���𽺿� key�� �ִ� Ȯ��
			 	if(!jedis.exists(name)) {
			 		//Ű�� ������ rpush�� ���� Ű�� ���� ����
			 		jedis.rpush(name, Math.round((Math.random() * (int)setCate(name) ))+"_"+mTime+"_"+name);
			 	} else {
			 		//�̹� Ű�� �����ϴ� ���¿��� lpush�� ���� ����
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
			 viewComp.textArea_1.append("����� ������ : "+startTime+"\n\r");
			 viewComp.textArea_1.append("����� ������ : "+endTime+"\n\r");
			 viewComp.textArea_1.append("������ �Ϸ�Ǿ����ϴ�.\n\r");
			
		 } catch(Exception e) {
			 e.getStackTrace();
		 }
	}
	
	/**
	 * �ڷ� ���ε� �׽�Ʈ ���Ͽ� 
	 * �µ�/������ �´� ���� ���� ���� ���� ����
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