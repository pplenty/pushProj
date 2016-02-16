package com.pushman.dao;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.pushman.domain.SmsUserVo;

@Component
public interface SmsUserDao {
	int insert(SmsUserVo smsUser);
	SmsUserVo selectOne(Map<String, String> paramMap);
	SmsUserVo selectOneforEmail(Map<String, String> paramMap);
	SmsUserVo selectOneByUserNo(int user_no);
}











