package com.ideassoft.bean;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class LoginLogId implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 9094489172064599561L;
	private String logId;
	private String loginId;

	// Constructors

	/** default constructor */
	public LoginLogId() {
	}

	/** full constructor */
	public LoginLogId(String logId, String loginId) {
		this.logId = logId;
		this.loginId = loginId;
	}

	// Property accessors

	@Column(name = "LOG_ID", nullable = false, length = 13)
	public String getLogId() {
		return this.logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	@Column(name = "LOGIN_ID", nullable = false, length = 8)
	public String getLoginId() {
		return this.loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof LoginLogId))
			return false;
		LoginLogId castOther = (LoginLogId) other;

		return ((this.getLogId() == castOther.getLogId()) || (this.getLogId() != null && castOther.getLogId() != null && this
				.getLogId().equals(castOther.getLogId())))
				&& ((this.getLoginId() == castOther.getLoginId()) || (this.getLoginId() != null
						&& castOther.getLoginId() != null && this.getLoginId().equals(castOther.getLoginId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getLogId() == null ? 0 : this.getLogId().hashCode());
		result = 37 * result + (getLoginId() == null ? 0 : this.getLoginId().hashCode());
		return result;
	}

}