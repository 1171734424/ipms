package com.ideassoft.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hsqldb.lib.StringUtil;

import net.sf.json.JSONObject;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.ideassoft.core.constants.CommonConstants;

public class CardUtil {
	
	//添加身份证授权

	public static Integer doorAddCardData(HttpServletRequest request, HttpServletResponse response, String gatewayCode, String lockCode, String name, String cardNumb, String startTime, String endTime) {

		try {
			Integer re = Integer.parseInt(CommonConstants.doorInterfaceResult.FAILED);

			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			String serialNumber = simpleDateFormat.format(new Date()) + lockCode + String.valueOf(new Random().nextInt(9999-1000+1)+1000);
			String newName = new String(name.getBytes(),"UTF-8");
			
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("sign", 18);
			jsonObject.addProperty("ownerId",CommonConstants.doorInterfaceParam.OWNERID);
			jsonObject.addProperty("operatorId", CommonConstants.doorInterfaceParam.OPERATORID);
			jsonObject.addProperty("gatewayCode", gatewayCode);
			jsonObject.addProperty("lockCode", lockCode);
			jsonObject.addProperty("serviceNumb", serialNumber);
			jsonObject.addProperty("name", newName);
			jsonObject.addProperty("cardNumb", cardNumb);
			jsonObject.addProperty("dnCode", "");
			jsonObject.addProperty("startTime", startTime);
			jsonObject.addProperty("endTime", endTime);
			jsonObject.addProperty("timetag", "");
			String data = new String(jsonObject.toString().getBytes(),"UTF-8");
	    	
			String res = HttpUtil.postData(data, CommonConstants.ZyDoorIp.IP);
			Map<String,String> map = new HashMap<String,String>();
			JSONUtil.buildParamFromJSON(map,res);
			String result = map.get("result").toString();
			if(result != null && !result.equals("")){
				re = Integer.parseInt(result);
			}
			return re;
		}catch (Exception e) {
			e.printStackTrace();
			return CommonConstants.PortalResultCode.FAILED;
		}
	}
	
	
	//取消某个身份证某锁中身份证授权
	public static Integer doorDelCardData(HttpServletRequest request, HttpServletResponse response, String lockCode, String cardNumb) {
		try {
			Integer re = Integer.parseInt(CommonConstants.doorInterfaceResult.FAILED);
			
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("sign", 19);
			jsonObject.addProperty("ownerId",CommonConstants.doorInterfaceParam.OWNERID);
			jsonObject.addProperty("operatorId", CommonConstants.doorInterfaceParam.OPERATORID);
			jsonObject.addProperty("lockCode", lockCode);
			jsonObject.addProperty("serviceNumb", "");
			jsonObject.addProperty("cardNumb", cardNumb);
			jsonObject.addProperty("timetag", "");
			String data = new String(jsonObject.toString().getBytes(),"UTF-8");
	    	
			String res = HttpUtil.postData(data, CommonConstants.ZyDoorIp.IP);
			Map<String,String> map = new HashMap<String,String>();
			JSONUtil.buildParamFromJSON(map,res);
			String result = map.get("result").toString();
			if(result != null && !result.equals("")){
				re = Integer.parseInt(result);
			}
			return re;
		}catch (Exception e) {
			e.printStackTrace();
			return CommonConstants.PortalResultCode.FAILED;
		}
	}
	
	//取消某个身份证某锁中身份证授权,定时任务中使用
		public static Integer doorDelCardDataInTask(String lockCode, String cardNumb) {
			try {
				Integer re = Integer.parseInt(CommonConstants.doorInterfaceResult.FAILED);
				
				JsonObject jsonObject = new JsonObject();
				jsonObject.addProperty("sign", 19);
				jsonObject.addProperty("ownerId",CommonConstants.doorInterfaceParam.OWNERID);
				jsonObject.addProperty("operatorId", CommonConstants.doorInterfaceParam.OPERATORID);
				jsonObject.addProperty("lockCode", lockCode);
				jsonObject.addProperty("serviceNumb", "");
				jsonObject.addProperty("cardNumb", cardNumb);
				jsonObject.addProperty("timetag", "");
				String data = new String(jsonObject.toString().getBytes(),"UTF-8");
		    	
				String res = HttpUtil.postData(data, CommonConstants.ZyDoorIp.IP);
				Map<String,String> map = new HashMap<String,String>();
				JSONUtil.buildParamFromJSON(map,res);
				String result = map.get("result").toString();
				if(result != null && !result.equals("")){
					re = Integer.parseInt(result);
				}
				return re;
			}catch (Exception e) {
				e.printStackTrace();
				return CommonConstants.PortalResultCode.FAILED;
			}
		}
	
	//取消某个身份证某锁中某条身份证授权
		public static Integer oneDoorDelCardData(HttpServletRequest request, HttpServletResponse response, String lockCode, String cardNumb, String serviceNumb) {
			try {
				Integer re = Integer.parseInt(CommonConstants.doorInterfaceResult.FAILED);
				
				JsonObject jsonObject = new JsonObject();
				jsonObject.addProperty("sign", 19);
				jsonObject.addProperty("ownerId",CommonConstants.doorInterfaceParam.OWNERID);
				jsonObject.addProperty("operatorId", CommonConstants.doorInterfaceParam.OPERATORID);
				jsonObject.addProperty("lockCode", lockCode);
				jsonObject.addProperty("serviceNumb", serviceNumb);
				jsonObject.addProperty("cardNumb", cardNumb);
				jsonObject.addProperty("timetag", "");
				String data = new String(jsonObject.toString().getBytes(),"UTF-8");
		    	
				String res = HttpUtil.postData(data, CommonConstants.ZyDoorIp.IP);
				Map<String,String> map = new HashMap<String,String>();
				JSONUtil.buildParamFromJSON(map,res);
				String result = map.get("result").toString();
				if(result != null && !result.equals("")){
					re = Integer.parseInt(result);
				}
				return re;
			}catch (Exception e) {
				e.printStackTrace();
				return CommonConstants.PortalResultCode.FAILED;
			}
		}
	
