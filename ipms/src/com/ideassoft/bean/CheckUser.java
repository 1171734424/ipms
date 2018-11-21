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
@Table(name = "TB_C_CHECKUSER", schema = "UCR_PMS")
public class CheckUser implements Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7739430273107375987L;

	private String checkuserId;
	private String checkuserName;
	private String idcard;
	private String gender;
	private String mobile;
	private Date recordTime;
	private String recordUser;
	private String status;
	private String remark;
	private String checkId;
	private String checkuserType;
	private String rankName;
	private String address;
	private String checkinType;

	// Constructors

	/** default constructor */
	public CheckUser() {
	}

	/** minimal constructor */
	public CheckUser(String checkuserId, String checkuserName, String idcard, Date recordTime, String recordUser,
			String status, String checkuserType) {
		super();
		this.checkuserId = checkuserId;
		this.checkuserName = checkuserName;
		this.idcard = idcard;
		this.recordTime = recordTime;
		this.recordUser = recordUser;
		this.status = status;
		this.setCheckuserType(checkuserType);
	}

	/** full constructor */
	public CheckUser(String checkuserId, String checkuserName, String idcard, String gender, String mobile,
			Date recordTime, String recordUser, String status, String remark, String checkuserType, String checkinType) {
		super();
		this.checkuserId = checkuserId;
		this.checkuserName = checkuserName;
		this.idcard = idcard;
		this.gender = gender;
		this.mobile = mobile;
		this.recordTime = recordTime;
		this.recordUser = recordUser;
		this.status = status;
		this.remark = remark;
		this.setCheckuserType(checkuserType);
		this.setCheckinType(checkinType);
	}

	// Property accessors
	@Id
	@Column(name = "CHECKUSER_ID", unique = true, nullable = false, length = 15)
	public String getCheckuserId() {
		return checkuserId;
	}

	public void setCheckuserId(String checkuserId) {
		this.checkuserId = checkuserId;
	}

	@Column(name = "CHECKUSER_NAME", nullable = false, length = 20)
	public String getCheckuserName() {
		return checkuserName;
	}

	public void setCheckuserName(String checkuserName) {
		this.checkuserName = checkuserName;
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

	@Column(name = "CHECK_ID", nullable = false, length = 18)
	public String getCheckId() {
		return checkId;
	}

	public void setCheckId(String checkId) {
		this.checkId = checkId;
	}

	@Column(name = "CHECKUSER_TYPE", nullable = false, length = 18)
	public String getCheckuserType() {
		return checkuserType;
	}
	
	public void setCheckuserType(String checkuserType) {
		this.checkuserType = checkuserType;
	}
	
	@Column(name = "RANKNAME", nullable = true, length = 20)
	public String getRankName() {
		return rankName;
	}

	public void setRankName(String rankName) {
		this.rankName = rankName;
	}
	
	@Column(name = "ADDRESS", nullable = true, length = 60)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "CHECKIN_TYPE", nullable = true, length = 1)
	public String getCheckinType() {
		return checkinType;
	}
	
	public void setCheckinType(String checkinType) {
		this.checkinType = checkinType;
	}

	@Override  
    public CheckUser clone() throws CloneNotSupportedException {  
        return (CheckUser)super.clone();  
    } 


}
