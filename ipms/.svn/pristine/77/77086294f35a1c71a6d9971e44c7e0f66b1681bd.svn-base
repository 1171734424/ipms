package com.ideassoft.apartment.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ideassoft.apartment.service.ApartmentWorkBillService;
import com.ideassoft.bean.Bill;
import com.ideassoft.bean.Branch;
import com.ideassoft.bean.Check;
import com.ideassoft.bean.Staff;
import com.ideassoft.bean.WorkBill;
import com.ideassoft.bean.WorkBillDetail;
import com.ideassoft.bean.wrapper.MultiQueryWorkbill;
import com.ideassoft.core.ajax.AjaxResult;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.constants.CommonConstants;
import com.ideassoft.core.jdbc.SqlBuilder;
import com.ideassoft.core.page.Pagination;
import com.ideassoft.util.JSONUtil;
import com.ideassoft.util.RequestUtil;

@Controller
public class ApartmentWorkBillController {
	
	@Autowired
	private ApartmentWorkBillService awbService;
	
	
	@RequestMapping("/apartmentshowworkaccountdetail.do")
	public ModelAndView apartmentshowworkaccountdetail(MultiQueryWorkbill multiqueryworkbill, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String branchId = staff.getBranchId();
		Pagination pagination = SqlBuilder.buildPagination(request);
		String recorduser = staff.getStaffId();
		// String branchid = staff.getBranchId();
		// String workbillid = sdf.format(currentdate);
		if ("self".equals(multiqueryworkbill.getRecorduser())) {
			multiqueryworkbill.setRecorduser(recorduser);
		}
		String sql = "loadworkbill";
		List<?> list = awbService.findBySQLWithPagination(sql, new String[] { multiqueryworkbill.getWorkbillid(),
				branchId, multiqueryworkbill.getStatus(), multiqueryworkbill.getCurrentdate(),
				multiqueryworkbill.getName(), multiqueryworkbill.getRecorduser() }, pagination, true);
		ModelAndView mv = new ModelAndView();
		mv.addObject("list", list);
		mv.addObject("pagination", pagination);
		mv.addObject("multiqueryworkbill", multiqueryworkbill);
		mv.setViewName("/page/apartment/apartmentmainmenu/apartmentworkbill/workaccountData");
		return mv;
	}
	
	@RequestMapping("/apartmentgetworkbillinfo.do")
	public void apartmentgetworkbillinfo(HttpServletRequest request, HttpServletResponse response, String hostcheckid,
			String workbillid) {
		WorkBill workbill = (WorkBill) awbService.findOneByProperties(WorkBill.class, "workbillId", workbillid);
		Staff recordUser = (Staff) awbService.findOneByProperties(Staff.class, "staffId", workbill.getRecordUser());
		Staff finalUser = (Staff) awbService.findOneByProperties(Staff.class, "staffId", workbill.getFinalUser());
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("workbill", workbill);
		String finalUserName = "";
		String recordUserName = "";
		if (finalUser != null) {
			finalUserName = finalUser.getStaffName();
		}
		if (recordUser != null) {
			recordUserName = recordUser.getStaffName();
		}
		result.put("finalUserName", finalUserName);
		result.put("recordUserName", recordUserName);
		JSONUtil.responseJSON(response, result);
	}
	
	
	
