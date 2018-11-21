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
@Table(name = "TL_P_SWITCHROOM", schema = "UCR_PMS")
public class SwitchRoom implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = -6062096736956984842L;
	private String logId;
	private String branchId;
	private String checkId;
	private String lastrpId;
	private String lastRoomid;
	private String lastRoomtype;
	private Double lastrRoomprice;
	private String currrpId;
	private String currRoomid;
	private String currRoomtype;
	private Double currRoomprice;
	private Date recordTime;
	private String recordUser;

	// Constructors

	/** default constructor */
	public SwitchRoom() {
	}

	/** full constructor */
	public SwitchRoom(String logId, String branchId, String checkId, String lastrpId, String lastRoomid,
			String lastRoomtype, Double lastrRoomprice, String currrpId, String currRoomid, String currRoomtype,
			Double currRoomprice, Date recordTime, String recordUser) {
		super();
		this.logId = logId;
		this.branchId = branchId;
		this.checkId = checkId;
		this.lastrpId = lastrpId;
		this.lastRoomid = lastRoomid;
		this.lastRoomtype = lastRoomtype;
		this.lastrRoomprice = lastrRoomprice;
		this.currrpId = currrpId;
		this.currRoomid = currRoomid;
		this.currRoomtype = currRoomtype;
		this.currRoomprice = currRoomprice;
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

	@Column(name = "LAST_RPID", nullable = true, length = 3)
	public String getLastrpId() {
		return lastrpId;
	}

	public void setLastrpId(String lastrpId) {
		this.lastrpId = lastrpId;
	}

	@Column(name = "LAST_ROOMPRICE", nullable = false, precision = 9)
	public Double getLastrRoomprice() {
		return lastrRoomprice;
	}

	public void setLastrRoomprice(Double lastrRoomprice) {
		this.lastrRoomprice = lastrRoomprice;
	}

	@Column(name = "CURR_RPID", nullable = true, length = 3)
	public String getCurrrpId() {
		return currrpId;
	}

	public void setCurrrpId(String currrpId) {
		this.currrpId = currrpId;
	}

	@Column(name = "CURR_ROOMPRICE", nullable = false, precision = 9)
	public Double getCurrRoomprice() {
		return currRoomprice;
	}

	public void setCurrRoomprice(Double currRoomprice) {
		this.currRoomprice = currRoomprice;
	}

	@Column(name = "BRANCH_ID", nullable = false, length = 6)
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

	@Column(name = "LAST_ROOMID", nullable = false, length = 6)
	public String getLastRoomid() {
		return this.lastRoomid;
	}

	public void setLastRoomid(String lastRoomid) {
		this.lastRoomid = lastRoomid;
	}

	@Column(name = "LAST_ROOMTYPE", length = 2)
	public String getLastRoomtype() {
		return this.lastRoomtype;
	}

	public void setLastRoomtype(String lastRoomtype) {
		this.lastRoomtype = lastRoomtype;
	}

	@Column(name = "CURR_ROOMID", nullable = false, length = 6)
	public String getCurrRoomid() {
		return this.currRoomid;
	}

	public void setCurrRoomid(String currRoomid) {
		this.currRoomid = currRoomid;
	}

	@Column(name = "CURR_ROOMTYPE", length = 2)
	public String getCurrRoomtype() {
		return this.currRoomtype;
	}

	public void setCurrRoomtype(String currRoomtype) {
		this.currRoomtype = currRoomtype;
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