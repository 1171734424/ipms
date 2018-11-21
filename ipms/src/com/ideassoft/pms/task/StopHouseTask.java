package com.ideassoft.pms.task;

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
import com.ideassoft.pms.service.RoomService;
import com.ideassoft.util.DateUtil;

public class StopHouseTask extends ScheduledTask implements ProjectName {
	
	private final Logger logger = Logger.getLogger(StopHouseTask.class);
	
	private static RoomService roomService = (RoomService) ServiceFactory.getService("roomService");

	public StopHouseTask(String name) {
		super(name);
	}
	
	public void initScheduledData() {
		roomService = (RoomService) ServiceFactory.getService("roomService");
		logger.debug("schedule task [StopHouseTask] init...............");
	}
	
	public void run(){
		
		
		
		List<?> listendhaltplan = roomService.findByProperties(HaltPlan.class, "status", "3");
		if(listendhaltplan != null && !listendhaltplan.isEmpty()){
			Calendar calendarendhaltplan = Calendar.getInstance();
			Calendar currenttime = Calendar.getInstance();
			for (Object object : listendhaltplan) {
				HaltPlan haltPlan = (HaltPlan) object;
				//结束日期
				Date dateendhaltplan = haltPlan.getEndTime();
				calendarendhaltplan.setTime(dateendhaltplan);
				calendarendhaltplan.add(Calendar.DATE, 1);
				
				String branchid = haltPlan.getBranchId();
				String roomid = haltPlan.getRoomId();
				
				//自动关闭停售计划
				if(DateUtil.isSameDay(currenttime,  calendarendhaltplan)){
					haltPlan.setStatus(CommonConstants.HaltPlanStatus.DONE);
					roomService.update(haltPlan);
					if(branchid.startsWith("H")){
						House house = (House) roomService.findOneByProperties(House.class, "houseId", branchid);
						house.setStatus(CommonConstants.HouseStatus.HouseNull);
						house.setRecordTime(new Date());
						roomService.update(house);
					}
					else{
						Room roomt = (Room) roomService.findOneByProperties(Room.class, "roomKey.roomId", roomid, "roomKey.branchId", branchid);
						RoomStatus roomstatus = (RoomStatus) roomService.findOneByProperties(RoomStatus.class, "branchId", branchid, "roomType", roomt.getRoomType());
						if(roomstatus != null){
							roomstatus.setStopnum(roomstatus.getStopnum() - 1);
							roomstatus.setSellnum(roomstatus.getSellnum() + 1);
							roomService.update(roomstatus);
						}
						roomService.upateroomstatus(branchid, roomid, CommonConstants.RoomStatus.RoomNull);
					}
				}
				
			}
		}
		
		
		
		List<?> liststarthaltplan = roomService.findByProperties(HaltPlan.class, "status", "1");
		if(liststarthaltplan != null && !liststarthaltplan.isEmpty()){
			Calendar calendarstarthaltplan = Calendar.getInstance();
			Calendar currenttime = Calendar.getInstance();
			for (Object object : liststarthaltplan) {
				HaltPlan haltPlan = (HaltPlan) object;
				//开始日期
				Date datestarthaltplan = haltPlan.getStartTime();
				calendarstarthaltplan.setTime(datestarthaltplan);
				
				String haltType = haltPlan.getHaltType();
				String branchid = haltPlan.getBranchId();
				String roomid = haltPlan.getRoomId();
				
				//自动开启停售计划
				if(DateUtil.isSameDay(currenttime, calendarstarthaltplan)){
					haltPlan.setStatus("3");
					roomService.update(haltPlan);
					if(branchid.startsWith("H")){
						House house = (House) roomService.findOneByProperties(House.class, "houseId", branchid);
						house.setStatus("1".equals(haltType) ? 
								CommonConstants.HouseStatus.HouseStop : CommonConstants.HouseStatus.HouseRepair);
						house.setRecordTime(new Date());
						roomService.update(house);
					}
					else{
						Room roomt = (Room) roomService.findOneByProperties(Room.class, "roomKey.roomId", roomid, "roomKey.branchId", branchid);
						RoomStatus roomstatus = (RoomStatus) roomService.findOneByProperties(RoomStatus.class, "branchId", branchid, "roomType", roomt.getRoomType());
						if(roomstatus != null){
							roomstatus.setStopnum(roomstatus.getStopnum() + 1);
							roomstatus.setSellnum(roomstatus.getSellnum() - 1);
							roomService.update(roomstatus);
						}
						roomService.upateroomstatus(branchid, roomid, "1".equals(haltType) ? 
								CommonConstants.RoomStatus.RoomStop : CommonConstants.RoomStatus.RoomRepair);
					}
				}
				
			}
		}
		
		
		
		

	}
}
