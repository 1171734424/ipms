package com.ideassoft.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import javax.persistence.EmbeddedId;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hsqldb.lib.StringUtil;
import org.json.JSONArray;
import org.json.JSONObject;

import com.ideassoft.core.annotation.interfaces.ColumnDefaultValue;
import com.ideassoft.core.dao.GenericDAOImpl;
import com.ideassoft.crm.service.InitialService;


public class ReflectUtil {
	private static Logger logger = Logger.getLogger(ReflectUtil.class);
	
    private final static String beanPackage = "com.ideassoft.bean.";
    
    private static GenericDAOImpl gdhimpl = (GenericDAOImpl) SpringUtil.getBean("genericDAOImpl");
    
    public static InitialService initialSv = (InitialService) SpringUtil.getBean("initialService"); 

    public static String getFullBeanName(String objName) throws Exception {
    	return beanPackage + objName;
    }
      
    public static Object getBeanByName(String objName) throws Exception {
    	return Class.forName(beanPackage + objName).newInstance();
    }
    
    public static String getTurelyFieldName(Object object, String fieldName) throws Exception {
    	Field[] fields = object.getClass().getDeclaredFields();
    	
    	for (Field field : fields) {
			if (field.getName().equalsIgnoreCase(fieldName)) {
				return field.getName();
			}
		}
    	
    	return null;
    }
    
    public static Annotation getDefaultValue(Object object, String fieldName) throws Exception {
    	Method method = getMethodByFieldName(object, fieldName, "get");
    	if (method != null) {
        	return method.getAnnotation(ColumnDefaultValue.class);
		}
    	return null;
    }
    
    public static Method getMethodByFieldName(Object object, String fieldName, String methodType) throws Exception {
    	fieldName = getTruelyFieldName(object, fieldName);
    	
    	Class<?> type = object.getClass().getDeclaredField(fieldName).getType();
		String prefix = methodType.equals("get") ? "boolean".equals(type.getSimpleName()) || "Boolean".equals(type.getSimpleName()) 
				? "is" : "get" : "set";

		Method[] methods = object.getClass().getDeclaredMethods();
		for (Method method : methods) {
			if (method.getName().equalsIgnoreCase(prefix + fieldName.substring(0, 1).toUpperCase() 
					+ fieldName.substring(1, fieldName.length()))) {
				return method;
			}
		}
		return null;
    }
    public static Object setBean(JSONObject jo, String modelName) throws Exception {
    	return setBean(jo, modelName, false);
    }
    
    public static Object setBean(JSONObject jo, String modelName, boolean isNew) throws Exception {
    	return setBean(jo, modelName, isNew, null);
    }
    
    public static Object setBean(JSONObject jo, String modelName, boolean isNew, String idName) throws Exception {
    	return setBean(jo, modelName, isNew, idName, null, null, null);
    }
    
    public static Object setBean(JSONObject jo, String modelName, String idName) throws Exception {
    	return setBean(jo, modelName, false, idName, null, null, null);
    }  
    
    public static Object setBean(JSONObject jo, String modelName, String idName, String sequence, 
    		String prefix, String sequenceSource) throws Exception {
    	return setBean(jo, modelName, false, idName, sequence, prefix, null, sequenceSource);
    }
    
    public static Object setBean(JSONObject jo, String modelName, boolean isNew, String idName, 
			String sequence, String prefix, String sequenceSource) throws Exception {
    	return setBean(jo, modelName, isNew, idName, sequence, prefix, null, sequenceSource);
    }
    
	public static Object setBean(JSONObject jo, String modelName, boolean isNew, String idName, 
			String sequence, String prefix, Map<String, String> params, String sequenceSource) throws Exception {
		Object o = Class.forName(beanPackage + modelName).newInstance();
		
		Class<?> classT = o.getClass();
		Field[] fields = classT.getDeclaredFields();
		
		String varName, data;
		Annotation anno;
		for (int k = 0; k < fields.length; k++) {
			varName = fields[k].getName();
			
			if(!jo.has(varName)) {
				if (jo.has(varName.toUpperCase())) {
					varName = varName.toUpperCase();
				} else if (jo.has(varName.toLowerCase())) {
					varName = varName.toLowerCase();
				} else {
					anno = getDefaultValue(o, varName);
					if (anno != null) {
						setValue(o, varName, null, (ColumnDefaultValue) anno);
					}
					continue;
				}
			}

			data = jo.get(varName).toString();
			if(data.equalsIgnoreCase("[]")){
				data = null;
			}
			
			if(idName != null && varName.equalsIgnoreCase(idName)/* && "".equals(data)*/) {
				continue;
			}
			
			setValue(o, varName, data);
		}

		if(idName != null) {
			o = setBeanId(jo, o, isNew, idName, sequence, prefix, params, sequenceSource);
		}
		
		return o;
	}
	
