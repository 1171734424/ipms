package com.ideassoft.basic.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.hsqldb.lib.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ideassoft.basic.service.AuditBasicService;
import com.ideassoft.bean.AuditLog;
import com.ideassoft.bean.Branch;
import com.ideassoft.bean.CheckOut;
import com.ideassoft.bean.Equipment;
import com.ideassoft.bean.House;
import com.ideassoft.bean.MaintenanceLog;
import com.ideassoft.bean.Member;
import com.ideassoft.bean.PriceApply;
import com.ideassoft.bean.Purchase;
import com.ideassoft.bean.RepairApply;
import com.ideassoft.bean.Room;
import com.ideassoft.bean.RoomPrice;
import com.ideassoft.bean.Staff;
import com.ideassoft.bean.SysParam;
import com.ideassoft.core.ajax.AjaxResult;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.constants.CommonConstants;
import com.ideassoft.core.constants.SystemConstants;
import com.ideassoft.core.exception.DAOException;
import com.ideassoft.core.notifier.SmsSingleSender;
import com.ideassoft.core.page.PageBuilder;
import com.ideassoft.crm.service.CommonService;
import com.ideassoft.crm.service.TransferServcie;
import com.ideassoft.crm.templateMessage.ServiceMsg;
import com.ideassoft.crm.templateMessage.TemplateMessageUtil;
import com.ideassoft.util.JSONUtil;
import com.ideassoft.util.RegExUtil;
import com.ideassoft.util.RequestUtil;

@Transactional
@Controller
public class AuditBasicController {
	@Autowired
	private AuditBasicService auditBasicService;
	
	@Autowired
	PageBuilder pageBuilder;

	@Autowired
	CommonService commonService;

