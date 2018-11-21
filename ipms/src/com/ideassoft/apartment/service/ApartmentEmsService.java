package com.ideassoft.apartment.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ideassoft.core.dao.GenericDAOImpl;
@Service
public class ApartmentEmsService extends GenericDAOImpl {
	
	public List<?> getWorkbill(String branchid,String loginstaff) throws Exception {
		List<?> result = findBySQL("gdsworkbill", new String[] { branchid }, true);
		return result;
	}
}
