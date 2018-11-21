package com.ideassoft.basic.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ideassoft.core.dao.GenericDAOImpl;
@Service
public class CityBasicService extends GenericDAOImpl{
	public List<?> queryDistrictMaxSeq(String cityCode,String rank) throws Exception {
		List<?> result = findBySQL("queryDistrictMaxSeq", new String[] {cityCode,rank}, true);
		return result;
	}
}
