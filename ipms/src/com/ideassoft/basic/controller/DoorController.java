package com.ideassoft.basic.controller;


import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.JsonObject;












import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.ideassoft.bean.Equipment;
import com.ideassoft.bean.Staff;
import com.ideassoft.core.ajax.AjaxResult;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.bean.pageConfig.PageConfig;
import com.ideassoft.core.constants.CommonConstants;
import com.ideassoft.core.constants.SystemConstants;
import com.ideassoft.core.page.PageBuilder;
import com.ideassoft.crm.service.CommonService;
import com.ideassoft.util.CardUtil;
import com.ideassoft.util.DateUtil;
import com.ideassoft.util.DoorImportUtil;
import com.ideassoft.util.HttpUtil;
import com.ideassoft.util.ImportUtil;
import com.ideassoft.util.JSONUtil;
import com.ideassoft.util.MD5Util;
import com.ideassoft.util.RequestUtil;
import com.ideassoft.util.SpringUtil;

@Transactional
@Controller
public class DoorController {
	// 日志管理
	private static Logger log = Logger.getLogger(DoorController.class);

	@Autowired
	CommonService commonService;
	
	@Autowired
	PageBuilder pageBuilder;
	/**
	 * 用于门锁设备数据同步
	 * 
	 */
	@RequestMapping("/doorDataAbutment.do")
	public void doorSyscData(HttpServletRequest request,HttpServletResponse response) {
		try {
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("sign", 16);
			jsonObject.addProperty("ownerId", CommonConstants.doorInterfaceParam.OWNERID);
			jsonObject.addProperty("operatorId", CommonConstants.doorInterfaceParam.OPERATORID);
			String re = HttpUtil.postData(jsonObject.toString(), CommonConstants.ZyDoorIp.IP);
			LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
			
			Map<String,String> map = new HashMap<String,String>();
			JSONUtil.buildParamFromJSON(map,re);

			if(map.get("result").toString().equals(CommonConstants.doorInterfaceResult.SUCESS)){
				JSONArray jsonArray = JSONArray.fromObject(map.get("devices")); 
				for(int i = 0; i < jsonArray.size(); i++){
					String  gatewayName = jsonArray.getJSONObject(i).getString("gatewayName").toString(); 
					String  gatewayComment = jsonArray.getJSONObject(i).getString("gatewayComment").toString();
					String  gatewayLocation = jsonArray.getJSONObject(i).getString("gatewayLocation").toString();
					String  gatewayStatus =  Integer.toString(jsonArray.getJSONObject(i).getInt("gatewayStatus"));
					String  gatewayCode = jsonArray.getJSONObject(i).getString("gatewayCode").toString();
					String  lockLists = jsonArray.getJSONObject(i).getString("lockLists").toString();
					JSONArray jsonArraynew = JSONArray.fromObject(lockLists); 
					for(int j = 0; j < jsonArraynew.size(); j++){
						String  lockLocation = jsonArraynew.getJSONObject(j).getString("lockLocation").toString(); 
						String  lockPower = Integer.toString(jsonArraynew.getJSONObject(j).getInt("lockPower"));
						String  lockComment = jsonArraynew.getJSONObject(j).getString("lockComment").toString();
						String  lockCode = jsonArraynew.getJSONObject(j).getString("lockCode").toString();
						String  lockName = jsonArraynew.getJSONObject(j).getString("lockName").toString();
						String  lockStatus = Integer.toString(jsonArraynew.getJSONObject(j).getInt("lockStatus"));
						//根据网关和设备号查重，有的只改两者的状态，没有的新增，每个设备只用recordTime最新的
						Equipment equipmenta = (Equipment)commonService.findOneByProperties(Equipment.class, "serialNo", lockCode, "gateWayCode",gatewayCode);
						
						if(equipmenta == null){
							Equipment equipment = new Equipment();
							equipment.setDataId(loginuser.getStaff().getBranchId() + commonService.getSequence("SEQ_EQUIPMENT_ID"));
							
							equipment.setBranchId("H");
							equipment.setSuperKind(CommonConstants.serviceKind.DOOR);
							equipment.setSerialNo(lockCode);
							equipment.setRecordUser(SystemConstants.User.ADMIN_ID);
							equipment.setRecordTime(new Date());
							equipment.setGateWayName(gatewayName);
							equipment.setGateWayCode(gatewayCode);
							equipment.setGatewayLocation(gatewayLocation);
							equipment.setGateWayComment(gatewayComment);
							equipment.setGateWayStatus(gatewayStatus);
							equipment.setLockName(lockName);
							equipment.setLockComment(lockComment);
							equipment.setLockLocation(lockLocation);
							equipment.setLockPower(lockPower);
							equipment.setStatus(lockStatus);
							commonService.save(equipment);
						} else {
							//后期要修改branchID
							equipmenta.setRecordTime(new Date());
							equipmenta.setGateWayName(gatewayName);
							equipmenta.setGatewayLocation(gatewayLocation);
							equipmenta.setGateWayComment(gatewayComment);
							equipmenta.setGateWayStatus(gatewayStatus);
							equipmenta.setLockName(lockName);
							equipmenta.setLockComment(lockComment);
							equipmenta.setLockLocation(lockLocation);
							equipmenta.setLockPower(lockPower);
							equipmenta.setStatus(lockStatus);
							commonService.update(equipmenta);
						}
					}
				}
				
				//分配门锁网关
				JsonObject jsonObjectDoor = new JsonObject();
				jsonObjectDoor.addProperty("sign", 100);
				jsonObjectDoor.addProperty("ownerId", CommonConstants.doorInterfaceParam.OWNERID);
				jsonObjectDoor.addProperty("operatorId", CommonConstants.doorInterfaceParam.OPERATORID);
				String data = new String(jsonObjectDoor.toString().getBytes(),"UTF-8");
				String res = HttpUtil.postData(data, CommonConstants.ZyDoorIp.IP);
				Map<String,String> maps = new HashMap<String,String>();
				JSONUtil.buildParamFromJSON(maps,res);
				String result = maps.get("result").toString();
				if(result.equals("0")){
					JSONUtil.responseJSON(response, new AjaxResult(Integer.parseInt(CommonConstants.doorReturnToJs.SUCESS),"数据同步成功！"));		
				} else {
					JSONUtil.responseJSON(response, new AjaxResult(Integer.parseInt(CommonConstants.doorReturnToJs.SUCESS),"数据同步成功,返回安装返回码失败！"));	
				}
			} else {
				JSONUtil.responseJSON(response, new AjaxResult(Integer.parseInt(CommonConstants.doorReturnToJs.FAILED),"数据同步失败！"));	
			}
		} catch (Exception e) {
			log.error("sysc doordata occurs error! " + e.getMessage());
			JSONUtil.responseJSON(response, new AjaxResult(Integer.parseInt(CommonConstants.doorReturnToJs.FAILED),"数据同步失败！"));
		}
	}

