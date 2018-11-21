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
@Table(name = "TL_P_TRANSFER", schema = "UCR_PMS")
public class Transfer implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 4458584435611557955L;
	private String transferId;
	private String branchId;
	private String lastCheckid;
	private String currCheckid;
	private Date recordTime;
	private String recordUser;
	private String remark;
	private Double transferFee;

	// Constructors

	/** default constructor */
	public Transfer() {
	}

	/** minimal constructor */
	public Transfer(String transferId, String branchId, String lastCheckid, String currCheckid, String recordUser) {
		this.transferId = transferId;
		this.branchId = branchId;
		this.lastCheckid = lastCheckid;
		this.currCheckid = currCheckid;
		this.recordUser = recordUser;
	}

	/** full constructor */
	public Transfer(String transferId, String branchId, String lastCheckid, String currCheckid, Date recordTime,
			String recordUser, String remark) {
		this.transferId = transferId;
		this.branchId = branchId;
		this.lastCheckid = lastCheckid;
		this.currCheckid = currCheckid;
		this.recordTime = recordTime;
		this.recordUser = recordUser;
		this.remark = remark;
	}

	// Property accessors
	@Id
	@Column(name = "TRANSFER_ID", unique = true, nullable = false, length = 15)
	public String getTransferId() {
		return this.transferId;
	}

	public void setTransferId(String transferId) {
		this.transferId = transferId;
	}

	@Column(name = "BRANCH_ID", nullable = false, length = 4)
	public String getBranchId() {
		return this.branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	@Column(name = "LAST_CHECKID", nullable = false, length = 14)
	public String getLastCheckid() {
		return this.lastCheckid;
	}

	public void setLastCheckid(String lastCheckid) {
		this.lastCheckid = lastCheckid;
	}

	@Column(name = "CURR_CHECKID", nullable = false, length = 14)
	public String getCurrCheckid() {
		return this.currCheckid;
	}

	public void setCurrCheckid(String currCheckid) {
		this.currCheckid = currCheckid;
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

	@Column(name = "RECORD_USER", nullable = false, length = 8)
	public String getRecordUser() {
		return this.recordUser;
	}

	public void setRecordUser(String recordUser) {
		this.recordUser = recordUser;
	}

	@Column(name = "REMARK", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "TRANSFER_FEE", precision = 9)
	public Double getTransferFee() {
		return transferFee;
	}

	public void setTransferFee(Double transferFee) {
		this.transferFee = transferFee;
	}

}