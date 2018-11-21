package com.ideassoft.core.notifier;

import java.util.ArrayList;

public class SmsSDKDemo {
	public static void main(String[] args) {
		try {

			int appid = 1400040929; // getAppId()

			String appkey = "c04d6a97f6c8020d0eb36fc70cbb9ba5"; // getAppKey()
			SmsSingleSender singleSender = new SmsSingleSender(appid, appkey);
			SmsSingleSenderResult singleSenderResult;

			String country = "86"; // 每个国家的手机号开头 SystemConstants.note.COUNTRY
			String phoneNumber1 = "18252407002"; // 手机号
			int templId = 43742; // getTemplId(String param_desc) 模板的名称
			ArrayList<String> params = new ArrayList<String>(); // 例如:你的验证码是{1},有几个参数就添加几个
			params.add("123456");
			String sign = ""; // 签名 ,添加签名需要审核 例如:[禾悦会],不填则默认
			String extend = ""; // 扩展码，可填空
			String ext = ""; // 服务端原样返回的参数，可填空
			singleSenderResult = singleSender.sendWithParam(country,
					phoneNumber1, templId, params, sign, extend, ext);
			System.out.println(singleSenderResult);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
