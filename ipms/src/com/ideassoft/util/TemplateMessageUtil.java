package com.ideassoft.util;

import java.util.TreeMap;

import org.json.JSONObject;

import com.ideassoft.bean.templateMessage.BeMemberMsg;
import com.ideassoft.bean.templateMessage.BookingFailMsg;
import com.ideassoft.bean.templateMessage.BookingSuccessMsg;
import com.ideassoft.bean.templateMessage.ChargeMsg;
import com.ideassoft.bean.templateMessage.CheckoutApplyNoticeMsg;
import com.ideassoft.bean.templateMessage.CheckoutSuccessNoticeMsg;
import com.ideassoft.bean.templateMessage.HotelCheckinNoticeMsg;
import com.ideassoft.bean.templateMessage.HouseOrderCancelMsg;
import com.ideassoft.bean.templateMessage.MemberConsumeMsg;
import com.ideassoft.bean.templateMessage.OrderCancelMsg;
import com.ideassoft.bean.templateMessage.OrderSubmitSuccessNoticeMsg;
import com.ideassoft.bean.templateMessage.PayFailMsg;
import com.ideassoft.bean.templateMessage.PaySuccessMsg;
import com.ideassoft.bean.templateMessage.ServiceMsg;
import com.ideassoft.bean.templateMessage.TemplateMessageExample;
import com.ideassoft.bean.templateMessage.WechatMessageConstants;





public class TemplateMessageUtil {
	/**
	 * 酒店入住提醒
	 * 
	 * @param token
	 * @param openId
	 * @param hotelCheckinNoticeMsg
	 * @return
	 */
	public static JSONObject sendHotelCheckinNoticeMsg(String token, String openId,
			HotelCheckinNoticeMsg hotelCheckinNoticeMsg) {
		String templateId = WechatMessageConstants.CHECKIN_NOTICE;
		TreeMap<String, TreeMap<String, String>> params = new TreeMap<String, TreeMap<String, String>>();
		params.put("first", TemplateMessageExample.item(hotelCheckinNoticeMsg.getFirst(), hotelCheckinNoticeMsg
				.getFirstColor()));
		params.put("OrderID", TemplateMessageExample.item(hotelCheckinNoticeMsg.getOrderID(), hotelCheckinNoticeMsg
				.getOrderIDColor()));
		params.put("HotelName", TemplateMessageExample.item(hotelCheckinNoticeMsg.getHotelName(), hotelCheckinNoticeMsg
				.getHotelNameColor()));
		params.put("CheckInDate", TemplateMessageExample.item(hotelCheckinNoticeMsg.getCheckInDate(),
				hotelCheckinNoticeMsg.getCheckInDateColor()));
		params.put("CheckOutDate", TemplateMessageExample.item(hotelCheckinNoticeMsg.getCheckOutDate(),
				hotelCheckinNoticeMsg.getCheckOutDateColor()));
		params.put("remark", TemplateMessageExample.item(hotelCheckinNoticeMsg.getRemark(), hotelCheckinNoticeMsg
				.getRemarkColor()));

		TemplateMessageExample tme = new TemplateMessageExample();
		tme.setTemplate_id(templateId);
		tme.setTouser(openId);
		tme.setData(params);

		JSONObject jsontme = new JSONObject(tme);
		JSONObject jo = WeixinUtil.sendTemplateMsg(token, jsontme.toString());
		return jo;
	}

