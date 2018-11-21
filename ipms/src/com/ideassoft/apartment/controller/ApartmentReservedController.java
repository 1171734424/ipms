package com.ideassoft.apartment.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hsqldb.lib.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alipay.api.AlipayApiException;
import com.ideassoft.apartment.service.ApartmentAliPayServiceImpl;
import com.ideassoft.apartment.service.ApartmentCommonService;
import com.ideassoft.apartment.service.ApartmentLogService;
import com.ideassoft.apartment.service.ApartmentLogServiceInPms;
import com.ideassoft.apartment.service.ApartmentReservedService;
import com.ideassoft.apartment.service.ApartmentWeChatPayServiceImpl;
import com.ideassoft.bean.Branch;
import com.ideassoft.bean.Member;
import com.ideassoft.bean.MemberAccount;
import com.ideassoft.bean.RefundDetail;
import com.ideassoft.bean.Reserved;
import com.ideassoft.bean.RoomType;
import com.ideassoft.core.ajax.AjaxResult;
import com.ideassoft.core.annotation.interfaces.RightMethodControl;
import com.ideassoft.core.annotation.interfaces.RightModelControl;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.constants.RightConstants;
import com.ideassoft.core.constants.SystemConstants;
import com.ideassoft.core.notifier.SmsSingleSender;
import com.ideassoft.core.notifier.SmsSingleSenderResult;
import com.ideassoft.core.page.Pagination;
import com.ideassoft.crm.templateMessage.BookingSuccessMsg;
import com.ideassoft.crm.templateMessage.OrderCancelMsg;
import com.ideassoft.crm.templateMessage.TemplateMessageUtil;
import com.ideassoft.util.JSONUtil;
import com.ideassoft.util.MD5Util;
import com.ideassoft.util.ReflectUtil;
import com.ideassoft.util.RequestUtil;
import com.ideassoft.wechatrefund.WeChatRefundReqModel;

@Transactional
@Controller
@RightModelControl(rightModel = RightConstants.RightModel.APARTMENT_CONTROL)
public class ApartmentReservedController {

	@Autowired
	private ApartmentReservedService apartmentReservedService;

	@Autowired
	private ApartmentCommonService commonService;

	@Autowired
	private ApartmentLogService logService;

	@Autowired
	private ApartmentLogServiceInPms logServiceInPms;
	
	@Autowired
	private ApartmentWeChatPayServiceImpl weChatPayServiceImpl;
	
	@Autowired
	private ApartmentAliPayServiceImpl aliPayServiceImpl;

	/**
	 * 公寓预约-主页面左侧栏“处理公寓预约”弹出页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/apartmentNewReserved.do")
	@RightMethodControl(rightType = RightConstants.RightType.APA_DEARES)
	public ModelAndView apartmentNewReserved(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/apartment/apartmentLeftmenu/apartmentreserved/apartmentnewreserved");
		return mv;
	}
	
	/**
	 * 公寓预约-显示公寓预约页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/apartmentReserved.do")
	public ModelAndView apartmentReserved(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/apartment/apartmentLeftmenu/apartmentreserved/apartmentreserved");
		return mv;
	}
	
	/**
	 * 公寓预约-显示无人预约页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/apartmentSelfservice.do")
	public ModelAndView apartmentSelfservice(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/apartment/apartmentLeftmenu/apartmentreserved/selfservice");
		return mv;
	}
	
	/**
	 * 公寓预约-查询全部公寓预约信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/queryApartmentReserved.do")
	public ModelAndView queryApartmentReserved(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		Pagination pagination = (Pagination) ReflectUtil.setBeanFromRequest(request, Pagination.class);
		if (pagination != null && pagination.getPageNum() == null) {
			pagination.setPageNum(1);
			pagination.setRows(17);
		}
		String memberName = request.getParameter("memberName");
		String mobile = request.getParameter("mobile");
		String status = request.getParameter("status");
		String reservedTime = request.getParameter("reservedTime");
		List<Reserved> list = apartmentReservedService.findBySQLWithPagination("reservedApartment", new String[] {
				branchId, memberName, mobile, status, reservedTime }, pagination, true);
		mv.addObject("list", list);
		mv.addObject("orderuser", memberName);
		mv.addObject("mobile", mobile);
		mv.addObject("ordertime", reservedTime);
		mv.addObject("status", status);
		mv.addObject("pagination", pagination);
		mv.setViewName("page/apartment/apartmentLeftmenu/apartmentreserved/apartmentreservedinfo");
		return mv;
	}
	
	/**
	 * 公寓预约-显示添加公寓预约页面
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addApartmentReserved.do")
	public ModelAndView addApartmentReserved(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/apartment/apartmentLeftmenu/apartmentreserved/addReserved");
		return mv;
	}
	
	/**
	 * 公寓预约-根据ID 改变状态
	 * 
	 * @param request
	 * @param response
	 * @param reservedId ID
	 * @param status 状态
	 * @throws Exception
	 */
	@RequestMapping("/checkApartmentReserved.do")
	public void checkApartmentReserved(HttpServletRequest request, HttpServletResponse response, String reservedId,
			String status) throws Exception {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Reserved reserved = (Reserved) apartmentReservedService.findOneByProperties(Reserved.class, "reservedId",
				reservedId);
		reserved.setStatus(status);
		reserved.setRecordTime(new Date());
		reserved.setRecordUser(loginUser.getStaff().getStaffId());
		apartmentReservedService.update(reserved);
		String result = "{\"code\":\"1\"}";
		JSONUtil.responseJSON(response, result);
	}
	
