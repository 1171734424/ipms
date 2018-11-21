package com.ideassoft.priceRuleUtile;


import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hsqldb.lib.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import com.ideassoft.bean.PriceVolatility;
import com.ideassoft.core.constants.CommonConstants;
import com.ideassoft.core.factory.ServiceFactory;
import com.ideassoft.crm.service.PriceRuleService;
import com.ideassoft.util.CloneUtil;

/**
 * @author zhengsj
 * @date 2018年5月15日
 * @version 1.0  
 */
public class BasePriceUtile {

	@Autowired
	private static PriceRuleService priceRuleService = (PriceRuleService) ServiceFactory.getService("priceRuleService");
	/**
	 * 当系统初始化时将所有房间价的基准价格save到房价汇总表中
	 * 
	 * @param branchId 门店编号
	 * @param theme 场景 1-酒店 2-公寓 3-民宿
	 * @param roomPrice 房价
	 * @return 返回 1为保存成功，-1为保存失败
	 */
	public static int addBasePrice(String branchId, String theme, String roomPrice) {
		
		int returnParam = 1;
		// 获取当前时间用于下面开始时间及记录时间使用
		Date date = new Date();
		// 获取当前日期并加上1000年 用于基本价格的有效时间
		Calendar calendar = Calendar.getInstance();
		calendar.add(calendar.YEAR, 1000);
		Calendar calendarS = Calendar.getInstance();
		calendarS.add(calendar.YEAR, -1000);
		PriceVolatility pv = null;
		// 循环存储价格
		pv = new PriceVolatility();
		// 判断数据格式是否为空为null 如果不为空且为null 将值设置到PriceVolatility中
		if (!StringUtils.isEmpty(branchId)) {
			pv.setBranchId(branchId);
		} else {
			returnParam = -1;
		}
		if (!StringUtils.isEmpty(theme)) {
			pv.setTheme(theme);
		} else {
			returnParam = -1;
		}
		if (!StringUtils.isEmpty(roomPrice)) {
			pv.setRoomPrice(Double.valueOf(roomPrice));
		} else {
			returnParam = -1;
		}
		// 将数据存入到PriceVolatility JavaBean中
		pv.setStartTime(calendarS.getTime());
		pv.setEndTime(calendar.getTime());
		pv.setRecordTime(date);
		pv.setPriceType(CommonConstants.VolatilityPriceType.DAILYRENT);
		pv.setPriceTypeDetail(CommonConstants.VolatilityPriceTypeDetail.ONEDAY);
		pv.setPriority(CommonConstants.VolatilityPricePriority.BEASEPRICE);
		pv.setRulesId(CommonConstants.VolatilityPriceRulesId.PriceRulesId);
		pv.setVolatilityId(priceRuleService.getSequence("SEQ_NEW_VOLATILITY"));
		pv.setRpId("MSJ");
		if (returnParam == 1) {
			priceRuleService.save(pv);
		}			
		return returnParam;
	}

	/**
	 * 当系统初始化时将所有公寓房间价的基准价格save到房价汇总表中
	 * 
	 * @param branchId 门店编号
	 * @param roomType 房型
	 * @param theme 场景 1-酒店 2-公寓 3-民宿
	 * @param roomPrice 房价
	 * @return 返回 1为保存成功，-1为保存失败
	 */
	public static int addApartmentBasePrice(String branchId, String roomType, String theme, String roomPrice) {
		
		int returnParam = 1;
		// 获取当前时间用于下面开始时间及记录时间使用
		Date date = new Date();
		// 获取当前日期并加上1000年 用于基本价格的有效时间
		Calendar calendar = Calendar.getInstance();
		calendar.add(calendar.YEAR, 1000);
		Calendar calendarS = Calendar.getInstance();
		calendarS.add(calendar.YEAR, -1000);
		PriceVolatility pv = null;
		// 循环存储价格
		pv = new PriceVolatility();
		// 判断数据格式是否为空为null 如果不为空且为null 将值设置到PriceVolatility中
		if (!StringUtils.isEmpty(branchId)) {
			pv.setBranchId(branchId);
		} else {
			returnParam = -1;
		}
		if (!StringUtils.isEmpty(theme)) {
			pv.setTheme(theme);
		} else {
			returnParam = -1;
		}
		if (!StringUtils.isEmpty(roomPrice)) {
			pv.setRoomPrice(Double.valueOf(roomPrice));
		} else {
			returnParam = -1;
		}
		// 将数据存入到PriceVolatility JavaBean中
		pv.setStartTime(calendarS.getTime());
		pv.setEndTime(calendar.getTime());
		pv.setRecordTime(date);
		pv.setRoomType(roomType);
		pv.setPriceType(CommonConstants.VolatilityPriceType.LONGRENT);
		pv.setPriceTypeDetail(CommonConstants.VolatilityPriceTypeDetail.ONEMONTH);
		pv.setPriority(CommonConstants.VolatilityPricePriority.BEASEPRICE);
		pv.setRulesId(CommonConstants.VolatilityPriceRulesId.PriceRulesId);
		pv.setVolatilityId(priceRuleService.getSequence("SEQ_NEW_VOLATILITY"));
		pv.setRpId("MSJ");
		if (returnParam == 1) {
			priceRuleService.save(pv);
		}			
		return returnParam;
	}
	
