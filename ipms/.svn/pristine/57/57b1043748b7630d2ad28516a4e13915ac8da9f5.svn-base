package com.ideassoft.pmsinhouse.task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hsqldb.lib.StringUtil;

import com.ideassoft.bean.Bill;
import com.ideassoft.bean.Check;
import com.ideassoft.bean.HouseReFundLog;
import com.ideassoft.bean.Order;
import com.ideassoft.bean.ReFundLog;
import com.ideassoft.bean.RefundDetail;
import com.ideassoft.core.ajax.AjaxResult;
import com.ideassoft.core.constants.SystemConstants;
import com.ideassoft.core.constants.SystemConstants.ProjectName;
import com.ideassoft.core.factory.ServiceFactory;
import com.ideassoft.core.task.ScheduledTask;
import com.ideassoft.pmsinhouse.service.AliPayInHouseService;
import com.ideassoft.pmsinhouse.service.HouseDailyService;
import com.ideassoft.util.DateUtil;
import com.ideassoft.util.JSONUtil;
import com.ideassoft.util.PriceUtil;
import com.ideassoft.wechatrefund.WeChatPayServiceImpl;
import com.ideassoft.wechatrefund.WeChatRefundReqModel;
import com.ideassoft.wechatrefund.WeChatRefundResModel;

public class HouseRefundTask extends ScheduledTask implements ProjectName {

	private final Logger logger = Logger.getLogger(HouseRefundTask.class);

	private static HouseDailyService houseDailyService = null;
	
	private static WeChatPayServiceImpl weChatPayServiceImpl = null;
	
	private static AliPayInHouseService aliPayInHouseService = null;
	
	private static String adminName;

	public HouseRefundTask(String name) {
		super(name);
	}
	
	public void initScheduledData() {
		houseDailyService = (HouseDailyService) ServiceFactory.getService("houseDailyService");
		weChatPayServiceImpl = (WeChatPayServiceImpl) ServiceFactory.getService("weChatPayServiceImpl");
		aliPayInHouseService = (AliPayInHouseService) ServiceFactory.getService("aliPayInHouseService");
		adminName = SystemConstants.User.ADMIN_NAME;
		logger.debug("schedule task [test1111] init...............");
	}

