package swiwng;

import java.util.HashMap;

public class threadExcuter {

	public void excuter(String flag, HashMap<String, String> map) {
		threads ths = new threads(flag, map);
		ths.start();
	}
}
