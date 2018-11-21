package com.ideassoft.apartment.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ideassoft.apartment.service.ApartmentAliPayServiceImpl;
import com.ideassoft.apartment.service.ApartmentOrderService;
import com.ideassoft.apartment.service.ApartmentWeChatPayServiceImpl;
import com.ideassoft.bean.Aptorder;
import com.ideassoft.bean.AptorderDetail;
import com.ideassoft.bean.Bill;
import com.ideassoft.bean.Contrart;
import com.ideassoft.bean.RefundDetail;
import com.ideassoft.bean.RepairApply;
import com.ideassoft.bean.Room;
import com.ideassoft.bean.RoomStatus;
import com.ideassoft.core.ajax.AjaxResult;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.constants.CommonConstants;
import com.ideassoft.core.jdbc.SqlBuilder;
import com.ideassoft.core.page.Pagination;
import com.ideassoft.util.DateUtil;
import com.ideassoft.util.JSONUtil;
import com.ideassoft.util.RequestUtil;
import com.ideassoft.wechatrefund.WeChatRefundReqModel;

@Transactional
@Controller
public class ApartmentOrderController {

	@Autowired
	private ApartmentOrderService apartmentOrderService;
	
	@Autowired
	private ApartmentWeChatPayServiceImpl weChatPayServiceImpl;
	
	@Autowired
	private ApartmentAliPayServiceImpl aliPayServiceImpl;
	
	@RequestMapping("ApartmentOrderAll.do")
	public ModelAndView apartmentOrderAll(HttpServletRequest request,HttpServletResponse response) throws Exception {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		ModelAndView mv = new ModelAndView();
		String arrivaltime = request.getParameter("arrivaltime");
		String memberName = request.getParameter("memberName");
		String mobile = request.getParameter("mobile");
		Pagination pagination = SqlBuilder.buildPagination(request);
		List<?> list = apartmentOrderService.findBySQLWithPagination("queryAptOrder", new String[] { loginUser.getStaff().getBranchId(), "", memberName, mobile, arrivaltime }, pagination, true);
		mv.addObject("list", list);
		mv.addObject("pagination", pagination);
		mv.addObject("memberName", memberName);
		mv.addObject("mobile", mobile);
		mv.addObject("action", "ApartmentOrderAll.do");
		mv.setViewName("page/apartment/apartmentmainmenu/apartmentorder/apartmentorderall");
		return mv;
	}
	
	@RequestMapping("ApartmentNewOrder.do")
	public ModelAndView apartmentNewOrder(HttpServletRequest request,HttpServletResponse response) throws Exception {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		ModelAndView mv = new ModelAndView();
		String arrivaltime = request.getParameter("arrivaltime");
		String memberName = request.getParameter("memberName");
		String mobile = request.getParameter("mobile");
		Pagination pagination = SqlBuilder.buildPagination(request);
		List<?> list = apartmentOrderService.findBySQLWithPagination("queryAptOrderTwo", new String[] { loginUser.getStaff().getBranchId(), memberName, mobile, arrivaltime }, pagination, true);
		mv.addObject("list", list);
		mv.addObject("pagination", pagination);
		mv.addObject("memberName", memberName);
		mv.addObject("mobile", mobile);
		mv.addObject("action", "ApartmentNewOrder.do");
		mv.setViewName("page/apartment/apartmentmainmenu/apartmentorder/apartmentorderall");
		return mv;
	}
	
	@RequestMapping("ApartmentOrderCancel.do")
	public ModelAndView apartmentOrderCancel(HttpServletRequest request,HttpServletResponse response) throws Exception {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		ModelAndView mv = new ModelAndView();
		String arrivaltime = request.getParameter("arrivaltime");
		String memberName = request.getParameter("memberName");
		String mobile = request.getParameter("mobile");
		Pagination pagination = SqlBuilder.buildPagination(request);
		List<?> list = apartmentOrderService.findBySQLWithPagination("queryAptOrder", new String[] { loginUser.getStaff().getBranchId(), "0", memberName, mobile, arrivaltime }, pagination, true);
		mv.addObject("list", list);
		mv.addObject("pagination", pagination);
		mv.addObject("memberName", memberName);
		mv.addObject("mobile", mobile);
		mv.addObject("action", "ApartmentOrderCancel.do");
		mv.setViewName("page/apartment/apartmentmainmenu/apartmentorder/apartmentorderall");
		return mv;
	}
	
