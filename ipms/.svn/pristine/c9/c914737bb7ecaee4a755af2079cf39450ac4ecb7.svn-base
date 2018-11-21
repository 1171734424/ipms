package com.ideassoft.pmsinhouse.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ideassoft.bean.Staff;
import com.ideassoft.core.ajax.AjaxResult;
import com.ideassoft.core.annotation.interfaces.RightMethodControl;
import com.ideassoft.core.annotation.interfaces.RightModelControl;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.constants.RightConstants;
import com.ideassoft.pmsinhouse.service.HouseManageService;
import com.ideassoft.util.JSONUtil;
import com.ideassoft.util.RequestUtil;
@Controller
@RightModelControl(rightModel = RightConstants.RightModel.HOUSE_CONTROL)
public class HouseManagerController {
	@Autowired
	HouseManageService houseManageService;
	//跳至民宿管理员信息修改页面
	
	
	@RequestMapping("/editHouseStaff.do")
	@RightMethodControl(rightType =  RightConstants.RightType.HO_PEINFO)
	public ModelAndView editHouseManager(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
        String staffid = loginUser.getStaff().getStaffId();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Staff staff =  (Staff) houseManageService.findById(Staff.class, staffid);
      String birthday = sdf.format(staff.getBirthday());
      String entrytime = sdf.format(staff.getEntryTime());
      
		mv.addObject("staff",staff);	
		mv.addObject("birthday",birthday);	
		mv.addObject("entrytime",entrytime);	
		mv.setViewName("page/ipmshouse/leftmenu/houseMenager/houseManagerEdit");
		return mv;
	}

	
	        
	//修改民宿管理员信息
	@RequestMapping("/submithousestaff.do")
	public void submithousestaff(HttpServletRequest request,
			HttpServletResponse response,String staffname,String staffid, String loginname,
			String idcard,String mobile,String birthday,String entrytime,String email,String  address,
			String gender,String remark ) throws Exception {
		
		
	if(!StringUtils.isEmpty(staffid)){
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
        String branchId = loginUser.getStaff().getBranchId();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat sdf2=new SimpleDateFormat("yyyyMMdd");
        
        Staff staff =  (Staff) houseManageService.findById(Staff.class, staffid);
        String name = staff.getLoginName();
        String id = staff.getIdcard();
        String phone = staff.getMobile();
        
        String birthstr = idcard.substring(6, 14);
        
	    List<?> loginnamecheck =  houseManageService.findByProperties(Staff.class, "loginName", loginname,"status","1");
	    List<?> idcardcheck =  houseManageService.findByProperties(Staff.class, "idcard", idcard,"status","1");
	    List<?> mobilecheck =  houseManageService.findByProperties(Staff.class, "mobile", mobile,"status","1");
	    
		if(loginnamecheck.size()>0 && !loginname.equals(name)){
			JSONUtil.responseJSON(response, new AjaxResult(0, "登录名已存在!"));
		}else if(idcardcheck.size()>0 && !idcard.equals(id)){
			JSONUtil.responseJSON(response, new AjaxResult(0, "身份证号已存在!"));
		}else if(mobilecheck.size()>0 && !mobile.equals(phone)){
			JSONUtil.responseJSON(response, new AjaxResult(0, "手机号已存在!"));
			
		}else{
        	Staff manager = (Staff)houseManageService.findById(Staff.class, staffid); 
        	manager.setStaffName(staffname);
        	manager.setLoginName(loginname);
        	manager.setIdcard(idcard);
        	manager.setMobile(mobile);
        	manager.setBirthday(sdf2.parse(birthstr));
        	manager.setEntryTime(sdf.parse(entrytime));
        	manager.setEmail(email);
        	manager.setGendor(gender);
        	manager.setAddress(address);
        	manager.setRemark(remark);
        	manager.setRecordTime(new Date());
        	try{
        		houseManageService.update(manager);
        		JSONUtil.responseJSON(response, new AjaxResult(1, "修改成功!"));
        		}catch (Exception e) {
        			e.printStackTrace();
        			JSONUtil.responseJSON(response, new AjaxResult(2, "修改失败!"));
        		}
        		
		}
	}	

	}	
	
	
}
