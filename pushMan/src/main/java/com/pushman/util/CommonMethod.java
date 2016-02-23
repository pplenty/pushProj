package com.pushman.util;

public class CommonMethod {
	
	// 오직 한 번에 한 스레드 만이 카운트 값을 얻을 수 있다. 중복 불가(random 생성)!
	static private int count = 0;
	synchronized public static int count() {
		if (count == 100) {
			count = 0;
		}
		return ++count;
	}
	
	// 현재 시간 mariaDB 형식으로 반환
	public static String createNowTime() {
		java.util.Date dt = new java.util.Date();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");// mariaDB DATETIME 형식
		String currentTime = sdf.format(dt);
		return currentTime;
	}
	
	// 현재페이지 시작 인덱스 구하는 함수
	public static int getStartIndexOfPage(int pageNo, int pageSize) {
		return (pageNo - 1) * pageSize;
	}

	// 전체 페이지 수 구하는 함수
	public static int countTotalPage(int pageSize, int totalRecords) {
		int maxPage = totalRecords / pageSize;
		if (totalRecords % pageSize > 0) {
			maxPage++;
		}
		return maxPage;
	}
	
	
	// ReqUid에서 push type 추출(project_pushType_campId /*_cdId*/ )
	public static String getPushTypeFromReqUid(String ReqUid) {
		String[] resultSet = ReqUid.split("_");
		String result = null;
		
		// CampReqUid가 형식에 맞지 않을 경우 null 리턴
		if (resultSet.length < 3)
			return null;
		
		// result에 T/H 둘 중 하나 넣기 
		try {
			result = resultSet[resultSet.length - 2];
		} catch (Exception e) {
			return null;
		}
		
		if ("T".equals(result)) {
			result = "TEXT";
		} else if ("H".equals(result)) {
			result = "RICH";
		}
		
		return result;
	}
	
	
}
