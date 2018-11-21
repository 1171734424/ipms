package com.ideassoft.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "TB_P_CHECKOUT", schema = "UCR_PMS")
public class CheckOut implements java.io.Serializable {

	private static final long serialVersionUID = -8695027657239153742L;

	private String checkOutId;
	private String contractId;
	private String memberId;
	private String branchId;
	private String source;
	private String roomId;
	private String status;
	private Date applicationTime;
	private String auditDescription;
	private Date recordTime;
	private String recordUser;
	private String remark;
	private String dispose;
	private Date checkoutTime;
	private String post;
	private String auditRemark;

	public CheckOut() {

	}

	public CheckOut(String checkOutId, String contractId, String memberId, String branchId, String source,
			String roomId, String status, Date applicationTime, String auditDescription, Date recordTime,
			String recordUser, String remark, String post,String auditRemark) {
		super();
		this.checkOutId = checkOutId;
		this.contractId = contractId;
		this.memberId = memberId;
		this.branchId = branchId;
		this.source = source;
		this.roomId = roomId;
		this.status = status;
		this.applicationTime = applicationTime;
		this.auditDescription = auditDescription;
		this.recordTime = recordTime;
		this.recordUser = recordUser;
		this.remark = remark;
		this.post = post;
		this.auditRemark = auditRemark;
	}

	@Id
	@Column(name = "CHECKOUT_ID", unique = true, nullable = false, length = 18)
	public String getCheckOutId() {
		return checkOutId;
	}

	public void setCheckOutId(String checkOutId) {
		this.checkOutId = checkOutId;
	}

	@Column(name = "CONTRACT_ID", nullable = false, length = 17)
	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	@Column(name = "MEMBER_ID", nullable = false, length = 8)
	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	@Column(name = "BRANCH_ID", nullable = false, length = 6)
	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	@Column(name = "SOURCE", nullable = false, length = 2)
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Column(name = "ROOM_ID", nullable = false, length = 6)
	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
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
	@Column(name = "RECORD_TIME")
	public Date getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	@Column(name = "RECORD_USER", length = 200)
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

	@Column(name = "DISPOSE", nullable = false, length = 1)
	public String getDispose() {
		return dispose;
	}

	public void setDispose(String dispose) {
		this.dispose = dispose;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CHECKOUT_TIME")
	public Date getCheckoutTime() {
		return checkoutTime;
	}

	public void setCheckoutTime(Date checkoutTime) {
		this.checkoutTime = checkoutTime;
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
