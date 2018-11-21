package com.ideassoft.basic.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ideassoft.core.dao.GenericDAOImpl;

@Service
public class ApartmentPriceBasicService extends GenericDAOImpl{
	public List<?> getJudgerpexit(String theme, String branchid, String rpid, String rptypename, String status,
			String rpkind) throws Exception {
		List<?> result = findBySQL("judgerpexit", new String[] { theme, branchid, rpid, rptypename, status, rpkind },
				true);
		return result;
	}
	public List<?> getRpsetup() throws Exception {
		return findBySQL("rpsetup", true);
	}
}
