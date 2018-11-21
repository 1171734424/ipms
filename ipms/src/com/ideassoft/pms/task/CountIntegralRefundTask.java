package com.ideassoft.pms.task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;

import com.ideassoft.bean.Bill;
import com.ideassoft.bean.Check;
import com.ideassoft.bean.Contrart;
import com.ideassoft.bean.MemberAccount;
import com.ideassoft.core.constants.SystemConstants.ProjectName;
import com.ideassoft.core.factory.ServiceFactory;
import com.ideassoft.core.task.ScheduledTask;
import com.ideassoft.pms.service.DailyService;

public class CountIntegralRefundTask extends ScheduledTask implements ProjectName {

	private final Logger logger = Logger.getLogger(CountIntegralRefundTask.class);

	private static DailyService dailyService = null;

	public CountIntegralRefundTask(String name) {
		super(name);
	}
	
	public void initScheduledData() {
		dailyService = (DailyService) ServiceFactory.getService("dailyService");
		logger.debug("schedule task [test1111] init...............");
	}

	@SuppressWarnings("unchecked")
	public void run() {
		try {
			//
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			Calendar dayTime = Calendar.getInstance();
			dayTime.add(Calendar.DAY_OF_MONTH, -1);
			List<Contrart> contracts = dailyService.findByProperties(Contrart.class, "status", "2");
			List<Check> checks = dailyService.findByProperties(Check.class, "status", "4");
			for (Contrart contract : contracts) {
				if (sdf.format(dayTime.getTime()).equals(sdf.format(contract.getRecordTime()))) {
					Double cost = 0D;
					MemberAccount memberAccount = (MemberAccount) dailyService.findOneByProperties(MemberAccount.class, "memberId", contract.getMemberId());
					List<Bill> billone = dailyService.findByProperties(Bill.class, "checkId", contract.getContrartId(), "projectId", "1234");
					List<Bill> billtwo = dailyService.findByProperties(Bill.class, "checkId", contract.getContrartId(), "projectId", "1235");
					if(billone != null){
						for (Bill bill : billone) {
							cost += bill.getCost();
						}
					}
					if(billtwo != null){
						for (Bill bill : billone) {
							cost += bill.getCost();
						}
					}
					memberAccount.setCurrIntegration(memberAccount.getCurrIntegration() + Long.parseLong(Double.toString(cost)));
					memberAccount.setTotalIntegration(memberAccount.getTotalIntegration() + Long.parseLong(Double.toString(cost)));
					dailyService.update(memberAccount);
				}
			}
			for (Check check : checks) {
				if (sdf.format(dayTime.getTime()).equals(sdf.format(check.getCheckoutTime()))) {
					Double cost = 0D;
					MemberAccount memberAccount = (MemberAccount) dailyService.findOneByProperties(MemberAccount.class, "memberId", check.getCheckUser());
					List<Bill> billone = dailyService.findByProperties(Bill.class, "checkId", check.getCheckId(), "projectId", "1234");
					List<Bill> billtwo = dailyService.findByProperties(Bill.class, "checkId", check.getCheckId(), "projectId", "1235");
					if(billone != null){
						for (Bill bill : billone) {
							cost += bill.getCost();
						}
					}
					if(billtwo != null){
						for (Bill bill : billone) {
							cost += bill.getCost();
						}
					}
					int days = (int) ((check.getCheckoutTime().getTime() - check.getCheckinTime().getTime()) / (1000*3600*24));
					String Smoney = String.valueOf(cost);
					Smoney = Smoney.substring(0, Smoney.indexOf("."));
					memberAccount.setCurrIntegration(memberAccount.getCurrIntegration() + Long.parseLong(Smoney));
					memberAccount.setTotalIntegration(memberAccount.getTotalIntegration() + Long.parseLong(Smoney));
					memberAccount.setCurrRoomnights(memberAccount.getCurrRoomnights() + days);
					memberAccount.setTotalRoomnights(memberAccount.getTotalRoomnights() + days);
					dailyService.update(memberAccount);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}