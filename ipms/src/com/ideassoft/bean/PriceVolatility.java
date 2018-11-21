package com.ideassoft.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@Table(name = "TB_P_PRICE_VOLATILITY", schema = "UCR_PMS")
public class PriceVolatility implements java.io.Serializable{
	
	
	private static final long serialVersionUID = 6601692883384259228L;
	private String volatilityId;
	private String branchId;
	private String theme;
	private String roomType;
	private String rpId;
	private Double roomPrice;
	private String priceType;
	private String priceTypeDetail;
	private Date startTime;
	private Date endTime;
	private String priority;
	private String rulesId;
	private Date recordTime;
	
	
	// Constructors
	/** default constructor */
	public PriceVolatility() {
		
	}
	
	/** full constructor */
	public PriceVolatility(String volatilityId,String branchId, String theme, String roomType,
			String rpId, Double roomPrice, String priceType,
			String priceTypeDetail, Date startTime, Date endTime,
			String priority, String rulesId, Date recordTime) {
		this.volatilityId = volatilityId;
		this.branchId = branchId;
		this.theme = theme;
		this.roomType = roomType;
		this.rpId = rpId;
		this.roomPrice = roomPrice;
		this.priceType = priceType;
		this.priceTypeDetail = priceTypeDetail;
		this.startTime = startTime;
		this.endTime = endTime;
		this.priority = priority;
		this.rulesId = rulesId;
		this.recordTime = recordTime;
	}
	@Id
	@Column(name = "VOLATILITY_ID", nullable = false, length = 8)
	public  String getVolatilityId() {
		return volatilityId;
	}
	public  void setVolatilityId(String volatilityId) {
		this.volatilityId = volatilityId;
	}
	
	@Column(name = "BRANCH_ID", nullable = false, length = 8)
	public  String getBranchId() {
		return branchId;
	}
	public  void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	
	@Column(name = "THEME", nullable = false, length = 1)
	public  String getTheme() {
		return theme;
	}
	public  void setTheme(String theme) {
		this.theme = theme;
	}
	
	@Column(name = "ROOM_TYPE", length = 3)
	public  String getRoomType() {
		return roomType;
	}
	public  void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	
	@Column(name = "RP_ID", length = 3)
	public  String getRpId() {
		return rpId;
	}
	
	public  void setRpId(String rpId) {
		this.rpId = rpId;
	}
	
	@Column(name = "ROOM_PRICE", nullable = false, length = 9)
	public  Double getRoomPrice() {
		return roomPrice;
	}
	public  void setRoomPrice(Double roomPrice) {
		this.roomPrice = roomPrice;
	}
	
	@Column(name = "PRICE_TYPE", nullable = false, length = 1)
	public  String getPriceType() {
		return priceType;
	}
	public  void setPriceType(String priceType) {
		this.priceType = priceType;
	}
	
	@Column(name = "PRICETYPE_DETAIL",  length = 50)
	public  String getPriceTypeDetail() {
		return priceTypeDetail;
	}
	public  void setPriceTypeDetail(String priceTypeDetail) {
		this.priceTypeDetail = priceTypeDetail;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "START_TIME", nullable = false, length = 6)
	public  Date getStartTime() {
		return startTime;
	}
	public  void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "END_TIME", nullable = false, length = 6)
	public  Date getEndTime() {
		return endTime;
	}
	public  void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	@Column(name = "PRIORITY", nullable = false, length = 6)
	public  String getPriority() {
		return priority;
	}
	public  void setPriority(String priority) {
		this.priority = priority;
	}
	
	@Column(name = "RULES_ID", nullable = false, length = 6)
	public  String getRulesId() {
		return rulesId;
	}
	public  void setRulesId(String rulesId) {
		this.rulesId = rulesId;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RECORDTIME",  length = 7)
	public  Date getRecordTime() {
		return recordTime;
	}
	public  void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}
	
}
