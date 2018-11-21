package com.ideassoft.pmsinhouse.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.hsqldb.lib.StringUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ideassoft.bean.Bill;
import com.ideassoft.bean.Branch;
import com.ideassoft.bean.Check;
import com.ideassoft.bean.CheckUser;
import com.ideassoft.bean.DataSource;
import com.ideassoft.bean.House;
import com.ideassoft.bean.Member;
import com.ideassoft.bean.OperateLog;
import com.ideassoft.bean.OrdchePrice;
import com.ideassoft.bean.Order;
import com.ideassoft.bean.PriceApply;
import com.ideassoft.bean.PriceApplyDetail;
import com.ideassoft.bean.RepairApply;
import com.ideassoft.bean.RoomStatus;
import com.ideassoft.bean.SysParam;
import com.ideassoft.bean.templateMessage.HouseOrderCancelMsg;
import com.ideassoft.core.ajax.AjaxResult;
import com.ideassoft.core.annotation.interfaces.RightMethodControl;
import com.ideassoft.core.annotation.interfaces.RightModelControl;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.constants.CommonConstants;
import com.ideassoft.core.constants.RightConstants;
import com.ideassoft.core.constants.SystemConstants;
import com.ideassoft.core.jdbc.SqlBuilder;
import com.ideassoft.core.notifier.SmsSingleSender;
import com.ideassoft.core.notifier.SmsSingleSenderResult;
import com.ideassoft.core.page.Pagination;
import com.ideassoft.pmsinhouse.service.HouseOrderService;
import com.ideassoft.util.DateUtil;
import com.ideassoft.util.JSONUtil;
import com.ideassoft.util.PriceUtil;
import com.ideassoft.util.RequestUtil;
import com.ideassoft.util.RoomUtil;
import com.ideassoft.util.TemplateMessageUtil;



@Transactional
@Controller
@RightModelControl(rightModel = RightConstants.RightModel.HOUSE_CONTROL)
public class HouseOrderController {

	@Autowired
	private HouseOrderService houseOrderService;
	
	

	/*订单全部订单数据展示*/
	@RequestMapping("HouseOrderAll.do")//dgfgdg
	public ModelAndView houseOrderAll(HttpServletRequest request,HttpServletResponse response) throws Exception {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		ModelAndView mv = new ModelAndView();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String arrivaltime = null;
		String arrivaltime2 = null;
		if(request.getParameter("arrivaltime") == null && request.getParameter("arrivaltime2") == null){
			 arrivaltime = request.getParameter("arrivaltime") == null ? sdf.format(new Date()) : request.getParameter("arrivaltime");
			 arrivaltime2 = request.getParameter("arrivaltime2") == null ? sdf.format(new Date()) : request.getParameter("arrivaltime2");
			
		}else{
			 arrivaltime = request.getParameter("arrivaltime");
			 arrivaltime2 = request.getParameter("arrivaltime2");
		}
		String memberName = request.getParameter("memberName");//预订人，中文
		String mobile = request.getParameter("mobile");
		String memberId = request.getParameter("memberId");//会员号
		List<?> check = new ArrayList();
		String checkinPeople = request.getParameter("checkinPeople");//入住人
		String source = request.getParameter("source");
		if("0".equals(source)){
			source = "";
		}
		String roomType = request.getParameter("roomType");
		String recordUser = request.getParameter("recordUser");//操作员，中文
		String bookingTime = request.getParameter("bookingTime");//预定时间
		String orderstatus = request.getParameter("selectstatus");

		if(!StringUtils.isEmpty(bookingTime)){ 
			bookingTime = bookingTime.trim();
		}
		
		
		Pagination pagination = SqlBuilder.buildPagination(request);
        //由于含有未确认订单的情况，重写一条sql防止对后面用到quertHouseOrder语句的代码产生影响
		//List<?> list = houseOrderService.findBySQLWithPagination("quertHouseOrder", new String[] { "", loginUser.getStaff().getStaffId(),
		//		loginUser.getStaff().getStaffId(), memberName, mobile, arrivaltime,arrivaltime2 }, pagination, true);
		List<?> list = houseOrderService.findBySQLWithPagination("quertHouseOrderTwo", new String[] { "", loginUser.getStaff().getStaffId(),
				loginUser.getStaff().getStaffId(), memberName, mobile, arrivaltime,arrivaltime2,source,memberId,roomType,bookingTime,orderstatus }, pagination, true);
		
		if(checkinPeople != null && checkinPeople != ""){
			List<Object> newList = new ArrayList();
			for(int i = 0;i<list.size();i++){
				String id = ((Map<?,?>)list.get(i)).get("ORDER_ID").toString();
				List<CheckUser> checkList = houseOrderService.findByProperties(CheckUser.class, "checkId", id,"status","1");
				for(int j = 0;j<checkList.size();j++){
					String checkUserName = checkList.get(j).getCheckuserName();
					if(checkUserName.indexOf(checkinPeople)!=-1){
						newList.add(list.get(i));//包含
						break;
					}
				}
			}
			list = newList;
		}
		if(recordUser != null && recordUser != ""){
			List<Object> newList = new ArrayList();
			for(int i = 0;i<list.size();i++){
				String id = ((Map<?,?>)list.get(i)).get("SOURCEID").toString();
				if("1".equals(id) || "2".equals(id) ||"4".equals(id) ||"7".equals(id)){
					if("顾客".indexOf(recordUser) != -1){
						newList.add(list.get(i));//包含
					}
				}else{
					String user = ((Map<?,?>)list.get(i)).get("RECORD_USER").toString();
					if(user.indexOf(recordUser) != -1){
						newList.add(list.get(i));//包含
					}
				}
			}
			list = newList;
		}
		mv.addObject("list", list);
		mv.addObject("pagination", pagination);
		mv.addObject("memberName", memberName);
		mv.addObject("mobile", mobile);
		mv.addObject("action", "HouseOrderAll.do");
		mv.setViewName("page/ipmshouse/houseorder/houseorderall");
		return mv;
	}
	
	@RequestMapping("HouseOrderCheckIn.do")
	public ModelAndView houseOrderCheckIn(HttpServletRequest request,HttpServletResponse response) throws Exception {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		ModelAndView mv = new ModelAndView();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String arrivaltime = request.getParameter("arrivaltime") == null ? sdf.format(new Date()) : request.getParameter("arrivaltime");
		String memberName = request.getParameter("memberName");
		String mobile = request.getParameter("mobile");
		Pagination pagination = SqlBuilder.buildPagination(request);
		List<?> list = houseOrderService.findBySQLWithPagination("quertHouseOrder", new String[] { "3", loginUser.getStaff().getStaffId(), memberName, mobile, arrivaltime }, pagination, true);
		mv.addObject("list", list);
		mv.addObject("pagination", pagination);
		mv.addObject("memberName", memberName);
		mv.addObject("mobile", mobile);
		mv.addObject("action", "HouseOrderAll.do");
		mv.setViewName("page/ipmshouse/houseorder/houseorderall");
		return mv;
	}
	
	@RequestMapping("HouseOrderCancel.do")//dfdsfsf
	public ModelAndView houseOrderCancel(HttpServletRequest request,HttpServletResponse response) throws Exception {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		ModelAndView mv = new ModelAndView();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String arrivaltime = null;
		String arrivaltime2 = null;
		if(request.getParameter("arrivaltime") == null && request.getParameter("arrivaltime2") == null){
			 arrivaltime = request.getParameter("arrivaltime") == null ? sdf.format(new Date()) : request.getParameter("arrivaltime");
			 arrivaltime2 = request.getParameter("arrivaltime2") == null ? sdf.format(new Date()) : request.getParameter("arrivaltime2");
			
		}else{
			 arrivaltime = request.getParameter("arrivaltime");
			 arrivaltime2 = request.getParameter("arrivaltime2");
		}
		String memberName = request.getParameter("memberName");
		String mobile = request.getParameter("mobile");
		String checkinPeople = request.getParameter("checkinPeople");//入住人
		String recordUser = request.getParameter("recordUser");//操作员，中文
		String source = request.getParameter("source");
		if("0".equals(source)){
			source = "";
		}
		String memberId = request.getParameter("memberId");//会员号
		String roomType = request.getParameter("roomType");
		String bookingTime = request.getParameter("bookingTime");//预定时间
		Pagination pagination = SqlBuilder.buildPagination(request);
		List<?> list = houseOrderService.findBySQLWithPagination("HouseOrderCancel", new String[] { loginUser.getStaff().getStaffId(), memberName, mobile, arrivaltime,arrivaltime2,source,memberId,roomType,bookingTime }, pagination, true);
		
		if(checkinPeople != null && checkinPeople != ""){
			List<Object> newList = new ArrayList();
			for(int i = 0;i<list.size();i++){
				String id = ((Map<?,?>)list.get(i)).get("ORDER_ID").toString();
				List<CheckUser> checkList = houseOrderService.findByProperties(CheckUser.class, "checkId", id,"status","1");
				for(int j = 0;j<checkList.size();j++){
					String checkUserName = checkList.get(j).getCheckuserName();
					if(checkUserName.indexOf(checkinPeople)!=-1){
						newList.add(list.get(i));//包含
						break;
					}
				}
			}
			list = newList;
		}
		if(recordUser != null && recordUser != ""){
			List<Object> newList = new ArrayList();
			for(int i = 0;i<list.size();i++){
				String id = ((Map<?,?>)list.get(i)).get("SOURCEID").toString();
				if("1".equals(id) || "2".equals(id) ||"4".equals(id) ||"7".equals(id)){
					if("顾客".indexOf(recordUser) != -1){
						newList.add(list.get(i));//包含
					}
				}else{
					String user = ((Map<?,?>)list.get(i)).get("RECORD_USER").toString();
					if(user.indexOf(recordUser) != -1){
						newList.add(list.get(i));//包含
					}
				}
			}
			list = newList;
		}
		mv.addObject("list", list);
		mv.addObject("pagination", pagination);
		mv.addObject("memberName", memberName);
		mv.addObject("mobile", mobile);
		mv.addObject("action", "HouseOrderAll.do");
		mv.setViewName("page/ipmshouse/houseorder/houseorderall");
		return mv;
	}
	
	@RequestMapping("HouseOrderNoCheckIn.do")
	public ModelAndView houseOrderNoCheckIn(HttpServletRequest request,HttpServletResponse response) throws Exception {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		ModelAndView mv = new ModelAndView();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String arrivaltime = request.getParameter("arrivaltime") == null ? sdf.format(new Date()) : request.getParameter("arrivaltime");
		String memberName = request.getParameter("memberName");
		String mobile = request.getParameter("mobile");
		Pagination pagination = SqlBuilder.buildPagination(request);
		List<?> list = houseOrderService.findBySQLWithPagination("quertHouseOrder", new String[] { "2", loginUser.getStaff().getStaffId(), memberName, mobile, arrivaltime }, pagination, true);
		mv.addObject("list", list);
		mv.addObject("pagination", pagination);
		mv.addObject("memberName", memberName);
		mv.addObject("mobile", mobile);
		mv.addObject("action", "HouseOrderAll.do");
		mv.setViewName("page/ipmshouse/houseorder/houseorderall");
		return mv;
	}
	@RequestMapping("waitAgree.do")//qwewe
	public ModelAndView waitAgree(HttpServletRequest request,HttpServletResponse response) throws Exception {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		ModelAndView mv = new ModelAndView();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String arrivaltime = null;
		String arrivaltime2 = null;
		if(request.getParameter("arrivaltime") == null && request.getParameter("arrivaltime2") == null){
			 arrivaltime = request.getParameter("arrivaltime") == null ? sdf.format(new Date()) : request.getParameter("arrivaltime");
			 arrivaltime2 = request.getParameter("arrivaltime2") == null ? sdf.format(new Date()) : request.getParameter("arrivaltime2");
			
		}else{
			 arrivaltime = request.getParameter("arrivaltime");
			 arrivaltime2 = request.getParameter("arrivaltime2");
		}
		String memberName = request.getParameter("memberName");
		String mobile = request.getParameter("mobile");
		Pagination pagination = SqlBuilder.buildPagination(request);
		List<?> list = houseOrderService.findBySQLWithPagination("quertwaitHouseOrder", new String[] { "1", loginUser.getStaff().getStaffId(), 
				loginUser.getStaff().getStaffId(), memberName, mobile, arrivaltime,arrivaltime2 }, pagination, true);

		mv.addObject("list", list);
		mv.addObject("pagination", pagination);
		mv.addObject("memberName", memberName);
		mv.addObject("mobile", mobile);
		mv.addObject("judge", "waitpage");
		mv.addObject("action", "HouseOrderAll.do");
		mv.setViewName("page/ipmshouse/houseorder/houseorderall");
		return mv;
	}
	
