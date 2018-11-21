package com.ideassoft.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "TB_P_PRICEAPPLYDETAIL", schema = "UCR_PMS")
public class PriceApplyDetail implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 5459869026944965371L;
	private String detailId;
	private String applyId;
	private String content;
	private String roomType;
	private Double roomPrice;
	private String rowOrder;
	private String remark;
	private Date recordTime;

	// Constructors

	/** default constructor */
	public PriceApplyDetail() {
	}

	/** minimal constructor */
	public PriceApplyDetail(String detailId, String applyId, String content, String roomType) {
		this.detailId = detailId;
		this.applyId = applyId;
		this.content = content;
		this.roomType = roomType;
	}

	/** full constructor */
	public PriceApplyDetail(String detailId, String applyId, String content, String roomType, Double roomPrice,
			String rowOrder, String remark, Date recordTime) {
		this.detailId = detailId;
		this.applyId = applyId;
		this.content = content;
		this.roomType = roomType;
		this.roomPrice = roomPrice;
		this.rowOrder = rowOrder;
		this.remark = remark;
		this.recordTime = recordTime;
	}

	// Property accessors
	@Id
	@Column(name = "DETAIL_ID", unique = true, nullable = false, length = 8)
	public String getDetailId() {
		return this.detailId;
	}

	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}

	@Column(name = "APPLY_ID", nullable = false, length = 14)
	public String getApplyId() {
		return this.applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	@Column(name = "CONTENT", nullable = false, length = 3)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "ROOM_TYPE", nullable = false, length = 16)
	public String getRoomType() {
		return this.roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	@Column(name = "ROOM_PRICE", precision = 9)
	public Double getRoomPrice() {
		return this.roomPrice;
	}

	public void setRoomPrice(Double roomPrice) {
		this.roomPrice = roomPrice;
	}

	@Column(name = "ROW_ORDER", length = 2)
	public String getRowOrder() {
		return this.rowOrder;
	}

	public void setRowOrder(String rowOrder) {
		this.rowOrder = rowOrder;
	}

	@Column(name = "REMARK", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RECORD_TIME",  updatable = true)
	public Date getRecordTime() {
		return this.recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

}