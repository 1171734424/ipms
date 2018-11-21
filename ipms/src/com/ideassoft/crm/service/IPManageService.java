package com.ideassoft.crm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ideassoft.bean.IPManage;
import com.ideassoft.core.dao.GenericDAOImpl;

@Service
public class IPManageService extends GenericDAOImpl {
	/**
	 * 根据用户名获取IP过滤列表
	 * 
	 * @param username
	 * @param listtype
	 * @param flag
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<IPManage> getIPNameList(String userId, Integer listtype, boolean flag) {
		String hql = " from o in class " + IPManage.class.getName() + " where o.userName = ?";
		hql += listtype != null ? (" and o.listType = ?") : "";
		hql += flag ? " and o.ipAddress is null" : "";

		Object[] os = listtype != null ? new Object[] { userId, listtype } : new Object[] { userId };
		return findByHQL(hql, os);
	}

	/**
	 * 根据名单类型查询
	 * 
	 * @param listtype
	 */
	@SuppressWarnings("unchecked")
	public List<IPManage> getIPNameListByType(Integer listtype, String args) {
		String hql = " from o in class " + IPManage.class.getName() + " where o.listType = ?";
		hql += args != null && !"".equals(args) ? " and (userId like '%" + args + "%' or ipAddress like '%" + args
				+ "%')" : "";

		return findByHQL(hql, new Object[] { listtype });
	}

	/**
	 * 获取最大ID
	 */
	@SuppressWarnings("unchecked")
	public Integer getMaxFilterID() {
		String hql = " from o in class " + IPManage.class.getName() + " order by 1 desc";
		List<IPManage> list = findByHQL(hql);
		return list != null && list.size() > 0 ? list.get(0).getFilterId() : 100001;
	}

}
