package com.ideassoft.price;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ideassoft.bean.PriceRules;
import com.ideassoft.bean.PriceVolatility;
import com.ideassoft.core.factory.ServiceFactory;
import com.ideassoft.util.DateUtil;

@Deprecated
public class RealPrice_deprecated implements IPrice {
	
	
	private PriceService priceService = (PriceService) ServiceFactory.getService("priceService");
	
//	private PriceVolatility basicPrice = null;
//	private PriceVolatility adjustPrice = null ;
//	private PriceVolatility activityPrice = null;
	
	private Map<String, PriceVolatility> prices = new HashMap<String, PriceVolatility>();
	
	private Double basicPrice = 0D;
	private Double volatilePrice = 0D;
	private Double activityPrice = 0D;
	
	private String branchId;
	private String theme;
	private String roomType;
	private String rpId;
	private String memoTime;
	

	public RealPrice_deprecated(){
		
		//List<?> kj = priceService.getPrice("100002",  "1", "1", "PK", "2018/05/14 13:14:20", "2018/05/14 13:14:20");
		
		//System.out.println(kj);
	}
	
	public RealPrice_deprecated(String branchId, String theme, String roomType, String rpId, String memoTime){
		this.branchId = branchId;
		this.theme = theme;
		this.roomType = roomType;
		this.rpId = rpId;
		this.memoTime = memoTime;
		getData("1");
	}

	public RealPrice_deprecated(String branchId, String theme, String roomType, String rpId, Date memoTime){
		this.branchId = branchId;
		this.theme = theme;
		this.roomType = roomType;
		this.rpId = rpId;
		this.memoTime = DateUtil.d2s(memoTime, "yyyy/MM/dd HH:mm:ss");
		getData("1");
	}
	
	private void getData(String level){
		List<?> list = priceService.getPrice(branchId, theme, roomType, rpId, memoTime, level);
		if(list != null && !list.isEmpty()){
			for (Object object : list) {
				Map<?, ?> map = (Map<?, ?>) object;
				String priority = (String) map.get("PRIORITY");
				PriceVolatility priceVolatility = new PriceVolatility((String)map.get("VOLATILITY_ID"),
				(String)map.get("BRANCH_ID"),
				(String)map.get("THEME"),
				(String)map.get("ROOM_TYPE"),
				(String)map.get("RP_ID"),
				((BigDecimal)map.get("ROOM_PRICE")).doubleValue(),
				(String)map.get("PRICE_TYPE"),
				(String)map.get("PRICETYPE_DETAIL"),
				(Date)map.get("START_TIME"),
				(Date)map.get("END_TIME"),
				(String)map.get("PRIORITY"),
				(String)map.get("RULES_ID"),
				(Date)map.get("RECORDTIME"));
				prices.put(priority, priceVolatility);
			}
		}else{
			prices.clear();
		}
	}
	
	private boolean verifyRule(PriceVolatility priceVolatility){
		String rulesId = priceVolatility.getRulesId();
		if("0".equals(rulesId)){
			return true;
		}
		PriceRules priceRules = (PriceRules) priceService.findOneByProperties(PriceRules.class, "rulesId", rulesId);
		if(priceRules == null){
			return true;
		}
		//if(priceRules.getRulesFilters().equals("1")){ 
		Calendar cl = Calendar.getInstance();
		cl.setTime(DateUtil.s2d(memoTime, "yyyy/MM/dd HH:mm:ss"));
		int month = cl.get(Calendar.MONTH) + 1;
		int date = cl.get(Calendar.DATE);
		int week = cl.get(Calendar.DAY_OF_WEEK) - 1;
		boolean flag = false;
		//下面代码，默认查到的天数，都是生效
		if("1".equals(priceRules.getRulesPeriod())){ //判断时间
			flag = verifyHours(priceRules.getRulesPeriodDetails());
		} else if("2".equals(priceRules.getRulesPeriod())){ //判断日（某天）
			flag = verifyDateWeekMonth(priceRules.getRulesPeriodDetails(), date);
		} else if("3".equals(priceRules.getRulesPeriod())){ //判断周 （星期几）
			flag = verifyDateWeekMonth(priceRules.getRulesPeriodDetails(), week);
		} else if("4".equals(priceRules.getRulesPeriod())){ //判断月
			flag = verifyDateWeekMonth(priceRules.getRulesPeriodDetails(), month);
		}
		if(priceRules.getRulesFilters().equals("2")){ //过滤规则为1，表示查到的天数即生效,过滤规则为2则排除
			if(flag){
				flag = false;
			}else{
				flag = true;
			}
		}
		return flag;
	}
	
	private boolean verifyHours(String rulesHour){
		String hours = memoTime.split(" ")[1];
		String[] ruleh = rulesHour.split("-");
		long startlhour = DateUtil.s2d(ruleh[0], "HH:mm:ss").getTime();
		long endlhour = DateUtil.s2d(ruleh[1], "HH:mm:ss").getTime();
		long lhour = DateUtil.s2d(hours, "HH:mm:ss").getTime();
		if( lhour >= startlhour && lhour <= endlhour){
			return true;
		}else{
			return false;
		}
	}
	
	private boolean verifyDateWeekMonth(String data, int date){
		String[] arrdetail = data.split(",");
		for (String date1 : arrdetail) {
			int intdate = Integer.valueOf(date1);
			if(intdate == date){
				return true;
			}
		}
		return false;
	}
	
	//被规则规避掉的结果，去前一个时间结果查询
	private Double searchPriceVolatility(String  priorty){
		int searchLevel = 2; //从第二个结果开始查询
		while (prices.containsKey(priorty)){
			PriceVolatility priceVolatility = prices.get(priorty);
			if(verifyRule(priceVolatility)){
				return Double.valueOf(priceVolatility.getRoomPrice());
			}else{
				getData(searchLevel + "");
				searchLevel++;
			}
		}
		return 0D;
	}

	public Double checkHousePrice() {
		Double roomPrice = 0D;
		if(prices.containsKey("1")){
			PriceVolatility priceVolatility = prices.get("1");
			roomPrice = Double.valueOf(priceVolatility.getRoomPrice());
		}
		if(prices.containsKey("2")){
			Double tempPrice = searchPriceVolatility("2");
			if( tempPrice != 0){
				roomPrice = tempPrice;
			}
		}
		if(prices.containsKey("3")){
			getData("1");
			Double tempPrice = searchPriceVolatility("3");
			if( tempPrice != 0){
				roomPrice = tempPrice;
			}
		}
		return roomPrice;
	}
	
	public Double getBasicPrice() {
		return basicPrice;
	}

	public Double getVolatilePrice() {
		return volatilePrice;
	}
	
	public Double getActivityPrice() {
		return activityPrice;
	}
	
	
	
	

	public String getBranchId() {
		return branchId;
	}


	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}


	public String getTheme() {
		return theme;
	}


	public void setTheme(String theme) {
		this.theme = theme;
	}


	public String getRoomType() {
		return roomType;
	}


	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}


	public String getRpId() {
		return rpId;
	}


	public void setRpId(String rpId) {
		this.rpId = rpId;
	}
	
	public String getMemoTime() {
		return memoTime;
	}

	public void setMemoTime(String memoTime) {
		this.memoTime = memoTime;
	}

	public Double checkRoomPrice() {
		// TODO Auto-generated method stub
		return null;
	}
}
