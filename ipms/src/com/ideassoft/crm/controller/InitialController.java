package com.ideassoft.crm.controller;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hsqldb.lib.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ideassoft.apartment.service.ApartmentCleanService;
import com.ideassoft.basic.service.AuditBasicService;
import com.ideassoft.bean.Branch;
import com.ideassoft.bean.BranchKeywords;
import com.ideassoft.bean.CashBox;
import com.ideassoft.bean.City;
import com.ideassoft.bean.Clean;
import com.ideassoft.bean.Goods;
import com.ideassoft.bean.GoodsCategory;
import com.ideassoft.bean.Location;
import com.ideassoft.bean.RightDefine;
import com.ideassoft.bean.Role;
import com.ideassoft.bean.RoleRelation;
import com.ideassoft.bean.Room;
import com.ideassoft.bean.RoomKey;
import com.ideassoft.bean.RoomPrice;
import com.ideassoft.bean.RoomPriceId;
import com.ideassoft.bean.RoomStatus;
import com.ideassoft.bean.RoomType;
import com.ideassoft.bean.RoomTypeKey;
import com.ideassoft.bean.ShiftTime;
import com.ideassoft.bean.Staff;
import com.ideassoft.bean.SysParam;
import com.ideassoft.bean.UserRole;
import com.ideassoft.core.ajax.AjaxResult;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.bean.pageConfig.ModelConfig;
import com.ideassoft.core.bean.pageConfig.PageConfig;
import com.ideassoft.core.constants.CommonConstants;
import com.ideassoft.core.constants.RightConstants;
import com.ideassoft.core.constants.SystemConstants;
import com.ideassoft.core.page.PageBuilder;
import com.ideassoft.crm.service.InitialService;
import com.ideassoft.crm.service.ManageService;
import com.ideassoft.crm.service.RightService;
import com.ideassoft.pmsmanage.service.PmsmanageService;
import com.ideassoft.priceRuleUtile.BasePriceUtile;
import com.ideassoft.util.CloneUtil;
import com.ideassoft.util.JSONUtil;
import com.ideassoft.util.MD5Util;
import com.ideassoft.util.ReflectUtil;
import com.ideassoft.util.RequestUtil;

@Transactional
@Controller
public class InitialController {
	private static Logger logger = Logger.getLogger(InitialController.class);
	
	@Autowired
	private PmsmanageService pmsmanageService;
	
	@Autowired
	private AuditBasicService auditBasicService;
	
	@Autowired
	InitialService initialService;

	@Autowired
	private RightService rightService;
	
	@Autowired
	private ManageService manageService;

	@Autowired
	PageBuilder pageBuilder;
	
	@Autowired
	private ApartmentCleanService cleanService;

	//操作步骤界面
	@RequestMapping("/instruction.do")
	public ModelAndView instruction(HttpServletRequest request) throws Exception {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/initializtion/instruction");
		mv.addObject("loginInfo", loginUser);
		return mv;
	}

	//新增门店界面
	@SuppressWarnings("unchecked")
	@RequestMapping("/turnToBranchPage.do")
	public ModelAndView turnToBranchPage(HttpServletRequest request) throws Exception {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		Branch marketCenterInfo = initialService.queryCenterBranchInfo();
		if(marketCenterInfo != null && marketCenterInfo.getBranchId() != null && !marketCenterInfo.getBranchId().isEmpty()){
			if(branchId.equalsIgnoreCase(marketCenterInfo.getBranchId())){
				branchId = "";
			}
		}
		List<?> branchList = initialService.queryBranchList(branchId);
		List<?> branchLocation = initialService.queryBranchLocation(branchId);
		List<City> cityList = manageService.queryCity();
		List<?> cityInfo = initialService.findByProperties(City.class, "rank", "1", "status", "1");
		List<Map<String, Object>> newCity = new ArrayList<Map<String, Object>>();
		
		for (int i = 0; i < cityInfo.size(); i++) {
			Map<String, Object> el = new HashMap<String, Object>();
			String pinYin = getAlpha(cityList.get(i).getAdminiName());
			el.put("py", pinYin);
			el.put("name", cityList.get(i).getAdminiName());
			el.put("code", cityList.get(i).getAdminiCode());
			newCity.add(el);
		}
		
		List<?> customerPhone = initialService.queryCustomerPhone(branchId);	
		List<?> loginUserInfo = initialService.queryCurrentLoginUser(loginUser.getStaff().getStaffId());	
		List<City> districtList =null;
		String sql = "queryPlace";
		//创建门店后的城市编码，名字，区域代码
		String cityAdminCode = "";
		String cityAdminCodeName = "";
		String cityAdminSubCode ="";
		if(branchList != null && branchList.size() > 0){
			cityAdminCode = ((Map<?,?>)branchList.get(0)).get("CITY").toString();
			cityAdminSubCode= ((Map<?,?>)branchList.get(0)).get("DISTRICT").toString();
			for(int i =0;i<cityList.size();i++){
				if(cityAdminCode.equals(cityList.get(i).getAdminiCode())){
					cityAdminCodeName = cityList.get(i).getAdminiName();
					break;
				}
			}
			String citySubCode = cityAdminCode.substring(0, 4) + "%";
			 districtList = manageService.findBySQL(sql, new String[] { citySubCode, CommonConstants.CityRank.DISTRICT },true);
		}
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/initializtion/initialBranchAdd");
		mv.addObject("loginUser", loginUserInfo.get(0));
		mv.addObject("customerPhone", customerPhone);
		mv.addObject("cityList", cityList);
		mv.addObject("branchList", branchList);
		mv.addObject("branchLocation", branchLocation);
		mv.addObject("districtList", districtList);
		mv.addObject("cityAdminCode", cityAdminCode);
		mv.addObject("cityAdminCodeName", cityAdminCodeName);
		mv.addObject("cityAdminSubCode", cityAdminSubCode);
		mv.addObject("newCity", JSONUtil.fromObject(newCity));
		return mv;
	}
	
	//修改系统参数界面
	@RequestMapping("/turnToParameterAdd.do")
	public ModelAndView turnToParameterAdd(HttpServletRequest request, Branch branch, String rank) throws Exception {
		String turnFlag = request.getParameter("turnFlag");
		ModelAndView mv = new ModelAndView();
		Map<String, Object> map = new HashMap<String, Object>();
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		if(turnFlag == null){
			turnFlag = "false";
		}
		if(turnFlag.trim().equals("turnFlag")){
			List<?> result = initialService.findByProperties(Branch.class, "branchId", branchId);
			String branchSubType = ((Branch)result.get(0)).getBranchType();
			
			if(branchSubType.equalsIgnoreCase(CommonConstants.SubBranchType.HOTEL)){
				String branchName = initialService.findBranchNameByBranchId(branchId);
				List<?> cashBoxList = initialService.findCashBoxData(branchId);
				if(cashBoxList == null || cashBoxList.size() <= 0){
					mv.addObject("branchName", branchName);
					mv.addObject("cashCount", "3000.00");
				}
				mv.addObject("branchSubType", CommonConstants.SubBranchType.HOTEL);
				mv.setViewName("page/initializtion/cashBoxAdd");
				mv.addObject("cashBoxList", cashBoxList);	
			} else if (branchSubType.equalsIgnoreCase(CommonConstants.SubBranchType.APART)){
				String branchName = initialService.findBranchNameByBranchId(branchId);
				List<?> cleanData = initialService.findCleanCountFromSysParam(branchId);
				if(cleanData == null || cleanData.size() <= 0){
					mv.addObject("branchName", branchName);
					mv.addObject("CleanCount", "5");	
					mv.addObject("CleanMoney", "100");
				}
				mv.addObject("branchSubType", CommonConstants.SubBranchType.APART);
				mv.setViewName("page/initializtion/initialCleanAdd");
				mv.addObject("cleanData", cleanData);
			}
		}else{
			//保存门店信息
			
			try{
				if (branch.getBranchId() == null || StringUtil.isEmpty(branch.getBranchId())) {
					String dataId = this.initialService.getCloudOrLocalSequence("SEQ_NEW_BRANCHID");
					branch.setBranchId(dataId);
				}
				branch.setRank(rank);
				branch.setRecordUser(loginUser.getStaff().getStaffId());
				branch.setRecordTime(new Date());
				branch.setStatus("1");
				pageBuilder.getHibernateTemplate().saveOrUpdate(branch);
				List<Branch> branchList = new ArrayList();
				branchList.add(branch);
				map.put("Branch", branchList);
					
				//将当前的分超级管理员的branchId改掉
				loginUser.getStaff().setBranchId(branch.getBranchId());
				request.getSession(true).setAttribute(RequestUtil.LOGIN_USER_SESSION_KEY, loginUser);
				pageBuilder.getHibernateTemplate().saveOrUpdate(loginUser.getStaff());
				//将branchId 同步
				branchId = branch.getBranchId();
				//保存location
				String latitude = request.getParameter("latitude");
				String longitude = request.getParameter("longitude");
				List<?> branchLocation = initialService.queryBranchLocation(branchId);
				if(branchLocation != null){
					initialService.updateLocation(branchId, latitude, longitude);
					//查询修改之后的branchlocation
					List<?> newLocation = initialService.queryBranchLocation(branchId);
					map.put("Location", newLocation);
				}else {
					Location loca = new Location();
					loca.setBranchId(branchId);
					loca.setLatitude(latitude);
					loca.setLongitude(longitude);
					loca.setRecordTime(new Date());
					loca.setRecordUser(loginUser.getStaff().getStaffId());
					loca.setStatus("1");
					pageBuilder.getHibernateTemplate().saveOrUpdate(loca);
					List<Location> locationList = new ArrayList();
					locationList.add(loca);
					map.put("Location", locationList);
				}
				//保存门店关键词
				List<?> branchKeyWords = initialService.queryBranchKeyWords(branchId);
				if(branchKeyWords != null){
					initialService.updateKeyWords(branchId, branch.getBranchName());
					//查询修改之后的branchKeyWords
					List<?> keywordsList = initialService.queryBranchKeyWords(branchId);
					map.put("BranchKeywords", keywordsList);
				}else {
					SimpleDateFormat sdfa = new SimpleDateFormat("yyMMdd");
					String dateString = sdfa.format(new Date());
					BranchKeywords bky = new BranchKeywords();
					bky.setBranchId(branch.getBranchId());
					bky.setKeywords(branch.getBranchName());
					bky.setKeywordsId( dateString + branch.getBranchId() + this.initialService.getCloudOrLocalSequence("SEQ_NEW_BRANCHID") );
					bky.setRecordTime(new Date());
					bky.setRecordUser(loginUser.getStaff().getStaffId());
					bky.setRemark("");
					bky.setStatus(CommonConstants.STATUS.NORMAL);
					pageBuilder.getHibernateTemplate().saveOrUpdate(bky);
					List<BranchKeywords> keywordsList = new ArrayList();
					keywordsList.add(bky);
					map.put("BranchKeywords", keywordsList);
				}
				//保存客服电话到SYSParam表里
				String customerPhone = request.getParameter("customerPhone");
				if(customerPhone != null){
					SysParam oneCustomerPhone = initialService.queryOneCustomerPhone(branchId);
					if(oneCustomerPhone != null){
						oneCustomerPhone.setContent(customerPhone);
						pageBuilder.getHibernateTemplate().saveOrUpdate(oneCustomerPhone);
						List<SysParam> customerPhoneList = new ArrayList();
						customerPhoneList.add(oneCustomerPhone);
						map.put("SysParam", customerPhoneList); //客服电话1
					}else{
						SysParam sp = new SysParam();
						String paramId = this.initialService.getCloudOrLocalSequence("SEQ_SYSPARAM_ID");
						sp.setParamId(paramId);
						sp.setParamType("SERVICETEL");
						sp.setParamName(branchId);
						sp.setContent(customerPhone);
						sp.setParamDesc("客服电话");
						sp.setOrderNo("1");
						sp.setStatus("1");
						pageBuilder.getHibernateTemplate().saveOrUpdate(sp);
						List<SysParam> customerPhoneList = new ArrayList();
						customerPhoneList.add(sp);
						map.put("SysParam", customerPhoneList); //客服电话2
					}
				}
				if(branch.getBranchType().trim().equals("1")){
					//当前门店的班次的录入
					Boolean isRightShift = initialService.queryShiftTimeInDB(branchId);
					if(!isRightShift){
						List<ShiftTime> errorShiftTime = initialService.errorShiftTimeInDB(branchId);
						if(errorShiftTime != null && errorShiftTime.size() > 0){
							for(int i = 0; i < errorShiftTime.size(); i++){
								initialService.delete(errorShiftTime.get(i));
							}
						}
						List<ShiftTime> st = new ArrayList();
						Date date = new Date();
						SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
						String time = sdf.format(date);
						String sequences1 = initialService.getCloudOrLocalSequence("SEQ_SYSPARAM_ORDERNO_ID");
						String orderno1 = initialService.getCloudOrLocalSequence("SEQ_SYSPARAM_ORDERNO");
						String sequences2 = initialService.getCloudOrLocalSequence("SEQ_SYSPARAM_ORDERNO_ID");
						String orderno2 = initialService.getCloudOrLocalSequence("SEQ_SYSPARAM_ORDERNO");
						String sequences3 = initialService.getCloudOrLocalSequence("SEQ_SYSPARAM_ORDERNO_ID");
						String orderno3 = initialService.getCloudOrLocalSequence("SEQ_SYSPARAM_ORDERNO");
						st.add(new ShiftTime(time+branch.getBranchId()+sequences1, branch.getBranchId(), "08:00:00","14:00:00",orderno1, "早班", CommonConstants.STATUS.NORMAL, null, null));
						st.add(new ShiftTime(time+branch.getBranchId()+sequences2, branch.getBranchId(), "14:00:00","23:00:00",orderno2, "中班", CommonConstants.STATUS.NORMAL, null, null));
						st.add(new ShiftTime(time+branch.getBranchId()+sequences3, branch.getBranchId(), "23:00:00","08:00:00",orderno3, "晚班", CommonConstants.STATUS.NORMAL, null, null));
						pageBuilder.saveOrUpdateAll(st);
						map.put("ShiftTime", st);//班次
					}
				}

				SysParam RepairAudit = this.initialService.queryRrecordsInSysParam("paramType","AUDITRepair","parameter1",branchId);
				if(RepairAudit == null){
					SysParam sp = new SysParam();
					String paramId = this.initialService.getCloudOrLocalSequence("SEQ_SYSPARAM_ID");
					sp.setParamId(paramId);
					sp.setParamType("AUDITRepair");
					sp.setParamName("0008");
					sp.setParamDesc("*");
					sp.setContent("*");
					sp.setStatus("1");
					sp.setRemark("*");
					sp.setParameter1(branchId);
					pageBuilder.getHibernateTemplate().saveOrUpdate(sp);
				}else{
					RepairAudit.setParamName("0008");
					pageBuilder.getHibernateTemplate().saveOrUpdate(RepairAudit);
				}
				
				SysParam CheckOutAUDIT = this.initialService.queryRrecordsInSysParam("paramType","AUDITCheckOut","parameter1",branchId);
				if(CheckOutAUDIT == null){
					SysParam sp = new SysParam();
					String paramId = this.initialService.getCloudOrLocalSequence("SEQ_SYSPARAM_ID");
					sp.setParamId(paramId);
					sp.setParamType("AUDITCheckOut");
					sp.setParamName("0008");
					sp.setParamDesc("*");
					sp.setContent("*");
					sp.setStatus("1");
					sp.setRemark("*");
					sp.setParameter1(branchId);
					pageBuilder.getHibernateTemplate().saveOrUpdate(sp);
				}else{
					CheckOutAUDIT.setParamName("0008");
					pageBuilder.getHibernateTemplate().saveOrUpdate(CheckOutAUDIT);
				}
				//云端数据上传或者下载
//				initialService.dataUpDown(1, branch.getBranchIp(), "initialStep2", map);	
			}catch (Exception e) {
				e.printStackTrace();
				logger.error("initalData step2 save branchInfo occurs error! step2! " + "\n" + e);
			}
			
			if(branch.getBranchType().equalsIgnoreCase(CommonConstants.SubBranchType.HOTEL)){
				String branchName = initialService.findBranchNameByBranchId(branchId);
				List<?> cashBoxList = initialService.findCashBoxData(branchId);
				if(cashBoxList == null || cashBoxList.size() <= 0){
					mv.addObject("branchName", branchName);
					mv.addObject("cashCount", "1000.00");
				}
				mv.addObject("branchSubType", CommonConstants.SubBranchType.HOTEL);
				mv.setViewName("page/initializtion/cashBoxAdd");
				mv.addObject("cashBoxList", cashBoxList);	
			} else if (branch.getBranchType().equalsIgnoreCase(CommonConstants.SubBranchType.APART)){
				String branchName = initialService.findBranchNameByBranchId(branchId);
				List<?> cleanData = initialService.findCleanCountFromSysParam(branchId);
				if(cleanData == null || cleanData.size() <= 0){
					mv.addObject("branchName", branchName);
					mv.addObject("CleanCount", "5");	
					mv.addObject("CleanMoney", "100");
				}
				mv.addObject("branchSubType", CommonConstants.SubBranchType.APART);
				mv.setViewName("page/initializtion/initialCleanAdd");
				mv.addObject("cleanData", cleanData);
			}
		}
		return mv;
	}
	
