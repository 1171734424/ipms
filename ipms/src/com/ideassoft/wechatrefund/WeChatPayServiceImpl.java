package com.ideassoft.wechatrefund;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ideassoft.bean.Bill;
import com.ideassoft.bean.HouseReFundLog;
import com.ideassoft.bean.Order;
import com.ideassoft.bean.ReFundLog;

/**
 * 微信支付
 * @author wolf
 *
 */
@Service
public class WeChatPayServiceImpl {

	private static SystemCfg systemCfg = new SystemCfg();
	
    public WeChatRefundResModel weChatRefund(WeChatRefundReqModel reqModel) {
        reqModel.setAppId(systemCfg.getAppId());//1
        reqModel.setMchId(systemCfg.getMchid());//1
        reqModel.setNonoceStr(WeChatUtil.create_nonce_str());//1
        reqModel.setOpUserId(systemCfg.getWeChatOpUserId());//1
        reqModel.setOutRefundNo(UUID.randomUUID().toString().replaceAll("-", ""));
        String refundSign = reqModel.toString() + "&key=" + systemCfg.getWeChatPaykey();//1
        String refundSignEncry = MD5Util.encryptMD5(refundSign).toUpperCase();
        String list = reqModel.toString() + "&sign=" + refundSignEncry;
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
        for (String B : list.split("&")) {
            map.put(B.split("=")[0], B.split("=")[1]);
        }
        String refundXML = XMLUtil.getXMLForMap(map);
        Map<String, String> resultMap = new HashMap<String,String>();
        try {
            String resultXML = ClientCustomSSL.doRefund(systemCfg.getWeChatRefundUrl(), refundXML,//1
                "D:/apiclient_cert.p12", systemCfg.getMchid());

            resultMap = XMLUtil.parseRefundXml(resultXML);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        WeChatRefundResModel resModel = buildWeChatRefundResModel(resultMap);
        return resModel;
    }

	private WeChatRefundResModel buildWeChatRefundResModel(Map<String, String> resultMap) {

        WeChatRefundResModel resModel = new WeChatRefundResModel();
        resModel.setReturnCode(resultMap.get("return_code"));
        resModel.setRetrunMsg(resultMap.get("return_msg"));
        resModel.setAppId(resultMap.get("appid"));
        resModel.setMchId(resultMap.get("mch_id"));
        resModel.setNonceStr(resultMap.get("nonce_str"));
        resModel.setSign(resultMap.get("sign"));
        resModel.setResultCode(resultMap.get("result_code"));
        resModel.setTransactionId(resultMap.get("transaction_id"));
        resModel.setOutTradeNo(resultMap.get("out_trade_no"));
        resModel.setOutRefundNo(resultMap.get("out_refund_no"));
        resModel.setRefundId(resultMap.get("refund_id"));
        resModel.setRefundFee(resultMap.get("refund_fee"));
        resModel.setCouponRefundFee(resultMap.get("coupon_refund_fee"));
        resModel.setTotalFee(resultMap.get("total_fee"));
        resModel.setCashFee(resultMap.get("cash_fee"));
        resModel.setCountponRefundCount(resultMap.get("coupon_refund_count"));
        resModel.setCashRefundFee(resultMap.get("cash_refund_fee"));

        return resModel;
    }
	
	public Bill saveRefundBill(String billId, Order order, Double reFundMoney, String recordUser){
		Bill bill = new Bill();
		bill.setBillId(billId);
		bill.setBranchId(order.getBranchId());
		bill.setCheckId(order.getOrderId());
		bill.setProjectId("1238");
		bill.setProjectName("线上退款");
		bill.setDescribe("线上退款");
		bill.setCost(reFundMoney);
		bill.setPay(0.0);
		bill.setPayment("5");
		bill.setStatus("1");
		bill.setRecordUser(recordUser);
		return bill;
	}
	
	public HouseReFundLog saveReFundLog(String logd, Order order, 
			String recordUser, String remark, String source, 
			String bussinessId, String totalfee, String refundMoney){
		HouseReFundLog reFundLog = new HouseReFundLog();
		reFundLog.setLogId(logd);
		reFundLog.setBranchId(order.getBranchId());
		reFundLog.setCheckId(order.getOrderId());
		reFundLog.setRecordUser(recordUser);
		reFundLog.setRemark(remark);
		reFundLog.setTotalfee(Double.parseDouble(totalfee));
		reFundLog.setRefundMoney(Double.parseDouble(refundMoney));
		reFundLog.setSource(source);
		reFundLog.setBussinessId(bussinessId);
		return reFundLog;
	}

}