	@RequestMapping("lookRoomWithNobody.do")
	public ModelAndView lookRoomWithNobody(HttpServletRequest request,HttpServletResponse response) throws Exception {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		ModelAndView mv = new ModelAndView();
		String arrivaltime = request.getParameter("arrivaltime");
		String memberName = request.getParameter("memberName");
		String mobile = request.getParameter("mobile");
		Pagination pagination = SqlBuilder.buildPagination(request);
		List<?> list = apartmentOrderService.findBySQLWithPagination("queryAptOrder", new String[] { loginUser.getStaff().getBranchId(), "1", memberName, mobile, arrivaltime }, pagination, true);
		mv.addObject("list", list);
		mv.addObject("pagination", pagination);
		mv.addObject("memberName", memberName);
		mv.addObject("mobile", mobile);
		mv.addObject("action", "lookRoomWithNobody.do");
		mv.addObject("type","3");
		mv.setViewName("page/apartment/apartmentmainmenu/apartmentorder/apartmentorderall");
		return mv;
	}
	
	@RequestMapping("/aptOrderReFund.do")
	public void aptOrderReFund(HttpServletRequest request, HttpServletResponse response, String aptOrderId) {
		Aptorder aptOrder = (Aptorder) apartmentOrderService.findById(Aptorder.class, aptOrderId);
		aptOrder.setReFundTime(new Date());
		aptOrder.setStatus(CommonConstants.OrderStatus.Cancel);
		try{
			RefundDetail refundDetail = (RefundDetail) apartmentOrderService.findOneByProperties(RefundDetail.class, "orderId", aptOrderId, "status", "1");
			
			if(refundDetail != null && refundDetail.getStatus().equals("1")){
//				Bill bill = (Bill) apartmentOrderService.findOneByProperties(Bill.class, "branchId", aptOrder.getBranchId(), "checkId", aptOrder.getOrderId() ,"projectId", "2004");
				if(refundDetail.getSource().equals("7")){
					String Refund = String.valueOf(refundDetail.getRefundMoney() * 100);
					Refund = Refund.substring(0, Refund.indexOf("."));
//					String money = String.valueOf(bill.getPay() * 100);
//					money = money.substring(0, money.indexOf("."));
					WeChatRefundReqModel a = new WeChatRefundReqModel();
					a.setOutTradeNo(refundDetail.getBussinessId().trim());
					a.setTotalFee(Integer.parseInt(Refund));
					a.setRefundFee(Integer.parseInt(Refund));
					weChatPayServiceImpl.weChatRefund(a);
				}
				if(refundDetail.getSource().equals("1")){
					aliPayServiceImpl.alipayReFund(refundDetail.getBussinessId(), refundDetail.getTradeId(), String.valueOf(refundDetail.getRefundMoney()));
				}
				refundDetail.setStatus("0");
				apartmentOrderService.update(refundDetail);
			}
			// 查询此房间是存在维修或停售
			List<RepairApply> repairApplys = apartmentOrderService.findByProperties(RepairApply.class, "branchId", aptOrder.getBranchId(), "roomId", aptOrder.getRoomId());
			// 查询根据门店号、房间号、状态为待入住订单的Aptorder表
			List<Aptorder>  aptorderList = apartmentOrderService.findByProperties(Aptorder.class, "branchId", aptOrder.getBranchId(), "roomId", aptOrder.getRoomId(), "status", "6");
			// 设置初始化状态为空房，再查询是否是停售房或维修房，判断完毕后更新房间状态
			String status = "1";
			if(repairApplys.size() > 0){
				for(RepairApply repairApply : repairApplys){
					if(repairApply.getStatus().equals("2") || repairApply.getStatus().equals("3")){
						status = "W";
					}
				}
			}
			// 此时还需要判断是否为维修房，如果不为维修房，则将状态值修改为"2"待入住状态
			if (aptorderList.size() > 0 && !"W".equals(status)) {
				status = "2";
			}
			apartmentOrderService.upateroomstatus(aptOrder.getBranchId(),aptOrder.getRoomId(), status);
			apartmentOrderService.update(aptOrder);
			JSONUtil.responseJSON(response, new AjaxResult(1, "退款成功!"));
		}catch (Exception e) {
			e.printStackTrace();
			JSONUtil.responseJSON(response, new AjaxResult(2, "退款失败!"));
		}
	}
	
	
	
