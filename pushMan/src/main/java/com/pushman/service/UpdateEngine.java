package com.pushman.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.pushman.dao.TB_SEND_QUE_LOG_Dao;
import com.pushman.domain.TB_SEND_QUE_LOG_Vo;
import com.pushman.util.MultipleDataSource;

@Service
public class UpdateEngine {
	@Autowired
	TB_SEND_QUE_LOG_Dao tbSendQueLogDao;
	
	// 푸시 로그 스케줄러
	 @Scheduled(fixedDelay = 5000)
	 public void updatePushLogSchedular() throws RuntimeException {

		 MultipleDataSource.setDataSourceKey("localDB");
		 MultipleDataSource.setDataSourceKey("pushpiaDB");
		 List<TB_SEND_QUE_LOG_Vo> pushLogList = tbSendQueLogDao.selectListAll(null);
//		 System.out.println(pushLogList.size());
		 
		 
	 }
	 
	// 매일 새벽 2시에 로그인 횟수 초기화
//	 @Scheduled(cron="0 0 02 * * ?")
//	 public void HOWSchedular() throws RuntimeException {
//		 System.out.println("02시 00분");
//	 }
	 
	 //ReqUid
	 public void getCdIdFromReqUid(String ReqUid) {
		 
	 }
	
}
