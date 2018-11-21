package com.ideassoft.pmsinhouse.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.ideassoft.bean.Member;
import com.ideassoft.bean.MemberAccount;
import com.ideassoft.bean.Room;
import com.ideassoft.bean.RoomKey;
import com.ideassoft.bean.SqlInfo;
import com.ideassoft.bean.wrapper.MultiQueryCheck;
import com.ideassoft.core.constants.CommonConstants;
import com.ideassoft.core.dao.GenericDAOImpl;
import com.ideassoft.core.page.Pagination;
import com.ideassoft.util.DateUtil;

@Service
public class RoomUtilService extends GenericDAOImpl {

	public List<?> queryCheckData(String branchId, MultiQueryCheck multiquerycheck, String checkuserType,String checkinType, Pagination pagination) {
		String sql = "select c.check_id checkid,c.branch_id branchid, c.room_id roomid, c.room_type roomtype,c.rp_id rpid, c.room_price roomprice,"
				+ " c.check_user checkuser,transtitles(c.check_user, 'tb_c_member', 'member_id', 'member_name') checkusername,"
				+ "(select cu.checkuser_name from tb_c_checkuser cu where cu.check_id = c.check_id and cu.checkin_type = '" + checkinType + "' and cu.checkuser_type = '" + checkuserType + "') firstcheckusername,"
				+ "(select cu.mobile from tb_c_checkuser cu where cu.check_id = c.check_id and cu.checkin_type = '" + checkinType + "' and cu.checkuser_type = '" + checkuserType + "') firstcheckuserphone, to_char(c.checkin_time, 'YYYY/MM/DD HH24:MI') checkintime,"
				+ " to_char(c.checkout_time, 'YYYY/MM/DD HH24:MI') checkouttime, c.deposit deposit, c.ttv ttv, c.cost cost,c.pay pay,c.account_fee accountfee,"
				+ " c.total_fee totalfee, c.pay_type paytype,c.pay_info payinfo,c.payer payer,c.switch_id switchid,"
				+ " c.status status, decode(c.status, 1,'在住', 2, '离店', 3,'已退未结', 4, '离店') statusname, c.record_time recordtime,"
				+ " c.record_user recorduser, c.remark remark, r.room_name roomname,o.order_user orderuser,"
				+ "transtitles(o.order_user, 'tb_c_member', 'member_id', 'member_name') orderusername,"
				+ "(select t2.rank_name from tb_c_member t1,tp_c_memberrank t2 where t1.member_rank=t2.member_rank and t1.member_id=o.order_user) rankname,o.m_phone mphone,"
				+ " o.source source,decode(o.source, '1','app', '2','网站', '3','分店', '4','wap', '5','合作渠道','其他') decodesource, o.guarantee guarantee,decode(o.guarantee,'1','无','2','担保') decodeguarantee, o.limited limited from tb_p_check c, tp_p_roomtype r, tb_p_order o"
				+ " where c.room_type = r.room_type(+) and c.check_id = o.order_id {and c.status like '%' || ? || '%'}"
				+ " {and c.branch_id like '%' || ? || '%'}"
				+ " {and r.branch_id like '%' || ? || '%'}"
				+ " {and c.check_id like '%' || ? || '%'}"
				+ " {and c.room_id like '%' || ? || '%'} "
				+ " {and r.room_name like  '%' || ? || '%'} "
				+ " {and to_char(c.checkin_time, 'yyyy/MM/dd') like '%' || ? || '%'}"
				+ " {and to_char(c.checkout_time, 'yyyy/MM/dd') like '%' || ? || '%'}"
				+ " {and transtitles(substr(c.check_user,0,decode(instr(c.check_user, ',') - 1, '-1',length(c.check_user), instr(c.check_user, ',') - 1)), 'tb_c_member', 'member_id','member_name') like '%' || ? || '%'} "
				+ " {and o.guarantee like '%' || ? || '%'} "
				+ " {and transtitles(o.order_user, 'tb_c_member', 'member_id', 'member_name') like '%' || ? || '%'} "
				+ " {and o.m_phone like '%' || ? || '%'} "
				+ " {and o.record_time >= to_date(?, 'yyyy/MM/dd')}"
				+ " {and o.record_time <= to_date(?, 'yyyy/MM/dd')}"
				+ " {and c.checkin_time >= to_date(?, 'yyyy/MM/dd')}"
				+ " {and c.checkin_time <= to_date(?, 'yyyy/MM/dd')}"
				+ " {and c.checkout_time >= to_date(?, 'yyyy/MM/dd')}"
				+ " {and c.checkout_time <= to_date(?, 'yyyy/MM/dd')}" + " order by c.check_id desc";
		return findBySQLWithPagination(sql, new String[] { multiquerycheck.getStatus(), branchId, branchId,
				multiquerycheck.getCheckid(), multiquerycheck.getRoomid(), multiquerycheck.getRoomtype(),
				multiquerycheck.getCheckintime(), multiquerycheck.getCheckouttime(), multiquerycheck.getCheckuser(),
				multiquerycheck.getGuarantee(), multiquerycheck.getOrderuser(), multiquerycheck.getMphone(),
				multiquerycheck.getOrdertimebegin(), multiquerycheck.getOrdertimeend(),
				multiquerycheck.getArrivaltimebegin(), multiquerycheck.getArrivaltimeend(),
				multiquerycheck.getLeavetimebegin(), multiquerycheck.getLeavetimeend() }, pagination);
	}

	public void upateroomstatus(String branchid, String roomid, String status) {
		String sql = "update tb_p_room set status = '" + status + "', record_time = sysdate where  branch_id = " + branchid + " and room_id = "
				+ roomid;
		this.executeUpdateSQL(sql);
	}

	public Room selectroomstatus(String branchid, String roomid) {
		RoomKey id = new RoomKey();
		id.setRoomId(roomid);
		id.setBranchId(branchid);
		// String hql1 = "from Room where id = ?";
		return (Room) this.getSession().get(Room.class, id);
		// return (Room) this.findByHQL(hql1, new Object[]{ id});
		// this.executeUpdateHQL(hql1,new String[]{ "ID"}, new Object[]{ id});
	}
	
