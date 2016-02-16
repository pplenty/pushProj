package com.pushman.domain;

import java.io.Serializable;

public class MSG_LOG_Vo implements Serializable {
	private static final long serialVersionUID = 1L;

	protected int user_data;
	protected int msg_seq;
	protected int cur_state;
	protected String sent_date;
	protected String rslt_date;
	protected String report_date;
	protected String req_date;
	protected int rslt_code;
	protected char rslt_code2;
	protected String rslt_net;
	protected String call_to;
	protected String call_from;
	protected String sms_txt;
	protected int msg_type;
	protected int cont_seq;
	protected String msg_etc1;
	protected String msg_etc2;
	protected String msg_etc3;
	protected String msg_etc4;
	protected String msg_etc5;
	public int getUser_data() {
		return user_data;
	}
	public void setUser_data(int user_data) {
		this.user_data = user_data;
	}
	public int getMsg_seq() {
		return msg_seq;
	}
	public void setMsg_seq(int msg_seq) {
		this.msg_seq = msg_seq;
	}
	public int getCur_state() {
		return cur_state;
	}
	public void setCur_state(int cur_state) {
		this.cur_state = cur_state;
	}
	public String getSent_date() {
		return sent_date;
	}
	public void setSent_date(String sent_date) {
		this.sent_date = sent_date;
	}
	public String getRslt_date() {
		return rslt_date;
	}
	public void setRslt_date(String rslt_date) {
		this.rslt_date = rslt_date;
	}
	public String getReport_date() {
		return report_date;
	}
	public void setReport_date(String report_date) {
		this.report_date = report_date;
	}
	public String getReq_date() {
		return req_date;
	}
	public void setReq_date(String req_date) {
		this.req_date = req_date;
	}
	public int getRslt_code() {
		return rslt_code;
	}
	public void setRslt_code(int rslt_code) {
		this.rslt_code = rslt_code;
	}
	public char getRslt_code2() {
		return rslt_code2;
	}
	public void setRslt_code2(char rslt_code2) {
		this.rslt_code2 = rslt_code2;
	}
	public String getRslt_net() {
		return rslt_net;
	}
	public void setRslt_net(String rslt_net) {
		this.rslt_net = rslt_net;
	}
	public String getCall_to() {
		return call_to;
	}
	public void setCall_to(String call_to) {
		this.call_to = call_to;
	}
	public String getCall_from() {
		return call_from;
	}
	public void setCall_from(String call_from) {
		this.call_from = call_from;
	}
	public String getSms_txt() {
		return sms_txt;
	}
	public void setSms_txt(String sms_txt) {
		this.sms_txt = sms_txt;
	}
	public int getMsg_type() {
		return msg_type;
	}
	public void setMsg_type(int msg_type) {
		this.msg_type = msg_type;
	}
	public int getCont_seq() {
		return cont_seq;
	}
	public void setCont_seq(int cont_seq) {
		this.cont_seq = cont_seq;
	}
	public String getMsg_etc1() {
		return msg_etc1;
	}
	public void setMsg_etc1(String msg_etc1) {
		this.msg_etc1 = msg_etc1;
	}
	public String getMsg_etc2() {
		return msg_etc2;
	}
	public void setMsg_etc2(String msg_etc2) {
		this.msg_etc2 = msg_etc2;
	}
	public String getMsg_etc3() {
		return msg_etc3;
	}
	public void setMsg_etc3(String msg_etc3) {
		this.msg_etc3 = msg_etc3;
	}
	public String getMsg_etc4() {
		return msg_etc4;
	}
	public void setMsg_etc4(String msg_etc4) {
		this.msg_etc4 = msg_etc4;
	}
	public String getMsg_etc5() {
		return msg_etc5;
	}
	public void setMsg_etc5(String msg_etc5) {
		this.msg_etc5 = msg_etc5;
	}
	@Override
	public String toString() {
		return "MSG_LOG_Vo [user_data=" + user_data + ", msg_seq=" + msg_seq + ", cur_state=" + cur_state
				+ ", sent_date=" + sent_date + ", rslt_date=" + rslt_date + ", report_date=" + report_date
				+ ", req_date=" + req_date + ", rslt_code=" + rslt_code + ", rslt_code2=" + rslt_code2 + ", rslt_net="
				+ rslt_net + ", call_to=" + call_to + ", call_from=" + call_from + ", sms_txt=" + sms_txt
				+ ", msg_type=" + msg_type + ", cont_seq=" + cont_seq + ", msg_etc1=" + msg_etc1 + ", msg_etc2="
				+ msg_etc2 + ", msg_etc3=" + msg_etc3 + ", msg_etc4=" + msg_etc4 + ", msg_etc5=" + msg_etc5 + "]";
	}
}
