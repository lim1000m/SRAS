package log;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class logger {

	public static String m_FileName = "sras";
	private static FileWriter objfile = null; 
	
	
	public static void traceLog(String log) {
	        String stPath         = "";
	        String stFileName     = "";
	        
	        String m_PathName = "c://devStore//log//";  

	        stPath     = m_PathName;
	        stFileName = m_FileName;
	        SimpleDateFormat formatter1 = new SimpleDateFormat ("yyyyMMdd");
	        SimpleDateFormat formatter2 = new SimpleDateFormat ("HH:mm:ss");
	        
	        String stDate = formatter1.format(new Date());
	        String stTime = formatter2.format(new Date());
	        StringBuffer bufLogPath  = new StringBuffer();       
	                     bufLogPath.append(stPath);
	                     bufLogPath.append(stFileName);
	                     bufLogPath.append("_");
	                     bufLogPath.append(stDate);
	                     bufLogPath.append(".log") ;
	        StringBuffer bufLogMsg = new StringBuffer(); 
	            bufLogMsg.append("[");
	            bufLogMsg.append(stTime);
	            bufLogMsg.append("]");             
	            bufLogMsg.append(log);
	                     
	        try{
	                objfile = new FileWriter(bufLogPath.toString(), true);
	                objfile.write(bufLogMsg.toString());
	                objfile.write("\r\n");
	        }catch(IOException e){
	            
	        }
	        finally
	        {
	            try{
	             objfile.close();
	            }catch(Exception e1){}
	        }
	    }
}
