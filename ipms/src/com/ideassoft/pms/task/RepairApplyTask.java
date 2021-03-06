package com.ideassoft.pms.task;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ideassoft.apartment.service.ApartmentRepairService;
import com.ideassoft.bean.RepairApply;
import com.ideassoft.bean.Room;
import com.ideassoft.core.constants.SystemConstants.ProjectName;
import com.ideassoft.core.factory.ServiceFactory;
import com.ideassoft.core.task.ScheduledTask;

public class RepairApplyTask extends ScheduledTask implements ProjectName {

	private final Logger logger = Logger.getLogger(LogTask.class);

	private ApartmentRepairService repairApplicationService = (ApartmentRepairService) ServiceFactory
			.getService("repairApplicationService");

	public RepairApplyTask(String name) {
		super(name);
	}

	@Override
	public void initScheduledData() {
		logger.debug("schedule task [RepairApplyTask] init...............");

	}
	
	@SuppressWarnings("unchecked")
	public void run() {
		try {
			List<RepairApply> list = repairApplicationService.findBySQL("queryRepairingApply", true);//zhengzaiweixiude
			List<RepairApply> listed = repairApplicationService.findBySQL("queryRepairedApply", true);//xiufuwanchengde
			if (!list.isEmpty() && list != null) {
				Room room;
				for (int i = 0; i < list.size(); i++) {
					String branchid = (String) ((Map)(list.get(i))).get("BRANCHID");
					String roomid = (String) ((Map)(list.get(i))).get("ROOMID");
					
					room = (Room) repairApplicationService.findOneByProperties(
							Room.class, "roomKey.branchId", branchid,
							"roomKey.roomId", roomid);
					room.setStatus("W");//weixiu 
					room.setRecordTime(new Date());
					repairApplicationService.update(room);
				}
			}
			if(!listed.isEmpty() && listed != null){
				Room room;
				for (int i = 0; i < listed.size(); i++) {
					String branchid = (String) ((Map)(listed.get(i))).get("BRANCHID");
					String roomid = (String) ((Map)(listed.get(i))).get("ROOMID");
					
					room = (Room) repairApplicationService.findOneByProperties(
							Room.class, "roomKey.branchId", branchid,
							"roomKey.roomId", roomid);
					room.setStatus("3");//zaizhu
					room.setRecordTime(new Date());
					repairApplicationService.update(room);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
