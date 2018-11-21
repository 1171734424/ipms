package com.ideassoft.crm.task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.ideassoft.bean.PriceApply;
import com.ideassoft.core.constants.SystemConstants.ProjectName;
import com.ideassoft.core.factory.ServiceFactory;
import com.ideassoft.core.task.ScheduledTask;
import com.ideassoft.pms.service.DailyService;

public class PriceApplyTask extends ScheduledTask implements ProjectName {

	private final Logger logger = Logger.getLogger(PriceApplyTask.class);

	private static DailyService dailyService = null;

	public PriceApplyTask(String name) {
		super(name);
	}
	
	public void initScheduledData() {
		dailyService = (DailyService) ServiceFactory.getService("dailyService");
		logger.debug("schedule task [test1111] init...............");
	}

	@SuppressWarnings("unchecked")
	public void run() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			List<PriceApply> priceApplys = dailyService.findByProperties(PriceApply.class, "status", "0");
			//List<PriceApply> priceApplys = dailyService.findByProperties(PriceApply.class, "status", "0", "applyType", "4");
			Date date = new Date();
			String time = sdf.format(date);
			List<PriceApply> list = new ArrayList<PriceApply>();
			for (PriceApply priceApply : priceApplys) {
				String startTime = sdf.format(priceApply.getValidStart());
				if (time.equals(startTime)) {
					priceApply.setStatus("2");
					priceApply.setRecordTime(new Date());
					list.add(priceApply);
				}
			}
			if (list.size() > 0) {
				dailyService.saveOrUpdateAll(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}