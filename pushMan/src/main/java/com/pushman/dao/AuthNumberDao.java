package com.pushman.dao;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.pushman.domain.AuthNumberVo;

@Component
public interface AuthNumberDao {
	int insert(AuthNumberVo authNumberVo);

	int delete(AuthNumberVo authNumberVo);

	AuthNumberVo selectOne(Map<String, String> paramMap);
}
