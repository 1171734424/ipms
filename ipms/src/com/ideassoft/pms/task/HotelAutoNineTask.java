package com.ideassoft.pms.task;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.ideassoft.bean.Check;
import com.ideassoft.bean.CheckUser;
import com.ideassoft.bean.Member;
import com.ideassoft.bean.OperateLog;
import com.ideassoft.bean.Order;
import com.ideassoft.bean.RepairApply;
import com.ideassoft.bean.RoomStatus;
import com.ideassoft.core.constants.CommonConstants;
import com.ideassoft.core.constants.SystemConstants;
import com.ideassoft.core.constants.SystemConstants.ProjectName;
import com.ideassoft.core.factory.ServiceFactory;
import com.ideassoft.core.task.ScheduledTask;
import com.ideassoft.pms.service.DailyService;
import com.ideassoft.pms.service.RoomService;
import com.ideassoft.util.DateUtil;

public class HotelAutoNineTask extends ScheduledTask implements ProjectName {

	private final Logger logger = Logger.getLogger(HotelAutoNineTask.class);

	private static DailyService dailyService = null;
	
	private static RoomService roomService = null;
	
	private static String adminName;

	public HotelAutoNineTask(String name) {
		super(name);
	}
	
	public void initScheduledData() {
		dailyService = (DailyService) ServiceFactory.getService("dailyService");
		roomService = (RoomService) ServiceFactory.getService("roomService");
		adminName = SystemConstants.User.ADMIN_NAME;
		logger.debug("schedule task [test1111] init...............");
	}

	@SuppressWarnings("unchecked")
	public void run() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH");
			SimpleDateFormat sdff = new SimpleDateFormat("yyyy/MM/dd");
			Date date = new Date();
			List<Order> orders = dailyService.findByProperties(Order.class, "theme", "1", "status", "1");
			for (Order order : orders) {
				String limited = sdff.format(order.getArrivalTime()) + " " + order.getLimited().split(":")[0];
				if(sdf.format(date).equals(limited) && order.getGuarantee().equals(CommonConstants.Guarantee.SECURED)){
					Double a = 0.00;
					Check check = new Check();
					check.setCheckId(order.getOrderId());
					check.setBranchId(order.getBranchId());
					check.setCheckinTime(date);
					check.setRoomPrice(order.getRoomPrice());
					check.setRpId(order.getRpId());
					check.setCheckoutTime(order.getLeaveTime());
					check.setCheckUser(order.getOrderUser());
					check.setRoomId(order.getRoomId());
					check.setRoomType(order.getRoomType());
					check.setDeposit(a);
					check.setTtv(a);
					check.setCost(a);
					Double advanceCash = order.getAdvanceCash() != null ? order.getAdvanceCash() : 0.0;
					Double advanceCard = order.getAdvanceCard() != null ? order.getAdvanceCard() : 0.0;
					check.setPay(advanceCash + advanceCard);
					check.setAccountFee(a);
					check.setTotalFee(a);
					check.setStatus("1");
					check.setRecordTime(date);
					check.setRecordUser(adminName);
					check.setRemark(order.getRemark());
					check.setMsj(order.getMsj());
					
					OperateLog operlog = new OperateLog();
					operlog.setBranchId(order.getBranchId());
					operlog.setLogId(DateUtil.currentDate("yyMMdd") + order.getBranchId() + dailyService.getSequence("SEQ_OPERATELOG_ID"));
					String operid = InetAddress.getLocalHost().toString();// IP地址
					operid = (String) operid.subSequence(operid.indexOf("/") + 1, operid.length());
					operlog.setOperIp(operid);
					operlog.setOperType("5");
					operlog.setOperModule("入住操作(酒店)");
					operlog.setContent("订单号" + order + "已经入住");
					operlog.setRecordUser(adminName);
					operlog.setRecordTime(date);
					
					List<CheckUser> checkUsers = dailyService.findByProperties(CheckUser.class, "checkId", order.getOrderId(), "checkinType", "1");
					for(CheckUser checkUser : checkUsers){
						CheckUser newCheckUser = new CheckUser();
						newCheckUser.setCheckuserId(order.getBranchId() + "3" + dailyService.getSequence("SEQ_NEW_CHECKUSER"));
						newCheckUser.setCheckuserName(checkUser.getCheckuserName());
						newCheckUser.setIdcard(checkUser.getIdcard());
						newCheckUser.setGender(checkUser.getGender());
						newCheckUser.setMobile(checkUser.getMobile());
						newCheckUser.setStatus(checkUser.getStatus());
						newCheckUser.setRemark(checkUser.getRemark());
						newCheckUser.setRecordUser(adminName);
						newCheckUser.setRecordTime(date);
						newCheckUser.setCheckId(checkUser.getCheckId());
						newCheckUser.setCheckuserType("1");
						newCheckUser.setRankName(checkUser.getRankName());
						newCheckUser.setAddress(checkUser.getAddress());
						newCheckUser.setCheckinType("2");
						dailyService.save(newCheckUser);
					}
					
					List<RepairApply> repairApplys = dailyService.findByProperties(RepairApply.class, "branchId", order.getBranchId(), "roomId", order.getRoomId());
					String status = "3";
					if(repairApplys.size() > 0){
						for(RepairApply repairApply : repairApplys){
							if(repairApply.getStatus().equals("2") || repairApply.getStatus().equals("3")){
								status = "w";
							}
						}
					}
					
					order.setStatus("3");
					dailyService.update(order);
					dailyService.save(check);
					dailyService.save(operlog);
					roomService.upateroomstatus(order.getBranchId(), order.getRoomId(), status);
				} else if (sdf.format(date).equals(limited) && 
						order.getGuarantee().equals(CommonConstants.Guarantee.UNSECURED)) {
					order.setStatus(CommonConstants.OrderStatus.Absent);
					dailyService.update(order);
					//roomService.upateroomstatus(order.getBranchId(), order.getRoomId(), CommonConstants.OrderStatus.Absent);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}