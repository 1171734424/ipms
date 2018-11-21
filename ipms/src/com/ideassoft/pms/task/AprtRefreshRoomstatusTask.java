package com.ideassoft.pms.task;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ideassoft.apartment.service.ApartmentRentService;
import com.ideassoft.bean.Aptorder;
import com.ideassoft.bean.AptorderDetail;
import com.ideassoft.bean.CheckUser;
import com.ideassoft.bean.Contrart;
import com.ideassoft.bean.Member;
import com.ideassoft.bean.OperateLog;
import com.ideassoft.bean.RepairApply;
import com.ideassoft.bean.Room;
import com.ideassoft.bean.WarningLog;
import com.ideassoft.core.constants.SystemConstants;
import com.ideassoft.core.constants.SystemConstants.ProjectName;
import com.ideassoft.core.factory.ServiceFactory;
import com.ideassoft.core.task.ScheduledTask;
import com.ideassoft.pms.service.RoomService;
import com.ideassoft.portal.DataDealPortalService;
import com.ideassoft.portal.IDataDealPortal;
import com.ideassoft.util.DateUtil;

public class AprtRefreshRoomstatusTask extends ScheduledTask implements ProjectName {

	private final Logger logger = Logger.getLogger(AprtRefreshRoomstatusTask.class);

	private static ApartmentRentService apartmentRentService = null;
	
	private static RoomService roomService = null;
	
	private static String adminName;
	
	private static String CONNECTIONFAIL;
	
	private static String CONNECTION;
	
	private static String VALID;
	
	private static int NULL;

	public AprtRefreshRoomstatusTask(String name) {
		super(name);
	}
	
	public void initScheduledData() {
		apartmentRentService = (ApartmentRentService) ServiceFactory.getService("apartmentRentService");
		roomService = (RoomService) ServiceFactory.getService("roomService");
		adminName = SystemConstants.User.ADMIN_NAME;
		CONNECTIONFAIL = SystemConstants.PortalResultCode.CONNECTIONFAIL;
		CONNECTION = SystemConstants.WarningType.CONNECTION;
		VALID = SystemConstants.WarningStatus.VALID;
		NULL = SystemConstants.PortalResultCode.NULL;
		logger.debug("schedule task [test1111] init...............");
	}

	@SuppressWarnings("unchecked")
	public void run() {
		try {
			List<?> list = roomService.findBySQL("refreshAprtRoom", true);
			Map<String,String> map ;
			Room room;
			if(list.size()>0){
				for (int j = 0; j < list.size(); j++) {
					map = (Map<String,String>) list.get(j);
					room = (Room) roomService.findOneByProperties(Room.class, "roomKey.branchId", map.get("branchId"),"roomKey.roomId", map.get("roomId"));
					room.setStatus("2");
					roomService.update(room);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}