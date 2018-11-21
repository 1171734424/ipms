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
@Table(name = "TB_C_CASHBOX", schema = "UCR_PMS")
public class CashBox implements java.io.Serializable {

	private static final long serialVersionUID = -4050833414114258654L;
	// Fields
	private String dataId;
	private String branchId;
	private String cashBox;
	private Double cashCount;
	private String cashStatus;
	private String recordUser;
	private Date recordTime;
	private String status;
	private String remark;

	// Constructors
	/** default constructor */
	public CashBox() {
	}

	/** minimal constructor */
	public CashBox(String dataId, Double cashCount) {
		this.dataId = dataId;
		this.cashCount = cashCount;
	}

	/** full constructor */
	public CashBox(String dataId, String branchId,String cashBox,Double cashCount, String cashStatus, String recordUser, Date recordTime,
			String status, String remark) {
		this.dataId = dataId;
		this.branchId = branchId;
		this.cashBox = cashBox;
		this.cashCount = cashCount;
		this.cashStatus = cashStatus;
		this.recordUser = recordUser;
		this.recordTime = recordTime;
		this.status = status;
		this.remark = remark;
	}

	// Property accessors
	@Id
	@Column(name = "DATA_ID", unique = true, nullable = false, length = 16)
	public String getDataId() {
		return this.dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}
	
	@Column(name = "BRANCH_ID", nullable = false, length = 6)
	public String getBranchId() {
		return this.branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	@Column(name = "CASH_BOX", length = 1)
	public String getCashBox() {
		return this.cashBox;
	}

	public void setCashBox(String cashBox) {
		this.cashBox = cashBox;
	}

	@Column(name = "CASH_COUNT", nullable = false, precision = 12)
	public Double getCashCount() {
		return this.cashCount;
	}

	public void setCashCount(Double cashCount) {
		this.cashCount = cashCount;
	}

	@Column(name = "CASH_STATUS",  length = 1)
	public String getCashStatus() {
		return this.cashStatus;
	}

	public void setCashStatus(String cashStatus) {
		this.cashStatus = cashStatus;
	}

	@Column(name = "RECORD_USER", nullable = false, length = 8)
	public String getRecordUser() {
		return this.recordUser;
	}

	public void setRecordUser(String recordUser) {
		this.recordUser = recordUser;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RECORD_TIME", insertable = false, updatable = true)
	@ColumnDefaultValue(value = "sysdate", insertable = false)
	public Date getRecordTime() {
		return this.recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	@Column(name = "STATUS", nullable = false, length = 1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "REMARK", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}