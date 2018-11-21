package com.ideassoft.apartment.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ideassoft.apartment.service.ApartmentCleanService;
import com.ideassoft.apartment.service.ApartmentLogServiceInPms;
import com.ideassoft.bean.Bill;
import com.ideassoft.bean.Branch;
import com.ideassoft.bean.Clean;
import com.ideassoft.bean.CleanApply;
import com.ideassoft.bean.CleanInfo;
import com.ideassoft.bean.Goods;
import com.ideassoft.bean.Member;
import com.ideassoft.bean.MemberAccount;
import com.ideassoft.bean.Recording;
import com.ideassoft.bean.RefundDetail;
import com.ideassoft.bean.SaleLog;
import com.ideassoft.bean.Staff;
import com.ideassoft.bean.SysParam;
import com.ideassoft.bean.WorkBill;
import com.ideassoft.bean.WorkBillDetail;
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
import com.ideassoft.crm.templateMessage.BookingFailMsg;
import com.ideassoft.crm.templateMessage.ServiceMsg;
import com.ideassoft.crm.templateMessage.TemplateMessageUtil;
import com.ideassoft.util.DateUtil;
import com.ideassoft.util.JSONUtil;
import com.ideassoft.util.ReflectUtil;
import com.ideassoft.util.RequestUtil;
import com.ideassoft.wechatrefund.WeChatRefundReqModel;


@Transactional
@Controller
@RightModelControl(rightModel = RightConstants.RightModel.APARTMENT_CONTROL)
public class ApartCleanApplicationController {
	@Autowired
	private ApartmentCleanService CleanService;
	@Autowired
	private ApartmentLogServiceInPms logServiceInPms;

