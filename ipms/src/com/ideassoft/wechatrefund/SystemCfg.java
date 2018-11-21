package com.ideassoft.wechatrefund;


/**
 * 星动系统配置
 * @author JACKIN
 *
 */
public class SystemCfg {
	
	public static final String CODE_IS_SEND_FAIL = "send fail";
	
    private String appId = "wx6bf6a861480d0a5c";
    //受理商ID，身份标识
    private String mchid = "1505647461";
    //微信操作人
    private String weChatOpUserId = "123456789";
    //商户支付密钥Key。审核通过后，在微信发送的邮件中查看
    private String weChatPaykey = "mohozhixuanmohozhixuanmohozhixua";
    //商户支付密钥Key。审核通过后，在微信发送的邮件中查看
    private String aliPaykey = "";
    //JSAPI接口中获取openid，审核后在公众平台开启开发模式后可查看
    private String appSecret = "a420c146e7e23559c0e8de3a3ea96034";
    //重定向地址
    private String redirectUrl = "";
    //异步回调地址
	private String notifyUrl = "";
	//web回调地址
	private String webNotifyUrl = "";
	//支付宝结果回调
	private String alipayNotifyUrl = "";
    //微信获取预订单地址
    private String prepayIdRequestUrl = "";
    //微信查询订单结果地址
    private String queryWeChatPayResult = "";
    //微信退款订单结果地址
    private String weChatRefundUrl = "https://api.mch.weixin.qq.com/secapi/pay/refund";
    //平台账户
    private String platAccount = "";
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getMchid() {
		return mchid;
	}
	public void setMchid(String mchid) {
		this.mchid = mchid;
	}
	public String getWeChatOpUserId() {
		return weChatOpUserId;
	}
	public void setWeChatOpUserId(String weChatOpUserId) {
		this.weChatOpUserId = weChatOpUserId;
	}
	public String getWeChatPaykey() {
		return weChatPaykey;
	}
	public void setWeChatPaykey(String weChatPaykey) {
		this.weChatPaykey = weChatPaykey;
	}
	public String getAliPaykey() {
		return aliPaykey;
	}
	public void setAliPaykey(String aliPaykey) {
		this.aliPaykey = aliPaykey;
	}
	public String getAppSecret() {
		return appSecret;
	}
	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}
	public String getRedirectUrl() {
		return redirectUrl;
	}
	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	public String getWebNotifyUrl() {
		return webNotifyUrl;
	}
	public void setWebNotifyUrl(String webNotifyUrl) {
		this.webNotifyUrl = webNotifyUrl;
	}
	public String getAlipayNotifyUrl() {
		return alipayNotifyUrl;
	}
	public void setAlipayNotifyUrl(String alipayNotifyUrl) {
		this.alipayNotifyUrl = alipayNotifyUrl;
	}
	public String getPrepayIdRequestUrl() {
		return prepayIdRequestUrl;
	}
	public void setPrepayIdRequestUrl(String prepayIdRequestUrl) {
		this.prepayIdRequestUrl = prepayIdRequestUrl;
	}
	public String getQueryWeChatPayResult() {
		return queryWeChatPayResult;
	}
	public void setQueryWeChatPayResult(String queryWeChatPayResult) {
		this.queryWeChatPayResult = queryWeChatPayResult;
	}
	public String getWeChatRefundUrl() {
		return weChatRefundUrl;
	}
	public void setWeChatRefundUrl(String weChatRefundUrl) {
		this.weChatRefundUrl = weChatRefundUrl;
	}
	public String getPlatAccount() {
		return platAccount;
	}
	public void setPlatAccount(String platAccount) {
		this.platAccount = platAccount;
	}
	public static String getCodeIsSendFail() {
		return CODE_IS_SEND_FAIL;
	}
   
    
    
}
