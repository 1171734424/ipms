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
@Table(name = "TB_P_HALTPLAN", schema = "UCR_PMS")
public class HaltPlan implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = -501739875356869177L;
	private String logId;
	private String branchId;
	private String roomId;
	private String haltType;
	private String haltReason;
	private Date startTime;
	private Date endTime;
	private String status;
	private Date recordTime;
	private String recordUser;
	private String remark;

	// Constructors

	/** default constructor */
	public HaltPlan() {
	}

	/** minimal constructor */
	public HaltPlan(String logId, String branchId, String roomId, String haltType, String haltReason, Date startTime,
			Date endTime, String status, String recordUser) {
		this.logId = logId;
		this.branchId = branchId;
		this.roomId = roomId;
		this.haltType = haltType;
		this.haltReason = haltReason;
		this.startTime = startTime;
		this.endTime = endTime;
		this.status = status;
		this.recordUser = recordUser;
	}

	/** full constructor */
	public HaltPlan(String logId, String branchId, String roomId, String haltType, String haltReason, Date startTime,
			Date endTime, String status, Date recordTime, String recordUser, String remark) {
		this.logId = logId;
		this.branchId = branchId;
		this.roomId = roomId;
		this.haltType = haltType;
		this.haltReason = haltReason;
		this.startTime = startTime;
		this.endTime = endTime;
		this.status = status;
		this.recordTime = recordTime;
		this.recordUser = recordUser;
		this.remark = remark;
	}

	// Property accessors
	@Id
	@Column(name = "LOG_ID", unique = true, nullable = false, length = 12)
	public String getLogId() {
		return this.logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	@Column(name = "BRANCH_ID", nullable = false, length = 4)
	public String getBranchId() {
		return this.branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	@Column(name = "ROOM_ID", nullable = false, length = 6)
	public String getRoomId() {
		return this.roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	@Column(name = "HALT_TYPE", nullable = false, length = 1)
	public String getHaltType() {
		return this.haltType;
	}

	public void setHaltType(String haltType) {
		this.haltType = haltType;
	}

	@Column(name = "HALT_REASON", nullable = false, length = 1)
	public String getHaltReason() {
		return this.haltReason;
	}

	public void setHaltReason(String haltReason) {
		this.haltReason = haltReason;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "START_TIME", nullable = false)
	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "END_TIME", nullable = false)
	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Column(name = "STATUS", nullable = false, length = 2)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
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