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
@Table(name = "TB_P_WORKBILL", schema = "UCR_PMS")
public class WorkBill implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 5529759716953441259L;
	private String workbillId;
	private String branchId;
	private String name;
	private String describe;
	private String recordUser;
	private Date recordTime;
	private String finalUser;
	private Date finalTime;
	private String status;
	private String remark;

	// Constructors

	/** default constructor */
	public WorkBill() {
	}

	/** minimal constructor */
	public WorkBill(String workbillId, String branchId, String name, String recordUser, String status) {
		this.workbillId = workbillId;
		this.branchId = branchId;
		this.name = name;
		this.recordUser = recordUser;
		this.status = status;
	}

	/** full constructor */
	public WorkBill(String workbillId, String branchId, String name, String describe, String recordUser,
			Date recordTime, String finalUser, Date finalTime, String status, String remark) {
		this.workbillId = workbillId;
		this.branchId = branchId;
		this.name = name;
		this.describe = describe;
		this.recordUser = recordUser;
		this.recordTime = recordTime;
		this.finalUser = finalUser;
		this.finalTime = finalTime;
		this.status = status;
		this.remark = remark;
	}

	// Property accessors
	@Id
	@Column(name = "WORKBILL_ID", unique = true, nullable = false, length = 14)
	public String getWorkbillId() {
		return this.workbillId;
	}

	public void setWorkbillId(String workbillId) {
		this.workbillId = workbillId;
	}

	@Column(name = "BRANCH_ID", nullable = false, length = 6)
	public String getBranchId() {
		return this.branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	@Column(name = "NAME", nullable = false, length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "DESCRIBE", length = 200)
	public String getDescribe() {
		return this.describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
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

	@Column(name = "FINAL_USER", length = 8)
	public String getFinalUser() {
		return this.finalUser;
	}

	public void setFinalUser(String finalUser) {
		this.finalUser = finalUser;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FINAL_TIME", length = 7)
	public Date getFinalTime() {
		return this.finalTime;
	}

	public void setFinalTime(Date finalTime) {
		this.finalTime = finalTime;
	}

	@Column(name = "STATUS", nullable = false, length = 1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "REMARK", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}