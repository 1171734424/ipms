package com.ideassoft.pmsinhouse.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ideassoft.bean.HouseAccount;
import com.ideassoft.core.ajax.AjaxResult;
import com.ideassoft.core.annotation.interfaces.RightMethodControl;
import com.ideassoft.core.annotation.interfaces.RightModelControl;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.constants.RightConstants;
import com.ideassoft.pmsinhouse.service.HouseOrderService;
import com.ideassoft.util.JSONUtil;
import com.ideassoft.util.RequestUtil;
@Transactional
@Controller
@RightModelControl(rightModel = RightConstants.RightModel.HOUSE_CONTROL)
public class CheckInWorkController {

	
	@Autowired
	private HouseOrderService houseOrderService;
	/**
	 * 打卡
	 * @author ideas_zhongll
	 * @date 2018-05-29 
	 * @param request
	 * @return
	 */
	@RequestMapping("/clockIn.do")
	@RightMethodControl(rightType = RightConstants.RightType.HO_CLOCK)
	public ModelAndView clockIn(HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		mv.setViewName("page/ipmshouse/leftmenu/houseOrder/clockIn");
		List<HouseAccount> list = houseOrderService.findByProperties(HouseAccount.class, "staffId", loginUser.getStaff().getStaffId(), "status","1","workStatus","1");
		if(list.size() > 0){
			mv.addObject("workstatus","1");
		}else{
			mv.addObject("workstatus","0");
		}
		return mv;
		
	}
	/**
     * 提交打卡
	 * @author ideas_zhongll
	 * @date 2018-05-29 
	 * @param request
	 * @return
	 */
	@RequestMapping("/submitClockIn.do")
	public void submitClockIn(HttpServletRequest request, HttpServletResponse response) throws Exception{
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String workStatus = request.getParameter("status");
		String flag = request.getParameter("flag");//0一次，1是二次确认过
		if("1".equals(workStatus)){
			List<HouseAccount> list = houseOrderService.findByProperties(HouseAccount.class, "staffId", loginUser.getStaff().getStaffId(), "status","1","workStatus","0");
			for(int i = 0;i<list.size();i++){
				HouseAccount ha = list.get(i);
				List<HouseAccount> listWorking = houseOrderService.findByProperties(HouseAccount.class, "houseAccountName",ha.getHouseAccountName(),"status","1","workStatus","1");
				if(listWorking.size() > 0){
					JSONUtil.responseJSON(response, new AjaxResult(2,"打卡失败,"+ha.getHouseAccountName()+"账号下有员工正在上班"));
					return;
				}
			}
			for(int i = 0;i<list.size();i++){
				HouseAccount ha = list.get(i);
				ha.setWorkStatus(workStatus);
				ha.setRecordTime(new Date());
				ha.setRecordUser(loginUser.getStaff().getStaffId());
				houseOrderService.update(ha);
			}
			JSONUtil.responseJSON(response, new AjaxResult(1,"上班打卡成功!"));
		}else if("0".equals(workStatus)){
			if("0".equals(flag)){
				//做个校验，是否账户中最后一个正在上班的人
				List<HouseAccount> list = houseOrderService.findByProperties(HouseAccount.class, "staffId", loginUser.getStaff().getStaffId(), "status","1","workStatus","1");
				for(int i = 0;i<list.size();i++){
					HouseAccount ac = list.get(i);
					List<HouseAccount> workOnList = houseOrderService.findByProperties(HouseAccount.class, "houseAccountName", ac.getHouseAccountName(), "status","1","workStatus","1");
					if(workOnList.size() == 1){
						JSONUtil.responseJSON(response, new AjaxResult(2,"您下班后将存在没有管家的民宿，是否确认下班?"));
						return;
					}
				}
				
				//如果校验都通过，直接下班
				List<HouseAccount> listTwo = houseOrderService.findByProperties(HouseAccount.class, "staffId", loginUser.getStaff().getStaffId(), "status","1");
				for(int i = 0;i<listTwo.size();i++){
					HouseAccount ha = listTwo.get(i);
					ha.setWorkStatus(workStatus);
					ha.setRecordTime(new Date());
					ha.setRecordUser(loginUser.getStaff().getStaffId());
					houseOrderService.update(ha);
				}
				JSONUtil.responseJSON(response, new AjaxResult(1,"下班打卡成功!"));
				
			}else if("1".equals(flag)){
				List<HouseAccount> list = houseOrderService.findByProperties(HouseAccount.class, "staffId", loginUser.getStaff().getStaffId(), "status","1");
				for(int i = 0;i<list.size();i++){
					HouseAccount ha = list.get(i);
					ha.setWorkStatus(workStatus);
					ha.setRecordTime(new Date());
					ha.setRecordUser(loginUser.getStaff().getStaffId());
					houseOrderService.update(ha);
				}
				JSONUtil.responseJSON(response, new AjaxResult(1,"下班打卡成功!"));
			}
		}
		
		
	}
	
}