	@SuppressWarnings("unchecked")
	public void run() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			Calendar dayTime = Calendar.getInstance();
			dayTime.add(Calendar.DAY_OF_MONTH, -2);
			List<Order> orders = houseDailyService.findByProperties(Order.class, "status", "6", "theme", "3", "autoRefund", "0");
			for (Order order : orders) {
				Check check = (Check) houseDailyService.findById(Check.class, order.getOrderId());
				if (sdf.format(dayTime.getTime()).equals(sdf.format(order.getCheckoutTime()))) {
					Double cost = 0D;
					Double pay = 0D;
					List<Bill> bills = houseDailyService.findByProperties(Bill.class, "checkId", order.getOrderId());
					for (Bill bill : bills) {
						cost += bill.getCost();
						pay += bill.getPay();
					}
					//RefundDetail refundDetail = (RefundDetail) houseDailyService.findOneByProperties(RefundDetail.class, "orderId", order.getOrderId(), "refundType", "");
					Double money = (pay - cost) * 100;
					if(money > 0){
						RefundDetail refundDetail = null;
						if(order.getGuarantee().equals("2")){
							List<Object> refundDetails =  houseDailyService.findBySQL("findRefundDetail", new Object[]{order.getOrderId()}, true);
							if(refundDetails.size() > 0){
								for(int i = 0; i < refundDetails.size(); i++){
									
									refundDetail = new RefundDetail();
									refundDetail.setRefundId(((Map<String, String>)refundDetails.get(i)).get("REFUND_ID").toString());
									refundDetail.setOrderId(((Map<String, String>)refundDetails.get(i)).get("ORDER_ID").toString());
									refundDetail.setBussinessId(((Map<String, String>)refundDetails.get(i)).get("BUSSINESS_ID").toString());
									refundDetail.setTradeId(((Map<String, String>)refundDetails.get(i)).get("TRADE_ID").toString());
									refundDetail.setRefundMoney(Double.parseDouble(((Map<Object, Object>)refundDetails.get(i)).get("REFUND_MONEY").toString()));
									refundDetail.setStatus(((Map<String, String>)refundDetails.get(i)).get("STATUS").toString());
									refundDetail.setSource(((Map<String, String>)refundDetails.get(i)).get("SOURCE").toString());
									
									if(refundDetail.getStatus().equals("1")){
										Bill bill = null;
										HouseReFundLog refundLog = null;
										
										boolean isSuccess = false;
										boolean isAllReFund = false;
										
										Double reFundMoney = 0.0;
										
										String billId = DateUtil.currentDate("yyMMdd") + order.getBranchId() + houseDailyService.getSequence("SEQ_NEW_BILL");
										String refundLogId = DateUtil.currentDate("yyMMdd") + order.getBranchId() + houseDailyService.getSequence("SEQ_NEW_REFUNDLOG");
										
										if(refundDetail.getSource().equals("7")){
											WeChatRefundResModel wechatRefund = null;
											String Smoney = String.format("%.2f", money);
											Smoney = Smoney.substring(0, Smoney.indexOf("."));
											String Refund = String.valueOf(refundDetail.getRefundMoney() * 100);
											Refund = Refund.substring(0, Refund.indexOf("."));
											WeChatRefundReqModel a = new WeChatRefundReqModel();
											a.setOutTradeNo(refundDetail.getBussinessId().trim());
											a.setTotalFee(Integer.parseInt(Refund));
											if(Integer.parseInt(Smoney) <= Integer.parseInt(Refund)){
												a.setRefundFee(Integer.parseInt(Smoney));
												isAllReFund = true;
												reFundMoney = PriceUtil.doubleToPriceDouble(money / 100);
											}else{
												a.setRefundFee(Integer.parseInt(Refund));
												money = money - Double.parseDouble(Refund);
												reFundMoney = refundDetail.getRefundMoney();
											}
											wechatRefund = weChatPayServiceImpl.weChatRefund(a);
											if(!StringUtil.isEmpty(wechatRefund.getErrCode()) && wechatRefund.getErrCode().equals("NOTENOUGH")){
												String remark = "账户余额不足!";
												refundLog = weChatPayServiceImpl.saveReFundLog(refundLogId, order, adminName, 
														remark, refundDetail.getSource(), refundDetail.getBussinessId(),
														String.valueOf(refundDetail.getRefundMoney()), String.valueOf(reFundMoney));
												
											}else if(wechatRefund.getResultCode().equals("SUCCESS")){
												String remark = "已退款!";
												isSuccess = true;
												bill = weChatPayServiceImpl.saveRefundBill(billId, order, reFundMoney, adminName);
												refundLog = weChatPayServiceImpl.saveReFundLog(refundLogId, order, adminName, 
														remark, refundDetail.getSource(), refundDetail.getBussinessId(),
														String.valueOf(refundDetail.getRefundMoney()), String.valueOf(reFundMoney));
												
												refundDetail.setStatus("0");
												refundDetail.setRecordTime(new Date());
												houseDailyService.update(refundDetail);
												houseDailyService.save(bill);
											}
										}
										if(refundDetail.getSource().equals("1")){
											int aliRefund = 0;
											String Refund = String.valueOf(refundDetail.getRefundMoney() * 100);
											Refund = Refund.substring(0, Refund.indexOf("."));
											
											if(money <= Double.parseDouble(Refund)){
												aliRefund = aliPayInHouseService.alipayReFund(refundDetail.getBussinessId(), refundDetail.getTradeId(), String.valueOf(money / 100));
												isAllReFund = true;
												reFundMoney = PriceUtil.doubleToPriceDouble(money / 100);
											}else{
												aliRefund = aliPayInHouseService.alipayReFund(refundDetail.getBussinessId(), refundDetail.getTradeId(), String.valueOf(refundDetail.getRefundMoney()));
												money = money - Double.parseDouble(Refund);
												reFundMoney = refundDetail.getRefundMoney();
											}
											if(aliRefund == 1){
												String remark = "已退款!";
												isSuccess = true;
												bill = weChatPayServiceImpl.saveRefundBill(billId, order, reFundMoney, adminName);
												refundLog = weChatPayServiceImpl.saveReFundLog(refundLogId, order, adminName, 
														remark, refundDetail.getSource(), refundDetail.getBussinessId(),
														String.valueOf(refundDetail.getRefundMoney()), String.valueOf(reFundMoney));
												
												refundDetail.setStatus("0");
												refundDetail.setRecordTime(new Date());
												houseDailyService.update(refundDetail);
												houseDailyService.save(bill);
											}else{
												String remark = "退款失败!";
												refundLog = weChatPayServiceImpl.saveReFundLog(refundLogId, order, adminName, 
														remark, refundDetail.getSource(), refundDetail.getBussinessId(),
														String.valueOf(refundDetail.getRefundMoney()), String.valueOf(reFundMoney));
											}
											houseDailyService.save(refundLog);
										}
										if(isSuccess){
											if(isAllReFund){
												if(check != null){
													check.setStatus("4");
													check.setRecordTime(new Date());
													houseDailyService.update(check);
												}
												order.setStatus("4");
												order.setRecordTime(new Date());
												houseDailyService.update(order);
											}else{
												continue;
											}
										}
									}
								}
							}
						}
					}else if(money == 0){
						if(check != null){
							check.setStatus("4");
							check.setRecordTime(new Date());
							houseDailyService.update(check);
						}
						order.setStatus("4");
						order.setRecordTime(new Date());
						houseDailyService.update(order);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}