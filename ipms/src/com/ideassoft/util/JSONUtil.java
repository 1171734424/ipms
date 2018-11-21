package com.ideassoft.util;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsDateJsonBeanProcessor;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.log4j.Logger;

import com.ideassoft.core.ajax.DateJsonValueProcessor;
import com.ideassoft.core.page.Pagination;


/**
 * @author ZenShin
 * @time 2014-04-16
 */
public class JSONUtil {
	private static Logger logger = Logger.getLogger(JSONUtil.class);
	
	private static JsonConfig jcf;
	
	static {
		jcf = new JsonConfig();
		jcf.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		jcf.registerJsonBeanProcessor(java.util.Date.class,new JsDateJsonBeanProcessor());
	}
    
	@SuppressWarnings("unchecked")
	public static String buildReportJSON(Object result) {
		Integer count = result != null ? result instanceof Map ? ((Map) result).size() 
				: result instanceof List ? ((List) result).size() : 1 : 0;
		return buildReportJSONByPagination(result, new Pagination(1, count, 1));
	}
	
	public static String buildReportJSONByPagination(Object result, Pagination pagination) {
		StringBuilder json = new StringBuilder();
		json.append("{\"records\":").append(pagination.getRecords());
		json.append(", \"total\":").append(pagination.getTotal());
		json.append(", \"rows\":").append(buildJSONfromObject(result));
		if (pagination != null) {
			json.append(", \"page\":" + pagination.getPageNum());
			json.append(", \"pagination\":" + buildJSONfromObject(result));
		}
		json.append("}");
		return json.toString();
	}
	
	@SuppressWarnings("unchecked")
	public static String buildJSONfromObject(Object obj) {
		if (obj == null) {
			return null;
		}
		
		JSONArray ja = new JSONArray();
		String json = null;
		
		if (obj instanceof List) {
			for (Object o : (List<?>) obj) {
				ja.add(JSONObject.fromObject(o, jcf));
			}
			json = ja.toString();
		} else {
			ja.add(JSONObject.fromObject(obj, jcf));
			json = ja.toString();
		}
		
		return json;
	}
	
	public static void responseJSON(HttpServletResponse response, Object data) {
		response.setContentType("text/html;charset=UTF-8");
		response.setContentType("application/json");
		
		try {
			JsonConfig jsonConfig = new JsonConfig();

			jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
			JSON json = JSONSerializer.toJSON(data, jsonConfig);
			response.getWriter().write(json.toString());
		} catch (IOException e) {
			logger.fatal(e.getMessage(), e);
		}
	}

	public static Object fromJSON(String jsonStr) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd hh:mm:ss"));

		JSON json = JSONSerializer.toJSON(jsonStr, jsonConfig);
		Object obj = JSONSerializer.toJava(json, jsonConfig);

		return obj;
	}
	
	public static void buildParamFromJSON(Map<String, String> paramMap, String jsonStr) throws Exception {
		org.json.JSONObject jo = new org.json.JSONObject(jsonStr);
		Iterator<?> it = jo.keys();
		String key;
		while(it.hasNext()) {
			key = it.next().toString();
			paramMap.put(key, jo.getString(key));
		}
	}
	
	public static JSONArray fromObject(List<?> list) {
		return JSONArray.fromObject(list, jcf);
	}
	
	public static JSONObject fromObject(Object object) {
		return JSONObject.fromObject(object, jcf);
	}
}
