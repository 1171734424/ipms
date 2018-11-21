package com.ideassoft.basic.controller;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ideassoft.basic.service.HouseAccountBasicService;
import com.ideassoft.bean.HouseAccount;
import com.ideassoft.core.ajax.AjaxResult;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.util.JSONUtil;
import com.ideassoft.util.RequestUtil;


@Transactional
@Controller
public class HouseAccountController {
	@Autowired
	private HouseAccountBasicService houseAccountBasicService;

	
	@RequestMapping("/addHouseAccount.do")
	public ModelAndView addHouseAccount(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("page/basic/houseAccount/addHouseAccount");
		return mv;
	}
	
	@RequestMapping("/selectHouseStaff.do")
	public ModelAndView selectHouseStaff(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/dialog/customDialog");
		mv.addObject("dialogColumns", "staffId,staffName");
		mv.addObject("dialogTarget", "Staff");
		//mv.addObject("dialogConditions", "status='1' and branchId =$BRANCHID and post !=" + CommonConstants.Postgrade.SuperManagerPost);
		mv.addObject("dialogConditions", "status='1' and branchId =$BRANCHID ");
		mv.addObject("dialogQuickAdd", null);
		mv.addObject("selectType", "dialog");
		mv.addObject("colName", "staffname");
		return mv;
	}
	
	@RequestMapping("/submitHouseAccount.do")
	public void submitHouseAccount(HttpServletRequest request,
			HttpServletResponse response,String staffId,String accountName) throws Exception {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		List<?> list = houseAccountBasicService.findByProperties(HouseAccount.class, "houseAccountName",accountName);
		if(list.size() > 0){
			JSONUtil.responseJSON(response, new AjaxResult(2, "账户名已存在!"));
		}else{
			String[] staff = staffId.split(",");
			for(int i = 0;i<staff.length;i++){
				HouseAccount ha = new HouseAccount();
				ha.setHouseAccountId(houseAccountBasicService.getSequence("SEQ_HOUSEACCOUNT_ID"));
				ha.setHouseAccountName(accountName);
				ha.setRecordTime(new Date());
				ha.setRecordUser(loginUser.getStaff().getStaffId());
				ha.setStaffId(staff[i]);
				ha.setStatus("1");
				ha.setWorkStatus("0");
				houseAccountBasicService.save(ha);
			}
			JSONUtil.responseJSON(response, new AjaxResult(1, "添加成功,民宿管理员重新打卡后方可接收预订信息!"));
		}
		
	
	}
	
	
	@RequestMapping("/editHouseAccount.do")
	public ModelAndView eddHouseAccount(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String accountName = request.getParameter("accountName");
		List<?> list = houseAccountBasicService.findHouseAccount(accountName);
		ModelAndView mv = new ModelAndView("page/basic/houseAccount/editHouseAccount");
		mv.addObject("account", list.get(0));
		return mv;
	}
	

	@RequestMapping("/updateHouseAccount.do")
	public void updateHouseAccount(HttpServletRequest request,
			HttpServletResponse response,String staffId,String accountName) throws Exception {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		List<HouseAccount> list = houseAccountBasicService.findByProperties(HouseAccount.class, "houseAccountName",accountName,"status","1");
		List<String> staffOldList = new ArrayList<String>(); 
		for(int i = 0;i<list.size();i++){
			staffOldList.add(list.get(i).getStaffId());
		}
		String[] staff = staffId.split(",");
		//找出减少的
		for(int i = 0;i<staffOldList.size();i++){
			if(!Arrays.asList(staff).contains(staffOldList.get(i))){
				HouseAccount ha = (HouseAccount) houseAccountBasicService.findOneByProperties(HouseAccount.class, "houseAccountName", accountName, "staffId",staffOldList.get(i));
				ha.setRecordTime(new Date());
				ha.setRecordUser(loginUser.getStaff().getStaffId());
				ha.setStatus("0");
				ha.setWorkStatus("0");
				houseAccountBasicService.update(ha);
			}
		}
		//找出新增的
		for(int i = 0;i<staff.length;i++){
			if(!staffOldList.contains(staff[i])){
				//判断库里是否存在以前删除的数据
				HouseAccount account = (HouseAccount) houseAccountBasicService.findOneByProperties(HouseAccount.class, "houseAccountName", accountName, "staffId",staff[i],"status","0");
				if(account == null){
					HouseAccount ha = new HouseAccount();
					ha.setHouseAccountId(houseAccountBasicService.getSequence("SEQ_HOUSEACCOUNT_ID"));
					ha.setHouseAccountName(accountName);
					ha.setRecordTime(new Date());
					ha.setRecordUser(loginUser.getStaff().getStaffId());
					ha.setStaffId(staff[i]);
					ha.setStatus("1");
					ha.setWorkStatus("0");
					houseAccountBasicService.save(ha);
				}else{
					account.setStatus("1");
					account.setWorkStatus("0");
					houseAccountBasicService.update(account);
				}
				
			}
		}
		
		
		JSONUtil.responseJSON(response, new AjaxResult(1, "编辑成功,民宿管理员重新打卡后方可生效!"));
		
	}
}
