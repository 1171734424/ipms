package com.ideassoft.crm.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ideassoft.bean.PriceRules;
import com.ideassoft.core.ajax.AjaxResult;
import com.ideassoft.crm.service.PriceRuleService;
import com.ideassoft.util.DateUtil;
import com.ideassoft.util.JSONUtil;

/**
 * @author zhengsj
 * @date 2018年5月15日
 * @version 1.0  
 */
@Controller
@Transactional
public class PriceRuleController {

	@Autowired
	private PriceRuleService priceRuleService;
	

	
}
