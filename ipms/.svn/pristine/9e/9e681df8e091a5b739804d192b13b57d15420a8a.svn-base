package com.ideassoft.crm.task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hsqldb.lib.StringUtil;

import com.ideassoft.bean.PriceApply;
import com.ideassoft.bean.PriceApplyDetail;
import com.ideassoft.bean.RoomPrice;
import com.ideassoft.core.constants.SystemConstants.ProjectName;
import com.ideassoft.core.factory.ServiceFactory;
import com.ideassoft.core.task.ScheduledTask;
import com.ideassoft.pms.service.DailyService;

public class RoomPriceTask extends ScheduledTask implements ProjectName {

	private final Logger logger = Logger.getLogger(RoomPriceTask.class);

	private static DailyService dailyService = null;

	public RoomPriceTask(String name) {
		super(name);
	}
	
	public void initScheduledData() {
		dailyService = (DailyService) ServiceFactory.getService("dailyService");
		logger.debug("schedule task [test1111] init...............");
	}

	@SuppressWarnings("unchecked")
	public void run() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH");
			Date date = new Date();
			String time = sdf.format(date);
			List<PriceApply> list = new ArrayList<PriceApply>();
			List<PriceApply> priceApplys = dailyService.findByProperties(PriceApply.class, "status", "2");
			if (priceApplys != null && !priceApplys.isEmpty()) {
				PriceApply priceApply;
				for (int i = 0; i < priceApplys.size(); i++) {
					priceApply = (PriceApply) priceApplys.get(i);
					String startTime = sdf.format(priceApply.getValidStart());
					if(StringUtil.isEmpty(priceApply.getPost())){
						if (time.equals(startTime)) {
							priceApply.setStatus("3");
							priceApply.setRecordTime(new Date());
							list.add(priceApply);
						}
					}
				}
			}
			if (list.size() > 0) {
				dailyService.saveOrUpdateAll(list);
			}

			priceApplys = dailyService.findBySQL("getRoomPrice", true);
			if (priceApplys != null && !priceApplys.isEmpty()) {
				Map<?, ?> map;
				for (int i = 0; i < priceApplys.size(); i++) {
					map = (Map<?, ?>) priceApplys.get(i);
					String days = map.get("VALID_DAY") == null ? "" : map.get("VALID_DAY").toString();
					String timeDay = map.get("FILTER_DAY") == null ? "" : map.get("FILTER_DAY").toString();
					String[] times = null;
					Date startTime = null;
					Date endTime = null;
					Calendar nextEndTime = Calendar.getInstance();
					if (!StringUtil.isEmpty(timeDay)) {
						times = timeDay.split("至");
						startTime = sdf.parse(times[0]);
						endTime = sdf.parse(times[1]);
						nextEndTime.setTime(endTime);
						nextEndTime.add(Calendar.DAY_OF_MONTH, 1);
					}
					Calendar cal = Calendar.getInstance();
					cal.setTime(date);
					String day = Integer.toString(cal.get(Calendar.DAY_OF_WEEK) - 1);
					List<?> priceApplyDetails = dailyService.findByProperties(PriceApplyDetail.class, "applyId", map.get("APPLY_ID").toString());
					PriceApplyDetail priceApplyDetail;
					for (int j = 0; j < priceApplyDetails.size(); j++) {
						priceApplyDetail = (PriceApplyDetail) priceApplyDetails.get(j);
						RoomPrice roomPrice = (RoomPrice) dailyService.findOneByProperties(RoomPrice.class, "roomPriceId.branchId", map.get("BRANCH_ID").toString(), "roomPriceId.rpKind", 
								map.get("APPLY_TYPE").toString(), "roomPriceId.rpId", priceApplyDetail.getContent(), "roomPriceId.roomType", priceApplyDetail.getRoomType(), "roomPriceId.status", 
								map.get("APPLY_KIND").toString());
						roomPrice.setRoomPrice(priceApplyDetail.getRoomPrice());
						roomPrice.setRecordTime(new Date());
						if (!days.contains(day)) {
							dailyService.upateroomPrice(map.get("BRANCH_ID").toString(), map.get("APPLY_TYPE")
									.toString(), priceApplyDetail.getContent(), priceApplyDetail.getRoomType(),
									((Map<?, ?>) priceApplys.get(i)).get("APPLY_KIND").toString(), "4");
						} else if (!StringUtil.isEmpty(timeDay)) {
							if (startTime.getTime() == sdf.parse(time).getTime()) {
								dailyService.upateroomPrice(map.get("BRANCH_ID").toString(), 
										map.get("APPLY_TYPE").toString(), priceApplyDetail.getContent(), 
										priceApplyDetail.getRoomType(), map.get("APPLY_KIND").toString(), "4");
							} else if (sdf.parse(time).getTime() == nextEndTime.getTimeInMillis()) {
								dailyService.upateroomPrice(map.get("BRANCH_ID").toString(), map.get("APPLY_TYPE")
										.toString(), priceApplyDetail.getContent(), priceApplyDetail.getRoomType(), 
										map.get("APPLY_KIND").toString(), "5");
							}
						} else {
							dailyService.upateroomPrice(map.get("BRANCH_ID").toString(), 
									map.get("APPLY_TYPE").toString(), priceApplyDetail.getContent(), 
									priceApplyDetail.getRoomType(),
									((Map<?, ?>) priceApplys.get(i)).get("APPLY_KIND").toString(), "5");
						}
						priceApplyDetail.setRecordTime(new Date());
						dailyService.update(priceApplyDetail);
						dailyService.update(roomPrice);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}