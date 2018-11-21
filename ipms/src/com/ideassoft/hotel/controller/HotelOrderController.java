package com.ideassoft.hotel.controller;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hsqldb.lib.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ideassoft.bean.Bill;
import com.ideassoft.bean.Branch;
import com.ideassoft.bean.Check;
import com.ideassoft.bean.CheckUser;
import com.ideassoft.bean.CheckoutLog;
import com.ideassoft.bean.HaltPlan;
import com.ideassoft.bean.House;
import com.ideassoft.bean.Member;
import com.ideassoft.bean.MemberRank;
import com.ideassoft.bean.OffsetLog;
import com.ideassoft.bean.OperateLog;
import com.ideassoft.bean.OrdchePrice;
import com.ideassoft.bean.Order;
import com.ideassoft.bean.Recording;
import com.ideassoft.bean.Room;
import com.ideassoft.bean.RoomStatus;
import com.ideassoft.bean.RoomType;
import com.ideassoft.bean.Staff;
import com.ideassoft.bean.SysParam;
import com.ideassoft.bean.Tips;
import com.ideassoft.bean.wrapper.MultiQueryOrder;
import com.ideassoft.core.ajax.AjaxResult;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.constants.CommonConstants;
import com.ideassoft.core.constants.SystemConstants;
import com.ideassoft.core.constants.CommonConstants.DefaultRoomPrice;
import com.ideassoft.core.exception.DAOException;
import com.ideassoft.core.jdbc.SqlBuilder;
import com.ideassoft.core.notifier.SmsSingleSender;
import com.ideassoft.core.notifier.SmsSingleSenderResult;
import com.ideassoft.core.page.Pagination;
import com.ideassoft.hotel.service.HotelLeftmenuService;
import com.ideassoft.hotel.service.HotelOrderService;
import com.ideassoft.price.MultiPrice;
import com.ideassoft.price.RealPrice;
import com.ideassoft.util.DateUtil;
import com.ideassoft.util.JSONUtil;
import com.ideassoft.util.PriceUtil;
import com.ideassoft.util.ReflectUtil;
import com.ideassoft.util.RequestUtil;
import com.ideassoft.util.RoomUtil;

@Transactional
@Controller
public class HotelOrderController {
	
	@Autowired
	private HotelLeftmenuController leftmenuController;
	
	@Autowired
	private HotelOrderService orderService;
	
	@Autowired
	private HotelLeftmenuService leftmenuService;
	
	@RequestMapping("/orderInfoAll.do")
	public ModelAndView orderInfoAll(HttpServletRequest request, HttpServletResponse reponse, String movement)
			throws Exception {
		String startTime = "";
		String endTime = "";
		ModelAndView mv = new ModelAndView();
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		String memberId = request.getParameter("memberId");
		String selectTime = request.getParameter("selectTime");
		String turnToOldPage = request.getParameter("turnToOldPage");
		String arrivalTime = request.getParameter("arrivalTime");
		String arrtimes = request.getParameter("arrtimes");
		String orderUser = request.getParameter("orderUser");
		String mphone = request.getParameter("mphone");
		
		//Pagination pagination = SqlBuilder.buildPagination(request);
		
		Pagination pagination = (Pagination) ReflectUtil.setBeanFromRequest(request, Pagination.class);
		if (pagination != null && pagination.getPageNum() == null) {
			pagination.setPageNum(1);
			pagination.setRows(18);
		}
		String guarantee = "";
		String limited = "";
		String status = "1";
		List<?> orderinfo = new ArrayList<Object>();

		if (movement == null || movement.equals("undefined")) {
			if(request.getParameter("movement") != null && !request.getParameter("movement").equals("undefined")){
				movement = request.getParameter("movement");
			} else {
				movement = "5";
			};
			
		}
		if(selectTime == null)
			selectTime = "";
		
		if (movement.equals("5")){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			MultiQueryOrder order = new MultiQueryOrder();
			if(turnToOldPage != null && turnToOldPage.equals("turnToOldPage")){
				if(memberId != null)
				order.setMemberId(memberId);
				if(!selectTime.isEmpty()){
					startTime = selectTime;
					/*Calendar c = Calendar.getInstance();
					c.setTime(sdf.parse(selectTime));
					c.add(Calendar.DATE, 1);
					endTime = sdf.format(c.getTime());*/
					endTime = selectTime;
					mv.addObject("selectTime", selectTime);
				}else if(arrivalTime == null || arrivalTime.isEmpty()){
					//Calendar c = Calendar.getInstance();
					startTime = sdf.format(new Date());
					//c.set(Calendar.DATE, c.get(Calendar.DATE) + 1);
					//endTime = sdf.format(c.getTime());
					endTime = startTime;
				} else {
					startTime = arrivalTime;
					endTime = arrtimes;
				}
				
				order.setArrivalTime(startTime);
				order.setArrtimes(endTime);
				order.setOrderUser(orderUser);
				order.setMphone(mphone);
				String[] statuss = new String[]{CommonConstants.OrderStatus.Cancel, CommonConstants.OrderStatus.NewOrder, CommonConstants.OrderStatus.Absent, };
				orderinfo = orderService.queryOrderData(branchId, order, statuss, pagination);
				order.setArrivalTime(arrivalTime);
				order.setArrtimes(arrtimes);
				mv.addObject("orderinfo", orderinfo);
				mv.addObject("multiQueryOrder", order);
				mv.addObject("multiQueryOrderNew", order);
				mv.setViewName("page/ipmshotel/order/orderinfo");
			} else {
				if(memberId == null || memberId.length() != 8)
					memberId = "%";
				String theme = "1";
				
				if(arrivalTime == null || arrivalTime.isEmpty()){
					Calendar c = Calendar.getInstance();
					startTime = sdf.format(new Date());
					//c.set(Calendar.DATE, c.get(Calendar.DATE) + 1);
					//endTime = sdf.format(c.getTime());
					endTime = startTime;
				} else {
					startTime = arrivalTime;
					endTime = arrtimes;
				}
				
				if(orderUser == null || orderUser.isEmpty()){
					orderUser ="%";
				} 
				
				if(mphone == null || mphone.isEmpty()){
					mphone = "%";
				} 
				orderinfo = orderService.findBySQLWithPagination("queryorderNewJSM", new String[] {branchId, theme, endTime, startTime, memberId, mphone, orderUser}, pagination, true);
				order.setArrivalTime(startTime);
				order.setArrtimes(endTime);
				order.setOrderUser(orderUser);
				order.setMphone(mphone);
				order.setMemberId(memberId);
				mv.addObject("orderinfo", orderinfo);
				mv.addObject("multiQueryOrder", order);
				mv.addObject("multiQueryOrderNew", order);
				mv.setViewName("page/ipmshotel/order/orderinfoNew");
			}
		} else {
			if((movement.equals("3"))){
				Date date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
				limited = sdf.format(date);
				orderinfo = orderService.findBySQLWithPagination("queryorderlimited", new String[] { branchId, branchId, guarantee,
						status, limited }, pagination, true);
			}else {
				if (movement.equals("1")) {
					guarantee = "2";
				}
				if (movement.equals("2")) {
					Date date = new Date();
					SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
					limited = sdf.format(date);
				}
				if (movement.equals("4")) {
					status = "2";
				}
				orderinfo = orderService.findBySQLWithPagination("queryorderinfo", new String[] { branchId, branchId, guarantee,
						status, limited }, pagination, true);
			}
			mv.addObject("orderinfo", orderinfo);
			mv.setViewName("page/ipmshotel/order/orderinfo");
			
		} 
		mv.addObject("movement", movement);
		mv.addObject("pagination", pagination);
		return mv;
	}
	
