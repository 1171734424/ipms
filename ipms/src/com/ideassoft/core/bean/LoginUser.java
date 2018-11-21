package com.ideassoft.core.bean;

import java.io.Serializable;
import java.util.List;

import com.ideassoft.bean.Role;
import com.ideassoft.bean.Staff;

public class LoginUser implements Serializable {

	private static final long serialVersionUID = 4972255667339759842L;

	private Staff staff;

	private List<Role> rightsList;
	
	private Integer systemType;
	
	private Long recordBeginTime;
	
	private String sessionId;
	
	private String ipAddress;

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public void setRightsList(List<Role> rightsList) {
		this.rightsList = rightsList;
	}

	public void setRecordBeginTime(Long recordBeginTime) {
		this.recordBeginTime = recordBeginTime;
	}
	
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	public Staff getStaff() {
		return staff;
	}

	public List<Role> getRightsList() {
		return rightsList;
	}
	
	public Long getRecordBeginTime() {
		return recordBeginTime;
	}
	
	public String getSessionId() {
		return sessionId;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setSystemType(Integer systemType) {
		this.systemType = systemType;
	}

	public Integer getSystemType() {
		return systemType;
	}
}
