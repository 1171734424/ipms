package com.ideassoft.pmsinhouse.task;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.ideassoft.bean.Bill;
import com.ideassoft.bean.Check;
import com.ideassoft.bean.CheckUser;
import com.ideassoft.bean.House;
import com.ideassoft.bean.OperateLog;
import com.ideassoft.bean.OrdchePrice;
import com.ideassoft.bean.Order;
import com.ideassoft.bean.RepairApply;
import com.ideassoft.core.constants.CommonConstants;
import com.ideassoft.core.constants.SystemConstants;
import com.ideassoft.core.constants.SystemConstants.ProjectName;
import com.ideassoft.core.factory.ServiceFactory;
import com.ideassoft.core.task.ScheduledTask;
import com.ideassoft.pmsinhouse.service.HouseDailyService;
import com.ideassoft.pmsinhouse.service.HouseOrderService;
import com.ideassoft.util.DateUtil;

public class HouseAutoTask extends ScheduledTask implements ProjectName {

	private final Logger logger = Logger.getLogger(HouseAutoTask.class);

	private static HouseDailyService houseDailyService = null;
	
	private static String adminName;

	public HouseAutoTask(String name) {
		super(name);
	}
	
	public void initScheduledData() {
		houseDailyService = (HouseDailyService) ServiceFactory.getService("houseDailyService");
		adminName = SystemConstants.User.ADMIN_NAME;
		logger.debug("schedule task [test1111] init...............");
	}

	@SuppressWarnings("unchecked")
	public void run() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			Date date = new Date();
			List<Order> orders = houseDailyService.findByProperties(Order.class, "theme", CommonConstants.Branch.HOUSEID, "status", CommonConstants.OrderStatus.NewOrder);
			for (Order order : orders) {
				if(sdf.format(date).equals(sdf.format((order.getArrivalTime()))) && order.getGuarantee().equals(CommonConstants.Guarantee.SECURED)){
					House house = (House) houseDailyService.findOneByProperties(House.class, "houseNo", order.getRoomId(),
							"houseId", order.getBranchId());
					
					List<OrdchePrice> list = houseDailyService.findByProperties(OrdchePrice.class, "branchId", order.getBranchId(), "orderId", order.getOrderId(), "status", "1");
					Double roomPrice = 0.0;
					for( OrdchePrice ordchePrice : list ){
						if(sdf.format(ordchePrice.getWhichDay()).equals(sdf.format(date))){
							roomPrice = ordchePrice.getDayPrice();
						}
					}
					Double a = 0.00;
					Check check = new Check();
					check.setCheckId(order.getOrderId());
					check.setBranchId(order.getBranchId());
					check.setCheckinTime(date);
					check.setRoomPrice(roomPrice);
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
					operlog.setLogId(DateUtil.currentDate("yyMMdd") + order.getBranchId() + houseDailyService.getSequence("SEQ_OPERATELOG_ID"));
					String operid = InetAddress.getLocalHost().toString();// IP地址
					operid = (String) operid.subSequence(operid.indexOf("/") + 1, operid.length());
					operlog.setOperIp(operid);
					operlog.setOperType("5");
					operlog.setOperModule("入住操作(民宿)");
					operlog.setContent("订单号" + order.getOrderId() + "已经入住");
					operlog.setRecordUser(adminName);
					operlog.setRecordTime(date);
					
					
					List<?> listcheckuser = houseDailyService.findByProperties(CheckUser.class, 
							"checkId", order.getOrderId(), "checkinType", CommonConstants.CheckinType.ORDER,
							"status", CommonConstants.CheckUserStatus.ON);
					
					
					if(listcheckuser != null && !listcheckuser.isEmpty()){
						for (Object object : listcheckuser) {
							CheckUser oldcheckuser = (CheckUser) object;
							CheckUser newcheckuser = new CheckUser();
							newcheckuser = oldcheckuser.clone();
							String checkuserId = order.getBranchId() + CommonConstants.OrderSource.Branch + 
							houseDailyService.getSequence("SEQ_NEW_CHECKUSER");
							newcheckuser.setCheckuserId(checkuserId);
							newcheckuser.setCheckinType(CommonConstants.CheckinType.CHECK);
							houseDailyService.save(newcheckuser);
						}
					}
					
					
					List<RepairApply> repairApplys = houseDailyService.findByProperties(RepairApply.class, "branchId", order.getBranchId(), "roomId", order.getRoomId());
					String status = "3";
					if(repairApplys.size() > 0){
						for(RepairApply repairApply : repairApplys){
							if(repairApply.getStatus().equals("2") || repairApply.getStatus().equals("3")){
								status = "W";
							}
						}
					}
					
					Bill service = (Bill) houseDailyService.findOneByProperties(Bill.class, "branchId", order.getBranchId(), "checkId", order.getOrderId(),
							"projectId", CommonConstants.BillProject.Service);
					
					Bill cleanPrices = (Bill) houseDailyService.findOneByProperties(Bill.class, "branchId", order.getBranchId(), "checkId", order.getOrderId(),
							"projectId", CommonConstants.BillProject.CLEANPRICE);
					
					houseDailyService.saveCleanServicePrices(cleanPrices, service, order, USER_ID);
					
					house.setStatus(status);
					order.setStatus("3");
					houseDailyService.update(order);
					houseDailyService.save(check);
					houseDailyService.save(operlog);
					houseDailyService.update(house);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}