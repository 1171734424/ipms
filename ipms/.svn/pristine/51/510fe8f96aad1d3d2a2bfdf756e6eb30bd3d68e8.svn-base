package com.ideassoft.hotel.controller;

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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ideassoft.bean.Branch;
import com.ideassoft.bean.OffsetLog;
import com.ideassoft.bean.Staff;
import com.ideassoft.bean.WorkBill;
import com.ideassoft.bean.WorkBillDetail;
import com.ideassoft.bean.wrapper.MultiQueryWorkbill;
import com.ideassoft.core.ajax.AjaxResult;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.constants.CommonConstants;
import com.ideassoft.core.jdbc.SqlBuilder;
import com.ideassoft.core.page.Pagination;
import com.ideassoft.pms.service.WorkService;
import com.ideassoft.util.DateUtil;
import com.ideassoft.util.JSONUtil;
import com.ideassoft.util.RequestUtil;

@Transactional
@Controller
public class HotelWorkController {

	@Autowired
	private WorkService workservice;
	
	@RequestMapping("/showworkaccountdetail.do")
	public ModelAndView showworkaccountdetail(MultiQueryWorkbill multiqueryworkbill, HttpServletRequest request,
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
		List<?> list = workservice.findBySQLWithPagination(sql, new String[] { multiqueryworkbill.getWorkbillid(),
				branchId, multiqueryworkbill.getStatus(), multiqueryworkbill.getCurrentdate(),
				multiqueryworkbill.getName(), multiqueryworkbill.getRecorduser() }, pagination, true);
		ModelAndView mv = new ModelAndView();
		mv.addObject("list", list);
		mv.addObject("pagination", pagination);
		mv.addObject("multiqueryworkbill", multiqueryworkbill);
		mv.setViewName("/page/ipmshotel/work_account/workaccountData");
		return mv;
	}
	
