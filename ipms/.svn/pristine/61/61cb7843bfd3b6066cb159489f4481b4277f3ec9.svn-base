package com.ideassoft.hotel.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hsqldb.lib.StringUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ideassoft.bean.Campaign;
import com.ideassoft.bean.RoomStatus;
import com.ideassoft.bean.RoomType;
import com.ideassoft.core.annotation.interfaces.RightMethodControl;
import com.ideassoft.core.annotation.interfaces.RightModelControl;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.constants.RightConstants;
import com.ideassoft.core.jdbc.SqlBuilder;
import com.ideassoft.core.page.Pagination;
import com.ideassoft.pms.service.RoomService;
import com.ideassoft.portal.DataDealPortalService;
import com.ideassoft.portal.IDataDealPortal;
import com.ideassoft.price.SinglePrice;
import com.ideassoft.util.DateUtil;
import com.ideassoft.util.JSONUtil;
import com.ideassoft.util.RequestUtil;

@Transactional
@Controller
@RightModelControl( rightModel = RightConstants.RightModel.HOTEL_CONTROL )
public class HotelRoomStatusController {
	
	@Autowired
	private RoomService roomService;
	
//	@SuppressWarnings("unchecked")
//	@RequestMapping("forwardRoom.do")
//	public ModelAndView forwardRoom(HttpServletRequest request, HttpServletResponse response, String madate,
//			String days, String rpId) throws Exception {
//		ModelAndView mv = new ModelAndView();
//		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
//		String branchId = loginUser.getStaff().getBranchId();
//		Pagination pagination = SqlBuilder.buildPagination(request);
//		List<Object> list = new ArrayList<Object>();
//		List<Object> listdate = new ArrayList<Object>();
//		List<Object> listroomtype = new ArrayList<Object>();
//		Map<String, Object> newlistdata = new HashMap<String, Object>();
//		Map<String, List<Object>> listinfo = new HashMap<String, List<Object>>();
//		int day = Integer.parseInt(days);
//		String roomType = null;
//		String sumNum = null;
//		String alltypenum = null;
//		Date date = null;
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
//		List<?> rt = roomService.findByProperties(RoomType.class, "theme", "1", "roomTypeKey.branchId", branchId);
//
//		for (int x = 0; x < rt.size(); x++) {
//			date = sdf.parse(madate);
//			Calendar c = Calendar.getInstance();
//			c.setTime(date);
//			Map<String, Object> newlist = new HashMap<String, Object>();
//
//			for (int y = 0; y < day; y++) {
//				String newdate = sdf.format(c.getTime());
//				list = roomService.findBySQLWithPagination("queryforward", new String[] { branchId, branchId, branchId, newdate,
//						 newdate, branchId, newdate, newdate, branchId, newdate, newdate, branchId }, pagination, true);
//				c.add(Calendar.DAY_OF_MONTH, 1);
//				if (x == 0) {
//					listdate.add(newdate);
//				}
//				for (int a = 0; a < list.size(); a++) {
//					roomType = ((Map<?, ?>) list.get(a)).get("ROOM_TYPE").toString();
//					sumNum = ((Map<?, ?>) list.get(a)).get("SUMNUM").toString();
//					alltypenum = ((Map<?, ?>) list.get(a)).get("TYPENUMS").toString();
//					if (((RoomType) rt.get(x)).getRoomTypeKey().getRoomType().toString().equals(roomType)) {
//						Map<String, Object> oneinfo = new HashMap<String, Object>();
//						oneinfo.put(sumNum, alltypenum);
//						newlist.put(newdate, oneinfo);
//						//List<?> roomprice = roomService.getRoomPrice(((RoomType) rt.get(x)).getRoomType(), branchId, 
//								//newdate, rpId);
//						//newlist.put(((RoomType) rt.get(x)).getRoomName(),roomprice);
//						}
//				}
//				newlistdata.put(((RoomType) rt.get(x)).getRoomName().toString(), newlist);
//			}
//			listroomtype.add(((RoomType) rt.get(x)).getRoomName());
//		}
//		JSONUtil.fromObject(newlistdata);
//		mv.addObject("listdate", listdate);
//		mv.addObject("listinfo", listinfo);
//		mv.addObject("listroomtype", listroomtype);
//		mv.addObject("newlistdata", newlistdata);
//		mv.addObject("pagination", pagination);
//		mv.addObject("madate", madate);
//		mv.addObject("days", days);
//		mv.addObject("rpId", rpId);
//		mv.setViewName("page/ipmshotel/room_statistics/forward_room");
//		return mv;
//	}
	
//	//当前房态统计
//	@SuppressWarnings("unchecked")
//	@RequestMapping("currentRoom.do")
//	public ModelAndView currentRoom(HttpServletRequest request, HttpServletResponse response, String madate)
//			throws Exception {
//		ModelAndView mv = new ModelAndView();
//		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
//		String branchId = loginUser.getStaff().getBranchId();
//		List<Object> list = new ArrayList<Object>();
//		Pagination pagination = SqlBuilder.buildPagination(request);
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
//		SimpleDateFormat sdfs = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//		if (madate == null) {
//			madate = sdf.format(new Date());
//		}
//		String mdate = madate;
//		Date date = sdf.parse(mdate);
//		Date nowdate = new Date();
//
//		Calendar querytime = Calendar.getInstance();
//		Calendar calnowdate = Calendar.getInstance();
//		querytime.setTime(date);
//		calnowdate.setTime(nowdate);
//		querytime.getTime();
//		calnowdate.getTime();
//
//		if (DateUtil.isSameDay(querytime, calnowdate)) {
//			String sql = "querycurrentstatus";
//			list = roomService.findBySQLWithPagination(sql, new String[] { branchId, branchId, branchId }, pagination, true);
//		} else {
//			DateUtil.setEndOfDay(querytime);
//			list = (List<Object>) roomService.getRoomStatus(madate, sdfs.format(querytime.getTime()), branchId, pagination);
//		}
//		mv.addObject("list", list);
//		mv.addObject("pagination", pagination);
//		mv.addObject("madate", madate);
//		mv.setViewName("page/ipmshotel/room_statistics/current_room");
//		return mv;
//	}
	
//	@SuppressWarnings("unchecked")
//	@RequestMapping("queryMyCampaign.do")
//	public ModelAndView queryMyCampaign(HttpServletRequest request, HttpServletResponse response, String madate)
//			throws Exception {
//		ModelAndView mv = new ModelAndView();
//		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
//		String branchId = loginUser.getStaff().getBranchId();
//		List<Campaign> campaign = (List<Campaign>) roomService.findBySQL("querycampaigndata",
//				new String[] { branchId, branchId, branchId }, true);
//		mv.addObject("list", campaign);
//		mv.setViewName("page/ipmshotel/room_statistics/pagecampaign");
//		return mv;
//	}
	/**
	 * 
	 * @Description 同城房态
	 * @author ideas_mengl
	 * @date   2018年7月24日下午3:30:06
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/queryRoom.do")
	@RightMethodControl(rightType = RightConstants.RightType.HOT_CITYST)
	public ModelAndView queryRoom(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branch = loginUser.getStaff().getBranchId();
		DataDealPortalService service = new DataDealPortalService();
		IDataDealPortal portal = service.getDataDealPortalPort();
		String branchIds = portal.call(1, 1, "{ function: \"roomStatus.queryBranch\", data:{branch:" + branch + " } }");
		String branchId = "";
		try {
			if (!branchIds.equals("-1")) {
				JSONArray jsonArray = new JSONArray(branchIds);
				JSONObject object = null;
				branchId = "";
				for (int i = 0; i < jsonArray.length(); i++) {
					object = jsonArray.getJSONObject(i);
					branchId += " " + object.getString("BRANCH_ID");
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		String room = portal.call(1, 1, "{ function: \"roomStatus.roomSum\", data:{branchId:" + branchId + " } }");
		Map<String, String> roomName = new HashMap<String, String>();
		if (!room.equals("-1")) {
			try {
				if (!room.equals("-1")) {
					JSONArray jsonArray = new JSONArray(room);
					JSONObject object = null;
					for (int i = 0; i < jsonArray.length(); i++) {
						object = jsonArray.getJSONObject(i);
						roomName.put(object.getString("BRANCH_NAME"), object.getString("BRANCH_ID"));
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		mv.setViewName("page/ipmshotel/room_urban/room_urban");
		mv.addObject("roomName", roomName);
		return mv;
	}
	
	@RequestMapping("cityroomstatus.do")
	public ModelAndView cityroomstatus(HttpServletRequest request, HttpServletResponse response) throws ParseException {
		ModelAndView mv = new ModelAndView();
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branch = loginUser.getStaff().getBranchId();
		String branchId = request.getParameter("branchId");
		DataDealPortalService service = new DataDealPortalService();
		IDataDealPortal portal = service.getDataDealPortalPort();
		if (StringUtil.isEmpty(branchId)) {
			String branchIds = portal.call(1, 1, "{ function: \"roomStatus.queryBranch\", data:{branch:" + branch
					+ " } }");
			try {
				if (!branchIds.equals("-1")) {
					JSONArray jsonArray = new JSONArray(branchIds);
					JSONObject object = null;
					branchId = "";
					for (int i = 0; i < jsonArray.length(); i++) {
						object = jsonArray.getJSONObject(i);
						branchId += " " + object.getString("BRANCH_ID");
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		String data = portal.call(1, 1, "{ function: \"roomStatus.queryRoom\", data:{branchId:" + branchId + " } }");
		String room = portal.call(1, 1, "{ function: \"roomStatus.roomSum\", data:{branchId:" + branchId + " } }");
		List<RoomStatus> list = new ArrayList<RoomStatus>();
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> roomName = new HashMap<String, String>();
		try {
			if (!data.equals("-1")) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				JSONArray jsonArray = new JSONArray(data);
				JSONObject object = null;
				for (int i = 0; i < jsonArray.length(); i++) {
					object = jsonArray.getJSONObject(i);
					RoomStatus roomstatus = new RoomStatus();
					roomstatus.setLogId(object.getString("LOG_ID"));
					roomstatus.setBranchId(object.getString("BRANCH_ID"));
					roomstatus.setBranchName(object.getString("BRANCH_NAME"));
					roomstatus.setRoomType(object.getString("ROOM_TYPE"));
					roomstatus.setCount(Integer.parseInt(object.getString("COUNT")));
					roomstatus.setRecordTime(sdf.parse(object.getString("RECORD_TIME")));
					roomstatus.setRecordUser(object.getString("RECORD_USER"));
					roomstatus.setRemark(object.getString("REMARK").equals("null") ? "" : object.getString("REMARK"));
					roomstatus.setCampaigns(object.getString("CAMPAIGNS").equals("null") ? 0 : Integer.parseInt(object
							.getString("CAMPAIGNS")));
					roomstatus.setSellnum(object.getString("SELLNUM").equals("null") ? 0 : Integer.parseInt(object
							.getString("SELLNUM")));
					roomstatus.setStopnum(object.getString("STOPNUM").equals("null") ? 0 : Integer.parseInt(object
							.getString("STOPNUM")));
					roomstatus.setNightnum(object.getString("NIGHTNUM").equals("null") ? 0 : Integer.parseInt(object
							.getString("NIGHTNUM")));
					roomstatus.setValidnum(object.getString("VALIDNUM").equals("null") ? 0 : Integer.parseInt(object
							.getString("VALIDNUM")));
					roomstatus.setInvalidnum(object.getString("INVALIDNUM").equals("null") ? 0 : Integer
							.parseInt(object.getString("INVALIDNUM")));
					SinglePrice  mp = new SinglePrice(object.getString("BRANCH_ID"),"1",object.getString("TYPE"),"MSJ","1",new Date());
					Double price = mp.checkRoomPrice();
					roomstatus.setRoomPrice(String.valueOf(price));
					/*roomstatus.setRoomPrice(object.getString("ROOM_PRICE").equals("null") ? "" : object
							.getString("ROOM_PRICE"));*/
					list.add(roomstatus);
				}
				mv.addObject("list", list);
			}
			if (!room.equals("-1")) {
				JSONArray jsonArray = new JSONArray(room);
				JSONObject object = null;
				for (int i = 0; i < jsonArray.length(); i++) {
					object = jsonArray.getJSONObject(i);
					map.put(object.getString("BRANCH_ID"), object.getString("SUM"));
					roomName.put(object.getString("BRANCH_NAME"), object.getString("BRANCH_ID"));
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		mv.setViewName("page/ipmspage/room_urban/cityroomstatus");
		mv.addObject("map", JSONUtil.fromObject(map));
		mv.addObject("roomName", roomName);
		return mv;
	}

}
