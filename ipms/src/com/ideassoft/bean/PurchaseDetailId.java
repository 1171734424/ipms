package com.ideassoft.bean;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PurchaseDetailId implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 5562892196957413640L;
	private String purchaseId;
	private String dataId;

	// Constructors

	/** default constructor */
	public PurchaseDetailId() {
	}

	/** full constructor */
	public PurchaseDetailId(String purchaseId, String dataId) {
		this.purchaseId = purchaseId;
		this.dataId = dataId;
	}

	// Property accessors

	@Column(name = "PURCHASE_ID", nullable = false, length = 12)
	public String getPurchaseId() {
		return this.purchaseId;
	}

	public void setPurchaseId(String purchaseId) {
		this.purchaseId = purchaseId;
	}

	@Column(name = "DATA_ID", nullable = false, length = 2)
	public String getDataId() {
		return this.dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PurchaseDetailId))
			return false;
		PurchaseDetailId castOther = (PurchaseDetailId) other;

		return ((this.getPurchaseId() == castOther.getPurchaseId()) || (this.getPurchaseId() != null
				&& castOther.getPurchaseId() != null && this.getPurchaseId().equals(castOther.getPurchaseId())))
				&& ((this.getDataId() == castOther.getDataId()) || (this.getDataId() != null
						&& castOther.getDataId() != null && this.getDataId().equals(castOther.getDataId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getPurchaseId() == null ? 0 : this.getPurchaseId().hashCode());
		result = 37 * result + (getDataId() == null ? 0 : this.getDataId().hashCode());
		return result;
	}

}