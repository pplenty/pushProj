package pushMan;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import com.google.gson.JsonObject;

public class test {

	public static void main(String[] args) {
		URL url = null;
		URLConnection urlConnection = null;

		// URL 주소
		String sUrl = "http://dev-api.pushpia.com/msg/send/realtime";

		// 파라미터 이름
		String paramName = "d";

		// 파라미터 이름에 대한 값
		String paramValue = null;
		
		JsonObject jo = new JsonObject();
		jo.addProperty("bizId", "06d388bd180a42018ba0da946d099d09");
		jo.addProperty("msgType", "T");
		jo.addProperty("pushTime", 1800);
		jo.addProperty("pushTitle", "test제목");
		jo.addProperty("popupContent", "test_수신 Push의 팝업 메시지");
		jo.addProperty("pushMsg", "test_메세지??!!");
		jo.addProperty("inappContent", "test__앱 내 메시지함에서 보여줄 메시지 내용");
		jo.addProperty("pushKey", "1");
		jo.addProperty("pushValue", "http://www.pushpia.com");
		jo.addProperty("reserveTime", "20150417101702");
		jo.addProperty("reqUid", "pushpia_20150417101702");
		jo.addProperty("custId", "436149");
		
		try {
			paramValue = URLEncoder.encode(jo.toString(), "UTF-8");
			
			// Get방식으로 전송 하기
//			url = new URL(sUrl + "?" + paramName + "=" + paramValue);
//			urlConnection = url.openConnection();
//			printByInputStream(urlConnection.getInputStream());

			// Post방식으로 전송 하기
			url = new URL(sUrl);
			urlConnection = url.openConnection();
			urlConnection.setDoOutput(true);

			printByOutputStream(urlConnection.getOutputStream(), paramName + "=" + paramValue);
			printByInputStream(urlConnection.getInputStream());

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	 // 웹 서버로 부터 받은 웹 페이지 결과를 콘솔에 출력하는 메소드
   public static void printByInputStream(InputStream is) {
       byte[] buf = new byte[1024];
       int len = -1;
      
       try {
           while((len = is.read(buf, 0, buf.length)) != -1) {
               System.out.write(buf, 0, len);
           }
       } catch(IOException e) {
           e.printStackTrace();
       }
   }




   // 웹 서버로 파라미터명과 값의 쌍을 전송하는 메소드
   public static void printByOutputStream(OutputStream os, String msg) {
       try {
           byte[] msgBuf = msg.getBytes("UTF-8");
           os.write(msgBuf, 0, msgBuf.length);
           os.flush();
       } catch(IOException e) {
           e.printStackTrace();
       }
   }

}
