/**
 * Funball.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.ideassoft.wechatrefund;



/**
 * 
 * @author White Wolf
 * @version $Id: PrepayIdRequestReqModel.java, v 0.1 2015-11-5 下午8:52:22 White Wolf Exp $
 */
public class PrepayIdRequestReqModel  {
    /**必填，公众账号Id */
    private String appId;
    /**必填，商户号Id */
    private String mchId;
    /**非必填，终端设备号(门店号或收银设备ID)，注意：PC网页或公众号内支付请传"WEB" */
    private String deviceInfo;
    /**必填，随机字符串 */
    private String nonceStr;
    /**必填，签名*/
    private String sign;
    /**必填，商品或支付单简要描述 32字符*/
    private String body;
    /**非必填，商品名称明细列表*/
    private String detail;
    /**非必填，附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据*/
    private String attach;
    /**必填，商户系统内部的订单号,32个字符内、可包含字母, 其他说明见商户订单号*/
    private String outTradeNo;
    /**非必填，符合ISO 4217标准的三位字母代码，默认人民币：CNY*/
    private String feeType;
    /**必填，订单总金额，单位为分*/
    private String totalFee;
    /**必填，APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP*/
    private String spbillCreateIp;
    /**非必填，订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010*/
    private String timeExpire;
    /**非必填，商品标记，代金券或立减优惠功能的参数*/
    private String goodsTag;
    /**必填，接收微信支付异步通知回调地址*/
    private String notifyUrl;
    /**必填，取值如下：JSAPI，NATIVE，APP，*/
    private String tradeType;
    /**非必填，trade_type=NATIVE，此参数必传。此id为二维码中包含的商品ID，商户自行定义。*/
    private String productId;
    /**非必填，no_credit--指定不能使用信用卡支付*/
    private String limitPay;
    /**非必填，trade_type=JSAPI，此参数必传，用户在商户appid下的唯一标识*/
    private String openId;

    /**
     * Getter method for property <tt>appId</tt>.
     * 
     * @return property value of appId
     */
    public String getAppId() {
        return appId;
    }

    /**
     * Setter method for property <tt>appId</tt>.
     * 
     * @param appId value to be assigned to property appId
     */
    public void setAppId(String appId) {
        this.appId = appId;
    }

    /**
     * Getter method for property <tt>mchId</tt>.
     * 
     * @return property value of mchId
     */
    public String getMchId() {
        return mchId;
    }

    /**
     * Setter method for property <tt>mchId</tt>.
     * 
     * @param mchId value to be assigned to property mchId
     */
    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    /**
     * Getter method for property <tt>deviceInfo</tt>.
     * 
     * @return property value of deviceInfo
     */
    public String getDeviceInfo() {
        return deviceInfo;
    }

    /**
     * Setter method for property <tt>deviceInfo</tt>.
     * 
     * @param deviceInfo value to be assigned to property deviceInfo
     */
    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    /**
     * Getter method for property <tt>nonceStr</tt>.
     * 
     * @return property value of nonceStr
     */
    public String getNonceStr() {
        return nonceStr;
    }

    /**
     * Setter method for property <tt>nonceStr</tt>.
     * 
     * @param nonceStr value to be assigned to property nonceStr
     */
    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    /**
     * Getter method for property <tt>sign</tt>.
     * 
     * @return property value of sign
     */
    public String getSign() {
        return sign;
    }

    /**
     * Setter method for property <tt>sign</tt>.
     * 
     * @param sign value to be assigned to property sign
     */
    public void setSign(String sign) {
        this.sign = sign;
    }

    /**
     * Getter method for property <tt>body</tt>.
     * 
     * @return property value of body
     */
    public String getBody() {
        return body;
    }

    /**
     * Setter method for property <tt>body</tt>.
     * 
     * @param body value to be assigned to property body
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * Getter method for property <tt>detail</tt>.
     * 
     * @return property value of detail
     */
    public String getDetail() {
        return detail;
    }

    /**
     * Setter method for property <tt>detail</tt>.
     * 
     * @param detail value to be assigned to property detail
     */
    public void setDetail(String detail) {
        this.detail = detail;
    }

    /**
     * Getter method for property <tt>attach</tt>.
     * 
     * @return property value of attach
     */
    public String getAttach() {
        return attach;
    }

    /**
     * Setter method for property <tt>attach</tt>.
     * 
     * @param attach value to be assigned to property attach
     */
    public void setAttach(String attach) {
        this.attach = attach;
    }

