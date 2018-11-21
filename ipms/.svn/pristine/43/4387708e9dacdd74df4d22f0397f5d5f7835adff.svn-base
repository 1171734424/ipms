package com.ideassoft.crm.service;

import org.springframework.stereotype.Service;

import com.ideassoft.bean.Member;
import com.ideassoft.bean.MemberAccount;
import com.ideassoft.core.dao.GenericDAOImpl;
import com.ideassoft.core.exception.DAOException;

@Service
public class MemberService extends GenericDAOImpl {

	public String getSequence(String seqName) throws DAOException {
		return getSequence(seqName, null);
	}

	public void saveIfo(Object obj) {
		this.save(obj);
	}

	// 根据ID获取会员账户表
	public Object getCustomerInfo(String account_Id) {
		return findById(MemberAccount.class, account_Id);
	}

	// 根据ID获取会员资料表
	public Object getmember(String memberid) {
		return findById(Member.class, memberid);
	}

}
