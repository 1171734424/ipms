package com.ideassoft.core.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hsqldb.lib.StringUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.ideassoft.basic.service.MarketingBasicService;
import com.ideassoft.bean.Branch;
import com.ideassoft.bean.CashBox;
import com.ideassoft.bean.DialogBean;
import com.ideassoft.bean.RightDefine;
import com.ideassoft.bean.Role;
import com.ideassoft.bean.RoleRelation;
import com.ideassoft.bean.ShiftTime;
import com.ideassoft.bean.Staff;
import com.ideassoft.bean.SysParam;
import com.ideassoft.bean.UserRole;
import com.ideassoft.core.ajax.AjaxResult;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.bean.pageConfig.ColumnConfig;
import com.ideassoft.core.bean.pageConfig.ModelConfig;
import com.ideassoft.core.bean.pageConfig.PageConfig;
import com.ideassoft.core.constants.CommonConstants;
import com.ideassoft.core.constants.CommonParams;
import com.ideassoft.core.constants.RightConstants;
import com.ideassoft.core.constants.SystemConstants;
import com.ideassoft.core.page.PageBuilder;
import com.ideassoft.core.page.Pagination;
import com.ideassoft.crm.service.InitialService;
import com.ideassoft.crm.service.LogService;
import com.ideassoft.crm.service.RightService;
import com.ideassoft.crm.service.UserService;
import com.ideassoft.hotel.service.HotelLeftmenuService;
import com.ideassoft.util.CloneUtil;
import com.ideassoft.util.HeartBeatClient;
import com.ideassoft.util.ImportUtil;
import com.ideassoft.util.JSONUtil;
import com.ideassoft.util.MD5Util;
import com.ideassoft.util.ReflectUtil;
import com.ideassoft.util.RegExUtil;
import com.ideassoft.util.RequestUtil;
import com.ideassoft.util.SpringUtil;

/**
 * 页面控制器
 * 
 * @author ZenShin
 * @time 2014-04-16
 */
@Transactional
@Controller
public class PageController {

	private static Logger logger = Logger.getLogger(PageController.class);

	@Autowired
	PageBuilder pageBuilder;

	@Autowired
	RightService rightService;
	
	@Autowired
	LogService logService;

	@Autowired
	InitialService initialService;
	
	@Autowired
	HotelLeftmenuService leftmenuService;
	
	@Autowired
	UserService userService;
	@Autowired
    MarketingBasicService marketingBasicService;

	private static Comparator<String> comparator = new Comparator<String>() {
		public int compare(String src, String tar) {
			return tar.compareTo(src);
		}
	};

