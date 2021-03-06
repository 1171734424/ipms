package com.ideassoft.price;

import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.Calendar;
import java.util.Date;
import java.util.Deque;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ideassoft.bean.Member;
import com.ideassoft.bean.MemberRank;
import com.ideassoft.bean.PriceRules;
import com.ideassoft.bean.PriceVolatility;
import com.ideassoft.core.factory.ServiceFactory;
import com.ideassoft.exception.BusinessException;
import com.ideassoft.util.DateUtil;

public class RealPrice implements IPrice {
	
	private static Logger logger = Logger.getLogger(RealPrice.class);
	
	private PriceService priceService = (PriceService) ServiceFactory.getService("priceService");
	
//	private PriceVolatility basicPrice = null;
//	private PriceVolatility adjustPrice = null ;
//	private PriceVolatility activityPrice = null;
	
//	private Map<String, PriceVolatility> prices = new HashMap<String, PriceVolatility>();
	
	public interface Priority {
		final String ACTIVITY = "1";
		final String VOLATILE = "2";
		final String BASIC = "3";
	}
	
	public interface PriceType {
		final static String DATE = "1";
		final static String HOUR = "2";
		final static String PERIOD = "3";
	}
	
	
	
	private Double basicPrice = 0D;
	private Double volatilePrice = 0D;
	private Double activityPrice = 0D;
	private Double roomPrice = 0D;
	
	private String branchId;
	private String theme;
	private String roomType;
	private String rpId;
	private String priceType;
	private String memoTime;
	private String memberId;
	
	private Deque<PriceVolatility> qavtivityPrice = new ArrayDeque<PriceVolatility>();
	private Deque<PriceVolatility> qadjustPrice = new ArrayDeque<PriceVolatility>();
	private Deque<PriceVolatility> qbasicPrice = new ArrayDeque<PriceVolatility>();
	
	private Double discount;
	
	

	public RealPrice(){
	}
	
	public RealPrice(String branchId, String theme, String roomType, String rpId, String priceType, String memoTime, String memberId){
		this.branchId = branchId;
		this.theme = theme;
		this.roomType = roomType;
		this.rpId = rpId;
		this.priceType = priceType;
		this.memoTime = memoTime;
		this.memberId = memberId;
		dealDiscount();
		getData();
		dealPrice();
	}

	public RealPrice(String branchId, String theme, String roomType, String rpId, String priceType, Date memoTime, String memberId){
		this.branchId = branchId;
		this.theme = theme;
		this.roomType = roomType;
		this.rpId = rpId;
		this.priceType = priceType;
		this.memoTime = DateUtil.d2s(memoTime, "yyyy/MM/dd HH:mm:ss");
		this.memberId = memberId;
		dealDiscount();
		getData();
		dealPrice();
	}
	
	private void getData(){
		List<?> list = priceService.getPrice(branchId, theme, roomType, "MSJ", priceType, memoTime);
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
				if(Priority.BASIC.equals(priority)){
					qbasicPrice.add(priceVolatility);
				} else if(Priority.VOLATILE.equals(priority)){
					qadjustPrice.add(priceVolatility);
				} else if(Priority.ACTIVITY.equals(priority)){
					qavtivityPrice.add(priceVolatility);
				}
			}
		}
	}
	
	/**
	 * 根据对应memberId获取会员，获取对应的折扣比率
	 * @throws Exception 
	 */
	public void setMemberDis(Member member) throws Exception{
			double disc = 1;
			String corp = member.getCorporationId();
			if(!StringUtils.isEmpty(corp)){
				if(member.getDiscount() != null){
					double temp =  member.getDiscount();
					disc = temp/100;
				}
			}else{
				String memberRank = member.getMemberRank();
				MemberRank mr = (MemberRank) priceService.findOneByProperties(MemberRank.class, "memberRank", memberRank);
				double temp = mr.getDiscount();
				disc = temp/100;
			}
			this.discount = disc;
	}
	
	/**
	 * 获取相对应的折扣比率
	 */
	public void setRankDiscount(){
		// 获取参数表中的房价码对应会员折扣率
		List<?> disList = priceService.findBySQL("queryrpdiscount", true);
		discount = 1D;
		boolean flag = true;
		for (Object object : disList) {
			Map<?, ?> map = (Map<?, ?>) object;
			if(this.rpId.equals(map.get("PARAM_NAME"))){
				flag = false;
				BigDecimal disco = (BigDecimal) map.get("DISCOUNT");
				discount = disco.doubleValue()/100;
			}
		}
		if(flag){
			logger.warn("折扣未查询到，恢复默认值,门市价。当前折扣价码：" + this.rpId);
		}
	}
	
	/**
	 * 根据获的会员还是Rank处理
	 */
	public void dealDiscount(){
		
		try {
			Member member = null;
			if(!StringUtils.isEmpty(this.memberId)){
				member = (Member) priceService.findOneByProperties(Member.class, "memberId", this.memberId);
			}
//			if(member == null){
//				logger.warn("未查到当前会员信息，会员编号：" + this.memberId + "!");
//			}
			if(member != null){
				setMemberDis(member);
			}else{
				setRankDiscount();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 如果被过滤掉，则返回false
	 * @param priceVolatility
	 * @return
	 */
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
		int hour = cl.get(Calendar.HOUR);
		boolean flag = false;
		//下面代码，默认查到的天数，都是生效
		if("1".equals(priceRules.getRulesPeriod())){ //判断时间
			flag = verifyDateWeekMonth(priceRules.getRulesPeriodDetails(), hour);
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
	
	@SuppressWarnings("unused")
	@Deprecated
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

	public void dealPrice() {
		PriceVolatility finalpriceVolatility = null;
		PriceVolatility basicpriceVolatility = qbasicPrice.poll();
		if(basicpriceVolatility != null){
			finalpriceVolatility = basicpriceVolatility;
			this.basicPrice = Double.valueOf(basicpriceVolatility.getRoomPrice());				
		}
		//PriceVolatility adjustpriceVolatility = qadjustPrice.poll();
		while (qadjustPrice.size() > 0){
			PriceVolatility adjustpriceVolatility = qadjustPrice.poll();
			if(verifyRule(adjustpriceVolatility)){

				finalpriceVolatility = adjustpriceVolatility;
				this.volatilePrice = Double.valueOf(adjustpriceVolatility.getRoomPrice());
				break;
			}
		}
		while (qavtivityPrice.size() > 0){
			PriceVolatility qavtivitypriceVolatility = qavtivityPrice.poll();
			if(verifyRule(qavtivitypriceVolatility)){
				finalpriceVolatility = qavtivitypriceVolatility;
				this.activityPrice = Double.valueOf(qavtivitypriceVolatility.getRoomPrice());
				break;
			}
		}
		if(finalpriceVolatility != null){
			this.roomPrice = finalpriceVolatility.getRoomPrice();			
		}
	}
	
	public Double checkRoomPrice() {
		if(this.activityPrice > 0){
			return (double)Math.round(roomPrice * 100) / 100;
		}else{
			return calcPrice(roomPrice);
		}
	}
	
	public Double getBasicPrice() {
		return calcPrice(basicPrice);
	}

	public Double getVolatilePrice() {
		return calcPrice(volatilePrice);
	}
	
	public Double getActivityPrice() {
		return (double)Math.round(activityPrice * 100) / 100;
	}
	
	public Double calcPrice(Double price){
		return (double)Math.round(this.discount * price * 100) / 100;
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
	
}
