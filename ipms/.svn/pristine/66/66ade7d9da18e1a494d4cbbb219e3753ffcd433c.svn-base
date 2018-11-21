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
@Table(name = "TB_C_SMSTEMPLATE", schema = "UCR_PMS")
public class SmsTemplate implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = -3579089408226542034L;
	private String templateId;
	private String templateName;
	private String smsCategory;
	private String branchId;
	private String templateContent;
	private String varTemplate;
	private String recordUser;
	private Date recordTime;
	private String status;
	private String remark;

	// Constructors

	/** default constructor */
	public SmsTemplate() {
	}

	/** minimal constructor */
	public SmsTemplate(String templateId, String templateName, String smsCategory, String branchId,
			String templateContent, String recordUser, String status) {
		this.templateId = templateId;
		this.templateName = templateName;
		this.smsCategory = smsCategory;
		this.branchId = branchId;
		this.templateContent = templateContent;
		this.recordUser = recordUser;
		this.status = status;
	}

	/** full constructor */
	public SmsTemplate(String templateId, String templateName, String smsCategory, String branchId,
			String templateContent, String varTemplate, String recordUser, Date recordTime, String status, String remark) {
		this.templateId = templateId;
		this.templateName = templateName;
		this.smsCategory = smsCategory;
		this.branchId = branchId;
		this.templateContent = templateContent;
		this.varTemplate = varTemplate;
		this.recordUser = recordUser;
		this.recordTime = recordTime;
		this.status = status;
		this.remark = remark;
	}

	// Property accessors
	@Id
	@Column(name = "TEMPLATE_ID", unique = true, nullable = false, length = 8)
	public String getTemplateId() {
		return this.templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	@Column(name = "TEMPLATE_NAME", nullable = false, length = 2)
	public String getTemplateName() {
		return this.templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	@Column(name = "SMS_CATEGORY", nullable = false, length = 1)
	public String getSmsCategory() {
		return this.smsCategory;
	}

	public void setSmsCategory(String smsCategory) {
		this.smsCategory = smsCategory;
	}

	@Column(name = "BRANCH_ID", length = 6)
	public String getBranchId() {
		return this.branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	@Column(name = "TEMPLATE_CONTENT", nullable = false, length = 400)
	public String getTemplateContent() {
		return this.templateContent;
	}

	public void setTemplateContent(String templateContent) {
		this.templateContent = templateContent;
	}

	@Column(name = "RECORD_USER", nullable = false, length = 8)
	public String getRecordUser() {
		return this.recordUser;
	}

	public void setRecordUser(String recordUser) {
		this.recordUser = recordUser;
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

	@Column(name = "VAR_TEMPLATE", length = 200)
	public String getVarTemplate() {
		return varTemplate;
	}

	public void setVarTemplate(String varTemplate) {
		this.varTemplate = varTemplate;
	}

}