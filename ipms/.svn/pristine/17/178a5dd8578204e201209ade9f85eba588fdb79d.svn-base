package com.ideassoft.pms.service;

import org.springframework.stereotype.Service;

import com.ideassoft.core.dao.GenericDAOImpl;

@Service
public class DailyService extends GenericDAOImpl {

	public void upateroomPrice(String branchid, String rpKind, String rpId, String roomType, String status, String state) {
		String sql = "update Tb_p_Roomprice r set r.state = '" + state + "' where r.branch_id = '" + branchid
				+ "' and r.rp_kind = '" + rpKind + "' and r.rp_id='" + rpId + "' " + "and r.room_type = '" + roomType
				+ "' and r.status = '" + status + "'";
		
		
		this.executeUpdateSQL(sql);
	}

}