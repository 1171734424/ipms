package com.ideassoft.price;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.ideassoft.bean.Order;
import com.ideassoft.util.DateUtil;

/**
 *  NOTE：********MultiPrice用来提取一段时间的价格，SinglePrice用来提取某天价格*************
 */
public class MultiPrice implements IPrice{
	

	private List<RealPrice> realPrices = null;
	
	/**
	 * 
	 * @param order 订单对象 
	 * 价格类型默认日租
	 * 弃用原因：06/01-06/02 注意最后一天，是否应该添加。
	 */
	@Deprecated
	public MultiPrice(Order order){
		//默认价格日租
		dealOrder(order, RealPrice.PriceType.DATE);
	}
	
	/**
	 * 
	 * @param order 订单对象
	 * @param priceType 价格类型 1-日租 2-时租 3-长租
	 * 弃用原因：06/01-06/02 注意最后一天，是否应该添加。
	 */
	@Deprecated
	public MultiPrice(Order order, String priceType){
		dealOrder(order, priceType);
	}
	
	/**
	 * 
	 * @param branchId 门店编号
	 * @param theme 场景 1-酒店 2-公寓 3-民宿
	 * @param roomType 房型（适用于酒店、公寓） 民宿（null)
	 * @param rpId 房价码
	 * @param priceType 价格类型 1-日租 2-时租 3-长租
	 * @param startTime, endTime  一段日期（格式：yyyy/MM/dd HH:mm:ss, 2018/05/14 13:14:20）
	 */
	public MultiPrice(String branchId, String theme, String roomType, String rpId, String priceType, String startTime, String endTime){
		init(branchId, theme, roomType, rpId, priceType, DateUtil.s2d(startTime, "yyyy/MM/dd HH:mm:ss"), DateUtil.s2d(endTime, "yyyy/MM/dd HH:mm:ss"));
	}
	
	/**
	 * 
	 * @param branchId 门店编号
	 * @param theme 场景 1-酒店 2-公寓 3-民宿
	 * @param roomType 房型（适用于酒店、公寓） 民宿（null)
	 * @param rpId 房价码
	 * @param priceType 价格类型 1-日租 2-时租 3-长租
	 * @param startTime， endTime  一段日期（格式：Date）
	 */
	public MultiPrice(String branchId, String theme, String roomType, String rpId, String priceType, Date startTime, Date endTime){
		init(branchId, theme, roomType, rpId, priceType, startTime, endTime);
	}
	
	@Deprecated
	public void dealOrder(Order order, String priceType){
		if(order == null){
			try {
				throw new Exception("Order订单空指针异常，检查传入的order是否为null");
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}
		/*if(StringUtils.isEmpty(order.getBranchId())){
			throw new Exception("Order中的branchId空异常");
		}*/
		String roomType = null;
		if(!"3".equals(order.getTheme())){
			roomType = order.getRoomType();
		}
		init(order.getBranchId(), order.getTheme(), roomType, order.getRpId(), "1", order.getArrivalTime(), order.getLeaveTime());
	}
	
	public void  init(String branchId, String theme, String roomType, String rpId, String priceType, Date startTime, Date endTime){
		int size = DateUtil.daysOfTwo(startTime, endTime);
		if(size >= 0){
			realPrices = new ArrayList<RealPrice>();
		}else{
			try {
				throw new Exception("传入MultiPrice参数的开始日期大于结束日期，请检查开始日期和结束是否设置相反");
			} catch (Exception e) {
				System.out.println("开始日期： " + DateUtil.d2s(startTime));
				System.out.println("结束日期： " + DateUtil.d2s(endTime));
				e.printStackTrace();
			}
		}
		Calendar clstart = Calendar.getInstance();
		clstart.setTime(startTime);
		for(int i = 0; i <= size; i++){
			Date memoTime = clstart.getTime();
			RealPrice rp = new RealPrice(branchId, theme, roomType, rpId, priceType, memoTime, null);
			clstart.add(Calendar.DATE, 1);
			realPrices.add(rp);
		}
	}

	/**
	 * 获取一段时间房间的总价格（价格按照活动-调整-基准优先权来获取）
	 */
	public Double checkRoomPrice() {
		Double roomPrice = 0D;
		for (RealPrice realPrice : realPrices) {
			roomPrice += realPrice.checkRoomPrice();
		}
		return roomPrice;
	}

	/**
	 * 获取一段时间房间的活动总价
	 */
	public Double getActivityPrice() {
		Double roomPrice = 0D;
		for (RealPrice realPrice : realPrices) {
			roomPrice += realPrice.getActivityPrice();
		}
		return roomPrice;
	}

	/**
	 * 获取一段时间房间的基总准价
	 */
	public Double getBasicPrice() {
		Double roomPrice = 0D;
		for (RealPrice realPrice : realPrices) {
			roomPrice += realPrice.getBasicPrice();
		}
		return roomPrice;
	}

	/**
	 * 获取一段时间房间的调整总价
	 */
	public Double getVolatilePrice() {
		Double roomPrice = 0D;
		for (RealPrice realPrice : realPrices) {
			roomPrice += realPrice.getVolatilePrice();
		}
		return roomPrice;
	}

}
