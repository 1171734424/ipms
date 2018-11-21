package com.ideassoft.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



@Entity
@Table(name = "TB_P_RESERVED", schema = "UCR_PMS")
public class Reserved implements java.io.Serializable {

	private static final long serialVersionUID = 8823422079855383642L;
	private String reservedId;
	private String branchId;
	private String source;
	private String roomType;
	private Date reservedDate;
	private String reservedPerson;
	private String mobile;
	private String status;
	private Date applicationTime;
	private Date recordTime;
	private String recordUser;
	private String remark;
	private String dispose;
	private String seeable;
	private Date startdate;
	private Date enddate;
	private String roomId;
	private String reservedType;

	@Id
	@Column(name = "RESERVED_ID", unique = true, nullable = false, length = 18)
	public String getReservedId() {
		return reservedId;
	}

	public void setReservedId(String reservedId) {
		this.reservedId = reservedId;
	}

	@Column(name = "BRANCH_ID", nullable = false, length = 6)
	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	@Column(name = "SOURCE", nullable = false, length = 2)
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Column(name = "ROOM_TYPE", nullable = false, length = 3)
	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RESERVED_DATE")
	public Date getReservedDate() {
		return reservedDate;
	}

	public void setReservedDate(Date reservedDate) {
		this.reservedDate = reservedDate;
	}

	@Column(name = "RESERVED_PERSON", nullable = false, length = 8)
	public String getReservedPerson() {
		return reservedPerson;
	}

	public void setReservedPerson(String reservedPerson) {
		this.reservedPerson = reservedPerson;
	}

	@Column(name = "MOBILE", nullable = false, length = 11)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "STATUS", length = 1)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "APPLICATION_TIME")
	public Date getApplicationTime() {
		return applicationTime;
	}

	public void setApplicationTime(Date applicationTime) {
		this.applicationTime = applicationTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RECORD_TIME")
	public Date getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	@Column(name = "RECORD_USER", length = 8)
	public String getRecordUser() {
		return recordUser;
	}

	public void setRecordUser(String recordUser) {
		this.recordUser = recordUser;
	}

	@Column(name = "REMARK", length = 200)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "DISPOSE", length = 1)
	public String getDispose() {
		return dispose;
	}

	public void setDispose(String dispose) {
		this.dispose = dispose;
	}
	@Column(name = "SEEABLE", length = 1)
	public String getSeeable() {
		return seeable;
	}

	public void setSeeable(String seeable) {
		this.seeable = seeable;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "STARTDATE")
	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ENDDATE")
	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}
	
	@Column(name = "ROOM_ID", length = 6)
	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	
	
	@Column(name = "RESERVE_TYPE", length = 1)
	public String getReservedType() {
		return reservedType;
	}

	public void setReservedType(String reservedType) {
		this.reservedType = reservedType;
	}

	public Reserved(String reservedId, String branchId, String source,
			String roomType, Date reservedDate, String reservedPerson,
			String mobile, String status, Date applicationTime,
			Date recordTime, String recordUser, String remark, String dispose,
			String seeable, Date startdate, Date enddate, String roomId,String reservedType) {
		super();
		this.reservedId = reservedId;
		this.branchId = branchId;
		this.source = source;
		this.roomType = roomType;
		this.reservedDate = reservedDate;
		this.reservedPerson = reservedPerson;
		this.mobile = mobile;
		this.status = status;
		this.applicationTime = applicationTime;
		this.recordTime = recordTime;
		this.recordUser = recordUser;
		this.remark = remark;
		this.dispose = dispose;
		this.seeable = seeable;
		this.startdate = startdate;
		this.enddate = enddate;
		this.roomId = roomId;
		this.reservedType = reservedType;
	}

	public Reserved() {

	}

	
	
	
}
