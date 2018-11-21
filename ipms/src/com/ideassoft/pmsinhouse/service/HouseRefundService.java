package com.ideassoft.pmsinhouse.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.ideassoft.core.dao.GenericDAOImpl;

@Service
public class HouseRefundService extends GenericDAOImpl {
 
	public List<?> findBillBySqlInHouse(String checkid, String branchid, String status){
		String sql = "select b.bill_id billid, b.branch_id branchid, b.check_id checkid, b.project_id projectid, b.project_name projectname," +
		"(select s.param_desc  from tp_c_sysparam s where s.param_type = 'PROJECT' and s.content = b.project_id) billtype, " +
		"b.describe describe, b.cost cost, b.pay pay, b.payment payment, b.offset offset, b.status status, b.record_time recordtime, " +
		"b.record_user recorduser,nvl(s.staff_name, (select sy.param_name from tp_c_sysparam sy where sy.param_type = 'DAYEND')) staffname, " +
		"b.remark remark,nvl(b.refund_status,'0') refundstatus from tb_p_bill b, tb_c_staff s " +
		"where b.record_user = s.staff_id(+) " +
		" and b.check_id = '" + checkid + "'";
		if(!StringUtils.isEmpty(branchid)){
			sql = sql + " and b.branch_id = '" + branchid + "'";
		}
				
		sql = sql + " and b.status in (" + status + ") order by b.record_time desc";
		return this.findBySQL(sql);
	}
	
	
	public List<?> findBillBySql(String checkid, String branchid, String status){
		String sql = "select b.bill_id billid, b.branch_id branchid, b.check_id checkid, b.project_id projectid, b.project_name projectname," +
		"(select s.param_desc  from tp_c_sysparam s where s.param_type = 'PROJECT' and s.content = b.project_id) billtype, " +
		"b.describe describe, b.cost cost, b.pay pay, b.payment payment, b.offset offset, b.status status, b.record_time recordtime, " +
		"b.record_user recorduser,nvl(s.staff_name, (select sy.param_name from tp_c_sysparam sy where sy.param_type = 'DAYEND')) staffname, " +
		"b.remark remark,nvl(b.refund_status,'0') refundstatus from tb_p_bill b, tb_c_staff s " +
		"where b.record_user = s.staff_id(+) " +
		" and b.check_id = '" + checkid + "'";
		if(!StringUtils.isEmpty(branchid)){
			sql = sql + " and b.branch_id = '" + branchid + "'";
		}
				
		sql = sql + " and b.status in (" + status + ") order by b.record_time desc";
		return this.findBySQL(sql);
	}
	
}
