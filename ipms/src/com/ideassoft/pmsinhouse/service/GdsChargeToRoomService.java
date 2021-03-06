package com.ideassoft.pmsinhouse.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ideassoft.core.dao.GenericDAOImpl;
import com.ideassoft.core.page.Pagination;

@Service
public class GdsChargeToRoomService extends GenericDAOImpl {
	
	public List<?> getCategorycondition(String branchid) throws Exception {
		List<?> result = findBySQL("categorycondition", new String[] { branchid }, true);
		return result;
	}
	
	public List<?> getGdsaleconditionInHouse(String goodsid, String goodsname, String categoryid, String branchId,
			Pagination pagination) throws Exception {
		List<?> result = findBySQLWithPagination("gdsalecondition", new String[] { branchId, goodsid, goodsname, categoryid,goodsid, goodsname, categoryid
				 }, pagination, true);
		return result;
	}
	
	public List<?> getGdsprojectInHouse() throws Exception {
		String sql = "gdsproject";
		return findBySQL(sql, true);
	}
	
	public List<?> getWorkbillInHouse(String branchid,String loginstaff) throws Exception {
		List<?> result = findBySQL("gdsworkbill", new String[] { branchid }, true);
		return result;
	}
	
	public List<?> getGdmanageconditionInHouse(String goodsid, String goodsname, String categoryid, String status,
			String branchId, Pagination

			pagination) throws Exception {
		List<?> result = findBySQLWithPagination("gdmanagecondition", new String[] { goodsid, goodsname, categoryid,
				status, branchId,goodsid, goodsname, categoryid,status }, pagination, true);
		return result;
	}
	
	public List<?> getGoodssaleInHouse(Pagination pagination, String branchId) throws Exception {
		List<?> result = findBySQLWithPagination("goodssaledata", new String[] { branchId }, pagination, true);
		return result;
	}
	
	public List<?> getGdsroomidInHouse(String branchId,String theme) throws Exception {
		List<?> result = findBySQL("gdsroomid", new String[] { branchId, branchId, branchId }, true);
		return result;
	}
	
	/**
	 * 商品售卖查询项目（结算）
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<?> getGdsprojectpayInHouse() throws Exception {
		String sql = "gdsprojectpay";
		return findBySQL(sql, true);
	}
	


}
