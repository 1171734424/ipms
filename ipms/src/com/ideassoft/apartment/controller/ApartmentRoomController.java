package com.ideassoft.apartment.controller;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ideassoft.apartment.service.ApartmentRoomService;
import com.ideassoft.bean.Aptorder;
import com.ideassoft.bean.Branch;
import com.ideassoft.bean.HaltPlan;
import com.ideassoft.bean.House;
import com.ideassoft.bean.OperateLog;
import com.ideassoft.bean.Room;
import com.ideassoft.bean.RoomStatus;
import com.ideassoft.bean.Staff;
import com.ideassoft.bean.SysParam;
import com.ideassoft.core.ajax.AjaxResult;
import com.ideassoft.core.annotation.interfaces.RightModelControl;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.constants.CommonConstants;
import com.ideassoft.core.constants.RightConstants;
import com.ideassoft.util.DateUtil;
import com.ideassoft.util.JSONUtil;
import com.ideassoft.util.RequestUtil;

@Transactional
@Controller
@RightModelControl( rightModel = RightConstants.RightModel.ROOM_CONTROL )
public class ApartmentRoomController {

	private static Logger logger = Logger.getLogger(ApartmentRoomController.class);
	
	@Autowired
	private ApartmentRoomService apartmentRoomService;

	/**
	 * 房态图-展示民宿页面
	 * 
	 * @param request
	 * @param response
	 * @param roomtype
	 * @param roomfloor
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/queryApartmentRoomList.do")
	public void queryApartmentRoomList(HttpServletRequest request, HttpServletResponse response, String roomtype,
			String roomfloor) throws Exception {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		Branch branch = (Branch) apartmentRoomService.findOneByProperties(Branch.class, "branchId", branchId);
		SysParam sysparam = (SysParam) apartmentRoomService.findOneByProperties(SysParam.class, "paramType", "SYSTEMTYPE");
		List<?> roomlist = apartmentRoomService.findBySQL("queryroomlistdata", new String[] {branchId, branchId, roomtype, roomfloor,
				branchId, branchId, roomtype, roomfloor, branchId }, true);
		List<?> totalcount = apartmentRoomService.findBySQL("querytotalcount", new String[] { branchId }, true);
		List<?> totallive = apartmentRoomService.findBySQL("querylivecount", new String[] { branchId, branchId }, true);
		Map<Object, Object> roomdata = new HashMap<Object, Object>();
		
		
		List<?> apartment = apartmentRoomService.findBySQL("queryapartroom2", new String[] { branchId, branchId, branchId }, true);
		//List<?> houselist = apartmentRoomService.findBySQL("queryhouseroom", new String[] { loginUser.getStaff().getStaffId(),loginUser.getStaff().getStaffId(),loginUser.getStaff().getStaffId()}, true);
		//List<?> housetotal = apartmentRoomService.findBySQL("queryhousecount", new String[] { loginUser.getStaff().getStaffId() }, true);
		//List<?> houselive = apartmentRoomService.findBySQL("queryhouselivecount", new String[] { loginUser.getStaff().getStaffId()}, true);
		String type = "TYPE";
		Map<String, Object> map;
		Aptorder aprtorder;
		Aptorder aprtorder2;
		if (apartment != null && !apartment.isEmpty()) {
			for (int i = 0; i < apartment.size(); i++) {//dairuzhu为空房
				map = (Map<String, Object>) apartment.get(i);
				if ((map).get("STATUS").equals(CommonConstants.RoomStatus.RoomOrder)) {
					aprtorder = (Aptorder) apartmentRoomService.findOneByProperties(Aptorder.class, "branchId", map.get("BRANCHID"), "roomId", map.get("RMID"), "status", "1");
					aprtorder2 = (Aptorder) apartmentRoomService.findOneByProperties(Aptorder.class, "branchId", map.get("BRANCHID"), "roomId", map.get("RMID"), "status", "6");
					if(aprtorder != null && equalDate(aprtorder.getArrivalTime(),new Date())){
						map.put(type, "预订");
					}else{
						map.put(type, "空置房");
						map.put("STATUS", "1");
					}
				} else if ((map).get("STATUS").equals(CommonConstants.RoomStatus.RoomNull)) {
					map.put(type, "空置房");
				} else if ((map).get("STATUS").equals(CommonConstants.RoomStatus.RoomChecked)) {
					map.put(type, "已入住");
				}
			}
		}

		if (branch.getRank().equals(CommonConstants.SystemLevel.SubBranch)) {
			if (branch.getBranchType().equals(CommonConstants.SubBranchType.APART)) {
				roomdata.put("apartment", apartment);
				roomdata.put("state", 2);
			} else if (branch.getBranchType().equals(CommonConstants.SubBranchType.HOTEL)) {
				roomdata.put("roomlist", roomlist);
				roomdata.put("totalcount", totalcount);
				roomdata.put("totallive", totallive);
				roomdata.put("state", 1);
			} 
		} else {
			//roomdata.put("houselist", houselist);
			roomdata.put("totalcount", totalcount);
			roomdata.put("totallive", totallive);
			roomdata.put("state", 3);
			//roomdata.put("totalcount", housetotal);
			//roomdata.put("totallive", houselive);
		}
		JSONUtil.responseJSON(response, roomdata);
	}
	
	public static boolean equalDate(Date date1,Date date2){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date1);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_YEAR);
		calendar.setTime(date2);
		int year2 = calendar.get(Calendar.YEAR);
		int month2 = calendar.get(Calendar.MONTH);
		int day2 = calendar.get(Calendar.DAY_OF_YEAR);
		if(year == year2 && month == month2 && day == day2){
			return true;
		}
		return false;
		
	}
	
	
	
	/**
	 * 房态图-查询房间状态数量
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/roomApartStatusCount.do")
	public void roomApartStatusCount(HttpServletRequest request, HttpServletResponse response) {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		List<?> roomCountList = apartmentRoomService.findBySQL("apartmentRoomCount", new String[] { branchId, branchId,
				branchId, branchId, branchId, branchId, branchId, branchId, branchId, branchId, }, true);
		Map<Object, Object> roomCountMap = new HashMap<Object, Object>();
		roomCountMap.put("roomCountList", roomCountList);
		JSONUtil.responseJSON(response, roomCountMap);
	}
	
	/**
	 * 房态图-民宿预离在住
	 * 
	 * @param request
	 * @param response
	 * @param type
	 * @throws Exception
	 */
	@RequestMapping("/queryApartmentArrival.do")
	public void queryApartmentArrival(HttpServletRequest request, HttpServletResponse response, String type) throws Exception {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		List<?> arrivalList = new ArrayList<Object>();
		if (type.equals("1")) {
			 arrivalList = apartmentRoomService.findBySQL("queryarrival", new String[] { staff.getStaffId() }, true);
		} else if (type.equals("2")) {
			arrivalList = apartmentRoomService.findBySQL("queryhouseleave", new String[] { staff.getStaffId() }, true);
		} else if (type.equals("3")) {
			arrivalList = apartmentRoomService.findBySQL("queryhouselive", new String[] { staff.getStaffId() }, true);
		}
		JSONUtil.responseJSON(response, arrivalList);
	}
	
	/**
	 * 房态图-预离
	 * 
	 * @param request
	 * @param response
	 * @param status
	 * @param roomtype
	 * @param roomfloor
	 * @throws Exception
	 */
	@RequestMapping("/queryApartLeavelive.do ")
	public void queryApartLeavelive(HttpServletRequest request, HttpServletResponse response, String status,
			String roomtype, String roomfloor) throws Exception {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		List<?> arrivalList = new ArrayList<Object>();
		if (("1").equals(status)) {
			arrivalList = apartmentRoomService.findBySQL("queryleave", new String[] { branchId, branchId, roomtype, roomfloor, branchId }, true);
		} else if (("2").equals(status)) {
			arrivalList = apartmentRoomService.findBySQL("querylive", new String[] { branchId, branchId, roomtype, roomfloor, branchId }, true);
		}
		JSONUtil.responseJSON(response, arrivalList);
	}
	
	/**
	 * 房态图-查询房间状态
	 * 
	 * @param request
	 * @param response
	 * @param status
	 * @param roomtype
	 * @param roomfloor
	 * @throws Exception
	 */
	@RequestMapping("/queryApartRoomByStatus.do")
	public void queryApartRoomByStatus(HttpServletRequest request, HttpServletResponse response, String status,
			String roomtype, String roomfloor) throws Exception {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		List<?> arrivalList = new ArrayList<Object>();
		if (("empty").equals(status)) {
			arrivalList = apartmentRoomService.findBySQL("queryroomempty", new String[] { branchId, branchId, roomtype, roomfloor }, true);
		} else if (("dirty").equals(status)) {
			arrivalList = apartmentRoomService.findBySQL("queryroomdirty", new String[] { branchId, branchId, branchId, roomtype, roomfloor }, true);
		} else if (("stop").equals(status)) {
			arrivalList = apartmentRoomService.findBySQL("queryroomestop", new String[] { branchId, branchId, roomtype, roomfloor }, true);
		} else if (("maintence").equals(status)) {
			arrivalList = apartmentRoomService.findBySQL("queryroommaintence", new String[] { branchId, roomtype, roomfloor },
					true);
		}
		JSONUtil.responseJSON(response, arrivalList);
	}
	
	/**
	 * 房态图-预定直接入住合并
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/checkInApartByOrder.do")
	public ModelAndView checkInApartByOrder(HttpServletRequest request,HttpServletResponse response){
		String roomType = request.getParameter("roomType");
		String roomId = request.getParameter("roomId");
		ModelAndView mv = new ModelAndView(); 
		mv.setViewName("page/ipmspage/leftmenu/leftorder/checkInByOrder");
		if(roomType != null && roomId != null){
			mv.addObject("roomType",roomType);
			mv.addObject("roomId",roomId);
		}
		return mv;
	}
	
	/**
	 * 房态图-查询是否是停售房
	 * 
	 * @param request
	 * @param response
	 * @param roomId
	 */
	@RequestMapping("/getApartLogIdByRoom.do")
	public void getApartLogIdByRoom(HttpServletRequest request, HttpServletResponse response, String roomId) {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		HaltPlan haltplan = new HaltPlan();
		if (roomId.indexOf("H") >= 0) {
			haltplan = (HaltPlan) apartmentRoomService.findOneByProperties(HaltPlan.class, "branchId", roomId, 
					"status", "3");
		} else {
			haltplan = (HaltPlan) apartmentRoomService.findOneByProperties(HaltPlan.class, "branchId", branchId, 
				"status", "3", "roomId", roomId);
		}
		
		try {
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.SUCESS, "", haltplan));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 房态图-查询房间状态
	 * 
	 * @param request
	 * @param response
	 * @param roomfloor
	 * @param roomType
	 * @param label
	 * @throws Exception
	 */
	@RequestMapping("/queryApartByRoomType.do")
	public void queryApartByRoomType(HttpServletRequest request, HttpServletResponse response, String roomfloor,
			String roomType, String label) throws Exception {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		List<?> roomList = new ArrayList<Object>();

		if ("预离".equals(label)) {
			roomList = apartmentRoomService.findBySQL("queryleave", new String[] { branchId, branchId, roomType, roomfloor, branchId }, true);
		} else if ("在住".equals(label)) {
			roomList = apartmentRoomService.findBySQL("querylive", new String[] { branchId, branchId, roomType, roomfloor, branchId }, true);
		} else if ("空净房".equals(label)) {
			roomList = apartmentRoomService.findBySQL("queryroomempty", new String[] { branchId, branchId, roomType, roomfloor }, true);
		} else if ("脏房".equals(label)) {
			roomList = apartmentRoomService.findBySQL("queryroomdirty", new String[] { branchId, branchId, branchId, roomType, roomfloor }, true);
		} else if ("停售房".equals(label)) {
			roomList = apartmentRoomService.findBySQL("queryroomestop", new String[] { branchId, branchId, roomType, roomfloor }, true);
		} else if ("维修房".equals(label)) {
			roomList = apartmentRoomService.findBySQL("queryroommaintence", new String[] { branchId, branchId, roomType, roomfloor }, true);
		} else {
			roomList = apartmentRoomService.findBySQL("queryroomlistdata", new String[] {branchId, branchId, roomType, roomfloor,
					branchId, branchId, roomType, roomfloor, branchId }, true);
		}
		JSONUtil.responseJSON(response, roomList);
	}
	
