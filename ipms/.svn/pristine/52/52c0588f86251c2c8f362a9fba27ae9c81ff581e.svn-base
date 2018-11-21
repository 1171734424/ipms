package com.ideassoft.wechatrefund;

import java.util.ResourceBundle;

public class PropertiesUtil {

	protected static ResourceBundle res;
	protected static final String SERVER_WEB = "server.web";
	protected static final String SERVER_SSL_WEB = "server.ssl.web";
	protected static final String SERVER_STATIC = "server.static";
	protected static final String SERVER_DOWNLOAD = "server.download";
	protected static final String GLOBAL_PROPERTIES_FILE = "placeholder";
	

	public static String get(String key) {
		return res.getString(key);
	}

	static {
		if (res == null)
			res = ResourceBundle.getBundle("placeholder");
	}

}
