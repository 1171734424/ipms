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
@Table(name = "TB_P_EXCEPTION", schema = "UCR_PMS")
public class ExceptionMessage implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6921191151118050777L;
	private String exceptionId;
	private String exceptionType;
	private Date dailyTime;
	private Date recordTime;
	private String status;
	private String remark;
	private String branchId;

	@Id
	@Column(name = "EXCEPTION_ID", unique = true, nullable = false, length = 8)
	public String getExceptionId() {
		return exceptionId;
	}

	public void setExceptionId(String exceptionId) {
		this.exceptionId = exceptionId;
	}

	@Column(name = "EXCEPTION_TYPE", length = 1)
	public String getExceptionType() {
		return exceptionType;
	}

	public void setExceptionType(String exceptionType) {
		this.exceptionType = exceptionType;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DAILY_TIME", length = 7)
	public Date getDailyTime() {
		return dailyTime;
	}

	public void setDailyTime(Date dailyTime) {
		this.dailyTime = dailyTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RECORD_TIME", insertable = false, updatable = true)
	@ColumnDefaultValue(value = "sysdate", insertable = false)
	public Date getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	@Column(name = "STATUS", length = 1)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "REMARK", length = 200)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "BRANCH_ID", length = 6)
	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

}