	//新增房型界面
	@RequestMapping("/saveRightAndTurnToRoomTypeAddView.do")
	public ModelAndView saveRightAndTurnToRoomTypeAddView(HttpServletRequest request, HttpServletResponse response,SysParam sysParam,CashBox cashBox) throws Exception {
		String turnFlag = request.getParameter("turnFlag");
		ModelAndView mv = new ModelAndView();
		Map<String, Object> map = new HashMap<String, Object>();
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		String branchType = "";
		String branchSubType = request.getParameter("branchSubType");
		if(turnFlag == null){
			turnFlag = "false";
		}
		if(turnFlag.trim().equals("turnFlag")){
			List<?> resultBranchType = initialService.findByProperties(Branch.class, "branchId", loginUser.getStaff().getBranchId());
			if(resultBranchType != null && resultBranchType.size()>0)
				branchType = ((Branch)resultBranchType.get(0)).getBranchType();
			// 如果数据库没有数据，则传递一个flag给前台，前台调用的保存，删除的方法不同
			List<?> roomTypeList = rightService.findAllRoomType(loginUser.getStaff().getBranchId());
			if(roomTypeList == null || roomTypeList.size() <= 0){
				mv.addObject("newDataFlag", "newDataFlag");
			}
			mv.addObject("branchId", loginUser.getStaff().getBranchId());
			mv.addObject("branchType", branchType);
			mv.addObject("recordUser", loginUser.getStaff().getStaffId());
			mv.setViewName("page/initializtion/roomTypeAdd");
		}else{
			if(branchSubType.trim().equals("1")){
				String branchName = initialService.findBranchNameByBranchId(branchId);
				//保存当前酒店的账户
				if(cashBox.getDataId() == null || cashBox.getDataId().equals("")){
					cashBox.setDataId(this.initialService.getCloudOrLocalSequence("SEQ_NEW_CASHBOX"));
				}
				cashBox.setBranchId(branchId);
				cashBox.setRecordTime(new Date());
				cashBox.setRecordUser(loginUser.getStaff().getStaffId());
				cashBox.setStatus("1");
					
				pageBuilder.getHibernateTemplate().saveOrUpdate(cashBox);
				
				List<CashBox> cashBoxList = new ArrayList();
				cashBoxList.add(cashBox);
				map.put("CashBox", cashBoxList);
//				Branch loginBranch = (Branch)initialService.findOneByProperties(Branch.class, "branchId", branchId);
//				initialService.dataUpDown(1, loginBranch.getBranchIp(), "initial_hotel_Step7", map);
				List<?> result = initialService.findByProperties(Branch.class, "branchId", loginUser.getStaff().getBranchId());
				if(result != null && result.size()>0) 
					branchSubType = ((Branch)result.get(0)).getBranchType();
				List<?> roomTypeList = rightService.findAllRoomType(loginUser.getStaff().getBranchId());
				if(roomTypeList == null || roomTypeList.size() <= 0){
					mv.addObject("newDataFlag", "newDataFlag");
				}
				mv.addObject("branchId", loginUser.getStaff().getBranchId());
				mv.addObject("branchType", branchSubType);
				mv.addObject("recordUser", loginUser.getStaff().getStaffId());
				mv.setViewName("page/initializtion/roomTypeAdd");
				
			}else{
				//保存系统参数
				String price = request.getParameter("cleanMoney");
				
				String branchName = initialService.findBranchNameByBranchId(branchId);
				//保存当前公寓的每日清洁的数量 
				if(sysParam.getParamId()== null || sysParam.getParamId().equals("")){
					sysParam.setParamId(this.initialService.getCloudOrLocalSequence("SEQ_SYSPARAM_ID"));
				}
				sysParam.setParamDesc(branchId);
				sysParam.setParamType("CLEANAMOUNT");
				sysParam.setParamName("*");
				sysParam.setStatus("1");
				sysParam.setParameter1("预置保洁次数");
				pageBuilder.getHibernateTemplate().saveOrUpdate(sysParam);
				List<SysParam> cleanList = new ArrayList();
				cleanList.add(sysParam);
				map.put("SysParam", cleanList);
				//保存当前公寓的保洁费
				String categoryId = "";
				String goodId = request.getParameter("goodId");
				Goods gd = null;
				if(goodId == null || goodId.equals("")){
					gd = new Goods();
					GoodsCategory gc = new GoodsCategory();
					gc.setBranchId(branchId);
					categoryId = this.initialService.getCloudOrLocalSequence("SEQ_NEW_GOODSCATEGORY");
					gc.setCategoryId(categoryId);
					gc.setCategoryName("保洁");
					gc.setChargeRoom("1");
					gc.setRecordTime(new Date());
					gc.setRecordUser(loginUser.getStaff().getStaffId());
					gc.setStatus("1");
					gc.setSuperCategory("2");
					pageBuilder.getHibernateTemplate().saveOrUpdate(gc);
					List<GoodsCategory> goodCategoryList = new ArrayList();
					goodCategoryList.add(gc);
					map.put("GoodsCategory", goodCategoryList);
					gd.setGoodsId(this.initialService.getCloudOrLocalSequence("SEQ_NEW_GOODS"));
					gd.setCategoryId(categoryId);
					gd.setPrice(0.1);
					gd.setBranchId(branchId);
					gd.setGoodsName("保洁费");
					gd.setProductionDate(new Date());
					gd.setRecordTime(new Date());
					gd.setRecordUser(loginUser.getStaff().getStaffId());
					gd.setSaleType("1");
					gd.setSpecifications("次");
					gd.setStatus("1");
					gd.setSupplierId("10000001");
					gd.setUnit("次");
				} else {
					gd = (Goods)initialService.findOneByProperties(Goods.class,  "branchId", branchId, "goodsId", goodId);
					gd.setPrice(Double.valueOf(price));
					gd.setRecordTime(new Date());
				}
					
				pageBuilder.getHibernateTemplate().saveOrUpdate(gd);
					
				List<Goods> goodsList = new ArrayList();
				goodsList.add(gd);
				map.put("Goods", goodsList);
					
//				Branch loginBranch = (Branch)initialService.findOneByProperties(Branch.class, "branchId", branchId);
//				initialService.dataUpDown(1, loginBranch.getBranchIp(), "initial_apart_Step7", map);
				
				List<?> result = initialService.findByProperties(Branch.class, "branchId", loginUser.getStaff().getBranchId());
				if(result != null && result.size()>0) 
					branchSubType = ((Branch)result.get(0)).getBranchType();
				
				branchType = CommonConstants.SubBranchType.HOTEL;
				//+附带加上当前门店的branchType ，当前人的编号
				List<?> resultBranchType = initialService.findByProperties(Branch.class, "branchId", loginUser.getStaff().getBranchId());
				if(resultBranchType != null && resultBranchType.size()>0)
					branchType = ((Branch)resultBranchType.get(0)).getBranchType();
				// 如果数据库没有数据，则传递一个flag给前台，前台调用的保存，删除的方法不同
				List<?> roomTypeList = rightService.findAllRoomType(loginUser.getStaff().getBranchId());
				if(roomTypeList == null || roomTypeList.size() <= 0){
					mv.addObject("newDataFlag", "newDataFlag");
				}
				mv.addObject("branchId", loginUser.getStaff().getBranchId());
				mv.addObject("branchType", branchType);
				mv.addObject("recordUser", loginUser.getStaff().getStaffId());
				mv.setViewName("page/initializtion/roomTypeAdd");
			}
		}
		return mv;
	}
	
