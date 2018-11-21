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
@Table(name = "TB_P_APTORDER", schema = "UCR_PMS")
public class Aptorder implements java.io.Serializable {


	private static final long serialVersionUID = 4683471510757256398L;
	// Fields
	
	private String orderId;
	private String branchId;
	private String source;
	private String roomId;
	private String roomType;
	private String orderUser;
	private String mPhone;
	private Date arrivalTime;
	private Date leaveTime;
	private Double roomPrice;
	private Double advanceCash;
	private Date orderTime;
	private Date recordTime;
	private String remark;
	private String voucher;
	private String status;
	private Date reFundTime;
	
	private String unitPrice;
	private String time;
	private String typeOfPayment;
	private Date contrartEndTime;
	

	public Aptorder() {
		
	}


	



	public Aptorder(String orderId, String branchId, String source,
			String roomId, String roomType, String orderUser, String mPhone,
			Date arrivalTime, Date leaveTime, Double roomPrice,
			Double advanceCash, Date orderTime, Date recordTime, String remark,
			String voucher, String status, Date reFundTime, String unitPrice,
			String time, String typeOfPayment, Date contrartEndTime) {
		super();
		this.orderId = orderId;
		this.branchId = branchId;
		this.source = source;
		this.roomId = roomId;
		this.roomType = roomType;
		this.orderUser = orderUser;
		this.mPhone = mPhone;
		this.arrivalTime = arrivalTime;
		this.leaveTime = leaveTime;
		this.roomPrice = roomPrice;
		this.advanceCash = advanceCash;
		this.orderTime = orderTime;
		this.recordTime = recordTime;
		this.remark = remark;
		this.voucher = voucher;
		this.status = status;
		this.reFundTime = reFundTime;
		this.unitPrice = unitPrice;
		this.time = time;
		this.typeOfPayment = typeOfPayment;
		this.contrartEndTime = contrartEndTime;
	}






	@Id
	@Column(name = "ORDER_ID", unique = true, nullable = false, length = 17)
	public String getOrderId() {
		return orderId;
	}


	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@Column(name = "BRANCH_ID", nullable = false, length = 6)
	public String getBranchId() {
		return branchId;
	}


	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	@Column(name = "SOURCE", nullable = false, length = 2)
	public String getSource() {
		return source;
	}


	public void setSource(String source) {
		this.source = source;
	}

	@Column(name = "ROOM_ID", length = 6)
	public String getRoomId() {
		return roomId;
	}


	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	@Column(name = "ROOM_TYPE", nullable = false, length = 3)
	public String getRoomType() {
		return roomType;
	}


	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	@Column(name = "ORDER_USER", length = 8)
	public String getOrderUser() {
		return orderUser;
	}


	public void setOrderUser(String orderUser) {
		this.orderUser = orderUser;
	}

	@Column(name = "M_PHONE",  length = 11)
	public String getmPhone() {
		return mPhone;
	}


	public void setmPhone(String mPhone) {
		this.mPhone = mPhone;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ARRIVAL_TIME" ,length = 7)
	public Date getArrivalTime() {
		return arrivalTime;
	}


	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LEAVE_TIME" ,length = 7)
	public Date getLeaveTime() {
		return leaveTime;
	}


	public void setLeaveTime(Date leaveTime) {
		this.leaveTime = leaveTime;
	}

	@Column(name = "ROOM_PRICE", nullable = false, precision = 9)
	public Double getRoomPrice() {
		return roomPrice;
	}


	public void setRoomPrice(Double roomPrice) {
		this.roomPrice = roomPrice;
	}

	@Column(name = "ADVANCE_CASH",  precision = 9)
	public Double getAdvanceCash() {
		return advanceCash;
	}


	public void setAdvanceCash(Double advanceCash) {
		this.advanceCash = advanceCash;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ORDER_TIME",  length = 7)
	public Date getOrderTime() {
		return orderTime;
	}


	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
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

	@Column(name = "REMARK",  length = 200)
	public String getRemark() {
		return remark;
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

	@Column(name = "STATUS",  length = 1)
	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "REFUND_TIME")
	public Date getReFundTime() {
		return reFundTime;
	}

	public void setReFundTime(Date reFundTime) {
		this.reFundTime = reFundTime;
	}





	@Column(name = "UNITPRICE",  length = 10)
	public String getUnitPrice() {
		return unitPrice;
	}






	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}





	@Column(name = "TIME",  length = 2)
	public String getTime() {
		return time;
	}






	public void setTime(String time) {
		this.time = time;
	}





	@Column(name = "TYPEOFPAYMENT",  length = 1)
	public String getTypeOfPayment() {
		return typeOfPayment;
	}






	public void setTypeOfPayment(String typeOfPayment) {
		this.typeOfPayment = typeOfPayment;
	}





	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CONTRART_END_TIME")
	public Date getContrartEndTime() {
		return contrartEndTime;
	}






	public void setContrartEndTime(Date contrartEndTime) {
		this.contrartEndTime = contrartEndTime;
	}


}