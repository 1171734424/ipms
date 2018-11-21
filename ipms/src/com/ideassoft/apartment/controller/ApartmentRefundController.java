package com.ideassoft.apartment.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ideassoft.apartment.service.ApartmentRefundService;
import com.ideassoft.bean.Bill;
import com.ideassoft.bean.Check;
import com.ideassoft.bean.Contrart;
import com.ideassoft.bean.OffsetLog;
import com.ideassoft.bean.OperateLog;
import com.ideassoft.bean.Order;
import com.ideassoft.bean.Recording;
import com.ideassoft.bean.RoomMapping;
import com.ideassoft.bean.Staff;
import com.ideassoft.bean.SysParam;
import com.ideassoft.bean.wrapper.CheckAllAmount;
import com.ideassoft.bean.wrapper.CheckoutRoom;
import com.ideassoft.bean.wrapper.MultiQueryCheck;
import com.ideassoft.core.ajax.AjaxResult;
import com.ideassoft.core.annotation.interfaces.RightMethodControl;
import com.ideassoft.core.annotation.interfaces.RightModelControl;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.constants.CommonConstants;
import com.ideassoft.core.constants.RightConstants;
import com.ideassoft.core.page.Pagination;
import com.ideassoft.util.DateUtil;
import com.ideassoft.util.JSONUtil;
import com.ideassoft.util.PriceUtil;
import com.ideassoft.util.ReflectUtil;
import com.ideassoft.util.RequestUtil;

@Transactional
@Controller
@RightModelControl(rightModel = RightConstants.RightModel.APARTMENT_CONTROL)
public class ApartmentRefundController {
	
	@Autowired
	private ApartmentRefundService apartmentRefundService;
	
