package com.ideassoft.pms.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
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

import com.ideassoft.bean.Bill;
import com.ideassoft.bean.Check;
import com.ideassoft.bean.CheckUser;
import com.ideassoft.bean.Member;
import com.ideassoft.bean.MemberAccount;
import com.ideassoft.bean.OperateLog;
import com.ideassoft.bean.OrdchePrice;
import com.ideassoft.bean.Order;
import com.ideassoft.bean.Room;
import com.ideassoft.bean.RoomPrice;
import com.ideassoft.bean.RoomStatus;
import com.ideassoft.bean.SysParam;
import com.ideassoft.core.ajax.AjaxResult;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.constants.CommonConstants;
import com.ideassoft.core.constants.SystemConstants;
import com.ideassoft.hotel.controller.HotelOrderController;
import com.ideassoft.pms.service.CheckInDirectSercive;
import com.ideassoft.portal.DataDealPortalService;
import com.ideassoft.portal.IDataDealPortal;
import com.ideassoft.price.RealPrice;
import com.ideassoft.price.SinglePrice;
import com.ideassoft.util.DateUtil;
import com.ideassoft.util.JSONUtil;
import com.ideassoft.util.MD5Util;
import com.ideassoft.util.ReflectUtil;
import com.ideassoft.util.RequestUtil;
import com.ideassoft.util.RoomUtil;

@Transactional
@Controller
public class CheckInDirectController {
	@Autowired
	private CheckInDirectSercive checkInDirectService;
	
	@Autowired
	private HotelOrderController ordercontroller;

	/**
	 * 查询会员信息
	 */
	@RequestMapping("/querymember.do")
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
//	@RequestMapping("/querymember.do")
//	public ModelAndView querymember(HttpServletRequest request,HttpServletResponse response, String phone) {
//		ModelAndView mv = new ModelAndView();
//		if(HeartBeatClient.heartbeat){
//			com.ideassoft.portal.DataDealPortalService service = new com.ideassoft.portal.DataDealPortalService();
//			com.ideassoft.portal.IDataDealPortal portal = service.getDataDealPortalPort();
//			String member = portal.call(1, 1, "{ function: \"checkInDirect.getMemberData\", data:{phone:" + phone  +" } }"); 
//			mv.setViewName("/page/ipmspage/leftmenu/repair/memberUpdate");
//			if((!member.equals(Integer.toString(SystemConstants.PortalResultCode.FAILED))) && (!member.equals(Integer.toString(SystemConstants.PortalResultCode.NULL)))){
//				JSONArray array = null;
//				JSONObject obj = null;
//				try {
//					array = new JSONArray(member);
//					for (int i = 0; i < array.length(); i++) {
//						obj = array.getJSONObject(i);
//						mv.addObject("name", obj.get("MEMBER_NAME"));
//						mv.addObject("memberid",obj.get("MEMBER_ID"));
//						mv.addObject("rank", obj.get("MEMBER_RANK"));
//						mv.addObject("gender", obj.isNull("GENDOR") ? "" : obj.get("GENDOR"));
//						mv.addObject("mobile", obj.isNull("MOBILE") ? "" : obj.get("MOBILE"));
//						mv.addObject("idcard", obj.isNull("IDCARD") ? "" : obj.get("IDCARD"));
//						mv.addObject("remark", obj.isNull("REMARK") ? "" : obj.get("REMARK"));
//						mv.addObject("rankname",obj.get("RANKNAME"));
//						mv.addObject("status",obj.get("STATUS"));
//					}
//					
//				} catch (JSONException e) {
//					e.printStackTrace();					
//				}
//			}
//			
//		}else{
//			mv.setViewName("/page/ipmspage/leftmenu/repair/errorconnect");
////			mv.setViewName("/page/system/error");
//		}
//			return mv;
//		
//	}
	/**
	 * 更新云端会员身份证信息
	 * @throws UnknownHostException 
	 */
	@RequestMapping("/insertMemberIdCard.do")
	public void insertMemberIdCard(HttpServletRequest request,HttpServletResponse response) throws UnknownHostException{
		String idcard = request.getParameter("idcard");
		String newmobile = request.getParameter("newmobile");
		String memberId = request.getParameter("memberId");
		String status = request.getParameter("status");
		String memberName = request.getParameter("memberName");
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchid = loginUser.getStaff().getBranchId();
//		if(HeartBeatClient.heartbeat){
			com.ideassoft.portal.DataDealPortalService service = new com.ideassoft.portal.DataDealPortalService();
			com.ideassoft.portal.IDataDealPortal portal = service.getDataDealPortalPort();
			String data = portal.call(SystemConstants.EnginType.COMMON_DATA, 
					SystemConstants.Movement.CUSTOM_QUERY,
					"{ function: \"checkInDirect.insertMemberIdCard\",data:{ idcard:"+ idcard +",newmobile:"+newmobile+",memberId:"+memberId+",status:"+status+ "}}");
			if(data.equals(Integer.toString(SystemConstants.PortalResultCode.DONE))){
				JSONUtil.responseJSON(response, new AjaxResult(1,"更新会员信息成功!"));
				//修改会员信息记操作日志
				OperateLog op = new OperateLog();
				op.setBranchId(loginUser.getStaff().getBranchId());
				op.setLogId(DateUtil.currentDate("yyMMdd") + branchid + checkInDirectService.getSequence("SEQ_OPERATELOG_ID"));
				op.setContent("更新会员信息成功,会员姓名:"+memberName);
				String operid = InetAddress.getLocalHost().toString();// IP地址
				operid = (String) operid.subSequence(operid.indexOf("/") + 1, operid.length());
				op.setOperIp(operid);
				op.setOperModule("修改会员资料");
				op.setOperType("3");
				op.setRecordTime(new Date());
				op.setRecordUser(loginUser.getStaff().getStaffId());
				checkInDirectService.save(op);
			}
			if(data.equals(Integer.toString(SystemConstants.PortalResultCode.FAILED))){
				JSONUtil.responseJSON(response, new AjaxResult(2,"更新会员信息失败,请稍后再试!"));
			}
			
//		}else{
//			JSONUtil.responseJSON(response, new AjaxResult(3,"连接失败,请稍后再试!"));
//		}
	}

