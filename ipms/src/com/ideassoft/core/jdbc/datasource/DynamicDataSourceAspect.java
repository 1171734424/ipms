package com.ideassoft.core.jdbc.datasource;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Component;
  
@Component 
public class DynamicDataSourceAspect {
	private static Logger logger = Logger.getLogger(DynamicDataSourceAspect.class);
	
//    @Pointcut("target(com.ideassoft.core.dao.GenericDAOImpl)")  
//    public void serviceExecution(){}  
//      
//    @Before("serviceExecution()")  
    public void setDynamicDataSource(JoinPoint jpoint) {
    	String serviceName = jpoint.getTarget().getClass().getSimpleName();
    	
    	if ("*Service".equalsIgnoreCase(serviceName)) {
    		logger.debug("change datasource to " + DataSourceContextHolder.DS_SQLSERVER);
    		DataSourceContextHolder.setCustomerType(DataSourceContextHolder.DS_SQLSERVER);
		} else {
			if (!DataSourceContextHolder.DS_ORACLE.equals(DataSourceContextHolder.getCustomerType())) {
	    		logger.debug("change datasource to " + DataSourceContextHolder.DS_ORACLE);
				DataSourceContextHolder.setCustomerType(DataSourceContextHolder.DS_ORACLE);
			}
		}
    }
}  
