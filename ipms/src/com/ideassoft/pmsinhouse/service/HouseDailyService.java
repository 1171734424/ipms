package com.ideassoft.pmsinhouse.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ideassoft.bean.Bill;
import com.ideassoft.bean.Order;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.dao.GenericDAOImpl;
import com.ideassoft.util.DateUtil;

@Service
public class HouseDailyService extends GenericDAOImpl {

	public void upateroomPrice(String branchid, String rpKind, String rpId, String roomType, String status, String state) {
		String sql = "update Tb_p_Roomprice r set r.state = '" + state + "' where r.branch_id = '" + branchid
				+ "' and r.rp_kind = '" + rpKind + "' and r.rp_id='" + rpId + "' " + "and r.room_type = '" + roomType
				+ "' and r.status = '" + status + "'";
		
		
		this.executeUpdateSQL(sql);
	}
	
	
	public List<?> queryOrderPriceByDate(String time) {
		String sql = "queryordpricebydate";
		return findBySQL(sql, new String[] { time }, true);
	}
	
	public void upateroomstatus(String branchid, String roomid, String status) {
		String sql = "update tb_p_room set status = '" + status + "', record_time = sysdate where  branch_id = " + branchid + " and room_id = "
				+ roomid;
		this.executeUpdateSQL(sql);
	}
	
	public void saveCleanServicePrices(Bill clean, Bill service, Order orderdata, String admin){
		Date date = new Date();
		Bill bill;
		if(null != clean){
			bill = new Bill();
			String billId = DateUtil.currentDate("yyMMdd") + orderdata.getBranchId() + this.getSequence("SEQ_NEW_BILL");
			bill.setBillId(billId);
			bill.setBranchId(orderdata.getBranchId());
			bill.setCheckId(orderdata.getOrderId());
			bill.setProjectId("1236");
			bill.setProjectName("保洁费");
			bill.setDescribe("保洁费");
			bill.setCost(clean.getPay());
			bill.setPay(0.0);
			bill.setPayment("5");
			bill.setStatus("1");
			bill.setRecordTime(date);
			bill.setRecordUser(admin);
			this.save(bill);
		}
		if(null != service){
			bill = new Bill();
			String billId = DateUtil.currentDate("yyMMdd") + orderdata.getBranchId() + this.getSequence("SEQ_NEW_BILL");
			bill.setBillId(billId);
			bill.setBranchId(orderdata.getBranchId());
			bill.setCheckId(orderdata.getOrderId());
			bill.setProjectId("1237");
			bill.setProjectName("服务费");
			bill.setDescribe("服务费");
			bill.setCost(service.getPay());
			bill.setPay(0.0);
			bill.setPayment("5");
			bill.setStatus("1");
			bill.setRecordTime(date);
			bill.setRecordUser(admin);
			this.save(bill);
		}
	}

}