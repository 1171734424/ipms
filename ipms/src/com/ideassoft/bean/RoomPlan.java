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
@Table(name = "TB_P_ROOMPLAN", schema = "UCR_PMS")
public class RoomPlan implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 5431004656786891734L;
	private String logId;
	private String branchId;
	private Date validDay;
	private String roomId;
	private String rpId;
	private Double roomPrice;
	private Double cashback;
	private String status;
	private Date recordTime;
	private String recordUser;
	private String remark;
	private String checkId;

	// Constructors

	/** default constructor */
	public RoomPlan() {
	}

	/** minimal constructor */
	public RoomPlan(String logId, String branchId, Date validDay, String roomId, String rpId, Double roomPrice,
			String status, String recordUser) {
		this.logId = logId;
		this.branchId = branchId;
		this.validDay = validDay;
		this.roomId = roomId;
		this.rpId = rpId;
		this.roomPrice = roomPrice;
		this.status = status;
		this.recordUser = recordUser;
	}

	/** full constructor */
	public RoomPlan(String logId, String branchId, Date validDay, String roomId, String rpId, Double roomPrice,
			Double cashback, String status, Date recordTime, String recordUser, String remark) {
		this.logId = logId;
		this.branchId = branchId;
		this.validDay = validDay;
		this.roomId = roomId;
		this.rpId = rpId;
		this.roomPrice = roomPrice;
		this.cashback = cashback;
		this.status = status;
		this.recordTime = recordTime;
		this.recordUser = recordUser;
		this.remark = remark;
	}

	// Property accessors
	@Id
	@Column(name = "LOG_ID", unique = true, nullable = false, length = 15)
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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "VALID_DAY", nullable = false, length = 7)
	public Date getValidDay() {
		return this.validDay;
	}

	public void setValidDay(Date validDay) {
		this.validDay = validDay;
	}

	@Column(name = "ROOM_ID", nullable = false, length = 6)
	public String getRoomId() {
		return this.roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	@Column(name = "RP_ID", nullable = false, length = 3)
	public String getRpId() {
		return this.rpId;
	}

	public void setRpId(String rpId) {
		this.rpId = rpId;
	}

	@Column(name = "ROOM_PRICE", nullable = false, precision = 9)
	public Double getRoomPrice() {
		return this.roomPrice;
	}

	public void setRoomPrice(Double roomPrice) {
		this.roomPrice = roomPrice;
	}

	@Column(name = "CASHBACK", precision = 9)
	public Double getCashback() {
		return this.cashback;
	}

	public void setCashback(Double cashback) {
		this.cashback = cashback;
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
	
	@Column(name = "CHECKID", length = 20)
	public String getCheckId() {
		return checkId;
	}

	public void setCheckId(String checkId) {
		this.checkId = checkId;
	}


}