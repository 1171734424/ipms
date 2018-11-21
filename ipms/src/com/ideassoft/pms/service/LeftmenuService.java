package com.ideassoft.pms.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ideassoft.bean.CashKey;
import com.ideassoft.bean.LoginLogId;
import com.ideassoft.core.dao.GenericDAOImpl;
import com.ideassoft.core.page.Pagination;

@Service
public class LeftmenuService extends GenericDAOImpl {
	public List<?> getMemberdata(String Mnumber) throws Exception {
		List<?> result = findBySQL("selectmember", new String[] { Mnumber }, true);
		return result;
	}

	public List<?> getRoomtype(String branchid) throws Exception {
		List<?> result = findBySQL("orderselectroomtype", new String[] { branchid }, true);
		return result;
	}

	public List<?> getTheme() throws Exception {
		String sql = "orderselecttheme";
		return findBySQL(sql, true);
	}

	public List<?> getRoomid(String theme, String roomtype) throws Exception {
		List<?> result = findBySQL("roomselect", new String[] { theme, roomtype }, true);
		return result;
	}

	public List<?> getTypename(String roomtype) throws Exception {
		List<?> result = findBySQL("roomtypeselect", new String[] { roomtype }, true);
		return result;
	}

	public List<?> getRoomprice(String rpId, String branchid) throws Exception {
		List<?> result = findBySQL("selectroomprice", new String[] { rpId, branchid }, true);
		return result;
	}
	
	public List<?> getDiscountRoomprice(String rpId, String branchid) throws Exception {
		List<?> result = findBySQL("selectpriceDiscount", new String[] { rpId, branchid }, true);
		return result;
	}
	
	public List<?> getRoompriceadjust(String rpId, String branchid) throws Exception {
		List<?> result = findBySQL("selectroompricead", new String[] { rpId, branchid }, true);
		return result;
	}
	
	public List<?> getDiscountRoompriceadjust(String rpId, String branchid) throws Exception {
		List<?> result = findBySQL("selectadpiceDiscount", new String[] { rpId, branchid }, true);
		return result;
	}

	public List<?> getGuarantee() throws Exception {
		String sql = "orderselectguarantee";
		return findBySQL(sql, true);
	}

	/*public List<?> getOrderdata() throws Exception {
		String sql = "ordersearchdata";
		return findBySQL(sql, true);
	}*/

	public List<?> getRooms() throws Exception {
		String sql = "ordergetrooms";
		return findBySQL(sql, true);
	}
	

	public List<?> getOrdercondition(Pagination pagination,String orderid, String orderuser, String roomtype, String mobile,
			 String memberid, String branchid) throws Exception {
		List<?> result = findBySQLWithPagination("ordersearchcondition", new String[] {branchid, branchid,orderid, orderuser,
				roomtype, mobile, memberid, branchid }, pagination,  true);
		return result;
	}

	public List<?> getGoodssale(Pagination pagination, String branchId) throws Exception {
		List<?> result = findBySQLWithPagination("goodssaledata", new String[] { branchId }, pagination, true);
		return result;
	}

	public List<?> getCategorycondition(String branchid) throws Exception {
		List<?> result = findBySQL("categorycondition", new String[] { branchid }, true);
		return result;
	}

	public List<?> getGdsproject() throws Exception {
		String sql = "gdsproject";
		return findBySQL(sql, true);
	}

	public List<?> getGdsprojectpay() throws Exception {
		String sql = "gdsprojectpay";
		return findBySQL(sql, true);
	}

	public List<?> getWorkbill(String branchid,String loginstaff) throws Exception {
		List<?> result = findBySQL("gdsworkbill", new String[] { branchid }, true);
		return result;
	}

	public List<?> getGdsalecondition(String goodsid, String goodsname, String categoryid, String branchId,
			Pagination pagination) throws Exception {
		List<?> result = findBySQLWithPagination("gdsalecondition", new String[] { goodsid, goodsname, categoryid,
				branchId }, pagination, true);
		return result;
	}

	public List<?> getGdsroomid(String branchId,String theme) throws Exception {
		List<?> result = findBySQL("gdsroomid", new String[] { branchId, branchId, branchId }, true);
		return result;
	}

