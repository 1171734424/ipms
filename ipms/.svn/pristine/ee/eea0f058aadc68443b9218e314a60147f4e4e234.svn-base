package com.ideassoft.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TB_P_APPLYDETAIL", schema = "UCR_PMS")
public class ApplyDetail implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 5355901459291242118L;
	private String detailId;
	private String applyId;
	private String content;
	private String roomType;
	private Double roomPrice;
	private String rowOrder;
	private String remark;
	private String remark1;

	// Constructors

	/** default constructor */
	public ApplyDetail() {
	}

	/** minimal constructor */
	public ApplyDetail(String detailId, String applyId, String content, String roomType) {
		this.detailId = detailId;
		this.applyId = applyId;
		this.content = content;
		this.roomType = roomType;
	}

	/** full constructor */
	public ApplyDetail(String detailId, String applyId, String content, String roomType, Double roomPrice,
			String rowOrder, String remark) {
		this.detailId = detailId;
		this.applyId = applyId;
		this.content = content;
		this.roomType = roomType;
		this.roomPrice = roomPrice;
		this.rowOrder = rowOrder;
		this.remark = remark;
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

	@Column(name = "APPLY_ID", nullable = false, length = 12)
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

}