package com.ideassoft.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "TB_P_INVESTER", schema = "UCR_PMS")
public class Invester implements java.io.Serializable{

	private static final long serialVersionUID = -7991147737011036546L;
	private String investerApplyId;
	private String applyer;
	private String mobile;
	private String address;
	private String detail;
	private String status;
	private String remark;
	private String recordUser;
	private Date recordTime;
	
	
	public Invester() {
		
	}
	
	public Invester(String investerApplyId, String applyer, String mobile,
			String address, String detail, String status) {
		super();
		this.investerApplyId = investerApplyId;
		this.applyer = applyer;
		this.mobile = mobile;
		this.address = address;
		this.detail = detail;
		this.status = status;
	}

	public Invester(String investerApplyId, String applyer, String mobile,
			String address, String detail, String status, String remark,
			String recordUser, Date recordTime) {
		super();
		this.investerApplyId = investerApplyId;
		this.applyer = applyer;
		this.mobile = mobile;
		this.address = address;
		this.detail = detail;
		this.status = status;
		this.remark = remark;
		this.recordUser = recordUser;
		this.recordTime = recordTime;
	}
	
	
	@Id
	@Column(name = "INVESTERAPPLY_ID", unique = true, nullable = false, length = 12)
	public String getInvesterApplyId() {
		return investerApplyId;
	}

	public void setInvesterApplyId(String investerApplyId) {
		this.investerApplyId = investerApplyId;
	}

	@Column(name = "APPLYER", nullable = false, length = 50)
	public String getApplyer() {
		return applyer;
	}
	public void setApplyer(String applyer) {
		this.applyer = applyer;
	}
	@Column(name = "MOBILE", nullable = false, length = 11)
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	@Column(name = "ADDRESS", nullable = false, length = 60)
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Column(name = "DETAIL", nullable = false, length = 1000)
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	@Column(name = "STATUS", nullable = false, length = 1)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Column(name = "REMARK",  length = 200)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name = "RECORD_USER",  length = 8)
	public String getRecordUser() {
		return recordUser;
	}
	public void setRecordUser(String recordUser) {
		this.recordUser = recordUser;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RECORD_TIME", insertable = false, updatable = true)
	public Date getRecordTime() {
		return recordTime;
	}
	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}
	
}
