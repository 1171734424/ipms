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
@Table(name = "TB_C_MEMBERACCOUNT", schema = "UCR_PMS")
public class MemberAccount implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = -4082040277896824751L;
	private String accountId;
	private String memberId;
	private Double currBalance;
	private Long currIntegration;
	private Double totalRecharge;
	private Double discountGift;
	private Double chargeGift;
	private Double totalConsume;
	private Long totalIntegration;
	private Long ingegrationGift;
	private Long totalConsIntegration;
	private Integer totalRoomnights;
	private Integer currRoomnights;
	private Short totalNoshow;
	private Short currNoshow;
	private Date recordTime;
	private String status;
	private Long thisYearIntegration;

	// Constructors

	/** default constructor */
	public MemberAccount() {
	}

	/** minimal constructor */
	public MemberAccount(String accountId, String status) {
		this.accountId = accountId;
		this.status = status;
	}

	/** full constructor */
	public MemberAccount(String accountId, String memberId, Double currBalance, Long currIntegration,
			Double totalRecharge, Double discountGift, Double chargeGift, Double totalConsume, Long totalIntegration,
			Long ingegrationGift, Long totalConsIntegration, Integer totalRoomnights, Integer currRoomnights,
			Short totalNoshow, Short currNoshow, Date recordTime, String status, Long thisYearIntegration) {
		this.accountId = accountId;
		this.memberId = memberId;
		this.currBalance = currBalance;
		this.currIntegration = currIntegration;
		this.totalRecharge = totalRecharge;
		this.discountGift = discountGift;
		this.chargeGift = chargeGift;
		this.totalConsume = totalConsume;
		this.totalIntegration = totalIntegration;
		this.ingegrationGift = ingegrationGift;
		this.totalConsIntegration = totalConsIntegration;
		this.totalRoomnights = totalRoomnights;
		this.currRoomnights = currRoomnights;
		this.totalNoshow = totalNoshow;
		this.currNoshow = currNoshow;
		this.recordTime = recordTime;
		this.status = status;
		this.thisYearIntegration = thisYearIntegration;
	}

	// Property accessors
	@Id
	@Column(name = "ACCOUNT_ID", unique = true, nullable = false, length = 16)
	public String getAccountId() {
		return this.accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	@Column(name = "MEMBER_ID", length = 8)
	public String getMemberId() {
		return this.memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	@Column(name = "CURR_BALANCE", precision = 10)
	public Double getCurrBalance() {
		return this.currBalance;
	}

	public void setCurrBalance(Double currBalance) {
		this.currBalance = currBalance;
	}

	@Column(name = "CURR_INTEGRATION", precision = 10, scale = 0)
	public Long getCurrIntegration() {
		return this.currIntegration;
	}

	public void setCurrIntegration(Long currIntegration) {
		this.currIntegration = currIntegration;
	}

	@Column(name = "TOTAL_RECHARGE", precision = 10)
	public Double getTotalRecharge() {
		return this.totalRecharge;
	}

	public void setTotalRecharge(Double totalRecharge) {
		this.totalRecharge = totalRecharge;
	}

	@Column(name = "DISCOUNT_GIFT", precision = 10)
	public Double getDiscountGift() {
		return this.discountGift;
	}

	public void setDiscountGift(Double discountGift) {
		this.discountGift = discountGift;
	}

	@Column(name = "CHARGE_GIFT", precision = 10)
	public Double getChargeGift() {
		return this.chargeGift;
	}

	public void setChargeGift(Double chargeGift) {
		this.chargeGift = chargeGift;
	}

	@Column(name = "TOTAL_CONSUME", precision = 10)
	public Double getTotalConsume() {
		return this.totalConsume;
	}

	public void setTotalConsume(Double totalConsume) {
		this.totalConsume = totalConsume;
	}

	@Column(name = "TOTAL_INTEGRATION", precision = 10, scale = 0)
	public Long getTotalIntegration() {
		return this.totalIntegration;
	}

	public void setTotalIntegration(Long totalIntegration) {
		this.totalIntegration = totalIntegration;
	}

	@Column(name = "INGEGRATION_GIFT", precision = 10, scale = 0)
	public Long getIngegrationGift() {
		return this.ingegrationGift;
	}

	public void setIngegrationGift(Long ingegrationGift) {
		this.ingegrationGift = ingegrationGift;
	}

	@Column(name = "TOTAL_CONS_INTEGRATION", precision = 10, scale = 0)
	public Long getTotalConsIntegration() {
		return this.totalConsIntegration;
	}

	public void setTotalConsIntegration(Long totalConsIntegration) {
		this.totalConsIntegration = totalConsIntegration;
	}

	@Column(name = "TOTAL_ROOMNIGHTS", precision = 5, scale = 0)
	public Integer getTotalRoomnights() {
		return this.totalRoomnights;
	}

	public void setTotalRoomnights(Integer totalRoomnights) {
		this.totalRoomnights = totalRoomnights;
	}

	@Column(name = "CURR_ROOMNIGHTS", precision = 5, scale = 0)
	public Integer getCurrRoomnights() {
		return this.currRoomnights;
	}

	public void setCurrRoomnights(Integer currRoomnights) {
		this.currRoomnights = currRoomnights;
	}

	@Column(name = "TOTAL_NOSHOW", precision = 4, scale = 0)
	public Short getTotalNoshow() {
		return this.totalNoshow;
	}

	public void setTotalNoshow(Short totalNoshow) {
		this.totalNoshow = totalNoshow;
	}

	@Column(name = "CURR_NOSHOW", precision = 4, scale = 0)
	public Short getCurrNoshow() {
		return this.currNoshow;
	}

	public void setCurrNoshow(Short currNoshow) {
		this.currNoshow = currNoshow;
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

	@Column(name = "THIS_YEAR_INTEGRATION", precision = 10, scale = 0)
	public Long getThisYearIntegration() {
		return thisYearIntegration;
	}

	public void setThisYearIntegration(Long thisYearIntegration) {
		this.thisYearIntegration = thisYearIntegration;
	}

}