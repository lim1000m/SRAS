package console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class mConsole extends Thread {

	@Override
	public void run() {
		
		while(true) {
			System.out.println("=================          REDIS 관리 콘솔          =================");
			System.out.println("========= 1.조회 | 2.저장 | 3.검색 | 4.수동저장 | 5.삭제 | 종료 ==========");
			System.out.println("===============================================================");
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				System.out.print("메뉴입력:");
				String input = br.readLine();
				controlRedis cr = new controlRedis(input);
				cr.excuteFunction();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	
}