	/**
	 * 退房申请提醒 {{first.DATA}} 酒店名称：{{keyword1.DATA}} 预约时间：{{keyword2.DATA}}
	 * 房间号：{{keyword3.DATA}} {{remark.DATA}}
	 * 
	 * @param token
	 * @param openId
	 * @param checkoutApplyNoticeMsg
	 * @return
	 */
	public static JSONObject sendCheckoutApplyNoticeMsg(String token, String openId,
			CheckoutApplyNoticeMsg checkoutApplyNoticeMsg) {
		String templateId = WechatMessageConstants.CHECKOUT_APPLY_NOTICE;
		TreeMap<String, TreeMap<String, String>> params = new TreeMap<String, TreeMap<String, String>>();
		params.put("first", TemplateMessageExample.item(checkoutApplyNoticeMsg.getFirst(), checkoutApplyNoticeMsg
				.getFirstColor()));
		params.put("keyword1", TemplateMessageExample.item(checkoutApplyNoticeMsg.getBranchName(),
				checkoutApplyNoticeMsg.getBranchNameColor()));
		params.put("keyword2", TemplateMessageExample.item(checkoutApplyNoticeMsg.getBookingDate(),
				checkoutApplyNoticeMsg.getBookingDateColor()));
		params.put("keyword3", TemplateMessageExample.item(checkoutApplyNoticeMsg.getRoomId(), checkoutApplyNoticeMsg
				.getRoomIdColor()));
		params.put("remark", TemplateMessageExample.item(checkoutApplyNoticeMsg.getRemark(), checkoutApplyNoticeMsg
				.getRemarkColor()));

		TemplateMessageExample tme = new TemplateMessageExample();
		tme.setTemplate_id(templateId);
		tme.setTouser(openId);
		tme.setData(params);

		JSONObject jsontme = new JSONObject(tme);
		JSONObject jo = WeixinUtil.sendTemplateMsg(token, jsontme.toString());
		return jo;
	}

	/**
	 * 恭喜您支付成功 {{first.DATA}} 支付金额：{{keyword1.DATA}} 支付时间：{{keyword2.DATA}}
	 * 支付方式：{{keyword3.DATA}} {{remark.DATA}}
	 * 
	 * @param token
	 * @param openId
	 * @param paySuccessMsg
	 * @return
	 */
	public static JSONObject sendPaySuccessMsg(String token, String openId, PaySuccessMsg paySuccessMsg) {
		String templateId = WechatMessageConstants.PAY_SUCCESS;
		TreeMap<String, TreeMap<String, String>> params = new TreeMap<String, TreeMap<String, String>>();
		params.put("first", TemplateMessageExample.item(paySuccessMsg.getFirst(), paySuccessMsg.getFirstColor()));
		params.put("keyword1", TemplateMessageExample.item(paySuccessMsg.getPay(), paySuccessMsg.getPayColor()));
		params
				.put("keyword2", TemplateMessageExample.item(paySuccessMsg.getPayDate(), paySuccessMsg
						.getPayDateColor()));
		params.put("keyword3", TemplateMessageExample.item(paySuccessMsg.getPayWay(), paySuccessMsg.getPayWayColor()));
		params.put("remark", TemplateMessageExample.item(paySuccessMsg.getRemark(), paySuccessMsg.getReamrkColor()));

		TemplateMessageExample tme = new TemplateMessageExample();
		tme.setTemplate_id(templateId);
		tme.setTouser(openId);
		tme.setData(params);

		JSONObject jsontme = new JSONObject(tme);
		JSONObject jo = WeixinUtil.sendTemplateMsg(token, jsontme.toString());
		return jo;
	}

	/**
	 * 支付失败提醒 {{first.DATA}}
	 * 
	 * 订单号：{{folioId.DATA}}，支付失败 {{remark.DATA}}
	 * 
	 * @param token
	 * @param openId
	 * @param payFailMsg
	 * @return
	 */
	public static JSONObject sendPayFailMsg(String token, String openId, PayFailMsg payFailMsg) {
		String templateId = WechatMessageConstants.PAY_FAIL;
		TreeMap<String, TreeMap<String, String>> params = new TreeMap<String, TreeMap<String, String>>();
		params.put("first", TemplateMessageExample.item(payFailMsg.getFirst(), payFailMsg.getFirstColor()));
		params.put("folioId", TemplateMessageExample.item(payFailMsg.getFolioId(), payFailMsg.getFolioIdColor()));
		params.put("remark", TemplateMessageExample.item(payFailMsg.getRemark(), payFailMsg.getRemarkColor()));

		TemplateMessageExample tme = new TemplateMessageExample();
		tme.setTemplate_id(templateId);
		tme.setTouser(openId);
		tme.setData(params);

		JSONObject jsontme = new JSONObject(tme);
		JSONObject jo = WeixinUtil.sendTemplateMsg(token, jsontme.toString());
		return jo;
	}

