package com.pushman.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.pushman.domain.SmsDetailVo;
import com.pushman.domain.SmsUserVo;

@Component
public interface SmsDetailDao {
	List<SmsDetailVo> selectList(Map<String, Object> paramMap);
	
	List<SmsDetailVo> selectListBySms(Map<String, Object> paramMap);
	
	int insert(SmsDetailVo smsDetailVo);
	
	int countAll(Map<String, Object> paramMap);
	
	int countByCamp(Map<String, Object> paramMap);
	
	int cntRsltByCamp(Map<String, Object> paramMap);
	
	int getMaxKey();
	
	SmsDetailVo selectOne(Map<String, String> paramMap);

	SmsDetailVo selectOneforEmail(Map<String, String> paramMap);

	List<SmsDetailVo> selectListLogIsNull();

	int updateSMSLog(SmsDetailVo smsDetailVo);
	
}
