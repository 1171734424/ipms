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
@Table(name = "TB_P_SHIFTTIME", schema = "UCR_PMS")
public class ShiftTime implements java.io.Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 6774449802394408987L;
	// Fields
	
	private String shiftTimeId;
	private String branchId;
	private String starttime;
	private String endtime;
	private String orderno;
	private String shiftname;
	private String status;
	private String remark;
    private Date recordTime;
	// Constructors

	
	public ShiftTime() {
		
	}

	public ShiftTime(String shiftTimeId, String branchId, String starttime,
			String endtime, String orderno, String shiftname, String status,
			String remark,Date recordTime) {
		super();
		this.shiftTimeId = shiftTimeId;
		this.branchId = branchId;
		this.starttime = starttime;
		this.endtime = endtime;
		this.orderno = orderno;
		this.shiftname = shiftname;
		this.status = status;
		this.remark = remark;
		this.recordTime = recordTime;
	}
	
	
	@Id
	@Column(name = "SHIFTTIME_ID", unique = true, nullable = false, length = 17)
	public String getShiftTimeId() {
		return shiftTimeId;
	}

	public void setShiftTimeId(String shiftTimeId) {
		this.shiftTimeId = shiftTimeId;
	}
	@Column(name = "BRANCH_ID", nullable = false, length = 6)
	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	@Column(name = "STARTTIME", nullable = false, length = 30)
	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	@Column(name = "ENDTIME", nullable = false, length = 30)
	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	@Column(name = "ORDERNO", length = 8)
	public String getOrderno() {
		return orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}
	@Column(name = "SHIFT_NAME", nullable = false, length = 30)
	public String getShiftname() {
		return shiftname;
	}

	public void setShiftname(String shiftname) {
		this.shiftname = shiftname;
	}
	@Column(name = "STATUS", nullable = false, length = 1)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	@Column(name = "REMARK",  length = 100)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	


	

}