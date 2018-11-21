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
@Table(name = "TB_E_DOOROPENRIGHT", schema = "UCR_PMS")
public class DoorOpenRight implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3047078973139806822L;

	private String dataId;
	private String gateWayCode;
	private String lockCode;
	private String userName;
	private String cardNumb;
	private String dnCode;
	private Date startTime;
	private Date endTime;
	private String recordUser;
	private Date recordTime;
	private String status;
	private String remark;
	

	public DoorOpenRight() {
	}
	
	public DoorOpenRight(String dataId, String gateWayCode, String lockCode,
			String userName, String cardNumb, String dnCode, Date startTime,
			Date endTime, String recordUser, Date recordTime, String status,
			String remark) {
		this.dataId = dataId;
		this.gateWayCode = gateWayCode;
		this.lockCode = lockCode;
		this.userName = userName;
		this.cardNumb = cardNumb;
		this.dnCode = dnCode;
		this.startTime = startTime;
		this.endTime = endTime;
		this.recordUser = recordUser;
		this.recordTime = recordTime;
		this.status = status;
		this.remark = remark;
	}
	
	@Id
	@Column(name = "DATA_ID", unique = true, nullable = false, length = 40)
	public String getDataId() {
		return dataId;
	}
	
	public void setDataId(String dataId) {
		this.dataId = dataId;
	}
	
	@Column(name = "GATEWAY_CODE", length = 40)
	public String getGateWayCode() {
		return gateWayCode;
	}
	
	public void setGateWayCode(String gateWayCode) {
		this.gateWayCode = gateWayCode;
	}
	
	@Column(name = "LOCK_CODE", length = 40)
	public String getLockCode() {
		return lockCode;
	}
	
	public void setLockCode(String lockCode) {
		this.lockCode = lockCode;
	}
	
	@Column(name = "USER_NAME", length = 30)
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Column(name = "CARD_NUMB", length = 18)
	public String getCardNumb() {
		return cardNumb;
	}
	
	public void setCardNumb(String cardNumb) {
		this.cardNumb = cardNumb;
	}
	
	@Column(name = "DN_CODE", length = 40)
	public String getDnCode() {
		return dnCode;
	}
	
	public void setDnCode(String dnCode) {
		this.dnCode = dnCode;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "START_TIME", insertable = true, updatable = true)
	public Date getStartTime() {
		return startTime;
	}
	
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "END_TIME", insertable = true, updatable = true)
	public Date getEndTime() {
		return endTime;
	}
	
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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
	
	@Column(name = "STATUS", length = 1)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name = "REMARK", length = 200)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
