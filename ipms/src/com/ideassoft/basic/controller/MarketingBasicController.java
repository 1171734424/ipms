package com.ideassoft.basic.controller;

import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hsqldb.lib.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ideassoft.basic.service.MarketingBasicService;
import com.ideassoft.bean.Branch;
import com.ideassoft.bean.Campaign;
import com.ideassoft.bean.CampaignRule;
import com.ideassoft.bean.CouponGroup;
import com.ideassoft.bean.House;
import com.ideassoft.bean.Integration;
import com.ideassoft.bean.Member;
import com.ideassoft.bean.MemberAccount;
import com.ideassoft.bean.PriceVolatility;
import com.ideassoft.bean.RoomType;
import com.ideassoft.bean.SysParam;
import com.ideassoft.core.ajax.AjaxResult;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.constants.CommonConstants;
import com.ideassoft.core.constants.SystemConstants;
import com.ideassoft.core.page.Pagination;
import com.ideassoft.crm.service.TransferServcie;
import com.ideassoft.util.JSONUtil;
import com.ideassoft.util.RequestUtil;

@Transactional
@Controller
public class MarketingBasicController {
	@Autowired
	private MarketingBasicService marketingBasicService;
	
	
	/**
	 * 打开积分兑换比例页面
	 * 
	 * @return
	 */
	@RequestMapping("/turnToExchangeScore.do")
	public ModelAndView turnToExchangeScore(HttpServletRequest request, HttpServletResponse response) {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Branch branch = (Branch) marketingBasicService.findById(Branch.class, loginUser.getStaff().getBranchId());
		ModelAndView mv = new ModelAndView();
		SysParam score = (SysParam) marketingBasicService.findOneByProperties(
				SysParam.class, "paramType", "SCORE");
		SysParam money = (SysParam) marketingBasicService.findOneByProperties(
				SysParam.class, "paramType", "MONEY");
		mv.addObject("score", null == score ? "" : score.getContent());
		mv.addObject("money", null == money ? "" : money.getContent());
		mv.addObject("branch", null == branch ? "0" : branch.getRank());
		mv.setViewName("page/basic/marketing/exchangeScore");
		return mv;
	}
	
	/**
	 * 修改积分兑换比例
	 * 
	 * @param request
	 * @param response
	 * @param score
	 * @param money
	 */
	@RequestMapping("/updateExchangeScoreRule.do")
	public void updateExchangeScoreRule(HttpServletRequest request,
			HttpServletResponse response, String score, String money) {
		SysParam Score = (SysParam) marketingBasicService.findOneByProperties(
				SysParam.class, "paramType", "SCORE");
		SysParam Money = (SysParam) marketingBasicService.findOneByProperties(
				SysParam.class, "paramType", "MONEY");
		String result;
		if (null == Score) {
			SysParam sysparam = new SysParam();
			sysparam.setParamId(marketingBasicService.getSequence("SEQ_NEW_PARAMID"));
			sysparam.setParamType("SCORE");
			sysparam.setParamName("积分");
			sysparam.setContent(score);
			sysparam.setStatus("1");
			marketingBasicService.saveIfo(sysparam);
			result = "{\"code\":\"1\"}";
		} else {
			Score.setContent(score);
			marketingBasicService.saveIfo(Score);
			result = "{\"code\":\"1\"}";
		}
		if (null == Money) {
			SysParam sysparam = new SysParam();
			sysparam.setParamId(marketingBasicService.getSequence("SEQ_NEW_PARAMID"));
			sysparam.setParamType("MONEY");
			sysparam.setParamName("金额");
			sysparam.setContent(money);
			sysparam.setStatus("1");
			marketingBasicService.saveIfo(sysparam);
			result = "{\"code\":\"1\"}";
		} else {
			Money.setContent(money);
			marketingBasicService.saveIfo(Money);
			result = "{\"code\":\"1\"}";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		List<SysParam> sysParam = new ArrayList<SysParam>();
		sysParam.add(Score);
		sysParam.add(Money);
		map.put("Campaign", sysParam);
		List<?> AllOfBranchList = marketingBasicService.findByProperties(Branch.class, "rank", "1","status","1");
		for(int i = 0; i < AllOfBranchList.size(); i++){
			String branchIpNew = ((Branch)(AllOfBranchList.get(i))).getBranchIp();
			if(branchIpNew != null && !StringUtil.isEmpty(branchIpNew)){
				String branchIpValue = "http://"+branchIpNew+":8080/ipms";
				//TransferServcie.getInstance().transferData(1, branchIpValue, JSONUtil.fromObject(map).toString());
			} else {
				continue;
			}
		}
		JSONUtil.responseJSON(response, result);
	}

	
	/**
	 * 积分调整页面
	 * 
	 * @param accountid
	 * @return
	 */
	@RequestMapping("/turnToScoreAdjustment.do")
	public ModelAndView turnToScoreAdjustment(String accountid) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("accountid", accountid);
		mv.setViewName("page/basic/marketing/scoreAdjustment");
		return mv;
	}
	
	
	/**
	 * 修改会员积分
	 * 
	 * @param request
	 * @param response
	 * @param integration
	 * @param accountid
	 * @throws UnknownHostException
	 */
	@RequestMapping("/changeIntegration.do")
	public void changeIntegration(HttpServletRequest request,
			HttpServletResponse response, Long integration, String accountid)
			throws UnknownHostException {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		MemberAccount account = (MemberAccount) marketingBasicService
				.getAccount(accountid);
		Member member = (Member) marketingBasicService.findById(Member.class, account
				.getMemberId());
		
		if (integration < 0) {
			if( (integration + account.getCurrIntegration()) < 0 ){
				JSONUtil.responseJSON(response, new AjaxResult(1, "扣除积分大于剩余积分!"));
			} else {
				account.setCurrIntegration(account.getCurrIntegration() + (integration));
				
				Integration inte = new Integration();
				SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
				String strdate = sdf.format(new Date());
				inte.setLogId(strdate + loginUser.getStaff().getBranchId() + marketingBasicService.getSequence("SEQ_NEW_INTEGRATION"));
				inte.setBranchId(loginUser.getStaff().getBranchId());
				inte.setIntegragion(Integer.parseInt(Long.toString(integration)));
				inte.setMemberId(account.getMemberId());
				inte.setRecordTime(new Date());
				
				String remark = member.getMemberName() + "积分调整:" + integration;
				marketingBasicService.changeIntegrationLog((LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY), remark,request);
				
				marketingBasicService.update(account);
				marketingBasicService.save(inte);
				JSONUtil.responseJSON(response, new AjaxResult(1, "修改成功!"));
			}
		} else {
			account.setCurrIntegration(account.getCurrIntegration() + (integration));
			
			Integration inte = new Integration();
			SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
			String strdate = sdf.format(new Date());
			inte.setLogId(strdate + loginUser.getStaff().getBranchId() + marketingBasicService.getSequence("SEQ_NEW_INTEGRATION"));
			inte.setBranchId(loginUser.getStaff().getBranchId());
			inte.setIntegragion(Integer.parseInt(Long.toString(integration)));
			inte.setMemberId(account.getMemberId());
			inte.setRecordTime(new Date());
			
			String remark = member.getMemberName() + "积分调整:" + integration;
			marketingBasicService.changeIntegrationLog((LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY), remark,request);
			
			marketingBasicService.update(account);
			marketingBasicService.save(inte);
			JSONUtil.responseJSON(response, new AjaxResult(1, "修改成功!"));
		}
	}
	
