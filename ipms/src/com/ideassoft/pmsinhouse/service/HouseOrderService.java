package com.ideassoft.pmsinhouse.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ideassoft.core.dao.GenericDAOImpl;
import com.ideassoft.core.page.Pagination;

@Service
public class HouseOrderService extends GenericDAOImpl {
	
	public List<?> getLog(String checkid, String type, Pagination pagination) {
		String sql = "";
		if ("check".equals(type)) {
			sql = "select * from("
					+ "select cul.check_id checkid, '入住' type, "
					+ "transTitles(cul.record_user, 'TB_C_STAFF', 'staff_id', 'staff_name') recorduser, "
					+ "to_char(cul.record_time,'yyyy/MM/dd HH24:mi:ss')  recordtime, "
					+ "( '原入住客人：' || transTitles(cul.last_checkinuser, 'tb_c_member', 'member_id','member_name') || "
					+ " '；新入住客人：' || transTitles(cul.curr_checkinuser, 'tb_c_member', 'member_id','member_name') || "
					+ " '；当前主客人：' || transtitles(substr(cul.curr_checkinuser,0,decode(instr(cul.curr_checkinuser, ',') - 1, '-1',length(cul.curr_checkinuser), instr(cul.curr_checkinuser, ',') - 1)), 'tb_c_member', 'member_id','member_name') ) content "
					+ "from tl_p_checkinuserlog cul where cul.check_id = '"
					+ checkid
					+ "'"
					+ " union all "
					+ "select t.check_id checkid,"
					+ " '转单' type,"
					+ "transTitles(t.record_user, 'TB_C_STAFF', 'staff_id', 'staff_name') recorduser,"
					+ " to_char(t.record_time,'yyyy/MM/dd HH24:mi:ss')  recordtime,"
					+ " ("
					+ " '已转订单号：' || t.order_id "
					+ " ) content "
					+ "from TL_P_SWITCHORDER t where t.check_id = '"
					+ checkid
					+ "'"
					+ " union all"
					+ " select e.check_id checkid,"
					+ " '续住 ' type,"
					+ "  transTitles(e.record_user, 'TB_C_STAFF', 'staff_id', 'staff_name') recorduser,"
					+ "  to_char(e.record_time,'yyyy/MM/dd HH24:mi:ss')  recordtime,"
					+ "  ("
					+ "  '原预离日期：' || to_char(e.last_day,'yyyy/MM/dd HH24:mi') || "
					+ "  '；新预离日期：' || to_char(e.add_day,'yyyy/MM/dd HH24:mi') || '；房价：' ||"
					+ "  to_char(e.room_price, '999999999990.99') || ' 元') content "
					+ "  from TL_P_EXTENSIONLOG e  where e.check_id = '"
					+ checkid
					+ "'"
					+ " union all"
					+ " select s.check_id checkid,"
					+ " '换房' type,"
					+ "   transTitles(s.record_user, 'TB_C_STAFF', 'staff_id', 'staff_name') recorduser,"
					+ "  to_char(s.record_time,'yyyy/MM/dd HH24:mi:ss')  recordtime,"
					+ "    ("
					+ "    '原房价：' || to_char(s.last_roomprice, '999999999990.99') || "
					+ "    ' 元；现房价：' || to_char(s.curr_roomprice, '999999999990.99') || ' 元；单日差价' || to_char(s.curr_roomprice - s.last_roomprice, '999999999990.99') || ' 元； 原房间编号：' || s.last_roomid ||"
					+ "    '；现房间编号：' || s.curr_roomid) content "
					+ " from TL_P_SWITCHROOM s  where s.check_id = '"
					+ checkid
					+ "'"
					+ " union all"
					+ " select r.check_id checkid,"
					+ "    '入账 ' type,"
					+ "    transTitles(r.record_user, 'TB_C_STAFF', 'staff_id', 'staff_name') recorduser,"
					+ "   to_char(r.record_time,'yyyy/MM/dd HH24:mi:ss')  recordtime,"
					+ "     ("
					+ "    '项目编号：' || (select sy.param_name from tp_c_sysparam sy where sy.param_type ='PROJECT' and sy.content = r.project_id) || '；金额：' || to_char(r.fee, '999999999990.99') || ' 元'"
					+ "     ) content from TL_P_RECORDING r  where r.check_id = '"
					+ checkid
					+ "'"
					+ " union all"
					+ " select r.check_id checkid,"
					+ "    '重新入住 ' type,"
					+ "    transTitles(r.record_user, 'TB_C_STAFF', 'staff_id', 'staff_name') recorduser,"
					+ "   to_char(r.rechecin_date,'yyyy/MM/dd HH24:mi:ss')  recordtime,"
					+ "     ("
					+ "    '入住时间：' || to_char(r.rechecin_date,'yyyy/MM/dd HH24:mi') || '；欲离时间：' || to_char(r.recheckout_date,'yyyy/MM/dd HH24:mi')"
					+ "     ) content from TL_P_RECHECKIN r  where r.check_id = '"
					+ checkid
					+ "'"
					+ " union all "
					+ " select os.check_id checkid, '冲减' type, "
					+ " transTitles(os.record_user, 'TB_C_STAFF', 'staff_id', 'staff_name') recorduser, "
					+ " to_char(os.record_time,'yyyy/MM/dd HH24:mi:ss')  recordtime, "
					+ " ( '被冲减账单号：' || os.last_billid || '；冲减抵消账单号：' || os.curr_billid || '；冲销金额：' || to_char(os.offset_fee, '999999999990.99') || " 
					+ " '；备注：' || os.remark) content"
					+ " from tl_p_offsetlog os where os.offset_type = '1' and os.check_id = '"
					+ checkid
					+ "'"//房价调整表
					+ " union all "
					+ " select rp.checkid checkid, '房价调整' type, "
					+ " transTitles(rp.record_user, 'TB_C_STAFF', 'staff_id', 'staff_name') recorduser, "
					+ " to_char(rp.record_time,'yyyy/MM/dd HH24:mi:ss')  recordtime, "
					+ " ( '民宿编号：' || rp.branch_id || '；原房价：' || to_char(rp.room_price, '999999999990.99') || '；调整价格：' || to_char( rp.cashback-rp.room_price, '999999999990.99') || " 
					+ " '；调整后房价：' || to_char(rp.cashback, '999999999990.99') || "
					+ "'；备注：' || rp.remark ) content"
					+ " from TB_P_ROOMPLAN rp where rp.checkid = '"
					+ checkid
					+ "'"//退房日志表
					+ " union all "
					+ " select rp.check_id checkid, '退房' type, "
					+ " transTitles(rp.record_user, 'TB_C_STAFF', 'staff_id', 'staff_name') recorduser, "
					+ " to_char(rp.record_time,'yyyy/MM/dd HH24:mi:ss')  recordtime, "
					+ " ( '民宿编号：' || rp.branch_id || '；退房日期：' || to_char(rp.record_time, 'yyyy/MM/dd HH24:mi') || "
					+ "'；备注：' || rp.remark ) content"
					+ " from TL_P_REFUNDLOG rp where rp.check_id = '"
					+ checkid
					+ "'"//转单日志表
					+ " union all "
					+ " select  tf.last_checkid checkid,"
					+ "    '转账' type,"
					+ "    transTitles(tf.record_user, 'TB_C_STAFF', 'staff_id', 'staff_name') recorduser,"
					+ "    to_char(tf.record_time,'yyyy/MM/dd HH24:mi:ss')  recordtime,"
					+ "    ('转账单号：' || tf.last_checkid || '；目标单号：' || tf.curr_checkid || "
					+ "    '；转账数目：' || to_char(tf.transfer_fee, '999999999990.99') || ' 元') content from TL_P_TRANSFER tf  where tf.last_checkid = '"
					+ checkid + "' or tf.curr_checkid = '" + checkid + "')r1 order by r1.recordtime desc";
		} else if ("workbill".equals(type)) {
			sql = "select * from("
					+ " select r.check_id checkid,"
					+ "    '工作账单' type,"
					+ "    transTitles(r.record_user, 'TB_C_STAFF', 'staff_id', 'staff_name') recorduser,"
					+ "    to_char(r.record_time,'yyyy/MM/dd HH24:mi:ss')  recordtime,"
					+ "    '项目编号：' || (select sy.param_name from tp_c_sysparam sy where sy.param_type ='PROJECT' and sy.content = r.project_id) || '；金额：' || to_char(r.fee, '999999999990.99') || ' 元'"
					+ "    content from TL_P_RECORDING r where r.record_type = '2' and r.check_id= '"
					+ checkid
					+ "'"
					+ " union all "
					+ " select os.check_id checkid, '冲减' type, "
					+ " transTitles(os.record_user, 'TB_C_STAFF', 'staff_id', 'staff_name') recorduser, "
					+ " to_char(os.record_time,'yyyy/MM/dd HH24:mi:ss')  recordtime, "
					+ " ( '被冲减账单号：' || os.last_billid || '；冲减抵消账单号：' || os.curr_billid || '；冲销金额：' || to_char(os.offset_fee, '999999999990.99') ||" 
					+ " ' 元；备注：' || os.remark) content"
					+ " from tl_p_offsetlog os where os.offset_type = '2' and os.check_id = '" + checkid + "'"
					+ ") r1 order by r1.recordtime desc";
		}

		return findBySQLWithPagination(sql, pagination);
	}
	