	@RequestMapping("/turnToRoomTypeAdd.do")
	public void turnToRoomTypeAdd(HttpServletRequest request, HttpServletResponse response,String branchType,String branchId) throws Exception{
		String delString = request.getParameter("delArray");
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginUser.getStaff();
		//查询已有的房型到页面上显示
		List<?> roomTypeList = rightService.findAllRoomType(branchId);
		if(roomTypeList == null || roomTypeList.size() <= 0){
			List<RoomType> roomTypeListNew = new ArrayList();
			if(branchType.equalsIgnoreCase(CommonConstants.SubBranchType.HOTEL)){
				roomTypeListNew.add(new RoomType(new RoomTypeKey(branchId,"BR"),"商务房", CommonConstants.SubBranchType.HOTEL, 2, "2", "1", "1", new Date(),staff.getStaffId(),"111111111111111111"));
				roomTypeListNew.add(new RoomType(new RoomTypeKey(branchId,"BSR"),"商务标准间", CommonConstants.SubBranchType.HOTEL, 2, "2", "1", "1", new Date(),staff.getStaffId(),"111111111111111111"));
				roomTypeListNew.add(new RoomType(new RoomTypeKey(branchId,"DS"),"豪华套房", CommonConstants.SubBranchType.HOTEL, 2, "2", "1", "1", new Date(),staff.getStaffId(),"111111111111111111"));
				roomTypeListNew.add(new RoomType(new RoomTypeKey(branchId,"DSR"),"豪华标间", CommonConstants.SubBranchType.HOTEL, 2, "2", "1", "1", new Date(),staff.getStaffId(),"111111111111111111"));
				roomTypeListNew.add(new RoomType(new RoomTypeKey(branchId,"SD"),"大床房", CommonConstants.SubBranchType.HOTEL, 2, "2", "1", "1", new Date(),staff.getStaffId(),"111111111111111111"));
				roomTypeListNew.add(new RoomType(new RoomTypeKey(branchId,"SKR"),"特色大床房", CommonConstants.SubBranchType.HOTEL, 2, "2", "1", "1", new Date(),staff.getStaffId(),"111111111111111111"));
				roomTypeListNew.add(new RoomType(new RoomTypeKey(branchId,"SR"),"标准间", CommonConstants.SubBranchType.HOTEL, 2, "2", "1", "1", new Date(),staff.getStaffId(),"111111111111111111"));
				roomTypeListNew.add(new RoomType(new RoomTypeKey(branchId,"SSR"),"套房", CommonConstants.SubBranchType.HOTEL, 2, "2", "1", "1", new Date(),staff.getStaffId(),"111111111111111111"));
				//roomTypeListNew.add(new RoomType(new RoomTypeKey(branchId,"XR"),"X?房", CommonConstants.SubBranchType.HOTEL, 2, "2", "1", "1", new Date(),staff.getStaffId(),"111111111111111111"));
			}else if (branchType.equalsIgnoreCase(CommonConstants.SubBranchType.APART)){
				roomTypeListNew.add(new RoomType(new RoomTypeKey(branchId,"D1"),"极简户型", CommonConstants.SubBranchType.APART, 2, "2", "1", "1", new Date(),staff.getStaffId(),"111111111111111111"));
				roomTypeListNew.add(new RoomType(new RoomTypeKey(branchId,"D2"),"极简户型2", CommonConstants.SubBranchType.APART, 2, "2", "1", "1", new Date(),staff.getStaffId(),"111111111111111111"));
				roomTypeListNew.add(new RoomType(new RoomTypeKey(branchId,"D3"),"雅居户型", CommonConstants.SubBranchType.APART, 2, "2", "1", "1", new Date(),staff.getStaffId(),"111111111111111111"));
				roomTypeListNew.add(new RoomType(new RoomTypeKey(branchId,"D4"),"奢华户型", CommonConstants.SubBranchType.APART, 2, "2", "1", "1", new Date(),staff.getStaffId(),"111111111111111111"));			
			}
			if(delString != null && delString.length() > 0){
				String[] delArray = delString.split(",");
				for(int j = 0; j < delArray.length; j++){
					for (int k = 0; k < roomTypeListNew.size(); k++){
						if(delArray[j].equals(roomTypeListNew.get(k).getRoomTypeKey().getRoomType())){
							roomTypeListNew.remove(k);
						} 	
					}	
				}
			}
			JSONUtil.responseJSON(response,JSONUtil.buildReportJSON(roomTypeListNew));
		}else{
			JSONUtil.responseJSON(response,JSONUtil.buildReportJSON(roomTypeList));
		}
	}
	
	
	
	
	//保存branch的相关的数据，数据上传或下载
	@RequestMapping("/turnToStaffPage.do")
	public ModelAndView turnToStaffPage(HttpServletRequest request,Branch branch) throws Exception {
		//数据上传或下载到本地的数据库
		Map<String, Object> map = new HashMap<String, Object>();
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		//保存branch的数据
		String turnFlag = request.getParameter("turnFlag");
		try{
			if(turnFlag== null || !turnFlag.equalsIgnoreCase("turnFlag")) {
				if (branch.getBranchId() == null || StringUtil.isEmpty(branch.getBranchId())) {
					String dataId = this.initialService.getCloudOrLocalSequence("SEQ_NEW_BRANCHID");
					branch.setBranchId(dataId);
				}
				branch.setRecordUser(loginUser.getStaff().getStaffId());
				branch.setRecordTime(new Date());
				branch.setStatus("1");
				pageBuilder.getHibernateTemplate().saveOrUpdate(branch);
				List<Branch> branchList = new ArrayList();
				branchList.add(branch);
				map.put("Branch", branchList);
				
				//将当前的分超级管理员的branchId改掉
				loginUser.getStaff().setBranchId(branch.getBranchId());
				request.getSession(true).setAttribute(RequestUtil.LOGIN_USER_SESSION_KEY, loginUser);
				pageBuilder.getHibernateTemplate().saveOrUpdate(loginUser.getStaff());
				//将branchId 同步
				branchId = branch.getBranchId();
				//保存location
				String latitude = request.getParameter("latitude");
				String longitude = request.getParameter("longitude");
				List<?> branchLocation = initialService.queryBranchLocation(branchId);
				if(branchLocation != null){
					initialService.updateLocation(branchId, latitude, longitude);
					//查询修改之后的branchlocation
					List<?> newLocation = initialService.queryBranchLocation(branchId);
					map.put("Location", newLocation);
				}  else {
					Location loca = new Location();
					loca.setBranchId(branchId);
					loca.setLatitude(latitude);
					loca.setLongitude(longitude);
					loca.setRecordTime(new Date());
					loca.setRecordUser(loginUser.getStaff().getStaffId());
					loca.setStatus("1");
					pageBuilder.getHibernateTemplate().saveOrUpdate(loca);
					List<Location> locationList = new ArrayList();
					locationList.add(loca);
					map.put("Location", locationList);
				}
				//保存门店关键词
				List<?> branchKeyWords = initialService.queryBranchKeyWords(branchId);
				if(branchKeyWords != null){
					initialService.updateKeyWords(branchId, branch.getBranchName());
					//查询修改之后的branchKeyWords
					List<?> keywordsList = initialService.queryBranchKeyWords(branchId);
					map.put("BranchKeywords", keywordsList);
				}  else {
					SimpleDateFormat sdfa = new SimpleDateFormat("yyMMdd");
					String dateString = sdfa.format(new Date());
					BranchKeywords bky = new BranchKeywords();
					bky.setBranchId(branch.getBranchId());
					bky.setKeywords(branch.getBranchName());
					bky.setKeywordsId( dateString + branch.getBranchId() + this.initialService.getCloudOrLocalSequence("SEQ_NEW_BRANCHID") );
					bky.setRecordTime(new Date());
					bky.setRecordUser(loginUser.getStaff().getStaffId());
					bky.setRemark("");
					bky.setStatus(CommonConstants.STATUS.NORMAL);
					pageBuilder.getHibernateTemplate().saveOrUpdate(bky);
					List<BranchKeywords> keywordsList = new ArrayList();
					keywordsList.add(bky);
					map.put("BranchKeywords", keywordsList);
				}
				//保存客服电话到SYSParam表里
				String customerPhone = request.getParameter("customerPhone");
				if(customerPhone != null){
					SysParam oneCustomerPhone = initialService.queryOneCustomerPhone(branchId);
					if(oneCustomerPhone != null){
						oneCustomerPhone.setContent(customerPhone);
						pageBuilder.getHibernateTemplate().saveOrUpdate(oneCustomerPhone);
						List<SysParam> customerPhoneList = new ArrayList();
						customerPhoneList.add(oneCustomerPhone);
						map.put("SysParam", customerPhoneList); //客服电话1
					}else{
						SysParam sp = new SysParam();
						String paramId = this.initialService.getCloudOrLocalSequence("SEQ_SYSPARAM_ID");
						sp.setParamId(paramId);
						sp.setParamType("SERVICETEL");
						sp.setParamName(branchId);
						sp.setContent(customerPhone);
						sp.setParamDesc("客服电话");
						sp.setOrderNo("1");
						sp.setStatus("1");
						pageBuilder.getHibernateTemplate().saveOrUpdate(sp);
						List<SysParam> customerPhoneList = new ArrayList();
						customerPhoneList.add(sp);
						map.put("SysParam", customerPhoneList); //客服电话2
					}
				}
				
				//当前门店的班次的录入
				Boolean isRightShift = initialService.queryShiftTimeInDB(branchId);
				if(!isRightShift){
					List<ShiftTime> errorShiftTime = initialService.errorShiftTimeInDB(branchId);
					if(errorShiftTime != null && errorShiftTime.size() > 0){
						for(int i = 0; i < errorShiftTime.size(); i++){
							initialService.delete(errorShiftTime.get(i));
						}
					}
					List<ShiftTime> st = new ArrayList();
					Date date = new Date();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
					String time = sdf.format(date);
					String sequences1 = initialService.getCloudOrLocalSequence("SEQ_SYSPARAM_ORDERNO_ID");
					String orderno1 = initialService.getCloudOrLocalSequence("SEQ_SYSPARAM_ORDERNO");
					String sequences2 = initialService.getCloudOrLocalSequence("SEQ_SYSPARAM_ORDERNO_ID");
					String orderno2 = initialService.getCloudOrLocalSequence("SEQ_SYSPARAM_ORDERNO");
					String sequences3 = initialService.getCloudOrLocalSequence("SEQ_SYSPARAM_ORDERNO_ID");
					String orderno3 = initialService.getCloudOrLocalSequence("SEQ_SYSPARAM_ORDERNO");
					st.add(new ShiftTime(time+branch.getBranchId()+sequences1, branch.getBranchId(), "08:00:00","14:00:00",orderno1, "早班", CommonConstants.STATUS.NORMAL, null, null));
					st.add(new ShiftTime(time+branch.getBranchId()+sequences2, branch.getBranchId(), "14:00:00","23:00:00",orderno2, "中班", CommonConstants.STATUS.NORMAL, null, null));
					st.add(new ShiftTime(time+branch.getBranchId()+sequences3, branch.getBranchId(), "23:00:00","08:00:00",orderno3, "晚班", CommonConstants.STATUS.NORMAL, null, null));
					pageBuilder.saveOrUpdateAll(st);
					map.put("ShiftTime", st);//班次
				}
				initialService.dataUpDown(1, branch.getBranchIp(), "initialStep2", map);
			}	
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("initalData step2 save branchInfo occurs error! step2! " + "\n" + e);
		}
		//查询所有职位中数据库预设的状态为2的所有职位
		List<?> postList = initialService.findAllPostNew();
		List<?> staffList = initialService.findStaffData(branchId,loginUser.getStaff().getStaffId());
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/initializtion/postAndStaffAdd");
		mv.addObject("postList", postList);
		mv.addObject("staffList", staffList);
		return mv;
	}
		
	@RequestMapping("/turnToCleanAdd.do")
	public ModelAndView turnToCleanAdd(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		String branchName = initialService.findBranchNameByBranchId(branchId);
		List<?> cleanData = initialService.findCleanCountFromSysParam(branchId);
		if(cleanData == null || cleanData.size() <= 0){
			mv.addObject("branchName", branchName);
			mv.addObject("CleanCount", "2");
			mv.addObject("CleanMoney", "0.1");
		}
		mv.setViewName("page/initializtion/initialCleanAdd");
		mv.addObject("cleanData", cleanData);
		return mv;
	}
	
	@RequestMapping("/turnToCashBoxAdd.do")
	public ModelAndView turnToCashBoxAdd(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		String branchName = initialService.findBranchNameByBranchId(branchId);
		List<?> cashBoxList = initialService.findCashBoxData(branchId);
		if(cashBoxList == null || cashBoxList.size() <= 0){
			mv.addObject("branchName", branchName);
			mv.addObject("cashCount", "3000.00");
		}
		mv.setViewName("page/initializtion/cashBoxAdd");
		mv.addObject("cashBoxList", cashBoxList);
		return mv;
	}
	
