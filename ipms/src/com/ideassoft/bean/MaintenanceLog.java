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
@Table(name = "TL_E_MAINTENANCELOG", schema = "UCR_PMS")
public class MaintenanceLog implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 3474600828960448642L;
	private String logId;
	private String branchId;
	private String equipment;
	private String problemTag;
	private String describe;
	private String recordUser;
	private Date recordTime;// 录入时间
	private String status;
	private String remark;

	private String roomId;
	private String applicationSource;
	private String repairPerson;
	private Date applicationDate;// 申请日期
	private String repairTimearea;
	private Date repairTime;// 申请维修日期
	private String repairApplyId;
	private Date acrepairTime;// 实际维修日期

	// Constructors

	/** default constructor */
	public MaintenanceLog() {
	}

	public MaintenanceLog(String logId, String branchId, String equipment, String problemTag, String describe,
			String recordUser, Date recordTime, String status, String remark, String roomId, String applicationSource,
			String repairPerson, Date applicationDate, String repairTimearea, Date repairTime, String repairApplyId,
			Date acrepairTime) {
		super();
		this.logId = logId;
		this.branchId = branchId;
		this.equipment = equipment;
		this.problemTag = problemTag;
		this.describe = describe;
		this.recordUser = recordUser;
		this.recordTime = recordTime;
		this.status = status;
		this.remark = remark;
		this.roomId = roomId;
		this.applicationSource = applicationSource;
		this.repairPerson = repairPerson;
		this.applicationDate = applicationDate;
		this.repairTimearea = repairTimearea;
		this.repairTime = repairTime;
		this.repairApplyId = repairApplyId;
		this.acrepairTime = acrepairTime;
	}

	// Property accessors
	@Id
	@Column(name = "LOG_ID", unique = true, nullable = false, length = 19)
	public String getLogId() {
		return this.logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	@Column(name = "BRANCH_ID", nullable = false, length = 6)
	public String getBranchId() {
		return this.branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	@Column(name = "EQUIPMENT", nullable = false, length = 10)
	public String getEquipment() {
		return equipment;
	}

	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}

	@Column(name = "PROBLEM_TAG", nullable = true, length = 100)
	public String getProblemTag() {
		return this.problemTag;
	}

	public void setProblemTag(String problemTag) {
		this.problemTag = problemTag;
	}

	@Column(name = "DESCRIBE", length = 200)
	public String getDescribe() {
		return this.describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	@Column(name = "RECORD_USER", nullable = true, length = 8)
	public String getRecordUser() {
		return this.recordUser;
	}

	public void setRecordUser(String recordUser) {
		this.recordUser = recordUser;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RECORD_TIME")
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

	@Column(name = "ROOM_ID", nullable = false, length = 8)
	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	@Column(name = "APPLICATION_SOURCE", nullable = false, length = 1)
	public String getApplicationSource() {
		return applicationSource;
	}

	public void setApplicationSource(String applicationSource) {
		this.applicationSource = applicationSource;
	}

	@Column(name = "REPAIR_PERSON", length = 6)
	public String getRepairPerson() {
		return repairPerson;
	}

	public void setRepairPerson(String repairPerson) {
		this.repairPerson = repairPerson;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "APPLICATION_DATE", nullable = false)
	public Date getApplicationDate() {
		return applicationDate;
	}

	public void setApplicationDate(Date applicationDate) {
		this.applicationDate = applicationDate;
	}

	@Column(name = "REPAIR_TIMEAREA", length = 1)
	public String getRepairTimearea() {
		return repairTimearea;
	}

	public void setRepairTimearea(String repairTimearea) {
		this.repairTimearea = repairTimearea;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "REPAIR_TIME", nullable = false)
	public Date getRepairTime() {
		return repairTime;
	}

	public void setRepairTime(Date repairTime) {
		this.repairTime = repairTime;
	}

	public void setRepairApplyId(String repairApplyId) {
		this.repairApplyId = repairApplyId;
	}

	@Column(name = "REPAIRAPPLY_ID", nullable = false, length = 18)
	public String getRepairApplyId() {
		return repairApplyId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ACREPAIR_TIME")
	public Date getAcrepairTime() {
		return acrepairTime;
	}

	public void setAcrepairTime(Date acrepairTime) {
		this.acrepairTime = acrepairTime;
	}

}