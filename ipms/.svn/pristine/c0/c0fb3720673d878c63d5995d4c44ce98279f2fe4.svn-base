package com.ideassoft.apartment.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ideassoft.core.dao.GenericDAOImpl;

@Service
public class ApartmentOrderService extends GenericDAOImpl {
	
	public void upateroomstatus(String branchid, String roomid, String status) {
		String sql = "update tb_p_room set status = '" + status + "', record_time = sysdate where  branch_id = " + branchid + " and room_id = "
				+ roomid;
		this.executeUpdateSQL(sql);
	}
	
	public List<?> queryAptOrder(String time, String branchId, String roomId) {
		String sql = "queryaptorderstay";
		return this.findBySQL(sql,new String[]{time,branchId,roomId}, true);
		
	}
}
