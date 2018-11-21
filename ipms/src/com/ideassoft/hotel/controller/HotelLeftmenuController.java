package com.ideassoft.hotel.controller;

import java.io.File;
import java.io.IOException;
import java.math.RoundingMode;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Source;

import org.apache.cxf.common.util.StringUtils;
import org.hsqldb.lib.StringUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ideassoft.basic.service.MarketingBasicService;
import com.ideassoft.bean.Bill;
import com.ideassoft.bean.Branch;
import com.ideassoft.bean.Check;
import com.ideassoft.bean.CheckUser;
import com.ideassoft.bean.Contrart;
import com.ideassoft.bean.DataSource;
import com.ideassoft.bean.ExceptionMessage;
import com.ideassoft.bean.Goods;
import com.ideassoft.bean.LoginLog;
import com.ideassoft.bean.LoginLogId;
import com.ideassoft.bean.Member;
import com.ideassoft.bean.MemberAccount;
import com.ideassoft.bean.MemberRank;
import com.ideassoft.bean.OperateLog;
import com.ideassoft.bean.OrdchePrice;
import com.ideassoft.bean.Order;
import com.ideassoft.bean.PettyCash;
import com.ideassoft.bean.Post;
import com.ideassoft.bean.Recording;
import com.ideassoft.bean.RightDefine;
import com.ideassoft.bean.Role;
import com.ideassoft.bean.RoleRelation;
import com.ideassoft.bean.Room;
import com.ideassoft.bean.RoomStatus;
import com.ideassoft.bean.SaleLog;
import com.ideassoft.bean.Shift;
import com.ideassoft.bean.ShiftTime;
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
import com.ideassoft.core.constants.CommonConstants.BillPayment;
import com.ideassoft.core.constants.CommonConstants.DefaultRoomPrice;
import com.ideassoft.core.exception.DAOException;
import com.ideassoft.core.jdbc.SqlBuilder;
import com.ideassoft.core.notifier.SmsSingleSender;
import com.ideassoft.core.page.Pagination;
import com.ideassoft.crm.service.CommonService;
import com.ideassoft.crm.service.InitialService;
import com.ideassoft.crm.service.TransferServcie;
import com.ideassoft.hotel.service.HotelLeftmenuService;
import com.ideassoft.pms.service.RoomService;
import com.ideassoft.pmsinhouse.controller.HouseLeftMenuOrderController;
import com.ideassoft.pmsmanage.service.PmsmanageService;
import com.ideassoft.portal.DataDealPortalService;
import com.ideassoft.portal.IDataDealPortal;
import com.ideassoft.price.MultiPrice;
import com.ideassoft.price.RealPrice;
import com.ideassoft.price.SinglePrice;
import com.ideassoft.util.ChineseCharacterUtil;
import com.ideassoft.util.DateUtil;
import com.ideassoft.util.JSONUtil;
import com.ideassoft.util.MD5Util;
import com.ideassoft.util.ReflectUtil;
import com.ideassoft.util.RegExUtil;
import com.ideassoft.util.RemoteTransUtil;
import com.ideassoft.util.RequestUtil;
import com.ideassoft.util.RoomUtil;
import com.ideassoft.util.WeixinUtil;

@Transactional
@Controller
@RightModelControl(rightModel = RightConstants.RightModel.HOTEL_CONTROL)
public class HotelLeftmenuController {

	private static List<?> beans = null;

	private static String tempPoint = null;

	private static String currPoint = null;

	private static SysParam checkPoint = null;

	@Autowired
	private HotelLeftmenuService leftmenuService;

	@Autowired
	private CommonService commonService;

	@Autowired
	private RoomService roomService;

	@Autowired
	private PmsmanageService pmsmanageService;

	@Autowired
	InitialService initialService;
	
	
	@Autowired
	private MarketingBasicService marketingBasicService;
	
	
	@RequestMapping("/hotelorderSerach.do")
	@RightMethodControl(rightType = RightConstants.RightType.HOT_ORSEA)
	public ModelAndView orderSerach(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/ipmshotel/leftmenu/ordercheckin/ordersearch");
		return mv;
	}
	
	@RequestMapping("/hotelorderData.do")
	public ModelAndView orderData(HttpServletRequest request, String orderid, String orderuser, String roomtype,
			String mobile, String memberid) throws Exception {
		ModelAndView mv = new ModelAndView();
		Pagination pagination = SqlBuilder.buildPagination(request);
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginuser.getStaff().getBranchId();
		List<?> orderlist = leftmenuService.getOrdercondition(pagination, orderid, orderuser, roomtype, mobile,
				memberid, branchId);
		mv.setViewName("page/ipmshotel/leftmenu/ordercheckin/orderdata");
		mv.addObject("orderlist", orderlist);
		mv.addObject("pagination", pagination);
		return mv;
	}
	
	/**
	 * 预定直接入住合并
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/hotelcheckInByOrder.do")
	@RightMethodControl(rightType = RightConstants.RightType.HOT_ORCH)
	public ModelAndView checkInByOrder(HttpServletRequest request,HttpServletResponse response){
		String roomType = request.getParameter("roomType");
		String roomId = request.getParameter("roomId");
		ModelAndView mv = new ModelAndView(); 
		mv.setViewName("page/ipmshotel/leftmenu/leftorder/checkInByOrder");
		if(roomType != null && roomId != null){
			mv.addObject("roomType",roomType);
			mv.addObject("roomId",roomId);
		}
		return mv;
	}
	
	@RequestMapping("/hotelorderNew.do")
	public ModelAndView orderCheckIn(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchid = loginuser.getStaff().getBranchId();
		List<?> roomtype = leftmenuService.getRoomtype(branchid);
		List<?> theme = leftmenuService.getTheme();
		String rpId = "MSJ";
		String outlinerpid = null;
		List<?> roomprice = leftmenuService.getRoomprice(rpId, branchid);
		List<?> outlineroomprice = leftmenuService.getRoomprice(outlinerpid, branchid);
		List<?> roompriceadjust = leftmenuService.getRoompriceadjust(rpId, branchid);
		if (null == roomprice || roomprice.size() == 0) {
			roomprice = roompriceadjust;
		} else if (null == roompriceadjust || roompriceadjust.size() == 0) {

		} else {

			roomprice = roompriceadjust;
		}
		List<?> guarantee = leftmenuService.getGuarantee();
		List<?> rpsetup = pmsmanageService.getRpsetup();
		mv.setViewName("page/ipmshotel/leftmenu/leftorder/order");
		request.setAttribute("roomtype", roomtype);
		request.setAttribute("theme", theme);
		request.setAttribute("roomprice", roomprice);
		request.setAttribute("guarantee", guarantee);
		request.setAttribute("rpsetup", rpsetup);
		//request.setAttribute("outlineroomprice", outlineroomprice);
		return mv;
	}
	
	/*
	 * 直接入住
	 */
	@RequestMapping("/hotelcheckInDirect.do")
	public ModelAndView checkInDirect(HttpServletRequest request) {
		String roomType = request.getParameter("roomType");
		String roomId = request.getParameter("roomId");
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginuser.getStaff().getBranchId();
		List<?> roomtypes = leftmenuService.findBySQL("checkinroomtype", new String[] { branchId }, true);
		List<?> memberranks = leftmenuService.findBySQL("queryMemberRanks", true);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/ipmshotel/leftmenu/checkInDirect/checkInDirect");
		mv.addObject("roomtypes", roomtypes);
		mv.addObject("memberranks", JSONUtil.fromObject(memberranks));
		if (roomType != null && roomId != null) {
			mv.addObject("roomType", roomType);
			mv.addObject("roomId", roomId);
		}
		return mv;
	}
	
	@RequestMapping("/hotelgoods.do")
	@RightMethodControl(rightType = RightConstants.RightType.HOT_SELL)
	public ModelAndView goods(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchid = loginuser.getStaff().getBranchId();
		List<?> categoryCondition = leftmenuService.getCategorycondition(branchid);
		mv.setViewName("page/ipmshotel/leftmenu/goodssale/goods");
		request.setAttribute("categoryCondition", categoryCondition);
		return mv;
	}
	
	@RequestMapping("/hotelgoodsPage.do")
	public ModelAndView goodsPage(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchid = loginuser.getStaff().getBranchId();
		/*if (branchid.equals(SystemConstants.User.BRANCH_ID)) {
			branchid = "%";
		}*/
		List<?> categoryCondition = leftmenuService.getCategorycondition(branchid);
		mv.setViewName("page/ipmshotel/leftmenu/goodssale/goodspage");
		request.setAttribute("categoryCondition", categoryCondition);
		return mv;
	}

	@RequestMapping("/hotelmemberSelect.do")
	public void memberSelect(HttpServletRequest request, HttpServletResponse response, String Mnumber) throws Exception {
		String a = null;
		String memberid1 = "";
		// 检测心跳
//		if (HeartBeatClient.heartbeat) {
			// 根据手机号或者身份证从云端查会员信息
			com.ideassoft.portal.DataDealPortalService service = new com.ideassoft.portal.DataDealPortalService();
			com.ideassoft.portal.IDataDealPortal portal = service.getDataDealPortalPort();
			String mr = portal.call(1, 1, "{ function: \"checkInDirect.getMemberBean\", data:{idcard:" + Mnumber
					+ " } }");
			// 判断云端是否有该会员数据
			if (mr.equals("-1")) {
				a = "未查询到相关会员信息或该会员已失效，请先注册会员！";
			} else if("hasmult".equals(mr)){
				a = "存在多条注册信息，请先更新会员信息！";
				//JSONUtil.responseJSON(response, new AjaxResult(1, a));
				//return;
			}else{
				// 判断本地端是否有该会员数据
				List<?> memberdata = leftmenuService.getMemberdata(Mnumber);
				if (memberdata.size() <= 0) {
					memberdata = leftmenuService.findBySQL("queryMemCorId", new String[]{Mnumber}, true);
				}
				JSONArray mrr = new JSONArray(mr);
				DateFormat dff = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				if (mrr.length() > 0) {
					for (int i = 0; i < mrr.length(); i++) {
						JSONObject mrrr = mrr.getJSONObject(i);
						Date birth;
						if (mrrr.getString("BIRTHDAY") == "null") {
							birth = null;
						} else {
							birth = dff.parse(mrrr.getString("BIRTHDAY"));
						}
						Date recordt;
						if (mrrr.getString("RECORD_TIME") == "null") {
							recordt = null;
						} else {
							recordt = dff.parse(mrrr.getString("RECORD_TIME"));
						}
						// 本地端没有会员数据，从云端拉取该会员数据和该会员账户表数据
						if (null == memberdata || memberdata.size() == 0) {
							Member member = new Member();
							member
									.setMemberId(mrrr.getString("MEMBER_ID") == "null" ? "" : mrrr
											.getString("MEMBER_ID"));
							member.setMemberName(mrrr.getString("MEMBER_NAME") == "null" ? "" : mrrr
									.getString("MEMBER_NAME"));
							member.setLoginName(mrrr.getString("LOGIN_NAME") == "null" ? "" : mrrr
									.getString("LOGIN_NAME"));
							member.setPassword(mrrr.getString("PASSWORD") == "null" ? "" : mrrr.getString("PASSWORD"));
							member.setMemberRank(mrrr.getString("MEMBER_RANK") == "null" ? "" : mrrr
									.getString("MEMBER_RANK"));
							member.setIdcard(mrrr.getString("IDCARD") == "null" ? "" : mrrr.getString("IDCARD"));
							member.setGendor(mrrr.getString("GENDOR") == "null" ? "" : mrrr.getString("GENDOR"));
							member.setBirthday(birth);
							member.setMobile(mrrr.getString("MOBILE") == "null" ? "" : mrrr.getString("MOBILE"));
							member.setEmail(mrrr.getString("EMAIL") == "null" ? "" : mrrr.getString("EMAIL"));
							member.setAddress(mrrr.getString("ADDRESS") == "null" ? "" : mrrr.getString("ADDRESS"));
							member.setPostcode(mrrr.getString("POSTCODE") == "null" ? "" : mrrr.getString("POSTCODE"));
							member.setPhotos(mrrr.getString("PHOTOS") == "null" ? "" : mrrr.getString("PHOTOS"));
							member.setSource(mrrr.getString("SOURCE") == "null" ? "" : mrrr.getString("SOURCE"));
							Date vtime = dff.parse(mrrr.getString("VALID_TIME"));
							member.setValidTime(vtime);
							member.setInvalidTime(dff.parse(mrrr.getString("INVALID_TIME")));
							member.setRegisterTime(dff.parse(mrrr.getString("REGISTER_TIME")));
							member.setRecordTime(recordt);
							member.setStatus(mrrr.getString("STATUS"));
							member.setRemark(mrrr.getString("REMARK") == "null" ? "" : mrrr.getString("REMARK"));
							member.setOpenId(mrrr.getString("OPENID"));
							member.setRecommend(mrrr.getString("RECOMMEND") == "null" ? "" : mrrr.getString("REMARK"));
							leftmenuService.save(member);
							String memberId = mrrr.getString("MEMBER_ID").toString();
							String mac = portal.call(1, 1,
									"{ function: \"checkInDirect.getMemberAccount\", data:{memberId:" + memberId
											+ " } }");
							JSONArray macc = new JSONArray(mac);
							if (macc.length() > 0) {
								for (int j = 0; j < macc.length(); j++) {
									JSONObject maccc = macc.getJSONObject(j);
									MemberAccount memberaccount = new MemberAccount();
									memberaccount.setAccountId(maccc.getString("ACCOUNT_ID"));
									memberaccount.setMemberId(maccc.getString("MEMBER_ID"));
									memberaccount.setCurrBalance(maccc.getDouble("CURR_BALANCE"));
									memberaccount.setCurrIntegration(maccc.getLong("CURR_INTEGRATION"));
									memberaccount.setTotalRecharge(maccc.getDouble("TOTAL_RECHARGE"));
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
									leftmenuService.save(memberaccount);
								}
							}
							memberid1 = memberId;
							// 本地端有会员数据，从云端更新该会员数据
						} else {
							String memberid = mrrr.getString("MEMBER_ID").toString();
							memberid1 = memberid;
							String mobile = mrrr.getString("MOBILE").toString();
							Member memberupdate = ((Member) (leftmenuService.findOneByProperties(Member.class,
									"memberId", memberid, "mobile", mobile)));
							memberupdate.setMemberName(mrrr.getString("MEMBER_NAME") == "null" ? "" : mrrr
									.getString("MEMBER_NAME"));
							memberupdate.setLoginName(mrrr.getString("LOGIN_NAME") == "null" ? "" : mrrr
									.getString("LOGIN_NAME"));
							memberupdate.setPassword(mrrr.getString("PASSWORD") == "null" ? "" : mrrr
									.getString("PASSWORD"));
							memberupdate.setMemberRank(mrrr.getString("MEMBER_RANK") == "null" ? "" : mrrr
									.getString("MEMBER_RANK"));
							memberupdate.setIdcard(mrrr.getString("IDCARD") == "null" ? "" : mrrr.getString("IDCARD"));
							memberupdate.setGendor(mrrr.getString("GENDOR") == "null" ? "" : mrrr.getString("GENDOR"));
							memberupdate.setBirthday(birth);
							memberupdate.setEmail(mrrr.getString("EMAIL") == "null" ? "" : mrrr.getString("EMAIL"));
							memberupdate.setAddress(mrrr.getString("ADDRESS") == "null" ? "" : mrrr
									.getString("ADDRESS"));
							memberupdate.setPostcode(mrrr.getString("POSTCODE") == "null" ? "" : mrrr
									.getString("POSTCODE"));
							memberupdate.setPhotos(mrrr.getString("PHOTOS") == "null" ? "" : mrrr.getString("PHOTOS"));
							memberupdate.setSource(mrrr.getString("SOURCE") == "null" ? "" : mrrr.getString("SOURCE"));
							Date vtime = dff.parse(mrrr.getString("VALID_TIME"));
							memberupdate.setValidTime(vtime);
							memberupdate.setInvalidTime(dff.parse(mrrr.getString("INVALID_TIME")));
							memberupdate.setRegisterTime(dff.parse(mrrr.getString("REGISTER_TIME")));
							memberupdate.setRecordTime(recordt);
							memberupdate.setStatus(mrrr.getString("STATUS"));
							memberupdate.setRemark(mrrr.getString("REMARK") == "null" ? "" : mrrr.getString("REMARK"));
							memberupdate.setOpenId(mrrr.getString("OPENID"));
							memberupdate.setRecommend(mrrr.getString("RECOMMEND") == "null" ? "" : mrrr
									.getString("REMARK"));
							leftmenuService.update(memberupdate);
						}
					}
				}

			}

		/*} else {
			a = "本地网络未连接，请手动选择会员价，办理预订！";
		}*/

		/*
		 * List<?> memberdata = LeftmenuService.getMemberdata(Mnumber); String
		 * branchid = loginuser.getStaff().getBranchId(); if ((((Map<?, ?>)
		 * memberdata.get(0)).get("RPID").toString()).equals("")) { a =
		 * "房价参数未配置，请先前往后台管理系统至少进行一次房价申请！"; } else { String rpId = ((Map<?, ?>)
		 * memberdata.get(0)).get("RPID").toString(); List<?> roomprice =
		 * LeftmenuService.getRoomprice(rpId, branchid); List<?> roompriceadjust
		 * = LeftmenuService.getRoompriceadjust(rpId, branchid); if ((null ==
		 * roomprice || roomprice.size() == 0) && (null == roompriceadjust ||
		 * roompriceadjust.size() == 0)) { a =
		 * "当前系统门店房价未配置或在审核中，请先前往后台管理系统进行配置！"; } }
		 */
		JSONUtil.responseJSON(response, new AjaxResult(1, a, memberid1));
	}

	@RequestMapping("/hoteljudgeUser.do")
	public ModelAndView judgeUser(HttpServletRequest request, HttpServletResponse response, String Mnumber)
			throws Exception {
		ModelAndView mv = new ModelAndView();
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		List<?> memberdata = leftmenuService.getMemberdata(Mnumber);
		if (memberdata.size() <=0) {
			Member members = (Member) leftmenuService.findOneByProperties(Member.class, "corporationId", Mnumber, "status", "1");
			if (members.getMobile() != null) {
				memberdata = leftmenuService.getMemberdata(members.getMobile().toString());
			}
		}
		String arrivedate = request.getParameter("arrivedate");
		String rpId = ((Map<?, ?>) memberdata.get(0)).get("RPID").toString();
		String memberrank = ((Map<?, ?>) memberdata.get(0)).get("MEMBERRANK").toString();
		String memeberId = ((Map<?, ?>) memberdata.get(0)).get("MEMBERID").toString();
		String discount = ((Map<?, ?>) memberdata.get(0)).get("DISCOUNT").toString();
		String branchid = loginuser.getStaff().getBranchId();
		//判断抵店日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		List pricess = null;
		if(sdf.parse(arrivedate).equals(sdf.parse(sdf.format(new Date())))){
			if("0".equals(memberrank)){
				pricess = RoomUtil.getNowRoomPrice(branchid,"MSJ",null);
			}else{
				pricess = RoomUtil.getNowRoomPrice(branchid,rpId,null);
			}
		}else{
			if("0".equals(memberrank)){
				pricess = RoomUtil.getForwardRoomPrice(branchid, arrivedate, "MSJ", null);
			}else{
				pricess = RoomUtil.getForwardRoomPrice(branchid, arrivedate, rpId, null);
			}
		} 
//		List<?> roomprice = null;
//		List<?> roompriceadjust = null;
//		if("0".equals(memberrank)){
//			roomprice = leftmenuService.getDiscountRoomprice(memeberId, branchid);
//			roompriceadjust = leftmenuService.getDiscountRoompriceadjust(memeberId, branchid);
//		}else{
//			roomprice = leftmenuService.getRoomprice(rpId, branchid);
//			roompriceadjust = leftmenuService.getRoompriceadjust(rpId, branchid);	
//		}
//
//		if (null == roomprice || roomprice.size() == 0) {
//			roomprice = roompriceadjust;
//		} else if (null == roompriceadjust || roompriceadjust.size() == 0) {
//
//		} else {
//
//			roomprice = roompriceadjust;
//		}
		List<?> guarantee = leftmenuService.getGuarantee();
		String niub = "[";
		for (int i = 0; i < pricess.size(); i++) {
			niub += pricess.get(i).toString().substring(1,pricess.get(i).toString().length()-1)+",";
		}
		niub = niub.substring(0,niub.length()-1)+"]";
		mv.setViewName("page/ipmshotel/leftmenu/leftorder/judgeusercheckin");// 二级页面
		request.setAttribute("memberdata", memberdata);
		request.setAttribute("roomprice", niub);
		request.setAttribute("guarantee", guarantee);
		request.setAttribute("rpId", rpId);
		request.setAttribute("discount", discount);
		return mv;
	}

	@RequestMapping("/hotelroomSelect.do")
	public ModelAndView roomSelect(HttpServletRequest request, HttpServletResponse response, String theme,
			String roomtype, String roomacount) throws Exception {
		ModelAndView mv = new ModelAndView();
		List<?> roomId = leftmenuService.getRoomid(theme, roomtype);
		List<?> roomTypename = leftmenuService.getTypename(roomtype);
		String roomtypename = ((Map<?, ?>) roomTypename.get(0)).get("room_name").toString();
		mv.setViewName("page/ipmshotel/leftmenu/leftorder/roomselect");
		request.setAttribute("roomId", roomId);
		request.setAttribute("roomtypename", roomtypename);
		request.setAttribute("roomacount", roomacount);
		return mv;
	}

