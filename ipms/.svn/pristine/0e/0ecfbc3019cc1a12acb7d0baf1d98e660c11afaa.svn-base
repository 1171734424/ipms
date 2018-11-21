package com.ideassoft.bean;

import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ideassoft.core.annotation.interfaces.ColumnDefaultValue;

/**
 * EquipmentCategory entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TP_E_EQUIPMENTCATEGORY", schema = "UCR_PMS")
public class EquipmentCategory implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4070057682798043809L;
	// Fields

	private EquipmentCategoryKey equipmentCategoryKey;
	private String categoryName;
	private String superKind;
	private String isAcquisitonn;
	private Date recordTime;
	private String recordUser;
	private String status;
	private String remark;

	// Constructors

	/** default constructor */
	public EquipmentCategory() {
	}

	/** minimal constructor */
	public EquipmentCategory(EquipmentCategoryKey equipmentCategoryKey, String categoryName,
			String superKind, String recordUser, String status) {
		this.equipmentCategoryKey = equipmentCategoryKey;
		this.categoryName = categoryName;
		this.superKind = superKind;
		this.recordUser = recordUser;
		this.status = status;
	}

	/** full constructor */
	public EquipmentCategory(EquipmentCategoryKey equipmentCategoryKey, String categoryName,
			String superKind, String isAcquisitonn, Date recordTime,
			String recordUser, String status, String remark) {
		this.equipmentCategoryKey = equipmentCategoryKey;
		this.categoryName = categoryName;
		this.superKind = superKind;
		this.isAcquisitonn = isAcquisitonn;
		this.recordTime = recordTime;
		this.recordUser = recordUser;
		this.status = status;
		this.remark = remark;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "categoryId", column = @Column(name = "CATEGORY_ID", nullable = false, length = 12)),
			@AttributeOverride(name = "branchId", column = @Column(name = "BRANCH_ID", nullable = false, length = 6)) })
	public EquipmentCategoryKey getEquipmentCategoryKey() {
		return this.equipmentCategoryKey;
	}

	public void setEquipmentCategoryKey(EquipmentCategoryKey equipmentCategoryKey) {
		this.equipmentCategoryKey = equipmentCategoryKey;
	}

	@Column(name = "CATEGORY_NAME", nullable = false, length = 30)
	public String getCategoryName() {
		return this.categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	@Column(name = "SUPER_KIND", nullable = false, length = 2)
	public String getSuperKind() {
		return this.superKind;
	}

	public void setSuperKind(String superKind) {
		this.superKind = superKind;
	}

	@Column(name = "IS_ACQUISITONN", length = 2)
	public String getIsAcquisitonn() {
		return this.isAcquisitonn;
	}

	public void setIsAcquisitonn(String isAcquisitonn) {
		this.isAcquisitonn = isAcquisitonn;
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

	@Column(name = "RECORD_USER", nullable = false, length = 8)
	public String getRecordUser() {
		return this.recordUser;
	}

	public void setRecordUser(String recordUser) {
		this.recordUser = recordUser;
	}

	@Column(name = "STATUS", nullable = false, length = 2)
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