	@RequestMapping("/loadMainFrame.do")
	public ModelAndView loadPageFrame(HttpServletRequest request) throws Exception {
		String bigBranchType = "";
//		String nextStepValue ="1";
		String branchname = "Admin的店";
		boolean houseRightflag = false;
		boolean pmsFlag = false;
		UserRole userrole = null;
		String branchtype = null;
		String branchrank = null;
		SysParam CheckOutAUDIT = null;
		ModelAndView mv = new ModelAndView();
		
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		
		//民宿业务权限
		HashMap<String, RightDefine> mapdefineright = RightConstants.rightDefineMap;
        Map<String, Object> maprighttype = ReflectUtil.getVariableMap(RightConstants.RightType.class);
        String modelName = "";
        
        Branch bigBranch = marketingBasicService.queryLoginUserBType(loginUser.getStaff().getBranchId());
        
       if (CommonConstants.SystemLevel.MarketCenter.equals(bigBranch.getRank())) {
        	modelName = RightConstants.RightModel.HOUSE_CONTROL;
        } else if (CommonConstants.SystemTheme.HOTEL.equals(bigBranch.getBranchType())) {
        	modelName = RightConstants.RightModel.HOTEL_CONTROL;
        } else if (CommonConstants.SystemTheme.APARTMENTS.equals(bigBranch.getBranchType())) {
        	modelName = RightConstants.RightModel.APARTMENT_CONTROL;
    		
        }
       List<Map<String, Object>> listrights = new ArrayList<Map<String, Object>>(); 
		for (Map.Entry<String, RightDefine> rightdefine : mapdefineright.entrySet()) {
			for (Map.Entry<String, Object> righttype : maprighttype.entrySet()) {
				Map<String, Object> el = new HashMap<String, Object>();
		    	if(modelName.equals(rightdefine.getValue().getRightModel()) && righttype.getValue().equals(rightdefine.getValue().getRightType())){
		    		el.put("righttype", righttype.getKey());
		    		el.put("name", righttype.getValue());
		    		listrights.add(el);
		    	}
		    }
		}
		
		//当前登录人的业务权限
    	List<Role> sysRoles = loginUser.getRightsList();
		List<RoleRelation> roleRelations;
    	if (sysRoles != null && !sysRoles.isEmpty()) {
			for (int i = 0; i < sysRoles.size(); i++) {
				roleRelations = sysRoles.get(i).getRoleRelation();
				if (roleRelations != null && !roleRelations.isEmpty()) {
					for (int j = 0; j < roleRelations.size(); j++) {
						for (int aa = 0; aa < listrights.size(); aa++) {
							if (roleRelations.get(j).getFuncrightId() != null && roleRelations.get(j).getFuncrightId().equals(((Map<?, ?>) listrights.get(aa)).get("righttype").toString())
									&& CommonConstants.SystemLevel.MarketCenter.equals(bigBranch.getRank())) {
								houseRightflag = true;
								pmsFlag = true;
								break;
							} else if (roleRelations.get(j).getFuncrightId() != null && roleRelations.get(j).getFuncrightId().equals(((Map<?, ?>) listrights.get(aa)).get("righttype").toString())
									&& !CommonConstants.SystemLevel.MarketCenter.equals(bigBranch.getRank())) {
								pmsFlag = true;
								break;
							}
						}
					}
				}
			}
		}
		
		
		//查找loginUser 的branchType
		String staffid = loginUser.getStaff().getStaffId();
		Branch branch = (Branch) logService.findById(Branch.class, loginUser.getStaff().getBranchId());
		SysParam systemtype = (SysParam) leftmenuService.findOneByProperties(SysParam.class, "paramType", "SYSTEMTYPE",
				"status", "1");
		String stype = systemtype.getContent().toString();
		if(branch != null){
			bigBranchType = branch.getRank();
			branchname = branch.getBranchName().toString();
			branchtype = branch.getBranchType().toString();
			branchrank = branch.getRank().toString();
			if(houseRightflag){
				branchtype = "3";
			}else if(stype.equals(CommonConstants.SystemType.Cloud)){
				if (branchrank.trim().equals("0")) {
					branchtype = "3";
				} 
				branchrank = "0";
			} 
		}
		
		String staffname = loginUser.getStaff().getStaffName();	
		String subSystem = request.getParameter("subSystem");
		String webstatus = null;
		String shiftname = null;
		String systemStatus = null;
		List<?> loginData = leftmenuService.getLoginnewdata(loginUser.getStaff().getStaffId());
		if(loginData != null){
			String shiftid = ((Map<?, ?>) loginData.get(0)).get("SHIFT")== null ? "" :((Map<?, ?>) loginData.get(0)).get("SHIFT").toString();
			ShiftTime shifttimename = ((ShiftTime) (leftmenuService
					.findOneByProperties(ShiftTime.class, "shiftTimeId", shiftid,"branchId",loginUser.getStaff().getBranchId())));
			if(shifttimename != null){
				 shiftname = shifttimename.getShiftname() == null ? "" :  shifttimename.getShiftname().toString();
			}
		}
		//判断sysparam中参数是否在云端，若在线状态就不显示
		if(initialService.checkSystemExistCloud()){
			systemStatus = CommonConstants.SystemType.Cloud;
		} else {
			if(HeartBeatClient.heartbeat){
				webstatus = CommonConstants.WebSatus.inline;
			}else{
				webstatus = CommonConstants.WebSatus.outline;
			}
		}

		if (SystemConstants.SystemStyle.PC == loginUser.getSystemType()) {
			if(loginUser.getStaff().getStaffId().equalsIgnoreCase(SystemConstants.User.ADMIN_ID) && subSystem == null){
				mv.setViewName("page/ipmspage/index/index");
				mv.addObject("branchtype", branchtype);
			}else if ((subSystem != null && !StringUtil.isEmpty(subSystem))) {
				if(subSystem == null){
					subSystem =SystemConstants.User.ADMIN_ID;
				}
				if (subSystem.equalsIgnoreCase(CommonConstants.SubSystemName.CRM)) {
					mv.setViewName("page/system/mainframe");
				} else if (subSystem.equalsIgnoreCase(CommonConstants.SubSystemName.IPMS)) {
					mv.setViewName("page/ipmspage/system/mainframe");
					if (houseRightflag && CommonConstants.SystemLevel.MarketCenter.equals(bigBranch.getRank())) {
						mv.addObject("house", CommonConstants.SystemTheme.HOMESTAY);
						request.setAttribute("houseAdmin", "houseAdmin");
						//判断其是否是区域管理员，若是区域管理员，在跳转页面不展示民宿的内容
						String subManager = null;
						Staff s = userService.findUser(loginUser.getStaff().getLoginName().toString(), loginUser.getStaff().getBranchId());
						if(s == null){
							subManager = "subManager";
							if( branch != null){
								mv.addObject("branch", branch);
							}
						}
						request.setAttribute("subManager", subManager);
					} else {
						if( branch != null){
							mv.addObject("branch", branch);
						} else {
							mv.addObject("loginBranch", loginUser.getStaff());
						}
					}
					request.setAttribute("branchname", branchname);
					request.setAttribute("staffid", staffid);
					request.setAttribute("staffname", staffname);
					request.setAttribute("systemStatus", systemStatus);
					request.setAttribute("webstatus", webstatus);
					request.setAttribute("branchrank", branchrank);
					request.setAttribute("shiftname", shiftname);
					request.setAttribute("branchtype", branchtype);
				} else if (subSystem.equalsIgnoreCase(CommonConstants.SubSystemName.EMS)) {
					mv.setViewName("page/ems/emssystem/mainframe");
				} else if (subSystem.equalsIgnoreCase("fms")) {
					mv.setViewName("page/fms/system/mainframe");
				} else if (subSystem.equalsIgnoreCase(CommonConstants.SubSystemName.UPMS)) {
					mv.setViewName("page/upms/system/mainframe");
				} else {
					mv.setViewName("page/ipmspage/index/index");
					mv.addObject("branchtype", branchtype);
				}
			
			} else {
			//SysParam sysObj = (SysParam) (logService.findByProperties(SysParam.class, "paramType", "INITIALSYSTEM",
			//		"status", "1").get(0));
			//if (sysObj != null) {
			//	String IsInitialSystem = sysObj.getContent();
				//if (IsInitialSystem.equals("1")) {
			
			 	
			if (!loginUser.getStaff().getStatus().equals(CommonConstants.InitialData.SubAdminStatus1) && !loginUser.getStaff().getStatus().equals(CommonConstants.InitialData.SubAdminStatus2)) {
				mv.setViewName("page/ipmspage/index/index");
				mv.addObject("branchtype", branchtype);
			} else {
				 //查询客服电话是否配置，若没有进step2
				Branch BranchInDb = initialService.queryBranchBySDA(loginUser.getStaff().getStaffId());
				SysParam oneCustomerPhone = initialService.queryOneCustomerPhone(loginUser.getStaff().getBranchId());
				if(branch != null){
					CheckOutAUDIT = initialService.queryRrecordsInSysParam("paramType","AUDITCheckOut","parameter1",branch.getBranchId());
				} else {
					CheckOutAUDIT = initialService.queryRrecordsInSysParam("paramType","AUDITCheckOut","parameter1",loginUser.getStaff().getBranchId());
				}
				Staff staffInDB = initialService.queryStaffBySDA(loginUser.getStaff().getStaffId());
				if(staffInDB != null){
					userrole = initialService.queryUserRoleBySDA(staffInDB.getStaffId(),loginUser.getStaff().getStaffId());
				}
				List<?> roomTypeList = rightService.findAllRoomType(loginUser.getStaff().getBranchId());
				List<?> roomList = initialService.findAllRoom(loginUser.getStaff().getBranchId());
				SysParam cleanCount = initialService.queryOneCleanCount(loginUser.getStaff().getBranchId());
				CashBox cashBoxValue = initialService.queryCashBoxAccount(loginUser.getStaff().getBranchId(),loginUser.getStaff().getStaffId());
				Staff registerUser = initialService.findinitialUser(loginUser.getStaff().getStaffId());
				//SysParam initialState = initialService.queryInitialState();
				/*if(BranchInDb == null || oneCustomerPhone == null){
					nextStepValue = "2";
				} else if (staffInDB == null || CheckOutAUDIT == null){
					nextStepValue = "3";
				} else if (userrole == null){
					nextStepValue = "4";
				} else if (roomTypeList == null || roomTypeList.size() <= 0){
					nextStepValue = "5";
				} else if (roomList == null || roomList.size() <= 0){
					nextStepValue = "6";
				} else if (BranchInDb.getBranchType().equalsIgnoreCase(CommonConstants.SubBranchType.APART) && cleanCount == null){
					nextStepValue = "7";
				}else if (BranchInDb.getBranchType().equalsIgnoreCase(CommonConstants.SubBranchType.HOTEL) && cashBoxValue == null){
					nextStepValue = "7";
				}else if (registerUser == null){
					nextStepValue = "8";
				}*/ 
				if(userrole != null){
					String roleIdValue = userrole.getRoleId();
					mv.addObject("roleIdValue", roleIdValue);
				}	
				List<?> postList = initialService.findAllPost();
				mv.addObject("postList", postList);
				mv.addObject("branch", branch);
//				mv.addObject("nextStepValue", nextStepValue);
				mv.setViewName("page/initializtion/initialPage");
			}
			    //} else {
				//	mv.setViewName("/page/ipmspage/login/loginError");
				//	mv.addObject("errorCode", "系统是否初始化的标签没有配置!");
				//}
			}

		} else {
			mv.setViewName("page/system/mainApp");
		}

		Map<String, ModelConfig> models = new TreeMap<String, ModelConfig>(comparator);

		ModelConfig model, defaultModel = null;
		PageConfig pageConfig = null;
		String pageId;
		boolean hasFirstPage = false;

		Map<String, String> subSystemKind = new HashMap<String, String>();
		if (sysRoles != null && !sysRoles.isEmpty()) {
			for (int i = 0; i < sysRoles.size(); i++) {
				roleRelations = sysRoles.get(i).getRoleRelation();
				if (roleRelations != null && !roleRelations.isEmpty()) {
					for (int j = 0; j < roleRelations.size(); j++) {
						if (roleRelations.get(j) == null) {
							continue;
						}
						pageId = roleRelations.get(j).getFuncrightId();
						model = pageBuilder.findModleByPageId(pageId);

						if (model != null) {
							if (subSystem != null && !StringUtil.isEmpty(subSystem) && !subSystem.equalsIgnoreCase(SystemConstants.User.ADMIN_ID)) {
								if (!subSystem.equalsIgnoreCase(model.getSubSystem())) {
									continue;
								}
							} else {
								// 当前用户拥有的子系统种类
								String subSyStemValue = pageBuilder.getModelConfig(model.getModelId()).getSubSystem();
								if (!subSystemKind.containsKey(subSyStemValue.toUpperCase())) {
									subSystemKind.put(subSyStemValue.toUpperCase(), ReflectUtil.getVariableMap(
											CommonConstants.SubSystemNames.class).get(subSyStemValue.toUpperCase()).toString());
								}

							}


							if (!models.containsKey(model.getModelId())) {
								models.put(model.getModelId(), model);
							}

							if (!hasFirstPage) {
								pageConfig = pageBuilder.getPageConfigByPageId(model.getModelId(), pageId);
								if (pageConfig.getFirstPage() != null) {
									mv.setViewName(pageConfig.getFirstPage());
									hasFirstPage = true;
	
									String datarights = roleRelations.get(j).getDatarights();
	
									if (datarights == null && roleRelations.get(j).getDatarightMap() != null) {
										Map<String, String> rights = roleRelations.get(j).getDatarightMap();
										datarights = "";
										Entry<String, String> entry;
	
										for (Iterator<Entry<String, String>> it = rights.entrySet().iterator(); it
												.hasNext();) {
											entry = it.next();
											datarights += entry.getKey() + (it.hasNext() ? "," : "");
										}
									}
	
									mv.addObject("datarights", datarights);
								}
							}

							if (model.isShow()) {
								if (defaultModel == null || defaultModel.getModelId().compareTo(model.getModelId()) > 0) {
									defaultModel = model;
								}
							}
						}
					}
				}
			}
			mv.addObject("subSystemKind", JSONUtil.fromObject(subSystemKind));
			mv.addObject("pmsFlag", pmsFlag);
			mv.addObject("modelConfig", defaultModel);
			mv.addObject("models", models);
		} else {
			mv.setViewName("page/system/error");
			mv.addObject("errorCode", 500);
		}

		return mv;
	}

