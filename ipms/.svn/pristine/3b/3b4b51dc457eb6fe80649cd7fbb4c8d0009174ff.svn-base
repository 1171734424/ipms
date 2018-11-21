package com.ideassoft.pms.task;

import org.apache.log4j.Logger;

import com.ideassoft.core.constants.SystemConstants.ProjectName;
import com.ideassoft.core.factory.ServiceFactory;
import com.ideassoft.core.task.ScheduledTask;
import com.ideassoft.pms.service.LogServiceInPms;

public class LogTask extends ScheduledTask implements ProjectName {
	private final Logger logger = Logger.getLogger(LogTask.class);

	private static LogServiceInPms logServiceInPms = null;

	public LogTask(String name) {
		super(name);
	}
	
	public void initScheduledData() {
		logServiceInPms = (LogServiceInPms) ServiceFactory.getService("logServiceInPms");
		logger.debug("schedule task [test1111] init...............");

	}

	public void run() {

		 String sql = "insert into TL_P_OPERATELOG_PAST select * from TL_P_OPERATELOG";
		 String sqls2 = "delete from TL_P_OPERATELOG";
		try {
			logServiceInPms.executeUpdateSQL(sql);
		    logServiceInPms.executeUpdateSQL(sqls2);
		} catch (Exception e) {
			e.printStackTrace();

		}

	}
}