	@RequestMapping("/queryOrderData.do")
	public ModelAndView queryOrderData(HttpServletRequest request, HttpServletResponse response, MultiQueryOrder order, String movement, String turnToOldPage)
			throws Exception {
		String mphone = "";
		String orderusers = "";
		String startTime = "";
		String endTime = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		if (movement == null || movement.equals("undefined")) {
			if(request.getParameter("movement") != null && !request.getParameter("movement").equals("undefined")){
				movement = request.getParameter("movement");
			}
		}
		ModelAndView mv = new ModelAndView();
		Pagination pagination = (Pagination) ReflectUtil.setBeanFromRequest(request, Pagination.class);
		if (pagination != null && pagination.getPageNum() == null) {
			pagination.setPageNum(1);
			pagination.setRows(18);
		}
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd");
		/*if (order.getOrdtimes() != null && !order.getOrdtimes().isEmpty()) {
			cal.setTime(f.parse(order.getOrdtimes()));
			cal.add(Calendar.DATE, 1);
			order.setOrdtimes(f.format(cal.getTime()));
		} 
		if (order.getArrtimes() != null && !order.getArrtimes().isEmpty()) {
			cal.setTime(f.parse(order.getArrtimes()));
			cal.add(Calendar.DATE, 1);
			order.setArrtimes(f.format(cal.getTime()));
		}
		if (order.getLeavetimes() != null && !order.getLeavetimes().isEmpty()) {
			cal.setTime(f.parse(order.getLeavetimes()));
			cal.add(Calendar.DATE, 1);
			order.setLeavetimes(f.format(cal.getTime()));
		}*/
		
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		String[] status = new String[]{CommonConstants.OrderStatus.Cancel, CommonConstants.OrderStatus.NewOrder, CommonConstants.OrderStatus.Absent, };
		List<?> orderinfo = orderService.queryOrderData(branchId, order, status, pagination);
		if(turnToOldPage == null){
			turnToOldPage = "";
		}
		if(movement != null && movement.equals("5")){

			if(order.getMphone() == null || order.getMphone().isEmpty()){
				mphone = "%";
			} else {
				mphone = order.getMphone();
			}
			
			if(order.getOrderUser() == null || order.getOrderUser().isEmpty()){
				orderusers = "%";
			} else {
				orderusers = order.getOrderUser();
			}
			
			if(order.getArrtimes() == null || order.getArrtimes().isEmpty()){
				
				/*Calendar c = Calendar.getInstance();
				c.setTime(new Date());
				c.add(Calendar.DATE, 1);
				endTime = sdf.format(c.getTime());*/
				endTime = null;
			} else {
				endTime = order.getArrtimes();
			}
			
			if(order.getArrivalTime() == null || order.getArrivalTime().isEmpty()){
				//startTime = sdf.format(new Date());
				startTime = null;
			} else {
				startTime = order.getArrivalTime();
			}
			if(turnToOldPage.equals("turnToOldPage")){
				order.setArrivalTime(startTime);
				order.setArrtimes(endTime);
				order.setOrderUser(orderusers);
				order.setMphone(mphone);
				orderinfo = orderService.queryOrderData(branchId, order, status, pagination);
				mv.setViewName("/page/ipmspage/order/orderinfo");
			} else {
				String theme = "1";
				orderinfo = orderService.findBySQLWithPagination("queryorderNewJSM", new String[] {branchId, theme, endTime, startTime, "%", mphone, orderusers}, pagination, true);
				if(endTime == null && orderinfo.size() > 0){
					String endTimeTem = ((Map<?,?>)(orderinfo.get(0))).get("arrivaltime").toString();
					order.setArrtimes(endTimeTem);
				}
				if(startTime == null && orderinfo.size() > 0){
					String startTimeTem = ((Map<?,?>)(orderinfo.get(orderinfo.size()-1))).get("arrivaltime").toString();
					order.setArrivalTime(startTimeTem);
				}
				mv.setViewName("/page/ipmshotel/order/orderinfoNew");
			}
		}else {
			mv.setViewName("/page/ipmshotel/order/orderinfo");
		}
		mv.addObject("orderinfo", orderinfo);
		mv.addObject("multiQueryOrder", order);
		mv.addObject("multiQueryOrderNew", order);
		mv.addObject("pagination", pagination);
		mv.addObject("movement", movement);
		return mv;
	}
	
