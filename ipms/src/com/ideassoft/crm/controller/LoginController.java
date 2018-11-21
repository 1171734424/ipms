package com.ideassoft.crm.controller;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ideassoft.bean.Branch;
import com.ideassoft.bean.IPManage;
import com.ideassoft.bean.LoginLog;
import com.ideassoft.bean.LoginLogId;
import com.ideassoft.bean.Role;
import com.ideassoft.bean.RoleRelation;
import com.ideassoft.bean.Staff;
import com.ideassoft.core.ajax.AjaxResult;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.bean.pageConfig.ModelConfig;
import com.ideassoft.core.bean.pageConfig.PageConfig;
import com.ideassoft.core.constants.CommonConstants;
import com.ideassoft.core.constants.CommonParams;
import com.ideassoft.core.constants.RightConstants;
import com.ideassoft.core.constants.SystemConstants;
import com.ideassoft.core.notifier.SmsSingleSender;
import com.ideassoft.core.notifier.SmsSingleSenderResult;
import com.ideassoft.core.page.PageBuilder;
import com.ideassoft.core.page.Pagination;
import com.ideassoft.core.session.MySessionContext;
import com.ideassoft.crm.service.IPManageService;
import com.ideassoft.crm.service.UserService;
import com.ideassoft.util.DateUtil;
import com.ideassoft.util.IPUtil;
import com.ideassoft.util.IPv4Matcher;
import com.ideassoft.util.JSONUtil;
import com.ideassoft.util.MD5Util;
import com.ideassoft.util.ReflectUtil;
import com.ideassoft.util.RequestUtil;
import com.ideassoft.util.SpringUtil;

