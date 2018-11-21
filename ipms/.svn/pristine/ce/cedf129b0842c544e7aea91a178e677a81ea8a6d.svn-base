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
@Table(name = "TP_C_GOODSCATEGORY", schema = "UCR_PMS")
public class GoodsCategory implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = -3305018756418138002L;
	private String categoryId;
	private String branchId;
	private String chargeRoom;
	private String superCategory;
	private String categoryName;
	private String recordUser;
	private Date recordTime;
	private String status;
	private String remark;
    private String picture;
	// Constructors

	/** default constructor */
	public GoodsCategory() {
	}

	/** minimal constructor */
	public GoodsCategory(String categoryId, String branchId, String superCategory, String categoryName,
			String recordUser, String status) {
		this.categoryId = categoryId;
		this.branchId = branchId;
		this.superCategory = superCategory;
		this.categoryName = categoryName;
		this.recordUser = recordUser;
		this.status = status;
	}

	/** full constructor */
	public GoodsCategory(String categoryId, String branchId, String chargeRoom, String superCategory,
			String categoryName, String recordUser, Date recordTime, String status, String remark) {
		this.categoryId = categoryId;
		this.branchId = branchId;
		this.chargeRoom = chargeRoom;
		this.superCategory = superCategory;
		this.categoryName = categoryName;
		this.recordUser = recordUser;
		this.recordTime = recordTime;
		this.status = status;
		this.remark = remark;
	}

	// Property accessors
	@Id
	@Column(name = "CATEGORY_ID", unique = true, nullable = false, length = 8)
	public String getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	@Column(name = "BRANCH_ID", nullable = false, length = 6)
	public String getBranchId() {
		return this.branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	@Column(name = "CHARGE_ROOM", nullable = false, length = 1)
	public String getChargeRoom() {
		return this.chargeRoom;
	}

	public void setChargeRoom(String chargeRoom) {
		this.chargeRoom = chargeRoom;
	}

	@Column(name = "SUPER_CATEGORY", nullable = false, length = 8)
	public String getSuperCategory() {
		return this.superCategory;
	}

	public void setSuperCategory(String superCategory) {
		this.superCategory = superCategory;
	}

	@Column(name = "CATEGORY_NAME", nullable = false, length = 10)
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
	@Column(name = "PICTURES", length = 1000)
	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

}