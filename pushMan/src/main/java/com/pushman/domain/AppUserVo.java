package com.pushman.domain;

import java.io.Serializable;

public class AppUserVo implements Serializable {
	private static final long serialVersionUID = 1L;

	protected int user_id;
	protected String mobile;
	protected String cust_id;
	protected String reg_date;
	protected String last_login_date;
	protected int cnt_login;
	protected String user_etc1;
	protected String user_etc2;
	protected String user_etc3;

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCust_id() {
		return cust_id;
	}

	public void setCust_id(String cust_id) {
		this.cust_id = cust_id;
	}

	public String getReg_date() {
		return reg_date;
	}

	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}

	public String getLast_login_date() {
		return last_login_date;
	}

	public void setLast_login_date(String last_login_date) {
		this.last_login_date = last_login_date;
	}

	public int getCnt_login() {
		return cnt_login;
	}

	public void setCnt_login(int cnt_login) {
		this.cnt_login = cnt_login;
	}

	public String getUser_etc1() {
		return user_etc1;
	}

	public void setUser_etc1(String user_etc1) {
		this.user_etc1 = user_etc1;
	}

	public String getUser_etc2() {
		return user_etc2;
	}

	public void setUser_etc2(String user_etc2) {
		this.user_etc2 = user_etc2;
	}

	public String getUser_etc3() {
		return user_etc3;
	}

	public void setUser_etc3(String user_etc3) {
		this.user_etc3 = user_etc3;
	}

}
