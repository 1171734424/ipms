package com.ideassoft.apartment.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ideassoft.bean.AuditLog;
import com.ideassoft.bean.RoomPriceId;
import com.ideassoft.bean.Staff;
import com.ideassoft.bean.SysParam;
import com.ideassoft.core.dao.GenericDAOImpl;
import com.ideassoft.core.page.Pagination;

@Service
public class ApartmentAuditService extends GenericDAOImpl {
	// 查询默认审核人
	public List<?> queryDefaultauditDialogData(String queryData, Pagination pagination) throws Exception {
		String sql = "selectaudit";
		return findBySQLWithPagination(sql, pagination, true);
	}
	
	public List<?> getAuditpost() throws Exception {
		String sql = "selectaudit";
		return findBySQL(sql, true);
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

	// 查询采购表信息
	public List<?> getPur(String operid) throws Exception {
		/*
		 * "select * from a231 where abc = ? and baa = ?";
		 * "select * from a231 where abc = :ABC and baa = :CBC";
		 */
		/* map.put("purchaseId", purchaseId); */
		List<?> result = findBySQL("selectpurchase", new String[] { operid }, true);
		return result;
		/* map.put("CBC", aads) */
		/*
		 * String sql = ((SqlInfo) findOneByProperties(SqlInfo.class, "sqlName",
		 * "selectpurchase")).getSqlInfo()+ purchaseId + "'"; List<?> result =
		 * findBySQLWithPagination(sql, null); return result;
		 */
	}

	// 查询采购副表信息
	public List<?> getPurinfo(String operid) throws Exception {
		List<?> result = findBySQL("selectpurchasedetali", new String[] { operid }, true);
		return result;
	}

	// 查询退房申请信息
	public List<?> getCheckdata(String operid) throws Exception {
		List<?> result = findBySQL("selectcheckputdetali", new String[] { operid }, true);
		return result;
	}

	// 查询维修申请信息
	public List<?> getRepairdata(String operid) throws Exception {
		List<?> result = findBySQL("selectrepairdetali", new String[] { operid }, true);
		return result;
	}
	
	public List<?> getRepairdatacloud(String operid) throws Exception {
		List<?> result = findBySQL("selectrepaircloud", new String[] { operid }, true);
		return result;
	}

	// 查询退房申请信息
	public List<?> getCheckroomtype(String branchid, String roomid) throws Exception {
		List<?> result = findBySQL("selectcheckroomtype", new String[] { branchid, roomid }, true);
		return result;
	}

	// 审核日志保存
	public void saveAuditLog(AuditLog auditLog) {
		this.save(auditLog);
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

	public List<?> getAuditroomtype(String roomtype, String branchid) throws Exception {
		List<?> result = findBySQL("auditroomtype", new String[] { roomtype, branchid }, true);
		return result;
	}
	
	public List<?> getAuditgrades(String auditkind,String branchidchange) throws Exception {
		List<?> result = findBySQL("auditgrades", new String[] {auditkind,branchidchange}, true);
		return result;
	}
	
	public List<?> getAuditgradesnew(String auditkind, String branchId) throws Exception {
		List<?> result = findBySQL("auditgradesnew", new String[] {auditkind,branchId,auditkind,branchId,auditkind,branchId,auditkind,branchId}, true);
		return result;
	}
	
	public List<?> getDepartmanager(String branchid,String departid) throws Exception {
		List<?> result = findBySQL("departmanager", new String[] {branchid,departid}, true);
		return result;
	}
	public List<?> getPoststaffphone(String auditpostnow,String branchid) throws Exception {
		List<?> result = findBySQL("poststaffphone", new String[] {auditpostnow,branchid}, true);
		return result;
	}
	
	public List<?> updateRoompricebasic(String operid) throws Exception {
		List<?> result = findBySQL("auditroompricebasic", new String[] {operid}, true);
		return result;
	}
	public List<?> queryDistrictMaxSeq(String cityCode,String rank) throws Exception {
		List<?> result = findBySQL("queryDistrictMaxSeq", new String[] {cityCode,rank}, true);
		return result;
	}
	/*public List<?> queryStreetMaxSeq(String code,String rank) throws Exception {
		List<?> result = findBySQL("queryStreetMaxSeq", new String[] {code,rank}, true);
		return result;
	}*/
	
}
