package com.ideassoft.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ideassoft.core.annotation.interfaces.ColumnDefaultValue;

@Entity
@Table(name = "TP_P_ROOMPACK", schema = "UCR_PMS")
public class RoomPack implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 3913042556719236453L;
	private String packId;
	private String packName;
	private String status;
	private Date recordTime;
	private String recordUser;
	private String remark;

	// Constructors

	/** default constructor */
	public RoomPack() {
	}

	/** minimal constructor */
	public RoomPack(String packId, String packName, String status, String recordUser) {
		this.packId = packId;
		this.packName = packName;
		this.status = status;
		this.recordUser = recordUser;
	}

	/** full constructor */
	public RoomPack(String packId, String packName, String status, Date recordTime, String recordUser, String remark) {
		this.packId = packId;
		this.packName = packName;
		this.status = status;
		this.recordTime = recordTime;
		this.recordUser = recordUser;
		this.remark = remark;
	}

	// Property accessors
	@Id
	@Column(name = "PACK_ID", unique = true, nullable = false, length = 4)
	public String getPackId() {
		return this.packId;
	}

	public void setPackId(String packId) {
		this.packId = packId;
	}

	@Column(name = "PACK_NAME", nullable = false, length = 10)
	public String getPackName() {
		return this.packName;
	}

	public void setPackName(String packName) {
		this.packName = packName;
	}

	@Column(name = "STATUS", nullable = false, length = 2)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
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

}