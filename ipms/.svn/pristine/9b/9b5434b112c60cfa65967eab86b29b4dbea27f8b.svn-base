package com.ideassoft.bean;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TP_C_SYSPARAM", schema = "UCR_PMS")
public class SysParam implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = -2137532036178475790L;
	private String paramId;
	private String paramType;
	private String paramName;
	private String paramDesc;
	private String content;
	private String orderNo;
	private String status;
	private String remark;
	private String parameter1;

	// Constructors

	/** default constructor */
	public SysParam() {
	}

	/** minimal constructor */
	public SysParam(String paramId, String paramType, String paramName, String content, String status) {
		this.paramId = paramId;
		this.paramType = paramType;
		this.paramName = paramName;
		this.content = content;
		this.status = status;
	}

	/** full constructor */
	public SysParam(String paramId, String paramType, String paramName, String paramDesc, String content,
			String orderNo, String status,String remark,String parameter1) {
		this.paramId = paramId;
		this.paramType = paramType;
		this.paramName = paramName;
		this.paramDesc = paramDesc;
		this.content = content;
		this.orderNo = orderNo;
		this.status = status;
		this.remark = remark;
		this.parameter1 = parameter1;
	}

	// Property accessors
	@Id
	@Column(name = "PARAM_ID", unique = true, nullable = false, length = 8)
	public String getParamId() {
		return this.paramId;
	}

	public void setParamId(String paramId) {
		this.paramId = paramId;
	}

	@Column(name = "PARAM_TYPE", nullable = false, length = 20)
	public String getParamType() {
		return this.paramType;
	}

	public void setParamType(String paramType) {
		this.paramType = paramType;
	}

	@Column(name = "PARAM_NAME", nullable = false, length = 30)
	public String getParamName() {
		return this.paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	@Column(name = "PARAM_DESC", length = 100)
	public String getParamDesc() {
		return this.paramDesc;
	}

	public void setParamDesc(String paramDesc) {
		this.paramDesc = paramDesc;
	}

	@Column(name = "CONTENT", nullable = false, length = 500)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "ORDER_NO", length =10, nullable = true)
	public String getOrderNo() {
		return this.orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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
	
	@Column(name = "PARAMETER1", length = 30, nullable = true)
	public String getParameter1() {
		return parameter1;
	}

	public void setParameter1(String parameter1) {
		this.parameter1 = parameter1;
	}

}