	//取消门锁内所有身份证信息
	public static Integer doorDelAllCardData(HttpServletRequest request, HttpServletResponse response, String lockCode, String gatewayCode) {
		try {
			Integer re = Integer.parseInt(CommonConstants.doorInterfaceResult.FAILED);
			
			JsonArray jsonArrayList = new JsonArray();
	    	JsonObject lockInfo = new JsonObject();
	    	lockInfo.addProperty("lockCode", lockCode);
	    	lockInfo.addProperty("gatewayCode", gatewayCode);
	    	jsonArrayList.add(lockInfo);
	    	
	    	JsonObject jsonObjects = new JsonObject();
	    	jsonObjects.addProperty("sign", 40);
	    	jsonObjects.addProperty("ownerId",CommonConstants.doorInterfaceParam.OWNERID);
	    	jsonObjects.addProperty("operatorId", CommonConstants.doorInterfaceParam.OPERATORID);
	    	jsonObjects.add("lockList", jsonArrayList);
	    	String data = new String(jsonObjects.toString().getBytes(),"UTF-8");
	    	
			String res = HttpUtil.postData(data, CommonConstants.ZyDoorIp.IP);
			Map<String,String> map = new HashMap<String,String>();
			JSONUtil.buildParamFromJSON(map,res);
			String result = map.get("result").toString();
			if(result != null && !result.equals("")){
				re = Integer.parseInt(result);
			}
			return re;
		}catch (Exception e) {
			e.printStackTrace();
			return CommonConstants.PortalResultCode.FAILED;
		}
	}
	
	//将某身份证批量添加到多个门锁中
	public static Integer cardDataAddMutilDoor(HttpServletRequest request, HttpServletResponse response, String cardData, String doorDataList) {
		try {
			Integer re = Integer.parseInt(CommonConstants.doorInterfaceResult.FAILED);
			String startTime = "";
			String endTime = "";
			JsonArray jsonArrayList = new JsonArray();
			
			net.sf.json.JSONArray jsonArray = net.sf.json.JSONArray.fromObject(doorDataList);
			net.sf.json.JSONObject jsonObject = null;
			
			for (int i = 0; i < jsonArray.size(); i++) {
				jsonObject = jsonArray.getJSONObject(i);
				String lockno = jsonObject.getString("lockno");
				String gatewayCode = jsonObject.getString("gatewayCode");
				String serviceNumb = jsonObject.getString("serviceNumb");
				JsonObject lockInfo = new JsonObject();
		    	lockInfo.addProperty("lockCode", lockno);
		    	lockInfo.addProperty("gatewayCode", gatewayCode);
		    	lockInfo.addProperty("serviceNumb", serviceNumb);
		    	jsonArrayList.add(lockInfo);
			}
			
			net.sf.json.JSONObject cardInfo = JSONObject.fromObject(cardData);
			String name = cardInfo.getString("name");
			String newName = new String(name.getBytes(),"UTF-8");
			String cardNumb = cardInfo.getString("cardNumb");
			
			if(cardInfo.getString("startTime") != null && !StringUtil.isEmpty(cardInfo.getString("startTime"))){
				startTime = cardInfo.getString("startTime");
				startTime = startTime.replace("/", "").replace(":", "").replace(" ", "");
			}
			
			if(cardInfo.getString("endTime") != null && !StringUtil.isEmpty(cardInfo.getString("endTime"))){
				endTime = cardInfo.getString("endTime");
				endTime = endTime.replace("/", "").replace(":", "").replace(" ", "");
			}
			
	    	JsonObject jsonObjects=new JsonObject();
	    	jsonObjects.addProperty("sign", 41);
	    	jsonObjects.addProperty("ownerId",CommonConstants.doorInterfaceParam.OWNERID);
	    	jsonObjects.addProperty("operatorId", CommonConstants.doorInterfaceParam.OPERATORID);
	    	jsonObjects.addProperty("name", newName);
	    	jsonObjects.addProperty("cardNumb", cardNumb);
	    	jsonObjects.addProperty("startTime", startTime);
	    	jsonObjects.addProperty("endTime", endTime);
	    	jsonObjects.addProperty("timetag", "");
	    	jsonObjects.add("lockList", jsonArrayList);
	    	//System.out.println("发："+jsonObjects.toString());
	    	String data = new String(jsonObjects.toString().getBytes(),"UTF-8");
	    	
			String res = HttpUtil.postData(data, CommonConstants.ZyDoorIp.IP);
			//System.out.println("收："+res);
			Map<String,String> map = new HashMap<String,String>();
			JSONUtil.buildParamFromJSON(map,res);
			String result = map.get("result").toString();
			if(result != null && !result.equals("")){
				re = Integer.parseInt(result);
			}
			return re;
		}catch (Exception e) {
			e.printStackTrace();
			return CommonConstants.PortalResultCode.FAILED;
		}
	}
	
	
	
}