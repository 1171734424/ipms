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
@Table(name = "TL_C_AUDITLOG", schema = "UCR_PMS")
public class AuditLog implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = -4552860350797263867L;
	private String logId;
	private String operType;
	private String operId;
	private String recordUser;
	private Date recordTime;
	private String status;
	private String remark;
	private String branchId;

	// Constructors

	/** default constructor */
	public AuditLog() {
	}

	/** minimal constructor */
	public AuditLog(String logId, String operType, String operId, String recordUser, String status) {
		this.logId = logId;
		this.operType = operType;
		this.operId = operId;
		this.recordUser = recordUser;
		this.status = status;
	}

	/** full constructor */
	public AuditLog(String logId, String operType, String operId, String recordUser, Date recordTime, String status,
			String remark,String branchId) {
		this.logId = logId;
		this.operType = operType;
		this.operId = operId;
		this.recordUser = recordUser;
		this.recordTime = recordTime;
		this.status = status;
		this.remark = remark;
		this.branchId = branchId;
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

	@Column(name = "OPER_TYPE", nullable = false, length = 8)
	public String getOperType() {
		return this.operType;
	}

	public void setOperType(String operType) {
		this.operType = operType;
	}

	@Column(name = "OPER_ID", nullable = false, length = 20)
	public String getOperId() {
		return this.operId;
	}

	public void setOperId(String operId) {
		this.operId = operId;
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

	@Column(name = "STATUS", nullable = false, length = 1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "REMARK", length = 100)
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