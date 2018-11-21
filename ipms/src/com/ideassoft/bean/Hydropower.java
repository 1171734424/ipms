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
 * Hydropower entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_E_HYDROPOWER", schema = "UCR_PMS")
public class Hydropower implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 7715031610541384014L;
	private String logId;
	private String orderId;
	private String branchId;
	private String roomId;
	private Date startTime;
	private Date endTime;
	private Double electricUseage;
	private Double electricPrice;
	private Double electricConsumemoney;
	private Double waterUseage;
	private Double waterPrice;
	private Double waterConsumemoney;
	private String status;
	private Date recordTime;
	private String remark;

	// Constructors

	/** default constructor */
	public Hydropower() {
	}

	/** minimal constructor */
	public Hydropower(String logId, String orderId, String branchId,
			Date startTime, Date endTime, Double electricUseage,
			Double electricConsumemoney, Double waterUseage,
			Double waterConsumemoney, String status) {
		this.logId = logId;
		this.orderId = orderId;
		this.branchId = branchId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.electricUseage = electricUseage;
		this.electricConsumemoney = electricConsumemoney;
		this.waterUseage = waterUseage;
		this.waterConsumemoney = waterConsumemoney;
		this.status = status;
		
	}

	/** full constructor */
	public Hydropower(String logId, String orderId, String branchId,
			String roomId, Date startTime, Date endTime, Double electricUseage,
			Double electricPrice,Double electricConsumemoney, Double waterUseage,
			Double waterPrice,Double waterConsumemoney, String status, Date recordTime,
			String remark) {
		this.logId = logId;
		this.orderId = orderId;
		this.branchId = branchId;
		this.roomId = roomId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.electricUseage = electricUseage;
		this.electricPrice = electricPrice;
		this.electricConsumemoney = electricConsumemoney;
		this.waterUseage = waterUseage;
		this.waterPrice = waterPrice;
		this.waterConsumemoney = waterConsumemoney;
		this.status = status;
		this.recordTime = recordTime;
		this.remark = remark;
	}

	// Property accessors
	@Id
	@Column(name = "LOG_ID", unique = true, nullable = false, length = 20)
	public String getLogId() {
		return this.logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	@Column(name = "ORDER_ID", nullable = false, length = 17)
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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "START_TIME", nullable = false)
	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "END_TIME", nullable = false)
	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Column(name = "ELECTRIC_USEAGE", nullable = false, precision = 9)
	public Double getElectricUseage() {
		return this.electricUseage;
	}

	public void setElectricUseage(Double electricUseage) {
		this.electricUseage = electricUseage;
	}
	
	@Column(name = "ELECTRIC_PRICE", precision = 9)
	public Double getElectricPrice() {
		return this.electricPrice;
	}

	public void setElectricPrice(Double electricPrice) {
		this.electricPrice = electricPrice;
	}

	@Column(name = "ELECTRIC_CONSUMEMONEY", nullable = false, precision = 9)
	public Double getElectricConsumemoney() {
		return this.electricConsumemoney;
	}

	public void setElectricConsumemoney(Double electricConsumemoney) {
		this.electricConsumemoney = electricConsumemoney;
	}

	@Column(name = "WATER_USEAGE", nullable = false, precision = 9)
	public Double getWaterUseage() {
		return this.waterUseage;
	}

	public void setWaterUseage(Double waterUseage) {
		this.waterUseage = waterUseage;
	}
	
	@Column(name = "WATER_PRICE", precision = 9)
	public Double getWaterPrice() {
		return this.waterPrice;
	}

	public void setWaterPrice(Double waterPrice) {
		this.waterPrice = waterPrice;
	}

	@Column(name = "WATER_CONSUMEMONEY", nullable = false, precision = 9)
	public Double getWaterConsumemoney() {
		return this.waterConsumemoney;
	}

	public void setWaterConsumemoney(Double waterConsumemoney) {
		this.waterConsumemoney = waterConsumemoney;
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

	@Column(name = "REMARK", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}