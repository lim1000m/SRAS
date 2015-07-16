package main;

import mongo.mgManager;
import prop.propManager;
import swiwng.displayThread;
import swiwng.viewComp;
import console.mConsole;

public class threadHandler{
	
	public void excute() throws InterruptedException {
		
		propManager.readProperties();
		mgManager.getClient(propManager.get("mongo.ip"), Integer.parseInt(propManager.get("mongo.port").toString()));
		
		Thread.sleep(1000);
		
		viewComp dis =new viewComp();
	}

}
