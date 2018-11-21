package com.ideassoft.pmsinhouse.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
import org.apache.log4j.Logger;
import org.hsqldb.lib.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.ideassoft.basic.controller.DoorController;
import com.ideassoft.bean.Bill;
import com.ideassoft.bean.Check;
import com.ideassoft.bean.CheckUser;
import com.ideassoft.bean.Equipment;
import com.ideassoft.bean.House;
import com.ideassoft.bean.Member;
import com.ideassoft.bean.OperateLog;
import com.ideassoft.bean.OrdchePrice;
import com.ideassoft.bean.Order;
import com.ideassoft.bean.RepairApply;
import com.ideassoft.bean.RoomPlan;
import com.ideassoft.bean.Staff;
import com.ideassoft.bean.SwitchRoom;
import com.ideassoft.bean.SysParam;
import com.ideassoft.bean.wrapper.MultiQueryCheck;
import com.ideassoft.bean.WarningLog;
import com.ideassoft.core.ajax.AjaxResult;
import com.ideassoft.core.annotation.interfaces.RightMethodControl;
import com.ideassoft.core.annotation.interfaces.RightModelControl;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.constants.CommonConstants;
import com.ideassoft.core.constants.ErrorConstants;
import com.ideassoft.core.constants.RightConstants;
import com.ideassoft.core.constants.SystemConstants;
import com.ideassoft.core.exception.DAOException;
import com.ideassoft.core.jdbc.SqlBuilder;
import com.ideassoft.core.page.Pagination;
import com.ideassoft.exception.BusinessException;
import com.ideassoft.pmsinhouse.service.HouseCheckService;
import com.ideassoft.price.MultiPrice;
import com.ideassoft.price.RealPrice;
import com.ideassoft.price.SinglePrice;
import com.ideassoft.util.CardUtil;
import com.ideassoft.util.CloneUtil;
import com.ideassoft.util.DateUtil;
import com.ideassoft.util.JSONUtil;
import com.ideassoft.util.PriceUtil;
import com.ideassoft.util.RequestUtil;
import com.ideassoft.util.RoomUtil;

@Transactional
@Controller
@RightModelControl(rightModel = RightConstants.RightModel.HOUSE_CONTROL)
public class HouseCheckController {
	
	private static Logger log = Logger.getLogger(HouseCheckController.class);
	@Autowired
	private HouseCheckService houseCheckService;
	
	@Autowired
	private HouseOrderController houseOrderController;
	
	@RequestMapping("HouseCheckAll.do")
	public ModelAndView houseCheckAll(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String orderUser = request.getParameter("orderUser");
		String memberId = request.getParameter("memberId");
		String memberName = request.getParameter("memberName");
		String mobile = request.getParameter("mobile");
		String source = "0".equals(request.getParameter("source")) ? "" : request.getParameter("source");
		String roomType = request.getParameter("roomType");
		String recordUser = request.getParameter("recordUser");
		String checkinTime = request.getParameter("checkinTime");
		String checkoutTime = request.getParameter("checkoutTime");
		Pagination pagination = SqlBuilder.buildPagination(request);
		//List<House> houses = houseCheckService.findByProperties(House.class, "staffId", loginUser.getStaff().getStaffId());
		List<?> houses = houseCheckService.findBySQL("fHouseWithStaffid",new String[]{loginUser.getStaff().getStaffId()}, true);
		String branchId = "";
		if (houses.size() > 0) {
			for (Object house : houses) {
				branchId +="'" + ((Map<?,?>)house).get("HOUSE_ID") + "',";
			}
			branchId = branchId.substring(0, branchId.length()-1);
		}
		//List<?> list = houseCheckService.findBySQLWithPagination("quertHouseCheck", new String[] { "1" , memberName, mobile, loginUser.getStaff().getStaffId(), ""}, pagination, true);
		/*List<?> list = houseCheckService.findBySQLWithPagination("quertHouseCheck", new String[] { "1" , memberName, mobile, loginUser.getStaff().getStaffId(),
				loginUser.getStaff().getStaffId(), ""}, pagination, true);*/
		List<?> list = houseCheckService.findBySQLWithPagination(
				"quertHouseCheck", new String[] { "1", orderUser, memberId,
						memberName, mobile,source, roomType,recordUser, checkinTime,checkoutTime,
						loginUser.getStaff().getStaffId(),"" }, pagination,
				true);
		mv.addObject("list", list);
		mv.addObject("pagination", pagination);
		mv.addObject("memberName", memberName);
		mv.addObject("mobile", mobile);
		mv.addObject("action", "HouseCheckAll.do");
		mv.setViewName("page/ipmshouse/housecheck/housecheckall");
		return mv;
	}
	
	@RequestMapping("HouseToDayWaitCheckOut.do")
	public ModelAndView houseToDayWaitCheckOut(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String orderUser = request.getParameter("orderUser");
		String memberId = request.getParameter("memberId");
		String memberName = request.getParameter("memberName");
		String mobile = request.getParameter("mobile");
		String source = "0".equals(request.getParameter("source")) ? "" : request.getParameter("source");
		String roomType = request.getParameter("roomType");
		String recordUser = request.getParameter("recordUser");
		Pagination pagination = SqlBuilder.buildPagination(request);
		//List<House> houses = houseCheckService.findByProperties(House.class, "staffId", loginUser.getStaff().getStaffId());
		List<?> houses = houseCheckService.findBySQL("fHouseWithStaffid",new String[]{loginUser.getStaff().getStaffId()}, true);

		String branchId = "";
		if (houses.size() > 0) {
			for (Object house : houses) {
				branchId +="'" + ((Map<?,?>)house).get("HOUSE_ID") + "',";
			}
			branchId = branchId.substring(0, branchId.length()-1);
		}
		//List<?> list = houseCheckService.findBySQLWithPagination("quertHouseCheck", new String[] { "1" , memberName, mobile, loginUser.getStaff().getStaffId(), sdf.format(date)}, pagination, true);
		/*List<?> list = houseCheckService.findBySQLWithPagination("quertHouseCheck", new String[] { "1" , memberName, mobile, loginUser.getStaff().getStaffId(), 
				loginUser.getStaff().getStaffId(), sdf.format(date)}, pagination, true);*/
		List<?> list = houseCheckService.findBySQLWithPagination("quertHouseCheck", new String[] { "1" ,orderUser, memberId, memberName, mobile,source, roomType,recordUser,"", "" ,loginUser.getStaff().getStaffId(),sdf.format(date)}, pagination, true);
		mv.addObject("list", list);
		mv.addObject("pagination", pagination);
		mv.addObject("memberName", memberName);
		mv.addObject("mobile", mobile);
		mv.addObject("action", "HouseToDayWaitCheckOut.do");
		mv.setViewName("page/ipmshouse/housecheck/housecheckall");
		return mv;
	}
	
	@RequestMapping("HouseCheckOut.do")
	public ModelAndView houseCheckOut(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String orderUser = request.getParameter("orderUser");
		String memberId = request.getParameter("memberId");
		String memberName = request.getParameter("memberName");
		String mobile = request.getParameter("mobile");
		String source = "0".equals(request.getParameter("source")) ? "" : request.getParameter("source");
		String roomType = request.getParameter("roomType");
		String recordUser = request.getParameter("recordUser");
		String checkinTime = request.getParameter("checkinTime");
		String checkoutTime = request.getParameter("checkoutTime");
		Pagination pagination = SqlBuilder.buildPagination(request);
		//List<House> houses = houseCheckService.findByProperties(House.class, "staffId", loginUser.getStaff().getStaffId());
		List<?> houses = houseCheckService.findBySQL("fHouseWithStaffid",new String[]{loginUser.getStaff().getStaffId()}, true);

		String branchId = "";
		if (houses.size() > 0) {
			for (Object house : houses) {
				branchId +="'" + ((Map<?,?>)house).get("HOUSE_ID") + "',";
			}
			branchId = branchId.substring(0, branchId.length()-1);
		}
		//List<?> list = houseCheckService.findBySQLWithPagination("quertHouseCheck", new String[] { "3" , memberName, mobile, loginUser.getStaff().getStaffId(), ""}, pagination, true);
		/*List<?> list = houseCheckService.findBySQLWithPagination("quertHouseCheck", new String[] { "3" , memberName, mobile, loginUser.getStaff().getStaffId(),
				loginUser.getStaff().getStaffId(), ""}, pagination, true);*/
		List<?> list = houseCheckService.findBySQLWithPagination(
				"quertHouseCheck", new String[] { "3", orderUser, memberId,
						memberName, mobile,source, roomType,recordUser, checkinTime, checkoutTime,
						loginUser.getStaff().getStaffId(),"" }, pagination,
				true);
		mv.addObject("list", list);
		mv.addObject("pagination", pagination);
		mv.addObject("memberName", memberName);
		mv.addObject("mobile", mobile);
		mv.addObject("action", "HouseCheckOut.do");
		mv.setViewName("page/ipmshouse/housecheck/housecheckall");
		return mv;
	}
	
