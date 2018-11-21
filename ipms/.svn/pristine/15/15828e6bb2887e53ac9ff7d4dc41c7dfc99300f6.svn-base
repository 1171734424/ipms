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
@Table(name = "TP_E_EQUIPCATEGORY", schema = "UCR_PMS")
public class EquipCategory implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 2518231955897207871L;
	private String categoryId;
	private String categoryName;
	private String recordUser;
	private Date recordTime;
	private String status;
	private String remark;

	// Constructors

	/** default constructor */
	public EquipCategory() {
	}

	/** minimal constructor */
	public EquipCategory(String categoryId, String categoryName, String recordUser, String status) {
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.recordUser = recordUser;
		this.status = status;
	}

	/** full constructor */
	public EquipCategory(String categoryId, String categoryName, String recordUser, Date recordTime, String status,
			String remark) {
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.recordUser = recordUser;
		this.recordTime = recordTime;
		this.status = status;
		this.remark = remark;
	}

	// Property accessors
	@Id
	@Column(name = "CATEGORY_ID", unique = true, nullable = false, length = 4)
	public String getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	@Column(name = "CATEGORY_NAME", nullable = false, length = 30)
	public String getCategoryName() {
		return this.categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	@Column(name = "RECORD_USER", nullable = false, length = 8)
	public String getRecordUser() {
		return this.recordUser;
	}

	public void setRecordUser(String recordUser) {
		this.recordUser = recordUser;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RECORD_TIME", insertable = false, updatable = true)
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

}