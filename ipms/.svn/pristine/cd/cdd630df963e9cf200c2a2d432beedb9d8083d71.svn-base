package com.ideassoft.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ideassoft.core.annotation.interfaces.ColumnDefaultValue;

@Entity
@Table(name = "TB_P_TIPS", schema = "UCR_PMS")
public class Tips implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 8733252037740652219L;
	private String logId;
	private String checkId;
	private String content;
	private String recordUser;
	private Date recordTime;
	private String reader;
	private Date readTime;
	private String status;
	private String type;

	// Constructors

	/** default constructor */
	public Tips() {
	}

	/** minimal constructor */
	public Tips(String logId, String checkId, String content, String recordUser, String status, String type) {
		this.logId = logId;
		this.checkId = checkId;
		this.content = content;
		this.recordUser = recordUser;
		this.status = status;
		this.type = type;
	}

	/** full constructor */
	public Tips(String logId, String checkId, String content, String recordUser, Date recordTime, String reader,
			Date readTime, String status, String type) {
		this.logId = logId;
		this.checkId = checkId;
		this.content = content;
		this.recordUser = recordUser;
		this.recordTime = recordTime;
		this.reader = reader;
		this.readTime = readTime;
		this.status = status;
		this.type = type;
	}

	// Property accessors
	@Id
	@Column(name = "LOG_ID", unique = true, nullable = false, length = 9)
	public String getLogId() {
		return this.logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	@Column(name = "CHECK_ID", nullable = false, length = 14)
	public String getCheckId() {
		return this.checkId;
	}

	public void setCheckId(String checkId) {
		this.checkId = checkId;
	}

	@Column(name = "CONTENT", nullable = false, length = 200)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "RECORD_USER", nullable = false, length = 8)
	public String getRecordUser() {
		return this.recordUser;
	}

	public void setRecordUser(String recordUser) {
		this.recordUser = recordUser;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RECORD_TIME", insertable = false, updatable = true)
	@ColumnDefaultValue(value = "sysdate", insertable = false)
	public Date getRecordTime() {
		return this.recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	@Column(name = "READER", length = 8)
	public String getReader() {
		return this.reader;
	}

	public void setReader(String reader) {
		this.reader = reader;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "READ_TIME", length = 7)
	public Date getReadTime() {
		return this.readTime;
	}

	public void setReadTime(Date readTime) {
		this.readTime = readTime;
	}

	@Column(name = "STATUS", nullable = false, length = 1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "TYPE", length = 1)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}