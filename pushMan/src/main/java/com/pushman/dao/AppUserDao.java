package com.pushman.dao;

import org.springframework.stereotype.Component;

import com.pushman.domain.AppUserVo;

@Component
public interface AppUserDao {
	int insert(AppUserVo appUserVo);
}
