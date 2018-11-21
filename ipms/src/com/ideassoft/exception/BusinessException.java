package com.ideassoft.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BusinessException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7269676833686849300L;

	private static final Logger logger = LoggerFactory.getLogger(BusinessException.class);
	
	protected String errorCode;
	protected String[] errorMessageArguments;
	
	
	protected BusinessException() {
		this("");
	}

	public BusinessException(String message) {
		super(message);
		this.errorCode = "fail";
		this.errorMessageArguments = new String[0];
	}
	
	public static BusinessException withErrorCode(String errorCode) {
		BusinessException businessException = new BusinessException();
		businessException.errorCode = errorCode;
		return businessException;
	}

}
