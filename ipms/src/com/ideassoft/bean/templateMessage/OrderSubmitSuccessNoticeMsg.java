package com.ideassoft.bean.templateMessage;

public class OrderSubmitSuccessNoticeMsg implements java.io.Serializable {

	private static final long serialVersionUID = 8436211468572439218L;
	private String first;
	private String firstColor;
	// 订单号
	private String orderId;
	private String orderIdColor;
	// 预订人
	private String memberName;
	private String memberNameColor;
	private String remark;
	private String remarkColor;

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

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberNameColor() {
		return memberNameColor;
	}

	public void setMemberNameColor(String memberNameColor) {
		this.memberNameColor = memberNameColor;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemarkColor() {
		return remarkColor;
	}

	public void setRemarkColor(String remarkColor) {
		this.remarkColor = remarkColor;
	}

}
