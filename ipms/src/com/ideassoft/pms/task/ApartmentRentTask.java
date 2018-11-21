package com.ideassoft.pms.task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ideassoft.apartment.service.ApartmentRentService;
import com.ideassoft.bean.Contrart;
import com.ideassoft.bean.Member;
import com.ideassoft.core.constants.SystemConstants;
import com.ideassoft.core.constants.SystemConstants.ProjectName;
import com.ideassoft.core.factory.ServiceFactory;
import com.ideassoft.core.notifier.SmsSingleSender;
import com.ideassoft.core.notifier.SmsSingleSenderResult;
import com.ideassoft.core.task.ScheduledTask;

public class ApartmentRentTask extends ScheduledTask implements ProjectName {

	private final Logger logger = Logger.getLogger(ApartmentRentTask.class);

	private static ApartmentRentService apartmentRentService = null;
	
	private static int appId;
	
	private static String appkey;
	
	private static String country;

	public ApartmentRentTask(String name) {
		super(name);
	}

	public void initScheduledData() {
		apartmentRentService = (ApartmentRentService) ServiceFactory.getService("apartmentRentService");
		appId = SystemConstants.note.APPID;
		appkey = SystemConstants.note.APPKEY;
		country = SystemConstants.note.COUNTRY;
		logger.debug("schedule task [test1111] init...............");
	}
	
	public void run() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			SmsSingleSender singleSender = new SmsSingleSender(appId, appkey);
			
			// 30天
			Calendar thirtyDays = Calendar.getInstance();
			thirtyDays.add(Calendar.DAY_OF_MONTH, 30);
			// 10天
			Calendar tenDays = Calendar.getInstance();
			tenDays.add(Calendar.DAY_OF_MONTH, 10);
			// 3天
			Calendar threeDays = Calendar.getInstance();
			threeDays.add(Calendar.DAY_OF_MONTH, 3);
			
			@SuppressWarnings("unchecked")
			List<Contrart> contracts = apartmentRentService.findByProperties(Contrart.class, "status", "1");
			Map<String, String> map = new HashMap<String, String>();
			for (Contrart contract : contracts) {
				if (sdf.format(contract.getContrartEndTime()).equals(sdf.format(thirtyDays.getTime()))) {
					map.put("天数", "30");
				}
				if (sdf.format(contract.getContrartEndTime()).equals(sdf.format(tenDays.getTime()))) {
					map.put("天数", "10");
				}
				if (sdf.format(contract.getContrartEndTime()).equals(sdf.format(threeDays.getTime()))) {
					map.put("天数", "3");
				}
				if (map.size() > 0) {
					Member member = (Member) apartmentRentService.findById(Member.class, contract.getMemberId());
					map.put("会员", member.getMemberName());
					ArrayList<String> params = new ArrayList<String>();
					params.add(member.getMemberName());
					params.add(map.get("天数"));
					singleSender.sendWithParam(country, member.getMobile(), 52201, params, "", "", "");
				}
			}
			
			Date date = new Date();
			String dayTime = sdf.format(date);
			@SuppressWarnings("unchecked")
			List<Contrart> apartmentContrarts = apartmentRentService.findByProperties(Contrart.class, "status", "4");
			for (Contrart contrart : apartmentContrarts) {
				if (dayTime.equals(sdf.format(contrart.getEndTime()))) {
					Member member = (Member) apartmentRentService.findById(Member.class, contrart.getMemberId());
					ArrayList<String> params = new ArrayList<String>(); // 例如:你的验证码是{1},有几个参数就添加几个
					params.add(member.getMemberName());
					singleSender.sendWithParam(country, contrart.getMobile(), 53525, params, "", "", "");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}