	@RequestMapping("/consumptionworkbill.do")
	public void consumptionworkbill(HttpServletRequest request, HttpServletResponse response, String strdetailid,
			String offset) {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String[] arrdetail = strdetailid.split(",");
		boolean errorbillflag = true;
		for (String detailid : arrdetail) {
			WorkBillDetail workbilldetail = (WorkBillDetail) workservice.findOneByProperties(WorkBillDetail.class,
					"detailId", detailid);
			String projectid = workbilldetail.getProjectId();
			if (projectid.startsWith("2")) {
				double price = workbilldetail.getPay();
				if (price == 0) {
					errorbillflag = false;
				}
			} else {
				double price = workbilldetail.getCost();
				if (price == 0) {
					errorbillflag = false;
				}
			}
		}

		if (!errorbillflag) {
			JSONUtil.responseJSON(response, new AjaxResult(0, "选中了错误的冲减金额!"));
			return;
		}
		for (String detailid : arrdetail) {
			WorkBillDetail consumedetail = new WorkBillDetail();
			WorkBillDetail workbilldetail = (WorkBillDetail) workservice.findOneByProperties(WorkBillDetail.class,
					"detailId", detailid);
			String detailId = DateUtil.currentDate("yyMMdd") + staff.getBranchId()
					+ workservice.getSequence("SEQ_NEW_WORKBILLDETAIL");
			consumedetail.setDetailId(detailId);
			consumedetail.setBranchId(staff.getBranchId());
			consumedetail.setWorkbillId(workbilldetail.getWorkbillId());
			consumedetail.setDescribe(workbilldetail.getDescribe());
			consumedetail.setOffset(offset);
			consumedetail.setPayment(workbilldetail.getPayment());
			consumedetail.setProjectId(workbilldetail.getProjectId());
			consumedetail.setProjectName(workbilldetail.getProjectName());
			consumedetail.setRecordUser(staff.getStaffId());
			consumedetail.setRemark(workbilldetail.getDetailId());
			consumedetail.setStatus("2");
			//consumedetail.setShift(loginGetShift.getShiftId());
			//consumedetail.setCashBox(loginGetShift.getCashbox());

			String projectid = workbilldetail.getProjectId();

			OffsetLog otlog = new OffsetLog();
			String logid = DateUtil.currentDate("yyMMdd") + staff.getBranchId()
					+ workservice.getSequence("SEQ_OFFSETLOG_ID");
			otlog.setLogId(logid);
			otlog.setBranchId(staff.getBranchId());
			otlog.setCheckId(workbilldetail.getWorkbillId());
			otlog.setLastBillId(workbilldetail.getDetailId());
			otlog.setCurrBillId(consumedetail.getDetailId());
			otlog.setRecordUser(staff.getStaffId());
			otlog.setRecordTime(new Date());
			otlog.setOffsetType("2");
			otlog.setRemark(offset);
			if (projectid.startsWith("2")) {
				double price = workbilldetail.getPay();
				if (price == 0) {
					JSONUtil.responseJSON(response, new AjaxResult(0, "选中了错误的冲减金额!"));
					continue;
				}
				consumedetail.setPay(-price);
				consumedetail.setCost(0.00);
				otlog.setOffsetFee(price);
			} else {
				double price = workbilldetail.getCost();
				if (price == 0) {
					JSONUtil.responseJSON(response, new AjaxResult(0, "选中了错误的冲减金额!"));
					continue;
				}
				consumedetail.setCost(-price);
				consumedetail.setPay(0.00);
				otlog.setOffsetFee(price);
			}
			workbilldetail.setStatus("2");

			try {
				workservice.save(consumedetail);
				workservice.save(workbilldetail);
				workservice.save(otlog);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		JSONUtil.responseJSON(response, new AjaxResult(1, "冲减成功!"));
	}
	
	@RequestMapping("/loadWorkBillData.do")
	public void loadWorkBillData(HttpServletRequest request, HttpServletResponse response, String workbillid,
			String status) {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchid = loginuser.getStaff().getBranchId();
		String sql = "loadworkbilldetail";
		List<?> list = workservice.findBySQL(sql, new String[] { status, workbillid, branchid }, true);
		JSONUtil.responseJSON(response, list);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/loadpayandcostworkbill.do")
	public void loadpayandcostworkbill(HttpServletRequest request, HttpServletResponse response, String workbillid) {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String branchId = staff.getBranchId();
		String sqlcost = "totalcostfromwork";
		List<Map<String, BigDecimal>> costresult = workservice.findBySQL(sqlcost,
				new String[] { workbillid, branchId }, true);
		String sqlpay = "totalpayfromwork";
		List<Map<String, BigDecimal>> payresult = workservice.findBySQL(sqlpay, new String[] { workbillid, branchId },
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
	
//	@RequestMapping("/showgetAddWorkBill.do")
//	public ModelAndView showgetAddWorkBill(HttpServletRequest request) {
//		String workbillid = request.getParameter("workbillid");
//		ModelAndView mv = new ModelAndView();
//		mv.addObject("workbillid", workbillid);
//		mv.setViewName("/page/ipmshotel/work_account/add_workbill");
//		return mv;
//	}

	//工作帐中冲减的判断情况
	@RequestMapping("/checkAllRights.do")
	public void checkAllRights(String strdetailid, HttpServletRequest request, HttpServletResponse response) {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		String staffId = loginUser.getStaff().getStaffId();
		String[] strdetailIds = strdetailid.split(",");
		Branch branchData = (Branch) workservice.findOneByProperties(Branch.class, "branchId", branchId, "status", "1");
		Staff staffData = (Staff) workservice.findOneByProperties(Staff.class, "staffId", staffId, "status", "1");
		int flag = 1;
		String message = "";
		
		for (String strDetailId : strdetailIds) {
			WorkBillDetail workBillDetail = (WorkBillDetail) workservice.findOneByProperties(WorkBillDetail.class, "detailId", strDetailId, "status", "1");
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
	
	@RequestMapping("/checkoutworkbill.do")
	public void checkoutworkbill(HttpServletRequest request, HttpServletResponse response, String workbillid) {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		// String branchId = staff.getStaffId();
		String recordUser = staff.getStaffId();
		WorkBill workbill = (WorkBill) workservice.findOneByProperties(WorkBill.class, "workbillId", workbillid);
		workbill.setFinalTime(new Date());
		workbill.setFinalUser(recordUser);
		workbill.setStatus("2");
		try {
			workservice.save(workbill);
			JSONUtil.responseJSON(response, new AjaxResult(1, "结账成功!"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	/**
//	 * 查看公寓民宿工作张备注信息
//	 * @param request
//	 * @param response
//	 */
//	@RequestMapping("/queryWorkbillRemark.do")
//	public void queryWorkbillRemark(HttpServletRequest request,HttpServletResponse response){
//		String detailId = request.getParameter("billId");
//		WorkBillDetail wkbilldetail =  (WorkBillDetail) workservice.findOneByProperties(WorkBillDetail.class, "detailId",detailId);
//		if(wkbilldetail != null){
//			JSONUtil.responseJSON(response, new AjaxResult(2,wkbilldetail.getRemark()));
//		}
//	}
	
	/**
	 * 跳转修改页面
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	 @RequestMapping("/reWriteWKRemark.do")
	public ModelAndView reWriteWKRemarkAp(HttpServletRequest request) throws UnsupportedEncodingException {		
		String workBillId = request.getParameter("billId");
		String text = request.getParameter("text");
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/ipmshotel/work_account/reWriteWKRemark");
		mv.addObject("text",text);
		mv.addObject("workBillId",workBillId);
		return mv;
	}
	 
	/**
	 * 添加工作账单备注
	 * @param request
	 * @param response
	 */
	 @RequestMapping("/addRemarkToworkBillDetail.do")
	public void addRemarkToworkBillDetail(HttpServletRequest request,HttpServletResponse response){
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String billId = request.getParameter("billId");
		String remark = request.getParameter("remark");
		WorkBillDetail billDetail = (WorkBillDetail) workservice.findOneByProperties(WorkBillDetail.class, "detailId",billId);
		billDetail.setRemark(remark);
		try {
			workservice.update(billDetail);
			JSONUtil.responseJSON(response, new AjaxResult(1,"修改成功!"));
		} catch (Exception e) {
			e.printStackTrace();
			JSONUtil.responseJSON(response, new AjaxResult(2,"操作异常!"));
		}
	}
	 
	@RequestMapping("/updateworkbill.do")
	public void updateworkbill(String name, String describe, String workbillid, HttpServletRequest request,
			HttpServletResponse response) {
		// WorkBill workbill = (WorkBill)
		// workservice.findOneByProperties(WorkBill.class, "workbillId",
		// workbillid);
		try {
			workservice.updateworkbill(name, describe, workbillid);
			JSONUtil.responseJSON(response, new AjaxResult(1, "修改成功!"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/getworkbillinfo.do")
	public void getworkbillinfo(HttpServletRequest request, HttpServletResponse response, String hostcheckid,
			String workbillid) {
		WorkBill workbill = (WorkBill) workservice.findOneByProperties(WorkBill.class, "workbillId", workbillid);
		Staff recordUser = (Staff) workservice.findOneByProperties(Staff.class, "staffId", workbill.getRecordUser());
		Staff finalUser = (Staff) workservice.findOneByProperties(Staff.class, "staffId", workbill.getFinalUser());
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
	
	@RequestMapping("/getcreatworkbillinfo.do")
	public void getcreatworkbillinfo(HttpServletRequest request, HttpServletResponse response) {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		WorkBill workbill = new WorkBill();
		Calendar calendar = Calendar.getInstance();
		String workbillid = null;// DateUtil.d2s(calendar.getTime(), "yyMMdd") +
									// workservice.getSequence("SEQ_NEW_WORKBILL");
		workbill.setWorkbillId(workbillid);
		workbill.setRecordTime(calendar.getTime());
		workbill.setRecordUser(staff.getStaffId());
		String recordTime = DateUtil.d2s(calendar.getTime(), "yyyy/MM/dd HH:mm");
		String staffName = staff.getStaffName();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("workbill", workbill);
		map.put("recordTime", recordTime);
		map.put("staffName", staffName);
		try {
			JSONUtil.responseJSON(response, JSONUtil.fromObject(map));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/creatworkbill.do")
	public void creatworkbill(WorkBill workbill, HttpServletRequest request, HttpServletResponse response) {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String branchId = staff.getBranchId();
		String recorduser = staff.getStaffId();
		List<WorkBill> listworkbill = workservice.findByProperties(WorkBill.class, "branchId", branchId, "status", CommonConstants.WorkBillStatus.NEW);
		if (listworkbill.size() <= 0) {
			String workbillid = DateUtil.d2s(new Date(), "yyMMdd") + branchId + workservice.getSequence("SEQ_NEW_WORKBILL");
			workbill.setWorkbillId(workbillid);
			workbill.setRecordUser(recorduser);
			workbill.setBranchId(branchId);
			workbill.setStatus(CommonConstants.WorkBillStatus.NEW);
			try {
				workservice.save(workbill);
				JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.SUCESS, "创建成功!"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, "已有未结工作账!"));
		}
	}
	
}
