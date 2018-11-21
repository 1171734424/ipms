package com.ideassoft.crm.templateMessage;

public class PaySuccessMsg implements java.io.Serializable {

	private static final long serialVersionUID = -4664534252311331943L;
	private String first;
	private String firstColor;
	// 支付金额
	private String pay;
	private String payColor;
	// 支付日期
	private String payDate;
	private String payDateColor;
	// 支付方式
	private String payWay;
	private String payWayColor;
	private String remark;
	private String reamrkColor;

	public String getFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public String getFirstColor() {
		return firstColor;
	}

	public void setFirstColor(String firstColor) {
		this.firstColor = firstColor;
	}

	public String getPay() {
		return pay;
	}

	public void setPay(String pay) {
		this.pay = pay;
	}

	public String getPayColor() {
		return payColor;
	}

	public void setPayColor(String payColor) {
		this.payColor = payColor;
	}

	public String getPayDate() {
		return payDate;
	}

	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}

	public String getPayDateColor() {
		return payDateColor;
	}

	public void setPayDateColor(String payDateColor) {
		this.payDateColor = payDateColor;
	}

	public String getPayWay() {
		return payWay;
	}

	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}

	public String getPayWayColor() {
		return payWayColor;
	}

	public void setPayWayColor(String payWayColor) {
		this.payWayColor = payWayColor;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getReamrkColor() {
		return reamrkColor;
	}

	public void setReamrkColor(String reamrkColor) {
		this.reamrkColor = reamrkColor;
	}

}
