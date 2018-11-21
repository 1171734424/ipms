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
 * OrderConsume entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_C_ORDERCONSUME", schema = "UCR_PMS")
public class OrderConsume implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 8717357263089563263L;
	private String orderId;
	private String branchId;
	private String roomId;
	private Double ammeter;
	private Double ammeterCost;
	private Double watermemter;
	private Double watermemterCost;
	private Date arrivalTime;
	private Date leaveTime;
	private String status;
	private Date recordTime;
	private String recordUser;
	private String remark;

	// Constructors

	/** default constructor */
	public OrderConsume() {
	}

	/** minimal constructor */
	public OrderConsume(String orderId, String branchId, Double ammeter,
			Double ammeterCost, Double watermemter, Double watermemterCost,
			Date arrivalTime, Date leaveTime, String status, String recordUser) {
		this.orderId = orderId;
		this.branchId = branchId;
		this.ammeter = ammeter;
		this.ammeterCost = ammeterCost;
		this.watermemter = watermemter;
		this.watermemterCost = watermemterCost;
		this.arrivalTime = arrivalTime;
		this.leaveTime = leaveTime;
		this.status = status;
		this.recordUser = recordUser;
	}

	/** full constructor */
	public OrderConsume(String orderId, String branchId, String roomId,
			Double ammeter, Double ammeterCost, Double watermemter,
			Double watermemterCost, Date arrivalTime, Date leaveTime,
			String status, Date recordTime, String recordUser, String remark) {
		this.orderId = orderId;
		this.branchId = branchId;
		this.roomId = roomId;
		this.ammeter = ammeter;
		this.ammeterCost = ammeterCost;
		this.watermemter = watermemter;
		this.watermemterCost = watermemterCost;
		this.arrivalTime = arrivalTime;
		this.leaveTime = leaveTime;
		this.status = status;
		this.recordTime = recordTime;
		this.recordUser = recordUser;
		this.remark = remark;
	}

	// Property accessors
	@Id
	@Column(name = "ORDER_ID", unique = true, nullable = false, length = 17)
	public String getOrderId() {
		return this.orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@Column(name = "BRANCH_ID", nullable = false, length = 6)
	public String getBranchId() {
		return this.branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	@Column(name = "ROOM_ID", length = 6)
	public String getRoomId() {
		return this.roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	@Column(name = "AMMETER", nullable = false, precision = 9)
	public Double getAmmeter() {
		return this.ammeter;
	}

	public void setAmmeter(Double ammeter) {
		this.ammeter = ammeter;
	}

	@Column(name = "AMMETER_COST", nullable = false, precision = 9)
	public Double getAmmeterCost() {
		return this.ammeterCost;
	}

	public void setAmmeterCost(Double ammeterCost) {
		this.ammeterCost = ammeterCost;
	}

	@Column(name = "WATERMEMTER", nullable = false, precision = 9)
	public Double getWatermemter() {
		return this.watermemter;
	}

	public void setWatermemter(Double watermemter) {
		this.watermemter = watermemter;
	}

	@Column(name = "WATERMEMTER_COST", nullable = false, precision = 9)
	public Double getWatermemterCost() {
		return this.watermemterCost;
	}

	public void setWatermemterCost(Double watermemterCost) {
		this.watermemterCost = watermemterCost;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ARRIVAL_TIME", nullable = false)
	public Date getArrivalTime() {
		return this.arrivalTime;
	}

	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LEAVE_TIME", nullable = false)
	public Date getLeaveTime() {
		return this.leaveTime;
	}

	public void setLeaveTime(Date leaveTime) {
		this.leaveTime = leaveTime;
	}

	@Column(name = "STATUS", nullable = false, length = 2)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
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