	public static List<Object> setBeans(String jsonString, String beanName) throws Exception {
		jsonString = transJsonString(jsonString);
		JSONArray ja = new JSONArray(jsonString);
		List<Object> list = new ArrayList<Object>();
		Method[] methods;
		JSONObject jo;
		Object object;
		String idName = null;
		
		methods = getBeanByName(beanName).getClass().getDeclaredMethods();
		if (methods != null && methods.length > 0) {
			Annotation anno = null;
			for(Method method : methods) {
				anno = method.getAnnotation(EmbeddedId.class);
				if (anno != null) {
					idName = method.getReturnType().getSimpleName();
					break;
				}
			}
		}
		
		for (int i = 0; i < ja.length(); i++) {
			jo = ja.getJSONObject(i);
			
			if(idName != null) {
				object = setBean(jo, beanName, idName);
			} else {
				object = setBean(jo, beanName);
			}
			
			list.add(object);
		}
		
		return list;
	}
	
	public static Object setBeanId (JSONObject jo, Object o) throws Exception {
		return setBeanId(jo, o, false, null);
	}
	
	public static Object setBeanId (JSONObject jo, Object o, boolean isNew, String sequenceSource) throws Exception {
		return setBeanId(jo, o, isNew, null, sequenceSource);
	}
	
	public static Object setBeanId (JSONObject jo, Object o, String idName, String sequenceSource) throws Exception {
		return setBeanId(jo, o, false, idName, sequenceSource);
	}
	
	public static Object setBeanId (JSONObject jo, Object o, boolean isNew, String idName, String sequenceSource) throws Exception {
		return setBeanId(jo, o, isNew, idName, null, null, sequenceSource);
	}
	
	public static Object setBeanId (JSONObject jo, Object o, String idName, String sequence, String prefix, String sequenceSource) throws Exception {
		return setBeanId(jo, o, false, idName, sequence, prefix, null, sequenceSource);
	}
	
	public static Object setBeanId (JSONObject jo, Object o, boolean isNew, String idName, String sequence, String prefix, String sequenceSource) throws Exception {
		return setBeanId(jo, o, isNew, idName, sequence, prefix, null, sequenceSource);
	}
	
	public static Object setBeanId (JSONObject jo, Object o, boolean isNew, String idName, 
			String sequence, String prefix, Map<String, String> params, String sequenceSource) throws Exception {
		boolean flag = true;
		Object idObject = null ;

		String[] subId = idName.split("[()]");
		idName = subId[0];
		
		try {
			idObject = Class.forName(beanPackage + idName).newInstance();
		} catch (Exception e) {
			flag = false;
		}
		
		if (flag) {
			String keyName = idName.substring(0, 1).toLowerCase() + idName.substring(1);
			if (jo.has(keyName)) {
				idObject = setBean(jo.getJSONObject(keyName), idName);
			} else {
				idObject = setBean(jo, idName);
			}
			
			if (subId.length > 1) {
				String[] subIds = subId[1].split("[:]");
				String[] idConfigs;
				for (int i = 0; i < subIds.length; i++) {
					idConfigs = subIds[i].split("[|]");
					if (idConfigs.length > 1) {
						if (idConfigs.length > 2) {
							prefix = idConfigs[2];
						}
						
						idObject = setBeanId(jo, idObject, isNew, idConfigs[0], idConfigs[1], 
								getCode(prefix, params), params, sequenceSource);
					}
				}
			}
		} else {
			String reqName = "";
			if (jo.has(idName.toUpperCase())) {
				reqName = idName.toUpperCase();
			}
			
			if ((jo.has(reqName) && ("".equals(jo.get(reqName)) || isNew)) || !jo.has(reqName)) {
				if (sequence != null) {
					prefix = getCode(prefix, params);
					if(initialSv.checkSystemExistCloud()){
						idObject = gdhimpl.getSequence(sequence, prefix);
					} else {
						if(sequenceSource != null && !sequenceSource.equals("null") && !sequenceSource.equals("")){
							idObject = gdhimpl.getCloudSequence(sequence, prefix);
						} else { 
							idObject = gdhimpl.getSequence(sequence, prefix);
						}
					}
					jo.put(reqName, idObject);
				} else 
					idObject = null; 
				
			} else if (jo.has(reqName) && !"".equals(jo.get(reqName))) {
				idObject = jo.get(reqName);
			}
		}
		
		if (idObject != null) setValue(o, idName, idObject);
		
		return o;
	}
	