	@RequestMapping("/aptOrderReFundTwo.do")
	public void aptOrderReFundTwo(HttpServletRequest request, HttpServletResponse response, String aptOrderId) {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Aptorder aptOrder = (Aptorder) apartmentOrderService.findById(Aptorder.class, aptOrderId);
		aptOrder.setReFundTime(new Date());
		aptOrder.setStatus(CommonConstants.OrderStatus.Cancel);
		
		AptorderDetail aptorderDetail  = (AptorderDetail) apartmentOrderService.findOneByProperties(AptorderDetail.class, "aptorderId", aptOrderId);
		
		Contrart contrart = (Contrart) apartmentOrderService.findOneByProperties(Contrart.class, "contrartId", aptorderDetail.getContrartId());
		contrart.setStatus(CommonConstants.OrderStatus.Cancel);
		contrart.setRecordTime(new Date());
		
		@SuppressWarnings("unchecked")
		List<Bill> billList = apartmentOrderService.findByProperties(Bill.class, "checkId", contrart.getContrartId(), "status","1");
		double deposit = 0.0;
		double totalPay = 0.0;
		double totalCost = 0.0;
		double totalRoomPrice = 0.0;
		for(int i = 0;i<billList.size();i++){
			Bill bill = billList.get(i);
			if("2008".equals(bill.getProjectId())){
				deposit += bill.getPay();
			}
			totalPay += bill.getPay();
			totalCost += bill.getCost();
		}
		totalRoomPrice = 0.0-((totalPay - totalCost) - deposit);//账面余额-押金总额 = 可退房费,要是负的哦
		
		Bill refundBill = new Bill();
		String billId = DateUtil.currentDate("yyMMdd") + contrart.getBranchId() + "3" + apartmentOrderService.getSequence("SEQ_NEW_BILL");
		refundBill.setBillId(billId);
		refundBill.setBranchId(contrart.getBranchId());
		refundBill.setCheckId(contrart.getContrartId());
		refundBill.setProjectId("2001");
		refundBill.setProjectName("现金支出");
		refundBill.setDescribe("结算");
		refundBill.setCost(0.0);
		refundBill.setPayment("1");
		refundBill.setPay(totalRoomPrice);
		refundBill.setStatus("1");
		refundBill.setRecordTime(new Date());
		refundBill.setRecordUser(loginUser.getStaff().getStaffId());
		apartmentOrderService.save(refundBill);
		
		Bill depositBill = new Bill();
		String depositBillId = DateUtil.currentDate("yyMMdd") + contrart.getBranchId() + "3" + apartmentOrderService.getSequence("SEQ_NEW_BILL");
		depositBill.setBillId(depositBillId);
		depositBill.setBranchId(contrart.getBranchId());
		depositBill.setCheckId(contrart.getContrartId());
		depositBill.setProjectId("1001");
		depositBill.setProjectName("赔偿");
		depositBill.setDescribe("赔偿");
		depositBill.setCost(deposit);
		depositBill.setPayment("1");
		depositBill.setPay(0.0);
		depositBill.setStatus("1");
		depositBill.setRecordTime(new Date());
		depositBill.setRecordUser(loginUser.getStaff().getStaffId());
		apartmentOrderService.save(depositBill);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		//只需看该房间当前状态是否为预订，如果为预定，就看今天和该合同的开始时间之内是否有预订，没有就改为空房
		Room room = ((Room) (apartmentOrderService.findOneByProperties(Room.class, "roomKey.branchId",aptOrder.getBranchId(),"roomKey.roomId",aptOrder.getRoomId())));
		if("2".equals(room.getStatus())){
			List<?> order = apartmentOrderService.queryAptOrder(sdf.format(contrart.getStartTime()),aptOrder.getBranchId(),aptOrder.getRoomId());
			if(order.size() == 0){
				room.setStatus("1");
				apartmentOrderService.update(room);
			}
		}
		apartmentOrderService.update(aptOrder);
		apartmentOrderService.update(contrart);
		JSONUtil.responseJSON(response, new AjaxResult(1, "退款成功!"));
		
	}
}
