package com.ideassoft.core.notifier.bean;

import java.io.Serializable;
import java.util.Date;

import com.ideassoft.bean.Event;

public class ZEvent implements Serializable {

	private static final long serialVersionUID = -3198605165270411798L;

	public ZEvent() {

	}

	public ZEvent(String eventId, Integer evtIsWarning, Date eventHappendTime,
			String eventSource, String eventOperator, String eventContent,
			String eventCode, String eventOperIPAddr, String eventName) {
		this.eventID = eventId;
		this.evtIsWarning = evtIsWarning;
		this.eventHappenTime = eventHappendTime.getTime();
		this.eventSource = eventSource;
		this.eventOperator = eventOperator;
		this.eventContent = eventContent;
		this.eventCode = eventCode;
		this.eventOperIPAddr = eventOperIPAddr;
		this.eventName = eventName;
	}

	public ZEvent(Integer evtIsWarning, String eventSource, String eventContent) {
		this.evtIsWarning = evtIsWarning;
		this.eventSource = eventSource;
		this.eventContent = eventContent;
	}

	// 事件唯一编码
	private String eventID;

	// 事件类型唯一编码
	private int evtIsWarning;

	private String eventOperIPAddr;
	//	
	// 事件发生的时间
	private long eventHappenTime;

	// 事件来源
	private String eventSource;

	// 事件的操作者
	private String eventOperator;

	// 事件的详细描述
	private String eventContent;

	// 事件名称
	private String eventName;

	// 事件代码
	private String eventCode;

	// 事件类
	private String eventClass;

	private String[] splitDescs;

	public String getEventID() {
		return eventID;
	}

	public void setEventID(String eventID) {
		this.eventID = eventID;
	}

	public long getEventHappenTime() {
		return eventHappenTime;
	}

	public void setEventHappenTime(long eventHappenTime) {
		this.eventHappenTime = eventHappenTime;
	}

	public String getEventSource() {
		return eventSource;
	}

	public void setEventSource(String eventSource) {
		this.eventSource = eventSource;
	}

	public String getEventOperator() {
		return eventOperator;
	}

	public void setEventOperator(String eventOperator) {
		this.eventOperator = eventOperator;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getEventCode() {
		return eventCode;
	}

	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}

	public String getEventOperIPAddr() {
		return eventOperIPAddr;
	}

	public void setEventOperIPAddr(String eventOperIPAddr) {
		this.eventOperIPAddr = eventOperIPAddr;
	}

	public int getEvtIsWarning() {
		return evtIsWarning;
	}

	public void setEvtIsWarning(int evtIsWarning) {
		this.evtIsWarning = evtIsWarning;
	}

	public String getEventContent() {
		return eventContent;
	}

	public void setEventContent(String eventContent) {
		this.eventContent = eventContent;
	}

	public String getEventClass() {
		return eventClass;
	}

	public void setEventClass(String eventClass) {
		this.eventClass = eventClass;
	}

	public void setSplitDescs(String... splitDescs) {
		this.splitDescs = splitDescs;
	}

	public String[] getSplitDescs() {
		return splitDescs;
	}

	public Event getTblDeventMoudel() {
		Event event = new Event();
		event.setEvtCode(eventCode);
		event.setEvtContent(eventContent);
		event.setEvtId(eventID);
		event.setEvtIsWarning(evtIsWarning);
		event.setEvtName(eventName);
		event.setEvtOperatorId(eventOperator);
		event.setEvtOperatorIp(eventOperIPAddr);
		event.setEvtSrc(eventSource);
		event.setEvtTime(new Date(eventHappenTime));

		return event;

	}
}
