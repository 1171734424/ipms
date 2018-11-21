package com.ideassoft.bean;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

public class EventType implements Serializable {

	private static final long serialVersionUID = 5804016035562330688L;
	private Integer id;
	private String eventType;
	private String eventClass;
	private String description;

	public EventType(Integer id, String eventType, String eventClass, String description) {
		this.id = id;
		this.eventType = eventType;
		this.eventClass = eventClass;
		this.description = description;
	}

	/** default constructor */
	public EventType() {
	}

	/** minimal constructor */
	public EventType(Integer id, String eventType) {
		this.id = id;
		this.eventType = eventType;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEventType() {
		return this.eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getEventClass() {
		return this.eventClass;
	}

	public void setEventClass(String eventClass) {
		this.eventClass = eventClass;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", getId()).toString();
	}

}