	@RequestMapping("/changeorderstatus.do")
	public void changeorderstatus(HttpServletRequest request, HttpServletResponse response, String orderId)
			throws UnknownHostException {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		Order orderdata = (Order) orderService.findOneByProperties(Order.class, "orderId", orderId);
		orderdata.setStatus("2");

		OperateLog operlog = new OperateLog();
		operlog.setBranchId(branchId);
		operlog.setLogId(DateUtil.currentDate("yyMMdd") + branchId + orderService.getSequence("SEQ_OPERATELOG_ID"));
		String operid = InetAddress.getLocalHost().toString();// IP地址
		operid = (String) operid.subSequence(operid.indexOf("/") + 1, operid.length());
		operlog.setOperIp(operid);
		operlog.setOperType("order");
		operlog.setOperModule("修改订单");
		operlog.setContent("订单号：" + orderdata.getOrderId() + "未到");
		operlog.setRecordUser(loginUser.getStaff().getStaffId());
		operlog.setRecordTime(new Date());

		try {
			orderService.save(orderdata);
			orderService.save(operlog);
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONUtil.responseJSON(response, new AjaxResult(0, "设为未到成功!"));
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/selectroom.do")
	public void selectroom(HttpServletRequest request, HttpServletResponse response, String orderId) {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		String flags = "1";
		Order orderdata = (Order) orderService.findOneByProperties(Order.class, "orderId", orderId);
		String rt = orderdata.getRoomType();
		RoomType rtdata = (RoomType) orderService.findOneByProperties(RoomType.class, "theme", "1", "roomTypeKey.roomType", rt, 
				"roomTypeKey.branchId", branchId);
		List<?> list = orderService.findBySQL("queryroomempty", new String[] { branchId, branchId, rt }, true);	
		Map<String, List<Object>> listinfo = new HashMap<String, List<Object>>();
	
		List<Object> listdata = new ArrayList<Object>();
		for (int i = 0; i < list.size(); i++) {
			List<HaltPlan> haltplan = orderService.findByProperties(HaltPlan.class, "branchId", branchId, 
					"roomId", ((Map<?, ?>) list.get(i)).get("ROOMID"), "status", CommonConstants.HaltPlanStatus.VALID);
			for (int j = 0; j <haltplan.size(); j++) {
				boolean flag = DateUtil.isOverlap(new Date[]{orderdata.getArrivalTime(), orderdata.getLeaveTime()}, 
						new Date[]{((HaltPlan) haltplan.get(j)).getStartTime(), ((HaltPlan) haltplan.get(j)).getEndTime()});
				if (flag) {
					flags = "2";
				}
			}
			
			if (flags.equals("1")) {
				listdata.add(((Map<?, ?>) list.get(i)).get("ROOMID"));
			}
		}
		if (listdata.size() <= 0) {
			JSONUtil.responseJSON(response, listdata);
		} else {
			listinfo.put(rtdata.getRoomName(), listdata);
			JSONUtil.responseJSON(response, listinfo);
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/checkIn.do")
	public void checkIn(HttpServletRequest request, HttpServletResponse response, String checkinUser, String orderId,
			String roomId) throws UnknownHostException {
		Double firstprice = null;
		Date lastest;
		String[] checkusers = checkinUser.split(",");
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		Order orderdata = (Order) orderService.findOneByProperties(Order.class, "orderId", orderId, "branchId",
				branchId);
		List<Check> checkdata = orderService.findByProperties(Check.class, "checkId", orderId, "branchId", branchId);
		Room roomdata = (Room) orderService.findOneByProperties(Room.class, "roomKey.roomId", roomId,
				"roomKey.branchId", branchId);
		List<OrdchePrice> ordcheprice = orderService.findByProperties(OrdchePrice.class, "branchId", branchId, 
				"orderId", orderId, "status", "1");
		firstprice = ordcheprice.get(0).getDayPrice();
		lastest = ordcheprice.get(0).getWhichDay();
		for (int j=1; j<ordcheprice.size(); j++) {
			if (ordcheprice.get(j).getWhichDay().before(lastest)) {
				firstprice = ordcheprice.get(j).getDayPrice();
				lastest = ordcheprice.get(j).getWhichDay();
			}
		}

		if (checkdata.size() > 0) {
			JSONUtil.responseJSON(response, new AjaxResult(1, "已经办理过入住了!"));
		} else {
			Date date = new Date();
			Calendar nowDate = Calendar.getInstance();
			Double a = 0.00;
			Bill bill = new Bill();
			Calendar minDate = Calendar.getInstance();
			Calendar maxDate = Calendar.getInstance();
			minDate.set(Calendar.HOUR_OF_DAY,4);
			maxDate.set(Calendar.HOUR_OF_DAY,6);
/*			LoginGetShift shift = (LoginGetShift) request.getSession(true).getAttribute("loginGetShift");
			String billId = DateUtil.currentDate("yyMMdd") + branchId + orderService.getSequence("SEQ_NEW_BILL");
			bill.setBillId(billId);
			bill.setBranchId(branchId);
			bill.setCheckId(orderId);
			bill.setProjectId(CommonConstants.BillProject.Deposit);
			bill.setProjectName("预存");
			bill.setDescribe("预存");
			bill.setCost(a);
			if (orderdata.getAdvanceCash() != null) {
				bill.setPayment("1");
			} else if (orderdata.getAdvanceCard() != null) {
				bill.setPayment("2");
			} else {
				bill.setPayment("0");
			}
			bill.setPay(orderdata.getAdvanceCash() + orderdata.getAdvanceCard());
			bill.setStatus("1");
			bill.setRecordTime(date);
			bill.setRecordUser(loginUser.getStaff().getStaffId());*/
			
			if (orderdata.getAdvanceCash() == null) {
				orderdata.setAdvanceCash(a);
			}
			if (orderdata.getAdvanceCard() == null) {
				orderdata.setAdvanceCard(a);
			}

			Check check = new Check();
			check.setCheckId(orderId);
			check.setBranchId(branchId);
			check.setCheckinTime(date);
			check.setRoomPrice(firstprice);
			check.setRpId(orderdata.getRpId());
			check.setCheckoutTime(orderdata.getLeaveTime());
			check.setCheckUser(checkinUser);
			check.setRoomId(roomId);
			check.setRoomType(roomdata.getRoomType());
			check.setDeposit(a);
			check.setTtv(a);
			check.setCost(a);
			check.setPay(orderdata.getAdvanceCash() + orderdata.getAdvanceCard());
			check.setAccountFee(a);
			check.setTotalFee(a);
			check.setCheckUser(orderdata.getOrderUser());
			check.setStatus(CommonConstants.CheckStatus.CheckOn);
			check.setRecordTime(date);
			check.setRecordUser(loginUser.getStaff().getStaffId());
			check.setRemark(orderdata.getRemark());
			check.setMsj(orderdata.getMsj());
			orderdata.setStatus(CommonConstants.OrderStatus.CheckOn);
			orderdata.setUserCheckin(checkinUser);
			orderdata.setRecordTime(date);
			
			if (minDate.compareTo(nowDate) < 0 && maxDate.compareTo(nowDate) > 0) {
				String billId = DateUtil.currentDate("yyMMdd") + branchId + orderService.getSequence("SEQ_NEW_BILL");
				bill.setBillId(billId);
				bill.setBranchId(branchId);
				bill.setCheckId(orderId);
				bill.setProjectId(CommonConstants.BillProject.RoomPrice);
				bill.setProjectName("房费");
				bill.setDescribe("房费");
				bill.setCost(firstprice);
				bill.setPayment(CommonConstants.BillPayment.NonPayment);
				bill.setPay(orderdata.getAdvanceCash() + orderdata.getAdvanceCard());
				bill.setStatus("1");
				bill.setRecordTime(date);
				bill.setRecordUser(loginUser.getStaff().getStaffId());
			}

			/*for (int i = 0; i < checkusers.length; i++) {
				Member member = (Member) orderService.findOneByProperties(Member.class, "memberId", checkusers[i]);
				CheckUser checkuser = new CheckUser();
				checkuser.setCheckId(orderId);
				checkuser.setCheckuserId(orderService.getSequence("SEQ_NEW_CHECKUSER"));
				
				checkuser.setCheckuserName(member.getMemberName());
				if (i == 0) {
					checkuser.setCheckuserType("1");
				} else {
					checkuser.setCheckuserType("2");
				}
				checkuser.setGender(member.getGendor());
				checkuser.setIdcard(member.getIdcard());
				checkuser.setMobile(member.getMobile());
				checkuser.setRecordTime(new Date());
				checkuser.setRecordUser(loginUser.getStaff().getStaffId());
				checkuser.setStatus("1");
				orderService.save(checkuser);
				//checkuser.setRemark(remark);
			}*/
			List<CheckUser> checkuser =  orderService.findByProperties(CheckUser.class, "checkId", orderId, "status", "2");
			for (int i=0;i<checkuser.size();i++) {
				checkuser.get(i).setStatus(CommonConstants.CheckUserStatus.ON);
				checkuser.get(i).setRecordTime(new Date());
			}
			
			OperateLog operlog = new OperateLog();
			operlog.setBranchId(branchId);
			operlog.setLogId(DateUtil.currentDate("yyMMdd") + branchId + orderService.getSequence("SEQ_OPERATELOG_ID"));
			String operid = InetAddress.getLocalHost().toString();// IP地址
			operid = (String) operid.subSequence(operid.indexOf("/") + 1, operid.length());
			operlog.setOperIp(operid);
			operlog.setOperType("order");
			operlog.setOperModule("入住操作");
			operlog.setContent("订单号：" + orderId + "已经入住");
			operlog.setRecordUser(loginUser.getStaff().getStaffId());
			operlog.setRecordTime(date);
			
			//记录入住日志
			CheckoutLog cklog = new CheckoutLog();
			String cklogId = DateUtil.currentDate("yyMMdd") + branchId + orderService.getSequence("SEQ_CHECKOUTlOG_ID");
			cklog.setCheckoutId(cklogId);
			cklog.setBranchId(branchId);
			cklog.setCheckId(orderId);
			cklog.setCheckoutDate(new Date());
			cklog.setRecordUser(loginUser.getStaff().getStaffId());
			cklog.setRemark("订单编号:"+orderId+",已入住!");
			cklog.setType("2");
			roomdata.setStatus(CommonConstants.RoomStatus.RoomChecked);
			roomdata.setRecordTime(new Date());
			
			List<?> listcheckuser = orderService.findByProperties(CheckUser.class, 
					"checkId", orderId, "checkinType", CommonConstants.CheckinType.ORDER,
					"status", CommonConstants.CheckUserStatus.ON);
			
			try {
				if(listcheckuser != null && !listcheckuser.isEmpty()){
					for (Object object : listcheckuser) {
						CheckUser oldcheckuser = (CheckUser) object;
						CheckUser newcheckuser = new CheckUser();
						newcheckuser = oldcheckuser.clone();
						String checkuserId = orderdata.getBranchId() + CommonConstants.OrderSource.Branch + 
						orderService.getSequence("SEQ_NEW_CHECKUSER");
						newcheckuser.setCheckuserId(checkuserId);
						newcheckuser.setCheckinType(CommonConstants.CheckinType.CHECK);
						orderService.save(newcheckuser);
					}
				}
				
				orderService.save(check);
				orderService.update(orderdata);
				orderService.save(operlog);
				orderService.save(cklog);

				//orderService.save(checkuser);
				//roomService.upateroomstatus(branchId, roomId, CommonConstants.RoomStatus.RoomChecked);
				orderService.update(roomdata);
				if (minDate.compareTo(nowDate) < 0 && maxDate.compareTo(nowDate) > 0)
					orderService.save(bill);
			} catch (Exception e) {
				e.printStackTrace();
			}
			JSONUtil.responseJSON(response, new AjaxResult(0, "入住成功!"));
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/queryCheckuser.do")
	public void queryCheckuser(HttpServletRequest request, HttpServletResponse response, String orderId) {
		//LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		//String branchId = loginUser.getStaff().getBranchId();
		Map<String, Object> member = new HashMap<String, Object>();
		List<Map<String, Object>> listmapcheckmember = new ArrayList<Map<String, Object>>();
		List<CheckUser> checkuser = orderService.findByProperties(CheckUser.class, "checkId", orderId );
		for (int i = 0; i < checkuser.size(); i++) {
//			Member memberdata = (Member) orderService.findOneByProperties(Member.class, "idcard", checkuser.get(i).getIdcard());
			Member memberdatas = (Member) orderService.findOneByProperties(Member.class, "mobile", checkuser.get(i).getMobile());
			if (memberdatas != null) {
				MemberRank memberrank = (MemberRank) orderService.findOneByProperties(MemberRank.class, "memberRank", memberdatas.getMemberRank());
				//String idcard = ((CheckUser) checkuser.get(i)).getIdcard().toString();
				member.put("CheckUser", checkuser.get(i));
				member.put("Member", memberdatas);
				member.put("rankname", memberrank.getRankName());
				listmapcheckmember.add(member);
				//checkuser.get(i).pu
			}
		}
		JSONUtil.responseJSON(response, JSONUtil.fromObject(listmapcheckmember));
	}
	
	@RequestMapping("/queryMyIdCard.do")
	public void queryMyIdCard(HttpServletResponse response) {
		Map<String, String> map = orderService.readGuestInfo();
		JSONUtil.responseJSON(response, map);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/loadOrderData.do")
	public void loadOrderData(HttpServletRequest request, HttpServletResponse response, String orderId) {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		String sql = "loadOrderData";
		List<Map<String, Object>> list = orderService.findBySQL(sql, new String[] { orderId, orderId, branchId }, true);
		String sqls = "queryDeposit";
		String advancecard = list.get(0).get("advancecard").toString();
		String advancecash = list.get(0).get("advancecash").toString();
		
		//订单展示总房价
		Order order = (Order)orderService.findOneByProperties(Order.class, "orderId", orderId);
		double a = Double.valueOf(advancecard) + Double.valueOf(advancecash);
		List<?> paylist = orderService.findBySQL(sqls,new String[] { orderId, branchId, CommonConstants.BillProject.Deposit }, true);
		list.get(0).put("ADVANCE", a);
		list.get(0).put("paylist", paylist);
		MultiPrice price = new MultiPrice(branchId, "1", order.getRoomType(), order.getRpId(), RealPrice.PriceType.DATE, order.getArrivalTime(), DateUtil.addDays(order.getLeaveTime(), -1));
		list.get(0).put("PRICE", price.checkRoomPrice());
		JSONUtil.responseJSON(response, list);
	}
	

	
	//查询每天的房价和总房价
	@SuppressWarnings("unchecked")
	public Map<String, Map<String, Object>> queryRoomPrice(HttpServletRequest request, HttpServletResponse response, String orderId, String roomtype) throws ParseException {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		Order orderdata = (Order) orderService.findOneByProperties(Order.class, "orderId", orderId, "branchId", branchId);
		Map<String, Map<String, Object>> priceList = new HashMap<String, Map<String, Object>>();
		Map<String, Object> everyDayPrice = new HashMap<String, Object>();
		Map<String, Object> totalPrices = new HashMap<String, Object>();
		List<List<?>> roomprice = new ArrayList<List<?>>();
		Calendar querytime = Calendar.getInstance();
		Calendar nowtime = Calendar.getInstance();
		
		double totalprice = 0.00;
		Calendar aCalendar = Calendar.getInstance();
		Calendar calendar = Calendar.getInstance();
        aCalendar.setTime(orderdata.getArrivalTime());
        int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
        aCalendar.setTime(orderdata.getLeaveTime());
        int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
        int days = day2-day1;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String date = sdf.format(orderdata.getArrivalTime());
        nowtime.setTime(new Date());
        nowtime.getTime();
		for (int i=0;i<days;i++) {
			double price = 0;
			querytime.setTime(sdf.parse(date));
	        querytime.getTime();
			if ("".equals(orderdata.getRpId())) {
				roomprice = RoomUtil.getNowRoomPrice(branchId, DefaultRoomPrice.DEFAULT_RP_ID, roomtype);
			} else {
				roomprice = RoomUtil.getNowRoomPrice(branchId, orderdata.getRpId(), roomtype);
			}
			price = Double.valueOf(((Map<?, ?>) roomprice.get(0).get(0)).get("ROOMPRICE").toString());
			
			if (!DateUtil.isSameDay(querytime, nowtime)) {
				if ("".equals(orderdata.getRpId())) {
					roomprice = RoomUtil.getForwardRoomPrice(branchId, date, DefaultRoomPrice.DEFAULT_RP_ID, roomtype);
				} else {
					roomprice = RoomUtil.getForwardRoomPrice(branchId, date, orderdata.getRpId(), roomtype);
				}
				if (((Map<?, ?>) roomprice.get(0).get(0)).get("ADJUSTMEM") != null && 
						!((Map<?, ?>) roomprice.get(0).get(0)).get("ADJUSTMEM").equals(0) &&
						!((Map<?, ?>) roomprice.get(0).get(0)).get("ADJUSTMEM").equals(0.0) &&
						!((Map<?, ?>) roomprice.get(0).get(0)).get("ADJUSTMEM").equals(0.00))
				price = Double.valueOf(((Map<?, ?>) roomprice.get(0).get(0)).get("ADJUSTMEM").toString());
			} 
			
			everyDayPrice.put(date, price);
			totalprice += Double.valueOf(price);
			calendar.setTime(sdf.parse(date));
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			date = sdf.format(calendar.getTime()); 
		}
		totalPrices.put("totalPrices", String.format("%.2f", totalprice));
		priceList.put("everyDayPrice", everyDayPrice);
		priceList.put("totalPrices", totalPrices);
		return priceList;
	}
	/**
	 * 
	 * @Description 修改订单查询价格走Roomutil
	 * @author ideas_mengl
	 * @date   2018年7月31日上午10:41:54
	 * @param request
	 * @param response
	 * @param orderId
	 * @param roomtype
	 * @throws ParseException
	 */
	// 根据选择的房型返回总价格
	@RequestMapping("/getRoomPrice.do")
	public void getRoomPrice(HttpServletRequest request, HttpServletResponse response, String orderId, String roomtype) throws ParseException {
//		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
/*		String branchId = loginUser.getStaff().getBranchId();
		Order orderdata = (Order) orderService.findOneByProperties(Order.class, "orderId", orderId);*/
//		Map<String, Map<String, Object>> totalPrice = queryRoomPrice(request, response, orderId, roomtype);
		
		Order orderdata = (Order) orderService.findOneByProperties(Order.class, "orderId", orderId);
		MultiPrice price = new MultiPrice(orderdata.getBranchId(), orderdata.getTheme(), roomtype, orderdata.getRpId(), RealPrice.PriceType.DATE, orderdata.getArrivalTime(), DateUtil.addDays(orderdata.getLeaveTime(), -1));
		
		//JSONUtil.responseJSON(response, totalPrice.get("totalPrices"));
		JSONUtil.responseJSON(response, new AjaxResult(1, "",price.checkRoomPrice()));
	}
	
	//保存每天的房价到ordercheckprice表
	@SuppressWarnings("unchecked")
	public void saveEveryDayPrice(HttpServletRequest request, HttpServletResponse response, String orderId, String roomType) throws ParseException {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginuser.getStaff().getBranchId();
		//Order orderdata = (Order) orderService.findOneByProperties(Order.class, "orderId", orderId, "branchId", branchId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		List<OrdchePrice> newlist = new ArrayList<OrdchePrice>();
		Map<String, Map<String, Object>> priceList = queryRoomPrice(request, response, orderId, roomType);
		
		for (Entry<String, Object> prices: priceList.get("everyDayPrice").entrySet()) {
			OrdchePrice ordChePrice = new OrdchePrice();
			ordChePrice.setBranchId(branchId);
			String dataId = DateUtil.currentDate("yyMMdd") + branchId + CommonConstants.OrderSource.Branch 
				+ orderService.getSequence("SEQ_PRICE_DATAID");
			ordChePrice.setDataId(dataId);
			ordChePrice.setDayPrice(Double.valueOf(prices.getValue().toString()));
			ordChePrice.setOrderId(orderId);
			ordChePrice.setRecordTime(new Date());
			ordChePrice.setRecordUser(loginuser.getStaff().getStaffId());
			ordChePrice.setStatus("1");
			ordChePrice.setWhichDay(sdf.parse(prices.getKey()));
			OrdchePrice oldChePrice = (OrdchePrice) orderService.findOneByProperties(OrdchePrice.class, "orderId", orderId, "whichDay", sdf.parse(prices.getKey()), "status", "1");
			if (oldChePrice != null) {
				oldChePrice.setStatus("0");
				newlist.add(oldChePrice);
				orderService.saveOrUpdateAll(newlist);
			}
			
			try {
				orderService.save(ordChePrice);
			} catch (DAOException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/editorderinfo.do")
	public void editorderinfo(HttpServletRequest request, HttpServletResponse response, Order order)
			throws UnknownHostException, ParseException {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		String roomtype = request.getParameter("roomType");
		OperateLog operlog = new OperateLog();
		Order orderdata = (Order) orderService.findOneByProperties(Order.class, "orderId", order.getOrderId(), "branchId", branchId);
		if (!orderdata.getRoomType().equals(order.getRoomType())) {
//			saveEveryDayPrice(request, response, order.getOrderId(), order.getRoomType());
//			Map<String, Map<String, Object>> priceList = queryRoomPrice(request, response, order.getOrderId(), order.getRoomType());
			MultiPrice price = new MultiPrice(orderdata.getBranchId(), orderdata.getTheme(), roomtype, orderdata.getRpId(), RealPrice.PriceType.DATE, orderdata.getArrivalTime(), DateUtil.addDays(orderdata.getLeaveTime(), -1));

			orderdata.setRoomPrice(price.checkRoomPrice());
		}
		if (!orderdata.getRoomType().equals(order.getRoomType())) {
			operlog.setContent("订单号：" + orderdata.getOrderId() + "修改了房型！");
		}else if(!orderdata.getGuarantee().equals(order.getGuarantee())) {
			operlog.setContent("订单号：" + orderdata.getOrderId() + "修改了担保类型！");
		}else if(!orderdata.getLimited().equals(order.getLimited())) {
			operlog.setContent("订单号：" + orderdata.getOrderId() + "修改了保留时效！");
		} else {
			operlog.setContent("订单号：" + orderdata.getOrderId() + "修改了备注！");
		}
		orderdata.setRoomType(order.getRoomType());
		orderdata.setGuarantee(order.getGuarantee());
		orderdata.setRemark(order.getRemark());
		orderdata.setRecordTime(new Date());
		if (order.getLimited() != null) {
			orderdata.setLimited(order.getLimited());
		}

		
		operlog.setBranchId(branchId);
		operlog.setLogId(DateUtil.currentDate("yyMMdd") + branchId + orderService.getSequence("SEQ_OPERATELOG_ID"));
		String operid = InetAddress.getLocalHost().toString();// IP地址
		operid = (String) operid.subSequence(operid.indexOf("/") + 1, operid.length());
		operlog.setOperIp(operid);
		operlog.setOperType("order");
		operlog.setOperModule("修改订单");
		operlog.setRecordUser(loginUser.getStaff().getStaffId());
		operlog.setRecordTime(new Date());
		OrdchePrice orderprice = (OrdchePrice) orderService.findOneByProperties(OrdchePrice.class, "orderId", orderdata.getOrderId(), "status", "1");
		orderprice.setStatus("0");;
		try {
			leftmenuController.saveEveryDayPrice(request, response, orderdata.getOrderId(), roomtype);
			orderService.update(orderdata);
			orderService.save(orderprice);
			//orderService.update(ordercheck);
			orderService.save(operlog);
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.SUCESS, "修改成功"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/hotelloadsearchroomtype.do")
	public void loadsearchroomtype(HttpServletRequest request, HttpServletResponse response, String checkid,
			String strguest) {
		try {
			LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
			String branchId = loginuser.getStaff().getBranchId();
			List<?> roomtype = leftmenuService.getRoomtype(branchId);
			JSONUtil.responseJSON(response, roomtype);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/loadOrderPromptData.do")
	public ModelAndView loadOrderPrompt(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		Pagination pagination = SqlBuilder.buildPagination(request);
		pagination.setRows(13);
		String checkid = request.getParameter("checkid");
		Order orderdata = (Order) orderService.findOneByProperties(Order.class, "orderId", checkid);
		String sql = "";
		if (orderdata.getStatus().equals(CommonConstants.OrderStatus.NewOrder) || 
				orderdata.getStatus().equals(CommonConstants.OrderStatus.Cancel) || 
				orderdata.getStatus().equals(CommonConstants.OrderStatus.Absent)) {
			sql = "queryordertips";
			List<Tips> result = orderService.findBySQLWithPagination(sql, new String[] { checkid }, pagination, true);
			mv.addObject("result", result);
			mv.addObject("checkid", checkid);
			mv.addObject("pagination", pagination);
			mv.setViewName("page/ipmshotel/order/orderPrompt");
			return mv;
		} else {
			sql = "loadorderprompt";
			List<Tips> result = orderService.findBySQLWithPagination(sql, new String[] { checkid }, pagination, true);
			mv.addObject("result", result);
			mv.addObject("checkid", checkid);
			mv.addObject("pagination", pagination);
			mv.setViewName("page/ipmshotel/order/prompt");
			return mv;
		}
	}
	
	@RequestMapping("/showOrderPromptRecord.do")
	public ModelAndView showOrderPromptRecord(HttpServletRequest request) {
		String checkid = request.getParameter("checkId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("checkid", checkid);
		mv.setViewName("/page/ipmshotel/order/addOrderPrompt");
		return mv;
	}
	
	// 双击显示订单提示信息详情页面
	@SuppressWarnings("unchecked")
	@RequestMapping("/showOrderPromptDetail.do")
	public ModelAndView showOrderPromptDetail(HttpServletRequest request, String logid, String checkId)
			throws Exception {
		ModelAndView mv = new ModelAndView();
		Pagination pagination = (Pagination) ReflectUtil.setBeanFromRequest(request, Pagination.class);
		if (pagination != null && pagination.getPageNum() == null) {
			pagination.setPageNum(1);
			pagination.setRows(13);
		}
		Tips tips = (Tips) orderService.findOneByProperties(Tips.class, "logId", logid, "checkId", checkId, "type", "1");
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		if (tips.getStatus().equals(CommonConstants.TipStatus.Add)) {
			OperateLog operlog = new OperateLog();
			operlog.setBranchId(loginUser.getStaff().getBranchId());
			operlog.setLogId(DateUtil.currentDate("yyMMdd") + loginUser.getStaff().getBranchId() + orderService.getSequence("SEQ_OPERATELOG_ID"));
			String operid = InetAddress.getLocalHost().toString();// IP地址
			operid = (String) operid.subSequence(operid.indexOf("/") + 1, operid.length());
			operlog.setOperIp(operid);
			operlog.setOperType("order");
			operlog.setOperModule("订单提示");
			operlog.setContent("订单号：" + checkId + "中，编号为" + tips.getLogId() + "的提示已被工号为：" + loginUser.getStaff().getStaffId() 
					+ "的" + loginUser.getStaff().getStaffName() + "阅读过了");
			operlog.setRecordUser(loginUser.getStaff().getStaffId());
			operlog.setRecordTime(new Date());
			orderService.save(operlog);
		}
		
		Staff staff = loginUser.getStaff();
		tips.setReader(staff.getStaffId());
		tips.setReadTime(new Date());
		tips.setStatus(CommonConstants.TipStatus.Readed);
		orderService.update(tips);
		String sql = "loadpromptdetail";
		List<Tips> result = orderService.findBySQLWithPagination(sql, new String[] { checkId, logid }, pagination, true);
		mv.addObject("result", result.get(0));
		mv.addObject("pagination", pagination);
		mv.setViewName("page/ipmshotel/order/orderPromptDetails");
		return mv;
	}
	
	// 订单中提示信息取消操作
	@RequestMapping("/cancelOrderPrompt.do")
	public void cancelOrderPrompt(HttpServletRequest request, HttpServletResponse response, String logid, String checkId) throws UnknownHostException {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Tips tip = (Tips) orderService.findOneByProperties(Tips.class, "logId", logid, "type", "1");
		if (!tip.getStatus().equals(CommonConstants.TipStatus.Cancel)) {
			OperateLog operlog = new OperateLog();
			operlog.setBranchId(loginUser.getStaff().getBranchId());
			operlog.setLogId(DateUtil.currentDate("yyMMdd") + loginUser.getStaff().getBranchId() + orderService.getSequence("SEQ_OPERATELOG_ID"));
			String operid = InetAddress.getLocalHost().toString();// IP地址
			operid = (String) operid.subSequence(operid.indexOf("/") + 1, operid.length());
			operlog.setOperIp(operid);
			operlog.setOperType("order");
			operlog.setOperModule("订单提示");
			operlog.setContent("订单号：" + checkId + "中，编号为" + tip.getLogId() + "的提示已被工号为：" + loginUser.getStaff().getStaffId() 
					+ "的" + loginUser.getStaff().getStaffName() + "取消了");
			operlog.setRecordUser(loginUser.getStaff().getStaffId());
			operlog.setRecordTime(new Date());
			orderService.save(operlog);
			tip.setStatus(CommonConstants.TipStatus.Cancel);
			try {
				orderService.update(tip);
				JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.SUCESS, "取消成功"));
			} catch (DAOException e) {
				e.printStackTrace();
				JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, "取消失败"));
			}
		} else {
			JSONUtil.responseJSON(response, new AjaxResult(3, "已经取消过了!"));
		} 
	}
	
	// 统计提示信息日志未阅读数量
	@RequestMapping("/ordercountPrompt.do")
	public void countPrompt(HttpServletRequest request, HttpServletResponse response, String checkid) {
		Order orderdata = (Order) orderService.findOneByProperties(Order.class, "orderId", checkid);
		String sql = "";
		if (orderdata.getStatus().equals(CommonConstants.OrderStatus.NewOrder) ||
				orderdata.getStatus().equals(CommonConstants.OrderStatus.Absent)) {
			sql = "countordertips";
		} else {
			sql = "countPrompt";
		}
		List<?> list = orderService.findBySQL(sql, new String[] { checkid }, true);
		JSONUtil.responseJSON(response, list);
	}
	
	@RequestMapping("/showBillPage.do")
	public ModelAndView showBillPage(HttpServletRequest request, HttpServletResponse response) {
		String checkid = request.getParameter("orderId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("checkid", checkid);
		mv.setViewName("/page/ipmshotel/order/orderbill");
		return mv;
	}
	
	@RequestMapping("/loadOrderBill.do")
	public void loadOrderBill(HttpServletRequest request, HttpServletResponse  response, String orderId, String status) {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginuser.getStaff().getBranchId();
		String sql = "loadorderbill";
		List<?> alllist = orderService.findBySQL(sql, new String[] { orderId, branchId, null}, true);
		List<?> normallist = orderService.findBySQL(sql, new String[] { orderId, branchId, "1" }, true);
		List<?> list = orderService.findBySQL(sql, new String[] { orderId, branchId, status }, true);
		if (alllist.size() <= normallist.size()) {
			JSONUtil.responseJSON(response, new AjaxResult(0, "账单无冲减记录!", list));
		} else {
			JSONUtil.responseJSON(response, new AjaxResult(1, "", list));
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/loadPay.do")
	public void loadPay(HttpServletRequest request, HttpServletResponse response, String orderId) {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String branchId = staff.getBranchId();
		double pay = 0.00;
		double cost = 0.00;
		String sql = "queryorderpay";
		List<Map<String, BigDecimal>> payresult = orderService.findBySQL(sql, new String[] { orderId, branchId },
				true);
		Map<String, Double> mappayandcost = new HashMap<String, Double>();
		for (int i = 0; i < payresult.size(); i++) {
			pay = pay + Double.valueOf(payresult.get(i).get("TOTALPAY").toString());
			cost = cost + Double.valueOf(payresult.get(i).get("TOTALCOST").toString());
			
		}
		mappayandcost.put("pay", pay);
		mappayandcost.put("cost", cost);
		mappayandcost.put("bookvalue", cost-pay);
		//mappayandcost.put("cost", checkallamount.getCost());
		//mappayandcost.put("subprice", checkallamount.getSubPrice());
	    JSONUtil.responseJSON(response, mappayandcost);
	}
	
	/**
	 * 查看酒店民宿账单对应的订单的状态
	 */
	@RequestMapping("/queryOrderStatus.do")
	public void queryOrderStatus(HttpServletRequest request,HttpServletResponse response){
		String billId = request.getParameter("billId");
		Bill bill = (Bill) orderService.findOneByProperties(Bill.class, "billId",billId);
		Order order = (Order) orderService.findOneByProperties(Order.class, "orderId", bill.getCheckId());
		//不能操作
		if(order.getStatus().equals(CommonConstants.OrderStatus.CheckOff) || order.getStatus().equals(CommonConstants.OrderStatus.Cancel) || order.getStatus().equals(CommonConstants.OrderStatus.Delete)){
			JSONUtil.responseJSON(response, new AjaxResult(1,"失效订单,无法操作!"));
		}else{
			JSONUtil.responseJSON(response, new AjaxResult(2,bill.getRemark()));
		}
	}
	
	/**
	 * 修改酒店账单备注页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/orderreWriteRemark.do")
	public ModelAndView reWriteRemark(HttpServletRequest request,HttpServletResponse response){

		String billId = request.getParameter("billId");
		String text = request.getParameter("text");
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/ipmspage/room_statistics/reWriteRemark");
		mv.addObject("text", text);
		mv.addObject("billId",billId);
		return mv;

	}
	
	@RequestMapping("/orderaddRemarkToBill.do")
	public void addRemarkToBill(HttpServletRequest request,HttpServletResponse response){
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String billId = request.getParameter("billId");
		String remark = request.getParameter("remark");
		Bill bill = (Bill) orderService.findOneByProperties(Bill.class, "billId",billId);
		bill.setRemark(remark);
//		bill.setRecordUser(loginUser.getStaff().getStaffId());
//		bill.setRecordTime(new Date());
		try {
			orderService.update(bill);
			JSONUtil.responseJSON(response, new AjaxResult(1,"修改成功!"));
		} catch (Exception e) {
			e.printStackTrace();
			JSONUtil.responseJSON(response, new AjaxResult(2,"操作异常!"));
		}
	}
	
	@RequestMapping("/orderconsumption.do")
	public void orderconsumption(HttpServletRequest request, HttpServletResponse response, String strbillid, String offset, String orderId) {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		//Check check = (Check) roomService.findById(Check.class, checkid);
		Staff staff = loginuser.getStaff();
		String[] arrbill = strbillid.split(",");
		boolean errorbillflag = true;
		for (String billid : arrbill) {
			Bill bill = (Bill) orderService.findOneByProperties(Bill.class, "billId", billid, "checkId", orderId);
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
			JSONUtil.responseJSON(response, new AjaxResult(0, "选中了错误的冲减金额!"));
			return;
		}

		for (String billid : arrbill) {
			Bill consumebill = new Bill();
			Bill bill = (Bill) orderService.findOneByProperties(Bill.class, "billId", billid, "checkId", orderId);
			String billId = DateUtil.currentDate("yyMMdd") + bill.getBranchId()
					+ orderService.getSequence("SEQ_NEW_BILL");
			consumebill.setBillId(billId);
			consumebill.setBranchId(bill.getBranchId());
			consumebill.setCheckId(bill.getCheckId());
			consumebill.setDescribe(bill.getDescribe());
			consumebill.setOffset(offset);
			consumebill.setPayment(bill.getPayment());
			consumebill.setProjectId(bill.getProjectId());
			consumebill.setProjectName(bill.getProjectName());
			consumebill.setRecordUser(staff.getStaffId());
			consumebill.setRemark(bill.getBillId());
			consumebill.setType("1");
			consumebill.setStatus("2");

			String projectid = bill.getProjectId();
			// 记日志
			OffsetLog otlog = new OffsetLog();
			String logid = DateUtil.currentDate("yyMMdd") + bill.getBranchId()
					+ orderService.getSequence("SEQ_OFFSETLOG_ID");
			otlog.setLogId(logid);
			otlog.setBranchId(bill.getBranchId());
			otlog.setCheckId(bill.getCheckId());
			otlog.setLastBillId(bill.getBillId());
			otlog.setCurrBillId(consumebill.getBillId());
			otlog.setRecordUser(staff.getStaffId());
			otlog.setRecordTime(new Date());
			otlog.setOffsetType("3");

			if (projectid.startsWith("2")) {
				double price = bill.getPay();
				if (price == 0) {
					JSONUtil.responseJSON(response, new AjaxResult(0, "选中了错误的冲减金额!"));
					return;
				}
				consumebill.setPay(-price);
				consumebill.setCost(0.00);
				otlog.setOffsetFee(bill.getPay());// 记冲减金额
			} else {
				double price = bill.getCost();
				if (price == 0) {
					JSONUtil.responseJSON(response, new AjaxResult(0, "选中了错误的冲减金额!"));
					return;
				}
				consumebill.setCost(-price);
				consumebill.setPay(0.00);
				otlog.setOffsetFee(bill.getCost()); // 记冲减金额
			}
			bill.setStatus("2");

			try {
				orderService.save(consumebill);
				orderService.save(bill);
				orderService.save(otlog);// 记冲减日志
				JSONUtil.responseJSON(response, new AjaxResult(1, "冲减成功!"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	//订单日志
	@RequestMapping("/showOrderLog.do")
	public ModelAndView showOrderLog(HttpServletRequest request, HttpServletResponse response, String type,
			String orderId) throws Exception {
		ModelAndView mv = new ModelAndView();
		// LoginUser loginUser =
		// (LoginUser)request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
	///	Pagination pagination = SqlBuilder.buildPagination(request);
	//	List<?> result = roomService.getLog(orderId, type, pagination);
		mv.addObject("orderId", orderId);
		mv.addObject("type", type);
	//	mv.addObject("result", result);
	//	mv.addObject("pagination", pagination);
		mv.setViewName("page/ipmshotel/order/orderLog");
		return mv;
	}
	
	//订单中日志
	@RequestMapping("/loadOrderLog.do")
	public void loadOrderLog(HttpServletRequest request, HttpServletResponse response, String orderId) {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String newId = "%" + orderId + "%";
		List<?> orderlog = orderService.findBySQL("queryorderlog", new String[] { newId, "order"}, true);
		JSONUtil.responseJSON(response, orderlog);
	}
	
	//返回上一页
	@RequestMapping("/orderInfoAllNew.do")
	public ModelAndView orderInfoAllNew(HttpServletRequest request, HttpServletResponse reponse, String movement)throws Exception {
		String startTime = "";
		String endTime = "";
		ModelAndView mv = new ModelAndView();
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		String memberId = request.getParameter("memberId");
		String selectTime = request.getParameter("selectTime");
		String arrivalTime = request.getParameter("arrivalTime");
		String arrtimes = request.getParameter("arrtimes");
		String orderUser = request.getParameter("orderUser");
		String mphone = request.getParameter("mphone");
		
		//Pagination pagination = SqlBuilder.buildPagination(request);
		
		Pagination pagination = (Pagination) ReflectUtil.setBeanFromRequest(request, Pagination.class);
		if (pagination != null && pagination.getPageNum() == null) {
			pagination.setPageNum(1);
			pagination.setRows(18);
		}

		List<?> orderinfo = new ArrayList<Object>();

		if (movement == null || movement.equals("undefined")) {
			if(request.getParameter("movement") != null && !request.getParameter("movement").equals("undefined")){
				movement = request.getParameter("movement");
			} else {
				movement = "5";
			};
			
		}
		if(selectTime == null)
			selectTime = "";
		
		if (movement.equals("5")){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			MultiQueryOrder order = new MultiQueryOrder();
			
				if(memberId == null || memberId.length() != 8)
					memberId = "%";
				String theme = "1";
				
				if(arrivalTime == null || arrivalTime.isEmpty()){
					Calendar c = Calendar.getInstance();
					startTime = sdf.format(new Date());
					c.set(Calendar.DATE, c.get(Calendar.DATE) + 1);
					endTime = sdf.format(c.getTime());
				} else {
					startTime = arrivalTime;
					endTime = arrtimes;
				}
				
				if(orderUser == null || orderUser.isEmpty()){
					orderUser ="%";
				} 
				
				if(mphone == null || mphone.isEmpty()){
					mphone = "%";
				} 
				orderinfo = orderService.findBySQLWithPagination("queryorderNewJSM", new String[] {branchId, theme, endTime, startTime, memberId, mphone, orderUser}, pagination, true);
				order.setArrivalTime(startTime);
				order.setArrtimes(endTime);
				order.setOrderUser(orderUser);
				order.setMphone(mphone);
				order.setMemberId(memberId);
				mv.addObject("orderinfo", orderinfo);
				mv.addObject("multiQueryOrder", order);
				mv.setViewName("page/ipmshotel/order/orderinfoNew");
			
		}
		mv.addObject("movement", movement);
		mv.addObject("pagination", pagination);
		return mv;
	}
	
	@RequestMapping("/addOrderBill.do")
	public void addOrderBill(HttpServletRequest request, HttpServletResponse response, String checkId, String project,
			String projectid, String amount, String remark) throws UnknownHostException {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String source = CommonConstants.OrderSource.Branch;
		String branchId = staff.getBranchId();
		Bill bill = new Bill();
		String billId = DateUtil.currentDate("yyMMdd") + branchId + source + orderService.getSequence("SEQ_NEW_BILL");
		bill.setBillId(billId);
		bill.setBranchId(branchId);
		bill.setCheckId(checkId);
		bill.setProjectId(projectid);// gai
		bill.setProjectName(project);
		bill.setDescribe("入账");
		bill.setType("1");
		if (projectid.startsWith("2")) {
			if ("2001".equals(projectid)) {
				bill.setPay(-Double.valueOf(amount));
			} else {
				bill.setPay(Double.valueOf(amount));
			}
			bill.setCost(0.00);
		} else {
			bill.setCost(Double.valueOf(amount));
			bill.setPay(0.00);
		}
		if (CommonConstants.BillProject.CashOutlay.equals(projectid)) {
			bill.setPayment(CommonConstants.BillPayment.Cash);
		} else if (CommonConstants.BillProject.Cash.equals(projectid)) {
			bill.setPayment(CommonConstants.BillPayment.Cash);
		} else if (CommonConstants.BillProject.BankCard.equals(projectid)) {
			bill.setPayment(CommonConstants.BillPayment.Bankcard);
		} else {
			bill.setPayment(CommonConstants.BillPayment.NonPayment);
		}
		bill.setStatus("1");
		bill.setRemark(remark);
		bill.setRecordUser(staff.getStaffId());

		Recording recording = new Recording();
		String recordId = DateUtil.currentDate("yyMMdd") + branchId + orderService.getSequence("SEQ_NEW_RECORDING");
		recording.setRecordId(recordId);
		recording.setBranchId(branchId);
		recording.setCheckId(checkId);
		recording.setRecordType("1");
		recording.setProjectId(projectid);
		recording.setFee(Double.valueOf(amount));
		recording.setRecordUser(staff.getStaffId());
		recording.setRecordTime(new Date());
		
		OperateLog operlog = new OperateLog();
		operlog.setBranchId(branchId);
		operlog.setLogId(DateUtil.currentDate("yyMMdd") + branchId + orderService.getSequence("SEQ_OPERATELOG_ID"));
		String operid = InetAddress.getLocalHost().toString();// IP地址
		operid = (String) operid.subSequence(operid.indexOf("/") + 1, operid.length());
		operlog.setOperIp(operid);
		operlog.setOperType("order");
		operlog.setOperModule("订单入账");
		operlog.setContent("订单号：" + checkId + "新入了一笔账单，账单号为：" + billId);
		operlog.setRecordUser(loginuser.getStaff().getStaffId());
		operlog.setRecordTime(new Date());

		try {
			orderService.save(bill);
			orderService.save(recording);
			orderService.save(operlog);
			JSONUtil.responseJSON(response, new AjaxResult(1, "入账成功!"));
		} catch (Exception e) {
			e.printStackTrace();
			JSONUtil.responseJSON(response, new AjaxResult(0, "入账失败!"));
		}
	}
	

}