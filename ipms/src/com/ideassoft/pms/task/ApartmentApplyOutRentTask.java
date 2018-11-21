package com.ideassoft.pms.task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ideassoft.apartment.service.ApartmentRentService;
import com.ideassoft.bean.Contrart;
import com.ideassoft.bean.Member;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.constants.SystemConstants;
import com.ideassoft.core.constants.SystemConstants.ProjectName;
import com.ideassoft.core.factory.ServiceFactory;
import com.ideassoft.core.notifier.SmsSingleSender;
import com.ideassoft.core.notifier.SmsSingleSenderResult;
import com.ideassoft.core.task.ScheduledTask;
import com.ideassoft.util.RequestUtil;

/**
 * 定时发送退租提醒短信给公寓管家
 * @author zhengsj
 * @date 2018年7月13日
 * @version 1.0
 */
public class ApartmentApplyOutRentTask extends ScheduledTask implements ProjectName {

	private final Logger logger = Logger.getLogger(ApartmentApplyOutRentTask.class);

	private static ApartmentRentService apartmentRentService = null;
	
	private static int appId;
	
	private static String appkey;
	
	private static String country;

	public ApartmentApplyOutRentTask(String name) {
		super(name);
	}

	/**
	 * 初始化加载数据
	 */
	public void initScheduledData() {
		apartmentRentService = (ApartmentRentService) ServiceFactory.getService("apartmentRentService");
		appId = SystemConstants.note.APPID;
		appkey = SystemConstants.note.APPKEY;
		country = SystemConstants.note.COUNTRY;
		logger.debug("schedule task [test1111] init...............");
	}
	
	/**
	 * 主任务流程
	 */
	public void run() {
		try {
			// 获取短信模板
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			SmsSingleSender singleSender = new SmsSingleSender(appId, appkey);
			// 获取当天公寓退租申请所有数据 TL_P_OPERATELOG_PAST表中
			List<?> OperatelogList = apartmentRentService.findBySQL("queryCheckoutdatas", true);
			
			// 获取OperatelogList中的BranchId以便查询该门店下所有的管理员,并发送短信
			ArrayList<String> params = new ArrayList<String>();
			for (int i = 0; i < OperatelogList.size(); i++) {
				String mobile = (String) ((Map<?, ?>) (OperatelogList.get(i)))
						.get("MOBILE");
				String staffname = (String) ((Map<?, ?>) (OperatelogList.get(i)))
						.get("STAFF_NAME");
				String branchId = (String) ((Map<?, ?>) (OperatelogList.get(i)))
						.get("BRANCHID");
				String roomId = (String) ((Map<?, ?>) (OperatelogList.get(i)))
						.get("ROOMID");
				String memberId = (String) ((Map<?, ?>) (OperatelogList.get(i)))
						.get("MEMBERID");
				Member member = (Member) apartmentRentService.findOneByProperties(Member.class, "memberId", memberId);
				String memberPhone = member.getMobile();
				params.add(staffname);
				params.add(branchId);
				params.add(roomId);
				params.add(member.getMemberName());
				params.add(memberPhone);
				singleSender.sendWithParam(SystemConstants.note.COUNTRY, mobile, 155973, params, "", "", "");
				
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}