package com.ideassoft.bean;

import java.util.Date;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.ideassoft.core.annotation.interfaces.ColumnDefaultValue;

@Entity
@Table(name = "TP_C_ROLERELATION", schema = "UCR_PMS")
public class RoleRelation implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = -8171449437975231167L;
	private String dataId;
	private String roleId;
	private String funcrightId;
	private String datarights;
	private String recordUser;
	private Date recordTime;
	private String status;

	private Map<String, String> datarightMap;

	// Constructors

	/** default constructor */
	public RoleRelation() {
	}

	/** minimal constructor */
	public RoleRelation(String dataId, String roleId, String funcrightId,
			String datarights, String recordUser, String status) {
		this.dataId = dataId;
		this.roleId = roleId;
		this.funcrightId = funcrightId;
		this.datarights = datarights;
		this.recordUser = recordUser;
		this.status = status;
	}

	/** full constructor */
	public RoleRelation(String dataId, String roleId, String funcrightId,
			String datarights, String recordUser, Date recordTime, String status) {
		this.dataId = dataId;
		this.roleId = roleId;
		this.funcrightId = funcrightId;
		this.datarights = datarights;
		this.recordUser = recordUser;
		this.recordTime = recordTime;
		this.status = status;
	}

	// Property accessors
	@Id
	@Column(name = "DATA_ID", unique = true, nullable = false, length = 8)
	public String getDataId() {
		return this.dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	@Column(name = "ROLE_ID", nullable = false, length = 8)
	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	@Column(name = "FUNCRIGHT_ID", nullable = false, length = 20)
	public String getFuncrightId() {
		return this.funcrightId;
	}

	public void setFuncrightId(String funcrightId) {
		this.funcrightId = funcrightId;
	}

	@Column(name = "DATARIGHTS", nullable = true, length = 100)
	public String getDatarights() {
		return this.datarights;
	}

	public void setDatarights(String datarights) {
		this.datarights = datarights;
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

	@Column(name = "STATUS", precision = 1, scale = 0, insertable = false, updatable = false)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setDatarightMap(Map<String, String> datarightMap) {
		this.datarightMap = datarightMap;
	}

	@Transient
	public Map<String, String> getDatarightMap() {
		return datarightMap;
	}
}