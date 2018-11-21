package com.ideassoft.bean;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * EquipmentCategoryKey entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class EquipmentCategoryKey implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -6590056788442738106L;
	private String categoryId;
	private String branchId;

	// Constructors

	/** default constructor */
	public EquipmentCategoryKey() {
	}

	/** full constructor */
	public EquipmentCategoryKey(String categoryId, String branchId) {
		this.categoryId = categoryId;
		this.branchId = branchId;
	}

	// Property accessors

	@Column(name = "CATEGORY_ID", nullable = false, length = 8)
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

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof EquipmentCategoryKey))
			return false;
		EquipmentCategoryKey castOther = (EquipmentCategoryKey) other;

		return ((this.getCategoryId() == castOther.getCategoryId()) || (this
				.getCategoryId() != null
				&& castOther.getCategoryId() != null && this.getCategoryId()
				.equals(castOther.getCategoryId())))
				&& ((this.getBranchId() == castOther.getBranchId()) || (this
						.getBranchId() != null
						&& castOther.getBranchId() != null && this
						.getBranchId().equals(castOther.getBranchId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getCategoryId() == null ? 0 : this.getCategoryId()
						.hashCode());
		result = 37 * result
				+ (getBranchId() == null ? 0 : this.getBranchId().hashCode());
		return result;
	}

}