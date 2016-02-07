package com.pushman.domain;

import java.io.Serializable;

public class AuthNumberVo implements Serializable {
	private static final long serialVersionUID = 1L;

	protected int auth_no;
	protected String authNumber;
	protected String create_time;
	protected int uno;

	public int getAuth_no() {
		return auth_no;
	}

	public void setAuth_no(int auth_no) {
		this.auth_no = auth_no;
	}

	public String getAuthNumber() {
		return authNumber;
	}

	public void setAuthNumber(String authNumber) {
		this.authNumber = authNumber;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public int getUno() {
		return uno;
	}

	public void setUno(int uno) {
		this.uno = uno;
	}

}