	/**
	 * 公寓预约-根据ID 同意或拒绝
	 * 
	 * @param request
	 * @param response
	 * @param reservedId ID
	 * @param status 状态
	 * @throws Exception
	 */
	@RequestMapping("/checkReservedApartment.do")
	public void checkReservedApartment(HttpServletRequest request, HttpServletResponse response, String reservedId, String status)
			throws Exception {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Reserved reserved = (Reserved) apartmentReservedService.findOneByProperties(Reserved.class, "reservedId",reservedId);
		Member member = (Member) apartmentReservedService.findById(Member.class, reserved.getReservedPerson());
		Branch branch = (Branch) apartmentReservedService.findOneByProperties(Branch.class, "branchId",reserved.getBranchId());
		reserved.setDispose(status);
		reserved.setRecordTime(new Date());
		reserved.setRecordUser(loginUser.getStaff().getStaffId());
		apartmentReservedService.update(reserved);
		logServiceInPms.apartmentReserved(loginUser, status, member,request);
		if (!StringUtil.isEmpty(member.getOpenId()) || !"null".equals(member.getOpenId())) {
			//SysParam sysparam = commonService.Token();
			String token = commonService.Token();
			switch (Integer.parseInt(status)) {
			case 0:
				status = "拒绝";
				OrderCancelMsg ocm = new OrderCancelMsg();
				ocm.setFirst("您好!您提交的公寓预约看房已被取消!");
				ocm.setOrderId(reserved.getReservedId());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				ocm.setTime(sdf.format(new Date()));
				ocm.setRemark("如您有任何疑问，请联系"+branch.getPhone()+"!");
				TemplateMessageUtil.sendOrderCancelSuccessNoticeMsg(token, member.getOpenId(), ocm);
				break;
			case 1:
				status = "同意";
				BookingSuccessMsg serviceMsg = new BookingSuccessMsg();
				serviceMsg.setFirst("您好!您提交的公寓预约看房已通过!");
				SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
				serviceMsg.setBookingDate(sd.format(reserved.getReservedDate()));
				serviceMsg.setMemberName(member.getMemberName());
				serviceMsg.setMemberPhone(reserved.getMobile());
				serviceMsg.setRemark("您的公寓预约申请已被" + status + "!如您有任何疑问，请联系"+ branch.getPhone() +"!");
				TemplateMessageUtil.sendBookingSuccessMsg(token, member.getOpenId(), serviceMsg);
				break;
			default:
				break;
			}
		}

		/*SysParam param = (SysParam) apartmentReservedService.findOneByProperties(SysParam.class, "paramType",SystemConstants.ParamType.BRANCH_IP, "paramName", "1");
		// 会员等级
		int priority = 1;
		priority = Integer.parseInt(member.getMemberRank());
		List<Reserved> list = new ArrayList<Reserved>();
		list.add(reserved);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Reserved", list);
		TransferServcie.getInstance().transferData(priority, param.getContent(), JSONUtil.fromObject(map).toString());*/
		
		SmsSingleSender singleSender = new SmsSingleSender(SystemConstants.note.APPID, SystemConstants.note.APPKEY);
		ArrayList<String> params = new ArrayList<String>();
		if("同意".equals(status)){
			status = "通过";
		}
		params.add(status);
		params.add(reserved.getReservedId());
		params.add(branch.getPhone());
		if(reserved.getMobile().trim().equals(member.getMobile().trim())){
			SmsSingleSenderResult singleSenderResult = singleSender.sendWithParam(SystemConstants.note.COUNTRY, reserved.getMobile(), 66100, params, "", "", "");
		}else{
			SmsSingleSenderResult singleSenderResult = singleSender.sendWithParam(SystemConstants.note.COUNTRY, reserved.getMobile(), 66100, params, "", "", "");
			SmsSingleSenderResult singleSenderResultAnother = singleSender.sendWithParam(SystemConstants.note.COUNTRY, member.getMobile(), 66100, params, "", "", "");
		}
		
		String result = "{\"code\":\"1\"}";
		JSONUtil.responseJSON(response, result);
	}
	
