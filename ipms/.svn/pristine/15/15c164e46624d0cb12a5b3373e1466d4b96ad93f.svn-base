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
@Table(name = "TL_P_RECORDING", schema = "UCR_PMS")
public class Recording implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = -1818069923079572100L;
	private String recordId;
	private String branchId;
	private String checkId;
	private String recordType;
	private String projectId;
	private Double fee;
	private Date recordTime;
	private String recordUser;
	private String remark;

	// Constructors

	/** default constructor */
	public Recording() {
	}

	/** minimal constructor */
	public Recording(String recordId, String branchId, String checkId, String recordType, String projectId, Double fee,
			String recordUser) {
		this.recordId = recordId;
		this.branchId = branchId;
		this.checkId = checkId;
		this.recordType = recordType;
		this.projectId = projectId;
		this.fee = fee;
		this.recordUser = recordUser;
	}

	/** full constructor */
	public Recording(String recordId, String branchId, String checkId, String recordType, String projectId, Double fee,
			Date recordTime, String recordUser, String remark) {
		this.recordId = recordId;
		this.branchId = branchId;
		this.checkId = checkId;
		this.recordType = recordType;
		this.projectId = projectId;
		this.fee = fee;
		this.recordTime = recordTime;
		this.recordUser = recordUser;
		this.remark = remark;
	}

	// Property accessors
	@Id
	@Column(name = "RECORD_ID", unique = true, nullable = false, length = 19)
	public String getRecordId() {
		return this.recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	@Column(name = "BRANCH_ID", nullable = false, length = 4)
	public String getBranchId() {
		return this.branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	@Column(name = "CHECK_ID", length = 17)
	public String getCheckId() {
		return this.checkId;
	}

	public void setCheckId(String checkId) {
		this.checkId = checkId;
	}

	@Column(name = "RECORD_TYPE", nullable = false, length = 4)
	public String getRecordType() {
		return this.recordType;
	}

	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}

	@Column(name = "PROJECT_ID", nullable = false, length = 4)
	public String getProjectId() {
		return this.projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	@Column(name = "FEE", nullable = false, precision = 9)
	public Double getFee() {
		return this.fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
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

}