	@RequestMapping("popuphouse.do")
	public ModelAndView popuphouse(HttpServletRequest request,HttpServletResponse response, String houseorderid){
		ModelAndView mv = new ModelAndView("page/ipmshouse/housecheck/popuphouse");
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Check check = (Check) houseCheckService.findOneByProperties(Check.class, "checkId", houseorderid);
		List<?> listhouse = houseCheckService.findhousebystaffid(loginUser.getStaff().getStaffId(), "'" + CommonConstants.HouseStatus.HouseNull + "','Z'", check.getBranchId());
		mv.addObject("listhouses", JSONUtil.fromObject(listhouse));
		mv.addObject("houseorderid", houseorderid);
		return mv;
	}
	
	@RequestMapping("prechangeHouse.do")
	public void prechangHouse(HttpServletRequest request,HttpServletResponse response,
			String chosehouseid, String houseorderid,
			String outStatus, String remark, String money) throws UnknownHostException, ParseException{
		Order order = (Order) houseCheckService.findOneByProperties(Order.class, "orderId", houseorderid);
		Member m = (Member) houseCheckService.findOneByProperties(Member.class, "memberId", order.getOrderUser());
		if(CommonConstants.MembenRank.NM.equals(m.getMemberRank())){
			String memberId = RoomUtil.checkMember(response, m.getMobile(), m.getIdcard());
			if(!StringUtils.isEmpty(memberId) && memberId.length() > 1){
				System.out.println(memberId);
			order.setOrderUser(memberId);
			changHouse(request, response, chosehouseid, houseorderid,
					outStatus, remark, money, null);
			}
		}else{
			changHouse(request, response, chosehouseid, houseorderid,
					outStatus, remark, money, null);
		}
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param chosehouseid
	 * @param houseorderid
	 * @param outStatus
	 * @param remark
	 * @param money
	 * @param memberupdate 判断是否需要会员更新
	 * @throws UnknownHostException
	 * @throws ParseException
	 */
	@RequestMapping("changeHouse.do")
	public void changHouse(HttpServletRequest request,HttpServletResponse response,
			String chosehouseid, String houseorderid,
			String outStatus, String remark, String money, String memberupdate) throws UnknownHostException, ParseException{
		List<?> bills = houseCheckService.findByProperties(Bill.class, "checkId", houseorderid, 
				"status", CommonConstants.BillStatus.NonPayment);
		Order order = (Order) houseCheckService.findOneByProperties(Order.class, "orderId", houseorderid);
		String orderHouseId = order.getBranchId();
		//res 为-1说明没有被预定，不等于-1时，说明已经被预定了
		String res1 = houseOrderController.reserveableByDate(chosehouseid, DateUtil.d2s(order.getArrivalTime(), "yyyy/MM/dd"), DateUtil.d2s(order.getLeaveTime(), "yyyy/MM/dd"));
		if(!"-1".equals(res1)){
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, "房间被预定!"));
			return;
		}
		
		if(bills != null && !bills.isEmpty()){
			JSONUtil.responseJSON(response, new AjaxResult(0, "存在未支付补差价账单!"));
			return;
		}
		
		List<?> nopaybills = houseCheckService.findByProperties(Bill.class, "status", CommonConstants.BillStatus.NonPayment);
		boolean hasbook = false;
		for (Object object : nopaybills) {
			Bill bill = (Bill) object;
			String houseid = bill.getRemark();
			if(!StringUtils.isEmpty(houseid) && houseid.startsWith("H") && houseid.length() == 6){
				if(chosehouseid.equals(houseid)){
					hasbook = true;
				}
			}
		}
		
		if(hasbook){
			JSONUtil.responseJSON(response, new AjaxResult(0, "所选民宿已被其他用户操作!"));
			return;
		}
		
		
		
		/**
		 * 注销原因，换房操作不在跟新order中的orderuser
		 */
		//根据前面的页面判断是否更新会员信息
//		String newmemberid = RoomUtil.memupdate(memberupdate, order.getOrderUser());
//		if(newmemberid == null )
//			throw BusinessException.withErrorCode(ErrorConstants.Member.EMPTY_MEMBER);
//		order.setOrderUser(newmemberid);
		
		if(StringUtils.isEmpty(money)){
			money = "0";
		}
		
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		
		//判断补的差价金额，如果小于0则直接开始更改数据表，如果大于0则开始插入账单，等待APP端进行支付
		double fine = Double.valueOf(money);
		if(outStatus.equals("1") || fine <= 0){
			exchangHouse(request, response, chosehouseid, houseorderid, outStatus, remark, money);
			//换房结束将原门锁里身份证都删除，而不删除保洁的身份证信息
			//先查询订单里的所有的身份证，不管状态
			Integer failFlagCount = 0;
			String checkUserLis = "";
			String checkUserLisFail = "";
			Integer result = CommonConstants.PortalResultCode.FAILED;
			List<?> checkUserList = houseCheckService.findByProperties(CheckUser.class, "checkId", order.getOrderId(), "checkinType", "2");
			if(checkUserList != null && checkUserList.size() > 0){
				for (int j = 0; j < checkUserList.size(); j++){
					String idcardUser = ((CheckUser)(checkUserList.get(j))).getIdcard();
					checkUserLis += idcardUser +",";
					if(idcardUser != null && !StringUtil.isEmpty(idcardUser)){
						try {
							Equipment equipment = (Equipment) houseCheckService.findOneByProperties(Equipment.class, "branchId", orderHouseId);
							if(equipment != null){
								result = CardUtil.doorDelCardData(request, response, equipment.getSerialNo(), idcardUser);
							}

							if(result != CommonConstants.PortalResultCode.FAILED && !result.toString().equals(CommonConstants.doorInterfaceResult.FAILED)){
								
							} else {
								
								failFlagCount++;
								checkUserLisFail += idcardUser +",";
								//删除锁中一个身份证信息所有授权失败记日志
								SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
								if(equipment != null){
									WarningLog warningLog = new WarningLog();
									warningLog.setLogId(sdf.format(new Date()) + houseCheckService.getSequence("SEQ_WARNINGLOG_ID"));
									warningLog.setRecordTime(new Date());
									warningLog.setRecordUser(SystemConstants.User.ADMIN_ID);
									warningLog.setWarningDate(new Date());
									warningLog.setStatus(CommonConstants.servieLogStatus.EFFECTIVE);
									warningLog.setWarningType(CommonConstants.warningLogStatus.DEL_ALL_CARD_FAILED);
									warningLog.setRemark(orderHouseId+"删除锁中所有身份证信息中["+idcardUser+"]失败");
									warningLog.setSerialNo(equipment.getSerialNo());
									houseCheckService.save(warningLog);
								}
							}	 
						} catch (Exception e) {
							failFlagCount++;
							checkUserLisFail += idcardUser +",";
							
						}
					}
				}
				
			}
			
			OperateLog operlog = new OperateLog();
			operlog.setBranchId(order.getBranchId());
			operlog.setLogId(DateUtil.currentDate("yyMMdd") + order.getBranchId() + houseCheckService.getSequence("SEQ_OPERATELOG_ID"));
			String operid = InetAddress.getLocalHost().toString();// IP地址
			operid = (String) operid.subSequence(operid.indexOf("/") + 1, operid.length());
			operlog.setOperIp(operid);
			operlog.setOperType("17");
			operlog.setOperModule("换房删人(民宿)");
			operlog.setRecordUser(loginuser.getStaff().getStaffId());
			operlog.setRecordTime(new Date());
			
			if(failFlagCount == 0){
				operlog.setContent("单号" + order.getOrderId() + "已换房(未结账),入住人["+checkUserLis.substring(0, checkUserLis.length()-1)+"]门锁里身份证信息删除成功");
				houseCheckService.save(operlog);
			} else {
				operlog.setContent("单号" + order.getOrderId() + "已换房(未结账),入住人中["+checkUserLis.substring(0, checkUserLis.length()-1)+"]门锁里有["+checkUserLisFail.substring(0, checkUserLisFail.length()-1)+"]身份证信息删除失败");
				houseCheckService.save(operlog);
			}	
		/*	Equipment equip = (Equipment)houseCheckService.findOneByProperties(Equipment.class, "branchId", houseorderid);
			if(equip != null){
				String lockCode = equip.getSerialNo();
				String gatewayCode = equip.getGateWayCode();
				try {
					Integer result = CardUtil.doorDelAllCardData(request, response, lockCode, gatewayCode);

					if(result != CommonConstants.PortalResultCode.FAILED && !result.toString().equals(CommonConstants.doorInterfaceResult.FAILED)){
					//删除指令成功
					} else {
					//删除锁中所有身份证信息失败记日志
						SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
						WarningLog warningLog = new WarningLog();
						warningLog.setLogId(sdf.format(new Date()) + houseCheckService.getSequence("SEQ_WARNINGLOG_ID"));
						warningLog.setRecordTime(new Date());
						warningLog.setRecordUser(SystemConstants.User.ADMIN_ID);
						warningLog.setWarningDate(new Date());
						warningLog.setStatus(CommonConstants.servieLogStatus.EFFECTIVE);
						warningLog.setWarningType(CommonConstants.warningLogStatus.DEL_ALL_CARD_FAILED);
						warningLog.setRemark(houseorderid+"删除锁中所有身份证信息失败");
						warningLog.setSerialNo(lockCode);
						houseCheckService.save(warningLog);
					}	 
				} catch (Exception e) {
					log.error("delDoordata occurs error! " + e.getMessage());
				}
			}*/
			return;
		}
		
