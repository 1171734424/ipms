package com.ideassoft.crm.controller;


import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.hsqldb.lib.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ideassoft.bean.Bill;
import com.ideassoft.bean.Campaign;
import com.ideassoft.bean.CouponGroup;
import com.ideassoft.bean.FreezeMember;
import com.ideassoft.bean.Member;
import com.ideassoft.bean.MemberAccount;
import com.ideassoft.bean.MemberCoupon;
import com.ideassoft.bean.MemberRank;
import com.ideassoft.bean.SysParam;
import com.ideassoft.bean.WorkBill;
import com.ideassoft.bean.WorkBillDetail;
import com.ideassoft.core.ajax.AjaxResult;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.constants.CommonConstants;
import com.ideassoft.core.constants.SystemConstants;
import com.ideassoft.core.notifier.SmsSingleSender;
import com.ideassoft.core.page.Pagination;
import com.ideassoft.crm.service.LogSer;
import com.ideassoft.crm.service.MemberCRMSer;
import com.ideassoft.crm.service.TransferServcie;
import com.ideassoft.util.DateUtil;
import com.ideassoft.util.JSONUtil;
import com.ideassoft.util.MD5Util;
import com.ideassoft.util.ReflectUtil;
import com.ideassoft.util.RequestUtil;

@Transactional
@Controller
public class MemberCRMCtrl {

	@Autowired
	private MemberCRMSer memberCRMService;

	@Autowired
	private LogSer logService;

