package com.ideassoft.bean.templateMessage;

public class CheckinNoticeMsg implements java.io.Serializable {

	private static final long serialVersionUID = 2881188140535936165L;
	// Hi，XXX：\n上海曼哈顿商务大酒店万分期待您在明晚的入住体验, 请您尽量在明天晚上8点前到店
	private String first;
	private String firstColor;
	// 酒店名
	private String hotelName;
	private String hotelNameColor;
	// 房间
	private String roomName;
	private String roomNameColor;
	// 2014-3-11
	private String CheckInDate;
	private String CheckInDateColor;

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

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public String getHotelNameColor() {
		return hotelNameColor;
	}

	public void setHotelNameColor(String hotelNameColor) {
		this.hotelNameColor = hotelNameColor;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getRoomNameColor() {
		return roomNameColor;
	}

	public void setRoomNameColor(String roomNameColor) {
		this.roomNameColor = roomNameColor;
	}

	public String getCheckInDate() {
		return CheckInDate;
	}

	public void setCheckInDate(String checkInDate) {
		CheckInDate = checkInDate;
	}

	public String getCheckInDateColor() {
		return CheckInDateColor;
	}

	public void setCheckInDateColor(String checkInDateColor) {
		CheckInDateColor = checkInDateColor;
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