	@RequestMapping("/saveCashBoxAndToEnd.do")
	public ModelAndView saveCashBoxAndToEnd(HttpServletRequest request,CashBox cashBox) throws Exception {
		String branchSubType = "";
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		String branchName = initialService.findBranchNameByBranchId(branchId);
		//保存当前酒店的账户
		String turnFlag = request.getParameter("turnFlag");
		if(turnFlag== null || !turnFlag.equalsIgnoreCase("turnFlag")){
			if(cashBox.getDataId() == null || cashBox.getDataId().equals("")){
				cashBox.setDataId(this.initialService.getCloudOrLocalSequence("SEQ_NEW_CASHBOX"));
			}
			cashBox.setBranchId(branchId);
			cashBox.setRecordTime(new Date());
			cashBox.setRecordUser(loginUser.getStaff().getStaffId());
			cashBox.setStatus("1");
			
			pageBuilder.getHibernateTemplate().saveOrUpdate(cashBox);
			Map<String, Object> map = new HashMap<String, Object>();
			List<CashBox> cashBoxList = new ArrayList();
			cashBoxList.add(cashBox);
			map.put("CashBox", cashBoxList);
			Branch loginBranch = (Branch)initialService.findOneByProperties(Branch.class, "branchId", branchId);
			initialService.dataUpDown(1, loginBranch.getBranchIp(), "initial_hotel_Step7", map);
	
		}
		
		List<?> result = initialService.findByProperties(Branch.class, "branchId", loginUser.getStaff().getBranchId());
		if(result != null && result.size()>0) 
			branchSubType = ((Branch)result.get(0)).getBranchType();
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/initializtion/initializationToEnd");
		Staff firstStaff = initialService.findFirstStaff(loginUser.getStaff().getStaffId());
		mv.addObject("firstStaff",firstStaff);
		mv.addObject("loginInfo", loginUser);
		mv.addObject("branchSubType", branchSubType);
		return mv;
	}
	
	@RequestMapping("/roomInDatabaseCheck.do")
	public void roomInDatabaseCheck(HttpServletRequest request, HttpServletResponse response,String branchId,String titleList,String flag) throws Exception{
		Integer result = CommonConstants.AJAXRESULT.FALSE;
		if ("false".equals(flag)) {
			result = CommonConstants.AJAXACTION.OTHER;
		} else {
			/*if(initialService.checkRoomInDB(branchId,titleList) && initialService.checkRoomTypeCountIsEqualsRoom(branchId)){*/
			if(initialService.checkRoomInDB(branchId,titleList)){
				result = CommonConstants.AJAXRESULT.SUCESS;
			} else {
				result = CommonConstants.AJAXRESULT.FALSE;
			}
		}
		
		JSONUtil.responseJSON(response, new AjaxResult(result, null));
	}
	
	@RequestMapping("/saveCleanAndToEnd.do")
	public ModelAndView saveCleanAndToEnd(HttpServletRequest request,SysParam sysParam) throws Exception {
		String price = request.getParameter("cleanMoney");
		String branchSubType = "";
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		String branchName = initialService.findBranchNameByBranchId(branchId);
		String turnFlag = request.getParameter("turnFlag");
		//保存当前公寓的每日清洁的数量 
		if(turnFlag== null || !turnFlag.equalsIgnoreCase("turnFlag")){
			if(sysParam.getParamId()== null || sysParam.getParamId().equals("")){
				sysParam.setParamId(this.initialService.getCloudOrLocalSequence("SEQ_SYSPARAM_ID"));
			}
			sysParam.setParamDesc(branchId);
			sysParam.setParamType("CLEANAMOUNT");
			sysParam.setParamName("*");
			sysParam.setStatus("1");
			sysParam.setParameter1("预置保洁次数");
			pageBuilder.getHibernateTemplate().saveOrUpdate(sysParam);
			Map<String, Object> map = new HashMap<String, Object>();
			List<SysParam> cleanList = new ArrayList();
			cleanList.add(sysParam);
			map.put("SysParam", cleanList);
			//保存当前公寓的保洁费
			String categoryId = "";
			String goodId = request.getParameter("goodId");
			Goods gd = null;
			if(goodId == null || goodId.equals("")){
				gd = new Goods();
				GoodsCategory gc = new GoodsCategory();
				gc.setBranchId(branchId);
				categoryId = this.initialService.getCloudOrLocalSequence("SEQ_NEW_GOODSCATEGORY");
				gc.setCategoryId(categoryId);
				gc.setCategoryName("保洁");
				gc.setChargeRoom("1");
				gc.setRecordTime(new Date());
				gc.setRecordUser(loginUser.getStaff().getStaffId());
				gc.setStatus("1");
				gc.setSuperCategory("2");
				pageBuilder.getHibernateTemplate().saveOrUpdate(gc);
				List<GoodsCategory> goodCategoryList = new ArrayList();
				goodCategoryList.add(gc);
				map.put("GoodsCategory", goodCategoryList);
				gd.setGoodsId(this.initialService.getCloudOrLocalSequence("SEQ_NEW_GOODS"));
				gd.setCategoryId(categoryId);
				gd.setPrice(0.1);
				gd.setBranchId(branchId);
				gd.setGoodsName("保洁费");
				gd.setProductionDate(new Date());
				gd.setRecordTime(new Date());
				gd.setRecordUser(loginUser.getStaff().getStaffId());
				gd.setSaleType("1");
				gd.setSpecifications("次");
				gd.setStatus("1");
				gd.setSupplierId("10000001");
				gd.setUnit("次");
			} else {
				//categoryId = ((GoodsCategory)this.initialService.findByProperties(GoodsCategory.class, "branchId", branchId, "categoryName", "保洁").get(0)).getCategoryId();
				gd = (Goods)initialService.findOneByProperties(Goods.class,  "branchId", branchId, "goodsId", goodId);
				gd.setPrice(Double.valueOf(price));
				gd.setRecordTime(new Date());
			}
			
			pageBuilder.getHibernateTemplate().saveOrUpdate(gd);
			
			List<Goods> goodsList = new ArrayList();
			goodsList.add(gd);
			map.put("Goods", goodsList);
			
			Branch loginBranch = (Branch)initialService.findOneByProperties(Branch.class, "branchId", branchId);
			initialService.dataUpDown(1, loginBranch.getBranchIp(), "initial_apart_Step7", map);
		}
		List<?> result = initialService.findByProperties(Branch.class, "branchId", loginUser.getStaff().getBranchId());
		if(result != null && result.size()>0) 
			branchSubType = ((Branch)result.get(0)).getBranchType();
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/initializtion/initializationToEnd");
		Staff firstStaff = initialService.findFirstStaff(loginUser.getStaff().getStaffId());
		mv.addObject("firstStaff",firstStaff);
		mv.addObject("loginInfo", loginUser);
		mv.addObject("branchSubType", branchSubType);
		return mv;
	}
	
	@RequestMapping("/toEnd.do")
	public ModelAndView toEnd(HttpServletRequest request,SysParam sysParam) throws Exception {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/initializtion/initializationToEnd");
		mv.addObject("loginInfo", loginUser);
		return mv;
	}