	/**
	 * 公寓预约-添加公寓预约信息
	 * 根据名称查询会员信息,不存在创建会员
	 * 
	 * @param request
	 * @param response
	 * @param reservedperson 名称
	 * @param mobile 手机号
	 * @param roomType 房间类型
	 * @param remark 备注
	 * @throws Exception
	 */
	@RequestMapping("/addApartmentReservedInfo.do")
	public void addApartmentReservedInfo(HttpServletRequest request, HttpServletResponse response, String reservedperson,
			String mobile, String roomType, String remark) throws Exception {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		String strdate = sdf.format(date);
		Reserved reserved = new Reserved();
		Member members = (Member) apartmentReservedService.findOneByProperties(Member.class, "mobile", mobile);
		if (null != members) {
			reserved.setReservedPerson(members.getMemberId());
		} else {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.YEAR, 1);
			Member member = new Member();
			member.setMemberId(apartmentReservedService.getSequence("SEQ_MEMBER_ID"));
			member.setMemberName(reservedperson);
			member.setLoginName("123456");
			member.setMemberRank("1");
			member.setGendor("0");
			member.setMobile(mobile);
			member.setSource("1");
			member.setStatus("1");
			member.setPassword(MD5Util.getCryptogram("888888"));
			member.setValidTime(date);
			member.setInvalidTime(calendar.getTime());
			member.setRegisterTime(date);
			member.setRecordTime(date);
			MemberAccount account = new MemberAccount();
			account.setAccountId(apartmentReservedService.getSequence("SEQ_ACCOUNT_ID"));
			account.setMemberId(member.getMemberId());
			account.setCurrBalance((double) 0);// 当前余额
			account.setCurrIntegration((long) 0);// 当前积分
			account.setTotalRecharge((double) 0);// 充值金额
			account.setDiscountGift((double) 0);// 折扣赠送
			account.setChargeGift((double) 0);// 充值赠送
			account.setTotalConsume((double) 0);// 消费金额
			account.setTotalIntegration((long) 0);// 获取积分
			account.setIngegrationGift((long) 0);// 积分赠送
			account.setTotalConsIntegration((long) 0);// 消费积分
			account.setTotalRoomnights(0);// 总房晚
			account.setCurrRoomnights(0);// 当前房晚
			account.setTotalNoshow((short) 0);// 总未现天数
			account.setCurrNoshow((short) 0);// 当前未现天数
			account.setThisYearIntegration((long) 0);// 当年积分
			account.setRecordTime(date);
			account.setStatus("1");
			logService.memberLog((LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY),
					member, "1",request);
			reserved.setReservedPerson(member.getMemberId());
			apartmentReservedService.save(member);
			apartmentReservedService.save(account);
		}

