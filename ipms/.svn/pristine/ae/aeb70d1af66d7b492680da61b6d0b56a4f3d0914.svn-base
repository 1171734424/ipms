package com.ideassoft.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ideassoft.core.annotation.interfaces.ColumnDefaultValue;

@Entity
@Table(name = "TL_P_RECHECKIN", schema = "UCR_PMS")
public class RecheckIn implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1899992490333503097L;
	
	private String recheckinId;
	private String branchId;
	private String checkId;
	private Date recheckinDate;
	private Date recheckoutDate;
	private Date recordTime;
	private String recordUser;
	private String remark;
	
	// Constructors
	
	/** default constructor */
	public RecheckIn(){
	}
	
	/** mini constructor */
	public RecheckIn(String recheckinId, String branchId, String checkId,
			Date recheckinDate, Date recheckoutDate, String recordUser,
			String remark) {
		super();
		this.recheckinId = recheckinId;
		this.branchId = branchId;
		this.checkId = checkId;
		this.recheckinDate = recheckinDate;
		this.recheckoutDate = recheckoutDate;
		this.recordUser = recordUser;
		this.remark = remark;
	}

	// Property accessors
	@Id
	@Column(name = "RECHECKIN_ID", unique = true, nullable = false, length = 19)
	public String getRecheckinId() {
		return recheckinId;
	}

	public void setRecheckinId(String recheckinId) {
		this.recheckinId = recheckinId;
	}

	@Column(name = "BRANCH_ID", nullable = false, length = 6)
	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	@Column(name = "CHECK_ID", nullable = false, length = 16)
	public String getCheckId() {
		return checkId;
	}

	public void setCheckId(String checkId) {
		this.checkId = checkId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RECHECIN_DATE",insertable = false, length = 7)
	public Date getRecheckinDate() {
		return recheckinDate;
	}

	public void setRecheckinDate(Date recheckinDate) {
		this.recheckinDate = recheckinDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RECHECKOUT_DATE",insertable = true, length = 7)
	public Date getRecheckoutDate() {
		return recheckoutDate;
	}

	public void setRecheckoutDate(Date recheckoutDate) {
		this.recheckoutDate = recheckoutDate;
	}

	@Column(name = "RECORD_USER", nullable = false, length = 8)
	public String getRecordUser() {
		return recordUser;
	}

	public void setRecordUser(String recordUser) {
		this.recordUser = recordUser;
	}

	@Column(name = "REMARK", nullable = true, length = 20)
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