	/**
	 * 保存，修改会员信息
	 * 
	 * @param request
	 * @param response
	 * @param MEMBERID
	 * @throws Exception
	 */
	@RequestMapping("/saveMember.do")
	public void userUpdate(HttpServletRequest request,
			HttpServletResponse response, String MEMBERID) throws Exception {
		Member member;
		MemberAccount account = null;
//		SysParam sp = (SysParam) memberService.findOneByProperties(SysParam.class, "paramType",
//				CommonConstants.SecondsUpload.REMOTE_PATH, new Object[0]);
//		String remotePath = sp.getContent();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		// 会员等级
		int priority = 1;
		if (null != MEMBERID) {
			member = (Member) ReflectUtil.setBeansFromJsonArray(request, "Member").get(0);
			if (null == request.getParameter("PHOTOS")) {
				member.setPhotos(((Member) logService.findById(Member.class, MEMBERID)).getPhotos());
				logService.sessionClear();
			}
			logService.memberLog((LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY), member, "3",request);
		} else {
			String memberId = memberCRMService.getCloudSequence("SEQ_MEMBER_ID");
			String memberAccountId = memberCRMService.getCloudSequence("SEQ_ACCOUNT_ID");
			Date date = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.YEAR, 1);
			member = (Member) ReflectUtil.setBeansFromJsonArray(request, "Member", true).get(0);
			member.setMemberId(memberId);
			member.setPassword(MD5Util.getCryptogram("888888"));
			member.setRecordTime(sdf.parse(sdf.format(new Date())));
			member.setValidTime(date);
			member.setInvalidTime(calendar.getTime());
			member.setRegisterTime(date);
			//member.setRecordTime(date);
			account = (MemberAccount) ReflectUtil.setBeansFromJsonArray(request, "MemberAccount", true).get(0);
			account.setAccountId(memberAccountId);
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
			account.setRecordTime(new Date());
			logService.memberLog((LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY), member, "1",request);
			priority = Integer.parseInt(member.getMemberRank());
			List<MemberAccount> memberAccountList = new ArrayList<MemberAccount>();
			memberAccountList.add(account);
			Map<String, Object> memberAccountMap = new HashMap<String, Object>();
			memberAccountMap.put("MemberAccount", memberAccountList);
			// TransferServcie.getInstance().transferData(priority, CommonConstants.MarketCenterIpValue.centerIpValue, JSONUtil.fromObject(memberAccountMap).toString());
		}
		priority = Integer.parseInt(member.getMemberRank());
//		List<Member> memberList = new ArrayList<Member>();
//		memberList.add(member);
//		Map<String, Object> memberMap = new HashMap<String, Object>();
//		memberMap.put("Member", memberList);
		//TransferServcie.getInstance().transferData(priority, param.getContent(), JSONUtil.fromObject(memberMap).toString());
		memberCRMService.getHibernateTemplate().saveOrUpdate(member);
		if (null != account) {
			memberCRMService.getHibernateTemplate().saveOrUpdate(account);
		}
	}
	
	
	/**
	 * 删除会员
	 * 
	 * @param request
	 * @param response
	 * @param memberid
	 * @throws UnknownHostException
	 */
	@RequestMapping("/delMember.do")
	public void delMember(HttpServletRequest request,
			HttpServletResponse response, String memberid)
			throws UnknownHostException {
		LoginUser loginUser = (LoginUser) request.getSession(true)
				.getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		delMember(memberid, loginUser,request);
		String result = "{\"code\":\"1\"}";
		JSONUtil.responseJSON(response, result);
	}
	
	@RequestMapping("/freezeMember.do")
	public void freezeMember(HttpServletRequest request,
			HttpServletResponse response, String memberid) throws Exception {
		Member member = (Member) memberCRMService.findById(Member.class, memberid);
		FreezeMember fm = new FreezeMember();
		fm.setAddress(member.getAddress());
		fm.setAppToken(member.getAppToken());
		fm.setBirthday(member.getBirthday());
		fm.setCorporationId(member.getCorporationId());
		fm.setDiscount(member.getDiscount());
		fm.setEmail(member.getEmail());
		fm.setGendor(member.getGendor());
		fm.setIdcard(member.getIdcard());
		fm.setInvalidTime(member.getInvalidTime());
		fm.setLoginName(member.getLoginName());
		fm.setMemberId(member.getMemberId());
		fm.setMemberName(member.getMemberName());
		fm.setMemberRank(member.getMemberRank());
		fm.setMobile(member.getMobile());
		fm.setOpenId(member.getOpenId());
		fm.setPassword(member.getPassword());
		fm.setPhotos(member.getPhotos());
		fm.setPostcode(member.getPostcode());
		fm.setRecommend(member.getRecommend());
		fm.setRecordTime(member.getRecordTime());
		fm.setRegisterTime(member.getRegisterTime());
		fm.setRemark(member.getRemark());
		fm.setSource(member.getSource());
		fm.setStatus(member.getStatus());
		fm.setValidTime(member.getValidTime());
		memberCRMService.save(fm);
		memberCRMService.delete(member);
		JSONUtil.responseJSON(response, new AjaxResult(1, "冻结成功!"));
	}
	
	/**
	 * 根据手机号,省份证模糊查询 会员信息
	 * 
	 * @param request
	 * @param response
	 * @param msoidcard
	 */
	@RequestMapping("/selectMemberInfo.do")
	public void selectMemberInfo(HttpServletRequest request,
			HttpServletResponse response, String msoidcard) {
		List<?> list = memberCRMService.findBySQL("selectMemberInfo",
				new String[] { msoidcard, msoidcard }, true);
		Iterator<?> memList = list.iterator();
		while(memList.hasNext()){
			JSONObject object = JSONObject.fromObject(memList.next()) ;
			if("1".equals(object.getString("MEMBER_RANK"))){
				memList.remove();
			}
		}
		String result = null;
		if (null == list || list.size() == 0) {
			result = "未查询到相关会员信息！";
			JSONUtil.responseJSON(response, new AjaxResult(1, result));
		} else {
			JSONUtil.responseJSON(response, new AjaxResult(0, result));
		}
	}
	/**
	 * 储值卡充值
	 * 
	 * @param accountid
	 * @param memberid
	 * @param membername
	 * @return
	 */
	@RequestMapping("/turnToCardRecharge.do")
	public ModelAndView turnToCardRecharge(String memberid) {
		ModelAndView mv = new ModelAndView();
		Member member = (Member) memberCRMService.findById(Member.class, memberid);
		MemberAccount memberAccount = (MemberAccount) memberCRMService
				.findOneByProperties(MemberAccount.class, "memberId", member
						.getMemberId());
		MemberRank memberRank = (MemberRank) memberCRMService.findOneByProperties(
				MemberRank.class, "memberRank", member.getMemberRank());
		mv.addObject("member", member);
		mv.addObject("memberAccount", memberAccount);
		mv.addObject("memberRank", memberRank);
		mv.setViewName("page/crm/member/addCustom");
		return mv;
	}
	
	/**
	 * 解冻时跳转修改手机号码页面
	 * 
	 * @param accountid
	 * @param memberid
	 * @param membername
	 * @return
	 */
	@RequestMapping("/updateMemberPhone.do")
	public ModelAndView updateMemberPhone(String memberid) {
		ModelAndView mv = new ModelAndView();
		FreezeMember member = (FreezeMember) memberCRMService.findById(FreezeMember.class, memberid);
		mv.addObject("member", member);
		mv.addObject("mobile", member.getMobile());
		mv.setViewName("page/crm/member/updatePhone");
		return mv;
	}
	/**
	 * 弹出框,查询充值活动
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/selectCampaign.do")
	public ModelAndView selectCampaign(HttpServletRequest request,
			HttpServletResponse response, String memberRank) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("memberRank", memberRank);
		mv.setViewName("page/crm/member/memberDialog");
		return mv;
	}
	
	
	@RequestMapping("/queryCampaign.do")
	public void queryCampaign(HttpServletRequest request,
			HttpServletResponse response, String cond_CUSTOMVALUE, String memberRank) {
		Pagination pagination;
		Integer pageNum = Integer.parseInt(request.getParameter("page"));
		Integer rows = Integer.parseInt(request.getParameter("rows"));
		pagination = new Pagination(pageNum, rows);
		List<?> result = memberCRMService.findBySQLWithPagination("queryCampaign",
				new String[] { cond_CUSTOMVALUE, memberRank }, pagination, true);
		JSONUtil.responseJSON(response, JSONUtil.buildReportJSONByPagination(
				result, pagination));
	}
	
	
	/**
	 * 充值活动,优惠卷读取
	 * 
	 * @param request
	 * @param response
	 * @param account_Id
	 * @param dataId
	 * @param member_Id
	 * @throws Exception
	 */
	@RequestMapping("/changeMemberScoreAndReserve.do")
	public void changeMemberScoreAndReserve(HttpServletRequest request,
			HttpServletResponse response, String account_Id, String dataId,
			String member_Id, String discount_gift) throws Exception {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		MemberAccount me = (MemberAccount) memberCRMService.getCustomerInfo(account_Id);// 获取会员账户
		Member member = (Member) memberCRMService.getmember(member_Id);
		String remark = "";
		Bill bill = null;
		
		if (!StringUtil.isEmpty(discount_gift)) {
			me.setCurrBalance(me.getCurrBalance() + Integer.parseInt(discount_gift));// 根据充值添加到当前余额
			me.setTotalRecharge(me.getTotalRecharge() + Integer.parseInt(discount_gift));// 根据充值添加充值金额
			remark = member.getMemberName() + "充值金额:" + discount_gift;
			bill = new Bill();
			String billId = DateUtil.currentDate("yyMMdd") + loginUser.getStaff().getBranchId() + memberCRMService.getSequence("SEQ_NEW_BILL");
			bill.setBillId(billId);
			bill.setBranchId(loginUser.getStaff().getBranchId());
			bill.setCheckId(member.getMemberId());
			bill.setProjectId("2002");
			bill.setProjectName("现金");
			bill.setDescribe("充值");
			bill.setPayment("1");
			bill.setCost(0.0);
			bill.setPay(Double.parseDouble(discount_gift));
			bill.setStatus("1");
			bill.setRecordTime(new Date());
			bill.setRecordUser(loginUser.getStaff().getStaffId());
		}
		if (!StringUtil.isEmpty(dataId)) {
			Campaign campaign = (Campaign) memberCRMService.findById(Campaign.class, dataId);// 获取营销活动
			me.setCurrBalance(me.getCurrBalance() + campaign.getOperMoney());// 根据充值添加到当前余额
			me.setTotalRecharge(me.getTotalRecharge() + campaign.getOperMoney());// 根据充值添加充值金额
			
			bill = new Bill();
			String billId = DateUtil.currentDate("yyMMdd") + loginUser.getStaff().getBranchId() + memberCRMService.getSequence("SEQ_NEW_BILL");
			bill.setBillId(billId);
			bill.setBranchId(loginUser.getStaff().getBranchId());
			bill.setCheckId(member.getMemberId());
			bill.setProjectId("2002");
			bill.setProjectName("现金");
			bill.setDescribe("充值");
			bill.setPayment("1");
			bill.setCost(0.0);
			bill.setPay(Double.parseDouble(discount_gift));
			bill.setStatus("1");
			bill.setRecordTime(new Date());
			bill.setRecordUser(loginUser.getStaff().getStaffId());
			
			remark = member.getMemberName() + "充值金额:" + campaign.getOperMoney();
			CouponGroup coupongroup = (CouponGroup) memberCRMService.findById(CouponGroup.class, campaign.getCouponGroupId());// 获取优惠卷组合
			if (null != coupongroup) {
				List<MemberCoupon> couponlist = new ArrayList<MemberCoupon>();
				// 10元优惠卷
				if (null != coupongroup.getTenCoupon()) {
					SysParam sysparam = (SysParam) memberCRMService.findOneByProperties(SysParam.class, "paramType", "COUPON", "paramName", "TEN_COUPON");
					Calendar c = Calendar.getInstance();
					c.add(Calendar.DAY_OF_MONTH, Integer.parseInt(sysparam.getOrderNo().toString()));
					for (int i = 0; i < Integer.parseInt(coupongroup.getTenCoupon()); i++) {
						MemberCoupon membercoupon = new MemberCoupon();
						membercoupon.setMemberId(me.getMemberId());
						membercoupon.setCouponId(memberCRMService.getCloudSequence("SEQ_TEN_COUPON_ID"));
						membercoupon.setCouponPrice("10");
						membercoupon.setStartTime(new Date());
						membercoupon.setEndTime(c.getTime());
						membercoupon.setStatus("1");
						couponlist.add(membercoupon);
						sysparam.setParamDesc(membercoupon.getCouponId());
						memberCRMService.update(sysparam);
					}
				}
				// 20元优惠卷
				if (null != coupongroup.getTwentyCoupon()) {
					SysParam sysparam = (SysParam) memberCRMService.findOneByProperties(SysParam.class, "paramType", "COUPON", "paramName", "TWENTY_COUPON");
					Calendar c = Calendar.getInstance();
					c.add(Calendar.DAY_OF_MONTH, Integer.parseInt(sysparam.getOrderNo().toString()));
					for (int i = 0; i < Integer.parseInt(coupongroup.getTwentyCoupon()); i++) {
						MemberCoupon membercoupon = new MemberCoupon();
						membercoupon.setMemberId(me.getMemberId());
						membercoupon.setCouponId(memberCRMService.getCloudSequence("SEQ_TWENTY_COUPON_ID"));
						membercoupon.setCouponPrice("20");
						membercoupon.setStartTime(new Date());
						membercoupon.setEndTime(c.getTime());
						membercoupon.setStatus("1");
						couponlist.add(membercoupon);
						sysparam.setParamDesc(membercoupon.getCouponId());
						memberCRMService.update(sysparam);
					}
				}
				// 30元优惠卷
				if (null != coupongroup.getThirtyCoupon()) {
					SysParam sysparam = (SysParam) memberCRMService.findOneByProperties(SysParam.class, "paramType", "COUPON", "paramName", "THIRTY_COUPON");
					Calendar c = Calendar.getInstance();
					c.add(Calendar.DAY_OF_MONTH, Integer.parseInt(sysparam.getOrderNo().toString()));
					for (int i = 0; i < Integer.parseInt(coupongroup.getThirtyCoupon()); i++) {
						MemberCoupon membercoupon = new MemberCoupon();
						membercoupon.setMemberId(me.getMemberId());
						membercoupon.setCouponId(memberCRMService.getCloudSequence("SEQ_THIRTY_COUPON_ID"));
						membercoupon.setCouponPrice("30");
						membercoupon.setStartTime(new Date());
						membercoupon.setEndTime(c.getTime());
						membercoupon.setStatus("1");
						couponlist.add(membercoupon);
						sysparam.setParamDesc(membercoupon.getCouponId());
						memberCRMService.update(sysparam);
					}
				}
				// 50元优惠卷
				if (null != coupongroup.getFiftyCoupon()) {
					SysParam sysparam = (SysParam) memberCRMService.findOneByProperties(SysParam.class, "paramType", "COUPON", "paramName", "FIFTY_COUPON");
					Calendar c = Calendar.getInstance();
					c.add(Calendar.DAY_OF_MONTH, Integer.parseInt(sysparam.getOrderNo().toString()));
					for (int i = 0; i < Integer.parseInt(coupongroup.getFiftyCoupon()); i++) {
						MemberCoupon membercoupon = new MemberCoupon();
						membercoupon.setMemberId(me.getMemberId());
						membercoupon.setCouponId(memberCRMService.getCloudSequence("SEQ_FIFTY_COUPON_ID"));
						membercoupon.setCouponPrice("50");
						membercoupon.setStartTime(new Date());
						membercoupon.setEndTime(c.getTime());
						membercoupon.setStatus("1");
						couponlist.add(membercoupon);
						sysparam.setParamDesc(membercoupon.getCouponId());
						memberCRMService.update(sysparam);
					}
				}
				// 100元优惠卷
				if (null != coupongroup.getHundredCoupon()) {
					SysParam sysparam = (SysParam) memberCRMService.findOneByProperties(SysParam.class, "paramType", "COUPON", "paramName", "HUNDRED_COUPON");
					Calendar c = Calendar.getInstance();
					c.add(Calendar.DAY_OF_MONTH, Integer.parseInt(sysparam.getOrderNo().toString()));
					for (int i = 0; i < Integer.parseInt(coupongroup.getHundredCoupon()); i++) {
						MemberCoupon membercoupon = new MemberCoupon();
						membercoupon.setMemberId(me.getMemberId());
						membercoupon.setCouponId(memberCRMService.getCloudSequence("SEQ_HUNDRED_COUPON_ID"));
						membercoupon.setCouponPrice("100");
						membercoupon.setStartTime(new Date());
						membercoupon.setEndTime(c.getTime());
						membercoupon.setStatus("1");
						couponlist.add(membercoupon);
						sysparam.setParamDesc(membercoupon.getCouponId());
						memberCRMService.update(sysparam);
					}
				}
				for (int i = 0; i < couponlist.size(); i++) {
					memberCRMService.save(couponlist.get(i));
				}
			}
		}
		memberCRMService.save(me);
		memberCRMService.save(bill);
		logService.changeMemberScoreAndReserveLog((LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY), remark, "",request);
		String result = "{\"code\":\"1\"}";
		JSONUtil.responseJSON(response, result);
	}
	
	@RequestMapping("/memberInfo.do")
	public void memberInfo(HttpServletRequest request,
			HttpServletResponse response, String memberid) throws Exception {
		Member member = (Member) memberCRMService.findById(Member.class, memberid);
		JSONUtil.responseJSON(response, member);
	}
	
	/**
	 * 会员卡升级页面
	 * 
	 * @param accountid
	 * @param memberid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/memberUpGrade.do")
	public ModelAndView memberUpGrade(String accountid, String memberid) {
		ModelAndView mv = new ModelAndView();
		Member member = (Member) memberCRMService.findById(Member.class, memberid);
		MemberAccount memberaccount = (MemberAccount) memberCRMService
				.findOneByProperties(MemberAccount.class, "memberId", member
						.getMemberId());
		List<?> memberRank = memberCRMService
				.findBySQL("memberRankDiscount", true);
		if (member.getMemberRank().equals("5")) {
			List<Member> memberlist = memberCRMService.findByProperties(
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

	
	
	/**
	 * 会员卡升级的等级充值
	 * 
	 * @param request
	 * @param response
	 * @param memberrank
	 * @param accountid
	 * @param memberid
	 * @param money
	 * @param paytype
	 * @throws Exception 
	 */
	@RequestMapping("/savaMemberRank.do")
	public void savaMemberRank(HttpServletRequest request,
			HttpServletResponse response, String memberrank, String accountid,
			String memberid, Double money, String paytype)
			throws Exception {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		MemberAccount memberAccount = (MemberAccount) memberCRMService.getCustomerInfo(accountid);
		memberAccount.setCurrBalance(memberAccount.getCurrBalance() + money);
		memberAccount.setTotalRecharge(memberAccount.getTotalRecharge() + money);
		Member member = (Member) memberCRMService.getmember(memberid);
		member.setMemberRank(memberrank);
		member.setRecordTime(new Date());
		
		Bill bill = new Bill();
		String billId = DateUtil.currentDate("yyMMdd") + loginUser.getStaff().getBranchId() + memberCRMService.getSequence("SEQ_NEW_BILL");
		bill.setBillId(billId);
		bill.setBranchId(loginUser.getStaff().getBranchId());
		bill.setCheckId(member.getMemberId());
		bill.setProjectId("2002");
		bill.setProjectName("现金");
		bill.setDescribe("充值");
		bill.setPayment("1");
		bill.setCost(0.0);
		bill.setPay(money);
		bill.setStatus("1");
		bill.setRecordTime(new Date());
		bill.setRecordUser(loginUser.getStaff().getStaffId());
		MemberRank rank = (MemberRank) memberCRMService.findOneByProperties(MemberRank.class, "memberRank", memberrank);
		String remark = member.getMemberName() + "会员升级到:" + rank.getRankName();
		// 查询当前门店下已有的工作账
		WorkBill workbill = (WorkBill) memberCRMService.findOneByProperties(WorkBill.class, "branchId", loginUser.getStaff().getBranchId(), 
				"status", CommonConstants.WorkBillStatus.NEW);
		// 记房账
		WorkBillDetail wbDetail = new WorkBillDetail();
		wbDetail.setBranchId(loginUser.getStaff().getBranchId());
		wbDetail.setCost(money);
		wbDetail.setDescribe("会员购卡升级");
		wbDetail.setDetailId(DateUtil.currentDate("yyMMdd") + loginUser.getStaff().getBranchId() + memberCRMService.getSequence("SEQ_NEW_WORKBILLDETAIL", null));
		wbDetail.setPay(money);
		wbDetail.setPayment("1");
		wbDetail.setProjectId("1");
		wbDetail.setProjectName("充值");
		wbDetail.setRecordTime(new Date());
		wbDetail.setRecordUser(loginUser.getStaff().getStaffId());
		wbDetail.setWorkbillId(workbill.getWorkbillId());
		wbDetail.setStatus("1");
		wbDetail.setRemark(remark);
		memberCRMService.save(wbDetail);
		
		
		
		logService.changeMemberScoreAndReserveLog((LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY), remark, "4",request);
		
		SmsSingleSender singleSender = new SmsSingleSender(SystemConstants.note.APPID, SystemConstants.note.APPKEY);
		ArrayList<String> params = new ArrayList<String>();
		
		MemberRank memberRank = (MemberRank) memberCRMService.findOneByProperties(MemberRank.class, "memberRank", member.getMemberRank());
		params.add(member.getMemberName());
		params.add(member.getMemberId());
		params.add(memberRank.getRankName());
		singleSender.sendWithParam(SystemConstants.note.COUNTRY, member.getMobile(), 56658, params, "", "", "");
		
		memberCRMService.update(memberAccount);
		memberCRMService.update(member);
		memberCRMService.save(bill);
		String result = "{\"code\":\"1\"}";
		JSONUtil.responseJSON(response, result);
	}
	/**
	 * 会员卡升级选择
	 * 
	 * @param memberrank
	 * @param accountid
	 * @param memberid
	 * @param memberrankname
	 * @return
	 */
	@RequestMapping("/payUpGradeMemberLevel.do")
	public ModelAndView payUpGradeMemberLevel(String memberrank,
			String accountid, String memberid) {
		ModelAndView mv = new ModelAndView();
		SysParam rank = (SysParam) memberCRMService.findOneByProperties(
				SysParam.class, "paramType", "MEMBERRANK", "paramDesc",
				memberrank);
		MemberRank memberRank = (MemberRank) memberCRMService.findOneByProperties(
				MemberRank.class, "memberRank", memberrank);
		mv.addObject("memberrankmoney", rank.getContent());
		mv.addObject("memberrank", memberrank);
		mv.addObject("accountid", accountid);
		mv.addObject("memberid", memberid);
		mv.addObject("memberrankname", memberRank.getRankName());
		mv.setViewName("page/crm/member/payUpGradeMemberLevel");
		return mv;
	}
	
	
	
	
	/**
	 * 保存，修改冻结会员信息
	 * 
	 * @param request
	 * @param response
	 * @param MEMBERID
	 * @throws Exception
	 */
	@RequestMapping("/saveFreezeMember.do")
	public void saveFreezeMember(HttpServletRequest request,HttpServletResponse response, String MEMBERID) throws Exception {
		FreezeMember fm = (FreezeMember) ReflectUtil.setBeansFromJsonArray(request, "FreezeMember").get(0);
		if (null == request.getParameter("PHOTOS")) {
			fm.setPhotos(((FreezeMember) logService.findById(FreezeMember.class, MEMBERID)).getPhotos());
			logService.sessionClear();
		}
		logService.freezeMemberLog((LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY), fm, "3",request);

		memberCRMService.update(fm);
		
	}
	
	@RequestMapping("/recoverMember.do")
	public void recoverMember(HttpServletRequest request,HttpServletResponse response, String memberid,String nowMobile) throws Exception {
		FreezeMember fm = (FreezeMember) memberCRMService.findById(FreezeMember.class, memberid);
		String idcard = fm.getIdcard();
		String loginName = fm.getLoginName();
		String rank = fm.getMemberRank();
		List<?> listOne = memberCRMService.findByProperties(Member.class, "idcard", idcard);
		List<?> listTwo = memberCRMService.findByProperties(Member.class, "mobile", nowMobile);
		List<?> listThree = memberCRMService.findByProperties(Member.class, "loginName", loginName);
		if(listOne.size() > 0){
			if("0".equals(rank)){
				JSONUtil.responseJSON(response, new AjaxResult(4, "营业执照已存在!"));
			}else{
				JSONUtil.responseJSON(response, new AjaxResult(2, "身份证已存在!"));
			}	
		}else if(listTwo.size() > 0){
			JSONUtil.responseJSON(response, new AjaxResult(3, "手机号码已存在!"));
		}else if(listThree.size() > 0){
			JSONUtil.responseJSON(response, new AjaxResult(5, "登录名已存在!"));
		}else{
			Member member = new Member();
			member.setAddress(fm.getAddress());
			member.setAppToken(fm.getAppToken());
			member.setBirthday(fm.getBirthday());
			member.setCorporationId(fm.getCorporationId());
			member.setDiscount(fm.getDiscount());
			member.setEmail(fm.getEmail());
			member.setGendor(fm.getGendor());
			member.setIdcard(fm.getIdcard());
			member.setInvalidTime(fm.getInvalidTime());
			member.setLoginName(fm.getLoginName());
			member.setMemberId(fm.getMemberId());
			member.setMemberName(fm.getMemberName());
			member.setMemberRank(fm.getMemberRank());
			member.setMobile(nowMobile);
			member.setOpenId(fm.getOpenId());
			member.setPassword(fm.getPassword());
			member.setPhotos(fm.getPhotos());
			member.setPostcode(fm.getPostcode());
			member.setRecommend(fm.getRecommend());
			member.setRecordTime(fm.getRecordTime());
			member.setRegisterTime(fm.getRegisterTime());
			member.setRemark(fm.getRemark());
			member.setSource(fm.getSource());
			member.setStatus(fm.getStatus());
			member.setValidTime(fm.getValidTime());
			memberCRMService.save(member);
			memberCRMService.delete(fm);
			JSONUtil.responseJSON(response, new AjaxResult(1, "解冻成功!"));
		}
		
		
	}
	
	public void delMember(String memberid, LoginUser loginUser,HttpServletRequest request)
			throws UnknownHostException {
		String[] memberids = memberid.split(",");
		Member member;
		MemberAccount ma;
		List<Object> list = new ArrayList<Object>();
		for (int i = 0; i < memberids.length; i++) {
			member = (Member) memberCRMService
					.findById(Member.class, memberids[i]);
			if (member != null) {
				member.setStatus("0");
				member.setRecordTime(new Date());
				logService.memberLog(loginUser, member, "2",request);
				list.add(member);
			}
			ma = (MemberAccount) memberCRMService.findOneByProperties(
					MemberAccount.class, "status", "1", "memberId",
					memberids[i]);
			if (ma != null) {
				ma.setStatus("0");
				ma.setRecordTime(new Date());
				list.add(ma);
			}
		}

		if (!list.isEmpty()) {
			memberCRMService.saveOrUpdateAll(list);
		}
	}
}
