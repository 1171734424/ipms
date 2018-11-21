package com.ideassoft.bean.templateMessage;

public class ReservedMsg implements java.io.Serializable {

	private static final long serialVersionUID = 1640167537333212034L;
	// 招呼语：亲爱的会员，您已成功预约看房
	private String first;
	private String firstColor;
	// 品牌名
	private String type;
	private String typeColor;
	// 酒店、公寓名
	private String name;
	private String nameColor;
	// 预订的东西，如酒店、民宿、公寓、保洁、预约等
	private String productType;
	private String productTypeColor;
	// 房型名字
	private String serviceName;
	private String serviceNameColor;
	// 日期
	private String time;
	private String timeColor;
	// 备注
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTypeColor() {
		return typeColor;
	}

	public void setTypeColor(String typeColor) {
		this.typeColor = typeColor;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameColor() {
		return nameColor;
	}

	public void setNameColor(String nameColor) {
		this.nameColor = nameColor;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getProductTypeColor() {
		return productTypeColor;
	}

	public void setProductTypeColor(String productTypeColor) {
		this.productTypeColor = productTypeColor;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServiceNameColor() {
		return serviceNameColor;
	}

	public void setServiceNameColor(String serviceNameColor) {
		this.serviceNameColor = serviceNameColor;
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

	public String getRemarkColor() {
		return remarkColor;
	}

	public void setRemarkColor(String remarkColor) {
		this.remarkColor = remarkColor;
	}

}
