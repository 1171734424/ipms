package com.ideassoft.bean;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RoomTypeKey implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String branchId;
	private String roomType;
	
	public RoomTypeKey() {
	}
	
	public RoomTypeKey(String branchId, String roomType) {
		this.branchId = branchId;
		this.roomType = roomType;
	}
	
	@Column(name = "BRANCH_ID", nullable = false, length = 6)
	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	
	@Column(name = "ROOM_TYPE", nullable = false, length = 3)
	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof RoomTypeKey))
			return false;
		RoomTypeKey castOther = (RoomTypeKey) other;

		return ((this.getBranchId() == castOther.getBranchId()) || (this.getBranchId() != null
				&& castOther.getBranchId() != null && this.getBranchId().equals(castOther.getBranchId())))
				&& ((this.getRoomType() == castOther.getRoomType()) || (this.getRoomType() != null
						&& castOther.getRoomType() != null && this.getRoomType().equals(castOther.getRoomType())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getBranchId() == null ? 0 : this.getBranchId().hashCode());
		result = 37 * result + (getRoomType() == null ? 0 : this.getRoomType().hashCode());
		return result;
	}
	
	

}
