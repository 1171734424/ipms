package com.ideassoft.core.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.ideassoft.core.page.Pagination;

public interface IGenericDAO {
	
	String getSequence() ;
	
	String getSequence(String seqName, String prefix) ;
	
	String getSequence(String seqName, String prefix, boolean isCloudSource) ;
	
	Object findById(Class<?> persistentClass, Serializable id);
	
	List<?> findAll(Class<?> persistentClass);
	
	List<?> findByHQL(String hql);
	
	List<?> findByHQL(String hql, Object[] ids);
	
	List<?> findByHQL(String hql, Map<String, Object> param);
	
	List<?> findByHQLWithPagination(final String hql, final Object[] ids, 
    		final Integer pageNum, final Integer rowNum);
	
	List<?> findByHQLWithPagination(final String hql, final Map<String, Object> param, 
    		final Integer pageNum, final Integer rowNum);
	
	Integer findRecordsCount(String hql, Object[] ids);
	
	Integer findRecordsCount(String hql, Map<String, Object> param);
	
	List<?> findByHQLWithMax(String hql, Object[] ids, int max);
	
	Object findOneByProperties(Class<?> persistentClass, String propertyName,
    		Object value, Object... args) ;
	
	List<?> findByProperties(Class<?> persistentClass, String propertyName, Object value, Object... args);
	
	List<?> findByPropertiesWithSort(Class<?> persistentClass, String propertyNameToSort, String sortBy,
	           String propertyName, Object value, Object... args);
	
	Serializable save(Object newInstance);
	
	void update(Object transientObject);
	
	void merge(Object transientObject);
	
	void mergeAndUpdate(Object transientObject);
	
	void delete(Object persistentObject);
	
	List<?> executeQueryHQL(String hql);
	
	List<?> executeQueryHQL(String hql, Map<String, Object> params);
	
	void executeUpdateHQL(String hql, String[] names, Object[] values);
	
	void executeUpdateSQL(String sql);
	
	Integer findSQLRecordsCount(String sql);
	
	Integer findSQLRecordsCount(String sql, Object[] params);
	
	Integer findSQLRecordsCount(String sql, Map<String, Object> params);
	
	String findQuerySQL(String sql, boolean... inDB) ;
	
	List<?> findBySQLWithPagination(String sql, Pagination pagination, boolean... inDB) ;
	
	List<?> findBySQLWithPagination(String sql, Object[] params, Pagination pagination, boolean... inDB) ;
	
	List<?> findBySQLWithPagination(String sql, Map<String, Object> params, Pagination pagination, boolean... inDB) ;
	
	List<?> findBySQL(String sql, Class<?> cls, boolean... inDB) ;
	
	List<?> findBySQL(String sql, boolean... inDB) ;
	
	List<?> findBySQL(String sql, Object[] params, boolean... inDB) ;
	
	List<?> findBySQL(String sql, Map<String, Object> params, boolean... inDB) ;
	
	List<?> callProc(String procName, String params);
	
}
