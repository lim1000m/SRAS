package console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class mConsole extends Thread {

	@Override
	public void run() {
		
		while(true) {
			System.out.println("=================          REDIS ���� �ܼ�          =================");
			System.out.println("========= 1.��ȸ | 2.���� | 3.�˻� | 4.�������� | 5.���� | ���� ==========");
			System.out.println("===============================================================");
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				System.out.print("�޴��Է�:");
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
