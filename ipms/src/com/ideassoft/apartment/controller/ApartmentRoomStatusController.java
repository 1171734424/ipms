package com.ideassoft.apartment.controller;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hsqldb.lib.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ideassoft.apartment.service.ApartmentRoomStatusService;
import com.ideassoft.bean.Room;
import com.ideassoft.core.annotation.interfaces.RightMethodControl;
import com.ideassoft.core.annotation.interfaces.RightModelControl;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.constants.RightConstants;
import com.ideassoft.core.page.Pagination;
import com.ideassoft.util.ReflectUtil;
import com.ideassoft.util.RequestUtil;

@Transactional
@Controller
@RightModelControl(rightModel = RightConstants.RightModel.APARTMENT_CONTROL)
public class ApartmentRoomStatusController {

	@Autowired
	private ApartmentRoomStatusService apartmentRoomStatusService;

	/**
	 * 远期房态(公寓)-公寓远期房态页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/apartmentRoomStatus.do")
	@RightMethodControl(rightType = RightConstants.RightType.APA_ROMSTA)
	public ModelAndView apartmentRent(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/apartment/apartmentLeftmenu/apartmentroomstatus/apartmentroomstatus");
		return mv;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/queryApartmentRoomStatus.do")
	public ModelAndView queryApartmentRent(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		String year = request.getParameter("year");
		String pageNum = request.getParameter("pageNum");
		Pagination pagination = (Pagination) ReflectUtil.setBeanFromRequest(
				request, Pagination.class);
		if (pagination != null) {
			pagination.setPageNum(Integer.parseInt(pageNum));
			pagination.setRows(17);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		//List<Room> rooms = apartmentRoomStatusService.findByPropertiesWithSort(Room.class, "roomKey.roomId", "asc", "roomKey.branchId", loginUser.getStaff().getBranchId(),pagination);
		List<Room> rooms = apartmentRoomStatusService.findBySQLWithPagination("selectAptRoomStatus", new String[]{loginUser.getStaff().getBranchId()}, pagination, true);
		Map<String, Map<Integer, String>> roomMap = new HashMap<String, Map<Integer, String>>();
		Map<Integer, String> contractMap = null;
		for (int r = 0;r < rooms.size();r++) {
			String roomId = ((Map<?, ?>)rooms.get(r)).get("ROOM_ID").toString();
			contractMap = new HashMap<Integer, String>();
			//List<Contrart> Contracts = apartmentRoomStatusService.findByPropertiesWithSort(Contrart.class, "startTime", "asc", "branchId", loginUser.getStaff().getBranchId(), "roomId", room.getRoomKey().getRoomId(), "status", "1");
			//List<Contrart> Contracts = apartmentRoomStatusService.findBySQL("queryAptRoomStatus", Contrart.class, new String[] {loginUser.getStaff().getBranchId(), room.getRoomKey().getRoomId() }, true);
			List<Object> Contracts = apartmentRoomStatusService.findBySQL("queryAptRoomStatus", new String[] {loginUser.getStaff().getBranchId(), roomId }, true);
			if (Contracts.size() > 0) {
				for (Object Contract : Contracts) {
					int startMonth = 1;
					int endMonth = 12;
					String[] startTime = sdf.format(sdf.parse(((Map<?, ?>) Contract).get("START_TIME").toString())).split("/");
					String[] endTime = sdf.format(sdf.parse(((Map<?, ?>) Contract).get("END_TIME").toString())).split("/");
					if(startTime[0].equals(year) || endTime[0].equals(year)){
						if (startTime[0].equals(year)) {
							startMonth = Integer.parseInt(startTime[1]);
							if ( startMonth > 1) {
								for (int s = 1; s < startMonth; s++) {
									String value = contractMap.get(s);
									if (StringUtil.isEmpty(value)) {
										contractMap.put(s, "0");
									}
								}
							}
						}
						if (endTime[0].equals(year)) {
							endMonth = Integer.parseInt(endTime[1]);
							if ( endMonth < 12) {
								for (int e = endMonth + 1; e <= 12; e++) {
									String value = contractMap.get(e);
									if (StringUtil.isEmpty(value)) {
										contractMap.put(e, "0");
									}
								}
							}
						}
						for (int i = startMonth; i <= endMonth; i++) {
							String value = contractMap.get(i);
							if ("0".equals(value) || StringUtil.isEmpty(value)) {
								contractMap.put(i, startTime[2]);
							} else {
								String day = contractMap.get(i) + "|" + startTime[2];
								contractMap.put(i, day);
							}
						}
					} else if(Integer.parseInt(year) > Integer.parseInt(startTime[0]) && Integer.parseInt(year) < Integer.parseInt(endTime[0])){
						for (int i = 1; i <= 12; i++) {
							contractMap.put(i, startTime[2]);
						}
					} else if(contractMap.isEmpty()){
						for (int i = 1; i <= 12; i++) {
							contractMap.put(i, "0");
						}
					}
				}
			} else {
				for (int i = 1; i <= 12; i++) {
					contractMap.put(i, "0");
				}
			}
			roomMap.put(roomId, contractMap);
		}
		mv.addObject("rooms", rooms);
		mv.addObject("roomMap", roomMap);
		mv.addObject("pagination", pagination);
		mv.addObject("year", year);
		mv.addObject("pageNum", pagination.getPageNum());
		mv.setViewName("page/apartment/apartmentLeftmenu/apartmentroomstatus/apartmentroomstatusinfo");
		return mv;
	}
	
	/**
	 * 
	 * @Description 公寓房态图跳转
	 * @author ideas_mengl
	 * @date   2018年7月24日下午4:22:02
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/apartRoomStatus.do")
	@RightMethodControl(rightType = RightConstants.RightType.APA_STATUS)
	public ModelAndView apartRoomStatus(HttpServletRequest request, HttpServletResponse response){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/apartment/apartmentmainmenu/apartmentroomstatus/room_status");
		return mv;
	}
	
	/**
	 * 
	 * @Description 公寓合同跳转
	 * @author ideas_mengl
	 * @date   2018年7月24日下午4:25:00
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/apartmentcheckin.do")
	@RightMethodControl(rightType = RightConstants.RightType.APA_CONTRA)
	public ModelAndView apartmentcheckin(HttpServletRequest request, HttpServletResponse response){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/apartment/apartmentmainmenu/apartmentcontract/apartmentcheckin");
		return mv;
	}
	
	/**
	 * 
	 * @Description 公寓订单跳转
	 * @author ideas_mengl
	 * @date   2018年7月24日下午4:27:31
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/apartmentorder.do")
	@RightMethodControl(rightType = RightConstants.RightType.APA_ORDER)
	public ModelAndView apartmentorder(HttpServletRequest request, HttpServletResponse response){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/apartment/apartmentmainmenu/apartmentorder/apartmentorder");
		return mv;
	}
	
	
	/**
	 * 
	 * @Description 公寓日志
	 * @author ideas_mengl
	 * @date   2018年7月24日下午4:30:48
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/apartmentintegral.do")
	@RightMethodControl(rightType = RightConstants.RightType.APA_INTEG)
	public ModelAndView apartmentintegral(HttpServletRequest request, HttpServletResponse response){
		ModelAndView mv = new ModelAndView();
		String type = request.getParameter("type");
		mv.setViewName("page/apartment/apartmentmainmenu/apartmentjournal/integral");
		mv.addObject("type",type);
		return mv;
	}
	
	/**
	 * 
	 * @Description 公寓工作账
	 * @author ideas_mengl
	 * @date   2018年7月24日下午4:42:49      注意
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/apartwork_account.do")
	@RightMethodControl(rightType = RightConstants.RightType.APA_ACCOUN)
	public ModelAndView apartworkAccount(HttpServletRequest request, HttpServletResponse response){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/ipmshotel/work_account/work_account");
		return mv;
	}
	
	/**
	 * 
	 * @Description 公寓停售房跳转
	 * @author ideas_mengl
	 * @date   2018年7月24日下午4:45:26
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/apartStopSellHouse.do")
	@RightMethodControl(rightType = RightConstants.RightType.APA_STOP)
	public ModelAndView apartStopSellHouse(HttpServletRequest request, HttpServletResponse response){
		ModelAndView mv = new ModelAndView();
		String type = request.getParameter("type");
		mv.setViewName("page/apartment/apartmentmainmenu/apartmentstopselling/stopsellhouse");
		mv.addObject("type",type);
		return mv;
	}
	
	/**
	 * 
	 * @Description 公寓水电查询跳转
	 * @author ideas_mengl
	 * @date   2018年7月24日下午4:47:33
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/apartHydropowerLogin.do")
	@RightMethodControl(rightType = RightConstants.RightType.APA_WATER)
	public ModelAndView apartHydropowerLogin(HttpServletRequest request, HttpServletResponse response){
		ModelAndView mv = new ModelAndView();
		String type = request.getParameter("type");
		mv.setViewName("page/apartment/apartmentmainmenu/apartmenthydropower/hydropowerlogin");
		mv.addObject("type",type);
		return mv;
	}
	
	
}