	@RequestMapping("/queryModelFunctions.do")
	public void queryModelFunctions(String modelId, HttpServletRequest request, HttpServletResponse response) {
		List<PageConfig> pages = new ArrayList<PageConfig>();
		if (!StringUtils.isEmpty(modelId)) {
			Map<String, PageConfig> pageConfigs = pageBuilder.getPageConfigsByModelId(modelId);

			LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
			List<Role> sysRoles = loginUser.getRightsList();
			List<RoleRelation> roleRelations;
			ModelConfig model;
			Map<String, String> pageMap = new HashMap<String, String>();
			String pageId;

			if (sysRoles != null && !sysRoles.isEmpty()) {
				for (int i = 0; i < sysRoles.size(); i++) {
					roleRelations = sysRoles.get(i).getRoleRelation();
					if (roleRelations != null && !roleRelations.isEmpty()) {
						for (int j = 0; j < roleRelations.size(); j++) {
							if (roleRelations.get(j) == null) {
								continue;
							}
							pageId = roleRelations.get(j).getFuncrightId();
							model = pageBuilder.findModleByPageId(pageId);
							if (model != null && model.getModelId().equals(modelId)) {
								pageMap.put(pageId, pageId);
							}
						}
					}
				}
			}

			Iterator<Map.Entry<String, PageConfig>> it = pageConfigs.entrySet().iterator();
			Map.Entry<String, PageConfig> entry;
			while (it.hasNext()) {
				entry = (Map.Entry<String, PageConfig>) it.next();
				if (pageMap.containsKey(entry.getKey())) {
					pages.add(entry.getValue());
				}
			}
		}

		JSONUtil.responseJSON(response, pages);
	}

