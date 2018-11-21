package com.ideassoft.pmsinhouse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ideassoft.bean.Branch;
import com.ideassoft.core.dao.GenericDAOImpl;


@Service
public class HouseRepairService extends GenericDAOImpl {
	
	public Branch queryLoginUserByType(String branchId) {
		Branch result = (Branch)findOneByProperties(Branch.class, "branchId", branchId, "status", "1");
		return result != null ? result : null;
	}
	
	public List<?> getPoststaffphone(String auditpostnow,String branchid) throws Exception {
		List<?> result = findBySQL("poststaffphone", new String[] {auditpostnow,branchid}, true);
		return result;
	}

}
