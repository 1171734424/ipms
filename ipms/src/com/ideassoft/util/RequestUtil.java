package com.ideassoft.util;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;

import com.ideassoft.bean.Staff;
import com.ideassoft.core.page.ConfigLoader;

public class RequestUtil {
    private static Logger logger = Logger.getLogger(RequestUtil.class);
    
    public final static String LOGIN_USER_SESSION_KEY = "LOGIN_USER";

    /**
     * 获得当前登录用户
     *
     * @param request
     * @return
     */
    public static Staff getLoginUser(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        if (session.getAttribute(LOGIN_USER_SESSION_KEY) != null) {
            return (Staff) session.getAttribute(LOGIN_USER_SESSION_KEY);
        }
        return null;
    }

    /**
     * 判断是否登录
     *
     * @param request
     * @return
     */
    public static boolean hasLogin(HttpServletRequest request) {
        if (getLoginUser(request) == null) return false;
        return true;
    }

    public static String getAttributeStr(HttpServletRequest request, String name) {
        String parameter = (String) request.getAttribute(name);
        return parameter == null || "".equals(parameter.trim()) ? "" : parameter.trim();
    }

    @SuppressWarnings("unchecked")
	public static List<Object> getAttributeList(HttpServletRequest request, String name) {
        Object obj = request.getAttribute(name);
        return obj == null ? new ArrayList<Object>() : (List<Object>) obj;
    }

    @SuppressWarnings("unchecked")
	public static HashMap getAttributeHashMap(HttpServletRequest request, String name) {
        Object obj = request.getAttribute(name);
        return obj == null ? new HashMap() : (HashMap) obj;
    }

    /**
     * @param request
     * @param name
     * @param attributeClass
     * @return
     */
    public static Object getAttributeObject(HttpServletRequest request, String name, Class<?> attributeClass) {
        Object obj = request.getAttribute(name);
        if (obj == null)
            try {
                obj = attributeClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        return obj;
    }

    public static StringBuffer printCatalogSelectHTML(List<?> list, String propertyId, String propertyName, String selectValue) {
        StringBuffer html = new StringBuffer("");
        try {
            for (int i = 0; list != null && i < list.size(); i++) {
                Object o = list.get(i);
                String propertyIdValue = BeanUtils.getNestedProperty(o, propertyId);
                String propertyNameValue = BeanUtils.getNestedProperty(o, propertyName);
                if(propertyNameValue.trim().equals("UNDEFINED") || propertyNameValue.trim().equals("无")){
                	propertyNameValue = "----------";
                }
                String checkstr = propertyIdValue.equals(selectValue) ? "selected" : "";
                html.append("<option value='" + propertyIdValue + "'  ").append(checkstr).append(
                        " > ").append(propertyNameValue).append("</option>");
            }
        } catch (Exception e) {
            logger.error("Error Message: ", e);
        }
        return html;
    }
    
    public static StringBuffer printCountrySelectHTML(List<Object> list, String propertyId, String propertyName, String selectValue) {
        StringBuffer html = new StringBuffer("<option value=' '>-----</option>");
        try {
            for (int i = 0; list != null && i < list.size(); i++) {
                Object o = list.get(i);
                String propertyIdValue = BeanUtils.getNestedProperty(o, propertyId);
                String propertyNameValue = BeanUtils.getNestedProperty(o, propertyName);

                String checkstr = propertyIdValue.equals(selectValue) ? "selected" : "";
                html.append("<option value='" + propertyIdValue + "'  ").append(checkstr)
                	.append(" > ").append(propertyNameValue).append("</option>");
            }
        } catch (Exception e) {
            logger.error("Error Message: ", e);
        }
        
        return html;
    }

    public static String toStr(HttpServletRequest request) {
        String requestParams = "[";
        Enumeration<?> keys = request.getParameterNames();
        while (keys.hasMoreElements()) {
            String key = (String) keys.nextElement();
            String value = request.getParameter(key);

            requestParams += key + "=" + value + ",\r\n";
        }

        requestParams += "]";

        String requestStr = "Request: {\r\n" + "Request URI: " + request.getRequestURI() + "\r\n"
                + "Remote Address: " + request.getRemoteAddr() + "\r\n" + requestParams;

        return requestStr;
    }

    public static List<Object> getBeanList(HttpServletRequest request, Class<?> beanClass, String alias) {
        List<Object> postList = new ArrayList<Object>();
        HashMap<String, Object> map = new HashMap<String, Object>();

        String paramName = null;
        try {
            Enumeration<?> paramNames = request.getParameterNames();
            while (paramNames.hasMoreElements()) {
                paramName = paramNames.nextElement().toString();
                if (paramName.indexOf(alias + "[") != -1) {
                    String indexStr = paramName.substring(paramName.indexOf("[") + 1, paramName.indexOf("]"));
                    Object obj = null;
                    if (map.containsKey(indexStr)) {
                        obj = map.get(indexStr);
                    } else {
                        obj = beanClass.newInstance();
                        map.put(indexStr, obj);
                    }
                    String fieldName = paramName.substring(paramName.indexOf("]") + 2);
                    Field field = null;
                    try {
                        field = obj.getClass().getDeclaredField(fieldName);
                    } catch (Exception e) {
                        continue;
                    }
                    field.setAccessible(true);
                    String param = request.getParameter(paramName);
                    if(!param.equals("null")){
                    	Object value = ConvertUtil.convertValue(field.getType(), param);
                        if (value != null) {
                            field.set(obj, value);
                        }
                    }
                    
                }
            }
        } catch (Exception e) {
            logger.error("参数 " + paramName + " 错误: " + e.getMessage(), e);
            throw new IllegalArgumentException("参数 " + paramName + " 错误: " + e.getMessage());
        }
        
        Iterator<?> it = map.keySet().iterator();
        while(it.hasNext()){
        	Object o = map.get(it.next());
        	postList.add(postList.size(), o);
        }
        
        return postList;
    }

    /**
     * 获得配置在资源文件中的定制消息
     *
     * @param code    消息Code
     * @param propertyValue    消息参数，如果消息为{0},ddd{1} ,args={"11","22"};
     * @param request
     * @return
     */
    public static String getResourceMessage(String code, Object[] propertyValue, HttpServletRequest request) {
        MessageSource messageSource = (MessageSource) SpringUtil.getBean("messageSource");
        String message = messageSource.getMessage(code, propertyValue, request.getLocale());
        return message;
    }
	
	public static String getWebPath(HttpServletRequest request) {
		String webPath = request.getSession().getServletContext().getRealPath("");
		if (webPath.lastIndexOf(".") == webPath.length() - 1 || webPath.lastIndexOf("/") == webPath.length() - 1
				|| webPath.lastIndexOf("\\") == webPath.length() - 1) {
			webPath = webPath.substring(0, webPath.length() - 1);
		}
		return webPath;
	}
	
	public static String getWebPath() {
		ClassLoader loader = ConfigLoader.class.getClassLoader();
		
		if (loader == null) {
			loader = Thread.currentThread().getContextClassLoader();
		}

		URL souce = loader.getResource("");
		if (souce != null) {
			File file = new File(souce.getFile());
			return file.getParentFile().getParentFile().getPath();
		}
		
		return null;
	}
	
}
