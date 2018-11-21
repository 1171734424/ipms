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
@Table(name = "TB_P_SALELOG", schema = "UCR_PMS")
public class SaleLog implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 8672861590450970269L;
	private String logId;
	private String branchId;
	private String debts;
	private String checkId;
	private String roomId;
	private String categoryId;
	private String goodsName;
	private Integer amount;
	private Double price;
	private String payType;
	private Date recordTime;
	private String recordUser;
	private String remark;

	// Constructors

	/** default constructor */
	public SaleLog() {
	}

	/** minimal constructor */
	public SaleLog(String logId, String branchId, String debts, String categoryId, String goodsName, Integer amount,
			Double price, String payType, String recordUser) {
		this.logId = logId;
		this.branchId = branchId;
		this.debts = debts;
		this.categoryId = categoryId;
		this.goodsName = goodsName;
		this.amount = amount;
		this.price = price;
		this.payType = payType;
		this.recordUser = recordUser;
	}

	/** full constructor */
	public SaleLog(String logId, String branchId, String debts, String checkId, String roomId, String categoryId,
			String goodsName, Integer amount, Double price, String payType, Date recordTime, String recordUser,
			String remark) {
		this.logId = logId;
		this.branchId = branchId;
		this.debts = debts;
		this.checkId = checkId;
		this.roomId = roomId;
		this.categoryId = categoryId;
		this.goodsName = goodsName;
		this.amount = amount;
		this.price = price;
		this.payType = payType;
		this.recordTime = recordTime;
		this.recordUser = recordUser;
		this.remark = remark;
	}

	// Property accessors
	@Id
	@Column(name = "LOG_ID", unique = true, nullable = false, length = 18)
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

	@Column(name = "DEBTS", nullable = false, length = 1)
	public String getDebts() {
		return this.debts;
	}

	public void setDebts(String debts) {
		this.debts = debts;
	}

	@Column(name = "CHECK_ID", length = 17)
	public String getCheckId() {
		return this.checkId;
	}

	public void setCheckId(String checkId) {
		this.checkId = checkId;
	}

	@Column(name = "ROOM_ID", length = 6)
	public String getRoomId() {
		return this.roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	@Column(name = "CATEGORY_ID", nullable = false, length = 8)
	public String getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	@Column(name = "GOODS_NAME", nullable = false, length = 30)
	public String getGoodsName() {
		return this.goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	@Column(name = "AMOUNT", nullable = false, precision = 7, scale = 0)
	public Integer getAmount() {
		return this.amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	@Column(name = "PRICE", nullable = false, precision = 8)
	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Column(name = "PAY_TYPE", nullable = false, length = 1)
	public String getPayType() {
		return this.payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
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

}