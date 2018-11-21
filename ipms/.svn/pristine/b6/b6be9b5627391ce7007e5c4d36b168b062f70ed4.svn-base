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
@Table(name = "TB_P_HOUSE", schema = "UCR_PMS")
public class House implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 5555693991233071064L;
	private String houseId;
	private String houseNo;
	private String houseType;
	private Short area;
	private String floor;
	private Byte beds;
	private String bedDesc;
	private String broadband;
	private String label;
	private String houseDesc;
	private String tips;
	private String position;
	private String address;
	private String status;
	private Date recordTime;
	private String recordUser;
	private String remark;
	private String staffId;
	private String city;
	private String district;
	private String street;
	private String circle;
	private String housename;
	private Double initprice;
	private Double currentprice;
	private Double cleanprice;
	private String flag;
	private String services;
	private String isRecommend;
	private String specialLabel;
	private String specialDescription;
	private String orderNo;
	private String school;
	private String scenic;
	private String subway;
	private Double cashPledge;
	private String decStyle;
	private String communityName;

	// Constructors

	/** default constructor */
	public House() {
	}

	/** minimal constructor */
	public House(String houseId, String houseNo, String houseType, String floor, Byte beds, String bedDesc,
			String broadband, String recordUser,String isRecommend) {
		this.houseId = houseId;
		this.houseNo = houseNo;
		this.houseType = houseType;
		this.floor = floor;
		this.beds = beds;
		this.bedDesc = bedDesc;
		this.broadband = broadband;
		this.recordUser = recordUser;
		this.isRecommend = isRecommend;
	}

	/** full constructor */
	public House(String houseId, String houseNo, String houseType, Short area,
			String floor, Byte beds, String bedDesc, String broadband,
			String label, String houseDesc, String tips, String position,
			String address, String status, Date recordTime, String recordUser,
			String remark, String staffId, String city, String district,
			String street, String circle, String housename, Double initprice,
			Double currentprice, Double cleanprice,String flag, String services,
			String isRecommend, String school, String scenic, String subway, Double cashPledge,
			String decStyle, String communityName) {
		super();
		this.houseId = houseId;
		this.houseNo = houseNo;
		this.houseType = houseType;
		this.area = area;
		this.floor = floor;
		this.beds = beds;
		this.bedDesc = bedDesc;
		this.broadband = broadband;
		this.label = label;
		this.houseDesc = houseDesc;
		this.tips = tips;
		this.position = position;
		this.address = address;
		this.status = status;
		this.recordTime = recordTime;
		this.recordUser = recordUser;
		this.remark = remark;
		this.staffId = staffId;
		this.city = city;
		this.district = district;
		this.street = street;
		this.circle = circle;
		this.housename = housename;
		this.initprice = initprice;
		this.currentprice = currentprice;
		this.cleanprice = cleanprice;
		this.flag = flag;
		this.services = services;
		this.isRecommend = isRecommend;
		this.school = school;
		this.scenic = scenic;
		this.subway = subway;
		this.cashPledge = cashPledge;
		this.decStyle = decStyle;
		this.communityName = communityName;
	}


	// Property accessors
	@Id
	@Column(name = "HOUSE_ID", unique = true, nullable = false, length = 6)
	public String getHouseId() {
		return this.houseId;
	}


	public void setHouseId(String houseId) {
		this.houseId = houseId;
	}

	@Column(name = "HOUSE_NO", nullable = false, length = 7)
	public String getHouseNo() {
		return this.houseNo;
	}

	public void setHouseNo(String houseNo) {
		this.houseNo = houseNo;
	}

	@Column(name = "HOUSE_TYPE", nullable = true, length = 16)
	public String getHouseType() {
		return this.houseType;
	}

	public void setHouseType(String houseType) {
		this.houseType = houseType;
	}

	@Column(name = "AREA", precision = 4, scale = 0)
	public Short getArea() {
		return this.area;
	}

	public void setArea(Short area) {
		this.area = area;
	}

	@Column(name = "FLOOR", nullable = false, length = 2)
	public String getFloor() {
		return this.floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	@Column(name = "BEDS", nullable = false, precision = 2, scale = 0)
	public Byte getBeds() {
		return this.beds;
	}

	public void setBeds(Byte beds) {
		this.beds = beds;
	}

	@Column(name = "BED_DESC", nullable = false, length = 16)
	public String getBedDesc() {
		return this.bedDesc;
	}

	public void setBedDesc(String bedDesc) {
		this.bedDesc = bedDesc;
	}

	@Column(name = "BROADBAND", nullable = false, length = 1)
	public String getBroadband() {
		return this.broadband;
	}

	public void setBroadband(String broadband) {
		this.broadband = broadband;
	}

	@Column(name = "LABEL", length = 40)
	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Column(name = "HOUSE_DESC", length = 2000)
	public String getHouseDesc() {
		return this.houseDesc;
	}

	public void setHouseDesc(String houseDesc) {
		this.houseDesc = houseDesc;
	}

	@Column(name = "TIPS", length = 40)
	public String getTips() {
		return this.tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}

	@Column(name = "POSITION", nullable = false,length = 1)
	public String getPosition() {
		return this.position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	@Column(name = "ADDRESS", nullable = false,length = 60)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "STATUS", nullable = false,length = 2)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	@Column(name = "RECORD_USER", nullable = false, length = 8)
	public String getRecordUser() {
		return this.recordUser;
	}

	public void setRecordUser(String recordUser) {
		this.recordUser = recordUser;
	}

	@Column(name = "REMARK", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "STAFF_ID", nullable = false, length = 8)
	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
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
	@Column(name = "STREET",  length = 12)
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
	@Column(name = "HOUSENAME", nullable = false, length = 30)
	public String getHousename() {
		return housename;
	}

	public void setHousename(String housename) {
		this.housename = housename;
	}
	@Column(name = "INIT_PRICE", precision = 10)
	public Double getInitprice() {
		return initprice;
	}

	public void setInitprice(Double initprice) {
		this.initprice = initprice;
	}
	@Column(name = "CURRENT_PRICE", nullable = true,precision = 10)
	public Double getCurrentprice() {
		return currentprice;
	}

	public void setCurrentprice(Double currentprice) {
		this.currentprice = currentprice;
	}
	@Column(name = "CLEAN_PRICE", nullable = false,precision = 10)
	public Double getCleanprice() {
		return cleanprice;
	}

	public void setCleanprice(Double cleanprice) {
		this.cleanprice = cleanprice;
	}
	
	@Column(name = "FLAG", length = 1)
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	@Column(name = "SERVICES", length = 800)
	public String getServices() {
		return services;
	}
	
	public void setServices(String services) {
		this.services = services;
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
	
	@Column(name ="SCHOOL",length = 1000)
	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	@Column(name ="SCENIC",length = 1000)
	public String getScenic() {
		return scenic;
	}

	public void setScenic(String scenic) {
		this.scenic = scenic;
	}

	@Column(name ="SUBWAY",length = 1000)
	public String getSubway() {
		return subway;
	}

	public void setSubway(String subway) {
		this.subway = subway;
	}

	@Column(name = "CASHPLEDGE", precision = 10)
	public Double getCashPledge() {
		return cashPledge;
	}

	public void setCashPledge(Double cashPledge) {
		this.cashPledge = cashPledge;
	}

	@Column(name ="DEC_STYLE",length = 20)
	public String getDecStyle() {
		return decStyle;
	}

	public void setDecStyle(String decStyle) {
		this.decStyle = decStyle;
	}

	@Column(name ="COMMUNITY_NAME",length = 20)
	public String getCommunityName() {
		return communityName;
	}

	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}

}