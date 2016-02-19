package com.pushman.scheduler;

public class SchedulerCommon {
	
	public static final long FIXED_DELAY = 2000; // 스케줄러 반복 시간(ms)
	 
	// ReqUid에서 cd_id 추출
	public static int getCdIdFromReqUid(String ReqUid) {
		String[] resultSet = ReqUid.split("_");
		int result = 0;
		//ReqUid가 형식에 맞지 않을 경우 0 리턴
		if (resultSet.length < 4) return result;
		try {
			result =  Integer.parseInt(resultSet[resultSet.length - 1]);
		} catch (Exception e) {
			return 0;
		}
		return result;
	}

	// ReqUid에서 camp_id 추출(project_pushType_campId_cdId)
	public static int getCampIdFromReqUid(String ReqUid) {
		String[] resultSet = ReqUid.split("_");
		int result = 0;
		//ReqUid가 형식에 맞지 않을 경우 0 리턴
		if (resultSet.length < 4) return result;
		try {
			result =  Integer.parseInt(resultSet[resultSet.length - 2]);
		} catch (Exception e) {
			return 0;
		}
		return result;
	}
	
}