	/**
	 * 保洁申请(公寓)-加载保洁申请左侧导航栏页面
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/apartmentHandleApply.do")
	@RightMethodControl(rightType = RightConstants.RightType.APA_CLEAN)
	public ModelAndView apartmentHandleApply(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/apartment/apartmentLeftmenu/apartmenthandleapply/apply");
		return mv;
	}

	/**
	 * 保洁申请(公寓)-加载保洁申请处理页面
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/apartmentLoadapply.do")
	public ModelAndView apartmentLoadapply(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/apartment/apartmentLeftmenu/apartmenthandleapply/loadapply");
		return mv;
	}
	
	/**
	 * 保洁申请(公寓)-加载保洁记录页面
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/apartLoadcleanrecord.do")
	public ModelAndView apartLoadcleanrecord(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/apartment/apartmentLeftmenu/apartmenthandleapply/loadrecord");
		return mv;
	}
	
	/**
	 * 保洁申请(公寓)-初始化加载所有申请数据
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryApartCleanapply.do")
	public ModelAndView queryApartCleanapply(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		Pagination pagination = (Pagination) ReflectUtil.setBeanFromRequest(request, Pagination.class);
		if (pagination != null && pagination.getPageNum() == null) {
			pagination.setPageNum(1);
			pagination.setRows(15);
		}
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchid = loginuser.getStaff().getBranchId();
		List<?> applicationdata = CleanService.getApplicationdata(branchid, pagination);
		mv.setViewName("page/apartment/apartmentLeftmenu/apartmenthandleapply/applydata");
		mv.addObject("branchId", branchid);
		mv.addObject("applicationdata", applicationdata);
		mv.addObject("pagination", pagination);
		return mv;
	}

	/**
	 * 保洁申请(公寓)-根据条件查询申请数据
	 * 
	 * @param request
	 * @param cleanstarttime
	 * @param cleanendtime
	 * @param applystarttime
	 * @param applyendtime
	 * @param timeArea
	 * @param status
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/querycleanapplyApart.do")
	public ModelAndView querycleanapplyApart(HttpServletRequest request, String cleanstarttime,
			String cleanendtime, String applystarttime, String applyendtime, String timeArea, String status)
			throws Exception {
		ModelAndView mv = new ModelAndView();
		Pagination pagination = (Pagination) ReflectUtil.setBeanFromRequest(request, Pagination.class);
		if (pagination != null && pagination.getPageNum() == null) {
			pagination.setPageNum(1);
			pagination.setRows(15);
		}
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchid = loginuser.getStaff().getBranchId();
		List<?> applicationdataCondition = CleanService.getApplicationdataCondition(branchid, cleanstarttime,
				cleanendtime, applystarttime, applyendtime, timeArea, status, pagination);
		mv.setViewName("page/apartment/apartmentLeftmenu/apartmenthandleapply/applydatacondition");
		mv.addObject("applicationdataCondition", applicationdataCondition);
		mv.addObject("pagination", pagination);
		mv.addObject("cleanstarttime", cleanstarttime);
		mv.addObject("cleanendtime", cleanendtime);
		mv.addObject("applystarttime", applystarttime);
		mv.addObject("applyendtime", applyendtime);
		mv.addObject("timeArea", timeArea);
		mv.addObject("status", status);
		mv.addObject("branchId", branchid);
		return mv;
	}

	/**
	 * 保洁申请(公寓)-新增保洁申請
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/addApartNewApply.do")
	public ModelAndView addApartNewApply(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchid = loginuser.getStaff().getBranchId();
		//Goods goodsForClean = (Goods)CleanService.findOneByProperties(Goods.class, "goodsName", "保洁费", "branchId",branchid);
		List<Map<String, String>>  cleanlist = CleanService.findBySQL("querygoodsclean",new String[]{"保洁",branchid}, true);
		if(cleanlist != null && cleanlist.size()>0){
			mv.addObject("cleanprice",((Map<String ,String>)cleanlist.get(0)).get("PRICE"));
		}
		mv.setViewName("page/apartment/apartmentLeftmenu/apartmenthandleapply/addnewapply");
		return mv;
	}
	
	/**
	 * 保洁申请(公寓)-查询保洁申请将数据刷到页面
	 * 
	 * @param request
	 * @param response
	 * @param time
	 * @return
	 * @throws ParseException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/querycleanApartment.do")
	public ModelAndView querycleanApartment(HttpServletRequest request, HttpServletResponse response, String time)
			throws ParseException {
		ModelAndView mv = new ModelAndView();
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginuser.getStaff().getBranchId();
		String sql5 = "findcleaninforecord";
		Map<String, Map<String, CleanInfo>> maps = new HashMap<String, Map<String, CleanInfo>>();
		Map<String, CleanInfo> map = null;
		List<CleanInfo> list = new ArrayList<CleanInfo>();
		// 查出预排表日期用于遍历
		List<Clean> dateFromClean = CleanService.findBySQL("findcleandate", new String[] { branchId, time }, true);
		// 查申请表日期和timearea用作map
		// List<Map<String,String>> dateFromCleanApply =
		// CleanService.findBySQL("findcleanapplydate",new
		// String[]{branchId,time}, true);
		List<Map<String, String>> dateFromCleanApply = CleanService.findBySQL("findcleandate", new String[] { branchId,
				time }, true);
		for (Map<String, String> map1 : dateFromCleanApply) {
			String cleanapply_cleantime = map1.get("CLEANTIME");// 日期
			// 获取周几
			SimpleDateFormat sdf = new SimpleDateFormat("EEEE", Locale.CHINA);
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");
			Date date = sdf2.parse(cleanapply_cleantime);
			String weekday = sdf.format(date);
			for (int i = 0; i <= 1; i++) {
				CleanInfo cleanInfo = new CleanInfo();
				List<Object> cleaninfolist = CleanService.findBySQL(sql5, new String[] { branchId,
						cleanapply_cleantime, Integer.toString(i) }, true);
				if (null != cleaninfolist && !cleaninfolist.isEmpty()) {
					cleanInfo.setCleanPerson(((Map<String, String>) cleaninfolist.get(0)).get("CLEANPERSON").toString());
					cleanInfo.setRoomId(((Map<String, String>) cleaninfolist.get(0)).get("ROOMID").toString());
					cleanInfo.setCleanTime(cleanapply_cleantime);
					cleanInfo.setTimeArea(Integer.toString(i));
					cleanInfo.setRestAmount(((Map<String, String>) cleaninfolist.get(0)).get("RESTAMOUNT").toString());
					cleanInfo.setWeekday(weekday);
					list.add(cleanInfo);
				}

			}
		}
		for (int k = 0; k < list.size(); k++) {
//				 if(null != maps.get(list.get(k).getCleanTime())){
//				 map = maps.get(list.get(k).getCleanTime());
//				 }else{
//				 map = new HashMap<String, CleanInfo>();
//				 maps.put(list.get(k).getCleanTime(), map);
//				 }
//				 map.put(list.get(k).getTimeArea().toString(), list.get(k));
			if (null == maps.get(list.get(k).getCleanTime())) {
				map = new HashMap<String, CleanInfo>();
				maps.put(list.get(k).getCleanTime(), map);
			}
			map.put(list.get(k).getTimeArea().toString(), list.get(k));
		}
		mv.addObject("dateFromClean", dateFromClean);
		mv.addObject("maps", maps);
		mv.addObject("list", list);
		mv.setViewName("/page/apartment/apartmentLeftmenu/apartmenthandleapply/loadcleanrecord_new");
		return mv;
	}

	/**
	 * 保洁申请(公寓)-处理保洁申请(剩余次数查询)
	 * 
	 * @param request
	 * @param response
	 * @param cleanTime
	 * @param timeArea
	 * @param branchid
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/queryApartmentRest.do")
	public void queryApartmentRest(HttpServletRequest request, HttpServletResponse response, String cleanTime, String timeArea,
			String branchid,String cleanApplicationId) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date cleandate = sdf.parse(cleanTime);
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		//String branchid = loginuser.getStaff().getBranchId();
		//String staffid = loginuser.getStaff().getStaffId();
		CleanApply ca = (CleanApply)CleanService.findOneByProperties(CleanApply.class, "cleanApplyId", cleanApplicationId, "status","1");
		if(ca != null){
			if(ca.getPayment().trim().equals("1")){
				List<WorkBill> listworkbill = CleanService.findByProperties(WorkBill.class, "branchId", branchid, 
						"status", "1");
				if(listworkbill.size() <= 0){
					JSONUtil.responseJSON(response, new AjaxResult(0, "未找到自己的工作帐，请先创建工作帐!"));
					return ;
				}
		}
		if ("上午".equals(timeArea)) {
			timeArea = "0";
		} else {
			timeArea = "1";
		}
		Clean clean = (Clean) CleanService.findOneByProperties(Clean.class, "cleanDate", cleandate, "timeArea",
				timeArea, "branchId", branchid);
		int count = clean.getRestAmount();
		Branch branch = (Branch) CleanService.findOneByProperties(Branch.class, "branchId", branchid);
		String branchname = branch.getBranchName();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("count", count);
		map.put("branchname", branchname);
		JSONUtil.responseJSON(response, new AjaxResult(1, "该时间段可安排房间数已满!", map));
			
		}else{
			JSONUtil.responseJSON(response, new AjaxResult(0, "未找到需要处理的数据!"));
		}
	}
	
	/**
	 * 
	 * 保洁申请(公寓)-取消保洁申请
	 * 
	 * @param request
	 * @param response
	 * @param cleanApplicationId
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/cancleApartcleanapply.do")
	public void cancleApartcleanapply(HttpServletRequest request, HttpServletResponse response, String cleanApplicationId,String contractId)
			throws Exception {
//			if(HeartBeatClient.heartbeat){
			com.ideassoft.portal.DataDealPortalService service = new com.ideassoft.portal.DataDealPortalService();
			 com.ideassoft.portal.IDataDealPortal portal = service.getDataDealPortalPort();
			SimpleDateFormat sdf_msg = new SimpleDateFormat("yyyy年MM月dd日");
			CleanApply cleanApply = (CleanApply) CleanService.findOneByProperties(CleanApply.class, "cleanApplyId",
					cleanApplicationId);
			String roomid_msg = cleanApply.getRoomId();
			Double cleanprice = cleanApply.getCleanPrice();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			DateFormat dff = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


			String cleantime_msg = sdf_msg.format(cleanApply.getCleanTime());
			String timearea_msg = cleanApply.getTimeArea();
			if ("0".equals(timearea_msg)) {
				timearea_msg = "上午";
			} else {
				timearea_msg = "下午";
			}
			LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
			 String staffname = loginuser.getStaff().getStaffName();
			 String memberid = cleanApply.getReservedPerson();
			 // 判断是否是现金支付，如果是则走线下退款，如果不是则走线上退款
			 if (cleanApply != null) {
				 if(!cleanApply.getPayment().trim().equals("1")){
					 RefundDetail refundDetail = (RefundDetail) CleanService.findOneByProperties(RefundDetail.class, "orderId", contractId, "status", "1");
						
						if(refundDetail != null && refundDetail.getStatus().equals("1")){
//							Bill bill = (Bill) apartmentOrderService.findOneByProperties(Bill.class, "branchId", aptOrder.getBranchId(), "checkId", aptOrder.getOrderId() ,"projectId", "2004");
							if(refundDetail.getSource().equals("7")){
								String Refund = String.valueOf(refundDetail.getRefundMoney() * 100);
								Refund = Refund.substring(0, Refund.indexOf("."));
//								String money = String.valueOf(bill.getPay() * 100);
//								money = money.substring(0, money.indexOf("."));
								WeChatRefundReqModel a = new WeChatRefundReqModel();
								a.setOutTradeNo(refundDetail.getBussinessId().trim());
								a.setTotalFee(Integer.parseInt(Refund));
								a.setRefundFee(Integer.parseInt(Refund));
								CleanService.weChatRefund(a);
							}
							if(refundDetail.getSource().equals("1")){
								CleanService.alipayReFund(refundDetail.getBussinessId(), refundDetail.getTradeId(), String.valueOf(refundDetail.getRefundMoney()));
							}
							refundDetail.setStatus("0");
							CleanService.update(refundDetail);
						}
				 } else {
					 // 线下退款至账户表
					 MemberAccount majudge = (MemberAccount)CleanService.findOneByProperties(MemberAccount.class, "memberId", memberid, "status","1");
					 String memberAccountbackinfo = portal.call(SystemConstants.EnginType.COMMON_DATA, SystemConstants.Movement.CUSTOM_QUERY, "{ function: \"checkInDirect.getMemberAccount\", data:{memberId:" + memberid  +" } }");
						if((!memberAccountbackinfo.equals(Integer.toString(SystemConstants.PortalResultCode.FAILED))) && 
								(!memberAccountbackinfo.equals(Integer.toString(SystemConstants.PortalResultCode.NULL)))){
							JSONArray mrr = new JSONArray(memberAccountbackinfo);
							if(mrr.length()>0){
								  for(int i=0;i<mrr.length();i++){
									  JSONObject maccc = mrr.getJSONObject(i);
									  if(null != majudge){
										  //更新
										 // majudge.setAccountId(maccc.getString("ACCOUNT_ID"));
										  majudge.setMemberId(maccc.getString("MEMBER_ID"));
										  majudge.setCurrBalance(maccc.getDouble("CURR_BALANCE") + cleanprice);//当前余额
										  majudge.setCurrIntegration(maccc.getLong("CURR_INTEGRATION"));
										  majudge.setTotalRecharge(maccc.getDouble("TOTAL_RECHARGE") + cleanprice);//充值金额
										  majudge.setDiscountGift(maccc.getDouble("DISCOUNT_GIFT"));
										  majudge.setChargeGift(maccc.getDouble("CHARGE_GIFT"));
										  majudge.setTotalConsume(maccc.getDouble("TOTAL_CONSUME"));
										  majudge.setTotalIntegration(maccc.getLong("TOTAL_INTEGRATION"));
										  majudge.setIngegrationGift(maccc.getLong("INGEGRATION_GIFT"));
										  majudge.setTotalConsIntegration(maccc.getLong("TOTAL_CONS_INTEGRATION"));
							    		  Integer tr = Integer.valueOf(maccc.getString("TOTAL_ROOMNIGHTS"));
							    		  majudge.setTotalRoomnights(tr);
							    		  Integer cr = Integer.valueOf(maccc.getString("CURR_ROOMNIGHTS"));
							    		  majudge.setCurrRoomnights(cr);
							    		  Short tn = new Short(maccc.getString("TOTAL_NOSHOW"));
							    		  majudge.setTotalNoshow(tn);
							    		  Short cn = new Short(maccc.getString("CURR_NOSHOW"));
							    		  majudge.setCurrNoshow(cn);
							    		  Date acrecordt;
											if (maccc.getString("RECORD_TIME") == "null") {
												acrecordt = null;
											} else {
												acrecordt = dff.parse(maccc.getString("RECORD_TIME"));
											}
											majudge.setRecordTime(acrecordt);
											majudge.setStatus(maccc.getString("STATUS"));
											majudge.setThisYearIntegration(maccc.getLong("THIS_YEAR_INTEGRATION"));
										
										CleanService.update(majudge);
										  
									  }else{
										  //增加
										  MemberAccount memberaccount = new MemberAccount();
										  memberaccount.setAccountId(maccc.getString("ACCOUNT_ID"));
							    		  memberaccount.setMemberId(maccc.getString("MEMBER_ID"));
							    		  memberaccount.setCurrBalance(Double.parseDouble(String.format("%.2f",maccc.getDouble("CURR_BALANCE") + cleanprice)));
							    		  memberaccount.setCurrIntegration(maccc.getLong("CURR_INTEGRATION"));
							    		  memberaccount.setTotalRecharge(Double.parseDouble(String.format("%.2f",maccc.getDouble("TOTAL_RECHARGE") + cleanprice)));
							    		  memberaccount.setDiscountGift(maccc.getDouble("DISCOUNT_GIFT"));
							    		  memberaccount.setChargeGift(maccc.getDouble("CHARGE_GIFT"));
							    		  memberaccount.setTotalConsume(maccc.getDouble("TOTAL_CONSUME"));
							    		  memberaccount.setTotalIntegration(maccc.getLong("TOTAL_INTEGRATION"));
							    		  memberaccount.setIngegrationGift(maccc.getLong("INGEGRATION_GIFT"));
							    		  memberaccount.setTotalConsIntegration(maccc.getLong("TOTAL_CONS_INTEGRATION"));
							    		  Integer tr = Integer.valueOf(maccc.getString("TOTAL_ROOMNIGHTS"));
							    		  memberaccount.setTotalRoomnights(tr);
							    		  Integer cr = Integer.valueOf(maccc.getString("CURR_ROOMNIGHTS"));
							    		  memberaccount.setCurrRoomnights(cr);
							    		  Short tn = new Short(maccc.getString("TOTAL_NOSHOW"));
							    		  memberaccount.setTotalNoshow(tn);
							    		  Short cn = new Short(maccc.getString("CURR_NOSHOW"));
							    		  memberaccount.setCurrNoshow(cn);
							    		  Date acrecordt;
											if (maccc.getString("RECORD_TIME") == "null") {
												acrecordt = null;
											} else {
												acrecordt = dff.parse(maccc.getString("RECORD_TIME"));
											}
											memberaccount.setRecordTime(acrecordt);
											memberaccount.setStatus(maccc.getString("STATUS"));
											memberaccount.setThisYearIntegration(maccc.getLong("THIS_YEAR_INTEGRATION"));
										
											CleanService.save(memberaccount);
									  }
								  }
							}
						}
				 	}
			 	}
			 	//用作同步的
			    /*Bill bill = new Bill();
				String billId = DateUtil.currentDate("yyMMdd") + loginuser.getStaff().getBranchId() +
						CommonConstants.OrderSource.Branch + CleanService.getSequence("SEQ_NEW_BILL");
				bill.setBillId(billId);
				bill.setBranchId(loginuser.getStaff().getBranchId());
				bill.setCheckId(memberid);
				bill.setProjectId(CommonConstants.BillProject.Cash);
				bill.setProjectName("现金");
				bill.setDescribe("保洁退款");
				bill.setPayment("1");
				bill.setCost(0.0);
				bill.setPay(cleanprice);
				bill.setStatus("1");
				bill.setRecordTime(new Date());
				bill.setRecordUser(loginuser.getStaff().getStaffId());
				
				logServiceInPms.cleanapplyRefund((LoginUser) request.getSession(true).getAttribute(
						RequestUtil.LOGIN_USER_SESSION_KEY), "", "7",request);
				CleanService.save(bill);*/
				
