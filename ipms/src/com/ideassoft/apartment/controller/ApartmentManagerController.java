package com.ideassoft.apartment.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ideassoft.apartment.service.ApartmentManagerService;
import com.ideassoft.bean.Staff;
import com.ideassoft.core.ajax.AjaxResult;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.constants.CommonConstants;
import com.ideassoft.core.constants.SystemConstants;
import com.ideassoft.core.page.Pagination;
import com.ideassoft.portal.DataDealPortalService;
import com.ideassoft.portal.IDataDealPortal;
import com.ideassoft.util.JSONUtil;
import com.ideassoft.util.ReflectUtil;
import com.ideassoft.util.RequestUtil;

@Transactional
@Controller
public class ApartmentManagerController {
	
	@Autowired
	private ApartmentManagerService apartmentRepairService;
	
	/**
	 * 公寓管家-左侧加载页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/apartmentclicktaff.do")
	public ModelAndView apartmentclicktaff(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/apartment/apartmentLeftmenu/apartmentmanager/apartmentManagerEditList");
		return mv;
	}
	
	/**
	 * 公寓管家-查询页面
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/apartmentManagerListInfo.do")
	public ModelAndView apartmentManagerListInfo(HttpServletRequest request)throws Exception {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		String memberName = request.getParameter("memberName");
		String mobile = request.getParameter("mobile");

		Pagination pagination = (Pagination) ReflectUtil.setBeanFromRequest(request, Pagination.class);
		if (pagination != null && pagination.getPageNum() == null) {
			pagination.setPageNum(1);
			pagination.setRows(14);
		}

		List<?> managerList = apartmentRepairService.findBySQLWithPagination(
				"querybranchmanager",
				new String[] { branchId, CommonConstants.Postgrade.Manager,
						memberName, mobile }, pagination, true);

		ModelAndView mv = new ModelAndView();
		mv.addObject("managerList", managerList);
		mv.addObject("mobile", mobile);
		mv.addObject("memberName", memberName);
		mv.addObject("pagination", pagination);
		mv.setViewName("page/apartment/apartmentLeftmenu/apartmentmanager/apartmentManagerEditListInfo");
		
		return mv;
	}
	
	/**
	 * 公寓管家-跳至公寓管家信息修改页面
	 * 
	 * @param request
	 * @param response
	 * @param staffid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/showApartmentManager.do")
	public ModelAndView showApartmentManager(HttpServletRequest request,
			HttpServletResponse response, String staffid) throws Exception {
		ModelAndView mv = new ModelAndView();
		Staff manager = (Staff) apartmentRepairService.findById(Staff.class,staffid);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

		String birthday = sdf.format(manager.getBirthday());
		String entrytime = sdf.format(manager.getEntryTime());
		mv.addObject("manager", manager);
		mv.addObject("birthday", birthday);
		mv.addObject("entrytime", entrytime);
		mv.setViewName("page/apartment/apartmentLeftmenu/apartmentmanager/apartmentManagerEdit");
		return mv;
	}
	
	/**
	 * 公寓管家-修改管家信息
	 * 
	 * @param request
	 * @param response
	 * @param staffname
	 * @param staffid
	 * @param loginname
	 * @param idcard
	 * @param mobile
	 * @param birthday
	 * @param entrytime
	 * @param email
	 * @param address
	 * @param gender
	 * @param remark
	 * @throws Exception
	 */
	@RequestMapping("/submitApartmentManager.do")
	public void submitApartmentManager(HttpServletRequest request,
			HttpServletResponse response, String staffname, String staffid,
			String loginname, String idcard, String mobile, String birthday,
			String entrytime, String email, String address, String gender,
			String remark) throws Exception {
		// int priority = 1;
		String name = "";
		String id = "";
		String phone = "";
		String a = "";
		// String systype = null;
		// SysParam sys =
		// (SysParam)apartmentCheckInService.findOneByProperties(SysParam.class,
		// "paramType", "SYSTEMTYPE");
		// if(sys != null){
		// systype = sys.getContent();
		// }
		// List<Staff> staffList = new ArrayList<Staff>();

		// if(HeartBeatClient.heartbeat){
		if (!StringUtils.isEmpty(staffid)) {

			LoginUser loginUser = (LoginUser) request.getSession(true)
					.getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
			String branchId = loginUser.getStaff().getBranchId();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			Staff manager = (Staff) apartmentRepairService.findById(
					Staff.class, staffid);
			// List<?> managerList =
			// apartmentCheckInService.findBySQL("querybranchmanager", new
			// String[] {branchId,CommonConstants.Postgrade.Manager} ,true);
			// if(managerList.size()>0 ){
			// name = ((Map<String,String>)managerList.get(0)).get("LOGIN_NAME")
			// == null ? "":
			// ((Map<String,String>)managerList.get(0)).get("LOGIN_NAME").toString();
			// id = ((Map<String,String>)managerList.get(0)).get("IDCARD") ==
			// null ? "":
			// ((Map<String,String>)managerList.get(0)).get("IDCARD").toString();
			// phone = ((Map<String,String>)managerList.get(0)).get("MOBILE") ==
			// null ? "":
			// ((Map<String,String>)managerList.get(0)).get("MOBILE").toString();
			// }
			name = manager.getLoginName();
			id = manager.getIdcard();
			phone = manager.getMobile();

			DataDealPortalService service = new DataDealPortalService();
			IDataDealPortal portal = service.getDataDealPortalPort();
			String backinfo = portal.call(
					SystemConstants.EnginType.COMMON_DATA,
					SystemConstants.Movement.CUSTOM_QUERY,
					"{ function: \"apartmentManager.checkstaff\", data:{staffid:"
							+ staffid + ",loginname:" + loginname + ",idcard:"
							+ idcard + ",mobile:" + mobile + ",branchId:"
							+ branchId + ",name:" + name + ",id:" + id
							+ ",phone:" + phone + "} }");

			if ("hasLoginname".equals(backinfo)) {
				JSONUtil.responseJSON(response, new AjaxResult(0, "登录名已存在!"));
			} else if ("hasIdcard".equals(backinfo)) {
				JSONUtil.responseJSON(response, new AjaxResult(0, "身份证号已存在!"));
			} else if ("hasMobile".equals(backinfo)) {
				JSONUtil.responseJSON(response, new AjaxResult(0, "手机号已存在!"));
			} else if ("ok".equals(backinfo)) {

				manager.setStaffName(staffname);
				manager.setLoginName(loginname);
				manager.setIdcard(idcard);
				manager.setMobile(mobile);
				manager.setBirthday(sdf.parse(birthday));
				manager.setEntryTime(sdf.parse(entrytime));
				manager.setEmail(email);
				manager.setGendor(gender);
				manager.setAddress(address);
				manager.setRemark(remark);
				manager.setRecordTime(new Date());
				// staffList.add(manager);
				try {
					apartmentRepairService.update(manager);
					JSONUtil.responseJSON(response, new AjaxResult(1, "修改成功!"));
				} catch (Exception e) {
					e.printStackTrace();
					JSONUtil.responseJSON(response, new AjaxResult(2, "修改失败!"));
				}
				// 数据同步
				// if(CommonConstants.SystemType.Branch.equals(systype) ){
				// SysParam param = (SysParam) apartmentCheckInService
				// .findOneByProperties(SysParam.class, "paramType",
				// SystemConstants.ParamType.BRANCH_IP, "paramName", "300001");
				//
				// Map<String, Object> staffmap = new HashMap<String, Object>();
				// staffmap.put("Staff", staffList);
				// TransferServcie.getInstance().transferData(priority,
				// param.getContent(),
				// JSONUtil.fromObject(staffmap).toString());
				// }

			}
		}
		/*
		 * }else{ a = "本地网络未连接！"; JSONUtil.responseJSON(response, new
		 * AjaxResult(0, a)); }
		 */
	}
}