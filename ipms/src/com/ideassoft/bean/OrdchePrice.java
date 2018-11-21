package com.ideassoft.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ideassoft.core.annotation.interfaces.ColumnDefaultValue;

@Entity
@Table(name = "TB_P_ORDERCHECKPRICE", schema = "UCR_PMS")
public class OrdchePrice implements java.io.Serializable {

	private static final long serialVersionUID = 6360278715968076735L;
	// Fields

	private String dataId;
	private String branchId;
	private String orderId;
	private Date whichDay;
	private Double dayPrice;
	private String status;
	private Date recordTime;
	private String recordUser;
	private String remark;
	private String remark1;
	private String remark2;

	// Constructors

	/** default constructor */
	public OrdchePrice() {
	}

	/** minimal constructor */
	public OrdchePrice(String dataId, String branchId, String orderId, Date whichday, Double dayprice, String status,
			String recordUser) {
		this.dataId = dataId;
		this.branchId = branchId;
		this.orderId = orderId;
		this.whichDay = whichday;
		this.dayPrice = dayprice;
		this.status = status;
		this.recordUser = recordUser;
	}

	/** full constructor */
	public OrdchePrice(String dataId, String branchId, String orderId, Date whichday, Double dayprice, String status,
			String recordUser, Date recordTime, String remark, String remark1, String remark2) {
		this.dataId = dataId;
		this.branchId = branchId;
		this.orderId = orderId;
		this.whichDay = whichday;
		this.dayPrice = dayprice;
		this.status = status;
		this.recordUser = recordUser;
		this.recordTime = recordTime;
		this.remark = remark;
		this.remark1 = remark1;
		this.remark2 = remark2;
	}

	// Property accessors
	@Id
	@Column(name = "DATA_ID", unique = true, nullable = false, length = 17)
	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	@Column(name = "BRANCH_ID", nullable = false, length = 6)
	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	@Column(name = "ORDER_ID", nullable = false, length = 17)
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@Column(name = "WHICHDAY", nullable = false)
	public Date getWhichDay() {
		return whichDay;
	}

	public void setWhichDay(Date whichDay) {
		this.whichDay = whichDay;
	}

	@Column(name = "DAYPRICE", nullable = false)
	public Double getDayPrice() {
		return dayPrice;
	}

	public void setDayPrice(Double dayPrice) {
		this.dayPrice = dayPrice;
	}

	@Column(name = "STATUS", nullable = false, length = 1)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RECORD_TIME", insertable = false, updatable = true)
	@ColumnDefaultValue(value = "sysdate", insertable = false)
	public Date getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	@Column(name = "RECORD_USER", nullable = false, length = 8)
	public String getRecordUser() {
		return recordUser;
	}

	public void setRecordUser(String recordUser) {
		this.recordUser = recordUser;
	}

	@Column(name = "REMARK", length = 200)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "REMARK1", length = 200)
	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	@Column(name = "REMARK2", length = 200)
	public String getRemark2() {
		return remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}
}