	/*
	 * @RequestMapping("/saveStaffData.do") public void
	 * saveStaffData(HttpServletRequest request, HttpServletResponse
	 * response,Staff staff) throws Exception { String entryTime =
	 * request.getParameter("entryTimes"); String birthDay =
	 * request.getParameter("birthdays"); SimpleDateFormat sdf = new
	 * SimpleDateFormat("yyyy-MM-dd"); LoginUser loginUser = (LoginUser)
	 * request.
	 * getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY); String
	 * branchId = loginUser.getStaff().getBranchId(); String loginName =
	 * loginUser.getStaff().getLoginName(); String dataId =
	 * this.initialService.getSequence("SEQ_STAFF_ID", null);
	 * staff.setStaffId(dataId); staff.setBranchId(branchId);
	 * staff.setRecordTime(new Date()); staff.setRecordUser(loginName);
	 * staff.setPassword(MD5Util.getCryptogram("888888")); staff.setStatus("1");
	 * staff.setEntryTime(sdf.parse(entryTime));
	 * staff.setBirthday(sdf.parse(birthDay)); this.initialService.save(staff);
	 * }
	 */
	@RequestMapping("/turnToRightPage.do")
	public ModelAndView turnToRightPage(HttpServletRequest request, HttpServletResponse response, Staff staff,
			String roleId) throws Exception {
		//数据上传或下载到本地的数据库
		Map<String, Object> map = new HashMap<String, Object>();
		String turnFlag = request.getParameter("turnFlag");
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		// 保存数据（保存员工时查询是否有相同登录名，手机和身份证且状态有效的人 （本身已经在云端了））
		try{
			if(turnFlag== null || !turnFlag.equalsIgnoreCase("turnFlag")){
				String entryTime = request.getParameter("entryTimes");
				String birthDay = request.getParameter("birthdays");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String loginName = loginUser.getStaff().getLoginName();
				if (staff.getStaffId() == null || StringUtil.isEmpty(staff.getStaffId())) {
					String dataId = this.initialService.getCloudOrLocalSequence("SEQ_STAFF_ID");
					staff.setStaffId(dataId);
				}
				staff.setBranchId(branchId);
				staff.setRecordTime(new Date());
				staff.setRecordUser(loginUser.getStaff().getStaffId());
				staff.setPassword(MD5Util.getCryptogram("888888"));
				staff.setStatus("1");
				staff.setEntryTime(sdf.parse(entryTime));
				staff.setBirthday(sdf.parse(birthDay));
				pageBuilder.getHibernateTemplate().saveOrUpdate(staff);
				List<Staff> staffList = new ArrayList();
				staffList.add(staff);
				map.put("Staff", staffList);
				List<SysParam> auditList = new ArrayList();
				//保存和更新在sysparam表里配置的审核流程
				//查询表格里是否已有默认审核人的记录
				SysParam object = this.initialService.queryRrecordsInSysParamNew("paramType","AUDIT","remark",branchId);
				if(object == null){
					SysParam sp = new SysParam();
					String paramId = this.initialService.getCloudOrLocalSequence("SEQ_SYSPARAM_ID");
					sp.setParamId(paramId);
					sp.setParamType("AUDIT");
					sp.setParamName("默认审核人");
					sp.setContent(staff.getStaffId());
					sp.setParameter1(staff.getPost());
					sp.setParamDesc(staff.getMobile());
					sp.setStatus("1");
					sp.setRemark(branchId);
					pageBuilder.getHibernateTemplate().saveOrUpdate(sp);
					auditList.add(sp);
				}else{
					object.setContent(staff.getStaffId());
					pageBuilder.getHibernateTemplate().saveOrUpdate(object);
					auditList.add(object);
				}
				
				  //查询表格里是否已有房价流程审核人的记录,房价审核只有云端有，本地端没有
				/*SysParam RoompriceAudit = this.initialService.queryRrecordsInSysParam("paramType","AUDITRoomprice","parameter1",branchId);
				if(RoompriceAudit == null){
					SysParam sp = new SysParam();
					String paramId = this.initialService.getCloudOrLocalSequence("SEQ_SYSPARAM_ID");
					sp.setParamId(paramId);
					sp.setParamType("AUDITRoomprice");
					sp.setParamName(staff.getPost());
					sp.setParamDesc("*");
					sp.setContent("*");
					sp.setStatus("1");
					sp.setRemark("*");
					sp.setParameter1(branchId);
					pageBuilder.getHibernateTemplate().saveOrUpdate(sp);
					auditList.add(sp);
				}else{
					RoompriceAudit.setParamName(staff.getPost());
					pageBuilder.getHibernateTemplate().saveOrUpdate(RoompriceAudit);
					auditList.add(RoompriceAudit);
				}*/
				
				//查询表格里是否已有维修流程审核人的记录
				SysParam RepairAudit = this.initialService.queryRrecordsInSysParam("paramType","AUDITRepair","parameter1",branchId);
				if(RepairAudit == null){
					SysParam sp = new SysParam();
					String paramId = this.initialService.getCloudOrLocalSequence("SEQ_SYSPARAM_ID");
					sp.setParamId(paramId);
					sp.setParamType("AUDITRepair");
					sp.setParamName(staff.getPost());
					sp.setParamDesc("*");
					sp.setContent("*");
					sp.setStatus("1");
					sp.setRemark("*");
					sp.setParameter1(branchId);
					pageBuilder.getHibernateTemplate().saveOrUpdate(sp);
					auditList.add(sp);
					map.put("SysParam", auditList);
				}else{
					RepairAudit.setParamName(staff.getPost());
					pageBuilder.getHibernateTemplate().saveOrUpdate(RepairAudit);
					auditList.add(RepairAudit);
				}
				
				//查询表格里是否已有退房流程审核人的记录
				SysParam CheckOutAUDIT = this.initialService.queryRrecordsInSysParam("paramType","AUDITCheckOut","parameter1",branchId);
				if(CheckOutAUDIT == null){
					SysParam sp = new SysParam();
					String paramId = this.initialService.getCloudOrLocalSequence("SEQ_SYSPARAM_ID");
					sp.setParamId(paramId);
					sp.setParamType("AUDITCheckOut");
					sp.setParamName(staff.getPost());
					sp.setParamDesc("*");
					sp.setContent("*");
					sp.setStatus("1");
					sp.setRemark("*");
					sp.setParameter1(branchId);
					pageBuilder.getHibernateTemplate().saveOrUpdate(sp);
					auditList.add(sp);
				}else{
					CheckOutAUDIT.setParamName(staff.getPost());
					pageBuilder.getHibernateTemplate().saveOrUpdate(CheckOutAUDIT);
					auditList.add(CheckOutAUDIT);
				}
				map.put("SysParam", auditList);
				//根据branchId查找branch
				Branch loginBranch = (Branch)initialService.findOneByProperties(Branch.class, "branchId", branchId);
				initialService.dataUpDown(1, loginBranch.getBranchIp(), "initialStep3", map);
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("initalData step3 save staffInfo occurs error! step3! " + "\n" + e);
		}	
		// 查询前面流程里面员工数据
		List<?> staffList = initialService.findStaffData(branchId,loginUser.getStaff().getStaffId());
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/initializtion/staffRightAdd");
		mv.addObject("staffList", staffList);
		// 循环获得model里的各个子系统里模块总数
		Map<String, Integer> subSystemNum = new HashMap<String, Integer>();
		Map<String, ModelConfig> modelConfigs = pageBuilder.getModelConfigs();
		Iterator<Map.Entry<String, ModelConfig>> iter = modelConfigs.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, ModelConfig> entry = (Map.Entry<String, ModelConfig>) iter.next();
			ModelConfig tempModelConfig = entry.getValue();
			String subSyStemValue = tempModelConfig.getSubSystem().toUpperCase();
			if (!subSystemNum.containsKey(subSyStemValue)) {
				subSystemNum.put(subSyStemValue, 1);
			} else {
				subSystemNum.put(subSyStemValue, subSystemNum.get(subSyStemValue) + 1);
			}
		}
		Entry<?,?> modelEntry;
		Entry<?, ?> pageEntry;
		Map<?, ?> deepclonemodelConfigs = (Map<?, ?>) CloneUtil.deepClone(modelConfigs);
		Branch branch = (Branch) rightService.findOneByProperties(Branch.class,
				"branchId", loginUser.getStaff().getBranchId());
		for( Iterator<?> it = deepclonemodelConfigs.entrySet().iterator(); it.hasNext(); ){
			modelEntry = (Entry<?, ?>) it.next();
			ModelConfig modelConfig = (ModelConfig) modelEntry.getValue();
			if("M914".equals(modelConfig.getModelId())){
				
			}
			for(Iterator<?> pit = ((ModelConfig)modelEntry.getValue()).getPageConfigs()
					.entrySet().iterator(); pit.hasNext(); ) {
				pageEntry = (Entry<?, ?>) pit.next();
				PageConfig pageConfig = (PageConfig) pageEntry.getValue();
				
				if(branch != null){
					/*List<?> house = rightService.findByProperties(House.class, "staffId", loginUser.getStaff().getStaffId());
					if(house.size() > 0){//判断民宿代码
						if(!"4".equals(pageRole))
							pit.remove();
					} */
					String pageRole = pageConfig.getRole();
					if(!StringUtils.isEmpty(pageRole)){
						if(!"*".equals(pageRole)){
							if(CommonConstants.BranchRank.TOP.equals(branch.getRank())){
								//总店
								
							}
							else{
								//分店
								if(branch.getBranchType().equals("1")){
									if(!"1".equals(pageRole))
										pit.remove();
								}
								else if(branch.getBranchType().equals("2")){
									if(!"2".equals(pageRole))
										pit.remove();
								}
								
								//if(!RegExUtil.match(pageRole, userRole)) {
								//	pit.remove();
								//}
								
							}
							
						}
					}
				}
			}
			
			if(((ModelConfig)modelEntry.getValue()).getPageConfigs().isEmpty()){
				it.remove();
			}
		}
		
		Map<String, Object> subSystemNames = ReflectUtil.getVariableMap(CommonConstants.SubSystemNames.class);
		//检测MODEL中是否还有
		if(deepclonemodelConfigs.get("M915") == null){
			subSystemNames.remove("EMS");
			subSystemNum.remove("EMS");
		}
		if(deepclonemodelConfigs.get("M911") == null){
			subSystemNames.remove("CRM");
			subSystemNum.remove("CRM");
		}
		
		
        //将业务权限进行分类
        HashMap<String, RightDefine> mapdefineright = RightConstants.rightDefineMap;
        Map<String, Object> maprightmodel = ReflectUtil.getVariableMap(RightConstants.RightModel.class);
        Map<String, Object> maprighttype = ReflectUtil.getVariableMap(RightConstants.RightType.class);
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        
        String branchtype = branch.getBranchType();
        for (Map.Entry<String, Object> rightmodel : maprightmodel.entrySet()) {
        	Map<String, Object> mapmodel = new HashMap<String, Object>();
        	
        	if(CommonConstants.BranchRank.TOP.equals(branch.getRank())){
        		if(RightConstants.RightModel.HOTEL_CONTROL.equals(rightmodel.getValue()) || RightConstants.RightModel.APARTMENT_CONTROL.equals(rightmodel.getValue())){
        			//continue;
        		}
			}else{
				//当前是公寓的就过滤掉酒店,民宿
				if("2".equals(branchtype)){
					if(RightConstants.RightModel.HOUSE_CONTROL.equals(rightmodel.getValue()) || RightConstants.RightModel.HOTEL_CONTROL.equals(rightmodel.getValue())){
						continue;
					}
				//当前是酒店的就过滤掉公寓,民宿
				}else if("1".equals(branchtype)){
					if(RightConstants.RightModel.HOUSE_CONTROL.equals(rightmodel.getValue()) || RightConstants.RightModel.APARTMENT_CONTROL.equals(rightmodel.getValue())){
						continue;
					}
				}
			}
        	
        	mapmodel.put("model", rightmodel.getValue());
        	
        	List<Map<String, Object>> listrights = new ArrayList<Map<String, Object>>(); 
        	for (Map.Entry<String, RightDefine> rightdefine : mapdefineright.entrySet()) {
        		for (Map.Entry<String, Object> righttype : maprighttype.entrySet()) {
        			Map<String, Object> el = new HashMap<String, Object>();
                	if(rightmodel.getValue().equals(rightdefine.getValue().getRightModel()) && righttype.getValue().equals(rightdefine.getValue().getRightType())){
                		el.put("righttype", righttype.getKey());
                		el.put("name", righttype.getValue());
                		listrights.add(el);
                	}
                }
            }
        	mapmodel.put("listrights", listrights);
        	result.add(mapmodel);
        }
        JSONArray josnarray = JSONUtil.fromObject(result);
		
		mv.addObject("map", JSONUtil.fromObject(subSystemNum));
		mv.addObject("modelconfigs", deepclonemodelConfigs);
		mv.addObject("systemfunctions", JSONObject.fromObject(ReflectUtil
				.getVariableMap(CommonConstants.SystemFunctions.class)));
		mv.addObject("subSystemNames", JSONObject.fromObject(subSystemNames));
		mv.addObject("rightconstants", josnarray);

		String staffId = ((Map<?, ?>) (staffList.get(0))).get("STAFF_ID").toString();
		UserRole userrole = rightService.findRoleIdByStaffId(staffId);
		if (userrole != null) {
			roleId = userrole.getRoleId();
		} else {
			roleId = "";
		}
		Role role = rightService.findRoleById(roleId);
		List<RoleRelation> relations = rightService.findRoleRelationByRoleId(roleId);
		mv.addObject("userrole", userrole);
		mv.addObject("role", role);
		mv.addObject("loginData", loginUser);
		mv.addObject("relations", JSONArray.fromObject(relations));
		return mv;
	}

	// 保存权限的值跳转到房型的录入saveRightAndTurnToRoomTypeAdd.do
	@RequestMapping("/saveRightAndTurnToRoomTypeAdd.do")
	public void saveRightAndTurnToRoomTypeAdd(HttpServletRequest request, HttpServletResponse response,
			String role, String rights, String userrole) throws Exception {
		//数据上传或下载到本地的数据库
		Map<String, Object> map = new HashMap<String, Object>();
		String turnFlag = request.getParameter("turnFlag");
		String branchType =CommonConstants.SubBranchType.HOTEL;
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginUser.getStaff();
		
			if(turnFlag == null || !turnFlag.equals("turnFlag")){
				try{
					// 保存数据
					org.json.JSONObject r = new org.json.JSONObject(role);
					// delete RoleRelation
					if (r.has("ROLEID")) {
						rightService.removeRoleRelation(r.get("ROLEID").toString());
					}
					// save Role
					Role ro = (Role) ReflectUtil.setBean(r, "Role", r.has("ROLEID") ? null : "ROLEID", r.has("ROLEID") ? null
							: "SEQ_NEW_ROLE", null, null);
					ro.setRecordUser(staff.getStaffId());
					ro.setBranchId(staff.getBranchId());
					rightService.getHibernateTemplate().saveOrUpdate(ro);
					List<Role> roleList = new ArrayList();
					roleList.add(ro);
					map.put("Role", roleList);
			
					// save RoleRelation
					Map<String, String> params = new HashMap<String, String>();
					params.put("ROLEID", ro.getRoleId());
					params.put("RECORDUSER", staff.getStaffId());
					String keyConfig = "DATAID|SEQ_NEW_ROLERELATION";
					List<Object> rrs = ReflectUtil.setBeansFromJsonArray(rights, "RoleRelation", true, keyConfig, params, null);
					rightService.saveOrUpdateAll(rrs);
					map.put("RoleRelation", rrs);
			
					// save userrole
					org.json.JSONObject u = new org.json.JSONObject(userrole);
					UserRole uo = (UserRole) ReflectUtil.setBean(u, "UserRole", u.has("DATAID") ? null : "DATAID",
							u.has("DATAID") ? null : "SEQ_NEW_USERROLE", null, null);
					uo.setRecordUser(staff.getStaffId());
					uo.setRoleId(ro.getRoleId());
					uo.setStatus("1");
					uo.setBranchId(loginUser.getStaff().getBranchId());
					uo.setRecordUser(staff.getStaffId());
					rightService.getHibernateTemplate().saveOrUpdate(uo);
					List<UserRole> userRoleList = new ArrayList();
					userRoleList.add(uo);
					map.put("UserRole", userRoleList);
					//根据branchId查找branch
					Branch loginBranch = (Branch)initialService.findOneByProperties(Branch.class, "branchId", staff.getBranchId());
					initialService.dataUpDown(1, loginBranch.getBranchIp(), "initialStep4", map);
					JSONUtil.responseJSON(response, new AjaxResult(1,"save success!"));
				}catch (Exception e) {
					e.printStackTrace();
					logger.error("initalData step4 save staffRightAdd occurs error! step4! " + "\n" + e);		
				}
			}else if(turnFlag.equals("turnFlag")){
				JSONUtil.responseJSON(response, new AjaxResult(1,"save success!"));
			}	
	}
	
	
	
