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
@Table(name = "TB_P_WORKBILLDETAIL", schema = "UCR_PMS")
public class WorkBillDetail implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 8216091528107066860L;
	private String detailId;
	private String branchId;
	private String projectId;
	private String projectName;
	private String describe;
	private Double cost;
	private Double pay;
	private String payment;
	private String offset;
	private String status;
	private Date recordTime;
	private String recordUser;
	private String remark;
	private String voucher;
	private String workbillId;
	private String shiftVoucher;
	private String cashBox;
	

	// Constructors

	/** default constructor */
	public WorkBillDetail() {
	}

	/** minimal constructor */
	public WorkBillDetail(String detailId, String branchId, String projectId, String projectName, String describe,
			String payment, String status, String recordUser) {
		this.detailId = detailId;
		this.branchId = branchId;
		this.projectId = projectId;
		this.projectName = projectName;
		this.describe = describe;
		this.payment = payment;
		this.status = status;
		this.recordUser = recordUser;
	}

	/** full constructor */
	public WorkBillDetail(String detailId, String branchId, String projectId, String projectName, String describe,
			Double cost, Double pay, String payment, String offset, String status, Date recordTime, String recordUser,
			String remark,String shiftVoucher) {
		this.detailId = detailId;
		this.branchId = branchId;
		this.projectId = projectId;
		this.projectName = projectName;
		this.describe = describe;
		this.cost = cost;
		this.pay = pay;
		this.payment = payment;
		this.offset = offset;
		this.status = status;
		this.recordTime = recordTime;
		this.recordUser = recordUser;
		this.remark = remark;
		this.shiftVoucher = shiftVoucher;
	}

	// Property accessors
	@Id
	@Column(name = "DETAIL_ID", unique = true, nullable = false, length = 19)
	public String getDetailId() {
		return this.detailId;
	}

	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}

	@Column(name = "BRANCH_ID", nullable = false, length = 4)
	public String getBranchId() {
		return this.branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	@Column(name = "PROJECT_ID", nullable = false, length = 4)
	public String getProjectId() {
		return this.projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	@Column(name = "PROJECT_NAME", nullable = false, length = 20)
	public String getProjectName() {
		return this.projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	@Column(name = "DESCRIBE", nullable = false, length = 40)
	public String getDescribe() {
		return this.describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	@Column(name = "COST", precision = 9)
	public Double getCost() {
		return this.cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	@Column(name = "PAY", precision = 9)
	public Double getPay() {
		return this.pay;
	}

	public void setPay(Double pay) {
		this.pay = pay;
	}

	@Column(name = "PAYMENT", nullable = false, length = 1)
	public String getPayment() {
		return this.payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	@Column(name = "OFFSET", length = 200)
	public String getOffset() {
		return this.offset;
	}

	public void setOffset(String offset) {
		this.offset = offset;
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

	@Column(name = "REMARK", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "VOUCHER", length = 6)
	public String getVoucher() {
		return voucher;
	}

	public void setVoucher(String voucher) {
		this.voucher = voucher;
	}

	@Column(name = "WORKBILL_ID", length = 14)
	public String getWorkbillId() {
		return workbillId;
	}

	public void setWorkbillId(String workbillId) {
		this.workbillId = workbillId;
	}

	@Column(name = "SHIFT_VOUCHER", length = 50)
	public String getShiftVoucher() {
		return shiftVoucher;
	}

	public void setShiftVoucher(String shiftVoucher) {
		this.shiftVoucher = shiftVoucher;
	}

	@Column(name = "CASH_BOX", length = 4)
	public String getCashBox() {
		return cashBox;
	}

	public void setCashBox(String cashBox) {
		this.cashBox = cashBox;
	}

}