package com.ideassoft.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "TB_E_USERREAL", schema = "UCR_PMS")
public class RealUser implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6011465924728902260L;
	private String userId;
	private String userName;  
	private String idcard;
	private String sex;
	private String birthday;
	private String nation;
	private String address;
	private String institution;
	private String startTime;
	private String endTime;
	private Date recordTime;
	private String remark;
	
	public RealUser() {
	}
	
	/** full constructor */
	public RealUser(String userId, String userName, String idcard, String sex,
			String birthday, String nation, String address, String institution,
			String startTime, String endTime, Date recordTime, String remark) {
		this.userId = userId;
		this.userName = userName;
		this.idcard = idcard;
		this.sex = sex;
		this.birthday = birthday;
		this.nation = nation;
		this.address = address;
		this.institution = institution;
		this.startTime = startTime;
		this.endTime = endTime;
		this.recordTime = recordTime;
		this.remark = remark;
	}
	
	@Id
	@Column(name = "USER_ID", unique = true, nullable = false, length = 8)
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Column(name = "USER_NAME", length = 40)
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
	
	@Column(name = "SEX", length = 3)
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	@Column(name = "BIRTHDAY", length = 11)
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	
	@Column(name = "NATION", length = 20)
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	
	@Column(name = "ADDRESS", length = 80)
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Column(name = "INSTITUTION", length = 40)
	public String getInstitution() {
		return institution;
	}
	public void setInstitution(String institution) {
		this.institution = institution;
	}
	
	@Column(name = "START_TIME", length = 11)
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
	@Column(name = "END_TIME", length = 11)
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RECORD_TIME", nullable = false, insertable = true)
	public Date getRecordTime() {
		return recordTime;
	}
	
	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}
	
	@Column(name = "REMARK", length = 200)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