	@RequestMapping("agreeOrnot.do")
	public void agreeOrnot(HttpServletRequest request,HttpServletResponse response,String waitorderid,
			String status, String remark) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Order orderdata = (Order) houseOrderService.findOneByProperties(Order.class, "orderId", waitorderid);
		Member member = (Member) houseOrderService.findById(Member.class, orderdata.getOrderUser());
		House house = (House) houseOrderService.findOneByProperties(House.class,"houseId", orderdata.getBranchId(), "houseNo", orderdata.getRoomId());
		try{
			if("1".equals(status)){
				orderdata.setAgreeable("1");
				if(sdf.format(orderdata.getArrivalTime()).equals(sdf.format(new Date()))){
					house.setStatus("2");
				}
				houseOrderService.update(orderdata);
				JSONUtil.responseJSON(response, new AjaxResult(1, "设置成功!"));
				if(member != null){
					String token = houseOrderService.Token();
					SmsSingleSender singleSender = new SmsSingleSender(SystemConstants.note.APPID, SystemConstants.note.APPKEY);
					ArrayList<String> params = new ArrayList<String>();
					params.add(orderdata.getOrderId());
					//params.add(sdf.format(orderdata.getArrivalTime()));
					singleSender.sendWithParam(SystemConstants.note.COUNTRY, member.getMobile(), 146377, params, "", "", "");
					
					HouseOrderCancelMsg serviceMsg = new HouseOrderCancelMsg();
					serviceMsg.setFirst("您好,管家已同意您的订单，您可在客户端订单处支付该单房费!");
					serviceMsg.setOrderId(orderdata.getOrderId());
					serviceMsg.setHouseName(house.getHousename());
					serviceMsg.setTime(sdf.format(new Date()));
					//serviceMsg.setRemark("您的民宿订单由于" + remark + "被取消。");
					TemplateMessageUtil.sendHouseOrderCancelSuccessNoticeMsg(token, member.getOpenId(), serviceMsg);
				}
			}else{
				orderdata.setAgreeable("0");
				orderdata.setStatus("0");
				if(!StringUtil.isEmpty(remark)){
					orderdata.setRemark(orderdata.getRemark() == null ? "" : orderdata.getRemark() + "|" + remark);
				}
				houseOrderService.update(orderdata);
				JSONUtil.responseJSON(response, new AjaxResult(1, "设置成功!"));
				if(member != null){
					String token = houseOrderService.Token();
					SmsSingleSender singleSender = new SmsSingleSender(SystemConstants.note.APPID, SystemConstants.note.APPKEY);
					ArrayList<String> params = new ArrayList<String>();
					params.add(orderdata.getOrderId());
					params.add(sdf.format(orderdata.getArrivalTime()));
					singleSender.sendWithParam(SystemConstants.note.COUNTRY, member.getMobile(), 77226, params, "", "", "");
					
					HouseOrderCancelMsg serviceMsg = new HouseOrderCancelMsg();
					serviceMsg.setFirst("您好,您的民宿订单已被取消!");
					serviceMsg.setOrderId(orderdata.getOrderId());
					serviceMsg.setHouseName(house.getHousename());
					serviceMsg.setTime(sdf.format(new Date()));
					//serviceMsg.setRemark("您的民宿订单由于" + remark + "被取消。");
					TemplateMessageUtil.sendHouseOrderCancelSuccessNoticeMsg(token, member.getOpenId(), serviceMsg);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			JSONUtil.responseJSON(response, new AjaxResult(1, "设置失败!"));
		}
		
	}
	
	
	@RequestMapping("/quertHouseOrderInfo.do")
	public void quertHouseOrderInfo(HttpServletRequest request, HttpServletResponse response, String houseOrderId) throws Exception {
		//Order order = (Order) houseOrderService.findById(Order.class, houseOrderId);
		List<?> list = houseOrderService.findBySQL("houseLoadOrderData", new String[] { houseOrderId, houseOrderId, "" }, true);
		JSONUtil.responseJSON(response, list);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/HouseCheckIn.do")
	public void checkIn(HttpServletRequest request, HttpServletResponse response, String checkinUser, String orderId,
			String roomId) throws UnknownHostException {
		String[] checkusers = checkinUser.split(",");
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
//		String branchId = loginUser.getStaff().getBranchId();
		Order orderdata = (Order) houseOrderService.findOneByProperties(Order.class, "orderId", orderId);
		List<Check> checkdata = houseOrderService.findByProperties(Check.class, "checkId", orderId, "branchId", orderdata.getBranchId());
//		Room roomdata = (Room) houseOrderService.findOneByProperties(Room.class, "roomKey.roomId", roomId,
//				"roomKey.branchId", orderdata.getBranchId());
		House house = (House) houseOrderService.findOneByProperties(House.class, "houseNo", roomId,
				"houseId", orderdata.getBranchId());

		if (checkdata.size() > 0) {
			JSONUtil.responseJSON(response, new AjaxResult(1, "已经办理过入住了!"));
		} else {
			Double a = 0.00;
			Date date = new Date();
			
			List<OrdchePrice> list = houseOrderService.findByProperties(OrdchePrice.class, "branchId", orderdata.getBranchId(), "orderId", orderdata.getOrderId(), "status", "1");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			Double roomPrice = 0.0;
			for( OrdchePrice ordchePrice : list ){
				if(sdf.format(ordchePrice.getWhichDay()).equals(sdf.format(date))){
					roomPrice = ordchePrice.getDayPrice();
				}
			}
			
			Bill service = (Bill) houseOrderService.findOneByProperties(Bill.class, "branchId", orderdata.getBranchId(), "checkId", orderdata.getOrderId(),
					"projectId", CommonConstants.BillProject.Service);
			
			Bill cleanPrices = (Bill) houseOrderService.findOneByProperties(Bill.class, "branchId", orderdata.getBranchId(), "checkId", orderdata.getOrderId(),
					"projectId", CommonConstants.BillProject.CLEANPRICE);
			
			saveCleanServicePrices(cleanPrices, service, orderdata, loginUser);

			Check check = new Check();
			check.setCheckId(orderId);
			check.setBranchId(orderdata.getBranchId());
			check.setCheckinTime(date);
			check.setRoomPrice(roomPrice);
			check.setRpId(orderdata.getRpId());
			check.setCheckoutTime(orderdata.getLeaveTime());
			check.setCheckUser(orderdata.getOrderUser());
			check.setRoomId(roomId);
			check.setRoomType(house.getHouseType());
			check.setDeposit(a);
			check.setTtv(a);
			check.setCost(a);
			Double advanceCash = orderdata.getAdvanceCash() != null ? orderdata.getAdvanceCash() : 0.0;
			Double advanceCard = orderdata.getAdvanceCard() != null ? orderdata.getAdvanceCard() : 0.0;
			check.setPay(advanceCash + advanceCard);
			check.setAccountFee(a);
			check.setTotalFee(a);
			check.setStatus("1");
			check.setRecordTime(date);
			check.setRecordUser(loginUser.getStaff().getStaffId());
			check.setRemark(orderdata.getRemark());
			check.setMsj(orderdata.getMsj());
			orderdata.setStatus("3");
	
			OperateLog operlog = new OperateLog();
			operlog.setBranchId(orderdata.getBranchId());
			operlog.setLogId(DateUtil.currentDate("yyMMdd") + orderdata.getBranchId() + houseOrderService.getSequence("SEQ_OPERATELOG_ID"));
			String operid = InetAddress.getLocalHost().toString();// IP地址
			operid = (String) operid.subSequence(operid.indexOf("/") + 1, operid.length());
			operlog.setOperIp(operid);
			operlog.setOperType("5");
			operlog.setOperModule("入住操作(民宿)");
			operlog.setContent("订单号" + orderId + "已经入住");
			operlog.setRecordUser(loginUser.getStaff().getStaffId());
			operlog.setRecordTime(date);

			//roomdata.getRoomKey().setStatus("2");
			
			List<?> listcheckuser = houseOrderService.findByProperties(CheckUser.class, 
					"checkId", orderId, "checkinType", CommonConstants.CheckinType.ORDER,
					"status", CommonConstants.CheckUserStatus.ON);
			try {
				if(listcheckuser != null && !listcheckuser.isEmpty()){
					for (Object object : listcheckuser) {
						CheckUser checkuser = (CheckUser) object;
						CheckUser newcheckuser = new CheckUser();
						newcheckuser = checkuser.clone();
						String checkuserId = orderdata.getBranchId() + CommonConstants.OrderSource.Branch + 
						houseOrderService.getSequence("SEQ_NEW_CHECKUSER");
						newcheckuser.setCheckuserId(checkuserId);
						newcheckuser.setCheckinType(CommonConstants.CheckinType.CHECK);
						houseOrderService.save(newcheckuser);
					}
				}
				
				//List<RepairApply> repairApplys = houseOrderService.findByProperties(RepairApply.class, "branchId", orderdata.getBranchId(), "roomId", orderdata.getRoomId());
				List<RepairApply> repairApplys = houseOrderService.findByProperties(RepairApply.class, "branchId", orderdata.getBranchId());
				String status = "3";
				if(repairApplys.size() > 0){
					for(RepairApply repairApply : repairApplys){
						if(repairApply.getStatus().equals("2") || repairApply.getStatus().equals("3")){
							status = "W";
						}
					}
				}
				
				house.setStatus(status);
				houseOrderService.save(check);
				houseOrderService.update(orderdata);
				houseOrderService.save(operlog);
				//roomService.upateroomstatus(orderdata.getBranchId(), roomId, "2");
				houseOrderService.update(house);
				//houseOrderService.save(bill);
			} catch (Exception e) {
				e.printStackTrace();
			}
			JSONUtil.responseJSON(response, new AjaxResult(0, "入住成功!"));
		}
	}
	
	
	public void saveCleanServicePrices(Bill clean, Bill service, Order orderdata, LoginUser loginUser){
		Date date = new Date();
		Bill bill;
		if(null != clean){
			bill = new Bill();
			String billId = DateUtil.currentDate("yyMMdd") + orderdata.getBranchId() + houseOrderService.getSequence("SEQ_NEW_BILL");
			bill.setBillId(billId);
			bill.setBranchId(orderdata.getBranchId());
			bill.setCheckId(orderdata.getOrderId());
			bill.setProjectId("1236");
			bill.setProjectName("保洁费");
			bill.setDescribe("保洁费");
			bill.setCost(clean.getPay());
			bill.setPay(0.0);
			bill.setPayment("5");
			bill.setStatus("1");
			bill.setRecordTime(date);
			bill.setRecordUser(loginUser.getStaff().getStaffId());
			houseOrderService.save(bill);
		}
		if(null != service){
			bill = new Bill();
			String billId = DateUtil.currentDate("yyMMdd") + orderdata.getBranchId() + houseOrderService.getSequence("SEQ_NEW_BILL");
			bill.setBillId(billId);
			bill.setBranchId(orderdata.getBranchId());
			bill.setCheckId(orderdata.getOrderId());
			bill.setProjectId("1237");
			bill.setProjectName("服务费");
			bill.setDescribe("服务费");
			bill.setCost(service.getPay());
			bill.setPay(0.0);
			bill.setPayment("5");
			bill.setStatus("1");
			bill.setRecordTime(date);
			bill.setRecordUser(loginUser.getStaff().getStaffId());
			houseOrderService.save(bill);
		}
	}
	
	/**
	 * 已入住订单
	 * @author ideas_mengl
	 * @date 2018-1-23 下午01:39:37
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *
	 */
	@RequestMapping("/HouseOrderChecked.do")
	public ModelAndView houseOrderChecked(HttpServletRequest request,HttpServletResponse response) throws Exception {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		ModelAndView mv = new ModelAndView();   
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String arrivaltime = null;
		String arrivaltime2 = null;
		if(request.getParameter("arrivaltime") == null && request.getParameter("arrivaltime2") == null){
			 arrivaltime = request.getParameter("arrivaltime") == null ? sdf.format(new Date()) : request.getParameter("arrivaltime");
			 arrivaltime2 = request.getParameter("arrivaltime2") == null ? sdf.format(new Date()) : request.getParameter("arrivaltime2");
			
		}else{
			 arrivaltime = request.getParameter("arrivaltime");
			 arrivaltime2 = request.getParameter("arrivaltime2");
		}		
		String memberName = request.getParameter("memberName");
		String mobile = request.getParameter("mobile");
		String checkinPeople = request.getParameter("checkinPeople");//入住人
		String source = request.getParameter("source");
		if("0".equals(source)){
			source = "";
		}
		String memberId = request.getParameter("memberId");//会员号
		String roomType = request.getParameter("roomType");
		String bookingTime = request.getParameter("bookingTime");//预定时间
		String recordUser = request.getParameter("recordUser");//操作员，中文
		
		Pagination pagination = SqlBuilder.buildPagination(request);
		List<?> list = houseOrderService.findBySQLWithPagination("quertHouseOrder", new String[] { "3", loginUser.getStaff().getStaffId(), 
				 memberName, mobile, arrivaltime,arrivaltime2,source,memberId,roomType,bookingTime }, pagination, true);
		if(checkinPeople != null && checkinPeople != ""){
			List<Object> newList = new ArrayList();
			for(int i = 0;i<list.size();i++){
				String id = ((Map<?,?>)list.get(i)).get("ORDER_ID").toString();
				List<CheckUser> checkList = houseOrderService.findByProperties(CheckUser.class, "checkId", id,"status","1");
				for(int j = 0;j<checkList.size();j++){
					String checkUserName = checkList.get(j).getCheckuserName();
					if(checkUserName.indexOf(checkinPeople)!=-1){
						newList.add(list.get(i));//包含
						break;
					}
				}
			}
			list = newList;
		}
		if(recordUser != null && recordUser != ""){
			List<Object> newList = new ArrayList();
			for(int i = 0;i<list.size();i++){
				String id = ((Map<?,?>)list.get(i)).get("SOURCEID").toString();
				if("1".equals(id) || "2".equals(id) ||"4".equals(id) ||"7".equals(id)){
					if("顾客".indexOf(recordUser) != -1){
						newList.add(list.get(i));//包含
					}
				}else{
					String user = ((Map<?,?>)list.get(i)).get("RECORD_USER").toString();
					if(user.indexOf(recordUser) != -1){
						newList.add(list.get(i));//包含
					}
				}
			}
			list = newList;
		}
		mv.addObject("list", list);
		mv.addObject("pagination", pagination);
		mv.addObject("memberName", memberName);
		mv.addObject("mobile", mobile);
		mv.addObject("action", "HouseOrderAll.do");
		mv.setViewName("page/ipmshouse/houseorder/houseorderall");
		return mv;
	}

	/**
	 * 新订订单
	 * @author ideas_mengl
	 * @date 2018-1-22 下午03:57:15
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *
	 */
	@RequestMapping("/HouseOrderNew.do")
	public ModelAndView houseOrderNew(HttpServletRequest request,HttpServletResponse response) throws Exception {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		ModelAndView mv = new ModelAndView();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String arrivaltime = null;
		String arrivaltime2 = null;
		if(request.getParameter("arrivaltime") == null && request.getParameter("arrivaltime2") == null){
			 arrivaltime = request.getParameter("arrivaltime") == null ? sdf.format(new Date()) : request.getParameter("arrivaltime");
			 arrivaltime2 = request.getParameter("arrivaltime2") == null ? sdf.format(new Date()) : request.getParameter("arrivaltime2");
			
		}else{
			 arrivaltime = request.getParameter("arrivaltime");
			 arrivaltime2 = request.getParameter("arrivaltime2");
		}
		
		String memberName = request.getParameter("memberName");
		String mobile = request.getParameter("mobile");
		Pagination pagination = SqlBuilder.buildPagination(request);
		List<?> list = houseOrderService.findBySQLWithPagination("quertHouseOrder", new String[] { "1", loginUser.getStaff().getStaffId(), 
				loginUser.getStaff().getStaffId(), memberName, mobile, arrivaltime,arrivaltime2 }, pagination, true);

		mv.addObject("list", list);
		mv.addObject("pagination", pagination);
		mv.addObject("memberName", memberName);
		mv.addObject("mobile", mobile);
		mv.addObject("action", "HouseOrderAll.do");
		mv.setViewName("page/ipmshouse/houseorder/houseorderall");
		return mv;
	}
	
	/**
	 * 今日预抵(新订)
	 * @author ideas_zzk
	 * @date 2018-1-25 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *
	 */
	@RequestMapping("/HouseOrderCount.do")
	public ModelAndView houseOrderCount(HttpServletRequest request,HttpServletResponse response) throws Exception {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		ModelAndView mv = new ModelAndView();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String arrivaltime = null;
		String arrivaltime2 = null;
		String submitType = request.getParameter("submitType");
		String a = request.getParameter("arrivaltime");
		if(submitType == null ){
			if(request.getParameter("arrivaltime") == null && request.getParameter("arrivaltime2") == null){
				 arrivaltime = request.getParameter("arrivaltime") == null ? sdf.format(new Date()) : request.getParameter("arrivaltime");
				 arrivaltime2 = request.getParameter("arrivaltime2") == null ? sdf.format(new Date()) : request.getParameter("arrivaltime2");
				
			}else{
				 arrivaltime = request.getParameter("arrivaltime");
				 arrivaltime2 = request.getParameter("arrivaltime2");
			}
		}else{
			 arrivaltime = request.getParameter("arrivaltime");
			 arrivaltime2 = request.getParameter("arrivaltime2");
		}

		
		String memberName = request.getParameter("memberName");
		String mobile = request.getParameter("mobile");
		String source = request.getParameter("source");
		if("0".equals(source)){
			source = "";
		}
		String memberId = request.getParameter("memberId");//会员号
		String roomType = request.getParameter("roomType");
		String bookingTime = request.getParameter("bookingTime");//预定时间
		String checkinPeople = request.getParameter("checkinPeople");//入住人
		String recordUser = request.getParameter("recordUser");//操作员，中文
		Pagination pagination = SqlBuilder.buildPagination(request);
		List<?> list = houseOrderService.findBySQLWithPagination("queryodrcount", new String[] {loginUser.getStaff().getStaffId(),
				loginUser.getStaff().getStaffId(), loginUser.getStaff().getStaffId(),
				memberName, mobile, arrivaltime, arrivaltime2,source,memberId,roomType,bookingTime}, pagination, true);
		
		if(checkinPeople != null && checkinPeople != ""){
			List<Object> newList = new ArrayList();
			for(int i = 0;i<list.size();i++){
				String id = ((Map<?,?>)list.get(i)).get("ORDER_ID").toString();
				List<CheckUser> checkList = houseOrderService.findByProperties(CheckUser.class, "checkId", id,"status","1");
				for(int j = 0;j<checkList.size();j++){
					String checkUserName = checkList.get(j).getCheckuserName();
					if(checkUserName.indexOf(checkinPeople)!=-1){
						newList.add(list.get(i));//包含
						break;
					}
				}
			}
			list = newList;
		}
		if(recordUser != null && recordUser != ""){
			List<Object> newList = new ArrayList();
			for(int i = 0;i<list.size();i++){
				String id = ((Map<?,?>)list.get(i)).get("SOURCEID").toString();
				if("1".equals(id) || "2".equals(id) ||"4".equals(id) ||"7".equals(id)){
					if("顾客".indexOf(recordUser) != -1){
						newList.add(list.get(i));//包含
					}
				}else{
					String user = ((Map<?,?>)list.get(i)).get("RECORD_USER").toString();
					if(user.indexOf(recordUser) != -1){
						newList.add(list.get(i));//包含
					}
				}
			}
			list = newList;
		}
		
		
		mv.addObject("pagination", pagination);

		mv.addObject("arrivaltime", arrivaltime);
		mv.addObject("arrivaltime2", arrivaltime2);
		mv.addObject("mobile", mobile);
		mv.addObject("memberName", memberName);
		mv.addObject("orderinfo", list);
		mv.addObject("type", "new");
		mv.setViewName("page/ipmshouse/order/orderinfoNew2");
		return mv;
	}
	
	/**
	 * 待支付
	 * @author ideas_zzk
	 * @date 2018-1-25 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *
	 */
	@RequestMapping("/HouseOrderOnDue.do")
	public ModelAndView HouseOrderOnDue(HttpServletRequest request,HttpServletResponse response) throws Exception {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		ModelAndView mv = new ModelAndView();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String arrivaltime = null;
		String arrivaltime2 = null;
		String submitType = request.getParameter("submitType");
		String a = request.getParameter("arrivaltime");
		if(submitType == null ){
			if(request.getParameter("arrivaltime") == null && request.getParameter("arrivaltime2") == null){
				 arrivaltime = request.getParameter("arrivaltime") == null ? sdf.format(new Date()) : request.getParameter("arrivaltime");
				 arrivaltime2 = request.getParameter("arrivaltime2") == null ? sdf.format(new Date()) : request.getParameter("arrivaltime2");
				
			}else{
				 arrivaltime = request.getParameter("arrivaltime");
				 arrivaltime2 = request.getParameter("arrivaltime2");
			}
		}else{
			 arrivaltime = request.getParameter("arrivaltime");
			 arrivaltime2 = request.getParameter("arrivaltime2");
		}

		
		String memberName = request.getParameter("memberName");
		String mobile = request.getParameter("mobile");
		String source = request.getParameter("source");
		if("0".equals(source)){
			source = "";
		}
		String memberId = request.getParameter("memberId");//会员号
		String roomType = request.getParameter("roomType");
		String bookingTime = request.getParameter("bookingTime");//预定时间
		String checkinPeople = request.getParameter("checkinPeople");//入住人
		String recordUser = request.getParameter("recordUser");//操作员，中文
		Pagination pagination = SqlBuilder.buildPagination(request);
		List<?> list = houseOrderService.findBySQLWithPagination("queryorderondue", new String[] {loginUser.getStaff().getStaffId(),
				loginUser.getStaff().getStaffId(), null,
				memberName, mobile,source,memberId,roomType,bookingTime}, pagination, true);
		
		if(checkinPeople != null && checkinPeople != ""){
			List<Object> newList = new ArrayList();
			for(int i = 0;i<list.size();i++){
				String id = ((Map<?,?>)list.get(i)).get("ORDER_ID").toString();
				List<CheckUser> checkList = houseOrderService.findByProperties(CheckUser.class, "checkId", id,"status","1");
				for(int j = 0;j<checkList.size();j++){
					String checkUserName = checkList.get(j).getCheckuserName();
					if(checkUserName.indexOf(checkinPeople)!=-1){
						newList.add(list.get(i));//包含
						break;
					}
				}
			}
			list = newList;
		}
		if(recordUser != null && recordUser != ""){
			List<Object> newList = new ArrayList();
			for(int i = 0;i<list.size();i++){
				String id = ((Map<?,?>)list.get(i)).get("SOURCEID").toString();
				if("1".equals(id) || "2".equals(id) ||"4".equals(id) ||"7".equals(id)){
					if("顾客".indexOf(recordUser) != -1){
						newList.add(list.get(i));//包含
					}
				}else{
					String user = ((Map<?,?>)list.get(i)).get("RECORD_USER").toString();
					if(user.indexOf(recordUser) != -1){
						newList.add(list.get(i));//包含
					}
				}
			}
			list = newList;
		}
		
		
		mv.addObject("pagination", pagination);

		mv.addObject("arrivaltime", arrivaltime);
		mv.addObject("arrivaltime2", arrivaltime2);
		mv.addObject("mobile", mobile);
		mv.addObject("memberName", memberName);
		mv.addObject("list", list);
		mv.addObject("type", "new");
		mv.setViewName("page/ipmshouse/houseorder/houseorderall");
		return mv;
	}
	
	/**
	 * 今日预抵(新订)
	 * @author ideas_zhongll
	 * @date 2018-05-29 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *
	 */
	@RequestMapping("/HouseOrderCountTwo.do")
	public ModelAndView HouseOrderCountTwo(HttpServletRequest request,HttpServletResponse response) throws Exception {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		ModelAndView mv = new ModelAndView();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String arrivaltime = null;
		String arrivaltime2 = null;
		String submitType = request.getParameter("submitType");
		if(submitType == null ){
			if(request.getParameter("arrivaltime") == null && request.getParameter("arrivaltime2") == null){
				 arrivaltime = request.getParameter("arrivaltime") == null ? sdf.format(new Date()) : request.getParameter("arrivaltime");
				 arrivaltime2 = request.getParameter("arrivaltime2") == null ? sdf.format(new Date()) : request.getParameter("arrivaltime2");
				
			}else{
				 arrivaltime = request.getParameter("arrivaltime");
				 arrivaltime2 = request.getParameter("arrivaltime2");
			}
		}else{
			 arrivaltime = request.getParameter("arrivaltime");
			 arrivaltime2 = request.getParameter("arrivaltime2");
		}

		
		String memberName = request.getParameter("memberName");
		String mobile = request.getParameter("mobile");
		String source = request.getParameter("source");
		if("0".equals(source)){
			source = "";
		}
		String memberId = request.getParameter("memberId");//会员号
		String roomType = request.getParameter("roomType");
		String bookingTime = request.getParameter("bookingTime");//预定时间
		String checkinPeople = request.getParameter("checkinPeople");//入住人
		String recordUser = request.getParameter("recordUser");//操作员，中文
		Pagination pagination = SqlBuilder.buildPagination(request);
		List<?> list = houseOrderService.findBySQLWithPagination("queryodrcountTwo", new String[] {loginUser.getStaff().getStaffId(),
				loginUser.getStaff().getStaffId(),
				memberName, mobile, arrivaltime, arrivaltime2,source,memberId,roomType,bookingTime}, pagination, true);
		
		mv.addObject("pagination", pagination);

		mv.addObject("arrivaltime", arrivaltime);
		mv.addObject("arrivaltime2", arrivaltime2);
		mv.addObject("mobile", mobile);
		mv.addObject("memberName", memberName);
		mv.addObject("orderinfo", list);
		mv.addObject("type", "new");
		mv.setViewName("page/ipmshouse/houseorder/orderinfoNew2");
		return mv;
	}
	
	
	@RequestMapping("/orderCountInfo.do")
	public ModelAndView orderCountInfo(HttpServletRequest request,HttpServletResponse response) throws Exception {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		ModelAndView mv = new ModelAndView();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String arrivaltime = null;
		String arrivaltime2 = null;
		String selectTime = request.getParameter("selectTime");
		String type = request.getParameter("type");
	
		List<?> list = null;
		
		if(request.getParameter("arrivaltime") == null && request.getParameter("arrivaltime2") == null){
			 arrivaltime = request.getParameter("arrivaltime") == null ? sdf.format(new Date()) : request.getParameter("arrivaltime");
			 arrivaltime2 = request.getParameter("arrivaltime2") == null ? sdf.format(new Date()) : request.getParameter("arrivaltime2");
			
		}else{
			 arrivaltime = request.getParameter("arrivaltime");
			 arrivaltime2 = request.getParameter("arrivaltime2");
		}
		String memberName = request.getParameter("membername");
		String mobile = request.getParameter("mobile");
		String querymemberName = request.getParameter("querymemberName");
		String querymobile = request.getParameter("querymobile");
		Pagination pagination = SqlBuilder.buildPagination(request);
		
		if("wait".equals(type)){
			 list = houseOrderService.findBySQLWithPagination("dbclkcount4wait", new String[] { "1", loginUser.getStaff().getStaffId(), 
						loginUser.getStaff().getStaffId(), querymemberName, querymobile, selectTime }, pagination, true);
//			 list = houseOrderService.findBySQLWithPagination("quertwaitHouseOrder", new String[] { "1", loginUser.getStaff().getStaffId(), 
//						loginUser.getStaff().getStaffId(), memberName, mobile, selectTime }, pagination, true);
			 mv.addObject("judge", "waitpage");
		}else{
			 list = houseOrderService.findBySQLWithPagination("dbclkcount", new String[] { "1", loginUser.getStaff().getStaffId(), 
					loginUser.getStaff().getStaffId(),loginUser.getStaff().getStaffId(), 
					loginUser.getStaff().getStaffId(), querymemberName, querymobile, selectTime }, pagination, true);

		}
		
		mv.addObject("list", list);
		mv.addObject("pagination", pagination);
		mv.addObject("memberName", memberName);
		mv.addObject("mobile", mobile);
		mv.addObject("arrivaltime", arrivaltime);
		mv.addObject("arrivaltime2", arrivaltime2);
		mv.addObject("selectTime", selectTime);
		mv.addObject("type", type);
		
		mv.setViewName("page/ipmshouse/houseorder/houseorderall");
		return mv;
	}
	
	
	
	//待确认订单
	@RequestMapping("/HwaitAgreCount.do")
	public ModelAndView hwaitAgreCount(HttpServletRequest request,HttpServletResponse response) throws Exception {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		ModelAndView mv = new ModelAndView();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String arrivaltime = null;
		String arrivaltime2 = null;
		String submitType = request.getParameter("submitType");
		if(submitType == null ){
			if(request.getParameter("arrivaltime") == null && request.getParameter("arrivaltime2") == null){
				 arrivaltime = request.getParameter("arrivaltime") == null ? sdf.format(new Date()) : request.getParameter("arrivaltime");
				 arrivaltime2 = request.getParameter("arrivaltime2") == null ? sdf.format(new Date()) : request.getParameter("arrivaltime2");
				
			}else{
				 arrivaltime = request.getParameter("arrivaltime");
				 arrivaltime2 = request.getParameter("arrivaltime2");
			}
		}else{
			 arrivaltime = request.getParameter("arrivaltime");
			 arrivaltime2 = request.getParameter("arrivaltime2");
		}

		
		String memberName = request.getParameter("memberName");
		String mobile = request.getParameter("mobile");
		String source = request.getParameter("source");
		if("0".equals(source)){
			source = "";
		}
		String memberId = request.getParameter("memberId");//会员号
		String roomType = request.getParameter("roomType");
		String bookingTime = request.getParameter("bookingTime");//预定时间
		String checkinPeople = request.getParameter("checkinPeople");//入住人
		String recordUser = request.getParameter("recordUser");//操作员，中文
		Pagination pagination = SqlBuilder.buildPagination(request);
		List<?> list = houseOrderService.findBySQLWithPagination("hwaitordercount", new String[] {loginUser.getStaff().getStaffId(),
				memberName, mobile, arrivaltime, arrivaltime2,source,memberId,roomType,bookingTime}, pagination, true);
		if(checkinPeople != null && checkinPeople != ""){
			List<Object> newList = new ArrayList();
			for(int i = 0;i<list.size();i++){
				String id = ((Map<?,?>)list.get(i)).get("ORDER_ID").toString();
				List<CheckUser> checkList = houseOrderService.findByProperties(CheckUser.class, "checkId", id,"status","1");
				for(int j = 0;j<checkList.size();j++){
					String checkUserName = checkList.get(j).getCheckuserName();
					if(checkUserName.indexOf(checkinPeople)!=-1){
						newList.add(list.get(i));//包含
						break;
					}
				}
			}
			list = newList;
		}
		if(recordUser != null && recordUser != ""){
			List<Object> newList = new ArrayList();
			for(int i = 0;i<list.size();i++){
				String id = ((Map<?,?>)list.get(i)).get("SOURCEID").toString();
				if("1".equals(id) || "2".equals(id) ||"4".equals(id) ||"7".equals(id)){
					if("顾客".indexOf(recordUser) != -1){
						newList.add(list.get(i));//包含
					}
				}else{
					String user = ((Map<?,?>)list.get(i)).get("RECORD_USER").toString();
					if(user.indexOf(recordUser) != -1){
						newList.add(list.get(i));//包含
					}
				}
			}
			list = newList;
		}
		
		mv.addObject("pagination", pagination);
		mv.addObject("arrivaltime", arrivaltime);
		mv.addObject("mobile", mobile);
		mv.addObject("memberName", memberName);
		mv.addObject("arrivaltime2", arrivaltime2);
		mv.addObject("orderinfo", list);
		mv.addObject("type", "wait");
		mv.setViewName("page/ipmshouse/order/orderinfoNew2");
		return mv;
	}
	
	/**
	 * 待确认订单第二种
	 * @author ideas_zhongll
	 * @date 2018-05-29 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/HwaitAgreCountTwo.do")
	public ModelAndView HwaitAgreCountTwo(HttpServletRequest request,HttpServletResponse response) throws Exception {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		ModelAndView mv = new ModelAndView();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String arrivaltime = null;
		String arrivaltime2 = null;
		String submitType = request.getParameter("submitType");
		if(submitType == null ){
			if(request.getParameter("arrivaltime") == null && request.getParameter("arrivaltime2") == null){
				 arrivaltime = request.getParameter("arrivaltime") == null ? sdf.format(new Date()) : request.getParameter("arrivaltime");
				 arrivaltime2 = request.getParameter("arrivaltime2") == null ? sdf.format(new Date()) : request.getParameter("arrivaltime2");
				
			}else{
				 arrivaltime = request.getParameter("arrivaltime");
				 arrivaltime2 = request.getParameter("arrivaltime2");
			}
		}else{
			 arrivaltime = request.getParameter("arrivaltime");
			 arrivaltime2 = request.getParameter("arrivaltime2");
		}

		
		String memberName = request.getParameter("memberName");
		String mobile = request.getParameter("mobile");
		String source = request.getParameter("source");
		if("0".equals(source)){
			source = "";
		}
		String memberId = request.getParameter("memberId");//会员号
		String roomType = request.getParameter("roomType");
		String bookingTime = request.getParameter("bookingTime");//预定时间
		String checkinPeople = request.getParameter("checkinPeople");//入住人
		String recordUser = request.getParameter("recordUser");//操作员，中文
		Pagination pagination = SqlBuilder.buildPagination(request);
		List<?> list = houseOrderService.findBySQLWithPagination("hwaitordercountTwo", new String[] {loginUser.getStaff().getStaffId(),
				memberName, mobile, arrivaltime, arrivaltime2,source,memberId,roomType,bookingTime}, pagination, true);
		mv.addObject("pagination", pagination);
		mv.addObject("arrivaltime", arrivaltime);
		mv.addObject("mobile", mobile);
		mv.addObject("memberName", memberName);
		mv.addObject("arrivaltime2", arrivaltime2);
		mv.addObject("orderinfo", list);
		mv.addObject("type", "wait");
		mv.setViewName("page/ipmshouse/houseorder/orderinfoNew2");
		return mv;
	}
		
	
	/*
	 * 民宿名称
	 */
	@RequestMapping("/houseOrder.do")
	@RightMethodControl(rightType = RightConstants.RightType.HO_ORDER)
	public ModelAndView houseOrder(HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/ipmshouse/leftmenu/houseOrder/houseOrder");
		List<?> source = houseOrderService.findBySQL("querySource", true);
		mv.addObject("source", source);
		return mv;
		
	}
	
/**
 * 
 * @Description
 * @author ideas_mengl
 * @date   2018年5月18日
 * @param day
 * @param endDay
 * @param houseId
 * @return
 * @throws ParseException
 */
public String getDayofPrice(String day, String endDay, String houseId) throws ParseException {
		try {
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			Date dateOfDay = sdf.parse(day);//开始日期
			Date dateOfEndDay = sdf.parse(endDay);//结束日期
			List priceList = new ArrayList();
			while(dateOfDay.getTime()<dateOfEndDay.getTime()){
				long dateOfNextTime = dateOfDay.getTime()+1000*60*60*24;//开始日期加一天
				String nextDayString = sdf.format(new Date(dateOfNextTime));
				String nowDateString = sdf.format(new Date());
				String price = null;
				String dayString = "";
				House house = (House)houseOrderService.findOneByProperties(House.class, "houseId", houseId);
				Double currentPrice = house.getCurrentprice();//调整价格
				Double initPrice = house.getInitprice();//基准价格
				if(currentPrice!=null){//有调整价去调整价
					price = String.valueOf(currentPrice);
				}else{
					price = String.valueOf(initPrice);
				}
				
				if(nowDateString.equals(day)){
					priceList.add(price);
				}else{
					dayString = sdf.format(dateOfDay) + " 12";
					nextDayString = nextDayString +" 12";
					List<PriceApply> priceApplyList = houseOrderService.findBySQL("findHousePriceByDay", new String[]{dayString,nextDayString,houseId}, true);
					if(priceApplyList.size()==0){
						priceList.add(price);
					}else{
						String filterDay = (String)((Map<?,?>)priceApplyList.get(0)).get("FILTER_DAY");
						String validay = (String)((Map<?,?>)priceApplyList.get(0)).get("VALID_DAY");
						String applyId = (String)((Map<?,?>)priceApplyList.get(0)).get("APPLY_ID");
						PriceApplyDetail priceApplyDetail = (PriceApplyDetail)houseOrderService.findOneByProperties(PriceApplyDetail.class, "applyId", applyId);
						double priceApp = priceApplyDetail.getRoomPrice();
						String date[] = null;
						String filterStartDayString = null;
						String filterEndDayString = null;
						Date filterStartDate = null;
						Date filterEndDate = null;
						long filterStartDateTime = 0;
						long filterEndDateTime = 0;
						if(filterDay!=null){
							date = filterDay.split("至");
							filterStartDayString = date[0];
							filterEndDayString = date[1];
							SimpleDateFormat sdfHours = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
							filterStartDate = sdfHours.parse(filterStartDayString);
							filterEndDate = sdfHours.parse(filterEndDayString);
							filterStartDateTime = filterStartDate.getTime();
							filterEndDateTime = filterEndDate.getTime();
						}
						String dayStringCompaire =dayString+":00:00";
						String nextDayStringCompaire =nextDayString+":00:00";
						SimpleDateFormat sdfHoursChoose = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						Date liveinTime = sdfHoursChoose.parse(dayStringCompaire);
						Date leaveOutTime = sdfHoursChoose.parse(nextDayStringCompaire);
						long liveinMinsTime = liveinTime.getTime();
						long leaveOutMinsTime = leaveOutTime.getTime();
						Calendar ca = Calendar.getInstance();
						ca.setTime(dateOfDay);
						int dayOfWeek = ca.get(Calendar.DAY_OF_WEEK);
						if(filterDay == null){
							
						}
						if(validay ==null){
							priceList.add(price);
						}else if(!validay.contains(String.valueOf(dayOfWeek))){
							priceList.add(price);
						}else if(filterDay==null){
							priceList.add(String.valueOf(priceApp)) ;
						}else if(((filterStartDateTime<=liveinMinsTime)&&(leaveOutMinsTime<=filterEndDateTime))){
							priceList.add(price);
						}else{
							priceList.add(String.valueOf(priceApp)) ;
						}
					}
				}
				Calendar ca = Calendar.getInstance();
				ca.setTime(dateOfDay);
				ca.add(Calendar.DAY_OF_MONTH, 1);
				dateOfDay = ca.getTime();
			}
			//JSONArray ja = new JSONArray(priceList);
			return priceList.get(0).toString();
			
		} catch (Exception e) {
			return String.valueOf(SystemConstants.PortalResultCode.FAILED);
		}
	}
	

	
	
	@RequestMapping("/getSource.do")
	public void getSource(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<?> list = houseOrderService.findByPropertiesWithSort(DataSource.class, "dataId", "asc","status","1");
		JSONUtil.responseJSON(response, list);
	}
	
	@RequestMapping("calcprice.do")
	public void calcprice(HttpServletRequest request, HttpServletResponse response, String orderid, String price){
		Check check = (Check) houseOrderService.findOneByProperties(Check.class, "checkId", orderid);
		//Double orderchecin = RoomUtil.getCheckinPrice(new Date(), check.getCheckoutTime(), orderid);
		Double rp = RoomUtil.getCheckinPrice(new Date(), check.getCheckoutTime(), orderid);
		int size = DateUtil.daysOfTwo(new Date(), check.getCheckoutTime());
		Double re = Double.valueOf(price)*size;
		JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.SUCESS, null , String.format("%.2f", re - rp)));
	}
	
	
	/**
	 * 
	 * @Description
	 * @author ideas_mengl
	 * @date   2018年5月18日
	 * @param houseId
	 * @param start 2018/05/05
	 * @param end 2018/05/18
	 * @return
	 * @throws ParseException
	 */
	
