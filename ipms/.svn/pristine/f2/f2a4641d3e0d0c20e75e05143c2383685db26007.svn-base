package com.ideassoft.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "TB_C_HOUSEACCOUNT", schema = "UCR_PMS")
public class HouseAccount implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8900962599831391655L;
	private String houseAccountId;
	private String houseAccountName;
	private String staffId;
	private String status;
	private String workStatus;
	private String recordUser;
	private Date recordTime;
	private String remark;
	public HouseAccount() {
		
	}
	public HouseAccount(String houseAccountId, String houseAccountName,
			String staffId, String status, String workStatus,
			String recordUser, Date recordTime) {
		this.houseAccountId = houseAccountId;
		this.houseAccountName = houseAccountName;
		this.staffId = staffId;
		this.status = status;
		this.workStatus = workStatus;
		this.recordUser = recordUser;
		this.recordTime = recordTime;
	}
	public HouseAccount(String houseAccountId, String houseAccountName,
			String staffId, String status, String workStatus,
			String recordUser, Date recordTime, String remark) {
		super();
		this.houseAccountId = houseAccountId;
		this.houseAccountName = houseAccountName;
		this.staffId = staffId;
		this.status = status;
		this.workStatus = workStatus;
		this.recordUser = recordUser;
		this.recordTime = recordTime;
		this.remark = remark;
	}
	
	@Id
	@Column(name = "HOUSEACCOUNT_ID", unique = true, nullable = false, length = 8)
	public String getHouseAccountId() {
		return houseAccountId;
	}
	public void setHouseAccountId(String houseAccountId) {
		this.houseAccountId = houseAccountId;
	}
	
	@Column(name = "HOUSEACCOUNT_NAME", nullable = false, length = 100)
	public String getHouseAccountName() {
		return houseAccountName;
	}
	public void setHouseAccountName(String houseAccountName) {
		this.houseAccountName = houseAccountName;
	}
	
	@Column(name = "STAFF_ID", nullable = false, length = 8)
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	
	@Column(name = "STATUS", nullable = false, length = 1)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Column(name = "WORKSTATUS", nullable = false, length = 1)
	public String getWorkStatus() {
		return workStatus;
	}
	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}
	
	@Column(name = "RECORD_USER", nullable = false, length = 8)
	public String getRecordUser() {
		return recordUser;
	}
	public void setRecordUser(String recordUser) {
		this.recordUser = recordUser;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RECORD_TIME", nullable = false, insertable = true)
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
