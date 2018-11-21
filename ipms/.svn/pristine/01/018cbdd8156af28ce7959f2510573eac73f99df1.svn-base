package com.ideassoft.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ideassoft.core.annotation.interfaces.ColumnDefaultValue;

/**
 * TbCCouponGroup entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_C_COUPON_GROUP", schema = "UCR_PMS")
public class CouponGroup implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 3752561973522149178L;
	private String dataId;
	private String couponName;
	private Double totalPrice;
	private String tenCoupon;
	private String twentyCoupon;
	private String thirtyCoupon;
	private String fiftyCoupon;
	private String hundredCoupon;
	private String recordUser;
	private Date recordTime;
	private String status;
	private String remark;

	// Constructors

	/** default constructor */
	public CouponGroup() {
	}

	/** minimal constructor */
	public CouponGroup(String dataId) {
		this.dataId = dataId;
	}

	/** full constructor */
	public CouponGroup(String dataId, String couponName, Double totalPrice, String tenCoupon, String twentyCoupon,
			String thirtyCoupon, String fiftyCoupon, String hundredCoupon) {
		this.dataId = dataId;
		this.couponName = couponName;
		this.totalPrice = totalPrice;
		this.tenCoupon = tenCoupon;
		this.twentyCoupon = twentyCoupon;
		this.thirtyCoupon = thirtyCoupon;
		this.fiftyCoupon = fiftyCoupon;
		this.hundredCoupon = hundredCoupon;
	}

	// Property accessors
	@Id
	@Column(name = "DATA_ID", unique = true, nullable = false, length = 10)
	public String getDataId() {
		return this.dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	@Column(name = "TOTAL_PRICE", precision = 9)
	public Double getTotalPrice() {
		return this.totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	@Column(name = "TEN_COUPON", length = 2)
	public String getTenCoupon() {
		return this.tenCoupon;
	}

	public void setTenCoupon(String tenCoupon) {
		this.tenCoupon = tenCoupon;
	}

	@Column(name = "TWENTY_COUPON", length = 2)
	public String getTwentyCoupon() {
		return this.twentyCoupon;
	}

	public void setTwentyCoupon(String twentyCoupon) {
		this.twentyCoupon = twentyCoupon;
	}

	@Column(name = "THIRTY_COUPON", length = 2)
	public String getThirtyCoupon() {
		return this.thirtyCoupon;
	}

	public void setThirtyCoupon(String thirtyCoupon) {
		this.thirtyCoupon = thirtyCoupon;
	}

	@Column(name = "FIFTY_COUPON", length = 2)
	public String getFiftyCoupon() {
		return this.fiftyCoupon;
	}

	public void setFiftyCoupon(String fiftyCoupon) {
		this.fiftyCoupon = fiftyCoupon;
	}

	@Column(name = "HUNDRED_COUPON", length = 2)
	public String getHundredCoupon() {
		return this.hundredCoupon;
	}

	public void setHundredCoupon(String hundredCoupon) {
		this.hundredCoupon = hundredCoupon;
	}

	@Column(name = "RECORD_USER", nullable = false, length = 8)
	public String getRecordUser() {
		return recordUser;
	}

	public void setRecordUser(String recordUser) {
		this.recordUser = recordUser;
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

	@Column(name = "STATUS", nullable = false, length = 2)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "COUPON_NAME", nullable = false, length = 30)
	public String getCouponName() {
		return couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}

	@Column(name = "REMARK", length = 200)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
