package com.ideassoft.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ideassoft.core.annotation.interfaces.ColumnDefaultValue;

/**
 * Controllog entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TL_E_CONTROLLOG", schema = "UCR_PMS")
public class ControlLog implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8075925601506163974L;
	// Fields
	private String logId;
	private String serialNo;
	private String openCode;//1-身份证开锁，2-密码开锁
	private Date openDoorTime;//开门时间
	private String resultCode; //1-开锁成功，2-身份证读成功，但无有效授权信息失败
	private String status;
	private Date recordTime;
	private String recordUser;//身份证或者密码
	private String remark;
	private String lockNum;
	private String operCommand;
	private String orderId;

	// Constructors

	/** default constructor */
	public ControlLog() {
	}

	/** minimal constructor */
	public ControlLog(String logId, String serialNo, String status, String recordUser) {
		this.logId = logId;
		this.serialNo = serialNo;
		this.status = status;
		this.recordUser = recordUser;
	}

	/** full constructor */
	public ControlLog(String logId, String serialNo, String openCode,
			Date openDoorTime, String resultCode, String status,
			Date recordTime, String recordUser, String remark, String lockNum, String operCommand, String orderId) {
		this.logId = logId;
		this.serialNo = serialNo;
		this.openCode = openCode;
		this.openDoorTime = openDoorTime;
		this.resultCode = resultCode;
		this.status = status;
		this.recordTime = recordTime;
		this.recordUser = recordUser;
		this.remark = remark;
		this.lockNum = lockNum;
		this.operCommand = operCommand;
		this.orderId = orderId;
	}

	// Property accessors
	@Id
	@Column(name = "LOG_ID", unique = true, nullable = false, length = 18)
	public String getLogId() {
		return this.logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	@Column(name = "SERIAL_NO", nullable = false, length = 20)
	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
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
	public Date getRecordTime() {
		return this.recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	@Column(name = "RECORD_USER", nullable = false, length = 18)
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
	
	@Column(name = "OPEN_CODE", length = 2,nullable = false)
	public String getOpenCode() {
		return openCode;
	}

	public void setOpenCode(String openCode) {
		this.openCode = openCode;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "OPENDOOR_TIME", insertable = true, updatable = true)
	public Date getOpenDoorTime() {
		return openDoorTime;
	}

	public void setOpenDoorTime(Date openDoorTime) {
		this.openDoorTime = openDoorTime;
	}

	@Column(name = "RESULT_CODE", length = 2,nullable = false)
	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	@Column(name = "LOCK_NUM", length = 40,nullable = true)
	public String getLockNum() {
		return lockNum;
	}

	public void setLockNum(String lockNum) {
		this.lockNum = lockNum;
	}

	@Column(name = "OPER_COMMAND", length = 2,nullable = true)
	public String getOperCommand() {
		return operCommand;
	}

	public void setOperCommand(String operCommand) {
		this.operCommand = operCommand;
	}

	@Column(name = "ORDER_ID", length = 17,nullable = true)
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

}