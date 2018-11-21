package com.ideassoft.pmsinhouse.task;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.ideassoft.bean.Bill;
import com.ideassoft.core.constants.CommonConstants;
import com.ideassoft.core.constants.SystemConstants.ProjectName;
import com.ideassoft.core.factory.ServiceFactory;
import com.ideassoft.core.task.ScheduledTask;
import com.ideassoft.pmsinhouse.service.HouseDailyService;

public class HouseDBillDeleteTask extends ScheduledTask implements ProjectName{
	
	private final Logger logger = Logger.getLogger(HouseDailyTask.class);
	
	private static HouseDailyService houseDailyService = null;
	
	
	public HouseDBillDeleteTask(String name) {
		super(name);
	}

	@Override
	public void initScheduledData() {
		houseDailyService = (HouseDailyService) ServiceFactory.getService("houseDailyService");
		logger.debug("schedule task [HouseDBillDeleteTask] init...............");
	}
	
	@SuppressWarnings("unchecked")
	public void run(){
		List<Bill> listbill = houseDailyService.findByProperties(Bill.class, "projectId", 
				CommonConstants.BillProject.DIFF_PRICE, "status", CommonConstants.BillStatus.NonPayment);
		List<Bill> listbi = houseDailyService.findByProperties(Bill.class, "projectId", 
				CommonConstants.BillProject.EX_HOUSE_PRICE, "status", CommonConstants.BillStatus.NonPayment);
		listbill.addAll(listbi);
		
		for (Bill bill : listbill) {
			Date recordtime = bill.getRecordTime();
			long timeOne = recordtime.getTime();
			long timeTwo = new Date().getTime();
			long minute=(timeTwo - timeOne)/(1000*60);//转化minute
			if(minute > 30){
				houseDailyService.delete(bill);
			}
		}
		
	}
	
	

}