	/**
	 * 订单提交成功通知 {{first.DATA}} 订单号：{{keyword1.DATA}} 预订人：{{keyword2.DATA}}
	 * {{remark.DATA}}
	 * 
	 * @param token
	 * @param openId
	 * @param orderSubmitSuccessNoticeMsg
	 * @return
	 */
	public static JSONObject sendOrderSubmitSuccessNoticeMsg(String token, String openId,
			OrderSubmitSuccessNoticeMsg orderSubmitSuccessNoticeMsg) {
		String templateId = WechatMessageConstants.ORDER_SUBMIT_SUCCESS_NOTICE;
		TreeMap<String, TreeMap<String, String>> params = new TreeMap<String, TreeMap<String, String>>();
		params.put("first", TemplateMessageExample.item(orderSubmitSuccessNoticeMsg.getFirst(),
				orderSubmitSuccessNoticeMsg.getFirstColor()));
		params.put("keyword1", TemplateMessageExample.item(orderSubmitSuccessNoticeMsg.getOrderId(),
				orderSubmitSuccessNoticeMsg.getOrderIdColor()));
		params.put("keyword2", TemplateMessageExample.item(orderSubmitSuccessNoticeMsg.getMemberName(),
				orderSubmitSuccessNoticeMsg.getMemberNameColor()));
		params.put("remark", TemplateMessageExample.item(orderSubmitSuccessNoticeMsg.getRemark(),
				orderSubmitSuccessNoticeMsg.getRemarkColor()));

		TemplateMessageExample tme = new TemplateMessageExample();
		tme.setTemplate_id(templateId);
		tme.setTouser(openId);
		tme.setData(params);

		JSONObject jsontme = new JSONObject(tme);
		JSONObject jo = WeixinUtil.sendTemplateMsg(token, jsontme.toString());
		return jo;
	}

	/**
	 * 服务通知 {{first.DATA}} 酒店房号：{{keyword1.DATA}} 服务类型：{{keyword2.DATA}}
	 * 服务内容：{{keyword3.DATA}} {{remark.DATA}}
	 * 
	 * @param token
	 * @param openId
	 * @param serviceMsg
	 * @return
	 */
	public static JSONObject sendServiceMsg(String token, String openId, ServiceMsg serviceMsg) {
		String templateId = WechatMessageConstants.SERVICE;
		TreeMap<String, TreeMap<String, String>> params = new TreeMap<String, TreeMap<String, String>>();
		params.put("first", TemplateMessageExample.item(serviceMsg.getFirst(), serviceMsg.getFirstColor()));
		params.put("keyword1", TemplateMessageExample.item(serviceMsg.getRoomId(), serviceMsg.getRoomIdColor()));
		params.put("keyword2", TemplateMessageExample.item(serviceMsg.getServiceType(), serviceMsg.getServiceTypeColor()));
		params.put("keyword3", TemplateMessageExample.item(serviceMsg.getServiceContent(), serviceMsg.getServiceContentColor()));
		params.put("remark", TemplateMessageExample.item(serviceMsg.getRemark(), serviceMsg.getReamrkColor()));

		TemplateMessageExample tme = new TemplateMessageExample();
		tme.setTemplate_id(templateId);
		tme.setTouser(openId);
		tme.setData(params);

		JSONObject jsontme = new JSONObject(tme);
		JSONObject jo = WeixinUtil.sendTemplateMsg(token, jsontme.toString());
		return jo;
	}

	/**
	 * 预定失败通知 {{first.DATA}} 服务人员：{{keyword1.DATA}} 预约时间：{{keyword2.DATA}}
	 * {{remark.DATA}}
	 * 
	 * @param token
	 * @param openId
	 * @param bookingFailMsg
	 * @return
	 */
	public static JSONObject sendBookingFailMsg(String token, String openId, BookingFailMsg bookingFailMsg) {
		String templateId = WechatMessageConstants.BOOKING_FAIL;
		TreeMap<String, TreeMap<String, String>> params = new TreeMap<String, TreeMap<String, String>>();
		params.put("first", TemplateMessageExample.item(bookingFailMsg.getFirst(), bookingFailMsg.getFirstColor()));
		params.put("keyword1", TemplateMessageExample.item(bookingFailMsg.getStaff(), bookingFailMsg.getStaffColor()));
		params.put("keyword2", TemplateMessageExample.item(bookingFailMsg.getBookingDate(), bookingFailMsg
				.getBookingDateColor()));
		params.put("remark", TemplateMessageExample.item(bookingFailMsg.getRemark(), bookingFailMsg.getRemarkColor()));

		TemplateMessageExample tme = new TemplateMessageExample();
		tme.setTemplate_id(templateId);
		tme.setTouser(openId);
		tme.setData(params);

		JSONObject jsontme = new JSONObject(tme);
		JSONObject jo = WeixinUtil.sendTemplateMsg(token, jsontme.toString());
		return jo;
	}

