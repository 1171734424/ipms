package com.ideassoft.crm.controller;


import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hsqldb.lib.StringUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ideassoft.bean.ShiftTime;
import com.ideassoft.bean.SysParam;
import com.ideassoft.core.ajax.AjaxResult;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.pms.service.ShiftService;
import com.ideassoft.util.JSONUtil;
import com.ideassoft.util.RequestUtil;

@Transactional
@Controller
public class ShiftTimeController {

	@Autowired
	private ShiftService shiftService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/queryOperateLogStatus.do")
	public void queryOperateLogStatus(HttpServletRequest request, HttpServletResponse response,String shiftid)throws Exception{
	List<SysParam> opertypeList = 	shiftService.findByProperties(SysParam.class, "paramType", "OPER_TYPE");
	if(opertypeList.size()>0){
		
		JSONUtil.responseJSON(response, JSONUtil.fromObject(opertypeList));
	}
	}

}
