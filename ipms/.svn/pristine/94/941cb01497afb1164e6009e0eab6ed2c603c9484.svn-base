package com.ideassoft.pmsinhouse.task;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hsqldb.lib.StringUtil;

import com.ideassoft.bean.Check;
import com.ideassoft.bean.CheckUser;
import com.ideassoft.bean.Equipment;
import com.ideassoft.bean.House;
import com.ideassoft.bean.OperateLog;
import com.ideassoft.bean.Order;
import com.ideassoft.bean.RepairApply;
import com.ideassoft.bean.WarningLog;
import com.ideassoft.core.ajax.AjaxResult;
import com.ideassoft.core.constants.CommonConstants;
import com.ideassoft.core.constants.SystemConstants;
import com.ideassoft.core.constants.SystemConstants.ProjectName;
import com.ideassoft.core.factory.ServiceFactory;
import com.ideassoft.core.task.ScheduledTask;
import com.ideassoft.pmsinhouse.service.HouseDailyService;
import com.ideassoft.util.CardUtil;
import com.ideassoft.util.DateUtil;
import com.ideassoft.util.JSONUtil;

public class HouseCheckOutTask extends ScheduledTask implements ProjectName {

	private final Logger logger = Logger.getLogger(HouseCheckOutTask.class);

	private static HouseDailyService houseDailyService = null;
	
	private static String adminName;

	public HouseCheckOutTask(String name) {
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
			//SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Integer failFlagCount = 0;
			String checkUserLis = "";
			String checkUserLisFail = "";
			Integer result = CommonConstants.PortalResultCode.FAILED;
			Date date = new Date();
			List<Order> orders = houseDailyService.findByProperties(Order.class, "theme", "3", "status", "3");
			for (Order order : orders) {
				if(order.getLeaveTime().getTime() <= date.getTime()){
					Check check = (Check) houseDailyService.findById(Check.class, order.getOrderId());
					if(check != null){
						House house = (House) houseDailyService.findOneByProperties(House.class, "houseId", order.getBranchId());
						List<CheckUser> checkUsers = houseDailyService.findByProperties(CheckUser.class, "checkId", order.getOrderId());
						
						for(CheckUser checkUser : checkUsers){
							checkUser.setStatus("0");
							houseDailyService.update(checkUser);
						}
						
						//List<RepairApply> repairApplys = houseDailyService.findByProperties(RepairApply.class, "branchId", order.getBranchId(), "contractId", order.getOrderId());
						List<RepairApply> repairApplys = houseDailyService.findByProperties(RepairApply.class, "branchId", order.getBranchId());
						String status  = "Z";
						if(repairApplys.size() > 0){
							for(RepairApply repairApply : repairApplys){
								if(repairApply.getStatus().equals("2") || repairApply.getStatus().equals("3")){
									status = "W";
								}
							}
						}
						
						house.setStatus(status);
						order.setStatus("6");
						check.setStatus("3");
						check.setCheckoutTime(new Date());
						order.setCheckoutTime(new Date());
						houseDailyService.update(order);
						houseDailyService.update(house);
						houseDailyService.update(check);
						
						//门锁里删除住户身份证信息,而不删除保洁的身份证信息
						//先查询订单里的所有的身份证，不管状态
						List<?> checkUserList = houseDailyService.findByProperties(CheckUser.class, "checkId", check.getCheckId(), "checkinType", "2");
						if(checkUserList != null && checkUserList.size() > 0){
							for (int j = 0; j < checkUserList.size(); j++){
								String idcardUser = ((CheckUser)(checkUserList.get(j))).getIdcard();
								checkUserLis += idcardUser +",";
								if(idcardUser != null && !StringUtil.isEmpty(idcardUser)){
									try {
										Equipment equipment = (Equipment) houseDailyService.findOneByProperties(Equipment.class, "branchId", check.getBranchId());
										if(equipment != null){
											result = CardUtil.doorDelCardDataInTask(equipment.getSerialNo(), idcardUser);
										}

										if(result != CommonConstants.PortalResultCode.FAILED && !result.toString().equals(CommonConstants.doorInterfaceResult.FAILED)){
											
										} else {
											
											failFlagCount++;
											checkUserLisFail += idcardUser +",";
											//删除锁中一个身份证信息所有授权失败记日志
											SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
											if(equipment != null){
												WarningLog warningLog = new WarningLog();
												warningLog.setLogId(sdf.format(new Date()) + houseDailyService.getSequence("SEQ_WARNINGLOG_ID"));
												warningLog.setRecordTime(new Date());
												warningLog.setRecordUser(SystemConstants.User.ADMIN_ID);
												warningLog.setWarningDate(new Date());
												warningLog.setStatus(CommonConstants.servieLogStatus.EFFECTIVE);
												warningLog.setWarningType(CommonConstants.warningLogStatus.DEL_ALL_CARD_FAILED);
												warningLog.setRemark(check.getBranchId()+"删除锁中所有身份证信息中["+idcardUser+"]失败");
												warningLog.setSerialNo(equipment.getSerialNo());
												houseDailyService.save(warningLog);
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
						operlog.setBranchId(check.getBranchId());
						operlog.setLogId(DateUtil.currentDate("yyMMdd") + check.getBranchId() + houseDailyService.getSequence("SEQ_OPERATELOG_ID"));
						String operid = InetAddress.getLocalHost().toString();// IP地址
						operid = (String) operid.subSequence(operid.indexOf("/") + 1, operid.length());
						operlog.setOperIp(operid);
						operlog.setOperType("11");
						operlog.setOperModule("退房操作(民宿)");
						operlog.setRecordUser(adminName);
						operlog.setRecordTime(new Date());
						
						if(failFlagCount == 0){
							operlog.setContent("房单号" + check.getCheckId() + "/民宿编号:" + check.getBranchId() +"已退房(未结账),入住人["+checkUserLis.substring(0, checkUserLis.length()-1)+"]门锁里身份证信息删除成功");
							houseDailyService.save(operlog);
						} else {
							operlog.setContent("房单号" + check.getCheckId() + "/民宿编号:" + check.getBranchId() +"已退房(未结账),入住人中["+checkUserLis.substring(0, checkUserLis.length()-1)+"]门锁里有["+checkUserLisFail.substring(0, checkUserLisFail.length()-1)+"]身份证信息删除失败");
							houseDailyService.save(operlog);
						}		
					}
					//roomService.upateroomstatus(order.getBranchId(), order.getRoomId(), "1");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}