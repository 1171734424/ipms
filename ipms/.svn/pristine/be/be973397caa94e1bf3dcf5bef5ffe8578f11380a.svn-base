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
@Table(name = "TB_C_STAFF", schema = "UCR_PMS")
public class Staff implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = -523170608694027761L;
	private String staffId;
	private String staffName;
	private String loginName;
	private String password;
	private String branchId;
	private String post;
	private String idcard;
	private String gendor;
	private Date birthday;
	private String mobile;
	private String email;
	private String address;
	private Date entryTime;
	private String recordUser;
	private Date recordTime;
	private String status;
	private String remark;
	private String departId;
	private String appToken;

	// Constructors

	/** default constructor */
	public Staff() {
	}

	/** minimal constructor */
	public Staff(String staffId, String staffName, String loginName, String password, String branchId, String post,
			String idcard, String gendor, String mobile, Date entryTime, String recordUser, String status, String appToken) {
		this.staffId = staffId;
		this.staffName = staffName;
		this.loginName = loginName;
		this.password = password;
		this.branchId = branchId;
		this.post = post;
		this.idcard = idcard;
		this.gendor = gendor;
		this.mobile = mobile;
		this.entryTime = entryTime;
		this.recordUser = recordUser;
		this.status = status;
		this.appToken = appToken;
	}

	/** full constructor */
	public Staff(String staffId, String staffName, String loginName, String password, String branchId, String post,
			String idcard, String gendor, Date birthday, String mobile, String email, String address, Date entryTime,
			String recordUser, Date recordTime, String status, String remark, String appToken) {
		this.staffId = staffId;
		this.staffName = staffName;
		this.loginName = loginName;
		this.password = password;
		this.branchId = branchId;
		this.post = post;
		this.idcard = idcard;
		this.gendor = gendor;
		this.birthday = birthday;
		this.mobile = mobile;
		this.email = email;
		this.address = address;
		this.entryTime = entryTime;
		this.recordUser = recordUser;
		this.recordTime = recordTime;
		this.status = status;
		this.remark = remark;
		this.appToken = appToken;
	}

	// Property accessors
	@Id
	@Column(name = "STAFF_ID", unique = true, nullable = false, length = 8)
	public String getStaffId() {
		return this.staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	@Column(name = "STAFF_NAME", nullable = false, length = 20)
	public String getStaffName() {
		return this.staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	@Column(name = "LOGIN_NAME", nullable = false, length = 20, unique = true)
	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@Column(name = "PASSWORD", nullable = false, length = 32)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "BRANCH_ID", nullable = false, length = 6)
	public String getBranchId() {
		return this.branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	@Column(name = "POST", nullable = false, length = 4)
	public String getPost() {
		return this.post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	@Column(name = "IDCARD", nullable = false, length = 18)
	public String getIdcard() {
		return this.idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	@Column(name = "GENDOR", nullable = false, length = 1)
	public String getGendor() {
		return this.gendor;
	}

	public void setGendor(String gendor) {
		this.gendor = gendor;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "BIRTHDAY")
	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@Column(name = "MOBILE", nullable = false, length = 11)
	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "EMAIL", length = 40)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "ADDRESS", length = 60)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ENTRY_TIME", nullable = false)
	public Date getEntryTime() {
		return this.entryTime;
	}

	public void setEntryTime(Date entryTime) {
		this.entryTime = entryTime;
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

	@Column(name = "DEPART_ID", length = 4, nullable = true)
	public String getDepartId() {
		return departId;
	}

	public void setDepartId(String departId) {
		this.departId = departId;
	}

	@Column(name = "APP_TOKEN", nullable = true,length = 32)
	public String getAppToken() {
		return appToken;
	}

	public void setAppToken(String appToken) {
		this.appToken = appToken;
	}
}