	public String reserveableByDate(String houseId,String start,String end) throws ParseException {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			Date startDate = sdf.parse(start);
			Date endDate = sdf.parse(end);
			long startTimeMillion = startDate.getTime();
			long indexTime = 0;
			long endTimeMillion = endDate.getTime();
			boolean flag = true;
			String unreserve = null;
			indexTime = startTimeMillion;
			while(indexTime<endTimeMillion&&flag ==true){
				Date d = new Date(indexTime);
				String dString = sdf.format(d);
				List<Object> houseStatus =  houseOrderService.findBySQL("validateHouseStatus",new String[]{dString,dString,houseId} ,true);
				if(houseStatus.size()!=0){
					flag = false;
					unreserve =  dString;
				}
				indexTime +=1000*60*60*24;
			}
			if(flag == true){
				return String.valueOf(SystemConstants.PortalResultCode.NULL);
			}else{
				return unreserve;
			}
	}
	
	
	
	@RequestMapping("/HwaitAgreCountInfo.do")
	public ModelAndView HwaitAgreCountInfo(HttpServletRequest request,HttpServletResponse response) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String arrivaltime = null;
		String arrivaltime2 = null;
		if(request.getParameter("arrivaltime") == null && request.getParameter("arrivaltime2") == null){
			 arrivaltime = request.getParameter("arrivaltime") == null ? sdf.format(new Date()) : request.getParameter("arrivaltime");
			 arrivaltime2 = request.getParameter("arrivaltime2") == null ? sdf.format(new Date()) : request.getParameter("arrivaltime2");
		}else{
			 arrivaltime = request.getParameter("arrivaltime");
			 arrivaltime2 = request.getParameter("arrivaltime2");
		}
		String memberName = request.getParameter("membername");
		String mobile = request.getParameter("mobile");
		
