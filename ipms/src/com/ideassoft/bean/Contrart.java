package com.ideassoft.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.ideassoft.core.annotation.interfaces.ColumnDefaultValue;

@Entity
@Table(name = "TB_P_CONTRART", schema = "UCR_PMS")
public class Contrart implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 107475977691584884L;

	private String contrartId;
	private String contrart;
	private String memberId;
	private String roomId;
	private String roomType;
	private String mobile;
	private String branchId;
	private Date recordTime;
	private Date startTime;
	private Date endTime;
	private Date contrartEndTime;
	private String status;
	private String typeOfPayment;
	private String unitPrice;
	private String rentTime;
	private Double cash;
	private Double paymoney;
	private String autoRefund;
	private Date checkOutTime;
	private Date aptRenewTime;
	private String payment;
	
	
	
	public Contrart(String contrartId, String contrart, String memberId,
			String roomId, String roomType, String mobile, String branchId,
			Date recordTime, Date startTime, Date endTime,
			Date contrartEndTime, String status, String typeOfPayment,
			String unitPrice, String rentTime, Double cash, Double paymoney,String autoRefund,Date checkOutTime,String payment) {
			this.contrartId = contrartId;
			this.contrart = contrart;
			this.memberId = memberId;
			this.roomId = roomId;
			this.roomType = roomType;
			this.mobile = mobile;
			this.branchId = branchId;
			this.recordTime = recordTime;
			this.startTime = startTime;
			this.endTime = endTime;
			this.contrartEndTime = contrartEndTime;
			this.status = status;
			this.typeOfPayment = typeOfPayment;
			this.unitPrice = unitPrice;
			this.rentTime = rentTime;
			this.cash = cash;
			this.paymoney = paymoney;
			this.autoRefund = autoRefund;
			this.checkOutTime = checkOutTime;
			this.payment = payment;
	}

	public Contrart() {
		
	}




	@Id
	@Column(name = "CONTRART_ID", unique = true, nullable = false, length = 17)
	public String getContrartId() {
		return contrartId;
	}

	public void setContrartId(String contrartId) {
		this.contrartId = contrartId;
	}

	@Column(name = "CONTRART",  length = 20)
	public String getContrart() {
		return contrart;
	}

	public void setContrart(String contrart) {
		this.contrart = contrart;
	}

	@Column(name = "MEMBER_ID", nullable = false, length = 8)
	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	@Column(name = "ROOM_ID", length = 6)
	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	@Column(name = "ROOM_TYPE", length = 3)
	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	@Column(name = "MOBILE", length = 11)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "BRANCH_ID", length = 6)
	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "START_TIME", nullable = false)
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "END_TIME", nullable = false)
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CONTRART_END_TIME", nullable = false)
	public Date getContrartEndTime() {
		return contrartEndTime;
	}

	public void setContrartEndTime(Date contrartEndTime) {
		this.contrartEndTime = contrartEndTime;
	}

	@Column(name = "STATUS", nullable = false, length = 1)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "TYPEOFPAYMENT", nullable = false, length = 1)
	public String getTypeOfPayment() {
		return typeOfPayment;
	}

	public void setTypeOfPayment(String typeOfPayment) {
		this.typeOfPayment = typeOfPayment;
	}

	@Column(name = "UNITPRICE", nullable = false, length = 10)
	public String getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}
	
	@Column(name = "CASH", length = 10)
	public Double getCash() {
		return cash;
	}

	public void setCash(Double cash) {
		this.cash = cash;
	}
	
	
	@Column(name = "PAYMONEY", precision = 10)
	public Double getPaymoney() {
		return paymoney;
	}

	public void setPaymoney(Double paymoney) {
		this.paymoney = paymoney;
	}
	@Column(name = "AUTOREFUND", nullable = false, length = 1)
	public String getAutoRefund() {
		return autoRefund;
	}

	public void setAutoRefund(String autoRefund) {
		this.autoRefund = autoRefund;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CHECK_OUT_TIME")
	public Date getCheckOutTime() {
		return checkOutTime;
	}
	
	public void setCheckOutTime(Date checkOutTime) {
		this.checkOutTime = checkOutTime;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "APTRENEW_TIME")
	public Date getAptRenewTime() {
		return aptRenewTime;
	}

	public void setAptRenewTime(Date aptRenewTime) {
		this.aptRenewTime = aptRenewTime;
	}
	
	@Column(name = "PAYMENT", nullable = false, length = 1)
	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	@Transient
	public String getRentTime() {
		return rentTime;
	}
	
	public void setRentTime(String rentTime) {
		this.rentTime = rentTime;
	}

}