package com.ideassoft.core.notifier;

import org.apache.log4j.Logger;

import com.ideassoft.core.constants.SystemConstants;
import com.ideassoft.core.notifier.bean.NotifyContent;

public class EventNotifier {
	private static Logger logger = Logger.getLogger(EventNotifier.class);
	
	public static final void notify(NotifyContent notifyContent) {
		if (notifyContent.getNotifyType() == SystemConstants.NotifyMSGType.EVENTADD) {
			logger.info("test================test");
		}
	}
		
}
