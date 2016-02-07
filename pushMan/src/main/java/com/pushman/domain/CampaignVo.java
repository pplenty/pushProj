package com.pushman.domain;

import java.io.Serializable;

public class CampaignVo implements Serializable {// WAS 사이에 데이터 이전이 가능하도록 직렬화를
	private static final long serialVersionUID = 1L;

	protected int camp_no;
	protected String camp_title;
	protected String camp_content;
	protected int send_total;
	protected int num_error;
	protected String sender_mob;
	protected String send_date;
	protected String attr_sms;
	protected String camp_reg_date;
	protected int user_no;

	public int getCamp_no() {
		return camp_no;
	}

	public void setCamp_no(int camp_no) {
		this.camp_no = camp_no;
	}

	public String getCamp_title() {
		return camp_title;
	}

	public void setCamp_title(String camp_title) {
		this.camp_title = camp_title;
	}

	public String getCamp_content() {
		return camp_content;
	}

	public void setCamp_content(String camp_content) {
		this.camp_content = camp_content;
	}

	public int getSend_total() {
		return send_total;
	}

	public void setSend_total(int send_total) {
		this.send_total = send_total;
	}

	public int getNum_error() {
		return num_error;
	}

	public void setNum_error(int num_error) {
		this.num_error = num_error;
	}

	public String getSender_mob() {
		return sender_mob;
	}

	public void setSender_mob(String sender_mob) {
		this.sender_mob = sender_mob;
	}

	public String getSend_date() {
		return send_date;
	}

	public void setSend_date(String send_date) {
		this.send_date = send_date;
	}

	public String getAttr_sms() {
		return attr_sms;
	}

	public void setAttr_sms(String attr_sms) {
		this.attr_sms = attr_sms;
	}

	public String getCamp_reg_date() {
		return camp_reg_date;
	}

	public void setCamp_reg_date(String camp_reg_date) {
		this.camp_reg_date = camp_reg_date;
	}

	public int getUser_no() {
		return user_no;
	}

	public void setUser_no(int user_no) {
		this.user_no = user_no;
	}

	@Override
	public String toString() {
		return "CampaignVo [camp_no=" + camp_no + ", camp_title=" + camp_title
				+ ", camp_content=" + camp_content + ", send_total="
				+ send_total + ", num_error=" + num_error + ", sender_mob="
				+ sender_mob + ", send_date=" + send_date + ", attr_sms="
				+ attr_sms + ", camp_reg_date=" + camp_reg_date + ", user_no="
				+ user_no + "]";
	}
	
	
	
	

}
