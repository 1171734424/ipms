package com.ideassoft.bean;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ideassoft.core.annotation.interfaces.ColumnDefaultValue;

@Entity
@Table(name = "TB_C_PURCHASEDETAIL", schema = "UCR_PMS")
public class PurchaseDetail implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = -5653499743245118711L;
	private PurchaseDetailId purchaseDetailId;
	private String goodsId;
	private Double purchasePrice;
	private Short amount;
	private String recordUser;
	private Date recordTime;
	private String status;
	private String remark;

	// Constructors

	/** default constructor */
	public PurchaseDetail() {
	}

	/** minimal constructor */
	public PurchaseDetail(PurchaseDetailId purchaseDetailId, String goodsId, Double purchasePrice, Short amount,
			String recordUser, String status) {
		this.purchaseDetailId = purchaseDetailId;
		this.goodsId = goodsId;
		this.purchasePrice = purchasePrice;
		this.amount = amount;
		this.recordUser = recordUser;
		this.status = status;
	}

	/** full constructor */
	public PurchaseDetail(PurchaseDetailId purchaseDetailId, String goodsId, Double purchasePrice, Short amount,
			String recordUser, Date recordTime, String status, String remark) {
		this.purchaseDetailId = purchaseDetailId;
		this.goodsId = goodsId;
		this.purchasePrice = purchasePrice;
		this.amount = amount;
		this.recordUser = recordUser;
		this.recordTime = recordTime;
		this.status = status;
		this.remark = remark;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "purchaseId", column = @Column(name = "PURCHASE_ID", nullable = false, length = 12)),
			@AttributeOverride(name = "dataId", column = @Column(name = "DATA_ID", nullable = false, length = 2)) })
	public PurchaseDetailId getPurchaseDetailId() {
		return this.purchaseDetailId;
	}

	public void setPurchaseDetailId(PurchaseDetailId purchaseDetailId) {
		this.purchaseDetailId = purchaseDetailId;
	}

	@Column(name = "GOODS_ID", nullable = false, length = 8)
	public String getGoodsId() {
		return this.goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	@Column(name = "PURCHASE_PRICE", nullable = false, precision = 10)
	public Double getPurchasePrice() {
		return this.purchasePrice;
	}

	public void setPurchasePrice(Double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	@Column(name = "AMOUNT", nullable = false, precision = 4, scale = 0)
	public Short getAmount() {
		return this.amount;
	}

	public void setAmount(Short amount) {
		this.amount = amount;
	}

	@Column(name = "RECORD_USER", nullable = false, length = 8)
	public String getRecordUser() {
		return this.recordUser;
	}

	public void setRecordUser(String recordUser) {
		this.recordUser = recordUser;
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

}