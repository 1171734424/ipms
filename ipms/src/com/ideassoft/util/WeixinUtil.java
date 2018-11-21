package com.ideassoft.util;

import java.io.IOException;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.ideassoft.core.constants.Constants;

public class WeixinUtil {
	// 发送模板消息
	public static final String SEND_TEMPLATE_MESSAGE_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";

	public static JSONObject doGetStr(String url) throws IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		JSONObject jsonObject = null;
		try {
			HttpResponse response = httpClient.execute(httpGet);

			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String result = EntityUtils.toString(entity, "UTF-8");
				jsonObject = new JSONObject(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpClient.close();
		}
		return jsonObject;
	}

	public static JSONObject doPostStr(String url, String outStr) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		JSONObject jsonObject = null;
		try {
			httpPost.setEntity(new StringEntity(outStr, "UTF-8"));
			HttpResponse response = httpClient.execute(httpPost);
			String result = EntityUtils.toString(response.getEntity(), "UTF-8");
			jsonObject = new JSONObject(result);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return jsonObject;
	}

	// 模板消息 5发送模板消息
	public static JSONObject sendTemplateMsg(String token, String templateMsg) {
		String url = SEND_TEMPLATE_MESSAGE_URL.replace("ACCESS_TOKEN", token);
		JSONObject jo = doPostStr(url, templateMsg);
		return jo;
	}
	
	//身份证验证
	public static JSONObject iDcardAndName(String idCard,String name) throws IOException{
		String url = Constants.IDCARD_URL.replace("YOUR_KEY", Constants.APPKEY).replace("ID_CARD", idCard).replace("REAL_NAME", URLEncoder.encode(name,"UTF-8"));
		JSONObject jo = doGetStr(url);
		return jo;
	}
}
