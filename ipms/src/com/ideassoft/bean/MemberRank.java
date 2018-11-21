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
@Table(name = "TP_C_MEMBERRANK", schema = "UCR_PMS")
public class MemberRank implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 8698508748256725115L;
	private String memberRank;
	private String rankName;
	private Short discount;
	private Date recordTime = new Date();
	private String status;
	private String remark;
	private String memberRankId;

	// Constructors

	/** default constructor */
	public MemberRank() {
	}

	/** minimal constructor */
	public MemberRank(String memberRank, String rankName, Short discount, String status) {
		this.memberRank = memberRank;
		this.rankName = rankName;
		this.discount = discount;
		this.status = status;
	}

	/** full constructor */
	public MemberRank(String memberRank, String rankName, Short discount, Date recordTime, String status, String remark) {
		this.memberRank = memberRank;
		this.rankName = rankName;
		this.discount = discount;
		this.recordTime = recordTime;
		this.status = status;
		this.remark = remark;
	}

	@Id
	@Column(name = "MEMBER_RANK_ID", unique = true, nullable = false, length = 8)
	public String getMemberRankId() {
		return memberRankId;
	}

	public void setMemberRankId(String memberRankId) {
		this.memberRankId = memberRankId;
	}

	// Property accessors
	@Column(name = "MEMBER_RANK", nullable = false, length = 2)
	public String getMemberRank() {
		return this.memberRank;
	}

	public void setMemberRank(String memberRank) {
		this.memberRank = memberRank;
	}

	@Column(name = "RANK_NAME", nullable = false, length = 20)
	public String getRankName() {
		return this.rankName;
	}

	public void setRankName(String rankName) {
		this.rankName = rankName;
	}

	@Column(name = "DISCOUNT", nullable = false, precision = 3, scale = 0)
	public Short getDiscount() {
		return this.discount;
	}

	public void setDiscount(Short discount) {
		this.discount = discount;
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

}