	/**
	 * 房态图-获取房间楼层信息
	 * 
	 * @param request
	 * @param response
	 * @param checkid
	 * @param strguest
	 */
	@RequestMapping("/loadApartRoomFloor.do")
	public void loadApartRoomFloor(HttpServletRequest request, HttpServletResponse response, String checkid, String strguest) {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginuser.getStaff().getBranchId();
		String sql = "querycountfloor";
		try {
			List<?> roomtype = apartmentRoomService.findBySQL(sql, new String[] { branchId }, true);
			JSONUtil.responseJSON(response, roomtype);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 房态图-预订查询房型
	 * 
	 * @param request
	 * @param response
	 * @param checkid
	 * @param strguest
	 */
	@RequestMapping("/loadApartsearchroomtype.do")
	public void loadApartsearchroomtype(HttpServletRequest request, HttpServletResponse response, String checkid,
			String strguest) {
		try {
			LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
			String branchId = loginuser.getStaff().getBranchId();
			List<?> roomtype = apartmentRoomService.getRoomtype(branchId);
			JSONUtil.responseJSON(response, roomtype);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 房态图-查询房间信息
	 * 
	 * @param request
	 * @param response
	 * @param roomFloor
	 * @param roomtype
	 * @param label
	 * @throws Exception
	 */
	@RequestMapping("/queryApartByRoomFloor.do")
	public void queryApartByRoomFloor(HttpServletRequest request, HttpServletResponse response, String roomFloor,
			String roomtype, String label) throws Exception {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		List<?> roomlList = new ArrayList<Object>();
		if (StringUtils.isEmpty(roomFloor)) {
			roomFloor = "";
		}

		if ("预离".equals(label)) {
			roomlList = apartmentRoomService.findBySQL("queryleave", new String[] { branchId, branchId, roomtype, roomFloor, branchId }, true);
		} else if ("在住".equals(label)) {
			roomlList = apartmentRoomService.findBySQL("querylive", new String[] { branchId, branchId, roomtype, roomFloor, branchId }, true);
		} else if ("空净房".equals(label)) {
			roomlList = apartmentRoomService.findBySQL("queryroomempty", new String[] { branchId, branchId, roomtype, roomFloor }, true);
		} else if ("脏房".equals(label)) {
			roomlList = apartmentRoomService.findBySQL("queryroomdirty", new String[] { branchId, branchId, branchId, roomtype, roomFloor }, true);
		} else if ("停售房".equals(label)) {
			roomlList = apartmentRoomService.findBySQL("queryroomestop", new String[] { branchId, branchId, roomtype, roomFloor }, true);
		} else if ("维修房".equals(label)) { 
			roomlList = apartmentRoomService.findBySQL("queryroommaintence", new String[] { branchId, branchId, roomtype, roomFloor },
					true);
		} else {
			roomlList = apartmentRoomService.findBySQL("queryroomlistdata", new String[] {branchId, branchId, roomtype, roomFloor,
					branchId, branchId, roomtype, roomFloor, branchId }, true);
		}

		Map<Object, Object> roomdata = new HashMap<Object, Object>();
		roomdata.put("roomlist", roomlList);
		roomdata.put("roomfloorcount", roomlList.size());
		JSONUtil.responseJSON(response, roomdata);
	}
	
	/**
	 * 房态图-根据房号查房态
	 * 
	 * @param request
	 * @param response
	 * @param roomId
	 * @throws Exception
	 */
	@RequestMapping("/queryApartByRoomId.do")
	public void queryApartByRoomId(HttpServletRequest request, HttpServletResponse response, String roomId) throws Exception {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		List<?> roomlList = apartmentRoomService.findBySQL("queryroomid", new String[] { branchId, branchId, branchId, roomId, branchId, roomId, branchId },
				true);
		JSONUtil.responseJSON(response, roomlList);
	}
	
	/**
	 * 房态图-设置房间状态
	 * 
	 * @param request
	 * @param response
	 * @param roomstatus
	 * @param roomId
	 * @param type
	 * @throws Exception
	 */
	@RequestMapping("/setApartRoomStatus.do")
	public void setApartRoomStatus(HttpServletRequest request, HttpServletResponse response, String roomstatus, String roomId, 
					String type)
			throws Exception {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		if (roomId.indexOf("H") >= 0 ) {
			House housedata = (House) apartmentRoomService.findOneByProperties(House.class, "houseId", roomId, 
					"staffId", loginUser.getStaff().getStaffId());
			housedata.setStatus(roomstatus);
		} else if (type.equals(CommonConstants.Branch.APARTMENTID)) {
			Room roomdata = (Room) apartmentRoomService.findOneByProperties(Room.class, "roomKey.roomId", roomId,
					"roomKey.branchId", branchId, "theme", "2");
//			RoomStatus roomstatusdata = (RoomStatus) roomService.findOneByProperties(RoomStatus.class, "branchId",
//					branchId, "roomType", roomdata.getRoomType());
//			if (roomstatus.equals(CommonConstants.RoomStatus.RoomNull)) {
//				roomstatusdata.setCount(roomstatusdata.getCount() + 1);
//				roomstatusdata.setSellnum(roomstatusdata.getSellnum() + 1);
//			}
			roomdata.setStatus(roomstatus);
		} else if (type.equals(CommonConstants.Branch.HOTELID)) {
			Room roomdata = (Room) apartmentRoomService.findOneByProperties(Room.class, "roomKey.roomId", roomId,
					"roomKey.branchId", branchId, "theme", "1");
			RoomStatus roomstatusdata = (RoomStatus) apartmentRoomService.findOneByProperties(RoomStatus.class, "branchId",
					branchId, "roomType", roomdata.getRoomType());
			if (roomdata == null || StringUtils.isEmpty(roomdata.toString())) {
				JSONUtil.responseJSON(response, new AjaxResult(0, "无此房信息!"));
			} else {
				if (roomstatus.equals(CommonConstants.RoomStatus.RoomNull)) {
					roomstatusdata.setCount(roomstatusdata.getCount() + 1);
					roomstatusdata.setSellnum(roomstatusdata.getSellnum() + 1);
				} else if (roomstatus.equals(CommonConstants.RoomStatus.RoomStop)) {
					if (roomdata.getStatus().equals(CommonConstants.RoomStatus.RoomDirty)) {
						roomstatusdata.setSellnum(roomstatusdata.getSellnum() - 1);
						roomstatusdata.setStopnum(roomstatusdata.getStopnum() + 1);
					} else {
						roomstatusdata.setCount(roomstatusdata.getCount() - 1);
						roomstatusdata.setSellnum(roomstatusdata.getSellnum() - 1);
						roomstatusdata.setStopnum(roomstatusdata.getStopnum() + 1);
					}
				} else if (roomstatus.equals(CommonConstants.RoomStatus.RoomDirty)) {
					roomstatusdata.setCount(roomstatusdata.getCount() - 1);
				}
				// roomdata.getRoomKey().setStatus(roomstatus);
				try {
					// roomService.save(roomdata);
					apartmentRoomService.upateroomstatus(branchId, roomId, roomstatus);
					apartmentRoomService.save(roomstatusdata);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}
		SysParam sysparam = (SysParam) apartmentRoomService.findOneByProperties(SysParam.class, "paramType", "ROOMSTATUS",
				"content", roomstatus);
		Date date = new Date();
		OperateLog operlog = new OperateLog();
		operlog.setBranchId(branchId);
		operlog.setLogId(DateUtil.currentDate("yyMMdd") + branchId + apartmentRoomService.getSequence("SEQ_OPERATELOG_ID"));
		String operid = InetAddress.getLocalHost().toString();// IP地址
		operid = (String) operid.subSequence(operid.indexOf("/") + 1, operid.length());
		operlog.setOperIp(operid);
		operlog.setOperType("6");
		operlog.setOperModule("房态操作");
		operlog.setRecordUser(loginUser.getStaff().getStaffId());
		operlog.setRecordTime(date);
		operlog.setContent(loginUser.getStaff().getStaffName() + "设置"+roomId+"房间状态为" + sysparam.getParamName());
		
		try {
			// roomService.save(roomdata);
			apartmentRoomService.save(operlog);
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONUtil.responseJSON(response, new AjaxResult(1, "操作成功!"));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	

	

	@RequestMapping("/showgetcontinue.do")
	public ModelAndView showGetContinue(HttpServletRequest request) {
		String checkid = request.getParameter("checkid");
		Check check = (Check) roomService.findOneByProperties(Check.class, "checkId", checkid);
		ModelAndView mv = new ModelAndView();
		mv.addObject("checkid", checkid);
		Date checkouttimedate = check.getCheckoutTime();
		String checkoutTime = DateUtil.d2s(checkouttimedate, "yyyy/MM/dd HH:mm");
		mv.addObject("checkoutTime", checkoutTime);
		mv.setViewName("/page/ipmspage/room_statistics/getcontinue");
		return mv;
	}
	
	@RequestMapping("/showresetprice.do")
	@RightMethodControl( rightType = RightConstants.RightType.AD_ROOMPRC )
	public ModelAndView showresetprice(HttpServletRequest request, String checkid) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("checkid", checkid);
		mv.setViewName("/page/ipmspage/room_statistics/resetroomprice");
		return mv;
	}

	@RequestMapping("/showtransferorder.do")
	public ModelAndView orderSerach(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/page/ipmspage/room_statistics/transferorder");
		return mv;
	}

	@RequestMapping("/showTransferBill.do")
	public ModelAndView showTransferBill(HttpServletRequest request) {
		String checkid = request.getParameter("checkid");
		ModelAndView mv = new ModelAndView();
		mv.addObject("checkid", checkid);
		mv.setViewName("/page/ipmspage/room_statistics/transfer");
		return mv;
	}

	@RequestMapping("/showgetAvailableRoom.do")
	public ModelAndView showgetAvailableRoom(HttpServletRequest request) {
		String checkid = request.getParameter("checkid");
		ModelAndView mv = new ModelAndView();
		mv.addObject("checkid", checkid);
		mv.setViewName("/page/ipmspage/room_statistics/AvailableRoom");
		return mv;
	}

	@RequestMapping("/showgetAddBill.do")
	public ModelAndView showgetAddBill(HttpServletRequest request) {
		String checkid = request.getParameter("checkid");
		ModelAndView mv = new ModelAndView();
		mv.addObject("checkid", checkid);
		mv.setViewName("/page/ipmspage/room_statistics/add_bill");
		return mv;
	}

	@RequestMapping("/showgetAddWorkBill.do")
	public ModelAndView showgetAddWorkBill(HttpServletRequest request) {
		String workbillid = request.getParameter("workbillid");
		ModelAndView mv = new ModelAndView();
		mv.addObject("workbillid", workbillid);
		mv.setViewName("/page/ipmspage/work_account/add_workbill");
		return mv;
	}

	@RequestMapping("/showupdateRoomPrice.do")
	public ModelAndView showupdateRoomPrice(HttpServletRequest request) {
		String checkid = request.getParameter("checkid");
		ModelAndView mv = new ModelAndView();
		mv.addObject("checkid", checkid);
		mv.setViewName("/page/ipmspage/room_statistics/update_roomprice");
		return mv;
	}

	@RequestMapping("/showPromptRecord.do")
	public ModelAndView showPromptRecord(HttpServletRequest request) {
		String checkid = request.getParameter("checkid");
		ModelAndView mv = new ModelAndView();
		mv.addObject("checkid", checkid);
		mv.setViewName("/page/ipmspage/order/addPromptRecord");
		return mv;
	}
	
	@RequestMapping("/showOrderPromptRecord.do")
	public ModelAndView showOrderPromptRecord(HttpServletRequest request) {
		String checkid = request.getParameter("checkId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("checkid", checkid);
		mv.setViewName("/page/ipmspage/order/addOrderPrompt");
		return mv;
	}

	@RequestMapping("/showroommap.do")
	public ModelAndView showroommap(HttpServletRequest request) throws Exception {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginuser.getStaff().getBranchId();
		String checkid = request.getParameter("checkid");
		List<?> roomtype = leftmenuService.getRoomtype(branchId);
		ModelAndView mv = new ModelAndView();
		mv.addObject("checkid", checkid);
		mv.addObject("roomtype", roomtype);
		mv.setViewName("/page/ipmspage/room_statistics/roommap");
		return mv;
	}
	
	@RequestMapping("/showconsumption.do")
	@RightMethodControl(rightType = RightConstants.RightType.CON_BILL)
	public ModelAndView showconsumption(HttpServletRequest request, 
			String checkid, String strbillid) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.addObject("checkid", checkid);
		mv.addObject("strbillid", strbillid);
		mv.setViewName("/page/ipmspage/room_statistics/consumption");
		return mv;
	}

	@RequestMapping("/showroomlistcheckdata.do")
	public ModelAndView showroomlistcheckdata(HttpServletRequest request, MultiQueryCheck multiQuerycheck)
			throws Exception {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String branchId = staff.getBranchId();
		ModelAndView mv = new ModelAndView();
		Pagination pagination = SqlBuilder.buildPagination(request);
		List<?> list = roomService.queryCheckData(branchId, multiQuerycheck, CommonConstants.CheckUserType.HOST, CommonConstants.CheckinType.CHECK, pagination);
		mv.addObject("list", list);
		mv.addObject("pagination", pagination);
		mv.addObject("multiQuerycheck", multiQuerycheck);
		mv.setViewName("/page/ipmspage/room_statistics/roomlistCheckData");
		return mv;
	}
	
	@RequestMapping("/showRoomPlanData.do")
	public ModelAndView showRoomPlanData(HttpServletRequest request, HttpServletResponse response, String roomid) throws Exception {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		ModelAndView mv = new ModelAndView();
		Pagination pagination = SqlBuilder.buildPagination(request);
		pagination.setRows(14);
		String sql = "loadroomplan";
		List<?> result = roomService.findBySQLWithPagination(sql, new String[] { branchId, roomid }, pagination, true);
		mv.addObject("list", result);
		mv.addObject("pagination", pagination);
		mv.addObject("roomid", roomid);
		mv.setViewName("/page/ipmspage/room_statistics/chummageplanData");
		return mv;
	}

	@RequestMapping("/showupdateMember.do")
	public ModelAndView showupdateMember(HttpServletRequest request, String memberId) throws Exception {
		// LoginUser loginuser = (LoginUser) request.getSession(true)
		// .getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		// Staff staff = loginuser.getStaff();
		// String branchId = staff.getBranchId();
		Member member = (Member) roomService.findOneByProperties(Member.class, "memberId", memberId);
		MemberRank memberrank = (MemberRank) roomService.findOneByProperties(MemberRank.class, "memberRank", member
				.getMemberRank());
		String rankName = memberrank.getRankName();
		String validTime = DateUtil.d2s(member.getValidTime(), "yyyy/MM/dd");
		String invalidTime = DateUtil.d2s(member.getInvalidTime(), "yyyy/MM/dd");
		String esource = member.getSource();
		String decodesource = "";
		int intsource = Integer.parseInt(esource);
		switch (intsource) {
		case 1:
			decodesource = "app";
			break;
		case 2:
			decodesource = "网站";
			break;
		case 3:
			decodesource = "分店";
			break;
		case 4:
			decodesource = "wap";
			break;
		case 5:
			decodesource = "合作渠道";
			break;
		case 6:
			decodesource = "其他";
			break;
		case 7:
			decodesource = "微信";
			break;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("member", member);
		map.put("rankName", rankName);
		map.put("validTime", validTime);
		map.put("invalidTime", invalidTime);
		map.put("decodesource", decodesource);
		JSONObject jsonmember = JSONUtil.fromObject(map);
		ModelAndView mv = new ModelAndView();
		mv.addObject("member", jsonmember);
		mv.setViewName("/page/ipmspage/room_statistics/updatemember");
		return mv;
	}

	// 展示页面

	@RequestMapping("/loadCheckData.do")
	public void loadCheckData(HttpServletRequest request, HttpServletResponse response, MultiQueryCheck multiQuerycheck) {
		String sql = "loadOnlineCheckData";
		List<?> list = roomService.findBySQL(sql, new String[] { multiQuerycheck.getStatus(),
				multiQuerycheck.getCheckid(), multiQuerycheck.getRoomid(), multiQuerycheck.getRoomtype(),
				multiQuerycheck.getGuarantee(), multiQuerycheck.getMphone() }, true);
		JSONUtil.responseJSON(response, list);
	}

	

	@RequestMapping("submitgetcontinuebill.do")
	public void submitgetcontinuebill(HttpServletRequest request, HttpServletResponse response, String checkId,
			String projectid, String cuurent_checkouttime, String getcontinuetime, String amount) {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		Bill bill = new Bill();
		Check check = (Check) roomService.findOneByProperties(Check.class, "checkId", checkId);
		if( cuurent_checkouttime.equals(getcontinuetime)){
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, "续住日期未更改!"));
			return;
		}
		check.setCheckoutTime(DateUtil.s2d(getcontinuetime, "yyyy/MM/dd HH:mm"));

		if(StringUtils.isEmpty(amount)){
			amount = "0";
		}
		String branchid = check.getBranchId();
		String billId = DateUtil.currentDate("yyMMdd") + branchid + roomService.getSequence("SEQ_NEW_BILL");
		bill.setBillId(billId);
		bill.setBranchId(branchid);
		bill.setCheckId(checkId);
		RoomUtil.setBillPayment(bill, projectid, 
				Double.valueOf(amount));
		bill.setDescribe("续住");
		bill.setStatus(CommonConstants.BillStatus.BillNormal);
		bill.setRecordUser(staff.getStaffId());

		ExtensionLog extensionlog = new ExtensionLog();
		String logId = DateUtil.currentDate("yyMMdd") + branchid + roomService.getSequence("SEQ_NEW_EXTENSIONLOG");
		extensionlog.setLogId(logId);
		extensionlog.setBranchId(branchid);
		extensionlog.setCheckId(checkId);
		extensionlog.setAddDay(DateUtil.s2d(getcontinuetime, "yyyy/MM/dd hh:mm"));
		extensionlog.setLastDay(DateUtil.s2d(cuurent_checkouttime, "yyyy/MM/dd hh:mm"));
		extensionlog.setRpId(check.getRpId());
		extensionlog.setRoomPrice(check.getRoomPrice());
		extensionlog.setRecordUser(staff.getStaffId());

		try {
			if (!StringUtils.isEmpty(projectid)) {
				roomService.save(bill);
			}
			roomService.save(check);
			roomService.save(extensionlog);
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.SUCESS, "续住成功!"));

		} catch (Exception e) {
			e.printStackTrace();
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, "续住失败!"));
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/changeRoom.do")
	public void changeRoom(HttpServletRequest request, HttpServletResponse response, String roomid, String checkid) {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchid = loginuser.getStaff().getBranchId();

		String sqlroomprice = "selectchangeroom";
		List<Map<String, ?>> listroomprice = roomService.findBySQL(sqlroomprice, new String[] { roomid, branchid,
				checkid }, true);
		String rpid = (String) listroomprice.get(0).get("RPID");
		String roomtype = (String) listroomprice.get(0).get("ROOMTYPE");
		String roomprice = listroomprice.get(0).get("ROOMPRICE").toString();
		
		 * String sqlroom = "queryroom"; List<?> roomlist =
		 * roomservice.findBySQL(sqlroom, new String[]{roomid}, true); Order
		 * order = (Order) roomservice.findOneByProperties(Order.class,
		 * "orderId", checkid); Member member = (Member)
		 * roomservice.findOneByProperties(Member.class, "memberId",
		 * order.getOrderUser()); RoomPrice roomprice = (RoomPrice)
		 * roomservice.findOneByProperties(RoomPrice.class,
		 * "roomType",(String)((Map) roomlist.get(0)).get("ROOM_TYPE"),
		 * "mmeberRank", member.getMemberRank());
		 
		Check lastcheck = (Check) roomService.findOneByProperties(Check.class, "checkId", checkid);
		String lastroomid = lastcheck.getRoomId();

		SwitchRoom switchroom = new SwitchRoom();
		String logId = DateUtil.currentDate("yyMMdd") + branchid + roomService.getSequence("SEQ_NEW_SWITCHROOM");
		switchroom.setLogId(logId);
		switchroom.setBranchId(branchid);
		switchroom.setCheckId(checkid);
		switchroom.setLastrpId(lastcheck.getRpId());
		switchroom.setLastRoomid(lastroomid);
		switchroom.setLastRoomtype(lastcheck.getRoomType());
		switchroom.setLastrRoomprice(lastcheck.getRoomPrice());
		switchroom.setCurrrpId(rpid);
		switchroom.setCurrRoomid(roomid);
		switchroom.setCurrRoomtype(roomtype);
		switchroom.setCurrRoomprice(Double.parseDouble(roomprice));
		switchroom.setRecordUser(loginuser.getStaff().getStaffId());

		try {
			roomService.save(switchroom);
			roomService.upateroomstatus(branchid, roomid, CommonConstants.RoomStatus.RoomChecked);
			roomService.upateroomstatus(branchid, lastroomid, CommonConstants.RoomStatus.RoomDirty);
			roomService.updatecheck(rpid, roomid, roomtype, Double.parseDouble(roomprice), checkid);
			RoomUtil.cancelRoommap(lastroomid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// JSONUtil.responseJSON(response, list);
	}

	@RequestMapping("/loadRoomBillData.do")
	public void loadroomlistbill(HttpServletRequest request, HttpServletResponse  response, String checkid, String status) {
		String branchid = "";
		Check check = (Check) roomService.findOneByProperties(Check.class, "checkId", checkid);
		Order order = (Order) roomService.findOneByProperties(Order.class, "orderId", checkid);
		branchid = check == null ? order.getBranchId() : check.getBranchId();
		String sql = "loadroomlistbill";
		List<?> alllist = roomService.findBySQL(sql, new String[] { checkid, branchid, null}, true);
		List<?> normallist = roomService.findBySQL(sql, new String[] { checkid, branchid, 
				CommonConstants.BillStatus.BillNormal }, true);
		List<String> liststatus = new ArrayList<String>();
		liststatus.add("1");
		liststatus.add("4");
		List<?> list = roomService.findBillBySql(checkid, branchid, status);
		if (alllist.size() <= normallist.size()) {
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, 
					"账单无冲减/转账/结账记录!", list));
		} else {
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.SUCESS, 
					"", list));
		}
	}

	@RequestMapping("/loadWorkBillData.do")
	public void loadWorkBillData(HttpServletRequest request, HttpServletResponse response, String workbillid,
			String status) {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchid = loginuser.getStaff().getBranchId();
		String sql = "loadworkbilldetail";
		List<?> list = roomService.findBySQL(sql, new String[] { status, workbillid, branchid }, true);
		JSONUtil.responseJSON(response, list);
	}

	
	@SuppressWarnings("unchecked")
	@RequestMapping("/showallroomtype.do")
	public void queryalltype(HttpServletRequest request, HttpServletResponse response, String theme, String status) {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginuser.getStaff().getBranchId();
		String sql = "queryallroomtype";
		String sql1 = "queryoneroomtype";
		List<Map<String, String>> roomtypelist = roomService.findBySQL(sql, new String[] { status, theme, branchId, branchId },
				true);
		
		Map<String, List<String>> result = new HashMap<String, List<String>>();
		for (Map<String, String> map : roomtypelist) {
			String type = (String) map.get("ROOMTYPE");
			String typename = (String) map.get("ROOMNAME");
			List<Map<String, String>> maptype = roomService.findBySQL(sql1, new String[] { status, theme, type,
					branchId , branchId}, true);
			List<String> listroomid = new ArrayList<String>();
			for (Map map2 : maptype) {
				listroomid.add((String) map2.get("ROOMID"));
			}
			result.put(typename, listroomid);
		}
		JSONUtil.responseJSON(response, result);
	}

	@RequestMapping("/addPromptRecord.do")
	public void addPromptRecord(HttpServletRequest request, HttpServletResponse response, 
			String checkId, String content) throws UnknownHostException {
		LoginUser loginuser = (LoginUser) request.getSession(true).
			getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Order orderdata = (Order) roomService.findOneByProperties(Order.class, "orderId", checkId);
		Staff staff = loginuser.getStaff();
		Tips tips = new Tips();
		String logid = DateUtil.currentDate("yyMMdd") + roomService.getSequence("SEQ_TIPS_ID");
		tips.setLogId(logid);
		tips.setCheckId(checkId);
		tips.setContent(content);
		tips.setStatus(CommonConstants.TipStatus.Add);
		if (orderdata.getStatus().equals(CommonConstants.OrderStatus.NewOrder) ||
				orderdata.getStatus().equals(CommonConstants.OrderStatus.Absent) ||
				orderdata.getStatus().equals(CommonConstants.OrderStatus.Cancel)) {
			tips.setType(CommonConstants.TipType.OrderTip);
		}
		tips.setRecordUser(staff.getStaffId());
		
		OperateLog operlog = new OperateLog();
		operlog.setBranchId(staff.getBranchId());
		operlog.setLogId(DateUtil.currentDate("yyMMdd") + staff.getBranchId() + roomService.getSequence("SEQ_OPERATELOG_ID"));
		String operid = InetAddress.getLocalHost().toString();// IP地址
		operid = (String) operid.subSequence(operid.indexOf("/") + 1, operid.length());
		operlog.setOperIp(operid);
		operlog.setOperType("order");
		operlog.setOperModule("订单提示");
		operlog.setContent("订单号：" + checkId + "新加了一条提示信息，编号为：" + logid);
		operlog.setRecordUser(loginuser.getStaff().getStaffId());
		operlog.setRecordTime(new Date());
		// List<?> list = roomService.findBySQL("countPrompt", new
		// String[]{checkId}, true);
		// Map<String,String> message = new HashMap<String,String>();
		try {
			roomService.save(tips);
			roomService.save(operlog);
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.SUCESS, "添加成功 "));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/addbill.do")
	public void add_bill(HttpServletRequest request, HttpServletResponse response, String checkId, String project,
			String projectid, String amount, String remark) {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String branchid = "";
		Bill bill = new Bill();
		Check check = (Check) roomService.findOneByProperties(Check.class, "checkId", checkId);
		Order order = (Order) roomService.findOneByProperties(Order.class, "orderId", checkId);
		String source = order.getSource();
		branchid = check == null ? order.getBranchId() : check.getBranchId();
		String billId = DateUtil.currentDate("yyMMdd") + branchid + source + roomService.getSequence("SEQ_NEW_BILL");
		bill.setBillId(billId);
		bill.setBranchId(branchid);
		bill.setCheckId(checkId);
		bill.setDescribe("入账");
		RoomUtil.setBillPayment(bill, projectid, Double.valueOf(amount));
		bill.setStatus(CommonConstants.BillStatus.BillNormal);
		bill.setRemark(remark);
		bill.setRecordUser(staff.getStaffId());

		Recording recording = new Recording();
		String recordId = DateUtil.currentDate("yyMMdd") + branchid + roomService.getSequence("SEQ_NEW_RECORDING");
		recording.setRecordId(recordId);
		recording.setBranchId(branchid);
		recording.setCheckId(checkId);
		recording.setRecordType(CommonConstants.BillrecordingType.billType);
		recording.setProjectId(projectid);
		recording.setFee(Double.valueOf(amount));
		recording.setRecordUser(staff.getStaffId());

		try {
			roomService.save(bill);
			roomService.save(recording);
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.SUCESS, "入账成功!"));
		} catch (Exception e) {
			e.printStackTrace();
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, "入账失败!"));
		}
	}

	@RequestMapping("/addworkbill.do")
	public void addworkbill(HttpServletRequest request, HttpServletResponse response, String workbillid,
			String project, String projectid, String amount, String remark) {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		//LoginGetShift loginGetShift = (LoginGetShift) request.getSession(true).getAttribute("loginGetShift");
		Staff staff = loginuser.getStaff();
		WorkBillDetail workbilldetail = new WorkBillDetail();
		String branchid = staff.getBranchId();
		String detailid = DateUtil.currentDate("yyMMdd") + branchid + roomService.getSequence("SEQ_NEW_WORKBILLDETAIL");
		workbilldetail.setDetailId(detailid);
		workbilldetail.setBranchId(branchid);
		workbilldetail.setWorkbillId(workbillid);
		workbilldetail.setProjectId(projectid);// gai
		workbilldetail.setProjectName(project);
		workbilldetail.setDescribe("入账");
		if (projectid.startsWith("2")) {
			if ("2001".equals(projectid)) {
				workbilldetail.setPay(-Double.valueOf(amount));
			} else {
				workbilldetail.setPay(Double.valueOf(amount));
			}
			workbilldetail.setCost(0.00);
		} else {
			workbilldetail.setCost(Double.valueOf(amount));
			workbilldetail.setPay(0.00);
		}
		if ("2001".equals(projectid)) {
			workbilldetail.setPayment("1");
		} else if ("2002".equals(projectid)) {
			workbilldetail.setPayment("1");
		} else if ("2003".equals(projectid)) {
			workbilldetail.setPayment("2");
		} else {
			workbilldetail.setPayment("0");
		}
		workbilldetail.setStatus("1");
		workbilldetail.setRemark(remark);
		workbilldetail.setRecordUser(staff.getStaffId());
		//workbilldetail.setCashBox(loginGetShift.getCashbox());
		//workbilldetail.setShift(loginGetShift.getShiftId());

		Recording recording = new Recording();
		String recordId = DateUtil.currentDate("yyMMdd") + branchid + roomService.getSequence("SEQ_NEW_RECORDING");
		recording.setRecordId(recordId);
		recording.setBranchId(branchid);
		recording.setCheckId(workbillid);
		recording.setRecordType("2");
		recording.setProjectId(projectid);
		recording.setFee(Double.valueOf(amount));
		recording.setRecordUser(staff.getStaffId());

		try {
			roomService.save(workbilldetail);
			roomService.save(recording);
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.SUCESS, "入账成功!"));
		} catch (Exception e) {
			e.printStackTrace();
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, "入账失败!"));
		}
	}

	@RequestMapping("/loadCustomerData.do")
	public void loadCustomerData(HttpServletRequest request, HttpServletResponse response, String checkid,
			String checkusername) {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String branchId = staff.getBranchId();
		String strcheckid = "";
		Check check = (Check) roomService.findOneByProperties(Check.class, "checkId", checkid);
		String roomid = check.getRoomId();
		RoomMapping roommapping = (RoomMapping) roomService.findOneByProperties(RoomMapping.class, "relaRoomid", roomid);
		List<?> listroommapping = roomService.findByProperties(RoomMapping.class, "roomId", roommapping.getRoomId());
		for (Object object : listroommapping) {
			RoomMapping roommapp = (RoomMapping) object;
			if (!roommapp.getRelaRoomid().equals(roomid)){
				strcheckid = strcheckid + roommapp.getRelaRoomid() + ",";
			}
		}
		strcheckid = strcheckid.substring(0, strcheckid.length() - 1);
		List<?> result = roomService.loadcustomer(strcheckid, branchId);
		JSONUtil.responseJSON(response, result);
	}

	// 房单日志
	@RequestMapping("/showLogData.do")
	public ModelAndView loadLogData(HttpServletRequest request, HttpServletResponse response, String type,
			String checkid) throws Exception {
		ModelAndView mv = new ModelAndView();
		// LoginUser loginUser =
		// (LoginUser)request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Pagination pagination = SqlBuilder.buildPagination(request);
		List<?> result = roomService.getLog(checkid, type, pagination);
		mv.addObject("checkid", checkid);
		mv.addObject("type", type);
		mv.addObject("result", result);
		mv.addObject("pagination", pagination);
		mv.setViewName("page/ipmspage/room_statistics/logData");
		return mv;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/loadOrderPromptData.do")
	public ModelAndView loadOrderPrompt(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		Pagination pagination = SqlBuilder.buildPagination(request);
		pagination.setRows(13);
		String checkid = request.getParameter("checkid");
		Order orderdata = (Order) roomService.findOneByProperties(Order.class, "orderId", checkid);
		String sql = "";
		if (orderdata.getStatus().equals(CommonConstants.OrderStatus.NewOrder) || 
				orderdata.getStatus().equals(CommonConstants.OrderStatus.Cancel) || 
				orderdata.getStatus().equals(CommonConstants.OrderStatus.Absent)) {
			sql = "queryordertips";
			List<Tips> result = roomService.findBySQLWithPagination(sql, new String[] { checkid }, pagination, true);
			mv.addObject("result", result);
			mv.addObject("checkid", checkid);
			mv.addObject("pagination", pagination);
			mv.setViewName("page/ipmspage/order/orderPrompt");
			return mv;
		} else {
			sql = "loadorderprompt";
			List<Tips> result = roomService.findBySQLWithPagination(sql, new String[] { checkid }, pagination, true);
			mv.addObject("result", result);
			mv.addObject("checkid", checkid);
			mv.addObject("pagination", pagination);
			mv.setViewName("page/ipmspage/order/prompt");
			return mv;
		}
	}

	@RequestMapping("/loadtransferorderData.do")
	public void loadtransferorderData(HttpServletRequest request, HttpServletResponse response, String checkid) {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String branchId = staff.getBranchId();
		Check check = (Check) roomService.findOneByProperties(Check.class, "checkId", checkid);
		Order order = (Order) roomService.findOneByProperties(Order.class, "orderId", checkid);
		String sql = "querytransferorder";
		
		List<?> result = roomService.findBySQL(sql, new String[] { branchId, order.getOrderUser(), order.getSource(),check.getCheckId() }, true);
		JSONUtil.responseJSON(response, result);
	}

	@RequestMapping("/transferbill.do")
	public void transferbill(HttpServletRequest request, HttpServletResponse response, String checkid,
			String targetcheckid, String amount) {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String branchId = staff.getBranchId();
		
		CheckAllAmount checkallamount  = RoomUtil.getCheckamount(request, checkid);
		
		if (checkallamount.getUncheckoutPay() < Double.parseDouble(amount)) {
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, "账户金额不足!"));
		} else {
			Bill bill = new Bill();
			Check check = (Check) roomService.findOneByProperties(Check.class, "checkId", checkid);
			String branchid = check.getBranchId();
			String billId = DateUtil.currentDate("yyMMdd") + branchid + roomService.getSequence("SEQ_NEW_BILL");
			bill.setBillId(billId);
			bill.setBranchId(branchid);
			bill.setCheckId(checkid);
			bill.setDescribe("内部交易");
			RoomUtil.setBillPayment(bill, CommonConstants.BillProject.AccountTransfer, -Double.valueOf(amount));
			bill.setStatus(CommonConstants.BillStatus.BillTranfered);
			bill.setRecordUser(staff.getStaffId());

			Bill targetbill = new Bill();
			Check targetcheck = (Check) roomService.findOneByProperties(Check.class, "checkId", targetcheckid);
			String targetbranchid = targetcheck.getBranchId();
			String targetbillId = DateUtil.currentDate("yyMMdd") + branchid + roomService.getSequence("SEQ_NEW_BILL");
			targetbill.setBillId(targetbillId);
			targetbill.setBranchId(targetbranchid);
			targetbill.setCheckId(targetcheckid);
			targetbill.setDescribe("内部交易");
			RoomUtil.setBillPayment(targetbill, CommonConstants.BillProject.AccountTransfer, Double.valueOf(amount));
			targetbill.setStatus(CommonConstants.BillStatus.BillNormal);
			targetbill.setRecordUser(staff.getStaffId());

			Transfer transfer = new Transfer();
			String transferId = DateUtil.currentDate("yyMMdd") + branchId + roomService.getSequence("SEQ_NEW_TRANSFER");
			transfer.setTransferId(transferId);
			transfer.setBranchId(branchId);
			transfer.setLastCheckid(checkid);
			transfer.setCurrCheckid(targetcheckid);
			transfer.setTransferFee(Double.valueOf(amount));
			transfer.setRecordUser(staff.getStaffId());

			try {
				roomService.save(bill);
				roomService.save(targetbill);
				roomService.save(transfer);
				JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.SUCESS, "转账成功!"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

//	@RequestMapping("/autoautotransferbill.do")
//	public void autoautotransferbill(HttpServletRequest request, HttpServletResponse response, String checkid) {
//		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
//		Staff staff = loginuser.getStaff();
//		String branchId = staff.getBranchId();
//		Check check = (Check) roomService.findOneByProperties(Check.class, "checkId", checkid);
//		String hostroomid = check.getRoomId();
//		List<Map<String, Object>> listroommapping = roomService.findallroommapping(hostroomid);
//		if (listroommapping.size() <= 0) {
//			JSONUtil.responseJSON(response, new AjaxResult(2, "无关联房!"));
//			return;
//		}
//
//		List<Map<String, Double>> listmapsubpaycost = new ArrayList<Map<String, Double>>();
//		boolean mincheckamountflag = true;
//		double totalfee = 0; // 循环得出被关联方的总账面价格
//		for (Map<String, Object> map : listroommapping) {
//			String relaroomid = (String) map.get("RELAROOMID");
//			Check relacheck = (Check) roomService.findOneByProperties(Check.class, "roomId", relaroomid, "status", "1",
//					"branchId", branchId);
//			//CheckAllAmount checkallamount = getCheckamount(request, response, relacheck.getCheckId());
//			double subpaycost = 0.0;//checkallamount.getPay() - checkallamount.getCost() - checkallamount.getSubPrice();
//			if (subpaycost >= 0) {// 当某个房间结算大于消费，便不用平账
//				subpaycost = 0;
//			} else {
//				mincheckamountflag = false;
//				Map<String, Double> mapsubsubpaycost = new HashMap<String, Double>();
//				mapsubsubpaycost.put(relacheck.getCheckId(), subpaycost);
//				listmapsubpaycost.add(mapsubsubpaycost);
//				totalfee = totalfee + subpaycost;
//			}
//		}
//
//		CheckAllAmount hostcheckallamount = RoomUtil.getCheckamount(request, checkid);
//		if (hostcheckallamount.getPay() <= hostcheckallamount.getCost() + hostcheckallamount.getSubPrice() - totalfee) {
//			JSONUtil.responseJSON(response, new AjaxResult(1, "主账金额不足!"));
//			return;
//		}
//
//		if (mincheckamountflag) {
//			JSONUtil.responseJSON(response, new AjaxResult(1, "无需自动转账!"));
//			return;
//		}
//		for (Map<String, Double> mapsubsubpaycost : listmapsubpaycost) {
//			Set<String> set = mapsubsubpaycost.keySet();
//			for (String subpaycostcheckid : set) {
//				double subpaycost = mapsubsubpaycost.get(subpaycostcheckid);
//				Bill bill = new Bill();
//				String branchid = staff.getBranchId();
//				String billId = DateUtil.currentDate("yyMMdd") + branchid + roomService.getSequence("SEQ_NEW_BILL");
//				bill.setBillId(billId);
//				bill.setBranchId(branchid);
//				bill.setCheckId(subpaycostcheckid);
//				bill.setProjectId("2501");
//				bill.setProjectName("内部交易");
//				bill.setDescribe("自动转账");
//				bill.setPay(-subpaycost);
//				bill.setCost(0.00);
//				bill.setPayment("1");
//				bill.setStatus("1");
//				bill.setRecordUser(staff.getStaffId());
//
//				Bill targetbill = new Bill();
//				String targetbranchid = staff.getBranchId();
//				String targetbillId = DateUtil.currentDate("yyMMdd") + branchid
//						+ roomService.getSequence("SEQ_NEW_BILL");
//				targetbill.setBillId(targetbillId);
//				targetbill.setBranchId(targetbranchid);
//				targetbill.setCheckId(checkid);
//				targetbill.setProjectId("2501");// gai
//				targetbill.setProjectName("内部交易");
//				targetbill.setDescribe("自动转账");
//				targetbill.setPay(subpaycost);
//				targetbill.setCost(0.00);
//				targetbill.setPayment("1");// gai
//				targetbill.setStatus("3");
//				targetbill.setRecordUser(staff.getStaffId());
//
//				Transfer transfer = new Transfer();
//				String transferId = DateUtil.currentDate("yyMMdd") + branchId
//						+ roomService.getSequence("SEQ_NEW_TRANSFER");
//				transfer.setTransferId(transferId);
//				transfer.setBranchId(branchId);
//				transfer.setLastCheckid(checkid);
//				transfer.setCurrCheckid(subpaycostcheckid);
//				transfer.setTransferFee(-subpaycost);
//				transfer.setRecordUser(staff.getStaffId());
//
//				try {
//					roomService.save(bill);
//					roomService.save(targetbill);
//					roomService.save(transfer);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//
//			}
//		}
//		JSONUtil.responseJSON(response, new AjaxResult(0, "自动转账成功!"));
//
//	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/autoautotransferbill.do")
	public void autoautotransferbill(HttpServletRequest request, HttpServletResponse response, String checkid) {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String branchId = staff.getBranchId();
		Check check = (Check) roomService.findOneByProperties(Check.class, "checkId", checkid);
		String hostroomid = check.getRoomId();
		
		RoomMapping roommapping = (RoomMapping) roomService.findOneByProperties(RoomMapping.class, 
				"relaRoomid", hostroomid);
		if (roommapping == null) {
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, "无关联房!"));
			return;
		}
		
		boolean ischeckonfalg = false;     //判断房单是否有效
		boolean mincheckamountflag = true; //判断是否有需要转账的账单，一旦有则变为false，如果没有则始终是true
		double totalfee = 0;
		double totalneedpayfee = 0;
		double minamounttemp = 0;		   //数字越小，越代表有支付能力
		Check mincheckpay = null;          //最大的房单容器,
		Map<String, Double> needpaybill = new HashMap<String, Double>();//得到金额为负数的账单
		List<RoomMapping> listroommapping11 = roomService.findByProperties(RoomMapping.class, 
				"roomId", roommapping.getRoomId());
		for (RoomMapping roomMapping2 : listroommapping11) {
			Check roommapcheck = (Check) roomService.findOneByProperties(Check.class, 
					"roomId", roomMapping2.getRelaRoomid(), "status", CommonConstants.CheckStatus.CheckOn);
			if(roommapcheck == null){
				ischeckonfalg = true;
				break;
			}
			CheckAllAmount checkallamount = RoomUtil.getCheckamount(request, roommapcheck.getCheckId());
			double amount = checkallamount.getShouldPay();
			totalfee = totalfee + amount;
			if(amount < minamounttemp){ // 判断那个房单的应付金额最小，就是最有能力的支付那个
				mincheckpay = roommapcheck;
				minamounttemp = amount;
			}
			if(amount > 0){//过滤掉不需要转账的用户，amount > 0说明需要交付公司金额
				mincheckamountflag = false;
				totalneedpayfee = totalneedpayfee + amount;
				needpaybill.put(roommapcheck.getCheckId(), amount);
			}
		}
		if(ischeckonfalg){
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, "存在非在住用户!"));
			return;
		}
		if (mincheckamountflag) {
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, "无需自动转账!"));
			return;
		}
		if( minamounttemp == 0){
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, "无支付能力用户!"));
			return;
		}
		if(totalneedpayfee + minamounttemp > 0){
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, "金额不足!"));
			return;
		}
		Set<String> set = needpaybill.keySet();
		for (String subpaycostcheckid : set) {
			double subpaycost = needpaybill.get(subpaycostcheckid);
			Bill bill = new Bill();
			String branchid = staff.getBranchId();
			String billId = DateUtil.currentDate("yyMMdd") + branchid + roomService.getSequence("SEQ_NEW_BILL");
			bill.setBillId(billId);
			bill.setBranchId(branchid);
			bill.setCheckId(subpaycostcheckid);
			bill.setDescribe("自动转账");
			RoomUtil.setBillPayment(bill, CommonConstants.BillProject.InsiderTrading, subpaycost);
			bill.setStatus(CommonConstants.BillStatus.BillNormal);
			bill.setRecordUser(staff.getStaffId());

			Bill targetbill = new Bill();
			String targetbranchid = staff.getBranchId();
			String targetbillId = DateUtil.currentDate("yyMMdd") + branchid
					+ roomService.getSequence("SEQ_NEW_BILL");
			targetbill.setBillId(targetbillId);
			targetbill.setBranchId(targetbranchid);
			targetbill.setCheckId(mincheckpay.getCheckId());
			targetbill.setDescribe("自动转账");
			RoomUtil.setBillPayment(targetbill, CommonConstants.BillProject.InsiderTrading, -subpaycost);
			targetbill.setStatus(CommonConstants.BillStatus.BillTranfered);
			targetbill.setRecordUser(staff.getStaffId());

			Transfer transfer = new Transfer();
			String transferId = DateUtil.currentDate("yyMMdd") + branchId
					+ roomService.getSequence("SEQ_NEW_TRANSFER");
			transfer.setTransferId(transferId);
			transfer.setBranchId(branchId);
			transfer.setLastCheckid(mincheckpay.getCheckId());
			transfer.setCurrCheckid(subpaycostcheckid);
			transfer.setTransferFee(subpaycost);
			transfer.setRecordUser(staff.getStaffId());

			try {
				roomService.save(bill);
				roomService.save(targetbill);
				roomService.save(transfer);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		JSONUtil.responseJSON(response, new AjaxResult(0, "自动转账成功!"));
	}



	@RequestMapping("/submitupdateroomprice.do")
	public void submitupdateroomprice(HttpServletRequest request, HttpServletResponse response) {

	}
	
	@RequestMapping("/getBranchId.do")
	public void getBranchId(HttpServletRequest request, HttpServletResponse response, String tag){
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		Branch branch = (Branch) roomService.findOneByProperties(Branch.class, "branchId", branchId);
		String branchName = branch.getBranchName();
		String branchType = branch.getBranchType();
		if("BRANCHNAME".equals(tag)){
			JSONUtil.responseJSON(response, new AjaxResult(1, "", branchName));	
		}else if("BRANCHTYPE".equals(tag)){
			JSONUtil.responseJSON(response, new AjaxResult(2, "", branchType));
		}
	}
	
	

	

	

	

	

	

	
	//当前房态统计
	@SuppressWarnings("unchecked")
	@RequestMapping("currentRoom.do")
	public ModelAndView currentRoom(HttpServletRequest request, HttpServletResponse response, String madate)
			throws Exception {
		ModelAndView mv = new ModelAndView();
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		List<Object> list = new ArrayList<Object>();
		Pagination pagination = SqlBuilder.buildPagination(request);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		SimpleDateFormat sdfs = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		if (madate == null) {
			madate = sdf.format(new Date());
		}
		String mdate = madate;
		Date date = sdf.parse(mdate);
		Date nowdate = new Date();

		Calendar querytime = Calendar.getInstance();
		Calendar calnowdate = Calendar.getInstance();
		querytime.setTime(date);
		calnowdate.setTime(nowdate);
		querytime.getTime();
		calnowdate.getTime();

		if (DateUtil.isSameDay(querytime, calnowdate)) {
			String sql = "querycurrentstatus";
			list = roomService.findBySQLWithPagination(sql, new String[] { branchId, branchId, branchId }, pagination, true);
		} else {
			DateUtil.setEndOfDay(querytime);
			list = (List<Object>) roomService.getRoomStatus(madate, sdfs.format(querytime.getTime()), branchId, pagination);
		}
		mv.addObject("list", list);
		mv.addObject("pagination", pagination);
		mv.addObject("madate", madate);
		mv.setViewName("page/ipmspage/room_statistics/current_room");
		return mv;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("queryMyCampaign.do")
	public ModelAndView queryMyCampaign(HttpServletRequest request, HttpServletResponse response, String madate)
			throws Exception {
		ModelAndView mv = new ModelAndView();
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		List<Campaign> campaign = (List<Campaign>) roomService.findBySQL("querycampaigndata",
				new String[] { branchId, branchId, branchId }, true);
		mv.addObject("list", campaign);
		mv.setViewName("page/ipmspage/room_statistics/pagecampaign");
		return mv;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("forwardRoom.do")
	public ModelAndView forwardRoom(HttpServletRequest request, HttpServletResponse response, String madate,
			String days, String rpId) throws Exception {
		ModelAndView mv = new ModelAndView();
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		Pagination pagination = SqlBuilder.buildPagination(request);
		List<Object> list = new ArrayList<Object>();
		List<Object> listdate = new ArrayList<Object>();
		List<Object> listroomtype = new ArrayList<Object>();
		Map<String, Object> newlistdata = new HashMap<String, Object>();
		Map<String, List<Object>> listinfo = new HashMap<String, List<Object>>();
		int day = Integer.parseInt(days);
		String roomType = null;
		String sumNum = null;
		String alltypenum = null;
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		List<?> rt = roomService.findByProperties(RoomType.class, "theme", "1", "roomTypeKey.branchId", branchId);

		for (int x = 0; x < rt.size(); x++) {
			date = sdf.parse(madate);
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			Map<String, Object> newlist = new HashMap<String, Object>();

			for (int y = 0; y < day; y++) {
				String newdate = sdf.format(c.getTime());
				list = roomService.findBySQLWithPagination("queryforward", new String[] { branchId, branchId, branchId, newdate,
						 newdate, branchId, newdate, newdate, branchId, newdate, newdate, branchId }, pagination, true);
				c.add(Calendar.DAY_OF_MONTH, 1);
				if (x == 0) {
					listdate.add(newdate);
				}
				for (int a = 0; a < list.size(); a++) {
					roomType = ((Map<?, ?>) list.get(a)).get("ROOM_TYPE").toString();
					sumNum = ((Map<?, ?>) list.get(a)).get("SUMNUM").toString();
					alltypenum = ((Map<?, ?>) list.get(a)).get("TYPENUMS").toString();
					if (((RoomType) rt.get(x)).getRoomTypeKey().getRoomType().toString().equals(roomType)) {
						Map<String, Object> oneinfo = new HashMap<String, Object>();
						oneinfo.put(sumNum, alltypenum);
						newlist.put(newdate, oneinfo);
						//List<?> roomprice = roomService.getRoomPrice(((RoomType) rt.get(x)).getRoomType(), branchId, 
								//newdate, rpId);
						//newlist.put(((RoomType) rt.get(x)).getRoomName(),roomprice);
						}
				}
				newlistdata.put(((RoomType) rt.get(x)).getRoomName().toString(), newlist);
			}
			listroomtype.add(((RoomType) rt.get(x)).getRoomName());
		}
		JSONUtil.fromObject(newlistdata);
		mv.addObject("listdate", listdate);
		mv.addObject("listinfo", listinfo);
		mv.addObject("listroomtype", listroomtype);
		mv.addObject("newlistdata", newlistdata);
		mv.addObject("pagination", pagination);
		mv.addObject("madate", madate);
		mv.addObject("days", days);
		mv.addObject("rpId", rpId);
		mv.setViewName("page/ipmspage/room_statistics/forward_room");
		return mv;
	}

	@RequestMapping("/checkoutbill.do")
	public void checkoutbill(HttpServletRequest request, HttpServletResponse response, CheckoutRoom checkoutroom) {
		if(checkoutroom.getAmount() == null || "".equals(checkoutroom.getAmount())){
			checkoutroom.setAmount("0");
		}
		String checkid = checkoutroom.getCheckId();
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String branchId = staff.getBranchId();
		String recordUser = staff.getStaffId();
		Check check = (Check) roomService.findOneByProperties(Check.class, "checkId", checkid);
		String roomid = check.getRoomId();
		SysParam sysparam = (SysParam) roomService.findOneByProperties(SysParam.class, 
				"paramType", "PROJECT", "content", checkoutroom.getProjectid());
		if(sysparam != null){
			String project = sysparam.getParamName();
			checkoutroom.setProject(project);
		}
		CheckAllAmount checkallamount = RoomUtil.getCheckamount(request, checkid);
		List<?> listbillnorm = roomService.findByProperties(Bill.class, "checkId", checkid,
				"status", CommonConstants.BillStatus.BillNormal);
		List<?> listbilltranfered = roomService.findByProperties(Bill.class, "checkId", checkid,
				"status", CommonConstants.BillStatus.BillTranfered);
		List<Object> listbill = new ArrayList<Object>();
		listbill.addAll(listbillnorm);
		listbill.addAll(listbilltranfered);
		try{
			if(listbill != null && !listbill.isEmpty()){
				for (Object object : listbill) {
					Bill bill = (Bill) object;
					bill.setStatus(CommonConstants.BillStatus.BillCheckout);
					roomService.update(bill);
				}
			}
			checkoutbilladdbillnew(check, recordUser, checkoutroom);
			String status = RoomUtil.checkstatus(checkallamount, checkoutroom);
			roomService.updatestatus(checkid, status, checkallamount.getCheckoutPay(), 
					checkallamount.getCheckoutCost(), new Date());
			roomService.upateroomstatus(branchId, roomid, CommonConstants.RoomStatus.RoomDirty);
			roomService.updateorderstatus(checkid, CommonConstants.OrderStatus.CheckOff);
			RoomUtil.cancelRoommap(roomid);
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.SUCESS, "结算成功!"));
		}
		catch(Exception e){
			e.printStackTrace();
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, "结算失败 !"));
		}
	}
	
	@RequestMapping("/partcheckoutbill.do")
	public void partcheckoutbill(HttpServletRequest request, HttpServletResponse response, CheckoutRoom checkoutroom,
			String strbillids) {
		if(checkoutroom.getAmount() == null || "".equals(checkoutroom.getAmount())){
			checkoutroom.setAmount("0");
		}
		String checkid = checkoutroom.getCheckId();
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String recordUser = staff.getStaffId();
		Check check = (Check) roomService.findOneByProperties(Check.class, "checkId", checkid);

		SysParam sysparam = (SysParam) roomService.findOneByProperties(SysParam.class, 
				"paramType", "PROJECT", "content", checkoutroom.getProjectid());
		if(sysparam != null){
			String project = sysparam.getParamName();
			checkoutroom.setProject(project);
		}
		String[] billids = strbillids.split(",");
		CheckAllAmount checkallamount = RoomUtil.getCheckamount(request, checkid);
		try{
			for (String billid : billids) {
				Bill bill = (Bill) roomService.findOneByProperties(Bill.class, "billId", billid);
				bill.setStatus(CommonConstants.BillStatus.BillCheckout);
				roomService.update(bill);
			}
			checkoutroom.setSubprice("0");//部分结算则将所有罚金置为零
			checkoutbilladdbillnew(check, recordUser, checkoutroom);
			//String status = RoomUtil.checkstatus(checkallamount, checkoutroom);
			//roomService.updatestatus(checkid, status, checkallamount.getCheckoutPay(), 
			//		checkallamount.getCheckoutCost(), new Date());
			//roomService.upateroomstatus(branchId, roomid, CommonConstants.RoomStatus.RoomDirty);
			//roomService.updateorderstatus(checkid, CommonConstants.OrderStatus.CheckOff);
			//RoomUtil.cancelRoommap(roomid);
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.SUCESS, "部分结算成功!"));
		} catch (Exception e) {
			e.printStackTrace();
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, "部分结算失败!"));
		}
	}
	
	@RequestMapping("/allcheckoutbill.do")
	public void allcheckoutbill(HttpServletRequest request, HttpServletResponse response, CheckoutRoom checkoutroom) {
		//手输金额为null或者“”，置零
		if(checkoutroom.getAmount() == null || "".equals(checkoutroom.getAmount())){
			checkoutroom.setAmount("0");
		}
		String checkid = checkoutroom.getCheckId();
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String branchId = staff.getBranchId();
		Check check = (Check) roomService.findOneByProperties(Check.class, "checkId", checkid);
		String roomid = check.getRoomId();
		RoomMapping roommapping = (RoomMapping) roomService.findOneByProperties(RoomMapping.class, "relaRoomid", roomid);
		if(roommapping == null){
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, "非关联房用户!"));
			return;
		}
		List<?> listroommapping1 = roomService.findByProperties(RoomMapping.class, "roomId", roommapping.getRoomId());
		BigDecimal allamount = new BigDecimal(0);
		BigDecimal zerodec = new BigDecimal(0);
		if(CommonConstants.BillProject.CashOutlay.equals(checkoutroom.getProjectid())){
			allamount = allamount.add(new BigDecimal("-" + checkoutroom.getAmount()));
		} else{
			allamount = allamount.add(new BigDecimal(checkoutroom.getAmount()));
		}
		for (Object object : listroommapping1) {
			RoomMapping roomMapping2 = (RoomMapping) object;
			String relaroomid = roomMapping2.getRelaRoomid();
			Check relacheck = (Check) roomService.findOneByProperties(Check.class, "roomId", relaroomid, "status",
					CommonConstants.CheckStatus.CheckOn, "branchId", branchId);
			CheckAllAmount checkallamount = RoomUtil.getCheckamount(request, relacheck.getCheckId());
			double subpaycost = checkallamount.getShouldPay();
			if (subpaycost < 0) {// 不同的房间结算效果不一样
				allamount = allamount.add(new BigDecimal(-subpaycost));
			} else if (subpaycost == 0) {
				
			} else if (subpaycost > 0) {
				allamount = allamount.add(new BigDecimal(-subpaycost));
			}
		}
		if (allamount.setScale(4, RoundingMode.HALF_UP).compareTo(zerodec) < 0 ){
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, "金额多出" + allamount.setScale(2, RoundingMode.HALF_UP) + "元!"));
			return;
		} else if(allamount.setScale(4, RoundingMode.HALF_UP).compareTo(zerodec) > 0 ){
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, "金额缺少" + allamount.setScale(2, RoundingMode.HALF_UP) + "元!"));
			return;
		}
		
		try{
			for (Object object : listroommapping1) {
				RoomMapping roomMapping2 = (RoomMapping) object;
				String relaroomid = roomMapping2.getRelaRoomid();
				Check relacheck = (Check) roomService.findOneByProperties(Check.class, "roomId", relaroomid, "status",
						CommonConstants.CheckStatus.CheckOn, "branchId", branchId);
				CheckAllAmount checkallamount = RoomUtil.getCheckamount(request, relacheck.getCheckId());
				double subpaycost = checkallamount.getShouldPay();
				if (subpaycost < 0) {// 不同的房间结算效果不一样
					CheckoutRoom relacheckoutroom = new CheckoutRoom();
					relacheckoutroom.setAmount("" + (-subpaycost));
					relacheckoutroom.setProjectid(CommonConstants.BillProject.CashOutlay);
					relacheckoutroom.setSubprice("0");
					checkoutbilladdbillnew(relacheck, staff.getStaffId(), relacheckoutroom);
				} else if (subpaycost == 0) {
					
				} else if (subpaycost > 0) {
					CheckoutRoom relacheckoutroom = new CheckoutRoom();
					relacheckoutroom.setAmount("" + subpaycost);
					relacheckoutroom.setProjectid(checkoutroom.getProjectid());
					relacheckoutroom.setSubprice("" + subpaycost);
					checkoutbilladdbillnew(relacheck, staff.getStaffId(), relacheckoutroom);
				}
			}
			
			for (Object object : listroommapping1) {
				RoomMapping roomMapping2 = (RoomMapping) object;
				String relaroomid = roomMapping2.getRelaRoomid();
				Check relacheck = (Check) roomService.findOneByProperties(Check.class, "roomId", relaroomid, "status",
						CommonConstants.CheckStatus.CheckOn, "branchId", branchId);
				CheckAllAmount checkallamount = RoomUtil.getCheckamount(request, relacheck.getCheckId());
				String relacheckid = relacheck.getCheckId();
				
				List<?> listbillnorm = roomService.findByProperties(Bill.class, "checkId", relacheckid,
						"status", CommonConstants.BillStatus.BillNormal);
				List<?> listbilltranfered = roomService.findByProperties(Bill.class, "checkId", relacheckid,
						"status", CommonConstants.BillStatus.BillTranfered);
				List<Object> listbill = new ArrayList<Object>();
				listbill.addAll(listbillnorm);
				listbill.addAll(listbilltranfered);
				if(listbill != null && !listbill.isEmpty()){
					for (Object objectbill : listbill) {
						Bill bill = (Bill) objectbill;
						bill.setStatus(CommonConstants.BillStatus.BillCheckout);
						roomService.update(bill);
					}
				}
				
				roomService.updatestatus(relacheckid, CommonConstants.CheckStatus.CheckOff, 
						checkallamount.getPay() + checkallamount.getSubPrice(), checkallamount.getCost(), new Date());
				roomService.upateroomstatus(branchId, relaroomid, CommonConstants.RoomStatus.RoomDirty);
				roomService.updateorderstatus(relacheckid, CommonConstants.OrderStatus.CheckOff);
			}
			roomService.deleteroommapping(roommapping.getRoomId());
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.SUCESS, "集体结算成功!"));
		}
		catch(Exception e){
			e.printStackTrace();
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, "集体结算失败!"));
		}
	}

	public void checkoutbilladdbillnew(Check check, String recordUser,CheckoutRoom checkoutroom) {
		String branchId = check.getBranchId();
		String checkid = check.getCheckId();
		if (!"0".equals(checkoutroom.getSubprice())) {
			Bill costbill = new Bill();
			String costbillId = DateUtil.currentDate("yyMMdd") + branchId + roomService.getSequence("SEQ_NEW_BILL");
			costbill.setBillId(costbillId);
			costbill.setBranchId(branchId);
			costbill.setCheckId(checkid);
			costbill.setDescribe("手动日结");
			RoomUtil.setBillPayment(costbill, CommonConstants.BillProject.CheckRoomprice, 
					Double.valueOf(checkoutroom.getSubprice()));
			costbill.setStatus(CommonConstants.BillStatus.BillCheckout);
			costbill.setRecordUser(recordUser);
			roomService.save(costbill);
		}

		if (!("0".equals(checkoutroom.getAmount())
				|| checkoutroom.getProjectid() == null || "".equals(checkoutroom.getProjectid()))) {
			Bill bill = new Bill();
			String billId = DateUtil.currentDate("yyMMdd") + branchId + roomService.getSequence("SEQ_NEW_BILL");
			bill.setBillId(billId);
			bill.setBranchId(branchId);
			bill.setCheckId(checkid);
			bill.setDescribe("结账");
			RoomUtil.setBillPayment(bill, checkoutroom.getProjectid(), 
					Double.valueOf(checkoutroom.getAmount()));
			bill.setStatus(CommonConstants.BillStatus.BillCheckout);
			bill.setRecordUser(recordUser);
			roomService.save(bill);
		}
	}

	@RequestMapping("/checkoutworkbill.do")
	public void checkoutworkbill(HttpServletRequest request, HttpServletResponse response, String workbillid) {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		// String branchId = staff.getStaffId();
		String recordUser = staff.getStaffId();
		WorkBill workbill = (WorkBill) roomService.findOneByProperties(WorkBill.class, "workbillId", workbillid);
		workbill.setFinalTime(new Date());
		workbill.setFinalUser(recordUser);
		workbill.setStatus("2");
		try {
			roomService.save(workbill);
			JSONUtil.responseJSON(response, new AjaxResult(1, "结账成功!"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/loadpayandcost.do")
	public void loadpayandcost(HttpServletRequest request, HttpServletResponse response, String checkid,
			String strbillids) {
		CheckAllAmount checkallamount = RoomUtil.getCheckamount(request, checkid, strbillids);
	    JSONUtil.responseJSON(response, checkallamount);
	}
	
	@RequestMapping("/loadallroompayandcost.do")
	public void loadallroompayandcost(HttpServletRequest request, HttpServletResponse response, String checkid) {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String branchId = staff.getBranchId();
		Check check = (Check) roomService.findOneByProperties(Check.class, "checkId", checkid);
		String roomid = check.getRoomId();
		RoomMapping roommapping = (RoomMapping) roomService.findOneByProperties(RoomMapping.class, "relaRoomid", roomid);
		if(roommapping == null){
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, "非关联房用户!"));
			return;
		}
		List<?> listroommapping1 = roomService.findByProperties(RoomMapping.class, "roomId", roommapping.getRoomId());
		CheckAllAmount personalcheckallamount = RoomUtil.getCheckamount(request, checkid);
		CheckAllAmount sumcheckallamount = new CheckAllAmount();
		sumcheckallamount.setShouldPay(0.0);
		sumcheckallamount.setSubPrice(0.0);
		sumcheckallamount.setRoomPrice(0.0);
		sumcheckallamount.setSubpriceType(personalcheckallamount.getSubpriceType());
		sumcheckallamount.setCheckStatus(personalcheckallamount.getCheckStatus());
		sumcheckallamount.setUncheckoutCost(0.0);
		sumcheckallamount.setUncheckoutPay(0.0);
		sumcheckallamount.setTtv(0.0);
		
		for (Object object : listroommapping1) {
			RoomMapping roomMapping2 = (RoomMapping) object;
			String relaroomid = roomMapping2.getRelaRoomid();
			Check relacheck = (Check) roomService.findOneByProperties(Check.class, "roomId", relaroomid, "status",
					CommonConstants.CheckStatus.CheckOn, "branchId", branchId);
			CheckAllAmount checkallamount = RoomUtil.getCheckamount(request, relacheck.getCheckId());
			sumcheckallamount.setShouldPay(sumcheckallamount.getShouldPay() + checkallamount.getShouldPay());
			sumcheckallamount.setSubPrice(sumcheckallamount.getSubPrice() + checkallamount.getSubPrice());
			sumcheckallamount.setRoomPrice(sumcheckallamount.getRoomPrice() + checkallamount.getRoomPrice());
			sumcheckallamount.setUncheckoutCost(sumcheckallamount.getUncheckoutCost() + checkallamount.getUncheckoutCost());
			sumcheckallamount.setUncheckoutPay(sumcheckallamount.getUncheckoutPay() + checkallamount.getUncheckoutPay());
			sumcheckallamount.setTtv(sumcheckallamount.getTtv() + 1);
		}
		
	    JSONUtil.responseJSON(response, sumcheckallamount);
	}

	@RequestMapping("/checkroompayoff.do")
	public void checkroompayoff(HttpServletRequest request, HttpServletResponse response, String checkid, String status) {
		Check check = (Check) roomService.findOneByProperties(Check.class, "checkId", checkid);
		if (status.equalsIgnoreCase(check.getStatus())) {
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, "当前已结算!"));
		} else {
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.SUCESS, "可以结算!"));
		}
	}
	
	@RequestMapping("/checkrollback.do")
	public void checkrollback(HttpServletRequest request, HttpServletResponse response, String checkid) {
		Check check = (Check) roomService.findOneByProperties(Check.class, "checkId", checkid);
		if(check != null){
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.SUCESS, "状态!", check.getStatus()));
		}else {
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, "无房单!"));
		}
	}
	
	@RequestMapping("/recheckin.do")
	public void recheckin(HttpServletRequest request, HttpServletResponse response, String checkid, String checkOuttime) {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String branchId = staff.getBranchId();
		Check check = (Check) roomService.findOneByProperties(Check.class, "checkId", checkid);
		if(check != null){
			Room room = (Room) roomService.findOneByProperties(Room.class, "roomKey.branchId", branchId, 
					"roomKey.roomId", check.getRoomId());
			String roomstatus = room.getStatus();
			if( CommonConstants.RoomStatus.RoomNull.equals(roomstatus) 
					|| CommonConstants.RoomStatus.RoomDirty.equalsIgnoreCase(roomstatus)){
				
				Date checkoutdate = DateUtil.s2d(checkOuttime + " 12:00", "yyyy/MM/dd HH:mm");
				RecheckIn recheckin = new RecheckIn();
				String recheckinId = DateUtil.currentDate("yyMMdd") + 
					branchId + roomService.getSequence("SEQ_NEW_RECHECKIN");
				recheckin.setBranchId(branchId);
				recheckin.setRecheckinId(recheckinId);
				recheckin.setCheckId(checkid);
				recheckin.setRecheckinDate(new Date());
				recheckin.setRecheckoutDate(checkoutdate);
				recheckin.setRecordUser(staff.getStaffId());
				List<?>	listbillfine = roomService.findByProperties(Bill.class, "checkId", checkid, "projectId", CommonConstants.BillProject.Fine);
				List<?>	listbillcheckroomprice = roomService.findByProperties(Bill.class, "checkId", checkid, "projectId", CommonConstants.BillProject.CheckRoomprice);
				List<Bill> listbill = new ArrayList<Bill>();
				for (Object object : listbillfine) {
					Bill bill = (Bill) object;
					if(bill == null)
						continue;
					listbill.add(bill);
				}
				for (Object object : listbillcheckroomprice) {
					Bill bill = (Bill) object;
					if(bill == null)
						continue;
					listbill.add(bill);
				}
				check.setStatus(CommonConstants.CheckStatus.CheckOn);
				check.setCheckoutTime(checkoutdate);
				try{
					if(listbill != null && !listbill.isEmpty()){
						for (Bill bill : listbill) {
							boolean amountflag = true;
							Bill consumebill = new Bill();
							String billId = DateUtil.currentDate("yyMMdd") + staff.getBranchId()
									+ roomService.getSequence("SEQ_NEW_BILL");
							consumebill.setBillId(billId);
							consumebill.setBranchId(staff.getBranchId());
							consumebill.setCheckId(bill.getCheckId());
							consumebill.setDescribe(bill.getDescribe());
							consumebill.setPayment(bill.getPayment());
							consumebill.setProjectId(bill.getProjectId());
							consumebill.setProjectName(bill.getProjectName());
							consumebill.setRecordUser(staff.getStaffId());
							consumebill.setRemark(bill.getBillId());
							consumebill.setStatus(CommonConstants.BillStatus.BillCONSUME);
							
							String projectid = bill.getProjectId();
							
							if (projectid.startsWith("2")) {
								double price = bill.getPay();
								if (price == 0) {
									amountflag = false;
								}
								consumebill.setPay(-price);
								consumebill.setCost(0.00);
							} else {
								double price = bill.getCost();
								if (price == 0) {
									amountflag = false;
								}
								consumebill.setCost(-price);
								consumebill.setPay(0.00);
							}
							bill.setStatus(CommonConstants.BillStatus.BillCONSUME);
							if(amountflag){
								roomService.save(consumebill);
								roomService.update(bill);
							}
						}
					}
					roomService.save(recheckin);
					roomService.upateroomstatus(branchId, check.getRoomId(), 
							CommonConstants.RoomStatus.RoomChecked);
					roomService.update(check);
					JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.SUCESS, "重新入住成功!"));
				}catch (Exception e){
					e.printStackTrace();
				}
			}else if( CommonConstants.RoomStatus.RoomStop.equalsIgnoreCase(roomstatus) 
					|| CommonConstants.RoomStatus.RoomRepair.equalsIgnoreCase(roomstatus) ){
				JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, "房间已停售!"));
			}else if( CommonConstants.RoomStatus.RoomOrder.equals(roomstatus) 
					|| CommonConstants.RoomStatus.RoomChecked.equals(roomstatus)){
				JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, "房间预定入住中!"));
			}
		}else {
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, "无房单!"));
		}
	}
	
	@RequestMapping("/rollbackcheckout.do")
	public void rollbackcheckout(HttpServletRequest request, HttpServletResponse response, String checkid) {
		Check check = (Check) roomService.findOneByProperties(Check.class, "checkId", checkid);
		if(check != null){
			check.setStatus(CommonConstants.CheckStatus.CheckOffAndUnout);
			roomService.update(check);
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.SUCESS, "取消结账成功!"));
		}else {
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, "无房单!"));
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/loadpayandcostworkbill.do")
	public void loadpayandcostworkbill(HttpServletRequest request, HttpServletResponse response, String workbillid) {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String branchId = staff.getBranchId();
		String sqlcost = "totalcostfromwork";
		List<Map<String, BigDecimal>> costresult = roomService.findBySQL(sqlcost,
				new String[] { workbillid, branchId }, true);
		String sqlpay = "totalpayfromwork";
		List<Map<String, BigDecimal>> payresult = roomService.findBySQL(sqlpay, new String[] { workbillid, branchId },
				true);
		double pay = 0;
		double cost = 0;
		if (!costresult.isEmpty()) {
			pay = payresult.get(0).get("totalpay").doubleValue();
		}
		if (!payresult.isEmpty()) {
			cost = costresult.get(0).get("totalcost").doubleValue();
		}
		Map<String, Double> mappayandcost = new HashMap<String, Double>();
		mappayandcost.put("pay", pay);
		mappayandcost.put("cost", cost);
		JSONUtil.responseJSON(response, mappayandcost);
	}
	
	@RequestMapping("/consumptionhouseandaprtment.do")
	@RightMethodControl( rightType = RightConstants.RightType.CON_BILL )
	public void consumptionhouseandaprtment(HttpServletRequest request, HttpServletResponse response, 
			String strbillid, String offset, String checkid) {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Check check = (Check) roomService.findById(Check.class, checkid);
		Staff staff = loginuser.getStaff();
		String[] arrbill = strbillid.split(",");
		boolean errorbillflag = true;
		for (String billid : arrbill) {
			Bill bill = (Bill) roomService.findOneByProperties(Bill.class, "billId", billid);
			String projectid = bill.getProjectId();
			if (projectid.startsWith("2")) {
				double price = bill.getPay();
				if (price == 0) {
					errorbillflag = false;
				}
			} else {
				double price = bill.getCost();
				if (price == 0) {
					errorbillflag = false;
				}
				
			}
			double pricep = bill.getPay();
			double pricec = bill.getCost();
			if(pricec <= 0 && pricep <= 0){
				errorbillflag = false;
			}
			if(pricec > 0 && pricep > 0){
				errorbillflag = false;
			}
		}
		
		if (!errorbillflag) {
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, "选中了错误的冲减金额!"));
			return;
		}

		for (String billid : arrbill) {
			Bill consumebill = new Bill();
			Bill bill = (Bill) roomService.findOneByProperties(Bill.class, "billId", billid);
			String billId = DateUtil.currentDate("yyMMdd") + check.getBranchId()
					+ roomService.getSequence("SEQ_NEW_BILL");
			consumebill.setBillId(billId);
			consumebill.setBranchId(check.getBranchId());
			consumebill.setCheckId(bill.getCheckId());
			consumebill.setDescribe(bill.getDescribe());
			consumebill.setOffset(offset);
			consumebill.setPayment(bill.getPayment());
			consumebill.setProjectId(bill.getProjectId());
			consumebill.setProjectName(bill.getProjectName());
			consumebill.setRecordUser(staff.getStaffId());
			consumebill.setRemark(bill.getBillId());
			consumebill.setStatus(CommonConstants.BillStatus.BillCONSUME);

			String projectid = bill.getProjectId();
			// 记日志
			OffsetLog otlog = new OffsetLog();
			String logid = DateUtil.currentDate("yyMMdd") + check.getBranchId()
					+ roomService.getSequence("SEQ_OFFSETLOG_ID");
			otlog.setLogId(logid);
			otlog.setBranchId(check.getBranchId());
			otlog.setCheckId(bill.getCheckId());
			otlog.setLastBillId(bill.getBillId());
			otlog.setCurrBillId(consumebill.getBillId());
			otlog.setRecordUser(staff.getStaffId());
			otlog.setRecordTime(new Date());
			otlog.setOffsetType("1");
			otlog.setRemark(offset);
			
			double pricep = bill.getPay();
			if(pricep > 0){
				consumebill.setPay(-pricep);
				consumebill.setCost(0.00);
				otlog.setOffsetFee(bill.getPay());// 记冲减金额
			}
			double pricec = bill.getCost();
			if(pricec > 0){
				consumebill.setCost(-pricec);
				consumebill.setPay(0.00);
				otlog.setOffsetFee(bill.getCost()); // 记冲减金额					
			}
			bill.setStatus(CommonConstants.BillStatus.BillCONSUME);

			try {
				roomService.save(consumebill);
				roomService.save(bill);
				roomService.save(otlog);// 记冲减日志
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.SUCESS, "冲减金额成功!"));

	}


	@RequestMapping("/consumption.do")
	@RightMethodControl( rightType = RightConstants.RightType.CON_BILL )
	public void consumption(HttpServletRequest request, HttpServletResponse response, 
			String strbillid, String offset, String checkid) {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Check check = (Check) roomService.findById(Check.class, checkid);
		Staff staff = loginuser.getStaff();
		String[] arrbill = strbillid.split(",");
		boolean errorbillflag = true;
		for (String billid : arrbill) {
			Bill bill = (Bill) roomService.findOneByProperties(Bill.class, "billId", billid);
			String projectid = bill.getProjectId();
			if (projectid.startsWith("2")) {
				double price = bill.getPay();
				if (price == 0) {
					errorbillflag = false;
				}
			} else {
				double price = bill.getCost();
				if (price == 0) {
					errorbillflag = false;
				}
				
			}
		}

		if (!errorbillflag) {
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, "选中了错误的冲减金额!"));
			return;
		}

		for (String billid : arrbill) {
			Bill consumebill = new Bill();
			Bill bill = (Bill) roomService.findOneByProperties(Bill.class, "billId", billid);
			if(CommonConstants.BillProject.AccountTransfer.equals(bill.getProjectId())){
				JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE,
				"转账金额不可选!"));
				return;
			}
			String billId = DateUtil.currentDate("yyMMdd") + check.getBranchId()
					+ roomService.getSequence("SEQ_NEW_BILL");
			consumebill.setBillId(billId);
			consumebill.setBranchId(check.getBranchId());
			consumebill.setCheckId(bill.getCheckId());
			consumebill.setDescribe(bill.getDescribe());
			consumebill.setOffset(offset);
			consumebill.setPayment(bill.getPayment());
			consumebill.setProjectId(bill.getProjectId());
			consumebill.setProjectName(bill.getProjectName());
			consumebill.setRecordUser(staff.getStaffId());
			consumebill.setRemark(bill.getBillId());
			consumebill.setStatus(CommonConstants.BillStatus.BillCONSUME);
			

			String projectid = bill.getProjectId();
			// 记日志
			OffsetLog otlog = new OffsetLog();
			String logid = DateUtil.currentDate("yyMMdd") + check.getBranchId()
					+ roomService.getSequence("SEQ_OFFSETLOG_ID");
			otlog.setLogId(logid);
			otlog.setBranchId(check.getBranchId());
			otlog.setCheckId(bill.getCheckId());
			otlog.setLastBillId(bill.getBillId());
			otlog.setCurrBillId(consumebill.getBillId());
			otlog.setRecordUser(staff.getStaffId());
			otlog.setRecordTime(new Date());
			otlog.setOffsetType("1");
			otlog.setRemark(offset);

			if (projectid.startsWith("2")) {
				double price = bill.getPay();
				if (price == 0) {
					JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE,
							"选中了错误的冲减金额!"));
					return;
				}
				consumebill.setPay(-price);
				consumebill.setCost(0.00);
				otlog.setOffsetFee(bill.getPay());// 记冲减金额
			} else {
				double price = bill.getCost();
				if (price == 0) {
					JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, 
							"选中了错误的冲减金额!"));
					return;
				}
				consumebill.setCost(-price);
				consumebill.setPay(0.00);
				otlog.setOffsetFee(bill.getCost()); // 记冲减金额
			}
			bill.setStatus(CommonConstants.BillStatus.BillCONSUME);

			try {
				roomService.save(consumebill);
				roomService.save(bill);
				roomService.save(otlog);// 记冲减日志
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.SUCESS, "冲减金额成功!"));

	}

	@RequestMapping("/consumptionworkbill.do")
	public void consumptionworkbill(HttpServletRequest request, HttpServletResponse response, String strdetailid,
			String offset) {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String[] arrdetail = strdetailid.split(",");
		boolean errorbillflag = true;
		for (String detailid : arrdetail) {
			WorkBillDetail workbilldetail = (WorkBillDetail) roomService.findOneByProperties(WorkBillDetail.class,
					"detailId", detailid);
			String projectid = workbilldetail.getProjectId();
			if (projectid.startsWith("2")) {
				double price = workbilldetail.getPay();
				if (price == 0) {
					errorbillflag = false;
				}
			} else {
				double price = workbilldetail.getCost();
				if (price == 0) {
					errorbillflag = false;
				}
			}
		}

		if (!errorbillflag) {
			JSONUtil.responseJSON(response, new AjaxResult(0, "选中了错误的冲减金额!"));
			return;
		}
		for (String detailid : arrdetail) {
			WorkBillDetail consumedetail = new WorkBillDetail();
			WorkBillDetail workbilldetail = (WorkBillDetail) roomService.findOneByProperties(WorkBillDetail.class,
					"detailId", detailid);
			String detailId = DateUtil.currentDate("yyMMdd") + staff.getBranchId()
					+ roomService.getSequence("SEQ_NEW_WORKBILLDETAIL");
			consumedetail.setDetailId(detailId);
			consumedetail.setBranchId(staff.getBranchId());
			consumedetail.setWorkbillId(workbilldetail.getWorkbillId());
			consumedetail.setDescribe(workbilldetail.getDescribe());
			consumedetail.setOffset(offset);
			consumedetail.setPayment(workbilldetail.getPayment());
			consumedetail.setProjectId(workbilldetail.getProjectId());
			consumedetail.setProjectName(workbilldetail.getProjectName());
			consumedetail.setRecordUser(staff.getStaffId());
			consumedetail.setRemark(workbilldetail.getDetailId());
			consumedetail.setStatus("2");
			//consumedetail.setShift(loginGetShift.getShiftId());
			//consumedetail.setCashBox(loginGetShift.getCashbox());

			String projectid = workbilldetail.getProjectId();

			OffsetLog otlog = new OffsetLog();
			String logid = DateUtil.currentDate("yyMMdd") + staff.getBranchId()
					+ roomService.getSequence("SEQ_OFFSETLOG_ID");
			otlog.setLogId(logid);
			otlog.setBranchId(staff.getBranchId());
			otlog.setCheckId(workbilldetail.getWorkbillId());
			otlog.setLastBillId(workbilldetail.getDetailId());
			otlog.setCurrBillId(consumedetail.getDetailId());
			otlog.setRecordUser(staff.getStaffId());
			otlog.setRecordTime(new Date());
			otlog.setOffsetType("2");
			otlog.setRemark(offset);
			if (projectid.startsWith("2")) {
				double price = workbilldetail.getPay();
				if (price == 0) {
					JSONUtil.responseJSON(response, new AjaxResult(0, "选中了错误的冲减金额!"));
					continue;
				}
				consumedetail.setPay(-price);
				consumedetail.setCost(0.00);
				otlog.setOffsetFee(price);
			} else {
				double price = workbilldetail.getCost();
				if (price == 0) {
					JSONUtil.responseJSON(response, new AjaxResult(0, "选中了错误的冲减金额!"));
					continue;
				}
				consumedetail.setCost(-price);
				consumedetail.setPay(0.00);
				otlog.setOffsetFee(price);
			}
			workbilldetail.setStatus("2");

			try {
				roomService.save(consumedetail);
				roomService.save(workbilldetail);
				roomService.save(otlog);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		JSONUtil.responseJSON(response, new AjaxResult(1, "冲减成功!"));
	}

	@RequestMapping("/committransferorder.do")
	public void committransferorder(HttpServletRequest request, HttpServletResponse response, String checkid,
			String orderid) {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String branchId = staff.getBranchId();
		Check check = (Check) roomService.findOneByProperties(Check.class, "checkId", checkid);
		Order order = (Order) roomService.findOneByProperties(Order.class, "orderId", orderid);
		List<?> listbill = roomService.findByProperties(Bill.class, "checkId", orderid);
		try {
			for (Object object : listbill) {
				Bill bill = (Bill)object;
				roomService.delete(bill);
				bill.setCheckId(checkid);
				roomService.save(bill);
			}
			String sqlcost = "totalcostfromcheck";
			List<Map<String, BigDecimal>> costresult = roomService.findBySQL(sqlcost, new String[] { order.getOrderId(), branchId },
					true);
			String sqlpay = "totalpayfromcheck";
			List<Map<String, BigDecimal>> payresult = roomService.findBySQL(sqlpay, new String[] { order.getOrderId(), branchId },
					true);
			double pay = 0;
			double cost = 0;
			if (!costresult.isEmpty()) {
				pay = payresult.get(0).get("totalpay").doubleValue();
			}
			if (!payresult.isEmpty()) {
				cost = costresult.get(0).get("totalcost").doubleValue();
			}
			
			check.setCheckoutTime(order.getLeaveTime());
			check.setSwitchId(order.getOrderId());
			order.setStatus(CommonConstants.OrderStatus.CheckOn);
	
			SwitchOrder switchorder = new SwitchOrder();
			String logId = DateUtil.currentDate("yyMMdd") + branchId + roomService.getSequence("SEQ_NEW_SWITCHORDER");
			switchorder.setLogId(logId);
			switchorder.setBranchId(branchId);
			switchorder.setCheckId(checkid);
			switchorder.setOrderId(orderid);
			switchorder.setStatus("1");
			switchorder.setRecordUser(staff.getStaffId());
			//roomService.save(order);
			//roomService.save(check);
			roomService.save(switchorder);
			
			JSONUtil.responseJSON(response, new AjaxResult(1, "转单成功!"));
		} catch (Exception e) {
			e.printStackTrace();
			JSONUtil.responseJSON(response, new AjaxResult(0, "转单失败!"));
		}

	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/getproject.do")
	public void getproject(HttpServletRequest request, HttpServletResponse response) {
		String sqltype = "queryprojecttype";
		String sqlallproejct = "queryallproject";
		List<Map<String, Object>> projecttype = roomService.findBySQL(sqltype, true);
		Map<Object, Map<String, String>> allkingmap = new HashMap<Object, Map<String, String>>();
		for (Map<String, Object> map : projecttype) {
			Object orderno = map.get("ORDERNO");
			Object paramdesc = map.get("PARAMDESC");
			List<Map<String, String>> allkindproject = roomService.findBySQL(sqlallproejct, 
					new String[] {CommonConstants.SysparamStatus.VALID, orderno.toString() }, true);
			Map<String, String> resultallkind = new HashMap<String, String>();
			for (Map<String, String> map2 : allkindproject) {
				String content = map2.get("CONTENT");
				String paramname = map2.get("PARAMNAME");
				resultallkind.put(content, paramname);
				allkingmap.put(paramdesc, resultallkind);
			}
		}
		JSONUtil.responseJSON(response, allkingmap);
	}

	@RequestMapping("/setroommap.do")
	public void setroommap(HttpServletRequest request, HttpServletResponse response, String strcheckid,
			String hostcheckid) {
		// LoginUser loginuser = (LoginUser) request.getSession(true)
		// .getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		// Staff staff = loginuser.getStaff();
		String[] arrcheck = strcheckid.split(",");
		Check hostcheck = (Check) roomService.findOneByProperties(Check.class, "checkId", hostcheckid);
		String hostroomid = hostcheck.getRoomId();
		try {
			for (String checkid : arrcheck) {
				Check check = (Check) roomService.findOneByProperties(Check.class, "checkId", checkid);
				String roomid = check.getRoomId();
				RoomMapping roommapping = (RoomMapping) roomService.findOneByProperties(RoomMapping.class, "relaRoomid", hostroomid);
				if(roommapping == null){
					RoomMapping roommapp;
					
					roommapp = new RoomMapping();
					String roommappingId = roomService.getSequence("SEQ_ROOMMAPPING_ID");
					roommapp.setRoommappingId(roommappingId);
					roommapp.setRoomId(DateUtil.currentDate("yyMMdd") + roommappingId);
					roommapp.setRelaRoomid(hostroomid);
					roomService.save(roommapp);
					
					roommapp = new RoomMapping();
					String nextroommappingId = roomService.getSequence("SEQ_ROOMMAPPING_ID");
					roommapp.setRoommappingId(nextroommappingId);
					roommapp.setRoomId(DateUtil.currentDate("yyMMdd") + roommappingId);
					roommapp.setRelaRoomid(roomid);
					roomService.save(roommapp);
					//roomService.insertroommapping(hostroomid, hostroomid);
					//roomService.insertroommapping(hostroomid, roomid);
				}else{
					RoomMapping roommapp = new RoomMapping();
					String realhostroomid = roommapping.getRoomId();
					String nextroommappingId = roomService.getSequence("SEQ_ROOMMAPPING_ID");
					roommapp.setRoommappingId(nextroommappingId);
					roommapp.setRoomId(realhostroomid);
					roommapp.setRelaRoomid(roomid);
					roomService.save(roommapp);
					//roomService.insertroommapping(realhostroomid, roomid);
				}
			}
			JSONUtil.responseJSON(response, new AjaxResult(1, "添加成功!"));
		} catch (Exception e) {
			JSONUtil.responseJSON(response, new AjaxResult(0, "添加失败!"));
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/cancelroommapping.do")
	public void cancelroommapping(HttpServletRequest request, HttpServletResponse response, String checkid,
			String delroomid) {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String branchId = staff.getBranchId();
		Check check = (Check) roomService.findOneByProperties(Check.class, "checkId", checkid);
		String roomid = check.getRoomId();
		
		String sql = "loadroommapping";

		//String hostroomid = check.getRoomId();
		RoomMapping roommapping = (RoomMapping) roomService.findOneByProperties(RoomMapping.class, "relaRoomid", roomid);
		List<Map<String, Object>> roommappinglist = roomService.findallroommapping(roommapping.getRoomId());
		if (roommapping == null) {
			JSONUtil.responseJSON(response, new AjaxResult(0, "无关联房!"));
		} else {
			try {
				if(roommappinglist.size() == 2){
					roomService.deleteroommapping(roommapping.getRoomId());
				}else{
					roomService.deleteroommappingbyroomid(roommapping.getRoomId(), delroomid);
				}
				List<Map<String, String>> result = roomService.findBySQL(sql, new String[] { branchId, roommapping.getRoomId() }, true);
				JSONUtil.responseJSON(response, new AjaxResult(1, "添加成功!", result));
			} catch (Exception e) {
				JSONUtil.responseJSON(response, new AjaxResult(0, "添加失败!"));
			}
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/loadroommapping.do")
	public void loadroommapping(HttpServletRequest request, HttpServletResponse response, String checkid) {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String branchId = staff.getBranchId();
		Check check = (Check) roomService.findOneByProperties(Check.class, "checkId", checkid);
		String roomid = check.getRoomId();
		RoomMapping roommapping = (RoomMapping) roomService.findOneByProperties(RoomMapping.class, "relaRoomid", roomid);
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		if(roommapping != null){
			String sql = "loadroommapping";
			result = roomService.findBySQL(sql, new String[] { branchId, roommapping.getRoomId() }, true);
		}
		
		 * List<RoomMapping> listroommapping =
		 * roomService.findByProperties(RoomMapping.class, "roomId", roomid);
		 * Map<String, String> result = new HashMap<String, String>();
		 * 
		 * for (RoomMapping roomMapping : listroommapping) { String relaRoomid =
		 * roomMapping.getRelaRoomid(); Check relacheck = (Check)
		 * roomService.findOneByProperties(Check.class, "roomId", relaRoomid,
		 * "status", "1"); if(relacheck != null){ String checkuser =
		 * relacheck.getCheckUser(); String firstuser = checkuser.split(",")[0];
		 * Member member = (Member)
		 * roomService.findOneByProperties(Member.class, "memberId", firstuser);
		 * String firstusername = member.getMemberName(); result.put(relaRoomid,
		 * firstusername); } }
		 
		JSONUtil.responseJSON(response, result);
	}

	@RequestMapping("/selectroommapping.do")
	public void selectroommapping(HttpServletRequest request, HttpServletResponse response, String hostcheckid,
			MultiQueryCheck multiQuerycheck) {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginuser.getStaff().getBranchId();
		Check check = (Check) roomService.findOneByProperties(Check.class, "checkId", hostcheckid);
		String hostroomid = check.getRoomId();
		//List<RoomMapping> listroommapping = roomService.findByProperties(RoomMapping.class, hostroomid, listroommapping, null);
		//List<Map<String, Object>> roomid = roomService.findallroommapping(hostroomid);
		List<?> result = roomService.selectroommapping(hostroomid, branchId, multiQuerycheck);
		JSONUtil.responseJSON(response, result);
	}
	
	@RequestMapping("/jumptocustom.do")
	public void jumptocustom(HttpServletRequest request, HttpServletResponse response, 
			String relaroomid) throws Exception {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginuser.getStaff().getBranchId();
		Check check = (Check) roomService.findOneByProperties(Check.class, "roomId", relaroomid, "status", 
				CommonConstants.CheckStatus.CheckOn, "branchId", branchId);
		if(check == null){
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, "无在住房！"));
		}else{
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.SUCESS, null, check));
		}
		
	}
	
	@RequestMapping("/roomout.do")
	public void roomout(HttpServletRequest request, HttpServletResponse response, String checkid) {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginuser.getStaff().getBranchId();
		String recordUser = loginuser.getStaff().getStaffId();
		Check check = (Check) roomService.findOneByProperties(Check.class, "checkId", checkid);
		String roomId = check.getRoomId();
		CheckAllAmount checkallamount = RoomUtil.getCheckamount(request, checkid);
		check.setStatus(CommonConstants.CheckStatus.CheckOffAndUnout);
		try{
			if (checkallamount.getSubPrice() != 0) {
				Bill costbill = new Bill();
				String costbillId = DateUtil.currentDate("yyMMdd") + branchId + roomService.getSequence("SEQ_NEW_BILL");
				costbill.setBillId(costbillId);
				costbill.setBranchId(branchId);
				costbill.setCheckId(checkid);
				costbill.setDescribe("手动日结");
				RoomUtil.setBillPayment(costbill, CommonConstants.BillProject.CheckRoomprice, 
						checkallamount.getSubPrice());
				costbill.setStatus(CommonConstants.BillStatus.BillCheckout);
				costbill.setRecordUser(recordUser);
				roomService.save(costbill);
			}
			roomService.update(check);
			roomService.upateroomstatus(check.getBranchId(), roomId, CommonConstants.RoomStatus.RoomDirty);
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.SUCESS, "退房成功！"));
		}
		catch(Exception e ){
			e.printStackTrace();
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, "退房失败！"));
		}
	}

	@RequestMapping("/querycampaign.do")
	public void querycampaign(HttpServletRequest request, HttpServletResponse response) {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginuser.getStaff().getBranchId();
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String newdate = sdf.format(date);
		List<?> campaignlist = roomService.findBySQL("querycampaign", new String[] { branchId, newdate }, true);

		List<?> campaignnum = roomService.findBySQL("querycampaigncount", new String[] { branchId, newdate }, true);
		JSONUtil.responseJSON(response, campaignlist);
		JSONUtil.responseJSON(response, campaignnum);
	}

	@RequestMapping("/checkroommappingnull.do")
	public void checkroommappingnull(HttpServletRequest request, HttpServletResponse response, String checkid) {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String branchId = staff.getBranchId();
		Check check = (Check) roomService.findOneByProperties(Check.class, "checkId", checkid);
		String roomid = check.getRoomId();
		RoomMapping roommapping = (RoomMapping) roomService.findOneByProperties(RoomMapping.class, "relaRoomid", roomid);
		//String sql = "loadroommapping";
		//List<?> listroommapping = roomService.findBySQL(sql, new String[] { branchId, roomid }, true);
		// List<RoomMapping> listroommapping =
		// roomService.findByProperties(RoomMapping.class, "roomId", roomid);
		if (roommapping == null) {
			JSONUtil.responseJSON(response, new AjaxResult(0, "账户无关联房!"));
		} else {
			JSONUtil.responseJSON(response, new AjaxResult(1, "账户有关联房!"));
		}
	}

	@RequestMapping("/loadGuestData.do")
	public void loadGuestData(HttpServletRequest request, HttpServletResponse response, String checkuserid) throws JSONException {
		CheckUser checkuser = (CheckUser) roomService.findOneByProperties(CheckUser.class, "checkuserId", checkuserid);
		
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchid = loginuser.getStaff().getBranchId();
		Branch branch = (Branch)roomService.findOneByProperties(Branch.class, "branchId", branchid);
		
		Member mb = null;
		
		if("0".equals(branch.getRank())){
			String idcard = checkuser.getIdcard();
            if(!StringUtils.isEmpty(idcard)){
				
			    mb = (Member)roomService.findOneByProperties(Member.class, "idcard", idcard,"status","1");
			}
				
			if(mb == null){
	            String mobile = checkuser.getMobile();
				mb = (Member)roomService.findOneByProperties(Member.class, "mobile", mobile,"status","1");
			}
			
			if(mb != null){
				checkuser.setRankName(mb.getMemberRank());
			}
			
			JSONUtil.responseJSON(response, checkuser);
			return ;
		}
		
		
		if(StringUtils.isEmpty(checkuser.getRankName())){
			DataDealPortalService service = new DataDealPortalService();
			IDataDealPortal portal = service.getDataDealPortalPort();
			String member = null;
			JSONArray jsonarraymember = null;
			String searchMessage = checkuser.getIdcard();
			if(StringUtils.isEmpty(searchMessage)){
				searchMessage = checkuser.getMobile();
			}
			if(!StringUtils.isEmpty(searchMessage)){
				member = portal.call(1, 1, "{ function: \"checkInDirect.getMemberBean\", data:{idcard:" +searchMessage  +" } }");
				if("-1".equals(member)){
					checkuser.setRankName(CommonConstants.MembenRank.NM);
				}
				else{
					jsonarraymember = new JSONArray(member);
					org.json.JSONObject memberbean =  jsonarraymember.getJSONObject(0);
					checkuser.setRankName(memberbean.getString("MEMBER_RANK"));
					if( jsonarraymember.length() > 1){
						JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, "查出多个会员!"));
						return;
					}					
				}
			}
		}
		String idcard = null;
		if (checkuser != null){
			idcard = checkuser.getIdcard();
		}
		if (idcard != null && !idcard.isEmpty()){
			Member member = (Member) roomService.findOneByProperties(Member.class, "idcard", idcard);
			CheckUserData checkuserdata = new CheckUserData();
			if(member != null){
				checkuserdata.setCheckuserId(checkuserid);
				checkuserdata.setCheckuserName(checkuser.getCheckuserName());
				checkuserdata.setIdcard(idcard);
				checkuserdata.setGender(checkuser.getGender());
				checkuserdata.setMobile(member.getMobile());
				checkuserdata.setAddress(member.getAddress());
				checkuserdata.setRemark(member.getRemark());
				MemberRank memberrank = (MemberRank) roomService.findOneByProperties(MemberRank.class, "memberRank", member
						.getMemberRank());
				checkuserdata.setRankName(memberrank.getRankName());
			}
			else{
				checkuserdata.setCheckuserId(checkuserid);
				checkuserdata.setCheckuserName(checkuser.getCheckuserName());
				checkuserdata.setIdcard(idcard);
				checkuserdata.setGender(checkuser.getGender());
			}
			JSONUtil.responseJSON(response, checkuserdata);
			return;
		}
		String mobile = null;
		if (checkuser != null){
			mobile = checkuser.getMobile();	
		}
		if (mobile != null && !mobile.isEmpty()){
			Member member = (Member) roomService.findOneByProperties(Member.class, "mobile", mobile);
			CheckUserData checkuserdata = new CheckUserData();
			if(member != null){
				checkuserdata.setCheckuserId(checkuserid);
				checkuserdata.setCheckuserName(checkuser.getCheckuserName());
				checkuserdata.setIdcard(idcard);
				checkuserdata.setGender(checkuser.getGender());
				checkuserdata.setMobile(member.getMobile());
				checkuserdata.setAddress(member.getAddress());
				checkuserdata.setRemark(member.getRemark());
				MemberRank memberrank = (MemberRank) roomService.findOneByProperties(MemberRank.class, "memberRank", member
						.getMemberRank());
				checkuserdata.setRankName(memberrank.getRankName());
			}
			else{
				checkuserdata.setCheckuserId(checkuserid);
				checkuserdata.setCheckuserName(checkuser.getCheckuserName());
				checkuserdata.setIdcard(idcard);
				checkuserdata.setGender(checkuser.getGender());
			}
			JSONUtil.responseJSON(response, checkuserdata);
			return;
		}
		JSONUtil.responseJSON(response, checkuser);
	}
	
	@RequestMapping("/addcustomerHand.do")
	public void addcustomerHand(HttpServletRequest request, HttpServletResponse response, 
			String idcard, String username, String gender, String checkid, String checkphone, 
			String memberrankname, String address, String customerreamrk, String checkinType){
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		
		//判断checkuser是否已经入住了，或者已经入住已经被删除了
		CheckUser precheckuser = (CheckUser) roomService.findOneByProperties(CheckUser.class, "idcard", idcard, "checkId", checkid, "checkinType", checkinType);
		if(precheckuser ==null)
			precheckuser = (CheckUser) roomService.findOneByProperties(CheckUser.class, "mobile", checkphone, "checkId", checkid, "checkinType", checkinType);
		if(precheckuser != null){
			List<?> listcheckuser = roomService.findByProperties(CheckUser.class, 
					"checkId", checkid, "checkinType", checkinType,
					"checkuserType", CommonConstants.CheckUserType.HOST);
			if(listcheckuser == null || listcheckuser.isEmpty() ){
				precheckuser.setCheckuserType(CommonConstants.CheckUserType.HOST);
			}
			else{
				precheckuser.setCheckuserType(CommonConstants.CheckUserType.GUEST);
			}
			if (precheckuser.getStatus().equals(CommonConstants.CheckUserStatus.CANCEL)){
				precheckuser.setCheckuserName(username);
				precheckuser.setIdcard(idcard);
				precheckuser.setMobile(checkphone);
				precheckuser.setRankName(memberrankname);
				precheckuser.setGender(gender);
				precheckuser.setRecordUser(staff.getStaffId());
				precheckuser.setAddress(address);
				precheckuser.setRemark(customerreamrk);
				precheckuser.setStatus(CommonConstants.CheckUserStatus.ON);
				try{
					roomService.update(precheckuser);
					JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXACTION.OTHER, "客人" + username + "重新入住!", precheckuser));
					return;
				}
				catch(Exception e){
					JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, "重新入住失败!", precheckuser));
					return;
				}
			}else if(precheckuser.getStatus().equals(CommonConstants.CheckUserStatus.ON)){
				//判断是否有主客人
				JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXACTION.OTHER, "客人已入住!", precheckuser));
				return;
			}
		}
		else{//没有入住人，开始新添入住人
			CheckUser checkuser = new CheckUser();
			String checkuserId = staff.getBranchId() + CommonConstants.OrderSource.Branch + 
			roomService.getSequence("SEQ_NEW_CHECKUSER");
			
			//判断是否有主客人
			List<?> listcheckuser = roomService.findByProperties(CheckUser.class, 
					"checkId", checkid, "checkinType", checkinType,
					"checkuserType", CommonConstants.CheckUserType.HOST);
			if(listcheckuser == null || listcheckuser.isEmpty() ){
				checkuser.setCheckuserType(CommonConstants.CheckUserType.HOST);
			}
			else{
				checkuser.setCheckuserType(CommonConstants.CheckUserType.GUEST);
			}
			//Member member = (Member) roomService.findOneByProperties(Member.class, "idcard", idcard);
			//RoomUtil.cansetCheckuserbyMember(checkuser, member);
			
			checkuser.setCheckuserId(checkuserId);
			checkuser.setCheckId(checkid);
			checkuser.setStatus(CommonConstants.CheckUserStatus.ON);
			checkuser.setCheckinType(checkinType);
			checkuser.setCheckuserName(username);
			checkuser.setIdcard(idcard);
			checkuser.setMobile(checkphone);
			checkuser.setRankName(memberrankname);
			checkuser.setGender(gender);
			checkuser.setRecordUser(staff.getStaffId());
			checkuser.setAddress(address);
			checkuser.setRemark(customerreamrk);
			try{
				roomService.save(checkuser);
				JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.SUCESS, "添加客人成功!", checkuser));
				return;
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}
	
	@RequestMapping("/addCheckuser.do")
	public void addCheckuser(HttpServletRequest request, HttpServletResponse response, String idcard, 
			String username, String sex, String searchMessage, String checkid, String checkinType ) throws DAOException, JSONException{
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		DataDealPortalService service = new DataDealPortalService();
		IDataDealPortal portal = service.getDataDealPortalPort();
		String member = null;
		JSONArray jsonarraymember = null;
		if(!StringUtils.isEmpty(searchMessage)){
			member = portal.call(1, 1, "{ function: \"checkInDirect.getMemberBean\", data:{idcard:" +searchMessage  +" } }");
			if("-1".equals(member)){
				JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, "无此会员!"));
				return;
			}
			if("hasmult".equals(member)){
				JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, "查出多个会员!"));
				return;
			}
			jsonarraymember = new JSONArray(member);
		}
		else{
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, "硬件未提供，功能暂未开放!"));
			return;
		}
		org.json.JSONObject memberbean =  jsonarraymember.getJSONObject(0);
		//判断checkuser是否已经入住了，或者已经入住已经被删除了
		CheckUser precheckuser = (CheckUser) roomService.findOneByProperties(CheckUser.class, "idcard", memberbean.getString("IDCARD"), "checkId", checkid, "checkinType", checkinType);
		if(precheckuser == null){
			precheckuser = (CheckUser) roomService.findOneByProperties(CheckUser.class, "mobile", memberbean.getString("MOBILE"), "checkId", checkid, "checkinType", checkinType);
		}
		if(precheckuser != null){
			List<?> listcheckuser = roomService.findByProperties(CheckUser.class, 
					"checkId", checkid, "checkinType", checkinType,
					"checkuserType", CommonConstants.CheckUserType.HOST);
			if(listcheckuser == null || listcheckuser.isEmpty() ){
				precheckuser.setCheckuserType(CommonConstants.CheckUserType.HOST);
			}
			else{
				precheckuser.setCheckuserType(CommonConstants.CheckUserType.GUEST);
			}
			if (precheckuser.getStatus().equals(CommonConstants.CheckUserStatus.CANCEL)){
				precheckuser.setStatus(CommonConstants.CheckUserStatus.ON);
				roomService.update(precheckuser);
				JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXACTION.OTHER, "客人" + username + "重新添加!", precheckuser));
				return;
			}else if(precheckuser.getStatus().equals(CommonConstants.CheckUserStatus.ON)){
				//判断是否有主客人
				Member member = (Member) roomService.findOneByProperties(Member.class, "idcard", idcard);
				RoomUtil.cansetCheckuserbyMember(precheckuser, member);
				precheckuser.setStatus(CommonConstants.CheckUserStatus.ON);
				precheckuser.setCheckuserName(username);
				precheckuser.setIdcard(idcard);
				precheckuser.setGender(sex);
				try{
					roomService.update(precheckuser);
				}
				catch(Exception e){
					e.printStackTrace();
				}
				JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, "客人已入住!", precheckuser));
				return;
			}
		}
		else{
			CheckUser checkuser = new CheckUser();
			String checkuserId = staff.getBranchId() + CommonConstants.OrderSource.Branch + 
			roomService.getSequence("SEQ_NEW_CHECKUSER");
			
			//判断是否有主客人
			List<?> listcheckuser = roomService.findByProperties(CheckUser.class, 
					"checkId", checkid,
					"checkinType", checkinType,
					"checkuserType", CommonConstants.CheckUserType.HOST);
			if(listcheckuser == null || listcheckuser.isEmpty() ){
				checkuser.setCheckuserType(CommonConstants.CheckUserType.HOST);
			}
			else{
				checkuser.setCheckuserType(CommonConstants.CheckUserType.GUEST);
			}
			checkuser.setCheckuserId(checkuserId);
			checkuser.setCheckId(checkid);
			checkuser.setCheckuserName(memberbean.getString("MEMBER_NAME"));
			checkuser.setGender(memberbean.getString("GENDOR"));
			if(!memberbean.isNull("IDCARD"))
				checkuser.setIdcard(memberbean.getString("IDCARD"));
			if(!memberbean.isNull("MOBILE"))
			checkuser.setMobile(memberbean.getString("MOBILE"));
			checkuser.setRankName(memberbean.getString("MEMBER_RANK"));
			checkuser.setRecordUser(staff.getStaffId());
			checkuser.setStatus(CommonConstants.CheckUserStatus.ON);
			checkuser.setCheckinType(checkinType);
			checkuser.setAddress(memberbean.getString("ADDRESS"));
			if(!memberbean.isNull("REMARK"))
			checkuser.setRemark(memberbean.getString("REMARK"));
			try{
				roomService.save(checkuser);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.SUCESS, "添加客人成功!", checkuser));
			return;
		}
		JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, "添加客人失败!", null));
	}
	
	@RequestMapping("/dlcheckuser.do")
	public void dlcheckuser(HttpServletRequest request, HttpServletResponse response, String checkuserId, 
			String status ){
		CheckUser checkuser = (CheckUser) roomService.findOneByProperties(CheckUser.class, "checkuserId", checkuserId);
		checkuser.setStatus(status);
		try{
			roomService.update(checkuser);
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.SUCESS, "删除客人成功!", null));
		}
		catch(Exception e){
			e.printStackTrace();
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, "删除客人失败!", null));			
		}
	}
	
	@RequestMapping("/sethousecheckuser.do")
	public void sethousecheckuser(HttpServletRequest request, HttpServletResponse response, String checkuserId, 
			String hostcheckuserId ){
		CheckUser checkuser = (CheckUser) roomService.findOneByProperties(CheckUser.class, 
				"checkuserId", checkuserId);
		CheckUser hostcheckuser = (CheckUser) roomService.findOneByProperties(CheckUser.class, 
				"checkuserId", hostcheckuserId);
		checkuser.setCheckuserType(CommonConstants.CheckUserType.HOST);
		hostcheckuser.setCheckuserType(CommonConstants.CheckUserType.GUEST);
		try{
			roomService.update(checkuser);
			roomService.update(hostcheckuser);
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.SUCESS, "设置主客人成功!", null));
		}
		catch(Exception e){
			e.printStackTrace();
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, "设置主客人失败!", null));			
		}
	}
	
	@RequestMapping("/loadallcheckuser.do")
	public void loadallcheckuser(HttpServletRequest request, HttpServletResponse response, String checkid){
		List<?> listcheckusers = roomService.findByProperties(CheckUser.class, "checkId", checkid, 
				"status", CommonConstants.CheckUserStatus.ON, "checkinType", CommonConstants.CheckinType.CHECK);
		if(listcheckusers != null && !listcheckusers.isEmpty()){
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.SUCESS, "成功!", listcheckusers));
		}
		else{
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, "失败!", listcheckusers));
		}
	}
	
	@RequestMapping("/getallmemberrank.do")
	public void getallmemberrank(HttpServletRequest request, HttpServletResponse response, String checkid){
		List<?> listmemberrank = roomService.findByProperties(MemberRank.class, 
				"status", CommonConstants.STATUS.NORMAL);
		JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.SUCESS, "成功!", listmemberrank));
	}

	@RequestMapping("/queryGuest.do")
	public void queryGuest(HttpServletRequest request, HttpServletResponse response, String checkUserId) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		if (checkUserId.isEmpty()) {
			JSONUtil.responseJSON(response, null);
		} else {
			//if (checkUserId.indexOf(",") > 0) {
				String[] ids = checkUserId.split(",");
				for (String id : ids) {
					String birthday = "";
					CheckUser checkuser = (CheckUser) roomService.findOneByProperties(CheckUser.class, "checkuserId", id);
					Member member = (Member) roomService.findOneByProperties(Member.class, "mobile", checkuser.getMobile());
					if (member != null) {
						MemberRank memberrank = (MemberRank) roomService.findOneByProperties(MemberRank.class,
								"memberRank", member.getMemberRank());
						map.put("Member", member);
						map.put("rankName", memberrank.getRankName());
						map.put("birthday", member.getBirthday());
						map.put("idCard", member.getIdcard());
					} else {
						map.put("birthday", birthday);
					}
					map.put("CheckUser", checkuser);
					result.add(map);
				}
			} else {
				String birthday = "";
				Member member = (Member) roomService.findOneByProperties(Member.class, "memberId", memberid);
				MemberRank memberrank = (MemberRank) roomService.findOneByProperties(MemberRank.class, "memberRank",
						member.getMemberRank());
				map.put(memberid, member);
				map.put("rankName", memberrank.getRankName());
				map.put("birthday", birthday);
				map.put("idCard", member.getIdcard());
			}
		}
		JSONUtil.responseJSON(response, result);
	}
	
	@RequestMapping("loadAllCheckuser.do")
	public void loadAllCheckuser(HttpServletRequest request, HttpServletResponse response, String checkid, String checkinType)
			throws Exception {
		List<?> listcheckusers = new ArrayList<Object>();
		listcheckusers = roomService.findByPropertiesWithSort(CheckUser.class, "status", "desc", "checkId", checkid, "checkinType", checkinType);
		if(listcheckusers != null && !listcheckusers.isEmpty()){
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.SUCESS, "", 
					JSONUtil.fromObject(listcheckusers)));
		}
		else{
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, "", null));
		}
	}

	@RequestMapping("/searchmember.do")
	public void searchmember(HttpServletRequest request, HttpServletResponse response,String checkid, String datamember, String checkinType) throws JSONException {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		SysParam sysparam = (SysParam) roomService.findOneByProperties(SysParam.class, "paramType", "SYSTEMTYPE");
		if (sysparam != null){
			if (CommonConstants.SystemType.Branch.equals(sysparam.getContent())){
				if(!HeartBeatClient.heartbeat){
					JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, "网络未连接!"));
					return;
				}
			}
		}
		else{
			logger.warn("SysParam SYSTEMTYPE is null");
		}
		DataDealPortalService service = new DataDealPortalService();
		IDataDealPortal portal = service.getDataDealPortalPort();
		String member = portal.call(1, 1, "{ function: \"checkInDirect.getMemberBean\", data:{idcard:" +datamember  +" } }");
		if("-1".equals(member)){
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, "无此会员!"));
			return;
		}
		JSONArray jsonarraymember = new JSONArray(member);
		if(jsonarraymember.length() > 1){
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, "查出多个会员!"));
			return;
		}
		
		org.json.JSONObject memberbean =  jsonarraymember.getJSONObject(0);
		//String memberrank = portal.call(1, 1, "{ function: \"memberCenter.getMemberRank\", data:{ memberRank : " +memberbean.getString("MEMBER_RANK")  +"}  }");
		//org.json.JSONObject memberk = new org.json.JSONObject(memberrank);
		
		CheckUser checkuser = new CheckUser();
		//判断checkuser是否已经入住了，或者已经入住已经被删除了
		CheckUser precheckuser = (CheckUser) roomService.findOneByProperties(CheckUser.class, "mobile", datamember, "checkId", checkid, "checkinType", checkinType);
		if(precheckuser != null){
			List<?> listcheckuser = roomService.findByProperties(CheckUser.class, 
					"checkId", checkid, "checkinType", checkinType,
					"checkuserType", CommonConstants.CheckUserType.HOST);
			if(listcheckuser == null || listcheckuser.isEmpty() ){
				precheckuser.setCheckuserType(CommonConstants.CheckUserType.HOST);
			}
			else{
				precheckuser.setCheckuserType(CommonConstants.CheckUserType.GUEST);
			}
			if(precheckuser.getStatus().equals(CommonConstants.CheckUserStatus.ON)){
				JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, "已存在客人" + precheckuser.getCheckuserName() + "!", null));
				return;
			}else if (precheckuser.getStatus().equals(CommonConstants.CheckUserStatus.CANCEL)){
				precheckuser.setStatus(CommonConstants.CheckUserStatus.ON);
				precheckuser.setGender(memberbean.getString("GENDOR"));
				if(!memberbean.isNull("IDCARD"))
					precheckuser.setIdcard(memberbean.getString("IDCARD"));
				if(!memberbean.isNull("MOBILE"))
					precheckuser.setMobile(memberbean.getString("MOBILE"));
				precheckuser.setRankName(memberbean.getString("MEMBER_RANK"));
				precheckuser.setRecordUser(staff.getStaffId());
				if(!memberbean.isNull("REMARK"))
				checkuser.setRemark(memberbean.getString("REMARK"));
				try{
					roomService.update(precheckuser);
					JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXACTION.OTHER, "客人" + precheckuser.getCheckuserName() + "重新入住!", precheckuser));
					return;
				}
				catch(Exception e){
					e.printStackTrace();
					JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXACTION.OTHER, "客人" + precheckuser.getCheckuserName() + "重新入住!", precheckuser));
					return;
				}
			}
		}
		else{
			String checkuserId = staff.getBranchId() + CommonConstants.OrderSource.Branch + 
					roomService.getSequence("SEQ_NEW_CHECKUSER");
			checkuser.setCheckuserId(checkuserId);
			checkuser.setCheckId(checkid);
			checkuser.setCheckuserName(memberbean.getString("MEMBER_NAME"));
			//判断是否有主客人
			List<?> listcheckuser = roomService.findByProperties(CheckUser.class, 
					"checkId", checkid, "checkinType", checkinType,
					"checkuserType", CommonConstants.CheckUserType.HOST);
			if(listcheckuser == null || listcheckuser.isEmpty() ){
				checkuser.setCheckuserType(CommonConstants.CheckUserType.HOST);
			}
			else{
				checkuser.setCheckuserType(CommonConstants.CheckUserType.GUEST);
			}
			checkuser.setGender(memberbean.getString("GENDOR"));
			if(!memberbean.isNull("IDCARD"))
				checkuser.setIdcard(memberbean.getString("IDCARD"));
			if(!memberbean.isNull("MOBILE"))
			checkuser.setMobile(memberbean.getString("MOBILE"));
			checkuser.setRankName(memberbean.getString("MEMBER_RANK"));
			checkuser.setRecordUser(staff.getStaffId());
			checkuser.setStatus(CommonConstants.CheckUserStatus.ON);
			checkuser.setCheckinType(checkinType);
			if(!memberbean.isNull("REMARK"))
			checkuser.setRemark(memberbean.getString("REMARK"));
			
			try{
				roomService.save(checkuser);
				JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.SUCESS,  "入住成功!", checkuser));
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		
		
		
		
		
		
		if (result.isEmpty()) {
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, "无此会员，请注册!", ""));
		} else {
			if (result.size() > 1) {
				JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, "查出多个会员!", result));
			} else {
				JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.SUCESS, "查询成功!", result));
			}
		}
	}

	@RequestMapping("/updateCustomer.do")
	public void updateCustomer(HttpServletRequest request, HttpServletResponse response, String checkid, String strguest) {
		Check check = (Check) roomService.findOneByProperties(Check.class, "checkId", checkid);
		String checkuser = check.getCheckUser();
		int checknum = checkuser.split(",").length;
		int strguesthnum = strguest.split(",").length;
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String branchid = staff.getBranchId();
		if (!strguest.equals(checkuser)) {
			try {
				CheckinUserLog addlog = new CheckinUserLog();
				String logId = DateUtil.currentDate("yyMMdd") + branchid
						+ roomService.getSequence("SEQ_CHECKINUSERLOG_ID");
				addlog.setLogId(logId);
				addlog.setBranchId(branchid);
				addlog.setCheckId(checkid);
				addlog.setLastCheckUser(checkuser);
				addlog.setCurrCheckUser(strguest);
				addlog.setRecordUser(staff.getStaffId());
				addlog.setRecordTime(new Date());
				roomService.save(addlog);
				if (checknum < strguesthnum) {
					roomService.updatecheckcheckuser(strguest, checkid);
					JSONUtil.responseJSON(response, new AjaxResult(1, "入住成功!"));
				} else {
					roomService.updatecheckcheckuser(strguest, checkid);
					JSONUtil.responseJSON(response, new AjaxResult(1, "设置成功!"));
				}
			} catch (DAOException e) {
				e.printStackTrace();
			}
		} else {
			JSONUtil.responseJSON(response, new AjaxResult(0, "客人已入住!"));
		}
	}

	

	

	@RequestMapping("/addmember.do")
	public void addmember(HttpServletRequest request, HttpServletResponse response, Member member) {
		Member existmember = (Member) roomService.findOneByProperties(Member.class, "mobile", member.getMobile());
		if (existmember != null) {
			JSONUtil.responseJSON(response, new AjaxResult(0, "手机号已注册!", null));
			return;
		}
		String memberId = roomService.getSequence("SEQ_MEMBER_ID");
		String memberName = member.getMemberName();
		String loginName = ChineseCharacterUtil.convertHanzi2Pinyin(memberName);
		Calendar calendar = Calendar.getInstance();
		member.setMemberId(memberId);
		member.setLoginName(loginName);
		member.setPassword(MD5Util.getCryptogram("888888"));
		member.setMemberRank("1");
		member.setSource("1");
		member.setValidTime(calendar.getTime());
		member.setRecordTime(calendar.getTime());
		member.setRegisterTime(calendar.getTime());
		calendar.add(Calendar.YEAR, 1);
		member.setInvalidTime(calendar.getTime());
		member.setStatus("1");

		MemberAccount memberaccount = new MemberAccount();
		String accountId = roomService.getSequence("SEQ_ACCOUNT_ID");
		memberaccount.setAccountId(accountId);
		memberaccount.setMemberId(memberId);
		memberaccount.setCurrBalance(0.00);
		memberaccount.setCurrIntegration((long) 0);
		memberaccount.setTotalRecharge(0.00);
		memberaccount.setDiscountGift(0.00);
		memberaccount.setChargeGift(0.00);
		memberaccount.setTotalConsume(0.00);
		memberaccount.setTotalIntegration((long) 0);
		memberaccount.setIngegrationGift((long) 0);
		memberaccount.setTotalConsIntegration((long) 0);
		memberaccount.setTotalRoomnights(0);
		memberaccount.setCurrRoomnights(0);
		memberaccount.setTotalNoshow((short) 0);
		memberaccount.setCurrNoshow((short) 0);
		memberaccount.setRecordTime(new Date());
		memberaccount.setStatus("1");
		memberaccount.setThisYearIntegration((long) 0);
		try {
			roomService.save(member);
			roomService.save(memberaccount);
			JSONUtil.responseJSON(response, new AjaxResult(1, "注册成功!", memberId));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/updatemember.do")
	public void updatemember(HttpServletRequest request, HttpServletResponse response, Member member) {
		member.setStatus("1");
		String memberId = member.getMemberId();
		String memberName = member.getMemberName();
		String loginName = ChineseCharacterUtil.convertHanzi2Pinyin(memberName);
		String idcard = member.getIdcard();
		String gendor = member.getGendor();
		String mobile = member.getMobile();
		String eamil = member.getEmail();
		String address = member.getAddress();
		try {
			roomService.updateMember(memberId, memberName, loginName, idcard, gendor, mobile, eamil, address);
			JSONUtil.responseJSON(response, new AjaxResult(1, "更改成功!"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/getcheckStatus.do")
	public void getcheckStatus(HttpServletRequest request, HttpServletResponse response, String checkid) {
		Check check = (Check) roomService.findOneByProperties(Check.class, "checkId", checkid);
		try {
			if (check != null) {
				JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.SUCESS, "未入住!", check));
			} else {
				JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, "未入住!", null));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/isCheckout.do")
	public void isCheckout(HttpServletRequest request, HttpServletResponse response, String checkid) {
		Check check = (Check) roomService.findOneByProperties(Check.class, "checkId", checkid);
		try {
			if (check == null) {
				JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, "未入住!"));
			} else {
				if (CommonConstants.CheckStatus.CheckLeave.equalsIgnoreCase(check.getStatus()) 
						|| CommonConstants.CheckStatus.CheckOff.equalsIgnoreCase(check.getStatus())) {
					JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.SUCESS, "当前已结算!"));
				} else {
					JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, "未结算!"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/isCustomout.do")
	public void isCustomout(HttpServletRequest request, HttpServletResponse response, String checkid) {
		Check check = (Check) roomService.findOneByProperties(Check.class, "checkId", checkid);
		try {
			if (check == null) {
				JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, "未入住!"));
			} else {
				if (CommonConstants.CheckStatus.CheckOffAndUnout.equalsIgnoreCase(check.getStatus())) {
					JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.SUCESS, "已退未结!"));
				} else {
					JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, "未结算!"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/resetprice.do")
	public void resetprice(HttpServletRequest request, HttpServletResponse response, String checkid, String amount, String remark) {
		if(!RightUtil.hasRight(request, "L6661")){
			JSONUtil.responseJSON(response, new AjaxResult(0, "抱歉,权限不够!"));
			return;
		}
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String branchId = staff.getBranchId();
		Check check = (Check) roomService.findOneByProperties(Check.class, "checkId", checkid);
		RoomPlan roomplan = new RoomPlan();
		String logId = DateUtil.currentDate("yyMMdd") + staff.getBranchId()
				+ roomService.getSequence("SEQ_NEW_ROOMPLAN");
		RoomUtil.saveCheckinPrice(DateUtil.s2d("2017/11/21 15:34:54", "yyyy/MM/dd HH:mm:ss"), DateUtil.s2d("2017/11/22 15:34:54", "yyyy/MM/dd HH:mm:ss"), check.getRoomType(), check.getRpId(), branchId, "1", check.getCheckId(), staff.getStaffId(), null );
		roomplan.setLogId(logId);
		roomplan.setBranchId(branchId);
		roomplan.setValidDay(new Date());
		roomplan.setRoomId(check.getRoomId());
		roomplan.setRpId(check.getRpId());
		roomplan.setRoomPrice(check.getRoomPrice());
		roomplan.setCashback(Double.valueOf(amount));
		roomplan.setStatus(CommonConstants.RoomPlanStatus.Valid);
		roomplan.setRecordUser(staff.getStaffId());
		roomplan.setCheckId(checkid);
		roomplan.setRemark(remark);
		
		check.setRoomPrice(check.getRoomPrice() + Double.valueOf(amount));
		
		try {
			roomService.save(roomplan);
			roomService.save(check);
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.SUCESS, "调整成功!"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}

	@RequestMapping("/autosaveRemark.do")
	public void autosaveRemark(HttpServletRequest request, HttpServletResponse response, String checkid, String remark) {
		// LoginUser loginuser = (LoginUser) request.getSession(true)
		// .getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		// Staff staff = loginuser.getStaff();
		Check check = (Check) roomService.findOneByProperties(Check.class, "checkId", checkid);
		check.setRemark(remark);
		try {
			roomService.save(check);
		} catch (Exception e) {
			e.printStackTrace();
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, "备注丢失!"));
		}
	}

	@RequestMapping("/loadsinglehaltplan.do")
	public void loadsinglehaltplan(HttpServletRequest request, HttpServletResponse response, String logid) {
		// LoginUser loginuser = (LoginUser) request.getSession(true)
		// .getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		// Staff staff = loginuser.getStaff();
		HaltPlan haltplan = (HaltPlan) roomService.findOneByProperties(HaltPlan.class, "logId", logid);

		try {
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, "加载成功!", haltplan));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/veritfycheckout.do")
	public void veritfycheckout(HttpServletRequest request, HttpServletResponse response, String checkid) {
		// LoginUser loginuser = (LoginUser) request.getSession(true)
		// .getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		// Staff staff = loginuser.getStaff();
		Check check = (Check) roomService.findOneByProperties(Check.class, "checkId", checkid);
		String roomid = check.getRoomId();
		RoomMapping roommapping = (RoomMapping) roomService.findOneByProperties(RoomMapping.class, "relaRoomid", roomid);
		//List<?> listroommapping = roomService.findByProperties(RoomMapping.class, "roomId", roomid);
		if (roommapping != null ) {
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.SUCESS, "有关联房!"));
		} else {
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, "无关联房!"));
		}

	}

	@RequestMapping("/resetvip.do")
	public void resetvip(HttpServletRequest request, HttpServletResponse response, String checkid, String orderuser) {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String branchId = staff.getBranchId();
		Member member = (Member) roomService.findOneByProperties(Member.class, "memberId", orderuser);
		Check check = (Check) roomService.findOneByProperties(Check.class, "checkId", checkid);
		Order order = (Order) roomService.findOneByProperties(Order.class, "orderId", checkid);
		if (!StringUtil.isEmpty(order.getActivity())) {
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, "活动价不能重置!"));
		} else {
			String roomType = check.getRoomType();
			String memberRank = member.getMemberRank();
			RoomPriceId roompriceid = new RoomPriceId();
			roompriceid.setBranchId(branchId);
			roompriceid.setRoomType(roomType);
			roompriceid.setStatus("1");
			RoomPrice roomprice = (RoomPrice) roomService.findOneByProperties(RoomPrice.class, "memberRank",
					memberRank, "roomPriceId.branchId", branchId, "roomPriceId.roomType", roomType,
					"roomPriceId.status", "1", "roomPriceId.rpKind", "1");
			double price = roomprice.getRoomPrice();
			if (price == check.getRoomPrice()) {
				JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, "无需重置!"));
			} else {
				double subprice = price - check.getRoomPrice();
				check.setRoomPrice(price);

				RoomPlan roomplan = new RoomPlan();
				String logId = DateUtil.currentDate("yyMMdd") + staff.getBranchId()
						+ roomService.getSequence("SEQ_NEW_ROOMPLAN");
				roomplan.setLogId(logId);
				roomplan.setBranchId(branchId);
				roomplan.setValidDay(new Date());
				roomplan.setRoomId(check.getRoomId());
				roomplan.setRpId(check.getRpId());
				roomplan.setRoomPrice(check.getRoomPrice());
				roomplan.setCashback(subprice);
				roomplan.setStatus(CommonConstants.RoomPlanStatus.Valid);
				roomplan.setRecordUser(staff.getStaffId());
				roomplan.setCheckId(checkid);
				try {
					roomService.save(roomplan);
					roomService.save(check);
					JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.SUCESS, "重置成功!"));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	// 双击显示提示信息详情页面
	@SuppressWarnings("unchecked")
	@RequestMapping("/showPromptRecordDetail.do")
	public ModelAndView showPromptRecordDetail(HttpServletRequest request, String logid, String checkid)
			throws Exception {
		ModelAndView mv = new ModelAndView();
		Pagination pagination = SqlBuilder.buildPagination(request);
		Tips tips = (Tips) roomService.findOneByProperties(Tips.class, "logId", logid);
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginUser.getStaff();
		tips.setReader(staff.getStaffId());
		tips.setReadTime(new Date());
		tips.setStatus(CommonConstants.TipStatus.Readed);
		roomService.update(tips);
		String sql = "loadpromptdetail";
		List<Tips> result = roomService.findBySQLWithPagination(sql, new String[] { checkid, logid }, pagination, true);
		mv.addObject("result", result.get(0));
		mv.addObject("pagination", pagination);
		mv.setViewName("page/ipmspage/order/prompt_details");
		return mv;
	}

	// 统计提示信息日志未阅读数量
	@RequestMapping("/countPrompt.do")
	public void countPrompt(HttpServletRequest request, HttpServletResponse response, String checkid) {
		Order orderdata = (Order) roomService.findOneByProperties(Order.class, "orderId", checkid);
		String sql = "";
		if (orderdata.getStatus().equals(CommonConstants.OrderStatus.NewOrder) ||
				orderdata.getStatus().equals(CommonConstants.OrderStatus.Absent)) {
			sql = "countordertips";
		} else {
			sql = "countPrompt";
		}
		List<?> list = roomService.findBySQL(sql, new String[] { checkid }, true);
		JSONUtil.responseJSON(response, list);
	}

	// 统计提示信息日志未阅读数量
	@RequestMapping("/countPrompt.do")
	public void countPrompt(HttpServletRequest request, HttpServletResponse response, String checkid) {
		Order orderdata = (Order) roomService.findOneByProperties(Order.class, "orderId", checkid);
		String sql = "";
		if (orderdata.getStatus().equals(CommonConstants.OrderStatus.NewOrder) ||
				orderdata.getStatus().equals(CommonConstants.OrderStatus.Absent)) {
			sql = "countordertips";
		} else {
			sql = "countPrompt";
		}
		List<?> list = roomService.findBySQL(sql, new String[] { checkid }, true);
		JSONUtil.responseJSON(response, list);
	}
	
	// 提示信息新增取消操作
	@RequestMapping("/cancelPromptRecord.do")
	public void cancelPromptRecord(HttpServletRequest request, HttpServletResponse response, String logid) {
		Tips tip = (Tips) roomService.findOneByProperties(Tips.class, "logId", logid);
		tip.setStatus(CommonConstants.TipStatus.Cancel);
		try {
			roomService.update(tip);
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.SUCESS, "取消成功"));
		} catch (DAOException e) {
			e.printStackTrace();
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, "取消失败"));
		}
	}
	
	

	//查询民宿空房、停售房、维修房,脏房
	@RequestMapping("/queryHouseRoomByStatus.do")
	public void queryHouseRoomByStatus(HttpServletRequest request, HttpServletResponse response, String status) {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		//String branchId = loginUser.getStaff().getBranchId();
		List<?> houseList = new ArrayList<Object>();
		if (("empty").equals(status)) {
			houseList = roomService.findBySQL("queryhouseempty", new String[] { loginUser.getStaff().getStaffId() }, true);
		} else if (("dirty").equals(status)) {
			houseList = roomService.findBySQL("queryhousedirty", new String[] { loginUser.getStaff().getStaffId() }, true);
		} else if (("stop").equals(status)) {
			houseList = roomService.findBySQL("queryhouesstop", new String[] { loginUser.getStaff().getStaffId() }, true);
		} else if (("maintence").equals(status)) {
			houseList = roomService.findBySQL("queryhousemaintence", new String[] { loginUser.getStaff().getStaffId() }, true);
		}
		JSONUtil.responseJSON(response, houseList);
	}
	
	//根据民宿名称查询
	@RequestMapping("/queryByHouseName.do")
	public void queryByHouseName(HttpServletRequest request, HttpServletResponse response, String houseName) {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		String newhousename = "%" + houseName + "%";
		List<?> houseList = roomService.findBySQL ("querybyhousename", new String[] { loginUser.getStaff().getStaffId(), 
				newhousename,newhousename,loginUser.getStaff().getStaffId(), newhousename, newhousename}, true);
		JSONUtil.responseJSON(response, houseList);
	}
	
	//将房租计划加到账单列表中
	@RequestMapping("/addroomplan.do")
	public void addroomplan(HttpServletRequest request, HttpServletResponse response, String checkid) {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		List<?> listroomplan = roomService.findByProperties(RoomPlan.class, "checkId", checkid, "branchId", branchId);
		List <Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
		for (Object object : listroomplan) {
			Map<String, Object> maproomplan = new HashMap<String, Object>(); 
			RoomPlan roomplan = (RoomPlan) object;
			Staff staff = (Staff) roomService.findOneByProperties(Staff.class, "staffId", roomplan.getRecordUser());
			maproomplan.put("staffName", staff.getStaffName());
			maproomplan.put("recordTime", DateUtil.d2s(roomplan.getRecordTime(), "yyyy/MM/dd HH:mm"));
			maproomplan.put("data", roomplan);
			listmap.add(maproomplan);
		}
		JSONUtil.responseJSON(response, listmap);
	}
	
	@RequestMapping("/addRemarkToBill.do")
	public void addRemarkToBill(HttpServletRequest request,HttpServletResponse response){
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String billId = request.getParameter("billId");
		String remark = request.getParameter("remark");
		Bill bill = (Bill) roomService.findOneByProperties(Bill.class, "billId",billId);
		bill.setRemark(remark);
//		bill.setRecordUser(loginUser.getStaff().getStaffId());
//		bill.setRecordTime(new Date());
		try {
			roomService.update(bill);
			JSONUtil.responseJSON(response, new AjaxResult(1,"修改成功!"));
		} catch (Exception e) {
			e.printStackTrace();
			JSONUtil.responseJSON(response, new AjaxResult(2,"操作异常!"));
		}
	}
	*//**
	 * 查看酒店账单对应的房单的状态
	 *//*
	@RequestMapping("/queryCheckStatus.do")
	public void queryCheckStatus(HttpServletRequest request,HttpServletResponse response){
		String billId = request.getParameter("billId");
		Bill bill = (Bill) roomService.findOneByProperties(Bill.class, "billId",billId);
		Check check = (Check) roomService.findOneByProperties(Check.class, "checkId", bill.getCheckId());
		//不能操作
		if(check.getStatus().equals(CommonConstants.CheckStatus.CheckOff) || check.getStatus().equals(CommonConstants.CheckStatus.CheckLeave)){
			JSONUtil.responseJSON(response, new AjaxResult(1,"离店账单,无法操作!"));
		}else{
			JSONUtil.responseJSON(response, new AjaxResult(2,bill.getRemark()));
		}
	}
	
	*//**
	 * 查看酒店民宿账单对应的订单的状态
	 *//*
	@RequestMapping("/queryOrderStatus.do")
	public void queryOrderStatus(HttpServletRequest request,HttpServletResponse response){
		String billId = request.getParameter("billId");
		Bill bill = (Bill) roomService.findOneByProperties(Bill.class, "billId",billId);
		Order order = (Order) roomService.findOneByProperties(Order.class, "orderId", bill.getCheckId());
		//不能操作
		if(order.getStatus().equals(CommonConstants.OrderStatus.CheckOff) || order.getStatus().equals(CommonConstants.OrderStatus.Cancel) || order.getStatus().equals(CommonConstants.OrderStatus.Delete)){
			JSONUtil.responseJSON(response, new AjaxResult(1,"失效订单,无法操作!"));
		}else{
			JSONUtil.responseJSON(response, new AjaxResult(2,bill.getRemark()));
		}
	}
	*//**
	 * 查看公寓账单对应的合同的状态
	 *//*
	@RequestMapping("/queryContrartStatus.do")
	public void queryContrartStatus(HttpServletRequest request,HttpServletResponse response){
		String billId = request.getParameter("billId");
		Bill bill = (Bill) roomService.findOneByProperties(Bill.class, "billId",billId);
		Contrart ct = (Contrart) roomService.findOneByProperties(Contrart.class, "contrartId", bill.getCheckId());
		//不能操作
		if(ct.getStatus().equals(CommonConstants.ApartmentStatus.Cancel) || ct.getStatus().equals(CommonConstants.CheckStatus.CheckOff)){//取消,退租
			JSONUtil.responseJSON(response, new AjaxResult(1,"失效账单,无法操作!"));
		}else{
			JSONUtil.responseJSON(response, new AjaxResult(2,bill.getRemark()));
		}
	}
	*//**
	 * 修改酒店账单备注页面
	 * @param request
	 * @param response
	 * @return
	 *//*
	@RequestMapping("/reWriteRemark.do")
	public ModelAndView reWriteRemark(HttpServletRequest request,HttpServletResponse response){

		String billId = request.getParameter("billId");
		String text = request.getParameter("text");
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/ipmspage/room_statistics/reWriteRemark");
		mv.addObject("text", text);
		mv.addObject("billId",billId);
		return mv;

	} 
	*//**
	 * 修改公寓账单备注页面
	 * @param request
	 * @param response
	 * @return
	 *//*
	@RequestMapping("/reWriteRemarkAP.do")
	public ModelAndView reWriteRemarkAP(HttpServletRequest request,HttpServletResponse response){

		String billId = request.getParameter("billId");
		String text = request.getParameter("text");
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/ipmspage/leftmenu/apartmentrefund/reWriteRemarkAp");
		mv.addObject("text", text);
		mv.addObject("billId",billId);
		return mv;
	}
	
	*//**
	 * 修改民宿账单备注页面
	 * @param request
	 * @param response
	 * @return
	 *//*
	@RequestMapping("/reWriteRemarkH.do")
	public ModelAndView reWriteRemarkH(HttpServletRequest request,HttpServletResponse response){

		String billId = request.getParameter("billId");
		String text = request.getParameter("text");
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/ipmspage/leftmenu/houserefund/reWriteRemarkH");
		mv.addObject("text", text);
		mv.addObject("billId",billId);
		return mv;
	}
	*//**
	 * 查看公寓民宿工作张备注信息
	 * @param request
	 * @param response
	 *//*
	@RequestMapping("/queryWorkbillRemark.do")
	public void queryWorkbillRemark(HttpServletRequest request,HttpServletResponse response){
		String detailId = request.getParameter("billId");
		WorkBillDetail wkbilldetail =  (WorkBillDetail) roomService.findOneByProperties(WorkBillDetail.class, "detailId",detailId);
		if(wkbilldetail != null){
			JSONUtil.responseJSON(response, new AjaxResult(2,wkbilldetail.getRemark()));
		}
	}
	*//**
	 * 跳转修改页面
	 * @return
	 * @throws UnsupportedEncodingException 
	 *//*
	 @RequestMapping("/reWriteWKRemark.do")
	public ModelAndView reWriteWKRemarkAp(HttpServletRequest request) throws UnsupportedEncodingException {		
		String workBillId = request.getParameter("billId");
		String text = request.getParameter("text");
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/ipmspage/work_account/reWriteWKRemark");
		mv.addObject("text",text);
		mv.addObject("workBillId",workBillId);
		return mv;
	}
	*//**
	 * 添加工作账单备注
	 * @param request
	 * @param response
	 *//*
	 @RequestMapping("/addRemarkToworkBillDetail.do")
	public void addRemarkToworkBillDetail(HttpServletRequest request,HttpServletResponse response){
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String billId = request.getParameter("billId");
		String remark = request.getParameter("remark");
		WorkBillDetail billDetail = (WorkBillDetail) roomService.findOneByProperties(WorkBillDetail.class, "detailId",billId);
		billDetail.setRemark(remark);
		try {
			roomService.update(billDetail);
			JSONUtil.responseJSON(response, new AjaxResult(1,"修改成功!"));
		} catch (Exception e) {
			e.printStackTrace();
			JSONUtil.responseJSON(response, new AjaxResult(2,"操作异常!"));
		}
	}
	
	*//**
	 * 
	 * 拆分账界面
	 * 
	 *//*
	@RequestMapping("/spitAccountPage.do")
	public ModelAndView addRemarkToworkBillDetail(HttpServletRequest request,HttpServletResponse response, String checkId, String strbillId)throws Exception {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		ModelAndView mv = new ModelAndView();
		Bill bill = (Bill) roomService.findOneByProperties(Bill.class, "billId", strbillId, "checkId", checkId, 
				"status", CommonConstants.BillStatus.BillNormal, "branchId", loginUser.getStaff().getBranchId());
		mv.addObject("checkid", checkId);
		mv.addObject("strbillid", strbillId);
		if (Double.toString(bill.getCost()).equals("0") || Double.toString(bill.getCost()).equals("0.0") 
				|| Double.toString(bill.getCost()).equals("0.00")) {
			mv.addObject("totalnum", bill.getPay().toString());
		} else {
			mv.addObject("totalnum", bill.getCost().toString());
		}
		mv.setViewName("/page/ipmspage/room_statistics/splitaccount");
		return mv;
	}
	
	*//**
	 * 
	 * 拆分功能
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 * 
	 *//*
	@RequestMapping("/splitAccount.do")
	public void splitAccount(HttpServletRequest request,HttpServletResponse response, Bill billData, String numberone) throws IOException, ClassNotFoundException {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Bill bill = (Bill) roomService.findOneByProperties(Bill.class, "billId", billData.getBillId(), "checkId", billData.getCheckId(), 
				"status", CommonConstants.BillStatus.BillNormal, "branchId", loginUser.getStaff().getBranchId());
		String[] numbers = numberone.split(",");
		Bill newbill = new Bill();
		for (int i=0;i<numbers.length;i++) {
		    newbill = (Bill) CloneUtil.deepClone(bill);//(Bill) bill.clone(); 
			newbill.setBillId(DateUtil.currentDate("yyMMdd") + loginUser.getStaff().getBranchId() + roomService.getSequence("SEQ_NEW_BILL"));
			if (Double.toString(newbill.getCost()).equals("0") || Double.toString(newbill.getCost()).equals("0.0") 
					|| Double.toString(newbill.getCost()).equals("0.00")) {
				newbill.setPay(Double.valueOf(numbers[i]));
			} else {
				newbill.setCost(Double.valueOf(numbers[i]));
			}
			newbill.setRecordTime(new Date());
			newbill.setRecordUser(loginUser.getStaff().getStaffId());
			roomService.save(newbill);
		}
		bill.setStatus(CommonConstants.BillStatus.BillSplitAccount);
		bill.setRemark(bill.getRemark() + billData.getRemark());
		
		try {
			roomService.update(bill);
			JSONUtil.responseJSON(response, new AjaxResult(1,"拆分成功!"));
		} catch (Exception e) {
			e.printStackTrace();
			JSONUtil.responseJSON(response, new AjaxResult(2,"拆分失败!"));
		}
	}
	
	*//**
	 * 
	 * 民宿远期房态
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 * 
	 *//*
	@SuppressWarnings("unchecked")
	@RequestMapping("houseForwardRoom.do")
	public ModelAndView houseForwardRoom(HttpServletRequest request, HttpServletResponse response, String madate,
			String edate) throws Exception {
		ModelAndView mv = new ModelAndView();
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String userid = loginUser.getStaff().getStaffId();
		Pagination pagination = SqlBuilder.buildPagination(request);
		pagination.setRows(15);
		List<Object> list = new ArrayList<Object>();
		List<Object> listdate = new ArrayList<Object>();
		List<Object> listRoom = new ArrayList<Object>();
		List<Object> listmap = new ArrayList<Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date mdate = sdf.parse(madate);
		Date eedate = sdf.parse(edate);
		long day = (eedate.getTime()- mdate.getTime())/(24*60*60*1000) + 1;
		Date date = null;
		List<?> rt = roomService.queryHouse(userid, pagination);//huoqu suoyou 管理下的民宿houseID
		
		List<Map<String, Object>> newlist = new ArrayList<Map<String, Object>>();
		

		for (int x = 0; x < rt.size(); x++) { 
			date = sdf.parse(madate);
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			Map<String, Object> el = new HashMap<String, Object>();
			el.put("houseId", ((Map)rt.get(x)).get("HOUSEID"));
			List<Object> datelist = new ArrayList<Object>();
			for (int y = 0; y < day; y++) {
				Map<String, Object> dateandstatues = new HashMap<String, Object>();
				String newdate = sdf.format(c.getTime());
				list = roomService.findBySQL("newhouseforward", new String[] {  newdate,
						newdate,  ((Map<?,?>) rt.get(x)).get("houseid").toString(), newdate, newdate, ((Map<?,?>) rt.get(x)).get("houseid").toString(),newdate, newdate, ((Map<?,?>) rt.get(x)).get("houseid").toString(),newdate, ((Map<?,?>) rt.get(x)).get("houseid").toString() }, true);
				c.add(Calendar.DAY_OF_MONTH, 1);
				if (x == 0) {
					listdate.add(newdate);
				}
				String sta = "";
				for (int a = 0; a < list.size(); a++) {
					if(((Map<?,?>) list.get(a)).get("YD") != null){
						sta = "预定";
					}
					if(((Map<?,?>) list.get(a)).get("WX") != null){
						sta = "维修";
					}
					if(((Map<?,?>) list.get(a)).get("ZZ") != null){
						sta = "在住";
					}
					if(((Map<?,?>) list.get(a)).get("TS") != null){
						sta = "停售";
					}
					if("".equals(sta)){
						sta = "空房";
					}
					map.put(newdate, sta);
				}
				dateandstatues.put("date", newdate);
				dateandstatues.put("status", sta);
				datelist.add(dateandstatues);
			}
			el.put("dates", datelist);
			newlist.add(el);
			listRoom.add(((Map<?,?>) rt.get(x)).get("houseid").toString());
			listmap.add(map);
		}
		Calendar instance = Calendar.getInstance();
		instance.setTime(new SimpleDateFormat("yyyy/MM/dd").parse(madate));
		mv.addObject("listdate", listdate);
		mv.addObject("listroomtype", listRoom);
		mv.addObject("newlist", newlist);
		mv.addObject("pagination", pagination);
		mv.addObject("madate", madate);
		mv.addObject("edate", edate);
		mv.setViewName("page/ipmspage/room_statistics/forward_housedetail");
		return mv;
	}*/
}
