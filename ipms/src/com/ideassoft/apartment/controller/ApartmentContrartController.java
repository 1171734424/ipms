package com.ideassoft.apartment.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hsqldb.lib.StringUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;







import com.ideassoft.apartment.service.ApartmentContrartService;
import com.ideassoft.apartment.service.ApartmentLeftmenuService;
import com.ideassoft.apartment.service.ApartmentLogServiceInPms;
import com.ideassoft.bean.Aptorder;
import com.ideassoft.bean.AptorderDetail;
import com.ideassoft.bean.Bill;
import com.ideassoft.bean.CheckUser;
import com.ideassoft.bean.Contrart;
import com.ideassoft.bean.Equipment;
import com.ideassoft.bean.Member;
import com.ideassoft.bean.OperateLog;
import com.ideassoft.bean.RepairApply;
import com.ideassoft.bean.Room;
import com.ideassoft.bean.SysParam;
import com.ideassoft.bean.WarningLog;
import com.ideassoft.core.ajax.AjaxResult;
import com.ideassoft.core.annotation.interfaces.RightMethodControl;
import com.ideassoft.core.annotation.interfaces.RightModelControl;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.constants.CommonConstants;
import com.ideassoft.core.constants.RightConstants;
import com.ideassoft.core.constants.SystemConstants;
import com.ideassoft.portal.DataDealPortalService;
import com.ideassoft.portal.IDataDealPortal;
import com.ideassoft.util.DateUtil;
import com.ideassoft.util.JSONUtil;
import com.ideassoft.util.RemoteTransUtil;
import com.ideassoft.util.RequestUtil;

@Transactional
@Controller
@RightModelControl(rightModel = RightConstants.RightModel.APARTMENT_CONTROL)
public class ApartmentContrartController {
	@Autowired
	private ApartmentLeftmenuService leftmenuService;
	@Autowired
	private ApartmentContrartService contrartService;
	@Autowired
	private ApartmentLogServiceInPms logServiceInPms;
	
	private static String CONNECTIONFAIL;
	
	private static String CONNECTION;
	
	private static String VALID;
	
	private static int NULL;
	
