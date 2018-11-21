package com.ideassoft.basic.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.ideassoft.bean.AuditLog;
import com.ideassoft.core.dao.GenericDAOImpl;

@Service
public class AuditBasicService extends GenericDAOImpl {
	
	public List<?> getAuditpost() throws Exception {
		String sql = "selectaudit";
		return findBySQL(sql, true);
	}
	
	public List<?> getAuditgradesnew(String auditkind, String branchId) throws Exception {
		List<?> result = findBySQL("auditgradesnew", new String[] {auditkind,branchId,auditkind,branchId,auditkind,branchId,auditkind,branchId}, true);
		return result;
	}
	
	// 查询采购表信息
	public List<?> getPur(String operid) throws Exception {
		List<?> result = findBySQL("selectpurchase", new String[] { operid }, true);
		return result;
	}
	
	// 查询采购副表信息
	public List<?> getPurinfo(String operid) throws Exception {
		List<?> result = findBySQL("selectpurchasedetali", new String[] { operid }, true);
		return result;
	}
	
	public List<?> getRepairdatacloud(String operid) throws Exception {
		List<?> result = findBySQL("selectrepaircloud", new String[] { operid }, true);
		return result;
	}
	
	// 查询维修申请信息
	public List<?> getRepairdata(String operid) throws Exception {
		List<?> result = findBySQL("selectrepairdetali", new String[] { operid }, true);
		return result;
	}
	
	// 查询退房申请信息
	public List<?> getCheckdata(String operid) throws Exception {
		List<?> result = findBySQL("selectcheckputdetali", new String[] { operid }, true);
		return result;
	}

	// 查询退房申请信息
	public List<?> getCheckroomtype(String branchid, String roomid) throws Exception {
		List<?> result = findBySQL("selectcheckroomtype", new String[] { branchid, roomid }, true);
		return result;
	}
	
	public List<?> getAuditroomtype(String roomtype, String branchid) throws Exception {
		List<?> result = findBySQL("auditroomtype", new String[] { roomtype, branchid }, true);
		return result;
	}
	
	// 审核日志保存
	public void saveAuditLog(AuditLog auditLog) {
		this.save(auditLog);
	}
	
	public List<?> getAuditgrades(String auditkind,String branchidchange) throws Exception {
		List<?> result = findBySQL("auditgrades", new String[] {auditkind,branchidchange}, true);
		return result;
	}
	
	public List<?> updateRoompricebasic(String operid) throws Exception {
		List<?> result = findBySQL("auditroompricebasic", new String[] {operid}, true);
		return result;
	}
	
	public List<?> getPoststaffphone(String auditpostnow,String branchid) throws Exception {
		List<?> result = findBySQL("poststaffphone", new String[] {auditpostnow,branchid}, true);
		return result;
	}
	
	public List<?> getRpdatanew(String operid) throws Exception {
		List<?> result = findBySQL("rpdatanew", new String[] { operid },true);
		return result;
	}
	
	public List<?> getRpauditrtdata(String applybranch,String operid) throws Exception {
		List<?> result = findBySQL("rpauditrtdata", new String[] {applybranch, operid },true);
		return result;
	}
	
	public List<?> getRpidInitialize() throws Exception {
		return findBySQL("rpidInitialize", true);
	}
	
}