		Staff staff = loginuser.getStaff();
		String branchId = staff.getBranchId();
		//添加房价调整差额账单
		SysParam sysParamCleanPrice = (SysParam)houseCheckService.findOneByProperties(SysParam.class, "paramType", "PROJECT", "content",CommonConstants.BillProject.EX_HOUSE_PRICE);
		Bill bill =new Bill();
		String billId = DateUtil.currentDate("yyMMdd")+order.getBranchId()+order.getSource()+ houseCheckService.getSequence("SEQ_NEW_BILL");
		bill.setBillId(billId);
		bill.setBranchId(order.getBranchId());
		bill.setCheckId(houseorderid);
		bill.setPay(fine);
		bill.setCost(0.0);
		bill.setDescribe(sysParamCleanPrice.getParamName());
		bill.setPayment(CommonConstants.BillPayment.WeChat);
		bill.setProjectId(sysParamCleanPrice.getContent());
		bill.setProjectName(sysParamCleanPrice.getParamName());
		bill.setRecordTime(new Date());
		bill.setRecordUser(branchId);
		bill.setStatus(CommonConstants.BillStatus.NonPayment);
		bill.setRemark(chosehouseid);
		try{
			houseCheckService.save(bill);
			JSONUtil.responseJSON(response, new AjaxResult(1, "账单提交成功!"));
		}catch(Exception e){
			JSONUtil.responseJSON(response, new AjaxResult(0, "账单提交失败!"));
			e.printStackTrace();
		}
	}
	
	public void exchangHouse(HttpServletRequest request,HttpServletResponse response,
			String chosehouseid, String houseorderid,
			String outStatus, String remark, String money) throws UnknownHostException, ParseException{
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		//选中的民宿，待换的民宿
		House house = (House) houseCheckService.findOneByProperties(House.class, "houseId", chosehouseid);
		//当前民宿，将要被换的民宿
		Check check = (Check) houseCheckService.findOneByProperties(Check.class, "checkId", houseorderid);
		String lastBranchId = check.getBranchId();
		double roomPrice = check.getRoomPrice();
		
		//添加换房日志表
		SwitchRoom switchroom = new SwitchRoom();
		String logId = DateUtil.currentDate("yyMMdd") + chosehouseid + houseCheckService.getSequence("SEQ_NEW_SWITCHROOM");
		switchroom.setLogId(logId);
		switchroom.setBranchId(chosehouseid);
		switchroom.setCheckId(houseorderid);
		switchroom.setLastrpId(check.getRpId());
		//被换房的branchId
		switchroom.setLastRoomid(check.getBranchId());
		//switchroom.setLastRoomtype(check.getRoomType());
		switchroom.setLastrRoomprice(check.getRoomPrice());
		switchroom.setCurrrpId(check.getRpId());
		switchroom.setCurrRoomid(chosehouseid);
		//switchroom.setCurrRoomtype(house.getHouseType());
		switchroom.setCurrRoomprice(check.getRoomPrice());
		switchroom.setRecordTime(new Date());
		switchroom.setRecordUser(loginUser.getStaff().getStaffId());
		
		check.setBranchId(chosehouseid);
		check.setRoomId(house.getHouseNo());
		
		Order order = (Order) houseCheckService.findOneByProperties(Order.class, "orderId", houseorderid);
			
		order.setBranchId(check.getBranchId());
		
		//判断是否选中了有责换房，如果是有责换房则要加账单的
		if (outStatus.equals("0")) {
			roomPrice = new SinglePrice(chosehouseid, "3", null, check.getRpId(), 
					RealPrice.PriceType.DATE, new Date()).checkRoomPrice();
			check.setRoomPrice(roomPrice);
			
			//对order中的roomprice总价和usercheckin纯房价进行调整
			
			Double rp = RoomUtil.getCheckinPrice(new Date(), check.getCheckoutTime(), houseorderid);
			Double re = new MultiPrice(chosehouseid, "3", null, check.getRpId(), RealPrice.PriceType.DATE, new Date(), 
					DateUtil.addDays(check.getCheckoutTime(), -1)).checkRoomPrice();
			Double res = re - rp;
			order.setRoomPrice(order.getRoomPrice() + res);
			order.setUserCheckin("" + (Double.valueOf(order.getUserCheckin()) + res));
			
			RoomUtil.updateCheckinPricenew(new Date(), DateUtil.addDays(check.getCheckoutTime(), -1), 
					null, check.getRpId(), check.getBranchId(), "3", check.getCheckId(), null);
		}
		//换房时，判断remark是否为空！
		if(!StringUtil.isEmpty(remark) && !remark.equals("undefined")){
			check.setRemark(check.getRemark() == null ? "" : check.getRemark() + "," + remark);
		}
		//保存roomPrice价格到日志表中
		switchroom.setCurrRoomprice(roomPrice);
		
		List<?> bills = houseCheckService.findByProperties(Bill.class, "checkId", houseorderid);
		
		try{
			
			//账单也需要修改
			for (Object object : bills) {
				Bill bill = (Bill) object;
				bill.setBranchId(chosehouseid);
				houseCheckService.update(bill);
			}
			
			houseCheckService.updatehouseStatus(lastBranchId, CommonConstants.HouseStatus.HouseDirty);
			houseCheckService.updatehouseStatus(chosehouseid, CommonConstants.HouseStatus.HouseChecked);
			houseCheckService.update(check);
			houseCheckService.update(order);
			houseCheckService.save(switchroom);
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.SUCESS, "换房成功!", outStatus));
		}catch(Exception e){
			e.printStackTrace();
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, "换房失败!"));
		}
	}
	
	@RequestMapping("overstay.do")
	public void overstay(HttpServletRequest request,HttpServletResponse response,
			String houseId,String projectid, String cuurent_checkouttime, 
			String getcontinuetime, String amount) throws DAOException, ClassNotFoundException, IOException, ParseException{
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String staffid = staff.getStaffId();
		Order lastorder = (Order) houseCheckService.findOneByProperties(Order.class, "orderId", houseId);
		Check check = (Check) houseCheckService.findOneByProperties(Check.class, "checkId", houseId);
		

		
		Order order = (Order) CloneUtil.deepClone(lastorder);
		order.setBranchId(check.getBranchId());
		String orderId = DateUtil.currentDate("yyMMdd") + order.getBranchId() + order.getSource()
		+ houseCheckService.getSequence("SEQ_NEW_ORDER");
		order.setOrderId(orderId);
		order.setRecordUser(staffid);
		Calendar cla = Calendar.getInstance();
		Calendar cll = Calendar.getInstance();
		cla.setTime(DateUtil.s2d(cuurent_checkouttime + " 14:00:00", "yyyy/MM/dd HH:mm:ss"));
		cll.setTime(DateUtil.s2d(getcontinuetime + " 12:00:00", "yyyy/MM/dd HH:mm:ss"));
		order.setArrivalTime(cla.getTime());
		order.setLeaveTime(cll.getTime());
		cll.add(Calendar.DATE, -1);
		Double totalhouseprice = new MultiPrice(order.getBranchId(), order.getTheme(), 
				null,order.getRpId(), RealPrice.PriceType.DATE, cla.getTime(), 
				cll.getTime()).checkRoomPrice();
		order.setRoomPrice(totalhouseprice);
		order.setUserCheckin("" + totalhouseprice);
		order.setPaymentType("4");
		order.setGuarantee("1");
		order.setStatus(CommonConstants.OrderStatus.NewOrder);
		order.setOrderTime(new Date());
		order.setRecordTime(new Date());
		order.setRecordUser(staff.getStaffId());
		order.setReletRelated(lastorder.getOrderId());
		
		List<CheckUser> ckuList = houseCheckService.findByProperties(CheckUser.class, "checkId", houseId,"checkinType","1");
		
		if(ckuList.size() >0){
			for(CheckUser cku : ckuList){
				String ckuId = cku.getCheckuserId();
				CheckUser checkUser = (CheckUser) houseCheckService.findOneByProperties(CheckUser.class, "checkuserId", ckuId);
				CheckUser cku2 = (CheckUser) CloneUtil.deepClone(checkUser);
				String ckuid = order.getBranchId() + order.getSource() + 
						houseCheckService.getSequence("SEQ_NEW_CHECKUSER");
				
				cku2.setCheckuserId(ckuid);
				cku2.setCheckId(orderId);
				houseCheckService.save(cku2);
			}
		}
		
		//添加保洁费账单
		SysParam sysParamCleanPrice = (SysParam)houseCheckService.findOneByProperties(SysParam.class, "paramType", "PROJECT", "content",CommonConstants.BillProject.CLEANPRICE);
		Bill bill =new Bill();
		String billId = DateUtil.currentDate("yyMMdd")+order.getBranchId()+order.getSource()+ houseCheckService.getSequence("SEQ_NEW_BILL");
		bill.setBillId(billId);
		bill.setBranchId(order.getBranchId());
		bill.setCheckId(orderId);
		bill.setPay(0.0);
		bill.setCost(0.0);
		bill.setDescribe(sysParamCleanPrice.getParamName());
		bill.setPayment(CommonConstants.BillPayment.WeChat);
		bill.setProjectId(sysParamCleanPrice.getContent());
		bill.setProjectName(sysParamCleanPrice.getParamName());
		bill.setRecordTime(new Date());
		bill.setRecordUser(staffid);
		bill.setStatus(CommonConstants.BillStatus.NonPayment);
		
		bill.setType("1");
		houseCheckService.save(bill);
		
		//添加服务费账单
		SysParam sysParamrealServicePrice = (SysParam)houseCheckService.findOneByProperties(SysParam.class, "paramType", "PROJECT", "content",CommonConstants.BillProject.Service);
		Bill billrealServicePrice =new Bill();
		String billIdrealServicePrice = DateUtil.currentDate("yyMMdd")+order.getBranchId()+order.getSource()+houseCheckService.getSequence("SEQ_NEW_BILL");
		billrealServicePrice.setBillId(billIdrealServicePrice);
		billrealServicePrice.setBranchId(order.getBranchId());
		billrealServicePrice.setCheckId(orderId);
		billrealServicePrice.setPay(0.0);
		billrealServicePrice.setCost(0.0);
		billrealServicePrice.setDescribe(sysParamrealServicePrice.getParamName());
		billrealServicePrice.setPayment(CommonConstants.BillPayment.WeChat);
		billrealServicePrice.setProjectId(sysParamrealServicePrice.getContent());
		billrealServicePrice.setProjectName(sysParamrealServicePrice.getParamName());
		billrealServicePrice.setRecordTime(new Date());
		billrealServicePrice.setRecordUser(staffid);
		billrealServicePrice.setStatus(CommonConstants.BillStatus.NonPayment);
		
		billrealServicePrice.setType("1");
		houseCheckService.save(billrealServicePrice);
		
		//续订房费或者预存
		SysParam sysParamOnlyTotal = (SysParam)houseCheckService.findOneByProperties(SysParam.class, "paramType", "PROJECT", "content",CommonConstants.BillProject.Deposit);
		Bill billOnlyTotal = new Bill();
		String billIdOnlyTotal = DateUtil.currentDate("yyMMdd") + order.getBranchId()+order.getSource()+ houseCheckService.getSequence("SEQ_NEW_BILL");
		billOnlyTotal.setBillId(billIdOnlyTotal);
		billOnlyTotal.setBranchId(order.getBranchId());
		billOnlyTotal.setCheckId(orderId);
		billOnlyTotal.setCost(0.0);
		billOnlyTotal.setPay(totalhouseprice);
		billOnlyTotal.setDescribe(sysParamOnlyTotal.getParamName());
		billOnlyTotal.setPayment(CommonConstants.BillPayment.WeChat);
		billOnlyTotal.setProjectId(sysParamOnlyTotal.getContent());
		billOnlyTotal.setProjectName(sysParamOnlyTotal.getParamName());
		billOnlyTotal.setRecordTime(new Date());
		billOnlyTotal.setRecordUser(staffid);
		billOnlyTotal.setStatus(CommonConstants.BillStatus.NonPayment);
		billOnlyTotal.setType("1");
		houseCheckService.save(billOnlyTotal);
		
		SysParam sysParamdepositPrice = (SysParam)houseCheckService.findOneByProperties(SysParam.class, "paramType", "PROJECT", "content",CommonConstants.BillProject.ForeGift);
		Bill billdepositPrice =new Bill();
		String billIddepositPrice = DateUtil.currentDate("yyMMdd")+order.getBranchId()+order.getSource()+houseCheckService.getSequence("SEQ_NEW_BILL");
		billdepositPrice.setBillId(billIddepositPrice);
		billdepositPrice.setBranchId(order.getBranchId());
		billdepositPrice.setCheckId(orderId);
		billdepositPrice.setPay(0.0);
		billdepositPrice.setCost(0.0);
		billdepositPrice.setDescribe(sysParamdepositPrice.getParamName());
		billdepositPrice.setPayment(CommonConstants.BillPayment.WeChat);
		billdepositPrice.setProjectId(sysParamdepositPrice.getContent());
		billdepositPrice.setProjectName(sysParamdepositPrice.getParamName());
		billdepositPrice.setRecordTime(new Date());
		billdepositPrice.setRecordUser(staffid);
		billdepositPrice.setStatus(CommonConstants.BillStatus.NonPayment);
		
		billdepositPrice.setType("1");
		houseCheckService.save(billdepositPrice);
		
		
 		String res = houseOrderController.reserveableByDate(order.getBranchId(), cuurent_checkouttime, getcontinuetime);
		
		try{
			if("-1".equals(res)){
				houseCheckService.save(billOnlyTotal);
				houseCheckService.save(order);
				RoomUtil.saveCheckinPrice(order.getArrivalTime(), DateUtil.addDays(order.getLeaveTime(), -1) , null, order.getRpId(), 
						order.getBranchId(), "3", orderId, staffid, null);
				JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.SUCESS, "续住订单提交!"));
			}else{
				JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.SUCESS, "民宿已被预定!"));
			}
		}catch(Exception e){
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, "续住订单提交失败!"));
		}
