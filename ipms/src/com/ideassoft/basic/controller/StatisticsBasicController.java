package com.ideassoft.basic.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ideassoft.basic.service.StatisticsBasicService;
import com.ideassoft.bean.Branch;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.page.Pagination;
import com.ideassoft.util.JSONUtil;
import com.ideassoft.util.RequestUtil;

@Controller
public class StatisticsBasicController {
	
	@Autowired
	private StatisticsBasicService statisticsService; 
	

	// 跳转房间汇总房单明细
	@RequestMapping("/checkDetailShow.do")
	public ModelAndView checkDetailShow(HttpServletRequest request, HttpServletResponse response, String roomtype,
			String startdate, String enddate) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/page/basic/statistic/checkDetailShow");
		request.setAttribute("roomtype", roomtype);
		request.setAttribute("startdate", startdate);
		request.setAttribute("enddate", enddate);
		return mv;

	}
	
	
	// 房单详情显示页面
	@RequestMapping("/roomsummarycheck.do")
	public void roomsummarycheck(HttpServletRequest request, HttpServletResponse response, String roomtype,
			String startdate, String enddate) throws Exception {
		Integer pageNum = Integer.parseInt(request.getParameter("page"));
		Integer rows = Integer.parseInt(request.getParameter("rows"));
		Pagination pagination = new Pagination(pageNum, rows);
		List<?> result = statisticsService.getcheck(roomtype, startdate, enddate, pagination);
		JSONUtil.responseJSON(response, JSONUtil.buildReportJSONByPagination(result, pagination));
	}
	
	
	// 跳转房间明细订单详情页面
	@RequestMapping("/orderRoomDetailShow.do")
	public ModelAndView orderRoomDetailShow(HttpServletRequest request, HttpServletResponse response, String roomtype,
			String startdate, String enddate) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/page/basic/statistic/orderRoomDetailShow");
		request.setAttribute("roomtype", roomtype);
		request.setAttribute("startdate", startdate);
		request.setAttribute("enddate", enddate);
		return mv;
	}
	
	
	// 订单详情显示
	@RequestMapping("/roomorder.do")
	public void storelog(HttpServletRequest request, HttpServletResponse response, String roomtype, String startdate,
			String enddate) throws Exception {
		Integer pageNum = Integer.parseInt(request.getParameter("page"));
		Integer rows = Integer.parseInt(request.getParameter("rows"));
		Pagination pagination = new Pagination(pageNum, rows);
		List<?> result = statisticsService.getroomorders(roomtype, startdate, enddate, pagination);
		JSONUtil.responseJSON(response, JSONUtil.buildReportJSONByPagination(result, pagination));

	}
	
	
	// 客户来源统计
	@RequestMapping("/showDetailInformation.do")
	public ModelAndView showMtnInformation(HttpServletRequest request, HttpServletResponse response, String time)
			throws Exception {
		// String t = request.getParameter("time");
		ModelAndView mv = new ModelAndView();
		// mv.setViewName("/page/static/showMtnInfo");
		mv.setViewName("/page/basic/statistic/querySourceInfo");
		// List<?> result = sysService.showMtnInformation(time);
		// request.setAttribute("Info", result);
		request.setAttribute("time", time);

		return mv;
	}
	
	// 客户来源信息详情
	@RequestMapping("/sourceinfo.do")
	public void querySourceInfo(HttpServletRequest request, HttpServletResponse response, String orderDate)
			throws Exception {
		String branchrank = "";
		
		Integer pageNum = Integer.parseInt(request.getParameter("page"));
		Integer rows = Integer.parseInt(request.getParameter("rows"));
		Pagination pagination = new Pagination(pageNum, rows);
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchid = loginuser.getStaff().getBranchId();
		Branch branch = (Branch) statisticsService.findById(Branch.class, branchid);
		if(branch != null){
			branchrank = branch.getRank();
		}
		if(branchrank.equals("1")){
			 branchid	= loginuser.getStaff().getBranchId();
		}else{
			branchid = "";
		}
		List<?> result = statisticsService.showSourceInformation(orderDate, branchid, pagination);
		JSONUtil.responseJSON(response, JSONUtil.buildReportJSONByPagination(result, pagination));

	}
	
	// 订单消结一栏表
	@RequestMapping("/qIntoAccountDetil.do")
	public ModelAndView qIntoAccountDetil(HttpServletRequest request, HttpServletResponse response, String checkId)
			throws Exception {
		// String t = request.getParameter("time");
		ModelAndView mv = new ModelAndView();
		// mv.setViewName("/page/static/showMtnInfo");
		mv.setViewName("/page/basic/statistic/queryqIntoAccountInfo");
		// List<?> result = sysService.showMtnInformation(time);
		// request.setAttribute("Info", result);
		request.setAttribute("checkId", checkId);
		return mv;
	}
	
	// 订单消结信息详情
	@RequestMapping("/queryqIntoAccountInfo.do")
	public void queryqIntoAccountInfo(HttpServletRequest request, HttpServletResponse response, String checkId)
			throws Exception {
		Integer pageNum = Integer.parseInt(request.getParameter("page"));
		Integer rows = Integer.parseInt(request.getParameter("rows"));
		Pagination pagination = new Pagination(pageNum, rows);
		List<?> result = statisticsService.findBySQLWithPagination("queryIntoAccountInfo", new String[] {checkId }, pagination, true);
		JSONUtil.responseJSON(response, JSONUtil.buildReportJSONByPagination(result, pagination));
	}
	
	// 订单消结一栏表
	@RequestMapping("/queryHosueAbnormal.do")
	public ModelAndView queryHosueAbnormal(HttpServletRequest request, HttpServletResponse response, String checkId)
			throws Exception {
		// String t = request.getParameter("time");
		ModelAndView mv = new ModelAndView();
		// mv.setViewName("/page/static/showMtnInfo");
		mv.setViewName("/page/basic/statistic/queryHouseAbnormal");
		// List<?> result = sysService.showMtnInformation(time);
		// request.setAttribute("Info", result);
		request.setAttribute("checkId", checkId);
		return mv;
	}
	
	// 订单消结信息详情
	@RequestMapping("/queryHosueAbnormalInfo.do")
	public void queryHosueAbnormalInfo(HttpServletRequest request, HttpServletResponse response, String checkId)
			throws Exception {
		Integer pageNum = Integer.parseInt(request.getParameter("page"));
		Integer rows = Integer.parseInt(request.getParameter("rows"));
		Pagination pagination = new Pagination(pageNum, rows);
		List<?> result = statisticsService.findBySQLWithPagination("qHosueAbnormalInfo", new String[] {checkId }, pagination, true);
		JSONUtil.responseJSON(response, JSONUtil.buildReportJSONByPagination(result, pagination));
	}
	
	// 会员层次统计
	@RequestMapping("/showMemberDetailInfo.do")
	public ModelAndView showMemberDetailInfo(HttpServletRequest request, HttpServletResponse response, String time)
			throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/page/basic/statistic/queryMemberInfo");
		request.setAttribute("time", time);
		return mv;
	}
	
	
	// 会员层次统计详情
	@RequestMapping("/memberinfo2.do")
	public void queryMemberInfo(HttpServletRequest request, HttpServletResponse response, String registerDate)
			throws Exception {
		Integer pageNum = Integer.parseInt(request.getParameter("page"));
		Integer rows = Integer.parseInt(request.getParameter("rows"));
		Pagination pagination = new Pagination(pageNum, rows);
		List<?> result = statisticsService.showMemberInformation(registerDate, pagination);
		JSONUtil.responseJSON(response, JSONUtil.buildReportJSONByPagination(result, pagination));

	}
	
	
	// 会员消费详情
	@RequestMapping("/showConsumeDetailInfo.do")
	public ModelAndView showConsumeInfo(HttpServletRequest request, HttpServletResponse response, String memberID)
			throws Exception {
		ModelAndView mv = new ModelAndView();

		mv.setViewName("/page/basic/statistic/queryConsumeInfo");
		request.setAttribute("memberID", memberID);
		// request.setAttribute("mobile", mobile);

		return mv;
	}
	

	// 会员消费详情数据
	@RequestMapping("/consumeinfo.do")
	public void queryConsumeInfo(HttpServletRequest request, HttpServletResponse response, String memberID)
			throws Exception {
		Integer pageNum = Integer.parseInt(request.getParameter("page"));
		Integer rows = Integer.parseInt(request.getParameter("rows"));
		Pagination pagination = new Pagination(pageNum, rows);
		List<?> result = statisticsService.showConsumeInformation(memberID, pagination);
		JSONUtil.responseJSON(response, JSONUtil.buildReportJSONByPagination(result, pagination));

	}
	
	
}
