package com.ideassoft.core.notifier.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ideassoft.bean.CommParameter;
import com.ideassoft.bean.EventType;
import com.ideassoft.bean.MemberRank;
import com.ideassoft.bean.SmsSendLog;
import com.ideassoft.bean.SmsTemplate;
import com.ideassoft.bean.Staff;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.constants.SystemConstants;
import com.ideassoft.core.dao.GenericDAOImpl;
import com.ideassoft.core.factory.ServiceFactory;
import com.ideassoft.core.notifier.NotifyServer;
import com.ideassoft.core.notifier.bean.SendMessage;
import com.ideassoft.core.notifier.emay.ResultModel;
import com.ideassoft.core.notifier.emay.eucp.inter.http.v1.dto.request.BalanceRequest;
import com.ideassoft.core.notifier.emay.eucp.inter.http.v1.dto.request.ReportRequest;
import com.ideassoft.core.notifier.emay.eucp.inter.http.v1.dto.request.SmsBatchOnlyRequest;
import com.ideassoft.core.notifier.emay.eucp.inter.http.v1.dto.request.SmsSingleRequest;
import com.ideassoft.core.notifier.emay.eucp.inter.http.v1.dto.response.BalanceResponse;
import com.ideassoft.core.notifier.emay.eucp.inter.http.v1.dto.response.ReportResponse;
import com.ideassoft.core.notifier.emay.eucp.inter.http.v1.dto.response.SmsResponse;
import com.ideassoft.core.notifier.emay.util.AES;
import com.ideassoft.core.notifier.emay.util.GZIPUtils;
import com.ideassoft.core.notifier.emay.util.JsonHelper;
import com.ideassoft.core.notifier.emay.util.http.EmayHttpClient;
import com.ideassoft.core.notifier.emay.util.http.EmayHttpRequestBytes;
import com.ideassoft.core.notifier.emay.util.http.EmayHttpResponseBytes;
import com.ideassoft.core.notifier.emay.util.http.EmayHttpResponseBytesPraser;
import com.ideassoft.core.notifier.emay.util.http.EmayHttpResultCode;
import com.ideassoft.core.notifier.service.INotifyService;



@Service
public class NotifyService extends GenericDAOImpl implements INotifyService {
	protected Logger log = Logger.getLogger(NotifyServer.class);
	//发短信服务起时就加载相应配置的数据
	@SuppressWarnings("unchecked")
	public CommParameter getNotifyCommParameter() throws Exception {
		//String sql = "select * from TP_P_COMMPARAMETER";
		//List<CommParameter> list = findBySQL(sql, CommParameter.class);
		String hql = " from CommParameter";
		List<CommParameter> list = findByHQL(hql);
		return list != null && list.size() >= 0 ? list.get(0) : null;
	}
    //获取所有的被通知对象（从中获取各被通知对象的手机号码或是邮箱地址）（可以重载，传递不同的参数，查询条件）
	@SuppressWarnings("unchecked")
	public List<Staff> getAllNotiUsers() throws Exception {
		String hql = " from Staff where status = '1'";
		return findByHQL(hql);
	}
	
	//将要发短信的列表里数据，包装成smssendlog对象保存到TL_C_SMSSENDLOG表里去
	public boolean addSms(SmsSendLog[] smsInfos) throws Exception {
		if(smsInfos!=null&&smsInfos.length!=0)
		{
			for(int i=0;i<smsInfos.length;i++)
			{
				getHibernateTemplate().save(smsInfos[i]);
			}
		}
		return false;
	}
	
	//获取额外的通知所需的数据（不同的业务查询不同的数据，再将不同的数据替换到模板里 ）
	//--------------------------------------------------------------------------------------------------------------------------
	
