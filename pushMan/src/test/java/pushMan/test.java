package pushMan;

import com.pushman.util.CommonMethod;

public class test {

	public static void main(String[] args) {
	    String reqUid = 
	    		"pushman_" + System.currentTimeMillis() + "_" 
                + CommonMethod.count();
	    
	    System.out.println(reqUid);
	}

}
