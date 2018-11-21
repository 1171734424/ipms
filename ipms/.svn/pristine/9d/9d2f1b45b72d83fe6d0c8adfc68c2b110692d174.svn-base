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
@Table(name = "TB_P_SHIFT", schema = "UCR_PMS")
public class Shift implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = -1083405897763700747L;
	private String logId;
	private String branchId;
	private String lastShift;
	private String currShift;
	private String lastUser;
	private String currUser;
	private Date recordTime;
	private String recordUser;
	private String remark;

	// Constructors

	/** default constructor */
	public Shift() {
	}

	/** minimal constructor */
	public Shift(String logId, String branchId, String lastShift, String currShift, String lastUser, String currUser,
			String recordUser) {
		this.logId = logId;
		this.branchId = branchId;
		this.lastShift = lastShift;
		this.currShift = currShift;
		this.lastUser = lastUser;
		this.currUser = currUser;
		this.recordUser = recordUser;
	}

	/** full constructor */
	public Shift(String logId, String branchId, String lastShift, String currShift, String lastUser, String currUser,
			Date recordTime, String recordUser, String remark) {
		this.logId = logId;
		this.branchId = branchId;
		this.lastShift = lastShift;
		this.currShift = currShift;
		this.lastUser = lastUser;
		this.currUser = currUser;
		this.recordTime = recordTime;
		this.recordUser = recordUser;
		this.remark = remark;
	}

	// Property accessors
	@Id
	@Column(name = "LOG_ID", unique = true, nullable = false, length = 16)
	public String getLogId() {
		return this.logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	@Column(name = "BRANCH_ID", nullable = false, length = 6)
	public String getBranchId() {
		return this.branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	@Column(name = "LAST_SHIFT", length = 17)
	public String getLastShift() {
		return this.lastShift;
	}

	public void setLastShift(String lastShift) {
		this.lastShift = lastShift;
	}

	@Column(name = "CURR_SHIFT", nullable = false, length = 17)
	public String getCurrShift() {
		return this.currShift;
	}

	public void setCurrShift(String currShift) {
		this.currShift = currShift;
	}

	@Column(name = "LAST_USER",  length = 8)
	public String getLastUser() {
		return this.lastUser;
	}

	public void setLastUser(String lastUser) {
		this.lastUser = lastUser;
	}

	@Column(name = "CURR_USER", nullable = false, length = 8)
	public String getCurrUser() {
		return this.currUser;
	}

	public void setCurrUser(String currUser) {
		this.currUser = currUser;
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

}