	/*// 保存权限的值跳转到房型的录入saveRightAndTurnToRoomTypeAdd.do
	@RequestMapping("/saveRightAndTurnToRoomTypeAdd.do")
	public ModelAndView saveRightAndTurnToRoomTypeAdd(HttpServletRequest request, HttpServletResponse response,
			String role, String rights, String userrole) throws Exception {
		//数据上传或下载到本地的数据库
		Map<String, Object> map = new HashMap<String, Object>();
		String turnFlag = request.getParameter("turnFlag");
		String branchType =CommonConstants.SubBranchType.HOTEL;
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginUser.getStaff();
		try{
			if(turnFlag == null || !turnFlag.equals("turnFlag")){
				// 保存数据
				org.json.JSONObject r = new org.json.JSONObject(role);
				// delete RoleRelation
				if (r.has("ROLEID")) {
					rightService.removeRoleRelation(r.get("ROLEID").toString());
				}
				// save Role
				Role ro = (Role) ReflectUtil.setBean(r, "Role", r.has("ROLEID") ? null : "ROLEID", r.has("ROLEID") ? null
						: "SEQ_NEW_ROLE", null, null);
				ro.setRecordUser(staff.getStaffId());
				ro.setBranchId(staff.getBranchId());
				rightService.getHibernateTemplate().saveOrUpdate(ro);
				List<Role> roleList = new ArrayList();
				roleList.add(ro);
				map.put("Role", roleList);
		
				// save RoleRelation
				Map<String, String> params = new HashMap<String, String>();
				params.put("ROLEID", ro.getRoleId());
				params.put("RECORDUSER", staff.getStaffId());
				String keyConfig = "DATAID|SEQ_NEW_ROLERELATION";
				List<Object> rrs = ReflectUtil.setBeansFromJsonArray(rights, "RoleRelation", true, keyConfig, params, null);
				rightService.saveOrUpdateAll(rrs);
				map.put("RoleRelation", rrs);
		
				// save userrole
				org.json.JSONObject u = new org.json.JSONObject(userrole);
				UserRole uo = (UserRole) ReflectUtil.setBean(u, "UserRole", u.has("DATAID") ? null : "DATAID",
						u.has("DATAID") ? null : "SEQ_NEW_USERROLE", null, null);
				uo.setRecordUser(staff.getStaffId());
				uo.setRoleId(ro.getRoleId());
				uo.setStatus("1");
				uo.setBranchId(loginUser.getStaff().getBranchId());
				uo.setRecordUser(staff.getStaffId());
				rightService.getHibernateTemplate().saveOrUpdate(uo);
				List<UserRole> userRoleList = new ArrayList();
				userRoleList.add(uo);
				map.put("UserRole", userRoleList);
				//根据branchId查找branch
				Branch loginBranch = (Branch)initialService.findOneByProperties(Branch.class, "branchId", staff.getBranchId());
				initialService.dataUpDown(1, loginBranch.getBranchIp(), "initialStep4", map);
			}	
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("initalData step4 save staffRightAdd occurs error! step4! " + "\n" + e);
		}	
		
		//+附带加上当前门店的branchType ，当前人的编号
		ModelAndView mv = new ModelAndView();
		List<?> result = initialService.findByProperties(Branch.class, "branchId", loginUser.getStaff().getBranchId());
		if(result != null && result.size()>0)
			branchType = ((Branch)result.get(0)).getBranchType();
		// 如果数据库没有数据，则传递一个flag给前台，前台调用的保存，删除的方法不同
		List<?> roomTypeList = rightService.findAllRoomType(loginUser.getStaff().getBranchId());
		if(roomTypeList == null || roomTypeList.size() <= 0){
			mv.addObject("newDataFlag", "newDataFlag");
		}
		mv.addObject("branchId", staff.getBranchId());
		mv.addObject("branchType", branchType);
		mv.addObject("recordUser", staff.getStaffId());
		mv.setViewName("page/initializtion/roomTypeAdd");
		return mv;
	}
	*/
	
	
	@SuppressWarnings("deprecation")
	@RequestMapping("/dealroomTypeData.do")
	public void dealroomTypeData(String modelId, String pageId, String args, String keyConfig, String dealType, String param,String currTarget,
			String frameParams, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String roomType ="";
		String branchId ="";
		SysParam sysParam = (SysParam) pageBuilder.findOneByProperties(SysParam.class, "paramType", "SYSTEMTYPE");
		String systemType = sysParam.getContent();
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginUser.getStaff();
		int result = CommonConstants.AJAXRESULT.FALSE;
		Map<String, String> paramMap = new HashMap<String, String>();

		String target = currTarget;
		if (target != null && !"".equals(target)) {
			
				List<Object> list = null;

				param = URLDecoder.decode(param, "UTF-8");
				if (param != null && !"".equals(param)) {
					JSONUtil.buildParamFromJSON(paramMap, param);
				}

				list = ReflectUtil.setBeansFromJsonArray(args, target, SystemConstants.DatabaseOperation.SAVE.equals(dealType), keyConfig, paramMap, null);

				if (list != null) {
					if (SystemConstants.DatabaseOperation.SAVE.equals(dealType)) {
						//保存room表
						for (Object object:list) {
							if(object instanceof Room){
								Room newRooms = (Room)object;
								newRooms.setRecordTime(new Date());
							} else if(object instanceof RoomType){
								RoomType newRoomType = (RoomType)object;
								newRoomType.setRecordTime(new Date());
							}
							
						}
						pageBuilder.saveOrUpdateAll(list);
						/*if(CommonConstants.SystemType.Branch.equals(systemType)){
							Map<String, Object> map = new HashMap<String, Object>();
							map.put(target, list);
							Branch loginBranch = (Branch)initialService.findOneByProperties(Branch.class, "branchId", staff.getBranchId());
							TransferServcie.getInstance().transferData(1, loginBranch.getBranchIp(), JSONUtil.fromObject(map).toString());
						}*/
						//保存房间的数量表roomStatus在这边进行操作数据会错误，在最后结束初始化导向时进行该表数据的插入			
					} else if (SystemConstants.DatabaseOperation.DELETE.equals(dealType)) {
						// pageBuilder.getHibernateTemplate().deleteAll(list);
						if(target.equals("RoomType")){
							for (Object object : list) {
								RoomType newRoomType = (RoomType)object;
								newRoomType.setStatus("0");
								newRoomType.setRecordTime(new Date());
								/*Field[] fields = object.getClass().getDeclaredFields();
								for (Field f : fields) {
									String fieldName = f.getName();
									
										if (fieldName.equalsIgnoreCase("status")) {
											ReflectUtil.setValue(object,"status", "0");	
										}
								}*/
								pageBuilder.getHibernateTemplate().saveOrUpdate(object);
								//将相应的房间置为无效
								branchId = ((RoomType)object).getRoomTypeKey().getBranchId();
								roomType = ((RoomType)object).getRoomTypeKey().getRoomType();
								initialService.updateRoomStateByRoomType(roomType, branchId);
								//将roomStatus 的状态置为无效
								initialService.updateRoomStatusTable(roomType, branchId);

							/*for (Object object : list) {
								Field[] fields = object.getClass().getDeclaredFields();
								for (Field f : fields) {
									String fieldName = f.getName();
									
										if (fieldName.equalsIgnoreCase("roomKey")) {
											ReflectUtil.setValue(fieldName, "status", "F");
											
										}
									
								}
								pageBuilder.getHibernateTemplate().saveOrUpdate(object);*/
							}
						}else{
							 JSONArray jsonArray = JSONArray.fromObject(args);
							 for (int i = 0; i < jsonArray.size(); i++) {  
			                        JSONObject info = jsonArray.getJSONObject(i);
			                        info.put("status", "0");
			                        info.put("recordTime", new Date());
							 }
							 //List<?> roomList = JSONArray.toList(jsonArray,Room.class);
							 List<?> roomList = ReflectUtil.setBeansFromJsonArray(jsonArray.toString(), target, SystemConstants.DatabaseOperation.SAVE.equals(dealType), keyConfig, paramMap, null);
							 pageBuilder.saveOrUpdateAll(roomList);
							 for (Object object : list) {
								 Room newRoom = (Room)object;
								/*Field[] fields = object.getClass().getDeclaredFields();
								for (Field f : fields) {
									String fieldName = f.getName();
										if (fieldName.equalsIgnoreCase("roomType")) {
											roomType = ReflectUtil.getValue(object,"roomType").toString();
										}										
								}*/
								 branchId = newRoom.getRoomKey().getBranchId();
								roomType = newRoom.getRoomType();
								//count一直减1
							 	initialService.subRoomStatusNum(roomType, branchId);
							 }
						}
					}
					result = CommonConstants.AJAXRESULT.SUCESS;
                  //记载初始化日志
					//recordRoomTypeLog(dealType, list, request);
				}
			
		} else {
			logger.error("page config error, save target can not be null or empty! page id " + pageId);
		}
		
		JSONUtil.responseJSON(response, new AjaxResult(result, null));
	}
	
