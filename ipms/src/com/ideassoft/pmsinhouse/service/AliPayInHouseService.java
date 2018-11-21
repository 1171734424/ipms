package com.ideassoft.pmsinhouse.service;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.ideassoft.core.constants.CommonConstants;
import com.ideassoft.core.constants.SystemConstants;

@Service
public class AliPayInHouseService {
	
	public int alipayReFund(String out_trade_no, String trade_no, String refund_amount) throws AlipayApiException{
		AlipayClient alipayClient = new DefaultAlipayClient(CommonConstants.Alipay.ALIPAYREFUND_URL, CommonConstants.Alipay.APP_ID, CommonConstants.Alipay.APP_PRIVATE_KEY,
				CommonConstants.Alipay.FORMAT, CommonConstants.Alipay.GBK, CommonConstants.Alipay.ALIPAY_PUBLIC_KEY, CommonConstants.Alipay.SIGN_TYPE);
		 AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
		 request.setBizContent("{" +
				 "\"out_trade_no\":\"" + out_trade_no.trim() + "\"," +
                 "\"trade_no\":\"" + trade_no.trim() + "\"," +
                 "\"refund_amount\": " + refund_amount + "," +
                 "\"out_request_no\":\" "+ new Date() +"  \"}");
		 AlipayTradeRefundResponse response = alipayClient.execute(request);
		 if(response.isSuccess()){
			 return SystemConstants.PortalResultCode.DONE;
		 } else {
			 return SystemConstants.PortalResultCode.NULL;
		 }
	}
}
