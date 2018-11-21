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
@Table(name = "TB_P_REPAIRAPPLY", schema = "UCR_PMS")
public class RepairApply implements java.io.Serializable {

	private static final long serialVersionUID = 4957373722937444409L;

	private String repairApplyId;
	private String branchId;
	private String contractId;
	private String roomId;
	private Date applicationDate;
	private String reservedPerson;
	private String mobile;
	private String status;
	private String auditDescription;
	private Date recordTime;
	private String recordUser;
	private String remark;
	private Date repairTime;
	private String equipment;
	private String emergent;
	private String roomType;
	private String post;
	private String auditRemark;
	private String serialNo;
	private String warningId;

	public RepairApply() {

	}

	public RepairApply(String repairApplyId, String branchId, String contractId, String roomId, Date applicationDate,
			String reservedPerson, String mobile, String status, String auditDescription, Date recordTime,
			String recordUser, String remark, Date repairTime, String equipment, String emergent, String roomType,String post, String auditRemark, String serialNo,
			String warningId) {
		super();
		this.repairApplyId = repairApplyId;
		this.branchId = branchId;
		this.contractId = contractId;
		this.roomId = roomId;
		this.applicationDate = applicationDate;
		this.reservedPerson = reservedPerson;
		this.mobile = mobile;
		this.status = status;
		this.auditDescription = auditDescription;
		this.recordTime = recordTime;
		this.recordUser = recordUser;
		this.remark = remark;
		this.repairTime = repairTime;
		this.equipment = equipment;
		this.emergent = emergent;
		this.roomType = roomType;
		this.post = post;
		this.auditRemark = auditRemark;
		this.serialNo = serialNo;
		this.warningId = warningId;
	}

	@Column(name = "ROOM_TYPE",nullable = true, length = 16)
	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	@Column(name = "EQUIPMENT", nullable = false, length = 10)
	public String getEquipment() {
		return equipment;
	}

	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}

	@Id
	@Column(name = "REPAIRAPPLY_ID", unique = true, nullable = false, length = 18)
	public String getRepairApplyId() {
		return repairApplyId;
	}

	public void setRepairApplyId(String repairApplyId) {
		this.repairApplyId = repairApplyId;
	}

	@Column(name = "BRANCH_ID", nullable = false, length = 6)
	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	@Column(name = "CONTRACT_ID", nullable = true, length = 17)
	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	@Column(name = "ROOM_ID", nullable = false, length = 6)
	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "APPLICATION_DATE")
	public Date getApplicationDate() {
		return applicationDate;
	}

	public void setApplicationDate(Date applicationDate) {
		this.applicationDate = applicationDate;
	}

	@Column(name = "RESERVED_PERSON", nullable = false, length = 8)
	public String getReservedPerson() {
		return reservedPerson;
	}

	public void setReservedPerson(String reservedPerson) {
		this.reservedPerson = reservedPerson;
	}

	@Column(name = "MOBILE" , length = 11)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "status", nullable = false, length = 1)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	@Column(name = "REMARK", nullable = true, length = 200)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "REPAIR_TIME")
	public Date getRepairTime() {
		return repairTime;
	}

	public void setRepairTime(Date repairTime) {
		this.repairTime = repairTime;
	}

	public void setEmergent(String emergent) {
		this.emergent = emergent;
	}

	@Column(name = "EMERGENT", nullable = false, length = 1)
	public String getEmergent() {
		return emergent;
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
	
	@Column(name = "SERIAL_NO", length = 20)
	public String getSerialNo() {
		return this.serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	@Column(name = "WARNING_ID", length = 18)
	public String getWarningId() {
		return this.warningId;
	}

	public void setWarningId(String warningId) {
		this.warningId = warningId;
	}

}