	/**
	 * 会员注册成功通知 {{first.DATA}} 会员号：{{keyword1.DATA}} 会员名：{{keyword2.DATA}}
	 * 手机号：{{keyword3.DATA}} {{remark.DATA}}
	 * 
	 * @param token
	 * @param openId
	 * @param beMemberMsg
	 * @return
	 */
	public static JSONObject sendBeMemberMsg(String token, String openId, BeMemberMsg beMemberMsg) {
		String templateId = WechatMessageConstants.BE_MEMBER;
		TreeMap<String, TreeMap<String, String>> params = new TreeMap<String, TreeMap<String, String>>();
		params.put("first", TemplateMessageExample.item(beMemberMsg.getFirst(), beMemberMsg.getFirstColor()));
		params.put("keyword1", TemplateMessageExample.item(beMemberMsg.getCardNumber(), beMemberMsg
				.getCardNumberColor()));
		params.put("keyword2", TemplateMessageExample.item(beMemberMsg.getVIPName(), beMemberMsg.getVIPNameColor()));
		params.put("keyword3", TemplateMessageExample.item(beMemberMsg.getVIPPhone(), beMemberMsg.getVIPPhoneColor()));
		params.put("remark", TemplateMessageExample.item(beMemberMsg.getRemark(), beMemberMsg.getRemarkColor()));

		TemplateMessageExample tme = new TemplateMessageExample();
		tme.setTemplate_id(templateId);
		tme.setTouser(openId);
		tme.setData(params);

		JSONObject jsontme = new JSONObject(tme);
		JSONObject jo = WeixinUtil.sendTemplateMsg(token, jsontme.toString());
		return jo;
	}

	/**
	 * 预约成功通知
	 * 
	 * {{first.DATA}} 预约时间：{{keyword1.DATA}} 预约人：{{keyword2.DATA}}
	 * 预约电话：{{keyword3.DATA}} {{remark.DATA}}
	 * 
	 * @param token
	 * @param openId
	 * @param bookingSuccessMsg
	 * @return
	 */
	public static JSONObject sendBookingSuccessMsg(String token, String openId, BookingSuccessMsg bookingSuccessMsg) {
		String templateId = WechatMessageConstants.BOOKING_SUCCESS;
		TreeMap<String, TreeMap<String, String>> params = new TreeMap<String, TreeMap<String, String>>();
		params.put("first", TemplateMessageExample
				.item(bookingSuccessMsg.getFirst(), bookingSuccessMsg.getFirstColor()));
		params.put("keyword1", TemplateMessageExample.item(bookingSuccessMsg.getBookingDate(), bookingSuccessMsg
				.getBookingDateColor()));
		params.put("keyword2", TemplateMessageExample.item(bookingSuccessMsg.getMemberName(), bookingSuccessMsg
				.getMemberNameColor()));
		params.put("keyword3", TemplateMessageExample.item(bookingSuccessMsg.getMemberPhone(), bookingSuccessMsg
				.getMemberPhoneColor()));
		params.put("remark", TemplateMessageExample.item(bookingSuccessMsg.getRemark(), bookingSuccessMsg
				.getReamrkColor()));

		TemplateMessageExample tme = new TemplateMessageExample();
		tme.setTemplate_id(templateId);
		tme.setTouser(openId);
		tme.setData(params);

		JSONObject jsontme = new JSONObject(tme);
		JSONObject jo = WeixinUtil.sendTemplateMsg(token, jsontme.toString());
		return jo;
	}

