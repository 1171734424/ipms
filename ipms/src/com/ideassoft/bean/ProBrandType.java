package com.ideassoft.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TB_C_PROBRANDTYPE", schema = "UCR_PMS")
public class ProBrandType implements java.io.Serializable {


	private static final long serialVersionUID = 857289753806678747L;
	private String typeId;
	private String typeName;
	private String remark;
	private String status;

	@Id
	@Column(name = "TYPE_ID", unique = true, nullable = false, length = 6)
	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	@Column(name = "TYPE_NAME", nullable = false, length = 12)
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	@Column(name = "REMARK", nullable = false, length = 30)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(name = "STATUS", length = 2)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
