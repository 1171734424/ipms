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
@Table(name = "TB_P_PRICEAPPLY", schema = "UCR_PMS")
public class PriceApply implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = -5200111829953524947L;
	private String applyId;
	private String branchId;
	private String applyType;
	private Date validStart;
	private Date validEnd;
	private String validDay;
	private String filterDay;
	private String status;
	private Date applyTime;
	private String recordUser;
	private Date recordTime;
	private String remark;
	private String applyKind;
	private String post;
	private String auditRemark;

	// Constructors

	/** default constructor */
	public PriceApply() {
	}

	/** minimal constructor */
	public PriceApply(String applyId, String branchId, String applyType, Date validStart, Date validEnd, String status,
			String recordUser, String applyKind) {
		this.applyId = applyId;
		this.branchId = branchId;
		this.applyType = applyType;
		this.validStart = validStart;
		this.validEnd = validEnd;
		this.status = status;
		this.recordUser = recordUser;
		this.applyKind = applyKind;
	}

	/** full constructor */
	public PriceApply(String applyId, String branchId, String applyType, Date validStart, Date validEnd,
			String validDay, String filterDay, String status, Date applyTime, String recordUser, Date recordTime,
			String remark, String applyKind,String post,String auditRemark) {
		this.applyId = applyId;
		this.branchId = branchId;
		this.applyType = applyType;
		this.validStart = validStart;
		this.validEnd = validEnd;
		this.validDay = validDay;
		this.filterDay = filterDay;
		this.status = status;
		this.applyTime = applyTime;
		this.recordUser = recordUser;
		this.recordTime = recordTime;
		this.remark = remark;
		this.applyKind = applyKind;
		this.post = post;
		this.auditRemark = auditRemark;
	}

	// Property accessors
	@Id
	@Column(name = "APPLY_ID", unique = true, nullable = false, length = 16)
	public String getApplyId() {
		return this.applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	@Column(name = "BRANCH_ID", nullable = false, length = 6)
	public String getBranchId() {
		return this.branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	@Column(name = "APPLY_TYPE", nullable = false, length = 1)
	public String getApplyType() {
		return this.applyType;
	}

	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "VALID_START",  length = 7)
	public Date getValidStart() {
		return this.validStart;
	}

	public void setValidStart(Date validStart) {
		this.validStart = validStart;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "VALID_END",  length = 7)
	public Date getValidEnd() {
		return this.validEnd;
	}

	public void setValidEnd(Date validEnd) {
		this.validEnd = validEnd;
	}

	@Column(name = "VALID_DAY", length = 7)
	public String getValidDay() {
		return this.validDay;
	}

	public void setValidDay(String validDay) {
		this.validDay = validDay;
	}

	@Column(name = "FILTER_DAY", length = 90)
	public String getFilterDay() {
		return this.filterDay;
	}

	public void setFilterDay(String filterDay) {
		this.filterDay = filterDay;
	}

	@Column(name = "STATUS", nullable = false, length = 1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "APPLY_TIME", length = 7)
	public Date getApplyTime() {
		return this.applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
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

	@Column(name = "REMARK", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "APPLY_KIND", nullable = false, length = 1)
	public String getApplyKind() {
		return this.applyKind;
	}

	public void setApplyKind(String applyKind) {
		this.applyKind = applyKind;
	}
	
	@Column(name = "POST", length = 4)
	public String getPost() {
		return this.post;
	}

	public void setPost(String post) {
		this.post = post;
	}
	
	@Column(name = "AUDIT_REMARK", length = 200)
	public String getAuditRemark() {
		return auditRemark;
	}

	public void setAuditRemark(String auditRemark) {
		this.auditRemark = auditRemark;
	}

}