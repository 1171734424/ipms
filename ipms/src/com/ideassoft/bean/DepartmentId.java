package com.ideassoft.bean;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class DepartmentId implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = -1072999480540302826L;
	private String branchId;
	private String departId;

	// Constructors

	/** default constructor */
	public DepartmentId() {
	}

	/** full constructor */
	public DepartmentId(String branchId, String departId) {
		this.branchId = branchId;
		this.departId = departId;
	}

	// Property accessors

	@Column(name = "BRANCH_ID", nullable = false, length = 6)
	public String getBranchId() {
		return this.branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	@Column(name = "DEPART_ID", nullable = false, length = 4)
	public String getDepartId() {
		return this.departId;
	}

	public void setDepartId(String departId) {
		this.departId = departId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof DepartmentId))
			return false;
		DepartmentId castOther = (DepartmentId) other;

		return ((this.getBranchId() == castOther.getBranchId()) || (this.getBranchId() != null
				&& castOther.getBranchId() != null && this.getBranchId().equals(castOther.getBranchId())))
				&& ((this.getDepartId() == castOther.getDepartId()) || (this.getDepartId() != null
						&& castOther.getDepartId() != null && this.getDepartId().equals(castOther.getDepartId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getBranchId() == null ? 0 : this.getBranchId().hashCode());
		result = 37 * result + (getDepartId() == null ? 0 : this.getDepartId().hashCode());
		return result;
	}

}