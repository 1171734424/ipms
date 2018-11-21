package com.ideassoft.pmsinhouse.task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.ideassoft.bean.Order;
import com.ideassoft.core.constants.SystemConstants.ProjectName;
import com.ideassoft.core.factory.ServiceFactory;
import com.ideassoft.core.task.ScheduledTask;
import com.ideassoft.pmsinhouse.service.HouseDailyService;

public class HouseOrderCancelTask extends ScheduledTask implements ProjectName {

	private final Logger logger = Logger.getLogger(HouseOrderCancelTask.class);

	private static HouseDailyService houseDailyService = null;

	public HouseOrderCancelTask(String name) {
		super(name);
	}
	
	public void initScheduledData() {
		houseDailyService = (HouseDailyService) ServiceFactory.getService("houseDailyService");
		logger.debug("schedule task [test1111] init...............");
	}

	@SuppressWarnings("unchecked")
	public void run() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm");
			List<Order> orders = houseDailyService.findByProperties(Order.class, "theme", "3");
			for (Order order : orders) {
				if(order.getAdvanceCash() == null){
					Calendar nowTime = Calendar.getInstance();
					nowTime.setTime(order.getOrderTime());
					nowTime.add(Calendar.MINUTE, 30);
					if(sdf.format(nowTime.getTime()).equals(sdf.format(new Date()))){
						order.setStatus("0");
					}
				}
				houseDailyService.update(order);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}