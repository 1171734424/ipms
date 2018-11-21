package com.ideassoft.bean.imports;

import java.io.Serializable;

public class ErrorInfo implements Serializable {

	private static final long serialVersionUID = 3147258720082193491L;

	private String errorLocation;

	private Integer postion;

	private String title;

	private String errorData;

	private String errorMsg;

	public ErrorInfo() {

	}

	public ErrorInfo(String errorLocation, Integer postion, String title, String errorData, String errorMsg) {
		this.errorLocation = errorLocation;
		this.postion = postion;
		this.title = title;
		this.errorData = errorData;
		this.errorMsg = errorMsg;
	}

	public void setErrorLocation(String errorLocation) {
		this.errorLocation = errorLocation;
	}

	public String getErrorLocation() {
		return errorLocation;
	}

	public void setPostion(Integer postion) {
		this.postion = postion;
	}

	public Integer getPostion() {
		return postion;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setErrorData(String errorData) {
		this.errorData = errorData;
	}

	public String getErrorData() {
		return errorData;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

}
