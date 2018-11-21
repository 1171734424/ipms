package com.ideassoft.hotel.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ideassoft.bean.wrapper.MultiQueryOrder;
import com.ideassoft.core.constants.CommonConstants;
import com.ideassoft.core.dao.GenericDAOImpl;
import com.ideassoft.core.page.Pagination;

@Service
public class HotelOrderService extends GenericDAOImpl {
	public List<?> queryOrderData(String branchId, MultiQueryOrder order,String[] status, Pagination pagination) {
		String sql = "select ord.room_remark ROOMREMARK, ord.reception_remark RECEPTIONREMARK,ord.order_id orderid,ord.branch_id branchid,transTitlestwo(ord.room_type, 'tp_p_roomtype', 'room_type', 'room_name','branch_id', {?}) roomname,"
				+ "ord.room_price roomprice,  transtitlestwo(ord.guarantee,'TP_C_SYSPARAM','CONTENT','PARAM_NAME','PARAM_TYPE','GUARANTEE') guarantee," 
				+ " transtitlestwo(ord.status,'TP_C_SYSPARAM','CONTENT', 'PARAM_NAME','PARAM_TYPE','ORDERSTATUS') status," 
				+ " transtitlestwo(ord.source,'TP_C_SYSPARAM','CONTENT','PARAM_NAME','PARAM_TYPE','SOURCE') source,ord.limited limited,transTitles(ord.order_user,"
				+ "'tb_c_member','member_id','member_name') orderuser,to_char(ord.order_time,'yyyy/MM/dd') ordertime,to_char(ord.arrival_time,'yyyy/MM/dd') arrivaltime,"
				+ "to_char(ord.leave_time,'yyyy/MM/dd') leavetime,ord.m_phone phone,"
				+ "ord.remark remark from tb_p_order ord where ord.theme = ? {and ord.branch_id = ?} {and ord.order_id like '%' || ? || '%'}"
				+ "{and ord.room_type  = ? } { and ord.status like '%' || ? || '%'}{ and ord.source like '%' || ? || '%' }{and "
				+ "ord.guarantee like '%' || ? || '%' }{and transtitles(ord.order_user, 'tb_c_member', 'member_id', 'member_name') like '%' || ? || '%' }{"
				+ "and ord.m_phone like '%' || ? || '%' }"
				/*+ " {and ord.order_time >= to_date(?, 'yyyy/MM/dd')}"
				+ " {and ord.order_time <= to_date(?, 'yyyy/MM/dd')}"
				+ " {and ord.arrival_time >= to_date(?, 'yyyy/MM/dd')}"
				+ " {and ord.arrival_time <= to_date(?, 'yyyy/MM/dd')}"*/
				+ " {and to_char(ord.order_time, 'yyyy/MM/dd') >= ? }"
				+ " {and to_char(ord.order_time, 'yyyy/MM/dd') <= ? }"
				+ " {and to_char(ord.arrival_time, 'yyyy/MM/dd') >= ? }"
				+ " {and to_char(ord.arrival_time, 'yyyy/MM/dd') <= ? }"
				+ " {and ord.order_user like '%' || ? || '%' }";
		if(order.getStatus() != null && !order.getStatus().isEmpty()){
			//sql = sql + " and ord.status = " + order.getStatus();
		} else if (order.getArrivalTime() != null){
			sql = sql + " and ord.status in ("+ status[1] + ", " + status[2] + ")";
			sql = sql + " order by ord.arrival_time desc";
		} else{
			sql = sql + " and ord.status in (" + 
					status[0] + ", " + status[1] + ", " + status[2] + ")";
		}
		return findBySQLWithPagination(sql, new String[] {  branchId, CommonConstants.Branch.HOTELID, branchId, order.getOrderId(), order.getRoomType(),
				order.getStatus(), order.getSource(), order.getGuarantee(), 
				order.getOrderUser(), order.getMphone(), order.getOrderTime(),
				order.getOrdtimes(),order.getArrivalTime(),order.getArrtimes(),order.getMemberId()}, pagination);
	}
	
	public List<?> queryOrderDataInTab(String branchId, MultiQueryOrder order,String[] status, Pagination pagination) {
		String sql = "select ord.order_id orderid,ord.branch_id branchid,transTitlestwo(ord.room_type, 'tp_p_roomtype', 'room_type', 'room_name','branch_id', {?}) roomname,"
			+ "ord.room_price roomprice,  transtitlestwo(ord.guarantee,'TP_C_SYSPARAM','CONTENT','PARAM_NAME','PARAM_TYPE','GUARANTEE') guarantee," 
			+ " transtitlestwo(ord.status,'TP_C_SYSPARAM','CONTENT', 'PARAM_NAME','PARAM_TYPE','ORDERSTATUS') status," 
			+ " transtitlestwo(ord.source,'TP_C_SYSPARAM','CONTENT','PARAM_NAME','PARAM_TYPE','SOURCE') source,ord.limited limited,transTitles(ord.order_user,"
			+ "'tb_c_member','member_id','member_name') orderuser,to_char(ord.order_time,'yyyy/MM/dd') ordertime,to_char(ord.arrival_time,'yyyy/MM/dd') arrivaltime,"
			+ "to_char(ord.leave_time,'yyyy/MM/dd') leavetime,ord.m_phone phone,"
			+ "ord.remark remark from tb_p_order ord where ord.theme = ? {and ord.branch_id = ?} {and ord.order_id like '%' || ? || '%'}"
			+ "{and ord.room_type  = ?  } { and ord.status like '%' || ? || '%'}{ and ord.source like '%' || ? || '%' }{and "
			+ "ord.guarantee like '%' || ? || '%' } "
			+ " {and to_char(ord.order_time, 'yyyy/MM/dd') >= ? }"
			+ " {and to_char(ord.order_time, 'yyyy/MM/dd') <= ? }"
			+ " {and to_char(ord.arrival_time, 'yyyy/MM/dd') >= ? }"
			+ " {and to_char(ord.arrival_time, 'yyyy/MM/dd') <= ? }"
			+ " {and ord.order_user like '%' || ? || '%' }";
		if(order.getStatus() != null && !order.getStatus().isEmpty()){
			//sql = sql + " and ord.status = " + order.getStatus();
		} else {
			sql = sql + " and ord.status in ("+ status[1] + ", " + status[2] + ")";
		}
		return findBySQLWithPagination(sql, new String[] {  branchId, CommonConstants.Branch.HOTELID, branchId, order.getOrderId(), order.getRoomType(),
				order.getStatus(), order.getSource(), order.getGuarantee(), 
				 order.getOrderTime(),
				order.getOrdtimes(),order.getArrivalTime(),order.getArrtimes(),order.getMemberId()}, pagination);
	}
	
	/**
	 * 随机生成身份证信息(模拟读卡器读取身份证信息)
	 */
	public Map<String, String> readGuestInfo() {
		String year = "";
		String month = Integer.toString((int) (Math.random() * 10 + 1));
		if (month.length() == 1) {
			month = "0" + month;
		}
		int age = (int) (Math.random() * 100 + 1);
		year = Integer.toString(2017 - age);
		if (age < 18) {
			age = 18;
			year = "1999";
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", "张三" + (int) (Math.random() * 10) + 1);
		map.put("age", Integer.toString(age));
		map.put("gender", "男");
		map.put("idnumber", "320721" + year + month + "282478");
		return map;

	}
}
