package redis;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import prop.propManager;
import redis.clients.jedis.Jedis;
import swiwng.viewComp;

public class jpSetAuto extends Thread{

	//건마다 insert 간격 조정
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
	 * 레디서 리스트 자료형 업로드
	 * @param jedis 
	 * @param name 디바이스 명
	 * @param am 입력할 건수
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

			 	//레디스에 key가 있는 확인
			 	if(!jedis.exists(name)) {
			 		//키가 없으면 rpush를 통해 키를 최초 생성
			 		jedis.rpush(name, Math.round((Math.random() * (int)setCate(cateStr) ))+"_"+mTime+"_"+cateStr);
			 	} else {
			 		//이미 키가 존재하는 상태에서 lpush를 통해 주입
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
			 viewComp.label_6.setText((System.currentTimeMillis()-start)/1000.0+"초");
			 viewComp.label_4.setText("저장 완료");
			 viewComp.textArea_1.append(">>>REDIS 캐쉬에 저장이 완료됨\n\r");
			 viewComp.textArea_1.append(">>>저장된 시작일 : "+startTime+"\n\r");
			 viewComp.textArea_1.append(">>>저장된 종료일 : "+endTime+"\n\r");
			 viewComp.textArea_1.append(">>>저장이 완료되었습니다.\n\r");
			
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