package com.ideassoft.basic.controller;



import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ideassoft.basic.service.ApartmentPriceBasicService;
import com.ideassoft.bean.Branch;
import com.ideassoft.bean.PriceApply;
import com.ideassoft.bean.PriceApplyDetail;
import com.ideassoft.bean.PriceLog;
import com.ideassoft.bean.RoomPrice;
import com.ideassoft.bean.RoomPriceId;
import com.ideassoft.bean.Staff;
import com.ideassoft.bean.SysParam;
import com.ideassoft.core.ajax.AjaxResult;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.constants.CommonConstants;
import com.ideassoft.core.constants.SystemConstants;
import com.ideassoft.core.notifier.SmsSingleSender;
import com.ideassoft.price.AdjustPriceController;
import com.ideassoft.price.SinglePrice;
import com.ideassoft.priceRuleUtile.BasePriceUtile;
import com.ideassoft.util.DateUtil;
import com.ideassoft.util.JSONUtil;
import com.ideassoft.util.RegExUtil;
import com.ideassoft.util.RequestUtil;



@Transactional
@Controller
public class ApartmentPriceController {
	@Autowired
	private ApartmentPriceBasicService apartmentPriceBasicService;
	
	// 民宿房租价申请
	@RequestMapping("/apartmentBasicApply.do")
	public ModelAndView apartmentBasicApply(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		String branchId = request.getParameter("branchId");
		String branchName = request.getParameter("branchName");
		String roomType = request.getParameter("roomType");
		String roomName = request.getParameter("roomName");
		String roomPrice = request.getParameter("roomPrice");
		String rpType = request.getParameter("rpType");
		String theme = request.getParameter("theme");
		String priceType = request.getParameter("priceType");
		if ("公寓".equals(theme)) {
			theme = "2";
		} else if ("酒店".equals(theme)) {
			theme = "1";
		}
		if ("日租".equals(priceType)){
			priceType = "1";
		}else if ("时租".equals(priceType)){
			priceType = "2";
		}else if ("长租".equals(priceType)){
			priceType = "3";
		}
		mv.setViewName("page/basic/parameter/apartmentRpApply");
		mv.addObject("branchId",branchId);
		mv.addObject("branchName",branchName);
		mv.addObject("roomType",roomType);
		mv.addObject("roomName",roomName);
		mv.addObject("roomPrice",roomPrice);
		mv.addObject("rpType",rpType);
		mv.addObject("theme",theme);
		mv.addObject("priceType",priceType);
		return mv;
	}
	
	
	/**
	 * 查询规则，并加载到页面
	 * @param request
	 * @param response
	 */
	
	@RequestMapping("/queryApartdjustRules.do")
	public void queryApartdjustRules(HttpServletRequest request,HttpServletResponse response){
		List<?> result = apartmentPriceBasicService.findBySQL("querypr", true);
		JSONUtil.responseJSON(response,result);
	}
	
	@RequestMapping("/apartQueryApartdjustRules.do")
	public void apartQueryApartdjustRules(HttpServletRequest request,HttpServletResponse response){
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		
		List<?> result = apartmentPriceBasicService.findBySQL("queryprApart",true);
		JSONUtil.responseJSON(response,result);
	}
	/*
	 * 申请
	 */
	@RequestMapping("/checkApartPriceApplyTime.do")
	public void checkApartPriceApplyTime(HttpServletRequest request, HttpServletResponse response,String houseId,String validstart
			,String validend,String roomType){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        
		List<?> result =   apartmentPriceBasicService.findBySQL("checkApatPATime", new String []{validend,validstart,houseId,roomType},true);
		if(result.size() != 0){
			JSONUtil.responseJSON(response, new AjaxResult(1, null));
		}else{
			JSONUtil.responseJSON(response, new AjaxResult(2, null));
		}

	}
	