	public static List<Object> setBeansFromJsonArray(HttpServletRequest request, String beanName) throws Exception {
		return setBeansFromJsonArray(request, beanName, false);
	}
	
	public static List<Object> setBeansFromJsonArray(HttpServletRequest request, String beanName,
			boolean isNew) throws Exception {
		return setBeansFromJsonArray(request, beanName, isNew, null, null, null);
	}
	
	public static List<Object> setBeansFromJsonArray(HttpServletRequest request, String beanName,
			String keyConfig, Map<String, String> params) throws Exception {
		return setBeansFromJsonArray(request, beanName, false, null, null, null);
	}
	
	public static List<Object> setBeansFromJsonArray(HttpServletRequest request, String beanName, boolean isNew, 
			String keyConfig, Map<String, String> params, String sequenceSource) throws Exception {
		Enumeration<?> parameters = request.getParameterNames();

		String element, value;
		StringBuilder jsonString = new StringBuilder("[{");
		
		while(parameters.hasMoreElements()) {
			element = parameters.nextElement().toString();
			value = request.getParameter(element);
			if (request.getParameter(element + "_CUSTOM_VALUE") != null) {
				value = request.getParameter(element + "_CUSTOM_VALUE");
			}
			jsonString.append(element).append(":").append(value != null && !"".equals(value) ? "\"" + value + "\"" : "\"\"").append(",");
		}
		
		jsonString.substring(0, jsonString.length() - 1);
		jsonString.append("}]");
		return setBeansFromJsonArray(jsonString.toString(), beanName, isNew, keyConfig, params, sequenceSource);
	}
	
	public static List<Object> setBeansFromJsonArray(String jsonString, String beanName, String sequenceSource) throws Exception {
		return setBeansFromJsonArray(jsonString, beanName, true, sequenceSource);
	}
	
	public static List<Object> setBeansFromJsonArray(String jsonString, String beanName,
			boolean isNew, String sequenceSource) throws Exception {
		return setBeansFromJsonArray(jsonString, beanName, isNew, null, null, sequenceSource);
	}
	
	public static List<Object> setBeansFromJsonArray(String jsonString, String beanName,
			String keyConfig, Map<String, String> params, String sequenceSource) throws Exception {
		return setBeansFromJsonArray(jsonString, beanName, false, null, null, sequenceSource);
	}
	
	public static List<Object> setBeansFromJsonArray(String jsonString, String beanName, boolean isNew,
			String keyConfig, Map<String, String> params, String sequenceSource) throws Exception {
		String modelName = null;
		String idName = null;
		
		String[] beanObjects = beanName.split(",");
		String[] keyObjects = null;
		if(keyConfig != null && !"".equals(keyConfig)) {
			logger.info("bean[" + beanName +"] keyconfig[" + keyConfig + "].");
			keyObjects = keyConfig.split(",");
			
			if (keyObjects.length != beanObjects.length) {
				logger.error("primary key config error! bean name " + beanName);
				return null;
			}
		}
		
		jsonString = transJsonString(jsonString);
		JSONArray ja = new JSONArray(jsonString);
		List<Object> list = new ArrayList<Object>();
		String sequence = null;
		String prefix = null;
		
		for (int i = 0; i < ja.length(); i++) {
			JSONObject jo = ja.getJSONObject(i);
			
			if(params != null && !params.isEmpty()) {
				Iterator<Entry<String, String>> it = params.entrySet().iterator();
				Entry<String, String> entry;
				while(it.hasNext()) {
					entry = it.next();
					if (!jo.has(entry.getKey()) || StringUtils.isEmpty(jo.getString(entry.getKey()))) {
						jo.put(entry.getKey(), params.get(entry.getKey()));
					}
				}
			}
			
			Object o = null;
			for (int j = 0; j < beanObjects.length; j++) {
				modelName = beanObjects[j];
				String[] objects = null;
				if (keyObjects != null) {
					objects = keyObjects[j].split("[()]");
					
					if (objects.length > 1) {
						idName = keyObjects[j];
					} else {
						objects = keyObjects[j].split("[|]");
						idName = objects[0];
						
						if (objects.length > 1) {
							sequence = objects[1];
							prefix = objects.length > 2 ? objects[2] : null;
							prefix = getCode(prefix, params);
						}
					}
				}
				o = setBean(jo, modelName, isNew, idName, sequence, prefix, params, sequenceSource);
				
				list.add(o);
			}
		}
		
		return list;
	}
	