		ModelAndView mv = new ModelAndView();
		String type = request.getParameter("type");
		String orderId = request.getParameter("orderId");
		String[] orderOld = orderId.split(",");

		List<?> listOld = null;
		List<Object> list = new ArrayList<Object>();
		Pagination pagination = SqlBuilder.buildPagination(request);
		String[] orderNew = getOrder(orderOld,pagination);
		if("".equals(orderId) || null == orderId){
			list = null;
		}else{
			for(int i = 0;i<orderNew.length;i++){
				listOld = houseOrderService.findBySQL("dbclkcount4waitTwo",new String[]{orderNew[i]} ,true);
				if(listOld.size() > 0){
					list.add(listOld.get(0));
				}
			}
		}
		
		if("wait".equals(type)){
			 mv.addObject("judge", "waitpage");
		}
		
		mv.addObject("list", list);
		mv.addObject("pagination", pagination);
		mv.addObject("type", type);
		mv.addObject("memberName", memberName);
		mv.addObject("mobile", mobile);
		mv.addObject("orderId", orderId);
		mv.addObject("arrivaltime", arrivaltime);
		mv.addObject("arrivaltime2", arrivaltime2);
		mv.setViewName("page/ipmshouse/houseorder/houseorderall");
		return mv;
	}
	
	// 民宿房单日志
	@RequestMapping("/showHouseLogData.do")
	public ModelAndView showHouseLogData(HttpServletRequest request, HttpServletResponse response, String type,
			String checkid) throws Exception {
		ModelAndView mv = new ModelAndView();
		// LoginUser loginUser =
		// (LoginUser)request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Pagination pagination = SqlBuilder.buildPagination(request);
		List<?> result = houseOrderService.getLog(checkid, type, pagination);
		mv.addObject("checkid", checkid);
		mv.addObject("type", type);
		mv.addObject("result", result);
		mv.addObject("pagination", pagination);
		mv.setViewName("page/ipmshouse/room_statistics/logData");
		return mv;
	}

	
	public static String[] getOrder(String[] orderOld,Pagination pagination) throws Exception {  
		int start = (pagination.getPageNum() - 1)* pagination.getRows();
		int length = pagination.getPageNum() * pagination.getRows();
		if(orderOld.length > length){
			String[] orderNew = new String[pagination.getRows()];
			for(int i = start;i<length;i++){
				orderNew[i-start] = orderOld[i];
			}
			return orderNew;
		}else{
			String[] orderNew = new String[orderOld.length - start];
			for(int i = start;i<orderOld.length;i++){
				orderNew[i-start] = orderOld[i];
			}
			return orderNew;
		}
	}
	
	@RequestMapping("/getOrderDetailUsedInRefund.do")
	public void getOrderDetailUsedInRefund(HttpServletRequest request, HttpServletResponse response,String orderId)throws Exception {
		Order order = (Order) houseOrderService.findOneByProperties(Order.class, "orderId", orderId);
		List<Bill> bills = houseOrderService.findByProperties(Bill.class, "branchId", order.getBranchId(), "checkId", orderId,"type","1");
		List<Bill> newBills = new ArrayList<Bill>();
		for(Bill bill : bills){
			if(bill.getProjectId().equals(CommonConstants.BillProject.Service) || bill.getProjectId().equals(CommonConstants.BillProject.CLEANPRICE)
					|| bill.getProjectId().equals(CommonConstants.BillProject.ForeGift) || bill.getProjectId().equals(CommonConstants.BillProject.Deposit)){
				newBills.add(bill);
			}
		}
		
		JSONUtil.responseJSON(response, newBills);
	}
	@RequestMapping("/getOrderDetail.do")
	public void getOrderDetailUsedInRefund2(HttpServletRequest request, HttpServletResponse response,String orderId)throws Exception {
		Order order = (Order) houseOrderService.findOneByProperties(Order.class, "orderId", orderId);
		List<Bill> bills = houseOrderService.findByProperties(Bill.class, "branchId", order.getBranchId(), "checkId", orderId,"type","1");
		List<Bill> newBills = new ArrayList<Bill>();
		for(Bill bill : bills){
			if(bill.getProjectId().equals(CommonConstants.BillProject.Service) || bill.getProjectId().equals(CommonConstants.BillProject.CLEANPRICE)
					|| bill.getProjectId().equals(CommonConstants.BillProject.ForeGift) || bill.getProjectId().equals(CommonConstants.BillProject.Deposit)){
				newBills.add(bill);
			}
		}
		
		JSONUtil.responseJSON(response, newBills);
	}
	// 取消订单
	@SuppressWarnings("unchecked")
	@RequestMapping("/cancleorder.do")
	public void cancleorder(HttpServletRequest request, HttpServletResponse response, String orderId, String status, String money, String remark,String[] objarr)
			throws Exception {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		int flag = 0;
		Order orderdata = (Order) houseOrderService.findOneByProperties(Order.class, "orderId", orderId);
		if (orderdata.getTheme().equals("3")) {
			if(status.equals("0")){
				if (orderdata.getStatus().equals(CommonConstants.OrderStatus.NewOrder)) {
					SysParam timepoint = (SysParam)houseOrderService.findOneByProperties(SysParam.class, "paramType", "houseparameter", "paramName", "timepoint");
					SysParam reservationfee = (SysParam)houseOrderService.findOneByProperties(SysParam.class, "paramType", "houseparameter", "paramName", "reservationfee");
					House house = (House) houseOrderService.findOneByProperties(House.class, "houseId", orderdata.getBranchId());
					house.setStatus("1");
					Date date = new Date();
					Calendar oneTime = Calendar.getInstance();
					oneTime.setTime(orderdata.getArrivalTime());
					oneTime.add(Calendar.DAY_OF_MONTH, Integer.parseInt(timepoint.getContent()));
					// 10天
					Calendar twoTime = Calendar.getInstance();
					twoTime.setTime(orderdata.getArrivalTime());
					twoTime.add(Calendar.DAY_OF_MONTH, -Integer.parseInt(timepoint.getContent()));
					
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
			        Calendar cal = Calendar.getInstance();    
			        cal.setTime(sdf.parse(sdf.format(orderdata.getArrivalTime())));
			        long time1 = cal.getTimeInMillis();                 
			        cal.setTime(sdf.parse(sdf.format(orderdata.getLeaveTime())));
			        long time2 = cal.getTimeInMillis();         
			        long between_days=(time2-time1)/(1000*3600*24);  
			        List<Bill> roomBill = houseOrderService.findByProperties(Bill.class, "branchId", orderdata.getBranchId(), "checkId", orderdata.getOrderId());
			        Double pay = 0.0;
			        for (Bill bill : roomBill){
			        	pay = pay + bill.getPay();
			        }
					if (date.getTime() < twoTime.getTime().getTime()) {
						//全额退款
						List<Bill> bills = houseOrderService.findByProperties(Bill.class, "branchId", orderdata.getBranchId(), "checkId", orderdata.getOrderId());
						for(Bill bill : bills){
							if(bill.getProjectId().equals(CommonConstants.BillProject.Service) || bill.getProjectId().equals(CommonConstants.BillProject.CLEANPRICE)
									|| bill.getProjectId().equals(CommonConstants.BillProject.ForeGift) || bill.getProjectId().equals(CommonConstants.BillProject.Deposit)){
								bill.setRefundStatus("1");
								houseOrderService.update(bill);
							}
						}
					} else if (twoTime.getTime().getTime() < date.getTime() && date.getTime() < orderdata.getArrivalTime().getTime()) {
						Bill Service = (Bill) houseOrderService.findOneByProperties(Bill.class, "branchId", orderdata.getBranchId(), "checkId", orderdata.getOrderId(),
								"projectId", CommonConstants.BillProject.Service);
						Double houseFee = Double.parseDouble(orderdata.getUserCheckin()) / 2; // 50%房费
						
//						Bill cleanPrice = (Bill) houseOrderService.findOneByProperties(Bill.class, "branchId", orderdata.getBranchId(), "checkId", orderdata.getOrderId(),
//								"projectId", CommonConstants.BillProject.CLEANPRICE);
						
						Double cleanPrice = 0.0;
						
						List<Bill> cleanPrices = houseOrderService.findByProperties(Bill.class, "branchId", orderdata.getBranchId(), "checkId", orderdata.getOrderId(),
								"projectId", CommonConstants.BillProject.CLEANPRICE);
						if(cleanPrices.isEmpty()){
							for(Bill bill:cleanPrices){
								cleanPrice += bill.getPay();
								bill.setRefundStatus("1");
								houseOrderService.update(bill);
							}
						}
						
//						cleanPrice.setRefundStatus("1");
						
						Bill ForeGift = (Bill) houseOrderService.findOneByProperties(Bill.class, "branchId", orderdata.getBranchId(), "checkId", orderdata.getOrderId(),
								"projectId", CommonConstants.BillProject.ForeGift);
						ForeGift.setRefundStatus("1");
						
						Bill Deposit = (Bill) houseOrderService.findOneByProperties(Bill.class, "branchId", orderdata.getBranchId(), "checkId", orderdata.getOrderId(),
								"projectId", CommonConstants.BillProject.Deposit);
						Deposit.setRefundStatus("1");
						
						Bill bill = new Bill();
						String billId = DateUtil.currentDate("yyMMdd") + branchId + "3" + houseOrderService.getSequence("SEQ_NEW_BILL");
						bill.setBillId(billId);
						bill.setBranchId(orderdata.getBranchId());
						bill.setCheckId(orderdata.getOrderId());
						bill.setProjectId("1001");// gai
						bill.setProjectName("赔偿");
						bill.setDescribe("赔偿");
						bill.setType("1");
						bill.setCost(PriceUtil.doubleToPriceDouble(Service.getPay() + houseFee));
						bill.setPay(0.00);
						bill.setPayment("1");
						bill.setStatus("1");
						bill.setRecordUser(loginUser.getStaff().getStaffId());
						
						houseOrderService.save(bill);
//						houseOrderService.update(cleanPrice);
						houseOrderService.update(ForeGift);
						houseOrderService.update(Deposit);
						
					} else if (orderdata.getArrivalTime().getTime() < date.getTime() && date.getTime() < oneTime.getTime().getTime()) {
						//不退房费,服务费,退清洁费
//						Bill cleanPrice = (Bill) orderService.findOneByProperties(Bill.class, "branchId", orderdata.getBranchId(), "checkId", orderdata.getOrderId(),
//								"projectId", CommonConstants.BillProject.CLEANPRICE);
						
						Double cleanPrice = 0.0;
						
						List<Bill> cleanPrices = houseOrderService.findByProperties(Bill.class, "branchId", orderdata.getBranchId(), "checkId", orderdata.getOrderId(),
								"projectId", CommonConstants.BillProject.CLEANPRICE);
						if(cleanPrices.isEmpty()){
							for(Bill bill:cleanPrices){
								cleanPrice += bill.getPay();
								bill.setRefundStatus("1");
								houseOrderService.update(bill);
							}
						}
						
						Bill bill = new Bill();
						String billId = DateUtil.currentDate("yyMMdd") + branchId + "3" + houseOrderService.getSequence("SEQ_NEW_BILL");
						bill.setBillId(billId);
						bill.setBranchId(orderdata.getBranchId());
						bill.setCheckId(orderdata.getOrderId());
						bill.setProjectId("1001");
						bill.setProjectName("赔偿");
						bill.setDescribe("赔偿");
						bill.setType("1");
						bill.setCost(PriceUtil.doubleToPriceDouble(pay - cleanPrice));
						bill.setPay(0.00);
						bill.setPayment("1");
						bill.setStatus("1");
						bill.setRecordUser(loginUser.getStaff().getStaffId());
						
//						cleanPrice.setRefundStatus("1");
						
						houseOrderService.save(bill);
						houseOrderService.update(cleanPrice);
					}
					houseOrderService.update(house);
				}
				if(!StringUtil.isEmpty(remark) && !remark.equals("undefined")){
					orderdata.setRemark(orderdata.getRemark() == null ? "" : orderdata.getRemark() + "," + remark);
				}
			} else if(status.equals("1")){//免责取消
				if(!StringUtil.isEmpty(money) && !money.equals("undefined")){
					Bill bill = new Bill();
					String billId = DateUtil.currentDate("yyMMdd") + branchId + "3" + houseOrderService.getSequence("SEQ_NEW_BILL");
					bill.setBillId(billId);
					bill.setBranchId(orderdata.getBranchId());
					bill.setCheckId(orderdata.getOrderId());
					bill.setProjectId("1001");
					bill.setProjectName("赔偿");
					bill.setDescribe("赔偿");
					bill.setType("1");
					bill.setCost(Double.parseDouble(money));
					bill.setPay(0.00);
					bill.setPayment("1");
					bill.setStatus("1");
					bill.setRecordUser(loginUser.getStaff().getStaffId());
					houseOrderService.save(bill);
				}
				List<Bill> bills = houseOrderService.findByProperties(Bill.class, "branchId", orderdata.getBranchId(), "checkId", orderdata.getOrderId());
				for(Bill bill : bills){
					if(bill.getProjectId().equals(CommonConstants.BillProject.Service) || bill.getProjectId().equals(CommonConstants.BillProject.CLEANPRICE)
							|| bill.getProjectId().equals(CommonConstants.BillProject.ForeGift) || bill.getProjectId().equals(CommonConstants.BillProject.Deposit)){
						if(objarr != null){
							if(Arrays.asList(objarr).contains(bill.getProjectId())){
								bill.setRefundStatus("1");
								houseOrderService.update(bill);
							}
						}
						
						
					}
				}
				if(!StringUtil.isEmpty(remark) && !remark.equals("undefined")){
					orderdata.setRemark(orderdata.getRemark() == null ? "" : orderdata.getRemark() + "," + remark);
				}
			}
			orderdata.setStatus("6");
			orderdata.setCheckoutTime(new Date());
			Calendar ca = Calendar.getInstance();
			ca.setTimeInMillis(orderdata.getCheckoutTime().getTime()+48*60*60*1000);
			orderdata.setRemark(ca.get(Calendar.YEAR)+"年"+(ca.get(Calendar.MONTH)+1)+"月"+ca.get(Calendar.DATE)+"日退款");
			orderdata.setRecordTime(new Date());
			
			//将民宿状态改成空房，zhongll2018-05-30
			House house = (House) houseOrderService.findOneByProperties(House.class, "houseId", orderdata.getBranchId());
			house.setStatus("1");
			houseOrderService.update(house);
		} else {
			if (orderdata.getStatus().equals(CommonConstants.OrderStatus.Cancel)) {
				flag = 1;
			} else {
				double pay = 0.00;
				double cost = 0.00;
				String sql = "queryorderpay";
				List<Map<String, BigDecimal>> payresult = houseOrderService.findBySQL(sql, new String[] { orderId, branchId }, true);
				
				for (int i = 0; i < payresult.size(); i++) {
					pay = pay + Double.valueOf(payresult.get(i).get("TOTALPAY").toString());
					cost = cost + Double.valueOf(payresult.get(i).get("TOTALCOST").toString());
				}
				if (cost - pay != 0.00) {
					flag = 2;
				} else {
					orderdata.setStatus(CommonConstants.OrderStatus.Cancel);
					orderdata.setRecordTime(new Date());
					orderdata.setLeaveTime(new Date());
					
					RoomStatus roomstatus = (RoomStatus) houseOrderService.findOneByProperties(RoomStatus.class, "branchId", branchId, "roomType", orderdata.getRoomType());
					if (!orderdata.getSource().equals(CommonConstants.OrderSource.Branch)) {
						roomstatus.setCount(roomstatus.getCount() + 1);
						roomstatus.setSellnum(roomstatus.getSellnum() + 1);
						//roomstatus.setInvalidnum(roomstatus.getInvalidnum() + 1);
					}
					roomstatus.setValidnum(roomstatus.getValidnum() - 1);
					houseOrderService.update(roomstatus);
				}
			}
		}

		if (flag == 0) {
			OperateLog operlog = new OperateLog();
			operlog.setBranchId(branchId);
			operlog.setLogId(DateUtil.currentDate("yyMMdd") + branchId + houseOrderService.getSequence("SEQ_OPERATELOG_ID"));
			String operid = InetAddress.getLocalHost().toString();// IP地址
			operid = (String) operid.subSequence(operid.indexOf("/") + 1, operid.length());
			operlog.setOperIp(operid);
			operlog.setOperType("order");
			operlog.setOperModule("取消订单");
			operlog.setContent("订单号：" + orderId + "被取消");
			operlog.setRecordUser(loginUser.getStaff().getStaffId());
			operlog.setRecordTime(new Date());
	
			try {
				houseOrderService.save(orderdata);
				houseOrderService.save(operlog);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Branch branch = (Branch) houseOrderService.findOneByProperties(Branch.class, "branchId", branchId);
			Member member = (Member) houseOrderService.findOneByProperties(Member.class,"memberId",orderdata.getOrderUser());
//			if (!StringUtil.isEmpty(member.getOpenId()) || !"null".equals(member.getOpenId())) {
//				OrderCancelMsg ocm = new OrderCancelMsg();
//				ocm.setFirst("您好!您的订单已取消成功!");
//				ocm.setOrderId(orderdata.getOrderId());
//				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//				ocm.setTime(sdf.format(new Date()));
//				ocm.setRemark("如您有任何疑问，请联系"+branch.getPhone()+"!");
//				String token = commonService.Token();
//				TemplateMessageUtil.sendOrderCancelSuccessNoticeMsg(token, member.getOpenId(), ocm);
//			}
			SmsSingleSender singleSender = new SmsSingleSender(SystemConstants.note.APPID, SystemConstants.note.APPKEY);
			ArrayList<String> params = new ArrayList<String>();
			params.add(orderdata.getOrderId());
			params.add(branch.getPhone());
			if(orderdata.getmPhone().trim().equals(member.getMobile().trim())){
				SmsSingleSenderResult singleSenderResult = singleSender.sendWithParam(SystemConstants.note.COUNTRY,orderdata.getmPhone(), 66053, params, "", "", "");
			}else{
				SmsSingleSenderResult singleSenderResult = singleSender.sendWithParam(SystemConstants.note.COUNTRY,orderdata.getmPhone(), 66053, params, "", "", "");
				SmsSingleSenderResult singleSenderResultAnother = singleSender.sendWithParam(SystemConstants.note.COUNTRY,member.getMobile(), 66053, params, "", "", "");
			}
			
			JSONUtil.responseJSON(response, new AjaxResult(flag, "取消成功!"));
		} else if (flag == 1) {
			JSONUtil.responseJSON(response, new AjaxResult(flag, "此订单已被取消!"));
		} else if (flag == 2) {
			JSONUtil.responseJSON(response, new AjaxResult(flag, "此订单账面不平，可去账单处查看明细!"));
		}
	}
	
	// 取消订单
	@SuppressWarnings("unchecked")
	@RequestMapping("/cancleorderInHouse.do")
	public void cancleorderInHouse(HttpServletRequest request, HttpServletResponse response, String orderId, String status, String money, String remark,String[] objarr)
			throws Exception {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		int flag = 0;
		Order orderdata = (Order) houseOrderService.findOneByProperties(Order.class, "orderId", orderId);
		if (orderdata.getTheme().equals("3")) {
			if(status.equals("0")){
				//判断当前订单为新订并且为已支付(有担保)订单,走此方法
				if (orderdata.getStatus().equals(CommonConstants.OrderStatus.NewOrder) && orderdata.getGuarantee().equals("2")) {
					SysParam timepoint = (SysParam)houseOrderService.findOneByProperties(SysParam.class, "paramType", "houseparameter", "paramName", "timepoint");
					SysParam reservationfee = (SysParam)houseOrderService.findOneByProperties(SysParam.class, "paramType", "houseparameter", "paramName", "reservationfee");
					House house = (House) houseOrderService.findOneByProperties(House.class, "houseId", orderdata.getBranchId());
					//首先只有未入住订单才可取消，其次只有下午2点后取消当天入住的订单才会修改房间状态
					SimpleDateFormat sfNew = new SimpleDateFormat("yyMMdd");
					SimpleDateFormat sdfNew = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					SimpleDateFormat simpleNew = new SimpleDateFormat("yyyy-MM-dd");
					if(sfNew.format(new Date()).equals(sfNew.format(orderdata.getArrivalTime()))){
						if(orderdata.getRecordTime().getTime() > sdfNew.parse(simpleNew.format(new Date()) + " 14:00:00").getTime() && orderdata.getRecordTime().getTime() < sdfNew.parse(simpleNew.format(new Date()) + " 14:00:00").getTime() + 14*60*60*1000){
							house.setStatus("1");
						}
						
					}
					Date date = new Date();
					Calendar oneTime = Calendar.getInstance();
					oneTime.setTime(orderdata.getArrivalTime());
					oneTime.add(Calendar.DAY_OF_MONTH, Integer.parseInt(timepoint.getContent()));
					// 10天
					Calendar twoTime = Calendar.getInstance();
					twoTime.setTime(orderdata.getArrivalTime());
					twoTime.add(Calendar.DAY_OF_MONTH, -Integer.parseInt(timepoint.getContent()));
					
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
			        Calendar cal = Calendar.getInstance();    
			        cal.setTime(sdf.parse(sdf.format(orderdata.getArrivalTime())));
			        long time1 = cal.getTimeInMillis();                 
			        cal.setTime(sdf.parse(sdf.format(orderdata.getLeaveTime())));
			        long time2 = cal.getTimeInMillis();         
			        long between_days=(time2-time1)/(1000*3600*24);  
			        List<Bill> roomBill = houseOrderService.findByProperties(Bill.class, "branchId", orderdata.getBranchId(), "checkId", orderdata.getOrderId());
			        Double pay = 0.0;
			        for (Bill bill : roomBill){
			        	pay = pay + bill.getPay();
			        }
					if (date.getTime() < twoTime.getTime().getTime()) {
						//全额退款
						List<Bill> bills = houseOrderService.findByProperties(Bill.class, "branchId", orderdata.getBranchId(), "checkId", orderdata.getOrderId());
						for(Bill bill : bills){
							if(bill.getProjectId().equals(CommonConstants.BillProject.Service) || bill.getProjectId().equals(CommonConstants.BillProject.CLEANPRICE)
									|| bill.getProjectId().equals(CommonConstants.BillProject.ForeGift) || bill.getProjectId().equals(CommonConstants.BillProject.Deposit)){
								bill.setRefundStatus("1");
								houseOrderService.update(bill);
							}
						}
					} else if (twoTime.getTime().getTime() < date.getTime() && date.getTime() < orderdata.getArrivalTime().getTime()) {
						Bill Service = (Bill) houseOrderService.findOneByProperties(Bill.class, "branchId", orderdata.getBranchId(), "checkId", orderdata.getOrderId(),
								"projectId", CommonConstants.BillProject.Service);
						Double houseFee = Double.parseDouble(orderdata.getUserCheckin()) / 2; // 50%房费
						
//						Bill cleanPrice = (Bill) orderService.findOneByProperties(Bill.class, "branchId", orderdata.getBranchId(), "checkId", orderdata.getOrderId(),
//								"projectId", CommonConstants.BillProject.CLEANPRICE);
						
						Double cleanPrice = 0.0;
						
						List<Bill> cleanPrices = houseOrderService.findByProperties(Bill.class, "branchId", orderdata.getBranchId(), "checkId", orderdata.getOrderId(),
								"projectId", CommonConstants.BillProject.CLEANPRICE);
						if(cleanPrices.isEmpty()){
							for(Bill bill:cleanPrices){
								cleanPrice += bill.getPay();
								bill.setRefundStatus("1");
								houseOrderService.update(bill);
							}
						}
						
//						cleanPrice.setRefundStatus("1");
						
						Bill ForeGift = (Bill) houseOrderService.findOneByProperties(Bill.class, "branchId", orderdata.getBranchId(), "checkId", orderdata.getOrderId(),
								"projectId", CommonConstants.BillProject.ForeGift);
						ForeGift.setRefundStatus("1");
						
						Bill Deposit = (Bill) houseOrderService.findOneByProperties(Bill.class, "branchId", orderdata.getBranchId(), "checkId", orderdata.getOrderId(),
								"projectId", CommonConstants.BillProject.Deposit);
						Deposit.setRefundStatus("1");
						
						Bill bill = new Bill();
						String billId = DateUtil.currentDate("yyMMdd") + branchId + "3" + houseOrderService.getSequence("SEQ_NEW_BILL");
						bill.setBillId(billId);
						bill.setBranchId(orderdata.getBranchId());
						bill.setCheckId(orderdata.getOrderId());
						bill.setProjectId("1001");// gai
						bill.setProjectName("赔偿");
						bill.setDescribe("赔偿");
						bill.setType("1");
						bill.setCost(PriceUtil.doubleToPriceDouble(Service.getPay() + houseFee));
						bill.setPay(0.00);
						bill.setPayment("1");
						bill.setStatus("1");
						bill.setRecordUser(loginUser.getStaff().getStaffId());
						
						houseOrderService.save(bill);
//						orderService.update(cleanPrice);
						houseOrderService.update(ForeGift);
						houseOrderService.update(Deposit);
						
					} else if (orderdata.getArrivalTime().getTime() < date.getTime() && date.getTime() < oneTime.getTime().getTime()) {
						//不退房费,服务费,退清洁费
//						Bill cleanPrice = (Bill) orderService.findOneByProperties(Bill.class, "branchId", orderdata.getBranchId(), "checkId", orderdata.getOrderId(),
//								"projectId", CommonConstants.BillProject.CLEANPRICE);
						
						Double cleanPrice = 0.0;
						
						List<Bill> cleanPrices = houseOrderService.findByProperties(Bill.class, "branchId", orderdata.getBranchId(), "checkId", orderdata.getOrderId(),
								"projectId", CommonConstants.BillProject.CLEANPRICE);
						if(cleanPrices.isEmpty()){
							for(Bill bill:cleanPrices){
								cleanPrice += bill.getPay();
								bill.setRefundStatus("1");
								houseOrderService.update(bill);
							}
						}
						
						Bill bill = new Bill();
						String billId = DateUtil.currentDate("yyMMdd") + branchId + "3" + houseOrderService.getSequence("SEQ_NEW_BILL");
						bill.setBillId(billId);
						bill.setBranchId(orderdata.getBranchId());
						bill.setCheckId(orderdata.getOrderId());
						bill.setProjectId("1001");
						bill.setProjectName("赔偿");
						bill.setDescribe("赔偿");
						bill.setType("1");
						bill.setCost(PriceUtil.doubleToPriceDouble(pay - cleanPrice));
						bill.setPay(0.00);
						bill.setPayment("1");
						bill.setStatus("1");
						bill.setRecordUser(loginUser.getStaff().getStaffId());
						
//						cleanPrice.setRefundStatus("1");
						
						houseOrderService.save(bill);
						houseOrderService.update(cleanPrice);
					}
					houseOrderService.update(house);
				}
				if(!StringUtil.isEmpty(remark) && !remark.equals("undefined")){
					orderdata.setRemark(orderdata.getRemark() == null ? "" : orderdata.getRemark() + "," + remark);
				}
			} else if(status.equals("1")){//免责取消
				if(!StringUtil.isEmpty(money) && !money.equals("undefined")){
					Bill bill = new Bill();
					String billId = DateUtil.currentDate("yyMMdd") + branchId + "3" + houseOrderService.getSequence("SEQ_NEW_BILL");
					bill.setBillId(billId);
					bill.setBranchId(orderdata.getBranchId());
					bill.setCheckId(orderdata.getOrderId());
					bill.setProjectId("1001");
					bill.setProjectName("赔偿");
					bill.setDescribe("赔偿");
					bill.setType("1");
					bill.setCost(Double.parseDouble(money));
					bill.setPay(0.00);
					bill.setPayment("1");
					bill.setStatus("1");
					bill.setRecordUser(loginUser.getStaff().getStaffId());
					houseOrderService.save(bill);
				}
				List<Bill> bills = houseOrderService.findByProperties(Bill.class, "branchId", orderdata.getBranchId(), "checkId", orderdata.getOrderId());
				for(Bill bill : bills){
					if(bill.getProjectId().equals(CommonConstants.BillProject.Service) || bill.getProjectId().equals(CommonConstants.BillProject.CLEANPRICE)
							|| bill.getProjectId().equals(CommonConstants.BillProject.ForeGift) || bill.getProjectId().equals(CommonConstants.BillProject.Deposit)){
						if(objarr != null){
							if(Arrays.asList(objarr).contains(bill.getProjectId())){
								bill.setRefundStatus("1");
								houseOrderService.update(bill);
							}
						}
						
						
					}
				}
				if(!StringUtil.isEmpty(remark) && !remark.equals("undefined")){
					orderdata.setRemark(orderdata.getRemark() == null ? "" : orderdata.getRemark() + "," + remark);
				}
			}
			//判断订单有无担保
			if(orderdata.getGuarantee().equals("2")){
				//有担保
				orderdata.setStatus("6");
				orderdata.setCheckoutTime(new Date());
				Calendar ca = Calendar.getInstance();
				ca.setTimeInMillis(orderdata.getCheckoutTime().getTime()+48*60*60*1000);
				orderdata.setRemark(ca.get(Calendar.YEAR)+"年"+(ca.get(Calendar.MONTH)+1)+"月"+ca.get(Calendar.DATE)+"日退款");
			}else{
				//无担保
				orderdata.setStatus("0");
				orderdata.setRemark("此订单是未支付!");
			}
			
			orderdata.setRecordTime(new Date());
			
			//将民宿状态改成空房，zhongll2018-05-30
			House house = (House) houseOrderService.findOneByProperties(House.class, "houseId", orderdata.getBranchId());
			house.setStatus("1");
			houseOrderService.update(house);
		} else {
			if (orderdata.getStatus().equals(CommonConstants.OrderStatus.Cancel)) {
				flag = 1;
			} else {
				double pay = 0.00;
				double cost = 0.00;
				String sql = "queryorderpay";
				List<Map<String, BigDecimal>> payresult = houseOrderService.findBySQL(sql, new String[] { orderId, branchId }, true);
				
				for (int i = 0; i < payresult.size(); i++) {
					pay = pay + Double.valueOf(payresult.get(i).get("TOTALPAY").toString());
					cost = cost + Double.valueOf(payresult.get(i).get("TOTALCOST").toString());
				}
				if (cost - pay != 0.00) {
					flag = 2;
				} else {
					orderdata.setStatus(CommonConstants.OrderStatus.Cancel);
					orderdata.setRecordTime(new Date());
					orderdata.setLeaveTime(new Date());
					
					RoomStatus roomstatus = (RoomStatus) houseOrderService.findOneByProperties(RoomStatus.class, "branchId", branchId, "roomType", orderdata.getRoomType());
					if (!orderdata.getSource().equals(CommonConstants.OrderSource.Branch)) {
						roomstatus.setCount(roomstatus.getCount() + 1);
						roomstatus.setSellnum(roomstatus.getSellnum() + 1);
						//roomstatus.setInvalidnum(roomstatus.getInvalidnum() + 1);
					}
					roomstatus.setValidnum(roomstatus.getValidnum() - 1);
					houseOrderService.update(roomstatus);
				}
			}
		}

		if (flag == 0) {
			OperateLog operlog = new OperateLog();
			operlog.setBranchId(branchId);
			operlog.setLogId(DateUtil.currentDate("yyMMdd") + branchId + houseOrderService.getSequence("SEQ_OPERATELOG_ID"));
			String operid = InetAddress.getLocalHost().toString();// IP地址
			operid = (String) operid.subSequence(operid.indexOf("/") + 1, operid.length());
			operlog.setOperIp(operid);
			operlog.setOperType("order");
			operlog.setOperModule("取消订单");
			operlog.setContent("订单号：" + orderId + "被取消");
			operlog.setRecordUser(loginUser.getStaff().getStaffId());
			operlog.setRecordTime(new Date());
	
			try {
				houseOrderService.save(orderdata);
				houseOrderService.save(operlog);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Branch branch = (Branch) houseOrderService.findOneByProperties(Branch.class, "branchId", branchId);
			Member member = (Member) houseOrderService.findOneByProperties(Member.class,"memberId",orderdata.getOrderUser());
//			if (!StringUtil.isEmpty(member.getOpenId()) || !"null".equals(member.getOpenId())) {
//				OrderCancelMsg ocm = new OrderCancelMsg();
//				ocm.setFirst("您好!您的订单已取消成功!");
//				ocm.setOrderId(orderdata.getOrderId());
//				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//				ocm.setTime(sdf.format(new Date()));
//				ocm.setRemark("如您有任何疑问，请联系"+branch.getPhone()+"!");
//				String token = commonService.Token();
//				TemplateMessageUtil.sendOrderCancelSuccessNoticeMsg(token, member.getOpenId(), ocm);
//			}
			SmsSingleSender singleSender = new SmsSingleSender(SystemConstants.note.APPID, SystemConstants.note.APPKEY);
			ArrayList<String> params = new ArrayList<String>();
			params.add(orderdata.getOrderId());
			params.add(branch.getPhone());
			if(orderdata.getmPhone().trim().equals(member.getMobile().trim())){
				SmsSingleSenderResult singleSenderResult = singleSender.sendWithParam(SystemConstants.note.COUNTRY,orderdata.getmPhone(), 66053, params, "", "", "");
			}else{
				SmsSingleSenderResult singleSenderResult = singleSender.sendWithParam(SystemConstants.note.COUNTRY,orderdata.getmPhone(), 66053, params, "", "", "");
				SmsSingleSenderResult singleSenderResultAnother = singleSender.sendWithParam(SystemConstants.note.COUNTRY,member.getMobile(), 66053, params, "", "", "");
			}
			
			JSONUtil.responseJSON(response, new AjaxResult(flag, "取消成功!"));
		} else if (flag == 1) {
			JSONUtil.responseJSON(response, new AjaxResult(flag, "此订单已被取消!"));
		} else if (flag == 2) {
			JSONUtil.responseJSON(response, new AjaxResult(flag, "此订单账面不平，可去账单处查看明细!"));
		}
	}
	
	
	//订单日志
	@RequestMapping("/showOrderLogInHouse.do")
	public ModelAndView showOrderLogInHouse(HttpServletRequest request, HttpServletResponse response, String type,
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
		mv.setViewName("page/ipmshouse/houseorder/orderLog");
		return mv;
	}
	
	
	/**
	 * 修改民宿账单备注页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/reWriteRemarkH.do")
	public ModelAndView reWriteRemark(HttpServletRequest request,HttpServletResponse response){

		String billId = request.getParameter("billId");
		String text = request.getParameter("text");
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/ipmshouse/room_statistics/reWriteRemark");
		mv.addObject("text", text);
		mv.addObject("billId",billId);
		return mv;
	}
	
	@RequestMapping("error.do")
	public ModelAndView error(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv = new ModelAndView("page/ipmshouse/room_statistics/errortext");
		return mv;
	}
	
	@RequestMapping("/addRemarkToOrder.do")
	public void addRemarkToOrder (HttpServletRequest request,HttpServletResponse response){
		String remark = request.getParameter("remark");
		String id = request.getParameter("id");
		String data = request.getParameter("data");
		if("1".equals(data)){// 订单备注
			Order order = (Order) houseOrderService.findOneByProperties(Order.class, "orderId", id);
			order.setRemark(remark);
			houseOrderService.update(order);
			
		}else if("2".equals(data)){// 入住人备注
			CheckUser ck = (CheckUser) houseOrderService.findOneByProperties(CheckUser.class, "checkuserId", id);
			ck.setRemark(remark);
			houseOrderService.update(ck);
		}else if("3".equals(data)){// 接待备注
			Order order = (Order) houseOrderService.findOneByProperties(Order.class, "orderId", id);
			order.setReceptionRemark(remark);
			houseOrderService.update(order);
		}else if("4".equals(data)){// 客房备注
			Order order = (Order) houseOrderService.findOneByProperties(Order.class, "orderId", id);
			order.setRoomRemark(remark);
			houseOrderService.update(order);
		}
		JSONUtil.responseJSON(response, new AjaxResult(1, "修改成功!"));
	}
}
