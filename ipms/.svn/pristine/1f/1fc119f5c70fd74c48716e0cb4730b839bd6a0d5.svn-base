package com.ideassoft.pmsinhouse.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ideassoft.core.dao.GenericDAOImpl;
import com.ideassoft.core.page.Pagination;

@Service
public class HouseForwordService extends GenericDAOImpl{
	public List<?> queryHouse(String userid, Pagination pagination) {
		String sql = "select h.house_id houseid,h.housename housename from TB_P_HOUSE h, TB_C_HOUSEACCOUNT t  where h.staff_id = t.houseaccount_name and h.status <> '0' and  t.staff_id like '%'|| ? ||'%'";
	return findBySQLWithPagination(sql, new String[] { userid}, pagination);
	}
}
