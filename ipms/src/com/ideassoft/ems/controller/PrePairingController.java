package com.ideassoft.ems.controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ideassoft.core.ajax.AjaxResult;
import com.ideassoft.core.constants.SystemConstants;
import com.ideassoft.pms.service.RoomService;
import com.ideassoft.util.DateUtil;
import com.ideassoft.util.JSONUtil;
import com.ideassoft.util.MD5Util;

@Controller
public class PrePairingController {
	
	@Autowired
	private RoomService roomService;
	
	//添加网关
	@RequestMapping("/perPairing.do")
	public void perPairing(HttpServletRequest req,HttpServletResponse resp, String lockno, String gatewayNo,String type){
		try{
			String username_wb = SystemConstants.GateLock.USER_NAME;
			String timestamp = DateUtil.t2s(new Date());
			String key = SystemConstants.GateLock.KEY;
			String appId = SystemConstants.GateLock.APP_ID;
			String deviceNo = lockno;
			String gatePassword = SystemConstants.GateLock.PASSWORD;
			String password = SystemConstants.GateLock.GATELOCK_PASSWORD;
			String password_wb = "";
			String md5pwd = "app_id="+appId+"&deviceNo="+deviceNo+"&gatewayNo="+gatewayNo+"&key="+key+"&timestamp="+timestamp+"" +
				"&type="+type+"&username_wb="+username_wb+"&password="+gatePassword+"";
			//签名信息
			password_wb = MD5Util.getCryptogram(md5pwd);
		
			URL url = new URL(SystemConstants.GateLock.WS_URL_Pair);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);  
			connection.setDoInput(true);  
			connection.setRequestMethod("POST");  
			connection.setUseCaches(false);  
			connection.setInstanceFollowRedirects(true);  
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");   
			connection.connect();
			DataOutputStream dataout = new DataOutputStream(connection.getOutputStream());  
			String decode = "app_id="+appId+"&username_wb="+username_wb+"&timestamp="+timestamp+"&key="+key+"&gatewayNo="+gatewayNo+"" +
				"&deviceNo="+deviceNo+"&type="+type+"&password_wb="+password_wb;
			dataout.writeBytes(decode);  
			dataout.flush();  
			dataout.close();   
			InputStream is = connection.getInputStream();
	        int size = is.available();
	        byte[] jsonBytes = new byte[size];
	        is.read(jsonBytes);
	        String message = new String(jsonBytes, "UTF-8");
	        JSONObject demoJson =  JSONObject.fromObject(message);
	        String err = demoJson.getString("err");
	        if(err.equals("0")){
	            JSONUtil.responseJSON(resp, new AjaxResult(1, demoJson.getString("err_msg")));
	        } else {
	        	JSONUtil.responseJSON(resp, new AjaxResult(0, demoJson.getString("err_msg")));
	        }
	        is.close();
			connection.disconnect(); 
		} catch (Exception e) {
        	JSONUtil.responseJSON(resp, new AjaxResult(-1, null));
        }
	}
}
