package com.ideassoft.util;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.ideassoft.bean.House;
import com.ideassoft.bean.Staff;
import com.ideassoft.core.constants.SystemConstants;
import com.ideassoft.core.factory.ServiceFactory;
import com.ideassoft.core.notifier.SmsSingleSender;
import com.ideassoft.pmsinhouse.service.HouseOrderService;

/**
 * 
 * @author zhengsj
 * @date 2018年6月26日
 * @version 1.0
 */
public class NotifyStewardUtil {

	private static HouseOrderService houseOrderService = (HouseOrderService) ServiceFactory.getService("houseOrderService");
	
	/**
	 * 调用此方法可以实现，当民宿被预定时发送信息到管理该民宿下的所有管理员短信
	 * 
	 * @param orderarrivedate 抵店时间
	 * @param orderleavedate 离店时间
	 * @param houseName 民宿名称
	 * @param houseName 民宿Id
	 * @param orderprice 订单价格
	 * @param orderUser 预定人
	 * @param orderMobile 预定人手机
	 * @return
	 */
	public static int notifyStewardUtil(String orderarrivedate, String orderleavedate, String houseName,String houseId, String orderprice, String orderUser, String orderMobile) {
		int returnParam = 1;
		// 创建发短信功能对象
		try {
			SmsSingleSender singleSender = new SmsSingleSender(SystemConstants.note.APPID, SystemConstants.note.APPKEY);
			// 查询所有预定的民宿管家Id编号
			List<Map<String,String>> houseManages = houseOrderService.findBySQL("queryNotifySteward", new String[] { houseId }, true);
			// 循环遍历民宿人员
			if (houseManages.size() > 0) {
				for(Map<String,String> houseManage : houseManages) {
					// 根据查询的管家Id编号循环查询管家信息
					Staff houseStaff = (Staff) houseOrderService.findOneByProperties(Staff.class, "staffId", houseManage.get("STAFF_ID").toString());
					// 发送短信告知有会员入住(入住人、入住时间、离店时间、入住民宿、来源)
					ArrayList<String> params = new ArrayList<String>(); // 例如:客户**预定**民宿，入住日期为**离店日期为**联系方式13401900239
					params.add(orderUser);
					params.add(houseName);
					params.add(orderarrivedate);
					params.add(orderleavedate);
					params.add(orderMobile);
					singleSender.sendWithParam(SystemConstants.note.COUNTRY, houseStaff.getMobile(), 145950, params, "", "", "");
				}
			} else {
				returnParam = -1;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return returnParam;
	}
}
