package com.ideassoft.bean.wrapper;

public class LoginGetShift implements java.io.Serializable {

	private static final long serialVersionUID = 8930229866819341758L;

	private String shiftId;
	private String cashbox;

	public String getShiftId() {
		return shiftId;
	}

	public void setShiftId(String shiftId) {
		this.shiftId = shiftId;
	}

	public String getCashbox() {
		return cashbox;
	}

	public void setCashbox(String cashbox) {
		this.cashbox = cashbox;
	}

}
