package com.ideassoft.crm.controller;

import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ideassoft.bean.Department;
import com.ideassoft.core.ajax.AjaxResult;
import com.ideassoft.core.constants.CommonConstants;
import com.ideassoft.crm.service.CommonService;
import com.ideassoft.util.IPUtil;
import com.ideassoft.util.JSONUtil;
import com.ideassoft.util.ReflectUtil;

@Transactional
@Controller
public class CommonController {

	private static Logger logger = Logger.getLogger(CommonController.class);

	public static Map<String, Long> heartbeats = new ConcurrentHashMap<String, Long>();
	
	@Autowired
	CommonService commonService;

	
	/**
	 * 通用参数查询
	 * 
	 * @param queryInfo
	 * @param arg
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/commonSearch.do")
	public void commonSearch(String queryInfo, String arg, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Method method = commonService.getClass().getDeclaredMethod(arg + "Search", String.class);
		Object result = method.invoke(commonService, queryInfo);
		JSONUtil.responseJSON(response, JSONArray.fromObject(result));
	}

	@RequestMapping("/messageHints.do")
	public void messageHints(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONUtil.responseJSON(response, new AjaxResult(2, null));
	}

	public String calcDepartId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String departId = request.getParameter("DEPARTID");
		if (StringUtils.isEmpty(departId)) {
			String parentDepart = request.getParameter("PARENTDEPART_CUSTOM_VALUE");

			if (!StringUtils.isEmpty(parentDepart)) {
				Integer subCount = commonService.countSubDepartments(parentDepart) + 1;
				departId = parentDepart + (subCount > 9 ? subCount : "0" + subCount);
			} else {
				departId = commonService.getSequence("SEQ_NEW_DEPARTMENT");
			}
		}

		return "{ \"DEPARTID\": \"" + departId + "\" }";
	}

	@RequestMapping("/departDelete.do")
	public void departDelete(String departIds, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String[] ids = departIds.split(",");
		String message = "";
		Department department;

		for (String id : ids) {
			department = (Department) commonService.findById(Department.class, id);
			department.setStatus(CommonConstants.STATUS.WASTED);
			commonService.update(department);
		}

		JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.SUCESS, message));
	}

	@RequestMapping("/reciveData.do")
	public void reciveData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("recive data start...............");
		String comment = request.getParameter("comment");
		String result = Integer.toString(CommonConstants.PortalResultCode.DONE);
		try {
			if (comment != null && comment.length() > 0) {
				comment = URLDecoder.decode(comment, "UTF-8");
				org.json.JSONObject jo = new org.json.JSONObject(comment);
				String[] names = org.json.JSONObject.getNames(jo);
				org.json.JSONArray ja;
				List<?> beans;
				for (String name : names) {
					logger.debug("recive data [" + name + "]");
					ja = new org.json.JSONArray(jo.get(name).toString());
					if (ja.length() > 0) {
						beans = ReflectUtil.setBeans(ja.toString(), name);
//						beans = ReflectUtil.setBeansFromJsonArray(jo.get(name).toString(), name, null, null);
						commonService.saveOrUpdateAll(beans);
					}
				}
			}
		} catch (Exception e) {
			result = Integer.toString(CommonConstants.PortalResultCode.FAILED);
			e.printStackTrace();
		}
		response.getWriter().write(result);
		logger.debug("recive data finish...............");
	}
	
	//服务端
		@RequestMapping("/reciveNet.do")
		public void reciveNet(HttpServletRequest request, HttpServletResponse response) throws Exception {
			String IP = IPUtil.getIpAddr(request);
			heartbeats.put(IP, System.currentTimeMillis());
		}
	

}
