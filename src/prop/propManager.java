package prop;

import java.io.InputStream;
import java.util.Properties;

public class propManager {

	public static Properties properties;
	
	public static String startTime = "";
	public static String endTime = "";
	
	/**
	 * ������Ƽ ��ȸ �ʱ�ȭ
	 * @param key
	 * @return
	 */
	public static void readProperties() {
		
		properties = new Properties();
		
		try{
			InputStream is = propManager.class.getResourceAsStream("jedis.properties");
			properties.load(is);
		} catch(Exception e) {
			e.getStackTrace();
		}
	}

	/**
	 * ������Ƽ ��ȸ
	 * @param key
	 * @return
	 */
	public static String get(String key) {
		return properties.getProperty(key).toString();
	}
}