	@RequestMapping("/hotelscoreExchange.do")
	public ModelAndView scoreExchange(HttpServletRequest request, HttpServletResponse response, String MemberId,
			String sjscore) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/ipmshotel/leftmenu/leftorder/scoreexchage");
		SysParam sysParamscore = ((SysParam) (leftmenuService.findOneByProperties(SysParam.class, "paramType", "SCORE")));
		SysParam sysParammoney = ((SysParam) (leftmenuService.findOneByProperties(SysParam.class, "paramType", "MONEY")));
		MemberAccount memberscore = ((MemberAccount) (leftmenuService.findOneByProperties(MemberAccount.class,
				"memberId", MemberId)));
		String currintegration = memberscore.getCurrIntegration().toString();
		String score = sysParamscore.getContent().toString();
		String money = sysParammoney.getContent().toString();
		request.setAttribute("score", score);
		request.setAttribute("money", money);
		request.setAttribute("currintegration", currintegration);
		request.setAttribute("sjscore", sjscore);
		return mv;
	}

	@SuppressWarnings( { "deprecation", "unchecked" })
	@RequestMapping("/hotelorderRoom.do")
	public void orderRoom(HttpServletRequest request, HttpServletResponse response, String ordertheme,
			String orderroomtype, String orderarrivedate, String orderleavedate, String orderacount, String orderrpid,
			String orderprice, String orderuser, String ordermobile, String orderlimited, String receptionremark,
			String roomremark, String ordercash, String ordercard, String ordervoucher, String orderscore,
			String ordergurantee, String checkuserarray,String membergendor) throws Exception {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		List<Member> memberList = leftmenuService.findByProperties(Member.class, "mobile",ordermobile,"status","1");
		Member member = null;
		for(int i = 0;i<memberList.size();i++){
			if(!memberList.get(i).getMemberRank().equals("1")){
				member = memberList.get(i);
				break;
			}
		}
		//Member member = ((Member) (leftmenuService.findOneByProperties(Member.class,  "mobile",ordermobile)));
		
		String memberid = null;
		SysParam systemtype = (SysParam) leftmenuService.findOneByProperties(SysParam.class, "paramType", "SYSTEMTYPE",
				"status", "1");
		String stype = systemtype.getContent().toString();
		if (null == member) {
			com.ideassoft.portal.DataDealPortalService service = new com.ideassoft.portal.DataDealPortalService();
			com.ideassoft.portal.IDataDealPortal portal = service.getDataDealPortalPort();
			String mr = portal.call(1, 1, "{ function: \"checkInDirect.getMemberBean\", data:{idcard:" + ordermobile
					+ " } }");
			if (mr.equals("-1")) {
				String memberaddid = this.leftmenuService.getCloudSequence("SEQ_MEMBER_ID", null);
				Member menberadd = new Member();
				menberadd.setMemberId(memberaddid);
				menberadd.setMemberName(orderuser);
				String loginName = ChineseCharacterUtil.convertHanzi2Pinyin(orderuser);
				menberadd.setLoginName(loginName);
				String pwd = MD5Util.getCryptogram("888888");
				menberadd.setPassword(pwd);
				menberadd.setMemberRank(CommonConstants.MembenRank.NM);
				menberadd.setGendor(membergendor);
				menberadd.setMobile(ordermobile);
				menberadd.setSource("1");
				Date day = new Date();
				SimpleDateFormat dfe = new SimpleDateFormat("yyyy/MM/dd");
				DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
				String t = dfe.format(day);
				Date D = df.parse(t);
				Calendar calendar = Calendar.getInstance();
				Date date = new Date(System.currentTimeMillis());
				calendar.setTime(date);
				calendar.add(Calendar.YEAR, +1);
				date = calendar.getTime();
				String s = dfe.format(date);
				Date Dl = df.parse(s);
				menberadd.setValidTime(D);
				menberadd.setRegisterTime(D);
				menberadd.setInvalidTime(Dl);
				menberadd.setStatus("1");
				leftmenuService.save(menberadd);
				/*
				 * if(stype.equals(CommonConstants.SystemType.Branch)){ int
				 * priority = 1; SysParam param = (SysParam)
				 * LeftmenuService.findOneByProperties(SysParam.class,
				 * "paramType","REMOTE_PATH", "status", "1"); List<Member>
				 * memberlList = new ArrayList<Member>();
				 * memberlList.add(menberadd); Map<String, Object> memberMap =
				 * new HashMap<String, Object>(); memberMap.put("Member",
				 * memberlList);
				 * TransferServcie.getInstance().transferData(priority,
				 * param.getContent
				 * (),JSONUtil.fromObject(memberMap).toString()); }
				 */
				String accountid = this.leftmenuService.getCloudSequence("SEQ_ACCOUNT_ID", null);
				MemberAccount memberaccount = new MemberAccount();
				memberaccount.setAccountId(accountid);
				memberaccount.setMemberId(memberaddid);
				memberaccount.setCurrBalance((double) 0);
				memberaccount.setCurrIntegration((long) 0);
				memberaccount.setTotalRecharge((double) 0);
				memberaccount.setDiscountGift((double) 0);
				memberaccount.setChargeGift((double) 0);
				memberaccount.setTotalConsume((double) 0);
				memberaccount.setTotalIntegration((long) 0);
				memberaccount.setIngegrationGift((long) 0);
				memberaccount.setTotalConsIntegration((long) 0);
				memberaccount.setTotalRoomnights(0);
				memberaccount.setCurrRoomnights(0);
				memberaccount.setTotalNoshow((short) 0);
				memberaccount.setCurrNoshow((short) 0);
				memberaccount.setThisYearIntegration((long) 0);
				memberaccount.setRecordTime(new Date());
				memberaccount.setStatus("1");
				leftmenuService.save(memberaccount);
				if (stype.equals(CommonConstants.SystemType.Branch)) {
					int priority = 1;
					SysParam param = (SysParam) leftmenuService.findOneByProperties(SysParam.class, "paramType",
							"REMOTE_PATH", "status", "1");
					List<MemberAccount> memberaccountlList = new ArrayList<MemberAccount>();
					memberaccountlList.add(memberaccount);
					Map<String, Object> memberaccountMap = new HashMap<String, Object>();
					memberaccountMap.put("MemberAccount", memberaccountlList);
					// TransferServcie.getInstance().transferData(priority, param.getContent(),JSONUtil.fromObject(memberaccountMap).toString());
				}
				Member membernew = ((Member) (leftmenuService.findOneByProperties(Member.class, "memberName",
						orderuser, "mobile", ordermobile)));
				memberid = membernew.getMemberId().toString();
			} else {
				JSONArray mrr = new JSONArray(mr);
				DateFormat dff = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				if (mrr.length() > 0) {
					for (int i = 0; i < mrr.length(); i++) {
						JSONObject mrrr = mrr.getJSONObject(i);
						Member memberdown = new Member();
						memberdown.setMemberId(mrrr.getString("MEMBER_ID"));
						memberdown.setMemberName(mrrr.getString("MEMBER_NAME"));
						memberdown.setLoginName(mrrr.getString("LOGIN_NAME"));
						memberdown.setPassword(mrrr.getString("PASSWORD"));
						memberdown.setMemberRank(mrrr.getString("MEMBER_RANK"));
						memberdown.setIdcard(mrrr.getString("IDCARD"));
						memberdown.setGendor(mrrr.getString("GENDOR"));
						Date birth;
						if (mrrr.getString("BIRTHDAY") == "null") {
							birth = null;
						} else {
							birth = dff.parse(mrrr.getString("BIRTHDAY"));
						}
						memberdown.setBirthday(birth);
						memberdown.setMobile(mrrr.getString("MOBILE"));
						memberdown.setEmail(mrrr.getString("EMAIL"));
						memberdown.setAddress(mrrr.getString("ADDRESS"));
						memberdown.setPostcode(mrrr.getString("POSTCODE"));
						memberdown.setPhotos(mrrr.getString("PHOTOS"));
						memberdown.setSource(mrrr.getString("SOURCE"));
						Date vtime = dff.parse(mrrr.getString("VALID_TIME"));
						memberdown.setValidTime(vtime);
						memberdown.setInvalidTime(dff.parse(mrrr.getString("INVALID_TIME")));
						memberdown.setRegisterTime(dff.parse(mrrr.getString("REGISTER_TIME")));
						Date recordt;
						if (mrrr.getString("RECORD_TIME") == "null") {
							recordt = null;
						} else {
							recordt = dff.parse(mrrr.getString("RECORD_TIME"));
						}
						memberdown.setRecordTime(recordt);
						memberdown.setStatus(mrrr.getString("STATUS"));
						memberdown.setRemark(mrrr.getString("REMARK"));
						memberdown.setOpenId(mrrr.getString("OPENID"));
						memberdown.setRecommend(mrrr.getString("RECOMMEND"));
						leftmenuService.save(memberdown);
						memberid = mrrr.getString("MEMBER_ID");

					}

				}

			}

		}else{
			memberid = member.getMemberId().toString();
		}
		String branchid = loginuser.getStaff().getBranchId();
		Integer len = Integer.valueOf(orderacount);
		DateFormat dff = new SimpleDateFormat("yyyy/MM/dd");
		DateFormat dfff = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date s = dfff.parse((URLDecoder.decode((orderarrivedate + " 12:00:00"), "UTF-8")));
		Date g = dfff.parse((URLDecoder.decode((orderleavedate + " 12:00:00"), "UTF-8")));
		Double price = Double.parseDouble(orderprice);
		Double cash;
		Double card;
		Integer score;
		String paymentType = "";
		if (ordercash != null && !ordercash.equals("")) {
			cash = Double.parseDouble(ordercash);
			paymentType = "0,";
		} else {
			cash = null;
		}

		if (ordercard != null && !ordercard.equals("")) {
			card = Double.parseDouble(ordercard);
			paymentType = paymentType + "1,";
		} else {
			card = null;
		}

		if (orderscore != null && !orderscore.equals("")) {
			score = Integer.valueOf(orderscore);
			paymentType = paymentType + "2,";
		} else {
			score = null;
		}
		/*
		 * SysParam sysParamscore = ((SysParam)
		 * (LeftmenuService.findOneByProperties(SysParam.class, "paramType",
		 * "ORDERSWITCH")));
		 */
		/* String orderswitch = sysParamscore.getContent().toString(); */
		List<?> roomBed = leftmenuService.getRoombeds(branchid, orderroomtype);
		String roombed = ((Map<?, ?>) roomBed.get(0)).get("ROOMBED").toString();
		/*
		 * List<?> roomIds = LeftmenuService.getRoomids(orderswitch,
		 * orderroomtype, orderarrivedate, orderleavedate);
		 */
		String a = null;
		/*
		 * if(roomIds.size()==0){ a = "暂时没有合适的房间可供预订，请重新选择房型！"; }else{
		 */
		/*
		 * ArrayList<String> roomIdsarray = new ArrayList<String>(); for (int i
		 * = 0; i < roomIds.size(); i++) { String roomtypename = ((Map<?, ?>)
		 * roomIds.get(i)).get("room_id").toString();
		 * roomIdsarray.add(roomtypename); }
		 */
		
		double orderCardNew = 0.00;
		double orderCashNew = 0.00;
		double anotherdays = 0.00;
		double paytotal = 0.00;
		String flag = "";
		if (!("").equals(ordercard) && ordercard != null) {
			flag = "1";
			orderCardNew = Double.valueOf(ordercard);
		} 
		if (!("").equals(ordercash) && ordercash != null) {
			flag = "2";
			orderCashNew = Double.valueOf(ordercash);
		}
		paytotal = orderCardNew + orderCashNew;
		JSONArray checkuserarrayjson = new JSONArray(checkuserarray);
		int usersnum = checkuserarrayjson.length();
		for (int i = 0; i < len; i++) {
			//List<?> ordermsj = leftmenuService.getOrdermsj(branchid, ordertheme, orderroomtype);
			//List<List<?>> ordermsj = RoomUtil.getNowRoomPrice(branchid, CommonConstants.DefaultRoomPrice.DEFAULT_RP_ID, orderroomtype);
			//String ordermsjrp = ((Map<?, ?>) ordermsj.get(0).get(0)).get("ROOMPRICE").toString();
			SinglePrice singleP = new SinglePrice(branchid, ordertheme, orderroomtype, "MSJ", RealPrice.PriceType.DATE, s);
			Double pricemsj = singleP.checkRoomPrice();
			//Double pricemsj = Double.parseDouble(ordermsjrp);
			String logId = this.leftmenuService.getSequence("SEQ_NEW_ORDER", null);
			Date day = new Date();
			Date s2 = dff.parse(dff.format(day));
			SimpleDateFormat df = new SimpleDateFormat("yyMMdd");
			String t = df.format(day);
			String source = CommonConstants.OrderSource.Branch;
			String datId = t + branchid + source + logId;
			/* String rd = roomIdsarray.get(i); */

			// 预订刷房价
/*			Date startDate = dff.parse(orderarrivedate);
			Date endDate = dff.parse(orderleavedate);
			Long spi = endDate.getTime() - startDate.getTime();
		    Long step = spi / (24 * 60 * 60 * 1000);// 相隔天数
		    Double totalprice = 0.00;
		    String applyId = null;
		    String everydaystart = null;
		    List<OrdchePrice> ocpList = new ArrayList<OrdchePrice>();
		    try{
		    for (int d = 1; d <= step; d++) {
		    	Double dayprice = 0.00;
		    	if(d==1 && s2.compareTo(startDate) == 0){
		            dayprice = price;
		            everydaystart =  dff.format(startDate).toString();
		        }else{
		        	everydaystart =  dfff.format(new Date(s.getTime() + ((24 * 60 * 60 * 1000)*(d-1))));
		        	String everydayend = dfff.format(new Date(s.getTime() + ((24 * 60 * 60 * 1000)*(d))));
		 	        Calendar calendar = Calendar.getInstance();//获得一个日历
//		 	        int year =  new Date(startDate.getTime() - (24 * 60 * 60 * 1000)).getYear();
//		 	        int month = new Date(startDate.getTime() - (24 * 60 * 60 * 1000)).getMonth();
//		 	        int day2 = new Date(startDate.getTime() - (24 * 60 * 60 * 1000)).getDay();
		 	        calendar.setTime(startDate);
		 			String number = String.valueOf(calendar.get(Calendar.DAY_OF_WEEK)-1);
		        	//List<?> basicdayprice = LeftmenuService.getBasicdayprice(branchid,everydaystart,everydayend,number);//基准
		        	//List<?> adjustdayprice = LeftmenuService.getAdjustdayprice(branchid,everydaystart,everydayend,number);//调整
		 			List<Map<String,String>> maxAPPLYID = leftmenuService.findBySQL("findMaxId", new String[] { branchid, everydaystart,everydayend,number,everydayend,everydaystart},true);
		        	List<Map<String,String>> basicdayprice = leftmenuService.findBySQL("findbasicdayprice", new String[]{branchid}, true);//基准

						if (maxAPPLYID.size() > 0) {
							applyId = ((Map<String, String>) maxAPPLYID.get(0)).get("APPLYID") == null ? ""
									: ((Map<String, String>) maxAPPLYID.get(0)).get("APPLYID").toString();

						} else {
							if (basicdayprice.size() > 0) {
								applyId = ((Map<String, String>) basicdayprice.get(0)).get("APPLY_ID") == null ? ""
										: ((Map<String, String>) basicdayprice.get(0)).get("APPLY_ID").toString();

							}
						}
						if (applyId != null) {
							List<?> daypriceList = leftmenuService.findBySQL("findorderprice",
									new String[] { applyId, orderrpid, orderroomtype }, true);
							dayprice = Double.valueOf(((Map<?, ?>) daypriceList.get(0)).get("ROOM_PRICE")
									.toString());
		        	}else{
		        		List<RoomPrice> roomPricebean = leftmenuService.findByProperties(RoomPrice.class, "roomPriceId.branchId",branchid, 
		        				"roomPriceId.rpId",orderrpid, "roomPriceId.roomType",orderroomtype,
		        				"roomPriceId.rpKind",CommonConstants.DefaultRoomPrice.DEFAULT_RPKIND_COMMON,
		        				"state",CommonConstants.DefaultRoomPrice.DEFAULT_RP_STATE);
		        		for (i=0;i<roomPricebean.size();i++) {
		        			if (roomPricebean.get(i).getRoomPriceId().getStatus().equals(CommonConstants.RoomPriceStatus.adjust)
		        					&& roomPricebean.get(i).getRoomPrice() != null) {
		        				dayprice = roomPricebean.get(i).getRoomPrice();
		        				break;
		        			} else {
		        				dayprice = roomPricebean.get(i).getRoomPrice();
		        			}
		        		}
		        		if(null != RoomPricebean){
		        			dayprice = RoomPricebean.getRoomPrice();
		        		}
		        	}
		        }
		    	
		        totalprice += dayprice;
		        OrdchePrice ocp = new OrdchePrice();
		        String ocpsequence = this.leftmenuService.getSequence("SEQ_PRICE_DATAID", null);
				String ocp_dataId = t + branchid + source + ocpsequence;
		        ocp.setDataId(ocp_dataId);
		        ocp.setOrderId(datId);
		        ocp.setWhichDay(dff.parse(everydaystart));
		        ocp.setDayPrice(dayprice);
		        ocp.setStatus("1");
		        ocp.setRecordUser(staff.getStaffId());
		        ocp.setBranchId(branchid);
		        ocp.setRecordTime(new Date());
		        ocpList.add(ocp);
		        
		    }
		    
		    leftmenuService.saveOrUpdateAll(ocpList);
			} catch (Exception e) {
				e.printStackTrace();
				JSONUtil.responseJSON(response, new AjaxResult(1, "预定失败！"));
			}*/
			
			Order order = new Order();
			order.setOrderId(datId);
			order.setBranchId(branchid);
			order.setSource(source);
			order.setTheme(ordertheme);
			order.setSaleStaff(staff.getStaffId());
			/* order.setRoomId(rd); */
			order.setRoomType(orderroomtype);
			order.setOrderUser(memberid);
			order.setmPhone(ordermobile);
			order.setRpId(orderrpid);
			order.setArrivalTime(s);
			order.setLeaveTime(g);
			order.setRoomPrice(0.00);
			order.setGuarantee(ordergurantee);
			order.setLimited(orderlimited);
			order.setReceptionRemark(receptionremark);
			order.setRoomRemark(roomremark);
/*			order.setAdvanceCash(cash);
			order.setAdvanceCard(card);*/
			order.setVoucher(ordervoucher);
			order.setAdvanceScore(score);
			order.setStatus("1");
			order.setMsj(pricemsj);
			order.setRecordTime(new Date());
			order.setRecordUser(staff.getStaffId());
			// 支付类型的set值
			if (paymentType.length() > 0) {
				order.setPaymentType(paymentType.substring(0, paymentType.length() - 1));
			} else {
				order.setPaymentType(paymentType);
			}
			
			
			//预定时记日志表
			OperateLog 	orderLog = new OperateLog();
			orderLog.setBranchId(loginuser.getStaff().getBranchId());
			orderLog.setLogId(DateUtil.currentDate("yyMMdd") + branchid + roomService.getSequence("SEQ_OPERATELOG_ID"));
			orderLog.setContent("新的订单:" + order.getOrderId() + "生成成功!");
			String operid = InetAddress.getLocalHost().toString();// IP地址
			operid = (String) operid.subSequence(operid.indexOf("/") + 1, operid.length());
			orderLog.setOperIp(operid);
			orderLog.setOperModule("生成新订单");
			orderLog.setOperType("order");
			orderLog.setRecordTime(new Date());
			orderLog.setRecordUser(loginuser.getStaff().getStaffId());
			leftmenuService.save(orderLog);
			
			//if (order.getAdvanceCard() != null || order.getAdvanceCash() != null) {
			//预定时预付款记入账单表
			Bill bill = new Bill();
/*			if (order.getAdvanceCash() == null) {
				order.setAdvanceCash(0.00);
			}
			if (order.getAdvanceCard() == null) {
				order.setAdvanceCard(0.00);
			}*/
			double everypay = Math.floor(paytotal / Double.valueOf(orderacount));
			String billId = DateUtil.currentDate("yyMMdd") + branchid + source
					+ roomService.getSequence("SEQ_NEW_BILL");
			bill.setBillId(billId);
			bill.setBranchId(branchid);
			bill.setCheckId(order.getOrderId());
			bill.setCost(0.00);
			bill.setDescribe("预存");
			bill.setShiftVoucher(ordervoucher);
			bill.setProjectId(CommonConstants.BillProject.Deposit);
			bill.setProjectName("预存");
			if (i == Integer.parseInt(orderacount)-1) {
				bill.setPay(paytotal-anotherdays);
				
			} else {
				bill.setPay(everypay);
			}
			anotherdays += everypay;
			if (("").equals(order.getPaymentType())) {
				bill.setPayment(BillPayment.NonPayment);
			} else {
				bill.setPayment(order.getPaymentType());
			}
			bill.setStatus(CommonConstants.BillStatus.BillNormal);
			bill.setRecordTime(new Date());
			bill.setRecordUser(staff.getStaffId());
			bill.setType("1");
			if ("1".equals(flag)) {
				order.setAdvanceCard(bill.getPay());
			} else {
				order.setAdvanceCash(bill.getPay());
			}
			leftmenuService.save(order);
			
			try {
				saveEveryDayPrice(request, response, datId, orderroomtype);
			} catch (Exception e) {
				e.printStackTrace();
				JSONUtil.responseJSON(response, new AjaxResult(1, "预定失败！"));
			}
			Map<String, Map<String, Object>> priceList = queryRoomPrice(request, response, datId, orderroomtype);
			order.setRoomPrice(Double.valueOf(priceList.get("totalPrices").get("totalPrices").toString()));
			leftmenuService.update(order);
			
			leftmenuService.save(bill);		

			RoomStatus roomstatus = (RoomStatus) leftmenuService.findOneByProperties(RoomStatus.class, "branchId", branchid, "roomType", order.getRoomType());
			roomstatus.setValidnum(roomstatus.getValidnum() + 1);
			roomstatus.setInvalidnum(roomstatus.getInvalidnum() + 1);
			leftmenuService.update(roomstatus);
			
			//预定时预付款记操作日志表
			OperateLog 	orderBillLog = new OperateLog();
			orderBillLog.setBranchId(loginuser.getStaff().getBranchId());
			orderBillLog.setLogId(DateUtil.currentDate("yyMMdd") + branchid + roomService.getSequence("SEQ_OPERATELOG_ID"));
			orderBillLog.setContent("订单号：" + order.getOrderId() + "预付了" + bill.getPay());
			orderBillLog.setOperIp(operid);
			orderBillLog.setOperModule("订单预付款");
			orderBillLog.setOperType("order");
			orderBillLog.setRecordTime(new Date());
			orderBillLog.setRecordUser(loginuser.getStaff().getStaffId());
			leftmenuService.save(orderBillLog);	

			// 入住人
			if (usersnum <= len) {
				if (usersnum > i) {
					for (int k = 0; k < 1; k++) {
						JSONObject checkuserjson = checkuserarrayjson.getJSONObject(i);
						String checktype = null;
						if (k == 0) {
							checktype = CommonConstants.CheckUserType.HOST;
						} else {
							checktype = CommonConstants.CheckUserType.GUEST;
						}
						CheckUser checkuser = new CheckUser();
						String checklogId = this.leftmenuService.getSequence("SEQ_NEW_CHECKUSER", null);
						String checkuserId = branchid + source + checklogId;
						checkuser.setCheckuserId(checkuserId);
						checkuser.setCheckuserName(checkuserjson.getString("name"));
						checkuser.setIdcard(checkuserjson.getString("idcard"));
						checkuser.setGender(checkuserjson.getString("gender"));
						checkuser.setMobile(checkuserjson.getString("mobile"));
						checkuser.setStatus(CommonConstants.CheckUserStatus.ON);
						checkuser.setCheckinType(CommonConstants.CheckinType.ORDER);
						checkuser.setRecordUser(staff.getStaffId());
						checkuser.setRecordTime(new Date());
						checkuser.setCheckId(datId);
						checkuser.setCheckuserType(checktype);
						leftmenuService.save(checkuser);
						usersnum = usersnum--;

					}
				}

			} else if (usersnum > len) {
				double roombeds = Double.parseDouble(roombed);
				double haveusers = usersnum - (roombeds * i);
				if ((int) (haveusers) > 0) {
					if (len == (i + 1)) {
						roombeds = haveusers;
					}
					int j = 0;
					if (i == 0) {
						j = i;
					} else if (i > 0) {
						j = (int) ((Double.parseDouble(roombed)) * i);

					}
					for (int k = 0; k < roombeds; k++) {
						if (j < usersnum) {
							JSONObject checkuserjson = checkuserarrayjson.getJSONObject(j);
							String checktype = null;
							if (k == 0) {
								checktype = "1";

							} else {
								checktype = "2";

							}
							CheckUser checkuser = new CheckUser();
							String checklogId = this.leftmenuService.getSequence("SEQ_NEW_CHECKUSER", null);
							String checkuserId = branchid + source + checklogId;
							checkuser.setCheckuserId(checkuserId);
							checkuser.setCheckuserName(checkuserjson.getString("name"));
							checkuser.setIdcard(checkuserjson.getString("idcard"));
							checkuser.setGender(checkuserjson.getString("gender"));
							checkuser.setMobile(checkuserjson.getString("mobile"));
							checkuser.setStatus(CommonConstants.CheckUserStatus.ON);
							checkuser.setCheckinType(CommonConstants.CheckinType.ORDER);
							checkuser.setRecordUser(staff.getStaffId());
							checkuser.setRecordTime(new Date());
							checkuser.setCheckId(datId);
							checkuser.setCheckuserType(checktype);
							leftmenuService.save(checkuser);
							j = j + 1;
						}

					}

				}

			}

			// 预订更新房态
			/*
			 * RoomStatus roomStatus = ((RoomStatus)
			 * (LeftmenuService.findOneByProperties(RoomStatus.class,
			 * "branchId", branchid, "roomType", orderroomtype))); String
			 * oldsellnum = roomStatus.getSellnum().toString(); int v =
			 * Integer.parseInt(oldsellnum) - 1; Integer newsellnum =
			 * Integer.valueOf(v); roomStatus.setSellnum(newsellnum);
			 * roomStatus.setRecordTime(new Date());
			 * roomStatus.setRecordUser(staff.getStaffId());
			 * LeftmenuService.save(roomStatus);
			 */

		}
//		if (HeartBeatClient.heartbeat) {
		if (ordermobile.matches(RegExUtil.MOBILE)) {
			List<?> orderroomname = leftmenuService.getOrderroomname(ordertheme, branchid, orderroomtype);
			String roomtypename = ((Map<?, ?>) orderroomname.get(0)).get("ROOMNAME").toString(); 
			Branch branchmessage = ((Branch) (leftmenuService.findOneByProperties(Branch.class,"branchId", branchid, "status", "1"))); 
			String branchname = branchmessage.getBranchName().toString();
			String branchaddress = branchmessage.getAddress().toString();
			String branchphone = branchmessage.getPhone().toString();
			SmsSingleSender singleSender = new SmsSingleSender(SystemConstants.note.APPID, SystemConstants.note.APPKEY);
			ArrayList<String> params = new ArrayList<String>();
			params.add(orderuser);
			params.add(branchname);
			params.add(orderarrivedate);
			params.add(orderleavedate);
			params.add(roomtypename);
			params.add(orderacount);
			params.add(branchaddress);
			params.add(branchphone);
			singleSender.sendWithParam(SystemConstants.note.COUNTRY, ordermobile, 56428, params, "", "", "");
		  }
//		}
		/* } */
		JSONUtil.responseJSON(response, new AjaxResult(1, a));
	}

	// List<?> rooms = LeftmenuService.getRooms();
	/*
	 * int rooms [] = new int[]{201,202,203}; List rs= Arrays.asList(users); int
	 * len = rs.size(); Map<String, List> a = new HashMap<String, List>(); List
	 * list= new ArrayList(); list.add(s); list.add(orderacount); a.put("list",
	 * list); Map<String, Integer> results = new HashMap<String, Integer>(); int
	 * n = len;
	 */
	/*
	 * for (String key : a.keySet()) { int fnum = Integer.valueOf(a.get(key));
	 * for (int i = 0; i < fnum; i++) { results.put(key + "_" + i, 1); n--; } }
	 * 
	 * 
	 * int i; int b = Integer.valueOf(orderacount).intValue(); for(i=0;i<b;i++){
	 * 
	 * 
	 * }
	 */

	@RequestMapping("/hotelosSearch.do")
	public void osSearch(HttpServletRequest request, HttpServletResponse response, String orderid, String orderuser,
			String usercheckin, String mobile, String memberid) throws Exception {
		JSONUtil.responseJSON(response, new AjaxResult(1, null));
	}

	/*@RequestMapping("/goodsSale.do")
	public ModelAndView goodsSale(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		Pagination pagination = SqlBuilder.buildPagination(request);
		// @SuppressWarnings("unchecked")
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginuser.getStaff().getBranchId();
		if (branchId.equals(SystemConstants.User.BRANCH_ID)) {
			branchId = "%";
		}
		List<?> goodsSale = LeftmenuService.getGoodssale(pagination, branchId);
		mv.setViewName("page/ipmspage/leftmenu/goodssale/goodssale");
		mv.addObject("goodsSale", goodsSale);
		mv.addObject("pagination", pagination);
		return mv;
	}*/

	@RequestMapping("/hotelgdsaleCondition.do")
	public ModelAndView gdsaleCondition(HttpServletRequest request, HttpServletResponse response, String goodsid,
			String goodsname, String categoryid,String gdscontent) throws Exception {
		ModelAndView mv = new ModelAndView();
		Pagination pagination = SqlBuilder.buildPagination(request);
		pagination.setRows(13);
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginuser.getStaff().getBranchId();
		/*if (branchId.equals(SystemConstants.User.BRANCH_ID)) {
			branchId = "%";
		}*/
		List<?> gdsaleCondition = leftmenuService.getGdsalecondition(goodsid, goodsname, categoryid, branchId,
				pagination);
		mv.setViewName("page/ipmshotel/leftmenu/goodssale/gdsalecondition");
		mv.addObject("gdsaleCondition", gdsaleCondition);
		mv.addObject("pagination", pagination);
		mv.addObject("gdscontent", gdscontent);
		mv.addObject("goodsid", goodsid);
		mv.addObject("goodsname", goodsname);
		mv.addObject("categoryid", categoryid);
		return mv;
	}

	//单品售卖
	@RequestMapping("/hotelgdsPage.do")
	public ModelAndView gdsPage(HttpServletRequest request, String gdsid, String gdsname, String gdsprice)
			throws Exception {
		ModelAndView mv = new ModelAndView();
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String loginstaff = loginuser.getStaff().getStaffId().toString();
		Branch branchData = (Branch) leftmenuService.findOneByProperties(Branch.class, "branchId", loginuser.getStaff().getBranchId());
		List<?> gdsproject = leftmenuService.getGdsproject();
		List<?> gdsprojectpay = leftmenuService.getGdsprojectpay();
		String branchid = loginuser.getStaff().getBranchId();
		List<?> workbill = leftmenuService.getWorkbill(branchid, loginstaff);
		//List<?> house = leftmenuService.findByProperties(House.class, "staffId", loginuser.getStaff().getStaffId());
		List<?> house = leftmenuService.findBySQL("fHouseWithStaffid",new String[]{loginuser.getStaff().getStaffId(),loginuser.getStaff().getStaffId()}, true);

		mv.setViewName("page/ipmshotel/leftmenu/goodssale/gdspage");
		String systemtheme = "hotel";
		if (branchData.getBranchType().equals(CommonConstants.Branch.APARTMENTID)) {
			systemtheme = "depart";
		}
		if (house.size() > 0) {
			systemtheme = "house";
		}
		request.setAttribute("systemtheme", systemtheme);
		request.setAttribute("gdsid", gdsid);
		request.setAttribute("gdsname", gdsname);
		request.setAttribute("gdsprice", gdsprice);
		request.setAttribute("gdsproject", gdsproject);
		request.setAttribute("gdsprojectpay", gdsprojectpay);
		request.setAttribute("workbill", workbill);
		return mv;
	}

	//批量售卖
	@RequestMapping("/hotelgdsmulPage.do")
	public ModelAndView gdsPage(HttpServletRequest request, String saletype, String newstr) throws Exception {
		ModelAndView mv = new ModelAndView();
		List<?> gdsproject = leftmenuService.getGdsproject();
		List<?> gdsprojectpay = leftmenuService.getGdsprojectpay();
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchid = loginuser.getStaff().getBranchId();
		Branch branchData = (Branch) leftmenuService.findOneByProperties(Branch.class, "branchId", branchid);
		String loginstaff = loginuser.getStaff().getStaffId().toString();
		List<?> workbill = leftmenuService.getWorkbill(branchid, loginstaff);
		mv.setViewName("page/ipmshotel/leftmenu/goodssale/gdsmulpage");
		List<Goods> listgoods = new ArrayList<Goods>();
		JSONArray newstrr = new JSONArray(newstr);
		for (int i = 0; i < newstrr.length(); i++) {
			String s = newstrr.getString(i);
			Goods goods = (Goods) leftmenuService.findOneByProperties(Goods.class, "goodsId", s);
			listgoods.add(goods);
		}
		//List<?> house = leftmenuService.findByProperties(House.class, "staffId", loginuser.getStaff().getStaffId());
		List<?> house = leftmenuService.findBySQL("fHouseWithStaffid",new String[]{loginuser.getStaff().getStaffId(),loginuser.getStaff().getStaffId()}, true);

		String systemtheme = "hotel";
		if (branchData.getBranchType().equals(CommonConstants.Branch.APARTMENTID)) {
			systemtheme = "depart";
		}
		if (house.size() > 0) {
			systemtheme = "house";
		}
		request.setAttribute("systemtheme", systemtheme);
		request.setAttribute("listgoods", listgoods);
		request.setAttribute("saletype", saletype);
		request.setAttribute("gdsproject", gdsproject);
		request.setAttribute("gdsprojectpay", gdsprojectpay);
		request.setAttribute("workbill", workbill);
		// System.out.println();
		return mv;
	}

	// 判断挂房账时是否有房号，如果没有直接弹出提示框，如果有正常进入！
	@RequestMapping("/hotelselectgdsRoom.do")
	public  void selectgdsRoom(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginuser.getStaff().getBranchId();
		List<?> gdsroomId;
		Branch branchInfo = (Branch) leftmenuService.findOneByProperties(Branch.class, "branchId", branchId);
		if (branchInfo.getRank().equals(CommonConstants.SystemLevel.MarketCenter)) {
			gdsroomId = leftmenuService.findBySQL("querylivehouse", new String[] { loginuser.getStaff().getStaffId()}, true);
		} else if (branchInfo.getBranchType().equals(CommonConstants.Branch.APARTMENTID)) {
			gdsroomId = leftmenuService.findBySQL("gdsroomIdapart", new String[] { branchId,branchId, branchId,branchId}, true);
		} else {
			gdsroomId = leftmenuService.getGdsroomid(branchId,CommonConstants.SystemTheme.HOTEL);
		}
		String a = "没有在住房间!";
		if (gdsroomId.size() <= 0) {
			JSONUtil.responseJSON(response, new AjaxResult(-1, a));
		}else{
			a = "有在住房间！";
			JSONUtil.responseJSON(response, new AjaxResult(1,a));
		}
	}
	
	@RequestMapping("/hotelgdsroomSelect.do")
	public ModelAndView gdsroomSelect(HttpServletRequest request, HttpServletResponse response, String totalprice) throws Exception {
		ModelAndView mv = new ModelAndView();
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginuser.getStaff().getBranchId();
		// if (branchId.equals(SystemConstants.User.BRANCH_ID)) {
		// branchId = "%";
		// }
		List<?> gdsroomId;
		Branch branchInfo = (Branch) leftmenuService.findOneByProperties(Branch.class, "branchId", branchId);
		if (branchInfo.getRank().equals(CommonConstants.SystemLevel.MarketCenter)) {//民宿
			gdsroomId = leftmenuService.findBySQL("querylivehouse", new String[] { loginuser.getStaff().getStaffId()}, true);
			mv.addObject("theme", CommonConstants.Branch.HOUSEID);
		} else if (branchInfo.getBranchType().equals(CommonConstants.Branch.APARTMENTID)) {
			gdsroomId = leftmenuService.findBySQL("gdsroomIdapart", new String[] { branchId,branchId, branchId,branchId}, true);
			mv.addObject("theme", CommonConstants.Branch.APARTMENTID);

		} else {//酒店
			gdsroomId = leftmenuService.getGdsroomid(branchId,CommonConstants.SystemTheme.HOTEL);
			mv.addObject("theme", CommonConstants.Branch.HOTELID);

		}
		mv.setViewName("page/ipmshotel/leftmenu/goodssale/gdsroomselect");
		mv.addObject("allCost", totalprice);
		mv.addObject("gdsroomId", gdsroomId);
		return mv;
	}

	@RequestMapping("/hotelmemberSearch.do")
	@RightMethodControl(rightType = RightConstants.RightType.HOT_MEMSEA)
	public ModelAndView memberSearch(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/ipmshotel/leftmenu/membersearch/membersearch");
		return mv;
	}

	@RequestMapping("/hotelmemberSearchdata.do")
	public ModelAndView memberSearchdata(HttpServletRequest request, String data) throws Exception {
		ModelAndView mv = new ModelAndView();
		DateFormat dff = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		if (HeartBeatClient.heartbeat) {
		/*boolean isString = mphone instanceof String;
		boolean isStringc = mcard instanceof String;
		String memberms = null;
		if ((isString)) {
			memberms = mphone;
		} 
		if (isStringc) {
			memberms = mcard;
		}*/
		boolean isStringc = data instanceof String;
		if((isStringc==true)){
		com.ideassoft.portal.DataDealPortalService service = new com.ideassoft.portal.DataDealPortalService();
		com.ideassoft.portal.IDataDealPortal portal = service.getDataDealPortalPort();
		String mr = portal.call(1, 1, "{ function: \"checkInDirect.getMemberBean\", data:{idcard:" + data + " } }");
		if (mr.equals("-1")) {
		} else {
			JSONArray mrr = new JSONArray(mr);
			// DateFormat dff = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			if (mrr.length() > 0) {
				for (int i = 0; i < mrr.length(); i++) {
					JSONObject mrrr = mrr.getJSONObject(i);
					String memberid = mrrr.getString("MEMBER_ID") == "null" ? "" : mrrr.getString("MEMBER_ID");
					String membername = mrrr.getString("MEMBER_NAME") == "null" ? "" : mrrr.getString("MEMBER_NAME");
					String loginname = mrrr.getString("LOGIN_NAME") == "null" ? "" : mrrr.getString("LOGIN_NAME");
					String password = mrrr.getString("PASSWORD") == "null" ? "" : mrrr.getString("PASSWORD");
					String rankid = mrrr.getString("MEMBER_RANK") == "null" ? "" : mrrr.getString("MEMBER_RANK");
					MemberRank memberrank = (MemberRank) leftmenuService.findOneByProperties(MemberRank.class,
							"memberRank", rankid);
					String rankname = memberrank.getRankName().toString();
					String gendor = mrrr.getString("GENDOR") == "null" ? "" : mrrr.getString("GENDOR");
					String mobile = mrrr.getString("MOBILE") == "null" ? "" : mrrr.getString("MOBILE");
					String birthday = mrrr.getString("BIRTHDAY") == "null" ? "" : mrrr.getString("BIRTHDAY");
					String idcard = mrrr.getString("IDCARD") == "null" ? "" : mrrr.getString("IDCARD");
					String email = mrrr.getString("EMAIL") == "null" ? "" : mrrr.getString("EMAIL");
					String postcode = mrrr.getString("POSTCODE") == "null" ? "" : mrrr.getString("POSTCODE");
					String photos = mrrr.getString("PHOTOS") == "null" ? "" : mrrr.getString("PHOTOS");
					String source = mrrr.getString("SOURCE") == "null" ? "" : mrrr.getString("SOURCE");
					/*SysParam sourcedate = ((SysParam) (leftmenuService.findOneByProperties(SysParam.class, "paramType",
							"MEMBERSOURCE", "content", source, "status", "1")));*/
					DataSource datasource = (DataSource) leftmenuService.findOneByProperties(DataSource.class, "memberId", source, "status", "1");
					String sourcename = datasource.getName().toString();
					String validtime = mrrr.getString("VALID_TIME") == "null" ? "" : mrrr.getString("VALID_TIME");
					String invalidtime = mrrr.getString("INVALID_TIME") == "null" ? "" : mrrr.getString("INVALID_TIME");
					String registertime = mrrr.getString("REGISTER_TIME") == "null" ? "" : mrrr
							.getString("REGISTER_TIME");
					String recordtime = mrrr.getString("REGISTER_TIME");
					String address = mrrr.getString("ADDRESS") == "null" ? "" : mrrr.getString("ADDRESS");
					String status = mrrr.getString("STATUS") == "null" ? "" : mrrr.getString("STATUS");
					String remark = mrrr.getString("REMARK") == "null" ? "" : mrrr.getString("REMARK");
					String recommend = mrrr.getString("RECOMMEND") == "null" ? "" : mrrr.getString("RECOMMEND");
					String openid = mrrr.getString("OPENID") == "null" ? "" : mrrr.getString("OPENID");
					Member memberjudge = ((Member) (leftmenuService.findOneByProperties(Member.class, "memberId",
							memberid, "mobile", mobile)));
					Date birth;
					if (mrrr.getString("BIRTHDAY") == "null") {
						birth = null;
					} else {
						birth = dff.parse(mrrr.getString("BIRTHDAY"));
					}
					Date recordt;
					if (recordtime == "null") {
						recordt = null;
					} else {
						recordt = dff.parse(recordtime);
					}
					if (memberjudge == null) {
						Member member = new Member();
						member.setMemberId(memberid);
						member.setMemberName(membername);
						member.setLoginName(loginname);
						member.setPassword(password);
						member.setMemberRank(rankid);
						member.setIdcard(idcard);
						member.setGendor(gendor);
						member.setBirthday(birth);
						member.setMobile(mobile);
						member.setEmail(email);
						member.setAddress(address);
						member.setPostcode(postcode);
						member.setPhotos(photos);
						member.setSource(source);
						member.setValidTime(dff.parse(validtime));
						member.setInvalidTime(dff.parse(invalidtime));
						member.setRegisterTime(dff.parse(registertime));
						member.setRecordTime(recordt);
						member.setStatus(status);
						member.setRemark(remark);
						member.setOpenId(openid);
						member.setRecommend(recommend);
						leftmenuService.save(member);
					} else {
						memberjudge.setMemberName(membername);
						memberjudge.setLoginName(loginname);
						memberjudge.setPassword(password);
						memberjudge.setMemberRank(rankid);
						memberjudge.setIdcard(idcard);
						memberjudge.setGendor(gendor);
						memberjudge.setBirthday(birth);
						memberjudge.setEmail(email);
						memberjudge.setAddress(address);
						memberjudge.setPostcode(postcode);
						memberjudge.setPhotos(photos);
						memberjudge.setSource(source);
						memberjudge.setValidTime(dff.parse(validtime));
						memberjudge.setInvalidTime(dff.parse(invalidtime));
						memberjudge.setRegisterTime(dff.parse(registertime));
						memberjudge.setRecordTime(recordt);
						memberjudge.setStatus(status);
						memberjudge.setRemark(remark);
						memberjudge.setOpenId(openid);
						memberjudge.setRecommend(recommend);
						leftmenuService.update(memberjudge);
					}
					SimpleDateFormat sdf  =   new  SimpleDateFormat( " yyyy/MM/dd " );
					String corrbr = "";
					if(!StringUtil.isEmpty(birthday))
						corrbr = sdf.format( dff.parse(birthday));
				    String corrvt = sdf.format( dff.parse(validtime)); 
				    String corrivt = sdf.format( dff.parse(invalidtime));
					request.setAttribute("membername", membername);
					request.setAttribute("memberid", memberid);
					request.setAttribute("rankname", rankname);
					request.setAttribute("gendor", gendor);
					request.setAttribute("mobile", mobile);
					request.setAttribute("birthday", corrbr);
					request.setAttribute("idcard", idcard);
					request.setAttribute("email", email);
					request.setAttribute("source", sourcename);
					request.setAttribute("validtime", corrvt);
					request.setAttribute("invalidtime", corrivt);
					request.setAttribute("address", address);
					request.setAttribute("remark", remark);
				}
			}
		  }
//		}
		}

		mv.setViewName("page/ipmshotel/leftmenu/membersearch/memberpage");
		// request.setAttribute("mscData", mscData);

		return mv;
	}

	@RequestMapping("/hotelmemberJudge.do")
	public void memberJudge(HttpServletRequest request, HttpServletResponse response, String data)
			throws Exception {
		//boolean isString = mphone instanceof String;
		boolean isStringc = data instanceof String;
		String memberms = null;
		String a = null;
		 if (isStringc) {
			memberms = data;
		}
//		if (HeartBeatClient.heartbeat) {
		com.ideassoft.portal.DataDealPortalService service = new com.ideassoft.portal.DataDealPortalService();
		com.ideassoft.portal.IDataDealPortal portal = service.getDataDealPortalPort();
		String mr = portal.call(1, 1, "{ function: \"checkInDirect.getMemberBean\", data:{idcard:" + memberms + " } }");
		if (mr.equals("-1")) {
			a = "未查询到相关会员信息！";
		   }else if(mr.equals("hasmult")){
			a = "查询到多条会员信息！";
		   }
		/*}else{
			a = "本地网络未连接，请网络连通后再进行查询！";
		}*/
		JSONUtil.responseJSON(response, new AjaxResult(1, a));
	}

	@RequestMapping("/hoteleditmemberData.do")
	public void editmemberData(HttpServletRequest request, HttpServletResponse response, String memberid,
			String mobile, String email, String address, String remark, String idcard) throws Exception {
		Member member = ((Member) (leftmenuService.findOneByProperties(Member.class, "memberId", memberid)));
		member.setMobile(mobile);
		member.setEmail(email);
		member.setAddress(address);
		member.setRemark(remark);
		//针对身份证只能修改一次，不能添加
/*		if(!StringUtils.isEmpty(idcard)){
			List<?> listmember = leftmenuService.findByProperties(Member.class, "idCard", idcard);
			if(listmember.size() > 0){
				JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, "身份证已被注册!"));
				return;
			}
			member.setIdcard(idcard);
		}
		*/
		

		
		leftmenuService.update(member);
		SysParam systemtype = (SysParam) pmsmanageService.findOneByProperties(SysParam.class, "paramType",
				"SYSTEMTYPE", "status", "1");
		String stype = systemtype.getContent().toString();
		if (stype.equals(CommonConstants.SystemType.Branch)) {
			int priority = 1;
			SysParam param = (SysParam) pmsmanageService.findOneByProperties(SysParam.class, "paramType",
					"REMOTE_PATH", "status", "1");
			List<Member> memberupdateList = new ArrayList<Member>();
			memberupdateList.add(member);
			Map<String, Object> memberupdateMap = new HashMap<String, Object>();
			memberupdateMap.put("Member", memberupdateList);
			// TransferServcie.getInstance().transferData(priority, param.getContent(),JSONUtil.fromObject(memberupdateMap).toString());
		}
		JSONUtil.responseJSON(response, new AjaxResult(1, "修改成功!"));
	}

	//商品售卖挂房账
	@RequestMapping("/hotelgdsRoompay.do")
	public void gdsRoompay(HttpServletRequest request, HttpServletResponse response, String gdscheckid,
			String totalprice, String gdsroomid, String gdsid, String totalnumber, String gdsprice) throws Exception {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		SysParam sysParamrpay = ((SysParam) (leftmenuService.findOneByProperties(SysParam.class, "paramType",
				"PROJECT", "paramName", "商品售卖")));
		String content = sysParamrpay.getContent().toString();
		Staff staff = loginuser.getStaff();
		Order order = (Order) leftmenuService.findById(Order.class, gdscheckid);
		Contrart contract = (Contrart) leftmenuService.findById(Contrart.class, gdscheckid);
		String branchid = order != null ? order.getBranchId() : contract.getBranchId();
		String logId = this.leftmenuService.getSequence("SEQ_NEW_BILL", null);
		Date day = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyMMdd");
		String t = df.format(day);
		String datId = t + branchid + logId;
		Double cost = Double.parseDouble(totalprice);
		Bill bill = new Bill();
		bill.setBillId(datId);
		bill.setBranchId(branchid);
		bill.setCheckId(gdscheckid);
		bill.setProjectId(content);
		bill.setProjectName("商品售卖");
		bill.setDescribe("商品");
		bill.setCost(cost);
		bill.setPay((double) 0);
		bill.setPayment("0");
		bill.setStatus("1");
		bill.setRecordUser(staff.getStaffId());
		leftmenuService.save(bill);
		Goods goods = ((Goods) (leftmenuService.findOneByProperties(Goods.class, "goodsId", gdsid)));
		// String goodsname = goods.getGoodsName();
		String categoryid = goods.getCategoryId();
		// GoodsCategory goodscategory = ((GoodsCategory)
		// (LeftmenuService.findOneByProperties(GoodsCategory.class,
		// "categoryId", categoryid)));
		// String categoryname = goodscategory.getCategoryName();
		String salelogId = this.leftmenuService.getSequence("SEQ_NEW_SALELOG", null);
		String saledatId = t + branchid + salelogId;
		Integer amount = Integer.valueOf(totalnumber);
		Double price = Double.parseDouble(gdsprice);
		SaleLog salelog = new SaleLog();
		salelog.setLogId(saledatId);
		salelog.setBranchId(branchid);
		salelog.setDebts("2");
		salelog.setCheckId(gdscheckid);
		salelog.setRoomId(gdsroomid);
		salelog.setCategoryId(categoryid);
		salelog.setGoodsName(gdsid);
		salelog.setAmount(amount);
		salelog.setPrice(price);
		salelog.setPayType("1");
		salelog.setRecordUser(staff.getStaffId());
		leftmenuService.save(salelog);
		JSONUtil.responseJSON(response, new AjaxResult(1, null));

	} 
	
	//商品售卖挂哑房
	@RequestMapping("/hotelgdsDumbpay.do")
	public void gdsDumbpay(HttpServletRequest request, HttpServletResponse response, 
			String totalprice, String gdsid, String totalnumber, String gdsprice) throws Exception {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String branchId = loginuser.getStaff().getBranchId();
		SysParam sysParam = ((SysParam) (leftmenuService.findOneByProperties(SysParam.class, "paramType",
				"PROJECT", "paramName", "挂哑房", "status", "1")));
		SysParam sysParams = ((SysParam) (leftmenuService.findOneByProperties(SysParam.class, "paramType",
				"BILLPAYMENT", "paramName", "挂哑房", "status", "1")));
		WorkBill workbill = (WorkBill) leftmenuService.findOneByProperties(WorkBill.class, "branchId", branchId, 
				"status", CommonConstants.WorkBillStatus.NEW);
		String content = sysParam.getContent().toString();
/*		Order order = (Order) leftmenuService.findById(Order.class, gdscheckid);
		Contrart contract = (Contrart) leftmenuService.findById(Contrart.class, gdscheckid);*/
		String logId = this.leftmenuService.getSequence("SEQ_NEW_BILL", null);
		Date day = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyMMdd");
		String t = df.format(day);
		String datId = t + branchId + logId;
		Double cost = Double.parseDouble(totalprice);
		WorkBillDetail wbDetail = new WorkBillDetail();
		wbDetail.setBranchId(branchId);
		wbDetail.setCost(cost);
		wbDetail.setDescribe("商品售卖");
		wbDetail.setDetailId(DateUtil.currentDate("yyMMdd") + branchId + leftmenuService.getSequence("SEQ_NEW_WORKBILLDETAIL", null));
		wbDetail.setPay(0.00);
		wbDetail.setPayment(sysParams.getContent());
		wbDetail.setProjectId(sysParam.getContent());
		wbDetail.setProjectName(sysParam.getParamName());
		wbDetail.setRecordTime(new Date());
		wbDetail.setRecordUser(staff.getStaffId());
		wbDetail.setWorkbillId(workbill.getWorkbillId());
		wbDetail.setStatus("1");
		leftmenuService.save(wbDetail);
		Goods goods = ((Goods) (leftmenuService.findOneByProperties(Goods.class, "goodsId", gdsid)));
		// String goodsname = goods.getGoodsName();
		String categoryid = goods.getCategoryId();
		// GoodsCategory goodscategory = ((GoodsCategory)
		// (LeftmenuService.findOneByProperties(GoodsCategory.class,
		// "categoryId", categoryid)));
		// String categoryname = goodscategory.getCategoryName();
		String salelogId = this.leftmenuService.getSequence("SEQ_NEW_SALELOG", null);
		String saledatId = t + branchId + salelogId;
		Integer amount = Integer.valueOf(totalnumber);
		Double price = Double.parseDouble(gdsprice);
		SaleLog salelog = new SaleLog();
		salelog.setLogId(saledatId);
		salelog.setBranchId(branchId);
		salelog.setDebts("1");
		salelog.setCategoryId(categoryid);
		salelog.setGoodsName(gdsid);
		salelog.setAmount(amount);
		salelog.setPrice(price);
		salelog.setCheckId(workbill.getWorkbillId());
		salelog.setPayType(sysParams.getContent());
		salelog.setRecordUser(staff.getStaffId());
		salelog.setRecordTime(new Date());
		leftmenuService.save(salelog);
		Recording recording = new Recording();
		recording.setRecordId(DateUtil.currentDate("yyMMdd") + branchId + leftmenuService.getSequence("SEQ_NEW_RECORDING", null));
		recording.setBranchId(branchId);
		recording.setCheckId(workbill.getWorkbillId());
		recording.setRecordType("2");
		recording.setProjectId(sysParam.getContent());
		recording.setFee(cost);
		recording.setRecordUser(staff.getStaffId());
		leftmenuService.save(recording); 
		JSONUtil.responseJSON(response, new AjaxResult(1, null));
	}

	@RequestMapping("/hotelgdsRoompaycash.do")
	public void gdsRoompaycash(HttpServletRequest request, HttpServletResponse response, String gdscheckid, String gdsworkbill, String gdsworkbillname,
			String totalprice, String cashpayvalue, String gdsid, String totalnumber, String gdsprice) throws Exception {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String branchid = loginuser.getStaff().getBranchId();
		String logId = this.leftmenuService.getSequence("SEQ_NEW_WORKBILLDETAIL", null);
		Date day = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyMMdd");
		String t = df.format(day);
		String datId = t + branchid + logId;
		Double pay = Double.parseDouble(totalprice);
		WorkBillDetail workbilldetail = new WorkBillDetail();
		workbilldetail.setDetailId(datId);
		workbilldetail.setBranchId(branchid);
		SysParam paymentsysparam = (SysParam) roomService.findOneByProperties(SysParam.class, "paramType", "BILLPAYMENT", "content", "1");
		String projectid = paymentsysparam.getOrderNo().toString();
		SysParam projectsysparam = (SysParam) roomService.findOneByProperties(SysParam.class, "paramType", "PROJECT", "content", projectid);
		String projectname = projectsysparam.getParamName();
		/*bill.setProjectId(projectid);
		bill.setProjectName(projectname);*/
		workbilldetail.setProjectId(projectid);
		workbilldetail.setProjectName(projectname);
		workbilldetail.setDescribe("商品售卖");
		workbilldetail.setPay(pay);
		workbilldetail.setCost((double) 0);
		workbilldetail.setPayment("1");
		workbilldetail.setStatus("1");
		workbilldetail.setRecordUser(staff.getStaffId());
		workbilldetail.setWorkbillId(gdsworkbill);
		WorkBillDetail workbilldetail2 = new WorkBillDetail();
		workbilldetail2.setDetailId(t + branchid + this.leftmenuService.getSequence("SEQ_NEW_WORKBILLDETAIL", null));
		workbilldetail2.setBranchId(branchid);
		workbilldetail2.setProjectId(projectid);
		workbilldetail2.setProjectName(projectname);
		workbilldetail2.setDescribe("商品售卖");
		workbilldetail2.setPay((double) 0);
		workbilldetail2.setCost(pay);
		workbilldetail2.setPayment("1");
		workbilldetail2.setStatus("1");
		workbilldetail2.setRecordUser(staff.getStaffId());
		workbilldetail2.setWorkbillId(gdsworkbill);
		leftmenuService.save(workbilldetail);
		leftmenuService.save(workbilldetail2);
		Goods goods = ((Goods) (leftmenuService.findOneByProperties(Goods.class, "goodsId", gdsid)));
		String categoryid = goods.getCategoryId();
		String salelogId = this.leftmenuService.getSequence("SEQ_NEW_SALELOG", null);
		String saledatId = t + branchid + salelogId;
		Integer amount = Integer.valueOf(totalnumber);
		Double price = Double.parseDouble(gdsprice);
		SaleLog salelog = new SaleLog();
		salelog.setLogId(saledatId);
		salelog.setBranchId(branchid);
		salelog.setDebts("1");
		salelog.setCategoryId(categoryid);
		salelog.setGoodsName(gdsid);
		salelog.setAmount(amount);
		salelog.setPrice(price);
		//原来是为2，不以备注为准，以参数表BILLPAYMENT为准
		salelog.setPayType(CommonConstants.BillPayment.Cash);
		salelog.setRecordUser(staff.getStaffId());
		leftmenuService.save(salelog);
		String recordlogId = this.leftmenuService.getSequence("SEQ_NEW_RECORDING", null);
		String recorddatId = t + branchid + recordlogId;
		Recording recording = new Recording();
		recording.setRecordId(recorddatId);
		recording.setBranchId(branchid);
		recording.setCheckId(gdsworkbill);
		recording.setRecordType("2");
		recording.setProjectId(projectid);
		recording.setFee(pay);
		recording.setRecordUser(staff.getStaffId());
		leftmenuService.save(recording);
		JSONUtil.responseJSON(response, new AjaxResult(1, null));

	}

	@RequestMapping("/hotelgdsRoompaycard.do")
	public void gdsRoompaycard(HttpServletRequest request, HttpServletResponse response, String gdscheckid, String gdsworkbill, String gdsworkbillname,
			String totalprice, String cardpayvalue, String gdsid, String totalnumber, String gdsprice) throws Exception {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String branchid = loginuser.getStaff().getBranchId();
		String logId = this.leftmenuService.getSequence("SEQ_NEW_WORKBILLDETAIL", null);
		Date day = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyMMdd");
		String t = df.format(day);
		String datId = t + branchid + logId;
		Double pay = Double.parseDouble(totalprice);
		WorkBillDetail workbilldetail = new WorkBillDetail();
		workbilldetail.setDetailId(datId);
		workbilldetail.setBranchId(branchid);
		SysParam paymentsysparam = (SysParam) roomService.findOneByProperties(SysParam.class, "paramType", "BILLPAYMENT", "content", "2");
		String projectid = paymentsysparam.getOrderNo().toString();
		SysParam projectsysparam = (SysParam) roomService.findOneByProperties(SysParam.class, "paramType", "PROJECT", "content", projectid);
		String projectname = projectsysparam.getParamName();
		workbilldetail.setProjectId(projectid);
		workbilldetail.setProjectName(projectname);
		workbilldetail.setDescribe("商品售卖");
		workbilldetail.setPay(pay);
		workbilldetail.setCost((double) 0);
		workbilldetail.setPayment("2");
		workbilldetail.setStatus("1");
		workbilldetail.setRecordUser(staff.getStaffId());
		workbilldetail.setVoucher(cardpayvalue);
		workbilldetail.setWorkbillId(gdsworkbill);
		WorkBillDetail workbilldetail2 = new WorkBillDetail();
		workbilldetail2.setDetailId(t + branchid + this.leftmenuService.getSequence("SEQ_NEW_WORKBILLDETAIL", null));
		workbilldetail2.setBranchId(branchid);
		workbilldetail2.setProjectId(projectid);
		workbilldetail2.setProjectName(projectname);
		workbilldetail2.setDescribe("商品售卖");
		workbilldetail2.setPay((double) 0);
		workbilldetail2.setCost(pay);
		workbilldetail2.setPayment("2");
		workbilldetail2.setStatus("1");
		workbilldetail2.setRecordUser(staff.getStaffId());
		workbilldetail2.setVoucher(cardpayvalue);
		workbilldetail2.setWorkbillId(gdsworkbill);
		leftmenuService.save(workbilldetail);
		leftmenuService.save(workbilldetail2);
		Goods goods = ((Goods) (leftmenuService.findOneByProperties(Goods.class, "goodsId", gdsid)));
		String categoryid = goods.getCategoryId();
		String salelogId = this.leftmenuService.getSequence("SEQ_NEW_SALELOG", null);
		String saledatId = t + branchid + salelogId;
		Integer amount = Integer.valueOf(totalnumber);
		Double price = Double.parseDouble(gdsprice);
		SaleLog salelog = new SaleLog();
		salelog.setLogId(saledatId);
		salelog.setBranchId(branchid);
		salelog.setDebts("1");
		salelog.setCategoryId(categoryid);
		salelog.setGoodsName(gdsid);
		salelog.setAmount(amount);
		salelog.setPrice(price);
		//原来是为3，不以备注为准，以参数表BILLPAYMENT为准
		salelog.setPayType(CommonConstants.BillPayment.Bankcard);
		salelog.setRecordUser(staff.getStaffId());
		leftmenuService.save(salelog);
		String recordlogId = this.leftmenuService.getSequence("SEQ_NEW_RECORDING", null);
		String recorddatId = t + branchid + recordlogId;
		Recording recording = new Recording();
		recording.setRecordId(recorddatId);
		recording.setBranchId(branchid);
		recording.setRecordType("2");
		recording.setProjectId(projectid);
		recording.setFee(pay);
		recording.setRecordUser(staff.getStaffId());
		leftmenuService.save(recording);
		JSONUtil.responseJSON(response, new AjaxResult(1, null));

	}

	@RequestMapping("/gdmanageData.do")
	public ModelAndView gdmanageData(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchid = loginuser.getStaff().getBranchId();
		/*if (branchid.equals(SystemConstants.User.BRANCH_ID)) {
			branchid = "%";
		}*/
		List<?> categoryCondition = leftmenuService.getCategorycondition(branchid);
		mv.setViewName("page/ipmshotel/leftmenu/goodssale/gdsmanage");
		request.setAttribute("categoryCondition", categoryCondition);
		return mv;
	}

	@RequestMapping("/goodsManage.do")
	public ModelAndView goodsManage(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		Pagination pagination = SqlBuilder.buildPagination(request);
		pagination.setRows(13);
		// @SuppressWarnings("unchecked")
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginuser.getStaff().getBranchId();
		/*if (branchId.equals(SystemConstants.User.BRANCH_ID)) {
			branchId = "%";
		}*/
		List<?> goodsSale = leftmenuService.getGoodssale(pagination, branchId);
		mv.setViewName("page/ipmshotel/leftmenu/goodssale/gdsmanagedata");
		mv.addObject("goodsSale", goodsSale);
		mv.addObject("pagination", pagination);
		return mv;
	}

	@RequestMapping("/gdsDown.do")
	public void gdsDown(HttpServletRequest request, HttpServletResponse response, String gdsid) throws Exception {
		Goods goods = ((Goods) (leftmenuService.findOneByProperties(Goods.class, "goodsId", gdsid)));
		goods.setStatus("2");
		leftmenuService.save(goods);
		JSONUtil.responseJSON(response, new AjaxResult(1, null));
	}

	@RequestMapping("/gdsUp.do")
	public void gdsUp(HttpServletRequest request, HttpServletResponse response, String gdsid) throws Exception {
		Goods goods = ((Goods) (leftmenuService.findOneByProperties(Goods.class, "goodsId", gdsid)));
		goods.setStatus("1");
		leftmenuService.save(goods);
		JSONUtil.responseJSON(response, new AjaxResult(1, null));
	}

	/*
	 * @RequestMapping("/goodsManage.do") public ModelAndView
	 * goodsManage(HttpServletRequest request) throws Exception{ ModelAndView mv
	 * = new ModelAndView(); List<?> categoryCondition =
	 * LeftmenuService.getCategorycondition();
	 * mv.setViewName("page/ipmspage/leftmenu/goodsmanage");
	 * request.setAttribute("categoryCondition", categoryCondition); return mv;
	 * }
	 */

	@RequestMapping("/gdmanageCondition.do")
	public ModelAndView gdmanageCondition(HttpServletRequest request, HttpServletResponse response, String goodsid,
			String goodsname, String categoryid, String status) throws Exception {
		ModelAndView mv = new ModelAndView();
		Pagination pagination = SqlBuilder.buildPagination(request);
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginuser.getStaff().getBranchId();
		List<?> gdsmanageCondition = leftmenuService.getGdmanagecondition(goodsid, goodsname, categoryid, status,
				branchId, pagination);
		mv.setViewName("page/ipmshotel/leftmenu/goodssale/gdsmanagecondition");
		mv.addObject("gdsmanageCondition", gdsmanageCondition);
		mv.addObject("goodsid", goodsid);
		mv.addObject("goodsname", goodsname);
		mv.addObject("categoryid", categoryid);
		mv.addObject("status", status);
		mv.addObject("pagination", pagination);
		return mv;
	}

	@RequestMapping("/ordermemberAdd.do")
	public ModelAndView ordermemberAdd(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/ipmshotel/leftmenu/leftorder/ordermemberadd");
		return mv;
	}
	
	/**
	 * 检验身份证唯一
	 * @param request
	 * @param response
	 * @param idcard
	 */
	@RequestMapping("/leftmenuuniqueIdcard.do")
	public void uniqueIdcard (HttpServletRequest request, HttpServletResponse response, String idcard) {
		Member member = (Member) leftmenuService.findOneByProperties(Member.class, "idcard", idcard, "status", "1");
		if(member != null){
			JSONUtil.responseJSON(response, new AjaxResult(0, "身份证重复!"));
		} else {
			JSONUtil.responseJSON(response, new AjaxResult(1, "可以注册"));
		}
	}

	@RequestMapping("/formOmadd.do")
	public void formPurchase(HttpServletRequest request, HttpServletResponse response, String ordermaddjson)
			throws Exception {
		JSONArray ordermaddarray = new JSONArray(ordermaddjson);
		/*SysParam systemtype = (SysParam) leftmenuService.findOneByProperties(SysParam.class, "paramType", "SYSTEMTYPE",
				"status", "1");
		String stype = systemtype.getContent().toString();*/
		for (int i = 0; i < ordermaddarray.length(); i++) {
			JSONObject jsonordermadd = (JSONObject) ordermaddarray.get(i);
			String memberid = this.leftmenuService.getCloudSequence("SEQ_MEMBER_ID", null);
			String membername = jsonordermadd.getString("omembername");
			membername = URLDecoder.decode(membername,"UTF-8");
			String loginname = jsonordermadd.getString("ologinname");
			String pwd = MD5Util.getCryptogram("888888");
			String idcard = jsonordermadd.getString("oidcard");
			String gendor = jsonordermadd.getString("ogendor");
			String birthday = jsonordermadd.getString("obirthday");
			String mobile = jsonordermadd.getString("omobile");
			String email = jsonordermadd.getString("oemail");
			String address = jsonordermadd.getString("oaddress");
			String postcode = jsonordermadd.getString("opostcode");
			String remark = jsonordermadd.getString("oremark");
			DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
			Date birdate;
			if (((URLDecoder.decode(birthday, "UTF-8")) != null && !(URLDecoder.decode(birthday, "UTF-8")).equals(""))) {
				birdate = df.parse((URLDecoder.decode(birthday, "UTF-8")));
			} else {
				birdate = null;
			}
			Date day = new Date();
			SimpleDateFormat dfe = new SimpleDateFormat("yyyy/MM/dd");
			String t = dfe.format(day);
			Date D = df.parse(t);
			Calendar calendar = Calendar.getInstance();
			Date date = new Date(System.currentTimeMillis());
			calendar.setTime(date);
			calendar.add(Calendar.YEAR, +1);
			date = calendar.getTime();
			String s = dfe.format(date);
			Date Dl = df.parse(s);
/*			String mr = null;
				com.ideassoft.portal.DataDealPortalService service = new com.ideassoft.portal.DataDealPortalService();
				com.ideassoft.portal.IDataDealPortal portal = service.getDataDealPortalPort();
                mr = portal.call(1, 1, "{ function: \"checkInDirect.getMemberBean\", data:{idcard:" + mobile
						+ " } }");
			if (((mr.equals("-1"))==false)&&((mr.equals(null))==false)) {
				JSONUtil.responseJSON(response, new AjaxResult(0, "手机号已注册!", null));
				return;
			}*/
			
			List<?> memberList = leftmenuService.findBySQL("select4mebphone", new String[]{mobile},true);
			if(memberList.size() > 0 ){
				JSONUtil.responseJSON(response, new AjaxResult(0, "手机号已注册!", null));
				return;
			}
			
			//实名校验
			String checkIdCard = checkIdCard(membername,idcard);
			if("-1".equals(checkIdCard) || "2".equals(checkIdCard)){
				JSONUtil.responseJSON(response, new AjaxResult(5,"你的姓名和身份证不符!"));
				return;
			}
			Member member = new Member();
			member.setMemberId(memberid);
			member.setMemberName(URLDecoder.decode(membername, "UTF-8"));
			member.setLoginName(URLDecoder.decode(loginname, "UTF-8"));
			member.setPassword(pwd);
			member.setMemberRank(CommonConstants.MembenRank.PM);
			member.setIdcard(idcard);
			member.setGendor(gendor);
			member.setBirthday(birdate);
			member.setMobile(mobile);
			member.setEmail(URLDecoder.decode(email, "UTF-8"));
			member.setAddress(URLDecoder.decode(address, "UTF-8"));
			member.setPostcode(postcode);
			member.setSource("1");
			member.setValidTime(D);
			member.setRegisterTime(D);
			member.setInvalidTime(Dl);
			member.setStatus("1");
			member.setRemark(URLDecoder.decode(remark, "UTF-8"));
			member.setRecordTime(new Date());
			leftmenuService.save(member);
			/*if (stype.equals(CommonConstants.SystemType.Branch)) {
				int priority = 1;
				SysParam param = (SysParam) leftmenuService.findOneByProperties(SysParam.class, "paramType",
						"REMOTE_PATH", "status", "1");
				List<Member> memberlList = new ArrayList<Member>();
				memberlList.add(member);
				Map<String, Object> memberMap = new HashMap<String, Object>();
				memberMap.put("Member", memberlList);
				// TransferServcie.getInstance().transferData(priority, param.getContent(),JSONUtil.fromObject(memberMap).toString());
			}*/
			String accountid = this.leftmenuService.getCloudSequence("SEQ_ACCOUNT_ID", null);
			MemberAccount memberaccount = new MemberAccount();
			memberaccount.setAccountId(accountid);
			memberaccount.setMemberId(memberid);
			memberaccount.setCurrBalance((double) 0);
			memberaccount.setCurrIntegration((long) 0);
			memberaccount.setTotalRecharge((double) 0);
			memberaccount.setDiscountGift((double) 0);
			memberaccount.setChargeGift((double) 0);
			memberaccount.setTotalConsume((double) 0);
			memberaccount.setTotalIntegration((long) 0);
			memberaccount.setIngegrationGift((long) 0);
			memberaccount.setTotalConsIntegration((long) 0);
			memberaccount.setTotalRoomnights(0);
			memberaccount.setCurrRoomnights(0);
			memberaccount.setTotalNoshow((short) 0);
			memberaccount.setCurrNoshow((short) 0);
			memberaccount.setThisYearIntegration((long) 0);
			memberaccount.setRecordTime(new Date());
			memberaccount.setStatus("1");
			leftmenuService.save(memberaccount);
			/*if (stype.equals(CommonConstants.SystemType.Branch)) {
				int priority = 1;
				SysParam param = (SysParam) leftmenuService.findOneByProperties(SysParam.class, "paramType",
						"REMOTE_PATH", "status", "1");
				List<MemberAccount> memberaccountlList = new ArrayList<MemberAccount>();
				memberaccountlList.add(memberaccount);
				Map<String, Object> memberaccountMap = new HashMap<String, Object>();
				memberaccountMap.put("MemberAccount", memberaccountlList);
				// TransferServcie.getInstance().transferData(priority, param.getContent(),JSONUtil.fromObject(memberaccountMap).toString());
			}*/
		}

		JSONUtil.responseJSON(response, new AjaxResult(1, null));
	}

	@RequestMapping("/exceptionSerach.do")
	@RightMethodControl(rightType = RightConstants.RightType.HOT_EXCEP)
	public ModelAndView exceptionSerach(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		List<?> exceptionType = leftmenuService.getExceptiontype();
		mv.setViewName("page/ipmshotel/leftmenu/exception/exceptionpage");
		request.setAttribute("exceptionType", exceptionType);
		return mv;
	}

	@RequestMapping("/exceptionData.do")
	public ModelAndView exceptionData(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchid = loginuser.getStaff().getBranchId();
		//Pagination pagination = SqlBuilder.buildPagination(request);
		Pagination pagination = (Pagination) ReflectUtil.setBeanFromRequest(request, Pagination.class);
		if (pagination != null && pagination.getPageNum() == null) {
			pagination.setPageNum(1);
			pagination.setRows(17);
		}
		List<?> exceptiondata = leftmenuService.getExceptiondata(branchid, pagination);
		mv.setViewName("page/ipmshotel/leftmenu/exception/exceptiondata");
		mv.addObject("exceptiondata", exceptiondata);
		mv.addObject("pagination", pagination);
		return mv;
	}

	@RequestMapping("/exceptionCondition.do")
	public ModelAndView exceptionCondition(HttpServletRequest request, HttpServletResponse response,
			String exbegintime, String exendtime, String exceptiontype, String exceptionstatus) throws Exception {
		ModelAndView mv = new ModelAndView();
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchid = loginuser.getStaff().getBranchId();
		Pagination pagination = SqlBuilder.buildPagination(request);
		List<?> exceptioncondition = leftmenuService.getExceptioncondition(exbegintime, exendtime, exceptiontype,
				exceptionstatus, branchid, pagination);
		mv.setViewName("page/ipmshotel/leftmenu/exception/exceptioncondition");
		mv.addObject("exceptioncondition", exceptioncondition);
		mv.addObject("pagination", pagination);
		return mv;
	}

	@RequestMapping("/exceptioncs.do")
	public void exceptioncs(HttpServletRequest request, HttpServletResponse response, String exceptionid)
			throws Exception {
		ExceptionMessage exceptionmessage = ((ExceptionMessage) (leftmenuService.findOneByProperties(
				ExceptionMessage.class, "exceptionId", exceptionid)));
		exceptionmessage.setStatus("1");
		leftmenuService.save(exceptionmessage);
		JSONUtil.responseJSON(response, new AjaxResult(1, null));
	}

	/*
	 * @RequestMapping("/pettyCash.do") public ModelAndView
	 * pettyCash(HttpServletRequest request) throws Exception { ModelAndView mv
	 * = new ModelAndView(); // List<?> exceptionType =
	 * LeftmenuService.getExceptiontype();
	 * mv.setViewName("page/ipmspage/leftmenu/pettycash/cashregister"); //
	 * request.setAttribute("exceptionType", exceptionType); return mv; }
	 */

	@SuppressWarnings("unchecked")
	@RequestMapping("/pmsLoginlog.do")
	public void pmsLoginlog(HttpServletRequest request, HttpServletResponse response, String shiftid) throws Exception {
		String bigBranchType = "";
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String loginstaff = staff.getStaffId();
		String branchid = loginuser.getStaff().getBranchId();
		String a = null;
		String branchTypeApart = "";
		//List<?> house = leftmenuService.findByProperties(House.class, "staffId", loginstaff);
		List<?> house = leftmenuService.findBySQL("fHouseWithStaffid",new String[]{loginuser.getStaff().getStaffId(),loginuser.getStaff().getStaffId()}, true);

		Branch bigBranch = marketingBasicService.queryLoginUserBType(branchid);
		
		List<Role> sysRoles = loginuser.getRightsList();
		List<RoleRelation> roleRelations;
		String flag = "1";
		
		if (bigBranch != null) {
			bigBranchType = bigBranch.getRank();
			branchTypeApart =  bigBranch.getBranchType();
		}
		
		//所有业务权限
		HashMap<String, RightDefine> mapdefineright = RightConstants.rightDefineMap;
        Map<String, Object> maprighttype = ReflectUtil.getVariableMap(RightConstants.RightType.class);
        String modelName = "";
        if (CommonConstants.SystemLevel.MarketCenter.equals(bigBranchType)) {
        	modelName = RightConstants.RightModel.HOUSE_CONTROL;
        } else if (CommonConstants.SystemTheme.HOTEL.equals(bigBranch.getBranchType())) {
        	modelName = RightConstants.RightModel.HOTEL_CONTROL;
        } else if (CommonConstants.SystemTheme.APARTMENTS.equals(bigBranch.getBranchType())) {
        	modelName = RightConstants.RightModel.APARTMENT_CONTROL;
        }
    	//Map<String, Object> mapmodel = new HashMap<String, Object>();
    	
//        	if(CommonConstants.BranchRank.TOP.equals(bigBranchType)){
//        		if(RightConstants.RightModel.HOTEL_CONTROL.equals(rightmodel.getValue()) || RightConstants.RightModel.APARTMENT_CONTROL.equals(rightmodel.getValue())){
//        			//continue;
//        		}
//			}
    	
    	//mapmodel.put("model", modelName);
    	
    	List<Map<String, Object>> listrights = new ArrayList<Map<String, Object>>(); 
    	for (Map.Entry<String, RightDefine> rightdefine : mapdefineright.entrySet()) {
    		for (Map.Entry<String, Object> righttype : maprighttype.entrySet()) {
    			Map<String, Object> el = new HashMap<String, Object>();
            	if(modelName.equals(rightdefine.getValue().getRightModel()) && righttype.getValue().equals(rightdefine.getValue().getRightType())){
            		el.put("righttype", righttype.getKey());
            		el.put("name", righttype.getValue());
            		listrights.add(el);
            	}
            }
        }
    	//mapmodel.put("listrights", listrights);
		
		//登录人的所有权限
		if (sysRoles != null && !sysRoles.isEmpty()) {
			for (int i = 0; i < sysRoles.size(); i++) {
				roleRelations = sysRoles.get(i).getRoleRelation();
				if (roleRelations != null && !roleRelations.isEmpty()) {
					for (int j = 0; j < roleRelations.size(); j++) {
						for (int aa = 0; aa < listrights.size(); aa++) {
							if (roleRelations.get(j).getFuncrightId() != null && roleRelations.get(j).getFuncrightId().equals(((Map<?, ?>) listrights.get(aa)).get("righttype").toString())) {
								flag = "2";
								break;
							} 
//							else if (roleRelations.get(j).getFuncrightId() != null && !CommonConstants.SystemLevel.MarketCenter.equals(bigBranchType)) {
//								flag = "2";
//								break;
//							}
						}
					}
				}
			}
		}
	
		List<?> ordernoList = leftmenuService.findBySQL("shifttime2", new String[] { branchid, branchid, branchid },
				true);

		//总店的，有民宿管理的才可登录ipms
		/*List<?> houseList = leftmenuService.findBySQL("selectMyHouse", new String[] { loginuser.getStaff().getStaffId() },
				true);*/
		
		if ((loginstaff.equals(SystemConstants.User.ADMIN_ID)) && branchid.equals(SystemConstants.User.BRANCH_ID)) {
			a = "超级用户暂不能使用pms功能";
		} else if (flag == "1") {
			a = "没有业务权限，暂不能使用pms功能";
		} else if (ordernoList.size() == 0 && !CommonConstants.SystemLevel.MarketCenter.equals(bigBranchType) && branchTypeApart.trim().equals("1")) {
			a = "默认班次失败或班次未配置，请前往crm配置班次！";
		} else {

			String logId = DateUtil.currentDate("yyMMdd") + CommonConstants.LoginSource.BRANCH + leftmenuService.getSequence("SEQ_LOGINLOG_ID");
			LoginLog loginlog = new LoginLog();
			String operid = InetAddress.getLocalHost().toString();// IP地址
			//String operid = IPUtil.getIpAddr(request);
			operid = (String) operid.subSequence(operid.indexOf("/") + 1, operid.length());
			String header = request.getHeader("User-Agent").toLowerCase();
			if (header == null || header.equals("")) {
				loginlog.setBrowser(" ");
			}
			if (header.indexOf("chrome") > 0) {
				loginlog.setBrowser("chrome");
			} else if (header.indexOf("firefox") > 0) {
				loginlog.setBrowser("firefox");
			} else if (header.indexOf("msie") > 0) {
				loginlog.setBrowser("ie");
			} else if (header.indexOf("safari") > 0) {
				loginlog.setBrowser("safari");
			} else if (header.indexOf("camino") > 0) {
				loginlog.setBrowser("camino");
			} else if (header.indexOf("konqueror") > 0) {
				loginlog.setBrowser("konqueror");
			}
			Branch branch = ((Branch) (leftmenuService.findOneByProperties(Branch.class, "branchId", branchid)));
			String theme = branch.getBranchType().toString();
			String state = null;
			if (theme.equals(CommonConstants.SystemTheme.HOTEL)) {
				List<?> newloginnoshiftData = leftmenuService.getNewloginnoshiftData(loginstaff, loginstaff, branchid);
				if (null == newloginnoshiftData || newloginnoshiftData.size() == 0) {

					state = "1";
				} else {
					state = "0";
				}
			}
			

			
			
			//记交接班日志
			String branchrank = branch.getRank();
			String branchtype = branch.getBranchType();
			SysParam sys = (SysParam)leftmenuService.findOneByProperties(SysParam.class, "paramType", "SYSTEMTYPE");
			if(null != sys 
					&& (branchtype.equals(CommonConstants.SystemTheme.HOTEL)) 
					&& !(branchrank.equals(CommonConstants.BranchRank.TOP)) ){
				List<?> logininfo = leftmenuService.findByProperties(LoginLog.class,"loginLogId.loginId", staff.getStaffId(),"state","1");
				if(logininfo.size() == 0){
					SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
					String shiftlogid = sdf.format(new Date()) + branchid + leftmenuService.getSequence("SEQ_NEW_SHIFT");
					
					List<Shift> shiftList = leftmenuService.findByPropertiesWithSort(Shift.class, "logId","desc","branchId", branchid);
					Shift shiftlog = null;
					if(shiftList.size() == 0){
						shiftlog = new  Shift();
						shiftlog.setLogId(shiftlogid);
						shiftlog.setBranchId(branchid);
						shiftlog.setLastShift("");
						shiftlog.setCurrShift(shiftid);
						shiftlog.setLastUser("");
						shiftlog.setCurrUser(loginstaff);
						shiftlog.setRecordTime(new Date());
						shiftlog.setRecordUser(loginstaff);
						
					}else{
						
						shiftlog = new  Shift();
						shiftlog.setLogId(shiftlogid);
						shiftlog.setBranchId(branchid);
						shiftlog.setLastShift(shiftList.get(0).getCurrShift());
						shiftlog.setCurrShift(shiftid);
						shiftlog.setLastUser(shiftList.get(0).getCurrUser());
						shiftlog.setCurrUser(loginstaff);
						shiftlog.setRecordTime(new Date());
						shiftlog.setRecordUser(loginstaff);
						
					}
					leftmenuService.save(shiftlog);
					
					
				}
				
			}
			
			
			LoginLogId loginlogid = new LoginLogId();
			loginlogid.setLogId(logId);
			loginlogid.setLoginId(staff.getStaffId());
			loginlog.setLoginLogId(loginlogid);
			loginlog.setShift(shiftid);
			loginlog.setLoginIp(operid);
			loginlog.setRecordTime(new Date());
			loginlog.setSource(CommonConstants.LoginSource.BRANCH);
			loginlog.setStatus("1");
			loginlog.setState(state);
			loginlog.setRemark("登录ipms成功!");
			leftmenuService.save(loginlog);
			
			
		}
		

		JSONUtil.responseJSON(response, new AjaxResult(1, a));
	}

	@RequestMapping("/cashCount.do")
	public void cashCount(HttpServletRequest request, HttpServletResponse response, String logid) throws Exception {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String branchid = null;
		String cashierstaff = null;
		String shiftid = null;
		/* String cashbox = null; */
		String loginstaff = null;
		String logintime = null;
		if ((logid == null) || (logid.equals(""))) {
			cashierstaff = staff.getStaffId();
			branchid = loginuser.getStaff().getBranchId();
			 List<?> loginData = leftmenuService.getLoginnewdata(cashierstaff); 
			List<?> newloginnoshiftData = leftmenuService.getNewloginnoshiftData(cashierstaff, cashierstaff, branchid);
			loginstaff = ((Map<?, ?>) newloginnoshiftData.get(0)).get("LOGIN_ID").toString();
			logintime = ((Map<?, ?>) newloginnoshiftData.get(0)).get("RECORD_TIME").toString();
			shiftid = ((Map<?, ?>) loginData.get(0)).get("SHIFT").toString();
			/* cashbox = "A"; */

		} else {
			 List<?> loginData = leftmenuService.getLoginnewdata(cashierstaff);
			List<?> loginDataother = leftmenuService.getLogindataother(logid);
			loginstaff = ((Map<?, ?>) loginDataother.get(0)).get("LOGINID").toString();
			logintime = ((Map<?, ?>) loginDataother.get(0)).get("RECORDTIME").toString();
			shiftid = ((Map<?, ?>) loginData.get(0)).get("SHIFT").toString();
			/*
			 * cashbox = ((Map<?, ?>)
			 * loginDataother.get(0)).get("CASHBOX").toString();
			 */
			Staff stafflogin = ((Staff) (leftmenuService.findOneByProperties(Staff.class, "staffId", loginstaff)));
			branchid = stafflogin.getBranchId().toString();
			cashierstaff = loginstaff;

		}

		List<?> cashiercashDatanew = leftmenuService.getCashiercashdatanew(branchid, loginstaff, logintime);
		List<?> cashiercardDatanew = leftmenuService.getCashiercarddatanew(branchid, loginstaff, logintime);

		String cashcountin = null;
		String cashcountout = null;
		String cardcount = null;
		if (null == cashiercashDatanew || cashiercashDatanew.size() == 0) {
			cashcountin = "0.00";
			cashcountout = "0.00";
		} else {
			cashcountin = ((Map<?, ?>) cashiercashDatanew.get(0)).get("CASHIN") == null ? "0.00"
					: ((Map<?, ?>) cashiercashDatanew.get(0)).get("CASHIN").toString();
			cashcountout = ((Map<?, ?>) cashiercashDatanew.get(0)).get("CASHOUT") == null ? "0.00"
					: ((Map<?, ?>) cashiercashDatanew.get(0)).get("CASHOUT").toString();

		}
		if (null == cashiercardDatanew || cashiercardDatanew.size() == 0) {
			cardcount = "0.00";
		} else {
			cardcount = ((Map<?, ?>) cashiercardDatanew.get(0)).get("CARD").toString();

		}
		String lastbranchid = branchid;
		List<?> cashboxstatus = leftmenuService.getCashboxstatus(lastbranchid);
		String cashboxcount = null;
		if (null == cashboxstatus || cashboxstatus.size() == 0) {
			cashboxcount = "0.00";
		} else {
			cashboxcount = ((Map<?, ?>) cashboxstatus.get(0)).get("CASH_COUNT").toString();
		}

		Staff cashierstaffclass = ((Staff) (leftmenuService.findOneByProperties(Staff.class, "staffId", cashierstaff)));
		String cashierstaffname = cashierstaffclass.getStaffName().toString();
		Branch branch = ((Branch) (leftmenuService.findOneByProperties(Branch.class, "branchId", branchid)));
		String branchname = branch.getBranchName().toString();
		ShiftTime shifttimename = ((ShiftTime) (leftmenuService
				.findOneByProperties(ShiftTime.class, "shiftTimeId", shiftid,"branchId",loginuser.getStaff().getBranchId())));

		String shiftname = shifttimename.getShiftname() == null ? "" :  shifttimename.getShiftname().toString();
		List<?> lastshift = leftmenuService.getLastshift(branchid);
		String lastshiftvalue = null;
		if (null == lastshift || lastshift.size() == 0) {
			lastshiftvalue = "0.00";
		} else {
			String lastbranchidlast = ((Map<?, ?>) lastshift.get(0)).get("BRANCH_ID").toString();
			String currshift = ((Map<?, ?>) lastshift.get(0)).get("SHIFT").toString();
			String curruser = ((Map<?, ?>) lastshift.get(0)).get("HAND_USER").toString();
			String recordtime = ((Map<?, ?>) lastshift.get(0)).get("RECORD_TIME").toString();
			List<?> lastshiftValue = leftmenuService.getLastshiftvalue(lastbranchidlast, currshift, curruser,
					recordtime);
			lastshiftvalue = ((Map<?, ?>) lastshiftValue.get(0)).get("CURR_SHIFTVALUE").toString();
		}
		List<?> billcard = leftmenuService.getBillcard(branchid, loginstaff, logintime);
		List<?> workcard = leftmenuService.getWorkcard(branchid, loginstaff, logintime);
		String bcard = ((Map<?, ?>) billcard.get(0)).get("BCARD") == null ? "0" : ((Map<?, ?>) billcard.get(0)).get(
				"BCARD").toString();
		String wcard = ((Map<?, ?>) workcard.get(0)).get("WCARD") == null ? "0" : ((Map<?, ?>) workcard.get(0)).get(
				"WCARD").toString();
		int a = Integer.parseInt(bcard);
		int b = Integer.parseInt(wcard);
		String cards = String.valueOf(a + b);
		Map<String, String> cashmap = new HashMap<String, String>();
		cashmap.put("cashin", cashcountin);
		cashmap.put("cashout", cashcountout);
		cashmap.put("card", cardcount);
		cashmap.put("cards", cards);
		/* cashmap.put("boxname", cashbox); */
		cashmap.put("boxcount", cashboxcount);
		cashmap.put("shift", shiftid);
		cashmap.put("shifterid", cashierstaff);
		cashmap.put("shiftername", cashierstaffname);
		cashmap.put("branchname", branchname);
		cashmap.put("shiftname", shiftname);
		cashmap.put("lastshiftvalue", lastshiftvalue);
		cashmap.put("logintime", logintime);
		JSONUtil.responseJSON(response, cashmap);
	}

	@RequestMapping("/cashShift.do")
	@RightMethodControl(rightType = RightConstants.RightType.HOT_BACASH)
	public ModelAndView cashShift(HttpServletRequest request, HttpServletResponse response, String cashcountin,
			String cashcountout, String cardcount, String cards, String boxcount, String shift, String shifterid,
			String shiftername, String branchname, String shiftname, String lastshiftvalue, String shifttype,
			String logintime) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/ipmshotel/leftmenu/pettycash/cashregister");
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchid = loginuser.getStaff().getBranchId();
		List<?> submitperson = leftmenuService.getSubmitperson(branchid, shifterid);
		request.setAttribute("cashcountin", cashcountin);
		request.setAttribute("cashcountout", cashcountout);
		request.setAttribute("cardcount", cardcount);
		request.setAttribute("cards", cards);
		/* request.setAttribute("boxname", boxname); */
		request.setAttribute("boxcount", boxcount);
		request.setAttribute("shift", shift);
		request.setAttribute("shifterid", shifterid);
		request.setAttribute("shiftername", shiftername);
		request.setAttribute("branchid", branchid);
		request.setAttribute("branchname", branchname);
		request.setAttribute("shiftname", shiftname);
		request.setAttribute("lastshiftvalue", lastshiftvalue);
		request.setAttribute("submitperson", submitperson);
		request.setAttribute("shifttype", shifttype);
		request.setAttribute("logintime", logintime);
		return mv;
	}
	/*
	 * 交接人是否存在
	 */
	@RequestMapping("/cashCheckBefore.do")
	public void cashCheckBefore(HttpServletRequest request, HttpServletResponse response) throws Exception{
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchid = loginuser.getStaff().getBranchId();
		String shifterid = loginuser.getStaff().getStaffId();
		List<?> submitperson = leftmenuService.getSubmitperson(branchid, shifterid);
		if(submitperson.isEmpty()){
			JSONUtil.responseJSON(response, new AjaxResult(1, "门店下无可交接员工!"));
			return;
		}
		JSONUtil.responseJSON(response, new AjaxResult(2, null));
	}
	
	/*
	 * 交接选择确认人页面
	 */
	@RequestMapping("/cashSubmitstaffselect.do")
	public ModelAndView cashSubmitstaffselect(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchid = loginuser.getStaff().getBranchId();
		String shifterid = loginuser.getStaff().getStaffId();
		List<?> submitperson = leftmenuService.getSubmitperson(branchid, shifterid);
		mv.setViewName("page/ipmshotel/leftmenu/pettycash/cashsubmitstaff");
		mv.addObject("submitperson", submitperson);
		return mv;
	}

	/*
	 * 左侧跳转维修页面
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/repair.do")
	public ModelAndView repair(HttpServletRequest request) {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Branch bigBranch = marketingBasicService.queryLoginUserBType(loginuser.getStaff().getBranchId());
		List optionone = leftmenuService.findBySQL("queryOptionsOne", true);
		List optiontwo = leftmenuService.findBySQL("queryOptionsTwo", true);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/ipmspage/leftmenu/repair/repair");
		mv.addObject("optionone", optionone);
		mv.addObject("optiontwo", optiontwo);
		// 当是营销中心的职员登录时
		mv.addObject("loginBranch", loginuser.getStaff());
		return mv;
	}

	/*
	 * @RequestMapping("/judgeCash.do") public void judgeCash(HttpServletRequest
	 * request, HttpServletResponse response,String shiftid,String cashbox)
	 * throws Exception { LoginUser loginuser = (LoginUser)
	 * request.getSession(true
	 * ).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY); Staff staff =
	 * loginuser.getStaff(); String curruser = staff.getStaffId(); String
	 * lastbranchid = loginuser.getStaff().getBranchId(); //LoginGetShift
	 * loginGetShift = (LoginGetShift)
	 * request.getSession(true).getAttribute("loginGetShift"); //String shiftid
	 * = loginGetShift.getShiftId(); //String cashbox =
	 * loginGetShift.getCashbox(); String a = null; String cbstatus = "";
	 * List<?> cashboxstatus = LeftmenuService.getCashboxstatus(lastbranchid,
	 * cashbox); if ((cashboxstatus.size()) > 0 && (!curruser.equals("1"))) {
	 * cbstatus = ((Map<?, ?>)
	 * cashboxstatus.get(0)).get("CASH_STATUS").toString(); } else if
	 * ((cashboxstatus.size()) > 0 && (lastbranchid.equals("100001"))) {
	 * cbstatus = "-2"; } else { cbstatus = "-1"; }
	 * 
	 * if (cbstatus.equals("0")) { String branchid = lastbranchid; String
	 * recordUser = curruser; String updatestatus = "1"; String boxname =
	 * cashbox; Branch branchcash = ((Branch)
	 * (LeftmenuService.findOneByProperties(Branch.class, "branchId",
	 * branchid))); String theme = branchcash.getBranchType().toString(); if
	 * (theme.equals("1")) { LeftmenuService.upatecashbox(branchid, boxname,
	 * recordUser, updatestatus); List<?> loginstaffData =
	 * LeftmenuService.getLoginstaffData(curruser); String updatelogid =
	 * ((Map<?, ?>) loginstaffData.get(0)).get("LOGID").toString(); String
	 * updatestate = "1"; String oldstate = "0";
	 * LeftmenuService.updateLoglinlog(updatelogid, curruser, updatestate,
	 * oldstate); List<?> lastshift = LeftmenuService.getLastshift(branchid);
	 * String shift = null; String hanguser = null; if (null == lastshift ||
	 * lastshift.size() == 0) { shift = "0"; hanguser = "0"; } else { shift =
	 * ((Map<?, ?>) lastshift.get(0)).get("SHIFT").toString(); hanguser =
	 * ((Map<?, ?>) lastshift.get(0)).get("HAND_USER").toString(); } String
	 * logId = this.LeftmenuService.getSequence("SEQ_NEW_SHIFT", null); Date
	 * shiftday = new Date(); SimpleDateFormat dfe = new
	 * SimpleDateFormat("yyMMdd"); String t = dfe.format(shiftday); String logid
	 * = t + branchid + logId; Shift shiftdata = new Shift();
	 * shiftdata.setLogId(logid); shiftdata.setBranchId(lastbranchid);
	 * shiftdata.setCurrShift(shiftid); shiftdata.setCurrUser(curruser);
	 * shiftdata.setLastShift(shift); shiftdata.setLastUser(hanguser);
	 * shiftdata.setRecordTime(new Date()); shiftdata.setRecordUser(curruser);
	 * LeftmenuService.save(shiftdata);
	 * 
	 * }
	 * 
	 * } else if (cbstatus.equals("1")) { String branchid = lastbranchid; Branch
	 * branchcash = ((Branch) (LeftmenuService.findOneByProperties(Branch.class,
	 * "branchId", branchid))); String theme =
	 * branchcash.getBranchType().toString(); if (theme.equals("1")) { List<?>
	 * loginnoshiftData = LeftmenuService.getLoginnoshiftData(cashbox); String
	 * noshiftstaff = ((Map<?, ?>)
	 * loginnoshiftData.get(0)).get("LOGINID").toString(); String noshiftlogid =
	 * ((Map<?, ?>) loginnoshiftData.get(0)).get("LOGID").toString(); if
	 * (noshiftstaff.equals(curruser)) { a = "same" + noshiftlogid;
	 * 
	 * } else {
	 * 
	 * a = "other" + noshiftlogid; } }
	 * 
	 * } else if (cbstatus.equals("-1")) { a = "该门店还没有配置金柜"; } else if
	 * (cbstatus.equals("-2")) { a = "超级用户暂不能使用pms功能"; }
	 * JSONUtil.responseJSON(response, new AjaxResult(1, a)); }
	 */
	@RequestMapping("/pettyCash.do")
	public void pettyCash(HttpServletRequest request, HttpServletResponse response, String branchid, String shift,
			String shifterid, String submitstaff, String cashin, String cashout, String givecash, String boxnow,
			String lastshiftvalue, String fixcash, String cardin, String cards, String payday, String depositno,
			String invoiceno, String remark) throws Exception {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String recordUser = staff.getStaffId();
		String logId = this.leftmenuService.getSequence("SEQ_NEW_PETTYCASH", null);
		Date day = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyMMdd");
		String t = df.format(day);
		String logid = t + branchid + logId;
		Double cashIn = Double.parseDouble(cashin);
		Double cashOut = Double.parseDouble(cashout);
		Double paymentvalue = Double.parseDouble(givecash);
		Double shiftvalue = Double.parseDouble(boxnow);
		Double lastshiftValue = Double.parseDouble(lastshiftvalue);
		Double currshiftvalue = Double.parseDouble(fixcash);
		Double cardIn = Double.parseDouble(cardin);
		/* String updatestatus = "0"; */
		PettyCash pettycash = new PettyCash();
		pettycash.setLogId(logid);
		pettycash.setBranchId(branchid);
		pettycash.setShift(shift);
		pettycash.setHandUser(shifterid);
		pettycash.setConfirmUser(submitstaff);
		pettycash.setCashIn(cashIn);
		pettycash.setCashOut(cashOut);
		pettycash.setPaymentValue(paymentvalue);
		pettycash.setShiftValue(shiftvalue);
		pettycash.setLastShiftvalue(lastshiftValue);
		pettycash.setCurrShiftvalue(currshiftvalue);
		pettycash.setCardBalance(cardIn);
		pettycash.setCards(cards);
		pettycash.setDepositNo(depositno);
		pettycash.setInvoiceNo(invoiceno);
		pettycash.setRemark(remark);
		pettycash.setStatus("1");
		pettycash.setState("0");
		pettycash.setRecordTime(new Date());
		pettycash.setRecordUser(shifterid);
		leftmenuService.save(pettycash);
		/*
		 * LeftmenuService.upatecashbox(branchid, boxname, recordUser,
		 * updatestatus);
		 */
		/*
		 * List<?> loginnoshiftData =
		 * LeftmenuService.getLoginnoshiftData(boxname);
		 */
		List<?> newloginnoshiftData = leftmenuService.getNewloginnoshiftData(recordUser, recordUser, branchid);
		String updatelogid = ((Map<?, ?>) newloginnoshiftData.get(0)).get("LOG_ID").toString();
		String noshiftuser = ((Map<?, ?>) newloginnoshiftData.get(0)).get("LOGIN_ID").toString();
		String updatestate = "0";
		String oldstate = "1";
		leftmenuService.updateLoglinlog(updatelogid, noshiftuser, updatestate, oldstate);
		JSONUtil.responseJSON(response, new AjaxResult(1, null));
	}

	@RequestMapping("/forPay.do")
	public ModelAndView forPay(HttpServletRequest request, String newstr) throws Exception {
		ModelAndView mv = new ModelAndView();
		List<PettyCash> listpetty = new ArrayList<PettyCash>();
		JSONArray newstrr = new JSONArray(newstr);
		for (int i = 0; i < newstrr.length(); i++) {
			String s = newstrr.getString(i);
			PettyCash pettydata = (PettyCash) leftmenuService.findOneByProperties(PettyCash.class, "logId", s);
			listpetty.add(pettydata);
		}
		mv.setViewName("page/ipmshotel/leftmenu/pettypay/forpay");
		request.setAttribute("listpetty", listpetty);
		return mv;
	}

	@RequestMapping("/cashUpdate.do")
	public ModelAndView cashUpdate(HttpServletRequest request, HttpServletResponse response, String cashbox,
			String voucher, String state) throws Exception {
		ModelAndView mv = new ModelAndView();
		Pagination pagination = SqlBuilder.buildPagination(request);
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginuser.getStaff().getBranchId();
		Staff staff = loginuser.getStaff();
		String recordUser = staff.getStaffId();
		Staff staffdata = (Staff) leftmenuService.findOneByProperties(Staff.class, "staffId", recordUser);
		String usepost = staffdata.getPost().toString();
		Post post = (Post) leftmenuService.findOneByProperties(Post.class, "postId", usepost);
		String postname = post.getPostName();
		/*
		 * if (branchId.equals(SystemConstants.User.BRANCH_ID)) { branchId =
		 * "%"; }
		 */
		if ((recordUser.equals(SystemConstants.User.ADMIN_ID)) || postname.equals(CommonConstants.Postgrade.STORMANAGE)) {
			recordUser = "%";
		}
		// List<?> pettypaydata =
		// LeftmenuService.getPettypaydata(pagination,branchId,recordUser);
		List<?> allpaydata = leftmenuService.getAllpay(branchId, recordUser);
		List<?> havepaydata = leftmenuService.getHavepay(branchId, recordUser);
		List<?> cashboxkind = leftmenuService.getCashboxkind();
		String allpay = ((Map<?, ?>) allpaydata.get(0)).get("ALLPAY").toString();
		String havepay = ((Map<?, ?>) havepaydata.get(0)).get("HAVEPAY").toString();
		mv.setViewName("/page/ipmshotel/leftmenu/pettypay/pettypay");
		// mv.addObject("pettypaydata", pettypaydata);
		mv.addObject("allpay", allpay);
		mv.addObject("havepay", havepay);
		mv.addObject("recordUser", recordUser);
		mv.addObject("cashboxkind", cashboxkind);
		mv.addObject("cashbox", cashbox);
		mv.addObject("voucher", voucher);
		mv.addObject("state", state);
		mv.addObject("pagination", pagination);
		return mv;
	}

	@RequestMapping("/pettypayData.do")
	public ModelAndView pettypayData(HttpServletRequest request, HttpServletResponse response, String start,
			String end, String voucher, String state) throws Exception {
		ModelAndView mv = new ModelAndView();
		Pagination pagination = SqlBuilder.buildPagination(request);
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginuser.getStaff().getBranchId();
		Staff staff = loginuser.getStaff();
		String recordUser = staff.getStaffId();
		Staff staffdata = (Staff) leftmenuService.findOneByProperties(Staff.class, "staffId", recordUser);
		String usepost = staffdata.getPost().toString();
		Post post = (Post) leftmenuService.findOneByProperties(Post.class, "postId", usepost);
		String postname = post.getPostName();
		/*
		 * if (branchId.equals(SystemConstants.User.BRANCH_ID)) { branchId =
		 * "%"; }
		 */
		if ((recordUser.equals(SystemConstants.User.ADMIN_ID)) || postname.equals(CommonConstants.Postgrade.STORMANAGE)) {
			recordUser = "%";
		}
		List<?> pettypaydata = leftmenuService.getPettypaydata(branchId, recordUser, start, end, voucher, state);
		List<?> allpaydata = leftmenuService.getAllpay(branchId, recordUser);
		List<?> havepaydata = leftmenuService.getHavepay(branchId, recordUser);
		List<?> cashboxkind = leftmenuService.getCashboxkind();
		String allpay = ((Map<?, ?>) allpaydata.get(0)).get("ALLPAY").toString();
		String havepay = ((Map<?, ?>) havepaydata.get(0)).get("HAVEPAY").toString();
		mv.setViewName("/page/ipmshotel/leftmenu/pettypay/pettypaydata");
		mv.addObject("pettypaydata", pettypaydata);
		mv.addObject("allpay", allpay);
		mv.addObject("havepay", havepay);
		mv.addObject("recordUser", recordUser);
		mv.addObject("cashboxkind", cashboxkind);
		mv.addObject("start", start);
		mv.addObject("end", end);
		mv.addObject("voucher", voucher);
		mv.addObject("state", state);
		mv.addObject("pagination", pagination);
		return mv;
	}

	@RequestMapping("/shiftcashData.do")
	public ModelAndView shiftcashData(HttpServletRequest request, HttpServletResponse response, String logintime)
			throws Exception {
		ModelAndView mv = new ModelAndView();
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		// String branchId = loginuser.getStaff().getBranchId();
		Staff staff = loginuser.getStaff();
		String recordUser = staff.getStaffId();
		Staff staffdata = (Staff) leftmenuService.findOneByProperties(Staff.class, "staffId", recordUser);
		String usepost = staffdata.getPost().toString();
		Post post = (Post) leftmenuService.findOneByProperties(Post.class, "postId", usepost);
		String postname = post.getPostName();
		if ((recordUser.equals(SystemConstants.User.ADMIN_ID)) || postname.equals(CommonConstants.Postgrade.STORMANAGE)) {
			recordUser = "%";
		}
		// List<?> shiftdatanew =
		// LeftmenuService.getCashiercashdatanew(branchId, recordUser,
		// logintime);
		// List<?> cashiercardDatanew =
		// LeftmenuService.getCashiercarddatanew(branchid, loginstaff,
		// logintime);
		// List<?> allpaydata = LeftmenuService.getAllpay(branchId, recordUser);
		// List<?> havepaydata = LeftmenuService.getHavepay(branchId,
		// recordUser);
		// List<?> cashboxkind = LeftmenuService.getCashboxkind();
		// String allpay = ((Map<?, ?>)
		// allpaydata.get(0)).get("ALLPAY").toString();
		// String havepay = ((Map<?, ?>)
		// havepaydata.get(0)).get("HAVEPAY").toString();
		mv.setViewName("/page/ipmshotel/leftmenu/pettycash/shiftcash");
		// mv.addObject("allpay", allpay);
		// mv.addObject("havepay", havepay);
		mv.addObject("recordUser", recordUser);
		// mv.addObject("cashboxkind", cashboxkind);
		mv.addObject("logintime", logintime);
		return mv;
	}

	@RequestMapping("/shiftbillData.do")
	public ModelAndView shiftbillData(HttpServletRequest request, HttpServletResponse response, String start,
			String end, String voucher, String state, String paytype) throws Exception {
		ModelAndView mv = new ModelAndView();
		Pagination pagination = SqlBuilder.buildPagination(request);
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginuser.getStaff().getBranchId();
		Staff staff = loginuser.getStaff();
		String recordUser = staff.getStaffId();
		Staff staffdata = (Staff) leftmenuService.findOneByProperties(Staff.class, "staffId", recordUser);
		String usepost = staffdata.getPost().toString();
		Post post = (Post) leftmenuService.findOneByProperties(Post.class, "postId", usepost);
		String postname = post.getPostName();
		if (postname.equals(CommonConstants.Postgrade.STORMANAGE)) {
			recordUser = "%";
		}
		List<?> shiftbilldatanew = leftmenuService.getShiftbilldatanew(pagination, branchId, recordUser, start, end,
				voucher, state, paytype);
		mv.setViewName("/page/ipmshotel/leftmenu/pettycash/shiftbilldata");
		mv.addObject("shiftbilldatanew", shiftbilldatanew);
		mv.addObject("recordUser", recordUser);
		mv.addObject("start", start);
		mv.addObject("end", end);
		mv.addObject("voucher", voucher);
		mv.addObject("state", state);
		mv.addObject("pagination", pagination);
		return mv;
	}

	@RequestMapping("/shiftpayCount.do")
	public void shiftpayCount(HttpServletRequest request, HttpServletResponse response, String days) throws Exception {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginuser.getStaff().getBranchId();
		List<?> shiftpaycount = leftmenuService.getShiftpaycount(branchId, days);
		String cashin = ((Map<?, ?>) shiftpaycount.get(0)).get("CASHIN") == null ? "0" : ((Map<?, ?>) shiftpaycount
				.get(0)).get("CASHIN").toString();
		String cashout = ((Map<?, ?>) shiftpaycount.get(0)).get("CASHOUT") == null ? "0" : ((Map<?, ?>) shiftpaycount
				.get(0)).get("CASHOUT").toString();
		Map<String, String> shiftcashmap = new HashMap<String, String>();
		shiftcashmap.put("cashin", cashin);
		shiftcashmap.put("cashout", cashout);
		JSONUtil.responseJSON(response, shiftcashmap);

	}

	@RequestMapping("/cashShiftpay.do")
	public ModelAndView shiftPay(HttpServletRequest request, HttpServletResponse response, String shiftcashin,
			String shiftcashout) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/page/ipmshotel/leftmenu/pettycash/shiftpay");
		mv.addObject("shiftcashin", shiftcashin);
		mv.addObject("shiftcashout", shiftcashout);
		return mv;
	}

	@RequestMapping("/shiftpayUpdate.do")
	public void shiftpayUpdate(HttpServletRequest request, HttpServletResponse response, String voucherpay,
			String shouldpay, String days) throws Exception {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginuser.getStaff().getBranchId();
		Staff staff = loginuser.getStaff();
		String recordUser = staff.getStaffId();
		List<?> shiftbillupdate = leftmenuService.getShiftbillupdate(branchId, days);
		List<?> shiftwbillupdate = leftmenuService.getShiftwbillupdate(branchId, days);
		String ab = null;
		if (!shiftbillupdate.isEmpty()) {
			for (int i = 0; i < shiftbillupdate.size(); i++) {
				String billid = ((Map<?, ?>) shiftbillupdate.get(i)).get("BILL_ID").toString();
				Bill bill = ((Bill) (leftmenuService.findOneByProperties(Bill.class, "billId", billid)));
				String sv = bill.getShiftVoucher() == null ? "" : bill.getShiftVoucher().toString();
				if (sv.equals("") == false) {
					ab = sv;
				} else if (sv.equals("") == true) {
					bill.setShiftVoucher(voucherpay);
					bill.setRecordUser(recordUser);
					leftmenuService.save(bill);
				}
			}
		}
		String aw = null;
		if (!shiftwbillupdate.isEmpty()) {
			for (int j = 0; j < shiftbillupdate.size(); j++) {
				String detailid = ((Map<?, ?>) shiftwbillupdate.get(j)).get("DETAIL_ID").toString();
				WorkBillDetail Wbill = ((WorkBillDetail) (leftmenuService.findOneByProperties(WorkBillDetail.class,
						"detailId", detailid)));
				String sv = Wbill.getShiftVoucher() == null ? "" : Wbill.getShiftVoucher().toString();
				if (sv.equals("") == false) {
					aw = sv;
				} else if (sv.equals("") == true) {
					Wbill.setShiftVoucher(voucherpay);
					Wbill.setRecordUser(recordUser);
					leftmenuService.save(Wbill);
				}
			}
		}
		String a = null;
		if ((ab == null) && (aw == null)) {
			OperateLog operatelog = new OperateLog();
			String sequences = this.leftmenuService.getSequence("SEQ_OPERATELOG_ID", null);
			SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
			String strdate = sdf.format(new Date());
			InetAddress address = InetAddress.getLocalHost();// 获取的是本地的IP地址
			String operid = address.getHostAddress();// 192.168.0.121
			String logId = strdate + loginuser.getStaff().getBranchId() + sequences;
			String content = loginuser.getStaff().getStaffId() + "操作投缴，金额为：" + shouldpay;
			operatelog.setLogId(logId);
			operatelog.setOperType(SystemConstants.OperType.billPay);
			operatelog.setOperModule("投缴");
			operatelog.setContent(content);
			operatelog.setRecordUser(loginuser.getStaff().getStaffId());
			operatelog.setRecordTime(new Date());
			operatelog.setOperIp(operid);
			operatelog.setBranchId(loginuser.getStaff().getBranchId());
			leftmenuService.save(operatelog);

		} else {
			a = "今日投缴已完成，无需重复投缴";
		}
		JSONUtil.responseJSON(response, new AjaxResult(1, a));

	}

	@RequestMapping("/payUpdate.do")
	public void payUpdate(HttpServletRequest request, HttpServletResponse response, String logid, String cashin,
			String cashout, String paymentvalue, String shiftvalue, String lastshiftvalue, String currshiftvalue,
			String cardbalance, String cards, String depositno, String invoiceno) throws Exception {
		PettyCash pettycash = (PettyCash) leftmenuService.findOneByProperties(PettyCash.class, "logId", logid);
		pettycash.setCashIn(Double.parseDouble(cashin));
		pettycash.setCashOut(Double.parseDouble(cashout));
		pettycash.setPaymentValue(Double.parseDouble(paymentvalue));
		pettycash.setShiftValue(Double.parseDouble(shiftvalue));
		pettycash.setLastShiftvalue(Double.parseDouble(lastshiftvalue));
		pettycash.setCurrShiftvalue(Double.parseDouble(currshiftvalue));
		pettycash.setCardBalance(Double.parseDouble(cardbalance));
		pettycash.setCards(cards);
		pettycash.setDepositNo(depositno);
		pettycash.setInvoiceNo(invoiceno);
		leftmenuService.save(pettycash);
		JSONUtil.responseJSON(response, new AjaxResult(1, null));
	}

	// 左侧手动上传
	@RequestMapping("/updateBySelf.do")
	public void updateBySelf(HttpServletRequest request, HttpServletResponse response) throws ParseException {
		SysParam checkPoint = (SysParam) leftmenuService.findOneByProperties(SysParam.class, "paramType", CommonConstants.SystemParams.CHECK_POINT);
		SysParam secondCheck = (SysParam) leftmenuService.findOneByProperties(SysParam.class, "paramType", "SECONDSCHECKPOINT");
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		Calendar myTimeOne = Calendar.getInstance();
		myTimeOne.setTime(df.parse(secondCheck.getContent().toString()));
		Calendar myTimeTwo = Calendar.getInstance();
		myTimeTwo.setTime(df.parse(checkPoint.getContent().toString()));
		Calendar yesterday = Calendar.getInstance();
		yesterday.add(Calendar.DAY_OF_YEAR,-1);
		Calendar oneminute = Calendar.getInstance();
		oneminute.add(Calendar.MINUTE, -1);
		
/*		if (myTimeOne.getTime().compareTo(yesterday.getTime()) >= 0 && myTimeTwo.getTime().compareTo(oneminute.getTime()) >= 0) {
			JSONUtil.responseJSON(response, new AjaxResult(0, "无需手动上传"));
		} else {*/
			JSONUtil.responseJSON(response, new AjaxResult(1, null));
		//}
	}
	
	@RequestMapping("/sumitUpload.do")
	public void sumitUpload(HttpServletRequest request, HttpServletResponse response) {
		SysParam checkPoint = (SysParam) leftmenuService.findOneByProperties(SysParam.class, "paramType", CommonConstants.SystemParams.CHECK_POINT);
		
//		if (HeartBeatClient.heartbeat) {
			tempPoint = DateUtil.nextDate("yyyy/MM/dd 00:00");
/*			checkPoint = (SysParam) commonService.findOneByProperties(SysParam.class, "paramType",
					CommonConstants.SystemParams.CHECK_POINT);*/

			try {
				int random = (int) (Math.random() * 30) * 1000;
				Thread.sleep(random);

				SysParam sp;
				List<?> data;
				Map<String, List<?>> dts = new HashMap<String, List<?>>();
				DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");

				beans = commonService.findByProperties(SysParam.class, "paramName",
						CommonConstants.SystemParams.TRANSPORT_NAME, "status", "1");

				/* beans = commonService.findBySQL("queryalltrans", true); */

				for (Object obj : beans) {
					sp = (SysParam) obj;
					data = commonService.findTransData(sp.getContent(), sdf.parse(checkPoint.getContent()), sdf
							.parse(tempPoint));

					if (data != null && !data.isEmpty()) {
						dts.put(sp.getContent(), data);
					}

					if (sp.getContent().equals("Contrart")) {
						List<?> list = commonService.findAll(Contrart.class);
						if (list != null && !list.isEmpty()) {
							Contrart contrart;
							for (int i = 0; i < list.size(); i++) {
								contrart = (Contrart) list.get(i);
								String webPath = RequestUtil.getWebPath().replace("\\", "/");
								String result = "";
								if (!"null".equals(contrart.getContrart())
										&& !StringUtil.isEmpty(contrart.getContrart())) {
									if ((new File(webPath + "/upload/" + contrart.getContrart())).exists()) {
										result = RemoteTransUtil.transData(CommonConstants.Path.RECIVEFILE,
												"\\upload\\" + contrart.getContrart(),
												SystemConstants.RemoteTransDataType.MIXED,
												SystemConstants.RemoteTransReturnType.STRING);
									}
								}

								if (SystemConstants.RemoteTransDataResult.FAILED.equals(result)) {
									// logger.error("schedule task [TRANSPORT DATAS] transport data failed...............");
								}
							}
						}
					}

					// logger.debug("[TRANSPORT DATA] query data [" +
					// sp.getContent() + "]!" + " data size [" + data.size()
					// + "]!");
				}

				currPoint = DateUtil.currentDate("yyyy/MM/dd HH:mm");

				String result = null;
				if (!dts.isEmpty()) {
					result = RemoteTransUtil.transData(CommonConstants.Path.RECIVEDATA, dts,
							SystemConstants.RemoteTransDataType.OBJECT, SystemConstants.RemoteTransReturnType.STRING);
				}

				if (SystemConstants.RemoteTransDataResult.FAILED.equals(result)) {
				} else {
					checkPoint.setContent(currPoint);
					commonService.update(checkPoint);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			// logger.info("schedule task [TRANSPORT DATA] finish...............");
			JSONUtil.responseJSON(response, new AjaxResult(1, null));
//		}
	}
	@SuppressWarnings("unchecked")
	@RequestMapping("/OrderdiscountPrice.do")
	public void OrderdiscountPrice(HttpServletRequest request, HttpServletResponse response) throws ParseException{
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchid = loginUser.getStaff().getBranchId();
		String roomtype = request.getParameter("roomtype");
		String corporationIdSelect = request.getParameter("corporationIdSelect");
		String arrivedate = request.getParameter("arrivedate");
		//云端查询
		com.ideassoft.portal.DataDealPortalService service = new com.ideassoft.portal.DataDealPortalService();
		com.ideassoft.portal.IDataDealPortal portal = service.getDataDealPortalPort();
		String discountprice = null;
		String newprice = "";
		String discount = portal.call(SystemConstants.EnginType.COMMON_DATA, 
				SystemConstants.Movement.CUSTOM_QUERY, "{ function: \"checkInDirect.queryDiscountPrice\", data:{corporationIdSelect:" + corporationIdSelect  +" } }");//查出公司会员id价格和折扣
		if(!(discount.equals(Integer.toString(SystemConstants.PortalResultCode.NULL))) && !(discount.equals(Integer.toString(SystemConstants.PortalResultCode.FAILED)))){
			discountprice = discount.substring(0,discount.indexOf(":"));
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		List<List<?>> price = new ArrayList<List<?>>();
		if(sdf.parse(arrivedate).equals(sdf.parse(sdf.format(new Date())))){
			price = RoomUtil.getNowRoomPrice(branchid, CommonConstants.DefaultRoomPrice.DEFAULT_RP_ID, roomtype);
		}else{
			price = RoomUtil.getForwardRoomPrice(branchid, arrivedate, CommonConstants.DefaultRoomPrice.DEFAULT_RP_ID, roomtype);
		} 
		if ((((Map<?, ?>) price.get(0).get(0)).get("ADJUSTMEM") != null && 
				!((Map<?, ?>) price.get(0).get(0)).get("ADJUSTMEM").equals(0) &&
				!((Map<?, ?>) price.get(0).get(0)).get("ADJUSTMEM").equals(0.0) &&
				!((Map<?, ?>) price.get(0).get(0)).get("ADJUSTMEM").equals(0.00))) {
			newprice = ((Map) price.get(0).get(0)).get("ADJUSTMEM").toString();
		} else {
			price = RoomUtil.getNowRoomPrice(branchid, CommonConstants.DefaultRoomPrice.DEFAULT_RP_ID, roomtype);
			newprice = ((Map) price.get(0).get(0)).get("ROOMPRICE").toString();
		}
		double price1 = Double.parseDouble(newprice)*(Double.parseDouble(discountprice)/100);
		DecimalFormat    df   = new DecimalFormat("######0.00");
		String price3 = df.format(price1);
		JSONUtil.responseJSON(response, new AjaxResult(1,"",price3+":"+discount.substring(discount.indexOf(":")+1)));
	}
	
	//检测Cloud连接状态
	@RequestMapping("/leftmenucheckConnection.do")
	public void checkConnection(HttpServletRequest request,HttpServletResponse response){
		
		Boolean ConnectionFlag = null;
//		if(HeartBeatClient.heartbeat){
			ConnectionFlag = false;
//		}else{
//			ConnectionFlag = true;
//		}
		JSONUtil.responseJSON(response, new AjaxResult(1,"",ConnectionFlag));
	}
	
	/**
	 * 查询会员信息
	 */
	@RequestMapping("/leftquerymember.do")
	public void querymember(HttpServletRequest request,HttpServletResponse response, String phone){
		
//		if(HeartBeatClient.heartbeat){
			DataDealPortalService service = new DataDealPortalService();
			IDataDealPortal portal = service.getDataDealPortalPort();
			String member = portal.call(SystemConstants.EnginType.COMMON_DATA, SystemConstants.Movement.CUSTOM_QUERY,"{ function: \"checkInDirect.getMemberData\",data:{ phone:"+ phone + "}}");
//			mv.setViewName("/page/ipmspage/leftmenu/repair/memberUpdate");
			if((!member.equals(Integer.toString(SystemConstants.PortalResultCode.FAILED))) && (!member.equals(Integer.toString(SystemConstants.PortalResultCode.NULL)))){
				JSONArray array = null;
				JSONObject obj = null;
				Map<String,Object> data = new HashMap<String,Object>();
				try {
					array = new JSONArray(member);
					for (int i = 0; i < array.length(); i++) {
						obj = array.getJSONObject(i);
						data.put("name", obj.get("MEMBER_NAME"));
						data.put("memberid", obj.get("MEMBER_ID"));
						data.put("rank", obj.get("MEMBER_RANK"));
						data.put("gender", obj.isNull("GENDOR") ? "" : obj.get("GENDOR"));
						data.put("mobile", obj.isNull("MOBILE") ? "" : obj.get("MOBILE"));
						data.put("idcard", obj.isNull("IDCARD") ? "" : obj.get("IDCARD"));
						data.put("remark", obj.isNull("REMARK") ? "" : obj.get("REMARK"));
						data.put("rankname", obj.get("RANKNAME"));
						data.put("status", obj.get("STATUS"));
					}
					JSONUtil.responseJSON(response, JSONUtil.fromObject(data));
				} catch (JSONException e) {
					e.printStackTrace();					
				}
			}
			
//		}else{
//			JSONUtil.responseJSON(response, new AjaxResult(1,"网络未连接,请稍后再试!"));
//		}
	}
	
	/**
	 * 依据房型刷房号
	 */
	@RequestMapping("/leftmenuqueryRoomId.do")
	public void queryRoomId(HttpServletRequest request,
			HttpServletResponse response, String roomtype) {

		LoginUser loginUser = (LoginUser) request.getSession(true)
				.getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchid = loginUser.getStaff().getBranchId();
		List<?> roomids = leftmenuService.findBySQL("queryRoomIdByType",
				new String[] { branchid, roomtype }, true);
		if (roomids == null || roomids.size() == 0) {
			JSONUtil.responseJSON(response, new AjaxResult(1, "该房型下暂无可入住房间!"));
		} else {
			JSONUtil.responseJSON(response, roomids);
		}
	}
	
	/**
	 * 断网直接入住
	 * @param request
	 * @param response
	 * @param checkUser 预订人
	 * @param roomType 房型
	 * @param roomId 房号
	 * @param checkoutTime 预离时间
	 * @param rpId 房价码啊
	 * @param roomPrice
	 * @param deposit 押金
	 * @param remark 预定备注
	 * @param guestname 客人姓名,如:张三41,张三11,
	 * @param gender 性别,如: 男,女
	 * @param phone 手机号码,如10086,10010,非空
	 * @param idcardnumber 身份证号码
	 * @param cuRemark 客人备注信息
	 * @param memberid 客人会员编号,如10000001,12345678
	 * @param hourRoomFlag true为钟点房,false全天房
	 */
	@RequestMapping("/leftmenuCheckInDirectWithNoConnection.do")
	public void CheckInDirectWithNoConnection(HttpServletRequest request,HttpServletResponse response,
			String checkUser,String roomType,String roomId,String checkoutTime,String rpId,String roomPrice,
			String deposit,String remark,String guestname,String guestnameAll,String gender,String genderAll,
			String phone,String phoneAll,String idcardnumber,String idcardnumberAll,String cuRemark,String rankAll,
			String memberid,String rank,boolean hourRoomFlag) {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		//先注册非会员		
		String[] newguestname = guestname.substring(0,guestname.length() - 1).split(",");
		String[] newgender = gender.substring(0, gender.length() - 1).split(",");
		String[] newphone = phone.substring(0, phone.length() - 1).split(",");
		String[] newidcardnumber = idcardnumber.substring(0,idcardnumber.length() - 1).split(",");
		String[] newcuRemark = cuRemark.substring(0, cuRemark.length() - 1).split(",");
		String[] newrank = rank.substring(0, rank.length() - 1).split(",");
		String[] newmemberId = memberid.substring(0,memberid.length()-1).split(",");
		String newmemberid = "";
		for (int i = 0; i < newrank.length; i++) {
			//临时会员
			if("1".equals(newrank[i])){
				Member mb = new Member();
				MemberAccount account = new MemberAccount();
				//门店后3位+4位序列
				String mbId = branchId.substring(branchId.length()-3)+leftmenuService.getSequence("SE_MEMBERS_ID");
				String accountId = branchId.substring(branchId.length()-3)+leftmenuService.getSequence("SE_ACCOUNTS_ID");
				mb.setMemberId(mbId);
				mb.setMemberName(newguestname[i]);
				mb.setLoginName("guest");
				//身份证后6位为会员密码
				mb.setPassword(MD5Util.getCryptogram(newidcardnumber[i].substring(newidcardnumber[i].length()-6)));
				mb.setMemberRank(CommonConstants.MembenRank.NM);
				mb.setIdcard(newidcardnumber[i]);
				mb.setGendor(newgender[i] == "男" ? "1" : "0");
				mb.setMobile(newphone[i]);
				mb.setSource(CommonConstants.MemberSource.OFFICAIL_WEB);
				mb.setValidTime(new Date());
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.YEAR, 1);
				mb.setInvalidTime(calendar.getTime());
				mb.setRegisterTime(new Date());
				mb.setStatus("1");
				account.setAccountId(accountId);
				account.setMemberId(mbId);
				account.setCurrBalance((double) 0);
				account.setCurrIntegration((long) 0);
				account.setTotalRecharge((double) 0);
				account.setDiscountGift((double) 0);
				account.setChargeGift((double) 0);
				account.setTotalConsume((double) 0);
				account.setTotalIntegration((long) 0);
				account.setIngegrationGift((long) 0);
				account.setTotalConsIntegration((long) 0);
				account.setTotalRoomnights(0);
				account.setCurrRoomnights(0);
				account.setTotalNoshow((short) 0);
				account.setCurrNoshow((short) 0);
				account.setThisYearIntegration((long) 0);
				account.setRecordTime(new Date());
				account.setStatus("1");
				leftmenuService.save(mb);
				leftmenuService.save(account);
				leftmenuService.getSession().flush();
				//会员账户表同步手动到云端
				SysParam systemtype = (SysParam) leftmenuService.findOneByProperties(SysParam.class, "paramType","SYSTEMTYPE", "status", "1");
				String stype = systemtype.getContent().toString();
				SysParam param = (SysParam) leftmenuService.findOneByProperties(SysParam.class, "paramType","REMOTE_PATH", "status", "1");				
				String remoteIp = param.getContent().toString();
				int priority = 1;
				if(CommonConstants.SystemType.Branch.equals(stype.trim())){
					List<Object> list = new ArrayList<Object>();
					list.add(account);
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("MemberAccount", list);
					// com.ideassoft.crm.service.TransferServcie.getInstance().transferData(priority, remoteIp, JSONUtil.fromObject(map).toString());
				}
			}
			//更新memberId字符串,替换其中的""为新增注册的memberId
			if("".equals(newmemberId[i].trim())){
				Member m = (Member) leftmenuService.findOneByProperties(Member.class, "idcard", newidcardnumber[i]);
				newmemberId[i] = m.getMemberId();
			}
			
			newmemberid += newmemberId[i]+",";
			
		}
		
		//生成订单表和房单表,账单表,入住人表
		Check check = new Check();
		Bill bill = new Bill();
		Order order = new Order();
		OrdchePrice op = new OrdchePrice();
		String opId = DateUtil.currentDate("yyMMdd") + branchId+CommonConstants.OrderSource.Branch 
		+leftmenuService.getSequence("SEQ_PRICE_DATAID");
		String orderId = DateUtil.currentDate("yyMMdd") + branchId + CommonConstants.OrderSource.Branch
		+ leftmenuService.getSequence("SEQ_NEW_ORDER");
		order.setOrderId(orderId);
		order.setBranchId(branchId);
		order.setSource(CommonConstants.OrderSource.Branch);// 订单来源为分店
		order.setTheme("1");
		order.setRoomId(roomId);
		order.setRoomType(roomType);
		order.setmPhone(phone.substring(0, phone.indexOf(",")));// zhu客人信息
		order.setRpId(rpId);
		order.setArrivalTime(new Date());
		Calendar calendar = Calendar.getInstance();
		if (!hourRoomFlag) {// 全天房
			calendar.setTime(DateUtil.s2d(checkoutTime, "yy/MM/dd"));
			calendar.set(Calendar.HOUR_OF_DAY, 12);
		} else {// 钟点房
			calendar.setTime(DateUtil.s2d(checkoutTime, "yy/MM/dd HH:mm"));
		}
		order.setLeaveTime(calendar.getTime());
		order.setCheckoutTime(calendar.getTime());
		order.setRoomPrice(Double.valueOf(roomPrice));
		order.setGuarantee("1");
		order.setLimited("0:00");
		// 预订人以主客人memberid为准
		Member m = (Member) leftmenuService.findOneByProperties(Member.class, "idcard", newidcardnumber[0]);
		order.setOrderUser(newmemberId[0].trim() == "" ? m.getMemberId() : newmemberId[0].trim());
//取消	order.setUserCheckin(newmemberid.substring(0,newmemberid.length()-1));
		order.setStatus("3");// 订单为在住状态,产生房单
		order.setRecordUser(loginUser.getStaff().getStaffId());
		//查预订人当前门店的门市价
		List MSJ = null;
		if(hourRoomFlag){
			 MSJ = leftmenuService.findBySQL("getMSJ", new String[]{"1",branchId,roomType,"1","1",branchId,roomType,"1"}, true);
			
		}else{
			 MSJ = leftmenuService.findBySQL("getMSJ", new String[]{"1",branchId,roomType,"2","1",branchId,roomType,"2"}, true);

		}
		if(MSJ != null && MSJ.size()>0){
			order.setMsj(Double.valueOf(((Map) (MSJ.get(0))).get("ROOM_PRICE").toString()));
			check.setMsj(Double.valueOf(((Map) (MSJ.get(0))).get("ROOM_PRICE").toString()));
		}else{
			JSONUtil.responseJSON(response, new AjaxResult(1, "请先配置门市价等相关房价信息"));
			return;
		}
		check.setCheckId(orderId);// 和订单的订单号一致
		check.setBranchId(branchId);
		check.setRoomId(roomId);
		check.setRoomType(roomType);
		check.setRpId(rpId);
		check.setRoomPrice(Double.valueOf(roomPrice));// 房价
		
		check.setCheckUser(newmemberid.substring(0,newmemberid.length()-1));// 入住人以主客人为准
		check.setCheckinTime(new Date());
		check.setCheckoutTime(calendar.getTime());
		if (deposit == "") {
			deposit = "0";
		}
		check.setDeposit(Double.valueOf(deposit));// 押金
		check.setStatus("1");// 在住
		check.setRecordTime(new Date());
		check.setRecordUser(loginUser.getStaff().getStaffId());
		check.setRemark(remark);//入住备注
		String billId = DateUtil.currentDate("yyMMdd") + branchId
				+ leftmenuService.getSequence("SEQ_NEW_BILL");
		bill.setBillId(billId);
		bill.setBranchId(branchId);
		bill.setCheckId(orderId);
		bill.setProjectId(CommonConstants.BillProject.Deposit);
		bill.setProjectName("预存");
		bill.setDescribe("预存");
		bill.setCost(0.0);
		bill.setPay(0.0);
		bill.setPayment("0");
		bill.setStatus("1");
		bill.setRecordTime(new Date());
		bill.setRecordUser(loginUser.getStaff().getStaffId());
		//房价刷至tb_p_ordercheckprice表
		op.setDataId(opId);
		op.setBranchId(branchId);
		op.setOrderId(orderId);
		op.setWhichDay(new Date());
		op.setDayPrice(Double.valueOf(roomPrice));
		op.setStatus("1");
		op.setRecordUser(loginUser.getStaff().getStaffId());
		op.setRecordTime(new Date());
		//更新每一个入住人
		String[] newguestnameAll = guestnameAll.substring(0,guestnameAll.length()-1).split(",");
		String[] newgenderAll = genderAll.substring(0,genderAll.length()-1).split(",");
		String[] newphoneAll = phoneAll.substring(0,phoneAll.length()-1).split(",");
		String[] newidcardnumberAll = idcardnumberAll.substring(0,idcardnumberAll.length()-1).split(",");
		String[] newrankAll = rankAll.substring(0,rankAll.length()-1).split(",");
		for (int j = 0; j < newidcardnumberAll.length; j++) {
			CheckUser cu = new CheckUser();// 入住人
			cu.setCheckuserId(branchId+ CommonConstants.OrderSource.Branch + leftmenuService.getSequence("SEQ_NEW_CHECKUSER"));// 入住人表主键门店+来源+序列号
			if(newidcardnumberAll[j].trim().endsWith("unabled")){//已取消,无效客人
				cu.setCheckuserName(newguestnameAll[j].substring(0,newguestnameAll[j].indexOf("unabled")));// 入住人姓名
				cu.setIdcard(newidcardnumberAll[j].substring(0, newidcardnumberAll[j].indexOf("unabled")));
//				String phoneRegex = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
				String phoneRegex = "^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\\d{8})$"; 
				String data = newphoneAll[j].substring(0, newphoneAll[j].indexOf("unabled"));
				if(data.trim().matches(phoneRegex)){
					cu.setMobile(data);
				}
				if (newgenderAll[j] == "男") {
					cu.setGender("1");
				} else {
					cu.setGender("0");
				}
				cu.setStatus("0");
				if(newrankAll[j].trim() == ""){
					cu.setRankName("1");
				}else{
					cu.setRankName(newrankAll[j].substring(0,newrankAll[j].indexOf("unabled")));
					
				}
			}else{
				cu.setCheckuserName(newguestnameAll[j]);// 入住人姓名
				cu.setIdcard(newidcardnumberAll[j]);
				String phoneRegex = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
				if(newphoneAll[j].trim().matches(phoneRegex)){
					cu.setMobile(newphoneAll[j]);
				}
				if (newgenderAll[j] == "男") {
					cu.setGender("1");
				} else {
					cu.setGender("0");
				}
				cu.setStatus("1");
				cu.setRankName(newrankAll[j]);
			}
			cu.setRecordUser(loginUser.getStaff().getStaffId());
			cu.setCheckId(orderId);
			cu.setCheckinType(CommonConstants.CheckinType.CHECK);
			cu.setRankName(newrankAll[j]);
			if (j == 0) {
				cu.setCheckuserType(CommonConstants.CheckUserType.HOST);// 主客人
			} else {
				cu.setCheckuserType(CommonConstants.CheckUserType.GUEST);// 从客人
			}
			leftmenuService.save(cu);
			
		}
		leftmenuService.save(order);
		leftmenuService.save(check);
		leftmenuService.save(bill);	
		leftmenuService.save(op);
		//更新入住房间状态在住
		Room room = (Room) leftmenuService.findOneByProperties(Room.class, "roomKey.branchId", branchId, "roomKey.roomId",roomId,"roomType",roomType,"theme","1");
		room.setStatus("3");
		leftmenuService.update(room);
		//更新roomstatus房间数量
		RoomStatus rs = (RoomStatus) leftmenuService.findOneByProperties(RoomStatus.class, "branchId", branchId, "roomType",roomType);
		rs.setCount(rs.getCount()-1);
		rs.setSellnum(rs.getSellnum()-1);
		leftmenuService.update(rs);
		JSONUtil.responseJSON(response, new AjaxResult(2,"入住成功!"));
	}
	
	/**
	 * 联网直接入住
	 * @param request
	 * @param response
	 * @param checkUser 预订人
	 * @param roomType 房型
	 * @param roomId 房号
	 * @param checkoutTime 预离时间
	 * @param rpId 房价码啊
	 * @param roomPrice
	 * @param deposit 押金
	 * @param remark 预定备注
	 * @param guestname 客人姓名,如:张三41,张三11,
	 * @param gender 性别,如: 男,女
	 * @param phone 手机号码,如10086,10010,非空
	 * @param idcardnumber 身份证号码
	 * @param cuRemark 客人备注信息
	 * @param memberid 客人会员编号,如10000001,12345678
	 * @param rank 客人会员等级  要区分非会员是否为会员,要判断云端是否有该会员,然后是否注册
	 * @param hourRoomFlag true为钟点房,false全天房
	 * @throws ParseException 
	 */
	@RequestMapping("/leftmenuCheckInDirectWithConnection.do")
	public void CheckInDirectWithConnection(HttpServletRequest request,HttpServletResponse response,
			String checkUser,String roomType,String roomId,String checkoutTime,String rpId,String roomPrice,
			String deposit,String remark,String guestname,String guestnameAll,String gender,String genderAll,
			String phone,String phoneAll,String idcardnumber,String idcardnumberAll,String cuRemark,String rankAll,
			String memberid,String rank,boolean hourRoomFlag,String discountPerson, String companyOrMemberSelect) throws ParseException {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		//先注册非会员
		String[] newguestname = guestname.substring(0,guestname.length() - 1).split(",");
		String[] newgender = gender.substring(0, gender.length() - 1).split(",");
		String[] newphone = phone.substring(0, phone.length() - 1).split(",");
		String[] newidcardnumber = idcardnumber.substring(0,idcardnumber.length() - 1).split(",");
		String[] newcuRemark = cuRemark.substring(0, cuRemark.length() - 1).split(",");
		String[] newrank = rank.substring(0, rank.length() - 1).split(",");
		//String[] newmemberId = memberid.substring(0,memberid.length()-1).split(",");
		String newmemberid = "";
		/*for (int i = 0; i < newmemberId.length; i++) {
			//非会员则获取云端主键本地注册
			if("".equals(newmemberId[i].trim())  &&  isMember(newphone[i]).equals(CommonConstants.PortalResultCode.NULL.toString()) ){
				Member mb = new Member();
				MemberAccount account = new MemberAccount();
				//云端获取主键
				String mbId = leftmenuService.getCloudSequence("SEQ_MEMBER_ID");
				String accountId = leftmenuService.getCloudSequence("SEQ_ACCOUNT_ID");
				mb.setMemberId(mbId);
				mb.setMemberName(newguestname[i]);
				mb.setLoginName("guest");
				//身份证后6位为会员密码
				mb.setPassword(MD5Util.getCryptogram(newidcardnumber[i].substring(newidcardnumber[i].length()-6)));
				mb.setMemberRank("1");
				mb.setIdcard(newidcardnumber[i]);
				mb.setGendor(newgender[i] == "男" ? "1" : "0");
				mb.setMobile(newphone[i]);
				mb.setSource(CommonConstants.MemberSource.OFFICAIL_WEB);
				mb.setValidTime(new Date());
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.YEAR, 1);
				mb.setInvalidTime(calendar.getTime());
				mb.setRegisterTime(new Date());
				mb.setStatus("1");
				account.setAccountId(accountId);
				account.setMemberId(mbId);
				account.setCurrBalance((double) 0);
				account.setCurrIntegration((long) 0);
				account.setTotalRecharge((double) 0);
				account.setDiscountGift((double) 0);
				account.setChargeGift((double) 0);
				account.setTotalConsume((double) 0);
				account.setTotalIntegration((long) 0);
				account.setIngegrationGift((long) 0);
				account.setTotalConsIntegration((long) 0);
				account.setTotalRoomnights(0);
				account.setCurrRoomnights(0);
				account.setTotalNoshow((short) 0);
				account.setCurrNoshow((short) 0);
				account.setThisYearIntegration((long) 0);
				account.setRecordTime(new Date());
				account.setStatus("1");
				leftmenuService.save(mb);
				leftmenuService.save(account);
				leftmenuService.getSession().flush();
				//会员账户表同步手动到云端
				SysParam systemtype = (SysParam) leftmenuService.findOneByProperties(SysParam.class, "paramType","SYSTEMTYPE", "status", "1");
				String stype = systemtype.getContent().toString();
				SysParam param = (SysParam) leftmenuService.findOneByProperties(SysParam.class, "paramType","REMOTE_PATH", "status", "1");				
				String remoteIp = param.getContent().toString();
				int priority = 1;
				if(CommonConstants.SystemType.Branch.equals(stype.trim())){
					List<Object> list = new ArrayList<Object>();
					list.add(account);
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("MemberAccount", list);
					com.ideassoft.crm.service.TransferServcie.getInstance().transferData(priority, remoteIp, JSONUtil.fromObject(map).toString());
				}	
				
			}
//			else if(isMember(newphone[i]).equals(CommonConstants.PortalResultCode.EXIST.toString())){
//				JSONUtil.responseJSON(response, new AjaxResult(3,"手机号:"+newphone[i]+"已注册,请先更新"));
//				return;
//			}
			
			//更新memberId字符串,替换其中的""为新增注册的memberId
			if("".equals(newmemberId[i].trim())){
				Member m = (Member) leftmenuService.findOneByProperties(Member.class, "idcard", newidcardnumber[i]);
				newmemberId[i] = m.getMemberId();
			}
			
			newmemberid += newmemberId[i]+",";
			
		}*/
		
		//根据录入的companyOrMemberSelect来判断是手机号还是公司会员编号
		Member getPhonebymem = (Member) leftmenuService.findOneByProperties(Member.class, "corporationId", companyOrMemberSelect);
		if(getPhonebymem == null){
			getPhonebymem = (Member) leftmenuService.findOneByProperties(Member.class, "mobile", companyOrMemberSelect);
		}
		
		//生成订单表和房单表,账单表,入住人表,订单房单房价表
		Check check = new Check();
		Bill bill = new Bill();
		Order order = new Order();
		OrdchePrice op = new OrdchePrice();
		String orderId = DateUtil.currentDate("yyMMdd") + branchId + CommonConstants.OrderSource.Branch
		+ leftmenuService.getSequence("SEQ_NEW_ORDER");
		String opId = DateUtil.currentDate("yyMMdd") + branchId+CommonConstants.OrderSource.Branch
		+leftmenuService.getSequence("SEQ_PRICE_DATAID");
		order.setOrderId(orderId);
		order.setBranchId(branchId);
		order.setSource(CommonConstants.OrderSource.Branch);// 订单来源为直接入住
		order.setTheme("1");
		order.setRoomId(roomId);
		order.setRoomType(roomType);
		order.setmPhone(getPhonebymem.getMobile());// zhu客人信息
		order.setRpId(rpId);
		order.setArrivalTime(new Date());
		Calendar calendar = Calendar.getInstance();
		if (!hourRoomFlag) {// 全天房
			calendar.setTime(DateUtil.s2d(checkoutTime, "yy/MM/dd"));
			calendar.set(Calendar.HOUR_OF_DAY, 12);
		} else {// 钟点房
			calendar.setTime(DateUtil.s2d(checkoutTime, "yy/MM/dd HH:mm"));
		}
		order.setLeaveTime(calendar.getTime());
		order.setCheckoutTime(calendar.getTime());
		order.setRoomPrice(Double.valueOf(roomPrice));
		order.setGuarantee("1");
		order.setLimited("0:00");
		// 预订人以主客人memberid为准
		Member m = (Member) leftmenuService.findOneByProperties(Member.class, "idcard", newidcardnumber[0]);
		order.setOrderUser(discountPerson); //(discountPerson == "" || discountPerson == null) ? (newmemberId[0].trim() == "" ? m.getMemberId() : newmemberId[0].trim()) : discountPerson
//取消	order.setUserCheckin(newmemberid.substring(0,newmemberid.length()-1));
		order.setStatus("3");// 订单为在住状态,产生房单
		order.setRecordUser(loginUser.getStaff().getStaffId());
		//查预订人当前门店的门市价
		List MSJ = null;
		if(hourRoomFlag){
			 MSJ = leftmenuService.findBySQL("getMSJ", new String[]{"1",branchId,roomType,"1","1",branchId,roomType,"1"}, true);
			
		}else{
			 MSJ = leftmenuService.findBySQL("getMSJ", new String[]{"1",branchId,roomType,"2","1",branchId,roomType,"2"}, true);

		}
		if(MSJ != null && MSJ.size()>0){
			order.setMsj(Double.valueOf(((Map) (MSJ.get(0))).get("ROOM_PRICE").toString()));
			check.setMsj(Double.valueOf(((Map) (MSJ.get(0))).get("ROOM_PRICE").toString()));
		}else{
			JSONUtil.responseJSON(response, new AjaxResult(1, "请先配置门市价等相关房价信息"));
			return;
		}
		check.setCheckId(orderId);// 和订单的订单号一致
		check.setBranchId(branchId);
		check.setRoomId(roomId);
		check.setRoomType(roomType);
		check.setRpId(rpId);
		check.setRoomPrice(Double.valueOf(roomPrice));// 房价
		
		//check.setCheckUser(newmemberid.substring(0,newmemberid.length()-1));// 入住人以主客人为准
		check.setCheckinTime(new Date());
		check.setCheckoutTime(calendar.getTime());
		if (deposit == "") {
			deposit = "0";
		}
		check.setDeposit(Double.valueOf(deposit));// 押金
		check.setStatus("1");// 在住
		check.setRecordTime(new Date());
		check.setRecordUser(loginUser.getStaff().getStaffId());
		check.setRemark(remark);//入住备注
		String billId = DateUtil.currentDate("yyMMdd") + branchId
				+ leftmenuService.getSequence("SEQ_NEW_BILL");
		bill.setBillId(billId);
		bill.setBranchId(branchId);
		bill.setCheckId(orderId);
		bill.setProjectId(CommonConstants.BillProject.Deposit);
		bill.setProjectName("预存");
		bill.setDescribe("预存");
		bill.setCost(0.0);
		bill.setPay(0.0);
		bill.setPayment("0");
		bill.setStatus("1");
		bill.setRecordTime(new Date());
		bill.setRecordUser(loginUser.getStaff().getStaffId());
		//房价刷至tb_p_ordercheckprice表
/*		op.setDataId(opId);
		op.setBranchId(branchId);
		op.setOrderId(orderId);
		op.setWhichDay(new Date());
		op.setDayPrice(Double.valueOf(roomPrice));
		op.setStatus("1");
		op.setRecordUser(loginUser.getStaff().getStaffId());
		op.setRecordTime(new Date());*/
		//更新每一个入住人
		String[] newguestnameAll = guestnameAll.substring(0,guestnameAll.length()-1).split(",");
		String[] newgenderAll = genderAll.substring(0,genderAll.length()-1).split(",");
		String[] newphoneAll = phoneAll.substring(0,phoneAll.length()-1).split(",");
		String[] newidcardnumberAll = idcardnumberAll.substring(0,idcardnumberAll.length()-1).split(",");
		String[] newrankAll = rankAll.substring(0,rankAll.length()-1).split(",");
		for (int j = 0; j < newidcardnumberAll.length; j++) {
			CheckUser cu = new CheckUser();// 入住人
			cu.setCheckuserId(branchId+ CommonConstants.OrderSource.Branch+ leftmenuService.getSequence("SEQ_NEW_CHECKUSER"));// 入住人表主键门店+来源+序列号
			if(newidcardnumberAll[j].trim().endsWith("unabled")){//已取消,无效客人
				cu.setCheckuserName(newguestnameAll[j].substring(0,newguestnameAll[j].indexOf("unabled")));// 入住人姓名
				cu.setIdcard(newidcardnumberAll[j].substring(0, newidcardnumberAll[j].indexOf("unabled")));
//				String phoneRegex = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
				String phoneRegex = "^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\\d{8})$"; 
				String data = newphoneAll[j].substring(0, newphoneAll[j].indexOf("unabled"));
				if(data.trim().matches(phoneRegex)){
					cu.setMobile(data);
				}
				if (newgenderAll[j] == "男") {
					cu.setGender("1");
				} else {
					cu.setGender("0");
				}
				cu.setStatus("0");
				cu.setRankName(newrankAll[j].substring(0,newrankAll[j].indexOf("unabled")));
			}else{
				cu.setCheckuserName(newguestnameAll[j].trim());// 入住人姓名
				cu.setIdcard(newidcardnumberAll[j]);
				String phoneRegex = "^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\\d{8})$";
				if(newphoneAll[j].trim().matches(phoneRegex)){
					String a = newphoneAll[j];
					cu.setMobile(newphoneAll[j].trim());
				}
				if (newgenderAll[j] == "男") {
					cu.setGender("1");
				} else {
					cu.setGender("0");
				}
				cu.setStatus("1");
				cu.setRankName(newrankAll[j]);
			}
			cu.setCheckinType(CommonConstants.CheckinType.CHECK);
			cu.setRecordUser(loginUser.getStaff().getStaffId());
			cu.setCheckId(orderId);
			if (j == 0) {
				cu.setCheckuserType(CommonConstants.CheckUserType.HOST);// 主客人
			} else {
				cu.setCheckuserType(CommonConstants.CheckUserType.GUEST);// 从客人
			}
			leftmenuService.save(cu);
			
		}
		
		leftmenuService.save(order);
		leftmenuService.getSession().flush();
		leftmenuService.save(check);
		leftmenuService.save(bill);
		//leftmenuService.save(op);
		//更新入住房间状态在住
		Room room = (Room) leftmenuService.findOneByProperties(Room.class, "roomKey.branchId", branchId, "roomKey.roomId",roomId,"roomType",roomType,"theme","1");
		room.setStatus("3");
		leftmenuService.update(room);
		//更新roomstatus房间数量
		RoomStatus rs = (RoomStatus) leftmenuService.findOneByProperties(RoomStatus.class, "branchId", branchId, "roomType",roomType);
		rs.setCount(rs.getCount()-1);
		rs.setSellnum(rs.getSellnum()-1);
		leftmenuService.update(rs);
		//saveEveryDayPrice(request, response, orderId, roomType);
		if (!hourRoomFlag) {// 全天房
			saveEveryDayPrice(request, response, orderId, roomType);
		} 
		JSONUtil.responseJSON(response, new AjaxResult(2,"入住成功!"));
	
	}
	
	/**
	 * 获取实时房价刷到页面
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/leftmenushowRoomPriceDirectLive.do")
	public void showRoomPriceDirectLive(HttpServletRequest request,HttpServletResponse response, String roomtype, String idCard, boolean hourRoomFlag, boolean firsttime){
		Map<String, Object> formalmap = new HashMap<String, Object>();
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchid = loginUser.getStaff().getBranchId();
//		if(HeartBeatClient.heartbeat){
			com.ideassoft.portal.DataDealPortalService service = new com.ideassoft.portal.DataDealPortalService();
			com.ideassoft.portal.IDataDealPortal portal = service.getDataDealPortalPort();
			String member = portal.call(SystemConstants.EnginType.COMMON_DATA, 
					SystemConstants.Movement.CUSTOM_QUERY, "{ function: \"checkInDirect.getMemberBeanDirectLive\", data:{idnumber:" + idCard  +" } }");//查出会员id,级别,级别名称
	
			if((member.equals(Integer.toString(SystemConstants.PortalResultCode.NULL)))){
				//云端无此会员默认注册临时会员	
				JSONUtil.responseJSON(response, new AjaxResult(1,"未查询到相关会员信息或该会员已失效，请先注册会员！"));	
			} else if(!(member.equals(Integer.toString(SystemConstants.PortalResultCode.NULL))) && !(member.equals(Integer.toString(SystemConstants.PortalResultCode.FAILED)))){
				//云端有会员信息,saveorupdate到本地
				org.json.JSONObject m;
				org.json.JSONObject ma;
				List<Member> memberSdata = new ArrayList<Member>();
				List<?> ordermemberdata = new ArrayList<Object>();
				
				try {
					List<Object> ms = ReflectUtil.setBeans(member, "Member");
					//保存or更新到本地
					leftmenuService.saveOrUpdateAll(ms);
					for(Object mem : ms){
						Member mss = (Member)mem;
						String memberId = mss.getMemberId();
						if(!StringUtil.isEmpty(memberId) && memberId != null){
							String memberAccount = portal.call(SystemConstants.EnginType.COMMON_DATA, 
									SystemConstants.Movement.CUSTOM_QUERY, "{ function: \"checkInDirect.getMemberAccountInDirectLive\", data:{idnumber:" + memberId  +" } }");
							if(!(memberAccount.equals(Integer.toString(SystemConstants.PortalResultCode.NULL))) && !(memberAccount.equals(Integer.toString(SystemConstants.PortalResultCode.FAILED)))){
								List<Object> maccount = ReflectUtil.setBeans(memberAccount, "MemberAccount");
								leftmenuService.saveOrUpdateAll(maccount);
							}
							memberSdata.add(mss);	
						}	
					}
					//查询预定会员和价格
					Member onemember = (Member)ms.get(0);
					//此处只有一个会员
					String memberRank = onemember.getMemberRank();
					//公司会员加钟点房的情况
					if(memberRank.equals("0") && hourRoomFlag){
						JSONUtil.responseJSON(response, new AjaxResult(2,"公司会员没有钟点房业务！",null));
						return;
					} else {
						if(hourRoomFlag){//true为钟点房
							ordermemberdata = leftmenuService.findBySQL("gethourpricecheckin", new String[]{onemember.getMemberId(), roomtype, branchid, memberRank}, true);//会员号,房型,门店号,会员等级
						}else{
							ordermemberdata = leftmenuService.findBySQL("getfullpricecheckin", new String[]{onemember.getMemberId(), roomtype, branchid, memberRank}, true);
						}
						//如果是公司会员，查询出该房型的门市价和公司会员的折扣，替换掉ordermemberdata 中的数值
						if(memberRank.equals("0")){
							Float commanyDiscount = Float.parseFloat(onemember.getDiscount().toString());
							List<List<?>> roomprice = new ArrayList<List<?>>();
							roomprice =  RoomUtil.getNowRoomPrice(branchid,"MSJ",roomtype);
							String companymsj = ((Map<?, ?>) roomprice.get(0).get(0)).get("ROOMPRICE").toString();
							Float commanyPrice = Float.parseFloat(companymsj);
							SysParam SP = (SysParam) leftmenuService.findOneByProperties(SysParam.class, "paramType", "ORDERGUARANTEE", "paramName","CMJ");
							
							
							if ((onemember.getDiscount().toString() == null || StringUtil.isEmpty(onemember.getDiscount().toString()))) {
								JSONUtil.responseJSON(response, new AjaxResult(2,"未查到公司会员折扣信息!", null));
							} else {
								Float price1 = commanyPrice * (commanyDiscount/100);
								DecimalFormat df = new DecimalFormat("######0.00");
								String price2 = df.format(price1);	
								//TODO
								((Map<String,String>)(ordermemberdata.get(0))).put("PRICE", price2);
							}
						}
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				formalmap.put("ordermemberdata", ordermemberdata);
				JSONUtil.responseJSON(response, new AjaxResult(3,"会员",formalmap));
			}
//			}else{//断网发送身份证信息之前台手动入住及非会员注册放在入住功能里面
//			Map<String,Object> memberdata = new HashMap<String, Object>();
//			memberdata.put("name", name);
//			memberdata.put("idcard", idnumber);
//			memberdata.put("gendor", map.get("gender")=="男" ? "1" : "0");
			
//			JSONUtil.responseJSON(response, new AjaxResult(1,"网络连接异常,还要继续入住吗~",memberdata));//断网情况下前台页面房价码可以自选,客人信息默认为非会员
//		}		
	
	}
	
	public boolean existMember(String memberId){
		
		Member member = (Member) leftmenuService.findOneByProperties(Member.class, "memberId", memberId);
		if(member != null){
			return true;
		}
		return false;
		
	}
	
	
	/**
	 * 获取前台身份证的会员信息
	 */
	@RequestMapping("/leftmenugetGuestInfo.do")
	public void getGuestInfo(HttpServletRequest request,
			HttpServletResponse response, String roomtype,boolean hourRoomFlag,boolean firsttime) {//房型,钟点房开关
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchid = loginUser.getStaff().getBranchId();
//		Map<String, String> map = checkInDirectService.readGuestInfo();
//		String idnumber = map.get("idnumber");
		String idnumber = request.getParameter("idCard");
//		String name = map.get("name");
//		if(HeartBeatClient.heartbeat){
			com.ideassoft.portal.DataDealPortalService service = new com.ideassoft.portal.DataDealPortalService();
			com.ideassoft.portal.IDataDealPortal portal = service.getDataDealPortalPort();
			String member = portal.call(SystemConstants.EnginType.COMMON_DATA, 
					SystemConstants.Movement.CUSTOM_QUERY, "{ function: \"checkInDirect.getMemberIdcard\", data:{idnumber:" + idnumber  +" } }");//查出会员id,级别,级别名称
			if((member.equals(Integer.toString(SystemConstants.PortalResultCode.NULL)))){//云端无此会员默认注册临时会员
//				//刷会员信息,房价到前台页面
//				List<?> memberdata = null;
//				if(hourRoomFlag){//true为钟点房
//					memberdata = checkInDirectService.findBySQL("hourpricecheckin", new String[]{roomtype,branchid,"1"}, true);//房型,门店号,会员等级,无会员信息
//				}else{
//					memberdata = checkInDirectService.findBySQL("fullpricecheckin", new String[]{roomtype,branchid,"1"}, true);
//				}
//				Map<String, Object> trialmap = new HashMap<String, Object>();
//				trialmap.put("memberdata", memberdata);
//				trialmap.put("membername", name);//会员姓名
//				trialmap.put("idcard", idnumber);
//				trialmap.put("gendor", map.get("gender") =="男" ? "1":"0");
//				JSONUtil.responseJSON(response, new AjaxResult(2,"临时会员",trialmap));
//				return;
				
				JSONUtil.responseJSON(response, new AjaxResult(2,"设备硬件暂未提供"));
				
			}
			//云端有会员信息
			if(!(member.equals(Integer.toString(SystemConstants.PortalResultCode.NULL))) && !(member.equals(Integer.toString(SystemConstants.PortalResultCode.FAILED)))){
				//同步至本地,先判断本地是否存在
				JSONArray array;
				Map<String, Object> formalmap = new HashMap<String, Object>();
				try {
					array = new JSONArray(member);
				JSONObject obj;
				for (int j = 0; j < array.length(); j++) {
					obj = array.getJSONObject(j);
					//云端数据保存至本地
					if(!existMember(obj.getString("MEMBER_ID"))){
					Member mb = new Member();
					mb.setMemberId(obj.getString("MEMBER_ID"));
					mb.setMemberName(obj.getString("MEMBER_NAME"));
					mb.setLoginName(obj.getString("LOGIN_NAME"));
					mb.setPassword(obj.getString("PASSWORD"));
					mb.setMemberRank(obj.getString("MEMBER_RANK"));
					mb.setIdcard(obj.getString("IDCARD"));
					mb.setGendor(obj.getString("GENDOR"));
					if(!obj.isNull("BIRTHDAY")){
						mb.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(obj.getString("BIRTHDAY")));
					}
					mb.setEmail(obj.isNull("EMAIL") ? "" : obj.getString("EMAIL"));
					mb.setAddress(obj.isNull("ADDRESS")? "" : obj.getString("ADDRESS"));
					mb.setPostcode(obj.isNull("POSTCODE")? "" : obj.getString("POSTCODE"));
					mb.setPhotos(obj.isNull("PHOTOS") ? "" : obj.getString("PHOTOS"));
					mb.setSource(obj.getString("SOURCE"));
					mb.setValidTime(new SimpleDateFormat("yyyy-MM-dd").parse(obj.getString("VALID_TIME")));
					mb.setInvalidTime(new SimpleDateFormat("yyyy-MM-dd").parse(obj.getString("INVALID_TIME")));
					mb.setRegisterTime(new SimpleDateFormat("yyyy-MM-dd").parse(obj.getString("REGISTER_TIME")));
					mb.setRecordTime(new SimpleDateFormat("yyyy-MM-dd").parse(obj.getString("RECORD_TIME")));
					mb.setStatus(obj.getString("STATUS"));
					mb.setRemark(obj.isNull("REMARK") ? "" : obj.getString("REMARK"));
					mb.setOpenId(obj.isNull("OPENID") ? "" : obj.getString("OPENID"));
					mb.setRecommend(obj.isNull("RECOMMEND") ? "" : obj.getString("RECOMMEND"));
					mb.setMobile(obj.getString("MOBILE"));
					//云端账户表同步至本地
					MemberAccount account = new MemberAccount();
					String accountdata = portal.call(SystemConstants.EnginType.COMMON_DATA, 
							SystemConstants.Movement.CUSTOM_QUERY, "{ function: \"checkInDirect.getMemberAccount\", data:{memberId:" +  obj.getString("MEMBER_ID") +" } }");//从云端获取会员账户表同步至本地
					JSONArray accountarray = new JSONArray(accountdata);
					for (int k = 0; k < accountarray.length(); k++) {
						JSONObject accountobj =  accountarray.getJSONObject(k) ;
							account.setAccountId(accountobj.getString("ACCOUNT_ID"));
							account.setMemberId(accountobj.getString("MEMBER_ID"));
							account.setCurrBalance(accountobj.isNull("CURR_BALANCE") ? (double)0 : accountobj.getDouble("CURR_BALANCE"));
							account.setCurrIntegration(accountobj.isNull("CURR_INTEGRATION") ? (long)0 : accountobj.getLong("CURR_INTEGRATION"));
							account.setTotalRecharge(accountobj.isNull("TOTAL_RECHARGE") ? (double)0 : accountobj.getDouble("TOTAL_RECHARGE"));
							account.setDiscountGift(accountobj.isNull("DISCOUNT_GIFT") ? (double)0 : accountobj.getDouble("DISCOUNT_GIFT") );
							account.setChargeGift(accountobj.isNull("CHARGE_GIFT") ? (double)0 : accountobj.getDouble("CHARGE_GIFT"));
							account.setTotalConsume(accountobj.isNull("TOTAL_CONSUME") ? (double)0 : accountobj.getDouble("TOTAL_CONSUME"));
							account.setTotalIntegration(accountobj.isNull("TOTAL_INTEGRATION") ? (long)0 : accountobj.getLong("TOTAL_INTEGRATION"));
							account.setIngegrationGift(accountobj.isNull("INGERATION_GIFT") ? (long)0:accountobj.getLong("INGERATION_GIFT") );
							account.setTotalConsIntegration(accountobj.isNull("TOTAL_CONS_INTEGRATION") ? (long)0 : accountobj.getLong("TOTAL_CONS_INTEGRATION"));
							account.setTotalRoomnights(accountobj.isNull("TOTAL_ROOMNIGHTS") ? 0 : accountobj.getInt("TOTAL_ROOMNIGHTS"));
							account.setCurrRoomnights(accountobj.isNull("CURR_ROOMNIGHTS") ? 0 : accountobj.getInt("CURR_ROOMNIGHTS"));
							account.setTotalNoshow(accountobj.isNull("TOTAL_NOSHOW") ? (short)0 : (short)accountobj.getInt("TOTAL_NOSHOW"));
							account.setCurrNoshow(accountobj.isNull("CURR_NOSHOW") ? (short)0 : (short)accountobj.getInt("CURR_NOSHOW"));
							account.setThisYearIntegration(accountobj.isNull("THIS_YEAR_INTEGRATION") ? (long)0 : accountobj.getLong("THIS_YEAR_INTEGRATION"));
							account.setRecordTime(new Date());
							account.setStatus("1");
							leftmenuService.save(account);
					}
					leftmenuService.save(mb);
					//刷会员信息,房价到前台页面
					List<?> memberdata = null;
					if(hourRoomFlag){//true为钟点房
						memberdata = leftmenuService.findBySQL("gethourpricecheckin", new String[]{obj.getString("MEMBER_ID"),roomtype,branchid,obj.getString("MEMBER_RANK")}, true);//会员号,房型,门店号,会员等级
					}else{
						memberdata = leftmenuService.findBySQL("getfullpricecheckin", new String[]{obj.getString("MEMBER_ID"),roomtype,branchid,obj.getString("MEMBER_RANK")}, true);
					}
					
					formalmap.put("memberdata", memberdata);
					}else{
						//刷会员信息,房价到前台页面
						List<?> memberdata = null;
						List price = null;
						if(hourRoomFlag){//true为钟点房
							memberdata = leftmenuService.findBySQL("gethourpricecheckin", new String[]{obj.getString("MEMBER_ID"),roomtype,branchid,obj.getString("MEMBER_RANK")}, true);//会员号,房型,门店号,会员等级
						}else{
							memberdata = leftmenuService.findBySQL("getfullpricecheckin", new String[]{obj.getString("MEMBER_ID"),roomtype,branchid,obj.getString("MEMBER_RANK")}, true);
						}
						Member mm = null;
						if("0".equals(((Map) (memberdata.get(0))).get("MEMBERRANK").toString())){//公司会员门市价+折扣
							mm = (Member) leftmenuService.findOneByProperties(Member.class, "memberId",obj.getString("MEMBER_ID"));
							if(mm.getCorporationId()!= null){
								if(hourRoomFlag){
									price = leftmenuService.findBySQL("getMSJ", new String[]{"1",branchid,roomtype,"2","1",branchid,roomtype,"2"}, true);
									
								}else{
									price = leftmenuService.findBySQL("getMSJ", new String[]{"1",branchid,roomtype,"1","1",branchid,roomtype,"1"}, true);
								}
								//String newprice = ((Map) (memberdata.get(0))).get("MEMBERRANK").toString();
								//Double newprice = Double.valueOf(price)
							}
							String newprice = ((Map) (price.get(0))).get("ROOM_PRICE").toString();
							double price1 =Double.parseDouble(newprice)*(Double.parseDouble(mm.getDiscount().toString())/100);
							DecimalFormat    df   = new DecimalFormat("######0.00");
							String price2 = df.format(price1);
							formalmap.put("price", price2);
						}
						formalmap.put("memberdata", memberdata);
					}
					JSONUtil.responseJSON(response, new AjaxResult(3,"会员",formalmap));
					return;
				}
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}	
//		}else{//断网发送身份证信息之前台手动入住及非会员注册放在入住功能里面
//			Map<String,Object> memberdata = new HashMap<String, Object>();
//			memberdata.put("name", name);
//			memberdata.put("idcard", idnumber);
//			memberdata.put("gendor", map.get("gender")=="男" ? "1" : "0");
			
//			JSONUtil.responseJSON(response, new AjaxResult(1,"网络连接异常,还要继续入住吗~",memberdata));//断网情况下前台页面房价码可以自选,客人信息默认为非会员
//		}

	}
	
	// pms处新增会员购卡
	@RequestMapping("/leftmenupayUpGradeMemberLevelInPms.do")
	@RightMethodControl(rightType = RightConstants.RightType.HOT_BUYCA)
	public ModelAndView payUpGradeMemberLevelInPms(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/ipmspage/member/memberLevelInPms");
		return mv;
	}
	
	@RequestMapping("/leftmenuqueryMemberInfo.do")
	public ModelAndView queryMemberInfo(HttpServletRequest request,
			HttpServletResponse response, String msoidcard) {
		ModelAndView mv = new ModelAndView();
		String gendor = "";
		if (!StringUtil.isEmpty(msoidcard)) {
			if (msoidcard.length() > 11) {
				Member member = (Member) leftmenuService.findOneByProperties(
						Member.class, "idcard", msoidcard);
				MemberAccount memberAccount = (MemberAccount) leftmenuService
						.findOneByProperties(MemberAccount.class, "memberId",
								member.getMemberId());
				MemberRank rank = (MemberRank) leftmenuService
						.findOneByProperties(MemberRank.class, "memberRank",
								member.getMemberRank());
				switch (Integer.parseInt(member.getGendor())) {
				case 0:
					gendor = "女";
					mv.addObject("gendor", gendor);
				case 1:
					gendor = "男";
					mv.addObject("gendor", gendor);
				default:
				}
				mv.addObject("member", member);
				mv.addObject("memberAccount", memberAccount);
				mv.addObject("rank", rank);
				mv.setViewName("page/ipmspage/member/memberpage");
			} else {
				Member member = (Member) leftmenuService.findOneByProperties(
						Member.class, "mobile", msoidcard);
				MemberAccount memberAccount = (MemberAccount) leftmenuService
						.findOneByProperties(MemberAccount.class, "memberId",
								member.getMemberId());
				MemberRank rank = (MemberRank) leftmenuService
						.findOneByProperties(MemberRank.class, "memberRank",
								member.getMemberRank());
				switch (Integer.parseInt(member.getGendor())) {
				case 0:
					gendor = "女";
					mv.addObject("gendor", gendor);
					break;
				case 1:
					gendor = "男";
					mv.addObject("gendor", gendor);
					break;
				default:
				}
				mv.addObject("member", member);
				mv.addObject("memberAccount", memberAccount);
				mv.addObject("rank", rank);
				mv.setViewName("page/ipmspage/member/memberpage");
			}
		} else {
			mv.setViewName("page/ipmspage/member/memberpagenull");
		}
		return mv;
	}
	
	/**
	 * 根据手机号,省份证模糊查询 会员信息
	 * 
	 * @param request
	 * @param response
	 * @param msoidcard
	 */
	@RequestMapping("/leftmenuselectMemberInfo.do")
	public void selectMemberInfo(HttpServletRequest request,
			HttpServletResponse response, String msoidcard) {
		List<?> list = leftmenuService.findBySQL("selectMemberInfo",
				new String[] { msoidcard, msoidcard }, true);
		String result = null;
		if (null == list || list.size() == 0) {
			result = "未查询到相关会员信息！";
			JSONUtil.responseJSON(response, new AjaxResult(1, result));
		} else {
			JSONUtil.responseJSON(response, new AjaxResult(0, result));
		}
	}
	
	/**
	 * 会员卡升级页面
	 * 
	 * @param accountid
	 * @param memberid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/leftmenumemberUpGrade.do")
	public ModelAndView memberUpGrade(String accountid, String memberid) {
		ModelAndView mv = new ModelAndView();
		Member member = (Member) leftmenuService.findById(Member.class, memberid);
		MemberAccount memberaccount = (MemberAccount) leftmenuService
				.findOneByProperties(MemberAccount.class, "memberId", member
						.getMemberId());
		List<?> memberRank = leftmenuService
				.findBySQL("memberRankDiscount", true);
		if (member.getMemberRank().equals("5")) {
			List<Member> memberlist = leftmenuService.findByProperties(
					Member.class, "recommend", member.getMemberId());
			if (memberlist.size() >= 10
					&& memberaccount.getCurrRoomnights() >= 20
					&& memberaccount.getCurrNoshow() < 0) {
				int num = 0;
				for (int i = 0; i < memberlist.size(); i++) {
					if (Integer.parseInt(memberlist.get(i).getMemberRank()) >= 5) {
						num = num + 1;
					}
				}
				if (num >= 2) {
					mv.addObject("memberblack", "6");
				}
			}
		}
		mv.addObject("accountid", accountid);
		mv.addObject("memberid", memberid);
		mv.addObject("memberRankDiscount", memberRank);
		mv.addObject("memberrank", member.getMemberRank());
		mv.setViewName("page/crm/member/memberUpGrade");
		return mv;
	}
	
	@RequestMapping("/hotelorderprice.do")
	public void hotelorderprice(HttpServletRequest request, HttpServletResponse response, String roomType, String memberId ) throws JSONException{
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginuser.getStaff().getBranchId();
		double roomprice = new SinglePrice(branchId, "1", roomType, "MSJ", RealPrice.PriceType.DATE, new Date(), memberId).checkRoomPrice();
		JSONObject res = new JSONObject();
		Member member = (Member) leftmenuService.findOneByProperties(Member.class, "memberId", memberId);
		if (member != null) {
			SysParam sysParam = (SysParam) leftmenuService.findOneByProperties(SysParam.class, "paramType", "MEMBERRPID", "paramDesc", member.getMemberRank());
			res.accumulate("rpid", sysParam.getContent());
		} else {
			res.accumulate("rpid", "MSJ");
		}
		res.accumulate("roomprice", roomprice);
		
		SysParam guparam = (SysParam) leftmenuService.findOneByProperties(SysParam.class, "paramType", "ORDERGUARANTEE", "paramName", res.getString("rpid"), "status", "1");
		String time = guparam.getContent();
		res.accumulate("limit", time);
		JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.SUCESS, "获取成功", res.toString()));
		
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
				+ roomService.getSequence("SEQ_PRICE_DATAID");
			ordChePrice.setDataId(dataId);
			ordChePrice.setDayPrice(Double.valueOf(prices.getValue().toString()));
			ordChePrice.setOrderId(orderId);
			ordChePrice.setRecordTime(new Date());
			ordChePrice.setRecordUser(loginuser.getStaff().getStaffId());
			ordChePrice.setStatus("1");
			ordChePrice.setWhichDay(sdf.parse(prices.getKey()));
			OrdchePrice oldChePrice = (OrdchePrice) leftmenuService.findOneByProperties(OrdchePrice.class, "orderId", orderId, "whichDay", sdf.parse(prices.getKey()), "status", "1");
			if (oldChePrice != null) {
				oldChePrice.setStatus("0");
				newlist.add(oldChePrice);
				leftmenuService.saveOrUpdateAll(newlist);
			}
			
			try {
				leftmenuService.save(ordChePrice);
			} catch (DAOException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	//查询每天的房价和总房价
	@SuppressWarnings("unchecked")
	public Map<String, Map<String, Object>> queryRoomPrice(HttpServletRequest request, HttpServletResponse response, String orderId, String roomtype) throws ParseException {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		Order orderdata = (Order) leftmenuService.findOneByProperties(Order.class, "orderId", orderId, "branchId", branchId);
		Map<String, Map<String, Object>> priceList = new HashMap<String, Map<String, Object>>();
		Map<String, Object> everyDayPrice = new HashMap<String, Object>();
		Map<String, Object> totalPrices = new HashMap<String, Object>();
		List<List<?>> roomprice = new ArrayList<List<?>>();
		SinglePrice singlePrice = null;
		MultiPrice multiPrice = null;
		Calendar querytime = Calendar.getInstance();
		Calendar nowtime = Calendar.getInstance();
		
		/*NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMaximumFractionDigits(2); 
		nf.setRoundingMode(RoundingMode.CEILING);*/
		
		String totalprice = "0";
		Calendar aCalendar = Calendar.getInstance();
		Calendar calendar = Calendar.getInstance();
        aCalendar.setTime(orderdata.getArrivalTime());
        int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
        aCalendar.setTime(orderdata.getLeaveTime());
        int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
        int days = day2-day1;
        days = days == 0 ? 1 : days;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String date = sdf.format(orderdata.getArrivalTime());
        nowtime.setTime(new Date());
        nowtime.getTime();
		for (int i=0;i<days;i++) {
			String price = "0";
			querytime.setTime(sdf.parse(date));
	        querytime.getTime();
			if ("".equals(orderdata.getRpId())) {
				// roomprice = RoomUtil.getNowRoomPrice(branchId, DefaultRoomPrice.DEFAULT_RP_ID, roomtype);
				singlePrice = new SinglePrice(branchId,orderdata.getTheme(),orderdata.getRoomType(),DefaultRoomPrice.DEFAULT_RP_ID,"1",date.concat(" 12:00:00"));
				//price = Double.parseDouble(nf.format(singlePrice.checkRoomPrice()));
				price = String.format("%.2f", singlePrice.checkRoomPrice());
			} else {
				// roomprice = RoomUtil.getNowRoomPrice(branchId, orderdata.getRpId(), roomtype);
				singlePrice = new SinglePrice(branchId,orderdata.getTheme(),orderdata.getRoomType(),orderdata.getRpId(),"1",date.concat(" 12:00:00"),orderdata.getOrderUser());
				//price = Double.parseDouble(nf.format(singlePrice.checkRoomPrice()));
				price = String.format("%.2f", singlePrice.checkRoomPrice());
			}
			//price = Double.valueOf(((Map<?, ?>) roomprice.get(0).get(0)).get("ROOMPRICE").toString());
			
			/*if (!DateUtil.isSameDay(querytime, nowtime)) {
				if ("".equals(orderdata.getRpId())) {
					// roomprice = RoomUtil.getForwardRoomPrice(branchId, date, DefaultRoomPrice.DEFAULT_RP_ID, roomtype);
					multiPrice = new MultiPrice(branchId, orderdata.getTheme(), orderdata.getRoomType(), DefaultRoomPrice.DEFAULT_RP_ID, "1", orderdata.getArrivalTime(), orderdata.getLeaveTime())
					
					//roomprice = RoomUtil.getForwardRoomPrice(branchId, date, DefaultRoomPrice.DEFAULT_RP_ID, roomtype);
					
				} else {
					roomprice = RoomUtil.getForwardRoomPrice(branchId, date, orderdata.getRpId(), roomtype);
				}
				if (((Map<?, ?>) roomprice.get(0).get(0)).get("ADJUSTMEM") != null && 
						!((Map<?, ?>) roomprice.get(0).get(0)).get("ADJUSTMEM").equals(0) &&
						!((Map<?, ?>) roomprice.get(0).get(0)).get("ADJUSTMEM").equals(0.0) &&
						!((Map<?, ?>) roomprice.get(0).get(0)).get("ADJUSTMEM").equals(0.00))
				price = Double.valueOf(((Map<?, ?>) roomprice.get(0).get(0)).get("ADJUSTMEM").toString());
			} */
			
			everyDayPrice.put(date, price);
			// totalprice += Double.valueOf(price);
			calendar.setTime(sdf.parse(date));
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			date = sdf.format(calendar.getTime()); 
		}
		
		// 获取订单中所有天数价格总和
		multiPrice = new MultiPrice(branchId, orderdata.getTheme(), orderdata.getRoomType(), orderdata.getRpId(), "1", orderdata.getArrivalTime(), DateUtil.addDays(orderdata.getLeaveTime(), -1));
		// totalprice = Double.parseDouble(nf.format(multiPrice.checkRoomPrice()));
		totalprice = String.format("%.2f", multiPrice.checkRoomPrice()); 
		
		
		totalPrices.put("totalPrices", totalprice);
		priceList.put("everyDayPrice", everyDayPrice);
		priceList.put("totalPrices", totalPrices);
		return priceList;
	}
	
	
	public String checkIdCard(String name, String idcard ) throws IOException, JSONException{
		JSONObject jo = WeixinUtil.iDcardAndName(idcard, name);
		String resultString = jo.getString("result");
		String idCardResultString = "";
		JSONObject idCardResultJo = null;
		if(resultString.trim().equals("null") ){
			idCardResultString = String.valueOf(SystemConstants.PortalResultCode.NULL);
		}else{
			idCardResultJo = jo.getJSONObject("result");
		}
		if(idCardResultJo!=null){
			idCardResultString = jo.getJSONObject("result").getString("res");
		}
		return idCardResultString;
	}
}