	@RequestMapping("/queryPageConfig.do")
	public ModelAndView queryPageConfig(String modelId, String pageId, String initParams, 
			String initFunc, String quickAddCol, HttpServletRequest request) throws Exception {
		String branchrank = "0";
		ModelAndView mv = new ModelAndView();
		if (pageId != null && !"".equals(pageId)) {
			pageBuilder.construct(modelId, pageId, CommonParams.getCommonParams(request.getSession().getId()));
			PageConfig pageConfig = (PageConfig) CloneUtil
					.deepClone(pageBuilder.getPageConfigByPageId(modelId, pageId));

			LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
			List<Role> sysRoles = loginUser.getRightsList();
			List<RoleRelation> roleRelations;
			Map<String, String> pageFunctions;
			String tmpId;
			String[] dataRights;
			PageConfig subConfig;
			boolean breakFlag = false;

			Map<String, String> tmpTabs = new TreeMap<String, String>();
			Map<String, String> tabs = pageConfig.getTabs();
			if (sysRoles != null && !sysRoles.isEmpty()) {
				for (int i = 0; i < sysRoles.size(); i++) {
					roleRelations = sysRoles.get(i).getRoleRelation();

					if (roleRelations != null && !roleRelations.isEmpty()) {
						for (int j = 0; j < roleRelations.size(); j++) {
							if (roleRelations.get(j) == null) {
								continue;
							}
							tmpId = roleRelations.get(j).getFuncrightId();
							if (tabs != null) {
								if (tabs.containsKey(tmpId)) {
									subConfig = pageBuilder.getPageConfigByPageId(modelId, tmpId);
									tmpTabs.put(tmpId, subConfig.getName());
								}
							} else {
								if (pageId.equals(tmpId)) {
									if (roleRelations.get(j).getDatarights() != null) {
										dataRights = roleRelations.get(j).getDatarights().split(",");
										pageFunctions = new HashMap<String, String>();

										for (int k = 0; k < dataRights.length; k++) {
											pageFunctions.put(dataRights[k], dataRights[k]);
										}

										pageConfig.setFunctions(pageFunctions);
									} else if (roleRelations.get(j).getDatarightMap() != null) {
										pageConfig.setFunctions(roleRelations.get(j).getDatarightMap());
									} else {
										pageConfig.setFunctions(null);
									}

									breakFlag = true;
									break;
								}
							}
						}
					}

					if (breakFlag) {
						break;
					}
				}
			}

			List<ColumnConfig> columns = pageConfig.getColumns();
			if (columns != null && !columns.isEmpty()) {
				for (ColumnConfig column : columns) {
					if (column.getEditType() != null
							&& RegExUtil.match("dialog|dialog-multi|dialog-radio", column.getEditType())) {
						column.setDialogConditions(null);
					}
				}
			}
			LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
			Branch branch = (Branch) leftmenuService.findOneByProperties(Branch.class, "branchId", loginuser.getStaff().getBranchId(),"status","1");
			if(branch != null){
				branchrank = branch.getRank().toString();
			}
			
			mv.addObject("modelId", modelId);
			mv.addObject("pageConfig", pageConfig);
			mv.addObject("initFunc", initFunc);
			mv.addObject("quickAddCol", quickAddCol);
			mv.addObject("branchrank", branchrank);
			mv.addObject("branch", JSONUtil.fromObject(branch));
			mv.addObject("loginuser", JSONUtil.buildReportJSON(loginuser.getStaff()));
			Map<String, String> paramMap = new HashMap<String, String>();
			if (initParams != null) {
				initParams = URLDecoder.decode(initParams, "UTF-8");
				if (initParams != null && !"".equals(initParams)) {
					JSONUtil.buildParamFromJSON(paramMap, initParams);
				}
				mv.addObject("initParams", paramMap);
			}

			if (pageConfig.getTabs() != null) {
				pageConfig.setTabs(tmpTabs);
				mv.setViewName("page/report/reportTemplateWithTabs");
			} else {
				mv.setViewName("page/report/reportTemplate");
			}
		} else {
			logger.error("query page config failed, page id can not be null or empty!");
		}

		return mv;
	}

