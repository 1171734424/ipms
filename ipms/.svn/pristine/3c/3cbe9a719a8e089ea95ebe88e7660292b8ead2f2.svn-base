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
@Table(name = "TB_P_CLEANAPPLY", schema = "UCR_PMS")
public class CleanApply implements java.io.Serializable {

	private static final long serialVersionUID = -2276394574380866475L;
	private String cleanApplyId;
	private String branchId;
	private String contractId;
	private Date cleanTime;
	private String cleanStyle;
	private String roomId;
	private String reservedPerson;
	private String mobile;
	private String status;
	private Date applicationTime;
	private String auditDescription;
	private Date recordTime;
	private String recordUser;
	private String remark;
	private String timeArea;
	private Double cleanPrice;
	private String payStatus;
	private Double payCash;
	private Integer payScore;
	private String useCoupon;
	private String payment;
	public CleanApply() {

	}
	public CleanApply(String cleanApplyId, String branchId, String contractId,
			Date cleanTime, String cleanStyle, String roomId,
			String reservedPerson, String mobile, String status,
			Date applicationTime, String auditDescription, Date recordTime,
			String recordUser, String remark, String timeArea,
			Double cleanPrice, String payStatus, Double payCash,
			Integer payScore, String useCoupon,String payment) {
		super();
		this.cleanApplyId = cleanApplyId;
		this.branchId = branchId;
		this.contractId = contractId;
		this.cleanTime = cleanTime;
		this.cleanStyle = cleanStyle;
		this.roomId = roomId;
		this.reservedPerson = reservedPerson;
		this.mobile = mobile;
		this.status = status;
		this.applicationTime = applicationTime;
		this.auditDescription = auditDescription;
		this.recordTime = recordTime;
		this.recordUser = recordUser;
		this.remark = remark;
		this.timeArea = timeArea;
		this.cleanPrice = cleanPrice;
		this.payStatus = payStatus;
		this.payCash = payCash;
		this.payScore = payScore;
		this.useCoupon = useCoupon;
		this.payment = payment;
	}




	@Id
	@Column(name = "CLEANAPPLY_ID", unique = true, nullable = false, length = 18)
	public String getCleanApplyId() {
		return cleanApplyId;
	}

	public void setCleanApplyId(String cleanApplyId) {
		this.cleanApplyId = cleanApplyId;
	}

	@Column(name = "BRANCH_ID", nullable = false, length = 6)
	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	@Column(name = "CONTRACT_ID", nullable = false, length = 7)
	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CLEAN_TIME")
	public Date getCleanTime() {
		return cleanTime;
	}

	public void setCleanTime(Date cleanTime) {
		this.cleanTime = cleanTime;
	}

	@Column(name = "CLEAN_STYLE", nullable = false, length = 1)
	public String getCleanStyle() {
		return cleanStyle;
	}

	public void setCleanStyle(String cleanStyle) {
		this.cleanStyle = cleanStyle;
	}

	@Column(name = "ROOM_ID", nullable = false, length = 6)
	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	@Column(name = "RESERVED_PERSON", nullable = false, length = 8)
	public String getReservedPerson() {
		return reservedPerson;
	}

	public void setReservedPerson(String reservedPerson) {
		this.reservedPerson = reservedPerson;
	}

	@Column(name = "MOBILE", nullable = false, length = 11)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "STATUS", nullable = false, length = 1)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "APPLICATION_TIME")
	public Date getApplicationTime() {
		return applicationTime;
	}

	public void setApplicationTime(Date applicationTime) {
		this.applicationTime = applicationTime;
	}

	@Column(name = "AUDIT_DESCRIPTION", length = 200)
	public String getAuditDescription() {
		return auditDescription;
	}

	public void setAuditDescription(String auditDescription) {
		this.auditDescription = auditDescription;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RECORD_TIME", insertable = false, updatable = true)
	@ColumnDefaultValue(value = "sysdate", insertable = false)
	public Date getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	@Column(name = "RECORD_USER", length = 8)
	public String getRecordUser() {
		return recordUser;
	}

	public void setRecordUser(String recordUser) {
		this.recordUser = recordUser;
	}

	@Column(name = "REMARK", length = 200)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "TIMEAREA", nullable = false, length = 1)
	public String getTimeArea() {
		return timeArea;
	}

	public void setTimeArea(String timeArea) {
		this.timeArea = timeArea;
	}

	@Column(name = "CLEAN_PRICE",nullable = false, precision = 9)
	public Double getCleanPrice() {
		return cleanPrice;
	}
	public void setCleanPrice(Double cleanPrice) {
		this.cleanPrice = cleanPrice;
	}
	@Column(name = "PAY_STATUS", nullable = false, length = 1)
	public String getPayStatus() {
		return payStatus;
	}
	
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	@Column(name = "PAY_CASH", precision = 9)
	public Double getPayCash() {
		return payCash;
	}
	public void setPayCash(Double payCash) {
		this.payCash = payCash;
	}
	@Column(name = "PAY_SCORE", precision = 9, scale = 0)
	public Integer getPayScore() {
		return payScore;
	}
	public void setPayScore(Integer payScore) {
		this.payScore = payScore;
	}
	@Column(name = "USE_COUPON", length = 10)
	public String getUseCoupon() {
		return useCoupon;
	}
	public void setUseCoupon(String useCoupon) {
		this.useCoupon = useCoupon;
	}
	
	@Column(name = "PAYMENT",  length = 1)
	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}
}
