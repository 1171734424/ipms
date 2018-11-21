package com.ideassoft.core.jdbc.datasource;

public class DataSourceContextHolder {

	public static final String DS_DEFAULT = "dsOracle";

	public static final String DS_ORACLE = "dsOracle";
	
	public static final String DS_SQLSERVER = "dsSqlServer";

	public static final String DS_MYSQL = "dsMySql";
	
	public static final String DS_ACCESS = "dsAccess";

	private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

	public static void setCustomerType(String customerType) {
		contextHolder.set(customerType);
	}

	public static String getCustomerType() {
		String dbType = contextHolder.get();
		return dbType == null ? DataSourceContextHolder.DS_DEFAULT : dbType;  
	}

	public static void clearCustomerType() {
		contextHolder.remove();
	}
}