	@RequestMapping("/queryPageData.do")
	public void queryPageData(String modelId, String pageId, String conditions, boolean queryFlag,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (queryFlag && pageId != null && !"".equals(pageId)) {
			Map<String, Object> map = new HashMap<String, Object>();
			Pagination pagination;

			Integer pageNum = Integer.parseInt(request.getParameter("page"));
			Integer rows = Integer.parseInt(request.getParameter("rows"));
			if (pageNum != null && rows != null) {
				pagination = new Pagination(pageNum, rows);
				map.put("pagination", pagination);
			}
			if (conditions != null && !"".equals(conditions)) {
				try {
					conditions = URLDecoder.decode(conditions, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					logger.error(e.getMessage());
				}
				Map<String, Object> params = new HashMap<String, Object>();
				for (String condition : conditions.split(",")) {
					params.put(condition.split(":")[0], condition.split(":").length > 1 ? condition.split(":")[1]: "%");

				}
				map.put("params", params);
			}

			Map<String, Object> data = pageBuilder.buildData(modelId, pageId, map, CommonParams.getCommonParams(request
					.getSession().getId()));

			List<?> result = (List<?>) data.get("result");
			pagination = (Pagination) data.get("pagination");

			PageConfig pageConfig = pageBuilder.getPageConfigByPageId(modelId, pageId);
			if (pagination != null) {
				String jsonData = JSONUtil.buildReportJSONByPagination(result, pagination);
				if ("list".equalsIgnoreCase(pageConfig.getShowType())) {
					Map<String, Object> rm = new HashMap<String, Object>();
					rm.put("result", jsonData);
					rm.put("pagination", pagination);
					JSONUtil.responseJSON(response, rm);
				} else {
					JSONUtil.responseJSON(response, jsonData);
				}
			} else {
				JSONUtil.responseJSON(response, JSONUtil.buildReportJSON(result));
			}
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/queryPageChart.do")
	public void queryPageChart(String modelId, String pageId, String conditions, boolean queryFlag,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (queryFlag && pageId != null && !"".equals(pageId)) {
			PageConfig pageConfig = pageBuilder.getPageConfigByPageId(modelId, pageId);
			String[] latitude = pageConfig.getCharts().get("latitude").split("[:]");

			List<Map<String, Object>> list = null;
			Map<String, Object> chartInfo = null;

			Map<String, Object> params = null;
			if (conditions != null && !"".equals(conditions)) {
				try {
					conditions = URLDecoder.decode(conditions, "UTF-8");
				} catch (UnsupportedEncodingException e) {
				}
				params = new HashMap<String, Object>();
				for (String condition : conditions.split(",")) {
					params
							.put(condition.split(":")[0], condition.split(":").length > 1 ? condition.split(":")[1]
									: "%");
				}
			}

			Map<String, Object> map = pageBuilder.queryData(modelId, pageId, params, true,
					SystemConstants.QueryType.CHART, CommonParams.getCommonParams(request.getSession().getId()));
			if (map != null && !map.isEmpty()) {
				list = new ArrayList<Map<String, Object>>();
				List<?> result = (List<?>) map.get("result");
				for (int i = 0; i < result.size(); i++) {
					chartInfo = new HashMap<String, Object>();
					chartInfo.put("label", ((Map<String, Object>) result.get(i)).get(latitude[0]));
					chartInfo.put("value", ((Map<String, Object>) result.get(i)).get(latitude[1]));
					list.add(chartInfo);
				}
			}

			JSONUtil.responseJSON(response, list);
		}
	}

	@RequestMapping("/printTemplate.do")
	public ModelAndView printTemplate(String template) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/print/" + template);
		return mv;
	}

	@RequestMapping("/dealDataInFormEditing.do")
	public void dealDataInFormEditing(String modelId, String pageId, String keyConfig, String dealType, String sequenceSource,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		PageConfig pageConfig = pageBuilder.getPageConfigByPageId(modelId, pageId);

		String target = pageConfig.getTarget();
		if (target != null && !"".equals(target)) {
			try {
				Map<String, String> paramMap = new HashMap<String, String>();

				JSONUtil.buildParamFromJSON(paramMap, setDefaultParam(request, response));
				
				/*SysParam sysParam = (SysParam) pageBuilder.findOneByProperties(SysParam.class, "paramType", "SYSTEMTYPE");
				
				String systemType = sysParam.getContent();
				
				SysParam param = (SysParam) pageBuilder.findOneByProperties(SysParam.class, "paramType",
						SystemConstants.ParamType.BRANCH_IP, "paramName", "1");
				
				boolean isSync = pageConfig.isSync();
				
				int priority = 1;*/

				Object returnVal = beforeDealingData(pageConfig, null, request, response);
				if (returnVal != null) {
					JSONUtil.buildParamFromJSON(paramMap, returnVal.toString());
				}

				List<Object> list = ReflectUtil.setBeansFromJsonArray(request, target,
						SystemConstants.DatabaseOperation.SAVE.equals(dealType), keyConfig, paramMap, sequenceSource);
				if (list != null) {
					if (SystemConstants.DatabaseOperation.SAVE.equals(dealType)
							|| SystemConstants.DatabaseOperation.UPDATE.equals(dealType)) {
						pageBuilder.saveOrUpdateAll(list);
						/*if(isSync == true) {
							if(CommonConstants.SystemType.Branch.equals(systemType)){
								Map<String, Object> map = new HashMap<String, Object>();
								map.put(target, list);
								TransferServcie.getInstance().transferData(priority, param.getContent(), JSONUtil.fromObject(map).toString());
							}
						}*/
						
					} else if (SystemConstants.DatabaseOperation.DELETE.equals(dealType)) {
						// pageBuilder.getHibernateTemplate().deleteAll(list);
						for (Object object : list) {
							Field[] fields = object.getClass().getDeclaredFields();
							for (Field f : fields) {
								String fieldName = f.getName();
								if (fieldName.equalsIgnoreCase("status")) {
									ReflectUtil.setValue(object, fieldName, "0");
								}
							}
							pageBuilder.getHibernateTemplate().saveOrUpdate(object);
						/*	if(isSync ==true){
								if(CommonConstants.SystemType.Branch.equals(systemType)){
									List<Object> syncList = new ArrayList<Object>();
									list.add(object);
									Map<String, Object> map = new HashMap<String, Object>();
									map.put(target, syncList);
									TransferServcie.getInstance().transferData(priority, param.getContent(), JSONUtil.fromObject(map).toString());
								}
							}*/
						}
					}
				}

				recordLog(pageConfig, dealType, list, request);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("deal data occurs error! deal type " + dealType + " , page id " + pageId + "\n" + e);
			}
		} else {
			logger.error("page config error, save target can not be null or empty! page id " + pageId);
		}
	}

	@RequestMapping("/dealData.do")
	public void dealData(String modelId, String pageId, String args, String keyConfig, String dealType, String param,
			String frameParams, String sequenceSource, HttpServletRequest request, HttpServletResponse response) throws Exception {
		int result = CommonConstants.AJAXRESULT.FALSE;
		Map<String, String> paramMap = new HashMap<String, String>();
		PageConfig pageConfig = pageBuilder.getPageConfigByPageId(modelId, pageId);
		
		/*SysParam sysParam = (SysParam) pageBuilder.findOneByProperties(SysParam.class, "paramType", "SYSTEMTYPE");
		
		String systemType = sysParam.getContent();
		
		SysParam ipParam = (SysParam) pageBuilder.findOneByProperties(SysParam.class, "paramType",
				SystemConstants.ParamType.BRANCH_IP, "paramName", "1");
		
		boolean isSync = pageConfig.isSync();
		
		int priority = 1;*/

		String target = pageConfig.getTarget();
		if (target != null && !"".equals(target)) {
			try {
				List<Object> list = null;

				param = URLDecoder.decode(param, "UTF-8");
				if (param != null && !"".equals(param)) {
					JSONUtil.buildParamFromJSON(paramMap, param);
				}

				JSONUtil.buildParamFromJSON(paramMap, setDefaultParam(request, response));

				Object returnVal = beforeDealingData(pageConfig, frameParams, request, response);
				if (returnVal != null) {
					JSONUtil.buildParamFromJSON(paramMap, returnVal.toString());
				}

				list = ReflectUtil.setBeansFromJsonArray(args, target, SystemConstants.DatabaseOperation.SAVE
						.equals(dealType), keyConfig, paramMap, sequenceSource);

				if (list != null) {
					if (SystemConstants.DatabaseOperation.SAVE.equals(dealType)) {
						pageBuilder.saveOrUpdateAll(list);
					/*	if(isSync ==true){
							if(CommonConstants.SystemType.Branch.equals(systemType)){
								Map<String, Object> map = new HashMap<String, Object>();
								map.put(target, list);
								TransferServcie.getInstance().transferData(priority, ipParam.getContent(), JSONUtil.fromObject(map).toString());
							}
						}*/
						
					} else if (SystemConstants.DatabaseOperation.DELETE.equals(dealType)) {
						// pageBuilder.getHibernateTemplate().deleteAll(list);
						for (Object object : list) {
							Field[] fields = object.getClass().getDeclaredFields();
							for (Field f : fields) {
								String fieldName = f.getName();
								if (fieldName.equalsIgnoreCase("status")) {
									ReflectUtil.setValue(object, fieldName, "0");
								}
							}
							pageBuilder.getHibernateTemplate().saveOrUpdate(object);
						/*	if(isSync ==true){
								if(CommonConstants.SystemType.Branch.equals(systemType)){
									List<Object> syncList = new ArrayList<Object>();
									syncList.add(object);
									Map<String, Object> map = new HashMap<String, Object>();
									map.put(target, syncList);
									TransferServcie.getInstance().transferData(priority, ipParam.getContent(), JSONUtil.fromObject(map).toString());
								}
							}*/
						}
					}
					result = CommonConstants.AJAXRESULT.SUCESS;

					recordLog(pageConfig, dealType, list, request);
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("deal data occurs error! deal type " + dealType + " , page id " + pageId + "\n" + e);
			}
		} else {
			logger.error("page config error, save target can not be null or empty! page id " + pageId);
		}

		JSONUtil.responseJSON(response, new AjaxResult(result, null));
	}

	@RequestMapping("/exportFile.do")
	public ModelAndView exportFile(String modelId, String pageId, String conditions, HttpServletRequest request)
			throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/report/export");
		if (pageId != null && !"".equals(pageId)) {
			PageConfig pageConfig = pageBuilder.getPageConfigByPageId(modelId, pageId);

			Map<String, Object> params = null;
			if (conditions != null && !"".equals(conditions)) {
				params = new HashMap<String, Object>();
				for (String condition : conditions.split(",")) {
					params
							.put(condition.split(":")[0], condition.split(":").length > 1 ? condition.split(":")[1]
									: "%");
				}
			}

			Map<String, Object> map = pageBuilder.queryData(modelId, pageId, params, SystemConstants.QueryType.EXCEL,
					CommonParams.getCommonParams(request.getSession().getId()));

			if (map != null && !map.isEmpty()) {
				String fileName = pageConfig.getName() + '[' + new SimpleDateFormat("yyyy-MM-dd").format(new Date())
						+ "].xlsx";
				mv.addObject("fileName", fileName);
				mv.addObject("pageConfig", pageConfig);
				mv.addObject("result", (List<?>) map.get("result"));
			}
		}

		return mv;
	}

	@RequestMapping("/queryMultiData.do")
	public void queryMultiData(String modelId, String pageId, String param, String value, HttpServletResponse response) {
		JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.SUCESS, "", pageBuilder
				.queryMultiData(modelId, pageId, param, value)));
	}

