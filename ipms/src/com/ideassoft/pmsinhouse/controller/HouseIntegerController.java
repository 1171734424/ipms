package com.ideassoft.pmsinhouse.controller;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ideassoft.bean.ControlLog;
import com.ideassoft.bean.OperateLog;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.jdbc.SqlBuilder;
import com.ideassoft.core.page.Pagination;
import com.ideassoft.pmsinhouse.service.HouseIntegerService;
import com.ideassoft.util.JSONUtil;
import com.ideassoft.util.RequestUtil;

@Transactional
@Controller
public class HouseIntegerController {
	@Autowired
	private HouseIntegerService houseIntegerService;
	
	/**
	 * 操作日志
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("houseoperateLog.do")
	public ModelAndView houseoperateLog(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String opertype = request.getParameter("opertype");
		String recorduser = request.getParameter("recorduser");
		String type = request.getParameter("type");
		String starttime = request.getParameter("starttime") == null ? sdf
				.format(new Date()) : request.getParameter("starttime");
		String endtime = request.getParameter("endtime") == null ? sdf
				.format(new Date()) : request.getParameter("endtime");
		ModelAndView mv = new ModelAndView();
		Pagination pagination = SqlBuilder.buildPagination(request);
		LoginUser loginUser = (LoginUser) request.getSession(true)
				.getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		String staffid = loginUser.getStaff().getStaffId();
		
		//List<House> houseList = integralsService.findByProperties(House.class, "staffId", staffid);
		
			@SuppressWarnings("unchecked")
			List<OperateLog> list = houseIntegerService.findBySQLWithPagination(
					"operateLogforHouse", new String[] { staffid, opertype, recorduser,
							starttime, endtime }, pagination, true);
			mv.addObject("list", list);
			mv.addObject("staffid", staffid);
			mv.addObject("pagination", pagination);
			mv.addObject("opertype", opertype);
			mv.addObject("recorduser", recorduser);
			mv.addObject("starttime", starttime);
			mv.addObject("endtime", endtime);
			mv.setViewName("page/ipmshouse/houseintegral/houseoperatelog");
			return mv;
		
	}
	/**
	 * 历史操作日志
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("houseoperateLogHistory.do")
	public ModelAndView houseoperateLogHistory(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String opertype = request.getParameter("opertype");
		String recorduser = request.getParameter("recorduser");
		String type = request.getParameter("type");
		String starttime = request.getParameter("starttime") == null ? sdf
				.format(new Date()) : request.getParameter("starttime");
		String endtime = request.getParameter("endtime") == null ? sdf
				.format(new Date()) : request.getParameter("endtime");
		ModelAndView mv = new ModelAndView();
		Pagination pagination = SqlBuilder.buildPagination(request);
		LoginUser loginUser = (LoginUser) request.getSession(true)
				.getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		String staffid = loginUser.getStaff().getStaffId();
			@SuppressWarnings("unchecked")
			List<OperateLog> list = houseIntegerService.findBySQLWithPagination(
					"opLogforHouseHistory", new String[] { staffid, opertype, recorduser,
							starttime, endtime}, pagination, true);
			mv.addObject("list", list);
			mv.addObject("staffid", staffid);
			mv.addObject("pagination", pagination);
			mv.addObject("opertype", opertype);
			mv.addObject("recorduser", recorduser);
			mv.addObject("starttime", starttime);
			mv.addObject("endtime", endtime);
			mv.setViewName("page/ipmshouse/houseintegral/houseoperatelog");
			return mv;
	}
	// 查所有的操作日志类型
	@RequestMapping("housequeryOpertype.do")
	public void queryOpertype(HttpServletRequest request,
			HttpServletResponse response,String type) throws Exception {
		List<?> list = null;
			 list = houseIntegerService.findBySQL("queryopertypesql", new String[]{"3"},true);
		
		JSONUtil.responseJSON(response, list);

	}
	/**
	 * 门锁控制日志
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("housedoorDockLog.do")
	public ModelAndView doorDockLog(HttpServletRequest request,HttpServletResponse response) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String starttime = request.getParameter("starttime") == null ? sdf
				.format(new Date()) : request.getParameter("starttime");
		String endtime = request.getParameter("endtime") == null ? sdf
				.format(new Date()) : request.getParameter("endtime");
		String type = request.getParameter("type");
		String roomid = request.getParameter("roomId");
		String commandId = request.getParameter("commandId");
		ModelAndView mv = new ModelAndView();
		Pagination pagination = SqlBuilder.buildPagination(request);
		LoginUser loginUser = (LoginUser) request.getSession(true)
				.getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		String staffid = loginUser.getStaff().getStaffId();
			
			@SuppressWarnings("unchecked")
			List<ControlLog> list = houseIntegerService.findBySQLWithPagination(
					"doorDockquery", new String[] { roomid , starttime, endtime, commandId, branchId, branchId, staffid}, pagination, true);
			mv.addObject("list", list);
			mv.addObject("staffid", staffid);
			mv.addObject("pagination", pagination);
			mv.addObject("starttime", starttime);
			mv.addObject("endtime", endtime);
			mv.addObject("roomid", roomid);
			mv.addObject("commandId", commandId);
			mv.setViewName("page/ipmshouse/houseintegral/housedoorDockLog");
			return mv;
	}
}
