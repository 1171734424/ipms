package com.ideassoft.pms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ideassoft.core.dao.GenericDAOImpl;
import com.ideassoft.core.page.Pagination;

@Service
public class WorkService extends GenericDAOImpl {

	// 工作账日志
	public List<?> getworkbillLog(String workbillid, Pagination paginatin) {
		String sql = " select r.check_id workbillid,"
				+ "    '工作账单' a,"
				+ "    transTitles(r.record_user, 'TB_C_STAFF', 'staff_id', 'staff_name') recorduser,"
				+ "    nvl(r.record_time,'')  recordtime,"
				+ "    ('新增入账日志   入账号，新值：' || r.record_id || '；工作账号，新值：' || r.check_id ||"
				+ "    '；门店编号，新值：' || r.branch_id || '；入账类型，新值：' || r.record_type ||"
				+ "    '；项目编号，新值：' || r.project_id || '；金额，新值：' || r.fee ||"
				+ "    '；备注，新值：' || nvl(r.remark,'')) content from TL_P_RECORDING r where r.record_type = '2' and r.check_id= '"
				+ workbillid + "' order by r.record_time desc";
		return findBySQLWithPagination(sql, paginatin);
	}

	public void updateworkbill(String name, String describe, String workbillId) {
		String hql = "update WorkBill set name = :NAME, describe = :DESCRIBE where workbillId = :WORKBILLID";
		this.executeUpdateHQL(hql, new String[] { "NAME", "DESCRIBE", "WORKBILLID" }, new Object[] { name, describe,
				workbillId });
	}

}
