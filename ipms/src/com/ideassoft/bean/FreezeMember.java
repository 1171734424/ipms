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
@Table(name = "TB_C_FREEZEMEMBER", schema = "UCR_PMS")
public class FreezeMember implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 873807013422128710L;
	private String openId;// 添加的微信号17.07.07
	private String memberId;
	private String memberName;
	private String loginName;
	private String password;
	private String memberRank;
	private String idcard;
	private String gendor;
	private Date birthday;
	private String mobile;
	private String email;
	private String address;
	private String postcode;
	private String photos;
	private String source;
	private Date validTime;
	private Date invalidTime;
	private Date registerTime;
	private Date recordTime;
	private String status;
	private String remark;
	private String recommend;
	private String appToken;
	private String corporationId;
	private Short discount;
	

	// Constructors

	/** default constructor */
	public FreezeMember() {
	}

	/** minimal constructor */
	public FreezeMember(String memberId, String memberName, String loginName, String password, String memberRank,
			String idcard, String gendor, String mobile, String source, Date validTime, Date invalidTime,
			Date registerTime, String status,String appToken, String corporationId, Short discount) {
		this.memberId = memberId;
		this.memberName = memberName;
		this.loginName = loginName;
		this.password = password;
		this.memberRank = memberRank;
		this.idcard = idcard;
		this.gendor = gendor;
		this.mobile = mobile;
		this.source = source;
		this.validTime = validTime;
		this.invalidTime = invalidTime;
		this.registerTime = registerTime;
		this.status = status;
		this.appToken = appToken;
		this.corporationId = corporationId;
		this.discount = discount;
	}

	/** full constructor */
	public FreezeMember(String memberId, String memberName, String loginName, String password, String memberRank,
			String idcard, String gendor, Date birthday, String mobile, String email, String address, String postcode,
			String photos, String source, Date validTime, Date invalidTime, Date registerTime, Date recordTime,
			String status, String remark,String appToken, String corporationId, Short discount) {
		this.memberId = memberId;
		this.memberName = memberName;
		this.loginName = loginName;
		this.password = password;
		this.memberRank = memberRank;
		this.idcard = idcard;
		this.gendor = gendor;
		this.birthday = birthday;
		this.mobile = mobile;
		this.email = email;
		this.address = address;
		this.postcode = postcode;
		this.photos = photos;
		this.source = source;
		this.validTime = validTime;
		this.invalidTime = invalidTime;
		this.registerTime = registerTime;
		this.recordTime = recordTime;
		this.status = status;
		this.remark = remark;
		this.appToken = appToken;
		this.corporationId = corporationId;
		this.discount = discount;
	}

	// Property accessors
	@Id
	@Column(name = "MEMBER_ID", unique = true, nullable = false, length = 8)
	public String getMemberId() {
		return this.memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	@Column(name = "MEMBER_NAME", nullable = false, length = 20)
	public String getMemberName() {
		return this.memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	@Column(name = "LOGIN_NAME", nullable = false, length = 20)
	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@Column(name = "PASSWORD", nullable = false, length = 20)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "MEMBER_RANK", nullable = false, length = 1)
	public String getMemberRank() {
		return this.memberRank;
	}

	public void setMemberRank(String memberRank) {
		this.memberRank = memberRank;
	}

	@Column(name = "IDCARD", length = 18)
	public String getIdcard() {
		return this.idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	@Column(name = "OPENID", length = 28)
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
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

	@Column(name = "POSTCODE", length = 6)
	public String getPostcode() {
		return this.postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	@Column(name = "PHOTOS", length = 100)
	public String getPhotos() {
		return this.photos;
	}

	public void setPhotos(String photos) {
		this.photos = photos;
	}

	@Column(name = "SOURCE", nullable = false, length = 1)
	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "VALID_TIME", nullable = false, insertable = true)
	public Date getValidTime() {
		return this.validTime;
	}

	public void setValidTime(Date validTime) {
		this.validTime = validTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "INVALID_TIME", nullable = false, insertable = true)
	public Date getInvalidTime() {
		return this.invalidTime;
	}

	public void setInvalidTime(Date invalidTime) {
		this.invalidTime = invalidTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "REGISTER_TIME", nullable = false, insertable = true)
	public Date getRegisterTime() {
		return this.registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
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

	@Column(name = "RECOMMEND", length = 8)
	public String getRecommend() {
		return recommend;
	}

	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}

	@Column(name = "APP_TOKEN", nullable = true,length = 32)
	public String getAppToken() {
		return appToken;
	}

	public void setAppToken(String appToken) {
		this.appToken = appToken;
	}
	
	@Column(name = "CORPORATIONID", nullable = true,length = 40)
	public String getCorporationId() {
		return corporationId;
	}

	public void setCorporationId(String corporationId) {
		this.corporationId = corporationId;
	}

	@Column(name = "DISCOUNT", nullable = true,length = 3)
	public Short getDiscount() {
		return discount;
	}

	public void setDiscount(Short discount) {
		this.discount = discount;
	}
		
}