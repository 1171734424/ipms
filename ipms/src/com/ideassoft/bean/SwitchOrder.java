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
@Table(name = "TL_P_SWITCHORDER", schema = "UCR_PMS")
public class SwitchOrder implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = -2861202104302507574L;
	private String logId;
	private String checkId;
	private String orderId;
	private String branchId;
	private String status;
	private Date recordTime;
	private String recordUser;

	// Constructors

	/** default constructor */
	public SwitchOrder() {
	}

	/** minimal constructor */
	public SwitchOrder(String logId, String checkId, String orderId, String branchId, String status, String recordUser) {
		this.logId = logId;
		this.checkId = checkId;
		this.orderId = orderId;
		this.branchId = branchId;
		this.status = status;
		this.recordUser = recordUser;
	}

	/** full constructor */
	public SwitchOrder(String logId, String checkId, String orderId, String branchId, String status, Date recordTime,
			String recordUser) {
		this.logId = logId;
		this.checkId = checkId;
		this.orderId = orderId;
		this.branchId = branchId;
		this.status = status;
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

	@Column(name = "CHECK_ID", nullable = false, length = 14)
	public String getCheckId() {
		return this.checkId;
	}

	public void setCheckId(String checkId) {
		this.checkId = checkId;
	}

	@Column(name = "ORDER_ID", nullable = false, length = 17)
	public String getOrderId() {
		return this.orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@Column(name = "BRANCH_ID", nullable = false, length = 4)
	public String getBranchId() {
		return this.branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	@Column(name = "STATUS", nullable = false, length = 1)
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

}