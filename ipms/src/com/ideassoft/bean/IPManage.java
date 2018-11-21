package com.ideassoft.bean;

import java.io.Serializable;

/**
 * IP过滤
 * 
 * @author ZhengXing
 */

public class IPManage implements Serializable {

	private static final long serialVersionUID = 8028360929340309420L;

	private Integer filterId;

	private String userId;

	// IP地址 可使用两种方式
	// 1. 192.168.0.2 用于单IP的验证, 如添加申请开通的IP
	// 2. 192.168.0.0/25 含子网掩码, 用于整个网段的验证, 掩码说明见文件底部
	private String ipAddress;

	// 名单类型 0 黑名单, 1 白名单, 2 等待开通
	private Integer listType;

	private String remark;

	public void setFilterId(Integer filterId) {
		this.filterId = filterId;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public void setListType(Integer listType) {
		this.listType = listType;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getFilterId() {
		return filterId;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public String getUserId() {
		return userId;
	}

	public Integer getListType() {
		return listType;
	}

	public String getRemark() {
		return remark;
	}

	/**
	 * 子网掩码: A类 255.0.0.0 /8 255.128.0.0 /9 255.192.0.0 /10 255.224.0.0 /11
	 * 255.240.0.0 /12 255.248.0.0 /13 255.252.0.0 /14 255.254.0.0 /15
	 * 
	 * B类 255.255.0.0 /16 255.255.128.0 /17 255.255.192.0 /18 255.255.224.0 /19
	 * 255.255.240.0 /20 255.255.248.0 /21 255.255.252.0 /22 255.255.254.0 /23
	 * 
	 * C类 255.255.255.0 /24 255.255.255.128 /25 255.255.255.192 /26
	 * 255.255.255.224 /27 255.255.255.240 /28 255.255.255.248 /29
	 * 255.255.255.252 /30
	 * 
	 * 私有IP地址: A 类私有IP地址的范围从10.0.0.0 到10.255.255.255 B 类私有IP地址的范围从172.16.0.0
	 * 到172.31.255.255 C 类私有IP地址的范围从192.168.0.0 到192.168.255.255
	 */

}