	/**
	 * 公寓管理-公寓退款查询页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/apartmentRefund.do")
	@RightMethodControl(rightType = RightConstants.RightType.APA_REFUND)
	public ModelAndView apartmentRent(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/apartment/apartmentLeftmenu/apartmentrefund/apartmentrefund");
		return mv;
	}
	
	/**
	 * 公寓管理-公寓退款详情页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/apartmentRefundAll.do")
	public ModelAndView apartmentRefundAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Pagination pagination = (Pagination) ReflectUtil.setBeanFromRequest(request, Pagination.class);
		if (pagination != null && pagination.getPageNum() == null) {
			pagination.setPageNum(1);
			pagination.setRows(17);
		}
		String memberName = request.getParameter("memberName");
		String mobile = request.getParameter("mobile");
		String reservedTime = request.getParameter("reservedTime");
		List<?> list = apartmentRefundService.findBySQLWithPagination("apartmentRefundAll", new String[] { loginUser.getStaff().getBranchId(), memberName, mobile, reservedTime }, pagination, true);
		mv.addObject("list", list);
		mv.addObject("orderuser", memberName);
		mv.addObject("mobile", mobile);
		mv.addObject("ordertime", reservedTime);
		mv.addObject("pagination", pagination);
		mv.setViewName("page/apartment/apartmentLeftmenu/apartmentrefund/apartmentrefundinfo");
		return mv;
	}
	
	/**
	 * 公寓管理-公寓退款详情页面-“退款”按键事件
	 * 
	 * @param request
	 * @param response
	 * @param checkid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/apartmentRefundMoney.do")
	public ModelAndView apartmentRefundMoney(HttpServletRequest request,
			HttpServletResponse response, String checkid) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.addObject("checkid", checkid);
		mv.setViewName("page/apartment/apartmentLeftmenu/apartmentrefund/apartmentrefuntbill");
		return mv;
	}
	
	/**
	 * 加载公寓退款时消费、结算页面信息
	 * 
	 * @param request
	 * @param response
	 * @param checkid
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/loadApartmentPayandCost.do")
	public void loadApartmentPayandCost(HttpServletRequest request, HttpServletResponse response, String checkid) {
		//Contrart Contract = (Contrart) apartmentRefundService.findOneByProperties(Contrart.class, "contrartId", checkid);
		CheckAllAmount checkallamount = new CheckAllAmount();
		Double cost = 0D;
		Double pay = 0D;
		//if (Contract.getStatus().equals(CommonConstants.ApartmentStatus.CheckOffAndUnout)) {
			List<Bill> bills = apartmentRefundService.findByProperties(Bill.class, "checkId", checkid);
			for (Bill bill : bills) {
				cost += bill.getCost();
				pay += bill.getPay();
			}
			checkallamount.setCost(cost);
			checkallamount.setPay(pay);
		//}
		JSONUtil.responseJSON(response, checkallamount);
	}
	
	/**
	 * 退换押金(公寓)-查询关联房信息
	 *  
	 * @param request
	 * @param response
	 * @param checkid
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/apartmentloadroommapping.do")
	public void apartmentloadroommapping(HttpServletRequest request, HttpServletResponse response, String checkid) {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String branchId = staff.getBranchId();
		Check check = (Check) apartmentRefundService.findOneByProperties(Check.class, "checkId", checkid);
		Order order = (Order) apartmentRefundService.findById(Order.class, checkid);
		String roomid = check == null ? order.getRoomId() : check.getRoomId();
		RoomMapping roommapping = (RoomMapping) apartmentRefundService.findOneByProperties(RoomMapping.class, "relaRoomid", roomid);
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		if(roommapping != null){
			result = apartmentRefundService.findBySQL("loadroommapping", new String[] { branchId, roommapping.getRoomId() }, true);
		}
		JSONUtil.responseJSON(response, result);
	}
	
	/**
	 * 退换押金(公寓)-查询房间账单信息
	 * 
	 * @param request
	 * @param response
	 * @param checkid
	 * @param status
	 */
	@RequestMapping("/apartmentloadRoomBillData.do")
	public void apartmentloadRoomBillData(HttpServletRequest request, HttpServletResponse  response, String checkid, String status) {
		Contrart Contract = (Contrart) apartmentRefundService.findOneByProperties(Contrart.class, "contrartId", checkid);
		String sql = "loadroomlistbill";
		List<?> alllist = apartmentRefundService.findBySQL(sql, new String[] { checkid, Contract.getBranchId(), null}, true);
		List<?> normallist = apartmentRefundService.findBySQL(sql, new String[] { checkid, Contract.getBranchId(), "1" }, true);
		//List<?> list = apartmentRefundService.findBySQL(sql, new String[] { checkid, Contract.getBranchId(), status }, true);
		List<?> list = apartmentRefundService.findBillBySql(checkid, Contract.getBranchId(), status);
		if (alllist.size() <= normallist.size()) {
			JSONUtil.responseJSON(response, new AjaxResult(0, "账单无冲减/转账记录!", list));
		} else {
			JSONUtil.responseJSON(response, new AjaxResult(1, "", list));
		}
	}
	
