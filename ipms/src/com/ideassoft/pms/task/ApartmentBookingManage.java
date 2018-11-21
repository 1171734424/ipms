package com.ideassoft.pms.task;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ideassoft.apartment.service.ApartmentRepairService;
import com.ideassoft.bean.CheckUser;
import com.ideassoft.bean.Equipment;
import com.ideassoft.bean.Reserved;
import com.ideassoft.bean.SysParam;
import com.ideassoft.core.constants.SystemConstants.ProjectName;
import com.ideassoft.core.factory.ServiceFactory;
import com.ideassoft.core.task.ScheduledTask;
import com.ideassoft.ems.controller.GateLockController;
import com.ideassoft.util.GateLockUtil;
import com.ideassoft.util.SpringUtil;

public class ApartmentBookingManage extends ScheduledTask implements ProjectName {

	private final Logger logger = Logger.getLogger(LogTask.class);

	private ApartmentRepairService repairApplicationService = (ApartmentRepairService) ServiceFactory
			.getService("apartmentRepairService");
	

	public ApartmentBookingManage(String name) {
		super(name);
	}

	@Override
	public void initScheduledData() {
		logger.debug("schedule task [ApartmentBookingManageTask] init...............");

	}
	
	@SuppressWarnings("unchecked")
	public void run() {
		try {
			List<Reserved> reserved = repairApplicationService.findBySQL("queryApBookingO",true);
			for (int i = 0; i < reserved.size(); i++) {
				String reservedId = (String) ((Map)(reserved.get(i))).get("RESERVEDID");
				String branchId = (String) ((Map)(reserved.get(i))).get("BRANCHID");
				String roomId = (String) ((Map)(reserved.get(i))).get("ROOMID");
				SysParam sysparam = (SysParam)repairApplicationService.findOneByProperties(SysParam.class, "paramType", "EQUIPSUPERKIND", "paramName","门锁","status","1");
				Equipment equipment = (Equipment)repairApplicationService.findOneByProperties(Equipment.class, "branchId", branchId, "roomId",roomId,"superKind",sysparam.getContent());
				CheckUser ck = (CheckUser) repairApplicationService.findOneByProperties(CheckUser.class, "checkId", reservedId);
				if("0".equals(ck.getStatus())){
					ck.setStatus("1");//生效
					repairApplicationService.update(ck);
					GateLockUtil.gateLock(equipment.getSerialNo(), "2", ck.getIdcard());
				}
			}
			
			List<Reserved> reservedOver = repairApplicationService.findBySQL("queryApBookingT",true);
			for (int i = 0; i < reservedOver.size(); i++) {
				String reservedId = (String) ((Map)(reservedOver.get(i))).get("RESERVEDID");
				String branchId = (String) ((Map)(reservedOver.get(i))).get("BRANCHID");
				String roomId = (String) ((Map)(reservedOver.get(i))).get("ROOMID");
				SysParam sysparam = (SysParam)repairApplicationService.findOneByProperties(SysParam.class, "paramType", "EQUIPSUPERKIND", "paramName","门锁","status","1");
				Equipment equipment = (Equipment)repairApplicationService.findOneByProperties(Equipment.class, "branchId", branchId, "roomId",roomId,"superKind",sysparam.getContent());
				CheckUser ck = (CheckUser) repairApplicationService.findOneByProperties(CheckUser.class, "checkId", reservedId);
				if(ck != null){
					if("1".equals(ck.getStatus())){
						ck.setStatus("0");//失效
						repairApplicationService.update(ck);
						GateLockUtil.gateLock(equipment.getSerialNo(), "4", ck.getIdcard());
					}
					
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