	/**
	 * 依据房型刷房号
	 */
	@RequestMapping("/queryRoomId.do")
	public void queryRoomId(HttpServletRequest request,
			HttpServletResponse response, String roomtype) {

		LoginUser loginUser = (LoginUser) request.getSession(true)
				.getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchid = loginUser.getStaff().getBranchId();
		List<?> roomids = checkInDirectService.findBySQL("queryRoomIdByType",
				new String[] { branchid, roomtype }, true);
		if (roomids == null || roomids.size() == 0) {
			JSONUtil.responseJSON(response, new AjaxResult(1, "该房型下暂无可入住房间!"));
		} else {
			JSONUtil.responseJSON(response, roomids);
		}
	}

	/**
	 * 手动入住会员类型刷房价
	 */
	@RequestMapping("/queryRoomPrice.do")
	public void queryRoomPrice(HttpServletRequest request,
			HttpServletResponse response, String roomtype, String memberrank,
			boolean hourRoomFlag) {
		LoginUser loginUser = (LoginUser) request.getSession(true)
				.getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchid = loginUser.getStaff().getBranchId();
		List roomprice = checkInDirectService.findBySQL("getroompricebyrank", new String[]{roomtype,branchid,memberrank}, true);
		JSONUtil.responseJSON(response, roomprice);
	}

