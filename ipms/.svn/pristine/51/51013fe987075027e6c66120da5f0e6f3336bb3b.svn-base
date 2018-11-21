package com.ideassoft.basic.controller;



import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ideassoft.basic.service.HousePriceBasicService;
import com.ideassoft.bean.House;
import com.ideassoft.bean.PriceApply;
import com.ideassoft.bean.PriceApplyDetail;
import com.ideassoft.bean.PriceLog;
import com.ideassoft.bean.PriceRules;
import com.ideassoft.bean.PriceVolatility;
import com.ideassoft.bean.Staff;
import com.ideassoft.core.ajax.AjaxResult;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.constants.CommonConstants;
import com.ideassoft.core.constants.SystemConstants;
import com.ideassoft.price.AdjustPriceController;
import com.ideassoft.price.SinglePrice;
import com.ideassoft.priceRuleUtile.BasePriceUtile;
import com.ideassoft.util.DateUtil;
import com.ideassoft.util.JSONUtil;
import com.ideassoft.util.RequestUtil;



@Transactional
@Controller
public class HousePriceController {
	@Autowired
	private HousePriceBasicService housePriceBasicService;
	
	// 民宿房租价申请
	@RequestMapping("/houserpBasicApply.do")
	public ModelAndView houserpBasicApply(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		String branchId = request.getParameter("branchId");
		String branchName = request.getParameter("branchname");
		String houseType = request.getParameter("houseType");
		String houseNo = request.getParameter("houseNo");
		String roomPrice = request.getParameter("roomPrice");
		String rpType = request.getParameter("rpType");
		House house = (House)housePriceBasicService.findOneByProperties(House.class, "houseId", branchId);
/*			List<?> rppack = PmsmanageService.getRppack();
		List<?> rpstatus = PmsmanageService.getRpapplystatus();
		List<?> rpsetup = PmsmanageService.getRpsetup();*/
		mv.setViewName("page/basic/housePrice/houseRpApply");
		mv.addObject("houseId",branchId);
		mv.addObject("house",house);
		mv.addObject("branchName",branchName);
		mv.addObject("houseType",houseType);
		mv.addObject("houseNo",houseNo);
		mv.addObject("roomPrice",roomPrice);
		mv.addObject("rpType",rpType);
		return mv;
	}
	
	
	/**
	 * 查询规则，并加载到页面
	 * @param request
	 * @param response
	 */
	
	@RequestMapping("/queryAdjustRules.do")
	public void queryAdjustRules(HttpServletRequest request,HttpServletResponse response){
		 
		List<?> result = housePriceBasicService.findBySQL("querypr", true);
		
		
		JSONUtil.responseJSON(response,result);
	}
	
	// 过滤日期 页面跳转
	@RequestMapping("/filterDay.do")
	public ModelAndView filterDay(HttpServletRequest request, HttpServletResponse response, String validstart,
			String validend) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/page/basic/housePrice/filterday");
		request.setAttribute("validstart", validstart);
		request.setAttribute("validend", validend);
		return mv;
	}
	
	/*
	 * 申请
	 */
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
	/*
	 * rpType: rpType,
				initPrice : initPrice,
				validstart : validstart,
				validend : validend,
				filterday : filterday,
				validday : validday,
				houseId : houseId,
				remark : remark,
	 */
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
		Double lastprice = 0D;
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
			lastprice = new SinglePrice(houseId, "3", null, "MSJ", "1", new Date()).getBasicPrice();
			BasePriceUtile.updateBasePrice(houseId, initPrice);
			String dataid = DateUtil.currentDate("yyMMdd") + houseId + housePriceBasicService.getSequence("SEQ_PRICELOG_DATAID");
			PriceLog pl = new PriceLog(dataid, houseId, lastprice.toString(), 
					initPrice, new Date(), staffId, null, "1", "3");
			housePriceBasicService.save(pl);
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
			lastprice = new SinglePrice(houseId, "3", null, "MSJ", "1", new Date()).getVolatilePrice();
			AdjustPriceController.savePrice2newTable(houseId,startDate,endDate,new Double(initPrice),validday,ruleid);
			String dataid = DateUtil.currentDate("yyMMdd") + houseId + housePriceBasicService.getSequence("SEQ_PRICELOG_DATAID");
			PriceLog pl = new PriceLog(dataid, houseId, lastprice.toString(), 
					initPrice, new Date(), staffId, null, "1", "2");
			housePriceBasicService.save(pl);
			return String.valueOf(SystemConstants.PortalResultCode.DONE);
		}
		
		
		return null;
	}
	
	
	/**
	 * 跳转到新增房价规则页面
	 * @param request 请求数据
	 * @param response 响应数据
	 */
	@RequestMapping("/addPriceRule.do")
	public ModelAndView addPriceRule(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("page/basic/housePrice/addPriceRule");
		return mv;
	}
	
	/**
	 * 保存价格规则数据
	 * @param request 请求数据
	 * @param response 响应数据
	 */
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
			priceRules.setRulesBranchId(((LoginUser)request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY)).getStaff().getBranchId());
			// 调用service层将数据存入对应表中
			housePriceBasicService.save(priceRules);
			JSONUtil.responseJSON(response, new AjaxResult(1, null));
		// 如果中间出现异常则返回-1
		} catch (Exception e) {
			JSONUtil.responseJSON(response, new AjaxResult(-1, null));
		}
	}
	
	@RequestMapping("/delPrice.do")
	public void delPrice(HttpServletRequest request, HttpServletResponse response, String id){
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String staffId = staff.getStaffId();
		
		PriceVolatility pv = (PriceVolatility) housePriceBasicService.findOneByProperties(PriceVolatility.class, "volatilityId", id);
		housePriceBasicService.delete(pv);
		housePriceBasicService.getHibernateTemplate().flush();
		double price = new SinglePrice(pv.getBranchId(), pv.getTheme(), pv.getRoomType(), pv.getRpId(), pv.getPriceType(), new Date()).checkRoomPrice();
		String dataid = DateUtil.currentDate("yyMMdd") + pv.getBranchId() + housePriceBasicService.getSequence("SEQ_PRICELOG_DATAID");
		PriceLog pl = new PriceLog(dataid, pv.getBranchId(), "" + pv.getRoomPrice(), 
				"" + price, new Date(), staffId, pv.getRoomType(), pv.getPriceType(), "4");
		try{
			housePriceBasicService.save(pl);
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.SUCESS, "删除成功!"));
		}catch(Exception e){
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, "删除失败!"));
		}
	}
}
