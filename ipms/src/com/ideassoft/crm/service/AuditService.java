package com.ideassoft.crm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ideassoft.bean.AuditLog;
import com.ideassoft.bean.RoomPriceId;
import com.ideassoft.bean.Staff;
import com.ideassoft.bean.SysParam;
import com.ideassoft.core.dao.GenericDAOImpl;
import com.ideassoft.core.page.Pagination;

@Service
public class AuditService extends GenericDAOImpl {
	// 查询默认审核人
	public List<?> queryDefaultauditDialogData(String queryData, Pagination pagination) throws Exception {
		String sql = "selectaudit";
		return findBySQLWithPagination(sql, pagination, true);
	}
	

	public Object getAudit() throws Exception {
		return findOneByProperties(SysParam.class, "paramType", "AUDIT", "paramName", "默认审核人");
	}

	public Object getAuditName(String auditid) throws Exception {
		return findOneByProperties(Staff.class, "staffId", auditid);
	}

	/*
	 * public List<?> querysaleType() throws Exception { List<?> result =
	 * findBySQL("selectpurchase", new String[] { purchaseId }, true); return
	 * result; }
	 */

	public List<?> querysaleType() throws Exception {
		String sql = "querysaletype";
		return findBySQL(sql, true);
	}

	public void upateroompricedata(String bid, String rid, String tid, String rt, String aurecordUser, String status) {
		RoomPriceId roomPriceKey = new RoomPriceId();
		roomPriceKey.setBranchId(bid);
		roomPriceKey.setRoomType(rt);
		roomPriceKey.setRpId(rid);
		String hql1 = "update RoomPrice set status = :STATUS,recordUser = :RECORDUSER where roomPriceKey = :ROOMPRICEKEY";
		this.executeUpdateHQL(hql1, new String[] { "STATUS", "ROOMPRICEKEY", "RECORDUSER" }, new Object[] { status,
				roomPriceKey, aurecordUser });
	}

	public List<?> getDepartmanager(String branchid,String departid) throws Exception {
		List<?> result = findBySQL("departmanager", new String[] {branchid,departid}, true);
		return result;
	}
	
	/*public List<?> queryStreetMaxSeq(String code,String rank) throws Exception {
		List<?> result = findBySQL("queryStreetMaxSeq", new String[] {code,rank}, true);
		return result;
	}*/
	
}
