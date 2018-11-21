package com.ideassoft.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TB_P_ROOMMAPPING", schema = "UCR_PMS")
public class RoomMapping implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = -5789764407946093610L;
	private String roommappingId;
	private String roomId;
	private String relaRoomid;
	private String branchId;

	// Constructors

	/** default constructor */
	public RoomMapping() {
	}

	/** full constructor */
	public RoomMapping(String roomId, String relaRoomid,String branchId) {
		this.roomId = roomId;
		this.relaRoomid = relaRoomid;
		this.branchId = branchId;
	}

	// Property accessors
	@Id
	@Column(name = "ROOMMAPPING_ID", unique = true, nullable = false, length = 6)
	public String getRoommappingId() {
		return roommappingId;
	}
	
	public void setRoommappingId(String roommappingId) {
		this.roommappingId = roommappingId;
	}
	
	@Column(name = "ROOM_ID", unique = true, nullable = false, length = 6)
	public String getRoomId() {
		return this.roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	@Column(name = "RELA_ROOMID", nullable = false, length = 6)
	public String getRelaRoomid() {
		return this.relaRoomid;
	}

	public void setRelaRoomid(String relaRoomid) {
		this.relaRoomid = relaRoomid;
	}

	@Column(name = "BRANCH_ID", nullable = false, length = 6)
	public String getBranchId() {
		return this.branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	

}