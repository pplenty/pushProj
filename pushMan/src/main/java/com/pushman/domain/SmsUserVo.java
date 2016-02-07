package com.pushman.domain;

import java.io.Serializable;


public class SmsUserVo implements Serializable {//WAS 사이에 데이터 이전이 가능하도록 직렬화를 허용
	private static final long serialVersionUID = 1L;

	protected int no;
	protected String name;
	protected String mobile;
	protected String email;
	protected String password;
	protected String reg_date;

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getReg_date() {
		return reg_date;
	}

	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}

}
