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
@Table(name = "TL_P_EXTENSIONLOG", schema = "UCR_PMS")
public class ExtensionLog implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 4543032192404322669L;
	private String logId;
	private String branchId;
	private String checkId;
	private Date addDay;
	private Date lastDay;
	private String rpId;
	private Double roomPrice;
	private Date recordTime;
	private String recordUser;

	// Constructors

	/** default constructor */
	public ExtensionLog() {
	}

	/** minimal constructor */
	public ExtensionLog(String logId, String branchId, String checkId, Date addDay, Date lastDay, String rpId,
			Date recordTime, String recordUser) {
		this.logId = logId;
		this.branchId = branchId;
		this.checkId = checkId;
		this.addDay = addDay;
		this.lastDay = lastDay;
		this.rpId = rpId;
		this.recordTime = recordTime;
		this.recordUser = recordUser;
	}

	/** full constructor */
	public ExtensionLog(String logId, String branchId, String checkId, Date addDay, Date lastDay, String rpId,
			Double roomPrice, Date recordTime, String recordUser) {
		this.logId = logId;
		this.branchId = branchId;
		this.checkId = checkId;
		this.addDay = addDay;
		this.lastDay = lastDay;
		this.rpId = rpId;
		this.roomPrice = roomPrice;
		this.recordTime = recordTime;
		this.recordUser = recordUser;
	}

	// Property accessors
	@Id
	@Column(name = "LOG_ID", unique = true, nullable = false, length = 13)
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

	@Column(name = "CHECK_ID", nullable = false, length = 14)
	public String getCheckId() {
		return this.checkId;
	}

	public void setCheckId(String checkId) {
		this.checkId = checkId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ADD_DAY", nullable = false, length = 7)
	public Date getAddDay() {
		return this.addDay;
	}

	public void setAddDay(Date addDay) {
		this.addDay = addDay;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_DAY", nullable = false, length = 7)
	public Date getLastDay() {
		return this.lastDay;
	}

	public void setLastDay(Date lastDay) {
		this.lastDay = lastDay;
	}

	@Column(name = "RP_ID", nullable = false, length = 3)
	public String getRpId() {
		return this.rpId;
	}

	public void setRpId(String rpId) {
		this.rpId = rpId;
	}

	@Column(name = "ROOM_PRICE", precision = 9)
	public Double getRoomPrice() {
		return this.roomPrice;
	}

	public void setRoomPrice(Double roomPrice) {
		this.roomPrice = roomPrice;
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

}