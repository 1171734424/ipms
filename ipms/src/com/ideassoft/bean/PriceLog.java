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
@Table(name = "TL_P_PRICELOG", schema = "UCR_PMS")
public class PriceLog implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 4458584435611557955L;
	private String dataId;
	private String branchId;
	private String lastPrice;
	private String currPrice;
	private Date recordTime;
	private String recordUser;
	private String roomType;
	private String priceType;
	private String priority;

	// Constructors

	/** default constructor */
	public PriceLog() {
	}

	/** minimal constructor */
	

	/** full constructor */
	public PriceLog(String dataId, String branchId, String lastPrice,
			String currPrice, Date recordTime, String recordUser,
			String roomType, String priceType, String priority) {
		super();
		this.dataId = dataId;
		this.branchId = branchId;
		this.lastPrice = lastPrice;
		this.currPrice = currPrice;
		this.recordTime = recordTime;
		this.recordUser = recordUser;
		this.roomType = roomType;
		this.priceType = priceType;
		this.priority = priority;
	}

	// Property accessors
	@Id
	@Column(name = "DATA_ID", unique = true, nullable = false, length = 15)
	public String getDataId() {
		return dataId;
	}
	
	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	@Column(name = "BRANCH_ID", nullable = false, length = 4)
	public String getBranchId() {
		return this.branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	@Column(name = "LAST_PRICE", nullable = false, length = 14)
	public String getLastPrice() {
		return lastPrice;
	}

	public void setLastPrice(String lastPrice) {
		this.lastPrice = lastPrice;
	}

	@Column(name = "CURR_PRICE", nullable = false, length = 14)
		public String getCurrPrice() {
		return currPrice;
	}

	public void setCurrPrice(String currPrice) {
		this.currPrice = currPrice;
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

	@Column(name = "ROOMTYPE" , length = 8)
	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	
	@Column(name = "PRICE_TYPE" , length = 1)
	public String getPriceType() {
		return priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}
	
	@Column(name = "PRIORITY" , length = 1)
	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

}