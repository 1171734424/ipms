package com.ideassoft.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "TL_P_CHECKINUSERLOG", schema = "UCR_PMS")
public class CheckinUserLog implements java.io.Serializable {

	private static final long serialVersionUID = -4698124877478552590L;
	private String logId;
	private String branchId;
	private String checkId;
	private String lastCheckUser;
	private String currCheckUser;
	private String recordUser;
	private Date recordTime;
	private String remark;

	public CheckinUserLog() {

	}

	public CheckinUserLog(String logId, String branchId, String checkId, String lastCheckUser, String currCheckUser,
			String recordUser, Date recordTime, String remark) {
		super();
		this.logId = logId;
		this.branchId = branchId;
		this.checkId = checkId;
		this.lastCheckUser = lastCheckUser;
		this.currCheckUser = currCheckUser;
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

	@Column(name = "LAST_CHECKINUSER", nullable = false, length = 60)
	public String getLastCheckUser() {
		return lastCheckUser;
	}

	public void setLastCheckUser(String lastCheckUser) {
		this.lastCheckUser = lastCheckUser;
	}

	@Column(name = "CURR_CHECKINUSER", nullable = false, length = 60)
	public String getCurrCheckUser() {
		return currCheckUser;
	}

	public void setCurrCheckUser(String currCheckUser) {
		this.currCheckUser = currCheckUser;
	}

	@Column(name = "RECORD_USER", nullable = false, length = 8)
	public String getRecordUser() {
		return recordUser;
	}

	public void setRecordUser(String recordUser) {
		this.recordUser = recordUser;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RECORD_TIME")
	public Date getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	@Column(name = "REMARK", length = 200)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
