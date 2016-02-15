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
	// 로그 스케줄러
	 @Scheduled(fixedDelay = 5000)
	 public void getPushLogSchedular() throws RuntimeException {
		 
		 MultipleDataSource.setDataSourceKey("pushpiaDB");
		 List<TB_SEND_QUE_LOG_Vo> pushLogList = tbSendQueLogDao.selectListAll(null);
		 System.out.println(pushLogList.size());
	 }
	
}
