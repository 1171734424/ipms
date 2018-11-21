package com.ideassoft.core.factory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ideassoft.bean.EventType;
import com.ideassoft.core.constants.SystemConstants;
import com.ideassoft.core.notifier.service.impl.NotifyService;

public class ConstansFactory {
	/**
	 * constansTale数据说明：
	 * constansTale的key放的是要取得的静态数据类型，value中放的是以此静态类型主键为key，以此静态类型实例为value的map
	 */
	private static final Map<String, List<?>> constansTable = new HashMap<String, List<?>>();

	public static EventType getEventTypeByCode(int code) {
		List<?> lst = constansTable.get("eventType");
		if (lst == null || lst.size() == 0) {
			try {
				NotifyService service = (NotifyService) ServiceFactory.getService("notifyService");
				List<EventType> list = service.findEventTypes();
				if (list != null && list.size() != 0) {
					constansTable.put("eventType", list);
					for (int i = 0; i < list.size(); i++) {
						EventType eventType = list.get(i);
						if (eventType.getId() == code) {
							return eventType;
						}
					}
				} else {
					return null;
				}
			} catch (Exception e) {
				e.printStackTrace();

			}
		} else {
			for (int i = 0; i < lst.size(); i++) {
				EventType eventType = (EventType) lst.get(i);
				if (eventType.getId() == code) {
					return eventType;
				}
			}
		}
		return null;

	}

	public static String[] getSplitDescs(int code, String... strings) {
		return strings;
	}
	
	public static String getEventCode(int code) {
		return String.valueOf(code);
	}

	/**
	 * @param flag 0表示数据库描述规则，1表示短息发送描述规则，2表示邮件发送描述规则
	 * */
	public static String getEventConstent(int flag, int code, String... strings) {
		switch (code) {
		case SystemConstants.EventConst.EventCode.SMS_SERVER_ERROR:// "短信服务异常";
		case SystemConstants.EventConst.EventCode.SMS_SERVER_START:// "短信服务启动成功";
		case SystemConstants.EventConst.EventCode.EMAIL_SERVER_ERROE:// "邮件服务异常";
		case SystemConstants.EventConst.EventCode.EMAIL_SERVER_START:// "邮件服务启动成功";
		{
			if (flag == 0 || flag == 1 || flag == 2) {
				return strings[0];
			} else {
				return null;
			}
		}
		default:
			if (code >= 1 && code <= 99) {
				String splitCodes = null;
				if (flag == 0) {
					splitCodes = "<br>";
				} else if (flag == 1) {
					splitCodes = "\r\n";
				} else if (flag == 2) {
					splitCodes = "\r\n\t";
				} else {
					return null;
				}
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < strings.length; i++) {
					String temp = strings[i];
					String k = temp.replaceAll("null", "无 ");
					sb.append(k);
					if (i < strings.length - 1) {
						sb.append(splitCodes);
					}

				}
				return sb.toString();
			} else {
				break;
			}
		}
		return null;
	}

}