	/**
	 * 会员充值通知 {{first.DATA}} {{accountType.DATA}}：{{account.DATA}}
	 * 充值金额：{{amount.DATA}} 充值状态：{{result.DATA}} {{remark.DATA}}
	 * 
	 * @param token
	 * @param openId
	 * @return
	 */
	public static JSONObject sendChargeMsg(String token, String openId, ChargeMsg chargeMsg) {
		String templateId = WechatMessageConstants.CHARGE;
		TreeMap<String, TreeMap<String, String>> params = new TreeMap<String, TreeMap<String, String>>();
		params.put("first", TemplateMessageExample.item(chargeMsg.getFirst(), chargeMsg.getFirstColor()));
		params.put("accountType", TemplateMessageExample.item(chargeMsg.getAccountType(), chargeMsg
				.getAccountTypeColor()));
		params.put("account", TemplateMessageExample.item(chargeMsg.getAccount(), chargeMsg.getAccountColor()));
		params.put("amount", TemplateMessageExample.item(chargeMsg.getAmount(), chargeMsg.getAmountColor()));
		params.put("result", TemplateMessageExample.item(chargeMsg.getResult(), chargeMsg.getResultColor()));
		params.put("remark", TemplateMessageExample.item(chargeMsg.getRemark(), chargeMsg.getRemarkColor()));

		TemplateMessageExample tme = new TemplateMessageExample();
		tme.setTemplate_id(templateId);
		tme.setTouser(openId);
		tme.setData(params);

		JSONObject jsontme = new JSONObject(tme);
		JSONObject jo = WeixinUtil.sendTemplateMsg(token, jsontme.toString());
		return jo;
	}

	/**
	 * 退房成功提醒 {{first.DATA}} 酒店名称：{{keyword1.DATA}} 房间号码：{{keyword2.DATA}}
	 * {{remark.DATA}}
	 * 
	 * @param token
	 * @param openId
	 * @param checkoutSuccessNoticeMsg
	 * @return
	 */
	public static JSONObject sendCheckoutSuccessNoticeMsg(String token, String openId,
			CheckoutSuccessNoticeMsg checkoutSuccessNoticeMsg) {
		String templateId = WechatMessageConstants.CHECKOUT_SUCCESS_NOTICE;
		TreeMap<String, TreeMap<String, String>> params = new TreeMap<String, TreeMap<String, String>>();
		params.put("first", TemplateMessageExample.item(checkoutSuccessNoticeMsg.getFirst(), checkoutSuccessNoticeMsg
				.getFirstColor()));
		params.put("keyword1", TemplateMessageExample.item(checkoutSuccessNoticeMsg.getBranchName(),
				checkoutSuccessNoticeMsg.getBranchNameColor()));
		params.put("keyword2", TemplateMessageExample.item(checkoutSuccessNoticeMsg.getRoomId(),
				checkoutSuccessNoticeMsg.getRoomIdColor()));
		params.put("remark", TemplateMessageExample.item(checkoutSuccessNoticeMsg.getRemark(), checkoutSuccessNoticeMsg
				.getRemarkColor()));

		TemplateMessageExample tme = new TemplateMessageExample();
		tme.setTemplate_id(templateId);
		tme.setTouser(openId);
		tme.setData(params);

		JSONObject jsontme = new JSONObject(tme);
		JSONObject jo = WeixinUtil.sendTemplateMsg(token, jsontme.toString());
		return jo;
	}

