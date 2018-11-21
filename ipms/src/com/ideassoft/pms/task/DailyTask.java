package com.ideassoft.pms.task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hsqldb.lib.StringUtil;

import com.ideassoft.bean.Bill;
import com.ideassoft.bean.Branch;
import com.ideassoft.bean.Check;
import com.ideassoft.bean.MemberAccount;
import com.ideassoft.bean.OrdchePrice;
import com.ideassoft.bean.Order;
import com.ideassoft.bean.RoomStatus;
import com.ideassoft.core.constants.CommonConstants;
import com.ideassoft.core.constants.SystemConstants.ProjectName;
import com.ideassoft.core.factory.ServiceFactory;
import com.ideassoft.core.task.ScheduledTask;
import com.ideassoft.pms.service.DailyService;

public class DailyTask extends ScheduledTask implements ProjectName {

	private final Logger logger = Logger.getLogger(DailyTask.class);

	private static DailyService dailyService = null;

	public DailyTask(String name) {
		super(name);
	}
	
	public void initScheduledData() {
		dailyService = (DailyService) ServiceFactory.getService("dailyService");
		logger.debug("schedule task [test1111] init...............");
	}

	@SuppressWarnings("unchecked")
	public void run() {
		try {
			// 查询在住的房单，添加一天房费,日结房费添加到账单表
			List<Check> checks = dailyService.findByProperties(Check.class, "status", "1");
			
			if (null != checks) {
				for (Check check : checks) {
					Order order = (Order) dailyService.findById(Order.class, check.getCheckId());
					Branch branch = (Branch) dailyService.findById(Branch.class, check.getBranchId());
					if(branch != null){
						if (StringUtil.isEmpty(branch.getBranchIp())) {
							MemberAccount memberAccount = (MemberAccount) dailyService.findOneByProperties(MemberAccount.class, "memberId", order.getOrderUser());
							if (memberAccount == null) {
								System.out.println(branch.getBranchName());
							}
							Bill bill = new Bill();
							String sequences = dailyService.getSequence("SEQ_NEW_BILL");
							SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
							String strdate = sdf.format(new Date());
							check.setCost(check.getCost() == null ? 0 : check.getCost() + check.getRoomPrice());// 消费金额
							check.setTotalFee(check.getTotalFee() == null ? 0 : check.getTotalFee() + check.getRoomPrice());// 平账金额
							memberAccount.setTotalRoomnights(memberAccount.getTotalRoomnights() + 1);
							memberAccount.setCurrRoomnights(memberAccount.getCurrRoomnights() + 1);
							bill.setBillId(strdate + check.getBranchId() + sequences);
							bill.setBranchId(check.getBranchId());
							bill.setCheckId(check.getCheckId());
							bill.setProjectId("1234");
							bill.setProjectName("房费");
							bill.setDescribe("日结");
							bill.setCost(check.getRoomPrice());
							bill.setPay((double) 0);
							bill.setPayment("1");
							bill.setOffset("");// 可以不写
							bill.setStatus("1");
							bill.setRecordTime(new Date());
							bill.setRecordUser(USER_ID);
							bill.setRemark("");// 可以不写
							if (CommonConstants.CheckStatus.CheckOff.equals(check.getStatus())
									|| CommonConstants.CheckStatus.CheckOffAndUnout.equals(check.getStatus())){
								check.setStatus(CommonConstants.CheckStatus.CheckLeave);
							}
							dailyService.update(memberAccount);
							dailyService.update(check);
							dailyService.save(bill);
						}
					}
					
				}
			}
			
			//将所有离店可撤销的房单状态改为离店不可撤销
			List<?> checkoffs = dailyService.findByProperties(Check.class, "status", CommonConstants.CheckStatus.CheckOff);
			if(checkoffs != null && !checkoffs.isEmpty()){
				try{
					for (Object object : checkoffs) {
						Check checkoff = (Check) object;
						Order order = (Order) dailyService.findOneByProperties(Order.class, "orderId", checkoff.getCheckId());
						if (order != null) {
							order.setStatus(CommonConstants.OrderStatus.CheckOff);
							checkoff.setStatus(CommonConstants.CheckStatus.CheckLeave);
							dailyService.update(checkoff);
							dailyService.update(order);
						}
					}					
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
			
			//将所有已退未结的房单状态改为离店不可撤销
			List<?> checkoffandnouts = dailyService.findByProperties(Check.class, "status", CommonConstants.CheckStatus.CheckOffAndUnout);
			if(checkoffandnouts != null && !checkoffandnouts.isEmpty()){
				try{
					for (Object object : checkoffandnouts) {
						Check checkoffandnout = (Check) object;
						Order order = (Order) dailyService.findOneByProperties(Order.class, "orderId", checkoffandnout.getCheckId());
						if (order != null) {
							order.setStatus(CommonConstants.OrderStatus.CheckOff);
							checkoffandnout.setStatus(CommonConstants.CheckStatus.CheckLeave);
							dailyService.update(checkoffandnout);
							dailyService.update(order);
						}
						
					}
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
			
			//将未到的订单直接取消掉并将roomstatus表中有效订单数量减掉
			List<Order> orderdatas = dailyService.findByProperties(Order.class, "status", CommonConstants.OrderStatus.Absent, 
					"theme", CommonConstants.SystemTheme.HOTEL);
			for (Order orderdata : orderdatas) {
				orderdata.setStatus(CommonConstants.OrderStatus.Cancel);
				dailyService.update(orderdata);
				RoomStatus roomstatus = (RoomStatus) dailyService.findOneByProperties(RoomStatus.class, "branchId", orderdata.getBranchId(), 
							"roomType", orderdata.getRoomType());
				if (!orderdata.getSource().equals(CommonConstants.OrderSource.Branch)) {
					roomstatus.setCount(roomstatus.getCount() + 1);
					roomstatus.setSellnum(roomstatus.getSellnum() + 1);
					//roomstatus.setInvalidnum(roomstatus.getInvalidnum() + 1);
				}
				roomstatus.setValidnum(roomstatus.getValidnum() - 1);
				dailyService.update(roomstatus);
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			Calendar dayTime = Calendar.getInstance();
			dayTime.add(Calendar.DAY_OF_MONTH, -1);
			String time = sdf.format(dayTime.getTime());
			List<OrdchePrice> list = dailyService.findByProperties(OrdchePrice.class, "status", "1");
			for( OrdchePrice ordchePrice : list ){
				if(sdf.format(ordchePrice.getWhichDay()).equals(time)){
					//Order order = (Order) dailyService.findById(Order.class, ordchePrice.getOrderId());
					Check check = (Check) dailyService.findById(Check.class, ordchePrice.getOrderId());
					if (check == null) {
						continue;
					}
					Branch branch = (Branch) dailyService.findById(Branch.class, check.getBranchId());
					if(branch!=null ){
						if (StringUtil.isEmpty(branch.getBranchIp())) {
							check.setRoomPrice(ordchePrice.getDayPrice());
							dailyService.update(check);
						}
					}
					
					//order.setRoomPrice(ordchePrice.getDayPrice());
					//dailyService.update(order);
				}
			}
			
			//日结时，将限时特惠的活动的房间数刷到roomstatus表里
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}