/*		Bill bill = new Bill();
		Check check = (Check) roomService.findOneByProperties(Check.class, "checkId", houseId);
		if( cuurent_checkouttime.equals(getcontinuetime)){
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, "续住日期未更改!"));
			return;
		}
		check.setCheckoutTime(DateUtil.s2d(getcontinuetime, "yyyy/MM/dd"));

		if(StringUtils.isEmpty(amount)){
			amount = "0";
		}
		String branchid = check.getBranchId();
		String billId = DateUtil.currentDate("yyMMdd") + branchid + roomService.getSequence("SEQ_NEW_BILL");
		bill.setBillId(billId);
		bill.setBranchId(branchid);
		bill.setCheckId(houseId);
		RoomUtil.setBillPayment(bill, projectid, 
				Double.valueOf(amount));
		bill.setDescribe("续住");
		bill.setStatus(CommonConstants.BillStatus.BillNormal);
		bill.setRecordUser(staff.getStaffId());
		
		try {
			if (!StringUtils.isEmpty(projectid)) {
				roomService.save(bill);
			}
			roomService.save(check);
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.SUCESS, "续住成功!"));

		} catch (Exception e) {
			e.printStackTrace();
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, "续住失败!"));
		}*/
		
	}
	
	@RequestMapping("getPriceDifference.do")
	public void getPriceDifference(HttpServletRequest request,HttpServletResponse response,
			String chosehouseid, String houseorderid, String outStatus ) throws DAOException, ClassNotFoundException, IOException{
		Order order = (Order) houseCheckService.findOneByProperties(Order.class, "orderId", houseorderid);
		Check check = (Check) houseCheckService.findOneByProperties(Check.class, "checkId", houseorderid);
		//House house = (House) houseCheckService.findOneByProperties(House.class, "houseId", chosehouseid);
		//Order houseorder = (Order) CloneUtil.deepClone(houseCheckService.findOneByProperties(Order.class, "orderId", houseorderid));
		Double pricedifference = new MultiPrice(chosehouseid, "3", null, check.getRpId(), RealPrice.PriceType.DATE, 
				new Date(), DateUtil.addDays(check.getCheckoutTime(), -1)).checkRoomPrice() - RoomUtil.getCheckinPrice(new Date(), DateUtil.addDays(check.getCheckoutTime(), -1), houseorderid);
				//不要取远期价格表
//				new MultiPrice(check.getBranchId(), "3", null, 
//				check.getRpId(), RealPrice.PriceType.DATE, check.getCheckinTime(), 
//				DateUtil.addDays(check.getCheckoutTime(), -1)).checkRoomPrice();
		if("1".equals(outStatus)){
			JSONUtil.responseJSON(response, new AjaxResult(1, "免责换房!", String.format("%.2f", pricedifference)));
		}else if("0".equals(outStatus)){
			JSONUtil.responseJSON(response, new AjaxResult(0, "有责换房!", String.format("%.2f", pricedifference)));
		}
	}
	
	@RequestMapping("HouseCheckOutOver.do")
	public ModelAndView houseCheckOutOver(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String orderUser = request.getParameter("orderUser");
		String memberId = request.getParameter("memberId");
		String memberName = request.getParameter("memberName");
		String mobile = request.getParameter("mobile");
		String checkinTime = request.getParameter("checkinTime");
		String checkoutTime = request.getParameter("checkoutTime");
		String source = "0".equals(request.getParameter("source")) ? "" : request.getParameter("source");
		String roomType = request.getParameter("roomType");
		String recordUser = request.getParameter("recordUser");
		Pagination pagination = SqlBuilder.buildPagination(request);
		//List<House> houses = houseCheckService.findByProperties(House.class, "staffId", loginUser.getStaff().getStaffId());
		List<?> houses = houseCheckService.findBySQL("fHouseWithStaffid",new String[]{loginUser.getStaff().getStaffId()}, true);

		String branchId = "";
		if (houses.size() > 0) {
			for (Object house : houses) {
				branchId +="'" + ((Map<?,?>)house).get("HOUSE_ID") + "',";
			}
			branchId = branchId.substring(0, branchId.length()-1);
		}
		//List<?> list = houseCheckService.findBySQLWithPagination("quertHouseCheck", new String[] { "4" , memberName, mobile, loginUser.getStaff().getStaffId(), ""}, pagination, true);
		/*List<?> list = houseCheckService.findBySQLWithPagination("quertHouseCheck", new String[] { "4" , memberName, mobile, loginUser.getStaff().getStaffId(),
				loginUser.getStaff().getStaffId(), ""}, pagination, true);*/
		List<?> list = houseCheckService.findBySQLWithPagination(
				"quertHouseCheck", new String[] { "4", orderUser, memberId,memberName, mobile, source, roomType,recordUser,checkinTime, 
						checkoutTime,loginUser.getStaff().getStaffId(),"" }, pagination,true);
	
		mv.addObject("list", list);
		mv.addObject("pagination", pagination);
		mv.addObject("memberName", memberName);
		mv.addObject("mobile", mobile);
		mv.addObject("action", "HouseCheckOutOver.do");
		mv.setViewName("page/ipmshouse/housecheck/housecheckall");
		return mv;
	}
	
	
	@RequestMapping("/quertHouseCheckInfo.do")
	public void quertHouseCheckInfo(HttpServletRequest request, HttpServletResponse response, String houseCheckId) throws Exception {
		List<?> list = houseCheckService.findBySQL("loadCheckInfo", new String[] { houseCheckId }, true);
		if(list != null && list.size() > 0){
			JSONUtil.responseJSON(response, list);
		} else {
			JSONUtil.responseJSON(response, null);
		}
		
	}
	
	@RequestMapping("/quertHouseaddress.do")
	public void quertHouseaddress(HttpServletRequest request, HttpServletResponse response, String houseCheckId) throws Exception {
		List<?> list = houseCheckService.findBySQL("queryAddressmail", new String[] { houseCheckId }, true);
		JSONUtil.responseJSON(response, list);
	}

	@RequestMapping("/saveHousePerson.do")
	public void saveHousePerson(HttpServletRequest request,
			HttpServletResponse response, String mobile, String housecheckId)
			throws UnknownHostException {
		LoginUser loginUser = (LoginUser) request.getSession(true)
				.getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Check check = (Check) houseCheckService.findById(Check.class, housecheckId);

		OperateLog operlog = new OperateLog();
		operlog.setBranchId(check.getBranchId());
		operlog.setLogId(DateUtil.currentDate("yyMMdd") + check.getBranchId() + houseCheckService.getSequence("SEQ_OPERATELOG_ID"));
		String operid = InetAddress.getLocalHost().toString();// IP地址
		operid = (String) operid.subSequence(operid.indexOf("/") + 1, operid.length());
		operlog.setOperIp(operid);
		operlog.setOperType("1");
		operlog.setOperModule("入住操作(民宿)");
		operlog.setContent(check.getRoomId() + "房间添加入住人!");
		operlog.setRecordUser(loginUser.getStaff().getStaffId());
		operlog.setRecordTime(new Date());

		String[] mobiles = mobile.split(",");
		Date date = new Date();
		for (int i = 0; i < mobiles.length; i++) {
			Member member = (Member) houseCheckService
					.findOneByProperties(Member.class, "mobile", mobiles[i]);
			CheckUser checkUser = (CheckUser) houseCheckService
					.findOneByProperties(CheckUser.class, "mobile", mobiles[i],
							"checkId", housecheckId);
			if (null == checkUser) {
				checkUser = new CheckUser();
				checkUser.setCheckuserId(houseCheckService
						.getSequence("SEQ_NEW_CHECKUSER"));
				checkUser.setCheckuserName(member.getMemberName());
				checkUser.setIdcard(member.getIdcard());
				checkUser.setGender(member.getGendor());
				checkUser.setMobile(member.getMobile());
				checkUser.setStatus("1");
				checkUser.setRecordUser(loginUser.getStaff().getStaffId());
				checkUser.setRecordTime(date);
				checkUser.setCheckId(housecheckId);
			}
			if (i == 0) {
				checkUser.setCheckuserType("1");
			} else {
				checkUser.setCheckuserType("2");
			}
			houseCheckService.getHibernateTemplate().saveOrUpdate(checkUser);
		}

		houseCheckService.save(operlog);
		JSONUtil.responseJSON(response, new AjaxResult(0, "保存成功!"));
	}
	
	//民宿提前退房
	@SuppressWarnings("unchecked")
	@RequestMapping("/OperationHouseCheckOut.do")
	public void checkOut(HttpServletRequest request, HttpServletResponse response, String housecheckId,
			String outStatus, String remark, String money, String objarrs) throws UnknownHostException {
		String[] objarr = null;
		Integer failFlagCount = 0;
		String checkUserLis = "";
		String checkUserLisFail = "";
		Integer result = CommonConstants.PortalResultCode.FAILED;
		if(!StringUtil.isEmpty(objarrs)){
			objarr = objarrs.split(",");
		}
		//List<CheckUser> list = new ArrayList<CheckUser>();
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		List<CheckUser> checkUsers = houseCheckService.findByPropertiesWithSort(CheckUser.class, "checkuserType", "asc", "checkId", housecheckId);
		Check check = (Check) houseCheckService.findById(Check.class, housecheckId);
		Order order = (Order) houseCheckService.findById(Order.class, housecheckId);
		
		House house = (House) houseCheckService.findOneByProperties(House.class, "houseNo", check.getRoomId(), "houseId", check.getBranchId());
		if (checkUsers.size() > 0) {
			for (CheckUser checkUser : checkUsers) {
				checkUser.setStatus("0");
				//list.add(checkUser);
				houseCheckService.update(checkUser);
			}
		}
		//Bill roomBill = (Bill) houseCheckService.findOneByProperties(Bill.class, "branchId", order.getBranchId(), "checkId", order.getOrderId(), "projectId", "1011");
		if (outStatus.equals("0")){
			
			Double cleanPrice = 0.0;
			
//			Bill cleanPrice = (Bill) houseCheckService.findOneByProperties(Bill.class, "branchId", order.getBranchId(), "checkId", order.getOrderId(),
//					"projectId", CommonConstants.BillProject.CLEANPRICE);
			List<Bill> cleanPrices = houseCheckService.findByProperties(Bill.class, "branchId", order.getBranchId(), "checkId", order.getOrderId(),
					"projectId", CommonConstants.BillProject.CLEANPRICE);
			if(cleanPrices.isEmpty()){
				for(Bill bill:cleanPrices){
					cleanPrice += bill.getPay();
				}
			}
			
			Bill service = (Bill) houseCheckService.findOneByProperties(Bill.class, "branchId", order.getBranchId(), "checkId", order.getOrderId(),
					"projectId", CommonConstants.BillProject.Service);
			Bill ForeGift = (Bill) houseCheckService.findOneByProperties(Bill.class, "branchId", order.getBranchId(), "checkId", order.getOrderId(),
					"projectId", CommonConstants.BillProject.ForeGift);
			ForeGift.setRefundStatus("1");
			
			Bill bill;
			Date date = new Date();
			String billId;
//			bill = new Bill();
//			billId = DateUtil.currentDate("yyMMdd") + check.getBranchId()+ "3" + houseCheckService.getSequence("SEQ_NEW_BILL");
//			bill.setBillId(billId);
//			bill.setBranchId(check.getBranchId());
//			bill.setCheckId(check.getCheckId());
//			bill.setProjectId("1234");
//			bill.setProjectName("房费");
//			bill.setDescribe("房费");
//			bill.setCost(cleanPrice + service.getPay());
//			bill.setPayment("0");
//			bill.setPay(0.0);
//			bill.setStatus("1");
//			bill.setRecordTime(date);
//			bill.setRecordUser(loginUser.getStaff().getStaffId());
//			houseCheckService.save(bill);
			
			int days = days(date, check.getCheckoutTime());
			
			List<OrdchePrice> list = houseCheckService.findByProperties(OrdchePrice.class, "branchId", check.getBranchId(), "orderId", check.getCheckId(), "status", "1");
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			Date dateT = new Date();
			Calendar canlendar = Calendar.getInstance();
			for(int i = 1; i <= days; i++){
				bill = new Bill();
				billId = DateUtil.currentDate("yyMMdd") + check.getBranchId()+ "3" + houseCheckService.getSequence("SEQ_NEW_BILL");
				for( OrdchePrice ordchePrice : list ){
					if(sdf.format(ordchePrice.getWhichDay()).equals(sdf.format(dateT))){
						bill.setCost(ordchePrice.getDayPrice());
					}
				}
				canlendar.setTime(dateT);
				canlendar.add(Calendar.DAY_OF_MONTH, 1);
				dateT = canlendar.getTime();
				bill.setBillId(billId);
				bill.setBranchId(check.getBranchId());
				bill.setCheckId(check.getCheckId());
				bill.setProjectId("1234");
				bill.setProjectName("房费");
				bill.setDescribe("房费");
				bill.setPayment("0");
				bill.setPay(0.0);
				bill.setStatus("1");
				bill.setRecordTime(date);
				bill.setRecordUser(loginUser.getStaff().getStaffId());
				houseCheckService.save(bill);
			}
			
			houseCheckService.update(ForeGift);
			
			if(!StringUtil.isEmpty(remark) && remark.equals("undefined")){
				check.setRemark(check.getRemark() == null ? "" : check.getRemark() + "," + remark);
			}
		} else if (outStatus.equals("1")) {
			if(!StringUtil.isEmpty(money) && !money.equals("undefined")){
				Bill bill = new Bill();
				String billId = DateUtil.currentDate("yyMMdd") + check.getBranchId()+ "3" + houseCheckService.getSequence("SEQ_NEW_BILL");
				bill.setBillId(billId);
				bill.setBranchId(check.getBranchId());
				bill.setCheckId(check.getCheckId());
				bill.setProjectId("1001");
				bill.setProjectName("赔偿");
				bill.setDescribe("赔偿");
				bill.setType("1");
				bill.setCost(Double.parseDouble(money));
				bill.setPay(0.00);
				bill.setPayment("1");
				bill.setStatus("1");
				bill.setRecordUser(loginUser.getStaff().getStaffId());
				houseCheckService.save(bill);
			}
			List<Bill> bills = houseCheckService.findByProperties(Bill.class, "branchId", check.getBranchId(), "checkId", check.getCheckId());
			for(Bill bill : bills){
				//bill.getProjectId().equals(CommonConstants.BillProject.Service) || bill.getProjectId().equals(CommonConstants.BillProject.CLEANPRICE) || 
				if(bill.getProjectId().equals(CommonConstants.BillProject.ForeGift) || bill.getProjectId().equals(CommonConstants.BillProject.Deposit)){
					if(objarr != null){
						if(!Arrays.asList(objarr).contains(bill.getProjectId())){
							bill.setRefundStatus("1");
							houseCheckService.update(bill);
						}
					}else{
						bill.setRefundStatus("1");
						houseCheckService.update(bill);
					}
				}
			}
			if(!StringUtil.isEmpty(remark) && !remark.equals("undefined")){
				check.setRemark(check.getRemark() == null ? "" : check.getRemark() + "," + remark);
			}
		}
		
		

//		if (list.size() > 0) {
//			houseCheckService.saveOrUpdateAll(list);
//		}
		
		//List<RepairApply> repairApplys = houseCheckService.findByProperties(RepairApply.class, "branchId", order.getBranchId(), "contractId", order.getOrderId());
		List<RepairApply> repairApplys = houseCheckService.findByProperties(RepairApply.class, "branchId", order.getBranchId());
		String status  = "Z";
		if(repairApplys.size() > 0){
			for(RepairApply repairApply : repairApplys){
				if(repairApply.getStatus().equals("2") || repairApply.getStatus().equals("3")){
					status = "W";
				}
			}
		}
		
		check.setStatus("3");
		order.setStatus("6");
		check.setCheckoutTime(new Date());
		order.setLeaveTime(new Date());
		order.setCheckoutTime(new Date());
		
		house.setStatus(status);
		houseCheckService.update(check);
		houseCheckService.update(order);
		houseCheckService.update(house);
		
		//门锁里删除住户身份证信息,而不删除保洁的身份证信息
		//先查询订单里的所有的身份证，不管状态
		List<?> checkUserList = houseCheckService.findByProperties(CheckUser.class, "checkId", check.getCheckId(), "checkinType", "2");
		if(checkUserList != null && checkUserList.size() > 0){
			for (int j = 0; j < checkUserList.size(); j++){
				String idcardUser = ((CheckUser)(checkUserList.get(j))).getIdcard();
				checkUserLis += idcardUser +",";
				if(idcardUser != null && !StringUtil.isEmpty(idcardUser)){
					try {
						Equipment equipment = (Equipment) houseCheckService.findOneByProperties(Equipment.class, "branchId", check.getBranchId());
						if(equipment != null){
							result = CardUtil.doorDelCardData(request, response, equipment.getSerialNo(), idcardUser);
						}

						if(result != CommonConstants.PortalResultCode.FAILED && !result.toString().equals(CommonConstants.doorInterfaceResult.FAILED)){
							
						} else {
							
							failFlagCount++;
							checkUserLisFail += idcardUser +",";
							//删除锁中一个身份证信息所有授权失败记日志
							SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
							if(equipment != null){
								WarningLog warningLog = new WarningLog();
								warningLog.setLogId(sdf.format(new Date()) + houseCheckService.getSequence("SEQ_WARNINGLOG_ID"));
								warningLog.setRecordTime(new Date());
								warningLog.setRecordUser(SystemConstants.User.ADMIN_ID);
								warningLog.setWarningDate(new Date());
								warningLog.setStatus(CommonConstants.servieLogStatus.EFFECTIVE);
								warningLog.setWarningType(CommonConstants.warningLogStatus.DEL_ALL_CARD_FAILED);
								warningLog.setRemark(check.getBranchId()+"删除锁中所有身份证信息中["+idcardUser+"]失败");
								warningLog.setSerialNo(equipment.getSerialNo());
								houseCheckService.save(warningLog);
							}
						}	 
					} catch (Exception e) {
						failFlagCount++;
						checkUserLisFail += idcardUser +",";
						
					}
				}
			}
			
		}
		
		OperateLog operlog = new OperateLog();
		operlog.setBranchId(check.getBranchId());
		operlog.setLogId(DateUtil.currentDate("yyMMdd") + check.getBranchId() + houseCheckService.getSequence("SEQ_OPERATELOG_ID"));
		String operid = InetAddress.getLocalHost().toString();// IP地址
		operid = (String) operid.subSequence(operid.indexOf("/") + 1, operid.length());
		operlog.setOperIp(operid);
		operlog.setOperType("11");
		operlog.setOperModule("退房操作(民宿)");
		operlog.setRecordUser(loginUser.getStaff().getStaffId());
		operlog.setRecordTime(new Date());
		
		if(failFlagCount == 0){
			operlog.setContent("房单号" + check.getCheckId() + "已退房(未结账),入住人["+checkUserLis.substring(0, checkUserLis.length()-1)+"]门锁里身份证信息删除成功");
			houseCheckService.save(operlog);
			JSONUtil.responseJSON(response, new AjaxResult(0, "退房成功!"));
		} else {
			operlog.setContent("房单号" + check.getCheckId() + "已退房(未结账),入住人中["+checkUserLis.substring(0, checkUserLis.length()-1)+"]门锁里有["+checkUserLisFail.substring(0, checkUserLisFail.length()-1)+"]身份证信息删除失败");
			houseCheckService.save(operlog);
			JSONUtil.responseJSON(response, new AjaxResult(0, "退房成功，门锁里身份证信息删除失败!"));
		}	
	}
	
	@RequestMapping("/isCheckOutDate.do")
	public void isCheckOutDate(HttpServletRequest request, HttpServletResponse response, String housecheckId){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Check check = (Check) houseCheckService.findById(Check.class, housecheckId);
		
		List<?> bills = houseCheckService.findByProperties(Bill.class, "checkId", housecheckId, 
				"status", CommonConstants.BillStatus.NonPayment);
		if(bills != null && !bills.isEmpty()){
			JSONUtil.responseJSON(response, new AjaxResult(3, "存在未支付补差价账单!"));
			return;
		}
		if(sdf.format(check.getCheckoutTime()).equals(sdf.format(new Date()))){
			JSONUtil.responseJSON(response, new AjaxResult(0, "当天退房!"));
		}else{
			JSONUtil.responseJSON(response, new AjaxResult(1, "提前退房!"));
		}
	}
	
	public static int days(Date fDate, Date oDate) {

       Calendar aCalendar = Calendar.getInstance();

       aCalendar.setTime(fDate);

       int dayOne = aCalendar.get(Calendar.DAY_OF_YEAR);

       aCalendar.setTime(oDate);

       int dayTwo = aCalendar.get(Calendar.DAY_OF_YEAR);

       return dayTwo - dayOne;

    }
	
	
	@RequestMapping("/addHouseComment.do")
	public void addHouseComment (HttpServletRequest request, HttpServletResponse response, String checkId) throws IOException {
		String headPicUrl = addHouseCommentImage(request, response);
		JSONUtil.responseJSON(response, new AjaxResult(1, headPicUrl));
	}
	
	public String addHouseCommentImage(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String fileName = null;
		FileOutputStream fos = null;
		InputStream is = null;
		String headPicUrl = "";
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile srcFile = multipartRequest.getFile("file");
		try {
			String webPath = RequestUtil.getWebPath(request);
			if (StringUtils.isEmpty(fileName)) {
				fileName = srcFile.getOriginalFilename();
				fileName = new Date().getTime()+fileName.substring(fileName.lastIndexOf("."));
			}
			File srcFolder = new File(webPath + File.separator + "upload");
			if (!srcFolder.exists()) {
				srcFolder.mkdirs();
			}
			headPicUrl = CommonConstants.Domain.DOMAINNAME + fileName;
			File tarFile = new File(srcFolder.getAbsolutePath() + File.separator + fileName);
			if (!tarFile.exists()) {
				tarFile.createNewFile();
			}
			fos = new FileOutputStream(tarFile);
			is = srcFile.getInputStream();
			int ln = 0;
			byte[] buf = new byte[1024];
			while ((ln = is.read(buf)) != -1) {
				fos.write(buf, 0, ln);
			}
			fos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				fos.close();
			}
			if (is != null) {
				is.close();
			}
		}
		return headPicUrl;
	}
	
	@RequestMapping("adjustorderprice.do")
	@RightMethodControl( rightType = RightConstants.RightType.AD_ROOMPRC )
	public ModelAndView adjustorderprice(HttpServletRequest request, HttpServletResponse response, String orderid){
		Check check = (Check) houseCheckService.findOneByProperties(Check.class, "checkId", orderid);
		ModelAndView mv = new ModelAndView("page/ipmshouse/housecheck/resethouseorderprice");
		//List<?> result = houseOrderService.findByProperties(OrdchePrice.class, "orderId", orderid);
		//Double rp = new MultiPrice(check.getBranchId(), "3", null, check.getRpId(), RealPrice.PriceType.DATE, new Date(), check.getCheckoutTime());
		double or = 0D;
		or = Double.valueOf(check.getRoomPrice());
		mv.addObject("result", or);
		mv.addObject("orderid", orderid);
		return mv;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/getHouseOrderDetail.do")
	public void getHouseOrderDetail(HttpServletRequest request, HttpServletResponse response,String orderId)throws Exception {
		Order order = (Order) houseCheckService.findOneByProperties(Order.class, "orderId", orderId);
		List<Bill> bills = houseCheckService.findByProperties(Bill.class, "branchId", order.getBranchId(), "checkId", orderId,"type","1");
		List<Bill> newBills = new ArrayList<Bill>();
		houseCheckService.sessionClear();
		for(Bill bill : bills){
			if(bill.getProjectId().contains("2") && !bill.getProjectId().equals("2009") && !bill.getProjectId().equals("2999")){
				if(bill.getProjectId().equals("2004")){
					Double cost = 0.0;
					Double pay = bill.getPay();
					List<Bill> billTwo = houseCheckService.findByProperties(Bill.class, "branchId", order.getBranchId(), "checkId", orderId,"projectId","1234","status","1");
					Bill changeHouse = (Bill) houseCheckService.findOneByProperties(Bill.class, "branchId", order.getBranchId(), "checkId", orderId,"projectId","2010","status","1");
					Bill changeRoomPrice = (Bill) houseCheckService.findOneByProperties(Bill.class, "branchId", order.getBranchId(), "checkId", orderId,"projectId","2998","status","1");
					if(!billTwo.isEmpty()){
						for(Bill b : billTwo){
							cost += b.getCost();
						}
					}
					if(null != changeHouse){
						pay += changeHouse.getPay();
					}
					if(null != changeRoomPrice){
						pay += changeRoomPrice.getPay();
					}
					bill.setPay(PriceUtil.doubleToPriceDouble(pay - cost));
					newBills.add(bill);
				}else{
					newBills.add(bill);
				}
			}
//			if(bill.getProjectId().equals("1011")){
//				newBills.add(bill);
//			}
		}
		
		JSONUtil.responseJSON(response, newBills);
	}
	
	@RequestMapping("/loadHouseOnlineCheckData.do")
	public void loadHouseOnlineCheckData(HttpServletRequest request, HttpServletResponse response, MultiQueryCheck multiQuerycheck) {
		String sql = "loadOnlineCheckData";
		List<?> list = houseCheckService.findBySQL(sql, new String[] { multiQuerycheck.getStatus(),
				multiQuerycheck.getCheckid(), multiQuerycheck.getRoomid(), multiQuerycheck.getRoomtype(),
				multiQuerycheck.getGuarantee(), multiQuerycheck.getMphone() }, true);
		JSONUtil.responseJSON(response, list);
	}
	
	@RequestMapping("preresethouseorderprice.do")
	public void preresethouseorderprice(HttpServletRequest request, HttpServletResponse response, String orderid,
			String amount, String dayamount, String remark){
		Order order = (Order) houseCheckService.findOneByProperties(Order.class, "orderId", orderid);
		Member m = (Member) houseCheckService.findOneByProperties(Member.class, "memberId", order.getOrderUser());
		if(CommonConstants.MembenRank.NM.equals(m.getMemberRank())){
			String memberId = RoomUtil.checkMember(response, m.getMobile(), m.getIdcard());
			if(!StringUtils.isEmpty(memberId) && memberId.length() > 1){
				System.out.println(memberId);
			order.setOrderUser(memberId);
			resethouseorderprice(request, response, orderid,
					amount, dayamount, remark, null);
			}
		}else{
			resethouseorderprice(request, response, orderid,
			amount, dayamount, remark, null);
		}
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param orderid
	 * @param amount
	 * @param dayamount
	 * @param remark
	 * @param memberupdate 判断是否会员更新
	 */
	@RequestMapping("resethouseorderprice.do")
	public void resethouseorderprice(HttpServletRequest request, HttpServletResponse response, String orderid,
			String amount, String dayamount, String remark, String memberupdate){
		
		List<?> bills = houseCheckService.findByProperties(Bill.class, "checkId", orderid, 
				"status", CommonConstants.BillStatus.NonPayment);
		if(bills != null && !bills.isEmpty()){
			JSONUtil.responseJSON(response, new AjaxResult(0, "存在未支付补差价账单!"));
			return;
		}
		//判断补的差价金额，如果小于0则直接开始更改数据表，如果大于0则开始插入账单，等待APP端进行支付
		Order order = (Order) houseCheckService.findOneByProperties(Order.class, "orderId", orderid);
		
		/**
		 * 注销代码，不在更新order中的orderuser内容，非会员变成会员
		 */
//		String newmemberid = RoomUtil.memupdate(memberupdate, order.getOrderUser());
//		if(newmemberid == null )
//			throw BusinessException.withErrorCode(ErrorConstants.Member.EMPTY_MEMBER);
//		order.setOrderUser(newmemberid);
		
		double fine = Double.valueOf(amount);
		if(fine <= 0){
			exresethouseorderprice(request, response, orderid, dayamount, remark);
			return;
		}
		
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String branchId = staff.getBranchId();
		
		
		//添加房价调整差额账单
		SysParam sysParamCleanPrice = (SysParam)houseCheckService.findOneByProperties(SysParam.class, "paramType", "PROJECT", "content",CommonConstants.BillProject.DIFF_PRICE);
		Bill bill =new Bill();
		String billId = DateUtil.currentDate("yyMMdd")+order.getBranchId()+order.getSource()+ houseCheckService.getSequence("SEQ_NEW_BILL");
		bill.setBillId(billId);
		bill.setBranchId(order.getBranchId());
		bill.setCheckId(orderid);
		bill.setPay(fine);
		bill.setCost(0.0);
		bill.setDescribe(sysParamCleanPrice.getParamName());
		bill.setPayment(CommonConstants.BillPayment.WeChat);
		bill.setProjectId(sysParamCleanPrice.getContent());
		bill.setProjectName(sysParamCleanPrice.getParamName());
		bill.setRecordTime(new Date());
		bill.setRecordUser(branchId);
		bill.setStatus(CommonConstants.BillStatus.NonPayment);
		try{
			houseCheckService.save(bill);
			JSONUtil.responseJSON(response, new AjaxResult(1, "账单提交成功!"));
		}catch(Exception e){
			JSONUtil.responseJSON(response, new AjaxResult(0, "账单提交失败!"));
			e.printStackTrace();
		}
	}
	
	
	public void exresethouseorderprice(HttpServletRequest request, HttpServletResponse response, String orderid,
	String amount, String remark){
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		//String branchId = staff.getBranchId();
		Order order = (Order) houseCheckService.findOneByProperties(Order.class, "orderId", orderid);
		Check check = (Check) houseCheckService.findOneByProperties(Check.class, "checkId", orderid);
		RoomPlan roomplan = new RoomPlan();
		String logId = DateUtil.currentDate("yyMMdd") + staff.getBranchId()
				+ houseCheckService.getSequence("SEQ_NEW_ROOMPLAN");
		roomplan.setLogId(logId);
		roomplan.setBranchId(order.getBranchId());
		roomplan.setValidDay(new Date());
		roomplan.setRoomId(order.getRoomId());
		roomplan.setRpId(order.getRpId());
		roomplan.setRoomPrice(check.getRoomPrice());
		roomplan.setCashback(Double.valueOf(amount));
		roomplan.setStatus(CommonConstants.RoomPlanStatus.Valid);
		roomplan.setRecordUser(staff.getStaffId());
		roomplan.setCheckId(orderid);
		roomplan.setRemark(remark);
		
		//对order中的roomprice总价和usercheckin纯房价进行调整
		Double rp = RoomUtil.getCheckinPrice(new Date(), check.getCheckoutTime(), orderid);
		int size = DateUtil.daysOfTwo(new Date(), check.getCheckoutTime());
		Double re = Double.valueOf(amount)*size;
		Double res = re - rp;
		order.setRoomPrice(order.getRoomPrice() + res);
		order.setUserCheckin("" + (Double.valueOf(order.getUserCheckin()) + res));
		
		if(check != null){
			check.setRoomPrice(Double.valueOf(amount));
		}
		try{
			houseCheckService.save(roomplan);
			RoomUtil.updateCheckinPricenew(new Date(), DateUtil.addDays(order.getLeaveTime(), -1), null, order.getRpId(), order.getBranchId(), "3", orderid, amount);
			JSONUtil.responseJSON(response, new AjaxResult(1, "调整成功!"));
		}catch(Exception e){
			JSONUtil.responseJSON(response, new AjaxResult(0, "调整失败!"));
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/houseforward.do")
	public ModelAndView houseforward(HttpServletRequest request, HttpServletResponse response){
		return new ModelAndView("page/ipmshouse/topMenu/room_forward/house_forward");
	}
	
}


