package com.ideassoft.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TP_P_COMMPARAMETER", schema = "UCR_PMS")
public class CommParameter implements java.io.Serializable {

	private static final long serialVersionUID = 2961754122405503382L;

	/**
	 * 
	 */

	public CommParameter() {
	};

	public CommParameter(String commParaId, String smtpIpAddress, String smtpIpPort, String smtpUser,
			String smtpPassword, String emailAdmin, String isOpenEmail, String smsCom, String smsBaud, String isOpenSms) {
		this.commParaId = commParaId;
		this.smtpIPAddr = smtpIpAddress;
		this.smtpIPPort = smtpIpPort;
		this.smtpUserName = smtpUser;
		this.smtpUserPW = smtpPassword;
		this.emailAddr = emailAdmin;
		this.emailIsOpen = isOpenEmail;
		this.smsCom = smsCom;
		this.smsIsOpen = isOpenSms;
		this.smsBaud = smsBaud;
	}

	/**
	 * 通信参数ID
	 */
	private String commParaId;

	/**
	 * 邮件服务器IP地址
	 */
	private String smtpIPAddr;

	/**
	 * 邮件服务器端口
	 */
	private String smtpIPPort;

	/**
	 * 邮件服务器用户名称
	 */
	private String smtpUserName;

	/**
	 * 邮件服务器用户密码
	 */
	private String smtpUserPW;

	/**
	 * 发件人邮箱地址
	 */
	private String emailAddr;

	/**
	 * 是否启用邮箱服务 0：不启用，1：启用默认0
	 */
	private String emailIsOpen;

	/**
	 * 短信通讯串口编号：0：无、1：Com1、2：Com2、以此类推。默认0
	 */
	private String smsCom;

	/**
	 * 串口波特率，实际值默认0
	 */
	private String smsBaud;

	/**
	 * 是否启用短信服务 0：不启用，1：启用默认0
	 */
	private String smsIsOpen;

	@Id
	@Column(name = "COMM_PARAID", nullable = false)
	public String getCommParaId() {
		return commParaId;
	}

	public void setCommParaId(String commParaId) {
		this.commParaId = commParaId;
	}

	@Column(name = "SMTP_IPADDR")
	public String getSmtpIPAddr() {
		return smtpIPAddr;
	}

	public void setSmtpIPAddr(String smtpIPAddr) {
		this.smtpIPAddr = smtpIPAddr;
	}

	@Column(name = "SMTP_IPPORT")
	public String getSmtpIPPort() {
		return smtpIPPort;
	}

	public void setSmtpIPPort(String smtpIPPort) {
		this.smtpIPPort = smtpIPPort;
	}

	@Column(name = "SMTP_USERNAME")
	public String getSmtpUserName() {
		return smtpUserName;
	}

	public void setSmtpUserName(String smtpUserName) {
		this.smtpUserName = smtpUserName;
	}

	@Column(name = "SMTP_USERPW")
	public String getSmtpUserPW() {
		return smtpUserPW;
	}

	public void setSmtpUserPW(String smtpUserPW) {
		this.smtpUserPW = smtpUserPW;
	}

	@Column(name = "EMAIL_ADDR")
	public String getEmailAddr() {
		return emailAddr;
	}

	public void setEmailAddr(String emailAddr) {
		this.emailAddr = emailAddr;
	}

	@Column(name = "EMAIL_ISOPEN")
	public String getEmailIsOpen() {
		return emailIsOpen;
	}

	public void setEmailIsOpen(String emailIsOpen) {
		this.emailIsOpen = emailIsOpen;
	}

	@Column(name = "SMS_COM")
	public String getSmsCom() {
		return smsCom;
	}

	public void setSmsCom(String smsCom) {
		this.smsCom = smsCom;
	}

	@Column(name = "SMS_BAUD")
	public String getSmsBaud() {
		return smsBaud;
	}

	public void setSmsBaud(String smsBaud) {
		this.smsBaud = smsBaud;
	}

	@Column(name = "SMS_ISOPEN")
	public String getSmsIsOpen() {
		return smsIsOpen;
	}

	public void setSmsIsOpen(String smsIsOpen) {
		this.smsIsOpen = smsIsOpen;
	}

}
