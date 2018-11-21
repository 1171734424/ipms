package com.ideassoft.pmsinhouse.task;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.ideassoft.bean.HaltPlan;
import com.ideassoft.bean.House;
import com.ideassoft.bean.Room;
import com.ideassoft.bean.RoomStatus;
import com.ideassoft.core.constants.CommonConstants;
import com.ideassoft.core.constants.SystemConstants.ProjectName;
import com.ideassoft.core.factory.ServiceFactory;
import com.ideassoft.core.task.ScheduledTask;
import com.ideassoft.pmsinhouse.service.HouseDailyService;
import com.ideassoft.util.DateUtil;

public class StopHouseTask extends ScheduledTask implements ProjectName {
	
	private final Logger logger = Logger.getLogger(StopHouseTask.class);

	private static HouseDailyService houseDailyService = null;
	
	public StopHouseTask(String name) {
		super(name);
	}
	
	public void initScheduledData() {
		houseDailyService = (HouseDailyService) ServiceFactory.getService("houseDailyService");
		logger.debug("schedule task [StopHouseTask] init...............");
	}
	
	public void run(){
		
		List<?> listhaltplan = houseDailyService.findByProperties(HaltPlan.class, "status", "1");
		if(listhaltplan != null && !listhaltplan.isEmpty()){
			Calendar calendarstarthaltplan = Calendar.getInstance();
			Calendar currenttime = Calendar.getInstance();
			for (Object object : listhaltplan) {
				HaltPlan haltPlan = (HaltPlan) object;
				Date datestarthaltplan = haltPlan.getStartTime();
				calendarstarthaltplan.setTime(datestarthaltplan);
				String haltType = haltPlan.getHaltType();
				String branchid = haltPlan.getBranchId();
				String roomid = haltPlan.getRoomId();
				if(DateUtil.isSameDay(currenttime, calendarstarthaltplan)){
					haltPlan.setStatus("3");
					houseDailyService.update(haltPlan);
					if(branchid.startsWith("H")){
						House house = (House) houseDailyService.findOneByProperties(House.class, "houseId", branchid);
						house.setStatus("1".equals(haltType) ? 
								CommonConstants.HouseStatus.HouseStop : CommonConstants.HouseStatus.HouseRepair);
						house.setRecordTime(new Date());
						houseDailyService.update(house);
					}
					else{
						Room roomt = (Room) houseDailyService.findOneByProperties(Room.class, "roomKey.roomId", roomid, "roomKey.branchId", branchid);
						RoomStatus roomstatus = (RoomStatus) houseDailyService.findOneByProperties(RoomStatus.class, "branchId", branchid, "roomType", roomt.getRoomType());
						if(roomstatus != null){
							roomstatus.setStopnum(roomstatus.getStopnum() + 1);
							roomstatus.setSellnum(roomstatus.getSellnum() - 1);
							houseDailyService.update(roomstatus);
						}
						houseDailyService.upateroomstatus(branchid, roomid, "1".equals(haltType) ? 
								CommonConstants.RoomStatus.RoomStop : CommonConstants.RoomStatus.RoomRepair);
					}
				}
			}
		}
	}
}