	/**
	 * 当系统初始化时将所有酒店房间价的基准价格save到房价汇总表中
	 * 
	 * @param branchId 门店编号
	 * @param roomType 房型
	 * @param theme 场景 1-酒店 2-公寓 3-民宿
	 * @param roomPrice 房价
	 * @return 返回 1为保存成功，-1为保存失败
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public static int addHotelBasePrice(String branchId, String roomType, String theme, String roomPrice, String hourPrice) throws ClassNotFoundException, IOException {
		
		int returnParam = 1;
		// 获取当前时间用于下面开始时间及记录时间使用
		Date date = new Date();
		// 获取当前日期并加上1000年 用于基本价格的有效时间
		Calendar calendar = Calendar.getInstance();
		calendar.add(calendar.YEAR, 1000);
		Calendar calendarS = Calendar.getInstance();
		calendarS.add(calendar.YEAR, -1000);
		PriceVolatility pv = null;
		// 循环存储价格
		pv = new PriceVolatility();
		// 判断数据格式是否为空为null 如果不为空且为null 将值设置到PriceVolatility中
		if (!StringUtils.isEmpty(branchId)) {
			pv.setBranchId(branchId);
		} else {
			returnParam = -1;
		}
		if (!StringUtils.isEmpty(theme)) {
			pv.setTheme(theme);
		} else {
			returnParam = -1;
		}
		if (!StringUtils.isEmpty(roomPrice)) {
			pv.setRoomPrice(Double.valueOf(roomPrice));
		} else {
			returnParam = -1;
		}
		// 将数据存入到PriceVolatility JavaBean中
		pv.setStartTime(calendarS.getTime());
		pv.setEndTime(calendar.getTime());
		pv.setRecordTime(date);
		pv.setRoomType(roomType);
		pv.setPriceType(CommonConstants.VolatilityPriceType.DAILYRENT);
		pv.setPriceTypeDetail(CommonConstants.VolatilityPriceTypeDetail.ONEMONTH);
		pv.setPriority(CommonConstants.VolatilityPricePriority.BEASEPRICE);
		pv.setRulesId(CommonConstants.VolatilityPriceRulesId.PriceRulesId);
		pv.setVolatilityId(priceRuleService.getSequence("SEQ_NEW_VOLATILITY"));
		pv.setRpId("MSJ");
		
		PriceVolatility pv1 = (PriceVolatility) CloneUtil.deepClone(pv);
		pv1.setVolatilityId(priceRuleService.getSequence("SEQ_NEW_VOLATILITY"));
		pv1.setPriceType("2");
		pv1.setRoomPrice(Double.valueOf(hourPrice));
		if (returnParam == 1) {
			priceRuleService.save(pv);
			priceRuleService.save(pv1);
		}			
		return returnParam;
	}
	
	/**
	 * 当民宿的基准价格所有调整的时候，需要将调整后状态为激活的价格存入到房价汇总表中
	 * 
	 * @param branchId 门店编号
	 * @param roomPrice 房价
	 * @return 返回 1为保存成功，-1为保存失败
	 */
	public static int updateBasePrice(String branchId, String roomPrice) {
		int returnParam = 1;
		// 获取当前时间用于下面开始时间及记录时间使用
		Date date = new Date();
		// 查询PriceVolatility原有数据，准备下述修改价格
		PriceVolatility pv = (PriceVolatility) priceRuleService.findOneByProperties(PriceVolatility.class, "branchId", branchId,"rpId","MSJ","priority","3");
		// 判断数据格式是否为空为null 如果不为空且为null 将值设置到PriceVolatility中
		if (!StringUtil.isEmpty(roomPrice)) {
			pv.setRoomPrice(Double.valueOf(roomPrice));
		}else {
			returnParam = -1;
		}
		// 将数据存入到PriceVolatility JavaBean中
		pv.setRecordTime(date);
		if (returnParam == 1) {
			priceRuleService.update(pv);
		}
		return returnParam;
	}
	
	/**
	 * 当公寓酒店的基准价格所有调整的时候，需要将调整后状态为激活的价格存入到房价汇总表中
	 * 
	 * @param branchId 门店编号
	 * @param roomPrice 房价
	 * @param roomType 房型码
	 * @return 返回 1为保存成功，-1为保存失败
	 */
	public static int updateAdjustPrice(String branchId, String roomPrice,String priceType, String roomType) {
		int returnParam = 1;
		// 获取当前时间用于下面开始时间及记录时间使用
		Date date = new Date();
		// 查询PriceVolatility原有数据，准备下述修改价格
		PriceVolatility pv = (PriceVolatility) priceRuleService.findOneByProperties(PriceVolatility.class, "branchId", branchId,"rpId","MSJ","priority", "3", "priceType", priceType,"roomType",roomType);
		// 判断数据格式是否为空为null 如果不为空且为null 将值设置到PriceVolatility中
		if (!StringUtil.isEmpty(roomPrice)) {
			pv.setRoomPrice(Double.valueOf(roomPrice));
		}else {
			returnParam = -1;
		}
		// 将数据存入到PriceVolatility JavaBean中
		pv.setRecordTime(date);
		if (returnParam == 1) {
			priceRuleService.update(pv);
		}
		return returnParam;
	}
}
