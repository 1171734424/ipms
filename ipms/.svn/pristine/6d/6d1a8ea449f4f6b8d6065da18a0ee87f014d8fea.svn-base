package com.ideassoft.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "TL_C_STORAGELOG", schema = "UCR_PMS")
public class StorageLog implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = -6263029412436365444L;
	private String logId;
	private String branchId;
	private String goodsId;
	private Integer amount;
	private Double price;
	private Double purchasePrice;
	private Double stockPrice;
	private String recordUser;
	private Date recordTime;
	private String status;
	private String remark;

	// Constructors

	/** default constructor */
	public StorageLog() {
	}

	/** minimal constructor */
	public StorageLog(String logId, String branchId, String goodsId, Integer amount, Double price,
			Double purchasePrice, Double stockPrice, String recordUser, String status) {
		this.logId = logId;
		this.branchId = branchId;
		this.goodsId = goodsId;
		this.amount = amount;
		this.price = price;
		this.purchasePrice = purchasePrice;
		this.stockPrice = stockPrice;
		this.recordUser = recordUser;
		this.status = status;
	}

	/** full constructor */
	public StorageLog(String logId, String branchId, String goodsId, Integer amount, Double price,
			Double purchasePrice, Double stockPrice, String recordUser, Date recordTime, String status, String remark) {
		this.logId = logId;
		this.branchId = branchId;
		this.goodsId = goodsId;
		this.amount = amount;
		this.price = price;
		this.purchasePrice = purchasePrice;
		this.stockPrice = stockPrice;
		this.recordUser = recordUser;
		this.recordTime = recordTime;
		this.status = status;
		this.remark = remark;
	}

	// Property accessors
	@Id
	@Column(name = "LOG_ID", unique = true, nullable = false, length = 8)
	public String getLogId() {
		return this.logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	@Column(name = "BRANCH_ID", nullable = false, length = 4)
	public String getBranchId() {
		return this.branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	@Column(name = "GOODS_ID", nullable = false, length = 8)
	public String getGoodsId() {
		return this.goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	@Column(name = "AMOUNT", nullable = false, precision = 8, scale = 0)
	public Integer getAmount() {
		return this.amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	@Column(name = "PRICE", nullable = false, precision = 10)
	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Column(name = "PURCHASE_PRICE", nullable = false, precision = 10)
	public Double getPurchasePrice() {
		return this.purchasePrice;
	}

	public void setPurchasePrice(Double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	@Column(name = "STOCK_PRICE", nullable = false, precision = 10)
	public Double getStockPrice() {
		return this.stockPrice;
	}

	public void setStockPrice(Double stockPrice) {
		this.stockPrice = stockPrice;
	}

	@Column(name = "RECORD_USER", nullable = false, length = 8)
	public String getRecordUser() {
		return this.recordUser;
	}

	public void setRecordUser(String recordUser) {
		this.recordUser = recordUser;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RECORD_TIME")
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

}