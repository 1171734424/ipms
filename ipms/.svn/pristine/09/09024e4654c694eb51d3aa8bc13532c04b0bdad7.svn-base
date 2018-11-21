package com.ideassoft.hotel.controller;

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

import com.ideassoft.bean.LoginLog;
import com.ideassoft.bean.OperateLog;
import com.ideassoft.bean.SaleLog;
import com.ideassoft.bean.Shift;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.jdbc.SqlBuilder;
import com.ideassoft.core.page.Pagination;
import com.ideassoft.hotel.service.IntegralsSer;
import com.ideassoft.util.JSONUtil;
import com.ideassoft.util.RequestUtil;

@Transactional
@Controller
public class IntegralsCtrl {

	@Autowired
	private IntegralsSer integralsService;
	
	/**
	 * 登录日志
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("hotelLoginLog.do")
	public ModelAndView Loginlog(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		LoginUser loginUser = (LoginUser) request.getSession(true)
				.getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		String logname = request.getParameter("logname");
		String source = request.getParameter("source");
		String status = request.getParameter("status");
		String starttime = request.getParameter("starttime") == null ? sdf
				.format(new Date()) : request.getParameter("starttime");
		String endtime = request.getParameter("endtime") == null ? sdf
				.format(new Date()) : request.getParameter("endtime");
		ModelAndView mv = new ModelAndView();
		Pagination pagination = SqlBuilder.buildPagination(request);
		@SuppressWarnings("unchecked")
		List<LoginLog> list = integralsService.findBySQLWithPagination(
				"loginlog", new String[] { logname, source, status, starttime,
						endtime, branchId }, pagination, true);
		mv.addObject("list", list);
		mv.addObject("pagination", pagination);
		mv.addObject("logname", logname);
		mv.addObject("source", source);
		mv.addObject("status", status);
		mv.addObject("starttime", starttime);
		mv.addObject("endtime", endtime);
		mv.setViewName("page/ipmshotel/hotelIntegral/loginlog");
		return mv;
	}
	
	
	
	/**
	 * 交接班日志
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("hotelShiftLog.do")
	public ModelAndView shiftLog(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String lastshift = request.getParameter("lastshift");
		String currshift = request.getParameter("currshift");
		String lastuser = request.getParameter("lastuser");
		String curruser = request.getParameter("curruser");
		String starttime = request.getParameter("starttime") == null ? sdf
				.format(new Date()) : request.getParameter("starttime");
		String endtime = request.getParameter("endtime") == null ? sdf
				.format(new Date()) : request.getParameter("endtime");
		ModelAndView mv = new ModelAndView();
		Pagination pagination = SqlBuilder.buildPagination(request);
		LoginUser loginUser = (LoginUser) request.getSession(true)
				.getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		@SuppressWarnings("unchecked")
		List<Shift> list = integralsService.findBySQLWithPagination("shiftlog",
				new String[] { branchId, lastshift, currshift, lastuser,
						curruser, starttime, endtime }, pagination, true);
		mv.addObject("list", list);
		mv.addObject("pagination", pagination);
		mv.addObject("lastshift", lastshift);
		mv.addObject("currshift", currshift);
		mv.addObject("lastuser", lastuser);
		mv.addObject("curruser", curruser);
		mv.addObject("starttime", starttime);
		mv.addObject("endtime", endtime);
		mv.setViewName("page/ipmshotel/hotelIntegral/shiftlog");
		return mv;
	}

	/**
	 * 售卖日志
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("hotelSaleLog.do")
	public ModelAndView SaleLog(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String debts = request.getParameter("debts");
		String goodsname = request.getParameter("goodsname");
		String paytype = request.getParameter("paytype");
		String starttime = request.getParameter("starttime") == null ? sdf
				.format(new Date()) : request.getParameter("starttime");
		String endtime = request.getParameter("endtime") == null ? sdf
				.format(new Date()) : request.getParameter("endtime");
		ModelAndView mv = new ModelAndView();
		Pagination pagination = SqlBuilder.buildPagination(request);
		LoginUser loginUser = (LoginUser) request.getSession(true)
				.getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		@SuppressWarnings("unchecked")
		List<SaleLog> list = integralsService.findBySQLWithPagination(
				"salelog", new String[] { branchId, debts, goodsname, paytype,
						starttime, endtime }, pagination, true);
		mv.addObject("list", list);
		mv.addObject("pagination", pagination);
		mv.addObject("debts", debts);
		mv.addObject("goodsname", goodsname);
		mv.addObject("paytype", paytype);
		mv.addObject("starttime", starttime);
		mv.addObject("endtime", endtime);
		mv.setViewName("page/ipmshotel/hotelIntegral/salelog");
		return mv;
	}

	
	/**
	 * 操作日志
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("hotelOperateLog.do")
	public ModelAndView OperateLog(HttpServletRequest request,
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
		
		if("3".equals(type)){
			@SuppressWarnings("unchecked")
			List<OperateLog> list = integralsService.findBySQLWithPagination(
					"operateLogforHouse", new String[] { staffid, opertype, recorduser,
							starttime, endtime }, pagination, true);
			mv.addObject("list", list);
			mv.addObject("staffid", staffid);
			mv.addObject("pagination", pagination);
			mv.addObject("opertype", opertype);
			mv.addObject("recorduser", recorduser);
			mv.addObject("starttime", starttime);
			mv.addObject("endtime", endtime);
			mv.setViewName("page/ipmspage/integral/operatelog");
			return mv;
		}else{
			@SuppressWarnings("unchecked")
			List<OperateLog> list = integralsService.findBySQLWithPagination(
					"operateLog", new String[] { branchId, opertype, recorduser,
							starttime, endtime }, pagination, true);
			mv.addObject("list", list);
			mv.addObject("pagination", pagination);
			mv.addObject("opertype", opertype);
			mv.addObject("recorduser", recorduser);
			mv.addObject("starttime", starttime);
			mv.addObject("endtime", endtime);
			mv.setViewName("page/ipmshotel/hotelIntegral/operatelog");
			return mv;
		}
		
		
	}
	
	
	/**
	 * 房态操作日志
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("hotelRoomstatusLog.do")
	public ModelAndView RoomstatusLog(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String recorduser = request.getParameter("recorduser");
		String starttime = request.getParameter("starttime") == null ? sdf
				.format(new Date()) : request.getParameter("starttime");
		String endtime = request.getParameter("endtime") == null ? sdf
				.format(new Date()) : request.getParameter("endtime");
		ModelAndView mv = new ModelAndView();
		Pagination pagination = SqlBuilder.buildPagination(request);
		LoginUser loginUser = (LoginUser) request.getSession(true)
				.getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		@SuppressWarnings("unchecked")
		List<OperateLog> list = integralsService.findBySQLWithPagination(
				"roomstatus", new String[] { branchId, recorduser, starttime,
						endtime }, pagination, true);
		mv.addObject("list", list);
		mv.addObject("pagination", pagination);
		mv.addObject("recorduser", recorduser);
		mv.addObject("starttime", starttime);
		mv.addObject("endtime", endtime);
		mv.setViewName("page/ipmshotel/hotelIntegral/roomstatuslog");
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
	@RequestMapping("hotelOperateLogHistory.do")
	public ModelAndView OperateLogHistory(HttpServletRequest request,
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
		if("3".equals(type)){
			@SuppressWarnings("unchecked")
			List<OperateLog> list = integralsService.findBySQLWithPagination(
					"opLogforHouseHistory", new String[] { staffid, opertype, recorduser,
							starttime, endtime}, pagination, true);
			mv.addObject("list", list);
			mv.addObject("staffid", staffid);
			mv.addObject("pagination", pagination);
			mv.addObject("opertype", opertype);
			mv.addObject("recorduser", recorduser);
			mv.addObject("starttime", starttime);
			mv.addObject("endtime", endtime);
			mv.setViewName("page/ipmspage/integral/operatelog");
			return mv;
		}else{
			@SuppressWarnings("unchecked")
			List<OperateLog> list = integralsService.findBySQLWithPagination(
					"operateLogHistory", new String[] { branchId, opertype, recorduser,
							starttime, endtime }, pagination, true);
			mv.addObject("list", list);
			mv.addObject("pagination", pagination);
			mv.addObject("opertype", opertype);
			mv.addObject("recorduser", recorduser);
			mv.addObject("starttime", starttime);
			mv.addObject("endtime", endtime);
			mv.setViewName("page/ipmshotel/hotelIntegral/operatelog");
			return mv;
		}
	}

	
	
	
	// 查所有的操作日志类型
	@RequestMapping("hotelQueryOpertype.do")
	public void queryOpertype(HttpServletRequest request,
			HttpServletResponse response,String type) throws Exception {
		List<?> list = null;
		if(type.equals("1")){
		 list = integralsService.findBySQL("queryopertypesql", new String[]{"1"},true);
		}else if(type.equals("2")){
			 list = integralsService.findBySQL("queryopertypesql", new String[]{"2"},true);
		}else{
			 list = integralsService.findBySQL("queryopertypesql", new String[]{"3"},true);
		}
		
		JSONUtil.responseJSON(response, list);

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	


}
