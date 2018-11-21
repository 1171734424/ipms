package com.ideassoft.core.jdbc.dialect;

import org.hibernate.dialect.Oracle10gDialect;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StringType;

public class HibernateMySqlDialect extends Oracle10gDialect {
	
	public HibernateMySqlDialect() {
		super();
	    registerFunction("decode", new StandardSQLFunction("decode", StringType.INSTANCE));
	    registerFunction("ifnull", new StandardSQLFunction("ifnull", StringType.INSTANCE));
	}
	
}