	// 默认审核人页面
	@RequestMapping("/turnTodefaultUserSet.do")
	public ModelAndView turnTodefaultUserSet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		if (loginuser.getStaff().getStaffName().equals("admin")) {
			mv.setViewName("/page/basic/audit/turnTodefaultUserSet");
		} else {
			mv.setViewName("/page/basic/audit/turnTodefaultUserSet");
			String branchId = loginuser.getStaff().getBranchId();
			Branch type  = ((Branch) (auditBasicService.findOneByProperties(Branch.class, "branchId", branchId,"status","1")));
			String branchtype = type.getRank().toString();
			String branchchange = null;
			if(branchtype.equals("0")){
				branchchange = "*";
			}else{
				branchchange = branchId;
			}
			List<?> auditpost = auditBasicService.getAuditpost();
			String coauditkind = "AUDITCheckOut";
			String rauditkind = "AUDITRepair";
			String prauditkind = "AUDITRoomprice";
			//String puauditkind = "AUDITPurchase";
			List<?> auditgradesnew = auditBasicService.getAuditgradesnew(coauditkind,branchchange);
			List<?> rauditgradesnew = auditBasicService.getAuditgradesnew(rauditkind,branchchange);
			List<?> prauditgradesnew = auditBasicService.getAuditgradesnew(prauditkind,branchchange);
			request.setAttribute("auditpost", auditpost);
			request.setAttribute("auditgradesnew", auditgradesnew);
			request.setAttribute("rauditgradesnew", rauditgradesnew);
			request.setAttribute("prauditgradesnew", prauditgradesnew);
			request.setAttribute("style", branchtype);
		}
		return mv;
	}
	
	@RequestMapping("/auditPostSure.do")
	public void auditPostSure(HttpServletRequest request, HttpServletResponse response, String checkone,
			String checksecond, String checkthird, String checkforth) throws Exception {
		SysParam sysParam = ((SysParam) (auditBasicService.findOneByProperties(SysParam.class, "paramType", "AUDITCheckOut")));
		sysParam.setParamName(checkone);
		sysParam.setParamDesc(checksecond);
		sysParam.setContent(checkthird);
		sysParam.setRemark(checkforth);
		auditBasicService.save(sysParam);
		JSONUtil.responseJSON(response, new AjaxResult(1, null));
	}

	// select审核人页面
	@RequestMapping("/selectAuditer.do")
	public ModelAndView selectAuditer(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/page/basic/audit/selectAuditer");
		return mv;
	}
	
	@RequestMapping("/auditPostSurenew.do")
	public void auditPostSurenew(HttpServletRequest request, HttpServletResponse response, String checkpostsarray,
			String repairpostsarray, String roompricepostsarray, String purchasepostsarray) throws Exception {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginuser.getStaff().getBranchId();
		SysParam systemtype = (SysParam) auditBasicService.findOneByProperties(SysParam.class, "paramType","SYSTEMTYPE", "status", "1");
		String stype = systemtype.getContent().toString();
		Branch type  = ((Branch) (auditBasicService.findOneByProperties(Branch.class, "branchId", branchId,"status","1")));
		String branchtype = type.getRank().toString();
		String branchchange = null;
        if(branchtype.equals("0")){
        	branchchange = "*";
		}else{
			branchchange = branchId;
		}
		SysParam sysParam = ((SysParam) (auditBasicService.findOneByProperties(SysParam.class, "paramType", "AUDITCheckOut","parameter1",branchchange)));
		SysParam rsysParam = ((SysParam) (auditBasicService.findOneByProperties(SysParam.class, "paramType", "AUDITRepair","parameter1",branchchange)));
		SysParam prsysParam = ((SysParam) (auditBasicService.findOneByProperties(SysParam.class, "paramType","AUDITRoomprice","parameter1",branchchange)));
		JSONArray json = JSONArray.fromObject(checkpostsarray);
		JSONArray rjson = JSONArray.fromObject(repairpostsarray);
		JSONArray prjson = JSONArray.fromObject(roompricepostsarray);
		SysParam defaultsysParam = ((SysParam) (auditBasicService.findOneByProperties(SysParam.class, "paramType", "AUDIT","remark" ,branchchange)));
		
		String defaultpost = defaultsysParam.getParameter1().toString();
		if (json.size() > 0) {
			for (int i = 0; i < json.size(); i++) {
				String job = json.getString(i);
				String rjob = rjson.getString(i);
				String prjob = prjson.getString(i);
				if (i == 0) {
					if (job.equals("*")) {
						sysParam.setParamName(defaultpost);
					} else {
						sysParam.setParamName(job);
					}
					if (rjob.equals("*")) {
						rsysParam.setParamName(defaultpost);
					} else {
						rsysParam.setParamName(rjob);
					}
					if (prjob.equals("*")) {
						if(branchtype.equals("0")){
						prsysParam.setParamName(defaultpost);
						}
				} else {
					if(branchtype.equals("0")){
					prsysParam.setParamName(prjob);
					}
				}
				} else if (i == 1) {
					sysParam.setParamDesc(job);
					rsysParam.setParamDesc(rjob);
					if(branchtype.equals("0")){
					prsysParam.setParamDesc(prjob);
					}
				} else if (i == 2) {
					sysParam.setContent(job);
					rsysParam.setContent(rjob);
					if(branchtype.equals("0")){
				     prsysParam.setContent(prjob);
					}
				} else if (i == 3) {
					sysParam.setRemark(job);
					rsysParam.setRemark(rjob);
					if(branchtype.equals("0")){
				    prsysParam.setRemark(prjob);
					}
				}
				auditBasicService.update(sysParam);
				if(stype.equals(CommonConstants.SystemType.Branch)){
				int priority = 1;
				SysParam param = (SysParam) auditBasicService.findOneByProperties(SysParam.class, "paramType","REMOTE_PATH", "status", "1");
				List<SysParam> csysparamList = new ArrayList<SysParam>();
				csysparamList.add(sysParam);
				Map<String, Object> csysparamMap = new HashMap <String, Object>();
				csysparamMap.put("SysParam", csysparamList);
				//TransferServcie.getInstance().transferData(priority, param.getContent(),JSONUtil.fromObject(csysparamMap).toString());
				}
				
				auditBasicService.update(rsysParam);
				if(stype.equals(CommonConstants.SystemType.Branch)){
					int priority = 1;
					SysParam param = (SysParam) auditBasicService.findOneByProperties(SysParam.class, "paramType","REMOTE_PATH", "status", "1");
					List<SysParam> rsysparamList = new ArrayList<SysParam>();
					rsysparamList.add(rsysParam);
					Map<String, Object> rsysparamMap = new HashMap <String, Object>();
					rsysparamMap.put("SysParam", rsysparamList);
					//TransferServcie.getInstance().transferData(priority, param.getContent(),JSONUtil.fromObject(rsysparamMap).toString());
					}
			}
		}

		JSONUtil.responseJSON(response, new AjaxResult(1, null));
	}
	
	// 采购审核信息页面
	@RequestMapping("/auditInfoDetail.do")
	public ModelAndView auditInfoDetail(HttpServletRequest request, HttpServletResponse response, String operid,
			String pagetype, String audittype) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/page/basic/audit/auditInfoDetail");
		List<?> pur = auditBasicService.getPur(operid);
		String applystaff = ((Map<?, ?>) pur.get(0)).get("RECORD_USER").toString();
		String applystaffname = ((Map<?, ?>) pur.get(0)).get("APPLYSTAFFNAME").toString();
		String branchid = ((Map<?, ?>) pur.get(0)).get("BRANCH_ID").toString();
		String branchname = ((Map<?, ?>) pur.get(0)).get("BRANCHNAME").toString();
		String applytime = ((Map<?, ?>) pur.get(0)).get("APPLYTIME").toString();
		String purchasetype = ((Map<?, ?>) pur.get(0)).get("PURCHASE_CATEGORY").toString();
		String purchaseamount = ((Map<?, ?>) pur.get(0)).get("PURCHASE_AMOUNT").toString();
		String applyreason = ((Map<?, ?>) pur.get(0)).get("REMARK").toString();
		List<?> purinfo = auditBasicService.getPurinfo(operid);
		request.setAttribute("pagetype", pagetype);
		request.setAttribute("audittype", audittype);
		request.setAttribute("operid", operid);
		request.setAttribute("applystaff", applystaff);
		request.setAttribute("applystaffname", applystaffname);
		request.setAttribute("branchid", branchid);
		request.setAttribute("branchname", branchname);
		request.setAttribute("applytime", applytime);
		request.setAttribute("purchasetype", purchasetype);
		request.setAttribute("purchaseamount", purchaseamount);
		request.setAttribute("applyreason", applyreason);
		request.setAttribute("purinfo", purinfo);
		return mv;
	}
	// 维修审核信息页面
	@RequestMapping("/repairapplyInfoDetail.do")
	public ModelAndView repairapplyInfoDetail(HttpServletRequest request, HttpServletResponse response, String operid,
			String pagetype, String audittype) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/page/basic/audit/repairapplyInfoDetail");
		List<?> repairdata = null;
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchid = loginUser.getStaff().getBranchId();
		Branch type  = ((Branch) (auditBasicService.findOneByProperties(Branch.class, "branchId", branchid,"status","1")));
		String branchtype = type.getRank().toString();
		if(branchtype.equals("0")){
			repairdata = auditBasicService.getRepairdatacloud(operid);
		}else{
			repairdata =  auditBasicService.getRepairdata(operid);
		}
		
		request.setAttribute("repairdata", repairdata);
		request.setAttribute("operid", operid);
		request.setAttribute("pagetype", pagetype);
		request.setAttribute("audittype", audittype);
		return mv;
	}
	
	@RequestMapping("/rpapplyInfoDetail.do")
	public ModelAndView rpauditInfoDetail(HttpServletRequest request, HttpServletResponse response, String operid,
			String pagetype, String audittype) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/page/basic/audit/rpapplyInfoDetail");
		PriceApply priceapply = ((PriceApply) (auditBasicService
				.findOneByProperties(PriceApply.class, "applyId", operid)));
		String staff = priceapply.getRecordUser().toString();
		String applybranch = priceapply.getBranchId().toString();
		String applytype = priceapply.getApplyType().toString();
		SysParam atsysParam = ((SysParam) (auditBasicService.findOneByProperties(SysParam.class, "paramType", "RPKIND",
				"content", applytype)));
		String applytypename = atsysParam.getParamName().toString();
		String validstart = priceapply.getValidStart()== null ? "" : priceapply.getValidStart().toString();
		String validend = priceapply.getValidEnd()== null ? "" : priceapply.getValidEnd().toString();
		String validday = priceapply.getValidDay().toString();
		String filterday = priceapply.getFilterDay()== null ? "" : priceapply.getFilterDay().toString();
		String remark = priceapply.getRemark() == null ? "" : priceapply.getRemark().toString();
		/*String reremark =  remark.substring(0,remark.length() - 1);*/
		String applykind = priceapply.getApplyKind().toString();
		SysParam aksysParam = ((SysParam) (auditBasicService.findOneByProperties(SysParam.class, "paramType",
				"RPSTATUS", "content", applykind)));
		String applykindname = aksysParam.getParamName().toString();
		String applytime = priceapply.getApplyTime().toString();
		Branch branchName = ((Branch) (auditBasicService.findOneByProperties(Branch.class, "branchId", applybranch)));
		String theme = branchName.getBranchType().toString();
		Staff staffName = ((Staff) (auditBasicService.findOneByProperties(Staff.class, "staffId", staff)));
		String staffname = staffName.getStaffName();
		String branchname = branchName.getBranchName().toString();
		SysParam sysParam = ((SysParam) (auditBasicService.findOneByProperties(SysParam.class, "content", theme,
				"paramType", "THEME")));
		String themename = sysParam.getParamName().toString();
		List<?> rpauditdata = auditBasicService.getRpdatanew(operid);
		List<?> rpauditrtdata = auditBasicService.getRpauditrtdata(applybranch, operid);
		List<?> rpidInitialize = auditBasicService.getRpidInitialize();
		request.setAttribute("pagetype", pagetype);
		request.setAttribute("audittype", audittype);
		request.setAttribute("operid", operid);
		request.setAttribute("staff", staff);
		request.setAttribute("staffname", staffname);
		request.setAttribute("applytime", applytime);
		request.setAttribute("applybranch", applybranch);
		request.setAttribute("branchname", branchname);
		request.setAttribute("theme", theme);
		request.setAttribute("themename", themename);
		request.setAttribute("applytype", applytype);
		request.setAttribute("validstart", validstart);
		request.setAttribute("validend", validend);
		request.setAttribute("validday", validday);
		request.setAttribute("filterday", filterday);
		request.setAttribute("remark", remark);
		request.setAttribute("applykind", applykind);
		request.setAttribute("rpauditdata", rpauditdata);
		request.setAttribute("applytypename", applytypename);
		request.setAttribute("applykindname", applykindname);
		request.setAttribute("rpauditrtdata", rpauditrtdata);
		request.setAttribute("rpidInitialize", rpidInitialize);
		return mv;
	}

	// 退房审核信息页面
	@RequestMapping("/checkoutInfoDetail.do")
	public ModelAndView checkoutInfoDetail(HttpServletRequest request, HttpServletResponse response, String operid,
			String pagetype, String audittype) throws Exception {
		ModelAndView mv = new ModelAndView();
		SimpleDateFormat sdf = new  SimpleDateFormat("yyyy/MM/dd");
		mv.setViewName("/page/basic/audit/checkoutInfoDetail");
		List<?> checkdata = auditBasicService.getCheckdata(operid);
		String branchid = ((Map<?, ?>) checkdata.get(0)).get("BRANCHID").toString();
		String checkoutTime = ((Map<?, ?>) checkdata.get(0)).get("CHECKOUTTIME").toString();
		String roomid = ((Map<?, ?>) checkdata.get(0)).get("ROOMID").toString();
		List<?> checkroomtype = auditBasicService.getCheckroomtype(branchid, roomid);
		String roomtype = ((Map<?, ?>) checkroomtype.get(0)).get("ROOM_type").toString();
		List<?> auditroomtype = auditBasicService.getAuditroomtype(roomtype, branchid);
		String roomname = ((Map<?, ?>) auditroomtype.get(0)).get("ROOM_NAME").toString();
		request.setAttribute("checkdata", checkdata);
		request.setAttribute("checkouttime", sdf.format(sdf.parse(checkoutTime)));
		request.setAttribute("roomtype", roomtype);
		request.setAttribute("roomname", roomname);
		request.setAttribute("operid", operid);
		request.setAttribute("pagetype", pagetype);
		request.setAttribute("audittype", audittype);
		return mv;
	}

	// 审核通过且记录审核日志表
		@RequestMapping("/updateAuditSubmitOk.do")
		public void updateAuditSubmitOk(HttpServletRequest request, HttpServletResponse response, String audittype,
				String operid, String branchId, String applystaff, String auditMessage) throws Exception {
			LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
			String branchid = loginUser.getStaff().getBranchId();
			Staff staff = loginUser.getStaff();
			String aurecordUser = staff.getStaffId();
			int  priority = 1;
			if ("采购申请".equals(audittype)) {
				Purchase purchase = ((Purchase) (auditBasicService.findOneByProperties(Purchase.class, "purchaseId", operid)));
				purchase.setStatus("2");
				auditBasicService.save(purchase);
				String logId = this.auditBasicService.getSequence("SEQ_NEW_AUDITLOG", null);
				Date day = new Date();
				SimpleDateFormat df = new SimpleDateFormat("yyMMdd");
				String t = df.format(day);
				String datId = t + branchId + logId;
				AuditLog auditLog = new AuditLog();
				auditLog.setLogId(datId);
				auditLog.setOperId(operid);
				auditLog.setOperType("1");
				auditLog.setStatus("2");
				auditLog.setRecordUser(aurecordUser);
				auditLog.setBranchId(branchid);
				auditLog.setRecordTime(new Date());
				auditLog.setRemark(auditMessage);
				this.auditBasicService.saveAuditLog(auditLog);
				JSONUtil.responseJSON(response, new AjaxResult(1, null));
				
			} else if ("房价申请".equals(audittype)) {
				PriceApply priceapply = ((PriceApply) (auditBasicService.findOneByProperties(PriceApply.class, "applyId", operid)));
				String auditrecordtime = priceapply.getRecordTime()== null ? null : priceapply.getRecordTime().toString();
				String auidtapplytime = priceapply.getApplyTime()== null ? null : priceapply.getApplyTime().toString();
				DateFormat dff = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String auditpostnow = priceapply.getPost().toString();
				String applybranchid = priceapply.getBranchId().toString();
				String applytype = priceapply.getApplyType().toString();
				String recorduser = priceapply.getRecordUser().toString();
				String applykind = priceapply.getApplyKind().toString();
				String premark = priceapply.getRemark().toString();
				String pstatus = premark.substring(premark.length()-1);
				SysParam atsysParam = ((SysParam) (auditBasicService.findOneByProperties(SysParam.class, "paramType","RPKIND", "content", applytype)));
				String applytypename = atsysParam.getParamName().toString();
				SysParam aksysParam = ((SysParam) (auditBasicService.findOneByProperties(SysParam.class, "paramType","RPSTATUS", "content", applykind)));
				String applykindname = aksysParam.getParamName().toString();
				Branch branchName = ((Branch) (auditBasicService.findOneByProperties(Branch.class, "branchId", applybranchid)));
				String branchname = branchName.getBranchName().toString();
				Staff staffName = ((Staff) (auditBasicService.findOneByProperties(Staff.class, "staffId", recorduser)));
				String staffname = staffName.getStaffName();
				String auditkind = "AUDITRoomprice";
				String cloudauditbranchid = "*";
				List<?> auditgrades = auditBasicService.getAuditgrades(auditkind,cloudauditbranchid);
				String auditremark = null;
				String nextpost = null;
				String ip = "";
				if(branchName.getBranchIp() != null && !StringUtil.isEmpty(branchName.getBranchIp().toString())){
					ip = branchName.getBranchIp().toString();
				} 
				String branchURL = "http://"+ip+":8080/ipms";
				if (auditpostnow.equals(((Map<?, ?>) auditgrades.get(0)).get("GRADEONE").toString())) {
					priceapply.setPost(((Map<?, ?>) auditgrades.get(0)).get("GRADESECOND").toString());
					auditremark = "一审通过";
					auditMessage = auditMessage + "(一审通过)";
					nextpost = ((Map<?, ?>) auditgrades.get(0)).get("GRADESECOND").toString();

				} else if (auditpostnow.equals(((Map<?, ?>) auditgrades.get(0)).get("GRADESECOND").toString())) {
					priceapply.setPost(((Map<?, ?>) auditgrades.get(0)).get("GRADETHIRD").toString());
					auditremark = "二审通过";
					auditMessage = auditMessage + "(二审通过)";
					nextpost = ((Map<?, ?>) auditgrades.get(0)).get("GRADETHIRD").toString();

				} else if (auditpostnow.equals(((Map<?, ?>) auditgrades.get(0)).get("GRADETHIRD").toString())) {
					priceapply.setPost(((Map<?, ?>) auditgrades.get(0)).get("GRADEFORTH").toString());
					auditremark = "三审通过";
					auditMessage = auditMessage + "(三审通过)";
					nextpost = ((Map<?, ?>) auditgrades.get(0)).get("GRADEFORTH").toString();

				} else if (auditpostnow.equals(((Map<?, ?>) auditgrades.get(0)).get("GRADEFORTH").toString())) {
					priceapply.setPost("*");
					auditremark = "四审通过";
					auditMessage = auditMessage + "(四审通过)";
					nextpost = "";

				}
				String logId = this.auditBasicService.getSequence("SEQ_NEW_AUDITLOG", null);
				Date day = new Date();
				SimpleDateFormat df = new SimpleDateFormat("yyMMdd");
				String t = df.format(day);
				String datId = t + branchId + logId;
				AuditLog auditLog = new AuditLog();
				auditLog.setLogId(datId);
				auditLog.setOperId(operid);
				auditLog.setOperType("2");
				auditLog.setStatus("2");
				auditLog.setBranchId(branchid);
				auditLog.setRecordUser(aurecordUser);
				auditLog.setRemark(auditMessage);
				auditLog.setRecordTime(new Date());
				this.auditBasicService.saveAuditLog(auditLog);
				/*List<AuditLog> auditLogList = new ArrayList<AuditLog>();
				auditLogList.add(auditLog);
				Map<String, Object> auditLogMap = new HashMap<String, Object>();
				auditLogMap.put("AuditLog", auditLogList);
				TransferServcie.getInstance().transferData(priority, branchURL,JSONUtil.fromObject(auditLogMap).toString());*/
				if((applykind.equals(CommonConstants.RoomPriceStatus.basic))&&(nextpost.equals("*"))){
					List<?> updateroompricebasic = auditBasicService.updateRoompricebasic(operid);
					for (int k = 0; k < updateroompricebasic.size(); k++) {
						String rpid = ((Map<?, ?>) updateroompricebasic.get(k)).get("CONTENT").toString();
						String roomtype = ((Map<?, ?>) updateroompricebasic.get(k)).get("ROOM_TYPE").toString();
						RoomPrice basicroomorice = (RoomPrice) auditBasicService.findOneByProperties(RoomPrice.class,  "roomPriceId.branchId", applybranchid, "roomPriceId.rpId", rpid, "roomPriceId.roomType", roomtype, "roomPriceId.rpKind", applytype, "roomPriceId.status", CommonConstants.RoomPriceStatus.basic);
						Double pricebasic = Double.parseDouble(((Map<?, ?>) updateroompricebasic.get(k)).get("ROOM_PRICE").toString());
						basicroomorice.setRoomPrice(pricebasic);
						basicroomorice.setRecordTime(new Date());
						basicroomorice.setRecordUser(aurecordUser);
						auditBasicService.update(basicroomorice);
						List<RoomPrice> roompriceList = new ArrayList<RoomPrice>();
						roompriceList.add(basicroomorice);
						Map<String, Object> roompriceMap = new HashMap<String, Object>();
						roompriceMap.put("RoomPrice", roompriceList);
						if(ip != null && !StringUtil.isEmpty(ip)){
							//TransferServcie.getInstance().transferData(priority, branchURL,JSONUtil.fromObject(roompriceMap).toString());
						}
					}
					priceapply.setStatus("3");
					
				}else if((applykind.equals(CommonConstants.RoomPriceStatus.adjust))&&(nextpost.equals("*"))){
					priceapply.setStatus(pstatus);
					String auditvalidstart = priceapply.getValidStart()== null ? null : priceapply.getValidStart().toString();
					String auidtvalidend = priceapply.getValidEnd()== null ? null : priceapply.getValidEnd().toString();
					Date validstart = dff.parse(auditvalidstart);
					Date validend = dff.parse(auidtvalidend);
					priceapply.setValidStart(validstart);
					priceapply.setValidEnd(validend);
				}
				Date applytime = dff.parse(auidtapplytime);
				Date recordtime = dff.parse(auditrecordtime);
				priceapply.setRecordTime(recordtime);
				priceapply.setApplyTime(applytime);
				priceapply.setAuditRemark(auditremark);
				priceapply.setRecordUser(aurecordUser);
				auditBasicService.update(priceapply);
				auditBasicService.getSession().flush();
				List<PriceApply> priceapplyList = new ArrayList<PriceApply>();
				priceapplyList.add(priceapply);
				Map<String, Object> priceapplyMap = new HashMap<String, Object>();
				priceapplyMap.put("PriceApply", priceapplyList);
				if(ip != null && !StringUtil.isEmpty(ip)){
					//TransferServcie.getInstance().transferData(priority, branchURL,JSONUtil.fromObject(priceapplyMap).toString());
				}
					List<?> poststaffphone = auditBasicService.getPoststaffphone(nextpost, branchid);
				if (null != poststaffphone && !poststaffphone.isEmpty()) {
					for (int j = 0; j < poststaffphone.size(); j++) {
						String mobile = ((Map<?, ?>) poststaffphone.get(j)).get("MOBILE").toString();
						String auditername = ((Map<?, ?>) poststaffphone.get(j)).get("STAFFNAME").toString();
						if (mobile.matches(RegExUtil.MOBILE)) {
							SmsSingleSender singleSender = new SmsSingleSender(SystemConstants.note.APPID,
									SystemConstants.note.APPKEY);
							ArrayList<String> params = new ArrayList<String>();
							params.add(auditername);
							params.add(branchname);
							params.add(staffname);
							params.add(applytypename);
							params.add(applykindname);
							params.add(auditremark);
							singleSender.sendWithParam(SystemConstants.note.COUNTRY, mobile, 47258, params, "", "", "");
						}

					}
				}/*else{
					List<?> poststaffphone = auditBasicService.getPoststaffphone(aurecordUser, branchid);
					
				}*/
				JSONUtil.responseJSON(response, new AjaxResult(1, null));
			} else if ("退房申请".equals(audittype)) {
				CheckOut checkOut = (CheckOut) auditBasicService.findOneByProperties(CheckOut.class, "checkOutId", operid);
				Member member = (Member) auditBasicService.findById(Member.class, checkOut.getMemberId());
				Branch branch = (Branch) this.auditBasicService.findOneByProperties(Branch.class, "branchId", checkOut.getBranchId());
				checkOut.setStatus("2");
				auditBasicService.update(checkOut);
				String auditpostnow = checkOut.getPost().toString();
				String roomid = checkOut.getRoomId().toString();
				String memberid = checkOut.getMemberId().toString();
				String auditkind = "AUDITCheckOut";
				List<?> auditgrades = auditBasicService.getAuditgrades(auditkind,branchid);
				String auditremark = null;
				String nextpost = null;
				if (auditpostnow.equals(((Map<?, ?>) auditgrades.get(0)).get("GRADEONE").toString())) {
					checkOut.setPost(((Map<?, ?>) auditgrades.get(0)).get("GRADESECOND").toString());
					auditremark = "一审通过";
					auditMessage = auditMessage + "(一审通过)";
					nextpost = ((Map<?, ?>) auditgrades.get(0)).get("GRADESECOND").toString();

				} else if (auditpostnow.equals(((Map<?, ?>) auditgrades.get(0)).get("GRADESECOND").toString())) {
					checkOut.setPost(((Map<?, ?>) auditgrades.get(0)).get("GRADETHIRD").toString());
					auditremark = "二审通过";
					auditMessage = auditMessage + "(二审通过)";
					nextpost = ((Map<?, ?>) auditgrades.get(0)).get("GRADETHIRD").toString();

				} else if (auditpostnow.equals(((Map<?, ?>) auditgrades.get(0)).get("GRADETHIRD").toString())) {
					checkOut.setPost(((Map<?, ?>) auditgrades.get(0)).get("GRADEFORTH").toString());
					auditremark = "三审通过";
					auditMessage = auditMessage + "(三审通过)";

				} else if (auditpostnow.equals(((Map<?, ?>) auditgrades.get(0)).get("GRADEFORTH").toString())) {
					checkOut.setPost("*");
					auditremark = "四审通过";
					auditMessage = auditMessage + "(四审通过)";
					nextpost = "";

				}
				checkOut.setAuditRemark(auditremark);
				String logId = this.auditBasicService.getSequence("SEQ_NEW_AUDITLOG", null);
				Date day = new Date();
				SimpleDateFormat df = new SimpleDateFormat("yyMMdd");
				String t = df.format(day);
				String datId = t + branchId + logId;
				AuditLog auditLog = new AuditLog();
				auditLog.setLogId(datId);
				auditLog.setOperId(operid);
				auditLog.setOperType("3");
				auditLog.setStatus("2");
				auditLog.setRecordUser(aurecordUser);
				auditLog.setBranchId(branchid);
				auditLog.setRemark(auditMessage);
				auditLog.setRecordTime(new Date());
				this.auditBasicService.saveAuditLog(auditLog);
				
				List<?> poststaffphone = auditBasicService.getPoststaffphone(nextpost, branchid);
				if (null != poststaffphone && !poststaffphone.isEmpty()) {
					for (int j = 0; j < poststaffphone.size(); j++) {
						String mobile = ((Map<?, ?>) poststaffphone.get(j)).get("MOBILE").toString();
						String staffname = ((Map<?, ?>) poststaffphone.get(j)).get("STAFFNAME").toString();
						String audittypename = CommonConstants.MessageTextDefinition.CheckOut;
						if (mobile.matches(RegExUtil.MOBILE)) {
							Member membername = (Member) auditBasicService.findOneByProperties(Member.class, "memberId",
									memberid, "status", "1");
							String customername = membername.getMemberName().toString();
							SmsSingleSender singleSender = new SmsSingleSender(SystemConstants.note.APPID,
									SystemConstants.note.APPKEY);
							ArrayList<String> params = new ArrayList<String>();
							params.add(staffname);
							params.add(roomid);
							params.add(customername);
							params.add(audittypename);
							params.add(auditremark);
							singleSender.sendWithParam(SystemConstants.note.COUNTRY, mobile, 47260, params, "", "", "");
						}

					}
				}
				if("*".equals(checkOut.getPost())){
					commonService.Weixin(member, checkOut, "已通过审核",branch);
					SmsSingleSender singleSender = new SmsSingleSender(SystemConstants.note.APPID, SystemConstants.note.APPKEY);
					ArrayList<String> params = new ArrayList<String>();
					params.add("审核通过");
					params.add(branch.getPhone());
					singleSender.sendWithParam(SystemConstants.note.COUNTRY,member.getMobile(), 67318, params, "", "", "");
				}

				JSONUtil.responseJSON(response, new AjaxResult(1, null));
			} else if ("维修申请".equals(audittype)) {
				RepairApply ra = (RepairApply) auditBasicService.findOneByProperties(RepairApply.class, "repairApplyId", operid);
				String recorduser = ((LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY))
						.getStaff().getStaffId();
				String auditpostnow = ra.getPost().toString();
				String roomid = ra.getRoomId().toString();
				
				//补丁操作
				String memberid = ra.getReservedPerson().toString();
				Member member = new Member();
				if(StringUtils.isEmpty(ra.getContractId())){
					Staff staff1 = (Staff) auditBasicService.findById(Staff.class, memberid);
					member.setMobile(staff1.getMobile());				
				}else{
					member = (Member) auditBasicService.findById(Member.class, memberid);
				}
				
				String auditkind = "AUDITRepair";
				Branch branchrank = (Branch) auditBasicService.findOneByProperties(Branch.class, "branchId",branchid, "status", "1");
				String rank = branchrank.getRank().toString();
				String branchidchange = null;
				if(rank.equals("0")){
					branchidchange = "*";
				}else{
					branchidchange = branchid;
				}
				List<?> auditgrades = auditBasicService.getAuditgrades(auditkind,branchidchange);
				String auditremark = null;
				String nextpost = null;
				if (auditpostnow.equals(((Map<?, ?>) auditgrades.get(0)).get("GRADEONE").toString())) {
					ra.setPost(((Map<?, ?>) auditgrades.get(0)).get("GRADESECOND").toString());
					auditremark = "一审通过";
					auditMessage = auditMessage + "(一审通过)";
					nextpost = ((Map<?, ?>) auditgrades.get(0)).get("GRADESECOND").toString();

				} else if (auditpostnow.equals(((Map<?, ?>) auditgrades.get(0)).get("GRADESECOND").toString())) {
					ra.setPost(((Map<?, ?>) auditgrades.get(0)).get("GRADETHIRD").toString());
					auditremark = "二审通过";
					auditMessage = auditMessage + "(二审通过)";
					nextpost = ((Map<?, ?>) auditgrades.get(0)).get("GRADETHIRD").toString();

				} else if (auditpostnow.equals(((Map<?, ?>) auditgrades.get(0)).get("GRADETHIRD").toString())) {
					ra.setPost(((Map<?, ?>) auditgrades.get(0)).get("GRADEFORTH").toString());
					auditremark = "三审通过";
					auditMessage = auditMessage + "(三审通过)";
					nextpost = ((Map<?, ?>) auditgrades.get(0)).get("GRADEFORTH").toString();

				} else if (auditpostnow.equals(((Map<?, ?>) auditgrades.get(0)).get("GRADEFORTH").toString())) {
					ra.setPost("*");
					auditremark = "四审通过";
					auditMessage = auditMessage + "(四审通过)";
					nextpost = "";

				}
				ra.setStatus("2");
				ra.setRecordTime(new Date());
				ra.setRecordUser(recorduser);
				ra.setAuditRemark(auditremark);
				String logId = this.auditBasicService.getSequence("SEQ_NEW_AUDITLOG", null);
				Date day = new Date();
				SimpleDateFormat df = new SimpleDateFormat("yyMMdd");
				String t = df.format(day);
				String datId = t + branchId + logId;
				AuditLog auditLog = new AuditLog();
				auditLog.setLogId(datId);
				auditLog.setOperId(operid);
				auditLog.setOperType("4");
				auditLog.setStatus("2");
				auditLog.setRecordUser(aurecordUser);
				auditLog.setBranchId(branchid);
				auditLog.setRemark(auditMessage);
				auditLog.setRecordTime(new Date());
				try {
					auditBasicService.update(ra);
					this.auditBasicService.saveAuditLog(auditLog);
					if("*".equals(nextpost)){//已审核,房态改为维修房roomid
						//判断是民宿还是公寓
						if(ra.getBranchId().startsWith("H")){//民宿
							House house = (House) auditBasicService.findOneByProperties(House.class, "houseId", ra.getBranchId());
							house.setStatus("W");
							house.setRecordTime(new Date());
							//house.setRecordUser(aurecordUser);
							auditBasicService.update(house);
							
							if (!StringUtil.isEmpty(member.getOpenId()) || !"null".equals(member.getOpenId())) {
								ServiceMsg serviceMsg = new ServiceMsg();
								serviceMsg.setFirst("您好!您的维修申请已通过审核!");
								serviceMsg.setRoomId(house.getHouseNo());
								serviceMsg.setServiceType("维修");
								serviceMsg.setServiceContent("民宿维修");
								serviceMsg.setRemark("如您有任何疑问，请联系"+branchrank.getPhone()+"!");
								String token = commonService.Token();
								TemplateMessageUtil.sendServiceMsg(token, member.getOpenId(), serviceMsg);
							}
							
						}else{
							Room room = (Room) auditBasicService.findOneByProperties(Room.class, "roomKey.roomId", ra.getRoomId(),"roomKey.branchId",ra.getBranchId());
							room.setStatus("W");
							room.setRecordTime(new Date());
							room.setRecordUser(aurecordUser);
							if(ra.getSerialNo() != null){
								Equipment eqt = (Equipment) auditBasicService.findOneByProperties(Equipment.class, "serialNo", ra.getSerialNo());
								eqt.setStatus("3");//状态维修
								auditBasicService.update(eqt);
							}
							auditBasicService.update(room);
							if (!StringUtil.isEmpty(member.getOpenId()) || !"null".equals(member.getOpenId())) {
								ServiceMsg serviceMsg = new ServiceMsg();
								serviceMsg.setFirst("您好!您的维修申请已通过审核!");
								serviceMsg.setRoomId(ra.getRoomId());
								serviceMsg.setServiceType("维修");
								serviceMsg.setServiceContent("公寓维修");
								serviceMsg.setRemark("如您有任何疑问，请联系"+branchrank.getPhone()+"!");
								String token = commonService.Token();
								TemplateMessageUtil.sendServiceMsg(token, member.getOpenId(), serviceMsg);
							}
						}
		
					}
					List<?> poststaffphone = auditBasicService.getPoststaffphone(nextpost, branchid);
					if (null != poststaffphone && !poststaffphone.isEmpty()) {
						for (int j = 0; j < poststaffphone.size(); j++) {
							String mobile = ((Map<?, ?>) poststaffphone.get(j)).get("MOBILE").toString();
							String staffname = ((Map<?, ?>) poststaffphone.get(j)).get("STAFFNAME").toString();
							String audittypename = CommonConstants.MessageTextDefinition.Repair;
							if (mobile.matches(RegExUtil.MOBILE)) {
//								Member membername = (Member) auditBasicService.findOneByProperties(Member.class, "memberId",
//										memberid, "status", "1");
								Staff staff2 = (Staff) auditBasicService.findById(Staff.class, memberid);
								String customername = staff2.getStaffName().toString();
								SmsSingleSender singleSender = new SmsSingleSender(SystemConstants.note.APPID,
										SystemConstants.note.APPKEY);
								ArrayList<String> params = new ArrayList<String>();
								params.add(staffname);
								params.add(roomid);
								params.add(customername);
								params.add(audittypename);
								params.add(auditremark);
								singleSender.sendWithParam(SystemConstants.note.COUNTRY, mobile, 47260, params, "", "", "");
							}

						}
					}
					if("*".equals(ra.getPost())){
						SmsSingleSender singleSender = new SmsSingleSender(SystemConstants.note.APPID, SystemConstants.note.APPKEY);
						ArrayList<String> params = new ArrayList<String>();
						params.add("审核通过");
						params.add(branchrank.getPhone());
						singleSender.sendWithParam(SystemConstants.note.COUNTRY,member.getMobile(), 67080, params, "", "", "");
					}
					JSONUtil.responseJSON(response, new AjaxResult(1, "审核成功"));
				} catch (DAOException e) {
					e.printStackTrace();
					JSONUtil.responseJSON(response, new AjaxResult(2, "审核失败"));
				}
			}
		}


		// 审核打回且记录审核日志表
		@RequestMapping("/updateAuditSubmitback.do")
		public void updateAuditSubmitback(HttpServletRequest request, HttpServletResponse response, String audittype,
				String operid, String branchId, String applystaff, String auditMessage) throws Exception {
			LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
			String branchid = loginUser.getStaff().getBranchId();
			if ("采购申请".equals(audittype)) {
				Purchase purchase = ((Purchase) (auditBasicService.findOneByProperties(Purchase.class, "purchaseId", operid)));
				purchase.setStatus("3");
				auditBasicService.save(purchase);
				String logId = this.auditBasicService.getSequence("SEQ_NEW_AUDITLOG", null);
				Date day = new Date();
				SimpleDateFormat df = new SimpleDateFormat("yyMMdd");
				String t = df.format(day);
				String datId = t + branchId + logId;
				AuditLog auditLog = new AuditLog();
				auditLog.setLogId(datId);
				auditLog.setOperId(operid);
				auditLog.setOperType("1");
				auditLog.setStatus("3");
				auditLog.setRecordUser(applystaff);
				auditLog.setBranchId(branchid);
				auditLog.setRemark(auditMessage);
				auditLog.setRecordTime(new Date());
				this.auditBasicService.saveAuditLog(auditLog);
				JSONUtil.responseJSON(response, new AjaxResult(1, null));
			} 

		}

		// 审核驳回且记录审核日志表
		@RequestMapping("/updateAuditSubmitFail.do")
		public void updateAuditSubmitFail(HttpServletRequest request, HttpServletResponse response, String audittype,
				String operid, String branchId, String applystaff, String auditMessage) throws Exception {
			LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
			Staff staff = loginUser.getStaff();
			String branchid = loginUser.getStaff().getBranchId();
			String aurecordUser = staff.getStaffId();
			int  priority = 1;
			auditMessage = auditMessage + "(驳回)";
			if ("采购申请".equals(audittype)) {
				Purchase purchase = ((Purchase) (auditBasicService.findOneByProperties(Purchase.class, "purchaseId", operid)));
				purchase.setStatus("4");
				auditBasicService.save(purchase);
				String logId = this.auditBasicService.getSequence("SEQ_NEW_AUDITLOG", null);
				Date day = new Date();
				SimpleDateFormat df = new SimpleDateFormat("yyMMdd");
				String t = df.format(day);
				String datId = t + branchId + logId;
				AuditLog auditLog = new AuditLog();
				auditLog.setLogId(datId);
				auditLog.setOperId(operid);
				auditLog.setBranchId(branchid);
				auditLog.setOperType("1");
				auditLog.setStatus("4");
				auditLog.setRecordUser(aurecordUser);
				auditLog.setBranchId(branchid);
				auditLog.setRemark(auditMessage);
				auditLog.setRecordTime(new Date());
				auditBasicService.save(auditLog);
				JSONUtil.responseJSON(response, new AjaxResult(1, null));
			} else if ("房价申请".equals(audittype)) {
				Branch branchName = ((Branch) (auditBasicService.findOneByProperties(Branch.class, "branchId", branchId)));
				String ip = branchName.getBranchIp().toString();
				String branchURL = "http://"+ip+":8080/ipms";
				PriceApply priceapply = ((PriceApply) (auditBasicService.findOneByProperties(PriceApply.class, "applyId", operid)));
				priceapply.setStatus("4");
				priceapply.setPost("");
				priceapply.setRecordUser(aurecordUser);
				priceapply.setAuditRemark("驳回");
				auditBasicService.save(priceapply);
				List<PriceApply> priceapplyList = new ArrayList<PriceApply>();
				priceapplyList.add(priceapply);
				Map<String, Object> priceapplyMap = new HashMap<String, Object>();
				priceapplyMap.put("PriceApply", priceapplyList);
				//TransferServcie.getInstance().transferData(priority, branchURL,JSONUtil.fromObject(priceapplyMap).toString());
				String logId = this.auditBasicService.getSequence("SEQ_NEW_AUDITLOG", null);
				Date day = new Date();
				SimpleDateFormat df = new SimpleDateFormat("yyMMdd");
				String t = df.format(day);
				String datId = t + branchId + logId;
				AuditLog auditLog = new AuditLog();
				auditLog.setLogId(datId);
				auditLog.setBranchId(branchid);
				auditLog.setOperId(operid);
				auditLog.setOperType("2");
				auditLog.setStatus("4");
				auditLog.setRecordUser(aurecordUser);
				auditLog.setRemark(auditMessage);
				auditLog.setRecordTime(new Date());
				auditBasicService.save(auditLog);
				String staffid = priceapply.getRecordUser().toString();
				Staff staffname = (Staff) auditBasicService.findOneByProperties(Staff.class, "staffId",staffid, "status", "1");
				String mobile = staffname.getMobile().toString();
				if (mobile.matches(RegExUtil.MOBILE)) {
					String customername = staffname.getStaffName().toString();
					SmsSingleSender singleSender = new SmsSingleSender(SystemConstants.note.APPID,
					SystemConstants.note.APPKEY);
					ArrayList<String> params = new ArrayList<String>();
					params.add(customername);
					params.add(auditMessage);
					singleSender.sendWithParam(SystemConstants.note.COUNTRY, mobile, 67319, params, "", "", "");
					}
				JSONUtil.responseJSON(response, new AjaxResult(1, null));
			} else if ("退房申请".equals(audittype)) {
				CheckOut checkOut = (CheckOut) auditBasicService.findOneByProperties(CheckOut.class, "checkOutId", operid);
				Branch branch = (Branch) this.auditBasicService.findOneByProperties(Branch.class, "branchId", checkOut.getBranchId());
				Member member = (Member) auditBasicService.findById(Member.class, checkOut.getMemberId());
				checkOut.setStatus("0");
				checkOut.setPost("");
				checkOut.setAuditRemark("驳回");
				auditBasicService.update(checkOut);
				String logId = this.auditBasicService.getSequence("SEQ_NEW_AUDITLOG", null);
				Date day = new Date();
				SimpleDateFormat df = new SimpleDateFormat("yyMMdd");
				String t = df.format(day);
				String datId = t + branchId + logId;
				AuditLog auditLog = new AuditLog();
				auditLog.setLogId(datId);
				auditLog.setOperId(operid);
				auditLog.setBranchId(branchid);
				auditLog.setOperType("3");
				auditLog.setStatus("4");
				auditLog.setRecordUser(aurecordUser);
				auditLog.setBranchId(branchid);
				auditLog.setRemark(auditMessage);
				auditLog.setRecordTime(new Date());
				auditBasicService.save(auditLog);
				commonService.Weixin(member, checkOut, "已被驳回",branch);
				String memberid = checkOut.getMemberId().toString();
				Member membername = (Member) auditBasicService.findOneByProperties(Member.class, "memberId",memberid, "status", "1");
				String mobile = membername.getMobile().toString();
				if (mobile.matches(RegExUtil.MOBILE)) {
					String customername = membername.getMemberName().toString();
					String audittypename = CommonConstants.MessageTextDefinition.CheckOut;
					SmsSingleSender singleSender = new SmsSingleSender(SystemConstants.note.APPID,
					SystemConstants.note.APPKEY);
					ArrayList<String> params = new ArrayList<String>();
					params.add(customername);
					params.add(audittypename);
					params.add(auditMessage);
					singleSender.sendWithParam(SystemConstants.note.COUNTRY, mobile, 67320, params, "", "", "");
						}

				
				JSONUtil.responseJSON(response, new AjaxResult(1, null));
			} else if ("维修申请".equals(audittype)) {// 另外记维修日志已删除记录
				RepairApply ra = (RepairApply) auditBasicService.findOneByProperties(RepairApply.class, "repairApplyId", operid);
				MaintenanceLog mlog = new MaintenanceLog();
				String recorduser = ((LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY))
						.getStaff().getStaffId();
				ra.setStatus("0");
				ra.setPost("");
				ra.setRecordTime(new Date());
				ra.setRecordUser(recorduser);
				ra.setAuditRemark("驳回");
				String logId = this.auditBasicService.getSequence("SEQ_NEW_AUDITLOG", null);
				Date day = new Date();
				SimpleDateFormat df = new SimpleDateFormat("yyMMdd");
				String t = df.format(day);
				String datId = t + branchId + logId;
				AuditLog auditLog = new AuditLog();
				auditLog.setLogId(datId);
				auditLog.setOperId(operid);
				auditLog.setBranchId(branchid);
				auditLog.setOperType("4");
				auditLog.setStatus("4");
				auditLog.setRecordUser(aurecordUser);
				auditLog.setBranchId(branchid);
				auditLog.setRemark(auditMessage);
				auditLog.setRecordTime(new Date());
				// String mlogId = DateUtil.currentDate("yyMMdd") + branchId
				// + this.AuditService.getSequence("SEQ_MAINTENANCELOG_ID");
				// mlog.setLogId(mlogId);
				// mlog.setBranchId(branchId);
				// mlog.setEquipment(ra.getEquipment());
				// mlog.setRecordUser(staff.getStaffId());
				// mlog.setRecordTime(new Date());
				// mlog.setStatus("0");
				// mlog.setRoomId(ra.getRoomId());
				// mlog.setApplicationSource("2");
				// mlog.setApplicationDate(ra.getApplicationDate());// 申请时间
				// mlog.setRepairTime(ra.getRepairTime());// 申请维修日期
				// mlog.setRepairApplyId(ra.getRepairApplyId());
				try {
					auditBasicService.update(ra);
					auditBasicService.save(auditLog);
					// this.AuditService.save(mlog);
					String memberid = ra.getReservedPerson().toString();
					Member membername = new Member();
					membername.setMemberId(memberid);
					Staff staff1 = (Staff) auditBasicService.findById(Staff.class, memberid);
					if(staff1 == null){
						Member membername1 = (Member) auditBasicService.findOneByProperties(Member.class, "memberId",memberid, "status", "1");
						membername.setMobile(membername1.getMobile());
						membername.setMemberName(membername1.getMemberName());
					}
					if(staff1 != null){
						membername.setMobile(staff1.getMobile());
						membername.setMemberName(staff1.getStaffName());
						
					}
					if (membername.getMobile().toString().matches(RegExUtil.MOBILE)) {
						String customername = membername.getMemberName().toString();
						String audittypename = CommonConstants.MessageTextDefinition.Repair;
						SmsSingleSender singleSender = new SmsSingleSender(SystemConstants.note.APPID,
						SystemConstants.note.APPKEY);
						ArrayList<String> params = new ArrayList<String>();
						params.add(customername);
						params.add(audittypename);
						params.add(auditMessage);
						singleSender.sendWithParam(SystemConstants.note.COUNTRY, membername.getMobile().toString(), 67320, params, "", "", "");
					}
					JSONUtil.responseJSON(response, new AjaxResult(1, "维修申请已驳回"));
				} catch (DAOException e) {
					e.printStackTrace();
					JSONUtil.responseJSON(response, new AjaxResult(2, "驳回失败"));
				}
			}
		}
}