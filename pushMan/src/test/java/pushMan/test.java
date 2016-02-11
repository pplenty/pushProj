package pushMan;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class test {

	public static void main(String[] args) {

		JsonObject jo = new JsonObject();
		jo.addProperty("bizId", "06d388bd180a42018ba0da946d099d09");
		jo.addProperty("msgType", "T");
		jo.addProperty("pushTime", 1800);
		jo.addProperty("pushTitle", "pushPopupTitle");
		jo.addProperty("pushMsg", "pushPopupContent");
		jo.addProperty("inappContent", "innerContent");
		jo.addProperty("pushKey", "1");
		jo.addProperty("pushValue", "http://www.pushpia.com");
		jo.addProperty("reserveTime", "20150417101702");
//		jo.addProperty("reqUid", "pushpia_20150417101702");
//		jo.addProperty("custId", "436149");
		
		JsonArray jarr = new JsonArray();
		JsonObject jo1 = new JsonObject();
		JsonObject jo2 = new JsonObject();
		jo1.addProperty("reqUid", "pushpia_20150417101702");
		jo1.addProperty("custId", "436149");
		jo2.addProperty("reqUid", "pushpia_20150417101712");
		jo2.addProperty("custId", "kohyusik");
		jarr.add(jo1);
		jarr.add(jo2);
		
		jo.add("list", jarr);
		
		System.out.println(jo);
   }

}
