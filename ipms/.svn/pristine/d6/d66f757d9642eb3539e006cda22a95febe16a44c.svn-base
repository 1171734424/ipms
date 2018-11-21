package com.ideassoft.basic.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ideassoft.core.dao.GenericDAOImpl;
@Service
public class HouseAccountBasicService extends GenericDAOImpl{
	public List<?> findHouseAccount(String accountName) {
		List<?> result = findBySQL("queryOneHouseAccount", new String[] {accountName }, true);
		return result;
	}
}
