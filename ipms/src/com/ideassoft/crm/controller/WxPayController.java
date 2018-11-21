package com.ideassoft.crm.controller;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ideassoft.bean.Member;
import com.ideassoft.core.ajax.AjaxResult;
import com.ideassoft.crm.service.WxPayService;
import com.ideassoft.util.JSONUtil;
import com.ideassoft.wxPay.XMLUtil;

@Controller
public class WxPayController {
	
	@Autowired
	private WxPayService wxPayService;
	
	public static Map<String, Object> payDetail = new ConcurrentHashMap<String, Object>();
	
	
	@RequestMapping("weipayCallBack.do")
	public void weipayCallBack(HttpServletRequest request, HttpServletResponse response) throws Exception {
		InputStream inStream = request.getInputStream();
		ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outSteam.write(buffer, 0, len);
		}
		outSteam.close();
		inStream.close();
		String resultStr = new String(outSteam.toByteArray(), "utf-8");
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap = XMLUtil.xmlToMap(resultStr);
		//商户单号
        String out_trade_no = (String) resultMap.get("out_trade_no");
		String return_code = (String) resultMap.get("return_code");
		String openId = (String) resultMap.get("openid");
		//付款金额
		Double total_fee = (Double.parseDouble((String) resultMap.get("total_fee")) / 100);
		// 付款
		//String bank = (String) resultMap.get("bank_type");
		//流水单号
		String transaction_id = (String) resultMap.get("transaction_id");
		Map<String, Object> detail = new ConcurrentHashMap<String, Object>();
		detail.put("transaction_id", transaction_id);
		detail.put("total_fee", String.valueOf(total_fee));
		detail.put("out_trade_no", out_trade_no);
		payDetail.put(openId, detail);
		//BigDecimal fee = new BigDecimal(total_fee);
		 if(return_code.equals("SUCCESS")){
		        //支付成功的业务逻辑
		    }else{
		        //支付失败的业务逻辑
		    }
		// 通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了.
		String success = "<xml><return_code><![CDATA[SUCCESS]]></return_code></xml>";
		response.getOutputStream().write(new String(success).getBytes());
	}
	
	@RequestMapping("mohoGetMap.do")
	public void getMap(HttpServletRequest request, HttpServletResponse response, String memberId){
		Member member = (Member) wxPayService.findById(Member.class, memberId);
		String openId = member.getWechatAppopenId();
		@SuppressWarnings("unchecked")
		Map<String, Object> detail = (Map<String, Object>) WxPayController.payDetail.get(openId);
		WxPayController.payDetail.remove(openId);
		JSONUtil.responseJSON(response, new AjaxResult(0, JSONObject.fromObject(detail).toString()));
	}
}