package com.ideassoft.crm.service;

import org.springframework.stereotype.Service;

import com.ideassoft.bean.MemberAccount;
import com.ideassoft.bean.SysParam;
import com.ideassoft.core.dao.GenericDAOImpl;
import com.ideassoft.core.exception.DAOException;

@Service
public class IntegralService extends GenericDAOImpl {

	// public List<SysParam> getParam(String param){
	// String sql =
	// "select * from TP_C_SYSPARAM t where t.param_type='"+param+"'";
	// return findBySQL(sql);
	// }
	public void saveIfo(Object obj) {
		this.save(obj);
	}

	public Object getParam(String param) {
		return findById(SysParam.class, param);
	}


	public String getSequence(String seqName) throws DAOException {
		return getSequence(seqName, null);
	}

}