	private static String adminName;
	@SuppressWarnings("unchecked")
	@RequestMapping("/saveContrart.do")
	public void userUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception{
		FileOutputStream fos = null;
		InputStream is = null;
		String fileName_timestamp = "";
		Contrart contrart = new Contrart();
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchid = loginuser.getStaff().getBranchId();
		String staffid = loginuser.getStaff().getStaffId();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		SimpleDateFormat sdf4 = new SimpleDateFormat("yyyy/MM/dd 12:00:00");
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		String orderid  = multipartRequest.getParameter("orderid");
	    String memberid  = multipartRequest.getParameter("member_id_CUSTOM_VALUE");
		String roomid = multipartRequest.getParameter("room_id");
		String roomtype = multipartRequest.getParameter("room_type");
		String mobile = multipartRequest.getParameter("mobile");
		String typeofpayment = multipartRequest.getParameter("typeofpayment");
		String unitprice = multipartRequest.getParameter("unitprice");
		String oldprice = multipartRequest.getParameter("oldprice");
		String startTimeTemp = multipartRequest.getParameter("startTimeTemp");
		String contrartid = multipartRequest.getParameter("contrartid");
		String cash = multipartRequest.getParameter("cash");
		String totalprice = multipartRequest.getParameter("totalprice");
		String autoRefund = multipartRequest.getParameter("autorefund");
	//	String paymentBill = multipartRequest.getParameter("payment");

		Date startdate = sdf.parse(startTimeTemp);
		String starttime = sdf.format(startdate);
		Date date = new Date();
		String endTimeTemp = multipartRequest.getParameter("endTimeTemp");
		Date enddate = sdf.parse(endTimeTemp);
		String endtime = sdf.format(enddate);
		String contrart_end_time = multipartRequest.getParameter("contrart_end_time");
        MultipartFile srcFile = multipartRequest.getFile("contrartFile");
		
      //卡已有无人预约看房
        List<?> reservedJudge = contrartService.findBySQL("reservedJudge",new String[]{branchid,roomid,
				starttime,endtime}, true);
      //卡停售房
  		List<?> haltplanList = contrartService.findBySQL("checkhaltplan", new String[]{roomid,branchid,startTimeTemp,endTimeTemp,startTimeTemp,endTimeTemp},true);
  	  
  		boolean flag = ("".equals(orderid)) ? false : true;
  		if (!flag) {
	  		//卡已存在的合同或者app订单
	  	  	List<?> aptorderJudge = contrartService.findBySQL("aptorderJudge",new String[]{branchid,roomid,
	  						starttime,endtime,starttime,endtime}, true);
	  	  	if (aptorderJudge.size() == 0) {
				flag = true;
			}
	  	  	
		}
  		
  		List<Contrart> contrartList = contrartService.findBySQL("checkroombeforeadd",new String[]{branchid,roomid,
					starttime,endtime,starttime,endtime}, true);
  		
  		if(haltplanList.size() == 0){
  		  if(reservedJudge.size() == 0 && flag){
  			if(StringUtil.isEmpty(contrartid)){
  				 String originalFilename=srcFile.getOriginalFilename();
  	  		    if(!"".equals(originalFilename)){
  	  		    	fileName_timestamp = new Date().getTime() + originalFilename.substring(originalFilename.lastIndexOf("."));
  	  		    	try{
  	  					String webPath = RequestUtil.getWebPath(request);
  	  					File srcFolder = new File(webPath + File.separator+"upload");
  	  					if (!srcFolder.exists()) {
  	  						srcFolder.mkdirs();
  	  					}
  	  					File tarFile = new File(srcFolder.getAbsolutePath() + File.separator+ fileName_timestamp);
  	  					if(!tarFile.exists()){
  	  						tarFile.createNewFile();
  	  					}
  	  					fos = new FileOutputStream(tarFile);
  	  					is = srcFile.getInputStream();
  	  					if(is.available() <= 0){
  	  						JSONUtil.responseJSON(response, new AjaxResult(1,"不可上传空的合同附件!"));
  	  						return;
  	  					}
  	  					int ln = 0;
  	  					byte[] buf = new byte[1024];
  	  					while ((ln = is.read(buf)) != -1) {
  	  						fos.write(buf, 0, ln);
  	  					}
  	  					fos.flush();
  	  				}catch(IOException e){
  	  					e.printStackTrace();
  	  					 JSONUtil.responseJSON(response, new AjaxResult(2, "文件传输失败!"));
  	  				}finally{
  	  					if (fos != null) {
  	  						fos.close();
  	  					}
  	  					if (is != null) {
  	  						is.close();
  	  					}
  	  				}
  	  		    }
  				
  				
  				    if( contrartList.size() != 0 ){
  				    	JSONUtil.responseJSON(response, new AjaxResult(1,"房间合同已存在!"));
  				    }else{
  						
  						String sequences = contrartService.getCloudSequence("SEQ_CONTRART_ID");//获取云端序列，就不用更改字段
  						SimpleDateFormat sdf2 = new SimpleDateFormat("yyMMdd");
  						String strdate = sdf2.format(date);
   						contrart.setContrartId(strdate + branchid + sequences);
  						 if(!"".equals(originalFilename)){
  							 contrart.setContrart(fileName_timestamp);				    	
  						    }
  			    		contrart.setMobile(mobile);
  			    		contrart.setBranchId(branchid);
  			    		contrart.setContrartEndTime(sdf4.parse(contrart_end_time+" 12:00:00"));
  			    		contrart.setMemberId(memberid);
  			    		contrart.setRoomId(roomid);
  			    		contrart.setRoomType(roomtype);
  			    		contrart.setStartTime(sdf4.parse(starttime+" 12:00:00"));
  			    		contrart.setEndTime(sdf4.parse(endtime+" 12:00:00"));
  			    		contrart.setStatus("1");
  			    		contrart.setTypeOfPayment(typeofpayment);
  			    		contrart.setUnitPrice(unitprice);
  			    		contrart.setRecordTime(date);
  			    		contrart.setAutoRefund(autoRefund);
  			    		//contrart.setPayment(paymentBill);
  			    		contrart.setPayment("1");
  			    		if(!("".equals(cash))){
  			    			contrart.setCash(Double.parseDouble(cash));
  			    		}
  			    		contrart.setPaymoney(Double.parseDouble(totalprice));
  			    		
  			    		
  			    		String projectName1 = "";
  			    		String projectName2 = "";
  			    		SysParam project1 = (SysParam)contrartService.findOneByProperties(SysParam.class, "paramType","PROJECT","content",CommonConstants.BillProject.RoomPrice);
  			    		SysParam project2 = (SysParam)contrartService.findOneByProperties(SysParam.class, "paramType","PROJECT","content",CommonConstants.BillProject.ForeGift);
  			    		if(project1 != null && project1 != null){
  			    			projectName1 = project1.getParamName();
  			    			projectName2 = project2.getParamName();
  			    		}
  			    		Bill billprice = new Bill();
  			    		String billSequences = contrartService.getSequence("SEQ_NEW_BILL");
  			    		billprice.setBillId(strdate +  branchid + CommonConstants.OrderSource.Branch +billSequences);
  			    		billprice.setCheckId(strdate + branchid + sequences);
  			    		billprice.setBranchId(branchid);
  			    		billprice.setProjectId(CommonConstants.BillProject.Rent);
  			    		billprice.setProjectName(projectName1);
  			    		billprice.setDescribe("交租");
  			    		billprice.setCost(0.0);
  			    		billprice.setPay(Double.parseDouble(totalprice));
  			    		billprice.setPayment("1");
  			    		billprice.setStatus(CommonConstants.BillStatus.BillNormal);
  			    		billprice.setRecordTime(new Date());
  			    		billprice.setRecordUser(staffid);
  			    		billprice.setRemark("合同表入账");
  			    		
  			    		contrartService.save(billprice);
  			    		
  			    		Bill billcash = new Bill();
  			    		String billSequence = contrartService.getSequence("SEQ_NEW_BILL");
  			    		billcash.setBillId(strdate +  branchid + CommonConstants.OrderSource.Branch + billSequence);
  			    		billcash.setCheckId(strdate + branchid + sequences);
  			    		billcash.setBranchId(branchid);
  			    		billcash.setProjectId(CommonConstants.BillProject.ForeGift);
  			    		billcash.setProjectName(projectName2);
  			    		billcash.setDescribe("押金");
  			    		billcash.setCost(0.0);
  			    		billcash.setPay(Double.parseDouble(cash));
  			    		billcash.setPayment("1");
  			    		billcash.setStatus(CommonConstants.BillStatus.BillNormal);
  			    		billcash.setRecordTime(new Date());
  			    		billcash.setRecordUser(staffid);
  			    		billcash.setRemark("合同表入账");
  			    		
  			    		contrartService.save(billcash);
  			    	
  			    		
  			    		
  			    		Room roomdata = (Room) contrartService.findOneByProperties(Room.class, "roomKey.roomId", roomid,
  			 			           "roomKey.branchId",branchid);
  			    		List<RepairApply> repairApplys = contrartService.findByProperties(RepairApply.class, "branchId", contrart.getBranchId(), "roomId", contrart.getRoomId());
							String status = "3";
							if(repairApplys.size() > 0){
								for(RepairApply repairApply : repairApplys){
									if(repairApply.getStatus().equals("2") || repairApply.getStatus().equals("3")){
										status = "W";
									}
								}
							}
  			    		Date cc = sdf.parse(sdf.format(date));
  			    		int dd = startdate.compareTo(cc);
  			    		if( dd == 0 && (orderid == null || ("".equals(orderid)))){
  							
  				        	    roomdata.setStatus(status);
  				 	    		roomdata.setRecordTime(sdf.parse(sdf.format(new Date())));
  				 	    		contrart.setStatus("4");
  				 	    		contrartService.update(roomdata);
  				 	    		Member member = (Member) contrartService.findById(Member.class, memberid);
  				 	    		CheckUser ckuser = new CheckUser();
  				 	    		ckuser.setCheckuserId(branchid + "1" + contrartService.getSequence("SEQ_NEW_CHECKUSER"));
  				 	    		ckuser.setCheckuserName(member.getMemberName());
  				 	    		ckuser.setIdcard(member.getIdcard());
  				 	    		ckuser.setGender(member.getGendor());
  				 	    		ckuser.setMobile(member.getMobile());
  				 	    		ckuser.setStatus("1");
  				 	    		ckuser.setRecordUser(staffid);
  				 	    		ckuser.setRecordTime(new Date());
  				 	    		ckuser.setCheckId(strdate + branchid + sequences);
  				 	    		ckuser.setCheckuserType("1");
  				 	    		ckuser.setCheckinType("2");
								contrartService.save(ckuser);
								
								// 当orderid订单号为空的时候，添加aptOrder表字段及 aptOrderDetail表
								String orderId = sdf2.format(new Date()) + branchid + "3" + contrartService.getSequence("SEQ_NEW_APTORDER");
								Aptorder aptOrder = new Aptorder();
								aptOrder.setOrderId(orderId);
								aptOrder.setBranchId(branchid);
								aptOrder.setSource("3");
								aptOrder.setRoomId(roomid);
								aptOrder.setRoomType(roomtype);
								aptOrder.setOrderUser(memberid);
								aptOrder.setmPhone(mobile);
								aptOrder.setArrivalTime(sdf4.parse(startTimeTemp+" 12:00:00"));
								aptOrder.setLeaveTime(sdf4.parse(endTimeTemp+" 12:00:00"));
								aptOrder.setContrartEndTime(sdf4.parse(contrart_end_time+" 12:00:00"));
								aptOrder.setRoomPrice(Double.parseDouble(totalprice));
								aptOrder.setAdvanceCash(Double.parseDouble(cash));//押金？
								aptOrder.setUnitPrice(unitprice);
								aptOrder.setOrderTime(new Date());
								aptOrder.setRecordTime(new Date());
								aptOrder.setStatus("3");
								contrartService.save(aptOrder);
								
								CheckUser ckuser2 = new CheckUser();
  				 	    		ckuser2.setCheckuserId(branchid + "1" + contrartService.getSequence("SEQ_NEW_CHECKUSER"));
  				 	    		ckuser2.setCheckuserName(member.getMemberName());
  				 	    		ckuser2.setIdcard(member.getIdcard());
  				 	    		ckuser2.setGender(member.getGendor());
  				 	    		ckuser2.setMobile(member.getMobile());
  				 	    		ckuser2.setStatus("1");
  				 	    		ckuser2.setRecordUser(staffid);
  				 	    		ckuser2.setRecordTime(new Date());
  				 	    		ckuser2.setCheckId(orderId);
  				 	    		ckuser2.setCheckuserType("1");
  				 	    		ckuser2.setCheckinType("1");
								contrartService.save(ckuser);
								
								
								//记关联表aptOrderDetail
	  			    			AptorderDetail aptod = new AptorderDetail();
	  			    			String aptorderSeq = contrartService.getCloudSequence("SEQ_APTORDERDETAIL_ID");
	  			    			aptod.setAptorderId(orderId);
	  			    			aptod.setContrartId(strdate + branchid + sequences);
	  			    			aptod.setDetailId(strdate + branchid + aptorderSeq);
	  			    			aptod.setRecordTime(new Date());
	  			    			aptod.setRecordUser(staffid);
	  			    			aptod.setStatus("1");
	  			    			contrartService.save(aptod);
	  			    			
	  			    			// 添加操作日志
	  			    			OperateLog operlog = new OperateLog();
  	  							operlog.setBranchId(contrart.getBranchId());
  	  							operlog.setLogId(DateUtil.currentDate("yyMMdd") + contrart.getBranchId() + contrartService.getSequence("SEQ_OPERATELOG_ID"));
  	  							String operid = InetAddress.getLocalHost().toString();// IP地址
  	  							operid = (String) operid.subSequence(operid.indexOf("/") + 1, operid.length());
  	  							operlog.setOperIp(operid);
  	  							operlog.setOperType("5");
  	  							operlog.setOperModule("入住操作");
  	  							if(oldprice.equals(unitprice)){
  	  								operlog.setContent("合同号" + contrart.getContrartId() + "已经入住");
  	  							}else{
  	  								operlog.setContent("合同号" + contrart.getContrartId() + "已经入住!单价调整:由"+oldprice+"调整至"+unitprice+".");

  	  							}
  	  							operlog.setRecordUser(staffid);
  	  							operlog.setRecordTime(new Date());
  	  						    contrartService.save(operlog);
  	  						    
  	  						    // 当客户入住公寓时，记录该合同下公寓房间的水电用量
  	  						    DataDealPortalService service = new DataDealPortalService();
								IDataDealPortal portal = service.getDataDealPortalPort();
								String code = portal.call(SystemConstants.EnginType.COMMON_DATA,
										SystemConstants.Movement.CUSTOM_QUERY,
										"{ function: \"apartmentList.checkInHydropower\", "
												+ "data:{branchId:" + contrart.getBranchId()
												+ ",roomId:" + contrart.getRoomId() + ",orderId:"
												+ contrart.getContrartId() + "  } }");

								if ("-1".equals(code)) {
									Equipment equipment = (Equipment) contrartService.findOneByProperties(Equipment.class, "branchId", branchid, "roomId", roomid);
									if (equipment != null) {
										WarningLog warningLog = new WarningLog();
										warningLog.setLogId(DateUtil.currentDate("yyMMdd")
												+ contrart.getBranchId()
												+ contrartService.getSequence("SEQ_WARNINGLOG_ID"));
										warningLog.setBranchId(contrart.getBranchId());
										warningLog.setRoomId(contrart.getRoomId());
										warningLog.setSerialNo(equipment.getSerialNo());
										warningLog.setWarningType("3");
										warningLog.setWarningDate(new Date());
										warningLog.setStatus("1");
										warningLog.setRecordTime(new Date());
										warningLog.setRecordUser(staffid);
										warningLog.setRemark("合同号" + contrart.getContrartId() + DateUtil.d2s(new Date()) + "入住时连接水电后台失败!");
										contrartService.save(warningLog);
									}
								}
  				 	    		
  			    		} 
  			    		//如果入住时间还没到，先存一份预定状态的入住人(checkintype = 1)
  			    		if( dd == 1 && (orderid == null || ("".equals(orderid)))){
  			    			Member member = (Member) contrartService.findById(Member.class, memberid);
				 	    		CheckUser ckuser = new CheckUser();
				 	    		ckuser.setCheckuserId(branchid + "1" + contrartService.getSequence("SEQ_NEW_CHECKUSER"));
				 	    		ckuser.setCheckuserName(member.getMemberName());
				 	    		ckuser.setIdcard(member.getIdcard());
				 	    		ckuser.setGender(member.getGendor());
				 	    		ckuser.setMobile(member.getMobile());
				 	    		ckuser.setStatus("1");
				 	    		ckuser.setRecordUser(staffid);
				 	    		ckuser.setRecordTime(new Date());
				 	    		ckuser.setCheckId(strdate + branchid + sequences);
				 	    		ckuser.setCheckuserType("1");
				 	    		ckuser.setCheckinType("1");
								contrartService.save(ckuser);
								
								// 当orderid订单号为空的时候，添加aptOrder表字段及 aptOrderDetail表
								String orderId = sdf2.format(new Date()) + branchid + "3" + contrartService.getSequence("SEQ_NEW_APTORDER");
								Aptorder aptOrder = new Aptorder();
								aptOrder.setOrderId(orderId);
								aptOrder.setBranchId(branchid);
								aptOrder.setSource("3");
								aptOrder.setRoomId(roomid);
								aptOrder.setRoomType(roomtype);
								aptOrder.setOrderUser(memberid);
								aptOrder.setmPhone(mobile);
								aptOrder.setArrivalTime(sdf4.parse(startTimeTemp+" 12:00:00"));
								aptOrder.setLeaveTime(sdf4.parse(endTimeTemp+" 12:00:00"));
								aptOrder.setContrartEndTime(sdf4.parse(contrart_end_time+" 12:00:00"));
								aptOrder.setRoomPrice(Double.parseDouble(totalprice));
								aptOrder.setAdvanceCash(Double.parseDouble(cash));//押金？
								aptOrder.setUnitPrice(unitprice);
								aptOrder.setOrderTime(new Date());
								aptOrder.setRecordTime(new Date());
								/*aptOrder.setReFundTime(sdf.parse(refundTime));*/
								aptOrder.setStatus("6");
								contrartService.save(aptOrder);
								
								//记关联表aptOrderDetail
	  			    			AptorderDetail aptod = new AptorderDetail();
	  			    			String aptorderSeq = contrartService.getCloudSequence("SEQ_APTORDERDETAIL_ID");
	  			    			aptod.setAptorderId(orderId);
	  			    			aptod.setContrartId(strdate + branchid + sequences);
	  			    			aptod.setDetailId(strdate + branchid + aptorderSeq);
	  			    			aptod.setRecordTime(new Date());
	  			    			aptod.setRecordUser(staffid);
	  			    			aptod.setStatus("1");
	  			    			contrartService.save(aptod);
	  			    			
	  			    			// 原来是什么状态就是什么状态，除非原来是空房且没有维修
	  			    			String status1 = roomdata.getStatus();
								if(repairApplys.size() > 0){
									for(RepairApply repairApply : repairApplys){
										if(repairApply.getStatus().equals("2") || repairApply.getStatus().equals("3")){
											status1 = "W";
										}
									}
								}
								if("1".equals(status1)){
									status1 = "2";//变成预订
								}
	  			    			roomdata.setStatus(status1);
  				 	    		roomdata.setRecordTime(sdf.parse(sdf.format(new Date())));
  				 	    		contrartService.update(roomdata);
  			    		} 
  			    		//contrartService.save(contrart);
  			    		if(orderid != null && !("".equals(orderid))){
  			    			//如果是当天的，那么刷成入住状态
  			    			Aptorder aptOrder = (Aptorder)contrartService.findOneByProperties(Aptorder.class, "orderId", orderid, "status","1");
  			    			Date aa =sdf.parse(sdf.format(aptOrder.getArrivalTime())) ;
  			    			Date bb = sdf.parse(sdf.format(new Date()));
  			    			if(aa.compareTo(bb) == 0){
  			    				roomdata.setStatus(status);
  				 	    		roomdata.setRecordTime(sdf.parse(sdf.format(new Date())));
  				 	    		contrartService.update(roomdata);
  			    				contrart.setStatus("4");
  			    				aptOrder.setStatus("3");
  			    				OperateLog operlog = new OperateLog();
  	  							operlog.setBranchId(contrart.getBranchId());
  	  							operlog.setLogId(DateUtil.currentDate("yyMMdd") + contrart.getBranchId() + contrartService.getSequence("SEQ_OPERATELOG_ID"));
  	  							String operid = InetAddress.getLocalHost().toString();// IP地址
  	  							operid = (String) operid.subSequence(operid.indexOf("/") + 1, operid.length());
  	  							operlog.setOperIp(operid);
  	  							operlog.setOperType("5");
  	  							operlog.setOperModule("入住操作");
  	  							operlog.setContent("合同号" + contrart.getContrartId() + "已经入住");
  	  							operlog.setRecordUser(SystemConstants.User.ADMIN_NAME);
  	  							operlog.setRecordTime(new Date());
  	  						    contrartService.save(operlog);
  	  						    contrartService.save(aptOrder);
  								List <CheckUser> aptOrderCheckPersonList =  contrartService.findByProperties(CheckUser.class, "checkId", orderid, "status","1");
  								for(CheckUser checkUser:aptOrderCheckPersonList){
  									CheckUser checkUserContractType = new CheckUser();
  									checkUserContractType.setCheckuserId(aptOrder.getBranchId() + "1" + contrartService.getSequence("SEQ_NEW_CHECKUSER"));
  									checkUserContractType.setCheckuserName(checkUser.getCheckuserName());
  									checkUserContractType.setIdcard(checkUser.getIdcard());
  									checkUserContractType.setGender(checkUser.getGender());
  									checkUserContractType.setMobile(checkUser.getMobile());
  									checkUserContractType.setStatus("1");
  									checkUserContractType.setRecordUser(SystemConstants.User.ADMIN_NAME);
  									checkUserContractType.setRecordTime(new Date());
  									checkUserContractType.setCheckId(strdate + branchid + sequences);
  									checkUserContractType.setCheckuserType(checkUser.getCheckuserType());
  									checkUserContractType.setCheckinType("2");
  									contrartService.save(checkUserContractType);
  								}
  								DataDealPortalService service = new DataDealPortalService();
  								IDataDealPortal portal = service.getDataDealPortalPort();
  								String code = portal.call(SystemConstants.EnginType.COMMON_DATA,
  										SystemConstants.Movement.CUSTOM_QUERY,
  										"{ function: \"apartmentList.checkInHydropower\", "
  												+ "data:{branchId:" + contrart.getBranchId()
  												+ ",roomId:" + contrart.getRoomId() + ",orderId:"
  												+ contrart.getContrartId() + "  } }");

  								if ("-1".equals(code)) {
									Equipment equipment = (Equipment) contrartService.findOneByProperties(Equipment.class, "branchId", branchid, "roomId", roomid);
									if (equipment != null) {
										WarningLog warningLog = new WarningLog();
										warningLog.setLogId(DateUtil.currentDate("yyMMdd")
												+ contrart.getBranchId()
												+ contrartService.getSequence("SEQ_WARNINGLOG_ID"));
										warningLog.setBranchId(contrart.getBranchId());
										warningLog.setRoomId(contrart.getRoomId());
										warningLog.setSerialNo(equipment.getSerialNo());
										warningLog.setWarningType("3");
										warningLog.setWarningDate(new Date());
										warningLog.setStatus("1");
										warningLog.setRecordTime(new Date());
										warningLog.setRecordUser(staffid);
										warningLog.setRemark("合同号" + contrart.getContrartId() + DateUtil.d2s(new Date()) + "入住时连接水电后台失败!");
										contrartService.save(warningLog);
									}
								}
  			    			}else{
  			    				//这是判断从app来的订单如果没到入住时间，先存一份预定状态的入住人(checkintype = 1)
  			    				List <CheckUser> aptOrderCheckPersonList =  contrartService.findByProperties(CheckUser.class, "checkId", orderid, "status","1");
  								for(CheckUser checkUser:aptOrderCheckPersonList){
  									CheckUser checkUserContractType = new CheckUser();
  									checkUserContractType.setCheckuserId(aptOrder.getBranchId() + "1" + contrartService.getSequence("SEQ_NEW_CHECKUSER"));
  									checkUserContractType.setCheckuserName(checkUser.getCheckuserName());
  									checkUserContractType.setIdcard(checkUser.getIdcard());
  									checkUserContractType.setGender(checkUser.getGender());
  									checkUserContractType.setMobile(checkUser.getMobile());
  									checkUserContractType.setStatus("1");
  									checkUserContractType.setRecordUser(SystemConstants.User.ADMIN_NAME);
  									checkUserContractType.setRecordTime(new Date());
  									checkUserContractType.setCheckId(strdate + branchid + sequences);
  									checkUserContractType.setCheckuserType(checkUser.getCheckuserType());
  									checkUserContractType.setCheckinType("1");
  									contrartService.save(checkUserContractType);
  								}
  								// 更新公寓订单表中的订单状态-从新订状态转为待入住状态
  								aptOrder.setStatus("6");
  								contrartService.save(aptOrder);
  			    			}
  			    			
  			    			//记关联表
  			    			AptorderDetail aptod = new AptorderDetail();
  			    			String aptorderSeq = contrartService.getCloudSequence("SEQ_APTORDERDETAIL_ID");
  			    			aptod.setAptorderId(orderid);
  			    			aptod.setContrartId(strdate + branchid + sequences);
  			    			aptod.setDetailId(strdate + branchid + aptorderSeq);
  			    			aptod.setRecordTime(new Date());
  			    			aptod.setRecordUser(staffid);
  			    			aptod.setStatus("1");
  			    			contrartService.save(aptod);
  			    		}
  			    		contrartService.save(contrart);
  			    		logServiceInPms.contrartadd(loginuser,"0",request);
  		
  		  				
  		    				if(!"".equals(originalFilename)){
  		    					RemoteTransUtil.transData(CommonConstants.Path.RECIVEFILE,
  		    							"\\upload\\" + fileName_timestamp,
  										SystemConstants.RemoteTransDataType.MIXED,
  										SystemConstants.RemoteTransReturnType.STRING);
  		    					
  		    				}
  		
  			    		 JSONUtil.responseJSON(response, new AjaxResult(2, "新增成功!"));
  				    } 
  				}
  		  }else{
  			 JSONUtil.responseJSON(response, new AjaxResult(1, "该房已被预约!"));
  		  }
  		}else{
  			
  			 JSONUtil.responseJSON(response, new AjaxResult(1, "该房在合同时间范围内是停售房或未入住维修房!"));
  		}
      		
      		
	   


		}
		