	@RequestMapping("/isUnique.do")
	public void isUnique(String target, String colName, String colValue, String idInfo, HttpServletResponse response)
			throws Exception {
		Object tar = ReflectUtil.getBeanByName(target);
		Object bean = null;

		if (!StringUtils.isEmpty(idInfo)) {
			String[] infos = idInfo.split("[|]"), info;

			String cond = "";
			for (int i = 0; i < infos.length; i++) {
				info = infos[i].split(":");
				cond += " and " + ReflectUtil.getTruelyFieldName(tar, info[0]) + " != '" + info[1] + "'";
			}

			bean = pageBuilder.findByCond(target, ReflectUtil.getTruelyFieldName(tar, colName), colValue, cond);
		} else {
			if(target.equalsIgnoreCase("staff")){
				List<?> staff = initialService.checkStaffRepeatHql(ReflectUtil.getTruelyFieldName(tar, colName), colValue);
				if(staff != null && staff.size() >= 1)
					bean = new Object();
			}else{
				bean = pageBuilder.findOneByProperties(tar.getClass(), ReflectUtil.getTruelyFieldName(tar, colName),
						colValue, "status", "1");
			}
			
		}

		response.getWriter().write("{ \"result\":" + (bean == null) + " }");
	}

	@RequestMapping("/welcome.do")
	public ModelAndView welcome() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/system/welcome");
		return mv;
	}


	@RequestMapping("/customDialog.do")
	public ModelAndView customDialog(String modelId, String pageId, String colName, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/dialog/customDialog");
		PageConfig pageConfig = pageBuilder.getPageConfigByPageId(modelId, pageId);
		List<ColumnConfig> columns = pageConfig.getColumns();
		for (ColumnConfig column : columns) {
			if (colName.equals(column.getColumnId())) {
				mv.addObject("dialogColumns", column.getDialogColumns());
				mv.addObject("dialogTarget", column.getDialogTarget());
				mv.addObject("dialogConditions", column.getDialogConditions());
				mv.addObject("dialogQuickAdd", column.getDialogQuickAdd());
				mv.addObject("selectType", column.getEditType());
			}
		}
		mv.addObject("colName", colName);
		return mv;
	}
	
	@RequestMapping("/linkageDialog.do")
	public ModelAndView linkageDialog(String modelId, String pageId, String colName, String dataEvent, 
				String event, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/dialog/linkageDialog");
		PageConfig pageConfig = pageBuilder.getPageConfigByPageId(modelId, pageId);
		List<ColumnConfig> columns = pageConfig.getColumns();
		for (ColumnConfig column : columns) {
			if (colName.equals(column.getColumnId())) {
				mv.addObject("dialogColumns", column.getDialogColumns());
				mv.addObject("dialogTarget", column.getDialogTarget());
				mv.addObject("dialogConditions", column.getDialogConditions());
				mv.addObject("selectType", column.getEditType());
			}
		}
		mv.addObject("colName", colName);
		mv.addObject("dataEvent", dataEvent);
		mv.addObject("event", event);
		return mv;
	}

	@RequestMapping("/loadCustomDialogData.do")
	public void loadCustomDialogData(String dialogTarget, String dialogColumns, String dialogConditions,
			String queryData, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Pagination pagination = null;

		try {
			Integer pageNum = Integer.parseInt(request.getParameter("page"));
			Integer rows = Integer.parseInt(request.getParameter("rows"));
			pagination = new Pagination(pageNum, rows);
		} catch (Exception e) {
		}

		List<?> result = pageBuilder.queryCustomDialogData(dialogTarget, dialogColumns, dialogConditions, queryData,
				pagination, CommonParams.getCommonParams(request.getSession().getId()));
		if (pagination == null) {
			JSONUtil.responseJSON(response, JSONUtil.buildReportJSON(result));
		} else {
			JSONUtil.responseJSON(response, JSONUtil.buildReportJSONByPagination(result, pagination));
		}
	}
	/**
	 * 民宿账号查询
	 * @param dialogTarget
	 * @param dialogColumns
	 * @param dialogConditions
	 * @param queryData
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/loadHouseAccountDialogData.do")
	public void loadHouseAccountDialogData(String dialogTarget, String dialogColumns, String dialogConditions,
			String queryData, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Pagination pagination = null;

		try {
			Integer pageNum = Integer.parseInt(request.getParameter("page"));
			Integer rows = Integer.parseInt(request.getParameter("rows"));
			pagination = new Pagination(pageNum, rows);
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<?> result = userService.findBySQLWithPagination("queryAllHouseAccount", new Object[] { queryData}, pagination, true);
		if (pagination == null) {
			JSONUtil.responseJSON(response, JSONUtil.buildReportJSON(result));
		} else {
			JSONUtil.responseJSON(response, JSONUtil.buildReportJSONByPagination(result, pagination));
		}
	}
	/**
	 * 
	 * @Description 维修员工常雇人员查询(城市,区,姓名,编号)
	 * @author ideas_mengl
	 * @date   2018年8月1日下午1:42:14
	 * @param dialogTarget
	 * @param dialogColumns
	 * @param dialogConditions
	 * @param queryData
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping("/loadRepairDialogData.do")
	public void loadRepairDialogData(String dialogTarget, String dialogColumns, String dialogConditions,
			String queryData, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Pagination pagination = null;

		try {
			Integer pageNum = Integer.parseInt(request.getParameter("page"));
			Integer rows = Integer.parseInt(request.getParameter("rows"));
			pagination = new Pagination(pageNum, rows);
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<?> result = userService.findBySQLWithPagination("queryFrequenters", new Object[] { queryData,queryData,queryData,queryData}, pagination, true);
		List<Map<String,Object>> newresult = new ArrayList<Map<String,Object>>();
		for (Object object : result) {
			Map<?,?> map = (Map<?, ?>) object;
			Map<String,Object> newmap = new HashMap<String,Object>();
			Set<?> keyset = (Set<?>) map.keySet();
			for (Object object2 : keyset) {
				String key = (String) object2;
				Object value = map.get(key);
				key = key.toLowerCase();
				if(!"RN".equalsIgnoreCase(key)){
					newmap.put(key, (String) value);
				}
				
				
			}
			newresult.add(newmap);
		}
		if (pagination == null) {
			JSONUtil.responseJSON(response, JSONUtil.buildReportJSON(newresult));
		} else {
			JSONUtil.responseJSON(response, JSONUtil.buildReportJSONByPagination(newresult, pagination));
		}
	}
	
	
	@RequestMapping("/loadlinkageDialogData.do")
	public void loadlinkageDialogData(String dialogTarget, String dialogColumns, String dialogConditions,
		String queryData, String dataEvent, String event, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Pagination pagination = null;

		try {
			Integer pageNum = Integer.parseInt(request.getParameter("page"));
			Integer rows = Integer.parseInt(request.getParameter("rows"));
			pagination = new Pagination(pageNum, rows);
		} catch (Exception e) {
		}

		List<?> result = pageBuilder.queryLinkageDialogData(dialogTarget, dialogColumns, dialogConditions, queryData, 
				dataEvent, event, pagination, CommonParams.getCommonParams(request.getSession().getId()));
		if (pagination == null) {
			JSONUtil.responseJSON(response, JSONUtil.buildReportJSON(result));
		} else {
			JSONUtil.responseJSON(response, JSONUtil.buildReportJSONByPagination(result, pagination));
		}
	}

	@RequestMapping("/doReloadConfig.do")
	public void doReloadConfig(HttpServletResponse response) throws Exception {
		pageBuilder.loadConfigs();
		JSONUtil.responseJSON(response, true);
	}

	@RequestMapping("/doReloadSystem.do")
	public void doReloadSystem(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		loginUser
				.setSystemType(SystemConstants.SystemStyle.PC == loginUser.getSystemType() ? SystemConstants.SystemStyle.APP
						: SystemConstants.SystemStyle.PC);
		JSONUtil.responseJSON(response, true);
	}

	@RequestMapping("/loadAppFrame.do")
	public ModelAndView loadAppFrame(String modelId, HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/system/mainAppFrame");
		modelId = request.getParameter("modelId");
		ModelConfig model = pageBuilder.getModelConfig(modelId);
		mv.addObject("modelConfig", model);
		return mv;
	}

	private Object beforeDealingData(PageConfig pageConfig, String frameParams, HttpServletRequest request,
			HttpServletResponse response) {
		if (pageConfig.getBeforeDealingData() != null) {
			String[] configs = pageConfig.getBeforeDealingData().split("[.]");
			Object clazz = SpringUtil.getBean(configs.length > 1 ? configs[0] : "commonController");

			try {
				Method[] methods = clazz.getClass().getMethods();
				Method method = null;
				Class<?>[] types = null;
				Object[] clazParams;
				Object param = null;

				for (Method mt : methods) {
					if (mt.getName().equals(configs[configs.length - 1])) {
						method = mt;
						types = method.getParameterTypes();
						break;
					}
				}

				if (method != null && types != null) {
					clazParams = new Object[types.length];
					for (int i = 0; i < types.length; i++) {
						if ("HttpServletRequest".equalsIgnoreCase(types[i].getSimpleName())) {
							param = request;
						} else if ("HttpServletResponse".equalsIgnoreCase(types[i].getSimpleName())) {
							param = response;
						} else {
							if (frameParams != null) {
								frameParams = URLDecoder.decode(frameParams, "UTF-8");
								param = ReflectUtil.setBean(new JSONObject(frameParams), types[i].getSimpleName());
							}
						}
						Array.set(clazParams, i, param);
					}

					return method.invoke(clazz, clazParams);
				}
			} catch (Exception e) {
				logger.error(pageConfig.getPageId() + " deal page param data occurs error!\n" + e);
			}
		}

		return null;
	}

	private void recordLog(PageConfig pageConfig, String dealType, List<?> beans, HttpServletRequest request) {
		if (pageConfig.getRecordLog() != null) {
			String methodName = pageConfig.getRecordLog();
			try {
				LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(
						RequestUtil.LOGIN_USER_SESSION_KEY);
				Staff staff = loginUser.getStaff();
				Method method = logService.getClass().getDeclaredMethod(methodName, Staff.class, List.class);
				method.invoke(logService, staff, beans);
			} catch (Exception e) {
				logger.error(pageConfig.getPageId() + " record log occurs error!\n" + e);
			}
		}
	}

	public String setDefaultParam(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginUser.getStaff();
		paramMap.put("PASSWORD", MD5Util.getCryptogram("888888"));
		paramMap.put("BRANCHID", staff.getBranchId());
		paramMap.put("RECORDUSER", staff.getStaffId());
		paramMap.put("STATUS", CommonConstants.STATUS.NORMAL);

		return JSONUtil.fromObject(paramMap).toString();
	}

	@RequestMapping("/uploadFile.do")
	public void uploadFile(HttpServletRequest request, HttpServletResponse response, String key, String fileName,
			String dir) throws Exception {
		String webPath = RequestUtil.getWebPath(request);
		FileOutputStream fos = null;
		InputStream is = null;
		boolean result = true;

		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile srcFile = multipartRequest.getFile(key);

			File srcFolder = new File(webPath + "/upload/" + dir);

			if (!srcFolder.exists()) {
				srcFolder.mkdirs();
			}

			if (StringUtils.isEmpty(fileName)) {
				fileName = srcFile.getOriginalFilename();
				// fileName = String.valueOf(System.currentTimeMillis())
				// + orignName.substring(orignName.lastIndexOf("."));
			}

			File tarFile = new File(srcFolder.getAbsolutePath() + "/" + fileName);
			tarFile.createNewFile();

			fos = new FileOutputStream(tarFile);
			is = srcFile.getInputStream();

			int ln = 0;
			byte[] buf = new byte[1024];
			while ((ln = is.read(buf)) != -1) {
				fos.write(buf, 0, ln);
			}

			fos.flush();
		} catch (IOException e) {
			logger.error("uploadFile occurs error!\n" + e);
			result = false;
		} finally {
			if (fos != null) {
				fos.close();
			}

			if (is != null) {
				is.close();
			}
		}

		response.getWriter().write(result ? "{\"sucess\": \"" + fileName + "\"}" : "{ \"error\": -1 }");
	}

	@RequestMapping("/importFile.do")
	public ModelAndView importFile(String modelId, String pageId, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/report/import");

		PageConfig pageConfig = pageBuilder.getPageConfigByPageId(modelId, pageId);
		String fileType = pageConfig.getUploadFileType();

		if (!StringUtils.isEmpty(fileType)) {
			mv.addObject("mode", fileType);
			mv.addObject("fileName", pageConfig.getUploadFileName());
			mv.addObject("dir", pageConfig.getUploadFileDir());
		}

		return mv;
	}

	@RequestMapping("/importData.do")
	public void importData(String modelId, String pageId, String key, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile srcFile = multipartRequest.getFile(key);

		PageConfig pageConfig = pageBuilder.getPageConfigByPageId(modelId, pageId);

		Map<String, ?> errors = null;
		ImportUtil iu = new ImportUtil(pageConfig, srcFile, setDefaultParam(multipartRequest, response));
		errors = iu.importData();

		response.getWriter().write(
				errors.size() > 0 ? net.sf.json.JSONObject.fromObject(errors).toString() : "{ \"sucess\": -1 }");
	}

}