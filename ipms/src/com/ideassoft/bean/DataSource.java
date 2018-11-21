package com.ideassoft.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TB_C_DATARESOURCE", schema = "UCR_PMS")
public class DataSource implements java.io.Serializable{
	
	private static final long serialVersionUID = 4995150042220690275L;
	private String dataId;
	private String memberId;
	private String name;
	private String status;
	private String remark;
	public DataSource() {
		
	}
	public DataSource(String dataId, String memberId, String name, String status) {
	
		this.dataId = dataId;
		this.memberId = memberId;
		this.name = name;
		this.status = status;
	}
	public DataSource(String dataId, String memberId, String name,
			String status, String remark) {
		
		this.dataId = dataId;
		this.memberId = memberId;
		this.name = name;
		this.status = status;
		this.remark = remark;
	}
	@Id
	@Column(name = "DATA_ID", unique = true, nullable = false, length = 6)
	public String getDataId() {
		return dataId;
	}
	public void setDataId(String dataId) {
		this.dataId = dataId;
	}
	@Column(name = "MEMBER_ID", nullable = false,length = 8)
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	@Column(name = "NAME", nullable = false,length = 16)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(name = "STATUS", nullable = false,length = 1)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Column(name = "REMARK",length = 200)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