	/**
	 * 会员消费通知 您好，您已成功消费。 {{productType.DATA}}：{{name.DATA}}
	 * 消费{{accountType.DATA}}：{{account.DATA}} 消费时间：{{time.DATA}}
	 * {{remark.DATA}}
	 * 
	 * @param token
	 * @param openId
	 * @param memberConsumeMsg
	 * @return
	 */
	public static JSONObject sendMemberConsumeMsg(String token, String openId, MemberConsumeMsg memberConsumeMsg) {
		String templateId = WechatMessageConstants.MEMBER_CONSUME;
		TreeMap<String, TreeMap<String, String>> params = new TreeMap<String, TreeMap<String, String>>();
		params.put("productType", TemplateMessageExample.item(memberConsumeMsg.getProductType(), memberConsumeMsg
				.getProductTypeColor()));
		params.put("name", TemplateMessageExample.item(memberConsumeMsg.getName(), memberConsumeMsg.getNameColor()));
		params.put("accountType", TemplateMessageExample.item(memberConsumeMsg.getAccountType(), memberConsumeMsg
				.getAccountTypeColor()));
		params.put("account", TemplateMessageExample.item(memberConsumeMsg.getAccount(), memberConsumeMsg
				.getAccountColor()));
		params.put("time", TemplateMessageExample.item(memberConsumeMsg.getTime(), memberConsumeMsg.getTimeColor()));
		params.put("remark", TemplateMessageExample.item(memberConsumeMsg.getRemark(), memberConsumeMsg
				.getRemarkColor()));

		TemplateMessageExample tme = new TemplateMessageExample();
		tme.setTemplate_id(templateId);
		tme.setTouser(openId);
		tme.setData(params);

		JSONObject jsontme = new JSONObject(tme);
		JSONObject jo = WeixinUtil.sendTemplateMsg(token, jsontme.toString());
		return jo;
	}
	
	
	/**
	 * 订单取消通知 {{first.DATA}} 订单号：{{keyword1.DATA}} 时间：{{keyword2.DATA}}
	 * {{remark.DATA}}
	 * 
	 * @param token
	 * @param openId
	 * @param orderSubmitSuccessNoticeMsg
	 * @return
	 */
	public static JSONObject sendOrderCancelSuccessNoticeMsg(String token,String openId,OrderCancelMsg orderCancelMsg) {
		String templateId = WechatMessageConstants.ORDER_CANCEL;
		TreeMap<String, TreeMap<String, String>> params = new TreeMap<String, TreeMap<String, String>>();
		params.put("first", TemplateMessageExample.item(
				orderCancelMsg.getFirst(),
				orderCancelMsg.getFirstColor()));
		params.put("keyword1", TemplateMessageExample.item(
				orderCancelMsg.getOrderId(),
				orderCancelMsg.getOrderIdColor()));
		params.put("keyword2", TemplateMessageExample.item(
				orderCancelMsg.getTime(),
				orderCancelMsg.getTimeColor()));
		params.put("remark", TemplateMessageExample.item(
				orderCancelMsg.getRemark(),
				orderCancelMsg.getReamrkColor()));

		TemplateMessageExample tme = new TemplateMessageExample();
		tme.setTemplate_id(templateId);
		tme.setTouser(openId);
		tme.setData(params);

		JSONObject jsontme = new JSONObject(tme);
		JSONObject jo = WeixinUtil.sendTemplateMsg(token, jsontme.toString());
		return jo;
	}
	
	
	
	/**
	 * 民宿订单专用取消通知
	 * @param token
	 * @param openId
	 * @param houseorderCancelMsg
	 * @return
	 */
	public static JSONObject sendHouseOrderCancelSuccessNoticeMsg(String token,String openId,HouseOrderCancelMsg houseorderCancelMsg) {
		String templateId = WechatMessageConstants.HOUSE_ORDER_CANCEL;
		TreeMap<String, TreeMap<String, String>> params = new TreeMap<String, TreeMap<String, String>>();
		params.put("first", TemplateMessageExample.item(
				houseorderCancelMsg.getFirst(),
				houseorderCancelMsg.getFirstColor()));
		params.put("keyword1", TemplateMessageExample.item(
				houseorderCancelMsg.getOrderId(),
				houseorderCancelMsg.getOrderIdColor()));
		params.put("keyword2", TemplateMessageExample.item(
				houseorderCancelMsg.getHouseName(),
				houseorderCancelMsg.getHouseNameColor()));
		params.put("keyword3", TemplateMessageExample.item(
				houseorderCancelMsg.getTime(),
				houseorderCancelMsg.getTimeColor()));
		params.put("keyword4", TemplateMessageExample.item(
				houseorderCancelMsg.getReason(),
				houseorderCancelMsg.getReasonColor()));
		params.put("remark", TemplateMessageExample.item(
				houseorderCancelMsg.getRemark(),
				houseorderCancelMsg.getReamrkColor()));
		
		
		TemplateMessageExample tme = new TemplateMessageExample();
		tme.setTemplate_id(templateId);
		tme.setTouser(openId);
		tme.setData(params);

		JSONObject jsontme = new JSONObject(tme);
		JSONObject jo = WeixinUtil.sendTemplateMsg(token, jsontme.toString());
		return jo;
	}
}