	/**
	 * 退换押金(公寓)-跳转入账页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/showApartmentAddBill.do")
	public ModelAndView showApartmentAddBill(HttpServletRequest request) {
		String checkid = request.getParameter("checkid");
		ModelAndView mv = new ModelAndView();
		mv.addObject("checkid", checkid);
		mv.setViewName("/page/apartment/apartmentLeftmenu/apartmentrefund/add_bill");
		return mv;
	}
	
	/**
	 * 退换押金(公寓)-提交入账功能
	 * 
	 * @param request
	 * @param response
	 * @param checkId
	 * @param project
	 * @param projectid
	 * @param amount
	 * @param remark
	 */
	@RequestMapping("/apartmentAddbill.do")
	public void apartmentAddbill(HttpServletRequest request, HttpServletResponse response, String checkId, String project,
			String projectid, String amount, String remark) {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String branchid = "";
		Bill bill = new Bill();
		Contrart Contract = (Contrart) apartmentRefundService.findOneByProperties(Contrart.class, "contrartId", checkId);
		branchid = Contract.getBranchId();
		String billId = DateUtil.currentDate("yyMMdd") + branchid + apartmentRefundService.getSequence("SEQ_NEW_BILL");
		bill.setBillId(billId);
		bill.setBranchId(branchid);
		bill.setCheckId(checkId);
		bill.setProjectId(projectid);// gai
		bill.setProjectName(project);
		bill.setDescribe("入账");
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
		if ("2001".equals(projectid)) {
			bill.setPayment("1");
		} else if ("2002".equals(projectid)) {
			bill.setPayment("1");
		} else if ("2003".equals(projectid)) {
			bill.setPayment("2");
		} else {
			bill.setPayment("0");
		}
		bill.setStatus("1");
		bill.setRemark(remark);
		bill.setRecordUser(staff.getStaffId());

		Recording recording = new Recording();
		String recordId = DateUtil.currentDate("yyMMdd") + branchid + apartmentRefundService.getSequence("SEQ_NEW_RECORDING");
		recording.setRecordId(recordId);
		recording.setBranchId(branchid);
		recording.setCheckId(checkId);
		recording.setRecordType("1");
		recording.setProjectId(projectid);
		recording.setFee(Double.valueOf(amount));
		recording.setRecordUser(staff.getStaffId());

		try {
			apartmentRefundService.save(bill);
			apartmentRefundService.save(recording);
			JSONUtil.responseJSON(response, new AjaxResult(1, "入账成功!"));
		} catch (Exception e) {
			e.printStackTrace();
			JSONUtil.responseJSON(response, new AjaxResult(0, "入账失败!"));
		}
	}
	