	/*
	 * 营销活动新增
	 * @author jsm
	 * 20180626 update
	 */
	@RequestMapping("/turnToCampaignsAddPage.do")
	public ModelAndView turnToCampaignsAddPage(HttpServletRequest request) throws Exception {
		String bigBranchType = "";
		String IsMarketCenter = "";
		List<?> businessList = new ArrayList<Object>();
		List<?> branchList = new ArrayList<Object>();
		ModelAndView mv = new ModelAndView();
		// 查找营销活动业务,查找所有的门店传递到页面上去,查找优惠券组合表
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Branch bigBranch = marketingBasicService.queryLoginUserBType(loginUser.getStaff().getBranchId());
		branchList = this.marketingBasicService.queryAllOfBranch();
		 
		if (bigBranch != null){
			bigBranchType = bigBranch.getRank();
		} 
		if(CommonConstants.SystemLevel.MarketCenter.equals(bigBranchType) || loginUser.getStaff().getStaffId().equals(SystemConstants.User.ADMIN_ID)){
			 businessList = this.marketingBasicService.queryAllOfBusiness();
			 IsMarketCenter = "IsMarketCenter";
		} else {
			 businessList = this.marketingBasicService.queryBranchOfBusiness();
			 SysParam UsingType = (SysParam)this.marketingBasicService.findOneByProperties(SysParam.class, "paramType", "EVENT_TYPE", "paramName", bigBranch.getBranchType());
			 if(UsingType != null){
				 mv.addObject("currentUsingType", UsingType);
			 }	 
		}
		mv.addObject("branchList", branchList);
		mv.addObject("IsMarketCenter", IsMarketCenter);
		mv.addObject("businessList", businessList);
		mv.setViewName("page/basic/marketing/addCampaigns");
		return mv;
	}
	
	
	// 多选营销活动使用对象
	@RequestMapping("/selectMultiUsingPerson.do")
	public ModelAndView selectMultiUsingPerson(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/dialog/customDialog");
		mv.addObject("dialogColumns", "memberRank,rankName");
		mv.addObject("dialogTarget", "MemberRank");
		mv.addObject("dialogConditions", null);
		mv.addObject("dialogQuickAdd", null);
		mv.addObject("selectType", "dialog");
		mv.addObject("colName", "usingPerson");
		return mv;
	}
	
	
	// 多选营销活动（酒店，公寓，民宿）
	@RequestMapping("/selectMultiUsingType.do")
	public ModelAndView selectMultiUsingType(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/dialog/customDialog");
		mv.addObject("dialogColumns", "paramName,paramDesc");
		mv.addObject("dialogTarget", "SysParam");
		mv.addObject("dialogConditions", "paramType = 'EVENT_TYPE'");
		mv.addObject("dialogQuickAdd", null);
		mv.addObject("selectType", "dialog-radio");
		mv.addObject("colName", "usingType");
		return mv;
	}
	
	
	// 各种营销活动,保存数据
	@RequestMapping("/campaignsAdd.do")
	public void campaignsAdd(HttpServletRequest request, HttpServletResponse response, Campaign campaign)
			throws Exception {
		//数据上传或下载到本地的数据库
		//Map<String, Object> map = new HashMap<String, Object>();
		List<?> currExistlist = new ArrayList<Object>();
		boolean currExist = false;
		boolean currExistForAdd = false;
		String intNumberInCam = "";
		String intNumber = "";
		boolean checkrepair = request.getParameter("checkflag") == null ||  request.getParameter("checkflag") == ""  ? true : false;
		String businessId = request.getParameter("businessId");
		String usingPerson = request.getParameter("usingPerson_CUSTOM_VALUE");
		String usingType_CUSTOM_VALUE = request.getParameter("usingType_CUSTOM_VALUE");
		String dataId = marketingBasicService.getCloudOrLocalSequence("SEQ_CAMPAIGNS_ID");
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String recordUser = loginUser.getStaff().getStaffId();
		SimpleDateFormat s = new SimpleDateFormat("yyMMdd");
		dataId = s.format(new Date()) + loginUser.getStaff().getBranchId() + dataId;
		campaign.setDataId(dataId);
		campaign.setRecordUser(recordUser);
		campaign.setUsingPerson(usingPerson);
		campaign.setUsingType(usingType_CUSTOM_VALUE);
		campaign.setRecordTime(new Date());
		campaign.setEffectiveTime(campaign.getStartTime());
		campaign.setStatus("1");
		// 修改的过程中：时间加载到前台时会改变形式，再次保存时作修改(没有了时分秒)
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		//String startTime = request.getParameter("realStartTime");
		//Date startDate = sdf.parse(startTime.toString());
		//String endTime = request.getParameter("realEndTime");
		//Date endDate = sdf.parse(endTime.toString());
		//campaign.setStartTime(startDate);
		//campaign.setEndTime(endDate);
		if ((CommonConstants.CampaignsType.CampaignSix).equals(businessId)) {
			String branchId = request.getParameter("branchId_CUSTOM_VALUE");
			campaign.setBranchId(branchId);
			String roomType = request.getParameter("roomType_CUSTOM_VALUE");
			campaign.setRoomType(roomType);
		}
		if ((CommonConstants.CampaignsType.CampaignEight).equals(businessId)) {
			String branchId = request.getParameter("branchId_CUSTOM_VALUE");
			campaign.setBranchId(branchId);
		}

	
		// save之前要先来数据库检重一遍
		// 将时间进行处理，endTime 加1天
		Calendar cal = Calendar.getInstance();
		cal.setTime(campaign.getEndTime());
		cal.add(Calendar.DATE, 1);

		if (!(CommonConstants.CampaignsType.CampaignThirteen).equals(businessId)&&
				!(CommonConstants.CampaignsType.CampaignEleven).equals(businessId)&&
				!(CommonConstants.CampaignsType.CampaignEight).equals(businessId) && 
				!(CommonConstants.CampaignsType.CampaignThree).equals(businessId) && 
				!(CommonConstants.CampaignsType.CampaignTwo).equals(businessId) && 
				!(CommonConstants.CampaignsType.CampaignSix).equals(businessId) &&
				!(CommonConstants.CampaignsType.CampaignSeven).equals(businessId)) {
			currExist = this.marketingBasicService.isCurrExist(businessId, sdf.format(campaign.getStartTime()), sdf.format(cal.getTime()), null, null);
		} else if ((CommonConstants.CampaignsType.CampaignTwo).equals(businessId) || (CommonConstants.CampaignsType.CampaignSeven).equals(businessId)) {
			int count = 0;
			String[] usindPersonArray = campaign.getUsingPerson().split(",");
			for (int i = 0; i < usindPersonArray.length; i++) {
				currExist = this.marketingBasicService.isCurrExist(businessId, sdf.format(campaign.getStartTime()), sdf.format(cal.getTime()),
						usindPersonArray[i], null);
				if (currExist) {
					count++;
				}
			}

			if (count != 0) {
				currExist = true;
			}

		} else if ((CommonConstants.CampaignsType.CampaignThree).equals(businessId)) {
				String[] usindPersonArray = campaign.getUsingPerson().split(",");
				List<?> allOfCampaignThree =  this.marketingBasicService.findCampaignIsControl(businessId, sdf.format(campaign.getStartTime()), sdf.format(cal.getTime()),null, null);
				if (allOfCampaignThree != null && allOfCampaignThree.size() > 0 ){
					for(int k = 0; k < allOfCampaignThree.size(); k++){						
						int count = 0;
						String usingPersonTemp = ((Map<?,?>)(allOfCampaignThree.get(k))).get("USING_PERSON").toString();
						for (int i = 0; i < usindPersonArray.length; i++) {
							if (usingPersonTemp.contains(usindPersonArray[i]) == true) {
								count++;
							}
						}
						String operMoneyTemp = ((Map<?,?>)(allOfCampaignThree.get(k))).get("OPER_MONEY").toString();
						if(operMoneyTemp.indexOf(".") != -1){
							intNumberInCam = operMoneyTemp.substring(0,operMoneyTemp.indexOf("."));
						} else {
							intNumberInCam = operMoneyTemp;
						}
						String currCampaign = campaign.getOperMoney().toString();
						if(currCampaign.indexOf(".") != -1){
							intNumber = currCampaign.substring(0,currCampaign.indexOf("."));
						} else {
							intNumber = currCampaign;
						}
						if (count == usindPersonArray.length && usindPersonArray.length == usingPersonTemp.split(",").length 
								&& !intNumberInCam.equals(intNumber)) {
							continue;
						} else if (count == 0 ){
							continue;
						} else {
							currExist = true;
						}	
					}	
				}
			} else if ((CommonConstants.CampaignsType.CampaignEight).equals(businessId)) {
			// 营销活动8
				String branchId = campaign.getBranchId();
				String roomId = campaign.getRoomId();
				currExistlist = this.marketingBasicService.isCampaignExistOfall(businessId, branchId, roomId, null, sdf.format(campaign.getStartTime()), sdf.format(cal.getTime()), null);
				String[] usindRangeArray = campaign.getUsingRange().split(",");
				if (currExistlist != null) {
					for(int k = 0; k < currExistlist.size(); k++){						
						int count = 0;
						String usingRangeTemp = ((Map<?,?>)(currExistlist.get(k))).get("USING_RANGE").toString();
						for (int i = 0; i < usindRangeArray.length; i++) {
							if (usingRangeTemp.contains(usindRangeArray[i]) == true) {
								count++;
							}
						}
						
						if (count == 0 ){
							continue;
						} else {
							currExistForAdd = true;
							break;
						}	
					}
				}
			} else if ((CommonConstants.CampaignsType.CampaignSix).equals(businessId)) {
			// 营销活动6
				String branchId = campaign.getBranchId();
				String roomType = campaign.getRoomType();
				if(campaign.getUsingType().equals(CommonConstants.SystemTheme.HOMESTAY)){
					currExistlist = this.marketingBasicService.isCampaignExistOfall(businessId, branchId, null, null, sdf.format(campaign.getStartTime()), sdf.format(cal.getTime()), null);
				} else {
					currExistlist = this.marketingBasicService.isCampaignExistOfall(businessId, branchId, null, roomType, sdf.format(campaign.getStartTime()), sdf.format(cal.getTime()), null);
				}
				String[] usindRangeArray = campaign.getUsingRange().split(",");
				if (currExistlist != null) {
					for(int k = 0; k < currExistlist.size(); k++){						
						int count = 0;
						String usingRangeTemp = ((Map<?,?>)(currExistlist.get(k))).get("USING_RANGE").toString();
						for (int i = 0; i < usindRangeArray.length; i++) {
							if (usingRangeTemp.contains(usindRangeArray[i]) == true) {
								count++;
							}
						}
						
						if (count == 0 ){
							continue;
						} else {
							currExistForAdd = true;
							break;
						}	
					}
				}
			} else if ((CommonConstants.CampaignsType.CampaignEleven).equals(businessId)) {
			// 营销活动11,住多少天送多少天
				String usingType = campaign.getUsingType();
				String liveDaycount = campaign.getRoomCount();
				currExistlist = this.marketingBasicService.isCampaignExistEleven(businessId, usingType, sdf.format(campaign.getStartTime()), sdf.format(cal.getTime()), liveDaycount, null);
				String[] usindRangeArray = campaign.getUsingRange().split(",");
				if (currExistlist != null) {
					for(int k = 0; k < currExistlist.size(); k++){						
						int count = 0;
						String usingRangeTemp = ((Map<?,?>)(currExistlist.get(k))).get("USING_RANGE").toString();
						for (int i = 0; i < usindRangeArray.length; i++) {
							if (usingRangeTemp.contains(usindRangeArray[i]) == true) {
								count++;
							}
						}
						
						if (count == 0 ){
							continue;
						} else {
							currExistForAdd = true;
							break;
						}	
					}
				}
			} else if ((CommonConstants.CampaignsType.CampaignThirteen).equals(businessId)) {
				// 营销活动13,住即送X元
				String usingTypes = campaign.getUsingType();
				currExistlist = this.marketingBasicService.isCampaignExistEleven(businessId, usingTypes, sdf.format(campaign.getStartTime()), sdf.format(cal.getTime()), null, null);
				String[] usindRangeArray = campaign.getUsingRange().split(",");
				if (currExistlist != null) {
					for(int k = 0; k < currExistlist.size(); k++){						
						int count = 0;
						String usingRangeTemp = ((Map<?,?>)(currExistlist.get(k))).get("USING_RANGE").toString();
						for (int i = 0; i < usindRangeArray.length; i++) {
							if (usingRangeTemp.contains(usindRangeArray[i]) == true) {
								count++;
							}
						}
						
						if (count == 0 ){
							continue;
						} else {
							currExistForAdd = true;
							break;
						}	
					}
				}
			}else {
		
			}

		if (currExist && checkrepair) {
			JSONUtil.responseJSON(response, new AjaxResult(Integer.parseInt("2"), null));
		} else if (currExistForAdd && checkrepair) {
			JSONUtil.responseJSON(response, new AjaxResult(Integer.parseInt("3"), null));
		} else {
			this.marketingBasicService.save(campaign);
			marketingBasicService.campaignLog((LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY), campaign, "1",request);
			
			// 如果是活动折上折，需向活动副表记录数据
			if ((CommonConstants.CampaignsType.CampaignFifth).equals(businessId)) {
				String liveDay = request.getParameter("liveDay");
				List<CampaignRule> camRuleList = new ArrayList();
				for (int j = 0; j < Integer.parseInt(liveDay); j++) {
					CampaignRule camRule = new CampaignRule();
					String camRuleDataId = marketingBasicService.getCloudOrLocalSequence("SEQ_CAMPAIGNRULE_ID");
					String discountTemp = request.getParameter("dayCount" + String.valueOf((j + 1)));
					camRule.setCampaignId(dataId);
					camRule.setDataId(camRuleDataId);
					camRule.setLiveDay(liveDay);
					camRule.setCurrentDay(String.valueOf(j + 1));
					camRule.setDayCount(discountTemp);
					camRule.setStatus("1");
					camRule.setRecordUser(recordUser);
					this.marketingBasicService.save(camRule);
					camRuleList.add(camRule);
				}
				//map.put("CampaignRule", camRuleList);
			} else if ((CommonConstants.CampaignsType.CampaignEight).equals(businessId)){
				//营销活动8，要向价格规则表写活动价
				PriceVolatility pv = new PriceVolatility();
			}
			

			/*List<Campaign> CampaignList = new ArrayList();
			CampaignList.add(campaign);
			map.put("Campaign", CampaignList);
			if ((CommonConstants.CampaignsType.CampaignThree).equals(businessId) || 
				(CommonConstants.CampaignsType.CampaignTwo).equals(businessId) || 
				(CommonConstants.CampaignsType.CampaignSeven).equals(businessId)) {
				CouponGroup campaignGroup = (CouponGroup)marketingBasicService.findOneByProperties(CouponGroup.class, "dataId", campaign.getCouponGroupId());
				if(campaignGroup != null){
					List<CouponGroup> campaignGroupList = new ArrayList();
					campaignGroupList.add(campaignGroup);
					map.put("campaignGroup", campaignGroupList);
				}	
			}
			Branch loginBranch = (Branch)marketingBasicService.findOneByProperties(Branch.class, "branchId", loginUser.getStaff().getBranchId());
			marketingBasicService.dataUpDownForCampaign(loginUser.getStaff().getBranchId(), 1, loginBranch.getBranchIp(), "campaignAdd", map, campaign);
			*/JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.SUCESS, null));
		}
	}
	
	
	// 优惠券选择页面
	@RequestMapping("/selectcouponGroup.do")
	public ModelAndView selectcouponGroup(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/basic/marketing/couponGroupSelect");
		return mv;
	}
	
	// 另一种营销活动的查询方式
	@RequestMapping("/selectcouponGroupAnother.do")
	public ModelAndView selectcouponGroupAnother(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/basic/marketing/couponGroupSelectAnother");
		return mv;
	}
	
	
	// 选择归属门店
	@RequestMapping("/selectBranchId.do")
	public ModelAndView selectBranchId(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/basic/marketing/selectBranchId");
		return mv;
	}
	
	
	// 选择特惠房型
	@RequestMapping("/selectRoomType.do")
	public ModelAndView selectRoomType(HttpServletRequest request, HttpServletResponse response,String branchId) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/dialog/customDialog");
		mv.addObject("dialogColumns", "roomTypeKey.roomType,roomName");
		mv.addObject("dialogTarget", "RoomType");
		mv.addObject("dialogConditions", "roomTypeKey.branchId='"+branchId+"'");
		mv.addObject("dialogQuickAdd", null);
		mv.addObject("selectType", "dialog-radio");
		mv.addObject("colName", "roomType");
		return mv;
	}
	
	// 选择特惠房型
	@RequestMapping("/selectBranchRoomId.do")
	public ModelAndView selectBranchRoomId(HttpServletRequest request, HttpServletResponse response,String branchId) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/dialog/customDialog");
		mv.addObject("dialogColumns", "roomKey.roomId,roomKey.roomId");
		mv.addObject("dialogTarget", "Room");
		mv.addObject("dialogConditions", "roomKey.branchId='"+branchId+"'");
		mv.addObject("dialogQuickAdd", null);
		mv.addObject("selectType", "dialog-radio");
		mv.addObject("colName", "roomId");
		return mv;
	}
	
	
	// 营销活动修改跳页面
	@RequestMapping("/turnAlterCampaignPage.do")
	public ModelAndView turnAlterCampaignPage(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String bigBranchType = "1";
		String IsMarketCenter = "";
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/basic/marketing/campaignAlter");
		String campaignId = request.getParameter("campaignId");
		Campaign campaignInfo = (Campaign) this.marketingBasicService.findOneByProperties(Campaign.class, "dataId",
				campaignId);
		/*
		 * //对开始时间和结束时间进行相应的处理 Date startTime = campaignInfo.getStartTime();
		 * Date endTime = campaignInfo.getEndTime(); SimpleDateFormat sdf = new
		 * SimpleDateFormat("yyyy/MM/dd HH:mm"); String startTimeStr =
		 * sdf.format(startTime); String endTimeStr = sdf.format(endTime);
		 * campaignInfo.setStartTime(sdf.parse(startTimeStr));
		 * campaignInfo.setEndTime(sdf.parse(endTimeStr));
		 */
		mv.addObject("campaignInfo", campaignInfo);
		// 部分数据在campaignInfo，没有翻译成中文，则另外查传递到页面去
		List<?> usingPersonAndType = this.marketingBasicService.queryUsingPersonAndType(campaignId);
		JSONObject PersonAndType = JSONUtil.fromObject(usingPersonAndType.get(0));
		mv.addObject("usingPersonAndType", PersonAndType);
		// 查找所有的门店传递到页面上去加载到页面上
		List<?> branchList = this.marketingBasicService.queryAllOfBranch();
		mv.addObject("branchList", branchList);
		// 查找营销活动业务
		List<?> businessList = this.marketingBasicService.queryAllOfBusiness();
		mv.addObject("businessList", businessList);
		// 如果是营销活动折上折就要求campaignrule表里查找规则加载到页面5
		List<?> campaignRuleList = this.marketingBasicService.findByProperties(CampaignRule.class, "campaignId", campaignInfo
				.getDataId(), "status", "1");
		JSONArray campaignRules = JSONUtil.fromObject(campaignRuleList);
		mv.addObject("campaignRuleList", campaignRules);
		// 营销活动是推荐有礼活动时，要查询优惠劵组合表
		List<?> couponList = this.marketingBasicService.findByProperties(CouponGroup.class, "dataId", campaignInfo
				.getCouponGroupId());
		JSONArray couponLists = JSONUtil.fromObject(couponList);
		mv.addObject("couponList", couponLists);
		// 营销活动6和8查询其数据库里的归属部门，和房型
		if(campaignInfo.getBusinessId().equals(CommonConstants.CampaignsType.CampaignSix)||campaignInfo.getBusinessId().equals(CommonConstants.CampaignsType.CampaignEight)){
			if(!campaignInfo.getBranchId().contains("H")){
				//酒店公寓
				List<?> cambranchList = this.marketingBasicService.findByProperties(Branch.class, "branchId", campaignInfo
						.getBranchId());
				JSONArray branchLists = JSONUtil.fromObject(cambranchList);
				mv.addObject("branchLists", branchLists);
				List<?> camroomList = this.marketingBasicService.findByProperties(RoomType.class, "roomTypeKey.roomType", campaignInfo
						.getRoomType());
				JSONArray camroomLists = JSONUtil.fromObject(camroomList);
				mv.addObject("camroomLists", camroomLists);
				
			}else{
				//民宿
				List<?> cambranchList = this.marketingBasicService.findHousebyHouseId(campaignInfo.getBranchId());
				JSONArray branchLists = JSONUtil.fromObject(cambranchList);
				mv.addObject("branchLists", branchLists);
				
			}
		}
		
		
		
		//将当前门店的rank传递到前台去 
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Branch branch = (Branch) marketingBasicService.findById(Branch.class, loginUser.getStaff().getBranchId());
		mv.addObject("branchRank", branch);
		Branch bigBranch = marketingBasicService.queryLoginUserBType(loginUser.getStaff().getBranchId());
		if (bigBranch != null){
			bigBranchType = bigBranch.getRank();
		} 
		if(CommonConstants.SystemLevel.MarketCenter.equals(bigBranchType) || loginUser.getStaff().getStaffId().equals(SystemConstants.User.ADMIN_ID)){
			 businessList = this.marketingBasicService.queryAllOfBusiness();
			 IsMarketCenter = "IsMarketCenter";
		}
		mv.addObject("IsMarketCenter", IsMarketCenter);
		return mv;
	}
	
	// 营销活动修改保存数据
	@RequestMapping("/campaignsAlter.do")
	public void campaignsAlter(HttpServletRequest request, HttpServletResponse response, Campaign campaign)
			throws Exception {
		//数据上传或下载到本地的数据库
		boolean currExist = false;
		boolean currExistForAdd = false;
		List<?> currExistlist = new ArrayList<Object>();
		String intNumberInCam = "";
		String intNumber = "";
		boolean checkrepair = request.getParameter("checkflag") == null ||  request.getParameter("checkflag") == ""  ? true : false;
		//Map<String, Object> map = new HashMap<String, Object>();
		String businessId = request.getParameter("businessId");
		String usingPerson = request.getParameter("usingPerson_CUSTOM_VALUE");
		String usingType = request.getParameter("usingType_CUSTOM_VALUE");
		String dataId = request.getParameter("dataId");
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String recordUser = loginUser.getStaff().getStaffId();
		// campaign.setDataId(dataId);
		campaign.setRecordUser(recordUser);
		campaign.setUsingPerson(usingPerson);
		campaign.setUsingType(usingType);
		campaign.setStatus("1");
		campaign.setRecordTime(new Date());
		// 修改的过程中：时间加载到前台时会改变形式，再次保存时作修改(没有了时分秒)
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		String startTime = request.getParameter("realStartTime");
		Date startDate = sdf.parse(startTime.toString());
		String endTime = request.getParameter("realEndTime");
		Date endDate = sdf.parse(endTime.toString());
		String effectTime = request.getParameter("realEffectTime");
		//Date effectDate = sdf.parse(effectTime.toString());
		campaign.setStartTime(startDate);
		campaign.setEndTime(endDate);
		campaign.setEffectiveTime(startDate);
		if ((CommonConstants.CampaignsType.CampaignSix).equals(businessId)) {
			String branchId = request.getParameter("branchId_CUSTOM_VALUE");
			campaign.setBranchId(branchId);
			String roomType = request.getParameter("roomType_CUSTOM_VALUE");
			campaign.setRoomType(roomType);
		}
		if ((CommonConstants.CampaignsType.CampaignEight).equals(businessId)) {
			String branchId = request.getParameter("branchId_CUSTOM_VALUE");
			campaign.setBranchId(branchId);
		}
		
		// save之前要先来数据库检重一遍
		// 将时间进行处理，endTime 加1天
		Calendar cal = Calendar.getInstance();
		cal.setTime(campaign.getEndTime());
		cal.add(Calendar.DATE, 1);

		if (!(CommonConstants.CampaignsType.CampaignThirteen).equals(businessId) &&
				!(CommonConstants.CampaignsType.CampaignEleven).equals(businessId)&&
				!(CommonConstants.CampaignsType.CampaignEight).equals(businessId) &&
				!(CommonConstants.CampaignsType.CampaignThree).equals(businessId) && 
				!(CommonConstants.CampaignsType.CampaignTwo).equals(businessId) && 
				!(CommonConstants.CampaignsType.CampaignSix).equals(businessId) &&
				!(CommonConstants.CampaignsType.CampaignSeven).equals(businessId)) {
			currExist = this.marketingBasicService.isCurrExist(businessId, sdf.format(campaign.getStartTime()), sdf.format(cal.getTime()), null, campaign.getDataId());
		} else if ((CommonConstants.CampaignsType.CampaignTwo).equals(businessId) || (CommonConstants.CampaignsType.CampaignSeven).equals(businessId)) {
			int count = 0;
			String[] usindPersonArray = campaign.getUsingPerson().split(",");
			for (int i = 0; i < usindPersonArray.length; i++) {
				currExist = this.marketingBasicService.isCurrExist(businessId, sdf.format(campaign.getStartTime()), sdf.format(cal.getTime()), usindPersonArray[i], campaign.getDataId());
				if (currExist) {
					count++;
				}
			}

			if (count != 0) {
				currExist = true;
			}

		} else if ((CommonConstants.CampaignsType.CampaignThree).equals(businessId)) {
				String[] usindPersonArray = campaign.getUsingPerson().split(",");
				List<?> allOfCampaignThree =  this.marketingBasicService.findCampaignIsControl(businessId, sdf.format(campaign.getStartTime()), sdf.format(cal.getTime()), null, campaign.getDataId());
				if (allOfCampaignThree != null && allOfCampaignThree.size() > 0 ){
					for(int k = 0; k < allOfCampaignThree.size(); k++){						
						int count = 0;
						String usingPersonTemp = ((Map<?,?>)(allOfCampaignThree.get(k))).get("USING_PERSON").toString();
						for (int i = 0; i < usindPersonArray.length; i++) {
							if (usingPersonTemp.contains(usindPersonArray[i]) == true) {
								count++;
							}
						}
						String operMoneyTemp = ((Map<?,?>)(allOfCampaignThree.get(k))).get("OPER_MONEY").toString();
						if(operMoneyTemp.indexOf(".") != -1){
							intNumberInCam = operMoneyTemp.substring(0,operMoneyTemp.indexOf("."));
						} else {
							intNumberInCam = operMoneyTemp;
						}
						String currCampaign = campaign.getOperMoney().toString();
						if(currCampaign.indexOf(".") != -1){
							intNumber = currCampaign.substring(0,currCampaign.indexOf("."));
						} else {
							intNumber = currCampaign;
						}
						if (count == usindPersonArray.length && usindPersonArray.length == usingPersonTemp.split(",").length 
								&& !intNumberInCam.equals(intNumber)) {
							continue;
						} else if (count == 0 ){
							continue;
						} else {
							currExist = true;
						}	
					}	
				}
			} else if ((CommonConstants.CampaignsType.CampaignEight).equals(businessId)) {
				// 营销活动8
					String branchId = campaign.getBranchId();
					String roomId = campaign.getRoomId();
					currExistlist = this.marketingBasicService.isCampaignExistOfall(businessId, branchId, roomId, null, sdf.format(campaign.getStartTime()), sdf.format(cal.getTime()), campaign.getDataId());
					String[] usindRangeArray = campaign.getUsingRange().split(",");
					if (currExistlist != null) {
						for(int k = 0; k < currExistlist.size(); k++){						
							int count = 0;
							String usingRangeTemp = ((Map<?,?>)(currExistlist.get(k))).get("USING_RANGE").toString();
							for (int i = 0; i < usindRangeArray.length; i++) {
								if (usingRangeTemp.contains(usindRangeArray[i]) == true) {
									count++;
								}
							}
							
							if (count == 0 ){
								continue;
							} else {
								currExistForAdd = true;
								break;
							}	
						}
					}
			} else if ((CommonConstants.CampaignsType.CampaignSix).equals(businessId)) {
			// 营销活动6
				String branchId = campaign.getBranchId();
				String roomType = campaign.getRoomType();
				if(campaign.getUsingType().equals(CommonConstants.SystemTheme.HOMESTAY)){
					currExistlist = this.marketingBasicService.isCampaignExistOfall(businessId, branchId, null, null, sdf.format(campaign.getStartTime()), sdf.format(cal.getTime()), null);
				} else {
					currExistlist = this.marketingBasicService.isCampaignExistOfall(businessId, branchId, null, roomType, sdf.format(campaign.getStartTime()), sdf.format(cal.getTime()), null);
				}
				String[] usindRangeArray = campaign.getUsingRange().split(",");
				if (currExistlist != null) {
					for(int k = 0; k < currExistlist.size(); k++){						
						int count = 0;
						String usingRangeTemp = ((Map<?,?>)(currExistlist.get(k))).get("USING_RANGE").toString();
						for (int i = 0; i < usindRangeArray.length; i++) {
							if (usingRangeTemp.contains(usindRangeArray[i]) == true) {
								count++;
							}
						}
						
						if (count == 0 ){
							continue;
						} else {
							currExistForAdd = true;
							break;
						}	
					}
				}
			} else if ((CommonConstants.CampaignsType.CampaignEleven).equals(businessId)) {
				// 营销活动11,住多少天送多少天
					String usingTypes = campaign.getUsingType();
					String liveDaycount = campaign.getRoomCount();
					currExistlist = this.marketingBasicService.isCampaignExistEleven(businessId, usingTypes, sdf.format(campaign.getStartTime()), sdf.format(cal.getTime()), liveDaycount, campaign.getDataId());
					String[] usindRangeArray = campaign.getUsingRange().split(",");
					if (currExistlist != null) {
						for(int k = 0; k < currExistlist.size(); k++){						
							int count = 0;
							String usingRangeTemp = ((Map<?,?>)(currExistlist.get(k))).get("USING_RANGE").toString();
							for (int i = 0; i < usindRangeArray.length; i++) {
								if (usingRangeTemp.contains(usindRangeArray[i]) == true) {
									count++;
								}
							}
							
							if (count == 0 ){
								continue;
							} else {
								currExistForAdd = true;
								break;
							}	
						}
					}
				} else if ((CommonConstants.CampaignsType.CampaignThirteen).equals(businessId)) {
				// 营销活动13,住即送X元
					String usingTypes = campaign.getUsingType();
					currExistlist = this.marketingBasicService.isCampaignExistEleven(businessId, usingTypes, sdf.format(campaign.getStartTime()), sdf.format(cal.getTime()), null, campaign.getDataId());
					String[] usindRangeArray = campaign.getUsingRange().split(",");
					if (currExistlist != null) {
						for(int k = 0; k < currExistlist.size(); k++){						
							int count = 0;
							String usingRangeTemp = ((Map<?,?>)(currExistlist.get(k))).get("USING_RANGE").toString();
							for (int i = 0; i < usindRangeArray.length; i++) {
								if (usingRangeTemp.contains(usindRangeArray[i]) == true) {
									count++;
								}
							}
							
							if (count == 0 ){
								continue;
							} else {
								currExistForAdd = true;
								break;
							}	
						}
					}
				}
			else {
			
			}
		
		if (currExist && checkrepair) {
			JSONUtil.responseJSON(response, new AjaxResult(Integer.parseInt("2"), null));
		} else if (currExistForAdd && checkrepair) {
			JSONUtil.responseJSON(response, new AjaxResult(Integer.parseInt("3"), null));
		} else {
			this.marketingBasicService.update(campaign);
			marketingBasicService.campaignLog((LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY), campaign, "3",request);
			/*List<Campaign> CampaignList = new ArrayList();
			CampaignList.add(campaign);
			map.put("Campaign", CampaignList);
			//将此营销活动相关的优惠券组合的数据下传
			List<CouponGroup> ConponGroupList = new ArrayList();
			CouponGroup oneCouponGroup = (CouponGroup)this.marketingBasicService.findOneByProperties(CouponGroup.class, "dataId", campaign.getCouponGroupId());
			ConponGroupList.add(oneCouponGroup);
			map.put("CouponGroup", ConponGroupList);*/
	
			if ((CommonConstants.CampaignsType.CampaignFifth).equals(businessId)) {
				// 根据活动的campaignId找到辅表将相应的数据删除
				this.marketingBasicService.deleteCampaignRules(dataId);
				// 新增该活动的活动规则表
				String liveDay = request.getParameter("liveDay");
				List<CampaignRule> camRuleList = new ArrayList();
				for (int j = 0; j < Integer.parseInt(liveDay); j++) {
					CampaignRule camRule = new CampaignRule();
					String camRuleDataId = marketingBasicService.getCloudOrLocalSequence("SEQ_CAMPAIGNRULE_ID");
					String discountTemp = request.getParameter("dayCount" + String.valueOf((j + 1)));
					camRule.setCampaignId(dataId);
					camRule.setDataId(camRuleDataId);
					camRule.setLiveDay(liveDay);
					camRule.setCurrentDay(String.valueOf(j + 1));
					camRule.setDayCount(discountTemp);
					camRule.setStatus("1");
					camRule.setRecordUser(recordUser);
					this.marketingBasicService.save(camRule);
					//camRuleList.add(camRule);
				}
				//map.put("CampaignRule", camRuleList);
			}
			//Branch loginBranch = (Branch)marketingBasicService.findOneByProperties(Branch.class, "branchId", loginUser.getStaff().getBranchId());
			//marketingBasicService.dataUpDownForCampaign(loginUser.getStaff().getBranchId(),1, loginBranch.getBranchIp(), "campaignAlter", map, campaign);
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.SUCESS, null));
		}
	}

	// 删除当前营销活动的记录（改为逻辑删除改状态为0）
	@RequestMapping("/campaignsDelete.do")
	public void campaignsDelete(HttpServletRequest request, HttpServletResponse response, Campaign campaign)
			throws Exception {
		String businessId = request.getParameter("businessId");
		String dataId = request.getParameter("dataId");
		//campaign.setStatus("0");
		//this.campaignService.update(campaign);
		marketingBasicService.updateCampaignStatus(dataId);
		marketingBasicService.campaignLog((LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY), campaign, "2",request);
		
		if (businessId.equals((CommonConstants.CampaignsType.CampaignFifth))) {
			//// 根据活动的campaignId找到辅表将相应的数据删除。 删除是逻辑删除
			this.marketingBasicService.deleteCampaignRules(dataId);
		}
		JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.SUCESS, null));
	}
	
	//选择民宿，暂用所有民宿
	@RequestMapping("/selectHouse.do")
	public ModelAndView selectHouse(HttpServletRequest request, HttpServletResponse response) {
	/*	ModelAndView mv = new ModelAndView();
		mv.setViewName("page/dialog/customDialog");
		mv.addObject("dialogColumns", "houseId,houseName");
		mv.addObject("dialogTarget", "House");
		mv.addObject("dialogConditions", "status != '0'");
		mv.addObject("dialogQuickAdd", null);
		mv.addObject("selectType", "dialog-radio");
		mv.addObject("colName", "branchId");
		return mv;*/
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/basic/marketing/selectHouseId");
		return mv;
	}
	
	
	// 选择特惠房间，民宿
	@RequestMapping("/selectRoomIdTypeInHouse.do")
	public ModelAndView selectRoomIdTypeInHouse(HttpServletRequest request, HttpServletResponse response,String branchId) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/dialog/customDialog");
		mv.addObject("dialogColumns", "houseId,houseName");
		mv.addObject("dialogTarget", "House");
		mv.addObject("dialogConditions", "status != '0'");
		mv.addObject("dialogQuickAdd", null);
		mv.addObject("selectType", "dialog-radio");
		mv.addObject("colName", "roomId");
		return mv;
	}
	
	// 选择特惠房间,酒店和公寓
	@RequestMapping("/selectRoomIdType.do")
	public ModelAndView selectRoomIdType(HttpServletRequest request, HttpServletResponse response,String branchId) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/dialog/customDialog");
		mv.addObject("dialogColumns", "houseId,houseName");
		mv.addObject("dialogTarget", "House");
		mv.addObject("dialogConditions", "status != '0'");
		mv.addObject("dialogQuickAdd", null);
		mv.addObject("selectType", "dialog-radio");
		mv.addObject("colName", "roomId");
		return mv;
	}
	
	// 选择民宿的查询数据方法
	@RequestMapping("/queryHouseAndroomIdInHouse.do")
	public void queryHouseAndroomIdInHouse(HttpServletRequest request, HttpServletResponse response, String houseName) throws Exception {
		Pagination pagination;
		Integer pageNum = Integer.parseInt(request.getParameter("page"));
		Integer rows = Integer.parseInt(request.getParameter("rows"));
		pagination = new Pagination(pageNum, rows);
		houseName = URLDecoder.decode(houseName, "UTF-8");
		List<?> result = this.marketingBasicService.queryhouseAndRoomId( houseName, pagination);
		JSONUtil.responseJSON(response, JSONUtil.buildReportJSONByPagination(result, pagination));
	}

}
