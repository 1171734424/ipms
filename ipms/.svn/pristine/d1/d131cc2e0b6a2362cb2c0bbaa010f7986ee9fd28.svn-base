package com.ideassoft.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.ideassoft.core.annotation.interfaces.ColumnDefaultValue;

@Entity
@Table(name = "TB_P_ROOMSTATUS", schema = "UCR_PMS")
public class RoomStatus implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = -8386744994794339714L;
	private String logId;
	private String branchId;
	private String roomType;
	private Integer count;
	private Date recordTime;
	private String recordUser;
	private String remark;
	private Integer campaigns;
	private Integer sellnum;
	private Integer stopnum;
	private Integer nightnum;
	private Integer validnum;
	private Integer invalidnum;
	private String roomPrice;
	private String branchName;

	// Constructors

	/** default constructor */
	public RoomStatus() {
	}

	/** minimal constructor */
	public RoomStatus(String logId, String branchId, String roomType, Integer count, String recordUser) {
		this.logId = logId;
		this.branchId = branchId;
		this.roomType = roomType;
		this.count = count;
		this.recordUser = recordUser;
	}

	/** full constructor */
	public RoomStatus(String logId, String branchId, String roomType, Integer count, Date recordTime,
			String recordUser, String remark) {
		this.logId = logId;
		this.branchId = branchId;
		this.roomType = roomType;
		this.count = count;
		this.recordTime = recordTime;
		this.recordUser = recordUser;
		this.remark = remark;
	}

	// Property accessors
	@Id
	@Column(name = "LOG_ID", unique = true, nullable = false, length = 14)
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

	@Column(name = "ROOM_TYPE", nullable = false, length = 3)
	public String getRoomType() {
		return this.roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	@Column(name = "COUNT", nullable = false, precision = 5, scale = 0)
	public Integer getCount() {
		return this.count;
	}

	public void setCount(Integer count) {
		this.count = count;
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

	@Column(name = "CAMPAIGNS", length = 5)
	public Integer getCampaigns() {
		return campaigns;
	}

	public void setCampaigns(Integer campaigns) {
		this.campaigns = campaigns;
	}

	@Column(name = "SELLNUM", nullable = false, precision = 5, scale = 0)
	public Integer getSellnum() {
		return sellnum;
	}

	public void setSellnum(Integer sellnum) {
		this.sellnum = sellnum;
	}

	@Column(name = "STOPNUM", nullable = false, precision = 5, scale = 0)
	public Integer getStopnum() {
		return stopnum;
	}

	public void setStopnum(Integer stopnum) {
		this.stopnum = stopnum;
	}

	@Column(name = "NIGHTNUM", nullable = false, precision = 5, scale = 0)
	public Integer getNightnum() {
		return nightnum;
	}

	public void setNightnum(Integer nightnum) {
		this.nightnum = nightnum;
	}

	@Column(name = "VALIDNUM", nullable = false, precision = 5, scale = 0)
	public Integer getValidnum() {
		return validnum;
	}

	public void setValidnum(Integer validnum) {
		this.validnum = validnum;
	}

	@Column(name = "INVALIDNUM", nullable = false, precision = 5, scale = 0)
	public Integer getInvalidnum() {
		return invalidnum;
	}

	public void setInvalidnum(Integer invalidnum) {
		this.invalidnum = invalidnum;
	}

	@Transient
	public String getRoomPrice() {
		return roomPrice;
	}

	public void setRoomPrice(String roomPrice) {
		this.roomPrice = roomPrice;
	}

	@Transient
	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

}