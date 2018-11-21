package com.ideassoft.bean;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CashKey implements java.io.Serializable {

	private static final long serialVersionUID = -1400735784172915841L;
	// Fields

	private String branchId;
	private String cashBox;

	// Constructors

	/** default constructor */
	public CashKey() {
	}

	/** full constructor */
	public CashKey(String branchId, String cashBox) {
		this.branchId = branchId;
		this.cashBox = cashBox;
	}

	// Property accessors

	@Column(name = "BRANCH_ID", nullable = false, length = 6)
	public String getBranchId() {
		return this.branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	@Column(name = "CASH_BOX", nullable = false, length = 1)
	public String getCashBox() {
		return this.cashBox;
	}

	public void setCashBox(String cashBox) {
		this.cashBox = cashBox;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof CashKey))
			return false;
		CashKey castOther = (CashKey) other;

		return ((this.getBranchId() == castOther.getBranchId()) || (this.getBranchId() != null
				&& castOther.getBranchId() != null && this.getBranchId().equals(castOther.getBranchId())))
				&& ((this.getCashBox() == castOther.getCashBox()) || (this.getCashBox() != null
						&& castOther.getCashBox() != null && this.getCashBox().equals(castOther.getCashBox())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getBranchId() == null ? 0 : this.getBranchId().hashCode());
		result = 37 * result + (getCashBox() == null ? 0 : this.getCashBox().hashCode());
		return result;
	}

}