package com.ideassoft.apartment.service;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.ideassoft.core.constants.CommonConstants;
import com.ideassoft.core.constants.SystemConstants;
import com.ideassoft.core.dao.GenericDAOImpl;
import com.ideassoft.core.page.Pagination;
import com.ideassoft.wechatrefund.ClientCustomSSL;
import com.ideassoft.wechatrefund.MD5Util;
import com.ideassoft.wechatrefund.SystemCfg;
import com.ideassoft.wechatrefund.WeChatRefundReqModel;
import com.ideassoft.wechatrefund.WeChatRefundResModel;
import com.ideassoft.wechatrefund.WeChatUtil;
import com.ideassoft.wechatrefund.XMLUtil;

@Service
public class ApartmentCleanService extends GenericDAOImpl {
	// 查询默认可预约房间数
	public List<?> getDefaultParam() throws Exception {
		List<?> result = findBySQL("getDefaultParam", true);
		return result;
	}

	// 保洁
	public List<?> getApplicationdata(String branchid, Pagination pagination) throws Exception {
		List<?> result = findBySQLWithPagination("getApplicationdata", new String[] { branchid }, pagination, true);
		return result;
	}

	// 查询保洁申请带条件
	public List<?> getApplicationdataCondition(String branchid, String cleanstarttime, String cleanendtime,
			String applystarttime, String applyendtime, String timeArea, String status, Pagination pagination)
			throws Exception {

		List<?> result = findBySQLWithPagination("getApplydataCon", new String[] { branchid, cleanstarttime,
				cleanendtime, applystarttime, applyendtime, timeArea, status }, pagination, true);
		return result;
	}

	public List<?> findRoomByBranchId(String branchid) throws Exception {
		List<?> result = findBySQL("findRoomByBranchId", new String[] { branchid }, true);
		return result;
	}

	public List<?> findstaffByBranchId(String branchid,String post) throws Exception {
		List<?> result = findBySQL("findstaffByBranchId", new String[] { branchid,post }, true);
		return result;
	}

	// 查所有的保洁记录

	public List<?> queryAllOfRecord(String branchid, Pagination pagination) throws Exception {
		List<?> result = findBySQLWithPagination("queryAllOfRecord", new String[] { branchid }, pagination, true);
		return result;
	}

	// 按条件查询保洁记录
	public List<?> queryRecordByCondition(String branchid, String time, String status, Pagination pagination)
			throws Exception {
		List<?> result = findBySQLWithPagination("queryRecordByCon", new String[] { branchid, time, status },
				pagination, true);
		return result;
	}

	// 查询保洁申请详情
	public List<?> showdetail(String cleanApplicationId) throws Exception {
		List<?> result = findBySQL("showapplydetail", new String[] { cleanApplicationId }, true);
		return result;
	}
	
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
                "F:/apiclient_cert.p12", systemCfg.getMchid());
            resultMap = XMLUtil.doXMLParse(resultXML);
        } catch (Exception e) {
            e.printStackTrace();
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

}
