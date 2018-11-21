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
@Table(name = "TB_P_CLEAN", schema = "UCR_PMS")
public class Clean implements java.io.Serializable {

	private static final long serialVersionUID = 3082270169769087782L;

	private String cleanId;
	private String branchId;
	private Date cleanDate;
	private String roomId;
	private String cleanPerson;
	private String recordUser;
	private Date recordTime;
	private String timeArea;
	private String remark;
	private String cleanApplyId;
	private Integer restAmount;

	public Clean(String cleanId, String branchId, Date cleanDate, String roomId, String cleanPerson, String recordUser,
			Date recordTime, String timeArea, String remark, String cleanApplyId, Integer restAmount) {
		super();
		this.cleanId = cleanId;
		this.branchId = branchId;
		this.cleanDate = cleanDate;
		this.roomId = roomId;
		this.cleanPerson = cleanPerson;
		this.recordUser = recordUser;
		this.recordTime = recordTime;
		this.timeArea = timeArea;
		this.remark = remark;
		this.cleanApplyId = cleanApplyId;
		this.restAmount = restAmount;
	}

	public Clean() {

	}

	@Id
	@Column(name = "CLEAN_ID", unique = true, nullable = false, length = 17)
	public String getCleanId() {
		return cleanId;
	}

	public void setCleanId(String cleanId) {
		this.cleanId = cleanId;
	}

	@Column(name = "BRANCH_ID", nullable = false, length = 6)
	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CLEAN_DATE")
	public Date getCleanDate() {
		return cleanDate;
	}

	public void setCleanDate(Date cleanDate) {
		this.cleanDate = cleanDate;
	}

	@Column(name = "ROOM_ID", length = 50)
	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	@Column(name = "CLEAN_PERSON", length = 80)
	public String getCleanPerson() {
		return cleanPerson;
	}

	public void setCleanPerson(String cleanPerson) {
		this.cleanPerson = cleanPerson;
	}

	@Column(name = "RECORD_USER", length = 8)
	public String getRecordUser() {
		return recordUser;
	}

	public void setRecordUser(String recordUser) {
		this.recordUser = recordUser;
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

	@Column(name = "TIMEAREA", nullable = false, length = 1)
	public String getTimeArea() {
		return timeArea;
	}

	public void setTimeArea(String timeArea) {
		this.timeArea = timeArea;
	}

	@Column(name = "REMARK", length = 200)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "CLEANAPPLY_ID", length = 18)
	public String getCleanApplyId() {
		return cleanApplyId;
	}

	public void setCleanApplyId(String cleanApplyId) {
		this.cleanApplyId = cleanApplyId;
	}

	@Column(name = "REST_AMOUNT", nullable = false, precision = 2, scale = 0)
	public Integer getRestAmount() {
		return restAmount;
	}

	public void setRestAmount(Integer restAmount) {
		this.restAmount = restAmount;
	}

}