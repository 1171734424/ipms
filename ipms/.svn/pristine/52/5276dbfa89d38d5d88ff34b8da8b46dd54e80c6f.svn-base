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
@Table(name = "TL_P_OPERATELOG", schema = "UCR_PMS")
public class OperateLog implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = -8522279116521812775L;
	private String logId;
	private String operType;
	private String operModule;
	private String content;
	private String recordUser;
	private Date recordTime;
	private String operIp;
	private String remark;
	private String branchId;

	// Constructors

	/** default constructor */
	public OperateLog() {
	}

	/** minimal constructor */
	public OperateLog(String logId, String operType, String operModule, String recordUser, String operIp) {
		this.logId = logId;
		this.operType = operType;
		this.operModule = operModule;
		this.recordUser = recordUser;
		this.operIp = operIp;
	}

	/** full constructor */
	public OperateLog(String logId, String operType, String operModule, String content, String recordUser,
			Date recordTime, String operIp, String remark) {
		this.logId = logId;
		this.operType = operType;
		this.operModule = operModule;
		this.content = content;
		this.recordUser = recordUser;
		this.recordTime = recordTime;
		this.operIp = operIp;
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

	@Column(name = "OPER_TYPE", nullable = false, length = 10)
	public String getOperType() {
		return this.operType;
	}

	public void setOperType(String operType) {
		this.operType = operType;
	}

	@Column(name = "OPER_MODULE", nullable = false, length = 3)
	public String getOperModule() {
		return this.operModule;
	}

	public void setOperModule(String operModule) {
		this.operModule = operModule;
	}

	@Column(name = "CONTENT", length = 200)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "RECORD_USER", nullable = false, length = 8)
	public String getRecordUser() {
		return this.recordUser;
	}

	public void setRecordUser(String recordUser) {
		this.recordUser = recordUser;
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

	@Column(name = "OPER_IP", nullable = false, length = 15)
	public String getOperIp() {
		return this.operIp;
	}

	public void setOperIp(String operIp) {
		this.operIp = operIp;
	}

	@Column(name = "REMARK", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "BRANCH_ID", nullable = false, length = 6)
	public String getBranchId() {
		return this.branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

}