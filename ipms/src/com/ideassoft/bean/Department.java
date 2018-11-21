package com.ideassoft.bean;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ideassoft.core.annotation.interfaces.ColumnDefaultValue;

@Entity
@Table(name = "TB_C_DEPARTMENT", schema = "UCR_PMS")
public class Department implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 1704713169984407847L;
	private DepartmentId departmentId;
	private String departName;
	private String departLevel;
	private String superDepart;
	private String recordUser;
	private Date recordTime;
	private String status;
	private String remark;
	private String manager;

	// Constructors

	/** default constructor */
	public Department() {
	}

	/** minimal constructor */
	public Department(DepartmentId departmentId, String departName, String departLevel, String recordUser, String status) {
		this.departmentId = departmentId;
		this.departName = departName;
		this.departLevel = departLevel;
		this.recordUser = recordUser;
		this.status = status;
	}

	/** full constructor */
	public Department(DepartmentId departmentId, String departName, String departLevel, String superDepart,
			String recordUser, Date recordTime, String status, String remark) {
		this.departmentId = departmentId;
		this.departName = departName;
		this.departLevel = departLevel;
		this.superDepart = superDepart;
		this.recordUser = recordUser;
		this.recordTime = recordTime;
		this.status = status;
		this.remark = remark;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "branchId", column = @Column(name = "BRANCH_ID", nullable = false, length = 6)),
			@AttributeOverride(name = "departId", column = @Column(name = "DEPART_ID", nullable = false, length = 4)) })
	public DepartmentId getDepartmentId() {
		return this.departmentId;
	}

	public void setDepartmentId(DepartmentId departmentId) {
		this.departmentId = departmentId;
	}

	@Column(name = "DEPART_NAME", nullable = false, length = 30)
	public String getDepartName() {
		return this.departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}

	@Column(name = "DEPART_LEVEL", nullable = false, length = 1)
	public String getDepartLevel() {
		return this.departLevel;
	}

	public void setDepartLevel(String departLevel) {
		this.departLevel = departLevel;
	}

	@Column(name = "SUPER_DEPART", length = 4)
	public String getSuperDepart() {
		return this.superDepart;
	}

	public void setSuperDepart(String superDepart) {
		this.superDepart = superDepart;
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

	@Column(name = "STATUS", nullable = false, length = 1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "REMARK", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "MANAGER", length = 8)
	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

}