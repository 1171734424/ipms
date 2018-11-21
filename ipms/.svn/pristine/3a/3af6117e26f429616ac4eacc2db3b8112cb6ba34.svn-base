package com.ideassoft.crm.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ideassoft.bean.Branch;
import com.ideassoft.bean.Department;
import com.ideassoft.bean.Post;
import com.ideassoft.bean.Staff;
import com.ideassoft.core.ajax.AjaxResult;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.constants.CommonConstants;
import com.ideassoft.core.session.MySessionContext;
import com.ideassoft.crm.service.UserService;
import com.ideassoft.util.JSONUtil;
import com.ideassoft.util.RequestUtil;

@Transactional
@Controller
public class UserController {
	private Logger logger = Logger.getLogger(UserController.class);

	@Autowired
	private UserService userService;





	@RequestMapping("/userDelete.do")
	public void userDelete(String userIds, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String[] ids = userIds.split(",");
		final MySessionContext msc = MySessionContext.getInstance();
		String message = "";
		Integer result = CommonConstants.AJAXRESULT.SUCESS;
		Staff staff;

		for (String id : ids) {
			if (msc.getSession(request.getSession(true), id) == null) {
				userService.delete(userService.findById(Staff.class, id));
				userService.removeUserRole(id);
			} else {
				result = CommonConstants.AJAXRESULT.FALSE;
				staff = (Staff) userService.findById(Staff.class, id);
				message += staff.getStaffName() + ",";
			}
		}

		JSONUtil.responseJSON(response, new AjaxResult(result, message));
	}


	

}
