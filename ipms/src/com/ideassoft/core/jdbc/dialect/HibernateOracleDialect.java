package com.ideassoft.core.jdbc.dialect;

import org.hibernate.dialect.Oracle10gDialect;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StringType;

public class HibernateOracleDialect extends Oracle10gDialect {
	
	public HibernateOracleDialect() {
		super();
	    registerFunction("decode", new StandardSQLFunction("decode", StringType.INSTANCE));
	    registerFunction("nvl", new StandardSQLFunction("nvl", StringType.INSTANCE));
	    registerFunction("to_char", new StandardSQLFunction("to_char", StringType.INSTANCE));
	    registerFunction("to_date", new StandardSQLFunction("to_date", StringType.INSTANCE));
	    registerFunction("to_number", new StandardSQLFunction("to_number", StringType.INSTANCE));
	}
	
}
