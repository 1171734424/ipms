package com.ideassoft.apartment.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.NumberFormat;
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
import org.apache.log4j.chainsaw.Main;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.core.FileURIResolver;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.hsqldb.lib.StringUtil;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.ideassoft.apartment.service.ApartmentCheckInService;
import com.ideassoft.apartment.service.ApartmentRentService;
import com.ideassoft.bean.Aptorder;
import com.ideassoft.bean.AptorderDetail;
import com.ideassoft.bean.Bill;
import com.ideassoft.bean.Branch;
import com.ideassoft.bean.CheckUser;
import com.ideassoft.bean.Contrart;
import com.ideassoft.bean.DataSource;
import com.ideassoft.bean.Equipment;
import com.ideassoft.bean.Member;
import com.ideassoft.bean.MemberRank;
import com.ideassoft.bean.OperateLog;
import com.ideassoft.bean.RepairApply;
import com.ideassoft.bean.Reserved;
import com.ideassoft.bean.Room;
import com.ideassoft.bean.Staff;
import com.ideassoft.bean.SwitchRoom;
import com.ideassoft.bean.SysParam;
import com.ideassoft.bean.WarningLog;
import com.ideassoft.bean.WaterAndElectricity;
import com.ideassoft.core.ajax.AjaxResult;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.constants.CommonConstants;
import com.ideassoft.core.constants.SystemConstants;
import com.ideassoft.core.jdbc.SqlBuilder;
import com.ideassoft.core.page.Pagination;
import com.ideassoft.pms.service.RoomService;
import com.ideassoft.portal.DataDealPortalService;
import com.ideassoft.portal.IDataDealPortal;
import com.ideassoft.price.SinglePrice;
import com.ideassoft.util.CardUtil;
import com.ideassoft.util.DateUtil;
import com.ideassoft.util.JSONUtil;
import com.ideassoft.util.PriceUtil;
import com.ideassoft.util.ReflectUtil;
import com.ideassoft.util.RequestUtil;

@Transactional
@Controller
public class ApartmentCheckInController {

	@Autowired
	private ApartmentCheckInService apartmentCheckInService;
	
	@Autowired
	private ApartmentRentService apartmentRentService;

	@Autowired
	private RoomService roomService;

	/**
	 * 查询全部有效合同
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryApartment.do")
	public ModelAndView queryApartment(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		String memberName = request.getParameter("memberName");
		String mobile = request.getParameter("mobile");
		Pagination pagination = SqlBuilder.buildPagination(request);
		LoginUser loginUser = (LoginUser) request.getSession(true)
				.getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		List<?> list = apartmentCheckInService.findBySQLWithPagination(
				"queryApartment", new String[] { "", branchId, memberName,
						mobile }, pagination, true);
		mv.addObject("list", list);
		mv.addObject("pagination", pagination);
		mv.addObject("memberName", memberName);
		mv.addObject("mobile", mobile);
		mv.addObject("action", "queryApartment.do");
		mv.setViewName("page/apartment/apartmentmainmenu/apartmentcontract/apartmentcheckininfo");
		return mv;
	}

	/**
	 * 查询在住的合同
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryApartmentCheckIn.do")
	public ModelAndView queryApartmentCheckIn(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		String memberName = request.getParameter("memberName");
		String mobile = request.getParameter("mobile");
		Pagination pagination = SqlBuilder.buildPagination(request);
		LoginUser loginUser = (LoginUser) request.getSession(true)
				.getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		List<?> list = apartmentCheckInService.findBySQLWithPagination(
				"queryApartment", new String[] { "4", branchId, memberName,
						mobile }, pagination, true);
		mv.addObject("list", list);
		mv.addObject("pagination", pagination);
		mv.addObject("memberName", memberName);
		mv.addObject("mobile", mobile);
		mv.addObject("action", "queryApartmentCheckIn.do");
		mv.setViewName("page/apartment/apartmentmainmenu/apartmentcontract/apartmentcheckininfo");
		return mv;
	}

	/**
	 * 查询待住的有效合同
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryApartmentNoCheckIn.do")
	public ModelAndView queryApartmentNoCheckIn(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		String memberName = request.getParameter("memberName");
		String mobile = request.getParameter("mobile");
		Pagination pagination = SqlBuilder.buildPagination(request);
		LoginUser loginUser = (LoginUser) request.getSession(true)
				.getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		List<?> list = apartmentCheckInService.findBySQLWithPagination(
				"queryApartment", new String[] { "1", branchId, memberName,
						mobile }, pagination, true);
		mv.addObject("list", list);
		mv.addObject("pagination", pagination);
		mv.addObject("memberName", memberName);
		mv.addObject("mobile", mobile);
		mv.addObject("action", "queryApartmentNoCheckIn.do");
		mv.setViewName("page/apartment/apartmentmainmenu/apartmentcontract/apartmentcheckininfo");
		return mv;
	}

	@RequestMapping("/queryApartmentCheckOff.do")
	public ModelAndView queryApartmentCheckOff(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		String memberName = request.getParameter("memberName");
		String mobile = request.getParameter("mobile");
		Pagination pagination = SqlBuilder.buildPagination(request);
		LoginUser loginUser = (LoginUser) request.getSession(true)
				.getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		List<?> list = apartmentCheckInService.findBySQLWithPagination(
				"apartmentCheckOff", new String[] { branchId, memberName,
						mobile }, pagination, true);
		mv.addObject("list", list);
		mv.addObject("pagination", pagination);
		mv.addObject("memberName", memberName);
		mv.addObject("mobile", mobile);
		mv.addObject("action", "queryApartmentCheckOff.do");
		mv.setViewName("page/apartment/apartmentmainmenu/apartmentcontract/apartmentcheckininfo");
		return mv;
	}

	/**
	 * 根据合同号查询门店下的合同
	 * 
	 * @param request
	 * @param response
	 * @param contractId
	 *            合同表的 ID 号
	 * @throws Exception
	 */
	@RequestMapping("/quertApartmentInfo.do")
	public void quertApartmentInfo(HttpServletRequest request,
			HttpServletResponse response, String contractId) throws Exception {
		LoginUser loginUser = (LoginUser) request.getSession(true)
				.getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		List<?> list = apartmentCheckInService.findBySQL("apartmentInfo",
				new String[] { loginUser.getStaff().getBranchId(), contractId,
						"" }, true);
		JSONUtil.responseJSON(response, list);
	}

	/**
	 * 公寓入住
	 * 
	 * @param request
	 * @param response
	 * @param mobile
	 *            手机号
	 * @param contractId
	 *            合同表的 ID 号
	 * @throws UnknownHostException
	 * @throws CloneNotSupportedException
	 */
	@RequestMapping("/apartmentCheckIn.do")
	@SuppressWarnings("unchecked")
	public void apartmentCheckIn(HttpServletRequest request,
			HttpServletResponse response, String contractId)
			throws UnknownHostException, CloneNotSupportedException {
//		DataDealPortalService service = new DataDealPortalService();
//		IDataDealPortal portal = service.getDataDealPortalPort();
		LoginUser loginUser = (LoginUser) request.getSession(true)
				.getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Contrart contract = (Contrart) apartmentCheckInService.findById(
				Contrart.class, contractId);
		contract.setStatus(CommonConstants.ApartmentStatus.CheckOn);
		contract.setRecordTime(new Date());

		OperateLog operlog = new OperateLog();
		operlog.setBranchId(contract.getBranchId());
		operlog.setLogId(DateUtil.currentDate("yyMMdd")
				+ contract.getBranchId()
				+ apartmentCheckInService.getSequence("SEQ_OPERATELOG_ID"));
		String operid = InetAddress.getLocalHost().toString();// IP地址
		operid = (String) operid.subSequence(operid.indexOf("/") + 1,
				operid.length());
		operlog.setOperIp(operid);
		operlog.setOperType("5");
		operlog.setOperModule("入住操作");
		operlog.setContent("合同号" + contract.getContrartId() + "已经入住");
		operlog.setRecordUser(loginUser.getStaff().getStaffId());
		operlog.setRecordTime(new Date());

		List<?> listcheckuser = apartmentCheckInService.findByProperties(
				CheckUser.class, "checkId", contractId, "checkinType",
				CommonConstants.CheckinType.ORDER, "status",
				CommonConstants.CheckUserStatus.ON);

		if (listcheckuser != null && !listcheckuser.isEmpty()) {
			for (Object object : listcheckuser) {
				CheckUser oldcheckuser = (CheckUser) object;
				CheckUser newcheckuser = new CheckUser();
				newcheckuser = oldcheckuser.clone();
				String checkuserId = contract.getBranchId()
						+ CommonConstants.OrderSource.Branch
						+ apartmentCheckInService
								.getSequence("SEQ_NEW_CHECKUSER");
				newcheckuser.setCheckuserId(checkuserId);
				newcheckuser.setCheckinType(CommonConstants.CheckinType.CHECK);
				apartmentCheckInService.save(newcheckuser);
			}
		}

		List<RepairApply> repairApplys = apartmentCheckInService
				.findByProperties(RepairApply.class, "branchId",
						contract.getBranchId(), "roomId", contract.getRoomId());
		String status = "3";
		if (repairApplys.size() > 0) {
			for (RepairApply repairApply : repairApplys) {
				if (repairApply.getStatus().equals("2")
						|| repairApply.getStatus().equals("3")) {
					status = "W";
				}
			}
		}

//		String code = portal.call(SystemConstants.EnginType.COMMON_DATA,
//				SystemConstants.Movement.CUSTOM_QUERY,
//				"{ function: \"apartmentList.checkInHydropower\", "
//						+ "data:{branchId:" + contract.getBranchId()
//						+ ",roomId:" + contract.getRoomId() + ",orderId:"
//						+ contract.getContrartId() + "  } }");
//
//		if (code.equals(String.valueOf(SystemConstants.PortalResultCode.NULL))) {
//			WarningLog warningLog = new WarningLog();
//			warningLog.setLogId(DateUtil.currentDate("yyMMdd")
//					+ contract.getBranchId()
//					+ apartmentCheckInService.getSequence("SEQ_WARNINGLOG_ID"));
//			warningLog.setBranchId(contract.getBranchId());
//			warningLog.setRoomId(contract.getRoomId());
//			warningLog
//					.setSerialNo(SystemConstants.PortalResultCode.CONNECTIONFAIL);
//			warningLog.setWarningType(SystemConstants.WarningType.CONNECTION);
//			warningLog.setWarningDate(new Date());
//			warningLog.setStatus(SystemConstants.WarningStatus.VALID);
//			warningLog.setRecordTime(new Date());
//			warningLog.setRecordUser(loginUser.getStaff().getStaffId());
//			warningLog.setRemark("合同号" + contract.getContrartId() + DateUtil.d2s(new Date()) + "入住时连接水电后台失败!");
//		}

		apartmentCheckInService.save(operlog);
		apartmentCheckInService.update(contract);
		roomService.upateroomstatus(contract.getBranchId(),
				contract.getRoomId(), status);
		apartmentRentService.AptOrderCheckIn(contract.getContrartId());
		JSONUtil.responseJSON(response, new AjaxResult(0, "入住成功!"));
	}

	/**
	 * 从TB_C_CHECKUSER中根据合同ID号获取当前入住人信息
	 * 
	 * @param request
	 * @param response
	 * @param contractId
	 *            合同表的 ID 号
	 */
	@RequestMapping("/queryCheckUser.do")
	public void queryCheckUser(HttpServletRequest request,
			HttpServletResponse response, String contractId) {
		@SuppressWarnings("unchecked")
		List<CheckUser> list = apartmentCheckInService
				.findByPropertiesWithSort(CheckUser.class, "checkuserType",
						"asc", "checkId", contractId);
		JSONUtil.responseJSON(response, list);
	}

