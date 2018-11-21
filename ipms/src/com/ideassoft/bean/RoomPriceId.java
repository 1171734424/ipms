package com.ideassoft.bean;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RoomPriceId implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 6295491330316813071L;
	private String branchId;
	private String rpId;
	private String roomType;
	private String rpKind;
	private String status;

	// Constructors

	/** default constructor */
	public RoomPriceId() {
	}

	/** full constructor */
	public RoomPriceId(String branchId, String status, String rpId, String roomType, String rpKind) {
		this.branchId = branchId;
		this.rpId = rpId;
		this.roomType = roomType;
		this.rpKind = rpKind;
		this.status = status;

	}

	// Property accessors

	@Column(name = "BRANCH_ID", nullable = false, length = 4)
	public String getBranchId() {
		return this.branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	@Column(name = "RP_ID", nullable = false, length = 3)
	public String getRpId() {
		return this.rpId;
	}

	public void setRpId(String rpId) {
		this.rpId = rpId;
	}

	@Column(name = "ROOM_TYPE", nullable = false, length = 3)
	public String getRoomType() {
		return this.roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	@Column(name = "RP_KIND", length = 1)
	public String getRpKind() {
		return this.rpKind;
	}

	public void setRpKind(String rpKind) {
		this.rpKind = rpKind;
	}

	@Column(name = "STATUS", nullable = false, length = 1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof RoomPriceId))
			return false;
		RoomPriceId castOther = (RoomPriceId) other;

		return ((this.getBranchId() == castOther.getBranchId()) || (this.getBranchId() != null
				&& castOther.getBranchId() != null && this.getBranchId().equals(castOther.getBranchId())))
				&& ((this.getRpId() == castOther.getRpId()) || (this.getRpId() != null && castOther.getRpId() != null && this
						.getRpId().equals(castOther.getRpId())))
				&& ((this.getRoomType() == castOther.getRoomType()) || (this.getRoomType() != null
						&& castOther.getRoomType() != null && this.getRoomType().equals(castOther.getRoomType())))
				&& ((this.getStatus() == castOther.getStatus()) || (this.getStatus() != null
						&& castOther.getStatus() != null && this.getStatus().equals(castOther.getStatus())))
				&& ((this.getRpKind() == castOther.getRpKind()) || (this.getRpKind() != null
						&& castOther.getRpKind() != null && this.getRpKind().equals(castOther.getRpKind())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getBranchId() == null ? 0 : this.getBranchId().hashCode());
		result = 37 * result + (getRpId() == null ? 0 : this.getRpId().hashCode());
		result = 37 * result + (getRoomType() == null ? 0 : this.getRoomType().hashCode());
		result = 37 * result + (getStatus() == null ? 0 : this.getStatus().hashCode());
		result = 37 * result + (getRpKind() == null ? 0 : this.getRpKind().hashCode());
		return result;
	}

}