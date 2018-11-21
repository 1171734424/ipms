package com.ideassoft.crm.templateMessage;

public class HotelBookingConfirmMsg implements java.io.Serializable {

	private static final long serialVersionUID = 6409079962447288353L;
	// 您好，您已成功预订微信饭店！
	private String first;
	private String firstColor;
	// 12345678
	private String order;
	private String orderColor;
	// 黄瑞峰、黄瑞、黄峰
	private String Name;
	private String NameColor;
	// 2013-11-20
	private String datein;
	private String dateinColor;
	// 2013-11-23
	private String dateout;
	private String dateoutColor;
	// 房间数量2间
	private String number;
	private String numberColor;
	// 豪华大床房（不含早）
	private String roomtype;
	private String roomtypeColor;
	// ￥900
	private String pay;
	private String payColor;
	// 担保订单11月28日12点前可免费变更取消，过时变更取消或未如约入住。
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

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getOrderColor() {
		return orderColor;
	}

	public void setOrderColor(String orderColor) {
		this.orderColor = orderColor;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getNameColor() {
		return NameColor;
	}

	public void setNameColor(String nameColor) {
		NameColor = nameColor;
	}

	public String getDatein() {
		return datein;
	}

	public void setDatein(String datein) {
		this.datein = datein;
	}

	public String getDateinColor() {
		return dateinColor;
	}

	public void setDateinColor(String dateinColor) {
		this.dateinColor = dateinColor;
	}

	public String getDateout() {
		return dateout;
	}

	public void setDateout(String dateout) {
		this.dateout = dateout;
	}

	public String getDateoutColor() {
		return dateoutColor;
	}

	public void setDateoutColor(String dateoutColor) {
		this.dateoutColor = dateoutColor;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getNumberColor() {
		return numberColor;
	}

	public void setNumberColor(String numberColor) {
		this.numberColor = numberColor;
	}

	public String getRoomtype() {
		return roomtype;
	}

	public void setRoomtype(String roomtype) {
		this.roomtype = roomtype;
	}

	public String getRoomtypeColor() {
		return roomtypeColor;
	}

	public void setRoomtypeColor(String roomtypeColor) {
		this.roomtypeColor = roomtypeColor;
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
