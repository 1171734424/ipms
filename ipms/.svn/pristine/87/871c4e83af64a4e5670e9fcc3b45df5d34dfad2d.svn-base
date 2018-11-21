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
@Table(name = "TB_P_CHECK", schema = "UCR_PMS")
public class Check implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 5259072074106214109L;
	private String checkId;
	private String branchId;
	private String roomId;
	private String roomType;
	private String rpId;
	private Double roomPrice;
	private String checkUser;
	private Date checkinTime;
	private Date checkoutTime;
	private Double deposit;
	private Double ttv;
	private Double cost;
	private Double pay;
	private Double accountFee;
	private Double totalFee;
	private String payType;
	private String payInfo;
	private String payer;
	private String switchId;
	private String status;
	private Date recordTime;
	private String recordUser;
	private String remark;
	private Double msj;

	// Constructors

	/** default constructor */
	public Check() {
	}

	/** minimal constructor */
	public Check(String checkId, String branchId, String roomId, String roomType, String rpId, Double roomPrice,
			String status, Date recordTime, String recordUser, Double msj) {
		this.checkId = checkId;
		this.branchId = branchId;
		this.roomId = roomId;
		this.roomType = roomType;
		this.rpId = rpId;
		this.roomPrice = roomPrice;
		this.status = status;
		this.recordTime = recordTime;
		this.recordUser = recordUser;
		this.msj = msj;
	}

	/** full constructor */
	public Check(String checkId, String branchId, String roomId, String roomType, String rpId, Double roomPrice,
			String checkUser, Date checkinTime, Date checkoutTime, Double deposit, Double ttv, Double cost, Double pay,
			Double accountFee, Double totalFee, String payType, String payInfo, String payer, String switchId,
			String status, Date recordTime, String recordUser, String remark, Double msj) {
		this.checkId = checkId;
		this.branchId = branchId;
		this.roomId = roomId;
		this.roomType = roomType;
		this.rpId = rpId;
		this.roomPrice = roomPrice;
		this.checkUser = checkUser;
		this.checkinTime = checkinTime;
		this.checkoutTime = checkoutTime;
		this.deposit = deposit;
		this.ttv = ttv;
		this.cost = cost;
		this.pay = pay;
		this.accountFee = accountFee;
		this.totalFee = totalFee;
		this.payType = payType;
		this.payInfo = payInfo;
		this.payer = payer;
		this.switchId = switchId;
		this.status = status;
		this.recordTime = recordTime;
		this.recordUser = recordUser;
		this.remark = remark;
		this.msj = msj;
	}

	// Property accessors
	@Id
	@Column(name = "CHECK_ID", unique = true, nullable = false, length = 18)
	public String getCheckId() {
		return this.checkId;
	}

	public void setCheckId(String checkId) {
		this.checkId = checkId;
	}

	@Column(name = "BRANCH_ID", nullable = false, length = 4)
	public String getBranchId() {
		return this.branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	@Column(name = "ROOM_ID", nullable = false, length = 6)
	public String getRoomId() {
		return this.roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	@Column(name = "ROOM_TYPE", nullable = false, length = 2)
	public String getRoomType() {
		return this.roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	@Column(name = "RP_ID", nullable = false, length = 3)
	public String getRpId() {
		return this.rpId;
	}

	public void setRpId(String rpId) {
		this.rpId = rpId;
	}

	@Column(name = "ROOM_PRICE", nullable = false, precision = 9)
	public Double getRoomPrice() {
		return this.roomPrice;
	}

	public void setRoomPrice(Double roomPrice) {
		this.roomPrice = roomPrice;
	}

	@Column(name = "CHECK_USER", length = 60)
	public String getCheckUser() {
		return this.checkUser;
	}

	public void setCheckUser(String checkUser) {
		this.checkUser = checkUser;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CHECKIN_TIME", length = 7)
	public Date getCheckinTime() {
		return this.checkinTime;
	}

	public void setCheckinTime(Date checkinTime) {
		this.checkinTime = checkinTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CHECKOUT_TIME", length = 7)
	public Date getCheckoutTime() {
		return this.checkoutTime;
	}

	public void setCheckoutTime(Date checkoutTime) {
		this.checkoutTime = checkoutTime;
	}

	@Column(name = "DEPOSIT", precision = 9)
	public Double getDeposit() {
		return this.deposit;
	}

	public void setDeposit(Double deposit) {
		this.deposit = deposit;
	}

	@Column(name = "TTV", precision = 9)
	public Double getTtv() {
		return this.ttv;
	}

	public void setTtv(Double ttv) {
		this.ttv = ttv;
	}

	@Column(name = "COST", precision = 9)
	public Double getCost() {
		return this.cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	@Column(name = "PAY", precision = 9)
	public Double getPay() {
		return this.pay;
	}

	public void setPay(Double pay) {
		this.pay = pay;
	}

	@Column(name = "ACCOUNT_FEE", precision = 9)
	public Double getAccountFee() {
		return this.accountFee;
	}

	public void setAccountFee(Double accountFee) {
		this.accountFee = accountFee;
	}

	@Column(name = "TOTAL_FEE", precision = 9)
	public Double getTotalFee() {
		return this.totalFee;
	}

	public void setTotalFee(Double totalFee) {
		this.totalFee = totalFee;
	}

	@Column(name = "PAY_TYPE", length = 1)
	public String getPayType() {
		return this.payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	@Column(name = "PAY_INFO", length = 21)
	public String getPayInfo() {
		return this.payInfo;
	}

	public void setPayInfo(String payInfo) {
		this.payInfo = payInfo;
	}

	@Column(name = "PAYER", length = 8)
	public String getPayer() {
		return this.payer;
	}

	public void setPayer(String payer) {
		this.payer = payer;
	}

	@Column(name = "SWITCH_ID", length = 14)
	public String getSwitchId() {
		return this.switchId;
	}

	public void setSwitchId(String switchId) {
		this.switchId = switchId;
	}

	@Column(name = "STATUS", nullable = false, length = 2)
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

	@Column(name = "MSJ", nullable = false, precision = 9)
	public Double getMsj() {
		return msj;
	}

	public void setMsj(Double msj) {
		this.msj = msj;
	}
}