	// 房租价申请操作
	@RequestMapping("/rpApartOperate.do")
	public void rpApartOperate(HttpServletRequest request, HttpServletResponse response, String theme, String branchid,String validstart, String validend, String operate,
			String status, String remark) throws Exception {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		com.ideassoft.portal.DataDealPortalService service = new com.ideassoft.portal.DataDealPortalService();
		com.ideassoft.portal.IDataDealPortal portal = service.getDataDealPortalPort();
		Staff staff = loginUser.getStaff();
		String logId = this.apartmentPriceBasicService.getSequence("SEQ_NEW_PRICEAPPLY", null);
		Date day = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyMMdd");
		String t = df.format(day);
		String applyid = t + branchid + logId;
		String a = applyid;
		DateFormat dff = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
		Date validstartdata = null;
		if(validstart.equals("") == false){
			validstartdata = dff.parse(validstart);
		}
		Date validenddata = null;
		if(validend.equals("")==false){
			validenddata = dff.parse(validend);
		}
		String pastatus = null;
		Branch branch = ((Branch) (apartmentPriceBasicService.findOneByProperties(Branch.class, "branchId", branchid)));
		 String rpauditswitch = branch.getFlag() == null ? "" : branch.getFlag().toString();
		/*String rpauditswitch = portal.call(1, 1, "{ function: \"audit.RoompriceAuditswitch\", data:{branchid:"+branchid+" } }");*/
		String postone = null;
		String recorduser = staff.getStaffId().toString();
		SysParam atsysParam = ((SysParam) (apartmentPriceBasicService.findOneByProperties(SysParam.class, "paramType", "RPKIND",
				"content", CommonConstants.VolatilityPriceTypeDetail.THREEMONTH)));
		String applytypename = atsysParam.getParamName().toString();
		SysParam aksysParam = ((SysParam) (apartmentPriceBasicService.findOneByProperties(SysParam.class, "paramType",
				"RPSTATUS", "content", status)));
		String applykindname = aksysParam.getParamName().toString();
		Branch branchName = ((Branch) (apartmentPriceBasicService.findOneByProperties(Branch.class, "branchId", branchid)));
		String branchname = branchName.getBranchName().toString();
		Staff staffName = ((Staff) (apartmentPriceBasicService.findOneByProperties(Staff.class, "staffId", recorduser)));
		String staffname = staffName.getStaffName();
		String postcloud = null;
		String priceapplyremark = "";
		String Auditremark ="";
//				if(HeartBeatClient.heartbeat){
			if (operate.equals(CommonConstants.TakeEffect.Immediately)) {
				if (rpauditswitch.equals(CommonConstants.RpBranchSwitch.RbsOpen)) {
					   Auditremark = "待审核";
					   pastatus = CommonConstants.RpApplyStatus.RpAudit;
					   priceapplyremark = CommonConstants.RpApplyStatus.RpUse;
	                   postcloud = portal.call(1, 1, "{ function: \"audit.roompriceAuditpostphone\", data:{branchid:"+branchid+" } }");
	                   postone = portal.call(1, 1, "{ function: \"audit.roompriceAuditpost\", data:{branchid:"+branchid+" } }");
	                   JSONArray postcloudarray = null;
	                   if("-1".equals(postcloud)){
	                	   postcloudarray = new JSONArray();
	                   }else{
	                	   postcloudarray = new JSONArray(postcloud);
	                   }
					if(postcloudarray.length()>0){
						 for(int i=0;i<postcloudarray.length();i++){
							  JSONObject postcloudarraychild = postcloudarray.getJSONObject(i);
							  String mobile = postcloudarraychild.getString("MOBILE").toString();
							  if (mobile.matches(RegExUtil.MOBILE)) {
									SmsSingleSender singleSender = new SmsSingleSender(SystemConstants.note.APPID,SystemConstants.note.APPKEY);
									ArrayList<String> params = new ArrayList<String>();
									String auditremark = "待审核";
									params.add(postcloudarraychild.getString("STAFFNAME").toString());
									params.add(branchname);
									params.add(staffname);
									params.add(applytypename);
									params.add(applykindname);
									params.add(auditremark);
									singleSender.sendWithParam(SystemConstants.note.COUNTRY, mobile, 47258, params, "", "", "");
								}
						 }
						
					}
				} else if (rpauditswitch.equals(CommonConstants.RpBranchSwitch.RbsClose)) {
					 if(status.equals(CommonConstants.RoomPriceStatus.basic)){
						 pastatus = CommonConstants.RpApplyStatus.RpActive;
					 }else{
					     pastatus = CommonConstants.RpApplyStatus.RpUse;
					 }
				}else{
					 if(status.equals(CommonConstants.RoomPriceStatus.basic)){
						 pastatus = CommonConstants.RpApplyStatus.RpActive;
					 }else{
					     pastatus = CommonConstants.RpApplyStatus.RpUse;
					 }
				}
			} else {
				if (rpauditswitch.equals(CommonConstants.RpBranchSwitch.RbsOpen)) {
					       Auditremark = "待审核";
					       pastatus = CommonConstants.RpApplyStatus.RpAudit;
					       priceapplyremark = CommonConstants.RpApplyStatus.RpUnuse;
					       postcloud = portal.call(1, 1, "{ function: \"audit.roompriceAuditpostphone\", data:{branchid:"+branchid+" } }");
		                   postone = portal.call(1, 1, "{ function: \"audit.roompriceAuditpost\", data:{branchid:"+branchid+" } }");
						JSONArray postcloudarray = new JSONArray(postcloud);
						if(postcloudarray.length()>0){
							 for(int i=0;i<postcloudarray.length();i++){
								  JSONObject postcloudarraychild = postcloudarray.getJSONObject(i);
								  String mobile = postcloudarraychild.getString("MOBILE").toString();
								  if (mobile.matches(RegExUtil.MOBILE)) {
										SmsSingleSender singleSender = new SmsSingleSender(SystemConstants.note.APPID,SystemConstants.note.APPKEY);
										ArrayList<String> params = new ArrayList<String>();
										String auditremark = "待审核";
										params.add(postcloudarraychild.getString("MOBILE").toString());
										params.add(branchname);
										params.add(staffname);
										params.add(applytypename);
										params.add(applykindname);
										params.add(auditremark);
										singleSender.sendWithParam(SystemConstants.note.COUNTRY, mobile, 47258, params, "", "", "");
									}
							 }
							
						}
				} else if (rpauditswitch.equals(CommonConstants.RpBranchSwitch.RbsClose)) {
					pastatus = CommonConstants.RpApplyStatus.RpUnuse;
				} else {
					pastatus = CommonConstants.RpApplyStatus.RpUnuse;
				}
			}
			String premark = remark + priceapplyremark;
			//价格申请/调整表
			PriceApply priceapply = new PriceApply();
			priceapply.setApplyId(applyid);
			priceapply.setBranchId(branchid);
			priceapply.setApplyType(CommonConstants.VolatilityPriceTypeDetail.THREEMONTH);
			priceapply.setValidStart(validstartdata);
			priceapply.setValidEnd(validenddata);
			priceapply.setStatus(pastatus);
			priceapply.setApplyTime(new Date());
			priceapply.setRecordUser(staff.getStaffId());
			priceapply.setRemark(premark);
			priceapply.setPost(postone);
			priceapply.setAuditRemark(Auditremark);
			priceapply.setApplyKind(status);
			priceapply.setRecordTime(new Date());
			apartmentPriceBasicService.save(priceapply);
		/*}else{
			a = "本地网络未连接，请网络通畅后提交";
		}*/
		JSONUtil.responseJSON(response, new AjaxResult(1, a));
	}
	
	
	// 房租价申请保存申请数据
	@RequestMapping("/rpApartOperatedetail.do")
	public void rpApartOperatedetail(HttpServletRequest request, HttpServletResponse response, String theme, String branchid,
			String rpkind, String operate, String status, String applyid, String rptypename, String rprice, String pricetype,
			String validstart, String validend, String ruleid) throws Exception {
		DateFormat dff = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
		Date validstartdata = null;
		if(validstart.equals("") == false){
			validstartdata = dff.parse(validstart);
		}
		Date validenddata = null;
		if(validend.equals("")==false){
			validenddata = dff.parse(validend);
		}
		
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String recorduser = staff.getStaffId();
		List<?> rpsetup = apartmentPriceBasicService.getRpsetup();
		for (int i = 0; i < rpsetup.size(); i++) {
			String rpid = ((Map<?, ?>) rpsetup.get(i)).get("PARAMNAME").toString();
			String rpname = ((Map<?, ?>) rpsetup.get(i)).get("PARAMDESC").toString();
			String discount = ((Map<?, ?>) rpsetup.get(i)).get("DISCOUNT").toString();
			String memberrank = ((Map<?, ?>) rpsetup.get(i)).get("CONTENT").toString();
			Double d = new Double(discount) / 100;
			Double p = new Double(rprice);
			Format f = new DecimalFormat("0.00");
			Double price = Double.parseDouble(f.format(p * d));
			String detailid = this.apartmentPriceBasicService.getSequence("SEQ_NEW_PRICEAPPLYDETAIL", null);
			PriceApplyDetail priceapplydetail = new PriceApplyDetail();
			priceapplydetail.setDetailId(detailid);
			priceapplydetail.setApplyId(applyid);
			priceapplydetail.setContent(rpid);
			priceapplydetail.setRoomType(rptypename);
			priceapplydetail.setRoomPrice(price);
			priceapplydetail.setRecordTime(new Date());
			apartmentPriceBasicService.save(priceapplydetail);
			List<?> judgerpexit = apartmentPriceBasicService.getJudgerpexit(theme, branchid, rpid, rptypename, status, rpkind);
			PriceApply priceapplystatus = (PriceApply) apartmentPriceBasicService.findOneByProperties(PriceApply.class, "applyId",applyid);
			String statuspriceapply = priceapplystatus.getStatus().toString();
			if (null == judgerpexit || judgerpexit.size() == 0) {
				String rpstate =CommonConstants.RoomPriceState.unuse;
				if((status.equals(CommonConstants.RoomPriceStatus.basic))&&(statuspriceapply.equals(CommonConstants.RpApplyStatus.RpActive))){
					rpstate = CommonConstants.RoomPriceState.active;
				}
				RoomPrice rpriceapply = new RoomPrice();
				RoomPriceId roompriceid = new RoomPriceId();
				roompriceid.setBranchId(branchid);
				roompriceid.setRoomType(rptypename);
				roompriceid.setRpId(rpid);
				roompriceid.setRpKind(rpkind);
				roompriceid.setStatus(status);
				rpriceapply.setRoomPriceId(roompriceid);
				rpriceapply.setDiscount(d);
				rpriceapply.setMemberRank(memberrank);
				rpriceapply.setRecordUser(recorduser);
				rpriceapply.setRoomPrice(price);
				rpriceapply.setRpName(rpname);
				rpriceapply.setRpType("1");
				rpriceapply.setState(rpstate);
				rpriceapply.setTheme(theme);
				rpriceapply.setRecordTime(new Date());
				apartmentPriceBasicService.save(rpriceapply);
			}else{
				if((status.equals(CommonConstants.RoomPriceStatus.basic))&&(statuspriceapply.equals(CommonConstants.RpApplyStatus.RpActive))){
					RoomPrice basicroomorice = (RoomPrice) apartmentPriceBasicService.findOneByProperties(RoomPrice.class, "theme",theme, "roomPriceId.branchId", branchid, "roomPriceId.rpId", rpid, "roomPriceId.roomType", rptypename, "roomPriceId.rpKind", rpkind, "roomPriceId.status", status);
					RoomPriceId roompriceid = new RoomPriceId();
					roompriceid.setBranchId(branchid);
					roompriceid.setRoomType(rptypename);
					roompriceid.setRpId(rpid);
					roompriceid.setRpKind(rpkind);
					roompriceid.setStatus(status);
					basicroomorice.setRoomPriceId(roompriceid);
					basicroomorice.setDiscount(d);
					basicroomorice.setMemberRank(memberrank);
					basicroomorice.setRecordUser(recorduser);
					basicroomorice.setRoomPrice(price);
					basicroomorice.setRpName(rpname);
					basicroomorice.setRpType("1");
					basicroomorice.setState(CommonConstants.RoomPriceState.active);
					basicroomorice.setTheme(theme);
					basicroomorice.setRecordTime(new Date());
					apartmentPriceBasicService.update(basicroomorice);
				}
			}
		}
		//将价格存到新的表中
		Double lastprice = 0D;
		String priority = "";
		if (CommonConstants.RoomPriceStatus.basic.equals(status)) {
			lastprice = new SinglePrice(branchid, theme, rptypename, "MSJ", pricetype, new Date()).getBasicPrice();
			priority = "3";
			BasePriceUtile.updateAdjustPrice(branchid,rprice.toString(), pricetype, rptypename);
		} else if (CommonConstants.RoomPriceStatus.adjust.equals(status)) {
			if (StringUtils.isEmpty(ruleid)) {
				ruleid = "0";
			}
			lastprice = new SinglePrice(branchid, theme, rptypename, "MSJ", pricetype, new Date()).getVolatilePrice();
			priority = "2";
			AdjustPriceController.saveHotelPrice2newTable(branchid,validstartdata,validenddata,Double.valueOf(rprice),"",ruleid,theme,"MSJ",rptypename,pricetype);
		}
		String dataid = DateUtil.currentDate("yyMMdd") + branchid + apartmentPriceBasicService.getSequence("SEQ_PRICELOG_DATAID");
		PriceLog pl = new PriceLog(dataid, branchid, lastprice.toString(), 
				rprice.toString(), new Date(), recorduser, rptypename, pricetype, priority);
		apartmentPriceBasicService.save(pl);
		
		JSONUtil.responseJSON(response, new AjaxResult(1, null));
	}
	// 过滤日期 页面跳转
	/*@RequestMapping("/filterDay.do")
	public ModelAndView filterDay(HttpServletRequest request, HttpServletResponse response, String validstart,
			String validend) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/page/basic/housePrice/filterday");
		request.setAttribute("validstart", validstart);
		request.setAttribute("validend", validend);
		return mv;
	}
	
	
	 * 申请
	 
	@RequestMapping("/checkHousePriceApplyTime.do")
	public void checkHousePriceApplyTime(HttpServletRequest request, HttpServletResponse response,String houseId,String validstart
			,String validend){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

		List<?> result =   housePriceBasicService.findBySQL("checkPATime", new String []{validend,validstart,houseId},true);
		if(result.size() != 0){
			JSONUtil.responseJSON(response, new AjaxResult(1, null));
		}else{
			JSONUtil.responseJSON(response, new AjaxResult(2, null));
		}

	}
	
	// 民宿房租价申请提交
	
	 * rpType: rpType,
				initPrice : initPrice,
				validstart : validstart,
				validend : validend,
				filterday : filterday,
				validday : validday,
				houseId : houseId,
				remark : remark,
	 
	@SuppressWarnings("null")
	@RequestMapping("/addHousePriceApply.do")
	@ResponseBody
	public String addHousePriceApply(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String staffId = staff.getStaffId();
		String ruleid = request.getParameter("ruleid");
		if(ruleid == null || "".equals(ruleid)){
			ruleid = "0";
		}
		String houseId = request.getParameter("houseId");
		String rpType = request.getParameter("rpType");
		String initPrice = request.getParameter("initPrice");
		String validstart = request.getParameter("validstart");
		String validend = request.getParameter("validend");
		String filterday = request.getParameter("filterday");
		String validday = request.getParameter("validday");
		String remark = request.getParameter("remark");
		String houseType = request.getParameter("houseType");
		String operate = request.getParameter("operate");
		String applyId ="";
		if(rpType.equalsIgnoreCase("基准")){
			PriceApply priceApply = new PriceApply();
			applyId = new SimpleDateFormat("yyMMdd").format(new Date())+houseId+housePriceBasicService.getSequence("SEQ_NEW_PRICEAPPLY");
			priceApply.setApplyId(applyId);
			priceApply.setApplyKind("1");
			priceApply.setApplyTime(new Date());
			priceApply.setApplyType("4");
			priceApply.setBranchId(houseId);
			priceApply.setRecordTime(new Date());
			priceApply.setRecordUser(staffId);
			priceApply.setRemark(remark);
			if(operate.equals("N".trim())){
				priceApply.setStatus("3");
				
			}else{
				priceApply.setStatus("2");
			}
			housePriceBasicService.save(priceApply);
			PriceApplyDetail priceApplyDetail = new PriceApplyDetail();
			priceApplyDetail.setApplyId(applyId);
			if(initPrice!=null||!initPrice.equals("")){
				priceApplyDetail.setRoomPrice(new Double(initPrice));
			}
			priceApplyDetail.setContent("MSJ");
			priceApplyDetail.setDetailId(housePriceBasicService.getSequence("SEQ_NEW_PRICEAPPLYDETAIL"));
			priceApplyDetail.setRecordTime(new Date());
			priceApplyDetail.setRoomType(houseType);
			housePriceBasicService.save(priceApplyDetail);
			House house = (House)housePriceBasicService.findOneByProperties(House.class, "houseId", houseId);
			house.setInitprice(new Double(initPrice));
			housePriceBasicService.update(house);
			// 当民宿的基准价格所有调整的时候，需要将调整后状态为激活的价格存入到房价汇总表中
			BasePriceUtile.updateBasePrice(houseId, initPrice);
			return String.valueOf(SystemConstants.PortalResultCode.DONE);
		}else if(rpType.equalsIgnoreCase("调整")){
			PriceApply priceApply = new PriceApply();
			applyId = new SimpleDateFormat("yyMMdd").format(new Date())+houseId+housePriceBasicService.getSequence("SEQ_NEW_PRICEAPPLY");
			priceApply.setApplyId(applyId);
			priceApply.setApplyKind("2");
			priceApply.setApplyTime(new Date());
			priceApply.setApplyType("4");
			priceApply.setBranchId(houseId);
			priceApply.setRecordTime(new Date());
			priceApply.setRecordUser(staffId);
			priceApply.setRemark(remark);
			if(operate.equals("N".trim())){
				priceApply.setStatus("2");
			}else{
				priceApply.setStatus("0");
			}
			priceApply.setFilterDay(filterday);
			priceApply.setValidEnd(new SimpleDateFormat("yy/MM/dd HH:mm:ss").parse(validend));
			priceApply.setValidStart(new SimpleDateFormat("yy/MM/dd HH:mm:ss").parse(validstart));
			priceApply.setValidDay(validday);
			housePriceBasicService.save(priceApply);
			PriceApplyDetail priceApplyDetail = new PriceApplyDetail();
			priceApplyDetail.setApplyId(applyId);
			if(initPrice!=null||!initPrice.equals("")){
				priceApplyDetail.setRoomPrice(new Double(initPrice));
			}
			priceApplyDetail.setContent("MSJ");
			priceApplyDetail.setDetailId(housePriceBasicService.getSequence("SEQ_NEW_PRICEAPPLYDETAIL"));
			priceApplyDetail.setRecordTime(new Date());
			priceApplyDetail.setRoomType(houseType);
			housePriceBasicService.save(priceApplyDetail);
			
			
			//存数据
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date startDate = sdf.parse(validstart);
			Date endDate = sdf.parse(validend);
			
			AdjustPriceController.savePrice2newTable(houseId,startDate,endDate,new Double(initPrice),validday,ruleid);
			return String.valueOf(SystemConstants.PortalResultCode.DONE);
		}
		return null;
	}
	
	
	*//**
	 * 跳转到新增房价规则页面
	 * @param request 请求数据
	 * @param response 响应数据
	 *//*
	@RequestMapping("/addPriceRule.do")
	public ModelAndView addPriceRule(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("page/basic/housePrice/addPriceRule");
		return mv;
	}
	
	*//**
	 * 保存价格规则数据
	 * @param request 请求数据
	 * @param response 响应数据
	 *//*
	@RequestMapping("/savePriceRule.do")
	public void savePriceRule(HttpServletRequest request, HttpServletResponse response,String rulesName,String rulesPeriod,String rulesPeriodDetails,String rulesFilters,String rulesDesc) {
		// 如果成功保存则返回1
		try {
			// 创建PriceRules对象，将数据存入到此对象中
			PriceRules priceRules = new PriceRules();
			// 存入参数
			priceRules.setRulesDesc(rulesDesc);
			priceRules.setRulesFilters(rulesFilters);
			priceRules.setRulesId(housePriceBasicService.getSequence("SEQ_NEW_PRICERULES"));
			priceRules.setRulesName(rulesName);
			priceRules.setRulesPeriod(rulesPeriod);
			priceRules.setRulesPeriodDetails(rulesPeriodDetails);
			// 调用service层将数据存入对应表中
			housePriceBasicService.save(priceRules);
			JSONUtil.responseJSON(response, new AjaxResult(1, null));
		// 如果中间出现异常则返回-1
		} catch (Exception e) {
			JSONUtil.responseJSON(response, new AjaxResult(-1, null));
		}
	}*/
}
