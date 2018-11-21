package com.ideassoft.core.notifier.bean;

import java.io.Serializable;
import java.util.Date;

public class SendMessage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9190151641938813043L;
	/**
	 * 对应发送消息SmsSendLog里的数据编号
	 * 接受短信的人
	 * 短信内容
	 * 服务启动成功或异常
	 * 会员等级
	 */
	private String dataId;
	private String messageRecipentno;
	private String messageContent;
	private String messageEventCode;
	private String operUser;
	private Date operTime;
	private String memberLevel;
	
	public SendMessage(){};
	
	public SendMessage(String dataId, String messageRecipentno, String messageContent, String messageEventCode, String operUser, Date operTime, String memberLevel) {
		super();
		this.dataId = dataId;
		this.messageRecipentno = messageRecipentno;
		this.messageContent = messageContent;
		this.messageEventCode = messageEventCode;
		this.operUser = operUser;
		this.operTime = operTime;
		this.memberLevel = memberLevel;
	}
	
	public Date getOperTime() {
		return operTime;
	}

	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}

	public String getDataId() {
		return dataId;
	}
	public void setDataId(String dataId) {
		this.dataId = dataId;
	}
	public String getMessageRecipentno() {
		return messageRecipentno;
	}
	public void setMessageRecipentno(String messageRecipentno) {
		this.messageRecipentno = messageRecipentno;
	}
	public String getMessageContent() {
		return messageContent;
	}
	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
	public String getMessageEventCode() {
		return messageEventCode;
	}
	public void setMessageEventCode(String messageEventCode) {
		this.messageEventCode = messageEventCode;
	}
	public String getOperUser() {
		return operUser;
	}
	public void setOperUser(String operUser) {
		this.operUser = operUser;
	}

	public String getMemberLevel() {
		return memberLevel;
	}

	public void setMemberLevel(String memberLevel) {
		this.memberLevel = memberLevel;
	}

	

}
