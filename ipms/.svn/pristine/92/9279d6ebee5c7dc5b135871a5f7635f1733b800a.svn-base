package com.ideassoft.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TP_C_SQLINFO", schema = "UCR_PMS")
public class SqlInfo implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = -8429690721861531040L;
	private String sqlName;
	private String sqlInfo;
	private String dbType;
	private String remark;

	// Constructors

	/** default constructor */
	public SqlInfo() {
	}

	/** minimal constructor */
	public SqlInfo(String sqlName, String sqlInfo, String dbType) {
		this.sqlName = sqlName;
		this.sqlInfo = sqlInfo;
		this.dbType = dbType;
	}

	/** full constructor */
	public SqlInfo(String sqlName, String sqlInfo, String dbType, String remark) {
		this.sqlName = sqlName;
		this.sqlInfo = sqlInfo;
		this.dbType = dbType;
		this.remark = remark;
	}

	// Property accessors
    @Id
	@Column(name = "SQL_NAME", nullable = false, length = 20)
	public String getSqlName() {
		return this.sqlName;
	}

	public void setSqlName(String sqlName) {
		this.sqlName = sqlName;
	}

	@Column(name = "SQL_INFO", nullable = false, length = 2000)
	public String getSqlInfo() {
		return this.sqlInfo;
	}

	public void setSqlInfo(String sqlInfo) {
		this.sqlInfo = sqlInfo;
	}

	@Column(name = "DB_TYPE", nullable = false, length = 20)
	public String getDbType() {
		return this.dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	@Column(name = "REMARK", length = 400)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}