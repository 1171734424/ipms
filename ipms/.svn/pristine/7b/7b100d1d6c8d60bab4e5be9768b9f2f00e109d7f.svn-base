package com.ideassoft.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ideassoft.core.annotation.interfaces.ColumnDefaultValue;

@Entity
@Table(name = "TL_P_OFFSETLOG", schema = "UCR_PMS")
public class OffsetLog implements Serializable {

	private static final long serialVersionUID = -2725695701186678536L;
	private String logId;
	private String branchId;
	private String checkId;
	private String lastBillId;
	private String currBillId;
	private Double offsetFee;
	private String recordUser;
	private Date recordTime;
	private String remark;
	private String offsetType;

	public OffsetLog() {

	}

	public OffsetLog(String logId, String branchId, String checkId, String lastBillId, String currBillId,
			Double offsetFee, String recordUser, Date recordTime, String remark) {
		super();
		this.logId = logId;
		this.branchId = branchId;
		this.checkId = checkId;
		this.lastBillId = lastBillId;
		this.currBillId = currBillId;
		this.offsetFee = offsetFee;
		this.recordUser = recordUser;
		this.recordTime = recordTime;
		this.remark = remark;
	}

	@Id
	@Column(name = "LOG_ID", nullable = false, length = 19)
	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	@Column(name = "BRANCH_ID", nullable = false, length = 6)
	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	@Column(name = "CHECK_ID", nullable = false, length = 16)
	public String getCheckId() {
		return checkId;
	}

	public void setCheckId(String checkId) {
		this.checkId = checkId;
	}

	@Column(name = "LAST_BILLID", nullable = false, length = 17)
	public String getLastBillId() {
		return lastBillId;
	}

	public void setLastBillId(String lastBillId) {
		this.lastBillId = lastBillId;
	}

	@Column(name = "CURR_BILLID", nullable = false, length = 17)
	public String getCurrBillId() {
		return currBillId;
	}

	public void setCurrBillId(String currBillId) {
		this.currBillId = currBillId;
	}

	@Column(name = "OFFSET_FEE", nullable = false, length = 9)
	public Double getOffsetFee() {
		return offsetFee;
	}

	public void setOffsetFee(Double offsetFee) {
		this.offsetFee = offsetFee;
	}

	@Column(name = "RECORD_USER", nullable = false, length = 8)
	public String getRecordUser() {
		return recordUser;
	}

	public void setRecordUser(String recordUser) {
		this.recordUser = recordUser;
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

	@Column(name = "REMARK", nullable = true, length = 200)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "OFFSET_TYPE", length = 1)
	public String getOffsetType() {
		return offsetType;
	}

	public void setOffsetType(String offsetType) {
		this.offsetType = offsetType;
	}

}
