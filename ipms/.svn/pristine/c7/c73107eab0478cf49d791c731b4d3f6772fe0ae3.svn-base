package com.ideassoft.crm.controller;




import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ideassoft.bean.Invester;
import com.ideassoft.core.ajax.AjaxResult;
import com.ideassoft.crm.service.InvesterSer;
import com.ideassoft.util.JSONUtil;


@Transactional
@Controller
public class InvesterCRMCtrl {

	@Autowired
	InvesterSer investerService;

	@RequestMapping("/editinvester.do")
	public ModelAndView editinvester(HttpServletRequest request, HttpServletResponse response
			,String INVESTERAPPLYID,String remark) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("remark", remark);
		mv.addObject("INVESTERAPPLYID", INVESTERAPPLYID);
		mv.setViewName("page/crm/invester/editinvester");
		return mv;
	}
	
	@RequestMapping("/updateinvester.do")
	public void updateinvester(HttpServletRequest request, HttpServletResponse response
			,String INVESTERAPPLYID) {
		Invester invester = (Invester)investerService.findOneByProperties(Invester.class, "investerApplyId", INVESTERAPPLYID);
		invester.setStatus("2");
		investerService.update(invester);
		JSONUtil.responseJSON(response, new AjaxResult(1, null));

	}
	
}
