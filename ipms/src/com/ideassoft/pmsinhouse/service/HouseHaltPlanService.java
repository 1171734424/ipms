package com.ideassoft.pmsinhouse.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ideassoft.core.dao.GenericDAOImpl;

@Service
public class HouseHaltPlanService extends GenericDAOImpl {
	public List<?> queryroomcounts(String roomid, String branchId) {
		String sql = "select t.*, t.rowid from TB_P_ROOM t where t.room_id = ? and t.branch_id = ?";
		return this.findBySQL(sql, new String[] { roomid, branchId });
	}
}