  public static String getRealSmsContent(Map<String,String> data, String templateContent){
	  
	  String realSmsContent = "";
	 
	  Iterator<Entry<String,String>> entries = data.entrySet().iterator();
	   while(entries.hasNext()){
		   Entry<String,String> entry = entries.next();
		   String key = entry.getKey();
		   String value = entry.getValue();
		   if(templateContent != null && !templateContent.equals("")){
			   realSmsContent = templateContent.replace(key, value); 
		   }
		   templateContent = realSmsContent;
	  }
        return realSmsContent;
    }    
	
	
  public static void main(String[] args) throws Exception {
	
	  
	 /* Map<String,String> data = new HashMap<String,String>();
	  data.put("{加油}", "come on");
	  data.put("{蒋顺敏}", "jiangshunmin");
	  data.put("{你可以的}", "you can do it");
	  String kkk="新年愿望是{加油}，姓名是{蒋顺敏}，今年你一定是{你可以的}";
	 String d = getRealSmsContent(data,kkk);
	 System.out.println("结果 为："+d);*/
	  Map<String,String> data = new HashMap<String,String>();
	  data.put("{加油}", "come on");
	  data.put("{蒋顺敏}", "jiangshunmin");
	  data.put("{你可以的}", "you can do it");
	  for(int i = 0;i <= 5; i++){
	   // happenSendSms(null,null,null,"10000001",data,"18205253515");
	  }
	  
  }

  
public List<EventType> findEventTypes() {
	// TODO Auto-generated method stub
	return null;
}
//已知表中数据将数据封装到sendMessage中去	
public void logTransSendSms(String dataId){
	NotifyService dao = (NotifyService) ServiceFactory.getService("notifyService");
	SmsSendLog smsInfo = new SmsSendLog();
	smsInfo = (SmsSendLog)(dao.findOneByProperties(SmsSendLog.class, "dataId", dataId));
	String realSmsContent= smsInfo.getSmsContent();
	String smsRecipentno= smsInfo.getSmsRecipentno();
	String memberLevel = "";
	String status = "1";
	try {
			memberLevel = getHighestLevelOfMemRank().getMemberRank();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	//数据封装到sendMessage类里添加到队列中去
	SendMessage sendMessage = new SendMessage();
	sendMessage.setDataId(dataId);
	sendMessage.setMessageContent(realSmsContent);
	sendMessage.setMessageRecipentno(smsRecipentno);
	sendMessage.setMemberLevel(memberLevel);
	try {
		log.info("记录事件将表里没有发出去的短信读出来");
		if ("1".equals(status)) {//待发中的短信
			if (NotifyServer.getInstance() != null){
				NotifyServer.getInstance().sendMessage(sendMessage);
			}
		}
	} catch (Exception e) {
		log.error("保存事件 出错!");
		e.printStackTrace();
	}
}	
/**
 * 添加待发的短信到smsSendLog表里去
 * @param loginUser 登录用户
 * @param eventCode 
 * @param dataId    该发送短信的序列号
 * @param templateId 模板号  不同的信息模板号
 * @param varInTemplate	模板中值   Map<String, String>
 * @param smsRecipentno 接收号码
 * @param memberLevel 会员等级
 * @return
 */
public SmsSendLog happenSendSms(LoginUser loginUser,String eventCode, String dataId,String templateId, Map<String, String> varInTemplate,String smsRecipentno, String memberLevel){
	String realSmsContent= "";
	String operateUser = "";
	String branchId = "";
	String status = "1";
	NotifyService dao = (NotifyService) ServiceFactory.getService("notifyService");
	//数据保存到表里
	SmsSendLog smsInfo = new SmsSendLog();
	if(dataId == null){
		if(null != loginUser){
			operateUser = loginUser.getStaff().getStaffId();
			branchId = loginUser.getStaff().getBranchId();
		}else{
			operateUser = "-1";
			branchId = "100001";
		}
		dataId = getSequence("SEQ_SMSSENDLOG_ID", null);
		smsInfo.setDataId(dataId);
		smsInfo.setRecordUser(operateUser);
		smsInfo.setTemplateId(templateId);
		smsInfo.setBranchId(branchId);
		smsInfo.setRecordTime(new Date());
		smsInfo.setSmsRecipentno(smsRecipentno);
		String templateContent = ((SmsTemplate)(dao.findOneByProperties(SmsTemplate.class, "templateId", templateId))).getTemplateContent();
		realSmsContent = NotifyService.getRealSmsContent(varInTemplate,templateContent);
		smsInfo.setSmsContent(realSmsContent);
		smsInfo.setStatus(status);
	}else{
		smsInfo = (SmsSendLog)(dao.findOneByProperties(SmsSendLog.class, "dataId", dataId));
		dataId = getSequence("SEQ_SMSSENDLOG_ID", null);
		smsInfo.setDataId(dataId);
		realSmsContent = smsInfo.getSmsContent();
		smsRecipentno = smsInfo.getSmsRecipentno();	
		try {
				memberLevel = getHighestLevelOfMemRank().getMemberRank();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//可能逻辑还是有误的
	}
	
	//数据封装到sendMessage类里添加到队列中去
	SendMessage sendMessage = new SendMessage();
	sendMessage.setDataId(dataId);
	sendMessage.setMessageContent(realSmsContent);
	sendMessage.setMessageRecipentno(smsRecipentno);
	sendMessage.setMemberLevel(memberLevel);
	if(eventCode == null){
		eventCode = String.valueOf(SystemConstants.EventConst.EventCode.SMS_SERVER_START);
	}
	sendMessage.setMessageEventCode(String.valueOf(eventCode));
	try {
		dao.addSms(new SmsSendLog[] { smsInfo });
		log.info("记录事件");
		if ("1".equals(status)) {//待发中的短信
			if (NotifyServer.getInstance() != null){
				NotifyServer.getInstance().sendMessage(sendMessage);
			}
		}
	} catch (Exception e) {
		log.error("保存事件 出错!");
		e.printStackTrace();
	}
	return smsInfo;
}
//查询当前会员等级的最大级别
	public MemberRank getHighestLevelOfMemRank() throws Exception {
		String hql = " from MemberRank where status = 1 order by memberRank desc ";
		List<?> result = findByHQL(hql);
		return  result == null ? null : (MemberRank)(result.get(0));
	}

//------------------------------------------------------------------短信接口方法-----------------------------------------------
/**
 * 发送批次短信
 * @param isGzip 是否压缩
 */
public static String setBatchOnlySms(String appId,String secretKey,String host,String algorithm,String content, String extendCode, String[] mobiles,boolean isGzip,String encode) {
	System.out.println("=============begin setBatchOnlySms==================");
	SmsBatchOnlyRequest pamars = new SmsBatchOnlyRequest();
	pamars.setMobiles(mobiles);
	pamars.setExtendedCode(extendCode);
	pamars.setContent(content);
	ResultModel result = request(appId,secretKey,algorithm,pamars, "http://" + host + "/inter/sendBatchOnlySMS",isGzip,encode);
	System.out.println("result code :" + result.getCode());
	if("SUCCESS".equals(result.getCode())){
		SmsResponse[] response = JsonHelper.fromJson(SmsResponse[].class, result.getResult());
		if (response != null) {
			for (SmsResponse d : response) {
				System.out.println("data:" + d.getMobile() + "," + d.getSmsId() + "," + d.getCustomSmsId());
			}
		}
	}
	System.out.println("=============end setBatchOnlySms==================");
	return result.getCode();
}

/**
 * 公共请求方法
 */
public static ResultModel request(String appId,String secretKey,String algorithm,Object content, String url,final boolean isGzip,String encode) {
	Map<String, String> headers = new HashMap<String, String>();
	EmayHttpRequestBytes request = null;
	try {
		headers.put("appId", appId);
		headers.put("encode", encode);
		String requestJson = JsonHelper.toJsonString(content);
		System.out.println("result json: " + requestJson);
		byte[] bytes = requestJson.getBytes(encode);
		System.out.println("request data size : " + bytes.length);
		if (isGzip) {
			headers.put("gzip", "on");
			bytes = GZIPUtils.compress(bytes);
			System.out.println("request data size [com]: " + bytes.length);
		}
		byte[] parambytes = AES.encrypt(bytes, secretKey.getBytes(), algorithm);
		System.out.println("request data size [en] : " + parambytes.length);
		request = new EmayHttpRequestBytes(url, encode, "POST", headers, null, parambytes);
	} catch (Exception e) {
		System.out.println("加密异常");
		e.printStackTrace();
	}
	EmayHttpClient client = new EmayHttpClient();
	String code = null;
	String result = null;
	try {
		EmayHttpResponseBytes res = client.service(request, new EmayHttpResponseBytesPraser());
		if(res == null){
			System.out.println("请求接口异常");
			return new ResultModel(code, result);
		}
		if (res.getResultCode().equals(EmayHttpResultCode.SUCCESS)) {
			if (res.getHttpCode() == 200) {
				code = res.getHeaders().get("result");
				if (code.equals("SUCCESS")) {
					byte[] data = res.getResultBytes();
					System.out.println("response data size [en and com] : " + data.length);
					data = AES.decrypt(data, secretKey.getBytes(), algorithm);
					if (isGzip) {
						data = GZIPUtils.decompress(data);
					}
					System.out.println("response data size : " + data.length);
					result = new String(data, encode);
					System.out.println("response json: " + result);
				}
			} else {
				System.out.println("请求接口异常,请求码:" + res.getHttpCode());
			}
		} else {
			System.out.println("请求接口网络异常:" + res.getResultCode().getCode());
		}
	} catch (Exception e) {
		System.out.println("解析失败");
		e.printStackTrace();
	}
	ResultModel re = new ResultModel(code, result);
	return re;
}

/**
 * 获取状态报告(for 单条短信发送获得发送成功与否的状态)
 * @param isGzip 是否压缩
 * @return 
 */
public static List<String> getReport(String appId,String secretKey,String host,String algorithm,boolean isGzip,String encode) {
	System.out.println("=============begin getReport==================");
	List<String> smsSendState = new ArrayList<String>();
	ReportRequest pamars = new ReportRequest();
	ResultModel result = request(appId,secretKey,algorithm,pamars, "http://" + host + "/inter/getReport",isGzip,encode);
	System.out.println("result code :" + result.getCode());
	if("SUCCESS".equals(result.getCode())){
		ReportResponse[] response = JsonHelper.fromJson(ReportResponse[].class, result.getResult());
		if (response != null) {
			for (ReportResponse d : response) {
				//System.out.println("result data : " + d.getExtendedCode() + "," + d.getMobile() + "," + d.getCustomSmsId() + "," + d.getSmsId() + "," + d.getState() + "," + d.getDesc()
				//		+ "," + d.getSubmitTime() + "," + d.getReceiveTime());
				smsSendState.add(d.getState());
				System.out.println("=============end getReport=="+d.getState()+"=======");
			}
		}
	}
	return smsSendState;
	//System.out.println("=============end getReport==================");
}
	
/**
 * 发送单条短信
 * @param isGzip 是否压缩
 */
public static void setSingleSms(String appId,String secretKey,String host,String algorithm,String content, String customSmsId, String extendCode, String mobile,boolean isGzip,String encode) {
	System.out.println("=============begin setSingleSms==================");
	SmsSingleRequest pamars = new SmsSingleRequest();
	pamars.setContent(content);
	pamars.setCustomSmsId(customSmsId);
	pamars.setExtendedCode(extendCode);
	pamars.setMobile(mobile);
	ResultModel result = request(appId,secretKey,algorithm,pamars, "http://" + host + "/inter/sendSingleSMS",isGzip,encode);
	System.out.println("result code :" + result.getCode());
	if("SUCCESS".equals(result.getCode())){
		SmsResponse response = JsonHelper.fromJson(SmsResponse.class, result.getResult());
		if (response != null) {
			System.out.println("data : " + response.getMobile() + "," + response.getSmsId() + "," + response.getCustomSmsId());
		}
	}
	System.out.println("=============end setSingleSms==================");
}

/**
 * 获取余额
 * @param isGzip 是否压缩
 */
public static long getBalance(String appId,String secretKey,String host,String algorithm,boolean isGzip,String encode) {
	System.out.println("=============begin getBalance==================");
	BalanceRequest pamars = new BalanceRequest();
	ResultModel result = request(appId,secretKey,algorithm,pamars, "http://" + host + "/inter/getBalance",isGzip,encode);
	System.out.println("result code :" + result.getCode());
	BalanceResponse response = JsonHelper.fromJson(BalanceResponse.class, result.getResult());
	if("SUCCESS".equals(result.getCode())){
		if (response != null) {
			System.out.println("result data : " + response.getBalance());
		}
	}
	System.out.println("=============end getBalance==================");
	return response.getBalance();
}
	
}
