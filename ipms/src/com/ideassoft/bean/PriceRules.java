package com.ideassoft.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@Table(name = "TB_P_PRICERULES", schema = "UCR_PMS")
public class PriceRules implements java.io.Serializable{
	
	private static final long serialVersionUID = -8357842757927920319L;
	private String rulesId;
	private String rulesPeriod;
	private String rulesPeriodDetails;
	private String rulesFilters;
	private String rulesDesc;
	private String rulesName;
	private String rulesBranchId;
	// Constructors
	/** default constructor */
	public PriceRules() {
		
	}
	
	/** full constructor */
	public PriceRules(String rulesId,String rulesPeriod, String rulesPeriodDetails, String rulesFilters,
			String rulesDesc, String rulesName, String rulesBranchId) {
		super();
		this.rulesId = rulesId;
		this.rulesPeriod = rulesPeriod;
		this.rulesPeriodDetails = rulesPeriodDetails;
		this.rulesFilters = rulesFilters;
		this.rulesDesc = rulesDesc;
		this.rulesName = rulesName;
		this.rulesBranchId = rulesBranchId;
	}
	
	@Id
	@Column(name = "RULES_ID", nullable = false, length = 8)
	public String getRulesId() {
		return rulesId;
	}
	public void setRulesId(String rulesId) {
		this.rulesId = rulesId;
	}
	
	/*@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "START_TIME", length = 7)
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "END_TIME", length = 7)
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}*/
	
	@Column(name = "RULES_PERIOD", nullable = false, length = 1)
	public String getRulesPeriod() {
		return rulesPeriod;
	}
	public void setRulesPeriod(String rulesPeriod) {
		this.rulesPeriod = rulesPeriod;
	}
	
	@Column(name = "RULES_PERIODDETAILS", nullable = false, length = 200)
	public String getRulesPeriodDetails() {
		return rulesPeriodDetails;
	}
	public void setRulesPeriodDetails(String rulesPeriodDetails) {
		this.rulesPeriodDetails = rulesPeriodDetails;
	}
	
	@Column(name = "RULES_FILTERS", nullable = false, length = 1)
	public String getRulesFilters() {
		return rulesFilters;
	}
	public void setRulesFilters(String rulesFilters) {
		this.rulesFilters = rulesFilters;
	}
	
	@Column(name = "RULES_DESC", nullable = false, length = 300)
	public String getRulesDesc() {
		return rulesDesc;
	}
	public void setRulesDesc(String rulesDesc) {
		this.rulesDesc = rulesDesc;
	}
	
	@Column(name = "RULES_NAME", nullable = false, length = 50)
	public String getRulesName() {
		return rulesName;
	}
	public void setRulesName(String rulesName) {
		this.rulesName = rulesName;
	}

	@Column(name = "RULES_BRANCH_ID", length = 6)
	public String getRulesBranchId() {
		return rulesBranchId;
	}

	public void setRulesBranchId(String rulesBranchId) {
		this.rulesBranchId = rulesBranchId;
	}
	
	
	
}
