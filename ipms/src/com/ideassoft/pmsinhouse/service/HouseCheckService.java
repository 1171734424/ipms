package com.ideassoft.pmsinhouse.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ideassoft.core.dao.GenericDAOImpl;

@Service
public class HouseCheckService extends GenericDAOImpl {
	
	public void updatehouseStatus(String houseid, String status){
		String sql = "update tb_p_house set status = '" + status + "' " +
				"where house_id = '" + houseid + "'";
		this.executeUpdateSQL(sql);
	}
	
	public List<?> findhousebystaffid(String staffid, String status, String branchid){
		String sql = 
				"select h.* from TB_P_HOUSE h, TB_C_HOUSEACCOUNT t where h.staff_id = t.houseaccount_name and t.staff_id like '%"
				+staffid +"%'"+"and  t.status = '1' and h.status in (" + status + ") " +"and house_id not in '" + branchid + "'";
		return this.findBySQL(sql);
	}

}
