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
@Table(name = "TB_C_GOODS", schema = "UCR_PMS")
public class Goods implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = -2770026987489397299L;
	private String goodsId;
	private String goodsName;
	private String categoryId;
	private String branchId;
	private String supplierId;
	private Double price;
	private Integer integral;
	private String specifications;
	private String unit;
	private Date productionDate;
	private String saleType;
	private String recordUser;
	private Date recordTime;
	private String status;
	private String remark;
	private String picture;

	// Constructors

	/** default constructor */
	public Goods() {
	}

	/** minimal constructor */
	public Goods(String goodsId, String goodsName, String categoryId, String branchId, String supplierId, Double price,
			String specifications, String unit, String saleType, String recordUser, String status) {
		this.goodsId = goodsId;
		this.goodsName = goodsName;
		this.categoryId = categoryId;
		this.branchId = branchId;
		this.supplierId = supplierId;
		this.price = price;
		this.specifications = specifications;
		this.unit = unit;
		this.saleType = saleType;
		this.recordUser = recordUser;
		this.status = status;
	}

	/** full constructor */
	public Goods(String goodsId, String goodsName, String categoryId, String branchId, String supplierId, Double price,
			Integer integral, String specifications, String unit, Date productionDate, String saleType,
			String recordUser, Date recordTime, String status, String remark) {
		this.goodsId = goodsId;
		this.goodsName = goodsName;
		this.categoryId = categoryId;
		this.branchId = branchId;
		this.supplierId = supplierId;
		this.price = price;
		this.integral = integral;
		this.specifications = specifications;
		this.unit = unit;
		this.productionDate = productionDate;
		this.saleType = saleType;
		this.recordUser = recordUser;
		this.recordTime = recordTime;
		this.status = status;
		this.remark = remark;
	}

	// Property accessors
	@Id
	@Column(name = "GOODS_ID", unique = true, nullable = false, length = 8)
	public String getGoodsId() {
		return this.goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	@Column(name = "GOODS_NAME", nullable = false, length = 30)
	public String getGoodsName() {
		return this.goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	@Column(name = "CATEGORY_ID", nullable = false, length = 8)
	public String getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	@Column(name = "BRANCH_ID", nullable = false, length = 6)
	public String getBranchId() {
		return this.branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	@Column(name = "SUPPLIER_ID", nullable = false, length = 8)
	public String getSupplierId() {
		return this.supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	@Column(name = "PRICE", nullable = false, precision = 10)
	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Column(name = "INTEGRAL", precision = 6, scale = 0)
	public Integer getIntegral() {
		return this.integral;
	}

	public void setIntegral(Integer integral) {
		this.integral = integral;
	}

	@Column(name = "SPECIFICATIONS", nullable = false, length = 4)
	public String getSpecifications() {
		return this.specifications;
	}

	public void setSpecifications(String specifications) {
		this.specifications = specifications;
	}

	@Column(name = "UNIT", nullable = false, length = 4)
	public String getUnit() {
		return this.unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "PRODUCTION_DATE", length = 7)
	public Date getProductionDate() {
		return this.productionDate;
	}

	public void setProductionDate(Date productionDate) {
		this.productionDate = productionDate;
	}

	@Column(name = "SALE_TYPE", nullable = false, length = 1)
	public String getSaleType() {
		return this.saleType;
	}

	public void setSaleType(String saleType) {
		this.saleType = saleType;
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
	
	@Column(name = "PICTURES", length = 1000)
	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	
}