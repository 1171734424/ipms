package com.ideassoft.ems.controller;


import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.apache.log4j.Logger;
import org.hsqldb.lib.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
















import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.ideassoft.basic.controller.DoorController;
import com.ideassoft.basic.service.HouseBasicService;
import com.ideassoft.bean.Branch;
import com.ideassoft.bean.Check;
import com.ideassoft.bean.CheckUser;
import com.ideassoft.bean.City;
import com.ideassoft.bean.Contrart;
import com.ideassoft.bean.ControlLog;
import com.ideassoft.bean.DoorOpenRight;
import com.ideassoft.bean.Equipment;
import com.ideassoft.bean.FixedUserInDoor;
import com.ideassoft.bean.Frequenter;
import com.ideassoft.bean.House;
import com.ideassoft.bean.Member;
import com.ideassoft.bean.OperateLog;
import com.ideassoft.bean.Order;
import com.ideassoft.bean.Room;
import com.ideassoft.bean.RoomKey;
import com.ideassoft.bean.RoomStatus;
import com.ideassoft.bean.Staff;
import com.ideassoft.bean.SysParam;
import com.ideassoft.bean.WarningLog;
import com.ideassoft.core.ajax.AjaxResult;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.constants.CommonConstants;
import com.ideassoft.core.constants.SystemConstants;
import com.ideassoft.crm.service.InitialService;
import com.ideassoft.crm.service.TransferServcie;
import com.ideassoft.pms.service.RoomService;
import com.ideassoft.util.CardUtil;
import com.ideassoft.util.DateUtil;
import com.ideassoft.util.HttpUtil;
import com.ideassoft.util.IPUtil;
import com.ideassoft.util.JSONUtil;
import com.ideassoft.util.MD5Util;
import com.ideassoft.util.RequestUtil;
import com.ideassoft.util.SpringUtil;
import com.qcloud.sms.SmsSingleSender;

@Transactional
@Controller
public class GateLockController {
	
	private static Logger log = Logger.getLogger(DoorController.class);
	
	@Autowired
	RoomService roomService;
	
	@Autowired
	private HouseBasicService houseBasicService;
	
	@Autowired
	InitialService initialService;
	
