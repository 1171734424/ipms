package com.ideassoft.bean;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.ideassoft.core.annotation.interfaces.ColumnDefaultValue;

@Entity
@Table(name = "TP_C_ROLE", schema = "UCR_PMS")
public class Role implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 8507091909609090553L;
	private String roleId;
	private String roleName;
	private String recordUser;
	private Date recordTime;
	private String status;
	private String remark;
	private String branchId;

	private List<RoleRelation> roleRelation;

	// Constructors

	/** default constructor */
	public Role() {
	}

	/** minimal constructor */
	public Role(String roleId, String roleName, String recordUser, String status, String remark, String branchId) {
		this.roleId = roleId;
		this.roleName = roleName;
		this.recordUser = recordUser;
		this.status = status;
		this.remark = remark;
		this.branchId = branchId;
	}

	/** full constructor */
	public Role(String roleId, String roleName, String recordUser, Date recordTime, String status, String remark, String branchId) {
		this.roleId = roleId;
		this.roleName = roleName;
		this.recordUser = recordUser;
		this.recordTime = recordTime;
		this.status = status;
		this.remark = remark;
		this.branchId = branchId;
	}

	// Property accessors
	@Id
	@Column(name = "ROLE_ID", unique = true, nullable = false, length = 8)
	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	@Column(name = "ROLE_NAME", nullable = false, length = 20)
	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
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

	@Column(name = "STATUS", nullable = false, length = 1, updatable = false, insertable = false)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "REMARK", nullable = true, length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(name = "BRANCHID", nullable = false, length = 10)
	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public void setRoleRelation(List<RoleRelation> roleRelation) {
		this.roleRelation = roleRelation;
	}

	@OneToMany(targetEntity = RoleRelation.class, fetch = FetchType.EAGER, mappedBy = "roleId")
	@Cascade(value = CascadeType.DELETE)
	public List<RoleRelation> getRoleRelation() {
		return roleRelation;
	}

}