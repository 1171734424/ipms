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
@Table(name = "TB_C_STOCK", schema = "UCR_PMS")
public class Stock implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = -7933871695822755151L;
	private String stockId;
	private String branchId;
	private String goodsId;
	private Integer stockAmount;
	private Date recordTime;
	private String status;

	// Constructors

	/** default constructor */
	public Stock() {
	}

	/** minimal constructor */
	public Stock(String stockId, String branchId, String goodsId, Integer stockAmount, String status) {
		this.stockId = stockId;
		this.branchId = branchId;
		this.goodsId = goodsId;
		this.stockAmount = stockAmount;
		this.status = status;
	}

	/** full constructor */
	public Stock(String stockId, String branchId, String goodsId, Integer stockAmount, Date recordTime, String status) {
		this.stockId = stockId;
		this.branchId = branchId;
		this.goodsId = goodsId;
		this.stockAmount = stockAmount;
		this.recordTime = recordTime;
		this.status = status;
	}

	// Property accessors
	@Id
	@Column(name = "STOCK_ID", unique = true, nullable = false, length = 14)
	public String getStockId() {
		return this.stockId;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
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

	@Column(name = "STOCK_AMOUNT", nullable = false, precision = 8, scale = 0)
	public Integer getStockAmount() {
		return this.stockAmount;
	}

	public void setStockAmount(Integer stockAmount) {
		this.stockAmount = stockAmount;
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

}