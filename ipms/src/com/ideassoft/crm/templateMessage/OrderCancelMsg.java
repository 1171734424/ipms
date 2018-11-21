package com.ideassoft.crm.templateMessage;

public class OrderCancelMsg implements java.io.Serializable{

	private static final long serialVersionUID = -2284386276136048069L;
	private String first;
	private String firstColor;
	private String orderId;
	private String orderIdColor;
	private String time;
	private String timeColor;
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
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderIdColor() {
		return orderIdColor;
	}
	public void setOrderIdColor(String orderIdColor) {
		this.orderIdColor = orderIdColor;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getTimeColor() {
		return timeColor;
	}
	public void setTimeColor(String timeColor) {
		this.timeColor = timeColor;
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
