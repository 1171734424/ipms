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

@Entity
@Table(name = "TB_P_ROOM", schema = "UCR_PMS")
public class Room implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 1191788240704990887L;
	private RoomKey roomKey;
	private String theme;
	private String roomType;
	private Short area;
	private String floor;
	private Date recordTime;
	private String recordUser;
	private String remark;
	private String status;

	// Constructors

	/** default constructor */
	public Room() {
	}

	/** minimal constructor */
	public Room(RoomKey roomKey, String theme, String roomType, String floor, String recordUser) {
		this.roomKey = roomKey;
		this.theme = theme;
		this.roomType = roomType;
		this.floor = floor;
		this.recordUser = recordUser;
	}
	
	/** minimal constructor */
	public Room(RoomKey roomKey, String theme, String roomType, String floor, Date recordTime,String recordUser,String status) {
		this.roomKey = roomKey;
		this.theme = theme;
		this.roomType = roomType;
		this.floor = floor;
		this.recordTime = recordTime;
		this.recordUser = recordUser;
		this.status = status;
	}

	/** full constructor */
	public Room(RoomKey roomKey, String theme, String roomType, Short area, String floor, Date recordTime,
			String recordUser, String remark, String status) {
		this.roomKey = roomKey;
		this.theme = theme;
		this.roomType = roomType;
		this.area = area;
		this.floor = floor;
		this.recordTime = recordTime;
		this.recordUser = recordUser;
		this.remark = remark;
		this.status = status;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "branchId", column = @Column(name = "BRANCH_ID", nullable = false, length = 6)),
			@AttributeOverride(name = "roomId", column = @Column(name = "ROOM_ID", nullable = false, length = 6)),
			@AttributeOverride(name = "status", column = @Column(name = "STATUS", nullable = false, length = 1)) })
	public RoomKey getRoomKey() {
		return this.roomKey;
	}

	public void setRoomKey(RoomKey roomKey) {
		this.roomKey = roomKey;
	}

	@Column(name = "THEME", nullable = false, length = 1)
	public String getTheme() {
		return this.theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	@Column(name = "ROOM_TYPE", nullable = false, length = 3)
	public String getRoomType() {
		return this.roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	@Column(name = "AREA", precision = 3, scale = 0)
	public Short getArea() {
		return this.area;
	}

	public void setArea(Short area) {
		this.area = area;
	}

	@Column(name = "FLOOR", nullable = false, length = 2)
	public String getFloor() {
		return this.floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
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

	@Column(name = "REMARK", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(name = "STATUS", length = 1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}