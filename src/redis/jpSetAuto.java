package redis;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import prop.propManager;
import redis.clients.jedis.Jedis;
import swiwng.viewComp;

public class jpSetAuto extends Thread{

	//�Ǹ��� insert ���� ����
	int intervalTime;
	Jedis jedis = null;
	String name = "";
	int am = 0;
	
	public jpSetAuto(Jedis jedis, String name, int am) {
		this.intervalTime = Integer.parseInt(propManager.get("redis.inst"));
		this.jedis = jedis;
		this.name = name;
		this.am = am;
	}
	
	/**
	 * ���� ����Ʈ �ڷ��� ���ε�
	 * @param jedis 
	 * @param name ����̽� ��
	 * @param am �Է��� �Ǽ�
	 */
	public void run() {

		int i =1;		
		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat ( "yyyyMMddHHmmssSSS", Locale.KOREA );

		String startTime = "";
		String endTime = "";
		
		long start = System.currentTimeMillis() ;
		
		double cnt = 0;
		double amout = 0;
		
		String cateStr = name.split("_")[1];
		
		try {
			
			 while(true) {
			 	Date currentTime = new Date();
			 	String mTime = mSimpleDateFormat.format ( currentTime );
			 	
			 	if(i == 1) 
			 		startTime = mTime;	

			 	//���𽺿� key�� �ִ� Ȯ��
			 	if(!jedis.exists(name)) {
			 		//Ű�� ������ rpush�� ���� Ű�� ���� ����
			 		jedis.rpush(name, Math.round((Math.random() * (int)setCate(cateStr) ))+"_"+mTime+"_"+cateStr);
			 	} else {
			 		//�̹� Ű�� �����ϴ� ���¿��� lpush�� ���� ����
			 		jedis.lpush(name, Math.round((Math.random() * (int)setCate(cateStr) ))+"_"+mTime+"_"+cateStr);
			 	}

			 	Thread.sleep(intervalTime);
			 	
			 	cnt = i;
			 	amout = am;
			 	
			 	double persent = (cnt/amout)*100.0;
			 	viewComp.progressBar.setValue((int)persent);
			 	
				 if(i == am) {
					 endTime = mTime;
					 viewComp.progressBar.setValue(0);
					 break;
				 }
					 
				 i++;
			 }
			 viewComp.label_6.setText((System.currentTimeMillis()-start)/1000.0+"��");
			 viewComp.label_4.setText("���� �Ϸ�");
			 viewComp.textArea_1.append(">>>REDIS ĳ���� ������ �Ϸ��\n\r");
			 viewComp.textArea_1.append(">>>����� ������ : "+startTime+"\n\r");
			 viewComp.textArea_1.append(">>>����� ������ : "+endTime+"\n\r");
			 viewComp.textArea_1.append(">>>������ �Ϸ�Ǿ����ϴ�.\n\r");
			
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
	private int setCate(String cateStr) {
		
		if(cateStr.equals("temp")) {
			return 30;
		} else if(cateStr.equals("humi")) {
			return 100;
		} else {
			return 1000;
		}
	}
}