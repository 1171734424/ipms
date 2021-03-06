package com.ideassoft.pmsinhouse.controller;

import java.net.UnknownHostException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ideassoft.bean.Member;
import com.ideassoft.bean.MemberAccount;
import com.ideassoft.core.annotation.interfaces.RightMethodControl;
import com.ideassoft.core.annotation.interfaces.RightModelControl;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.constants.RightConstants;
import com.ideassoft.core.page.Pagination;
import com.ideassoft.pmsinhouse.service.MemberInHousePmsService;
import com.ideassoft.util.JSONUtil;
import com.ideassoft.util.ReflectUtil;
import com.ideassoft.util.RequestUtil;

/**
 * 
 * 民宿会员查询模块
 *
 */

@Transactional
@Controller
@RightModelControl( rightModel = RightConstants.RightModel.HOUSE_CONTROL )
public class MemberInHousePmsController {
	
	
	@Autowired
	private MemberInHousePmsService memberInHousePmsService;
	
	@RequestMapping("/memberManage.do")
	public ModelAndView queryMemberManagePage(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		ModelAndView mv = new ModelAndView("/page/ipmshouse/leftmenu/memberinhouse/memberAccountInfo");
		Pagination pagination = (Pagination) ReflectUtil.setBeanFromRequest(request, Pagination.class);
		
		if (pagination != null && pagination.getPageNum() == null) {
			pagination.setPageNum(1);
			pagination.setRows(12);
		}
		String memberId = request.getParameter("memberId");
		String mobile = request.getParameter("mobile");
		String memberName = request.getParameter("memberName");
		String memberRank = request.getParameter("memberRank");
		String companyName = request.getParameter("companyName");
		if(!(("".equals(memberId) || null == memberId) 
				&&("".equals(mobile) || null == mobile) 
				&&("".equals(memberName) || null == memberName)
				&&("".equals(companyName) || null == companyName))
			){
			List <?> memberList = memberInHousePmsService.findBySQLWithPagination("queryMemberAcount", new String[]{memberId,mobile,memberName,memberRank,companyName}, pagination, true);
			mv.addObject("memberList", memberList);
		}else{
			List <?> memberList = memberInHousePmsService.findBySQLWithPagination("queryMemberAcount", new String[]{memberId,mobile,memberName,memberRank,companyName}, pagination, true);
			mv.addObject("memberList", memberList);
		}
		
		mv.addObject("companyName", companyName);
		mv.addObject("pagination", pagination);
		mv.addObject("memberId", memberId);
		mv.addObject("mobile", mobile);
		mv.addObject("memberName", memberName);
		mv.addObject("memberRank", memberRank);
		return mv;
	}
	
	@RequestMapping("/toMemberPage.do")
	@RightMethodControl( rightType = RightConstants.RightType.MEM_SEARCH )
	public ModelAndView toMemberManagePage(HttpServletRequest request,
			HttpServletResponse response){
		ModelAndView mv = new ModelAndView("/page/ipmshouse/leftmenu/memberinhouse/memberManage");
		return mv;
	}
	@RequestMapping("/editMemberAccount.do")
	@RightMethodControl( rightType = RightConstants.RightType.MEM_MANAGE )
	public void toMemberEditPage(HttpServletRequest request,
			HttpServletResponse response, String id){
		List<?> memeberAccount = memberInHousePmsService.findBySQL("queryMemberAccById", new String[]{id}, true);
		JSONUtil.responseJSON(response, memeberAccount);
	}
	@RequestMapping("/saveMemberAccount.do")
	public void saveMemberAccount(HttpServletRequest request,
			HttpServletResponse response) throws UnknownHostException {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String memberId = request.getParameter("memberId");
		double memberBalance = Double.parseDouble(request.getParameter("memberBalance"));
		long curryScore = Long.parseLong(request.getParameter("curryScore"));
		if (!"".equals(request.getParameter("companyDiscount"))) {
			short companyDiscount = Short.parseShort(request.getParameter("companyDiscount"));
			Member member = (Member) memberInHousePmsService.findOneByProperties(Member.class, "memberId", memberId);
			member.setDiscount(companyDiscount);
			memberInHousePmsService.update(member);
		}
		
		try{
			MemberAccount memberAccount = (MemberAccount) memberInHousePmsService.findOneByProperties(MemberAccount.class, "memberId", memberId);
			memberAccount.setCurrBalance(memberBalance);
			memberAccount.setCurrIntegration(curryScore);
			memberInHousePmsService.update(memberAccount);
			memberInHousePmsService.houseMemberAccountchange(loginuser, "0",memberBalance,curryScore,request);
			String result = "{\"code\":\"1\"}";
			JSONUtil.responseJSON(response, result);
		}catch(Exception ex){
			memberInHousePmsService.houseMemberAccountchange(loginuser, "1",memberBalance,curryScore,request);
			String result = "{\"code\":\"0\"}";
			JSONUtil.responseJSON(response, result);
		}
	}
}
