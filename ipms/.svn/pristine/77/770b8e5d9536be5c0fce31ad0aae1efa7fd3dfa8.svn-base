package com.ideassoft.ems.service;

import java.util.List;

import org.hsqldb.lib.StringUtil;
import org.springframework.stereotype.Service;

import com.ideassoft.core.dao.GenericDAOImpl;
import com.ideassoft.core.page.Pagination;

@Service
public class EmsService  extends GenericDAOImpl {

	public List<?> queryAllRepairDetail() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<?> queryEquipRepairInDetailByCon(String serialNo,String queryTimeStart, String queryTimeEnd, String queryStatus,Pagination pagination) {
		if (queryStatus == null || StringUtil.isEmpty(queryStatus)){
			queryStatus = "%";
		}
		List<?> result = findBySQLWithPagination("equipRepairInDetail", new String[] { serialNo, queryStatus,
				queryTimeStart, queryTimeEnd}, pagination, true);
		return result;
	}
	
	public List<?> getWorkbill(String branchid,String loginstaff) throws Exception {
		List<?> result = findBySQL("gdsworkbill", new String[] { branchid }, true);
		return result;
	}
		
}
