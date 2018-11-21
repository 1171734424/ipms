package com.ideassoft.apartment.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ideassoft.core.dao.GenericDAOImpl;
@Service
public class ApartmentStopSellService extends GenericDAOImpl {
	
	public void apartmentupateroomstatus(String branchid, String roomid, String status) {
		String sql = "update tb_p_room set status = '" + status + "', record_time = sysdate where  branch_id = " + branchid + " and room_id = "
				+ roomid;
		this.executeUpdateSQL(sql);
	}
	
	public List<?> apartmentqueryroomcounts(String roomid, String branchId) {
		String sql = "select t.*, t.rowid from TB_P_ROOM t where t.room_id = ? and t.branch_id = ?";
		return this.findBySQL(sql, new String[] { roomid, branchId });
	}
}
