package com.ideassoft.core.notifier.bean;

import java.io.Serializable;

public class ZEventType implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4643718447823018813L;
	
	/**
	 * 事件类型ID
	 */
	private int eventTypeID;

	public int getEventTypeID() {
		return eventTypeID;
	}

	public void setEventTypeID(int eventTypeID) {
		this.eventTypeID = eventTypeID;
	} 
	
	/**
	 * 事件类型名称
	 */
	private String eventTypeName;

	public String getEventTypeName() {
		return eventTypeName;
	}

	public void setEventTypeName(String eventTypeName) {
		this.eventTypeName = eventTypeName;
	}
	
	/**
	 * 事件类型描述
	 */
	private String eventTypeDesc;

	public String getEventTypeDesc() {
		return eventTypeDesc;
	}

	public void setEventTypeDesc(String eventTypeDesc) {
		this.eventTypeDesc = eventTypeDesc;
	}
}
