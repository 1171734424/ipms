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
@Table(name = "TB_P_PETTYCASH", schema = "UCR_PMS")
public class PettyCash implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = -1424330126734454588L;
	private String logId;
	private String branchId;
	private String shift;
	private String cashBox;
	private String handUser;
	private String confirmUser;
	private Double cashIn;
	private Double cashOut;
	private Double paymentValue;
	private Double shiftValue;
	private Double lastShiftvalue;
	private Double currShiftvalue;
	private Double cardBalance;
	private String cards;
	private String depositNo;
	private String invoiceNo;
	private String status;
	private Date recordTime;
	private String recordUser;
	private String remark;
	private String voucher;
	private String state;
	// Constructors

	/** default constructor */
	public PettyCash() {
	}

	/** minimal constructor */
	public PettyCash(String logId, String branchId, String shift, String cashBox, String handUser, String confirmUser,
			String recordUser) {
		this.logId = logId;
		this.branchId = branchId;
		this.shift = shift;
		this.cashBox = cashBox;
		this.handUser = handUser;
		this.confirmUser = confirmUser;
		this.recordUser = recordUser;
	}

	/** full constructor */
	public PettyCash(String logId, String branchId, String shift, String cashBox, String handUser, String confirmUser,
			Double cashIn, Double cashOut, Double paymentValue, Double shiftValue, Double lastShiftvalue,
			Double currShiftvalue, Double cardBalance, String cards, String depositNo, String invoiceNo, String status,
			Date recordTime, String recordUser, String remark,String voucher,String state) {
		this.logId = logId;
		this.branchId = branchId;
		this.shift = shift;
		this.cashBox = cashBox;
		this.handUser = handUser;
		this.confirmUser = confirmUser;
		this.cashIn = cashIn;
		this.cashOut = cashOut;
		this.paymentValue = paymentValue;
		this.shiftValue = shiftValue;
		this.lastShiftvalue = lastShiftvalue;
		this.currShiftvalue = currShiftvalue;
		this.cardBalance = cardBalance;
		this.cards = cards;
		this.depositNo = depositNo;
		this.invoiceNo = invoiceNo;
		this.status = status;
		this.recordTime = recordTime;
		this.recordUser = recordUser;
		this.remark = remark;
		this.voucher = voucher;
		this.state = state;
	}

	// Property accessors
	@Id
	@Column(name = "LOG_ID", unique = true, nullable = false, length = 16)
	public String getLogId() {
		return this.logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	@Column(name = "BRANCH_ID", nullable = false, length = 6)
	public String getBranchId() {
		return this.branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	@Column(name = "SHIFT", nullable = false, length = 17)
	public String getShift() {
		return this.shift;
	}

	public void setShift(String shift) {
		this.shift = shift;
	}

	@Column(name = "CASH_BOX", length = 4)
	public String getCashBox() {
		return this.cashBox;
	}

	public void setCashBox(String cashBox) {
		this.cashBox = cashBox;
	}

	@Column(name = "HAND_USER", nullable = false, length = 8)
	public String getHandUser() {
		return this.handUser;
	}

	public void setHandUser(String handUser) {
		this.handUser = handUser;
	}

	@Column(name = "CONFIRM_USER", nullable = false, length = 8)
	public String getConfirmUser() {
		return this.confirmUser;
	}

	public void setConfirmUser(String confirmUser) {
		this.confirmUser = confirmUser;
	}

	@Column(name = "CASH_IN", precision = 9)
	public Double getCashIn() {
		return this.cashIn;
	}

	public void setCashIn(Double cashIn) {
		this.cashIn = cashIn;
	}

	@Column(name = "CASH_OUT", precision = 9)
	public Double getCashOut() {
		return this.cashOut;
	}

	public void setCashOut(Double cashOut) {
		this.cashOut = cashOut;
	}

	@Column(name = "PAYMENT_VALUE", precision = 9)
	public Double getPaymentValue() {
		return this.paymentValue;
	}

	public void setPaymentValue(Double paymentValue) {
		this.paymentValue = paymentValue;
	}

	@Column(name = "SHIFT_VALUE", precision = 9)
	public Double getShiftValue() {
		return this.shiftValue;
	}

	public void setShiftValue(Double shiftValue) {
		this.shiftValue = shiftValue;
	}

	@Column(name = "LAST_SHIFTVALUE", precision = 9)
	public Double getLastShiftvalue() {
		return this.lastShiftvalue;
	}

	public void setLastShiftvalue(Double lastShiftvalue) {
		this.lastShiftvalue = lastShiftvalue;
	}

	@Column(name = "CURR_SHIFTVALUE", precision = 9)
	public Double getCurrShiftvalue() {
		return this.currShiftvalue;
	}

	public void setCurrShiftvalue(Double currShiftvalue) {
		this.currShiftvalue = currShiftvalue;
	}

	@Column(name = "CARD_BALANCE", precision = 9)
	public Double getCardBalance() {
		return this.cardBalance;
	}

	public void setCardBalance(Double cardBalance) {
		this.cardBalance = cardBalance;
	}

	@Column(name = "CARDS", length = 2)
	public String getCards() {
		return this.cards;
	}

	public void setCards(String cards) {
		this.cards = cards;
	}

	@Column(name = "DEPOSIT_NO", length = 28)
	public String getDepositNo() {
		return this.depositNo;
	}

	public void setDepositNo(String depositNo) {
		this.depositNo = depositNo;
	}

	@Column(name = "INVOICE_NO", length = 28)
	public String getInvoiceNo() {
		return this.invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	@Column(name = "STATUS", length = 2)
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
	
	@Column(name = "VOUCHER", length = 50)
	public String getVoucher() {
		return voucher;
	}

	public void setVoucher(String voucher) {
		this.voucher = voucher;
	}
	
	@Column(name = "STATE", length = 1)
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

}