	public List<?> getMemberdataInHouse(String Mnumber) throws Exception {
		List<?> result = findBySQL("selectmember", new String[] { Mnumber }, true);
		return result;
	}
	
	public List<?> getGuaranteeInHouse() throws Exception {
		String sql = "orderselectguarantee";
		return findBySQL(sql, true);
	}
	
	public List<?>getRoombedsInHouse(String branchid, String orderroomtype) throws Exception {
		List<?> result = findBySQL("orderroombed", new String[] { branchid,orderroomtype}, true);
		return result;
	}
	
	public List<?>getOrderroomname(String ordertheme, String branchid, String orderroomtype) throws Exception {
		List<?> result = findBySQL("orderroomname", new String[] { ordertheme, branchid, orderroomtype}, 
				true);
		return result;
	}
	
	public String Token() {
		//DataDealPortalService service = new DataDealPortalService();
		//IDataDealPortal portal = service.getDataDealPortalPort();
		List<?> list = findBySQL("queryRepairToken", true);
		String token = (String) ((Map<?, ?>) list.get(0)).get("CONTENT");
	//	String token = portal.call(1, 1, "{ function: \"repairApplication.getToken\", data:{token:" + "1"  +"} }");
//		return (SysParam) findOneByProperties(SysParam.class, "paramName",
//				"微信token", "paramType", "TOKEN", "status", "1");
		return token;
	}

}
