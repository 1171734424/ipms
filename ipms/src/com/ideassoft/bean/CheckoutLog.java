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
@Table(name = "TL_P_CHECKOUT", schema = "UCR_PMS")
public class CheckoutLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1899992490333503097L;
	
	private String checkoutId;
	private String branchId;
	private String checkId;
	private Date checkoutDate;
	private Date recordTime;
	private String recordUser;
	private String remark;
	private String type;
	// Constructors
	
	/** default constructor */
	public CheckoutLog(){
	}
	
	/** mini constructor */
	public CheckoutLog(String checkoutId, String branchId, String checkId,
			Date checkoutDate, String recordUser,String type,
			String remark) {
		super();
		this.checkoutId = checkoutId;
		this.branchId = branchId;
		this.checkId = checkId;
		this.checkoutDate = checkoutDate;
		this.recordUser = recordUser;
		this.type = type;
		this.remark = remark;
	}

	// Property accessors
	@Id
	@Column(name = "CHECKOUT_ID", unique = true, nullable = false, length = 19)
	public String getCheckoutId() {
		return checkoutId;
	}

	public void setCheckoutId(String checkoutId) {
		this.checkoutId = checkoutId;
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
	@Column(name = "CHECKOUT_DATE",insertable = false, length = 7)
	public Date getCheckoutDate() {
		return checkoutDate;
	}

	public void setCheckoutDate(Date checkoutDate) {
		this.checkoutDate = checkoutDate;
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
	
	@Column(name = "TYPE", nullable = false, length = 1)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
