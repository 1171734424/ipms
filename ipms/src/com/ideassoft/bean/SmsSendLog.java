package com.ideassoft.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "TL_C_SMSSENDLOG", schema = "UCR_PMS")
public class SmsSendLog implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 2889423036809652833L;
	private String dataId;
	private String templateId;
	private String branchId;
	private String smsContent;
	private String smsRecipentno;
	private String errorCodes;
	private String recordUser;
	private Date recordTime;
	private Date sendTime;
	private String status;
	private String remark;

	// Constructors

	/** default constructor */
	public SmsSendLog() {
	}

	/** minimal constructor */
	public SmsSendLog(String dataId, String smsContent, String smsRecipentno, String recordUser, String status) {
		this.dataId = dataId;
		this.smsContent = smsContent;
		this.smsRecipentno = smsRecipentno;
		this.recordUser = recordUser;
		this.status = status;
	}

	/** full constructor */
	public SmsSendLog(String dataId, String templateId, String branchId, String smsContent, String smsRecipentno,
			String errorCodes, String recordUser, Date recordTime, Date sendTime, String status, String remark) {
		this.dataId = dataId;
		this.templateId = templateId;
		this.branchId = branchId;
		this.smsContent = smsContent;
		this.smsRecipentno = smsRecipentno;
		this.errorCodes = errorCodes;
		this.recordUser = recordUser;
		this.recordTime = recordTime;
		this.sendTime = sendTime;
		this.status = status;
		this.remark = remark;
	}

	// Property accessors
	@Id
	@Column(name = "DATA_ID", unique = true, nullable = false, length = 8)
	public String getDataId() {
		return this.dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	@Column(name = "TEMPLATE_ID", nullable = false, length = 8)
	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	@Column(name = "BRANCH_ID", nullable = false, length = 6)
	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	@Column(name = "SMS_CONTENT", nullable = false, length = 400)
	public String getSmsContent() {
		return this.smsContent;
	}

	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}

	@Column(name = "SMS_RECIPENTNO", nullable = false, length = 11)
	public String getSmsRecipentno() {
		return this.smsRecipentno;
	}

	public void setSmsRecipentno(String smsRecipentno) {
		this.smsRecipentno = smsRecipentno;
	}

	@Column(name = "ERROR_CODES", length = 20)
	public String getErrorCodes() {
		return this.errorCodes;
	}

	public void setErrorCodes(String errorCodes) {
		this.errorCodes = errorCodes;
	}

	@Column(name = "RECORD_USER", nullable = false, length = 8)
	public String getRecordUser() {
		return this.recordUser;
	}

	public void setRecordUser(String recordUser) {
		this.recordUser = recordUser;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RECORD_TIME")
	public Date getRecordTime() {
		return this.recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "SEND_TIME", insertable = true, updatable = true, nullable = true)
	public Date getSendTime() {
		return this.sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
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

	/*
	 * public SmsSendLog getSendBeforeInfo() { SmsSendLog sms = new
	 * SmsSendLog(); sms.setBranchId(branchId); sms.setTemplateId(templateId);
	 * sms.setRecordTime(recordTime); sms.setRecordUser(recordUser);
	 * sms.setRemark(remark); sms.setSmsContent(smsContent);
	 * sms.setSmsRecipentno(smsRecipentno); sms.setStatus("1");
	 * sms.setRemark(remark); return sms; }
	 */

	/*
	 * public SmsSendLog setSendBeforeInfo(String templateId, String branchId,
	 * String smsContent, String smsRecipentno,String recordUser, Date
	 * recordTime,String remark ) { SmsSendLog sms = new SmsSendLog();
	 * sms.setBranchId(branchId); sms.setTemplateId(templateId);
	 * sms.setRecordTime(recordTime); sms.setRecordUser(recordUser);
	 * sms.setRemark(remark); sms.setSmsContent(smsContent);
	 * sms.setSmsRecipentno(smsRecipentno); sms.setStatus("1");
	 * sms.setRemark(remark); return sms; }
	 */
}