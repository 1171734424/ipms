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
@Table(name = "TB_C_PURCHASE", schema = "UCR_PMS")
public class Purchase implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = -8751062293158151010L;
	private String purchaseId;
	private String purchaseCategory;
	private String branchId;
	private Double purchaseAmount;
	private Integer auditor;
	private String recordUser;
	private Date recordTime;
	private String status;
	private String remark;

	// Constructors

	/** default constructor */
	public Purchase() {
	}

	/** minimal constructor */
	public Purchase(String purchaseId, String purchaseCategory, String branchId, Double purchaseAmount,
			Integer auditor, String recordUser, String status) {
		this.purchaseId = purchaseId;
		this.purchaseCategory = purchaseCategory;
		this.branchId = branchId;
		this.purchaseAmount = purchaseAmount;
		this.auditor = auditor;
		this.recordUser = recordUser;
		this.status = status;
	}

	/** full constructor */
	public Purchase(String purchaseId, String purchaseCategory, Double purchaseAmount, Integer auditor,
			String recordUser, Date recordTime, String status, String remark) {
		this.purchaseId = purchaseId;
		this.purchaseCategory = purchaseCategory;
		this.purchaseAmount = purchaseAmount;
		this.auditor = auditor;
		this.recordUser = recordUser;
		this.recordTime = recordTime;
		this.status = status;
		this.remark = remark;
	}

	// Property accessors
	@Id
	@Column(name = "PURCHASE_ID", unique = true, nullable = false, length = 12)
	public String getPurchaseId() {
		return this.purchaseId;
	}

	public void setPurchaseId(String purchaseId) {
		this.purchaseId = purchaseId;
	}

	@Column(name = "PURCHASE_CATEGORY", nullable = false, length = 2)
	public String getPurchaseCategory() {
		return this.purchaseCategory;
	}

	public void setPurchaseCategory(String purchaseCategory) {
		this.purchaseCategory = purchaseCategory;
	}

	@Column(name = "BRANCH_ID", nullable = false, length = 6)
	public String getBranchId() {
		return this.branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	@Column(name = "PURCHASE_AMOUNT", nullable = false, precision = 10)
	public Double getPurchaseAmount() {
		return this.purchaseAmount;
	}

	public void setPurchaseAmount(Double purchaseAmount) {
		this.purchaseAmount = purchaseAmount;
	}

	@Column(name = "AUDITOR", nullable = false, precision = 8, scale = 0)
	public Integer getAuditor() {
		return this.auditor;
	}

	public void setAuditor(Integer auditor) {
		this.auditor = auditor;
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

	@Column(name = "REMARK", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}