	public static Object setBeanFromRequest(HttpServletRequest request, Class<?> c) throws Exception {
		Object obj = c.newInstance();
		
		String vName;
		Field[] fls = c.getDeclaredFields();
		for (int j = 0; j < fls.length; j++) {
			vName = fls[j].getName();
			setValue(obj, vName, request.getParameter(vName));
		}
		
		return obj;
	}
	
	public static Object getBean(List<Object> beans, String field, Object value) throws Exception {
		for (Object bean : beans) {
			Object val = getValue(bean, field);
			
			if (value.equals(val)) {
				return bean;
			}
		}
		
		return null;
	}
	
	public static Object getFieldInitValue(Object object, String fieldName) throws Exception {
		Field field = object.getClass().getField(fieldName);
		return field.get(fieldName);
	}
	
	public static Object getFieldInitValue(Class<?> cls, String fieldName) throws Exception { 
		Field field = cls.getField(fieldName);
		return field.get(fieldName);
	}
	
	public static Object getValue(Object object, String fieldName) throws Exception {
		Method method = getMethodByFieldName(object, fieldName, "get");

		Class<?>[] cls = null;
		Object obj = null;
		if (method != null) {
			obj = method.invoke(object, (Object[]) cls);
		}
		
		return obj != null ? obj : null;
	}
	
	public static String getTruelyFieldName(Object object, String fieldName) {
		String truelyName = null;
		Field[] fields = object.getClass().getDeclaredFields();
		for (Field field : fields) {
			if (fieldName.equalsIgnoreCase(field.getName())) {
				truelyName = field.getName();
				break;
			}
		}
		
		return truelyName;
	}
	