	/**
	 * 非会员查询门市价
	 */
	@RequestMapping("/queryMSJ.do")
	public void queryMSJ(HttpServletRequest request,
			HttpServletResponse response, String roomtype, boolean flag) {

		LoginUser loginUser = (LoginUser) request.getSession(true)
				.getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchid = loginUser.getStaff().getBranchId();
		if (flag) {// 全天房
			List<?> Msj = checkInDirectService.findBySQL("queryMSJ",
					new String[] { branchid, roomtype, "2" }, true);
			if (Msj == null || Msj.size() == 0) {// 如果查不到就查基准价
				Msj = checkInDirectService.findBySQL("queryMSJ", new String[] {
						branchid, roomtype, "1" }, true);
				if (Msj == null || Msj.size() == 0) {
					JSONUtil.responseJSON(response, new AjaxResult(1,
							"请前往后台配置房价相关信息"));
				} else {
					JSONUtil.responseJSON(response, Msj);
				}
			} else {
				JSONUtil.responseJSON(response, Msj);
			}

		} else {// 钟点房
			List<?> Msj = checkInDirectService.findBySQL("searchMSJ",
					new String[] { branchid, roomtype, "2" }, true);
			if (Msj == null || Msj.size() == 0) {// 如果查不到就查基准价
				Msj = checkInDirectService.findBySQL("searchMSJ", new String[] {
						branchid, roomtype, "1" }, true);
				if (Msj == null || Msj.size() == 0) {
					JSONUtil.responseJSON(response, new AjaxResult(1,
							"请前往后台配置房价相关信息"));
				} else {
					JSONUtil.responseJSON(response, Msj);
				}
			} else {
				JSONUtil.responseJSON(response, Msj);
			}
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
	@RequestMapping("/CheckInDirectWithNoConnection.do")
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
				String mbId = branchId.substring(branchId.length()-3)+checkInDirectService.getSequence("SE_MEMBERS_ID");
				String accountId = branchId.substring(branchId.length()-3)+checkInDirectService.getSequence("SE_ACCOUNTS_ID");
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
				checkInDirectService.save(mb);
				checkInDirectService.save(account);
				checkInDirectService.getSession().flush();
				//会员账户表同步手动到云端
				SysParam systemtype = (SysParam) checkInDirectService.findOneByProperties(SysParam.class, "paramType","SYSTEMTYPE", "status", "1");
				String stype = systemtype.getContent().toString();
				SysParam param = (SysParam) checkInDirectService.findOneByProperties(SysParam.class, "paramType","REMOTE_PATH", "status", "1");				
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
				Member m = (Member) checkInDirectService.findOneByProperties(Member.class, "idcard", newidcardnumber[i]);
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
		+checkInDirectService.getSequence("SEQ_PRICE_DATAID");
		String orderId = DateUtil.currentDate("yyMMdd") + branchId + CommonConstants.OrderSource.Branch
		+ checkInDirectService.getSequence("SEQ_NEW_ORDER");
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
		Member m = (Member) checkInDirectService.findOneByProperties(Member.class, "idcard", newidcardnumber[0]);
		order.setOrderUser(newmemberId[0].trim() == "" ? m.getMemberId() : newmemberId[0].trim());
//取消	order.setUserCheckin(newmemberid.substring(0,newmemberid.length()-1));
		order.setStatus("3");// 订单为在住状态,产生房单
		order.setRecordUser(loginUser.getStaff().getStaffId());
		//查预订人当前门店的门市价
		List MSJ = null;
		if(hourRoomFlag){
			 MSJ = checkInDirectService.findBySQL("getMSJ", new String[]{"1",branchId,roomType,"1","1",branchId,roomType,"1"}, true);
			
		}else{
			 MSJ = checkInDirectService.findBySQL("getMSJ", new String[]{"1",branchId,roomType,"2","1",branchId,roomType,"2"}, true);

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
				+ checkInDirectService.getSequence("SEQ_NEW_BILL");
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
			cu.setCheckuserId(branchId+ CommonConstants.OrderSource.Branch + checkInDirectService.getSequence("SEQ_NEW_CHECKUSER"));// 入住人表主键门店+来源+序列号
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
			checkInDirectService.save(cu);
			
		}
		checkInDirectService.save(order);
		checkInDirectService.save(check);
		checkInDirectService.save(bill);	
		checkInDirectService.save(op);
		//更新入住房间状态在住
		Room room = (Room) checkInDirectService.findOneByProperties(Room.class, "roomKey.branchId", branchId, "roomKey.roomId",roomId,"roomType",roomType,"theme","1");
		room.setStatus("3");
		checkInDirectService.update(room);
		//更新roomstatus房间数量
		RoomStatus rs = (RoomStatus) checkInDirectService.findOneByProperties(RoomStatus.class, "branchId", branchId, "roomType",roomType);
		rs.setCount(rs.getCount()-1);
		rs.setSellnum(rs.getSellnum()-1);
		checkInDirectService.update(rs);
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
	@RequestMapping("/CheckInDirectWithConnection.do")
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
				String mbId = checkInDirectService.getCloudSequence("SEQ_MEMBER_ID");
				String accountId = checkInDirectService.getCloudSequence("SEQ_ACCOUNT_ID");
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
				checkInDirectService.save(mb);
				checkInDirectService.save(account);
				checkInDirectService.getSession().flush();
				//会员账户表同步手动到云端
				SysParam systemtype = (SysParam) checkInDirectService.findOneByProperties(SysParam.class, "paramType","SYSTEMTYPE", "status", "1");
				String stype = systemtype.getContent().toString();
				SysParam param = (SysParam) checkInDirectService.findOneByProperties(SysParam.class, "paramType","REMOTE_PATH", "status", "1");				
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
				Member m = (Member) checkInDirectService.findOneByProperties(Member.class, "idcard", newidcardnumber[i]);
				newmemberId[i] = m.getMemberId();
			}
			
			newmemberid += newmemberId[i]+",";
			
		}*/
		
