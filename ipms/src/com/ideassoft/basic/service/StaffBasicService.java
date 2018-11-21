package com.ideassoft.basic.service;



import java.util.List;

import org.springframework.stereotype.Service;

import com.ideassoft.core.dao.GenericDAOImpl;

@Service
public class StaffBasicService extends GenericDAOImpl {
	public List<?> checkStaffRepeat(String beanProperty, String PropertyValue){
		String sql = "select * from TB_C_STAFF where " + beanProperty + " ='" + PropertyValue + "' and status in(1,2,3)";
		List<?> result = findBySQL(sql);
		return result != null ? result : null;
	}
}