	public static void setValue(Object object, String fieldName, Object data, ColumnDefaultValue... columnDefaultValue) throws Exception {
		if (columnDefaultValue != null && columnDefaultValue.length > 0) {
			String defaultValue = columnDefaultValue[0].value();
			boolean insertable = columnDefaultValue[0].insertable();
			if (!insertable) {
				data = defaultValue;
			}
		}
		
		if ( data == null || "null".equalsIgnoreCase(data.toString()) || "undefined".equalsIgnoreCase(data.toString()) || data.equals("")){
			return;
		}
		
		Method method = getMethodByFieldName(object, fieldName, "set");
		Class<?> type = method.getParameterTypes()[0];
		
		if (method != null && type != null) {
			if (!"String".equals(type.getSimpleName()) && !"Date".equals(type.getSimpleName()) && "".equals(data)) data = "0";
			String value = data.toString();
			
			if ("Byte".equals(type.getSimpleName()) || "byte".equals(type.getSimpleName()) ) {
				method.invoke(object, Byte.parseByte(value.replace(".0", "")));
			} else if ("Short".equals(type.getSimpleName()) || "short".equals(type.getSimpleName())) {
				method.invoke(object, Short.parseShort(value.replace(".0", "")));
			} else if ("Integer".equals(type.getSimpleName()) || "int".equals(type.getSimpleName())) {
				method.invoke(object, Integer.parseInt(value.replace(".0", "")));
			} else if ("Float".equals(type.getSimpleName()) || "float".equals(type.getSimpleName())) {
				method.invoke(object, Float.parseFloat(value));
			} else if ("Double".equals(type.getSimpleName()) || "double".equals(type.getSimpleName())) {
				method.invoke(object, Double.parseDouble(value));
			} else if ("Long".equals(type.getSimpleName()) || "long".equals(type.getSimpleName())) {
				method.invoke(object, Long.parseLong(value));
			} else if ("Date".equals(type.getSimpleName())) {
				Date dateTime = null;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				
				if (value.startsWith("{")) {
					JSONObject jo = new JSONObject(value);
					if (value.indexOf("time") != -1) {
						Long time = Long.parseLong(jo.get("time").toString());
						dateTime = new Date(time);
					} else {
						value = jo.get("year").toString() + "/" + String.valueOf(Integer.parseInt(jo.get("month").toString())+1) + "/"
						+ jo.get("day").toString() + " " + jo.get("hours").toString() + ":"
						+ jo.get("minutes").toString() + ":" + jo.get("seconds").toString();
						dateTime = sdf.parse(value);
					}
					
				} else if (value.equals("sysdate")){
					dateTime = new Date();
				} else if (StringUtil.isEmpty(value)) {
					return;
				}else {
					if(!value.contains(":") && value != null && !value.equals(" ")){
						value = value.trim() + " 00:00:00";
					} else {
						return;
					}
					dateTime = sdf.parse(value);
				}
				
				Date date = sdf.parse(sdf.format(dateTime));
				method.invoke(object, date);
				/*if (!StringUtil.isEmpty(value)) {
					if (value.startsWith("{")) {
						JSONObject jo = new JSONObject(value);
						value = jo.get("year").toString() + "/" + String.valueOf(Integer.parseInt(jo.get("month").toString())+1) + "/"
							+ jo.get("day").toString() + " " + jo.get("hours").toString() + ":"
							+ jo.get("minutes").toString() + ":" + jo.get("seconds").toString();
					}
					
					Date date;
					if (value.equals("sysdate")) {
						date = new Date(System.currentTimeMillis());
					} else {
						date = new SimpleDateFormat("yyyy/MM/dd").parse(value);
					}
					method.invoke(object, date);
				}*/
			} else if ("BigDecimal".equals(type.getSimpleName())) {
				method.invoke(object, new BigDecimal(value));
			} else if ("String".equals(type.getSimpleName())) {
				method.invoke(object, data.toString());
			} else {
				method.invoke(object, data);
			}
		}
	}
	
	public static Map<String, Object> getVariableMap(Class<?> cls) throws Exception {
		Map<String, Object> varMap = new HashMap<String, Object>();
		Field[] fields = cls.getDeclaredFields();
    	for (int i = 0; i < fields.length; i++) {
    		fields[i].setAccessible(true);
    		varMap.put(fields[i].getName(), fields[i].get(fields[i].getName()));
		}
    	return varMap;
	}
	
	public static List<?> transObjectToMap(List<?> result) throws Exception {
		List<Object> rtn = new Vector<Object>();
		if (result != null && !result.isEmpty()) {
			Map<String, Object> rs;
			Field[] fields;
			Object value;
			for (Object obj : result) {
				rs = new Hashtable<String, Object>();
				
				fields = obj.getClass().getDeclaredFields();
				for (Field field : fields) {
					try {
						value = getValue(obj, field.getName());
						rs.put(field.getName().toUpperCase(), value == null ? "" : value);
					} catch (NoSuchMethodException e) {
						continue;
					}
				}
				
				rtn.add(rs);
			}
		}
		return rtn;
	}

    public static String getCode(String prefix, Map<String, String> params) {
    	if ("$DATE".equals(prefix)) {
        	return DateUtil.currentDate("yyyyMMdd");
		} else if ("$BRANCHDATE".equals(prefix)) {
			return DateUtil.currentDate("yyyyMMdd") + params.get("BRANCHID");
		} else if ("$BRANCHID".equals(prefix)) {
			return params.get("BRANCHID");
		} else {
			return prefix;
		}
    }
	
	private static String transJsonString(String jsonString) {
		return jsonString.replace("{\"", "{").replace("\":", ":").replace(",\"", ",");
	}
	
	public static void main(String[] args) {
		
	}
}