		//根据录入的companyOrMemberSelect来判断是手机号还是公司会员编号
		Member getPhonebymem = (Member) checkInDirectService.findOneByProperties(Member.class, "corporationId", companyOrMemberSelect);
		if(getPhonebymem == null){
			getPhonebymem = (Member) checkInDirectService.findOneByProperties(Member.class, "mobile", companyOrMemberSelect);
		}
		
		//生成订单表和房单表,账单表,入住人表,订单房单房价表
		Check check = new Check();
		Bill bill = new Bill();
		Order order = new Order();
		OrdchePrice op = new OrdchePrice();
		String orderId = DateUtil.currentDate("yyMMdd") + branchId + CommonConstants.OrderSource.Branch
		+ checkInDirectService.getSequence("SEQ_NEW_ORDER");
		String opId = DateUtil.currentDate("yyMMdd") + branchId+CommonConstants.OrderSource.Branch
		+checkInDirectService.getSequence("SEQ_PRICE_DATAID");
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
		Member m = (Member) checkInDirectService.findOneByProperties(Member.class, "idcard", newidcardnumber[0]);
		order.setOrderUser(discountPerson); //(discountPerson == "" || discountPerson == null) ? (newmemberId[0].trim() == "" ? m.getMemberId() : newmemberId[0].trim()) : discountPerson
//取消	order.setUserCheckin(newmemberid.substring(0,newmemberid.length()-1));
		order.setStatus("3");// 订单为在住状态,产生房单
		order.setRecordUser(loginUser.getStaff().getStaffId());
		//查预订人当前门店的门市价
		List MSJ = null;
		if(hourRoomFlag){
			 MSJ = checkInDirectService.findBySQL("getMSJ", new String[]{"1",branchId,roomType,"1","1",branchId,roomType,"1"}, true);
			
		}else{
			 MSJ = checkInDirectService.findBySQL("getMSJ", new String[]{"1",branchId,roomType,"2","1",branchId,roomType,"2"}, true);

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
				+ checkInDirectService.getSequence("SEQ_NEW_BILL");
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
			cu.setCheckuserId(branchId+ CommonConstants.OrderSource.Branch+ checkInDirectService.getSequence("SEQ_NEW_CHECKUSER"));// 入住人表主键门店+来源+序列号
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
			checkInDirectService.save(cu);
			
		}
		