    /**
     * Getter method for property <tt>outTradeNo</tt>.
     * 
     * @return property value of outTradeNo
     */
    public String getOutTradeNo() {
        return outTradeNo;
    }

    /**
     * Setter method for property <tt>outTradeNo</tt>.
     * 
     * @param outTradeNo value to be assigned to property outTradeNo
     */
    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    /**
     * Getter method for property <tt>feeType</tt>.
     * 
     * @return property value of feeType
     */
    public String getFeeType() {
        return feeType;
    }

    /**
     * Setter method for property <tt>feeType</tt>.
     * 
     * @param feeType value to be assigned to property feeType
     */
    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    /**
     * Getter method for property <tt>totalFee</tt>.
     * 
     * @return property value of totalFee
     */
    public String getTotalFee() {
        return totalFee;
    }

    /**
     * Setter method for property <tt>totalFee</tt>.
     * 
     * @param totalFee value to be assigned to property totalFee
     */
    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }

    /**
     * Getter method for property <tt>spbillCreateIp</tt>.
     * 
     * @return property value of spbillCreateIp
     */
    public String getSpbillCreateIp() {
        return spbillCreateIp;
    }

    /**
     * Setter method for property <tt>spbillCreateIp</tt>.
     * 
     * @param spbillCreateIp value to be assigned to property spbillCreateIp
     */
    public void setSpbillCreateIp(String spbillCreateIp) {
        this.spbillCreateIp = spbillCreateIp;
    }

    /**
     * Getter method for property <tt>timeExpire</tt>.
     * 
     * @return property value of timeExpire
     */
    public String getTimeExpire() {
        return timeExpire;
    }

    /**
     * Setter method for property <tt>timeExpire</tt>.
     * 
     * @param timeExpire value to be assigned to property timeExpire
     */
    public void setTimeExpire(String timeExpire) {
        this.timeExpire = timeExpire;
    }

    /**
     * Getter method for property <tt>goodsTag</tt>.
     * 
     * @return property value of goodsTag
     */
    public String getGoodsTag() {
        return goodsTag;
    }

    /**
     * Setter method for property <tt>goodsTag</tt>.
     * 
     * @param goodsTag value to be assigned to property goodsTag
     */
    public void setGoodsTag(String goodsTag) {
        this.goodsTag = goodsTag;
    }

    /**
     * Getter method for property <tt>notifyUrl</tt>.
     * 
     * @return property value of notifyUrl
     */
    public String getNotifyUrl() {
        return notifyUrl;
    }

    /**
     * Setter method for property <tt>notifyUrl</tt>.
     * 
     * @param notifyUrl value to be assigned to property notifyUrl
     */
    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    /**
     * Getter method for property <tt>tradeType</tt>.
     * 
     * @return property value of tradeType
     */
    public String getTradeType() {
        return tradeType;
    }

    /**
     * Setter method for property <tt>tradeType</tt>.
     * 
     * @param tradeType value to be assigned to property tradeType
     */
    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    /**
     * Getter method for property <tt>productId</tt>.
     * 
     * @return property value of productId
     */
    public String getProductId() {
        return productId;
    }

    /**
     * Setter method for property <tt>productId</tt>.
     * 
     * @param productId value to be assigned to property productId
     */
    public void setProductId(String productId) {
        this.productId = productId;
    }

    /**
     * Getter method for property <tt>limitPay</tt>.
     * 
     * @return property value of limitPay
     */
    public String getLimitPay() {
        return limitPay;
    }

    /**
     * Setter method for property <tt>limitPay</tt>.
     * 
     * @param limitPay value to be assigned to property limitPay
     */
    public void setLimitPay(String limitPay) {
        this.limitPay = limitPay;
    }

    /**
     * Getter method for property <tt>openId</tt>.
     * 
     * @return property value of openId
     */
    public String getOpenId() {
        return openId;
    }

    /**
     * Setter method for property <tt>openId</tt>.
     * 
     * @param openId value to be assigned to property openId
     */
    public void setOpenId(String openId) {
        this.openId = openId;
    }

    /** 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "appid=" + appId + "&attach=" + attach + "&body=" + body
               + (!StringUtil.isNotBlank(deviceInfo) ? "" : "&device_info=" + deviceInfo) + "&mch_id="
               + mchId + "&nonce_str=" + nonceStr + "&notify_url=" + notifyUrl
               + (!StringUtil.isNotBlank(openId) ? "" : "&openid=" + openId) + "&out_trade_no="
               + outTradeNo + "&spbill_create_ip=" + spbillCreateIp + "&total_fee=" + totalFee
               + "&trade_type=" + tradeType;
    }
}