	public List<?> getMscdta(String mphone, String mcard) throws Exception {
		List<?> result = findBySQL("memberscondition", new String[] { mphone, mcard }, true);
		return result;
	}

	public List<?> getGdmanagecondition(String goodsid, String goodsname, String categoryid, String status,
			String branchId, Pagination

			pagination) throws Exception {
		List<?> result = findBySQLWithPagination("gdmanagecondition", new String[] { goodsid, goodsname, categoryid,
				status

				, branchId }, pagination, true);
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<?> getRoomids(String orderswitch, String orderroomtype, String orderarrivedate, String orderleavedate)
			throws Exception {
		List result = new ArrayList();
		if (orderswitch.equals("1")) {
			result = findBySQL("selectroomids", new String[] { orderroomtype, orderarrivedate, orderleavedate }, true);
		} else {
			result = findBySQL("switchroomids", new String[] { orderroomtype }, true);
		}
		return result;
	}

	public List<?> getExceptiontype() throws Exception {
		return findBySQL("exceptiontype", true);
	}

	public List<?> getExceptiondata(String branchid, Pagination pagination) throws Exception {
		List<?> result = findBySQLWithPagination("exceptiontypedata", new String[] { branchid }, pagination, true);
		return result;
	}

	public List<?> getExceptioncondition(String exbegintime, String exendtime, String exceptiontype,
			String exceptionstatus, String branchid,

			Pagination pagination) throws Exception {
		List<?> result = findBySQLWithPagination("exceptiontypecd", new String[] { exbegintime, exendtime,

		exceptiontype, exceptionstatus, branchid }, pagination, true);
		return result;
	}

	public List<?> getCashiercashdatanew(String branchid, String loginstaff, String logintime) throws Exception {
		String sql = "select sum(sh.cashinpay) cashin,sum(sh.cashoutpay) cashout,sh.branchid  from(   "
				+ "select DISTINCT (select sum(b.pay)  from tb_p_bill b   " + "where b.branch_id = '"
				+ branchid
				+ "'       "
				+ "and b.record_user = '"
				+ loginstaff
				+ "'     "
				+ "and to_char( b.record_time, 'YYYY/MM/DD hh24:mi:ss') >=  '"
				+ logintime
				+ "'   "
				+ "and to_char( b.record_time, 'YYYY/MM/DD hh24:mi:ss') <= to_char(sysdate, 'YYYY/MM/DD hh24:mi:ss')    "
				+ "and  b.payment = '1'      "
				+ "and  b.status = '4'      "
				+ "and b.pay>=0     "
				+ "group by b.branch_id) cashinpay,(select sum(b.pay) from  tb_p_bill b      "
				+ "where b.branch_id = '"
				+ branchid
				+ "'      "
				+ "and b.record_user = '"
				+ loginstaff
				+ "'       "
				+ "and to_char( b.record_time, 'YYYY/MM/DD hh24:mi:ss') >=  '"
				+ logintime
				+ "'   "
				+ "and to_char( b.record_time, 'YYYY/MM/DD hh24:mi:ss') <= to_char(sysdate, 'YYYY/MM/DD hh24:mi:ss')    "
				+ "and  b.payment = '1'    "
				+ "and  b.status = '4'      "
				+ "and b.pay<0     "
				+ "group by b.branch_id) cashoutpay,b.branch_id branchid   "
				+ "from tb_p_bill b      "
				+ "where b.branch_id = '"
				+ branchid
				+ "'     "
				+ "and b.record_user = '"
				+ loginstaff
				+ "'     "
				+ "and to_char( b.record_time, 'YYYY/MM/DD hh24:mi:ss') >=  '"
				+ logintime
				+ "'   "
				+ "and to_char( b.record_time, 'YYYY/MM/DD hh24:mi:ss') <= to_char(sysdate, 'YYYY/MM/DD hh24:mi:ss')     "
				+ "and  b.payment = '1'      "
				+ "and  b.status = '4'      "
				+ "union all      "
				+ "(select DISTINCT (select sum(w.pay)  from tb_p_workbilldetail w       "
				+ "where w.branch_id = '"
				+ branchid
				+ "'       "
				+ "and w.record_user = '"
				+ loginstaff
				+ "'     "
				+ "and to_char( w.record_time, 'YYYY/MM/DD hh24:mi:ss') >=  '"
				+ logintime
				+ "'   "
				+ "and to_char( w.record_time, 'YYYY/MM/DD hh24:mi:ss') <= to_char(sysdate, 'YYYY/MM/DD hh24:mi:ss')     "
				+ "and  w.payment = '1'     "
				+ "and  w.status = '1'      "
				+ "and w.pay>=0     "
				+ " group by w.branch_id) cashinpay, (select sum(w.pay)  from tb_p_workbilldetail w       "
				+ "where w.branch_id = '"
				+ branchid
				+ "'      "
				+ "and w.record_user = '"
				+ loginstaff
				+ "'    "
				+ "and to_char( w.record_time, 'YYYY/MM/DD hh24:mi:ss') >=  '"
				+ logintime
				+ "'   "
				+ "and to_char( w.record_time, 'YYYY/MM/DD hh24:mi:ss') <= to_char(sysdate, 'YYYY/MM/DD hh24:mi:ss')    "
				+ "and  w.payment = '1'      "
				+ "and  w.status = '1'      "
				+ "and w.pay<0     "
				+ "group by w.branch_id) cashoutpay,w.branch_id branchid   "
				+ "from tb_p_workbilldetail w      "
				+ "where w.branch_id =  '"
				+ branchid
				+ "'       "
				+ "and w.record_user = '"
				+ loginstaff
				+ "'      "
				+ "and to_char( w.record_time, 'YYYY/MM/DD hh24:mi:ss') >=  '"
				+ logintime
				+ "'   "
				+ "and to_char( w.record_time, 'YYYY/MM/DD hh24:mi:ss') <= to_char(sysdate, 'YYYY/MM/DD hh24:mi:ss')   "
				+ "and  w.status = '1'      " + "and  w.payment = '1')      " + ")sh group by sh.branchid ";
		return findBySQL(sql);
	}

	public List<?> getCashiercarddatanew(String branchid, String loginstaff, String logintime) throws Exception {
		String sql = "  select sum(rd.cardpay) card,rd.branchid from(   "
				+ "select b.pay cardpay,b.branch_id branchid    " + "from tb_p_bill b     " + "where  b.branch_id = '"
				+ branchid
				+ "'     "
				+ "and b.record_user = '"
				+ loginstaff
				+ "'     "
				+ "and to_char( b.record_time, 'YYYY/MM/DD hh24:mi:ss') >=  '"
				+ logintime
				+ "'  "
				+ "and to_char( b.record_time, 'YYYY/MM/DD hh24:mi:ss') <= to_char(sysdate, 'YYYY/MM/DD hh24:mi:ss')   "
				+ "and  b.status = '4'      "
				+ "and  b.payment = '2'     "
				+ "union all     "
				+ "(select w.pay cardpay,w.branch_id branchid      "
				+ "from tb_p_workbilldetail w     "
				+ "where w.branch_id = '"
				+ branchid
				+ "'     "
				+ "and w.record_user = '"
				+ loginstaff
				+ "'    "
				+ "and to_char( w.record_time, 'YYYY/MM/DD hh24:mi:ss') >=  '"
				+ logintime
				+ "'  "
				+ "and to_char( w.record_time, 'YYYY/MM/DD hh24:mi:ss') <= to_char(sysdate, 'YYYY/MM/DD hh24:mi:ss')   "
				+ "and  w.status = '1'      " + "and  w.payment = '2')     " + ")rd group by rd.branchid  ";
		return findBySQL(sql);
	}

	public List<?> getCashiercashdata(String branchid, String cashierstaff, String shiftid, String cashbox)
			throws Exception {
		String sql = "select sum(sh.cashinpay) cashin,sum(sh.cashoutpay) cashout,sh.shift from(  "
				+ "select DISTINCT (select sum(b.pay)  from tb_p_bill b   " + "where b.shift = '"
				+ shiftid
				+ "'   "
				+ "and  b.cash_box = '"
				+ cashbox
				+ "'   "
				+ "and b.branch_id = '"
				+ branchid
				+ "'   "
				+ "and b.record_user = '"
				+ cashierstaff
				+ "'   "
				+ "and to_char( b.record_time, 'YYYY/MM/DD') = to_char(sysdate, 'YYYY/MM/DD')   "
				+ "and  b.payment = '1'   "
				+ "and b.pay>=0  "
				+ "group by b.shift) cashinpay,(select sum(b.pay) from  tb_p_bill b   "
				+ "where b.shift = '"
				+ shiftid
				+ "'   "
				+ "and  b.cash_box = '"
				+ cashbox
				+ "'   "
				+ "and b.branch_id = '"
				+ branchid
				+ "'   "
				+ "and b.record_user = '"
				+ cashierstaff
				+ "'   "
				+ "and to_char( b.record_time, 'YYYY/MM/DD') = to_char(sysdate, 'YYYY/MM/DD')   "
				+ "and  b.payment = '1'   "
				+ "and b.pay<0  "
				+ "group by b.shift) cashoutpay,b.shift shift   "
				+ "from tb_p_bill b   "
				+ "where b.shift = '"
				+ shiftid
				+ "'   "
				+ "and  b.cash_box = '"
				+ cashbox
				+ "'   "
				+ "and b.branch_id = '"
				+ branchid
				+ "'   "
				+ "and b.record_user = '"
				+ cashierstaff
				+ "'   "
				+ "and to_char( b.record_time, 'YYYY/MM/DD') = to_char(sysdate, 'YYYY/MM/DD')   "
				+ "and  b.payment = '1'   "
				+ "union all   "
				+ "(select (select sum(w.pay)  from tb_p_workbilldetail w    "
				+ "where w.shift = '"
				+ shiftid
				+ "'   "
				+ "and  w.cash_box = '"
				+ cashbox
				+ "'   "
				+ "and w.branch_id = '"
				+ branchid
				+ "'   "
				+ "and w.record_user = '"
				+ cashierstaff
				+ "'   "
				+ "and to_char( w.record_time, 'YYYY/MM/DD') = to_char(sysdate, 'YYYY/MM/DD')   "
				+ "and  w.payment = '1'   "
				+ "and w.pay>=0  "
				+ "group by w.shift) cashinpay, (select sum(w.pay)  from tb_p_workbilldetail w    "
				+ "where w.shift = '"
				+ shiftid
				+ "'   "
				+ "and  w.cash_box = '"
				+ cashbox
				+ "'   "
				+ "and w.branch_id = '"
				+ branchid
				+ "'   "
				+ "and w.record_user = '"
				+ cashierstaff
				+ "'   "
				+ "and to_char( w.record_time, 'YYYY/MM/DD') = to_char(sysdate, 'YYYY/MM/DD')   "
				+ "and  w.payment = '1'   "
				+ "and w.pay<0  "
				+ "group by w.shift) cashoutpay, w.shift shift   "
				+ "from tb_p_workbilldetail w   "
				+ "where w.shift = '"
				+ shiftid
				+ "'  "
				+ "and  w.cash_box = '"
				+ cashbox
				+ "'   "
				+ "and w.branch_id = '"
				+ branchid
				+ "'     "
				+ "and w.record_user = '"
				+ cashierstaff
				+ "'   "
				+ "and to_char( w.record_time, 'YYYY/MM/DD') = to_char(sysdate, 'YYYY/MM/DD')   "
				+ "and  w.payment = '1')   " + ")sh group by sh.shift ";
		return findBySQL(sql);
	}

	public List<?> getCashiercarddata(String branchid, String cashierstaff, String shiftid, String cashbox)
			throws Exception {
		String sql = "select sum(rd.cardpay) card,rd.shift from(   " + "select b.pay cardpay,b.shift shift   "
				+ "from tb_p_bill b   " + "where b.shift = '"
				+ shiftid
				+ "'  "
				+ "and  b.cash_box = '"
				+ cashbox
				+ "'  "
				+ "and b.branch_id = '"
				+ branchid
				+ "'   "
				+ "and b.record_user = '"
				+ cashierstaff
				+ "'   "
				+ "and to_char( b.record_time, 'YYYY/MM/DD') = to_char(sysdate, 'YYYY/MM/DD')   "
				+ "and  b.payment = '2'   "
				+ "union all   "
				+ "(select w.pay cardpay,w.shift shift   "
				+ "from tb_p_workbilldetail w   "
				+ "where w.shift = '"
				+ shiftid
				+ "'   "
				+ "and  w.cash_box = '"
				+ cashbox
				+ "'   "
				+ "and w.branch_id = '"
				+ branchid
				+ "'   "
				+ "and w.record_user = '"
				+ cashierstaff
				+ "'    "
				+ "and to_char( w.record_time, 'YYYY/MM/DD') = to_char(sysdate, 'YYYY/MM/DD')   "
				+ "and  w.payment = '2')   " + ")rd group by rd.shift  ";
		return findBySQL(sql);
	}

	public List<?> getLastshift(String branchid) throws Exception {
		List<?> result = findBySQL("lastshift", new String[] { branchid }, true);
		return result;
	}

	public List<?> getLastshiftvalue(String lastbranchidlast, String currshift, String curruser, String recordtime)
			throws Exception {
		List<?> result = findBySQL("lastshiftvalue",
				new String[] { lastbranchidlast, currshift, curruser, recordtime }, true);
		return result;
	}

	// public List<?> getLastpettyvalue(String lastbranchid) throws Exception {
	// List<?> result = findBySQL("lastpettyvalue", new String[] {
	// lastbranchid}, true);
	// return result;
	// }

	public List<?> getSubmitperson(String branchid,String shifterid) throws Exception {
		List<?> result = findBySQL("submitperson", new String[] { branchid,shifterid }, true);
		return result;
	}

	/*
	 * public List<?> getCashA(String branchid) throws Exception { List<?>
	 * result = findBySQL("getcasha", new String[] { branchid }, true); return
	 * result; }
	 * 
	 * public List<?> getCashB(String branchid) throws Exception { List<?>
	 * result = findBySQL("getcashb", new String[] { branchid }, true); return
	 * result; }
	 */

	public List<?> getCashboxstatus(String lastbranchid) throws Exception {
		List<?> result = findBySQL("cashboxstatus", new String[] { lastbranchid }, true);
		return result;
	}

	public List<?> getBillcard(String branchid, String loginstaff, String logintime) throws Exception {
		List<?> result = findBySQL("billcard", new String[] { branchid, loginstaff, logintime }, true);
		return result;
	}

	public List<?> getWorkcard(String branchid, String loginstaff, String logintime) throws Exception {
		List<?> result = findBySQL("workcard", new String[] { branchid, loginstaff, logintime }, true);
		return result;
	}

	public void upatecashbox(String branchid, String boxname, String recordUser, String updatestatus) {
		CashKey cashKey = new CashKey();
		cashKey.setBranchId(branchid);
		cashKey.setCashBox(boxname);
		String hql1 = "update CashBox set cashStatus = :CASHSTATUS,recordUser = :RECORDUSER where cashKey = :CASHKEY";
		this.executeUpdateHQL(hql1, new String[] { "CASHSTATUS", "CASHKEY", "RECORDUSER" }, new Object[] {
				updatestatus, cashKey, recordUser });
	}

	public List<?> getOrdermsj(String branchid, String ordertheme, String orderroomtype) throws Exception {
		List<?> result = findBySQL("ordermsj", new String[] { branchid, ordertheme, orderroomtype }, true);
		return result;
	}

	public List<?> getLogindata(String cashierstaff) throws Exception {
		List<?> result = findBySQL("loginshiftdata", new String[] { cashierstaff }, true);
		return result;
	}

	public List<?> getLogindataother(String logid) throws Exception {
		List<?> result = findBySQL("loginshiftdataother", new String[] { logid }, true);
		return result;
	}

	public List<?> getLoginnoshiftData(String cashbox) throws Exception {
		List<?> result = findBySQL("loginnoshiftdata", new String[] { cashbox }, true);
		return result;
	}

	public List<?> getLoginstaffData(String curruser) throws Exception {
		List<?> result = findBySQL("loginstaffdata", new String[] { curruser }, true);
		return result;
	}

	public void updateLoglinlog(String updatelogid, String curruser, String updatestate, String oldstate) {
		LoginLogId id = new LoginLogId();
		id.setLogId(updatelogid);
		id.setLoginId(curruser);
		String hql1 = "update LoginLog set state = :STATE where id = :ID ";
		this.executeUpdateHQL(hql1, new String[] { "STATE", "ID" }, new Object[] { updatestate, id });
	}

	/*
	 * select count(*) wcard from tb_p_workbilldetail w where w.shift = ? and
	 * w.cash_box = ? and w.branch_id = ? and w.record_user = ? and to_char(
	 * w.record_time, 'YYYY/MM/DD') = to_char(sysdate, 'YYYY/MM/DD') and
	 * w.payment =
	 * 
	 * '2' order by w.shift
	 */
	
	public List<?> getPettypaydata(String branchId, String recordUser,String start,String end,String voucher,String state) throws Exception {
		List<?> result = findBySQL("pettypaydata", new String[] { branchId, recordUser ,start, end,voucher,state}, 
				true);
		return result;
	}
	
	public List<?> getAllpay(String branchId, String recordUser) throws Exception {
		List<?> result = findBySQL("allpaykinddata", new String[] { branchId, recordUser }, 
				true);
		return result;
	}
	public List<?> getHavepay(String branchId, String recordUser) throws Exception {
		List<?> result = findBySQL("havepaykinddata", new String[] { branchId, recordUser }, 
				true);
		return result;
	}
	
	public List<?> getCashboxkind() throws Exception {
		return findBySQL("cashboxkind", true);
	}
	
	@SuppressWarnings("deprecation")
	public List<?> getNewloginnoshiftData(String loginstaff,String staff,String branchid) throws Exception {
		Date d = new Date();
		int hours = d.getHours();
		List<?> result = null;
		if(hours>=0 && hours<4)
		{
		 result = findBySQL("loginnoshiftDataone", new String[] { loginstaff,staff, branchid }, true);
		}else{
	      result = findBySQL("loginnoshiftDatatwo", new String[] {loginstaff,staff, branchid  }, true);	
		}
		
		return result;
	}
	
	public List<?>getOrderroomname(String ordertheme, String branchid, String orderroomtype) throws Exception {
		List<?> result = findBySQL("orderroomname", new String[] { ordertheme, branchid, orderroomtype}, 
				true);
		return result;
	}

	
	public List<?>getRoombeds(String branchid, String orderroomtype) throws Exception {
		List<?> result = findBySQL("orderroombed", new String[] { branchid,orderroomtype}, true);
		return result;
	}

	public List<?> getBranchlist(String branchId) throws Exception {
		List<?> result = findBySQL("branchlist", new String[] { branchId }, true);
		return result;
	}
	
	public List<?> getShiftbilldatanew(Pagination pagination, String branchId, String recordUser,String start,String end,String voucher,String state,String paytype) throws Exception {
		List<?> result = findBySQLWithPagination("shiftbilldatanew", new String[] { branchId,recordUser,branchId,recordUser,start,end,voucher,state,paytype}, pagination, true);
		return result;
	}
	
	public List<?>getShiftpaycount(String branchId,String dyas) throws Exception {
		List<?> result = findBySQL("shiftpaycount", new String[] { branchId,dyas,branchId,dyas,branchId,dyas,branchId,dyas}, true);
		return result;
	}
	
	public List<?> getShiftbillupdate(String branchId,String days) throws Exception {
		List<?> result = findBySQL("shiftbillupdate", new String[] { branchId,days }, true);
		return result;
	}
	
	public List<?> getShiftwbillupdate(String branchId,String days) throws Exception {
		List<?> result = findBySQL("shiftwbillupdate", new String[] { branchId,days }, true);
		return result;
	}
	
	public List<?> getBasicdayprice(String branchid,String everydaystart,String everydayend,String number) throws Exception {
		List<?> result = findBySQL("basicdayprice", new String[] { branchid, everydaystart,everydayend,number}, true);
		return result;
	}
	
	public List<?> getAdjustdayprice(String branchid,String everydaystart,String everydayend,String number) throws Exception {
		List<?> result = findBySQL("adjustdayprice", new String[] { branchid, everydaystart,everydayend,number }, true);
		return result;
	}
	
	public List<?> getLoginnewdata(String cashierstaff) throws Exception {
		List<?> result = findBySQL("loginshiftnewdata", new String[] { cashierstaff }, true);
		return (result == null || result.size() <= 0) ? null : result;
	}

	public List<?> queryLeaveMoney(String checkid) {
		List<?> result = findBySQL("queryLMoneyApart", new String[] { checkid }, true);
		return (result == null || result.size() <= 0) ? null : result;
	}
}