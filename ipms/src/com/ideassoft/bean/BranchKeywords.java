package com.ideassoft.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@Table(name = "TB_C_BRANCHKEYWORDS", schema = "UCR_PMS")
public class BranchKeywords implements Serializable {

	private static final long serialVersionUID = -2422123202204796599L;
	private String keywordsId;
	private String branchId;
	private String keywords;
	private Date recordTime;
	private String recordUser;
	private String status;
	private String remark;
	
	public BranchKeywords() {

	}
	
	public BranchKeywords(String keywordsId, String branchId,String keywords,
			Date recordTime,String recordUser, String status, String remark) {
		super();
		this.keywordsId = keywordsId;
		this.branchId = branchId;
		this.keywords = keywords;
		this.recordTime = recordTime;
		this.recordUser = recordUser;
		this.status = status;
		this.remark = remark;
	}
	
	@Id
	@Column(name = "KEYWORDS_ID" , unique = true, nullable = false, length = 18)
	public String getKeywordsId() {
		return keywordsId;
	}
	
	public void setKeywordsId(String keywordsId) {
		this.keywordsId = keywordsId;
	}
	
	@Column(name = "BRANCH_ID", nullable = false, length = 6)
	public String getBranchId() {
		return branchId;
	}
	
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	
	@Column(name = "KEYWORDS", nullable = true, length = 200)
	public String getKeywords(){
		return keywords;
	}
	
	public void setKeywords(String keywords){
		this.keywords = keywords;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RECORD_TIME")
	public Date getRecordTime() {
		return recordTime;
	}
	
	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}
	
	@Column(name = "RECORD_USER", nullable = false, length = 8)
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
	
	@Column(name = "REMARK", nullable = true, length = 200)
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
