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
@Table(name = "TB_C_BRANCH", schema = "UCR_PMS")
public class Branch implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 7348873896785588971L;
	private String branchId;
	private String branchName;
	private String branchType;
	private String address;
	private String phone;
	private String postcode;
	private String contacts;
	private String mobile;
	private String recordUser;
	private Date recordTime;
	private String status;
	private String remark;
	private String city;
	private String district;
	private String street;
	private String circle;
	private String rank;
	private String flag;
	private String branchIp;
	private String isRecommend;
	private String specialLabel;
	private String specialDescription;
	private String orderNo;
	// Constructors
	
	/** default constructor */
	public Branch() {
	}

	/** minimal constructor */
	public Branch(String branchId, String branchName, String branchType, String address, String phone, String postcode,
			String contacts, String mobile, String recordUser, String status, String city, String district, String rank, 
			String branchIp,String 	isRecommend) {
		this.branchId = branchId;
		this.branchName = branchName;
		this.branchType = branchType;
		this.address = address;
		this.phone = phone;
		this.postcode = postcode;
		this.contacts = contacts;
		this.mobile = mobile;
		this.recordUser = recordUser;
		this.status = status;
		this.city = city;
		this.district = district;
		this.rank = rank;
		this.branchIp = branchIp;
		this.isRecommend = isRecommend;
	}

	/** full constructor */
	public Branch(String branchId, String branchName, String branchType, String address, String phone, String postcode,
			String contacts, String mobile, String recordUser, Date recordTime, String status, String remark,
			String city, String district, String street, String circle, String rank, String flag, String branchIp,String isRecommend) {
		this.branchId = branchId;
		this.branchName = branchName;
		this.branchType = branchType;
		this.address = address;
		this.phone = phone;
		this.postcode = postcode;
		this.contacts = contacts;
		this.mobile = mobile;
		this.recordUser = recordUser;
		this.recordTime = recordTime;
		this.status = status;
		this.remark = remark;
		this.city = city;
		this.district = district;
		this.street = street;
		this.circle = circle;
		this.rank = rank;
		this.flag = flag;
		this.branchIp = branchIp;
		this.isRecommend = isRecommend;
	}

	// Property accessors
	@Id
	@Column(name = "BRANCH_ID", unique = true, nullable = false, length = 6)
	public String getBranchId() {
		return this.branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	@Column(name = "BRANCH_NAME", nullable = false, length = 30)
	public String getBranchName() {
		return this.branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	@Column(name = "BRANCH_TYPE", nullable = false, length = 1)
	public String getBranchType() {
		return this.branchType;
	}

	public void setBranchType(String branchType) {
		this.branchType = branchType;
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

	@Column(name = "MOBILE", nullable = false, length = 11)
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

	@Column(name = "CITY", nullable = false, length = 12)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "DISTRICT", nullable = false, length = 12)
	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	@Column(name = "STREET", length = 12)
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	@Column(name = "CIRCLE", length = 2000)
	public String getCircle() {
		return circle;
	}

	public void setCircle(String circle) {
		this.circle = circle;
	}

	@Column(name = "RANK", nullable = false, length = 1)
	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}
	
	@Column(name ="FLAG",length = 1)
	public String getFlag(){
		return flag;
	}
	public void setFlag(String flag){
		this.flag = flag;
	}

	@Column(name ="BRANCH_IP", length = 15, nullable = true)	
	public String getBranchIp() {
		return branchIp;
	}

	public void setBranchIp(String branchIp) {
		this.branchIp = branchIp;
	}
	
	@Column(name ="IS_RECOMMEND", length = 1, nullable = true)	
	public String getIsRecommend() {
		return isRecommend;
	}

	public void setIsRecommend(String isRecommend) {
		this.isRecommend = isRecommend;
	}
	
	@Column(name ="SPECIAL_LABEL",length = 400)
	public String getSpecialLabel() {
		return specialLabel;
	}

	public void setSpecialLabel(String specialLabel) {
		this.specialLabel = specialLabel;
	}

	@Column(name ="SPECIAL_DESCRIPTION",length = 400)
	public String getSpecialDescription() {
		return specialDescription;
	}

	public void setSpecialDescription(String specialDescription) {
		this.specialDescription = specialDescription;
	}

	@Column(name ="ORDER_NO",length = 8)
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
}