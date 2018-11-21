package com.ideassoft.bean;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

public class Event implements Serializable {

	private static final long serialVersionUID = -2687583122059641640L;
	private String evtId;
	private Integer evtIsWarning;
	private Date evtTime;
	private String evtSrc;
	private String evtName;
	private String evtCode;
	private String evtContent;
	private String evtOperatorId;
	private String evtOperatorIp;

	/** full constructor */
	public Event(String evtId, Integer evtIsWarning, Date evtTime, String evtSrc, String evtName, String evtCode,
			String evtContent, String evtOperatorId, String evtOperatorIp) {
		this.evtId = evtId;
		this.evtIsWarning = evtIsWarning;
		this.evtTime = evtTime;
		this.evtSrc = evtSrc;
		this.evtName = evtName;
		this.evtCode = evtCode;
		this.evtContent = evtContent;
		this.evtOperatorId = evtOperatorId;
		this.evtOperatorIp = evtOperatorIp;
	}

	/** default constructor */
	public Event() {
	}

	/** minimal constructor */
	public Event(String evtId) {
		this.evtId = evtId;
	}

	public String getEvtId() {
		return this.evtId;
	}

	public void setEvtId(String evtId) {
		this.evtId = evtId;
	}

	public Integer getEvtIsWarning() {
		return this.evtIsWarning;
	}

	public void setEvtIsWarning(Integer evtIsWarning) {
		this.evtIsWarning = evtIsWarning;
	}

	public Date getEvtTime() {
		return this.evtTime;
	}

	public void setEvtTime(Date evtTime) {
		this.evtTime = evtTime;
	}

	public String getEvtSrc() {
		return this.evtSrc;
	}

	public void setEvtSrc(String evtSrc) {
		this.evtSrc = evtSrc;
	}

	public String getEvtName() {
		return this.evtName;
	}

	public void setEvtName(String evtName) {
		this.evtName = evtName;
	}

	public String getEvtCode() {
		return this.evtCode;
	}

	public void setEvtCode(String evtCode) {
		this.evtCode = evtCode;
	}

	public String getEvtContent() {
		return this.evtContent;
	}

	public void setEvtContent(String evtContent) {
		this.evtContent = evtContent;
	}

	public String getEvtOperatorId() {
		return this.evtOperatorId;
	}

	public void setEvtOperatorId(String evtOperatorId) {
		this.evtOperatorId = evtOperatorId;
	}

	public String getEvtOperatorIp() {
		return this.evtOperatorIp;
	}

	public void setEvtOperatorIp(String evtOperatorIp) {
		this.evtOperatorIp = evtOperatorIp;
	}

	public String toString() {
		return new ToStringBuilder(this).append("evtId", getEvtId()).toString();
	}
}
