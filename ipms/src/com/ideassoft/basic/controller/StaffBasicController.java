package com.ideassoft.basic.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hsqldb.lib.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ideassoft.basic.service.StaffBasicService;
import com.ideassoft.bean.HouseAccount;
import com.ideassoft.bean.Staff;
import com.ideassoft.core.ajax.AjaxResult;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.util.JSONUtil;
import com.ideassoft.util.MD5Util;
import com.ideassoft.util.RequestUtil;

@Transactional
@Controller
public class StaffBasicController {
	@Autowired
	private StaffBasicService staffBasicService;
	
	
	@RequestMapping("/staffUpdate.do")
	public void staffUpdate(String STAFFID, String STAFFNAME, String LOGINNAME, String BRANCHID_CUSTOM_VALUE, String DEPARTID_CUSTOM_VALUE,
			String POST_CUSTOM_VALUE, String GENDOR, String IDCARD, String MOBILE, String BIRTHDAY, String ENTRYTIME, String STATUS,
			String EMAIL, String ADDRESS, String REMARK, HttpServletRequest request, HttpServletResponse response) throws Exception {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String flag = "add";
		if(STAFFID != null && STAFFID.trim().length()==8){
			flag = "update";
		}
		List<?> infoByPhone = staffBasicService.checkStaffRepeat( "mobile", MOBILE);
		List<?> infoByLoingName = staffBasicService.checkStaffRepeat("login_name", LOGINNAME);
		List<?> infoBYIdCard = staffBasicService.checkStaffRepeat("idcard", IDCARD);
		
		if ((flag.equals("add") && infoByPhone != null && infoByPhone.size() >= 1) || (flag.equals("update") && infoByPhone != null && infoByPhone.size() > 1)) {
			JSONUtil.responseJSON(response, new AjaxResult(1, "已有该手机号，请重新输入!"));
		} else if ((flag.equals("add") && infoByLoingName != null && infoByLoingName.size() >= 1) || (flag.equals("update") && infoByLoingName != null  && infoByLoingName.size() > 1)) {
			JSONUtil.responseJSON(response, new AjaxResult(2, "已有该登录名，请重新输入!"));
		} else if ((flag.equals("add") && infoBYIdCard != null  && infoBYIdCard.size() >= 1) || (flag.equals("update") && infoBYIdCard != null && infoBYIdCard.size() > 1)) {
			JSONUtil.responseJSON(response, new AjaxResult(3, "已有该身份证号，请重新输入!"));
		} else { 
			//JSONUtil.responseJSON(response, new AjaxResult(4, "成功!"));
			if (STAFFID != null && !StringUtil.isEmpty(STAFFID.trim())) {
				Staff staffinfo = (Staff) staffBasicService.findOneByProperties(Staff.class, "staffId", STAFFID);
				String staffpassowrd = staffinfo.getPassword().toString();
				SimpleDateFormat sdf2=new SimpleDateFormat("yyyyMMdd");
				String birthstr = IDCARD.substring(6, 14);
				//根据身份证截取生日
				Date birthday = sdf2.parse(birthstr);
	/*			parameterService.updateStaff(STAFFID, STAFFNAME, LOGINNAME, BRANCHID_CUSTOM_VALUE, DEPARTID_CUSTOM_VALUE, POST_CUSTOM_VALUE, staffpassowrd,
						GENDOR, IDCARD, MOBILE, BIRTHDAY, ENTRYTIME, STATUS, EMAIL, ADDRESS, REMARK);*/
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
				staffinfo.setStaffName(STAFFNAME);
				staffinfo.setLoginName(LOGINNAME);
				if (("").equals(BRANCHID_CUSTOM_VALUE)) {
					staffinfo.setBranchId(loginUser.getStaff().getBranchId());
				} else {
					staffinfo.setBranchId(BRANCHID_CUSTOM_VALUE);
				}
				staffinfo.setDepartId(DEPARTID_CUSTOM_VALUE);
				staffinfo.setPost(POST_CUSTOM_VALUE);
				staffinfo.setGendor(GENDOR);
				staffinfo.setIdcard(IDCARD);
				staffinfo.setMobile(MOBILE);
				staffinfo.setBirthday(birthday);
				staffinfo.setEntryTime(sdf.parse(ENTRYTIME));
				staffinfo.setStatus(STATUS);
				staffinfo.setEmail(EMAIL);
				staffinfo.setAddress(ADDRESS);
				staffinfo.setRecordTime(new Date());
				staffinfo.setRemark(REMARK);
			} else {
				LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
				String staffId = staffBasicService.getCloudSequence("SEQ_STAFF_ID", null);
				String pwd = MD5Util.getCryptogram("888888");
				DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
				SimpleDateFormat sdf2=new SimpleDateFormat("yyyyMMdd");
				String birthstr = IDCARD.substring(6, 14);
				//根据身份证截取生日
				Date birthday = sdf2.parse(birthstr);
				Date entrytime;
				if (ENTRYTIME != null && !StringUtil.isEmpty(ENTRYTIME.trim())) {
					entrytime = df.parse(ENTRYTIME);
				} else {
					entrytime = null;
				}
				Staff staff = new Staff();
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
				if (("").equals(BRANCHID_CUSTOM_VALUE)) {
					staff.setBranchId(loginUser.getStaff().getBranchId());
				} else {
					staff.setBranchId(BRANCHID_CUSTOM_VALUE);
				}
				staff.setStaffId(staffId);
				staff.setPassword(pwd);
				staff.setStaffName(STAFFNAME);
				staff.setLoginName(LOGINNAME);
				staff.setDepartId(DEPARTID_CUSTOM_VALUE);
				staff.setPost(POST_CUSTOM_VALUE);
				staff.setGendor(GENDOR);
				staff.setIdcard(IDCARD);
				staff.setMobile(MOBILE);
				staff.setBirthday(birthday);
				staff.setEntryTime(entrytime);
				staff.setStatus(STATUS);
				staff.setEmail(EMAIL);
				staff.setAddress(ADDRESS);
				staff.setRecordTime(new Date());
				staff.setRemark(REMARK);
				staff.setRecordUser(loginuser.getStaff().getStaffId());
				staffBasicService.save(staff);
			}
		}
	}
	