		checkInDirectService.save(order);
		checkInDirectService.getSession().flush();
		checkInDirectService.save(check);
		checkInDirectService.save(bill);
		//checkInDirectService.save(op);
		//更新入住房间状态在住
		Room room = (Room) checkInDirectService.findOneByProperties(Room.class, "roomKey.branchId", branchId, "roomKey.roomId",roomId,"roomType",roomType,"theme","1");
		room.setStatus("3");
		checkInDirectService.update(room);
		//更新roomstatus房间数量
		RoomStatus rs = (RoomStatus) checkInDirectService.findOneByProperties(RoomStatus.class, "branchId", branchId, "roomType",roomType);
		rs.setCount(rs.getCount()-1);
		rs.setSellnum(rs.getSellnum()-1);
		checkInDirectService.update(rs);
		ordercontroller.saveEveryDayPrice(request, response, orderId, roomType);
		JSONUtil.responseJSON(response, new AjaxResult(2,"入住成功!"));
	
	}

	/**
	 * 获取前台身份证的会员信息
	 */
	@RequestMapping("/getGuestInfo.do")
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
							checkInDirectService.save(account);
					}
					checkInDirectService.save(mb);
					//刷会员信息,房价到前台页面
					List<?> memberdata = null;
					if(hourRoomFlag){//true为钟点房
						memberdata = checkInDirectService.findBySQL("gethourpricecheckin", new String[]{obj.getString("MEMBER_ID"),roomtype,branchid,obj.getString("MEMBER_RANK")}, true);//会员号,房型,门店号,会员等级
					}else{
						memberdata = checkInDirectService.findBySQL("getfullpricecheckin", new String[]{obj.getString("MEMBER_ID"),roomtype,branchid,obj.getString("MEMBER_RANK")}, true);
					}
					
					formalmap.put("memberdata", memberdata);
					}else{
						//刷会员信息,房价到前台页面
						List<?> memberdata = null;
						List price = null;
						if(hourRoomFlag){//true为钟点房
							memberdata = checkInDirectService.findBySQL("gethourpricecheckin", new String[]{obj.getString("MEMBER_ID"),roomtype,branchid,obj.getString("MEMBER_RANK")}, true);//会员号,房型,门店号,会员等级
						}else{
							memberdata = checkInDirectService.findBySQL("getfullpricecheckin", new String[]{obj.getString("MEMBER_ID"),roomtype,branchid,obj.getString("MEMBER_RANK")}, true);
						}
						Member mm = null;
						if("0".equals(((Map) (memberdata.get(0))).get("MEMBERRANK").toString())){//公司会员门市价+折扣
							mm = (Member) checkInDirectService.findOneByProperties(Member.class, "memberId",obj.getString("MEMBER_ID"));
							if(mm.getCorporationId()!= null){
								if(hourRoomFlag){
									price = checkInDirectService.findBySQL("getMSJ", new String[]{"1",branchid,roomtype,"2","1",branchid,roomtype,"2"}, true);
									
								}else{
									price = checkInDirectService.findBySQL("getMSJ", new String[]{"1",branchid,roomtype,"1","1",branchid,roomtype,"1"}, true);
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

	/**
	 *重置房价
	 */
	@RequestMapping("/refreshRoomPrice.do")
	public void refreshRoomPrice(HttpServletRequest request,
			HttpServletResponse response, String roomtype, String rpId) {

		LoginUser loginUser = (LoginUser) request.getSession(true)
				.getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchid = loginUser.getStaff().getBranchId();
		RoomPrice roomprice = (RoomPrice) checkInDirectService
				.findOneByProperties(RoomPrice.class, "state", "5", "theme",
						"1", "roomPriceId.branchId", branchid,
						"roomPriceId.rpId", rpId, "roomPriceId.roomType",
						roomtype, "roomPriceId.rpKind", "2",
						"roomPriceId.status", "2");
		if (roomprice == null) {
			roomprice = (RoomPrice) checkInDirectService.findOneByProperties(
					RoomPrice.class, "state", "5", "theme", "1",
					"roomPriceId.branchId", branchid, "roomPriceId.rpId", rpId,
					"roomPriceId.roomType", roomtype, "roomPriceId.rpKind",
					"2", "roomPriceId.status", "1");
			if (roomprice == null) {
				JSONUtil.responseJSON(response, new AjaxResult(2,
						"请前往后台配置房价相关信息"));

			} else {
				if (roomprice.getRoomPrice() == null) {
					JSONUtil.responseJSON(response, new AjaxResult(3,
							"请前往后台配置基准价"));
				} else {
					Double price = roomprice.getRoomPrice();
					Map<String, Object> maps = new HashMap<String, Object>();
					maps.put("price", price);
					JSONUtil.responseJSON(response, new AjaxResult(11, "钟点房房价",
							maps));

				}
			}
		} else {
			Double price = roomprice.getRoomPrice();
			Map<String, Object> maps = new HashMap<String, Object>();
			maps.put("price", price);
			JSONUtil.responseJSON(response, new AjaxResult(11, "钟点房房价", maps));
		}

	}
	public boolean existMember(String memberId){
		
		Member member = (Member) checkInDirectService.findOneByProperties(Member.class, "memberId", memberId);
		if(member != null){
			return true;
		}
		return false;
		
	}
	/**
	 * 联网注册会员之前用该手机号查询云端是否存在会员
	 * @param phone
	 * @return
	 */
	public String isMember(String phone){
		com.ideassoft.portal.DataDealPortalService service = new com.ideassoft.portal.DataDealPortalService();
		com.ideassoft.portal.IDataDealPortal portal = service.getDataDealPortalPort();
		String member = portal.call(SystemConstants.EnginType.COMMON_DATA, 
				SystemConstants.Movement.CUSTOM_QUERY, "{ function: \"checkInDirect.isMember\", data:{phone:" + phone  +" } }");//查出会员id,级别,级别名称
		return member;
	}
	@RequestMapping("/checkConnection.do")
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
	 * 任意钟点房刷新房间价格事件
	 * @param request
	 * @param response
	 */
	@RequestMapping("/refreshPrice.do")
	public void refreshPrice(HttpServletRequest request,HttpServletResponse response){
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchid = loginUser.getStaff().getBranchId();
		String roomtype = request.getParameter("roomtype");
		String rpId = request.getParameter("rpId");
		boolean hourRoomFlag = Boolean.getBoolean(request.getParameter("hourRoomFlag"));
		Map<String,Object> map = new HashMap<String, Object>();
		RoomPrice roomprice = null;
		if(hourRoomFlag){
			roomprice = (RoomPrice) checkInDirectService
			.findOneByProperties(RoomPrice.class, "state", "5", "theme",
					"1", "roomPriceId.branchId",branchid,
					"roomPriceId.rpId", rpId, "roomPriceId.roomType",
					roomtype, "roomPriceId.rpKind", "2",
					"roomPriceId.status", "2");
			if(roomprice == null){
				roomprice = (RoomPrice) checkInDirectService
				.findOneByProperties(RoomPrice.class, "state", "5", "theme",
						"1", "roomPriceId.branchId",branchid,
						"roomPriceId.rpId", rpId, "roomPriceId.roomType",
						roomtype, "roomPriceId.rpKind", "2",
						"roomPriceId.status", "1");
			}
		}else{
			roomprice = (RoomPrice) checkInDirectService
			.findOneByProperties(RoomPrice.class, "state", "5", "theme",
					"1", "roomPriceId.branchId",branchid,
					"roomPriceId.rpId", rpId, "roomPriceId.roomType",
					roomtype, "roomPriceId.rpKind", "1",
					"roomPriceId.status", "2");
			if(roomprice == null){
				roomprice = (RoomPrice) checkInDirectService
				.findOneByProperties(RoomPrice.class, "state", "5", "theme",
						"1", "roomPriceId.branchId",branchid,
						"roomPriceId.rpId", rpId, "roomPriceId.roomType",
						roomtype, "roomPriceId.rpKind", "1",
						"roomPriceId.status", "1");
			}
		}
		map.put("price", roomprice.getRoomPrice());
		map.put("rpId", rpId);
		map.put("rpname", roomprice.getRpName());
		JSONUtil.responseJSON(response, new AjaxResult(1,"钟点房刷房价",map));
		
	}
	/**
	 * 获取公司会员折扣价(门市价)
	 * @param request
	 * @param response
	 */
	@RequestMapping("/discountPrice.do")
	public void discountPrice(HttpServletRequest request,HttpServletResponse response){
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchid = loginUser.getStaff().getBranchId();
		boolean hourRoomFlag = Boolean.valueOf(request.getParameter("hourRoomFlag"));
		String roomtype = request.getParameter("roomtype");
		String corporationIdSelect = request.getParameter("corporationIdSelect");
		//云端查询
		com.ideassoft.portal.DataDealPortalService service = new com.ideassoft.portal.DataDealPortalService();
		com.ideassoft.portal.IDataDealPortal portal = service.getDataDealPortalPort();
		String discountprice = null;
		String discount = portal.call(SystemConstants.EnginType.COMMON_DATA, 
				SystemConstants.Movement.CUSTOM_QUERY, "{ function: \"checkInDirect.queryDiscountPrice\", data:{corporationIdSelect:" + corporationIdSelect  +" } }");//查出公司会员id价格和折扣
		
		if(!(discount.equals(Integer.toString(SystemConstants.PortalResultCode.NULL))) && !(discount.equals(Integer.toString(SystemConstants.PortalResultCode.FAILED)))){
			discountprice = discount.substring(0,discount.indexOf(":"));
		}
		List price = null;
		if(hourRoomFlag){
			price = checkInDirectService.findBySQL("getMSJ", new String[]{"1",branchid,roomtype,"2","1",branchid,roomtype,"2"}, true);
			
		}else{
			price = checkInDirectService.findBySQL("getMSJ", new String[]{"1",branchid,roomtype,"1","1",branchid,roomtype,"1"}, true);
		}
		String newprice = ((Map) (price.get(0))).get("ROOM_PRICE").toString();
		if (discountprice == null) {
			JSONUtil.responseJSON(response, new AjaxResult(2,"未查到公司会员信息!", null));
		} else {
			double price1 =Double.parseDouble(newprice)*(Double.parseDouble(discountprice)/100);
			DecimalFormat    df   = new DecimalFormat("######0.00");
			String price2 = df.format(price1);
			JSONUtil.responseJSON(response, new AjaxResult(1,"",price2+":"+discount.substring(discount.indexOf(":")+1)));
		}
	}
	
	/**
	 * @auThor ideas_jsm
	 * 获取前台身份证的会员信息
	 * getGuestInfoDirectLive.do
	 */
	@RequestMapping("/getGuestInfoDirectLive.do")
	public void getGuestInfoDirectLive(HttpServletRequest request, HttpServletResponse response, String idCard) {
		Map<String, Object> formalmap = new HashMap<String, Object>();
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchid = loginUser.getStaff().getBranchId();
//		if(HeartBeatClient.heartbeat){
			com.ideassoft.portal.DataDealPortalService service = new com.ideassoft.portal.DataDealPortalService();
			com.ideassoft.portal.IDataDealPortal portal = service.getDataDealPortalPort();
			String member = portal.call(SystemConstants.EnginType.COMMON_DATA, 
					SystemConstants.Movement.CUSTOM_QUERY, "{ function: \"checkInDirect.getMemberIdcardInDirectLive\", data:{idnumber:" + idCard  +" } }");//查出会员id,级别,级别名称
			
			if((member.equals(Integer.toString(SystemConstants.PortalResultCode.NULL)))){
				//云端无此会员默认注册临时会员	
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
			} else if(!(member.equals(Integer.toString(SystemConstants.PortalResultCode.NULL))) && !(member.equals(Integer.toString(SystemConstants.PortalResultCode.FAILED)))){
				//云端有会员信息,saveorupdate到本地
				org.json.JSONObject m;
				org.json.JSONObject ma;
				List<Member> memberSdata = new ArrayList<Member>();;
				try {
					
					List<Object> ms = ReflectUtil.setBeans(member, "Member");
					checkInDirectService.saveOrUpdateAll(ms);
					for(Object mem : ms){
						Member mss = (Member)mem;
						String memberId = mss.getMemberId();
						if(!StringUtil.isEmpty(memberId) && memberId != null){
							String memberAccount = portal.call(SystemConstants.EnginType.COMMON_DATA, 
									SystemConstants.Movement.CUSTOM_QUERY, "{ function: \"checkInDirect.getMemberAccountInDirectLive\", data:{idnumber:" + memberId  +" } }");
							if(!(memberAccount.equals(Integer.toString(SystemConstants.PortalResultCode.NULL))) && !(memberAccount.equals(Integer.toString(SystemConstants.PortalResultCode.FAILED)))){
								List<Object> maccount = ReflectUtil.setBeans(memberAccount, "MemberAccount");
								checkInDirectService.saveOrUpdateAll(maccount);
							}
							memberSdata.add(mss);	
						}	
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				formalmap.put("memberdata", memberSdata);
				JSONUtil.responseJSON(response, new AjaxResult(3,"会员",formalmap));
			}	
//		}else{//断网发送身份证信息之前台手动入住及非会员注册放在入住功能里面
//			Map<String,Object> memberdata = new HashMap<String, Object>();
//			memberdata.put("name", name);
//			memberdata.put("idcard", idnumber);
//			memberdata.put("gendor", map.get("gender")=="男" ? "1" : "0");
			
//			JSONUtil.responseJSON(response, new AjaxResult(1,"网络连接异常,还要继续入住吗~",memberdata));//断网情况下前台页面房价码可以自选,客人信息默认为非会员
//		}
	}
	
	
	/**
	 * 获取实时房价刷到页面
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/showRoomPriceDirectLive.do")
	public void showRoomPriceDirectLive(HttpServletRequest request,HttpServletResponse response, String roomtype, String idCard, boolean hourRoomFlag, boolean firsttime){
		Map<String, Object> formalmap = new HashMap<String, Object>();
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchid = loginUser.getStaff().getBranchId();
		String memberid1 = "";
		Double roomPrice = 0.00;
//		if(HeartBeatClient.heartbeat){
		// 根据branchId查询软件信息
		com.ideassoft.portal.DataDealPortalService service = new com.ideassoft.portal.DataDealPortalService();
		com.ideassoft.portal.IDataDealPortal portal = service.getDataDealPortalPort();
		// 查出会员id,级别,级别名称
		String member = portal.call(1, 1, "{ function: \"checkInDirect.getMemberBean\", data:{idcard:" + idCard + " } }");
		if((member.equals(Integer.toString(SystemConstants.PortalResultCode.NULL)))){
			//云端无此会员默认注册临时会员
			JSONUtil.responseJSON(response, new AjaxResult(1,"未查询到相关会员信息或该会员已失效，请先注册会员！"));	
		} else if("hasmult".equals(member)){
			JSONUtil.responseJSON(response, new AjaxResult(1,"存在多条注册信息,请先更新会员信息！"));
			return;
		} else if(!(member.equals(Integer.toString(SystemConstants.PortalResultCode.NULL))) && !(member.equals(Integer.toString(SystemConstants.PortalResultCode.FAILED)))){
			//云端有会员信息,saveorupdate到本地
			org.json.JSONObject m;
			org.json.JSONObject ma;
			List<?> ordermemberdata = new ArrayList<Object>();
			
			try {
				// 同步会员信息
				int result = SynchroMembers(idCard, memberid1, portal, member);
				if (result == SystemConstants.PortalResultCode.NULL) {
					new Exception();
				}
				//查询预定会员和价格
				JSONArray ms = new JSONArray(member);
				JSONObject onemember = ms.getJSONObject(0);
				                 //Member onemember = (Member)ms.get(0);
				//此处只有一个会员
				String memberRank = onemember.getString("MEMBER_RANK");
				String memberid = onemember.getString("MEMBER_ID");
				String membername = onemember.getString("MEMBER_NAME");
				String idcard1 = onemember.getString("IDCARD");
				String gendor = onemember.getString("GENDOR");
				// 根据会员等级查询房价码
				SysParam sysParam = (SysParam) checkInDirectService.findOneByProperties(SysParam.class, "paramType", "MEMBERRPID", "paramDesc",memberRank);
				//公司会员加钟点房的情况
				if(memberRank.equals("0") && hourRoomFlag){
					JSONUtil.responseJSON(response, new AjaxResult(2,"公司会员没有钟点房业务！",null));
					return;
				} else {
					if(hourRoomFlag){
						// [{RPID=JKJ, RPNAME=金卡价, ROOMTYPE=A2, MEMBERRANK=4, RPKIND=1, MEMBERID=10000031, MEMBERNAME=刘靖, IDCARD=320882199112204651, GENDOR=0, MOBILE=15396754667, PRICE=85}]
						// true为钟点房 获取时租价格 
						SinglePrice sp = new SinglePrice(branchid, "1", roomtype, "MSJ", RealPrice.PriceType.HOUR, new Date(), memberid);
						roomPrice = sp.checkRoomPrice();
						
						// 将数据封装值List集合中
						formalmap.put("RPID", sysParam.getContent());
						formalmap.put("RPNAME", sysParam.getParamName());
						formalmap.put("ROOMTYPE", roomtype);
						formalmap.put("MEMBERRANK", memberRank);
						formalmap.put("MEMBERID", memberid);
						formalmap.put("MEMBERNAME", membername);
						formalmap.put("IDCARD", idcard1);
						formalmap.put("GENDOR", gendor);
						formalmap.put("MOBILE", idCard);
						formalmap.put("PRICE", roomPrice.toString());
					}else{
						// false为日租房 获取日租价格
						SinglePrice sp = new SinglePrice(branchid, "1", roomtype, "MSJ", RealPrice.PriceType.DATE, new Date(), memberid);
						roomPrice = sp.checkRoomPrice();
						// 将数据封装值List集合中
						formalmap.put("RPID", sysParam.getContent());
						formalmap.put("RPNAME", sysParam.getParamName());
						formalmap.put("ROOMTYPE", roomtype);
						formalmap.put("MEMBERRANK", memberRank);
						formalmap.put("MEMBERID", memberid);
						formalmap.put("MEMBERNAME", membername);
						formalmap.put("IDCARD", idcard1);
						formalmap.put("GENDOR", gendor);
						formalmap.put("MOBILE", idCard);
						formalmap.put("PRICE", roomPrice.toString());
						
					}
					//如果是公司会员，查询出该房型的门市价和公司会员的折扣，替换掉ordermemberdata 中的数值
					if(memberRank.equals("0")){
						
						if ((onemember.getString("DISCOUNT") == null || StringUtil.isEmpty(onemember.getString("DISCOUNT")))) {
							JSONUtil.responseJSON(response, new AjaxResult(2,"未查到公司会员折扣信息!", null));
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
	
	private int SynchroMembers(String Mnumber, String memberid1,
			com.ideassoft.portal.IDataDealPortal portal, String mr)
			throws Exception, JSONException, ParseException {
		// 判断本地端是否有该会员数据
		List<?> memberdata = checkInDirectService.getMemberdata(Mnumber);
		if (memberdata.size() <= 0) {
			memberdata = checkInDirectService.findBySQL("queryMemCorId", new String[]{Mnumber}, true);
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
					checkInDirectService.save(member);
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
							checkInDirectService.save(memberaccount);
						}
					}
					memberid1 = memberId;
					// 本地端有会员数据，从云端更新该会员数据
				} else {
					String memberid = mrrr.getString("MEMBER_ID").toString();
					memberid1 = memberid;
					String mobile = mrrr.getString("MOBILE").toString();
					Member memberupdate = ((Member) (checkInDirectService.findOneByProperties(Member.class,
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
					checkInDirectService.update(memberupdate);
				}
			}
		}
		if (!StringUtils.isEmpty(memberid1)) {
			return SystemConstants.PortalResultCode.DONE;
		}
		return SystemConstants.PortalResultCode.NULL;
	}
	
}
