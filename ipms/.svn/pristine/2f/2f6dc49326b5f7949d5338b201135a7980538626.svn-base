package com.ideassoft.price;

import java.util.Date;

/**
 *  NOTE：********MultiPrice用来提取一段时间的价格，SinglePrice用来提取某天价格*************
 */
public class SinglePrice implements IPrice{
	

	private RealPrice realPrice;
	
	public SinglePrice(){
		
	}
	
	/**
	 * 
	 * @param branchId 门店编号
	 * @param theme 场景 1-酒店 2-公寓 3-民宿
	 * @param roomType 房型（适用于酒店、公寓） 民宿（null)
	 * @param rpId 房价码
	 * @param priceType 价格类型 1-日租 2-时租 3-长租
	 * @param memoTime 某天日期（格式：yyyy/MM/dd HH:mm:ss, 2018/05/14 13:14:20）
	 */
	public SinglePrice(String branchId, String theme, String roomType, String rpId, String priceType, String memoTime){
		if(realPrice == null){
			realPrice = new RealPrice(branchId, theme, roomType, rpId, priceType, memoTime, null);
			//将生成的realPrice提取活动价进行判断，大于0说明有活动价，此时在次获取价格，价格为MSJ不添加折扣
			/*Double activityprice = this.realPrice.getActivityPrice();
			if(activityprice > 0){
				this.realPrice = new RealPrice(branchId, theme, roomType, rpId, priceType, memoTime);
			}*/
		}
	}
	
	/**
	 * 
	 * @param branchId 门店编号
	 * @param theme 场景 1-酒店 2-公寓 3-民宿
	 * @param roomType 房型（适用于酒店、公寓） 民宿（null)
	 * @param rpId 房价码
	 * @param priceType 价格类型 1-日租 2-时租 3-长租
	 * @param memoTime 某天日期（格式：Date）
	 */
	public SinglePrice(String branchId, String theme, String roomType, String rpId, String priceType, Date memoTime){
		if(realPrice == null){
			realPrice = new RealPrice(branchId, theme, roomType, rpId, priceType, memoTime, null);
			//将生成的realPrice提取活动价进行判断，大于0说明有活动价，此时在次获取价格，价格为MSJ不添加折扣
			/*Double activityprice = this.realPrice.getActivityPrice();
			if(activityprice > 0){
				this.realPrice = new RealPrice(branchId, theme, roomType, rpId, priceType, memoTime);
			}*/
		}
	}
	
	/**
	 * 
	 * @param branchId 门店编号
	 * @param theme 场景 1-酒店 2-公寓 3-民宿
	 * @param roomType 房型（适用于酒店、公寓） 民宿（null)
	 * @param rpId 房价码
	 * @param priceType 价格类型 1-日租 2-时租 3-长租
	 * @param memoTime 某天日期（格式：yyyy/MM/dd HH:mm:ss, 2018/05/14 13:14:20）
	 * @param memberId 会员编号 
	 */
	public SinglePrice(String branchId, String theme, String roomType, String rpId, String priceType, String memoTime, String memberId){
		if(realPrice == null){
			realPrice = new RealPrice(branchId, theme, roomType, rpId, priceType, memoTime, memberId);
			//将生成的realPrice提取活动价进行判断，大于0说明有活动价，此时在次获取价格，价格为MSJ不添加折扣
			/*Double activityprice = this.realPrice.getActivityPrice();
			if(activityprice > 0){
				this.realPrice = new RealPrice(branchId, theme, roomType, rpId, priceType, memoTime);
			}*/
		}
	}
	
	/**
	 * 
	 * @param branchId 门店编号
	 * @param theme 场景 1-酒店 2-公寓 3-民宿
	 * @param roomType 房型（适用于酒店、公寓） 民宿（null)
	 * @param rpId 房价码
	 * @param priceType 价格类型 1-日租 2-时租 3-长租
	 * @param memoTime 某天日期（格式：Date）
	 * @param memberId 会员编号
	 */
	public SinglePrice(String branchId, String theme, String roomType, String rpId, String priceType, Date memoTime, String memberId){
		if(realPrice == null){
			realPrice = new RealPrice(branchId, theme, roomType, rpId, priceType, memoTime, memberId);
			//将生成的realPrice提取活动价进行判断，大于0说明有活动价，此时在次获取价格，价格为MSJ不添加折扣
			/*Double activityprice = this.realPrice.getActivityPrice();
			if(activityprice > 0){
				this.realPrice = new RealPrice(branchId, theme, roomType, rpId, priceType, memoTime);
			}*/
		}
	}
	
	public void  init(){
		
	}

	/**
	 * 获取房间的价格（价格按照活动-调整-基准优先权来获取）
	 */
	public Double checkRoomPrice() {
		//realPrice.dealPrice();
		return realPrice.checkRoomPrice();
	}

	/**
	 * 获取房间的活动价
	 */
	public Double getActivityPrice() {
		//realPrice.dealPrice();
		return realPrice.getActivityPrice();
	}

	/**
	 * 获取房间的基准价
	 */
	public Double getBasicPrice() {
		//realPrice.dealPrice();
		return realPrice.getBasicPrice();
	}

	/**
	 * 获取房间的调整价
	 */
	public Double getVolatilePrice() {
		//realPrice.dealPrice();
		return realPrice.getVolatilePrice();
	}

}
