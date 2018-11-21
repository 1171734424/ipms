package com.ideassoft.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "TB_P_CLEANRECORD", schema = "UCR_PMS")
public class CleanRecord implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6993878871576656684L;
	private String recordId;
	private String branchId;
	private Date cleanTime;
	private String roomId;
	private String timeArea;
	private String cleanPerson;
	private String status;
	private Date recordTime;
	private String recordUser;
	private String remark;

	public CleanRecord() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CleanRecord(String recordId, String branchId, Date cleanTime, String roomId, String timeArea,
			String cleanPerson, String status, Date recordTime, String recordUser, String remark) {
		super();
		this.recordId = recordId;
		this.branchId = branchId;
		this.cleanTime = cleanTime;
		this.roomId = roomId;
		this.timeArea = timeArea;
		this.cleanPerson = cleanPerson;
		this.status = status;
		this.recordTime = recordTime;
		this.recordUser = recordUser;
		this.remark = remark;
	}

	@Id
	@Column(name = "RECORD_ID", unique = true, nullable = false, length = 17)
	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	@Column(name = "BRANCH_ID", nullable = false, length = 6)
	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CLEAN_TIME")
	public Date getCleanTime() {
		return cleanTime;
	}

	public void setCleanTime(Date cleanTime) {
		this.cleanTime = cleanTime;
	}

	@Column(name = "ROOM_ID", nullable = false, length = 6)
	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	@Column(name = "TIMEAREA", nullable = false, length = 1)
	public String getTimeArea() {
		return timeArea;
	}

	public void setTimeArea(String timeArea) {
		this.timeArea = timeArea;
	}

	@Column(name = "CLEAN_PERSON", nullable = false, length = 8)
	public String getCleanPerson() {
		return cleanPerson;
	}

	public void setCleanPerson(String cleanPerson) {
		this.cleanPerson = cleanPerson;
	}

	@Column(name = "STATUS", nullable = false, length = 1)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RECORD_TIME")
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

}