			 List<?> memberlist = CleanService.findBySQL("queryOpenId",new String[]{memberid}, true);
			 String openId = ((Map<String,String>)memberlist.get(0)).get("OPENID")== null ? "" :((Map<String,String>)memberlist.get(0)).get("OPENID").toString();
			 String mobile = ((Map<String,String>)memberlist.get(0)).get("MOBILE")== null ? "" :((Map<String,String>)memberlist.get(0)).get("MOBILE").toString();
			 String errmsg = null;
			 String token  = "";
			 

				String backinfo = portal.call(SystemConstants.EnginType.COMMON_DATA, SystemConstants.Movement.CUSTOM_QUERY, "{ function: \"apartmentManager.findtokens\", data:{aa:bb} }");
				if((!backinfo.equals(Integer.toString(SystemConstants.PortalResultCode.FAILED))) && 
						(!backinfo.equals(Integer.toString(SystemConstants.PortalResultCode.NULL)))){
					token  = backinfo;
					
				}
			// List<?> tokenlist = CleanService.findBySQL("queryRepairToken", true);
			//发送微信消息
			BookingFailMsg bookingFailMsg = new BookingFailMsg();
			bookingFailMsg.setFirst("您好！您有新的保洁申请撤销通知！");
			bookingFailMsg.setStaff(staffname);
			bookingFailMsg.setBookingDate(cleantime_msg);
			bookingFailMsg.setRemark("您预约的 " + cleantime_msg + " " + timearea_msg + " " + roomid_msg + "房间的保洁申请已取消!");
			//String token = ((Map<String, String>) tokenlist.get(0)).get("CONTENT").toString();
			JSONObject jo = TemplateMessageUtil.sendBookingFailMsg(token,openId, bookingFailMsg);
			errmsg = (String) jo.get("errmsg");

