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
@Table(name = "TB_C_MEMBER_COUPON", schema = "UCR_PMS")
public class MemberCoupon implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6609052467446089319L;
	private String memberId;
	private String couponId;
	private String couponPrice;
	private Date startTime;
	private Date endTime;
	private String status;
	private String isGet;
	private Date recordTime;

	@Column(name = "MEMBER_ID", length = 8)
	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	@Id
	@Column(name = "COUPON_ID", unique = true, nullable = false, length = 10)
	public String getCouponId() {
		return couponId;
	}

	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}

	@Column(name = "COUPON_PRICE", length = 3)
	public String getCouponPrice() {
		return couponPrice;
	}

	public void setCouponPrice(String couponPrice) {
		this.couponPrice = couponPrice;
	}

	@Column(name = "START_TIME", length = 7)
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Column(name = "END_TIME", length = 7)
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Column(name = "STATUS", length = 1)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "IS_GET", length = 1)
	public String getIsGet() {
		return isGet;
	}

	public void setIsGet(String isGet) {
		this.isGet = isGet;
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

}