	//}
	@RequestMapping("/memberSelectForContrart.do")
	public void memberSelectForContrart(HttpServletRequest request, HttpServletResponse response, String Mnumber) throws Exception {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String a = null;

		List<String> resultList = new ArrayList<String>();
        List<?> list =null;
		//if(HeartBeatClient.heartbeat){
			DataDealPortalService service = new DataDealPortalService();
			IDataDealPortal portal = service.getDataDealPortalPort();
			String mr = portal.call(SystemConstants.EnginType.COMMON_DATA, SystemConstants.Movement.CUSTOM_QUERY, "{ function: \"checkInDirect.getMemberBean\", data:{idcard:" +Mnumber  +" } }");
			if(mr.equals("-1")){
				a = "未查询到相关会员信息或该会员已失效，请先注册会员！";
				JSONUtil.responseJSON(response, new AjaxResult(0, a));
			}else{
				//List<?> memberdata = LeftmenuService.getMemberdata(Mnumber);
				if(Mnumber.length() > 11){
					list = leftmenuService.findBySQL("queryMemberByIdcard", new String[]{Mnumber},true);
				}else{
					list = leftmenuService.findBySQL("queryMemberBymobile", new String[]{Mnumber},true);	
				}
				//Member memberbean =  (Member)LeftmenuService.findOneByProperties(Member.class, "mobile", Mnumber, "status","1");
					JSONArray mrr = new JSONArray(mr);
					DateFormat dff = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					if(mrr.length()>0){
						 JSONObject mrrr = mrr.getJSONObject(0);
						  Date birth;
							if (mrrr.getString("BIRTHDAY")=="null") {
								birth = null;
							} else {
								birth = dff.parse(mrrr.getString("BIRTHDAY"));
							} 
							
							  Date recordt;
								if (mrrr.getString("RECORD_TIME") == "null") {
									recordt = null;
								} else {
									recordt = dff.parse(mrrr.getString("RECORD_TIME"));
								}
						  if (list.size() == 0) {
						  Member member = new Member();
						  member.setMemberId(mrrr.getString("MEMBER_ID")== "null" ? "" : mrrr.getString("MEMBER_ID"));
					      member.setMemberName(mrrr.getString("MEMBER_NAME")== "null" ? "" :mrrr.getString("MEMBER_NAME"));
					      member.setLoginName(mrrr.getString("LOGIN_NAME")== "null" ? "" :mrrr.getString("LOGIN_NAME"));
					      member.setPassword(mrrr.getString("PASSWORD")== "null" ? "" :mrrr.getString("PASSWORD"));
					      member.setMemberRank(mrrr.getString("MEMBER_RANK")== "null" ? "" :mrrr.getString("MEMBER_RANK"));
					      member.setIdcard(mrrr.getString("IDCARD")== "null" ? "" :mrrr.getString("IDCARD"));
					      member.setGendor(mrrr.getString("GENDOR")== "null" ? "" :mrrr.getString("GENDOR"));					     
			              member.setBirthday(birth);
			              member.setMobile(mrrr.getString("MOBILE")== "null" ? "" :mrrr.getString("MOBILE"));
			              member.setEmail( mrrr.getString("EMAIL")== "null" ? "" :mrrr.getString("EMAIL")); 
			              member.setAddress(mrrr.getString("ADDRESS")== "null" ? "" :mrrr.getString("ADDRESS"));
			              member.setPostcode(mrrr.getString("POSTCODE")== "null" ? "" :mrrr.getString("POSTCODE"));
			              member.setPhotos(mrrr.getString("PHOTOS")== "null" ? "" :mrrr.getString("PHOTOS"));
			              member.setSource(mrrr.getString("SOURCE")== "null" ? "" :mrrr.getString("SOURCE"));
			              Date vtime = dff.parse(mrrr.getString("VALID_TIME"));
			              member.setValidTime(vtime);
			              member.setInvalidTime(dff.parse(mrrr.getString("INVALID_TIME")));
			              member.setRegisterTime(dff.parse(mrrr.getString("REGISTER_TIME")));
			              member.setRecordTime(recordt);
						  member.setStatus(mrrr.getString("STATUS"));
						  member.setRemark(mrrr.getString("REMARK")== "null" ? "" :mrrr.getString("REMARK"));
						  member.setOpenId(mrrr.getString("OPENID"));
					      member.setRecommend(mrrr.getString("RECOMMEND")== "null" ? "" :mrrr.getString("REMARK"));
					      leftmenuService.save(member);
						  }else{
							 String memberid = mrrr.getString("MEMBER_ID").toString();
							 //String mobile = mrrr.getString("MOBILE").toString();
							 Member memberupdate = ((Member) (leftmenuService.findOneByProperties(Member.class, "memberId", memberid,"status","1")));
							if(null != memberupdate){
								 memberupdate.setMemberName(mrrr.getString("MEMBER_NAME")== "null" ? "" :mrrr.getString("MEMBER_NAME"));
								 memberupdate.setLoginName(mrrr.getString("LOGIN_NAME")== "null" ? "" :mrrr.getString("LOGIN_NAME"));
								 memberupdate.setPassword(mrrr.getString("PASSWORD")== "null" ? "" :mrrr.getString("PASSWORD"));
								 memberupdate.setMemberRank(mrrr.getString("MEMBER_RANK")== "null" ? "" :mrrr.getString("MEMBER_RANK"));
								 memberupdate.setIdcard(mrrr.getString("IDCARD")== "null" ? "" :mrrr.getString("IDCARD"));
								 memberupdate.setGendor(mrrr.getString("GENDOR")== "null" ? "" :mrrr.getString("GENDOR"));					     
								 memberupdate.setBirthday(birth);
								 memberupdate.setEmail( mrrr.getString("EMAIL")== "null" ? "" :mrrr.getString("EMAIL"));
								 memberupdate.setAddress(mrrr.getString("ADDRESS")== "null" ? "" :mrrr.getString("ADDRESS"));
								 memberupdate.setPostcode(mrrr.getString("POSTCODE")== "null" ? "" :mrrr.getString("POSTCODE"));
								 memberupdate.setPhotos(mrrr.getString("PHOTOS")== "null" ? "" :mrrr.getString("PHOTOS"));
								 memberupdate.setSource(mrrr.getString("SOURCE")== "null" ? "" :mrrr.getString("SOURCE"));
					             Date vtime = dff.parse(mrrr.getString("VALID_TIME"));
					             memberupdate.setValidTime(vtime);
					             memberupdate.setInvalidTime(dff.parse(mrrr.getString("INVALID_TIME")));
					             memberupdate.setRegisterTime(dff.parse(mrrr.getString("REGISTER_TIME")));
					             memberupdate.setRecordTime(recordt);
					             memberupdate.setStatus(mrrr.getString("STATUS"));
					             memberupdate.setRemark(mrrr.getString("REMARK")== "null" ? "" :mrrr.getString("REMARK"));
					             memberupdate.setOpenId(mrrr.getString("OPENID"));
					             memberupdate.setRecommend(mrrr.getString("RECOMMEND")== "null" ? "" :mrrr.getString("REMARK"));
					             leftmenuService.update(memberupdate);
								
							}
							
						  }
						  
						String memberid =  mrrr.getString("MEMBER_ID")== "null" ? "" : mrrr.getString("MEMBER_ID").toString();
						String membername = mrrr.getString("MEMBER_NAME")== "null" ? "" :mrrr.getString("MEMBER_NAME").toString();
						String memberrank = mrrr.getString("MEMBER_RANK")== "null" ? "" :mrrr.getString("MEMBER_RANK").toString();
						String mobile = mrrr.getString("MOBILE")== "null" ? "" :mrrr.getString("MOBILE").toString();
						resultList.add(memberid);
						resultList.add(membername);
						resultList.add(memberrank);
						resultList.add(mobile);
						
						JSONUtil.responseJSON(response, new AjaxResult(1, a,resultList));
					}
				
				
			}
			
//		}else{
//			a = "本地网络未连接！";
//			JSONUtil.responseJSON(response, new AjaxResult(0, a));
//		}
		
		
		
	}
	@RequestMapping("/delContrart.do")
	public void delContrart(HttpServletRequest request, HttpServletResponse response, String contractId)
			throws UnknownHostException {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		delContrart(contractId);
		String result = "{\"code\":\"1\"}";
		logServiceInPms.contrartdelete(loginuser,request);
		JSONUtil.responseJSON(response, result);
	}

