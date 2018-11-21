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
@Table(name = "TB_P_APTORDERDETAIL", schema = "UCR_PMS")
public class AptorderDetail implements java.io.Serializable {


	private static final long serialVersionUID = 4683471510757256398L;
	// Fields
	
	private String detailId;
	private String aptorderId;
	private String contrartId;
	private String recordUser;
	private Date recordTime;
	private String remark;
	private String status;
	

	public AptorderDetail() {
		
	}


	public AptorderDetail(String detailId, String aptorderId,
			String contrartId, String recordUser, Date recordTime,
			String remark, String status) {
		super();
		this.detailId = detailId;
		this.aptorderId = aptorderId;
		this.contrartId = contrartId;
		this.recordUser = recordUser;
		this.recordTime = recordTime;
		this.remark = remark;
		this.status = status;
	}

	@Id
	@Column(name = "DETAIL_ID", unique = true, nullable = false, length = 17)
	public String getDetailId() {
		return detailId;
	}


	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}

	@Column(name = "APTORDER_ID",  nullable = false, length = 17)
	public String getAptorderId() {
		return aptorderId;
	}


	public void setAptorderId(String aptorderId) {
		this.aptorderId = aptorderId;
	}

	@Column(name = "CONTRART_ID",  nullable = false, length = 17)
	public String getContrartId() {
		return contrartId;
	}


	public void setContrartId(String contrartId) {
		this.contrartId = contrartId;
	}

	@Column(name = "RECORD_USER",   length = 8)
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

	@Column(name = "REMARK",  length = 200)
	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "STATUS",  length = 1)
	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}








}