	@RequestMapping("/delHouseAccount.do")
	public void delHouseAccount(HttpServletRequest request,
			HttpServletResponse response,String accountName) throws Exception {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String[] account = accountName.split(",");
		if(account.length > 1){
			for(int i = 0;i<account.length;i++){
				List<?> houseList = staffBasicService.findBySQL("queryHouseByAccount", new String[] { account[i] },true);
				if(houseList.size() > 0){
					JSONUtil.responseJSON(response, new AjaxResult(2, "存在已绑定民宿的账号，无法全部删除，请逐个操作!"));
					return;
				}
			}
			
			for(int i = 0;i<account.length;i++){
				List<HouseAccount> list = staffBasicService.findByProperties(HouseAccount.class, "houseAccountName",account[i],"status","1");
				for(int j=0;j<list.size();j++){
					HouseAccount ha = list.get(j);
					ha.setStatus("0");
					ha.setWorkStatus("0");
					ha.setRecordUser(loginUser.getStaff().getStaffId());
					staffBasicService.update(ha);
				}
			}
			
			JSONUtil.responseJSON(response, new AjaxResult(1, "删除成功,民宿管理员重新打卡后方可生效!"));
		}else if(account.length == 1){
			List<?> houseListOther = staffBasicService.findBySQL("queryHouseByAccount", new String[] { account[0] },true);
			if(houseListOther.size() > 0){
				JSONUtil.responseJSON(response, new AjaxResult(3, "该账号已绑定有效民宿，无法删除"));
				return;
			}
			List<HouseAccount> list = staffBasicService.findByProperties(HouseAccount.class, "houseAccountName",account[0],"status","1");
			for(int j=0;j<list.size();j++){
				HouseAccount ha = list.get(j);
				ha.setStatus("0");
				ha.setWorkStatus("0");
				ha.setRecordUser(loginUser.getStaff().getStaffId());
				staffBasicService.update(ha);
			}
			JSONUtil.responseJSON(response, new AjaxResult(1, "删除成功,民宿管理员重新打卡后方可生效!"));
			
		}
		
		
	}
}
