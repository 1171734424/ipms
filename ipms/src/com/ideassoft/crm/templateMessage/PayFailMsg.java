package com.ideassoft.crm.templateMessage;

public class PayFailMsg implements java.io.Serializable {

	private static final long serialVersionUID = -768132855717869332L;
	private String first;
	private String firstColor;
	// 订单号
	private String folioId;
	private String folioIdColor;
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

	public String getFolioId() {
		return folioId;
	}

	public void setFolioId(String folioId) {
		this.folioId = folioId;
	}

	public String getFolioIdColor() {
		return folioIdColor;
	}

	public void setFolioIdColor(String folioIdColor) {
		this.folioIdColor = folioIdColor;
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