	/*@RequestMapping("/AddCardDataInDoor.do")
	public void AddCardDataInDoor(HttpServletRequest request,HttpServletResponse response) {
		try {
			Integer result = CardUtil.doorAddCardData(request, response , gatewayCode, lockCode, name, cardNumb, startTime, endTime);
			if(result != CommonConstants.PortalResultCode.FAILED && !result.toString().equals(CommonConstants.doorInterfaceResult.FAILED)){
				JSONUtil.responseJSON(response, new AjaxResult(Integer.parseInt(CommonConstants.doorReturnToJs.SUCESS),"数据添加成功！"));	
			} else {
				JSONUtil.responseJSON(response, new AjaxResult(Integer.parseInt(CommonConstants.doorReturnToJs.FAILED),"数据添加失败！"));
			}	 
		} catch (Exception e) {
			log.error("transfer data occurs error! " + e.getMessage());
			JSONUtil.responseJSON(response, new AjaxResult(Integer.parseInt(CommonConstants.doorReturnToJs.FAILED),"数据添加失败！"));
		}
	}

	@RequestMapping("/DeleteCardDataInDoor.do")
	public void DeleteCardDataInDoor(HttpServletRequest request,HttpServletResponse response) {
		try {
			Integer result = CardUtil.doorDelCardData(request, response, lockCode, cardNumb);
			if(result != CommonConstants.PortalResultCode.FAILED && !result.toString().equals(CommonConstants.doorInterfaceResult.FAILED)){
				JSONUtil.responseJSON(response, new AjaxResult(Integer.parseInt(CommonConstants.doorReturnToJs.SUCESS),"数据删除成功！"));	
			} else {
				JSONUtil.responseJSON(response, new AjaxResult(Integer.parseInt(CommonConstants.doorReturnToJs.FAILED),"数据删除失败！"));
			}	 
		} catch (Exception e) {
			log.error("transfer data occurs error! " + e.getMessage());
			JSONUtil.responseJSON(response, new AjaxResult(Integer.parseInt(CommonConstants.doorReturnToJs.FAILED),"数据删除失败！"));
		}
	}
	*/
	@RequestMapping("/test.do")
	public ModelAndView test(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/page/basic/user/test");
		return mv;
	}
	
	@RequestMapping("/importDoorFile.do")
	public ModelAndView importDoorFile(String modelId, String pageId, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/report/importDoor");

		PageConfig pageConfig = pageBuilder.getPageConfigByPageId(modelId, pageId);
		String fileType = pageConfig.getUploadFileType();

		if (!StringUtils.isEmpty(fileType)) {
			mv.addObject("mode", fileType);
			mv.addObject("fileName", pageConfig.getUploadFileName());
			mv.addObject("dir", pageConfig.getUploadFileDir());
		}

		return mv;
	}

	@RequestMapping("/importDoorData.do")
	public void importDoorData(String modelId, String pageId, String key, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile srcFile = multipartRequest.getFile(key);

		PageConfig pageConfig = pageBuilder.getPageConfigByPageId(modelId, pageId);

		Map<String, ?> errors = null;
		DoorImportUtil iu = new DoorImportUtil(pageConfig, srcFile, setDefaultDoorParam(multipartRequest, response), request);
		errors = iu.importData();

		response.getWriter().write(
				errors.size() > 0 ? net.sf.json.JSONObject.fromObject(errors).toString() : "{ \"sucess\": -1 }");
	}

	public String setDefaultDoorParam(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginUser.getStaff();
		paramMap.put("PASSWORD", MD5Util.getCryptogram("888888"));
		paramMap.put("BRANCHID", staff.getBranchId());
		paramMap.put("RECORDUSER", staff.getStaffId());
		paramMap.put("STATUS", CommonConstants.STATUS.NORMAL);
		paramMap.put("SUPERKIND", "5");

		return JSONUtil.fromObject(paramMap).toString();
	}
}
