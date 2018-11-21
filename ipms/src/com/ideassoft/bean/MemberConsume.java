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
@Table(name = "TL_C_MEMBERCONSUME", schema = "UCR_PMS")
public class MemberConsume implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = -3613286912417326204L;
	private String consumeId;
	private String memberId;
	private String consumeType;
	private String orderId;
	private Double consumption;
	private String payment;
	private Date recordTime;
	private String status;

	// Constructors

	/** default constructor */
	public MemberConsume() {
	}

	/** minimal constructor */
	public MemberConsume(String consumeId) {
		this.consumeId = consumeId;
	}

	/** full constructor */
	public MemberConsume(String consumeId, String memberId, String consumeType, String orderId, Double consumption,
			String payment, Date recordTime, String status) {
		this.consumeId = consumeId;
		this.memberId = memberId;
		this.consumeType = consumeType;
		this.orderId = orderId;
		this.consumption = consumption;
		this.payment = payment;
		this.recordTime = recordTime;
		this.status = status;
	}

	// Property accessors
	@Id
	@Column(name = "CONSUME_ID", unique = true, nullable = false, length = 16)
	public String getConsumeId() {
		return this.consumeId;
	}

	public void setConsumeId(String consumeId) {
		this.consumeId = consumeId;
	}

	@Column(name = "MEMBER_ID", length = 8)
	public String getMemberId() {
		return this.memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	@Column(name = "CONSUME_TYPE", length = 1)
	public String getConsumeType() {
		return this.consumeType;
	}

	public void setConsumeType(String consumeType) {
		this.consumeType = consumeType;
	}

	@Column(name = "ORDER_ID", length = 17)
	public String getOrderId() {
		return this.orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@Column(name = "CONSUMPTION", precision = 10)
	public Double getConsumption() {
		return this.consumption;
	}

	public void setConsumption(Double consumption) {
		this.consumption = consumption;
	}

	@Column(name = "PAYMENT", length = 1)
	public String getPayment() {
		return this.payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
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

	@Column(name = "STATUS", length = 1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}