	/**
	 * 根据手机号从TB_C_MEMBER表中查询会员信息
	 * 
	 * @param request
	 * @param response
	 * @param mobile
	 *            手机号
	 */
	@RequestMapping("/loadCheckUser.do")
	public void loadCheckUser(HttpServletRequest request,
			HttpServletResponse response, String mobile) {
		Member member = (Member) roomService.findOneByProperties(Member.class,
				"mobile", mobile);
		MemberRank memberrank = (MemberRank) roomService.findOneByProperties(
				MemberRank.class, "memberRank", member.getMemberRank());
		String rankName = memberrank.getRankName();
		String birthday = "";
		if (member.getBirthday() != null) {
			birthday = DateUtil.d2s(member.getBirthday(), "yyyy/MM/dd");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("member", member);
		map.put("rankName", rankName);
		map.put("birthday", birthday);
		JSONUtil.responseJSON(response, map);
	}

	/**
	 * 公寓退房
	 * 
	 * @param request
	 * @param response
	 * @param contractId
	 *            合同表 ID 号
	 * @throws UnknownHostException
	 * @throws ParseException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/apCheckOut.do")
	public void checkOut(HttpServletRequest request,
			HttpServletResponse response, String contractId, String status)
			throws UnknownHostException, ParseException {
		String lastcontractbranchname = "";
		String message = "";
		LoginUser loginUser = (LoginUser) request.getSession(true)
				.getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		List<CheckUser> checkUsers = apartmentCheckInService
				.findByPropertiesWithSort(CheckUser.class, "checkuserType",
						"asc", "checkId", contractId);
		Contrart contract = (Contrart) apartmentCheckInService.findById(
				Contrart.class, contractId);
		String lastcontractbranchid = contract.getBranchId();
		Branch br = (Branch)apartmentCheckInService.findOneByProperties(Branch.class, "branchId", lastcontractbranchid);
		if(br != null){
			lastcontractbranchname = br.getBranchName();
		}
		String lastcontractroomid = contract.getRoomId();
		String lastcontractroomtype = contract.getRoomType();
		contract.setStatus("3");
		contract.setCheckOutTime(new Date());

		if (checkUsers.size() > 0) {
			for (CheckUser checkUser : checkUsers) {
				checkUser.setStatus("0");
				apartmentCheckInService.update(checkUser);
			}
		}
		if (status.equals("0")) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");
			String startTime = contract.getAptRenewTime() != null ? sdf
					.format(contract.getAptRenewTime()) : sdf.format(contract
					.getStartTime());// contract.getAptRenewTime()!=null ?
										// sdf.format(contract.getAptRenewTime())
										// :
			// String startTime = sdf.format();
			String endTime = sdf.format(contract.getContrartEndTime());
			Calendar sTime = Calendar.getInstance();
			Calendar eTime = Calendar.getInstance();
			sTime.setTime(sdf.parse(startTime));
			// sTime.setTime(new Date());
			eTime.setTime(sdf.parse(endTime));
			int result = sTime.get(Calendar.MONTH) - eTime.get(Calendar.MONTH);
			int month = (sTime.get(Calendar.YEAR) - eTime.get(Calendar.YEAR)) * 12;
			int num = Math.abs(month + result);
			for (int i = 0; i < num; i++) {
				Bill bill = new Bill();
				String billId = DateUtil.currentDate("yyMMdd")
						+ contract.getBranchId()
						+ apartmentCheckInService.getSequence("SEQ_NEW_BILL");
				bill.setBillId(billId);
				bill.setBranchId(contract.getBranchId());
				bill.setCheckId(contract.getContrartId());
				bill.setProjectId("1234");
				bill.setProjectName("房费");
				bill.setDescribe("房费");
				bill.setCost(contract.getCash());
				bill.setPayment("1");
				bill.setPay(0.0);
				bill.setStatus("1");
				bill.setRecordTime(new Date(0));
				bill.setRecordUser(loginUser.getStaff().getStaffId());
				apartmentCheckInService.save(bill);
			}
			Bill ForeGift = (Bill) apartmentCheckInService.findOneByProperties(Bill.class, "branchId", contract.getBranchId(), "checkId", contract.getContrartId(),
					"projectId", CommonConstants.BillProject.ForeGift);
			ForeGift.setRefundStatus("1");
			apartmentCheckInService.update(ForeGift);
		} else if (status.equals("1")) {
			// 由于不正常退款时-不退押金，只退部分房费
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");
			SimpleDateFormat sdfa = new SimpleDateFormat("yyyy/MM/dd");
			String startTime = contract.getAptRenewTime() != null ? sdf
					.format(contract.getAptRenewTime()) : sdf.format(contract
					.getStartTime());
			String endTime = sdf.format(contract.getContrartEndTime());
			Calendar sTime = Calendar.getInstance();
			Calendar eTime = Calendar.getInstance();
			sTime.setTime(sdf.parse(startTime));
			eTime.setTime(sdf.parse(endTime));
			int result = sTime.get(Calendar.MONTH) - eTime.get(Calendar.MONTH);
			int month = (sTime.get(Calendar.YEAR) - eTime.get(Calendar.YEAR)) * 12;
			int num = Math.abs(month + result);
			
			// 先将押金扣掉
			Bill billGoregift = new Bill();
			String billGoregiftId = DateUtil.currentDate("yyMMdd")
					+ contract.getBranchId()
					+ apartmentCheckInService.getSequence("SEQ_NEW_BILL");
			billGoregift.setBillId(billGoregiftId);
			billGoregift.setBranchId(contract.getBranchId());
			billGoregift.setCheckId(contract.getContrartId());
			billGoregift.setProjectId("1987");
			billGoregift.setProjectName("押金赔偿");
			billGoregift.setDescribe("押金赔偿");
			billGoregift.setCost(Double.parseDouble(contract.getUnitPrice()));
			billGoregift.setPayment("1");
			billGoregift.setPay(0.0);
			billGoregift.setStatus("1");
			billGoregift.setRecordTime(new Date());
			billGoregift.setRecordUser(loginUser.getStaff().getStaffId());
			apartmentCheckInService.save(billGoregift);
			
			// 再计算已经住了多少天的费用
			Double price = (double) Math.round((Double.parseDouble(contract.getUnitPrice()) / Double.parseDouble("30"))* 100)/100;
			String dayTime = sdfa.format(new Date());
			String firstDay = sdf.format(new Date()) + "/" + sdfa.format(contract.getStartTime()).split("/")[2];
			int days = (int) ((sdfa.parse(dayTime).getTime() - sdfa.parse(firstDay).getTime()) / (1000 * 3600 * 24)) + 1;
			//days = days == 0 ? 1 : days;
			Double money = days * price;
			Bill bill = new Bill();
			String billId = DateUtil.currentDate("yyMMdd") + contract.getBranchId() + apartmentCheckInService.getSequence("SEQ_NEW_BILL");
			bill.setBillId(billId);
			bill.setBranchId(contract.getBranchId());
			bill.setCheckId(contract.getContrartId());
			bill.setProjectId("1234");
			bill.setProjectName("房费");
			bill.setDescribe("房费");
			bill.setCost(money);
			bill.setPayment("1");
			bill.setPay(0.0);
			bill.setStatus("1");
			bill.setRecordTime(new Date());
			bill.setRecordUser(loginUser.getStaff().getStaffId());
			apartmentCheckInService.save(bill);
			// 计算剩余房费
			/*List<Bill> PayRent = apartmentCheckInService.findByPropertiesWithSort(Bill.class, "branchId", contract.getBranchId(), "checkId", contract.getContrartId(),
					"projectId", CommonConstants.BillProject.Rent);*/
			double apartRent = num * Double.parseDouble(contract.getUnitPrice());
			double newApartRent = (double)Math.round((apartRent - money)*100)/100 ;
			// 将第一次录入的账单添加一个备注信息
			List<Bill> bills = apartmentCheckInService.findByPropertiesWithSort(Bill.class, "recordTime", "desc", "branchId", contract.getBranchId(), "checkId", contract.getContrartId());
			Bill reFundBill = (Bill) apartmentCheckInService.findById(Bill.class, bills.get(0).getBillId());
			reFundBill.setRefundStatus("1");
			reFundBill.setRemark(reFundBill.getRemark() == null ? "" + "退部分房租:" + String.valueOf(newApartRent) : reFundBill.getRemark() + "退部分房租:" + String.valueOf(newApartRent));
			reFundBill.setRecordTime(new Date());
			apartmentCheckInService.update(reFundBill);
		}

		apartmentCheckInService.update(contract);

		List<RepairApply> repairApplys = apartmentCheckInService
				.findByProperties(RepairApply.class, "branchId",
						contract.getBranchId(), "contractId",
						contract.getContrartId());
		String roomStatus = "1";
		if (repairApplys.size() > 0) {
			for (RepairApply repairApply : repairApplys) {
				if (repairApply.getStatus().equals("2")
						|| repairApply.getStatus().equals("3")) {
					roomStatus = "W";
				}
			}
		}
		
		// 查询根据门店号、房间号、状态为待入住订单的Aptorder表
		List<Aptorder>  aptorderList = apartmentCheckInService.findByProperties(Aptorder.class, "branchId", contract.getBranchId(), "roomId", contract.getRoomId(), "status", "6");
		// 如果查询结果的长度大于0，则表示该门店的房间下有待入住的订单信息
		// 此时还需要判断段是否为维修房，如果不为维修房，则将状态值修改为"2"待入住状态
		if (aptorderList.size() > 0 && !"W".equals(roomStatus)) {
			roomStatus = "1";
		}
		DataDealPortalService service = new DataDealPortalService();
		IDataDealPortal portal = service.getDataDealPortalPort();
		portal.call(1, 1, "{ function: \"yunSign.cancelContract\", data:{memberId:" + contract.getMemberId() + ",orderId:" + contract.getContrart() + "} }");
		
		roomService.upateroomstatus(contract.getBranchId(),
				contract.getRoomId(), roomStatus);
		apartmentRentService.AptOrderCheckOut(contract.getContrartId());
		
