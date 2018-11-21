package com.ideassoft.core.exception;

import org.springframework.dao.DataAccessException;

public class DBAccessException extends DataAccessException {
	private static final long serialVersionUID = 2147854608939544789L;

	private String sql = null;

	private int errCode = 0;

	public final int getErrCode() {

		return errCode;
	}

	public final void setErrCode(int errCode) {
		this.errCode = errCode;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public DBAccessException(String msg) {
		super(msg);
	}

	public DBAccessException(String msg, int errCode) {
		this(msg, null, errCode);
	}

	public DBAccessException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public DBAccessException(String msg, Throwable cause, int errcode) {
		super(msg, cause);
		this.errCode = errcode;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer("DBAccessException");
		sb.append("错误代码:").append(this.errCode).append("\r\n");
		sb.append(getMessage());
		sb.append(super.toString());

		return sb.toString();
	}
}