			//发送短信
			String phoneNumber1 = mobile;
			// TODO
	    	SmsSingleSender singleSender = new SmsSingleSender(SystemConstants.note.APPID, SystemConstants.note.APPKEY);
	    	SmsSingleSenderResult singleSenderResult;
	    	
	    	ArrayList<String> params = new ArrayList<String>();
	    	//params.add("您预约的 " + cleantime_msg + " " + timearea_msg + " " + roomid_msg + "房间的保洁申请已取消!");
	    	params.add(cleantime_msg);
	    	params.add(timearea_msg);
	    	params.add(roomid_msg);
	    	params.add("已取消");
	    	singleSenderResult = singleSender.sendWithParam(SystemConstants.note.COUNTRY, phoneNumber1, 52202, params, "", "", "");
			if (!("ok".equals(errmsg))) {
				try {
					cleanApply.setStatus("0");
				//	cleanApply.setCleanTime(cleandate);
				//	cleanApply.setApplicationTime(cleanapplicationdate);
					cleanApply.setRecordTime(new Date());
				//	cleanapplyList.add(cleanApply);
					CleanService.update(cleanApply);
					logServiceInPms.deletecleanapplyrecordsuccess((LoginUser) request.getSession(true).getAttribute(
							RequestUtil.LOGIN_USER_SESSION_KEY), "", "7",request);
					//JSONUtil.responseJSON(response, new AjaxResult(2, "微信消息发送失败!"));
					JSONUtil.responseJSON(response, new AjaxResult(2, "申请撤销成功!"));
				} catch (Exception e) {
					e.printStackTrace();
					logServiceInPms.deletecleanapplyrecordfailed((LoginUser) request.getSession(true).getAttribute(
							RequestUtil.LOGIN_USER_SESSION_KEY), "", "7",request);
					JSONUtil.responseJSON(response, new AjaxResult(1, "申请撤销失败!"));
				}
				


			} else {
				try {
					cleanApply.setStatus("0");
					//cleanApply.setCleanTime(cleandate);
					//cleanApply.setApplicationTime(cleanapplicationdate);
					cleanApply.setRecordTime(new Date());
					//cleanapplyList.add(cleanApply);
					CleanService.update(cleanApply);
					logServiceInPms.deletecleanapplyrecordsuccess((LoginUser) request.getSession(true).getAttribute(
							RequestUtil.LOGIN_USER_SESSION_KEY), "", "7",request);
					
					JSONUtil.responseJSON(response, new AjaxResult(0, "申请撤销成功!"));
				} catch (Exception e) {
					e.printStackTrace();
					logServiceInPms.deletecleanapplyrecordfailed((LoginUser) request.getSession(true).getAttribute(
							RequestUtil.LOGIN_USER_SESSION_KEY), "", "7",request);
					JSONUtil.responseJSON(response, new AjaxResult(1, "申请撤销失败!"));
				}

			}
		/*}else{
			JSONUtil.responseJSON(response, new AjaxResult(1, "未连接网络!"));
			
		}*/

	}
	
	/**
	 * 保洁申请(公寓)-双击查看保洁申请详情
	 * 
	 * @param request
	 * @param response
	 * @param cleanApplicationId
	 * @param status
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/showApartcleanapplydetail.do")
	public ModelAndView showApartcleanapplydetail(HttpServletRequest request, HttpServletResponse response,
			String cleanApplicationId, String status) throws Exception {
		ModelAndView mv = new ModelAndView();
		List<?> list = CleanService.showdetail(cleanApplicationId);
		mv.addObject("result", JSONUtil.fromObject(list));
		mv.addObject("status", status);
		mv.setViewName("/page/apartment/apartmentLeftmenu/apartmenthandleapply/cleanApplyDetail");
		return mv;

	}
	
	/**
	 * 保洁申请(公寓)-新增保洁申请页面
	 * 
	 * @param request
	 * @param response
	 * @param roomid
	 * @param contrartid
	 * @param reservedperson
	 * @param mobile
	 * @param cleantime
	 * @param timeArea
	 * @param cleanstyle
	 * @param remark
	 * @param memberId
	 * @param cleanprice
	 * @param paycash
	 * @param payscore
	 * @param usecoupon
	 * @param paystatus
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/submitApartcleanapplication.do")
	public void submitApartcleanapplication(HttpServletRequest request, HttpServletResponse response, String roomid,
			String contrartid, String reservedperson, String mobile, String cleantime, String timeArea,
			String cleanstyle, String remark, String memberId,String cleanprice,String paycash,String payscore,
			String usecoupon,String paystatus
			) throws Exception {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchid = loginuser.getStaff().getBranchId();
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yy/MM/dd");
		Date cleanDate = sdf2.parse(cleantime);
		Date today = new Date();
		String strdate = sdf.format(today);
		int restamount = 0;
		int statusamount = 0;

		Clean clean = (Clean) CleanService.findOneByProperties(Clean.class, "cleanDate", cleanDate, "timeArea",
				timeArea,"branchId",branchid);
		if(null!=clean){
			 restamount = clean.getRestAmount();
		}
	
		List<Map<String, String>> notdealamount = CleanService.findBySQL("findrestamount_pms", new String[] {
				cleantime, timeArea, branchid }, true);
		if(notdealamount.size()>0){
			 statusamount = Integer.parseInt(((Map<String, String>) notdealamount.get(0)).get("NOTDEALAMOUNT")
					.toString());
			
		}
		
		if (restamount - statusamount > 0) {
			CleanApply cleanapply = new CleanApply();
			cleanapply.setCleanApplyId(strdate + branchid + CommonConstants.OrderSource.Branch + CleanService.getSequence("SEQ_CLEANAPPLY_ID"));
			cleanapply.setBranchId(branchid);
			cleanapply.setContractId(contrartid);
			cleanapply.setCleanTime(cleanDate);
			cleanapply.setCleanStyle(cleanstyle);
			cleanapply.setRoomId(roomid);
			cleanapply.setReservedPerson(memberId);
			cleanapply.setMobile(mobile);
			cleanapply.setStatus("1");
			cleanapply.setApplicationTime(today);
			cleanapply.setRecordTime(new Date());
			cleanapply.setRemark(remark);
			cleanapply.setTimeArea(timeArea);
			if(!("1".equals(cleanstyle))){
				cleanapply.setCleanPrice(0.0);
			}else{
				cleanapply.setCleanPrice(Double.parseDouble(cleanprice));
			}
//			if(!("".equals(cleanprice))){
//				cleanapply.setCleanPrice(Double.parseDouble(cleanprice));
//			}
//			if(!("".equals(paycash))){
//				cleanapply.setPayCash(Double.parseDouble(paycash));
//			}
//			if(!("".equals(payscore))){
//				cleanapply.setPayScore(Integer.parseInt(payscore));
//			}
//			cleanapply.setUseCoupon(usecoupon);
			cleanapply.setPayStatus(paystatus);
			cleanapply.setPayment("1");
			 
			try {
				CleanService.save(cleanapply);
				JSONUtil.responseJSON(response, new AjaxResult(1, "保洁申请成功！"));
				logServiceInPms.addcleanapplyrecord((LoginUser) request.getSession(true).getAttribute(
						RequestUtil.LOGIN_USER_SESSION_KEY), remark, "7",request);
			} catch (Exception e) {
				e.printStackTrace();
				JSONUtil.responseJSON(response, new AjaxResult(2, "保洁申请失败！"));
			}
		} else {

			JSONUtil.responseJSON(response, new AjaxResult(3, "保洁申请已达上限！"));
		}

	}
	
	/**
	 * 保洁申请(公寓)-当新增保洁申请时，加载所有入住房间选择页面
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getApartmentRoom.do")
	public void getApartmentRoom(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchid = loginuser.getStaff().getBranchId();
		String sqltype = "clean_getallroomtype";
		String sqlroomidbytype = "clean_getallroomid";
		List<Map<String, String>> allroomtype = CleanService.findBySQL(sqltype, new String[]{branchid}, true);
		Map<String, List<Map<String, String>>> resultallroomid = new HashMap<String, List<Map<String, String>>>();
		for (Map<String, String> map : allroomtype) {
			String roomtype = map.get("ROOMTYPE");
			String roomname = map.get("ROOMNAME");
			List<Map<String, String>> roomidandType = CleanService.findBySQL(sqlroomidbytype, new String[] { branchid,
					roomtype }, true);
			resultallroomid.put(roomname, roomidandType);
		}
		JSONUtil.responseJSON(response, resultallroomid);
	}

	/**
	 * 保洁申请(公寓)-双击选择新增保洁申请的房间
	 * 
	 * @param request
	 * @param response
	 * @param roomid
	 * @param roomtype
	 * @param inputdate
	 * @param timearea
	 * @throws ParseException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getApartApplicationDate2.do")
	public void getApartApplicationDate2(HttpServletRequest request, HttpServletResponse response, String roomid,
			String roomtype,String inputdate,String timearea) throws ParseException {// 此处应将房型传来并且在合同中的状态为1,有效
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String branchid = staff.getBranchId();
		// String [] rooms = roomid.split(",");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date time = sdf.parse(inputdate);
		Date today = new Date();
		String today_s = sdf.format(today);
		List<Map<String, String>> contrartList = CleanService.findBySQL("findcontforaddclean", new String[] { branchid,
				roomtype,roomid,today_s }, true);
		Map<String, Object> map = new HashMap<String, Object>();
		
		CleanApply cp = (CleanApply) CleanService.findOneByProperties(CleanApply.class, "branchId", branchid, "roomId",
				roomid, "status", "1","cleanTime",time,"timeArea",timearea);
		if (null != cp ) {

			JSONUtil.responseJSON(response, new AjaxResult(0, "该房间有未处理申请!"));
		} else if(contrartList.size()> 1 ){
			JSONUtil.responseJSON(response, new AjaxResult(0, "包含多个合同!"));
			
		}else if(contrartList.size() == 0){
			JSONUtil.responseJSON(response, new AjaxResult(0, "未找到有效合同!"));
		}else{
			String contractId = contrartList.get(0).get("CONTRARTID").toString();
			String memberId = contrartList.get(0).get("MEMBERID").toString();
			Member mr = (Member) CleanService.findOneByProperties(Member.class, "memberId", memberId,"status","1");
			if(mr != null){
				String memberName = mr.getMemberName();
				String mobile = contrartList.get(0).get("MOBILE").toString();
				map.put("contrartid", contractId);
				map.put("membername", memberName);
				map.put("mobile", mobile);
				map.put("memberId", memberId);
				JSONUtil.responseJSON(response, map);
			}else{
				JSONUtil.responseJSON(response, new AjaxResult(0, "未找到会员!"));
			}
			
		}
		

	}
	
	/**
	 * 保洁申请(公寓)-点击保洁处理时，查找当前门店下的所有员工
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/selectApartallstaff.do")
	public void selectApartallstaff(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchid = loginuser.getStaff().getBranchId();
		// String timer=time.trim();
		List<?> allstaff = CleanService.findstaffByBranchId(branchid,CommonConstants.Postgrade.CLEANSTAFF);
		JSONUtil.responseJSON(response, allstaff);

	}
	
	// 保洁记录插入
	/**
	 * 保洁申请(公寓)-派遣保洁员时，添加派遣记录
	 * 
	 * @param request
	 * @param response
	 * @param recordId
	 * @param cleanTime
	 * @param timeArea
	 * @param roomId
	 * @param cleanPerson
	 * @param remark
	 * @param memberid
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/updateApartcleanrecord.do")
	public void updateApartcleanrecord(HttpServletRequest request, HttpServletResponse response, String recordId,
			String cleanTime, String timeArea, String roomId, String cleanPerson, String remark, String memberid, String repairPerson_CUSTOM_VALUE)
			throws Exception {
		cleanPerson = repairPerson_CUSTOM_VALUE;
//			if(HeartBeatClient.heartbeat){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		SimpleDateFormat sdf_msg = new SimpleDateFormat("yyyy年MM月dd日");
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchid = loginuser.getStaff().getBranchId();
		String staffid = loginuser.getStaff().getStaffId();
		CleanApply cleanApply = (CleanApply) CleanService.findOneByProperties(CleanApply.class, "cleanApplyId",
				recordId);
		Date cleandate = sdf.parse(sdf.format(cleanApply.getCleanTime()));
		Date cleanapplicationdate =sdf.parse(sdf.format(cleanApply.getApplicationTime()));
		
		String contrartId = cleanApply.getContractId();
	  // String cleanApplyId = cleanApply.getCleanApplyId();
		String payment = cleanApply.getPayment();
		Double cleanprice = cleanApply.getCleanPrice();
		String roomid_msg = cleanApply.getRoomId();
		String cleantime_msg = sdf_msg.format(cleanApply.getCleanTime());
		String timearea_msg = cleanApply.getTimeArea();
		String errmsg = null;
		String token = "";
		if ("0".equals(timearea_msg)) {
			timearea_msg = "上午";
		} else {
			timearea_msg = "下午";
		}
		
		//Date cleandate = sdf.parse(cleanTime);
		// String cleandate2 = sdf2.format(cleandate);
		if ("上午".equals(timeArea)) {
			timeArea = "0";
		} else {
			timeArea = "1";
		}
		
		Clean clean = (Clean) CleanService.findOneByProperties(Clean.class, "cleanDate", cleandate, "timeArea",
				timeArea, "branchId", branchid);
		//clean.setBranchId(branchid);
		//clean.setCleanDate(cleandate);
		int n =clean.getRestAmount();
		n--;
		if(n >= 0){
			com.ideassoft.portal.DataDealPortalService service = new com.ideassoft.portal.DataDealPortalService();
			com.ideassoft.portal.IDataDealPortal portal = service.getDataDealPortalPort();

			String backinfo = portal.call(SystemConstants.EnginType.COMMON_DATA, SystemConstants.Movement.CUSTOM_QUERY, "{ function: \"apartmentManager.findtokens\", data:{aa:bb} }");
			if((!backinfo.equals(Integer.toString(SystemConstants.PortalResultCode.FAILED))) && 
					(!backinfo.equals(Integer.toString(SystemConstants.PortalResultCode.NULL)))){
				token  = backinfo;
				
			}

		 List<?> memberlist = CleanService.findBySQL("queryOpenId",new
		 String[]{memberid}, true);
		 String openId = ((Map<String,String>)memberlist.get(0)).get("OPENID")== null ? "" :((Map<String,String>)memberlist.get(0)).get("OPENID").toString();
		 String mobile = ((Map<String,String>)memberlist.get(0)).get("MOBILE")== null ? "" :((Map<String,String>)memberlist.get(0)).get("MOBILE").toString();

			//List<?> tokenlist = CleanService.findBySQL("queryRepairToken", true);
			ServiceMsg serviceMsg = new ServiceMsg();
			serviceMsg.setFirst("您好！您有新的保洁服务通知！");
			serviceMsg.setRoomId(roomid_msg);
			//serviceMsg.setServiceType();
			// serviceMsg.setServiceType();
			serviceMsg.setServiceContent("保洁申请");
			serviceMsg.setRemark("您预约的 " + cleantime_msg + " " + timearea_msg + " " + roomid_msg + "房间的保洁申请已受理!");
			
			JSONObject jo = TemplateMessageUtil.sendServiceMsg(token, openId, serviceMsg);
			errmsg = (String) jo.get("errmsg");
			
			
			//发送短信
			String phoneNumber1 = mobile;
	
	    	SmsSingleSender singleSender = new SmsSingleSender(SystemConstants.note.APPID, SystemConstants.note.APPKEY);
	    	SmsSingleSenderResult singleSenderResult;
	    	
	    	ArrayList<String> params = new ArrayList<String>();
	    	//params.add("您预约的 " + cleantime_msg + " " + timearea_msg + " " + roomid_msg + "房间的保洁申请已受理!");
	    	params.add(cleantime_msg);
	    	params.add(timearea_msg);
	    	params.add(roomid_msg);
	    	params.add("已受理");
	    	singleSenderResult = singleSender.sendWithParam(SystemConstants.note.COUNTRY, phoneNumber1, 52202, params, "", "", "");
    	
		
		if ("ok".equals(errmsg)) {
			try {
					if (null == clean.getRoomId()) {
						clean.setRoomId(roomId);
					} else {
						clean.setRoomId(clean.getRoomId() + "," + roomId);
					}
					if (null == clean.getCleanPerson()) {
						clean.setCleanPerson(cleanPerson);
					} else {
						clean.setCleanPerson(clean.getCleanPerson() + "," + cleanPerson);
					}
					
					Date clean_cleandate = sdf.parse(sdf.format(clean.getCleanDate()));
					clean.setCleanDate(clean_cleandate);
					clean.setRecordTime(new Date());
					clean.setRestAmount(n);

					cleanApply.setStatus("2");
					cleanApply.setCleanTime(cleandate);
					cleanApply.setApplicationTime(cleanapplicationdate);
					cleanApply.setRecordTime(new Date());
					
					CleanService.update(clean);
					CleanService.update(cleanApply);
					logServiceInPms.updatecleanapplyrecordsuccess((LoginUser) request.getSession(true).getAttribute(
							RequestUtil.LOGIN_USER_SESSION_KEY), remark, "7",request);
					List<WorkBill> listworkbill = CleanService.findByProperties(WorkBill.class, "branchId", branchid, "recordUser",
							staffid, "status", "1");
					
					if (listworkbill.size() > 0) {
						String workBillId = listworkbill.get(0).getWorkbillId();
						Date day = new Date();
						SimpleDateFormat df = new SimpleDateFormat("yyMMdd");
						String t = df.format(day);
						String logId = this.CleanService.getSequence("SEQ_NEW_WORKBILLDETAIL", null);
						WorkBillDetail workbilldetail = new WorkBillDetail();
						workbilldetail.setDetailId(t + branchid + logId);
						workbilldetail.setBranchId(branchid);
						workbilldetail.setProjectId(CommonConstants.BillProject.CLEANPRICE);
						workbilldetail.setProjectName("保洁费");
						workbilldetail.setDescribe("服务");
						workbilldetail.setPay((double) 0);
						workbilldetail.setCost(cleanprice);
						workbilldetail.setPayment("1");
						workbilldetail.setStatus("1");
						workbilldetail.setRecordUser(staffid);
						workbilldetail.setRecordTime(new Date());
						workbilldetail.setWorkbillId(workBillId);
						CleanService.save(workbilldetail);
						
						Recording recording = new Recording();
						String work = DateUtil.currentDate("yyMMdd") + branchid + CleanService.getSequence("SEQ_NEW_RECORDING");
						recording.setRecordId(work);
						recording.setBranchId(branchid);
						recording.setCheckId(workBillId);
						recording.setRecordType("2");
						recording.setProjectId(CommonConstants.BillProject.CLEANPRICE);
						recording.setFee(cleanprice);
						recording.setRecordUser(staffid);
						CleanService.save(recording);
					}
					//插入销售日志
					String salelogId = this.CleanService.getSequence("SEQ_NEW_SALELOG", null);
					String saledatId = DateUtil.currentDate("yyMMdd") + branchid + salelogId;
					SaleLog salelog = new SaleLog();
					salelog.setLogId(saledatId);
					salelog.setBranchId(branchid);
					salelog.setDebts("1");
					salelog.setCheckId(contrartId);
					salelog.setRoomId(roomid_msg);
					salelog.setCategoryId("10000001");
					salelog.setGoodsName("10000001");
					salelog.setAmount(1);
					salelog.setPrice(cleanprice);
					salelog.setPayType(payment);
					salelog.setRemark("保洁");
					salelog.setRecordUser(staffid);
					CleanService.save(salelog);
					JSONUtil.responseJSON(response, new AjaxResult(0, "处理成功!"));
				
				

			} catch (Exception e) {
				e.printStackTrace();
				logServiceInPms.updatecleanapplyrecordfailed((LoginUser) request.getSession(true).getAttribute(
						RequestUtil.LOGIN_USER_SESSION_KEY), remark, "7",request);
				JSONUtil.responseJSON(response, new AjaxResult(1, "处理失败!"));
			}

			
			
		} else {

				if (null == clean.getRoomId()) {
					clean.setRoomId(roomId);
				} else {
					clean.setRoomId(clean.getRoomId() + "," + roomId);
				}
				if (null == clean.getCleanPerson()) {
					clean.setCleanPerson(cleanPerson);
				} else {
					clean.setCleanPerson(clean.getCleanPerson() + "," + cleanPerson);
				}
				Date clean_cleandate = sdf.parse(sdf.format(clean.getCleanDate()));
				
				clean.setCleanDate(clean_cleandate);
				clean.setRecordTime(new Date());
				clean.setRestAmount(n);
			//	cleanList.add(clean);
				try {

				cleanApply.setStatus("2");
				cleanApply.setCleanTime(cleandate);
				cleanApply.setApplicationTime(cleanapplicationdate);
				cleanApply.setRecordTime(new Date());
				
				//cleanapplyList.add(cleanApply);
				CleanService.update(clean);
				CleanService.update(cleanApply);
				logServiceInPms.updatecleanapplyrecordsuccess((LoginUser) request.getSession(true).getAttribute(
						RequestUtil.LOGIN_USER_SESSION_KEY), remark, "7",request);
				List<WorkBill> listworkbill = CleanService.findByProperties(WorkBill.class, "branchId", branchid, "status", "1");
				
				if (listworkbill.size() > 0) {
					String workBillId = listworkbill.get(0).getWorkbillId();
					Date day = new Date();
					SimpleDateFormat df = new SimpleDateFormat("yyMMdd");
					String t = df.format(day);
					String logId = this.CleanService.getSequence("SEQ_NEW_WORKBILLDETAIL", null);

					WorkBillDetail workbilldetail = new WorkBillDetail();
					workbilldetail.setDetailId(t + branchid + logId);
					workbilldetail.setBranchId(branchid);
					workbilldetail.setProjectId(CommonConstants.BillProject.CLEANPRICE);
					workbilldetail.setProjectName("保洁费");
					workbilldetail.setDescribe("服务");
					workbilldetail.setPay((double) 0);
					workbilldetail.setCost(cleanprice);
					workbilldetail.setPayment("1");
					workbilldetail.setStatus("1");
					workbilldetail.setRecordUser(staffid);
					workbilldetail.setRecordTime(new Date());
					workbilldetail.setWorkbillId(workBillId);
					CleanService.save(workbilldetail);
					
					Recording recording = new Recording();
					String work = DateUtil.currentDate("yyMMdd") + branchid + CleanService.getSequence("SEQ_NEW_RECORDING");
					recording.setRecordId(work);
					recording.setBranchId(branchid);
					recording.setCheckId(workBillId);
					recording.setRecordType("2");
					recording.setProjectId(CommonConstants.BillProject.CLEANPRICE);
					recording.setFee(cleanprice);
					recording.setRecordUser(staffid);
					CleanService.save(recording);
					
				}
				//插入销售日志
				String salelogId = this.CleanService.getSequence("SEQ_NEW_SALELOG", null);
				String saledatId = DateUtil.currentDate("yyMMdd") + branchid + salelogId;
				SaleLog salelog = new SaleLog();
				salelog.setLogId(saledatId);
				salelog.setBranchId(branchid);
				salelog.setDebts("1");
				salelog.setCheckId(contrartId);
				salelog.setRoomId(roomid_msg);
				salelog.setCategoryId("10000001");
				salelog.setGoodsName("10000001");
				salelog.setAmount(1);
				salelog.setPrice(cleanprice);
				salelog.setPayType(payment);
				salelog.setRemark("保洁");
				salelog.setRecordUser(staffid);
				CleanService.save(salelog);
				
				//JSONUtil.responseJSON(response, new AjaxResult(0, "微信消息发送失败!"));
				JSONUtil.responseJSON(response, new AjaxResult(0, "处理成功!"));
			} catch (Exception e) {
				e.printStackTrace();
				logServiceInPms.updatecleanapplyrecordfailed((LoginUser) request.getSession(true).getAttribute(
						RequestUtil.LOGIN_USER_SESSION_KEY), remark, "7",request);
				JSONUtil.responseJSON(response, new AjaxResult(1, "处理失败!"));
			}
			

		}
		

	}else{
		logServiceInPms.updatecleanapplyrecordfailed((LoginUser) request.getSession(true).getAttribute(
				RequestUtil.LOGIN_USER_SESSION_KEY), remark, "7",request);
		JSONUtil.responseJSON(response, new AjaxResult(1, "处理失败!"));
	}
		/*}else{
			JSONUtil.responseJSON(response, new AjaxResult(1, "未连接网络!"));
			
		}*/
	}
	
	/**
	 * 保洁申请(公寓)-设置可保洁数
	 * 
	 * @param request
	 * @param response
	 * @param time
	 * @param timearea
	 * @param restamount
	 * @throws Exception
	 */
	@RequestMapping("/setApartmentamount.do")
	public void setApartmentamount(HttpServletRequest request, HttpServletResponse response, String time, String timearea,
			String restamount) throws Exception {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchid = loginuser.getStaff().getBranchId();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date cleanDate = sdf.parse(time);
		try {
			Clean clean = (Clean) CleanService.findOneByProperties(Clean.class, "cleanDate", cleanDate, "timeArea",
					timearea, "branchId", branchid);
			clean.setRestAmount(Integer.parseInt(restamount));
			clean.setRecordTime(new Date());
			CleanService.update(clean);
			JSONUtil.responseJSON(response, new AjaxResult(1, "修改成功!"));
		} catch (Exception e) {
			e.printStackTrace();
			JSONUtil.responseJSON(response, new AjaxResult(2, "修改失败!"));
		}

	}
	
	/**
	 * 保洁申请(公寓)-设置可保洁数(预留)
	 * 
	 * @param request
	 * @param response
	 * @param restamount
	 * @throws Exception
	 */
	@RequestMapping("/setApartmentamountinit.do")
	public void setApartmentamountinit(HttpServletRequest request, HttpServletResponse response,
			String restamount) throws Exception {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchid = loginuser.getStaff().getBranchId();

		try {
			SysParam sys = (SysParam) CleanService.findOneByProperties(SysParam.class, "paramType", "CLEANAMOUNT","paramDesc",branchid);
			sys.setContent(restamount);
			
			CleanService.update(sys);
			JSONUtil.responseJSON(response, new AjaxResult(1, "修改成功!"));
		} catch (Exception e) {
			e.printStackTrace();
			JSONUtil.responseJSON(response, new AjaxResult(2, "修改失败!"));
		}

	}
	
	/**
	 * 暂无使用
	 * 
	 * @param request
	 * @param response
	 * @param time
	 * @param timearea
	 * @param amount
	 * @throws Exception
	 */
	@RequestMapping("/resetamount.do")
	public void resetAmount(HttpServletRequest request, HttpServletResponse response, String time, String timearea,
			String amount) throws Exception {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchid = loginuser.getStaff().getBranchId();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date cleanDate = sdf.parse(time);
		if ("上午".equals(timearea)) {
			timearea = "0";
		} else {
			timearea = "1";
		}
		try {
			Clean clean = (Clean) CleanService.findOneByProperties(Clean.class, "cleanDate", cleanDate, "timeArea",
					timearea, "branchId", branchid);
			clean.setRestAmount(Integer.parseInt(amount));
			clean.setRecordTime(new Date());
			CleanService.update(clean);
			JSONUtil.responseJSON(response, new AjaxResult(1, "这是后台重新修改数量时传来的成功消息!"));
		} catch (Exception e) {
			e.printStackTrace();
			JSONUtil.responseJSON(response, new AjaxResult(2, "这是后台重新修改数量时传来的失败消息!"));
		}

	}
	
	/**
	 * 暂无使用
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/selectallroomid.do")
	public void selectallroomid(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchid = loginuser.getStaff().getBranchId();
		// String timer=time.trim();
		List<?> allroomid = CleanService.findRoomByBranchId(branchid);
		JSONUtil.responseJSON(response, allroomid);

	}
	
	/**
	 * 暂无使用
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/querycleanrecord.do")
	public ModelAndView querycleanrecord(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		Pagination pagination = SqlBuilder.buildPagination(request);
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchid = loginuser.getStaff().getBranchId();
		List<?> record = CleanService.queryAllOfRecord(branchid, pagination);
		mv.setViewName("page/ipmspage/leftmenu/cleanrecord/recorddata");
		mv.addObject("pagination", pagination);
		mv.addObject("record", record);
		return mv;
	}
	
	/**
	 * 暂无使用
	 * 
	 * @param request
	 * @param time
	 * @param status
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/querycleanrecordcondition.do")
	public ModelAndView querycleanrecordcondition(HttpServletRequest request, String time, String status)
			throws Exception {
		ModelAndView mv = new ModelAndView();
		Pagination pagination = SqlBuilder.buildPagination(request);
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchid = loginuser.getStaff().getBranchId();
		List<?> recorddataCondition = CleanService.queryRecordByCondition(branchid, time, status, pagination);
		mv.setViewName("page/ipmspage/leftmenu/cleanrecord/recorddatacondition");
		mv.addObject("recorddataCondition", recorddataCondition);
		return mv;
	}

	/**
	 * 暂无使用
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/cleanstylechange.do")
	public void getgoodsforclean(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchid = loginuser.getStaff().getBranchId();
		Goods goodsForClean = (Goods)CleanService.findOneByProperties(Goods.class, "goodsName", "保洁", "branchId",branchid,"status","1");
		String cleanprice = null;
		if(null!=goodsForClean){
		 cleanprice =goodsForClean.getPrice().toString();
		}
		JSONUtil.responseJSON(response, new AjaxResult(1, cleanprice));
	}
	
}
