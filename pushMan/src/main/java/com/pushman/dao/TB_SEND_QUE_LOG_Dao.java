package com.pushman.dao;

import java.util.List;
import java.util.Map;

import com.pushman.domain.TB_SEND_QUE_LOG_Vo;

//@Component
public interface TB_SEND_QUE_LOG_Dao {
	List<TB_SEND_QUE_LOG_Vo> selectListAll(Map<String, Object> paramMap);

	List<TB_SEND_QUE_LOG_Vo> selectListByRtnType(Map<String, Object> paramMap);
	
	List<TB_SEND_QUE_LOG_Vo> selectListByRtnType2(Map<String, Object> paramMap);
	
	int getMaxLogId(Map<String, Object> paramMap);
}