	//远程开锁
	@RequestMapping("/gateLock.do")
	public void gateLock(HttpServletRequest req,HttpServletResponse resp, String lockno, String LockType,String cardno){
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
			String branchide = "";
			String flooride = "";
			String roomide = "";
			String recordUser = "";
			String orderId = "";
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
	        if(err.equals("0")){
	            JSONUtil.responseJSON(resp, new AjaxResult(1, demoJson.getString("err_msg")));
	        } else {
	        	JSONUtil.responseJSON(resp, new AjaxResult(0, demoJson.getString("err_msg")));
	        }
	        is.close();
			connection.disconnect(); 
			//记录开门日志
			ControlLog cl = new ControlLog();
			Equipment e = (Equipment)initialService.findOneByProperties(Equipment.class, "serialNo", lockno);
			if(e != null){
				branchide = e.getBranchId();
				flooride = e.getFloorId();
				roomide = e.getRoomId();
			}
			List<?> mList = initialService.findByProperties(Member.class, "idcard", cardno, "status", "1");
			List<?> sList = initialService.findByProperties(Staff.class, "idcard", cardno, "status", "1");
			if(mList != null && mList.size() >= 1){
				recordUser = ((com.ideassoft.bean.Member)mList.get(0)).getMemberId();
				List<?> order = initialService.findByProperties(Check.class, "branchId",branchide,"roomId",roomide, "status", "1");
				List<?> contract = initialService.findByProperties(Contrart.class, "branchId",branchide,"roomId",roomide, "status", "1");
				List<?> contracts = initialService.findByProperties(Contrart.class, "branchId",branchide,"roomId",roomide, "status", "4");
				if(order != null && order.size() >= 1){
					orderId = ((Check)order.get(0)).getCheckId();
				} else if (contract != null && contract.size() >= 1){
					orderId = ((Contrart)contract.get(0)).getContrartId();
				} else if (contracts != null && contracts.size() >= 1){
					orderId = ((Contrart)contracts.get(0)).getContrartId();;
				}
			} else if (sList != null && sList.size() >= 1){
				recordUser = ((Staff)sList.get(0)).getStaffId();
			} else {
				recordUser = "-1";
			}
			String logId = initialService.getCloudOrLocalSequence("SEQ_CONTROLLOG_ID");
			/*cl.setLogId(logId);
			cl.setBranchId(branchide);
			cl.setFloorId(flooride);
			cl.setRoomId(roomide);
			cl.setOperCommand("1");
			cl.setOrderId(orderId);*/
			cl.setRecordTime(new Date());
			cl.setRecordUser(recordUser);
			cl.setStatus("1");
			cl.setRemark(lockno);
			initialService.getHibernateTemplate().save(cl);
		} catch (Exception e) {
        	JSONUtil.responseJSON(resp, new AjaxResult(-1, null));
        }
	}
	
	
	
	@RequestMapping("addAndDeleteUser.do")
	public ModelAndView addAndDeleteUser(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String branchId = request.getParameter("branchId");
		String roomId = request.getParameter("roomId");
		String lockno = request.getParameter("lockno");
		String isCheck = request.getParameter("isCheck");
		//Branch bha = (Branch)roomService.findOneByProperties(Branch.class, "rank", "0", "status","1");
		String branchIdNum = request.getParameter("branchIdNum");
		String gatewayCode = request.getParameter("gatewayCode");
		
		String flag = "0";
		String checkIdOrContract = "";
		String type = "";
		List checkUserList = new ArrayList();
		ModelAndView mv = new ModelAndView();
		
		//查询在住订单中用户
		if(isCheck.equals(SystemConstants.ParamType.ISCHECK)){
			if(!branchIdNum.contains("H")){
				Branch bh = (Branch)roomService.findOneByProperties(Branch.class, "branchId", branchIdNum, "status","1");
				if(bh.getBranchType().equals("2")){
					Contrart ck = (Contrart) roomService.findOneByProperties(Contrart.class, "branchId", branchIdNum, "roomId",roomId, "status","4");
					if(ck != null){
						checkUserList = roomService.findByPropertiesWithSort(CheckUser.class, "checkuserType", "asc", "checkId", ck.getContrartId(), "status","1","checkinType","2");
						checkIdOrContract = ck.getContrartId();
					}
					type = "2";
				}else{
					Check ck = (Check) roomService.findOneByProperties(Check.class, "branchId", branchIdNum, "roomId",roomId, "status","1");
					if(ck != null){
						checkUserList = roomService.findByPropertiesWithSort(CheckUser.class, "checkuserType", "asc", "checkId", ck.getCheckId(), "status","1","checkinType","2");
						checkIdOrContract = ck.getCheckId();
					}
					type = "1";
				}
			} else {
				Check ck = (Check) roomService.findOneByProperties(Check.class, "branchId", branchIdNum, "roomId",roomId, "status","1");
				if(ck != null){
					checkUserList = roomService.findByPropertiesWithSort(CheckUser.class, "checkuserType", "asc", "checkId", ck.getCheckId(), "status","1","checkinType","2");
					checkIdOrContract = ck.getCheckId();
				}
				type = "1";	
			}
			if(checkUserList.size() > 0){
				flag = "1";
				mv.addObject("checkUserList", checkUserList);
			}
		}
		mv.addObject("flag", flag);
		//查询绑定在门锁上的用户
		List<?> FixedUserList = roomService.findByPropertiesWithSort(FixedUserInDoor.class,"recordTime", "asc", "equipSerialNo", lockno,"status","1");
		if(FixedUserList.size() > 0){
			mv.addObject("FixedUserList", FixedUserList);
		} else {
			mv.addObject("FixedUserList", null);
		}
		mv.addObject("serialNo", lockno);
		mv.addObject("gatewayCode", gatewayCode);
		mv.addObject("checkIdOrContract", checkIdOrContract);
		mv.addObject("type", type);
		mv.setViewName("page/ems/emssystem/addAndDeleteFixedUser");
		return mv;
	}
	
	@RequestMapping("/saveNewFixedUser.do")
	public void saveNewFixedUser(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String parameter = request.getParameter("myForm");
		String serialNo = request.getParameter("serialNo");
		String gatewayCode = request.getParameter("gatewayCode");
		String checkIdOrContract = request.getParameter("checkIdOrContract");
		String type = request.getParameter("type");
		List<FixedUserInDoor> list = new ArrayList<FixedUserInDoor>();
		net.sf.json.JSONArray jsonArray = net.sf.json.JSONArray.fromObject(parameter);
		net.sf.json.JSONObject jsonObject = null;
		FixedUserInDoor a = null;
		Object checkInUser = null;
		String flag = "0";
		String dataToDoorFlag = "0";
		for (int i = 0; i < jsonArray.size(); i++) {
			jsonObject = jsonArray.getJSONObject(i);
			FixedUserInDoor q = (FixedUserInDoor)initialService.findOneByProperties(FixedUserInDoor.class, "equipSerialNo", serialNo, "idcard", jsonObject.getString("idcard"), "status", "1");
			if(type.equals("2")){
				checkInUser = roomService.findOneByProperties(CheckUser.class, "checkuserType", "asc", "checkId", checkIdOrContract, "status","1","checkinType","2");
			}else{
				checkInUser = roomService.findOneByProperties(CheckUser.class, "checkuserType", "asc", "checkId", checkIdOrContract, "status","1","checkinType","2");
			}
			
			try {
				String startTimetemp = jsonObject.getString("startTime").toString().replace("/", "").replace(":", "").replace(" ", "");
				String endTimetemp = jsonObject.getString("endTime").toString().replace("/", "").replace(":", "").replace(" ", "");
				Integer result = CardUtil.doorAddCardData(request, response , gatewayCode, serialNo, jsonObject.getString("userName").toString(), jsonObject.getString("idcard").toString(), startTimetemp, endTimetemp);
				if(result != CommonConstants.PortalResultCode.FAILED && !result.toString().equals(CommonConstants.doorInterfaceResult.FAILED)){
					dataToDoorFlag = "0";
					log.info("门锁内某个身份证信息添加指令发送成功！");
					//JSONUtil.responseJSON(response, new AjaxResult(Integer.parseInt(CommonConstants.doorReturnToJs.SUCESS),"数据添加成功！"));	
				} else {
					dataToDoorFlag = "1";
					log.info("门锁内某个身份证信息添加指令发送失败！");
					//JSONUtil.responseJSON(response, new AjaxResult(Integer.parseInt(CommonConstants.doorReturnToJs.FAILED),"数据添加失败！"));
					//添加失败记日志
					SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
					WarningLog warningLog = new WarningLog();
					warningLog.setLogId(sdf.format(new Date()) + roomService.getSequence("SEQ_WARNINGLOG_ID"));
					warningLog.setRecordTime(new Date());
					warningLog.setRecordUser(SystemConstants.User.ADMIN_ID);
					warningLog.setWarningDate(new Date());
					warningLog.setStatus(CommonConstants.servieLogStatus.EFFECTIVE);
					warningLog.setWarningType(CommonConstants.warningLogStatus.ADD_CARD_FAILED);
					warningLog.setRemark("新增"+jsonObject.getString("idcard")+"失败");
					warningLog.setSerialNo(serialNo);
					roomService.save(warningLog);
				}	 
			} catch (Exception e) {
				log.error("transfer doordata  to door occurs error! " + e.getMessage());
				dataToDoorFlag = "1";
				JSONUtil.responseJSON(response, new AjaxResult(Integer.parseInt(CommonConstants.doorReturnToJs.FAILED),"数据写入门锁接口失败！"));
			}
			
			
			if(q == null && checkInUser == null && dataToDoorFlag.equals("0")){ 
				a = new FixedUserInDoor();
				LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				Date startdate = sdf.parse(jsonObject.getString("startTime").toString());
				Date enddate = sdf.parse(jsonObject.getString("endTime").toString());
				a.setEquipSerialNo(serialNo);
				a.setGender("1");
				a.setIdcard(jsonObject.getString("idcard").toString());
				a.setMobile(jsonObject.getString("mobile").toString());
				a.setEndTime(enddate);
				a.setStartTime(startdate);
				a.setRecordTime(new Date());
				a.setRecordUser(loginUser.getStaff().getStaffId());
				a.setSourceType("4");
				a.setStatus("1");
				a.setUserId(initialService.getCloudOrLocalSequence("SEQ_FIXEDUSERINDOOR_ID"));
				a.setUserName(jsonObject.getString("userName").toString());
				initialService.getHibernateTemplate().save(a);
				//数据转发流程
				/*Map<String, Object> mapList = new HashMap<String, Object>();
				List<FixedUserInDoor> fixedUserList = new ArrayList();
				fixedUserList.add(a);
				mapList.put("FixedUserInDoor", fixedUserList);	
				//查询当前登陆者的门店应为加盟还是自营
				Branch bc = (Branch)initialService.findOneByProperties(Branch.class, "branchId", loginUser.getStaff().getBranchId());
				//若在本地数据库添加，要数据转发
				if(!initialService.checkSystemExistCloud()){
					//若在本地数据库添加，要数据转发到云端
					String branchIpValue = CommonConstants.MarketCenterIpValue.centerIpValue;
					TransferServcie.getInstance().transferData(1, branchIpValue, JSONUtil.fromObject(mapList).toString());
				} else if(initialService.checkSystemExistCloud() && bc != null && bc.getBranchIp() != null && !StringUtil.isEmpty(bc.getBranchIp())){
					//若自营在云端添加，数据要下发到本地数据库
					TransferServcie.getInstance().transferData(1, bc.getBranchIp(), JSONUtil.fromObject(mapList).toString());
				}*/
			} else {
				flag = "1";
				break;
			}
		}
		//记操作日志
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String sequences = roomService.getSequence("SEQ_OPERATELOG_ID");
		String operid = IPUtil.getIpAddr(request);
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		String strdate = sdf.format(new Date());
		String logid = strdate + loginUser.getStaff().getBranchId() + sequences;
		OperateLog operatelog = new OperateLog();
		operatelog.setLogId(logid);
		operatelog.setRecordUser(SystemConstants.User.ADMIN_ID);
		operatelog.setRecordTime(new Date());
		operatelog.setBranchId(loginUser.getStaff().getBranchId());
		operatelog.setOperIp(operid);
		operatelog.setOperModule(SystemConstants.LogModule.DOOR);
		operatelog.setOperType(SystemConstants.LogModule.DOORCARDADD);	
		
		if(flag.equals("0") && dataToDoorFlag.equals("0")){
			operatelog.setContent("门锁内身份证信息新增成功！");
			roomService.save(operatelog);
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.SUCESS, null));
		} else if (dataToDoorFlag.equals("1")){
			operatelog.setContent("门锁内身份证信息新增成功，到门锁失败！");
			roomService.save(operatelog);
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, "dataToDoorFlase"));
		} else {
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.SUCESS, "again"));
		}
	}
	
	
	@RequestMapping("/deleteNewFixedUser.do")
	public void deleteNewFixedUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userId = request.getParameter("userId");
		String cardNumb = request.getParameter("idcard");
		String serialNo = request.getParameter("serialNo");
		String dataToDoorFlag = "0";
		FixedUserInDoor a = (FixedUserInDoor)initialService.findOneByProperties(FixedUserInDoor.class, "userId",userId);
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		
		try {
			Integer result = CardUtil.doorDelCardData(request, response, serialNo, cardNumb);
			if(result != CommonConstants.PortalResultCode.FAILED && !result.toString().equals(CommonConstants.doorInterfaceResult.FAILED)){
				dataToDoorFlag = "0";
				log.info("门锁内某个身份证信息删除指令发送成功！");
			} else {
				dataToDoorFlag = "1";
				log.info("门锁内某个身份证信息删除指令发送失败！");
				//删除某个身份证失败记日志
				SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
				WarningLog warningLog = new WarningLog();
				warningLog.setLogId(sdf.format(new Date()) + roomService.getSequence("SEQ_WARNINGLOG_ID"));
				warningLog.setRecordTime(new Date());
				warningLog.setRecordUser(SystemConstants.User.ADMIN_ID);
				warningLog.setWarningDate(new Date());
				warningLog.setStatus(CommonConstants.servieLogStatus.EFFECTIVE);
				warningLog.setWarningType(CommonConstants.warningLogStatus.DEL_CARD_FAILED);
				warningLog.setRemark("删除"+cardNumb+"失败");
				warningLog.setSerialNo(serialNo);
				roomService.save(warningLog);
			}	 
		} catch (Exception e) {
			log.error("transfer data occurs error! " + e.getMessage());
			dataToDoorFlag = "1";
		}
		
		//记操作日志
		String sequences = roomService.getSequence("SEQ_OPERATELOG_ID");
		String operid = IPUtil.getIpAddr(request);
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		String strdate = sdf.format(new Date());
		String logid = strdate + loginUser.getStaff().getBranchId() + sequences;
		OperateLog operatelog = new OperateLog();
		operatelog.setLogId(logid);
		operatelog.setRecordUser(SystemConstants.User.ADMIN_ID);
		operatelog.setRecordTime(new Date());
		operatelog.setBranchId(loginUser.getStaff().getBranchId());
		operatelog.setOperIp(operid);
		operatelog.setOperModule(SystemConstants.LogModule.DOOR);
		operatelog.setOperType(SystemConstants.LogModule.DOORCARDADD);	
		if(a != null && dataToDoorFlag.equals("0")){
			a.setStatus("0");
			a.setRecordTime(new Date());
			initialService.getHibernateTemplate().saveOrUpdate(a);
			operatelog.setContent("门锁内某个身份证信息删除成功！");
			roomService.save(operatelog);
			//数据转发流程
			/*Map<String, Object> mapList = new HashMap<String, Object>();
			List<FixedUserInDoor> fixedUserList = new ArrayList();
			fixedUserList.add(a);
			mapList.put("FixedUserInDoor", fixedUserList);	
			//查询当前登陆者的门店应为加盟还是自营
			Branch bc = (Branch)initialService.findOneByProperties(Branch.class, "branchId", loginUser.getStaff().getBranchId());
			//若在本地数据库添加，要数据转发
			if(!initialService.checkSystemExistCloud()){
				//若在本地数据库添加，要数据转发到云端
				String branchIpValue = CommonConstants.MarketCenterIpValue.centerIpValue;
				TransferServcie.getInstance().transferData(1, branchIpValue, JSONUtil.fromObject(mapList).toString());
			} else if(initialService.checkSystemExistCloud() && bc != null && bc.getBranchIp() != null && !StringUtil.isEmpty(bc.getBranchIp())){
				//若自营在云端添加，数据要下发到本地数据库
				TransferServcie.getInstance().transferData(1, bc.getBranchIp(), JSONUtil.fromObject(mapList).toString());
			}*/
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.SUCESS, null));
		} else if (dataToDoorFlag.equals("1")){
			operatelog.setContent("门锁内某个身份证信息删除成功，到门锁失败！");
			roomService.save(operatelog);
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, "dataToDoorFlase"));
		} else {
			operatelog.setContent("门锁内某个身份证信息删除失败！");
			roomService.save(operatelog);
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, null));
		}
	}
	
	//批量添加多个身份证到多个锁中，跳加载的页面
	@RequestMapping("doorAddUser.do")
	public ModelAndView doorAddUser(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String lockno = request.getParameter("lockno");	
		String gatewayCode = request.getParameter("gatewayCode");
		String roomId = request.getParameter("roomId");
		String branchIdNum = request.getParameter("branchIdNum");
		String branchIdName = request.getParameter("branchIdName");
		String [] locknocount = lockno.split(",");
		String [] branchIdNumcount = branchIdNum.split(",");
		String [] gatewayCodecount = gatewayCode.split(",");
		String [] roomIdcount = roomId.split(",");
		String [] branchIdNamecount = branchIdName.split(",");
		
		JsonArray jsonArrayList = new JsonArray();
		
		for(int i = 0; i < branchIdNumcount.length ; i++){
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("lockno", locknocount[i]);
			jsonObject.addProperty("gatewayCode",gatewayCodecount[i]);
			jsonObject.addProperty("roomId", roomIdcount[i]);
			jsonObject.addProperty("branchIdNum", branchIdNumcount[i]);
			jsonObject.addProperty("branchIdName", branchIdNamecount[i]);
			jsonArrayList.add(jsonObject);
		}
		ModelAndView mv = new ModelAndView();
		//查询所有用户固定
		List<?> FrequenterList = roomService.findByPropertiesWithSort(Frequenter.class,"recordTime", "asc","status","1");
		if(FrequenterList.size() > 0){
			mv.addObject("FrequenterList", FrequenterList);
		} else {
			mv.addObject("FrequenterList", null);
		}
		
		mv.addObject("jsonArrayList", jsonArrayList);
		mv.addObject("serialNo", lockno);
		mv.addObject("gatewayCode", gatewayCode);
		mv.addObject("roomId", roomId);
		mv.addObject("branchIdNum", branchIdNum);
		mv.addObject("branchIdName", branchIdName);
		mv.setViewName("page/ems/emssystem/frequenter");
		return mv;
	}
	
	//批量添加多个身份证到多个锁中
	@RequestMapping("mutilDoorAddUser.do")
	public void mutilDoorAddUser(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String dataToDoorFlag = "0";
		String doorData = request.getParameter("doorData");	
		String username = request.getParameter("newarray");
		String locklist = "";
		Integer failcount = 0;
		String cardlistfail = "";
		String cardlist = "";
		String cardlistsuss = "";
		String startTime = "";
		String endTime = "";
		
		net.sf.json.JSONArray jsonArray = net.sf.json.JSONArray.fromObject(doorData);
		net.sf.json.JSONObject jsonObject = null;
		net.sf.json.JSONObject jsonObjects = null;
		
		
		
		
		net.sf.json.JSONArray jsonArrayUser = net.sf.json.JSONArray.fromObject(username);
		net.sf.json.JSONObject jsonObjectUser = null;
		
		for (int i = 0; i < jsonArrayUser.size(); i++) {
			jsonObjectUser = jsonArrayUser.getJSONObject(i);
			String cardNumb = jsonObjectUser.getString("cardNumb");
			cardlist += cardNumb + ",";
			String name = jsonObjectUser.getString("name");
			if(jsonObjectUser.getString("startTime") != null && !StringUtil.isEmpty(jsonObjectUser.getString("startTime"))){
				startTime = jsonObjectUser.getString("startTime")+":00";
			}
			
			if(jsonObjectUser.getString("endTime") != null && !StringUtil.isEmpty(jsonObjectUser.getString("endTime"))){
				endTime = jsonObjectUser.getString("endTime")+":00";	
			}
			
			
			for (int d = 0; d < jsonArray.size(); d++) {
				jsonObject = jsonArray.getJSONObject(d);
				String lockno = jsonObject.getString("lockno");
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
				
				String serialNumber = simpleDateFormat.format(new Date()) + lockno + String.valueOf(new Random().nextInt(9999-1000+1)+1000);
				
				jsonObject.put("serviceNumb", serialNumber);
				locklist += lockno + ";"; 
			}
			//先查询一下是否已有这段时间内的授权记录了，若有则不添加，去页面提示
			/*for (int d = 0; d < jsonArray.size(); d++) {
				jsonObject = jsonArray.getJSONObject(d);
				String locknod = jsonObject.getString("lockno");
				List<?> currentList = roomService.findBySQL("queryRightAlready", new String[]{startTime,endTime,cardNumb,locknod});
				if(currentList != null && currentList.size() > 0){
					
				}
			}			
			*/
			try {
				Integer result = CardUtil.cardDataAddMutilDoor(request, response,  jsonObjectUser.toString(),jsonArray.toString());
				if(result != CommonConstants.PortalResultCode.FAILED && !result.toString().equals(CommonConstants.doorInterfaceResult.FAILED)){
					dataToDoorFlag = "0";
					cardlistsuss += cardNumb +",";
					log.info("门锁内某个身份证信息添加到多个锁里指令发送成功！");
				} else {
					dataToDoorFlag = "1";
					cardlistfail += cardNumb +",";
					log.info("门锁内某个身份证信息添加到多个锁里指令发送失败！");
					SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
					WarningLog warningLog = new WarningLog();
					warningLog.setLogId(sdf.format(new Date()) + roomService.getSequence("SEQ_WARNINGLOG_ID"));
					warningLog.setRecordTime(new Date());
					warningLog.setRecordUser(SystemConstants.User.ADMIN_ID);
					warningLog.setWarningDate(new Date());
					warningLog.setStatus(CommonConstants.servieLogStatus.EFFECTIVE);
					warningLog.setWarningType(CommonConstants.warningLogStatus.ADD_CARDTOMUTILDOOR_FAILED);
					warningLog.setRemark("添加"+cardNumb+"到多个锁"+locklist+"中失败");
					warningLog.setSerialNo("Mutil");
					roomService.save(warningLog);
				}	 
			} catch (Exception e) {
				log.error("transfer data occurs error! " + e.getMessage());
				dataToDoorFlag = "1";
			}

			if(dataToDoorFlag.equals("0")){
				for (int g = 0; g < jsonArray.size(); g++) {
					jsonObjects = jsonArray.getJSONObject(g);
					String lockno = jsonObjects.getString("lockno");
					String gatewayCode = jsonObjects.getString("gatewayCode");
					String serviceNumb = jsonObjects.getString("serviceNumb");
					/*
					String roomId = jsonObject.getString("roomId");
					String branchIdNum = jsonObject.getString("branchIdNum");
					String branchIdName = jsonObject.getString("branchIdName");*/
					SimpleDateFormat simpleDateFormattwo = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					DoorOpenRight doorRight = new DoorOpenRight();
					doorRight.setCardNumb(cardNumb);
					doorRight.setDataId(serviceNumb);
					doorRight.setDnCode("");
					doorRight.setEndTime(simpleDateFormattwo.parse(endTime));
					doorRight.setGateWayCode(gatewayCode);
					doorRight.setLockCode(lockno);
					doorRight.setRecordTime(new Date());
					doorRight.setRecordUser(SystemConstants.User.ADMIN_ID);
					doorRight.setRemark("");
					doorRight.setStartTime(simpleDateFormattwo.parse(startTime));
					doorRight.setStatus(CommonConstants.servieLogStatus.EFFECTIVE);
					doorRight.setUserName(name);
					roomService.save(doorRight);
				}
			} else {
				failcount++;
			} 
		}
		
		//记操作日志
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String sequences = roomService.getSequence("SEQ_OPERATELOG_ID");
		String operid = IPUtil.getIpAddr(request);
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		String strdate = sdf.format(new Date());
		String logid = strdate + loginUser.getStaff().getBranchId() + sequences;
		OperateLog operatelog = new OperateLog();
		operatelog.setLogId(logid);
		operatelog.setRecordUser(SystemConstants.User.ADMIN_ID);
		operatelog.setRecordTime(new Date());
		operatelog.setBranchId(loginUser.getStaff().getBranchId());
		operatelog.setOperIp(operid);
		operatelog.setOperModule(SystemConstants.LogModule.DOOR);
		operatelog.setOperType(SystemConstants.LogModule.MUTILDOORCARDADD);	

		if(failcount == 0){
			operatelog.setContent("门锁内["+cardlistsuss.substring(0,cardlistsuss.length()-1)+"]身份证信息添加到多个锁["+locklist.substring(0,locklist.length()-1)+"]里指令发送成功！");
			roomService.save(operatelog);
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.SUCESS, null));
		} else {
			String message = "["+cardlist.substring(0,cardlist.length()-1)+"]身份证信息添加到多个锁["+locklist.substring(0,locklist.length()-1)+"]里["+cardlistfail.substring(0,cardlistfail.length()-1)+"]身份证添加指令发送失败！";
			operatelog.setContent(message);
			roomService.save(operatelog);
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, message));
		}
	}

	//授权详情
	@RequestMapping("turnToDoorRightRecord.do")
	public ModelAndView turnToDoorRightRecord(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String idcard = request.getParameter("idcard").trim();	
		
		ModelAndView mv = new ModelAndView();
		//查询所有用户固定
		List<?> DoorOpenRightList = roomService.findBySQL("queryDoorRightRecord", new String[]{idcard, idcard},true);
		
		if(DoorOpenRightList.size() > 0){
			mv.addObject("DoorOpenRightList", DoorOpenRightList);
		} else {
			mv.addObject("DoorOpenRightList", null);
		}

		mv.addObject("idcard", idcard);
		mv.setViewName("page/ems/emssystem/doorRightRecord");
		return mv;
	}
	
	//删除身份证授权，循环调用单个身份证授权删除接口
	@RequestMapping("deleteUserRightNew.do")
	public void deleteUserRightNew(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Integer count = 0;
		String dataToDoorFlag = "0";
		String suss = "";
		String fail = "";
		String idcard ="";
		String dataidArray = request.getParameter("dataidArray");
		net.sf.json.JSONArray jsonArray = net.sf.json.JSONArray.fromObject(dataidArray);
		for(int i=0; i < jsonArray.size(); i++){
			String dataid = jsonArray.get(i).toString();
			DoorOpenRight doorright = (DoorOpenRight)roomService.findOneByProperties(DoorOpenRight.class, "status", "1", "dataId", dataid);
			if(doorright != null){
				try {
					idcard = doorright.getCardNumb();
					Integer result = CardUtil.oneDoorDelCardData(request, response, doorright.getLockCode(), doorright.getCardNumb(), doorright.getDataId());
					if(result != CommonConstants.PortalResultCode.FAILED && !result.toString().equals(CommonConstants.doorInterfaceResult.FAILED)){
						dataToDoorFlag = "0";
						suss += doorright.getLockCode();
						log.info("门锁内某个身份证信息删除指令发送成功！");
					} else {
						dataToDoorFlag = "1";
						fail += doorright.getLockCode();
						count++;
						log.info("门锁内某个身份证信息删除指令发送失败！");
						//删除某个身份证失败记日志
						SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
						WarningLog warningLog = new WarningLog();
						warningLog.setLogId(sdf.format(new Date()) + roomService.getSequence("SEQ_WARNINGLOG_ID"));
						warningLog.setRecordTime(new Date());
						warningLog.setRecordUser(SystemConstants.User.ADMIN_ID);
						warningLog.setWarningDate(new Date());
						warningLog.setStatus(CommonConstants.servieLogStatus.EFFECTIVE);
						warningLog.setWarningType(CommonConstants.warningLogStatus.DEL_CARD_FAILED);
						warningLog.setRemark("删除身份证["+doorright.getCardNumb()+"]失败,流水号["+doorright.getDataId()+"]");
						warningLog.setSerialNo(doorright.getLockCode());
						roomService.save(warningLog);
					}	 
				} catch (Exception e) {
					log.error("transfer data occurs error! " + e.getMessage());
					dataToDoorFlag = "1";
					fail += doorright.getLockCode();
					count++;
				}
				
				if(dataToDoorFlag.equals("0")){
					doorright.setStatus("0");
					doorright.setRecordTime(new Date());
					roomService.merge(doorright);
				}
			} 
			
		}

		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);

		//记操作日志
		String sequences = roomService.getSequence("SEQ_OPERATELOG_ID");
		String operid = IPUtil.getIpAddr(request);
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		String strdate = sdf.format(new Date());
		String logid = strdate + loginUser.getStaff().getBranchId() + sequences;
		OperateLog operatelog = new OperateLog();
		operatelog.setLogId(logid);
		operatelog.setRecordUser(SystemConstants.User.ADMIN_ID);
		operatelog.setRecordTime(new Date());
		operatelog.setBranchId(loginUser.getStaff().getBranchId());
		operatelog.setOperIp(operid);
		operatelog.setOperModule(SystemConstants.LogModule.DOOR);
		operatelog.setOperType(SystemConstants.LogModule.DOORCARDDEL);	
		if(count == 0){
			operatelog.setContent("["+suss.substring(0, suss.length()-1)+"]门锁内某个身份证["+idcard+"]信息删除成功！");
			roomService.save(operatelog);
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.SUCESS, null));
		} else {
			String failmessage = "["+fail.substring(0, fail.length()-1)+"]门锁内某个身份证["+idcard+"]信息删除失败！";
			operatelog.setContent(failmessage);
			roomService.save(operatelog);
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, failmessage));
		}
	}

	//批量获得动态密码
	@RequestMapping("mutilUserGetPwd.do")
	public void mutilUserGetPwd(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String dataToDoorFlag = "0";
		String doorData = request.getParameter("doorData");	
		String username = request.getParameter("newarray");
		Map<String,String> map = new HashMap<String,String>();
		String locklist = "";
		String passwordlist = "";
		String name = "";
		String message = "";
		
		net.sf.json.JSONArray jsonArray = net.sf.json.JSONArray.fromObject(doorData);
		net.sf.json.JSONObject jsonObject = null;
		net.sf.json.JSONObject jsonObjects = null;
		
		try {
			
			JsonArray jsonArrayList = new JsonArray();
			
			for (int k = 0; k < jsonArray.size(); k++) {
				jsonObject = jsonArray.getJSONObject(k);
				String lockno = jsonObject.getString("lockno");
				locklist += lockno + ",";
				String gatewayCode = jsonObject.getString("gatewayCode");
				JsonObject lockInfo = new JsonObject();
		    	lockInfo.addProperty("lockCode", lockno);
		    	lockInfo.addProperty("gatewayCode", gatewayCode);
		    	jsonArrayList.add(lockInfo);
				
			}
	    	
	    	JsonObject jsonObjectgo = new JsonObject();
	    	jsonObjectgo.addProperty("sign", 42);
	    	jsonObjectgo.addProperty("ownerId",CommonConstants.doorInterfaceParam.OWNERID);
	    	jsonObjectgo.addProperty("operatorId", CommonConstants.doorInterfaceParam.OPERATORID);
	    	jsonObjectgo.add("lockList", jsonArrayList);
		
			String res = HttpUtil.postData(jsonObjectgo.toString(), CommonConstants.ZyDoorIp.IP);
			//System.out.println("获得结果："+res);
			
			JSONUtil.buildParamFromJSON(map,res);
			Integer result = Integer.parseInt(map.get("result").toString());
			
			if(result != null && result != CommonConstants.PortalResultCode.FAILED && !result.toString().equals(CommonConstants.doorInterfaceResult.FAILED)){
				dataToDoorFlag = "0";
				log.info("多个门锁获得动态密码成功！");
			} else {
				dataToDoorFlag = "1";
				log.info("多个门锁获得动态密码失败！");
				SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
				WarningLog warningLog = new WarningLog();
				warningLog.setLogId(sdf.format(new Date()) + roomService.getSequence("SEQ_WARNINGLOG_ID"));
				warningLog.setRecordTime(new Date());
				warningLog.setRecordUser(SystemConstants.User.ADMIN_ID);
				warningLog.setWarningDate(new Date());
				warningLog.setStatus(CommonConstants.servieLogStatus.EFFECTIVE);
				warningLog.setWarningType(CommonConstants.warningLogStatus.MUTIL_DOOR_PWD_FAILED);
				warningLog.setRemark("多个门锁"+locklist+"获得动态密码失败");
				warningLog.setSerialNo("Mutil");
				roomService.save(warningLog);
			}	 
		} catch (Exception e) {
			log.error("transfer data occurs error! " + e.getMessage());
			dataToDoorFlag = "1";
		}
		
		//记操作日志
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String sequences = roomService.getSequence("SEQ_OPERATELOG_ID");
		String operid = IPUtil.getIpAddr(request);
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		String strdate = sdf.format(new Date());
		String logid = strdate + loginUser.getStaff().getBranchId() + sequences;
		OperateLog operatelog = new OperateLog();
		operatelog.setLogId(logid);
		operatelog.setRecordUser(SystemConstants.User.ADMIN_ID);
		operatelog.setRecordTime(new Date());
		operatelog.setBranchId(loginUser.getStaff().getBranchId());
		operatelog.setOperIp(operid);
		operatelog.setOperModule(SystemConstants.LogModule.DOOR);
		operatelog.setOperType(SystemConstants.LogModule.MUTILDOORCARDADD);	
		dataToDoorFlag = "0";
		if(dataToDoorFlag.equals("0")){
			//解析动态多锁密码
			String passwordInfo = map.get("passwordInfo").toString();
			net.sf.json.JSONArray passwordInfolist = net.sf.json.JSONArray.fromObject(passwordInfo);
			net.sf.json.JSONObject passwordInfoObject = null;
			
			SysParam param = (SysParam) roomService.findOneByProperties(SysParam.class, "paramType","APPID");
			int appid = Integer.parseInt(param.getContent());
			param = (SysParam) roomService.findOneByProperties(SysParam.class, "paramType","APPKEY");
			String appkey = param.getContent();
			SmsSingleSender singleSender = new SmsSingleSender(appid, appkey);
			ArrayList<String> params = new ArrayList<String>();
			
			
			for (int d = 0; d < passwordInfolist.size(); d++) {
				passwordInfoObject = passwordInfolist.getJSONObject(d);
				String password = passwordInfoObject.getString("password");
				passwordlist += password +",";
				String lockCode = passwordInfoObject.getString("lockCode");
				//查询锁的门店，房间号。地址
				SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
				Equipment e = (Equipment) roomService.findOneByProperties(Equipment.class, "serialNo", lockCode);
				if(e != null){
					House h = (House) roomService.findOneByProperties(House.class, "houseId", e.getBranchId());
					String housename = h.getHousename();
					String address = h.getAddress();
					String opendate = simple.format(new Date());
					params.add(housename);
					params.add(address);
					params.add(password);
					params.add(opendate);	
				}
			}
			
			/*SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
			Equipment e = (Equipment) roomService.findOneByProperties(Equipment.class, "serialNo", "LCN0011805000242");
			if(e != null){
				Order o = (Order) roomService.findOneByProperties(Order.class, "orderId","180723H1003121185");
				Member m = (Member) roomService.findOneByProperties(Member.class, "memberId",o.getOrderUser());
				House h = (House) roomService.findOneByProperties(House.class, "houseId", o.getBranchId());
				
				sendMessageContainKey(o.getOrderId(),"1",m.getMemberName(),simple.format(o.getArrivalTime()),simple.format(o.getLeaveTime()),h.getAddress(),"232321",o.getmPhone());
				
			}*/
			//发短信给保洁
			net.sf.json.JSONArray jsonArrayUser = net.sf.json.JSONArray.fromObject(username);
			net.sf.json.JSONObject jsonObjectUser = null;
			Integer smsNo = null;
			int remainder5 = (int) (Math.ceil((double)params.size()/(double)4)%5);
			int merchant5 = (int) (Math.ceil((double)params.size()/(double)4)/5);
			ArrayList<String> realparams = new ArrayList<String>();
			
			if(merchant5 > 0){
				for(int k = 0; k < merchant5; k++){
					smsNo = 161100;
					for (int i = 0; i < jsonArrayUser.size(); i++) {
						jsonObjectUser = jsonArrayUser.getJSONObject(i);
						String phone = jsonObjectUser.getString("phone");
						//String useraname = jsonObjectUser.getString("name");
						name += name + ",";
						realparams = (ArrayList<String>) params.subList(4*5*k, (k+1)*4*5);
						singleSender.sendWithParam("86", phone, smsNo, realparams,"","","");
					}
				}
			}
			
			
				if(remainder5 == 1){
					smsNo = 161100;
					for (int i = 0; i < jsonArrayUser.size(); i++) {
						jsonObjectUser = jsonArrayUser.getJSONObject(i);
						String phone = jsonObjectUser.getString("phone");
						//String useraname = jsonObjectUser.getString("name");
						name += name + ",";
						realparams = new ArrayList<String>(params.subList(params.size()-4, params.size()));
						singleSender.sendWithParam("86", phone, smsNo, realparams,"","","");
					}
				} else if(remainder5 == 2){
					smsNo = 161103;
					for (int i = 0; i < jsonArrayUser.size(); i++) {
						jsonObjectUser = jsonArrayUser.getJSONObject(i);
						String phone = jsonObjectUser.getString("phone");
						//String useraname = jsonObjectUser.getString("name");
						name += name + ",";
						realparams = (ArrayList<String>) params.subList(params.size()-8, params.size());
						singleSender.sendWithParam("86", phone, smsNo, realparams,"","","");
					}
				} else if(remainder5 == 3){
					smsNo = 161106;
					for (int i = 0; i < jsonArrayUser.size(); i++) {
						jsonObjectUser = jsonArrayUser.getJSONObject(i);
						String phone = jsonObjectUser.getString("phone");
						//String useraname = jsonObjectUser.getString("name");
						name += name + ",";
						realparams = (ArrayList<String>) params.subList(params.size()-12, params.size());
						singleSender.sendWithParam("86", phone, smsNo, realparams,"","","");
					}
				} else if(remainder5 == 4){
					smsNo = 161111;
					for (int i = 0; i < jsonArrayUser.size(); i++) {
						jsonObjectUser = jsonArrayUser.getJSONObject(i);
						String phone = jsonObjectUser.getString("phone");
						//String useraname = jsonObjectUser.getString("name");
						name += name + ",";
						realparams = (ArrayList<String>) params.subList(params.size()-16, params.size());
						singleSender.sendWithParam("86", phone, smsNo, realparams,"","","");
					}
				} 
			
			message = "多个门锁["+locklist.substring(0,locklist.length()-1)+"]获得动态密码["+passwordlist.substring(0,passwordlist.length()-1)+"]成功！";
			operatelog.setContent(message);
			roomService.save(operatelog);
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.SUCESS, message));
		} else {
			message = "多个门锁["+locklist.substring(0,locklist.length()-1)+"]获得动态密码失败！";
			operatelog.setContent(message);
			roomService.save(operatelog);
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, message));
		} 
		
	}
	
	
	/**
	 * 发送订单消息给客户20180607含门锁消息
	 * @param allOrderId
	 * @param roomAmount
	 * @param memberName
	 * @param arriveTime
	 * @param endTime
	 * @param address
	 * @param password
	 * @param memberPhone
	 * @throws Exception
	 */
	public void sendMessageContainKey(String allOrderId,String roomAmount,String memberName,String arriveTime,String endTime,String address,String password,String memberPhone) throws Exception {  
		Branch branch = (Branch) roomService.findOneByProperties(Branch.class, "branchId", "100001");
		if(branch.getPhone()!=null){
			String servicePhone=branch.getPhone();
			SysParam param = (SysParam) roomService.findOneByProperties(SysParam.class, "paramType","APPID");
			int appid = Integer.parseInt(param.getContent());
			param = (SysParam) roomService.findOneByProperties(SysParam.class, "paramType","APPKEY");
			String appkey = param.getContent();
			SmsSingleSender singleSender = new SmsSingleSender(appid, appkey);
			ArrayList<String> params = new ArrayList<String>();
			params.add(allOrderId);
			params.add(roomAmount);
			params.add(memberName);
			params.add(arriveTime);
			params.add(endTime);
			params.add(address);
			params.add(password);
			params.add(memberPhone);
			singleSender.sendWithParam("86", memberPhone, 122651, params,"","","");
		}
	}
	//添加固定用户页面
	
	@RequestMapping("turnToAddFreUser.do")
	public ModelAndView turnToAddFreUser(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		String lockno = request.getParameter("lockno");	
		String gatewayCode = request.getParameter("gatewayCode");
		String roomId = request.getParameter("roomId");
		String branchIdNum = request.getParameter("branchIdNum");
		String branchIdName = request.getParameter("branchIdName");
		List<City> cityList = houseBasicService.queryCity();
		List<Map<String, Object>> newCity = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < cityList.size(); i++) {
			Map<String, Object> el = new HashMap<String, Object>();
			String pinYin = getAlphas(cityList.get(i).getAdminiName());
			el.put("py", pinYin);
			el.put("name", cityList.get(i).getAdminiName());
			el.put("code", cityList.get(i).getAdminiCode());
			newCity.add(el);
		}
		
		String sql = "queryPlace";
		String cityAdminCode = cityList.get(0).getAdminiCode();
		String citySubCode = cityAdminCode.substring(0, 4) + "%";
		List<City> districtList = houseBasicService.findBySQL(sql, new String[] { citySubCode, CommonConstants.CityRank.DISTRICT },
				true);
		
		
		mv.addObject("newCity", JSONUtil.fromObject(newCity));
		mv.addObject("districtList", districtList);
		mv.addObject("serialNo", lockno);
		mv.addObject("gatewayCode", gatewayCode);
		mv.addObject("roomId", roomId);
		mv.addObject("branchIdNum", branchIdNum);
		mv.addObject("branchIdName", branchIdName);
		mv.setViewName("page/ems/emssystem/addUser");
		return mv;
	}
	
	/* 获得汉语拼音首字母
    *
    * @param chines 汉字
    * @return
    */
   public static String getAlphas(String chines) {
       String pinyinName = "";
       char[] nameChar = chines.toCharArray();
       HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
       defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
       defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
       for (int i = 0; i < nameChar.length; i++) {
           if (nameChar[i] > 128) {
               try {
                   pinyinName += PinyinHelper.toHanyuPinyinStringArray(
                           nameChar[i], defaultFormat)[0].charAt(0);
               } catch (BadHanyuPinyinOutputFormatCombination e) {
                   e.printStackTrace();
               }
           } else {
               pinyinName += nameChar[i];
           }
       }
       return pinyinName;
   }
	
	//新增常雇人员的保存方法
	@RequestMapping("/addUserFrequetter.do")
	public void addUserFrequetter(HttpServletRequest request, HttpServletResponse response, String userName, String idcard,
			String gender, String mobile, String post, String city, String district, String address, String remark) throws Exception {
		String message = null;
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Frequenter fe = (Frequenter)roomService.findOneByProperties(Frequenter.class, "idcard", idcard, "status", "1");
		if(fe == null){
			fe = (Frequenter)roomService.findOneByProperties(Frequenter.class, "mobile", mobile, "status", "1");
		}
		if(fe == null){
			Frequenter f = new Frequenter();
			String userId = roomService.getSequence("SEQ_FQUENTER_ID");
			f.setUserId(userId);
			f.setUserName(userName);
			f.setGender(gender);
			f.setCity(city);
			f.setDistrict(district);
			f.setAddress(address);
			f.setGender(gender);
			f.setIdcard(idcard);
			f.setMobile(mobile);
			f.setPost(post);
			f.setRemark(remark);
			f.setRecordUser(loginuser.getStaff().getStaffId());
			f.setRecordTime(new Date());
			f.setStatus("1");
			roomService.save(f);
		} else {
			message = "该人员手机号或身份证已存在!";
		}
		JSONUtil.responseJSON(response, new AjaxResult(1, message));
	}
}