		//退房，原房间删人
		//退房结束将原门锁里身份证都删除，而不删除保洁的身份证信息
		//公寓查合同里的所有的身份证，不管状态
		Integer failFlagCount = 0;
		String checkUserLis = "";
		String checkUserLisFail = "";
		Integer result = CommonConstants.PortalResultCode.FAILED;
		List<?> checkUserList = apartmentCheckInService.findByProperties(CheckUser.class, "checkId", contract.getContrartId(), "checkinType", "2");
		if(checkUserList != null && checkUserList.size() > 0){
			for (int j = 0; j < checkUserList.size(); j++){
				String idcardUser = ((CheckUser)(checkUserList.get(j))).getIdcard();
				checkUserLis += idcardUser +",";
				if(idcardUser != null && !StringUtil.isEmpty(idcardUser)){
					try {
						Equipment equipment = (Equipment) apartmentCheckInService.findOneByProperties(Equipment.class, "branchId", lastcontractbranchid, "roomId", lastcontractroomid);
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
								warningLog.setLogId(sdf.format(new Date()) + apartmentCheckInService.getSequence("SEQ_WARNINGLOG_ID"));
								warningLog.setRecordTime(new Date());
								warningLog.setRecordUser(SystemConstants.User.ADMIN_ID);
								warningLog.setWarningDate(new Date());
								warningLog.setStatus(CommonConstants.servieLogStatus.EFFECTIVE);
								warningLog.setWarningType(CommonConstants.warningLogStatus.DEL_ALL_CARD_FAILED);
								warningLog.setRemark(lastcontractbranchname+"["+lastcontractroomtype+"/"+lastcontractroomid +"]"+"删除锁中所有身份证信息中["+idcardUser+"]失败");
								warningLog.setSerialNo(equipment.getSerialNo());
								apartmentCheckInService.save(warningLog);
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
		operlog.setBranchId(contract.getBranchId());
		operlog.setLogId(DateUtil.currentDate("yyMMdd")
				+ contract.getBranchId()
				+ apartmentCheckInService.getSequence("SEQ_OPERATELOG_ID"));
		String operid = InetAddress.getLocalHost().toString();// IP地址
		operid = (String) operid.subSequence(operid.indexOf("/") + 1,
				operid.length());
		operlog.setOperIp(operid);
		operlog.setOperType("11");
		operlog.setOperModule("退房操作(公寓)");
		operlog.setRecordUser(loginUser.getStaff().getStaffId());
		operlog.setRecordTime(new Date());
		
		if(failFlagCount == 0){
			message = "合同号" + contract.getContrartId() + "已退房(未结账),入住人["+checkUserLis.substring(0, checkUserLis.length()-1)+"]门锁里身份证信息删除成功!";
		} else {
			message = "合同号" + contract.getContrartId() + "已退房(未结账),入住人中["+checkUserLis.substring(0, checkUserLis.length()-1)+"]门锁里有["+checkUserLisFail.substring(0, checkUserLisFail.length()-1)+"]身份证信息删除失败";
		}
		operlog.setContent(message);
		apartmentCheckInService.save(operlog);
		JSONUtil.responseJSON(response, new AjaxResult(0, message));
	}

	/**
	 * 根据合同号查询当前合同的房间是否入住
	 * 
	 * @param request
	 * @param response
	 * @param contractId
	 *            合同表 ID 号
	 */
	@RequestMapping("/queryRoomStatus.do")
	public void queryRoomStatus(HttpServletRequest request,
			HttpServletResponse response, String contractId) {
		Contrart contract = (Contrart) apartmentCheckInService.findById(
				Contrart.class, contractId);
		Room room = (Room) apartmentCheckInService.findOneByProperties(
				Room.class, "roomKey.roomId", contract.getRoomId(),
				"roomKey.branchId", contract.getBranchId());
		JSONUtil.responseJSON(response, room);
	}

	/**
	 * 添加入住人
	 * 
	 * @param request
	 * @param response
	 * @param mobile
	 *            手机号
	 * @param contractId
	 *            合同表 ID 号
	 * @throws UnknownHostException
	 */
	@RequestMapping("/savePerson.do")
	public void savePerson(HttpServletRequest request,
			HttpServletResponse response, String mobile, String contractId)
			throws UnknownHostException {
		LoginUser loginUser = (LoginUser) request.getSession(true)
				.getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Contrart contract = (Contrart) apartmentCheckInService.findById(
				Contrart.class, contractId);

		OperateLog operlog = new OperateLog();
		operlog.setBranchId(contract.getBranchId());
		operlog.setLogId(DateUtil.currentDate("yyMMdd")
				+ contract.getBranchId()
				+ apartmentCheckInService.getSequence("SEQ_OPERATELOG_ID"));
		String operid = InetAddress.getLocalHost().toString();// IP地址
		operid = (String) operid.subSequence(operid.indexOf("/") + 1,
				operid.length());
		operlog.setOperIp(operid);
		operlog.setOperType("1");
		operlog.setOperModule("入住操作");
		operlog.setContent(contract.getRoomId() + "房间添加入住人!");
		operlog.setRecordUser(loginUser.getStaff().getStaffId());
		operlog.setRecordTime(new Date());

		String[] mobiles = mobile.split(",");
		Date date = new Date();
		for (int i = 0; i < mobiles.length; i++) {
			Member member = (Member) apartmentCheckInService
					.findOneByProperties(Member.class, "mobile", mobiles[i]);
			CheckUser checkUser = (CheckUser) apartmentCheckInService
					.findOneByProperties(CheckUser.class, "mobile", mobiles[i],
							"checkId", contractId);
			if (null == checkUser) {
				checkUser = new CheckUser();
				checkUser.setCheckuserId(apartmentCheckInService
						.getSequence("SEQ_NEW_CHECKUSER"));
				checkUser.setCheckuserName(member.getMemberName());
				checkUser.setIdcard(member.getIdcard());
				checkUser.setGender(member.getGendor());
				checkUser.setMobile(member.getMobile());
				checkUser.setStatus("1");
				checkUser.setRecordUser(loginUser.getStaff().getStaffId());
				checkUser.setRecordTime(date);
				checkUser.setCheckId(contractId);
			}
			if (i == 0) {
				checkUser.setCheckuserType("1");
			} else {
				checkUser.setCheckuserType("2");
			}
			apartmentCheckInService.getHibernateTemplate().saveOrUpdate(
					checkUser);
		}

		apartmentCheckInService.save(operlog);
		JSONUtil.responseJSON(response, new AjaxResult(0, "保存成功!"));
	}

	// 更改自动退租字段
	@RequestMapping("/changeAutorefund.do")
	public void changeAutorefund(HttpServletRequest request,
			HttpServletResponse response, String contrartid, String status) {
		Contrart contract = (Contrart) apartmentCheckInService.findById(
				Contrart.class, contrartid);
		contract.setAutoRefund(status);

		try {
			apartmentCheckInService.update(contract);
			JSONUtil.responseJSON(response, new AjaxResult(1, "修改成功!"));
		} catch (Exception e) {
			e.printStackTrace();
			JSONUtil.responseJSON(response, new AjaxResult(2, "修改失败!"));
		}

	}

	// 公寓管家列表
	@RequestMapping("/editHouseManagerList.do")
	public ModelAndView editHouseManagerList(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/ipmspage/leftmenu/apartmentMenager/apartmentManagerEditList");
		return mv;
	}

	@RequestMapping("/editHouseManagerListInfo.do")
	public ModelAndView editHouseManagerListInfo(HttpServletRequest request)
			throws Exception {
		LoginUser loginUser = (LoginUser) request.getSession(true)
				.getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		String memberName = request.getParameter("memberName");
		String mobile = request.getParameter("mobile");

		Pagination pagination = (Pagination) ReflectUtil.setBeanFromRequest(
				request, Pagination.class);
		if (pagination != null && pagination.getPageNum() == null) {
			pagination.setPageNum(1);
			pagination.setRows(14);
		}

		List<?> managerList = apartmentCheckInService.findBySQLWithPagination(
				"querybranchmanager",
				new String[] { branchId, CommonConstants.Postgrade.Manager,
						memberName, mobile }, pagination, true);

		ModelAndView mv = new ModelAndView();
		mv.addObject("managerList", managerList);
		mv.addObject("mobile", mobile);
		mv.addObject("memberName", memberName);
		mv.addObject("pagination", pagination);
		mv.setViewName("page/ipmspage/leftmenu/apartmentMenager/apartmentManagerEditListInfo");

		return mv;
	}

	// 跳至公寓管家信息修改页面
	@RequestMapping("/editHouseManager.do")
	public ModelAndView editHouseManager(HttpServletRequest request,
			HttpServletResponse response, String staffid) throws Exception {
		ModelAndView mv = new ModelAndView();
		Staff manager = (Staff) apartmentCheckInService.findById(Staff.class,
				staffid);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

		String birthday = sdf.format(manager.getBirthday());
		String entrytime = sdf.format(manager.getEntryTime());
		mv.addObject("manager", manager);
		mv.addObject("birthday", birthday);
		mv.addObject("entrytime", entrytime);
		mv.setViewName("page/ipmspage/leftmenu/apartmentMenager/apartmentManagerEdit");
		return mv;
	}

	// 修改管家信息
	@RequestMapping("/submitmanager.do")
	public void submitmanager(HttpServletRequest request,
			HttpServletResponse response, String staffname, String staffid,
			String loginname, String idcard, String mobile, String birthday,
			String entrytime, String email, String address, String gender,
			String remark) throws Exception {
		// int priority = 1;
		String name = "";
		String id = "";
		String phone = "";
		String a = "";
		// String systype = null;
		// SysParam sys =
		// (SysParam)apartmentCheckInService.findOneByProperties(SysParam.class,
		// "paramType", "SYSTEMTYPE");
		// if(sys != null){
		// systype = sys.getContent();
		// }
		// List<Staff> staffList = new ArrayList<Staff>();

		// if(HeartBeatClient.heartbeat){
		if (!StringUtils.isEmpty(staffid)) {

			LoginUser loginUser = (LoginUser) request.getSession(true)
					.getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
			String branchId = loginUser.getStaff().getBranchId();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			Staff manager = (Staff) apartmentCheckInService.findById(
					Staff.class, staffid);
			// List<?> managerList =
			// apartmentCheckInService.findBySQL("querybranchmanager", new
			// String[] {branchId,CommonConstants.Postgrade.Manager} ,true);
			// if(managerList.size()>0 ){
			// name = ((Map<String,String>)managerList.get(0)).get("LOGIN_NAME")
			// == null ? "":
			// ((Map<String,String>)managerList.get(0)).get("LOGIN_NAME").toString();
			// id = ((Map<String,String>)managerList.get(0)).get("IDCARD") ==
			// null ? "":
			// ((Map<String,String>)managerList.get(0)).get("IDCARD").toString();
			// phone = ((Map<String,String>)managerList.get(0)).get("MOBILE") ==
			// null ? "":
			// ((Map<String,String>)managerList.get(0)).get("MOBILE").toString();
			// }
			name = manager.getLoginName();
			id = manager.getIdcard();
			phone = manager.getMobile();

			DataDealPortalService service = new DataDealPortalService();
			IDataDealPortal portal = service.getDataDealPortalPort();
			String backinfo = portal.call(
					SystemConstants.EnginType.COMMON_DATA,
					SystemConstants.Movement.CUSTOM_QUERY,
					"{ function: \"apartmentManager.checkstaff\", data:{staffid:"
							+ staffid + ",loginname:" + loginname + ",idcard:"
							+ idcard + ",mobile:" + mobile + ",branchId:"
							+ branchId + ",name:" + name + ",id:" + id
							+ ",phone:" + phone + "} }");

			if ("hasLoginname".equals(backinfo)) {
				JSONUtil.responseJSON(response, new AjaxResult(0, "登录名已存在!"));
			} else if ("hasIdcard".equals(backinfo)) {
				JSONUtil.responseJSON(response, new AjaxResult(0, "身份证号已存在!"));
			} else if ("hasMobile".equals(backinfo)) {
				JSONUtil.responseJSON(response, new AjaxResult(0, "手机号已存在!"));
			} else if ("ok".equals(backinfo)) {

				manager.setStaffName(staffname);
				manager.setLoginName(loginname);
				manager.setIdcard(idcard);
				manager.setMobile(mobile);
				manager.setBirthday(sdf.parse(birthday));
				manager.setEntryTime(sdf.parse(entrytime));
				manager.setEmail(email);
				manager.setGendor(gender);
				manager.setAddress(address);
				manager.setRemark(remark);
				manager.setRecordTime(new Date());
				// staffList.add(manager);
				try {
					apartmentCheckInService.update(manager);
					JSONUtil.responseJSON(response, new AjaxResult(1, "修改成功!"));
				} catch (Exception e) {
					e.printStackTrace();
					JSONUtil.responseJSON(response, new AjaxResult(2, "修改失败!"));
				}
				// 数据同步
				// if(CommonConstants.SystemType.Branch.equals(systype) ){
				// SysParam param = (SysParam) apartmentCheckInService
				// .findOneByProperties(SysParam.class, "paramType",
				// SystemConstants.ParamType.BRANCH_IP, "paramName", "300001");
				//
				// Map<String, Object> staffmap = new HashMap<String, Object>();
				// staffmap.put("Staff", staffList);
				// TransferServcie.getInstance().transferData(priority,
				// param.getContent(),
				// JSONUtil.fromObject(staffmap).toString());
				// }

			}
		}
		/*
		 * }else{ a = "本地网络未连接！"; JSONUtil.responseJSON(response, new
		 * AjaxResult(0, a)); }
		 */
	}

	@RequestMapping("/HouseRenew.do")
	public ModelAndView houseRenew(HttpServletRequest request,
			HttpServletResponse response, String contractId)
			throws IOException, ParseException {
		ModelAndView mv = new ModelAndView();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Contrart contrart = (Contrart) apartmentCheckInService.findById(Contrart.class, contractId);
		String TypeOfPaymentName = "";
		switch (Integer.parseInt(contrart.getTypeOfPayment())) {
		case 1:
			TypeOfPaymentName = "月付";
			break;
		case 3:
			TypeOfPaymentName = "季付";
			break;
		case 6:
			TypeOfPaymentName = "半年付";
			break;
		case 12:
			TypeOfPaymentName = "年付";
			break;
		}
		Date nextTime = contrart.getContrartEndTime(); // 房租到期时间
		Date date = sdf.parse(sdf.format(new Date())); // 当天
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(contrart.getContrartEndTime()); // 添加 房租到期时间
		Double money = 0.0;
		
		Double roomPrice = PriceUtil.doubleToPriceDouble(Double.parseDouble(contrart.getUnitPrice()) / Double.parseDouble("30"));

		if (contrart.getStatus().equals("4")) {
			String startTime = sdf.format(new Date());
			String endTime = sdf.format(contrart.getContrartEndTime());
			Calendar sTime = Calendar.getInstance();
			Calendar eTime = Calendar.getInstance();
			sTime.setTime(sdf.parse(startTime));
			eTime.setTime(sdf.parse(endTime));
			int result = sTime.get(Calendar.MONTH) - eTime.get(Calendar.MONTH);
			int month = (sTime.get(Calendar.YEAR) - eTime.get(Calendar.YEAR)) * 12;
			int num = Math.abs(month + result);
			for (int i = 0; i < num; i++) {
				calendar.add(Calendar.MONTH, -1);
				if (nextTime.getTime() >= date.getTime() && date.getTime() >= calendar.getTime().getTime()) {
					int days = (int) ((date.getTime() - calendar.getTime().getTime()) / (1000 * 3600 * 24));
					if (days == 0) {
						money = money + Double.parseDouble(contrart.getUnitPrice());
					} else {
						money = (money + (Double.parseDouble(contrart.getUnitPrice()) - (roomPrice * Double.parseDouble(Integer.toString(days)))));
					}
				} else {
					money = money + Double.parseDouble(contrart.getUnitPrice());
				}
				nextTime = calendar.getTime();
			}
		} else if (contrart.getStatus().equals("1")) {
			String startTime = sdf.format(contrart.getStartTime());
			String endTime = sdf.format(contrart.getContrartEndTime());
			Calendar sTime = Calendar.getInstance();
			Calendar eTime = Calendar.getInstance();
			sTime.setTime(sdf.parse(startTime));
			eTime.setTime(sdf.parse(endTime));
			int result = sTime.get(Calendar.MONTH) - eTime.get(Calendar.MONTH);
			int month = (sTime.get(Calendar.YEAR) - eTime.get(Calendar.YEAR)) * 12;
			int num = Math.abs(month + result);
			money = Double.parseDouble(contrart.getUnitPrice()) * num;
		}
		
		mv.addObject("TypeOfPaymentName", TypeOfPaymentName);
		mv.addObject("type",contrart.getTypeOfPayment());
		mv.addObject("contrartEndTime", sdf.format(contrart.getContrartEndTime()));
		mv.addObject("contrartStartTime", sdf.format(contrart.getStartTime()));
		mv.addObject("aptRenewTime", contrart.getAptRenewTime() == null ? "" : sdf.format(contrart.getAptRenewTime()));
		mv.addObject("contrart", contrart);
		mv.addObject("money", PriceUtil.doubleToPrice(money));
		mv.setViewName("page/apartment/apartmentmainmenu/apartmentcontract/houserenew");
		return mv;
	}

	@RequestMapping("/queryAptRoom.do")
	public ModelAndView queryAptRoom(HttpServletRequest request,
			HttpServletResponse response, String memberId, String roomPrice,
			String contrartEndTime, String endPrice, String status,
			String contractId, String aptRenewTime,String contrart_endTime) throws ParseException {
		ModelAndView mv = new ModelAndView();
		Contrart contract = (Contrart) apartmentCheckInService.findById(
				Contrart.class, contractId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");
		String end_timestr = contrart_endTime.substring(0,11);
		String end_time = end_timestr.replaceAll("-", "/");
		String startTime = sdf.format(contract.getStartTime());
		String endTime = sdf.format(contract.getContrartEndTime());
		Calendar sTime = Calendar.getInstance();
		Calendar eTime = Calendar.getInstance();
		sTime.setTime(sdf.parse(startTime));
		eTime.setTime(sdf.parse(endTime));
		int result = sTime.get(Calendar.MONTH) - eTime.get(Calendar.MONTH);
		int month = (sTime.get(Calendar.YEAR) - eTime.get(Calendar.YEAR)) * 12;
		int num = Math.abs(month + result);
		mv.addObject("memberId", memberId);
		mv.addObject("roomPrice", roomPrice);
		mv.addObject("endPrice", endPrice);
		mv.addObject("status", status);
		mv.addObject("contrartEndTime", contrartEndTime);//这是已交房租到期时间
		mv.addObject("contrart_endTime", end_time);//这是合同结束时间
		mv.addObject("aptRenewTime", aptRenewTime);
		mv.addObject("num", num);
		mv.setViewName("page/apartment/apartmentmainmenu/apartmentcontract/aptroomDialog");
		return mv;
	}
	
		@SuppressWarnings("unchecked")
		@RequestMapping("/checkRoomEffectiveOrNot.do")
	public void checkRoomEffectiveOrNot(HttpServletRequest request,
			HttpServletResponse response, String roomid,String memberId,String contrartEndTime ) {
		LoginUser loginUser = (LoginUser) request.getSession(true)
				.getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		 String branchId = loginUser.getStaff().getBranchId();
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		 String memberRank = "";
		 String roomtype = "";
		 double discount = 1.0;
			Member member = (Member) apartmentCheckInService.findById(Member.class,
					memberId);
			if(member != null){
				memberRank = member.getMemberRank();
				
				 MemberRank mr = (MemberRank)apartmentCheckInService.findOneByProperties(MemberRank.class, "memberRank", memberRank, "status","1");
				 if(mr!=null){
				 	discount = (double)mr.getDiscount()/100;
				 }
			}

			Room room = (Room)apartmentCheckInService.findOneByProperties(Room.class, "roomKey.branchId", branchId,
					"roomKey.roomId",roomid,"status","1");
		if(room !=null){
			 roomtype = room.getRoomType();
		}
			Date today = new Date();
			String today_s = sdf.format(today);
		  //卡已有无人预约看房
	        List<?> reservedJudge = apartmentCheckInService.findBySQL("reservedJudge",new String[]{branchId,roomid,
	        		today_s,contrartEndTime}, true);
	      //卡停售房
	  		List<?> haltplanList = apartmentCheckInService.findBySQL("checkhaltplan", new String[]{roomid,branchId,today_s,contrartEndTime,today_s,contrartEndTime},true);
	  	  //卡已存在的合同或者app订单
	  		List<?> aptorderJudge = apartmentCheckInService.findBySQL("aptorderJudge",new String[]{branchId,roomid,
	  				today_s,contrartEndTime,today_s,contrartEndTime}, true);
	  		
	  		List<Contrart> contrartList = apartmentCheckInService.findBySQL("checkroombeforeadd",new String[]{branchId,roomid,
	  				today_s,contrartEndTime,today_s,contrartEndTime}, true);
	  		
/*
		  	List<?> aptorderJudge = apartmentCheckInService.findBySQL("chrcheckaptorder",new String[]{branchId,roomid,
	  				today_s}, true);
			List<?> contrartList = apartmentCheckInService.findBySQL("findcontforaddclean", new String[] { branchId,
					"",roomid,today_s }, true);
	        List<?> reservedJudge = apartmentCheckInService.findBySQL("ckreservedJudge",new String[]{branchId,roomid,
	        		today_s}, true);
	  		List<?> haltplanList = apartmentCheckInService.findBySQL("ckhaltplan", new String[]{roomid,branchId,today_s},true);
*/
		if(aptorderJudge.size() > 0){
			JSONUtil.responseJSON(response, new AjaxResult(1, "该房间已被预定!"));
		}else if(contrartList.size() > 0){
			JSONUtil.responseJSON(response, new AjaxResult(2, "该房间已录入合同!"));
		}else if(reservedJudge.size() > 0){
			JSONUtil.responseJSON(response, new AjaxResult(3, "该房间已存在预约!"));
		}else if(haltplanList.size() > 0){
			JSONUtil.responseJSON(response, new AjaxResult(4, "该房间为停售房!"));
		}else{
			
			 
		
			SinglePrice sp = new SinglePrice(branchId,"2",roomtype,"MSJ","3",today);
			Double price = sp.checkRoomPrice()*discount;
			/*NumberFormat nf = NumberFormat.getNumberInstance();
	        nf.setMaximumFractionDigits(2); 
	        nf.setRoundingMode(RoundingMode.CEILING);*/
	        String price_str =  String.format("%.2f",price);
			JSONUtil.responseJSON(response, new AjaxResult(5, price_str));

		}

	  		 
	}
		
	@RequestMapping("/queryAptRoomPrice.do")
	public void queryAptRoomPrice(HttpServletRequest request,
			HttpServletResponse response, String roomId, String memberId) {
		LoginUser loginUser = (LoginUser) request.getSession(true)
				.getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		// Contrart contract = (Contrart)
		// apartmentCheckInService.findById(Contrart.class, contractId);
		Member member = (Member) apartmentCheckInService.findById(Member.class,
				memberId);
		Pagination pagination;
		Integer pageNum = Integer.parseInt(request.getParameter("page"));
		Integer rows = Integer.parseInt(request.getParameter("rows"));
		pagination = new Pagination(pageNum, rows);
		List<?> result = apartmentCheckInService.findBySQLWithPagination(
				"aptFindunitprice",
				new String[] { loginUser.getStaff().getBranchId(),
						member.getMemberRank(), roomId,
						loginUser.getStaff().getBranchId(),
						loginUser.getStaff().getBranchId(),
						member.getMemberRank(), roomId,
						loginUser.getStaff().getBranchId() }, pagination, true);
		JSONUtil.responseJSON(response,
				JSONUtil.buildReportJSONByPagination(result, pagination));
	}

	@RequestMapping("/submintBill.do")
	public void submintBill(HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
		
		FileOutputStream fos = null;
		InputStream is = null;
		String fileName_timestamp = "";
		String lastcontractbranchname = "";
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile srcFile = multipartRequest.getFile("file");
		
		String contractId = multipartRequest.getParameter("contractId");
		String repayMoney = multipartRequest.getParameter("repayMoney");
		//押金差额
		String repayMoneyPartOne = multipartRequest.getParameter("repayMoneyPartOne");
		//房费差额
		String repayMoneyPartTwo = multipartRequest.getParameter("repayMoneyPartTwo");
		String newRoomId = multipartRequest.getParameter("newRoomId");
		String newRoomPrice = multipartRequest.getParameter("newRoomPrice");
//		String endPrice = multipartRequest.getParameter("endPrice");
//		String contrastPrice = multipartRequest.getParameter("contrastPrice");
		String daymoney = multipartRequest.getParameter("daymoney");
		String changeHouse = multipartRequest.getParameter("changeHouse");//换房费
		String originalFilename=srcFile.getOriginalFilename();
		fileName_timestamp = new Date().getTime() + originalFilename.substring(originalFilename.lastIndexOf("."));
	    	try{
				String webPath = RequestUtil.getWebPath(request);
				File srcFolder = new File(webPath + File.separator+"upload");
				if (!srcFolder.exists()) {
					srcFolder.mkdirs();
				}
				File tarFile = new File(srcFolder.getAbsolutePath() + File.separator+ fileName_timestamp);
				if(!tarFile.exists()){
					tarFile.createNewFile();
				}
				fos = new FileOutputStream(tarFile);
				is = srcFile.getInputStream();
				if(is.available() <= 0){
					JSONUtil.responseJSON(response, new AjaxResult(1,"不可上传空的合同附件!"));
					return;
				}
				int ln = 0;
				byte[] buf = new byte[1024];
				while ((ln = is.read(buf)) != -1) {
					fos.write(buf, 0, ln);
				}
				fos.flush();
			}catch(IOException e){
				e.printStackTrace();
				 JSONUtil.responseJSON(response, new AjaxResult(2, "文件传输失败!"));
			}finally{
				if (fos != null) {
					fos.close();
				}
				if (is != null) {
					is.close();
				}
			}
	    	
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Contrart contract = (Contrart) apartmentCheckInService.findById(Contrart.class, contractId);
		contract.setContrart(fileName_timestamp);
		String lastcontractbranchid = contract.getBranchId();
		Branch br = (Branch)apartmentCheckInService.findOneByProperties(Branch.class, "branchId", lastcontractbranchid);
		if(br != null){
			lastcontractbranchname = br.getBranchName();
		}
		String lastcontractroomid = contract.getRoomId();
		String lastcontractroomtype = contract.getRoomType();
		Room newRoom = (Room) apartmentCheckInService.findOneByProperties(Room.class, "roomKey.branchId", contract.getBranchId(), "roomKey.roomId", newRoomId);
	
		SimpleDateFormat sdff = new SimpleDateFormat("yyMMdd");
		String sequences = apartmentCheckInService.getSequence("SEQ_NEW_SWITCHROOM");
		String strdate = sdff.format(new Date());
		SwitchRoom switchRoom = new SwitchRoom();
		switchRoom.setLogId(strdate + contract.getBranchId() + sequences);
		switchRoom.setBranchId(contract.getBranchId());
		switchRoom.setCheckId(contractId);
		switchRoom.setLastRoomid(contract.getRoomId());
		switchRoom.setLastRoomtype(contract.getRoomType());
		switchRoom.setCurrRoomid(newRoomId);
		switchRoom.setCurrRoomtype(newRoom.getRoomType());
		switchRoom.setRecordTime(new Date());
		switchRoom.setRecordUser(loginUser.getStaff().getStaffId());
		switchRoom.setLastrRoomprice(Double.parseDouble(contract.getUnitPrice()));
		switchRoom.setCurrRoomprice(Double.parseDouble(newRoomPrice));

		if (contract.getStatus().equals("1")) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");
			String startTime = sdf.format(contract.getStartTime());
			String endTime = sdf.format(contract.getContrartEndTime());
			Calendar sTime = Calendar.getInstance();
			Calendar eTime = Calendar.getInstance();
			sTime.setTime(sdf.parse(startTime));
			eTime.setTime(sdf.parse(endTime));
			if(!"0".equals(repayMoney)){
			/*	Bill bill = new Bill();
				String billId = DateUtil.currentDate("yyMMdd") + contract.getBranchId() + "3" + apartmentCheckInService.getSequence("SEQ_NEW_BILL");
				bill.setBillId(billId);
				bill.setBranchId(contract.getBranchId());
				bill.setCheckId(contract.getContrartId());
				bill.setProjectId("1234");
				bill.setProjectName("房费");
				bill.setDescribe("房费");
				bill.setCost(0.0);
				bill.setPayment("1");
				bill.setPay(Double.parseDouble(repayMoney));
				bill.setStatus("1");
				bill.setRecordTime(new Date(0));
				bill.setRecordUser(loginUser.getStaff().getStaffId());
				apartmentCheckInService.save(bill);*/
				if(!"0".equals(repayMoneyPartOne)){
					Bill repayMoneyPartOneBill = new Bill();
					String billId = DateUtil.currentDate("yyMMdd") + contract.getBranchId() + "3" + apartmentCheckInService.getSequence("SEQ_NEW_BILL");
					repayMoneyPartOneBill.setBillId(billId);
					repayMoneyPartOneBill.setBranchId(contract.getBranchId());
					repayMoneyPartOneBill.setCheckId(contract.getContrartId());
					repayMoneyPartOneBill.setProjectId("2008");
					repayMoneyPartOneBill.setProjectName("押金");
					repayMoneyPartOneBill.setDescribe("押金");
					repayMoneyPartOneBill.setCost(0.0);
					repayMoneyPartOneBill.setPayment("1");
					repayMoneyPartOneBill.setPay(Double.parseDouble(repayMoneyPartOne));
					repayMoneyPartOneBill.setStatus("1");
					repayMoneyPartOneBill.setRecordTime(new Date(0));
					repayMoneyPartOneBill.setRecordUser(loginUser.getStaff().getStaffId());
					apartmentCheckInService.save(repayMoneyPartOneBill);
				}
				if(!"0".equals(repayMoneyPartTwo)){
					Bill repayMoneyPartTwoBill = new Bill();
					String billId = DateUtil.currentDate("yyMMdd") + contract.getBranchId() + "3" + apartmentCheckInService.getSequence("SEQ_NEW_BILL");
					repayMoneyPartTwoBill.setBillId(billId);
					repayMoneyPartTwoBill.setBranchId(contract.getBranchId());
					repayMoneyPartTwoBill.setCheckId(contract.getContrartId());
					repayMoneyPartTwoBill.setProjectId("2019");
					repayMoneyPartTwoBill.setProjectName("换房差额");
					repayMoneyPartTwoBill.setDescribe("换房差额");
					repayMoneyPartTwoBill.setCost(0.0);
					repayMoneyPartTwoBill.setPayment("1");
					repayMoneyPartTwoBill.setPay(Double.parseDouble(repayMoneyPartTwo));
					repayMoneyPartTwoBill.setStatus("1");
					repayMoneyPartTwoBill.setRecordTime(new Date(0));
					repayMoneyPartTwoBill.setRecordUser(loginUser.getStaff().getStaffId());
					apartmentCheckInService.save(repayMoneyPartTwoBill);
				}
				
				
			}
			
			contract.setAptRenewTime(contract.getStartTime());
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");
			String startTime = contract.getAptRenewTime() == null ? sdf.format(contract.getStartTime()) : sdf.format(contract.getAptRenewTime());
			String endTime = sdf.format(new Date());
			Calendar sTime = Calendar.getInstance();
			Calendar eTime = Calendar.getInstance();
			sTime.setTime(sdf.parse(startTime));
			eTime.setTime(sdf.parse(endTime));
			int result = sTime.get(Calendar.MONTH) - eTime.get(Calendar.MONTH);
			int month = (sTime.get(Calendar.YEAR) - eTime.get(Calendar.YEAR)) * 12;
			int num = Math.abs(month + result);
			for (int j = 0; j < num; j++) {
				Bill bill = new Bill();
				String billId = DateUtil.currentDate("yyMMdd") + contract.getBranchId() + "3" + apartmentCheckInService.getSequence("SEQ_NEW_BILL");
				bill.setBillId(billId);
				bill.setBranchId(contract.getBranchId());
				bill.setCheckId(contract.getContrartId());
				bill.setProjectId("1234");
				bill.setProjectName("房费");
				bill.setDescribe("房费");
				bill.setCost(Double.parseDouble(contract.getUnitPrice()));
				bill.setPayment("1");
				bill.setPay(0.0);
				bill.setStatus("1");
				bill.setRecordTime(new Date(0));
				bill.setRecordUser(loginUser.getStaff().getStaffId());
				apartmentCheckInService.save(bill);
			}

			if (repayMoney.indexOf("-") == -1) {
				Date date = new Date();
				Bill bill = new Bill();
				String billId = DateUtil.currentDate("yyMMdd") + contract.getBranchId() + "3" + apartmentCheckInService.getSequence("SEQ_NEW_BILL");
				bill.setBillId(billId);
				bill.setBranchId(contract.getBranchId());
				bill.setCheckId(contract.getContrartId());
				bill.setProjectId("1234");
				bill.setProjectName("房费");
				bill.setDescribe("房费");
				bill.setCost(Double.parseDouble(daymoney));
				bill.setPayment("0");
				bill.setPay(0.0);
				bill.setStatus("1");
				bill.setRecordTime(date);
				bill.setRecordUser(loginUser.getStaff().getStaffId());
				apartmentCheckInService.save(bill);
				
				if(!repayMoney.equals("0")){
					/*Bill billT = new Bill();
					String billIdT = DateUtil.currentDate("yyMMdd") + contract.getBranchId() + "3" + apartmentCheckInService.getSequence("SEQ_NEW_BILL");
					billT.setBillId(billIdT);
					billT.setBranchId(contract.getBranchId());
					billT.setCheckId(contract.getContrartId());
					billT.setProjectId("2004");
					billT.setProjectName("预存");
					billT.setDescribe("房租");
					billT.setCost(0.0);
					billT.setPayment("0");
					billT.setPay(Double.parseDouble(repayMoney));
					billT.setStatus("1");
					billT.setRecordTime(date);
					billT.setRecordUser(loginUser.getStaff().getStaffId());
					apartmentCheckInService.save(billT);*/
					if(!"0".equals(repayMoneyPartOne)){
						Bill repayMoneyPartOneBill = new Bill();
						String billIdT = DateUtil.currentDate("yyMMdd") + contract.getBranchId() + "3" + apartmentCheckInService.getSequence("SEQ_NEW_BILL");
						repayMoneyPartOneBill.setBillId(billIdT);
						repayMoneyPartOneBill.setBranchId(contract.getBranchId());
						repayMoneyPartOneBill.setCheckId(contract.getContrartId());
						repayMoneyPartOneBill.setProjectId("2008");
						repayMoneyPartOneBill.setProjectName("押金");
						repayMoneyPartOneBill.setDescribe("押金");
						repayMoneyPartOneBill.setCost(0.0);
						repayMoneyPartOneBill.setPayment("0");
						repayMoneyPartOneBill.setPay(Double.parseDouble(repayMoneyPartOne));
						repayMoneyPartOneBill.setStatus("1");
						repayMoneyPartOneBill.setRecordTime(date);
						repayMoneyPartOneBill.setRecordUser(loginUser.getStaff().getStaffId());
						apartmentCheckInService.save(repayMoneyPartOneBill);
					}
					if(!"0".equals(repayMoneyPartTwo)){
						Bill repayMoneyPartTwoBill = new Bill();
						String billIdT = DateUtil.currentDate("yyMMdd") + contract.getBranchId() + "3" + apartmentCheckInService.getSequence("SEQ_NEW_BILL");
						repayMoneyPartTwoBill.setBillId(billIdT);
						repayMoneyPartTwoBill.setBranchId(contract.getBranchId());
						repayMoneyPartTwoBill.setCheckId(contract.getContrartId());
						repayMoneyPartTwoBill.setProjectId("2019");
						repayMoneyPartTwoBill.setProjectName("换房差额");
						repayMoneyPartTwoBill.setDescribe("换房差额");
						repayMoneyPartTwoBill.setCost(0.0);
						repayMoneyPartTwoBill.setPayment("0");
						repayMoneyPartTwoBill.setPay(Double.parseDouble(repayMoneyPartTwo));
						repayMoneyPartTwoBill.setStatus("1");
						repayMoneyPartTwoBill.setRecordTime(date);
						repayMoneyPartTwoBill.setRecordUser(loginUser.getStaff().getStaffId());
						apartmentCheckInService.save(repayMoneyPartTwoBill);
					}
				}
			} else {
				Date date = new Date();
				/*Bill bill = new Bill();
				String billId = DateUtil.currentDate("yyMMdd") + contract.getBranchId() + "3" + apartmentCheckInService.getSequence("SEQ_NEW_BILL");
				bill.setBillId(billId);
				bill.setBranchId(contract.getBranchId());
				bill.setCheckId(contract.getContrartId());
				bill.setProjectId("2001");
				bill.setProjectName("现金支出");
				bill.setDescribe("结算");
				bill.setCost(0.0);
				bill.setPayment("0");
				bill.setPay(Double.parseDouble(repayMoney));
				bill.setStatus("1");
				bill.setRecordTime(date);
				bill.setRecordUser(loginUser.getStaff().getStaffId());
				apartmentCheckInService.save(bill);*/
				if(!"0".equals(repayMoneyPartOne)){
					Bill repayMoneyPartOnebill = new Bill();
					String billId = DateUtil.currentDate("yyMMdd") + contract.getBranchId() + "3" + apartmentCheckInService.getSequence("SEQ_NEW_BILL");
					repayMoneyPartOnebill.setBillId(billId);
					repayMoneyPartOnebill.setBranchId(contract.getBranchId());
					repayMoneyPartOnebill.setCheckId(contract.getContrartId());
					repayMoneyPartOnebill.setProjectId("2008");
					repayMoneyPartOnebill.setProjectName("押金");
					repayMoneyPartOnebill.setDescribe("押金");
					repayMoneyPartOnebill.setCost(0.0);
					repayMoneyPartOnebill.setPayment("0");
					repayMoneyPartOnebill.setPay(Double.parseDouble(repayMoneyPartOne));
					repayMoneyPartOnebill.setStatus("1");
					repayMoneyPartOnebill.setRecordTime(date);
					repayMoneyPartOnebill.setRecordUser(loginUser.getStaff().getStaffId());
					apartmentCheckInService.save(repayMoneyPartOnebill);
				}
				if(!"0".equals(repayMoneyPartTwo)){
					Bill repayMoneyPartTwobill = new Bill();
					String billId = DateUtil.currentDate("yyMMdd") + contract.getBranchId() + "3" + apartmentCheckInService.getSequence("SEQ_NEW_BILL");
					repayMoneyPartTwobill.setBillId(billId);
					repayMoneyPartTwobill.setBranchId(contract.getBranchId());
					repayMoneyPartTwobill.setCheckId(contract.getContrartId());
					repayMoneyPartTwobill.setProjectId("2001");
					repayMoneyPartTwobill.setProjectName("现金支出");
					repayMoneyPartTwobill.setDescribe("结算");
					repayMoneyPartTwobill.setCost(0.0);
					repayMoneyPartTwobill.setPayment("0");
					repayMoneyPartTwobill.setPay(Double.parseDouble(repayMoneyPartTwo));
					repayMoneyPartTwobill.setStatus("1");
					repayMoneyPartTwobill.setRecordTime(date);
					repayMoneyPartTwobill.setRecordUser(loginUser.getStaff().getStaffId());
					apartmentCheckInService.save(repayMoneyPartTwobill);
				}
				
				if(!daymoney.equals("0")){
					Bill billT = new Bill();
					String billIdT = DateUtil.currentDate("yyMMdd") + contract.getBranchId() + "3" + apartmentCheckInService.getSequence("SEQ_NEW_BILL");
					billT.setBillId(billIdT);
					billT.setBranchId(contract.getBranchId());
					billT.setCheckId(contract.getContrartId());
					billT.setProjectId("1234");
					billT.setProjectName("房费");
					billT.setDescribe("房费");
					billT.setCost(Double.parseDouble(daymoney));
					billT.setPayment("0");
					billT.setPay(0.0);
					billT.setStatus("1");
					billT.setRecordTime(date);
					billT.setRecordUser(loginUser.getStaff().getStaffId());
					apartmentCheckInService.save(billT);
				}
			}
			contract.setAptRenewTime(new Date());
		}

		if (contract.getStatus().equals("4")) {
			roomService.upateroomstatus(contract.getBranchId(),
					contract.getRoomId(), "1");
			roomService.upateroomstatus(contract.getBranchId(), newRoomId, "3");
		}
		
		if(!StringUtil.isEmpty(changeHouse)){
			if(Double.parseDouble(changeHouse) != 0){
				Date date = new Date();
				Bill bill = new Bill();
				String billId = DateUtil.currentDate("yyMMdd") + contract.getBranchId() + "3" + apartmentCheckInService.getSequence("SEQ_NEW_BILL");
				bill.setBillId(billId);
				bill.setBranchId(contract.getBranchId());
				bill.setCheckId(contract.getContrartId());
				bill.setProjectId("2987");
				bill.setProjectName("现金收入");
				bill.setDescribe("现金收入");
				bill.setCost(0.0);
				bill.setPayment("0");
				bill.setPay(Double.parseDouble(changeHouse));
				bill.setStatus("1");
				bill.setRecordTime(date);
				bill.setRecordUser(loginUser.getStaff().getStaffId());
				apartmentCheckInService.save(bill);
			}
			
		}
		
		if(!StringUtil.isEmpty(changeHouse)){
			if(Double.parseDouble(changeHouse) != 0){
				Date date = new Date();
				Bill bill = new Bill();
				String billId = DateUtil.currentDate("yyMMdd") + contract.getBranchId() + "3" + apartmentCheckInService.getSequence("SEQ_NEW_BILL");
				bill.setBillId(billId);
				bill.setBranchId(contract.getBranchId());
				bill.setCheckId(contract.getContrartId());
				bill.setProjectId("1001");
				bill.setProjectName("赔偿");
				bill.setDescribe("赔偿");
				bill.setCost(Double.parseDouble(changeHouse));
				bill.setPayment("0");
				bill.setPay(0.0);
				bill.setStatus("1");
				bill.setRecordTime(date);
				bill.setRecordUser(loginUser.getStaff().getStaffId());
				apartmentCheckInService.save(bill);
			}
			
		}

		contract.setRoomId(newRoomId);
		contract.setRoomType(newRoom.getRoomType());
		contract.setUnitPrice(newRoomPrice);
		contract.setCash(Double.parseDouble(newRoomPrice));
		contract.setPaymoney(Double.parseDouble(newRoomPrice) * Double.parseDouble(contract.getTypeOfPayment()));
		contract.setRecordTime(new Date());
		List<AptorderDetail> aptorderDetailList = apartmentCheckInService.findByProperties(AptorderDetail.class, "contrartId", contract.getContrartId(), "status","1"); 
		if(aptorderDetailList.size() > 0){
			AptorderDetail aptorderDetail = aptorderDetailList.get(0);
			Aptorder aptorder = (Aptorder) apartmentCheckInService.findOneByProperties(Aptorder.class, "orderId", aptorderDetail.getAptorderId());
			aptorder.setRoomId(contract.getRoomId());
			aptorder.setRoomType(contract.getRoomType());
			apartmentCheckInService.update(aptorder);
		}
		try {
			apartmentCheckInService.update(contract);
			apartmentCheckInService.save(switchRoom);
			DataDealPortalService service = new DataDealPortalService();
			IDataDealPortal portal = service.getDataDealPortalPort();
			portal.call(1, 1, "{ function: \"yunSign.cancelContract\", data:{memberId:" + contract.getMemberId() + ",orderId:" + contract.getContrart() + "} }");
			
			//换房删人
			//换房结束将原门锁里身份证都删除，而不删除保洁的身份证信息
			//公寓查合同里的所有的身份证，不管状态
			Integer failFlagCount = 0;
			String checkUserLis = "";
			String checkUserLisFail = "";
			Integer result = CommonConstants.PortalResultCode.FAILED;
			List<?> checkUserList = apartmentCheckInService.findByProperties(CheckUser.class, "checkId", contract.getContrartId(), "checkinType", "2");
			if(checkUserList != null && checkUserList.size() > 0){
				for (int j = 0; j < checkUserList.size(); j++){
					String idcardUser = ((CheckUser)(checkUserList.get(j))).getIdcard();
					checkUserLis += idcardUser +",";
					if(idcardUser != null && !StringUtil.isEmpty(idcardUser)){
						try {
							Equipment equipment = (Equipment) apartmentCheckInService.findOneByProperties(Equipment.class, "branchId", lastcontractbranchid, "roomId", lastcontractroomid);
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
									warningLog.setLogId(sdf.format(new Date()) + apartmentCheckInService.getSequence("SEQ_WARNINGLOG_ID"));
									warningLog.setRecordTime(new Date());
									warningLog.setRecordUser(SystemConstants.User.ADMIN_ID);
									warningLog.setWarningDate(new Date());
									warningLog.setStatus(CommonConstants.servieLogStatus.EFFECTIVE);
									warningLog.setWarningType(CommonConstants.warningLogStatus.DEL_ALL_CARD_FAILED);
									warningLog.setRemark(lastcontractbranchname+"["+lastcontractroomtype+"/"+lastcontractroomid +"]"+"删除锁中所有身份证信息中["+idcardUser+"]失败");
									warningLog.setSerialNo(equipment.getSerialNo());
									apartmentCheckInService.save(warningLog);
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
			operlog.setBranchId(lastcontractbranchid);
			operlog.setLogId(DateUtil.currentDate("yyMMdd") + contract.getBranchId() + apartmentCheckInService.getSequence("SEQ_OPERATELOG_ID"));
			String operid = InetAddress.getLocalHost().toString();// IP地址
			operid = (String) operid.subSequence(operid.indexOf("/") + 1, operid.length());
			operlog.setOperIp(operid);
			operlog.setOperType("17");
			operlog.setOperModule("换房删人(公寓)");
			operlog.setRecordUser(loginUser.getStaff().getStaffId());
			operlog.setRecordTime(new Date());
			
			if(failFlagCount == 0){
				operlog.setContent("合同号" + contract.getContrartId() + "已换房(未结账),入住人["+checkUserLis.substring(0, checkUserLis.length()-1)+"]门锁里身份证信息删除成功");
				apartmentCheckInService.save(operlog);
			} else {
				operlog.setContent("合同号" + contract.getContrartId() + "已换房(未结账),入住人中["+checkUserLis.substring(0, checkUserLis.length()-1)+"]门锁里有["+checkUserLisFail.substring(0, checkUserLisFail.length()-1)+"]身份证信息删除失败");
				apartmentCheckInService.save(operlog);
			}
			
			JSONUtil.responseJSON(response, new AjaxResult(1, "换房成功!"));
		} catch (Exception e) {
			e.printStackTrace();
			JSONUtil.responseJSON(response, new AjaxResult(2, "换房失败!"));
		}
	}
	/**
	 * 换房或结账时用于水电费清结算查询
	 * 
	 * @param request 入参
	 * @param response 出参
	 * @param contractId 合同编号
	 * @return 返回异常+未付合计金额、工作账、合同号、所有未结水电合计费用，所有水费、所有电费、JSON数据集
	 * @throws Exception 异常
	 */
	@RequestMapping("/queryCost.do")
	public ModelAndView queryCost(HttpServletRequest request, HttpServletResponse response, String contractId, String status)
			throws Exception {
		ModelAndView mv = new ModelAndView();
		// 获取branchId 和 loginstaff 查找工作账
		LoginUser loginUser = (LoginUser)request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		String loginstaff = loginUser.getStaff().getStaffId().toString();
		List<?> workbill = roomService.getWorkbill(branchId,loginstaff);
		// 通过获取合同编号从何获取订单编号
		Contrart contract = (Contrart) apartmentCheckInService.findById(Contrart.class, contractId);
		// 调用接口
		String contractList = queryAptCost(contract);
		// 将调用的接口数据转换成javaBean
		WaterAndElectricity we = (WaterAndElectricity) ReflectUtil.setBean(new JSONObject(contractList), "WaterAndElectricity");
		// 将调用的接口数据转换成JSONObject
		JSONObject jo = new JSONObject(contractList);
		// 从JSONObject中获取数据
		double amount = jo.getDouble("amount");
		double a = jo.getDouble("abnormalCost");
		double sumCost = amount + a;
		
		BigDecimal d = new BigDecimal(sumCost);
		sumCost = d.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		// 将总金额四舍五入至分
		BigDecimal c = new BigDecimal(amount);
		amount = c.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		// 将异常金额四舍五入至分
		BigDecimal b = new BigDecimal(a);
		double abnormalCost = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		mv.addObject("abnormalCost",abnormalCost);
		mv.addObject("sumCost",sumCost);
		mv.addObject("workbill",workbill);
		mv.addObject("contractId", contractId);
		mv.addObject("amount", amount );
		mv.addObject("watermemterCost", jo.getDouble("watermemterCost"));
		mv.addObject("ammeterCost", jo.getDouble("ammeterCost"));
		mv.addObject("we",we);
		mv.addObject("jo",jo);
		if(null != status){
			mv.addObject("status",status);
			mv.setViewName("page/apartment/apartmentmainmenu/apartmentcontract/waterAndElectricityTwo");
		} else {
			mv.setViewName("page/apartment/apartmentmainmenu/apartmentcontract/waterAndElectricity");
		}
		return mv;
	}
	
	/**
	 * 换房或结账时用于水电费清结算查询
	 * 
	 * @param request 入参
	 * @param response 出参
	 * @param we JSON数据集
	 * @param abnormalCost 异常合计金额
	 * @param payment 支付方式
	 * @param workbillId 工作账编号
	 * @throws JSONException JSON异常
	 * @throws Exception 异常
	 */
	@RequestMapping("/payMoney.do")
	public void payMoney(HttpServletRequest request, HttpServletResponse response, String wea,String abnormalCost, 
			String payment, String workbillId, String contractId)
			throws JSONException, Exception {
		// 将接收的数据转换成JSONObject
		JSONObject jo = new JSONObject(wea);
		// 获取loginUser从而得到操作人姓名
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String recordUser = loginUser.getStaff().getStaffName().toString();
		// 获取操作人电脑的Ip地址
		String operid = InetAddress.getLocalHost().getHostAddress();
		// 调用接口
		DataDealPortalService service = new DataDealPortalService();
		IDataDealPortal portal = service.getDataDealPortalPort();
		String contractList = portal.call(SystemConstants.EnginType.COMMON_DATA, SystemConstants.Movement.CUSTOM_QUERY, "{ function: \"apartmentList.updatPayAllHydropower\", "
						+ "data:{orderId:" + contractId + ",abnormalDays:" + jo.getString("abnormalDays") +
						",abnormalCost:"+ abnormalCost +",todayAmmeter:"+ jo.getDouble("todayAmmeter") +",todayAmmeterCost:"+ jo.getDouble("todayAmmeterCost") +
						",todayWater:"+ jo.getDouble("todayWater") +",todayWaterCost:"+ jo.getDouble("todayWaterCost") +",recordUser:"+ recordUser +
						",payment:"+ payment +",workbillId:"+ workbillId +
						",operIp:"+ operid +",watermemterCost:"+ jo.getDouble("watermemterCost") +
						",ammeterCost:"+ jo.getDouble("ammeterCost") +" } }");
		// 返回调用水电接口信息
		switch (Integer.parseInt(contractList)) {
		case 1:
			JSONUtil.responseJSON(response, new AjaxResult(Integer.parseInt(contractList), "缴费成功!"));
			break;
		case -1:
			JSONUtil.responseJSON(response, new AjaxResult(Integer.parseInt(contractList), "缴费失败!"));
			break;
		default:
			break;
		}
	}
	
	@RequestMapping("/payMoneyTwo.do")
	@SuppressWarnings("unchecked")
	public void payMoneyTwo(HttpServletRequest request, HttpServletResponse response, String wea,String abnormalCost, 
			String payment, String workbillId, String contractId, String status)
			throws JSONException, Exception {
		// 将接收的数据转换成JSONObject
		JSONObject jo = new JSONObject(wea);
		// 获取loginUser从而得到操作人姓名
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String recordUser = loginUser.getStaff().getStaffName().toString();
		// 获取操作人电脑的Ip地址
		//String operid = InetAddress.getLocalHost().getHostAddress();
		String operid = InetAddress.getLocalHost().toString();// IP地址
		operid = (String) operid.subSequence(operid.indexOf("/") + 1,operid.length());
		// 调用接口
		DataDealPortalService service = new DataDealPortalService();
		IDataDealPortal portal = service.getDataDealPortalPort();
		String contractList = portal.call(SystemConstants.EnginType.COMMON_DATA, SystemConstants.Movement.CUSTOM_QUERY, "{ function: \"apartmentList.updatPayAllHydropower\", "
						+ "data:{orderId:" + contractId + ",abnormalDays:" + jo.getString("abnormalDays") +
						",abnormalCost:"+ abnormalCost +",todayAmmeter:"+ jo.getDouble("todayAmmeter") +",todayAmmeterCost:"+ jo.getDouble("todayAmmeterCost") +
						",todayWater:"+ jo.getDouble("todayWater") +",todayWaterCost:"+ jo.getDouble("todayWaterCost") +",recordUser:"+ recordUser +
						",payment:"+ payment +",workbillId:"+ workbillId +
						",operIp:"+ operid +",watermemterCost:"+ jo.getDouble("watermemterCost") +
						",ammeterCost:"+ jo.getDouble("ammeterCost") +" } }");
		// 返回调用水电接口信息
		if(Integer.parseInt(contractList) == 1){
			//checkOut(request, response, contractId, status);
			List<CheckUser> checkUsers = apartmentCheckInService
					.findByPropertiesWithSort(CheckUser.class, "checkuserType",
							"asc", "checkId", contractId);
			Contrart contract = (Contrart) apartmentCheckInService.findById(
					Contrart.class, contractId);
			contract.setStatus("3");
			contract.setCheckOutTime(new Date());

			if (checkUsers.size() > 0) {
				for (CheckUser checkUser : checkUsers) {
					checkUser.setStatus("0");
					apartmentCheckInService.update(checkUser);
				}
			}
			if (status.equals("0")) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");
				String startTime = contract.getAptRenewTime() != null ? sdf
						.format(contract.getAptRenewTime()) : sdf.format(contract
						.getStartTime());// contract.getAptRenewTime()!=null ?
											// sdf.format(contract.getAptRenewTime())
											// :
				// String startTime = sdf.format();
				String endTime = sdf.format(contract.getContrartEndTime());
				Calendar sTime = Calendar.getInstance();
				Calendar eTime = Calendar.getInstance();
				sTime.setTime(sdf.parse(startTime));
				// sTime.setTime(new Date());
				eTime.setTime(sdf.parse(endTime));
				int result = sTime.get(Calendar.MONTH) - eTime.get(Calendar.MONTH);
				int month = (sTime.get(Calendar.YEAR) - eTime.get(Calendar.YEAR)) * 12;
				int num = Math.abs(month + result);
				for (int i = 0; i < num; i++) {
					Bill bill = new Bill();
					String billId = DateUtil.currentDate("yyMMdd")
							+ contract.getBranchId()
							+ apartmentCheckInService.getSequence("SEQ_NEW_BILL");
					bill.setBillId(billId);
					bill.setBranchId(contract.getBranchId());
					bill.setCheckId(contract.getContrartId());
					bill.setProjectId("1234");
					bill.setProjectName("房费");
					bill.setDescribe("房费");
					bill.setCost(Double.parseDouble(contract.getUnitPrice()));
					bill.setPayment("1");
					bill.setPay(0.0);
					bill.setStatus("1");
					bill.setRecordTime(new Date(0));
					bill.setRecordUser(loginUser.getStaff().getStaffId());
					apartmentCheckInService.save(bill);
				}
				Bill ForeGift = (Bill) apartmentCheckInService.findOneByProperties(Bill.class, "branchId", contract.getBranchId(), "checkId", contract.getContrartId(),
						"projectId", CommonConstants.BillProject.ForeGift);
				ForeGift.setRefundStatus("1");
				apartmentCheckInService.update(ForeGift);
			} else if (status.equals("1")) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");
				SimpleDateFormat sdfa = new SimpleDateFormat("yyyy/MM/dd");
				// String startTime = sdf.format(contract.getStartTime());
				String startTime = contract.getAptRenewTime() != null ? sdf
						.format(contract.getAptRenewTime()) : sdf.format(contract
						.getStartTime());
				String endTime = sdf.format(contract.getContrartEndTime());
				Calendar sTime = Calendar.getInstance();
				Calendar eTime = Calendar.getInstance();
				sTime.setTime(sdf.parse(startTime));
				eTime.setTime(sdf.parse(endTime));
				int result = sTime.get(Calendar.MONTH) - eTime.get(Calendar.MONTH);
				int month = (sTime.get(Calendar.YEAR) - eTime.get(Calendar.YEAR)) * 12;
				int num = Math.abs(month + result);
				for (int i = 0; i < num; i++) {
					Bill bill = new Bill();
					String billId = DateUtil.currentDate("yyMMdd")
							+ contract.getBranchId()
							+ apartmentCheckInService.getSequence("SEQ_NEW_BILL");
					bill.setBillId(billId);
					bill.setBranchId(contract.getBranchId());
					bill.setCheckId(contract.getContrartId());
					bill.setProjectId("1234");
					bill.setProjectName("房费");
					bill.setDescribe("房费");
					bill.setCost(Double.parseDouble(contract.getUnitPrice()));
					bill.setPayment("1");
					bill.setPay(0.0);
					bill.setStatus("1");
					bill.setRecordTime(new Date(0));
					bill.setRecordUser(loginUser.getStaff().getStaffId());
					apartmentCheckInService.save(bill);
				}
				Double price = (double) Math.round(Double.parseDouble(contract.getUnitPrice()) / Double.parseDouble("30"));
				String dayTime = sdfa.format(new Date());
				String firstDay = sdf.format(new Date()) + "/" + sdfa.format(contract.getStartTime()).split("/")[2];
				int days = (int) ((sdfa.parse(dayTime).getTime() - sdfa.parse(firstDay).getTime()) / (1000 * 3600 * 24));
				Double money = days * price;
				Bill bill = new Bill();
				String billId = DateUtil.currentDate("yyMMdd") + contract.getBranchId() + apartmentCheckInService.getSequence("SEQ_NEW_BILL");
				bill.setBillId(billId);
				bill.setBranchId(contract.getBranchId());
				bill.setCheckId(contract.getContrartId());
				bill.setProjectId("1234");
				bill.setProjectName("房费");
				bill.setDescribe("房费");
				bill.setCost(money);
				bill.setPayment("1");
				bill.setPay(0.0);
				bill.setStatus("1");
				bill.setRecordTime(new Date(0));
				bill.setRecordUser(loginUser.getStaff().getStaffId());
				apartmentCheckInService.save(bill);
				List<Bill> bills = apartmentCheckInService.findByPropertiesWithSort(Bill.class, "recordTime", "desc", "branchId", contract.getBranchId(), "checkId", contract.getContrartId());
				Bill reFundBill = (Bill) apartmentCheckInService.findById(Bill.class, bills.get(0).getBillId());
				reFundBill.setRefundStatus("1");
				reFundBill.setRemark(reFundBill.getRemark() == null ? "" + "退部分房租:" + String.valueOf(contract.getCash() - money) : reFundBill.getRemark() + "退部分房租:" + String.valueOf(contract.getCash() - money));
				Bill ForeGift = (Bill) apartmentCheckInService.findOneByProperties(Bill.class, "branchId", contract.getBranchId(), "checkId", contract.getContrartId(),
						"projectId", CommonConstants.BillProject.ForeGift);
				ForeGift.setRefundStatus("1");
				apartmentCheckInService.update(ForeGift);
			}

			OperateLog operlog = new OperateLog();
			operlog.setBranchId(contract.getBranchId());
			operlog.setLogId(DateUtil.currentDate("yyMMdd")
					+ contract.getBranchId()
					+ apartmentCheckInService.getSequence("SEQ_OPERATELOG_ID"));
			operlog.setOperIp(operid);
			operlog.setOperType("11");
			operlog.setOperModule("退房操作");
			operlog.setContent("合同号" + contract.getContrartId() + "退房");
			operlog.setRecordUser(loginUser.getStaff().getStaffId());
			operlog.setRecordTime(new Date());

			apartmentCheckInService.update(contract);
			apartmentCheckInService.save(operlog);

			List<RepairApply> repairApplys = apartmentCheckInService
					.findByProperties(RepairApply.class, "branchId",
							contract.getBranchId(), "contractId",
							contract.getContrartId());
			String roomStatus = "1";
			if (repairApplys.size() > 0) {
				for (RepairApply repairApply : repairApplys) {
					if (repairApply.getStatus().equals("2")
							|| repairApply.getStatus().equals("3")) {
						roomStatus = "W";
					}
				}
			}
			
			portal.call(1, 1, "{ function: \"yunSign.cancelContract\", data:{memberId:" + contract.getMemberId() + ",orderId:" + contract.getContrart() + "} }");
			
			roomService.upateroomstatus(contract.getBranchId(),
					contract.getRoomId(), roomStatus);
			JSONUtil.responseJSON(response, new AjaxResult(Integer.parseInt(contractList), "退房成功!"));
		} else {
			JSONUtil.responseJSON(response, new AjaxResult(Integer.parseInt(contractList), "退房失败!"));
		}
	}
	
	@RequestMapping("/isConflict.do")
	public void isConflict(HttpServletRequest request, HttpServletResponse response, String contractId, String roomId)
			throws IOException, ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Contrart contract = (Contrart) apartmentCheckInService.findById(Contrart.class, contractId);
		List<?> contracts = apartmentCheckInService.findBySQL("IsConflict", new String[] {contract.getBranchId(), roomId}, true);
		int num = 0;
		String result = "房间存在合同冲突";
		if(contracts.size() > 0){
			for(int i = 0; i < contracts.size(); i++){
				String startTime = ((Map<?, ?>)contracts.get(i)).get("STARTTIME").toString();
				String endTime = ((Map<?, ?>)contracts.get(i)).get("ENDTIME").toString();
				if(contract.getStartTime().getTime() > sdf.parse(endTime).getTime()){
					continue;
				} else if(contract.getEndTime().getTime() < sdf.parse(startTime).getTime()) {
					num = 1;
					result = "可以换房";
					break;
				}
			}
		} else {
			num = 1;
			result = "可以换房";
		}
		JSONUtil.responseJSON(response, new AjaxResult(num, result));
	}

	@RequestMapping("/queryApartmentCost.do")
	public void queryApartmentCost(HttpServletRequest request, HttpServletResponse response, String contractId)
			throws IOException, ParseException {
		Contrart contract = (Contrart) apartmentCheckInService.findById(Contrart.class, contractId);
		String contractList = queryAptCost(contract);
		JSONUtil.responseJSON(response, contractList);
	}
	
	public String queryAptCost(Contrart contract){
		DataDealPortalService service = new DataDealPortalService();
		IDataDealPortal portal = service.getDataDealPortalPort();
		String contractList = portal.call(SystemConstants.EnginType.COMMON_DATA, SystemConstants.Movement.CUSTOM_QUERY, "{ function: \"apartmentList.showNoPayHydropower\", "
				+ "data:{orderId:" + contract.getContrartId() + "  } }");
		return contractList;
	}
	
	@RequestMapping("/turnToContrartAddPage.do")
	public ModelAndView turnToCampaignsAddPage(HttpServletRequest request)
			throws Exception {
		ModelAndView mv = new ModelAndView();
		List<SysParam> paymentList = apartmentCheckInService.findByProperties(SysParam.class,"paramType", "PAYMENT","status","1");
		//支付方式
		mv.addObject("paymentList",paymentList);
		mv.setViewName("page/apartment/apartmentmainmenu/apartmentcontract/addContrart");
		return mv;
	}

	@RequestMapping("/countMoney.do")
	public void countMoney(HttpServletRequest request, HttpServletResponse  response, String contractId) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Contrart Contract = (Contrart) apartmentCheckInService.findOneByProperties(Contrart.class, "contrartId", contractId);
		Date nextTime = Contract.getContrartEndTime();
		Date date = sdf.parse(sdf.format(new Date()));
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(Contract.getContrartEndTime());
		Double money = 0.0;
		
		Double roomPrice = PriceUtil.doubleToPriceDouble(Double.parseDouble(Contract.getUnitPrice()) / Double.parseDouble("30"));
		if (Contract.getStatus().equals("4")){
			String startTime = sdf.format(new Date());
			String endTime = sdf.format(Contract.getContrartEndTime());
			Calendar sTime = Calendar.getInstance();
	        Calendar eTime = Calendar.getInstance();
	        sTime.setTime(sdf.parse(startTime));
	        eTime.setTime(sdf.parse(endTime));
	        int result = sTime.get(Calendar.MONTH) - eTime.get(Calendar.MONTH);
	        int month = (sTime.get(Calendar.YEAR) - eTime.get(Calendar.YEAR)) * 12;
	        int num = Math.abs(month + result);
			for(int i = 0; i < num; i++){
				calendar.add(Calendar.MONTH, -1);
				if (nextTime.getTime() >= date.getTime() && date.getTime() >= calendar.getTime().getTime()) {
					int days = (int) ((date.getTime() - calendar.getTime().getTime()) / (1000*3600*24));
					money = (money + (Double.parseDouble(Contract.getUnitPrice()) - (roomPrice * Double.parseDouble(Integer.toString(days)))));
				} else {
					money = money + Double.parseDouble(Contract.getUnitPrice());
				}
				nextTime = calendar.getTime();
			}
		} else if (Contract.getStatus().equals("1")) {
			String startTime = sdf.format(Contract.getStartTime());
			String endTime = sdf.format(Contract.getContrartEndTime());
			Calendar sTime = Calendar.getInstance();
	        Calendar eTime = Calendar.getInstance();
	        sTime.setTime(sdf.parse(startTime));
	        eTime.setTime(sdf.parse(endTime));
	        int result = sTime.get(Calendar.MONTH) - eTime.get(Calendar.MONTH);
	        int month = (sTime.get(Calendar.YEAR) - eTime.get(Calendar.YEAR)) * 12;
	        int num = Math.abs(month + result);
			money = Double.parseDouble(Contract.getUnitPrice()) * num;
		}
		JSONUtil.responseJSON(response, new AjaxResult(1, PriceUtil.doubleToPrice(money)));
	}
	
	@RequestMapping("/queryContract.do")
	public ModelAndView queryContract(HttpServletRequest request,
			HttpServletResponse response, String contract) throws IOException {
		ModelAndView mv = new ModelAndView();
		new JSONObject();
		Contrart contrart = (Contrart) apartmentCheckInService.findById(Contrart.class, contract);
		if (null != contrart.getContrart()) {
			String webPath = RequestUtil.getWebPath().replace("\\", "/");
			String contractPath = contrart.getContrart();
			if ((new File(webPath + "/upload/" + contractPath)).exists()) {
				contractToHtml(webPath + "/upload/" + contractPath, webPath + "/upload/", webPath + "/upload/1.html");
				mv.addObject("contract", contractPath);
			} else {
				DataDealPortalService service = new DataDealPortalService();
				IDataDealPortal portal = service.getDataDealPortalPort();
				String contractUrl = portal.call(1, 1, "{ function: \"yunSign.contractQuery\", data:{memberId:" + contrart.getMemberId() + ",orderId:" + contrart.getContrart() + "} }");
				mv.addObject("contractUrl", net.sf.json.JSONObject.fromObject(contractUrl).get("contractHtml").toString());
			}
			mv.addObject("ifNull", "true");
		} else {
			mv.addObject("contract", null);
			mv.addObject("ifNull", "false");
		}
		mv.setViewName("page/apartment/apartmentmainmenu/apartmentcontract/contract");
		return mv;
	}
	
	public static void contractToHtml(String fileName, String imageFile,
			String htmFile) throws IOException {
		File f = new File(fileName);
		if (f.getName().endsWith(".docx") || f.getName().endsWith(".DOCX")
				|| f.getName().endsWith(".doc")) {
			InputStream in = new FileInputStream(f);
			XWPFDocument document = new XWPFDocument(in);
			File imgFile = new File(imageFile);
			XHTMLOptions options = XHTMLOptions.create().URIResolver(
					new FileURIResolver(imgFile));
			options.setExtractor(new FileImageExtractor(imgFile));
			OutputStream out = new FileOutputStream(new File(htmFile));
			XHTMLConverter.getInstance().convert(document, out, options);
		}
	}
	
	@RequestMapping("/rentRenewal.do")
	public ModelAndView rentRenewal(HttpServletRequest request,
			HttpServletResponse response, String contractId) throws IOException {
		ModelAndView mv = new ModelAndView();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Contrart contrart = (Contrart) apartmentCheckInService.findById(Contrart.class, contractId);
		Member member = (Member) apartmentCheckInService.findById(Member.class, contrart.getMemberId());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(contrart.getContrartEndTime());
		//calendar.add(Calendar.MONTH, Integer.parseInt(contrart.getTypeOfPayment()));
		String typeofpaymentName = "";
		if (contrart.getTypeOfPayment().equals("1")) {
			typeofpaymentName = "月付";
		} else if (contrart.getTypeOfPayment().equals("3")) {
			typeofpaymentName = "季付";
		} else if (contrart.getTypeOfPayment().equals("6")) {
			typeofpaymentName = "半年付";
		} else if (contrart.getTypeOfPayment().equals("12")) {
			typeofpaymentName = "年付";
		}
		mv.addObject("contractId", contractId);
		mv.addObject("memberName", member.getMemberName());
		mv.addObject("roomId", contrart.getRoomId());
		Double money = Double.parseDouble(contrart.getUnitPrice()) * Double.parseDouble(contrart.getTypeOfPayment());
		mv.addObject("money",String.format("%.2f", money));
		mv.addObject("typeofpaymentName", typeofpaymentName);
		mv.addObject("unitPrice", contrart.getUnitPrice());
		mv.addObject("endTime", sdf.format(calendar.getTime()));
		mv.setViewName("page/apartment/apartmentmainmenu/apartmentcontract/rentRenewal");
		return mv;
	}
	
	//查房间号
	@RequestMapping("/contrartselectroomid.do")
	public ModelAndView contrartselectroomid(HttpServletRequest request,HttpServletResponse response,String memberrank,
			String startTime) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("memberrank",memberrank);
		mv.addObject("startTime",startTime);
		mv.addObject("memberrank",memberrank);
		mv.setViewName("page/apartment/apartmentmainmenu/apartmentcontract/addContrartRoomidDialog");
		return mv;
	}
	
	//根据房间号和门店号查出此房的定价（基准价或调整价）
	@RequestMapping("/findunitprice.do")
	public void findunitprice(HttpServletRequest request, HttpServletResponse response, String roomid,String memberrank)
			throws Exception {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchid = loginuser.getStaff().getBranchId();
		List<Map<String,Object>> unitpriceList = apartmentCheckInService.findBySQL("findunitprice",new String[]{memberrank,roomid,branchid},true);
		Double roomprice =  (Double)(unitpriceList.get(0)).get("ROOMPRICE");
		JSONUtil.responseJSON(response, roomprice);
	}
	
	//根据房间号和门店号查询该房间是否已为预约看房
	@RequestMapping("/findbespeakapart.do")
	public void findbespeakapart(HttpServletRequest request, HttpServletResponse response, String roomId,String startTime)
			throws Exception {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginuser.getStaff().getBranchId();
		List<String> reserved = apartmentCheckInService.findBySQL("queryApartReserved", new String[]{branchId,roomId,startTime},true);
		if (reserved.size() > 0) {
			JSONUtil.responseJSON(response, new AjaxResult(1, "","1"));
			return;
		}
		JSONUtil.responseJSON(response, new AjaxResult(1, "","0"));
	}
		
	// 查预订单
	@RequestMapping("/contrartaptorder.do")
	public ModelAndView contrartaptorder(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
        String branchId = loginUser.getStaff().getBranchId();
        mv.addObject("branchId", branchId);
		mv.setViewName("page/apartment/apartmentmainmenu/apartmentcontract/aptorderDialog");
		return mv;
	}
	
	@RequestMapping("/apartmentMemberAdd.do")
	public ModelAndView apartmentMemberAdd(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/apartment/apartmentmainmenu/apartmentcontract/ordermemberadd");
		return mv;
	}
	
	//预订单数据
	@RequestMapping("/querycontrartaptorder.do")
	public void querycontrartaptorder(HttpServletRequest request, HttpServletResponse response, String groupId,String branchId)
			throws Exception {
		Pagination pagination;
		Integer pageNum = Integer.parseInt(request.getParameter("page"));
		Integer rows = Integer.parseInt(request.getParameter("rows"));
		pagination = new Pagination(pageNum, rows);
		List<?> result = apartmentCheckInService.findBySQLWithPagination("allaptorder", new String[] {groupId,groupId,branchId}, pagination, true);
		JSONUtil.responseJSON(response, JSONUtil.buildReportJSONByPagination(result, pagination));
	}	
	
	// 房间号详情页数据
	@SuppressWarnings("unchecked")
	@RequestMapping("/queryresveredroomid.do")
	public void queryresveredroomid(HttpServletRequest request, HttpServletResponse response, String memberrank, String time, String roomtype)
			throws Exception {
		Pagination pagination;
		LoginUser loginuser = (LoginUser) request.getSession(true)
				.getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchid = loginuser.getStaff().getBranchId();
		Integer pageNum = Integer.parseInt(request.getParameter("page"));
		Integer rows = Integer.parseInt(request.getParameter("rows"));
		pagination = new Pagination(pageNum, rows);
	//	List<?> result = memberService.findBySQLWithPagination("roomidcontrartDetail", new String[] {branchid,groupId,memberrank}, pagination, true);
		List<Map<String,Object>> roominfoList = apartmentCheckInService.findBySQLWithPagination("queryreversedroomid",new String[]{branchid,branchid,branchid, time},pagination,true);
		JSONUtil.responseJSON(response, JSONUtil.buildReportJSONByPagination(roominfoList, pagination));
	}
	
	// 房间号详情页数据
	@SuppressWarnings("unchecked")
	@RequestMapping("/querycontrartroomidandroomtype.do")
	public void querycontrartroomidandroomtype(HttpServletRequest request, HttpServletResponse response, String groupId,String memberrank)
			throws Exception {
		Pagination pagination;
		LoginUser loginuser = (LoginUser) request.getSession(true)
				.getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchid = loginuser.getStaff().getBranchId();
		Integer pageNum = Integer.parseInt(request.getParameter("page"));
		Integer rows = Integer.parseInt(request.getParameter("rows"));
		pagination = new Pagination(pageNum, rows);
	//	List<?> result = memberService.findBySQLWithPagination("roomidcontrartDetail", new String[] {branchid,groupId,memberrank}, pagination, true);
		List<Map<String,Object>> roominfoList = apartmentCheckInService.findBySQLWithPagination("findunitprice",new String[]{branchid,memberrank,groupId,branchid,branchid,memberrank,groupId,branchid},pagination,true);
		JSONUtil.responseJSON(response, JSONUtil.buildReportJSONByPagination(roominfoList, pagination));
	}	
	
	/**
	 * 预约看房，修改房间号
	 * @param request
	 * @param response
	 * @param dataid
	 * @param roomid
	 * @throws Exception
	 */
	@RequestMapping("/resetresveredroomid.do")
	public void resetresveredroomid(HttpServletRequest request, HttpServletResponse response, String dataId, String roomid)
			throws Exception {
		Reserved reserved = (Reserved) apartmentCheckInService.findById(Reserved.class, dataId);
		reserved.setRoomId(roomid);
		try{
			apartmentCheckInService.save(reserved);
			JSONUtil.responseJSON(response, new AjaxResult(1, "修改成功!"));
		}catch(Exception e){
			JSONUtil.responseJSON(response, new AjaxResult(0, "修改失败!"));
		}
	}
		
		
		@SuppressWarnings("unchecked")
		@RequestMapping("/loadRoomPrice4Contrart.do")
		public void loadRoomPrice4Contrart(HttpServletRequest request, HttpServletResponse response,
				String startTime,String roomType,String memberRank)
				throws Exception {
			LoginUser loginuser = (LoginUser) request.getSession(true)
					.getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
			String branchid = loginuser.getStaff().getBranchId();
			double discount = 1.0;
			MemberRank mr = (MemberRank)apartmentCheckInService.findOneByProperties(MemberRank.class, "memberRank", memberRank, "status","1");
			if(mr!=null){
				discount = (double)mr.getDiscount()/100;
			}
			SinglePrice sp = new SinglePrice(branchid,"2",roomType,"MSJ","3",startTime+" 12:00:00");
			Double price = sp.checkRoomPrice()*discount;
			
			/*NumberFormat nf = NumberFormat.getNumberInstance();
	        nf.setMaximumFractionDigits(2); 
	        nf.setRoundingMode(RoundingMode.CEILING);*/
	        String price_str =  String.format("%.2f",price);
			JSONUtil.responseJSON(response, new AjaxResult(1, price_str));
		}	
		
}
