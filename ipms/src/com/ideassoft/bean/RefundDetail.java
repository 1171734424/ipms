package com.ideassoft.bean;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



@Entity
@Table(name = "TB_P_REFUNDDETAIL", schema = "UCR_PMS")
public class RefundDetail implements java.io.Serializable {


	private static final long serialVersionUID = 8998865771565363859L;
	// Fields
	
	private String refundId;
	private String orderId;
	private String bussinessId;
	private String tradeId;
	private Double refundMoney;
	private String source;
	private String status;
	private Date recordTime;
	private String applyId;
	private String refundType;
	
	
	public RefundDetail() {
		
		// TODO Auto-generated constructor stub
	}


	public RefundDetail(String refundId, String orderId, String bussinessId,
			String tradeId, Double refundMoney, String source, String status,
			Date recordTime) {
		super();
		this.refundId = refundId;
		this.orderId = orderId;
		this.bussinessId = bussinessId;
		this.tradeId = tradeId;
		this.refundMoney = refundMoney;
		this.source = source;
		this.status = status;
		this.recordTime = recordTime;
	}
    @Id
	@Column(name = "REFUND_ID", nullable = false, length = 17)
	public String getRefundId() {
		return refundId;
	}


	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}

	@Column(name = "ORDER_ID", nullable = false, length = 18)
	public String getOrderId() {
		return orderId;
	}


	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@Column(name = "BUSSINESS_ID", nullable = false, length = 20)
	public String getBussinessId() {
		return bussinessId;
	}


	public void setBussinessId(String bussinessId) {
		this.bussinessId = bussinessId;
	}

	@Column(name = "TRADE_ID", nullable = false, length = 100)
	public String getTradeId() {
		return tradeId;
	}


	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}

	@Column(name = "REFUND_MONEY", precision = 9 )
	public Double getRefundMoney() {
		return refundMoney;
	}


	public void setRefundMoney(Double refundMoney) {
		this.refundMoney = refundMoney;
	}

	@Column(name = "SOURCE", nullable = false, length = 1)
	public String getSource() {
		return source;
	}


	public void setSource(String source) {
		this.source = source;
	}

	@Column(name = "STATUS", nullable = false, length = 1)
	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RECORD_TIME", insertable = false, updatable = true)
	public Date getRecordTime() {
		return recordTime;
	}


	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	@Column(name = "APPLY_ID", nullable = true, length = 64)
	public String getApplyId() {
		return applyId;
	}


	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	@Column(name = "REFUNDTYPE", nullable = true, length = 2)
	public String getRefundType() {
		return refundType;
	}


	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}

	// Constructors

	

}