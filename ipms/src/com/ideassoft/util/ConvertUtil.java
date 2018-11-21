package com.ideassoft.util;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

public class ConvertUtil {

    private static Logger logger = Logger.getLogger(ConvertUtil.class);

    public static final int NOT_SUPPORTED = 0; // Unkown

    public static final int INTEGER = 1; // Integer

    public static final int LONG = 2; // Long

    public static final int STRING = 3; // String

    public static final int DECIMAL = 4; // BigDecimal

    public static final int DATE = 5; // Date

    public static final int BASIC_INT = 6; // int

    public static final int BASIC_LONG = 7; // long

    public static final int BASIC_FLOAT = 8; // float

    public static final int BASIC_DOUBLE = 9; // double
    
    public static final int BASIC_SHORT = 10; // short

    public static final SimpleDateFormat datePaser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 获取参数类型对应的常量值
     *
     * @param type Class - 参数类型
     * @return int - 对应的常量值
     */
    private static int getIntType(Class<?> type) {
        if ("java.lang.Integer".equals(type.getName())) {
            return INTEGER;
        }
        if ("java.lang.Long".equals(type.getName())) {
            return LONG;
        }
        if ("java.lang.String".equals(type.getName())) {
            return STRING;
        }
        if ("java.math.BigDecimal".equals(type.getName())) {
            return DECIMAL;
        }
        if ("int".equals(type.getName())) {
            return BASIC_INT;
        }
        if ("long".equals(type.getName())) {
            return BASIC_LONG;
        }
        if ("float".equals(type.getName())) {
            return BASIC_FLOAT;
        }
        if ("double".equals(type.getName())) {
            return BASIC_DOUBLE;
        }
        if ("short".equals(type.getName())) {
            return BASIC_SHORT;
        }
        if ("java.util.Date".equals(type.getName()) || "java.sql.Date".equals(type.getName())) {
            return DATE;
        }

        return NOT_SUPPORTED;
    }

    /**
     * 获取相应类型的JavaBean属性值
     *
     * @param type  Class 类型
     * @param value String 属性字串值
     * @return Object 属性值
     */
    public static Object convertValue(Class<?> type, String value) {
        if (getIntType(type) == STRING) {
            return value;
        }
        try {
            if ("".equals(value) || " ".equals(value)) {
                return null;
            }

            switch (getIntType(type)) {
                case INTEGER: {
                    return new Integer(value);
                }
                case LONG: {
                    return new Long(value);
                }
                case DECIMAL: {
                    return new BigDecimal(value);
                }
                case BASIC_INT: {
                    return new Integer(value);
                }
                case BASIC_LONG: {
                    return new Long(value);
                }
                case BASIC_FLOAT: {
                    return new Float(value);
                }
                case BASIC_DOUBLE: {
                    return new Double(value);
                }
                case BASIC_SHORT: {
                    return new Short(value);
                }
                case DATE: {
                    if (value.length() == 10) {
                        value += " 00:00:00";
                    }

                    return datePaser.parse(value);

                }
                default: {
                    return null;
                }
            }
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
            return null;
        }

    }

    public static int getIntegerValue(Object value) {
        if (value == null) return 0;
        if (value instanceof Integer) {
            return ((Integer)value).intValue();
        }
        if (value instanceof BigDecimal) {
            return ((BigDecimal)value).intValue();
        }
        return 0;
    }

    public static void main(String[] args) {
		System.out.println(Math.round(123 * 100/ 1222));
	}
}
