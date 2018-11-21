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
@Table(name = "TB_P_PASSWORD", schema = "UCR_PMS")
public class Password implements java.io.Serializable{
	
	private static final long serialVersionUID = -2132705310671104772L;
	private String dataId;
	private String memberId;
	private String password;
	private Date starttime;
	private Date endtime;
	private String remark;
	private Date recordTime;
	public Password() {
		
	}
	public Password(String dataId, String memberId, String password,
			Date starttime, Date endtime, Date recordTime) {

		this.dataId = dataId;
		this.memberId = memberId;
		this.password = password;
		this.starttime = starttime;
		this.endtime = endtime;
		this.recordTime = recordTime;
	}
	public Password(String dataId, String memberId, String password,
			Date starttime, Date endtime, String remark, Date recordTime) {

		this.dataId = dataId;
		this.memberId = memberId;
		this.password = password;
		this.starttime = starttime;
		this.endtime = endtime;
		this.remark = remark;
		this.recordTime = recordTime;
	}
	
	@Id
	@Column(name = "DATA_ID", unique = true, nullable = false, length = 17)
	public String getDataId() {
		return dataId;
	}
	public void setDataId(String dataId) {
		this.dataId = dataId;
	}
	@Column(name = "MEMBER_ID", nullable = false, length = 8)
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	@Column(name = "PASSWORD", nullable = false, length = 6)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "STARTTIME", nullable = false)
	public Date getStarttime() {
		return starttime;
	}
	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ENDTIME", nullable = false)
	public Date getEndtime() {
		return endtime;
	}
	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}
	@Column(name = "REMARK",  length = 200)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RECORD_TIME", nullable = false)
	@ColumnDefaultValue(value = "sysdate", insertable = false)
	public Date getRecordTime() {
		return recordTime;
	}
	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	
	
}