	public void delContrart(String contractId) throws UnknownHostException {
		
		String[] contractIds = contractId.split(",");
		Contrart contrart;
		List<Contrart> list = new ArrayList<Contrart>();
		for (int i = 0; i < contractIds.length; i++) {
			contrart = (Contrart) contrartService.findById(Contrart.class, contractIds[i]);
			if (!StringUtil.isEmpty(contractId)) {
				contrart.setStatus("0");
				list.add(contrart);
			}
		}
		if (!list.isEmpty()) {
			contrartService.saveOrUpdateAll(list);
			
			//数据同步
//    		SysParam param = (SysParam) contrartService
//			.findOneByProperties(SysParam.class, "paramType",
//					SystemConstants.ParamType.BRANCH_IP, "paramName", "300001");
//			
//			int priority = 1;
//			//priority = Integer.parseInt();
//			
//			
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("Contrart", list);
//			TransferServcie.getInstance().transferData(priority,
//					param.getContent(), JSONUtil.fromObject(map).toString());
//			
//			
		}
		
		
	}

	@RequestMapping("/adjustcontractprice.do")
	@RightMethodControl(rightType = RightConstants.RightType.APA_ADP)
	public ModelAndView adjustcontractprice(HttpServletRequest request, HttpServletResponse response, String price){
		ModelAndView mv = new ModelAndView("page/apartment/apartmentmainmenu/apartmentcontract/resetcontractprice");
		mv.addObject("price", price);
		return mv;
		
	}
}