	@SuppressWarnings("unused")
	private void recordRoomTypeLog(String dealType, List<?> beans, HttpServletRequest request) {
		String methodName = "roomTypeInitial";
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(
				RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginUser.getStaff();
		Method method;
		try {
			method = initialService.getClass().getDeclaredMethod(methodName, Staff.class, List.class);
			method.invoke(initialService, staff, beans);
		} catch (SecurityException e) {
			logger.error("roomTypeInitial error " + e);
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			logger.error("roomTypeInitial error " + e);
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			logger.error("roomTypeInitial error " + e);
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			logger.error("roomTypeInitial error " + e);
			e.printStackTrace();
		} catch (InvocationTargetException e) {	
			logger.error("roomTypeInitial error " + e);
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value= "/turnToRoomsAdd.do",produces = "application/json;charset = UTF-8")
	public ModelAndView turnToRoomsAdd(HttpServletRequest request,String branchType,String branchId) throws Exception {
		//+附带加上当前门店的branchType ，当前人的编号
		String addNewString = "";
		Integer forCount = 0;
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		List<?> result = initialService.findByProperties(Branch.class, "branchId", loginUser.getStaff().getBranchId());
		if(branchId.equals("undefined"))
			branchId = loginUser.getStaff().getBranchId();
		List<?> roomTypeList = rightService.findAllRoomType(branchId);
		Staff staff = loginUser.getStaff();
		ModelAndView mv = new ModelAndView();
		if(result != null && result.size()>0)
			branchType = ((Branch)result.get(0)).getBranchType();
		// 如果数据库没有数据，则传递一个flag给前台，前台调用的保存，删除的方法不同
		 
		List<Room> roomList = initialService.findAllRoom(branchId);
		if(roomList == null || roomList.size() <= 0){
			mv.addObject("newDataFlag", "newDataFlag");
		}
		
		mv.setViewName("page/initializtion/roomsOfBranchAdd");
		mv.addObject("branchId", staff.getBranchId());
		mv.addObject("branchType", branchType);
		mv.addObject("recordUser", staff.getStaffId());
		return mv;
	}
	
	@RequestMapping("/turnToRoomsshow.do")
	public void turnToRoomsshow(HttpServletRequest request, HttpServletResponse response,String branchType,String branchId) throws Exception{
		String delString = request.getParameter("delArray");
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginUser.getStaff();
		//查询已有的房型到页面上显示
		List<?> result = initialService.findByProperties(Branch.class, "branchId", loginUser.getStaff().getBranchId());
		if(result != null && result.size()>0)
			branchType = ((Branch)result.get(0)).getBranchType();
		List<Room> roomList = initialService.findAllRoom(branchId);
		List<?> roomTypeList = rightService.findAllRoomType(branchId);
		if(roomList == null || roomList.size() <= 0){
			List<Room> roomListNew = new ArrayList();
			for(int i = 0; i < roomTypeList.size(); i++ ){
				String roomIdValue = (i+1)+"01";
				roomListNew.add(new Room(new RoomKey(branchId,roomIdValue),branchType,((RoomType)roomTypeList.get(i)).getRoomTypeKey().getRoomType(),Integer.toString(i+1), new Date(),staff.getStaffId(),"1"));		
			}
			
			if(delString != null && delString.length() > 0){
				String[] delArray = delString.split(",");
				for(int j = 0; j < delArray.length; j++){
					for (int k = 0; k < roomListNew.size(); k++){
						if(delArray[j].equals(roomListNew.get(k).getRoomKey().getRoomId())){
							roomListNew.remove(k);
						} 	
					}	
				}
			}
			JSONUtil.responseJSON(response,JSONUtil.buildReportJSON(roomListNew));
		}else{
			/*Integer forCount = roomList.size();
			for(int i = 0; i < roomTypeList.size(); i++ ){
				String currRoomType = ((RoomType)roomTypeList.get(i)).getRoomTypeKey().getRoomType().toString();
				List<?> roomOfCurrRoomType = initialService.findAllRoomByTwo(branchId, currRoomType);
				if(roomOfCurrRoomType == null){
					String roomIdValue = forCount + 1 +"01";
					roomList.add(new Room(new RoomKey(branchId,roomIdValue),branchType,((RoomType)roomTypeList.get(i)).getRoomTypeKey().getRoomType(),Integer.toString(forCount), new Date(),staff.getStaffId(),"1"));		
					forCount++;
				}
			}*/
			JSONUtil.responseJSON(response,JSONUtil.buildReportJSON(roomList));
		}
	}
	
	@RequestMapping("/updateInitialState.do")
	public void updateInitialState(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//将roomStatus表的数据进行补全
		//数据上传或下载到本地的数据库
		Map<String, Object> map = new HashMap<String, Object>();
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String loginbranchId = loginUser.getStaff().getBranchId();
		List<Room> list = initialService.findAllRoom(loginbranchId);
		try{
			for(int i = 0; i < list.size(); i++){
				String branchId = ((Room)(list.get(i))).getRoomKey().getBranchId();
				String roomType = ((Room)(list.get(i))).getRoomType();
				RoomStatus oneRoomStatus = (RoomStatus)initialService.findOneByProperties(RoomStatus.class, "branchId", branchId, "roomType",roomType);
				if(oneRoomStatus != null){
					int count = oneRoomStatus.getCount()+ 1;
					oneRoomStatus.setCount(count);
					int sellcount = oneRoomStatus.getSellnum() + 1;
					oneRoomStatus.setSellnum(sellcount);
					pageBuilder.getHibernateTemplate().saveOrUpdate(oneRoomStatus);
				}else{
					RoomStatus newRoomStatus = new RoomStatus();
					String logId = this.initialService.getCloudOrLocalSequence("SEQ_NEW_ROOMSTATUS");
					String branchName = this.initialService.findBranchNameByBranchId(branchId);
					newRoomStatus.setBranchId(branchId);
					newRoomStatus.setBranchName(branchName);
					newRoomStatus.setCampaigns(0);
					newRoomStatus.setCount(1);
					newRoomStatus.setInvalidnum(0);
					newRoomStatus.setLogId(logId);
					newRoomStatus.setNightnum(0);
					newRoomStatus.setRecordTime(new Date());
					newRoomStatus.setRecordUser(loginUser.getStaff().getStaffId());
					newRoomStatus.setRoomType(roomType);
					newRoomStatus.setSellnum(1);
					newRoomStatus.setStopnum(0);
					newRoomStatus.setValidnum(0);
					initialService.save(newRoomStatus);		
				}
			}
			
			List<RoomType> roomTypeResult = initialService.findByProperties(RoomType.class, "roomTypeKey.branchId", loginbranchId, new Object[] { "status", "1" });
			if (roomTypeResult.size() > 0) {
				for (int j = 0; j < roomTypeResult.size(); j++) {
					RoomType roomTypebean = roomTypeResult.get(j);
					String branchId = loginbranchId;
					String roomType = roomTypebean.getRoomTypeKey().getRoomType();
					String theme = roomTypebean.getTheme();
					// 根据门店及房型查询价格信息
					
					// 调用价格调整方法，当系统初始化时将所有公寓房间价的基准价格save到房价汇总表中
					if("1".equals(theme)){
						RoomPrice roomPrice = (RoomPrice) initialService.findOneByProperties(RoomPrice.class, "roomPriceId.branchId", branchId, "roomPriceId.roomType",roomType,"rpKind", "1", "memberRank",CommonConstants.MembenRank.NM);
						RoomPrice hourPrice = (RoomPrice) initialService.findOneByProperties(RoomPrice.class, "roomPriceId.branchId", branchId, "roomPriceId.roomType",roomType,"rpKind", "2", "memberRank",CommonConstants.MembenRank.NM);
						BasePriceUtile.addHotelBasePrice(branchId, roomType, theme, roomPrice.getRoomPrice().toString(), hourPrice.getRoomPrice().toString());
					}else if("2".equals(theme)){
						RoomPrice roomPrice = (RoomPrice) initialService.findOneByProperties(RoomPrice.class, "roomPriceId.branchId", branchId, "roomPriceId.roomType",roomType,"memberRank",CommonConstants.MembenRank.NM);
						BasePriceUtile.addApartmentBasePrice(branchId, roomType, theme, roomPrice.getRoomPrice().toString());						
					}
				}
				
			}
			//将修改好的roomStatus的数据的值进行上传 
			List<?> roomStatusData = initialService.queryAllRoomStatusData(loginUser.getStaff().getBranchId());
			map.put("RoomStatus", roomStatusData);
			//修改初始化的flag
			//initialService.updateInitialState();
			//将sda账号status设为0
			//initialService.updateInitialLoginUser(loginUser.getStaff().getStaffId());
			Staff stf = (Staff)initialService.findOneByProperties(Staff.class, "staffId", loginUser.getStaff().getStaffId());
			stf.setStatus("0");
			pageBuilder.getHibernateTemplate().saveOrUpdate(stf);
			List<Staff> updateInitialStaffList = new ArrayList();
			updateInitialStaffList.add(stf);
			map.put("Staff", updateInitialStaffList);	
			//生成保洁预排表
			//CleanTask cleanTask = new CleanTask("cleanInitial");
			//cleanTask.run();
				Boolean cleanSaveFlag = this.cleanTableUp(request, response);
				//清洁数据下传，此处不可上传，业务生成保洁预排的定时任务里已经上传过
				if(cleanSaveFlag == true){
					List<?> cleanList = initialService.queryCleanListData(loginUser.getStaff().getBranchId());
					map.put("Clean", cleanList);
				}
			//查询该门店下所有的房型和房间和房价，数据下传
			List<?> allRoomTypeList = initialService.queryAllRoomType(loginUser.getStaff().getBranchId());
			List<?> allRoomList = initialService.queryAllRoom(loginUser.getStaff().getBranchId());
			List<?> allRoomPriceList = initialService.queryAllRoomPrice(loginUser.getStaff().getBranchId());
			map.put("RoomType", allRoomTypeList);	
			map.put("Room", allRoomList);
			map.put("RoomPrice", allRoomPriceList);
			//根据branchId查找branch
			Branch loginBranch = (Branch)initialService.findOneByProperties(Branch.class, "branchId", loginUser.getStaff().getBranchId());
			initialService.dataUpDown(1, loginBranch.getBranchIp(), "initialStep8", map);
			
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("initalData step8 update initialState occurs error! step8! " + "\n" + e);
		}	
		Integer result = CommonConstants.AJAXRESULT.SUCESS;
		JSONUtil.responseJSON(response, new AjaxResult(result, null));
	}
	
	
	
	public boolean cleanTableUp(HttpServletRequest request, HttpServletResponse response) throws Exception{
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");
		String todayTime = sdf2.format(new Date());
		List<?> judge = cleanService.findByProperties(Clean.class, "recordTime", sdf2.parse(todayTime));
		SysParam cleanCount = (SysParam)cleanService.findOneByProperties(SysParam.class, "paramDesc", loginUser.getStaff().getBranchId(), "paramType","CLEANAMOUNT");
		
		if(cleanCount != null){
			Clean clean=null;
			String timeArea = null;
			String sequences = null;
			List<Clean> cleanList = new ArrayList<Clean>();
			Boolean flag = true;
			List<?> restAmount = cleanService.getDefaultParam();
			
			Calendar todayCal = Calendar.getInstance();
			todayCal.set(Calendar.DATE, 1);//本月1号
			Date currentFirst = todayCal.getTime();//本月1号
			todayCal.roll(Calendar.DATE, -1);// 本月最后一天
			int dayOFMonth = todayCal.get(Calendar.DATE);//当月天数
			try{
				if (judge == null || judge.size() <= 0) {//数据库里没数据，要把本月的数据也插进去
					Boolean flag2 = true;
					for (int m = 0; m < dayOFMonth; m++) {
						todayCal.setTime(currentFirst);
						todayCal.add(Calendar.DAY_OF_MONTH, m);
						Date nextDate2 = todayCal.getTime();
						String newstrdate = sdf.format(nextDate2);
						String newstrdate2 = sdf2.format(nextDate2);
						Date cleandate2 = sdf2.parse(newstrdate2);
						for (int n = 0; n < 2; n++) {
							clean = new Clean();
							sequences = this.initialService.getCloudOrLocalSequence("SEQ_CLEAN_ID");
							clean.setCleanId(newstrdate + loginUser.getStaff().getBranchId() + sequences);
							clean.setBranchId(loginUser.getStaff().getBranchId());
							clean.setCleanDate(cleandate2);
							//clean.setRoomId("");
							//clean.setCleanPerson("");
							//clean.setRecordUser("");
							clean.setRecordTime(new Date());
							//clean.setCleanApplyId("");
							if (flag2) {
								timeArea = "0";
								flag2 = false;
							} else {
								timeArea = "1";
								flag2 = true;
							}
							clean.setTimeArea(timeArea);
							clean.setRestAmount(Integer.parseInt(((Map<?, ?>) restAmount.get(0)).get("CONTENT").toString()));
							cleanList.add(clean);
							cleanService.save(clean);
						}
					}
				
				}
				return true;
			}catch(Exception e){
				return false;
			}
			
		} else {
			return false;
		}
					
	}
	
	@RequestMapping("/roomTypeInDatabaseCheck.do")
	public void roomTypeInDatabaseCheck(HttpServletRequest request, HttpServletResponse response,String branchId,String titleList) throws Exception{
		Integer result = CommonConstants.AJAXRESULT.FALSE;
		if(initialService.checkRoomTypeInDB(branchId,titleList)){
			result = CommonConstants.AJAXRESULT.SUCESS;
		} else {
			result = CommonConstants.AJAXRESULT.FALSE;
		}
		JSONUtil.responseJSON(response, new AjaxResult(result, null));
	}
	
	
	
	
	@RequestMapping("/rpInitialByRoomType.do")
	public void rpInitialByRoomType(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String branchId = loginuser.getStaff().getBranchId();
		Branch branchid = (Branch) pmsmanageService.findOneByProperties(Branch.class, "branchId", branchId);
		String theme = branchid.getBranchType();
		//List<?> rpInitializejudge = PmsmanageService.getRpInitializejudge(branchId, theme);
		List<?> rpInitialCount = initialService.getRpInitializeByBranchId(branchId);
		String a = null;
		List<?> rpsetup = pmsmanageService.getRpsetup();
		List<?> rpbranchid = pmsmanageService.getRpbranchid();
		List<?> rproomtype = pmsmanageService.getRproomtype(branchId);
		String discountdata = ((Map<?, ?>) rpsetup.get(0)).get("DISCOUNT") == null ? "" : ((Map<?, ?>) rpsetup.get(0))
				.get("DISCOUNT").toString();
		if (null == discountdata || discountdata.equals("")) {
			a = "请先去配置会员等级等相关数据！";
		} else if (null == rpbranchid || rpbranchid.size() == 0) {
			a = "请先去配置门店等相关数据！";
		} else if (null == rproomtype || rproomtype.size() == 0) {
			a = "请先点击保存房型！";
		} else if (null == rpInitialCount || rpInitialCount.size() == 0) {
			List<?> rproomtypeInitialize = pmsmanageService.getRproomtypeInitialize(theme, branchId);
			String rpKind = null;
			if (theme.equals("1")) {
				rpKind = "1";
				for (int i = 0; i < rproomtypeInitialize.size(); i++) {
					String roomtype = ((Map<?, ?>) rproomtypeInitialize.get(i)).get("ROOMTYPE").toString();
					List<?> rpidInitialize = auditBasicService.getRpidInitialize();
					SysParam sysInitPrice = (SysParam) initialService.findOneByProperties(SysParam.class, "paramType", "APARTMENTINITPRICE","paramName","price","status","1");
					Double romprice = Double.parseDouble(sysInitPrice.getContent());
					for (int j = 0; j < rpidInitialize.size(); j++) {
						String discount = ((Map<?, ?>) rpidInitialize.get(j)).get("DISCOUNT").toString();
						String rpid = ((Map<?, ?>) rpidInitialize.get(j)).get("PARAMNAME").toString();
						String rpname = ((Map<?, ?>) rpidInitialize.get(j)).get("PARAMDESC").toString();
						String memberrank = ((Map<?, ?>) rpidInitialize.get(j)).get("CONTENT").toString();
						Double d = new Double(discount) / 100;
						Format f = new DecimalFormat("0.00");
						Double dscount = Double.parseDouble(f.format(d));
						RoomPrice rprice = new RoomPrice();
						RoomPriceId roompriceid = new RoomPriceId();
						roompriceid.setBranchId(branchId);
						roompriceid.setRoomType(roomtype);
						roompriceid.setRpId(rpid);
						roompriceid.setRpKind("2");
						roompriceid.setStatus("1");
						rprice.setRoomPriceId(roompriceid);
						rprice.setRpName(rpname);
						rprice.setRoomPrice(romprice * dscount);
						rprice.setRpType("1");
						rprice.setDiscount(dscount);
						rprice.setRecordTime(new Date());
						rprice.setRecordUser(staff.getStaffId());
						rprice.setMemberRank(memberrank);
						rprice.setTheme(theme);
						rprice.setState("5");
						pmsmanageService.save(rprice);

					}
				}

			} else if (theme.equals("2")) {
				rpKind = "3";
			} else if (theme.equals("3")) {

			}
			for (int i = 0; i < rproomtypeInitialize.size(); i++) {
				String roomtype = ((Map<?, ?>) rproomtypeInitialize.get(i)).get("ROOMTYPE").toString();
				List<?> rpidInitialize = auditBasicService.getRpidInitialize();
				SysParam sysInitPrice = (SysParam) initialService.findOneByProperties(SysParam.class, "paramType", "APARTMENTINITPRICE","paramName","price","status","1");
				Double romprice = Double.parseDouble(sysInitPrice.getContent());
				for (int j = 0; j < rpidInitialize.size(); j++) {
					String discount = ((Map<?, ?>) rpidInitialize.get(j)).get("DISCOUNT").toString();
					String rpid = ((Map<?, ?>) rpidInitialize.get(j)).get("PARAMNAME").toString();
					String rpname = ((Map<?, ?>) rpidInitialize.get(j)).get("PARAMDESC").toString();
					String memberrank = ((Map<?, ?>) rpidInitialize.get(j)).get("CONTENT").toString();
					Double d = new Double(discount) / 100;
					Format f = new DecimalFormat("0.00");
					Double dscount = Double.parseDouble(f.format(d));
					RoomPrice rprice = new RoomPrice();
					RoomPriceId roompriceid = new RoomPriceId();
					roompriceid.setBranchId(branchId);
					roompriceid.setRoomType(roomtype);
					roompriceid.setRpId(rpid);
					roompriceid.setRpKind(rpKind);
					roompriceid.setStatus("1");
					rprice.setRoomPriceId(roompriceid);
					rprice.setRpName(rpname);
					rprice.setRoomPrice(romprice * dscount);
					rprice.setRpType("1");
					rprice.setDiscount(dscount);
					rprice.setRecordTime(new Date());
					rprice.setRecordUser(staff.getStaffId());
					rprice.setMemberRank(memberrank);
					rprice.setTheme(theme);
					rprice.setState("5");
					pmsmanageService.save(rprice);
				}
			}
		} else if(rpInitialCount.size() > 0){
			List<?> rproomtypeInitialize = pmsmanageService.getRproomtypeInitialize(theme, branchId);
			if(CommonConstants.AJAXRESULT.SUCESS == roomPriceInDB(request,response,branchId)){
				a = "当前门店房价数据已配置，无须初始化";
			} else {
				String rpKind = null;
				if (theme.equals("1")) {
					rpKind = "1";
					for (int i = 0; i < rproomtypeInitialize.size(); i++) {
						String roomtype = ((Map<?, ?>) rproomtypeInitialize.get(i)).get("ROOMTYPE").toString();
						Boolean isInitial = initialService.queryCurrentRoomTypeIsInitial(branchId, roomtype, "2");
						if(!isInitial){
							List<RoomPrice> errorInitial = initialService.errorRoomTypeIsInitial(branchId, roomtype, "2");
							if(errorInitial != null && errorInitial.size() > 0){
								for(int k = 0; k < errorInitial.size(); k++){
									initialService.delete(errorInitial.get(k));
								}
							}
							List<?> rpidInitialize = auditBasicService.getRpidInitialize();
							SysParam sysInitPrice = (SysParam) initialService.findOneByProperties(SysParam.class, "paramType", "APARTMENTINITPRICE","paramName","price","status","1");
							Double romprice = Double.parseDouble(sysInitPrice.getContent());
							for (int j = 0; j < rpidInitialize.size(); j++) {
								String discount = ((Map<?, ?>) rpidInitialize.get(j)).get("DISCOUNT").toString();
								String rpid = ((Map<?, ?>) rpidInitialize.get(j)).get("PARAMNAME").toString();
								String rpname = ((Map<?, ?>) rpidInitialize.get(j)).get("PARAMDESC").toString();
								String memberrank = ((Map<?, ?>) rpidInitialize.get(j)).get("CONTENT").toString();
								Double d = new Double(discount) / 100;
								Format f = new DecimalFormat("0.00");
								Double dscount = Double.parseDouble(f.format(d));
								RoomPrice rprice = new RoomPrice();
								RoomPriceId roompriceid = new RoomPriceId();
								roompriceid.setBranchId(branchId);
								roompriceid.setRoomType(roomtype);
								roompriceid.setRpId(rpid);
								roompriceid.setRpKind("2");
								roompriceid.setStatus("1");
								rprice.setRoomPriceId(roompriceid);
								rprice.setRpName(rpname);
								rprice.setRoomPrice(romprice * dscount);
								rprice.setRpType("1");
								rprice.setDiscount(dscount);
								rprice.setRecordTime(new Date());
								rprice.setRecordUser(staff.getStaffId());
								rprice.setMemberRank(memberrank);
								rprice.setTheme(theme);
								rprice.setState("5");
								pmsmanageService.save(rprice);
							}
						}	
					}
				} else if (theme.equals("2")) {
					rpKind = "3";
				} else if (theme.equals("3")) {

				}
				for (int i = 0; i < rproomtypeInitialize.size(); i++) {
					String roomtype = ((Map<?, ?>) rproomtypeInitialize.get(i)).get("ROOMTYPE").toString();
					Boolean isInitial = initialService.queryCurrentRoomTypeIsInitial(branchId, roomtype, rpKind);
					if(!isInitial){
						List<RoomPrice> errorInitial = initialService.errorRoomTypeIsInitial(branchId, roomtype, rpKind);
						if(errorInitial != null && errorInitial.size() > 0){
							for(int k = 0; k < errorInitial.size(); k++){
								initialService.delete(errorInitial.get(k));
							}
						}
						List<?> rpidInitialize = auditBasicService.getRpidInitialize();
						SysParam sysInitPrice = (SysParam) initialService.findOneByProperties(SysParam.class, "paramType", "APARTMENTINITPRICE","paramName","price","status","1");
						Double romprice = Double.parseDouble(sysInitPrice.getContent());
						for (int j = 0; j < rpidInitialize.size(); j++) {
							String discount = ((Map<?, ?>) rpidInitialize.get(j)).get("DISCOUNT").toString();
							String rpid = ((Map<?, ?>) rpidInitialize.get(j)).get("PARAMNAME").toString();
							String rpname = ((Map<?, ?>) rpidInitialize.get(j)).get("PARAMDESC").toString();
							String memberrank = ((Map<?, ?>) rpidInitialize.get(j)).get("CONTENT").toString();
							Double d = new Double(discount) / 100;
							Format f = new DecimalFormat("0.00");
							Double dscount = Double.parseDouble(f.format(d));
							RoomPrice rprice = new RoomPrice();
							RoomPriceId roompriceid = new RoomPriceId();
							roompriceid.setBranchId(branchId);
							roompriceid.setRoomType(roomtype);
							roompriceid.setRpId(rpid);
							roompriceid.setRpKind(rpKind);
							roompriceid.setStatus("1");
							rprice.setRoomPriceId(roompriceid);
							rprice.setRpName(rpname);
							rprice.setRoomPrice(romprice * dscount);
							rprice.setRpType("1");
							rprice.setDiscount(dscount);
							rprice.setRecordTime(new Date());
							rprice.setRecordUser(staff.getStaffId());
							rprice.setMemberRank(memberrank);
							rprice.setTheme(theme);
							rprice.setState("5");
							pmsmanageService.save(rprice);
						}
					}
				}	
			}
		}else {
			a = "当前门店房价数据已配置，无须初始化";
		}
		JSONUtil.responseJSON(response, new AjaxResult(1, a));
	}
	
	@RequestMapping("/roomPriceInDBCheck.do")
	public void roomPriceInDBCheck(HttpServletRequest request, HttpServletResponse response,String branchId) throws Exception{
		int count =0;
		Integer result = CommonConstants.AJAXRESULT.FALSE;
		Branch branchid = (Branch) pmsmanageService.findOneByProperties(Branch.class, "branchId", branchId);
		String theme = branchid.getBranchType();
		List<?> rpsetup = pmsmanageService.getRpsetup();
		List<?> rproomtype = pmsmanageService.getRproomtype(branchId);
		List<?> rpInitializejudge = initialService.getRpInitializeByBranchId(branchId);
		List<?> rproomtypeInitialize = pmsmanageService.getRproomtypeInitialize(theme, branchId);
		if(rpsetup != null && rpsetup.size() > 0 && rproomtype != null && rproomtype.size() > 0 && rpInitializejudge != null && rpInitializejudge.size() > 0){		
			for (int i = 0; i < rproomtypeInitialize.size(); i++) {
				String roomtype = ((Map<?, ?>) rproomtypeInitialize.get(i)).get("ROOMTYPE").toString();
				Boolean isInitial = initialService.queryCurrentRoomTypeCount(theme, branchId, roomtype);
				if(!isInitial)
					count++;
			}			
			if(count != 0){
				result = CommonConstants.AJAXRESULT.FALSE;
			} else {
				result = CommonConstants.AJAXRESULT.SUCESS;
			}			
		} else {
			result = CommonConstants.AJAXRESULT.FALSE;
		}
		JSONUtil.responseJSON(response, new AjaxResult(result, null));
	}
	
	public Integer roomPriceInDB(HttpServletRequest request, HttpServletResponse response,String branchId) throws Exception{
		int count =0;
		Integer result = CommonConstants.AJAXRESULT.FALSE;
		Branch branchid = (Branch) pmsmanageService.findOneByProperties(Branch.class, "branchId", branchId);
		String theme = branchid.getBranchType();
		List<?> rpsetup = pmsmanageService.getRpsetup();
		List<?> rproomtype = pmsmanageService.getRproomtype(branchId);
		List<?> rpInitializejudge = initialService.getRpInitializeByBranchId(branchId);
		List<?> rproomtypeInitialize = pmsmanageService.getRproomtypeInitialize(theme, branchId);
		if(rpsetup != null && rpsetup.size() > 0 && rproomtype != null && rproomtype.size() > 0 && rpInitializejudge != null && rpInitializejudge.size() > 0){
			for (int i = 0; i < rproomtypeInitialize.size(); i++) {
				String roomtype = ((Map<?, ?>) rproomtypeInitialize.get(i)).get("ROOMTYPE").toString();
				Boolean isInitial = initialService.queryCurrentRoomTypeCount(theme, branchId, roomtype);
				if(!isInitial)
					count++;
			}			
			if(count != 0){
				result = CommonConstants.AJAXRESULT.FALSE;
			} else {
				result = CommonConstants.AJAXRESULT.SUCESS;
			}
		} else {
			result = CommonConstants.AJAXRESULT.FALSE;
		}
		return result;
	}
	
	@RequestMapping("/queryRoomTypeInCurrBranch.do")
		public void queryRoomTypeInCurrBranch(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Integer result = CommonConstants.AJAXRESULT.FALSE;
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginuser.getStaff().getBranchId();
		List<?> rproomtype = pmsmanageService.getRproomtype(branchId);
		result = CommonConstants.AJAXRESULT.SUCESS;
		JSONUtil.responseJSON(response,JSONUtil.buildReportJSON(rproomtype));
		//JSONUtil.responseJSON(response, new AjaxResult(result, null,JSONUtil.buildReportJSON(rproomtype)));
	}
	
	@RequestMapping("/uniqueInDb.do")
	public void uniqueInDb(String target, String colName, String colValue, String branchId, String branchIdValue, HttpServletResponse response)
			throws Exception { 
		Object tar = ReflectUtil.getBeanByName(target);
		List<?> bean = null;
		if (tar instanceof Room) {
			bean = pageBuilder.findBySQL("queryRoomByRoomId", new String[] {colValue, branchIdValue  }, true);
		} else if (tar instanceof RoomType) {
			bean = pageBuilder.findBySQL("queryApartRoomType", new String[] {colValue, branchIdValue  }, true);
		}

		response.getWriter().write("{ \"result\":" + (bean.isEmpty()) + " }");
	}
	
	
	@RequestMapping("/checkPersonRepeat.do")
	public void checkPersonRepeat(HttpServletRequest request, HttpServletResponse response) throws ParseException {
		String mobile = request.getParameter("mobile");
		String idcard = request.getParameter("idcard");
		String loginName = request.getParameter("loginName");
		String staffId = request.getParameter("staffId");
		String flag = "add";
		if(staffId != null && staffId.length()==8){
			flag = "update";
		}
		List<?> infoByPhone = initialService.checkStaffRepeat( "mobile", mobile);
		List<?> infoByLoingName = initialService.checkStaffRepeat("login_name", loginName);
		List<?> infoBYIdCard = initialService.checkStaffRepeat("idcard", idcard);
		
		if ((flag.equals("add") && infoByPhone != null && infoByPhone.size() >= 1) || (flag.equals("update") && infoByPhone != null && infoByPhone.size() > 1)) {
			JSONUtil.responseJSON(response, new AjaxResult(1, "已有该手机号，请重新输入!"));
		} else if ((flag.equals("add") && infoByLoingName != null && infoByLoingName.size() >= 1) || (flag.equals("update") && infoByLoingName != null  && infoByLoingName.size() > 1)) {
			JSONUtil.responseJSON(response, new AjaxResult(2, "已有该登录名，请重新输入!"));
		} else if ((flag.equals("add") && infoBYIdCard != null  && infoBYIdCard.size() >= 1) || (flag.equals("update") && infoBYIdCard != null && infoBYIdCard.size() > 1)) {
			JSONUtil.responseJSON(response, new AjaxResult(3, "已有该身份证号，请重新输入!"));
		} else { 
			JSONUtil.responseJSON(response, new AjaxResult(4, "成功!"));
		}
	}
	
	
	/* 获得汉语拼音首字母
    *
    * @param chines 汉字
    * @return
    */
   public static String getAlpha(String chines) {
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
}