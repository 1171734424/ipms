package com.ideassoft.bean;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ideassoft.core.annotation.interfaces.ColumnDefaultValue;

@Entity
@Table(name = "TL_C_LOGINLOG", schema = "UCR_PMS")
public class LoginLog implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 8320728069831146066L;
	private LoginLogId loginLogId;
	private String source;
	// private String userId;
	private String loginIp;
	private String status;
	private String browser;
	private String resolution;
	private Date recordTime;
	private String shift;
	private String cashbox;
	private String state;
	private String remark;

	// Constructors

	/** default constructor */
	public LoginLog() {
	}

	/** minimal constructor */
	public LoginLog(LoginLogId loginLogId, String source, String loginIp, String status) {
		this.loginLogId = loginLogId;
		this.source = source;
		// this.userId = userId;
		this.loginIp = loginIp;
		this.status = status;
	}

	/** full constructor */
	public LoginLog(LoginLogId loginLogId, String source, String loginIp, String status, String browser, String resolution,
			Date recordTime, String shift, String cashbox, String state, String remark) {
		this.loginLogId = loginLogId;
		this.source = source;
		// this.userId = userId;
		this.loginIp = loginIp;
		this.status = status;
		this.browser = browser;
		this.resolution = resolution;
		this.recordTime = recordTime;
		this.shift = shift;
		this.cashbox = cashbox;
		this.state = state;
		this.remark = remark;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "logId", column = @Column(name = "LOG_ID", nullable = false, length = 13)),
			@AttributeOverride(name = "loginId", column = @Column(name = "LOGIN_ID", nullable = false, length = 8)) })
	public LoginLogId getLoginLogId() {
		return loginLogId;
	}

	public void setLoginLogId(LoginLogId loginLogId) {
		this.loginLogId = loginLogId;
	}

	@Column(name = "SOURCE", nullable = false, length = 1)
	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	// @Column(name = "USER_ID", nullable = false, length = 8)
	// public String getUserId() {
	// return this.userId;
	// }
	//
	// public void setUserId(String userId) {
	// this.userId = userId;
	// }

	@Column(name = "LOGIN_IP", length = 15)
	public String getLoginIp() {
		return this.loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	@Column(name = "STATUS", nullable = false, length = 1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "BROWSER", length = 10)
	public String getBrowser() {
		return this.browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	@Column(name = "RESOLUTION", length = 9)
	public String getResolution() {
		return this.resolution;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
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

	@Column(name = "SHIFT", length = 17)
	public String getShift() {
		return shift;
	}

	public void setShift(String shift) {
		this.shift = shift;
	}

	@Column(name = "CASHBOX", length = 1)
	public String getCashbox() {
		return cashbox;
	}

	public void setCashbox(String cashbox) {
		this.cashbox = cashbox;
	}

	@Column(name = "STATE", length = 1)
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	@Column(name = "REMARK", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}