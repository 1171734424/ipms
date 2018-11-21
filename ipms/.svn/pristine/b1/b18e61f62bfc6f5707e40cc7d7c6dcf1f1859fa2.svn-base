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
@Table(name = "TL_P_INTEGRATION", schema = "UCR_PMS")
public class Integration implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = -9024043988099984112L;
	private String logId;
	private String branchId;
	private String checkId;
	private String source;
	private Double fee;
	private Integer integragion;
	private String rule;
	private String memberId;
	private Date recordTime;

	// Constructors

	/** default constructor */
	public Integration() {
	}

	/** minimal constructor */
	public Integration(String logId, String branchId, Double fee, Integer integragion, String rule, String memberId) {
		this.logId = logId;
		this.branchId = branchId;
		this.fee = fee;
		this.integragion = integragion;
		this.rule = rule;
		this.memberId = memberId;
	}

	/** full constructor */
	public Integration(String logId, String branchId, String checkId, String source, Double fee, Integer integragion,
			String rule, String memberId, Date recordTime) {
		this.logId = logId;
		this.branchId = branchId;
		this.checkId = checkId;
		this.source = source;
		this.fee = fee;
		this.integragion = integragion;
		this.rule = rule;
		this.memberId = memberId;
		this.recordTime = recordTime;
	}

	// Property accessors
	@Id
	@Column(name = "LOG_ID", unique = true, nullable = false, length = 14)
	public String getLogId() {
		return this.logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	@Column(name = "BRANCH_ID", nullable = false, length = 4)
	public String getBranchId() {
		return this.branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	@Column(name = "CHECK_ID", length = 14)
	public String getCheckId() {
		return this.checkId;
	}

	public void setCheckId(String checkId) {
		this.checkId = checkId;
	}

	@Column(name = "SOURCE", length = 1)
	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Column(name = "FEE", precision = 9)
	public Double getFee() {
		return this.fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	@Column(name = "INTEGRAGION", nullable = false, precision = 9, scale = 0)
	public Integer getIntegragion() {
		return this.integragion;
	}

	public void setIntegragion(Integer integragion) {
		this.integragion = integragion;
	}

	@Column(name = "RULE", length = 10)
	public String getRule() {
		return this.rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	@Column(name = "MEMBER_ID", nullable = false, length = 8)
	public String getMemberId() {
		return this.memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
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