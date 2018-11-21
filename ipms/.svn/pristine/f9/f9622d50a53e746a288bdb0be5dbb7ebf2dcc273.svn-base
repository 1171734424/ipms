package com.ideassoft.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExUtil {
	
	public static String NOTNULL = "\\S+";

	public static String NUMBER = "(-?\\d+)(\\.\\d+)?";
	
	public static String INTEGER = "[0-9]*";

	public static String EMAIL = "((([a-z]|\\d|[!#\\$%&'\\*\\+\\-\\/=\\?\\^_`{\\|}~]" +
			"|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])+(\\.([a-z]|\\d|[!#\\$%&'\\" +
			"*\\+\\-\\/=\\?\\^_`{\\|}~]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])+)*)" +
			"|((\\x22)((((\\x20|\\x09)*(\\x0d\\x0a))?(\\x20|\\x09)+)?(([\\x01-\\x08\\x0b\\x0c\\" +
			"x0e-\\x1f\\x7f]|\\x21|[\\x23-\\x5b]|[\\x5d-\\x7e]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\" +
			"uFDF0-\\uFFEF])|(\\\\([\\x01-\\x09\\x0b\\x0c\\x0d-\\x7f]|[\\u00A0-\\uD7FF\\uF900-\\" +
			"uFDCF\\uFDF0-\\uFFEF]))))*(((\\x20|\\x09)*(\\x0d\\x0a))?(\\x20|\\x09)+)?(\\x22)))@" +
			"((([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(([a-z]|\\d|[\\u00A0-\\" +
			"uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\" +
			"uFDCF\\uFDF0-\\uFFEF])*([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])))\\.)" +
			"+(([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(([a-z]|[\\u00A0-\\uD7FF\\" +
			"uF900-\\uFDCF\\uFDF0-\\uFFEF])([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\" +
			"uFDF0-\\uFFEF])*([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])))\\.?";
	
	public static String DATE = "(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])" +
			"|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]" +
			"|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)";
	
	public static String TIME = "(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])" +
			"|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]" +
			"|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)" +
			"( )([0-1][0-9]|[2][0-3])(:)([0-5][0-9])(:)([0-5][0-9])";
	
	public static String IP = "\\b(([01]?\\d?\\d|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d?\\d|2[0-4]\\d|25[0-5])\\b";
	
	public static String IDCARD = "(^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$)|(^[1-9]\\d{5}[1-9]" +
			"\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[x])$)";
	
	public static String MOBILE = "((13|15|17|18|19)+\\d{9})";

	public static String SPECCHAR = ".*[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？].*";

	public static boolean match(String regx, String value) {
		Pattern pattern = Pattern.compile(regx);
        Matcher mtr = pattern.matcher(value);
        return mtr.matches();
	}
	
	public static void main(String[] args) {
		System.out.println(RegExUtil.match(NOTNULL, "a"));
		System.out.println(RegExUtil.match(NUMBER, "1"));
		System.out.println(RegExUtil.match(EMAIL, "123@apache.org"));
		System.out.println(RegExUtil.match(MOBILE, "13111111111"));
		System.out.println(RegExUtil.match(TIME, "2005-02-28 23:00:59"));
	}
	
}
