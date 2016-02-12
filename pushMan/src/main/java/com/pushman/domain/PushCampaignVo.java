package com.pushman.domain;

import java.io.Serializable;

public class PushCampaignVo implements Serializable {// WAS 사이에 데이터 이전이 가능하도록 직렬화를
	private static final long serialVersionUID = 1L;

	protected int camp_id;
	protected String push_camp_title;
	protected String push_title;
	protected String push_msg;
	protected String popup_content;
	protected String inapp_content;
	protected String reg_date;
	protected int push_total;
	protected int push_succ;
	protected int push_wait;
	protected int push_fail;
	protected int push_open;
	protected int push_click;
	protected String camp_reqUid;
	public int getCamp_id() {
		return camp_id;
	}
	public void setCamp_id(int camp_id) {
		this.camp_id = camp_id;
	}
	public String getPush_camp_title() {
		return push_camp_title;
	}
	public void setPush_camp_title(String push_camp_title) {
		this.push_camp_title = push_camp_title;
	}
	public String getPush_title() {
		return push_title;
	}
	public void setPush_title(String push_title) {
		this.push_title = push_title;
	}
	public String getPush_msg() {
		return push_msg;
	}
	public void setPush_msg(String push_msg) {
		this.push_msg = push_msg;
	}
	public String getPopup_content() {
		return popup_content;
	}
	public void setPopup_content(String popup_content) {
		this.popup_content = popup_content;
	}
	public String getInapp_content() {
		return inapp_content;
	}
	public void setInapp_content(String inapp_content) {
		this.inapp_content = inapp_content;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public int getPush_total() {
		return push_total;
	}
	public void setPush_total(int push_total) {
		this.push_total = push_total;
	}
	public int getPush_succ() {
		return push_succ;
	}
	public void setPush_succ(int push_succ) {
		this.push_succ = push_succ;
	}
	public int getPush_wait() {
		return push_wait;
	}
	public void setPush_wait(int push_wait) {
		this.push_wait = push_wait;
	}
	public int getPush_fail() {
		return push_fail;
	}
	public void setPush_fail(int push_fail) {
		this.push_fail = push_fail;
	}
	public int getPush_open() {
		return push_open;
	}
	public void setPush_open(int push_open) {
		this.push_open = push_open;
	}
	public int getPush_click() {
		return push_click;
	}
	public void setPush_click(int push_click) {
		this.push_click = push_click;
	}
	public String getCamp_reqUid() {
		return camp_reqUid;
	}
	public void setCamp_reqUid(String camp_reqUid) {
		this.camp_reqUid = camp_reqUid;
	}
	@Override
	public String toString() {
		return "PushCampaignVo [camp_id=" + camp_id + ", push_camp_title=" + push_camp_title + ", push_title="
				+ push_title + ", push_msg=" + push_msg + ", popup_content=" + popup_content + ", inapp_content="
				+ inapp_content + ", reg_date=" + reg_date + ", push_total=" + push_total + ", push_succ=" + push_succ
				+ ", push_wait=" + push_wait + ", push_fail=" + push_fail + ", push_open=" + push_open + ", push_click="
				+ push_click + ", camp_reqUid=" + camp_reqUid + "]";
	}
	
	
	
}
