package com.ideassoft.crm.controller;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hsqldb.lib.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.ideassoft.bean.AdvertisePic;
import com.ideassoft.bean.Branch;
import com.ideassoft.bean.Picture;
import com.ideassoft.bean.Staff;
import com.ideassoft.bean.SysParam;
import com.ideassoft.bean.UserRole;
import com.ideassoft.bean.wrapper.FileMeta;
import com.ideassoft.core.ajax.AjaxResult;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.constants.CommonConstants;
import com.ideassoft.core.exception.DAOException;
import com.ideassoft.crm.service.InitialService;
import com.ideassoft.crm.service.ParameterService;
import com.ideassoft.util.JSONUtil;
import com.ideassoft.util.MD5Util;
import com.ideassoft.util.RequestUtil;
@Transactional
@Controller
public class ParameterController {
	@Autowired
	private ParameterService parameterService;
	
	@Autowired
	InitialService initialService;

	

	
	

	@RequestMapping("/delStaff.do")
	public void delStaff(String staffId, HttpServletRequest request, HttpServletResponse response) {
		//LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String[] staffIds = staffId.split(",");
		int flag = 1;
		for (String deleteId : staffIds ) {
			Staff staff = (Staff) parameterService.findOneByProperties(Staff.class, "staffId", deleteId);
			if (staff.getStatus().equals(CommonConstants.StaffStatus.JOIN) || 
					staff.getStatus().equals(CommonConstants.StaffStatus.ONESELF)) {
				flag = 3;
				break;
			} else if (staff.getPost().equals("1010")) {
				flag = 2;
			} 
		}
		JSONUtil.responseJSON(response, new AjaxResult(flag, ""));
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/deleteStaff.do")
	public void deleteStaff(String staffId, HttpServletRequest request, HttpServletResponse response) {
		String[] staffIds = staffId.split(",");
		//LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		for (String deleteId : staffIds ) {
			Staff staff = (Staff) parameterService.findOneByProperties(Staff.class, "staffId", deleteId, "status", "1");
			List<UserRole> userRoles = parameterService.findByProperties(UserRole.class, "userId", staff.getStaffId(), 
					"branchId", staff.getBranchId(), "status", "1");
			for (UserRole userRole : userRoles ) {
				userRole.setStatus("0");
				parameterService.update(userRole);
			}
			staff.setStatus("0");
			parameterService.update(staff);
		}
		JSONUtil.responseJSON(response, new AjaxResult(1, "操作成功！"));
	}
	
	
	
	
	@RequestMapping("/checkFile.do")
	public void checkFile(HttpServletRequest request, HttpServletResponse response, String filename) throws FileNotFoundException{
		String webPath = RequestUtil.getWebPath();
		webPath = webPath + "//upload//servicepolicy";
		InputStream inStream = new FileInputStream(webPath + "//" + filename);
		String ios_fileName = null;
		try {
			ios_fileName = new String(filename.getBytes("GBK"), "ISO-8859-1");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		// 设置输出的格式
		response.reset();
		response.setContentType("bin");
		response.addHeader("Content-Disposition", "attachment; filename=\"" + ios_fileName + "\"");
		// 循环取出流中的数据
		byte[] b = new byte[1024];
		int len;
		try {
			while ((len = inStream.read(b)) > 0)
				response.getOutputStream().write(b, 0, len);
			inStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/deleteFile.do")
	public void deleteFile(HttpServletRequest request, HttpServletResponse response, String filename){
		String webPath = RequestUtil.getWebPath();
		webPath = webPath + "//upload//servicepolicy";
		File file = new File(webPath + "//" + filename);
		if(file.exists()){
			file.delete();
		}
	}

}