	/**
	 * 退换押金(公寓)-入账查询项目信息
	 * 
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getApartmentProject.do")
	public void getApartmentProject(HttpServletRequest request, HttpServletResponse response) {
		String sqltype = "queryprojecttype";
		String sqlallproejct = "queryallproject";
		List<Map<String, Object>> projecttype = apartmentRefundService.findBySQL(sqltype, true);
		Map<Object, Map<String, String>> allkingmap = new HashMap<Object, Map<String, String>>();
		for (Map<String, Object> map : projecttype) {
			Object orderno = map.get("ORDERNO");
			Object paramdesc = map.get("PARAMDESC");
			List<Map<String, String>> allkindproject = apartmentRefundService.findBySQL(sqlallproejct, 
					new String[] {CommonConstants.SysparamStatus.VALID, orderno.toString() }, true);
			Map<String, String> resultallkind = new HashMap<String, String>();
			for (Map<String, String> map2 : allkindproject) {
				String content = map2.get("CONTENT");
				String paramname = map2.get("PARAMNAME");
				resultallkind.put(content, paramname);
				allkingmap.put(paramdesc, resultallkind);
			}
		}
		JSONUtil.responseJSON(response, allkingmap);
	}
	
	/**
	 * 退换押金(公寓)-“冲减”提交功能
	 * 
	 * @param request
	 * @param response
	 * @param strbillid
	 * @param offset
	 * @param checkid
	 */
	@RequestMapping("/apartmentConsumption.do")
	public void apartmentConsumption(HttpServletRequest request, HttpServletResponse response, String strbillid, String offset, String checkid) {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Contrart Contract = (Contrart) apartmentRefundService.findOneByProperties(Contrart.class, "contrartId", checkid);
		Staff staff = loginuser.getStaff();
		String[] arrbill = strbillid.split(",");
		boolean errorbillflag = true;
		for (String billid : arrbill) {
			Bill bill = (Bill) apartmentRefundService.findOneByProperties(Bill.class, "billId", billid);
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
			Bill bill = (Bill) apartmentRefundService.findOneByProperties(Bill.class, "billId", billid);
			String billId = DateUtil.currentDate("yyMMdd") + Contract.getBranchId() + apartmentRefundService.getSequence("SEQ_NEW_BILL");
			consumebill.setBillId(billId);
			consumebill.setBranchId(Contract.getBranchId());
			consumebill.setCheckId(bill.getCheckId());
			consumebill.setDescribe(bill.getDescribe());
			consumebill.setOffset(offset);
			consumebill.setPayment(bill.getPayment());
			consumebill.setProjectId(bill.getProjectId());
			consumebill.setProjectName(bill.getProjectName());
			consumebill.setRecordUser(staff.getStaffId());
			consumebill.setRemark(bill.getBillId());
			consumebill.setStatus("2");

			String projectid = bill.getProjectId();
			// 记日志
			OffsetLog otlog = new OffsetLog();
			String logid = DateUtil.currentDate("yyMMdd") + Contract.getBranchId()
					+ apartmentRefundService.getSequence("SEQ_OFFSETLOG_ID");
			otlog.setLogId(logid);
			otlog.setBranchId(Contract.getBranchId());
			otlog.setCheckId(bill.getCheckId());
			otlog.setLastBillId(bill.getBillId());
			otlog.setCurrBillId(consumebill.getBillId());
			otlog.setRecordUser(staff.getStaffId());
			otlog.setRecordTime(new Date());
			otlog.setOffsetType("1");

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
				apartmentRefundService.save(consumebill);
				apartmentRefundService.save(bill);
				apartmentRefundService.save(otlog);// 记冲减日志
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		JSONUtil.responseJSON(response, new AjaxResult(1, "冲减金额成功!"));
	}
	
	/**
	 * 退换押金(公寓)-点击结账跳转结账页面
	 * 
	 * @param request
	 * @param response
	 * @param checkid
	 */
	@RequestMapping("/apartmentOrderoff.do")
	public void apartmentOrderoff(HttpServletRequest request, HttpServletResponse response, String checkid) {
		Contrart Contract = (Contrart) apartmentRefundService.findOneByProperties(Contrart.class, "contrartId", checkid);
		String status = Contract.getStatus();
		if ("2".equalsIgnoreCase(status)) {
			JSONUtil.responseJSON(response, new AjaxResult(0, "当前已结算!"));
		} else {
			JSONUtil.responseJSON(response, new AjaxResult(1, "可以结算!"));
		}
	}
	
	/**
	 * 退换押金(公寓)-结账功能
	 * 
	 * @param request
	 * @param response
	 * @param multiQuerycheck
	 */
	@RequestMapping("/loadApartmentCheckData.do")
	public void loadCheckData(HttpServletRequest request, HttpServletResponse response, MultiQueryCheck multiQuerycheck) {
		Contrart Contract = (Contrart) apartmentRefundService.findOneByProperties(Contrart.class, "contrartId", multiQuerycheck.getCheckid());
		JSONUtil.responseJSON(response, Contract);
	}
	
	/**
	 * 退换押金(公寓)-检测备注是否存在
	 * 
	 * @param request
	 * @param response
	 * @param checkid
	 * @param remark
	 */
	@RequestMapping("/autoApartSaveRemark.do")
	public void autosaveRemark(HttpServletRequest request, HttpServletResponse response, String checkid, String remark) {
		Check check = (Check) apartmentRefundService.findOneByProperties(Check.class, "checkId", checkid);
		check.setRemark(remark);
		try {
			apartmentRefundService.save(check);
		} catch (Exception e) {
			e.printStackTrace();
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, "备注丢失!"));
		}
	}
	
	/**
	 * 退换押金(公寓)-点击退房按钮功能
	 * 
	 * @param request
	 * @param response
	 * @param checkoutroom
	 * @throws UnknownHostException
	 */
	@RequestMapping("/apartmentCheckOutBill.do")
	public void apartmentCheckOutBill(HttpServletRequest request, HttpServletResponse response, CheckoutRoom checkoutroom) throws UnknownHostException {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Contrart contract = (Contrart) apartmentRefundService.findOneByProperties(Contrart.class, "contrartId", checkoutroom.getCheckId());
		String branchId = contract.getBranchId();
		String checkId = contract.getContrartId();
		String money = "";
		if(checkoutroom.getAmount() == null || "".equals(checkoutroom.getAmount())){
			checkoutroom.setAmount("0");
		}
		SysParam sysparam = (SysParam) apartmentRefundService.findOneByProperties(SysParam.class, "paramType", "PROJECT", "content", checkoutroom.getProjectid());
		if(sysparam != null){
			String project = sysparam.getParamName();
			checkoutroom.setProject(project);
		}
		if (!("0".equals(checkoutroom.getAmount())
				|| checkoutroom.getProjectid() == null || "".equals(checkoutroom.getProjectid()))) {
			Bill bill = new Bill();
			String billId = DateUtil.currentDate("yyMMdd") + branchId + apartmentRefundService.getSequence("SEQ_NEW_BILL");
			bill.setBillId(billId);
			bill.setBranchId(branchId);
			bill.setCheckId(checkId);
			bill.setProjectId(checkoutroom.getProjectid());
			bill.setProjectName(checkoutroom.getProject());
			bill.setDescribe("结账");
			bill.setCost(0.00);
			if ("2001".equals(checkoutroom.getProjectid())) {
				bill.setPay(-Double.valueOf(checkoutroom.getAmount()));
				money = "-" + checkoutroom.getAmount();
				bill.setPayment("1");
			} else if ("2002".equals(checkoutroom.getProjectid())) {
				bill.setPay(Double.valueOf(checkoutroom.getAmount()));
				money = checkoutroom.getAmount();
				bill.setPayment("1");
			} else if ("2003".equals(checkoutroom.getProjectid())) {
				bill.setPay(Double.valueOf(checkoutroom.getAmount()));
				money = checkoutroom.getAmount();
				bill.setPayment("2");
			} else {
				bill.setPay(Double.valueOf(checkoutroom.getAmount()));
				money = checkoutroom.getAmount();
				bill.setPayment("3");
			}
			bill.setStatus("1");
			bill.setRecordUser(loginuser.getStaff().getStaffId());
			apartmentRefundService.save(bill);
		}
		
		OperateLog operlog = new OperateLog();
		operlog.setBranchId(contract.getBranchId());
		operlog.setLogId(DateUtil.currentDate("yyMMdd") + contract.getBranchId() + apartmentRefundService.getSequence("SEQ_OPERATELOG_ID"));
		String operid = InetAddress.getLocalHost().toString();// IP地址
		operid = (String) operid.subSequence(operid.indexOf("/") + 1, operid.length());
		operlog.setOperIp(operid);
		operlog.setOperType("11");
		operlog.setOperModule("退房操作");
		operlog.setContent("合同号" + contract.getContrartId() + "结账,退款金额:" + money);
		operlog.setRecordUser(loginuser.getStaff().getStaffId());
		operlog.setRecordTime(new Date());
		apartmentRefundService.save(operlog);
		
		if (contract != null) {
			contract.setStatus(CommonConstants.ApartmentStatus.CheckOff);
			contract.setRecordTime(new Date());
			apartmentRefundService.update(contract);
		}
		
		JSONUtil.responseJSON(response, new AjaxResult(1, "结算成功!"));
	}
	
	/**
	 * 查看公寓账单对应的合同的状态
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/queryApartContrartStatus.do")
	public void queryApartContrartStatus(HttpServletRequest request,HttpServletResponse response){
		String billId = request.getParameter("billId");
		Bill bill = (Bill) apartmentRefundService.findOneByProperties(Bill.class, "billId",billId);
		Contrart ct = (Contrart) apartmentRefundService.findOneByProperties(Contrart.class, "contrartId", bill.getCheckId());
		//不能操作
		if(ct.getStatus().equals(CommonConstants.ApartmentStatus.Cancel) || ct.getStatus().equals(CommonConstants.CheckStatus.CheckOff)){//取消,退租
			JSONUtil.responseJSON(response, new AjaxResult(1,"失效账单,无法操作!"));
		}else{
			JSONUtil.responseJSON(response, new AjaxResult(2,bill.getRemark()));
		}
	}
	
	/**
	 * 修改公寓账单备注页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/reWriteApartRemarkAP.do")
	public ModelAndView reWriteApartRemarkAP(HttpServletRequest request,HttpServletResponse response){

		String billId = request.getParameter("billId");
		String text = request.getParameter("text");
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/apartment/apartmentmainmenu/apartmentcontract/reWriteRemarkAp");
		mv.addObject("text", text);
		mv.addObject("billId",billId);
		return mv;
	}
	
}