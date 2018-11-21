package com.ideassoft.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ideassoft.core.annotation.interfaces.ColumnDefaultValue;

@Entity
@Table(name = "TB_E_FIXEDUSERINDOOR", schema = "UCR_PMS")
public class FixedUserInDoor implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 938363880749686322L;
	
	private String userId;
	private String userName;
	private String idcard;
	private String gender;
	private String mobile;
	private Date recordTime;
	private String recordUser;
	private String status;
	private String remark;
	private String equipSerialNo;
	private String sourceType;
	private Date startTime;
	private Date endTime;
	

	// Constructors

	/** default constructor */
	public FixedUserInDoor() {
	}

	/** minimal constructor */
	public FixedUserInDoor(String userId, String userName, String idcard, Date recordTime, String recordUser,
			String status, String equipSerialNo, String sourceType) {
		this.userId = userId;
		this.userName = userName;
		this.idcard = idcard;
		this.recordTime = recordTime;
		this.recordUser = recordUser;
		this.status = status;
		this.equipSerialNo = equipSerialNo;
		this.sourceType = sourceType;
	}

	/** full constructor */
	public FixedUserInDoor(String userId, String userName, String idcard, String gender, String mobile,
			Date recordTime, String recordUser, String status, String remark, String equipSerialNo,
			String sourceType, Date startTime, Date endTime) {
		this.userId = userId;
		this.userName = userName;
		this.idcard = idcard;
		this.gender = gender;
		this.mobile = mobile;
		this.recordTime = recordTime;
		this.recordUser = recordUser;
		this.status = status;
		this.remark = remark;
		this.equipSerialNo = equipSerialNo;
		this.sourceType = sourceType;
		this.endTime = endTime;
		this.startTime = startTime;
	}

	// Property accessors
	@Id
	@Column(name = "USER_ID", unique = true, nullable = false, length = 8)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "USER_NAME", nullable = false, length = 20)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "IDCARD", length = 18)
	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	@Column(name = "GENDER", length = 1)
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Column(name = "MOBILE", length = 11)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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

	@Column(name = "RECORD_USER", length = 18)
	public String getRecordUser() {
		return recordUser;
	}

	public void setRecordUser(String recordUser) {
		this.recordUser = recordUser;
	}

	@Column(name = "STATUS", nullable = false, length = 1)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "REMARK", length = 200)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "EQUIP_SERIALNO", nullable = false, length = 40)
	public String getEquipSerialNo() {
		return equipSerialNo;
	}

	public void setEquipSerialNo(String equipSerialNo) {
		this.equipSerialNo = equipSerialNo;
	}
	
	@Column(name = "SOURCE_TYPE", nullable = false, length = 4)
	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "START_TIME", insertable = true, updatable = true, nullable = false)
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "END_TIME", insertable = true, updatable = true, nullable = false)
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

}
