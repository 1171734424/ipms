package com.ideassoft.bean.templateMessage;

public class BookingSuccessMsg implements java.io.Serializable {

	private static final long serialVersionUID = 7697542971572521306L;
	private String first;
	private String firstColor;
	// 预约时间
	private String bookingDate;
	private String bookingDateColor;
	// 预约人
	private String memberName;
	private String memberNameColor;
	// 预约电话
	private String memberPhone;
	private String memberPhoneColor;
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

	public String getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(String bookingDate) {
		this.bookingDate = bookingDate;
	}

	public String getBookingDateColor() {
		return bookingDateColor;
	}

	public void setBookingDateColor(String bookingDateColor) {
		this.bookingDateColor = bookingDateColor;
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

	public String getMemberPhone() {
		return memberPhone;
	}

	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}

	public String getMemberPhoneColor() {
		return memberPhoneColor;
	}

	public void setMemberPhoneColor(String memberPhoneColor) {
		this.memberPhoneColor = memberPhoneColor;
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