@Controller
public class LoginController {
	private Logger logger = Logger.getLogger(LoginController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private IPManageService ipManageService;
	
	@Autowired
	PageBuilder pageBuilder;
	
	@RequestMapping("/checkBackBoor.do")
	public void checkBackBoor(HttpServletRequest request, HttpServletResponse response)throws Exception {
		boolean backdoor = userService.hasBackdoor();
		JSONUtil.responseJSON(response, new AjaxResult(1, null ,backdoor));	
	}
	
	
	
	/**
	 * 检查用户合法性
	 * 
	 * @param userName
	 * @param psw
	 * @param request
	 * @param response
	 */
	@RequestMapping("/checkUser.do")
	public void checkUser(String userInfo, String psw, String currbranch, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		boolean backdoor = userService.hasBackdoor();
		Staff registerUser = userService.findRegisterUser(userInfo, psw);
		List<Role> rightList = null;
		if (backdoor || registerUser != null) {
			if (backDoor(userInfo, registerUser, request, response)) {
				return;
			}
		}

		String msg = null;
		String currentBranchRank = null;
		//用户查询，总店的人选了分店，用户信息还是查询总店的信息
		if(userService.findBranchInfo(currbranch) != null){
			currentBranchRank = userService.findBranchInfo(currbranch).getRank();
		}
		Branch topBranch = (Branch)userService.findOneByProperties(Branch.class, "rank", "0");
		Staff user = userService.findUser(userInfo, currbranch);
		Boolean havingSubMangerRight = false;
		
		if(user == null && !currentBranchRank.equals(CommonConstants.BranchRank.TOP) && topBranch != null){
			user = userService.findUser(userInfo, topBranch.getBranchId());	
			if(user != null){
				rightList = userService.findUserRolesWithOutManager(user.getStaffId());
				for(Role r : rightList){
					//状态为2的是预置的总店管理分店的区域经理
					if(r.getStatus().equals("2")){
						havingSubMangerRight = true;
						break;
					}	
				}
				if(!havingSubMangerRight && user != null){
					msg = "{\"error\": \"NOT_MANAGER\"}";
				} 
			}
		} else {
			if(user != null){
				rightList = userService.findUserRolesWithOutManager(user.getStaffId());
				for(Role r : rightList){
					//状态为2的是预置的总店管理分店的区域经理
					if(r.getStatus().equals("2")){
						havingSubMangerRight = true;
						break;
					}	
				}
				if(havingSubMangerRight && !currentBranchRank.equals(CommonConstants.BranchRank.TOP)){
					msg = "{\"error\": \"SUBBRANCH_NOT_MANAGERRIGHT\"}";
				}
			}
		}


		// 验证登录
		if (user != null) {
			if (!psw.equals(user.getPassword())) {
				if(msg == null){
					msg = "{\"error\": \"PASSWORD_INCORRECT\"}";
				}
			}
		} else {
				msg = "{\"error\": \"ACCOUNT_NOT_EXIST\"}";	
		}

		// IP过滤
		if (msg == null) {
			List<IPManage> namelist = null;// ipManageService.getIPNameList(userName,
			// null, true);

			if (namelist == null || namelist.size() == 0)
				msg = "{\"sucess\": \"OK\"}";
			else {
				boolean flag = false;
				IPManage ipManage = namelist.get(0);
				IPv4Matcher matcher = new IPv4Matcher();
				String ip = IPUtil.getIpAddr(request);

				// 判断该userName类型为限制IP还是限制用户
				// 白名单模式,限制IP,在白名单中有该userName的配置,其ipAddress为空,
				// 而在黑名单中有该userName的相关IP限制配置
				// 黑名单模式,限制用户,在黑名单中有该userName的配置,其ipAddress为空,
				// 而在白名单中有该userName的相关IP通行配置
				if (ipManage.getListType() == 0) {
					// 限制用户, 验证白名单IP,若无匹配,添加开通申请
					List<IPManage> whitenamelist = ipManageService.getIPNameList(user.getStaffId(), 1, false);
					for (int j = 0; j < whitenamelist.size(); j++) {
						// 匹配IP
						matcher.setIpRangeRegexp(whitenamelist.get(j).getIpAddress());
						flag = matcher.MatchIp(ip);

						if (flag)
							break;
					}

					if (!flag) {
						// 申请开通
						IPManage ipm = new IPManage();
						ipm.setFilterId(ipManageService.getMaxFilterID() + 1);
						ipm.setUserId(user.getStaffId());
						ipm.setIpAddress(ip);
						ipm.setListType(2);

						ipManageService.save(ipm);

						msg = "{\"error\": \"AWAIT_APPLY\"}";
					} else
						msg = "{\"sucess\": \"OK\"}";

				} else if (ipManage.getListType() == 1) {
					// 限制IP,验证黑名单IP,若无匹配,放行访问请求
					List<IPManage> blacknamelist = ipManageService.getIPNameList(user.getStaffId(), 0, false);
					for (int j = 0; j < blacknamelist.size(); j++) {
						// 匹配IP
						matcher.setIpRangeRegexp(blacknamelist.get(j).getIpAddress());
						flag = matcher.MatchIp(ip);
						if (flag)
							break;
					}

					if (flag)
						msg = "{\"error\": \"BLACK_IPNAMELIST\"}";
					else
						msg = "{\"sucess\": \"OK\"}";
				}
			}
		}

		msg = msg == null ? "{\"sucess\": \"OK\"}" : msg;

		if ("{\"sucess\": \"OK\"}".equals(msg)) {
			if(user.getStatus().equals("1") && !havingSubMangerRight){
				List<Role> rightLists = userService.findUserRoles(user.getStaffId());
				if(rightLists.size() == 0){
					msg = "{\"error\": \"RIGHT_INCORRECT\"}";
				} else {
					setSession(user, null, request);
				}	
			} else {
				if (havingSubMangerRight && !user.getBranchId().equals(currbranch)){
					setSession(user, currbranch, request);
				} else {	 
					setSession(user, null, request);
				}		
			}
			
			if(!user.getStaffId().equalsIgnoreCase("1") && !"{\"error\": \"RIGHT_INCORRECT\"}".equals(msg)){
				if(havingSubMangerRight){
					user.setBranchId(currbranch);
				} 
				writeloginlog(user, request);
			}
		}

		try {
			response.getWriter().write(msg);
		} catch (IOException e) {
			logger.error("LoginController login json error!");
		}
	}

	private void setSession(Staff user, String currbranch, HttpServletRequest request) {
		LoginUser loginUser = new LoginUser();
		List<Role> rightList = null;
		String oldBranchId = user.getBranchId();
		try {
			loginUser.setStaff(user);
			rightList = userService.findUserRoles(user.getStaffId());
			if(currbranch != null && oldBranchId != currbranch){
				user.setBranchId(currbranch);
				loginUser.setStaff(user);
				
				rightList = new ArrayList<Role>();
				try {
					Role sysRole = new Role();
					sysRole.setRoleId(SystemConstants.User.ADMIN_ID);

					PageBuilder pb = (PageBuilder) SpringUtil.getBean("pageBuilder");
					Map<String, ModelConfig> modelConfings = pb.getModelConfigs();

					List<RoleRelation> roleRelation = new ArrayList<RoleRelation>();
					RoleRelation srr;
					Entry<String, ModelConfig> modelEntry;
					Entry<String, PageConfig> pageEntry;
					
					for (Iterator<Entry<String, ModelConfig>> it = modelConfings.entrySet().iterator(); it.hasNext();) {
						modelEntry = it.next();
						for (Iterator<Entry<String, PageConfig>> pit = modelEntry.getValue().getPageConfigs()
								.entrySet().iterator(); pit.hasNext();) {
							pageEntry = pit.next();
							PageConfig pageConfig = (PageConfig) pageEntry.getValue();
							Branch branch = (Branch) userService.findOneByProperties(Branch.class,"branchId", currbranch);
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
										}else{
											//分店
											if(branch.getBranchType().equals("1")){
												if(!"1".equals(pageRole))
													continue;
													//pit.remove();
											} else if(branch.getBranchType().equals("2")){
												if(!"2".equals(pageRole))
													continue;
													//pit.remove();
											}
											//if(!RegExUtil.match(pageRole, userRole)) {
											//	pit.remove();
											//}
										}	
									}
								}
								srr = new RoleRelation();
								srr.setFuncrightId(pageEntry.getValue().getPageId());
								srr.setDatarightMap(pageEntry.getValue().getFunctions());
								roleRelation.add(srr);
							}
						}
					}
					//加载业务权限的部分
					Map<String, Object> rightstants = ReflectUtil.getVariableMap(RightConstants.RightType.class);
					for (Map.Entry<String, Object> entry : rightstants.entrySet()) {
						 /* System.out.println("key= " + entry.getKey() + " and value= "
			                     + entry.getValue());*/
						srr = new RoleRelation();
						srr.setFuncrightId(entry.getKey());
						roleRelation.add(srr);
					}
					sysRole.setRoleRelation(roleRelation);
					rightList.add(sysRole);
				} catch (Exception e) {
					logger.error("find user rights occurs error!" + e);
				}
			}
		} catch (Exception e) {
			logger.error("find user rights occurs error!" + e);
		}

		loginUser.setRightsList(rightList);
		loginUser.setSystemType(SystemConstants.SystemStyle.DEFAULT);

		HttpSession session = request.getSession(true);
		String sid = session.getId();
		loginUser.setSessionId(sid);
		session.setAttribute(RequestUtil.LOGIN_USER_SESSION_KEY, loginUser);
		final MySessionContext msc = MySessionContext.getInstance();
		msc.delSession(session, user.getStaffId());
		msc.addSession(session);
		CommonParams.setCommonParams(session.getId(), "loginUser", loginUser);
		if(currbranch != null){
			user.setBranchId(oldBranchId);
		}	
	}

	/**
	 * 登出
	 * 
	 * @param userId
	 * @param request
	 */
	@RequestMapping("/logout.do")
	public ModelAndView logout(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/ipmspage/login/login");
		HttpSession session = request.getSession(true);

		try {
			session.removeAttribute("tempStatus");
			session.removeAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		} catch (Exception e) {
			logger.error("user logout occurs error!" + e);
		}

		return mv;
	}

	private boolean backDoor(String userInfo, Staff registerUser, HttpServletRequest request,
			HttpServletResponse response) {
		if (SystemConstants.User.ADMIN_NAME.equalsIgnoreCase(userInfo) || registerUser != null) {
			try {
				if (registerUser == null) {
					registerUser = new Staff();
					registerUser.setStaffId(SystemConstants.User.ADMIN_ID);
					registerUser.setStaffName(SystemConstants.User.ADMIN_NAME);
					registerUser.setBranchId(SystemConstants.User.BRANCH_ID);
					registerUser.setDepartId(SystemConstants.User.DEPART_ID);
					registerUser.setPost(SystemConstants.User.POST);
				}

				LoginUser loginUser = new LoginUser();
				loginUser.setStaff(registerUser);

				List<Role> rightList = new ArrayList<Role>();
				try {
					Role sysRole = new Role();
					sysRole.setRoleId(SystemConstants.User.ADMIN_ID);

					PageBuilder pb = (PageBuilder) SpringUtil.getBean("pageBuilder");
					Map<String, ModelConfig> modelConfings = pb.getModelConfigs();

					List<RoleRelation> roleRelation = new ArrayList<RoleRelation>();
					RoleRelation srr;
					Entry<String, ModelConfig> modelEntry;
					Entry<String, PageConfig> pageEntry;
					for (Iterator<Entry<String, ModelConfig>> it = modelConfings.entrySet().iterator(); it.hasNext();) {
						modelEntry = it.next();
						for (Iterator<Entry<String, PageConfig>> pit = modelEntry.getValue().getPageConfigs()
								.entrySet().iterator(); pit.hasNext();) {
							pageEntry = pit.next();
							srr = new RoleRelation();
							srr.setFuncrightId(pageEntry.getValue().getPageId());
							srr.setDatarightMap(pageEntry.getValue().getFunctions());
							roleRelation.add(srr);
						}
					}
					sysRole.setRoleRelation(roleRelation);
					rightList.add(sysRole);
				} catch (Exception e) {
					logger.error("find user rights occurs error!" + e);
				}
				loginUser.setRightsList(rightList);
				loginUser.setSystemType(SystemConstants.SystemStyle.DEFAULT);

				HttpSession session = request.getSession(true);
				String sid = session.getId();
				loginUser.setSessionId(sid);
				session.setAttribute(RequestUtil.LOGIN_USER_SESSION_KEY, loginUser);

				final MySessionContext msc = MySessionContext.getInstance();
				msc.addSession(session);

				CommonParams.setCommonParams(session.getId(), "loginUser", loginUser);

				response.getWriter().write("{\"sucess\": \"OK\"}");
				return true;
			} catch (IOException e) {
			}
		}
		return false;
	}

	@RequestMapping("/shiftTime.do")
	public void shiftTime(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// List<?> shiftTime = userService.getShifttime();
		// String data = ((Map<?, ?>)
		// shiftTime.get(0)).get("ORDER_NO").toString();
		 LoginUser loginUser = (LoginUser)request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		 String branchid = loginUser.getStaff().getBranchId();
		 Branch branchrank = (Branch)userService.findOneByProperties(Branch.class, "branchId", branchid);
		 String data_s = "";
		 Map<String,List<?>> resultMap =new HashMap<String,List<?>>();
		 
		 if(!(loginUser.getStaff().getStaffId().equals(SystemConstants.User.ADMIN_ID ))
				// && !(branchrank.getRank().equals("0")) 
				// && !(branchrank.getBranchType().equals("2"))
				 ){
			
				List<?> ordernoList = userService.findBySQL("shifttime2", new String[] { branchid ,branchid ,branchid },true);
				
				if(ordernoList.size()>0){
					 data_s = ((Map<?, ?>) ordernoList.get(0)).get("SHIFTTIME_ID").toString();
					 List<?> othershiftlist = userService.findBySQL("newshiftcontent", new String[] { branchid }, true);
					 resultMap.put("current", ordernoList);
					 resultMap.put("other", othershiftlist);
					 JSONUtil.responseJSON(response, new AjaxResult(1, data_s, resultMap));
				}else{
					JSONUtil.responseJSON(response, new AjaxResult(0, "默认班次失败或班次未配置，请前往crm配置班次！", ""));
				}
				
		 }else{
			 JSONUtil.responseJSON(response, new AjaxResult(0,"默认班次失败或班次未配置",""));
		 }
		
	}

	public void writeloginlog(Staff user, HttpServletRequest request) throws Exception {
		String logId = DateUtil.currentDate("yyMMdd") + CommonConstants.LoginSource.BRANCH + userService.getSequence("SEQ_LOGINLOG_ID");
		LoginLog loginlog = new LoginLog();
		String operid = InetAddress.getLocalHost().toString();// IP地址
		operid = (String) operid.subSequence(operid.indexOf("/") + 1, operid.length());
		String header = request.getHeader("User-Agent").toLowerCase();
		if (header == null || header.equals("")) {
			loginlog.setBrowser(" ");
		}
		if (header.indexOf("chrome") > 0) {
			loginlog.setBrowser("chrome");
		} else if (header.indexOf("firefox") > 0) {
			loginlog.setBrowser("firefox");
		} else if (header.indexOf("msie") > 0) {
			loginlog.setBrowser("ie");
		} else if (header.indexOf("safari") > 0) {
			loginlog.setBrowser("safari");
		} else if (header.indexOf("camino") > 0) {
			loginlog.setBrowser("camino");
		} else if (header.indexOf("konqueror") > 0) {
			loginlog.setBrowser("konqueror");
		}

		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchid = loginuser.getStaff().getBranchId();
		Branch branch = ((Branch) (userService.findOneByProperties(Branch.class, "branchId", branchid)));
		String theme = branch.getBranchType().toString();
		String state = null;
		if (theme.equals(CommonConstants.SystemTheme.HOTEL)) {
			state = "0";
		}
		LoginLogId loginlogid = new LoginLogId();
		loginlogid.setLogId(logId);
		loginlogid.setLoginId(user.getStaffId());
		loginlog.setLoginLogId(loginlogid);
		loginlog.setLoginIp(operid);
		loginlog.setRecordTime(new Date());
		loginlog.setSource(CommonConstants.LoginSource.BRANCH);
		loginlog.setStatus("1");
		loginlog.setState(state);
		try {
			userService.save(loginlog);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/welcomelogin.do")
	public ModelAndView welcomelogin(HttpServletRequest request, HttpServletResponse response, String exbegintime,
			String exendtime, String exceptiontype, String exceptionstatus) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/page/ipmspage/login/login.jsp");
		//List<?> loginBranchs =userService.findByProperties(Branch.class, "status", "1");
		//if(loginBranchs != null  && loginBranchs.size() >= 0)
		//	mv.addObject("loginBranchs", loginBranchs);
		return mv;
	}
	
	@RequestMapping("/queryAllBranchInDB.do")
	public void queryAllBranchInDB(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<?> loginBranchs = userService.findByProperties(Branch.class, "status", "1");
		Map<Object, Object> allBranchs = new HashMap<Object, Object>();
		if(loginBranchs != null  && loginBranchs.size() >= 0){
			allBranchs.put("loginBranchs", loginBranchs);
		}	
		JSONUtil.responseJSON(response, allBranchs);
	}
	
	
	//选择所属城市
	@RequestMapping("/branchDialog.do")
	public ModelAndView branchDialog(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/dialog/branchDialog");
		mv.addObject("dialogColumns", "branchId,branchName");
		mv.addObject("dialogTarget", "Branch");
		mv.addObject("dialogConditions", "status = '1' and branch_type = '1' ");
		mv.addObject("dialogQuickAdd", null);
		mv.addObject("selectType", "dialog-radio");
		mv.addObject("colName", "branch");
		return mv;
	}
	
	@RequestMapping("/loadBranchDialogData.do")
	public void loadBranchDialogData(String dialogTarget, String dialogColumns, String dialogConditions,
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
	
	/*
	 * 修改员工密码发送短信验证码
	 */
	@RequestMapping("/queryStaffPwd.do")
	public void queryStaffPwd(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String mobile = request.getParameter("mobile");
		Staff staff =  (Staff) userService.findOneByProperties(Staff.class, "mobile",mobile);
		if( staff != null){
//			String phoneNumber = staff.getMobile();
			String code= verificationCode(4);
			SmsSingleSender singleSender = new SmsSingleSender(SystemConstants.note.APPID,SystemConstants.note.APPKEY);
			ArrayList<String> message = new ArrayList<String>();
			message.add(code);
			SmsSingleSenderResult result = singleSender.sendWithParam(SystemConstants.note.COUNTRY,mobile, 43742, message, "", "", "");
			if("OK".equals(result.errMsg)){
				JSONUtil.responseJSON(response, new AjaxResult(2,code));
			}else{
				JSONUtil.responseJSON(response, new AjaxResult(1,"错误代码:"+result.errMsg));
			}
		}else{
			JSONUtil.responseJSON(response, new AjaxResult(1,"该手机号未注册!"));
		}
	}
	
	
	/*
	 * 重置员工密码
	 */
	@RequestMapping("/changStaffPwd.do")
	public void changStaffPwd(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String mobile = request.getParameter("mobile").trim();
		Staff staff =  (Staff) userService.findOneByProperties(Staff.class, "mobile",mobile);
		if( staff != null){
			staff.setPassword(MD5Util.getCryptogram("888888"));
//			Thread.sleep(20000);//第二条短信20秒后发送
			SmsSingleSender singleSender = new SmsSingleSender(SystemConstants.note.APPID,SystemConstants.note.APPKEY);
			ArrayList<String> message = new ArrayList<String>();
			message.add("888888");
			SmsSingleSenderResult code = singleSender.sendWithParam(SystemConstants.note.COUNTRY,mobile, 65831, message, "", "", "");
			if("OK".equals(code.errMsg)){
				userService.update(staff);
				JSONUtil.responseJSON(response, new AjaxResult(1,"密码重置成功!"));
			}else{
				JSONUtil.responseJSON(response, new AjaxResult(1,"错误代码:"+code.errMsg));
			}
		}else{
			JSONUtil.responseJSON(response, new AjaxResult(1,"该手机号未注册!"));
		}
	}
	
	public static String verificationCode(int length) {
		Random random = new Random();
		String verificationCode = "";
		for (int i = 0; i < length; i++) {
			int num;
			if(i==0){
				do{
					num = random.nextInt(10);
				}while(num == 0);
			}else{
				num = random.nextInt(10);
			}
			verificationCode += Integer.toString(num);
		}
		return verificationCode;
	}
}
