package com.ideassoft.crm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ideassoft.bean.City;
import com.ideassoft.bean.Location;
import com.ideassoft.bean.SysParam;
import com.ideassoft.core.dao.GenericDAOImpl;
import com.ideassoft.core.page.Pagination;

@Service
public class ManageService extends GenericDAOImpl {
	
	public List<City> queryCity(){
		String cityRank="1";
		return this.findByProperties(City.class, "rank",cityRank,"status","1");
	}
	

	public List<?> queryAllOfRoomType(String branchId) {
		String hql = "from RoomType where roomTypeKey.branchId = '"+ branchId+"'";
		return findByHQL(hql, new String[] {});
	}

	public List<?> queryAllOfTemplateType(String paramType) {
		String hql = "from SysParam where paramType = ?";
		return findByHQL(hql, new String[] { paramType });
	}

	/*
	 * public List<?> querySmsInDetailByCon(String templateId, String
	 * queryRecipentNo, String querySendTimeStart, String querySendTimeEnd,
	 * String queryBranchId, String queryStatus, Pagination pagination) throws
	 * Exception { String sql = ((SqlInfo) findOneByProperties(SqlInfo.class,
	 * "sqlName", "smsInDetail")).getSqlInfo() + templateId + "'"; if
	 * (queryStatus != null && !StringUtil.isEmpty(queryStatus.trim())) { sql =
	 * sql + " and l.status = '" + queryStatus + "'"; } if (queryRecipentNo !=
	 * null && !StringUtil.isEmpty(queryRecipentNo.trim())) { sql = sql +
	 * " and l.sms_recipentno like '%" + queryRecipentNo + "%'"; } if
	 * (querySendTimeStart != null &&
	 * !StringUtil.isEmpty(querySendTimeStart.trim()) && querySendTimeEnd !=
	 * null && !StringUtil.isEmpty(querySendTimeEnd.trim())) { sql = sql +
	 * " and l.send_time <= to_Date('" + querySendTimeEnd +
	 * "','yyyy/MM/dd HH24:mi:ss') and l.send_time >= to_Date('" +
	 * querySendTimeStart + "','yyyy/MM/dd HH24:mi:ss')"; } if (queryBranchId !=
	 * null && !StringUtil.isEmpty(queryBranchId.trim())) { sql = sql +
	 * " and l.branch_id = '" + queryBranchId + "'"; } sql = sql +
	 * " order by l.send_time desc"; List<?> result =
	 * findBySQLWithPagination(sql, pagination); return result; }
	 */

	public List<?> querySmsInDetailByCon(String templateId, String queryRecipentNo, String querySendTimeStart,
			String querySendTimeEnd, String queryBranchId, String queryStatus, Pagination pagination) throws Exception {
		List<?> result = findBySQLWithPagination("smsInDetail222", new String[] { templateId, queryStatus,
				queryRecipentNo, querySendTimeEnd, querySendTimeStart, queryBranchId }, pagination, true);
		return result;
	}
	
	public List<?> findHouseManagerName(String houseid) {
		List<?> result = findBySQL("selecthouseandtrans", new String[] {houseid }, true);
		return result;
	}
	

	public void deleteAllHousePrice(String branchId){
		String sql = "delete from tb_p_price_volatility where branch_id = '" + branchId + "'";
		this.executeUpdateSQL(sql);
	}
	
	

}
