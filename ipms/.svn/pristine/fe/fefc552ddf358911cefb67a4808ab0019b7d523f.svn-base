package com.ideassoft.pmsinhouse.controller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.DecimalFormat;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ideassoft.bean.Bill;
import com.ideassoft.bean.Branch;
import com.ideassoft.bean.CheckUser;
import com.ideassoft.bean.House;
import com.ideassoft.bean.Member;
import com.ideassoft.bean.MemberAccount;
import com.ideassoft.bean.OperateLog;
import com.ideassoft.bean.OrdchePrice;
import com.ideassoft.bean.Order;
import com.ideassoft.bean.Password;
import com.ideassoft.bean.RoomStatus;
import com.ideassoft.bean.Staff;
import com.ideassoft.bean.SysParam;
import com.ideassoft.core.ajax.AjaxResult;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.constants.CommonConstants;
import com.ideassoft.core.constants.CommonConstants.warningLogStatus;
import com.ideassoft.core.constants.SystemConstants;
import com.ideassoft.core.constants.CommonConstants.BillPayment;
import com.ideassoft.core.constants.CommonConstants.DefaultRoomPrice;
import com.ideassoft.core.exception.DAOException;
import com.ideassoft.core.notifier.SmsSingleSender;
import com.ideassoft.crm.service.TransferServcie;
import com.ideassoft.pmsinhouse.service.HouseOrderService;
import com.ideassoft.price.MultiPrice;
import com.ideassoft.price.RealPrice;
import com.ideassoft.util.ChineseCharacterUtil;
import com.ideassoft.util.DateUtil;
import com.ideassoft.util.JSONUtil;
import com.ideassoft.util.MD5Util;
import com.ideassoft.util.NotifyStewardUtil;
import com.ideassoft.util.RandomNumberUtil;
import com.ideassoft.util.RegExUtil;
import com.ideassoft.util.RequestUtil;
import com.ideassoft.util.RoomUtil;
import com.ideassoft.util.SpringUtil;
import com.ideassoft.util.WeixinUtil;

@Controller
public class HouseLeftMenuOrderController {


	@Autowired
	private HouseOrderService houseOrderService;

	/*
	 * 加载所有民宿
	 */
	@RequestMapping("/allhouse.do")
	public ModelAndView allhouse(HttpServletRequest request){
		String startTime =request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		List<?> list = houseOrderService.findBySQL("queryHouseListOrder", true);
		ModelAndView mv = new ModelAndView();
//		mv.setViewName("page/ipmspage/leftmenu/houseOrder/houseChoose");
		mv.addObject("dialogColumns", "houseId,housename");
		mv.addObject("dialogTarget", "House");
		mv.addObject("dialogConditions", "status<>'0'");
		mv.addObject("dialogQuickAdd", null);
		mv.addObject("selectType", "dialog-radio");
		mv.addObject("colName", "houseName");

		mv.setViewName("page/ipmshouse/leftmenu/houseOrder/myCustomDialog");
//		mv.addObject("result", list);
		mv.addObject("startTime",startTime);
		mv.addObject("endTime",endTime);
		return mv;
	}
	
	@RequestMapping("/ordermemberAddInHouse.do")
	public ModelAndView ordermemberAddInHouse(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/ipmshouse/leftmenu/leftorder/ordermemberadd");
		return mv;
	}
	
