package com.ideassoft.core.exception;

public class TaskException extends Exception {
	private static final long serialVersionUID = 2443023851779281965L;

	public TaskException(String message) {
		super(message);
	}

	public TaskException(String message, Throwable cause) {
		super(message, cause);
	}

}
