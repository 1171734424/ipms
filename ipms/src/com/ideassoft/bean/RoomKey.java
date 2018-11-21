package com.ideassoft.bean;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RoomKey implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = -4362652355008073505L;
	private String branchId;
	private String roomId;
	

	// Constructors

	/** default constructor */
	public RoomKey() {
	}

	/** full constructor */
	public RoomKey(String branchId, String roomId) {
		this.branchId = branchId;
		this.roomId = roomId;
	}

	// Property accessors

	@Column(name = "BRANCH_ID", nullable = false, length = 6)
	public String getBranchId() {
		return this.branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	@Column(name = "ROOM_ID", nullable = false, length = 6)
	public String getRoomId() {
		return this.roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof RoomKey))
			return false;
		RoomKey castOther = (RoomKey) other;

		return ((this.getBranchId() == castOther.getBranchId()) || (this.getBranchId() != null
				&& castOther.getBranchId() != null && this.getBranchId().equals(castOther.getBranchId())))
				&& ((this.getRoomId() == castOther.getRoomId()) || (this.getRoomId() != null
						&& castOther.getRoomId() != null && this.getRoomId().equals(castOther.getRoomId())));
						
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getBranchId() == null ? 0 : this.getBranchId().hashCode());
		result = 37 * result + (getRoomId() == null ? 0 : this.getRoomId().hashCode());
		return result;
	}

}