	@RequestMapping("/apartmentupdateworkbill.do")
	public void apartmentupdateworkbill(String name, String describe, String workbillid, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			awbService.apartmentupdateworkbill(name, describe, workbillid);
			JSONUtil.responseJSON(response, new AjaxResult(1, "修改成功!"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@RequestMapping("/apartmentloadWorkBillData.do")
	public void apartmentloadWorkBillData(HttpServletRequest request, HttpServletResponse response, String workbillid,
			String status) {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchid = loginuser.getStaff().getBranchId();
		String sql = "loadworkbilldetail";
		List<?> list = awbService.findBySQL(sql, new String[] { status, workbillid, branchid }, true);
		JSONUtil.responseJSON(response, list);
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/apartmentloadpayandcostworkbill.do")
	public void apartmentloadpayandcostworkbill(HttpServletRequest request, HttpServletResponse response, String workbillid) {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String branchId = staff.getBranchId();
		String sqlcost = "totalcostfromwork";
		List<Map<String, BigDecimal>> costresult = awbService.findBySQL(sqlcost,
				new String[] { workbillid, branchId }, true);
		String sqlpay = "totalpayfromwork";
		List<Map<String, BigDecimal>> payresult = awbService.findBySQL(sqlpay, new String[] { workbillid, branchId },
				true);
		double pay = 0;
		double cost = 0;
		if (!costresult.isEmpty()) {
			pay = payresult.get(0).get("totalpay").doubleValue();
		}
		if (!payresult.isEmpty()) {
			cost = costresult.get(0).get("totalcost").doubleValue();
		}
		Map<String, Double> mappayandcost = new HashMap<String, Double>();
		mappayandcost.put("pay", pay);
		mappayandcost.put("cost", cost);
		JSONUtil.responseJSON(response, mappayandcost);
	}
	
	@RequestMapping("/apartmentshowgetAddWorkBill.do")
	public ModelAndView apartmentshowgetAddWorkBill(HttpServletRequest request) {
		String workbillid = request.getParameter("workbillid");
		ModelAndView mv = new ModelAndView();
		mv.addObject("workbillid", workbillid);
		mv.setViewName("/page/apartment/apartmentmainmenu/apartmentworkbill/add_workbill");
		return mv;
	}
	
	
	
	
	//工作帐中冲减的判断情况
		@RequestMapping("/apartmentcheckAllRights.do")
		public void apartmentcheckAllRights(String strdetailid, HttpServletRequest request, HttpServletResponse response) {
			LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
			String branchId = loginUser.getStaff().getBranchId();
			String staffId = loginUser.getStaff().getStaffId();
			String[] strdetailIds = strdetailid.split(",");
			Branch branchData = (Branch) awbService.findOneByProperties(Branch.class, "branchId", branchId, "status", "1");
			Staff staffData = (Staff) awbService.findOneByProperties(Staff.class, "staffId", staffId, "status", "1");
			int flag = 1;
			String message = "";
			
			for (String strDetailId : strdetailIds) {
				WorkBillDetail workBillDetail = (WorkBillDetail) awbService.findOneByProperties(WorkBillDetail.class, "detailId", strDetailId, "status", "1");
				Calendar cal = Calendar.getInstance();
				Calendar recordTime = Calendar.getInstance();
				Calendar d = Calendar.getInstance();
				int lastDays = 0;
				Date date = new Date();
				cal.setTime(date);
				recordTime.setTime(workBillDetail.getRecordTime());
				int nowYear = cal.get(Calendar.YEAR);
				int nowDay = cal.get(Calendar.DAY_OF_YEAR);
				int recYear = recordTime.get(Calendar.YEAR);
				int recDay = recordTime.get(Calendar.DAY_OF_YEAR);
				int sevenDay = 0;
				//判断是否为同一年，同一年直接减天数，不是同一年则需要加上上一年的总天数
				if (nowYear == recYear) {
					sevenDay = nowDay - recDay;
				} else {
					d.set(Calendar.YEAR, recYear);
					lastDays = d.getActualMaximum(Calendar.DAY_OF_YEAR);
					sevenDay = nowDay + lastDays - recDay;
				}
				
				if (staffData.getPost().equals("0008")) {
					if (branchData.getBranchType().equals(CommonConstants.Branch.HOTELID)) {
						//酒店店长的情况下，判断是否是当天的账单
						if (nowYear != recYear || nowDay != recDay) {
							flag = 2; 
							message = "只能冲减当天账单";
							break;
						}
					} else if (branchData.getBranchType().equals(CommonConstants.Branch.APARTMENTID)) {
						//公寓店长的情况下，判断是否是七天内的账单
						if (sevenDay > 7) {
							flag = 3; 
							message = "只能冲减七天内账单";
							break;
						}
					}
				} else {
					//酒店公寓非店长的情况下，判断是否是当天的账单
					if (nowYear != recYear || nowDay != recDay || !staffId.equals(workBillDetail.getRecordUser())) {
						flag = 4; 
						message = "普通员工只能冲减自己当天的账单";
						break;
					}
				}
			}
			JSONUtil.responseJSON(response, new AjaxResult(flag, message));
		}
		
		
		@RequestMapping("/apartmentcheckoutworkbill.do")
		public void apartmentcheckoutworkbill(HttpServletRequest request, HttpServletResponse response, String workbillid) {
			LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
			Staff staff = loginuser.getStaff();
			// String branchId = staff.getStaffId();
			String recordUser = staff.getStaffId();
			WorkBill workbill = (WorkBill) awbService.findOneByProperties(WorkBill.class, "workbillId", workbillid);
			workbill.setFinalTime(new Date());
			workbill.setFinalUser(recordUser);
			workbill.setStatus("2");
			try {
				awbService.update(workbill);
				JSONUtil.responseJSON(response, new AjaxResult(1, "结账成功!"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		/**
		 * 跳转修改页面
		 * @return
		 * @throws UnsupportedEncodingException 
		 */
		 @RequestMapping("/apartmentreWriteWKRemark.do")
		public ModelAndView apartmentreWriteWKRemarkAp(HttpServletRequest request) throws UnsupportedEncodingException {		
			String workBillId = request.getParameter("billId");
			String text = request.getParameter("text");
			ModelAndView mv = new ModelAndView();
			mv.setViewName("page/apartment/apartmentmainmenu/apartmentworkbill/reWriteWKRemark");
			mv.addObject("text",text);
			mv.addObject("workBillId",workBillId);
			return mv;
		}
		 
		 
		 @RequestMapping("/apartmentaddRemarkToBill.do")
			public void apartmentaddRemarkToBill(HttpServletRequest request,HttpServletResponse response){
				LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
				String billId = request.getParameter("billId");
				String remark = request.getParameter("remark");
				Bill bill = (Bill) awbService.findOneByProperties(Bill.class, "billId",billId);
				bill.setRemark(remark);
//				bill.setRecordUser(loginUser.getStaff().getStaffId());
//				bill.setRecordTime(new Date());
				try {
					awbService.update(bill);
					JSONUtil.responseJSON(response, new AjaxResult(1,"修改成功!"));
				} catch (Exception e) {
					e.printStackTrace();
					JSONUtil.responseJSON(response, new AjaxResult(2,"操作异常!"));
				}
			}
		 
		 @RequestMapping("/apartmentisWorkbillCheckout.do")
			public void apartmentisWorkbillCheckout(HttpServletRequest request, HttpServletResponse response, String workbillid) {
				WorkBill workbill = (WorkBill) awbService.findOneByProperties(WorkBill.class, "workbillId", workbillid);
				try {
					if (workbill == null) {
						JSONUtil.responseJSON(response, new AjaxResult(0, "未入住!"));
					} else {
						if ("2".equalsIgnoreCase(workbill.getStatus())) {
							JSONUtil.responseJSON(response, new AjaxResult(1, "当前已结算!"));
						} else {
							JSONUtil.responseJSON(response, new AjaxResult(0, "未结算!"));
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		 
		 @RequestMapping("/apartmentisCheckout.do")
			public void isCheckout(HttpServletRequest request, HttpServletResponse response, String checkid) {
				Check check = (Check) awbService.findOneByProperties(Check.class, "checkId", checkid);
				try {
					if (check == null) {
						JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, "未入住!"));
					} else {
						if (CommonConstants.CheckStatus.CheckLeave.equalsIgnoreCase(check.getStatus()) 
								|| CommonConstants.CheckStatus.CheckOff.equalsIgnoreCase(check.getStatus())) {
							JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.SUCESS, "当前已结算!"));
						} else {
							JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, "未结算!"));
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		 
		 @RequestMapping("/apartmentisCustomout.do")
			public void apartmentisCustomout(HttpServletRequest request, HttpServletResponse response, String checkid) {
				Check check = (Check) awbService.findOneByProperties(Check.class, "checkId", checkid);
				try {
					if (check == null) {
						JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, "未入住!"));
					} else {
						if (CommonConstants.CheckStatus.CheckOffAndUnout.equalsIgnoreCase(check.getStatus())) {
							JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.SUCESS, "已退未结!"));
						} else {
							JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, "未结算!"));
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
}