	@RequestMapping("/uniqueIdcardInHouse.do")
	public void uniqueIdcardInHouse (HttpServletRequest request, HttpServletResponse response, String idcard) {
		Member member = (Member) houseOrderService.findOneByProperties(Member.class, "idcard", idcard, "status", "1");
		if(member != null){
			JSONUtil.responseJSON(response, new AjaxResult(0, "身份证重复!"));
		} else {
			JSONUtil.responseJSON(response, new AjaxResult(1, "可以注册"));
		}
	}
	/**
	 * 
	 * @author ideas_mengl
	 * @date   2018年5月18日
	 * @param request
	 * @param response
	 * @param orderarrivedate
	 * @param orderleavedate
	 * @param orderprice
	 * @param roomremark
	 * @param checkuserarray
	 * @param houseId
	 * @param source
	 * @param receptionremark
	 * @throws ParseException
	 */
	@RequestMapping("/orderHouse.do")
	public void orderHouse(HttpServletRequest request, HttpServletResponse response, String orderarrivedate,
			String orderleavedate, String orderprice, String roomremark, String checkuserarray, String houseId, String source, String receptionremark) throws ParseException{
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String staffid = loginUser.getStaff().getStaffId();
		boolean checkinFree = Boolean.parseBoolean(request.getParameter("checkinFree"));
		try {
			String memberName = "";
			String memberMobile = "";
			JSONArray array = new JSONArray(checkuserarray);
			Order order = new Order();
			String orderId = DateUtil.currentDate("yyMMdd") + houseId + source
					+ houseOrderService.getSequence("SEQ_NEW_ORDER");
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = array.getJSONObject(i);
				//取消实名校验
				String checkIdCard = checkIdCard(obj.getString("name"),obj.getString("idcard"));
				if("-1".equals(checkIdCard) || "2".equals(checkIdCard)){
					JSONUtil.responseJSON(response, new AjaxResult(2,"你的姓名和身份证不符!"));
					return;
				}
				CheckUser cu = new CheckUser();
				String cuId = houseId+ source + houseOrderService.getSequence("SEQ_NEW_CHECKUSER");
				Member mb = new Member();
				//只查非会员
				Member ismb = (Member) houseOrderService.findOneByProperties(Member.class, "idcard", obj.get("idcard"),"status", "1","memberRank","1");
				Member ismbtwo = (Member) houseOrderService.findOneByProperties(Member.class, "mobile", obj.get("mobile"), "status","1", "memberRank","1");
				MemberAccount account = new MemberAccount();
				if(ismb == null){//身份证无
					if(ismbtwo != null){//手机号码存在
//						List<?> phoneM =  houseOrderService.findByProperties(Member.class, "mobile", obj.get("mobile"),"status","1","memberRank","1");
//						if(phoneM.size() >= 2){
							JSONUtil.responseJSON(response, new AjaxResult(2, "手机号:"+obj.get("mobile")+"已存在注册信息,请更新会员信息!"));
							return ;
						//}
						//注册
						/*ismbtwo.setIdcard("".equals(obj.get("idcard").toString()) ? ismbtwo.getIdcard() : obj.get("idcard").toString());
						ismbtwo.setMemberName(obj.getString("name").toString());
						ismbtwo.setGendor(obj.get("gender").toString());
						houseOrderService.update(ismbtwo);
						if (i == 0) {
							order.setOrderUser(ismbtwo.getMemberId());//预订人
							order.setmPhone(obj.getString("mobile"));// 预订人手机
						}*/
					}else{
						String mbId = houseOrderService.getSequence("SEQ_MEMBER_ID");
						String accountId = houseOrderService.getSequence("SEQ_ACCOUNT_ID");
						mb.setMemberId(mbId);
						mb.setMemberName(obj.getString("name"));
						mb.setLoginName("guest");
						if("".equals(obj.getString("idcard").toString())){
							mb.setPassword(MD5Util.getCryptogram("888888"));
						}else{
							mb.setPassword(MD5Util.getCryptogram(obj.getString("idcard").substring(obj.getString("idcard").length()-6)));
							
						}
						mb.setMemberRank(CommonConstants.MembenRank.NM);
						mb.setIdcard(obj.getString("idcard"));
						mb.setGendor(obj.getString("gender"));
						mb.setMobile(obj.getString("mobile"));
						mb.setSource(source);//来源
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
						if (i == 0) {
							memberName = obj.getString("name");
							memberMobile = obj.getString("mobile");
							cu.setCheckuserType(CommonConstants.CheckUserType.HOST);// 主客人
							order.setOrderUser(mbId);//预订人
							order.setmPhone(obj.getString("mobile"));// 预订人手机
						} else {
							cu.setCheckuserType(CommonConstants.CheckUserType.GUEST);// 从客人
						}
						
						houseOrderService.save(mb);
						houseOrderService.save(account);
					}
				}else if(ismb != null && ismbtwo ==null){//身份证存在,手机号不存在
					ismb.setMobile(obj.get("mobile").toString());
					houseOrderService.update(ismb);//更新会员信息
					if (i == 0) {
						memberName = obj.getString("name");
						memberMobile = obj.getString("mobile");
						cu.setCheckuserType(CommonConstants.CheckUserType.HOST);// 主客人
						order.setOrderUser(ismb.getMemberId());//预订人
						order.setmPhone(obj.getString("mobile"));// 预订人手机
					} else {
						cu.setCheckuserType(CommonConstants.CheckUserType.GUEST);// 从客人
					}
					
				}else{//不需要注册,已存在
					Member mm = (Member) houseOrderService.findOneByProperties(Member.class, "idcard", obj.get("idcard"),"status", "1", "mobile", obj.get("mobile"), "memberRank", "1");
					if(mm == null){
						if(ismb != null && ismbtwo != null){
							if(ismbtwo.getMobile().toString().equals(obj.get("mobile"))){
								JSONUtil.responseJSON(response, new AjaxResult(2,"您的手机或身份证已存在会员信息!"));
								return;
							}
							if(ismb.getIdcard().toString().equals(obj.get("idcard"))){
								JSONUtil.responseJSON(response, new AjaxResult(2,"您的手机或身份证已存在会员信息!"));
								return;
							}
						}
					}else{
						mm.setMemberName(obj.getString("name"));
						mm.setGendor(obj.getString("gender"));
						houseOrderService.update(mm);
					}
					
					if (i == 0) {
						memberName = ismb.getMemberName();
						memberMobile = ismb.getMobile();
						cu.setCheckuserType(CommonConstants.CheckUserType.HOST);// 主客人
						order.setOrderUser(ismb.getMemberId());//预订人	
						order.setmPhone(obj.getString("mobile"));// 预订人联系手机
					} else {
						cu.setCheckuserType(CommonConstants.CheckUserType.GUEST);// 从客人
					}
					
					}
				cu.setCheckuserId(cuId);
				cu.setCheckuserName(obj.getString("name"));// 入住人姓名
				cu.setIdcard(obj.getString("idcard"));
				cu.setMobile(obj.getString("mobile"));
				cu.setGender(obj.getString("gender"));
				cu.setStatus("1");
				cu.setRankName("1");	
				cu.setRecordUser(loginUser.getStaff().getStaffId());
				cu.setCheckId(orderId);
				cu.setCheckinType(CommonConstants.CheckinType.ORDER);
				houseOrderService.save(cu);
			}
			
			House house = (House) houseOrderService.findOneByProperties(House.class, "houseId", houseId);
			String flag = house.getFlag();
			if(orderarrivedate.equals(new SimpleDateFormat("yyyy/MM/dd").format(new Date()))){
				house.setStatus("2");
				houseOrderService.update(house);
			}
			if("1".equals(flag)){
				order.setAgreeable("1");
			}
			order.setOrderId(orderId);
			order.setBranchId(houseId);
			order.setSource(source);// 订单来源为分店
			order.setTheme("3");
			order.setRoomId(house.getHouseNo());
			order.setRoomType(house.getHouseType());
			order.setRpId("MSJ");//门市价
			order.setArrivalTime(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(orderarrivedate + " 14:00:00"));
			order.setLeaveTime(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(orderleavedate+ " 12:00:00"));
			order.setCheckoutTime(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(orderleavedate+ " 12:00:00"));
			order.setRoomPrice(Double.valueOf(orderprice));
			order.setUserCheckin(String.valueOf(Double.valueOf(orderprice)));
			order.setMsj(Double.valueOf(orderprice));	
			order.setPaymentType("1");
			order.setGuarantee("2");
			order.setLimited("0:00");
			order.setStatus("1");
			order.setRecordUser(loginUser.getStaff().getStaffId());
			order.setReceptionRemark(receptionremark);
			order.setAutoRefund(CommonConstants.AutoRefundStatus.AUTOREFUND_TRUE);//默认自动退组
			order.setRoomRemark(roomremark);
			order.setAdvanceCash(Double.valueOf(orderprice));
			houseOrderService.save(order);
			
			//存账单 
			SysParam sysParamOnlyTotal = (SysParam)houseOrderService.findOneByProperties(SysParam.class, "paramType", "PROJECT", "content",CommonConstants.BillProject.Deposit);
			Bill billOnlyTotal =new Bill();
			String billIdOnlyTotal = new SimpleDateFormat("yyMMdd").format(new Date())+houseId+source+houseOrderService.getSequence("SEQ_NEW_BILL");
			billOnlyTotal.setBillId(billIdOnlyTotal);
			billOnlyTotal.setBranchId(houseId);
			billOnlyTotal.setCheckId(orderId);
			billOnlyTotal.setCost(0.0);
			billOnlyTotal.setPay(Double.valueOf(orderprice));//总房费
			billOnlyTotal.setDescribe(sysParamOnlyTotal.getParamName());
			billOnlyTotal.setPayment("3");
			billOnlyTotal.setProjectId(sysParamOnlyTotal.getContent());
			billOnlyTotal.setProjectName(sysParamOnlyTotal.getParamName());
			billOnlyTotal.setRecordTime(new Date());
			billOnlyTotal.setRecordUser(loginUser.getStaff().getStaffId());
			billOnlyTotal.setStatus(CommonConstants.BillStatus.BillNormal);
			billOnlyTotal.setType("1");
			houseOrderService.save(billOnlyTotal);
			//存保洁
			SysParam sysParamcleanBill = (SysParam)houseOrderService.findOneByProperties(SysParam.class, "paramType", "PROJECT", "content",CommonConstants.BillProject.CLEANPRICE);
			Bill cleanBill = new Bill();
			String cleanBillId = new SimpleDateFormat("yyMMdd").format(new Date())+houseId+source+houseOrderService.getSequence("SEQ_NEW_BILL");
			cleanBill.setBillId(cleanBillId);
			cleanBill.setBranchId(houseId);
			cleanBill.setCheckId(orderId);
			cleanBill.setCost(0.0);
			cleanBill.setPay(0.0);
			cleanBill.setDescribe(sysParamcleanBill.getParamName());
			cleanBill.setPayment("3");
			cleanBill.setProjectId(sysParamcleanBill.getContent());
			cleanBill.setProjectName(sysParamcleanBill.getParamName());
			cleanBill.setRecordTime(new Date());
			cleanBill.setRecordUser(loginUser.getStaff().getStaffId());
			cleanBill.setStatus(CommonConstants.BillStatus.BillNormal);
			cleanBill.setType("1");
			houseOrderService.save(cleanBill);
			//存服务费
			SysParam sysParamserviceBill = (SysParam)houseOrderService.findOneByProperties(SysParam.class, "paramType", "PROJECT", "content",CommonConstants.BillProject.Service);
			Bill serviceBill = new Bill();
			String serviceBillId = new SimpleDateFormat("yyMMdd").format(new Date())+houseId+source+houseOrderService.getSequence("SEQ_NEW_BILL");
			serviceBill.setBillId(serviceBillId);
			serviceBill.setBranchId(houseId);
			serviceBill.setCheckId(orderId);
			serviceBill.setCost(0.0);
			serviceBill.setPay(0.0);
			serviceBill.setDescribe(sysParamserviceBill.getParamName());
			serviceBill.setPayment("3");
			serviceBill.setProjectId(sysParamserviceBill.getContent());
			serviceBill.setProjectName(sysParamserviceBill.getParamName());
			serviceBill.setRecordTime(new Date());
			serviceBill.setRecordUser(loginUser.getStaff().getStaffId());
			serviceBill.setStatus(CommonConstants.BillStatus.BillNormal);
			serviceBill.setType("1");
			houseOrderService.save(serviceBill);
			//存押金
			SysParam sysParamdepositBill = (SysParam)houseOrderService.findOneByProperties(SysParam.class, "paramType", "PROJECT", "content",CommonConstants.BillProject.ForeGift);
			Bill depositBill = new Bill();
			String depositBillId = new SimpleDateFormat("yyMMdd").format(new Date())+houseId+source+houseOrderService.getSequence("SEQ_NEW_BILL");
			depositBill.setBillId(depositBillId);
			depositBill.setBranchId(houseId);
			depositBill.setCheckId(orderId);
			depositBill.setCost(0.0);
			depositBill.setPay(0.0);
			depositBill.setDescribe(sysParamdepositBill.getParamName());
			depositBill.setPayment("3");
			depositBill.setProjectId(sysParamdepositBill.getContent());
			depositBill.setProjectName(sysParamdepositBill.getParamName());
			depositBill.setRecordTime(new Date());
			depositBill.setRecordUser(loginUser.getStaff().getStaffId());
			depositBill.setStatus(CommonConstants.BillStatus.BillNormal);
			depositBill.setType("1");
			houseOrderService.save(depositBill);
			
			//存ordercheckprice每日价格表price,houseid,orderid,orderuser,source
			if(!checkinFree){
				RoomUtil.saveCheckinPrice(DateUtil.s2d(orderarrivedate, "yyyy/MM/dd"), new Date(DateUtil.s2d(orderleavedate, "yyyy/MM/dd").getTime()-24*60*60*1000), null, "MSJ", houseId, "3", orderId, staffid, null);
			}else{
				RoomUtil.saveCheckinPrice(DateUtil.s2d(orderarrivedate, "yyyy/MM/dd"), new Date(DateUtil.s2d(orderleavedate, "yyyy/MM/dd").getTime()-24*60*60*1000), null, "MSJ", houseId, "3", orderId, staffid, "0.0");
			}
//			String password = getpassword(houseId,order.getOrderUser(),order.getArrivalTime(),order.getLeaveTime());
//			sendMessage(order.getOrderId(),"1",memberName,new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(order.getArrivalTime()),new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(order.getLeaveTime()),house.getAddress(),password,memberMobile); 
			// 调用短信提醒所有民宿管理员
			NotifyStewardUtil.notifyStewardUtil(orderarrivedate, orderleavedate, house.getHousename(), houseId, orderprice, memberName, memberMobile);
			JSONUtil.responseJSON(response, new AjaxResult(0,"预订成功"));
			
		} catch (Exception e) {
			JSONUtil.responseJSON(response, new AjaxResult(1,"操作失败"));
			e.printStackTrace();
		}
		
		
		
	}
	
	
	/**
	 * 生成一道门密码并保存
	 * @param houseId
	 * @param memberId
	 * @param start
	 * @param end
	 * @throws Exception
	 */
	public static String getpassword(String houseId,String memberId,Date start,Date end) throws Exception {  
		HouseOrderService houseOrderService=(HouseOrderService)SpringUtil.getBean("houseOrderService");
		String number = RandomNumberUtil.verificationCode(6);
		Password pw = new Password();
		pw.setDataId(DateUtil.currentDate("yyMMdd") +houseId+ houseOrderService.getSequence("SEQ_PASSWORD_ID"));
		pw.setMemberId(memberId);
		pw.setPassword(number);
		pw.setRecordTime(new Date());
        pw.setStarttime(start);
        pw.setEndtime(end);
        houseOrderService.save(pw);
        return number;
	}
	
	/**
	 * 发送订单消息给客户
	 * @param allOrderId
	 * @param roomAmount
	 * @param memberName
	 * @param arriveTime
	 * @param endTime
	 * @param address
	 * @param password
	 * @param memberPhone
	 * @throws Exception
	 */
	public static void sendMessage(String allOrderId,String roomAmount,String memberName,String arriveTime,String endTime,String address,String password,String memberPhone) throws Exception {  
		HouseOrderService houseOrderService=(HouseOrderService)SpringUtil.getBean("houseOrderService");
		Branch branch = (Branch) houseOrderService.findOneByProperties(Branch.class, "branchId", "100001");
		if(branch.getPhone()!=null){
			String servicePhone=branch.getPhone();
			SysParam param = (SysParam) houseOrderService.findOneByProperties(SysParam.class, "paramType","APPID");
			int appid = Integer.parseInt(param.getContent());
			param = (SysParam) houseOrderService.findOneByProperties(SysParam.class, "paramType","APPKEY");
			String appkey = param.getContent();
			SmsSingleSender singleSender = new SmsSingleSender(appid, appkey);
			ArrayList<String> params = new ArrayList<String>();
			params.add(allOrderId);
			params.add(roomAmount);
			params.add(memberName);
			params.add(arriveTime);
			params.add(endTime);
			params.add(address);
			params.add(password);
			params.add(servicePhone);
			singleSender.sendWithParam("86", memberPhone, 122651, params,"","","");
		}
	}
	
	
	/**
	 * 
	 * @Description
	 * @author ideas_mengl
	 * @date  2018年5月18日下午1:57:55
	 * @param name
	 * @param idcard
	 * @return
	 * @throws IOException
	 * @throws JSONException
	 */
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
	
	
	@RequestMapping("/scoreExchangeInHouse.do")
	public ModelAndView scoreExchangeInHouse(HttpServletRequest request, HttpServletResponse response, String MemberId,
			String sjscore) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/ipmspage/leftmenu/leftorder/scoreexchage");
		SysParam sysParamscore = ((SysParam) (houseOrderService.findOneByProperties(SysParam.class, "paramType", "SCORE")));
		SysParam sysParammoney = ((SysParam) (houseOrderService.findOneByProperties(SysParam.class, "paramType", "MONEY")));
		MemberAccount memberscore = ((MemberAccount) (houseOrderService.findOneByProperties(MemberAccount.class,
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
	
	
	@RequestMapping("/memberSelectInHouse.do")
	public void memberSelectInHouse(HttpServletRequest request, HttpServletResponse response, String Mnumber) throws Exception {
		String a = null;
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
			} else {
				// 判断本地端是否有该会员数据
				List<?> memberdata = houseOrderService.getMemberdataInHouse(Mnumber);
				if (memberdata.size() <= 0) {
					memberdata = houseOrderService.findBySQL("queryMemCorId", new String[]{Mnumber}, true);
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
							houseOrderService.save(member);
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
									houseOrderService.save(memberaccount);
								}
							}
							// 本地端有会员数据，从云端更新该会员数据
						} else {
							String memberid = mrrr.getString("MEMBER_ID").toString();
							String mobile = mrrr.getString("MOBILE").toString();
							Member memberupdate = ((Member) (houseOrderService.findOneByProperties(Member.class,
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
							houseOrderService.update(memberupdate);
						}
					}
				}

			}
	}
	
	
	@RequestMapping("/judgeUserInHouse.do")
	public ModelAndView judgeUserInHouse(HttpServletRequest request, HttpServletResponse response, String Mnumber)
			throws Exception {
		ModelAndView mv = new ModelAndView();
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		List<?> memberdata = houseOrderService.getMemberdataInHouse(Mnumber);
		if (memberdata.size() <=0) {
			Member members = (Member) houseOrderService.findOneByProperties(Member.class, "corporationId", Mnumber, "status", "1");
			if (members.getMobile() != null) {
				memberdata = houseOrderService.getMemberdataInHouse(members.getMobile().toString());
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
		List<?> guarantee = houseOrderService.getGuaranteeInHouse();
		String niub = "[";
		for (int i = 0; i < pricess.size(); i++) {
			niub += pricess.get(i).toString().substring(1,pricess.get(i).toString().length()-1)+",";
		}
		niub = niub.substring(0,niub.length()-1)+"]";
		mv.setViewName("page/ipmspage/leftmenu/leftorder/judgeusercheckin");// 二级页面
		request.setAttribute("memberdata", memberdata);
		request.setAttribute("roomprice", niub);
		request.setAttribute("guarantee", guarantee);
		request.setAttribute("rpId", rpId);
		request.setAttribute("discount", discount);
		return mv;
	}
	
	
	@SuppressWarnings( { "deprecation", "unchecked" })
	@RequestMapping("/orderRoomInHouse.do")
	public void orderRoomInHouse(HttpServletRequest request, HttpServletResponse response, String ordertheme,
			String orderroomtype, String orderarrivedate, String orderleavedate, String orderacount, String orderrpid,
			String orderprice, String orderuser, String ordermobile, String orderlimited, String receptionremark,
			String roomremark, String ordercash, String ordercard, String ordervoucher, String orderscore,
			String ordergurantee, String checkuserarray,String membergendor) throws Exception {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		Member member = ((Member) (houseOrderService.findOneByProperties(Member.class,  "mobile",ordermobile)));
		String memberid = null;
		SysParam systemtype = (SysParam) houseOrderService.findOneByProperties(SysParam.class, "paramType", "SYSTEMTYPE",
				"status", "1");
		String stype = systemtype.getContent().toString();
		if (null == member) {
			com.ideassoft.portal.DataDealPortalService service = new com.ideassoft.portal.DataDealPortalService();
			com.ideassoft.portal.IDataDealPortal portal = service.getDataDealPortalPort();
			String mr = portal.call(1, 1, "{ function: \"checkInDirect.getMemberBean\", data:{idcard:" + ordermobile
					+ " } }");
			if (mr.equals("-1")) {
				String memberaddid = this.houseOrderService.getCloudSequence("SEQ_MEMBER_ID", null);
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
				houseOrderService.save(menberadd);
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
				String accountid = this.houseOrderService.getCloudSequence("SEQ_ACCOUNT_ID", null);
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
				houseOrderService.save(memberaccount);
				if (stype.equals(CommonConstants.SystemType.Branch)) {
					int priority = 1;
					SysParam param = (SysParam) houseOrderService.findOneByProperties(SysParam.class, "paramType",
							"REMOTE_PATH", "status", "1");
					List<MemberAccount> memberaccountlList = new ArrayList<MemberAccount>();
					memberaccountlList.add(memberaccount);
					Map<String, Object> memberaccountMap = new HashMap<String, Object>();
					memberaccountMap.put("MemberAccount", memberaccountlList);
					// TransferServcie.getInstance().transferData(priority, param.getContent(),JSONUtil.fromObject(memberaccountMap).toString());
				}
				Member membernew = ((Member) (houseOrderService.findOneByProperties(Member.class, "memberName",
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
						houseOrderService.save(memberdown);
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
		List<?> roomBed = houseOrderService.getRoombedsInHouse(branchid, orderroomtype);
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
			List<List<?>> ordermsj = RoomUtil.getNowRoomPrice(branchid, CommonConstants.DefaultRoomPrice.DEFAULT_RP_ID, orderroomtype);
			String ordermsjrp = ((Map<?, ?>) ordermsj.get(0).get(0)).get("ROOMPRICE").toString();
			
			Double pricemsj = Double.parseDouble(ordermsjrp);
			String logId = this.houseOrderService.getSequence("SEQ_NEW_ORDER", null);
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
			orderLog.setLogId(DateUtil.currentDate("yyMMdd") + branchid + houseOrderService.getSequence("SEQ_OPERATELOG_ID"));
			orderLog.setContent("新的订单:" + order.getOrderId() + "生成成功!");
			String operid = InetAddress.getLocalHost().toString();// IP地址
			operid = (String) operid.subSequence(operid.indexOf("/") + 1, operid.length());
			orderLog.setOperIp(operid);
			orderLog.setOperModule("生成新订单");
			orderLog.setOperType("order");
			orderLog.setRecordTime(new Date());
			orderLog.setRecordUser(loginuser.getStaff().getStaffId());
			houseOrderService.save(orderLog);
			
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
					+ houseOrderService.getSequence("SEQ_NEW_BILL");
			bill.setBillId(billId);
			bill.setBranchId(branchid);
			bill.setCheckId(order.getOrderId());
			bill.setCost(0.00);
			bill.setDescribe("预存");
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
			houseOrderService.save(order);
			
			try {
				saveEveryDayPrice(request, response, datId, orderroomtype);
			} catch (Exception e) {
				e.printStackTrace();
				JSONUtil.responseJSON(response, new AjaxResult(1, "预定失败！"));
			}
			Map<String, Map<String, Object>> priceList = queryRoomPrice(request, response, datId, orderroomtype);
			order.setRoomPrice(Double.valueOf(priceList.get("totalPrices").get("totalPrices").toString()));
			houseOrderService.update(order);
			
			houseOrderService.save(bill);		

			RoomStatus roomstatus = (RoomStatus) houseOrderService.findOneByProperties(RoomStatus.class, "branchId", branchid, "roomType", order.getRoomType());
			roomstatus.setValidnum(roomstatus.getValidnum() + 1);
			houseOrderService.update(roomstatus);
			
			//预定时预付款记操作日志表
			OperateLog 	orderBillLog = new OperateLog();
			orderBillLog.setBranchId(loginuser.getStaff().getBranchId());
			orderBillLog.setLogId(DateUtil.currentDate("yyMMdd") + branchid + houseOrderService.getSequence("SEQ_OPERATELOG_ID"));
			orderBillLog.setContent("订单号：" + order.getOrderId() + "预付了" + bill.getPay());
			orderBillLog.setOperIp(operid);
			orderBillLog.setOperModule("订单预付款");
			orderBillLog.setOperType("order");
			orderBillLog.setRecordTime(new Date());
			orderBillLog.setRecordUser(loginuser.getStaff().getStaffId());
			houseOrderService.save(orderBillLog);	

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
						String checklogId = this.houseOrderService.getSequence("SEQ_NEW_CHECKUSER", null);
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
						houseOrderService.save(checkuser);
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
							String checklogId = this.houseOrderService.getSequence("SEQ_NEW_CHECKUSER", null);
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
							houseOrderService.save(checkuser);
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
			List<?> orderroomname = houseOrderService.getOrderroomname(ordertheme, branchid, orderroomtype);
			String roomtypename = ((Map<?, ?>) orderroomname.get(0)).get("ROOMNAME").toString(); 
			Branch branchmessage = ((Branch) (houseOrderService.findOneByProperties(Branch.class,"branchId", branchid, "status", "1"))); 
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
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/OrderdiscountPriceInHouse.do")
	public void OrderdiscountPriceInHouse(HttpServletRequest request, HttpServletResponse response) throws ParseException{
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
	
	
	/**
	 * 
	 * @author ideas_mengl
	 * @date   2018年5月18日
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/houseChoose.do")
	public void houseChoose(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String startTime = request.getParameter("startTime").replace("-", "/");
		String endTime = request.getParameter("endTime").replace("-", "/");
		String houseId = request.getParameter("houseId");
		long currentDate = new SimpleDateFormat("yyyy/MM/dd").parse(startTime).getTime();
		long futureDate = new SimpleDateFormat("yyyy/MM/dd").parse(endTime).getTime();
		int day = (int)((futureDate - currentDate)/(24*60*60*1000));
		for (int i = 0; i < day; i++) {
			long newcurrentDate = currentDate + 24*60*60*1000*i;
			String newdate = new SimpleDateFormat("yyyy/MM/dd").format(new Date(newcurrentDate));
			@SuppressWarnings("unchecked")
			List<Map<?,?>> list = houseOrderService.findBySQL("houseForwardStatus", new String[] { newdate,
					newdate,  newdate, newdate,newdate , houseId }, true);
			for (int j = 0; j < list.size(); j++) {
				if("占用".equals(list.get(j).get("NUMSS"))){
					JSONUtil.responseJSON(response, new AjaxResult(0,"该民宿已被占用!"));
					return;
				}
			}
			
		}
		//查民宿是否存在订单
		
		String result = reserveableByDate(houseId,startTime,endTime);
		if( !result.equals(String.valueOf(SystemConstants.PortalResultCode.NULL))){
			JSONUtil.responseJSON(response, new AjaxResult(2,"该民宿已存在订单!"));
			return;
		}
	/*//	查远期房价
		String price = getDayofPrice(startTime, endTime, houseId);
	*/MultiPrice multiPrice = null;
		if(startTime.equals(endTime)){
			multiPrice = new MultiPrice(houseId, "3", null, "MSJ", RealPrice.PriceType.DATE, new SimpleDateFormat("yyyy/MM/dd").parse(startTime) ,new SimpleDateFormat("yyyy/MM/dd").parse(endTime));
	}else{
		multiPrice = new MultiPrice(houseId, "3", null, "MSJ", RealPrice.PriceType.DATE, new SimpleDateFormat("yyyy/MM/dd").parse(startTime) ,new Date((new SimpleDateFormat("yyyy/MM/dd").parse(endTime).getTime())-24*60*60*1000));

	}
		Double checkRoomPrice = multiPrice.checkRoomPrice();
		JSONUtil.responseJSON(response, new AjaxResult(1,checkRoomPrice.toString()));
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
	
	
	@RequestMapping("/formOmaddInHouse.do")
	public void formOmaddInHouse(HttpServletRequest request, HttpServletResponse response, String ordermaddjson)
			throws Exception {
		JSONArray ordermaddarray = new JSONArray(ordermaddjson);
		SysParam systemtype = (SysParam) houseOrderService.findOneByProperties(SysParam.class, "paramType", "SYSTEMTYPE",
				"status", "1");
		String stype = systemtype.getContent().toString();
		for (int i = 0; i < ordermaddarray.length(); i++) {
			JSONObject jsonordermadd = (JSONObject) ordermaddarray.get(i);
			String memberid = this.houseOrderService.getCloudSequence("SEQ_MEMBER_ID", null);
			String membername = jsonordermadd.getString("omembername");
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
			String mr = null;
//			if (HeartBeatClient.heartbeat) {
				com.ideassoft.portal.DataDealPortalService service = new com.ideassoft.portal.DataDealPortalService();
				com.ideassoft.portal.IDataDealPortal portal = service.getDataDealPortalPort();
                mr = portal.call(1, 1, "{ function: \"checkInDirect.getMemberBean\", data:{idcard:" + mobile
						+ " } }");
//			}
			if (((mr.equals("-1"))==false)&&((mr.equals(null))==false)) {
				JSONUtil.responseJSON(response, new AjaxResult(0, "手机号已注册!", null));
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
			houseOrderService.save(member);
			if (stype.equals(CommonConstants.SystemType.Branch)) {
				int priority = 1;
				SysParam param = (SysParam) houseOrderService.findOneByProperties(SysParam.class, "paramType",
						"REMOTE_PATH", "status", "1");
				List<Member> memberlList = new ArrayList<Member>();
				memberlList.add(member);
				Map<String, Object> memberMap = new HashMap<String, Object>();
				memberMap.put("Member", memberlList);
				// TransferServcie.getInstance().transferData(priority, param.getContent(),JSONUtil.fromObject(memberMap).toString());
			}
			String accountid = this.houseOrderService.getCloudSequence("SEQ_ACCOUNT_ID", null);
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
			houseOrderService.save(memberaccount);
			if (stype.equals(CommonConstants.SystemType.Branch)) {
				int priority = 1;
				SysParam param = (SysParam) houseOrderService.findOneByProperties(SysParam.class, "paramType",
						"REMOTE_PATH", "status", "1");
				List<MemberAccount> memberaccountlList = new ArrayList<MemberAccount>();
				memberaccountlList.add(memberaccount);
				Map<String, Object> memberaccountMap = new HashMap<String, Object>();
				memberaccountMap.put("MemberAccount", memberaccountlList);
				// TransferServcie.getInstance().transferData(priority, param.getContent(),JSONUtil.fromObject(memberaccountMap).toString());
			}
		}

		JSONUtil.responseJSON(response, new AjaxResult(1, null));
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
				+ houseOrderService.getSequence("SEQ_PRICE_DATAID");
			ordChePrice.setDataId(dataId);
			ordChePrice.setDayPrice(Double.valueOf(prices.getValue().toString()));
			ordChePrice.setOrderId(orderId);
			ordChePrice.setRecordTime(new Date());
			ordChePrice.setRecordUser(loginuser.getStaff().getStaffId());
			ordChePrice.setStatus("1");
			ordChePrice.setWhichDay(sdf.parse(prices.getKey()));
			OrdchePrice oldChePrice = (OrdchePrice) houseOrderService.findOneByProperties(OrdchePrice.class, "orderId", orderId, "whichDay", sdf.parse(prices.getKey()), "status", "1");
			if (oldChePrice != null) {
				oldChePrice.setStatus("0");
				newlist.add(oldChePrice);
				houseOrderService.saveOrUpdateAll(newlist);
			}
			
			try {
				houseOrderService.save(ordChePrice);
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
		Order orderdata = (Order) houseOrderService.findOneByProperties(Order.class, "orderId", orderId, "branchId", branchId);
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
	 * @Description 民宿预定查询会员按钮
	 * @author ideas_mengl
	 * @date   2018年6月20日上午10:59:27
	 * @param request
	 * @param response
	 */
	@RequestMapping("/housememberselect.do")
	public void housememberselect(HttpServletRequest request, HttpServletResponse response){
		String memberMsg = request.getParameter("memberMsg");
		List<?> phoneM =  houseOrderService.findByProperties(Member.class, "mobile", memberMsg,"status","1","memberRank","1");
		List<?>idcardM = houseOrderService.findByProperties(Member.class, "idcard", memberMsg,"status","1","memberRank","1");
		//查出会员信息
		if(phoneM.size() == 1 || idcardM.size() == 1){
			JSONUtil.responseJSON(response, new AjaxResult(1, "会员信息!",phoneM.size()==0 ? idcardM : phoneM));
			return ;
		}
		//多条手机注册信息
		/*if(phoneM.size() >= 2){
			JSONUtil.responseJSON(response, new AjaxResult(2, "已存在多条注册信息,请更新会员信息!"));
			return ;
		}*/
		if(phoneM.size() == 0 && idcardM.size() == 0){
			JSONUtil.responseJSON(response, new AjaxResult(2, "不存在会员信息!"));
			return ;
		}
		
	}
	
}
