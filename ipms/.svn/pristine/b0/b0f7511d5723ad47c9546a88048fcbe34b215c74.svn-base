package com.ideassoft.bean;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ideassoft.core.annotation.interfaces.ColumnDefaultValue;

/**
 * Supplier entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_C_SUPPLIER", schema = "UCR_PMS")
public class Supplier implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -180059082738954580L;
	private String supplierId;
	private String supplierName;
	private String address;
	private String phone;
	private String postcode;
	private String contacts;
	private String mobile;
	private String recordUser;
	private Date recordTime;
	private String status;
	private String remark;
	private String branchId;

	// Constructors

	/** default constructor */
	public Supplier() {
	}

	/** minimal constructor */
	public Supplier(String supplierId, String supplierName, String address,
			String phone, String postcode, String contacts, String recordUser,
			Date recordTime, String status, String branchId) {
		this.supplierId = supplierId;
		this.supplierName = supplierName;
		this.address = address;
		this.phone = phone;
		this.postcode = postcode;
		this.contacts = contacts;
		this.recordUser = recordUser;
		this.recordTime = recordTime;
		this.status = status;
		this.branchId = branchId;
	}

	/** full constructor */
	public Supplier(String supplierId, String supplierName, String address,
			String phone, String postcode, String contacts, String mobile,
			String recordUser, Date recordTime, String status, String remark,
			String branchId) {
		this.supplierId = supplierId;
		this.supplierName = supplierName;
		this.address = address;
		this.phone = phone;
		this.postcode = postcode;
		this.contacts = contacts;
		this.mobile = mobile;
		this.recordUser = recordUser;
		this.recordTime = recordTime;
		this.status = status;
		this.remark = remark;
		this.branchId = branchId;
	}

	// Property accessors
	@Id
	@Column(name = "SUPPLIER_ID", unique = true, nullable = false, length = 8)
	public String getSupplierId() {
		return this.supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	@Column(name = "SUPPLIER_NAME", nullable = false, length = 30)
	public String getSupplierName() {
		return this.supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	@Column(name = "ADDRESS", nullable = false, length = 60)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "PHONE", nullable = false, length = 13)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "POSTCODE", nullable = false, length = 6)
	public String getPostcode() {
		return this.postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	@Column(name = "CONTACTS", nullable = false, length = 10)
	public String getContacts() {
		return this.contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	@Column(name = "MOBILE", length = 11)
	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "RECORD_USER", nullable = false, length = 8)
	public String getRecordUser() {
		return this.recordUser;
	}

	public void setRecordUser(String recordUser) {
		this.recordUser = recordUser;
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

	@Column(name = "STATUS", nullable = false, length = 1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "REMARK", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "BRANCH_ID", nullable = false, length = 6)
	public String getBranchId() {
		return this.branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

}