package com.ideassoft.core.factory;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationContext;

import com.ideassoft.core.server.Server;

public abstract class ServiceFactory {
	private static final ApplicationContext ctx;
	private static final Map<String, String> serviceTable = new HashMap<String, String>();
	public static String str = "";
	static {
		ctx = Server.Context;
	}

	public static Object getService(String serviceName) {
		return ctx.getBean(serviceName);
	}

	public static Object getServiceByType(String typeId) {
		String beanName = (String) serviceTable.get(typeId);
		if (beanName != null)
			return ctx.getBean(beanName);
		else
			return null;
	}

}
