package com.ideassoft.core.exception;

public class MessageException extends RuntimeException {
	private static final long serialVersionUID = 7129156393920783825L;
	private int errCode;
	transient Throwable cause = this;

	public int getErrCode() {
		return errCode;
	}

	public void setErrCode(int errCode) {
		this.errCode = errCode;
	}

	public MessageException() {
	}

	public MessageException(String message, int errCode) {
		super(message);
	}

	public MessageException(String message) {
		super(message);
	}

	public MessageException(String message, Throwable cause) {
		super(message);
		this.cause = cause;
	}
}
