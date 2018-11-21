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
@Table(name = "TB_P_BILL", schema = "UCR_PMS")
public class Bill implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 8959806070364611698L;
	private String billId;
	private String branchId;
	private String checkId;
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
	private String type;
	private String shiftVoucher;
	private String refundStatus;

	// Constructors

	/** default constructor */
	public Bill() {
	}

	/** minimal constructor */
	public Bill(String billId, String branchId, String checkId, String projectId, String projectName, String describe,
			String payment, String status, String recordUser) {
		this.billId = billId;
		this.branchId = branchId;
		this.checkId = checkId;
		this.projectId = projectId;
		this.projectName = projectName;
		this.describe = describe;
		this.payment = payment;
		this.status = status;
		this.recordUser = recordUser;
	}

	/** full constructor */
	public Bill(String billId, String branchId, String checkId, String projectId, String projectName, String describe,
			Double cost, Double pay, String payment, String offset, String status, Date recordTime, String recordUser,
			String remark, String type,String shiftVoucher) {
		this.billId = billId;
		this.branchId = branchId;
		this.checkId = checkId;
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
		this.type = type;
		this.shiftVoucher = shiftVoucher;
	}

	// Property accessors
	@Id
	@Column(name = "BILL_ID", unique = true, nullable = false, length = 19)
	public String getBillId() {
		return this.billId;
	}

	public void setBillId(String billId) {
		this.billId = billId;
	}

	@Column(name = "BRANCH_ID", nullable = false, length = 6)
	public String getBranchId() {
		return this.branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	@Column(name = "CHECK_ID", nullable = false, length = 14)
	public String getCheckId() {
		return this.checkId;
	}

	public void setCheckId(String checkId) {
		this.checkId = checkId;
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

	@Column(name = "TYPE", length = 1)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Column(name = "SHIFT_VOUCHER", length = 50)
	public String getShiftVoucher() {
		return shiftVoucher;
	}

	public void setShiftVoucher(String shiftVoucher) {
		this.shiftVoucher = shiftVoucher;
	}
	
	@Column(name = "REFUND_STATUS", length = 1)
	public String getRefundStatus() {
		return this.refundStatus;
	}
	
	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}
}