	public List<?> findSysparam(String orderno, String paramtype){
		String hql = "from com.ideassoft.bean.SysParam as o where paramType= '" + paramtype + "' and o.orderNo= " + orderno + " order by o.id asc";
		return this.findByHQL(hql);
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
	
	public List<?> findneedCheckoutBill(String checkid, List<String> status){
		String statuss = "";
		for (String string : status) {
			statuss = statuss + string + ",";
		}
		statuss = statuss.substring(0, statuss.length() - 1);
		String sql = "select b.bill_id billId, b.branch_id branchId, b.check_id checkId, b.project_id projectId, b.project_name projectName, " + 
					"b.describe describe, b.cost cost, b.pay pay, b.payment payment, b.offset offset, b.status status, b.record_time recordTime, " +
					"b.record_user recordUser, b.remark remark, b.type type, b.shift_voucher shiftVoucher from tb_p_bill b where b.check_id = '" + checkid + "'" + 
					"and b.status in (" + statuss + ")";
		return this.findBySQL(sql);
	}

	public void updatecheck(String rpid, String roomid, String roomtype, double roomprice, String checkid) {
		String hql = "update Check set rpId = :RPID, roomId = :ROOMID, roomType = :ROOMTYPE, roomPrice = :ROOMPRICE where checkId = :CHECKID";
		this.executeUpdateHQL(hql, new String[] { "RPID", "ROOMID", "ROOMTYPE", "ROOMPRICE", "CHECKID" }, new Object[] {
				rpid, roomid, roomtype, roomprice, checkid });
	}

	public void updatecheckcheckuser(String strguest, String checkid) {
		String hql = "update Check set checkUser = :CHECKUSER where checkId = :CHECKID";
		this.executeUpdateHQL(hql, new String[] { "CHECKUSER", "CHECKID" }, new Object[] { strguest, checkid });
	}

	public void updatestatus(String checkid,String status, double pay, double cost, Date checkoutTime) {
		String hql = "update Check set status = :STATUS, pay = :PAY, cost = :COST, checkoutTime = :CHECKOUTTIME where checkId = :CHECKID";
		this.executeUpdateHQL(hql, new String[] { "STATUS", "PAY", "COST", "CHECKID", "CHECKOUTTIME" }, new Object[] { status, pay, cost,
				checkid, checkoutTime });
	}

	public void updateorderstatus(String orderid, String status) {
		String hql = "update Order set status = :STATUS where orderId = :ORDERID";
		this.executeUpdateHQL(hql, new String[] { "STATUS", "ORDERID" }, new Object[] { status, orderid });
	}

	public void updateMember(String memberId, String memberName, String loginName, String idcard, String gendor,
			String mobile, String email, String address) {
		String hql = "update Member set status = 1";
		List<String> listname = new ArrayList<String>();
		List<String> listvalue = new ArrayList<String>();

		if (memberName != null && !"".equals(memberName)) {
			hql = hql + ", memberName = :MEMBERNAME";
			listname.add("MEMBERNAME");
			listvalue.add(memberName);
		}
		if (loginName != null && !"".equals(loginName)) {
			hql = hql + ", loginName = :LOGINNAME";
			listname.add("LOGINNAME");
			listvalue.add(loginName);
		}
		if (idcard != null && !"".equals(idcard)) {
			hql = hql + ", idcard = :IDCARD";
			listname.add("IDCARD");
			listvalue.add(idcard);
		}
		if (gendor != null && !"".equals(gendor)) {
			hql = hql + ", gendor = :GENDOR";
			listname.add("GENDOR");
			listvalue.add(gendor);
		}
		if (mobile != null && !"".equals(mobile)) {
			hql = hql + ", mobile = :MOBILE";
			listname.add("MOBILE");
			listvalue.add(mobile);
		}
		if (email != null && !"".equals(email)) {
			hql = hql + ", email = :EMAIL";
			listname.add("EMAIL");
			listvalue.add(email);
		}
		if (address != null && !"".equals(address)) {
			hql = hql + ", address = :ADDRESS";
			listname.add("ADDRESS");
			listvalue.add(address);
		}

		hql = hql + " where memberId = :MEMBERID";
		String[] strhql = new String[listname.size() + 1];
		Object[] valhql = new Object[listvalue.size() + 1];
		for (int i = 0; i < listname.size(); i++) {
			strhql[i] = listname.get(i);
		}
		for (int i = 0; i < listvalue.size(); i++) {
			valhql[i] = listvalue.get(i);
		}
		strhql[listname.size()] = "MEMBERID";
		valhql[listvalue.size()] = memberId;
		// String hql =
		// "update Check set status = :STATUS, pay = :PAY, cost = :COST, checkoutTime = :CHECKOUTTIME  where checkId = :CHECKID";
		this.executeUpdateHQL(hql, strhql, valhql);
	}

	public void updateBillStatus(String status, String billid) {
		String hql = "update Bill set status = :STATUS where billId = :BILLID";
		this.executeUpdateHQL(hql, new String[] { "STATUS", "BILLID" }, new Object[] { "2", billid });
	}

	public void insertroommapping(String hostroomid, String roomid) {
		String sql = "insert into tb_p_roommapping (room_id, rela_roomid) values ('" + hostroomid + "', '" + roomid
				+ "')";
		this.executeUpdateSQL(sql);
	}

	public List<?> selectroommapping(String hostroomid, String branchId, MultiQueryCheck multiQuerycheck) {
		SqlInfo sqlinfo = (SqlInfo) this.findOneByProperties(SqlInfo.class, "sqlName", "selectroommapping");
		String sql = sqlinfo.getSqlInfo();
		sql = sql + " and c.branch_id = '" + branchId + "' and o.branch_id = '" + branchId + "' and r.branch_id = '" + branchId + "'";
		if (multiQuerycheck.getStatus() != null && !"".equals(multiQuerycheck.getStatus())) {
			sql = sql + " and c.status like '%' || '" + multiQuerycheck.getStatus() + "' || '%' ";
		}
		if (multiQuerycheck.getCheckid() != null && !"".equals(multiQuerycheck.getCheckid())) {
			sql = sql + " and c.check_id like '%' || '" + multiQuerycheck.getCheckid() + "' || '%' ";
		}
		if (multiQuerycheck.getRoomid() != null && !"".equals(multiQuerycheck.getRoomid())) {
			sql = sql + " and c.room_id like '%' || '" + multiQuerycheck.getRoomid() + "' || '%' ";
		}
		if (multiQuerycheck.getRoomtype() != null && !"".equals(multiQuerycheck.getRoomtype())) {
			sql = sql + " and r.room_name like  '%' || '" + multiQuerycheck.getRoomtype() + "' || '%'";
		}
		//sql = sql + " and r.status = '" + CommonConstants.RoomStatus.RoomChecked + "'";
		if (multiQuerycheck.getOrderuser() != null && !"".equals(multiQuerycheck.getOrderuser())) {
			sql = sql + " and transtitles(o.order_user, 'tb_c_member', 'member_id', 'member_name') like '%' || '"
					+ multiQuerycheck.getOrderuser() + "' || '%'";
		}
		if (multiQuerycheck.getCheckuser() != null && !"".equals(multiQuerycheck.getCheckuser())) {
			sql = sql + " and transtitles(c.check_user, 'tb_c_member', 'member_id', 'member_name') like '%' || '"
					+ multiQuerycheck.getCheckuser() + "' || '%'";
		}

		sql = sql + " and c.room_id not in (select rela_roomid from tb_p_roommapping) and c.room_id not in(" + hostroomid + ")";

		return this.findBySQL(sql);
	}

	public void deleteroommappingbyroomid(String hostroomid, String delroomid, String branchId) {
		String sql = "delete from tb_p_roommapping rm where rm.room_id =" + hostroomid + "and rm.rela_roomid = "
				+ delroomid + "and rm.branch_id = " + branchId;
		this.executeUpdateSQL(sql);
	}

	public void deleteroommapping(String hostroomid, String branchId) {
		String sql = "delete from tb_p_roommapping rm where rm.room_id =" + hostroomid + "and rm.branch_id = " + branchId;
		this.executeUpdateSQL(sql);
	}

	// 日志
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
					+ "    '原房价：' || s.last_roomprice ||"
					+ "    '；现房价：' || s.curr_roomprice || '；原房间类型：' || s.last_roomtype ||"
					+ "    '；现房间类型：' || s.curr_roomtype || '；原房号：' || s.last_roomid ||"
					+ "    '；现房号：' || s.curr_roomid) content "
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
					+ "'"
					+ " union all "
					+ " select rp.checkid checkid, '房价调整' type, "
					+ " transTitles(rp.record_user, 'TB_C_STAFF', 'staff_id', 'staff_name') recorduser, "
					+ " to_char(rp.record_time,'yyyy/MM/dd HH24:mi:ss')  recordtime, "
					+ " ( '房间号：' || rp.room_id || '；原房价：' || to_char(rp.room_price, '999999999990.99') || '；调整价：' || to_char( rp.cashback, '999999999990.99') || " 
					+ " '；调整后房价：' || to_char(rp.room_price + rp.cashback, '999999999990.99') || "
					+ "'；备注：' || rp.remark ) content"
					+ " from TB_P_ROOMPLAN rp where rp.checkid = '"
					+ checkid
					+ "'"
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

	public List<?> loadcustomer(String strcheckid, String branchId) {
		String sql = "select c.check_id checkid,c.branch_id branchid, c.room_id roomid, c.room_type roomtype,c.rp_id rpid, "
				+ "c.room_price roomprice, c.check_user checkuser,transtitles(c.check_user, 'tb_c_member', 'member_id', 'member_name') checkusername, substr(c.check_user, 0,decode(instr(c.check_user, ',') - 1, '-1', length(c.check_user), instr(c.check_user, ',') - 1)) firstcheckuser,"
				+ "transtitles(substr(c.check_user,0,decode(instr(c.check_user, ',') - 1, '-1',length(c.check_user), instr(c.check_user, ',') - 1)), 'tb_c_member', 'member_id','member_name') firstcheckusername, transtitles(substr(c.check_user,0, decode(instr(c.check_user, ',') - 1, '-1',length(c.check_user),instr(c.check_user, ',') - 1)),'tb_c_member','member_id','mobile') firstcheckuserphone,"
				+ " c.checkin_time checkintime, c.checkout_time checkouttime, c.deposit deposit, c.ttv ttv, c.cost cost,"
				+ "c.pay pay,c.account_fee accountfee, c.total_fee totalfee, c.pay_type paytype,c.pay_info payinfo,"
				+ "c.payer payer,c.switch_id switchid, c.status status, c.record_time recordtime, c.record_user recorduser, "
				+ "c.remark remark from tb_p_check c where c.status = '1' and c.branch_id = '"
				+ branchId
				+ "' and c.room_id in(" + strcheckid + ")";
		return this.findBySQL(sql);
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> findallroommapping(String roomid, String branchId) {
		String sql = "select room_id roomid, rela_roomid relaroomid from tb_p_roommapping where room_id = ? and branch_id = ?";
		return this.findBySQL(sql, new String[] { roomid, branchId });
	}
	
	public List<?> chosedpayfromcheck(String checkid, String branchid, String strbillids) {
		String sql = "select nvl(sum(t.pay), 0) totalpay, t.check_id checkid from tb_p_bill t " +
				"where t.status = '1' and t.check_id = '" + checkid + "' and t.branch_id = '" + branchid +  
				"' and t.bill_id in ( " + strbillids +  " ) group by t.check_id";
		return this.findBySQL(sql);
	}
	
	public List<?> chosedcostfromcheck(String checkid, String branchid, String strbillids) {
		String sql = "select nvl(sum(t.cost), 0) totalcost, t.check_id checkid from tb_p_bill t " +
				"where t.status = '1' and t.check_id = '" + checkid + "' and t.branch_id = '" + branchid +  
				"' and t.bill_id in ( " + strbillids +  " ) group by t.check_id";
		return this.findBySQL(sql);
	}

	public List<?> getRoomStatus(String madate, String queryTime, String branchId, Pagination pagination) {
		String sql = "select z.roomtype,z.roomname,z.roomid,z.roomid - z.stop - z.stopplan - z.live - z.leave - z.other sell,z.stop + z.stopplan stop,z.night,z.vaild," 
				+ "z.invaild from (select rt.room_type roomtype,rt.room_name roomname,count(rm.room_id) roomid,b.roomid stop,i.roomid stopplan,c.roomid night," 
				+ "d.neworder vaild,e.allorder invaild,f.roomid live,g.roomid leave,h.roomid other from tp_p_roomtype rt,tb_p_room rm,(select rt.room_type roomtype," 
				+ "nvl(a.roomid, 0) roomid,rt.branch_id from tp_p_roomtype rt  Left Join (select rm.room_type roomtype,count(rm.room_id) roomid" 
				+ " from tb_p_room rm, tb_p_haltplan ht where rm.status in ('T', 'W')  and rm.room_id = ht.room_id {and rm.branch_id = ?}  {and ht.branch_id = ?}" 
				+ " {and to_char(ht.start_time, 'yyyy/MM/dd') <=  ?} {and to_char(ht.end_time, 'yyyy/MM/dd') >= ?} and ht.status = '3' group by rm.room_type order by rm.room_type) a" 
				+ " on rt.room_type = a.roomtype) b,(select rt.room_type roomtype,nvl(a.roomid, 0) roomid, rt.branch_id from tp_p_roomtype rt Left Join (select rm.room_type roomtype,"
                +  " count(rm.room_id) roomid from tb_p_room rm, tb_p_haltplan ht  where rm.room_id = ht.room_id {and rm.branch_id = ?} {and ht.branch_id = ?}" 
                + " {and to_char(ht.start_time, 'yyyy/MM/dd') <= ?} {and to_char(ht.end_time, 'yyyy/MM/dd') >= ?} and ht.status = '1'  group by rm.room_type" 
                + " order by rm.room_type) a on rt.room_type = a.roomtype) i, (select rt.room_type roomtype,nvl(newrt.rmid, 0) roomid,rt.branch_id" 
                + " from tp_p_roomtype rt left join (select rt.room_type roomtype, count(che.room_id) rmid  from tb_p_check che, tp_p_roomtype rt where rt.room_type = che.room_type" 
                + " and che.status = '1' {and che.branch_id = ?} {and to_char(che.checkout_time,'yyyy/MM/dd HH:MM:ss') >= ?} {and to_char(che.checkin_time, 'yyyy/MM/dd HH:MM:ss') <= ?}" 
                + " group by rt.room_type) newrt on rt.room_type = newrt.roomtype) c, (select rt.room_type roomtype, nvl(neworder.countnum, 0) neworder, rt.branch_id" 
                + " from tp_p_roomtype rt left join (select rt.room_type roomtype, count(ord.room_type) countnum  from tb_p_order ord, tp_p_roomtype rt " 
                + " where ord.room_type = rt.room_type and ord.status = '1' {and ord.branch_id = ?} {and to_char(ord.arrival_time, 'yyyy/MM/dd') <= ?} {and to_char(ord.leave_time, 'yyyy/MM/dd') >= ?}" 
                + " {and rt.branch_id = ?}  group by rt.room_type) neworder  on rt.room_type = neworder.roomtype) d, (select rt.room_type roomtype, nvl(allorder.countnum, 0) allorder,"
                + " rt.branch_id  from tp_p_roomtype rt left join (select rt.room_type roomtype, count(ord.room_type) countnum from tb_p_order ord, tp_p_roomtype rt"
                + " where ord.room_type = rt.room_type  and ord.status in ('0', '1', '2') and {ord.branch_id = ?} {and to_char(ord.arrival_time, 'yyyy/MM/dd') <= ?}"
                + " {and to_char(ord.leave_time, 'yyyy/MM/dd') >= ?} {and rt.branch_id = ?}  group by rt.room_type) allorder on rt.room_type = allorder.roomtype) e,"
                + " (select rt.room_type roomtype, nvl(live.rmid, 0) roomid, rt.branch_id from tp_p_roomtype rt left join (select rt.room_type roomtype,count(che.room_id) rmid"
                + " from tb_p_check che, tp_p_roomtype rt  where rt.room_type = che.room_type and che.status = '1' {and che.branch_id = ?} {and to_char(che.checkout_time, 'yyyy/MM/dd') >= ?}"
                + " {and to_char(che.checkin_time, 'yyyy/MM/dd') <= ?}  group by rt.room_type) live  on rt.room_type = live.roomtype) f,(select rt.room_type roomtype,"
                + " nvl(orders.countnum, 0) roomid,rt.branch_id  from tp_p_roomtype rt  left join (select rt.room_type roomtype, count(ord.room_type) countnum"
                + " from tb_p_order ord, tp_p_roomtype rt where ord.room_type = rt.room_type and ord.status = '1' {and ord.branch_id = ?} {and to_char(ord.arrival_time, 'yyyy/MM/dd') <= ?}"
                + " {and to_char(ord.leave_time, 'yyyy/MM/dd') >= ?} {and rt.branch_id = ?} group by rt.room_type) orders  on rt.room_type = orders.roomtype) g,"
                + " (select rt.room_type roomtype,count(rm.room_id) roomid,rt.branch_id from tp_p_roomtype rt left join tb_p_room rm on rt.room_type = rm.room_type"
                + " and rm.status = 'O' group by rt.room_type, rt.branch_id) h where rt.room_type = rm.room_type  and rt.room_type = b.roomtype and rt.room_type = c.roomtype "
                + " and rt.room_type = d.roomtype  and rt.room_type = e.roomtype  and rt.room_type = f.roomtype and rt.room_type = g.roomtype  and rt.room_type = h.roomtype "
                + " and rt.room_type = i.roomtype and rt.theme = '1' and rm.status <> '0' {and rm.branch_id = ?} {and rt.branch_id = ?} {and b.branch_id = ?}" 
                + " {and c.branch_id = ?} {and d.branch_id = ?} {and e.branch_id = ?} {and f.branch_id = ?} {and g.branch_id = ?} {and h.branch_id = ?} {and i.branch_id = ?}"
                + " group by rt.room_type,rt.room_name,b.roomid,i.roomid,c.roomid,d.neworder,e.allorder,f.roomid, g.roomid,h.roomid order by rt.room_type) z";
		return findBySQLWithPagination(sql, new String[] { branchId, branchId, madate, madate, branchId, branchId, madate, madate, branchId, queryTime, queryTime,
				branchId, madate, madate, branchId, branchId, madate, madate, branchId, branchId, madate, madate, branchId,madate, madate, branchId, branchId,
				branchId, branchId, branchId, branchId, branchId, branchId, branchId, branchId, branchId }, pagination);
	}

	public List<?> getRoomPrice(String branchId, String todayNum, String date, String rpId) {
		String sql = "select nvl(j.rmmem, '0.00') remmem, nvl(j.rmprimsj, '0.00') rmprimsj, "
		 +" nvl(m.adjustmem, '0.00') adjmem,m.adjustmemfilter_day adjustmemday, nvl(m.msjprice, '0.00') msjprice, "
		 +" m.msjpricefilter_day msjpriceday,nvl(m.memprice, '0.00') memprice, m.mempricefilter_day mempriceday, nvl(m.adjustmsj, '0.00') " 
		 +" adjustmsj, m.adjustmsjfilter_day adjustmsjday,j.room_type from (select a.room_type,a.msjprice,a.msjapplyid,a.msjpricefilter_day,b.memprice, b.memapplyid,b.mempricefilter_day, "
		 +" c.adjustmsj,c.adjapplyid,c.adjustmsjfilter_day,d.adjustmem,d.apply_id,d.adjustmemfilter_day from  (select rt.room_type room_type, app.msjprice,app.msjapplyid,"
		 +" app.msjpricefilter_day from tp_p_roomtype rt left join (select prdetail.room_price msjprice, prdetail.apply_id  msjapplyid, prdetail.room_type  room_types,"
		 +" a.filter_day  msjpricefilter_day from tb_p_priceapplydetail prdetail,(select prapply.apply_id, prapply.filter_day from tb_p_priceapply prapply where " 
		 +" prapply.apply_id = (select max(prapply.apply_id)  from tb_p_priceapply prapply where {prapply.branch_id = ? and} prapply.apply_type = '1'  and prapply.status in ('3', '2')"
		 +" and prapply.apply_kind = '1' {and prapply.valid_start <= to_date(?,'yyyy/MM/dd HH:mi:ss')} {and prapply.valid_end >=  to_date(?, 'yyyy/MM/dd HH:mi:ss')}{and "
		 +" prapply.valid_day like '%' || ? || '%'})) a where prdetail.content = 'MSJ'  and prdetail.apply_id = a.apply_id) app on rt.room_type = app.room_types where rt.branch_id = {?} ) a ,"
         +" (select rt.room_type room_type, app.memprice,app.memapplyid,app.mempricefilter_day from tp_p_roomtype rt left join  (select prdetail.room_price memprice, prdetail.apply_id memapplyid,"
         +" prdetail.room_type  room_types,a.filter_day  mempricefilter_day  from tb_p_priceapplydetail prdetail, (select prapply.apply_id, prapply.filter_day  from tb_p_priceapply prapply"
         +" where prapply.apply_id = (select max(prapply.apply_id) from tb_p_priceapply prapply where {prapply.branch_id = ? and} prapply.apply_type = '1' and prapply.status in ('3', '2')"
         +" and prapply.apply_kind = '1' {and prapply.valid_start <= to_date(?,'yyyy/MM/dd HH:mi:ss')}{and prapply.valid_end >= to_date(?, 'yyyy/MM/dd HH:mi:ss')} "
         +" {and prapply.valid_day like '%' || ? || '%'})) a  where {prdetail.content = ?  and} prdetail.apply_id = a.apply_id) app  on rt.room_type = app.room_types where rt.branch_id = {?} ) b,"
         +" (select rt.room_type room_type, app.adjustmsj,app.adjapplyid,app.adjustmsjfilter_day from tp_p_roomtype rt left join (select prdetail.room_price adjustmsj,prdetail.apply_id   adjapplyid,"
         +" prdetail.room_type  room_types, a.filter_day adjustmsjfilter_day  from tb_p_priceapplydetail prdetail,(select prapply.apply_id, prapply.filter_day  from tb_p_priceapply prapply"
         +" where prapply.apply_id = (select max(prapply.apply_id) from tb_p_priceapply prapply where {prapply.branch_id = ?  and} prapply.apply_type = '1' and prapply.status in ('3', '2')"
         +" and prapply.apply_kind = '2' {and prapply.valid_start <= to_date(?, 'yyyy/MM/dd HH:mi:ss')} {and prapply.valid_end >= to_date(?,'yyyy/MM/dd HH:mi:ss')}"
         +" {and prapply.valid_day like '%' || ? || '%'})) a  where prdetail.content = 'MSJ'  and prdetail.apply_id = a.apply_id) app on rt.room_type = app.room_types where rt.branch_id = {?}) c ,"
         +" (select rt.room_type room_type, app.adjustmem, app.apply_id, app.adjustmemfilter_day from tp_p_roomtype rt left join (select prdetail.room_price adjustmem,prdetail.apply_id," 
         +" prdetail.room_type room_types, a.filter_day adjustmemfilter_day from tb_p_priceapplydetail prdetail,(select prapply.apply_id, prapply.filter_day  from tb_p_priceapply prapply"
         +"  where prapply.apply_id =  (select max(prapply.apply_id)  from tb_p_priceapply prapply  where {prapply.branch_id = ?  and} prapply.apply_type = '1' and prapply.status in ('3', '2')"
         +" and prapply.apply_kind = '2' {and prapply.valid_start <= to_date(?, 'yyyy/MM/dd HH:mi:ss')} {and prapply.valid_end >=  to_date(?, 'yyyy/MM/dd HH:mi:ss')}" 
         +" {and prapply.valid_day like '%' || ? || '%'})) a  where {prdetail.content = ?  and} prdetail.apply_id = a.apply_id) app  on  rt.room_type = app.room_types where rt.branch_id = {?} ) d "
         +" where a.room_type = b.room_type and a.room_type = c.room_type and a.room_type = d.room_type) m,(select e.rmprimsj,f.rmmem,e.room_type from (select rm.room_price rmprimsj, rm.room_type " 
		 +" from tb_p_roomprice rm where rm.status = '1' and rm.state = '5' and rm.rp_id = 'MSJ' { and rm.branch_id = ? } " 
		 +" and rm.rp_type = '1' and rm.theme = '1' and rm.rp_kind = '1') e ,  (select rm.room_price rmmem, rm.room_type "
		 +" from tb_p_roomprice rm where rm.status = '1' and rm.state = '5'  { and rm.rp_id = ? } " 
		 +" { and rm.branch_id = ? } and rm.rp_type = '1' and rm.theme = '1' and rm.rp_kind = '1') f "
		 +" where e.room_type=f.room_type) j where j.room_type = m.room_type(+) ";
		return findBySQL(sql, new String[] {branchId, date, date, todayNum, branchId, branchId, date, date,
				todayNum, rpId, branchId, branchId, date, date, todayNum, branchId, branchId, date, date, todayNum, rpId, branchId, branchId, rpId, branchId});
	}
	
	public List<?> queryRoom(String branchId,String arriveDate,String todayNum,String roomType,String rpId,String simpleArriveDate) {
		String sql=" select ro.*,i.pic_url,p.bed_desc,p.room_bed,p.room_desc,p.room_name from (select h.remmem, h.rmprimsj, h.adjmem, h.adjustmemfilter_day,h.msjprice,h.msjpricefilter_day, "
				 +" h.memprice,h.mempricefilter_day,h.adjustmsj,h.adjustmsjfilter_day,h.room_type,g.sumnum "
				 +" from ( select rt.room_type,nvl(sum(a.countnum), 0) sumnum "
				 +" from tp_p_roomtype rt left join (select rt.room_type, count(rm.room_id) countnum "
				 +" from tp_p_roomtype rt, tb_p_room rm where rt.room_type = rm.room_type and rt.theme = '1' " 
				 +" { and rm.branch_id = ? } group by rt.room_type union all select ord.room_type, "
				 +" -count(ord.room_type) countnum from tb_p_order ord where ord.theme = '1' { and branch_id = ? } "
				 +" and ord.status = '1' { and to_char(ord.arrival_time, 'YYYY-MM-DD') <= ? } "
				 +" { and to_char(ord.leave_time, 'YYYY-MM-DD') > ? } group by ord.room_type union all "
				 +" select che.room_type, count(che.room_type) countnum from tb_p_check che where  { che.branch_id = ? "
				 +" and } che.status = '1' { and to_char(che.checkin_time, 'YYYY-MM-DD') <= ? } " 
				 +" { and to_char(che.checkout_time, 'YYYY-MM-DD') > ? } group by che.room_type union all "
				 +" select rm.room_type, -count(rm.room_type) from tb_p_room rm where (rm.status = 'T' or rm.status = 'W') " 
				 +" { and rm.branch_id = ? } group by rm.room_type union all select rm.room_type, -count(rm.room_type) "
				 +" from tb_p_room rm, tb_p_haltplan hp where hp.room_id = rm.room_id { and hp.branch_id = ? } "
				 +" { and to_char(hp.start_time, 'YYYY-MM-DD') <= ? } { and to_char(hp.end_time, 'YYYY-MM-DD') >= ? } "
				 +" group by rm.room_type) a on rt.room_type = a.room_type where rt.theme = '1' "
				 +" group by rt.room_type) g,(select nvl(j.rmmem, '0.00') remmem, nvl(j.rmprimsj, '0.00') rmprimsj, "
				 +" nvl(m.adjustmem, '0.00') adjmem,m.adjustmemfilter_day, nvl(m.msjprice, '0.00') msjprice, "
				 +" m.msjpricefilter_day,nvl(m.memprice, '0.00') memprice, m.mempricefilter_day, nvl(m.adjustmsj, '0.00') " 
				 +" adjustmsj, m.adjustmsjfilter_day,j.room_type from (select j.*, nvl(d.adjustmem, '0.00') adjustmem, " 
		 		 +" d.adjustmemfilter_day from (select nvl(k.msjprice, '0.00') msjprice, k.msjpricefilter_day, " 
		 		 +"k.room_type, nvl(k.memprice, '0.00') memprice, k.mempricefilter_day, nvl(c.adjustmsj, '0.00') adjustmsj, "
		 		 +"c.adjustmsjfilter_day from (select msjprice,msjpricefilter_day, room_type, memprice, mempricefilter_day " 
		 		 +"from (select * from ((select prdetail.room_price msjprice,prdetail.apply_id   msjapplyid, prdetail.room_type  room_type," 
		 		 +"a.filter_day msjpricefilter_day from tb_p_priceapplydetail prdetail, (select prapply.apply_id, prapply.filter_day " 
		 		 +"from tb_p_priceapply prapply where prapply.apply_id = (select  max(prapply.apply_id) from tb_p_priceapply prapply " 
		 		 +"where  {prapply.branch_id = ? and } prapply.apply_type = '1' and prapply.status = '3' and prapply.apply_kind = '1' " 
		 		 +" { and prapply.valid_start <=to_date( ? ,'yyyy/MM/dd HH:mi:ss') } { and prapply.valid_end >=  to_date( ? ,'yyyy/MM/dd HH:mi:ss') }" 
		 		 +" { and prapply.valid_day like '%' || ? || '%' } )) a  where prdetail.content = 'MSJ' and prdetail.apply_id = a.apply_id) a " 
		 		 +" left join (select prdetail.room_price memprice, prdetail.apply_id  memapplyid,prdetail.room_type  room_otype, " 
		 		 +"a.filter_day  mempricefilter_day from tb_p_priceapplydetail prdetail,(select prapply.apply_id,  prapply.filter_day " 
		 		 +"from tb_p_priceapply prapply where prapply.apply_id = (select max(prapply.apply_id) from tb_p_priceapply prapply where " 
		 		 +"{ prapply.branch_id = ? and }  prapply.apply_type = '1' and prapply.status = '3' and prapply.apply_kind = '1'" 
		 		 +"{ and prapply.valid_start <= to_date( ? , 'yyyy-MM-dd HH:mi:ss') } { and prapply.valid_end >= to_date( ? , 'yyyy-MM-dd HH:mi:ss') }"
		 		 +"{ and prapply.valid_day like '%' || ? || '%' } )) a  where { prdetail.content = ? and } prdetail.apply_id = a.apply_id) b on "
		 		 +"a.room_type = b.room_otype))) k left join (select prdetail.room_price adjustmsj,prdetail.apply_id  adjapplyid, prdetail.room_type  roomtypes, " 
		 		 +"a.filter_day  adjustmsjfilter_day   from tb_p_priceapplydetail prdetail,(select prapply.apply_id, prapply.filter_day" 
		 		 +" from tb_p_priceapply prapply where prapply.apply_id = ( select max(prapply.apply_id) from tb_p_priceapply prapply " 
		 		 +"where { prapply.branch_id = ? and } prapply.apply_type = '1'  and prapply.status = '3' and prapply.apply_kind = '2' " 
		 		 +"{ and prapply.valid_start <=  to_date( ? ,'yyyy/MM/dd HH:mi:ss') } { and prapply.valid_end >= to_date( ? ,'yyyy/MM/dd HH:mi:ss') } "  
		 		 +"{ and prapply.valid_day like '%' || ? || '%' } )) a where prdetail.content = 'MSJ' and   prdetail.apply_id = a.apply_id) c "
		 		 +"on k.room_type = c.roomtypes) j left join (select prdetail.room_price adjustmem,prdetail.apply_id, prdetail.room_type," 
		 		 +" a.filter_day  adjustmemfilter_day from tb_p_priceapplydetail prdetail,(select prapply.apply_id, prapply.filter_day from " 
		 		 +"tb_p_priceapply prapply where prapply.apply_id = ( select max(prapply.apply_id) from tb_p_priceapply prapply "
		 		 +"where { prapply.branch_id = ?  and } prapply.apply_type = '1' and prapply.status = '3' and prapply.apply_kind = '2' " 
		 		 +"{ and prapply.valid_start <= to_date( ? , 'yyyy-MM-dd HH:mi:ss') } { and prapply.valid_end >= "
		 		 +"to_date( ? , 'yyyy-MM-dd HH:mi:ss') } )) a where { prdetail.content = ? and } prdetail.apply_id = a.apply_id) d "
		 		 +"on j.room_type = d.room_type) m,(select e.rmprimsj,f.rmmem,e.room_type from (select rm.room_price rmprimsj, rm.room_type " 
				 +" from tb_p_roomprice rm where rm.status = '1' and rm.state = '5' and rm.rp_id = 'MSJ' { and rm.branch_id = ? } " 
				 +" and rm.rp_type = '1' and rm.theme = '1' and rm.rp_kind = '1') e ,  (select rm.room_price rmmem, rm.room_type "
				 +" from tb_p_roomprice rm where rm.status = '1' and rm.state = '5'  { and rm.rp_id = ? } " 
				 +" { and rm.branch_id = ? } and rm.rp_type = '1' and rm.theme = '1' and rm.rp_kind = '1') f "
				 +" where e.room_type=f.room_type) j where j.room_type = m.room_type(+) ) h "
				 +" where h.room_type = g.room_type) ro  left join (select rp.room_type,pi.pic_url "
				 +" from TB_P_ROOMPICTURE rp, tb_p_picture pi where rp.pic_id = pi.pic_id and rp.pic_style = 'tt' "
				 +" and rp.status = '1'   { and rp.branch_id = ? }  and pi.status = '1') i on ro.room_type = i.room_type "
				 +" left join tp_p_roomtype p on p.room_type = ro.room_type { and p.branch_id= ? } " ;
		
		return findBySQL(sql, new String[] {branchId,branchId,simpleArriveDate,simpleArriveDate,branchId,simpleArriveDate,simpleArriveDate,
				branchId,branchId,simpleArriveDate,simpleArriveDate,branchId,arriveDate,arriveDate,todayNum,branchId,arriveDate,arriveDate,
				todayNum,rpId,branchId,arriveDate,arriveDate,todayNum,branchId,arriveDate,arriveDate,rpId,branchId,rpId,branchId,branchId,branchId});
				
	}
	
	public List<?> queryNowRoomPrice(String branchId, String rpId, String roomType, String rpStatus) {
		String sql = "select rm.room_price roomprice, rm.room_type roomtype, rm.rp_id rpid from tb_p_roomprice rm where {rm.status = ? " 
				+ "and} rm.state = '5' {and rm.rp_id = ?} { and rm.branch_id = ? } " 
				 +" and rm.rp_type = '1' and rm.theme = '1' and rm.rp_kind = '1' {and rm.room_type = ?} ";
		return findBySQL(sql, new String[] { rpStatus, rpId, branchId, roomType });
	}
	
	public List<?> queryForwardRoomPrice(String branchId, String rpId, String todayNum, String date, String roomType) {
		String sql = "select b.room_type,b.jzprice, b.memapplyid,d.rpid rpid,b.jzpricefilter_day jzfilter, d.adjustmem,d.apply_id,d.adjustmemfilter_day adjfilter from " 
				+ "(select rt.room_type room_type, app.jzprice,app.memapplyid,app.rpid rpid,app.jzpricefilter_day from tp_p_roomtype rt left join  " 
				+ "(select prdetail.room_price jzprice, prdetail.apply_id memapplyid,prdetail.content  rpid,prdetail.room_type  room_types,a.filter_day  jzpricefilter_day " 
				+ " from tb_p_priceapplydetail prdetail, (select prapply.apply_id, prapply.filter_day  from tb_p_priceapply prapply where " 
				+ " prapply.apply_id = (select max(prapply.apply_id) from tb_p_priceapply prapply where {prapply.branch_id = ? and} prapply.apply_type = '1' " 
				+ " and prapply.status in ('3', '2')  and prapply.apply_kind = '1' {and prapply.valid_start <= to_date(?,'yyyy/MM/dd HH:mi:ss')} " 
				+ " {and prapply.valid_end >= to_date(?, 'yyyy/MM/dd HH:mi:ss')}  {and prapply.valid_day like '%' || ? || '%'})) a  where {prdetail.content = ?  and} " 
				+ "prdetail.apply_id = a.apply_id  {and prdetail.room_type = ?}) app  on rt.room_type = app.room_types where rt.branch_id = {?} ) b,(select rt.room_type room_type, app.adjustmem, " 
				+ "app.apply_id,app.rpid rpid, app.adjustmemfilter_day from tp_p_roomtype rt left join (select prdetail.room_price adjustmem,prdetail.apply_id,prdetail.room_type room_types," 
				+ " prdetail.content  rpid, a.filter_day adjustmemfilter_day from tb_p_priceapplydetail prdetail,(select prapply.apply_id, prapply.filter_day  from tb_p_priceapply prapply " 
				+ " where prapply.apply_id =  (select max(prapply.apply_id)  from tb_p_priceapply prapply  where {prapply.branch_id = ?  and} prapply.apply_type = '1' and prapply.status in ('3','2') "
				+ "and prapply.apply_kind = '2' {and prapply.valid_start <= to_date(?, 'yyyy/MM/dd HH:mi:ss')} {and prapply.valid_end >=  to_date(?, 'yyyy/MM/dd HH:mi:ss')} "
                + "{and prapply.valid_day like '%' || ? || '%'})) a  where {prdetail.content = ?  and} prdetail.apply_id = a.apply_id {and prdetail.room_type = ?}) app  " 
                + "on  rt.room_type = app.room_types where rt.branch_id = {?} ) d where b.room_type = d.room_type  {and b.room_type = ?} {and d.room_type =  ?} ";
		return findBySQL(sql, new String[] { branchId, date, date, todayNum, rpId, roomType, branchId,  branchId, date, date, todayNum, rpId, roomType, branchId, roomType, roomType});
	}

	public List<?> getWorkbill(String branchid,String loginstaff) throws Exception {
		List<?> result = findBySQL("gdsworkbill", new String[] { branchid }, true);
		return result;
	}

	public List<?> queryHouse(String userid, Pagination pagination) {
		String sql = "select h.house_id houseid from TB_P_HOUSE h, TB_C_HOUSEACCOUNT t  where h.staff_id = t.houseaccount_name and h.status <> '0' and  t.staff_id like '%'|| ? ||'%'";
	return findBySQLWithPagination(sql, new String[] { userid}, pagination);
	}
	
	public void deleteOrdchin(String orderId){
		String sql = "delete from TB_P_ORDERCHECKPRICE where order_id = '" + orderId + "'";
		this.executeUpdateSQL(sql);
	}
	
	public void updateOrdchin(String branchId, String dataId, String dayPrice){
		String sql = "update  TB_P_ORDERCHECKPRICE set BRANCH_ID = '" + branchId + "', DAYPRICE = '" + dayPrice + "' where data_id = '" + dataId + "'";
		this.executeUpdateSQL(sql);
	}
	
	public void deleteOrdchinbydate(Date startDate, Date endDate, String orderId){
		String sql = "delete " +
				"from tb_p_ordercheckprice where order_id = '" + orderId + "' " +
				"and whichday >= to_date('" + DateUtil.d2s(startDate, "yyyy-MM-dd") + "', 'yyyy-mm-dd') " +
				"and whichday <= to_date('" + DateUtil.d2s(endDate, "yyyy-MM-dd") + "', 'yyyy-mm-dd')";
		this.findBySQL(sql);
	}
	
	public List<?> getorderchePricebydate(Date startDate, Date endDate, String orderId){
		String sql = "select * " +
				"from tb_p_ordercheckprice where order_id = '" + orderId + "' " +
				"and whichday >= to_date('" + DateUtil.d2s(startDate, "yyyy-MM-dd") + "', 'yyyy-mm-dd') " +
				"and whichday <= to_date('" + DateUtil.d2s(endDate, "yyyy-MM-dd 23:59:59") + "', 'yyyy-mm-dd HH24:MI:SS')";
		return this.findBySQL(sql);
	}
	
	public Member  findMemberhasRank(String mobile, String idcard){
		String sql = "getmemberhasrank";
		List<?> list = this.findBySQL(sql,new String[]{mobile, idcard}, true);
		Member member = null;
		if(list != null && !list.isEmpty()){
			if(list.size() > 1){
				System.out.println("警告!数据库中存在多条相同手机号和身份会员的数据，手机号： " + mobile + "，身份证号： " + idcard);
			}
			Map<?, ?> map = (Map<?, ?>) list.get(0);
			member = new Member();
			member.setMemberId((String)map.get("MEMBER_ID"));
			member.setMobile((String)map.get("MOBILE"));
			member.setIdcard((String)map.get("IDCARD"));
			member.setSource((String)map.get("SOURCE"));
		}
		return member;
		
	}
	
	public void newandsaveaccount(String memberId){
		MemberAccount account = new MemberAccount();
		account.setAccountId(this.getSequence("SEQ_ACCOUNT_ID"));
		account.setMemberId(memberId);
		account.setCurrBalance((double) 0);// 当前余额
		account.setCurrIntegration((long) 0);// 当前积分
		account.setTotalRecharge((double) 0);// 充值金额
		account.setDiscountGift((double) 0);// 折扣赠送
		account.setChargeGift((double) 0);// 充值赠送
		account.setTotalConsume((double) 0);// 消费金额
		account.setTotalIntegration((long) 0);// 获取积分
		account.setIngegrationGift((long) 0);// 积分赠送
		account.setTotalConsIntegration((long) 0);// 消费积分
		account.setTotalRoomnights(0);// 总房晚
		account.setCurrRoomnights(0);// 当前房晚
		account.setTotalNoshow((short) 0);// 总未现天数
		account.setCurrNoshow((short) 0);// 当前未现天数
		account.setThisYearIntegration((long) 0);// 当年积分
		account.setRecordTime(new Date());
		account.setStatus("1");
		this.save(account);
	}
	
	public void updatemobilebyid(String memberId, String mobile){
		String sql = "update tb_c_member set mobile = '" + mobile + "' where member_id = '" + memberId + "'";
		this.executeUpdateSQL(sql);
	}
	
	public void updateidcardbyid(String memberId, String idcard){
		String sql = "update tb_c_member set idcard = '" + idcard + "' where member_id = '" + memberId + "'";
		this.executeUpdateSQL(sql);
	}
	
}