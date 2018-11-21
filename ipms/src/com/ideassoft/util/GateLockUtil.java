package com.ideassoft.util;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.ideassoft.core.constants.SystemConstants;
import com.ideassoft.core.factory.ServiceFactory;
import com.ideassoft.pms.service.RoomService;

public class GateLockUtil {
	
private static RoomService roomService = (RoomService) ServiceFactory.getService("roomService");
	
	//远程开锁
	public static String gateLock(String lockno, String LockType,String cardno){
		String result = "0";
		try{
			String username_wb = SystemConstants.GateLock.USER_NAME;
			String timestamp = DateUtil.t2s(new Date());
			String key = SystemConstants.GateLock.KEY;
			String appId = SystemConstants.GateLock.APP_ID;
			List<?> list = roomService.findBySQL("queryIdByLockNo", new String[]{lockno},true);
			String gatewayNo = ((Map<?, ?>) list.get(0)).get("GATEWAY_NO").toString();
			String deviceNo = lockno;
			String type = LockType;
			String password = SystemConstants.GateLock.GATELOCK_PASSWORD;
			String gatePassword = SystemConstants.GateLock.PASSWORD;
			String locakUserName = "wsqh";
			Integer passwordType = 0;
			String password_wb = "";
			String md5pwd = "";
			if(type.equals("1")){
				md5pwd = "app_id="+appId+"&deviceNo="+deviceNo+"&gatewayNo="+gatewayNo+"&key="+key+"&password="+password+"&timestamp="+timestamp+"" +
				"&type="+type+"&username_wb="+username_wb+"&password="+gatePassword+"";
			}else if(type.equals("3") || type.equals("5")){
				passwordType = 3;
				password = cardno;
				md5pwd = "app_id="+appId+"&deviceNo="+deviceNo+"&gatewayNo="+gatewayNo+"&key="+key+"&locakUserName="+locakUserName+"&password="+password+"&passwordType="+passwordType+"&timestamp="+timestamp+"" +
				"&type="+type+"&username_wb="+username_wb+"&password="+gatePassword+"";
			}else if(type.equals("2") || type.equals("4")){
				md5pwd = "app_id="+appId+"&deviceNo="+deviceNo+"&gatewayNo="+gatewayNo+"&key="+key+"&locakUserName="+locakUserName+"&password="+password+"&timestamp="+timestamp+"" +
				"&type="+type+"&username_wb="+username_wb+"&password="+gatePassword+"";
			}
			//签名信息
			password_wb = MD5Util.getCryptogram(md5pwd);
			
			URL url = new URL(SystemConstants.GateLock.WS_URL_Lock);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);  
			connection.setDoInput(true);  
			connection.setRequestMethod("POST");  
			connection.setUseCaches(false);  
			connection.setInstanceFollowRedirects(true);  
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");   
			connection.connect();
			DataOutputStream dataout = new DataOutputStream(connection.getOutputStream());  
			String decode = "";
			if(type.equals("1")){
				decode = "app_id="+appId+"&username_wb="+username_wb+"&timestamp="+timestamp+"&key="+key+"&gatewayNo="+gatewayNo+"" +
				"&deviceNo="+deviceNo+"&type="+type+"&password_wb="+password_wb+"&password="+password;
			}else if(type.equals("3") || type.equals("5")){
				passwordType = 3;
				password = cardno;
				decode = "app_id="+appId+"&username_wb="+username_wb+"&timestamp="+timestamp+"&key="+key+"&gatewayNo="+gatewayNo+"" +
				"&deviceNo="+deviceNo+"&type="+type+"&passwordType="+passwordType+"&locakUserName="+locakUserName+"&password_wb="+password_wb+"&password="+password;
			}else if(type.equals("2") || type.equals("4")){
				decode = "app_id="+appId+"&username_wb="+username_wb+"&timestamp="+timestamp+"&key="+key+"&gatewayNo="+gatewayNo+"" +
				"&deviceNo="+deviceNo+"&type="+type+"&locakUserName="+locakUserName+"&password_wb="+password_wb+"&password="+password;
			}
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
	        String errMSg = demoJson.getString("err_msg");
	        if(err.equals("0")){
	            return result;
	        } else {
	        	result = err;
	        	return result;
	        } 
		} catch (Exception e) {
			result = "-1";
        	return result;
        }
	}
	
	

}