		reserved.setReservedId(strdate + loginUser.getStaff().getBranchId() + "3" + apartmentReservedService.getSequence("SEQ_RESERVED_ID"));
		reserved.setBranchId(loginUser.getStaff().getBranchId());
		reserved.setSource("3");
		reserved.setRoomType(roomType);
		reserved.setReservedDate(date);
		reserved.setMobile(mobile);
		reserved.setApplicationTime(date);
		reserved.setRecordTime(date);
		reserved.setRecordUser(loginUser.getStaff().getStaffId());
		reserved.setRemark(remark);
		reserved.setReservedType("2");
		apartmentReservedService.save(reserved);
		String result = "{\"code\":\"1\"}";
		JSONUtil.responseJSON(response, result);
	}
	
	/**
	 * 公寓预约-查询公寓房型
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/queryApartRoomTypeInfo.do")
	public void queryApartRoomTypeInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		List<RoomType> roomType = apartmentReservedService.findByProperties(RoomType.class, "theme", "2", "status", "1", "roomTypeKey.branchId", loginUser.getStaff().getBranchId());
		JSONUtil.responseJSON(response, roomType);
	}
	
	/**
	 * 公寓预约-无人看房查询页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/queryApartSelfservice.do")
	public ModelAndView queryApartSelfservice(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		Pagination pagination = (Pagination) ReflectUtil.setBeanFromRequest(request, Pagination.class);
		if (pagination != null && pagination.getPageNum() == null) {
			pagination.setPageNum(1);
			pagination.setRows(17);
		}
		String memberName = request.getParameter("memberName");
		String mobile = request.getParameter("mobile");
		String reservedTime = request.getParameter("reservedTime");
		List<Reserved> list = apartmentReservedService.findBySQLWithPagination("querySelfservice", new String[] {
				branchId, memberName, mobile, reservedTime }, pagination, true);
		mv.addObject("list", list);
		mv.addObject("orderuser", memberName);
		mv.addObject("mobile", mobile);
		mv.addObject("ordertime", reservedTime);
		mv.addObject("pagination", pagination);
		mv.setViewName("page/apartment/apartmentLeftmenu/apartmentreserved/selfserviceinfo");
		return mv;
	}
	
	/**
	 * 公寓预约-无人看房-退款按钮事件
	 * 
	 * @param request
	 * @param response
	 * @param aptOrderId
	 * @throws AlipayApiException
	 */
	@RequestMapping("/selfserviceApartReFund.do")
	public void selfserviceApartReFund(HttpServletRequest request, HttpServletResponse response, String aptOrderId) throws AlipayApiException {
		Reserved reserved = (Reserved) apartmentReservedService.findById(Reserved.class, aptOrderId);
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		reserved.setRecordUser(loginUser.getStaff().getStaffId());
		reserved.setDispose("0");
		RefundDetail refundDetail = (RefundDetail) apartmentReservedService.findOneByProperties(RefundDetail.class, "orderId", aptOrderId, "status", "1");
		if(refundDetail != null){
			if(refundDetail.getSource().equals("7")){
				String Refund = String.valueOf(refundDetail.getRefundMoney() * 100);
				Refund = Refund.substring(0, Refund.indexOf("."));
				WeChatRefundReqModel a = new WeChatRefundReqModel();
				a.setOutTradeNo(refundDetail.getBussinessId().trim());
				a.setTotalFee(Integer.parseInt(Refund));
				a.setRefundFee(Integer.parseInt(Refund));
				weChatPayServiceImpl.weChatRefund(a);
			}
			if(refundDetail.getSource().equals("1")){
				aliPayServiceImpl.alipayReFund(refundDetail.getBussinessId(), refundDetail.getTradeId(), String.valueOf(refundDetail.getRefundMoney()));
			}
		}
		apartmentReservedService.update(reserved);
		JSONUtil.responseJSON(response, new AjaxResult(1, "退款成功!"));
	}
	
}
