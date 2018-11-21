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
@Table(name = "TB_C_FREQUENTER", schema = "UCR_PMS")
public class Frequenter implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 938363880749686322L;
	
	private String userId;
	private String userName;
	private String idcard;
	private String gender;
	private String post;
	private String mobile;
	private String address;
	private String city;
	private String district;
	private Date recordTime;
	private String recordUser;
	private String status;
	private String remark;
	

	// Constructors

	/** default constructor */
	public Frequenter() {
	}

	public Frequenter(String userId, String userName, String idcard,
			String gender, String post, String mobile, String address, String city,
			String district, Date recordTime, String recordUser, String status,
			String remark) {
		this.userId = userId;
		this.userName = userName;
		this.idcard = idcard;
		this.gender = gender;
		this.post = post;
		this.mobile = mobile;
		this.address = address;
		this.city = city;
		this.district = district;
		this.recordTime = recordTime;
		this.recordUser = recordUser;
		this.status = status;
		this.remark = remark;
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

	@Column(name = "RECORD_USER", length = 10)
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

	@Column(name = "ADDRESS", length = 200)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "CITY", length = 13)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "DISTRICT", length = 13)
	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	@Column(name = "POST", length = 4)
	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	
}
