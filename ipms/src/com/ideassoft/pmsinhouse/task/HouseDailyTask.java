package com.ideassoft.pmsinhouse.task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hsqldb.lib.StringUtil;

import com.ideassoft.bean.Bill;
import com.ideassoft.bean.Check;
import com.ideassoft.bean.House;
import com.ideassoft.bean.MemberAccount;
import com.ideassoft.core.constants.CommonConstants;
import com.ideassoft.core.constants.SystemConstants.ProjectName;
import com.ideassoft.core.factory.ServiceFactory;
import com.ideassoft.core.task.ScheduledTask;
import com.ideassoft.pmsinhouse.service.HouseDailyService;

public class HouseDailyTask extends ScheduledTask implements ProjectName {

	private final Logger logger = Logger.getLogger(HouseDailyTask.class);

	private static HouseDailyService houseDailyService = null;

	public HouseDailyTask(String name) {
		super(name);
	}
	
	public void initScheduledData() {
		houseDailyService = (HouseDailyService) ServiceFactory.getService("houseDailyService");
		logger.debug("schedule task [HouseDailyTask] init...............");
	}

	@SuppressWarnings("unchecked")
	public void run() {
		try {
			// 查询在住的房单，添加一天房费,日结房费添加到账单表
			List<Check> checks = houseDailyService.findByProperties(Check.class, "status", "1");
			if (null != checks) {
				for (Check check : checks) {
					House house = (House) houseDailyService.findById(House.class, check.getBranchId());
					if(house != null){
						if (!StringUtil.isEmpty(house.getHouseId())) {
							MemberAccount memberAccount = (MemberAccount) houseDailyService.findOneByProperties(MemberAccount.class, "memberId", check.getCheckUser());
							Bill bill = new Bill();
							String sequences = houseDailyService.getSequence("SEQ_NEW_BILL");
							SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
							String strdate = sdf.format(new Date());
							check.setCost(check.getCost() == null ? 0 : check.getCost() + check.getRoomPrice());// 消费金额
							check.setTotalFee(check.getTotalFee() == null ? 0 : check.getTotalFee() + check.getRoomPrice());// 平账金额
							memberAccount.setTotalRoomnights(memberAccount.getTotalRoomnights() + 1);
							memberAccount.setCurrRoomnights(memberAccount.getCurrRoomnights() + 1);
							bill.setBillId(strdate + check.getBranchId() + sequences);
							bill.setBranchId(check.getBranchId());
							bill.setCheckId(check.getCheckId());
							bill.setProjectId("1234");//原来存的2004预存，现在要变成1234房费,对应的消费
							bill.setProjectName("房费");
							bill.setDescribe("日结");
							bill.setCost(check.getRoomPrice());
							bill.setPay((double) 0);
							bill.setPayment("1");
							bill.setOffset("");// 可以不写
							bill.setStatus("1");
							bill.setRecordTime(new Date());
							bill.setRecordUser(USER_ID);
							bill.setRemark("");// 可以不写
							if (CommonConstants.CheckStatus.CheckOff.equals(check.getStatus())
									|| CommonConstants.CheckStatus.CheckOffAndUnout.equals(check.getStatus())){
								check.setStatus(CommonConstants.CheckStatus.CheckLeave);
							}
							houseDailyService.update(memberAccount);
							houseDailyService.update(check);
							houseDailyService.save(bill);
						}
					}
					
				}
			}
			
			
			//把check表中的每天房价换了，从ordcheprice表捞出
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			Calendar dayTime = Calendar.getInstance();
			//dayTime.add(Calendar.DAY_OF_MONTH, -1);
			String time = sdf.format(dayTime.getTime());
			List<?> orderPriceList = houseDailyService.queryOrderPriceByDate(time);
			for(int i = 0;i < orderPriceList.size();i++){
				String houseId = ((Map<?,?>)orderPriceList.get(i)).get("BRANCH_ID").toString();
				House house = (House) houseDailyService.findOneByProperties(House.class, "houseId", houseId);
				if(house != null){
					Check check = (Check) houseDailyService.findById(Check.class, ((Map<?,?>)orderPriceList.get(i)).get("ORDER_ID").toString());
					//为null说明该房目前是空房，因为14点后才有人住，所以根据订单捞出来的check为null
					if(check != null){
						check.setRoomPrice(Double.parseDouble(((Map<?,?>)orderPriceList.get(i)).get("DAYPRICE").toString()));
						houseDailyService.update(check);
					}
					
					
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}