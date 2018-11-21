package com.ideassoft.crm.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ideassoft.bean.Equipment;
import com.ideassoft.bean.SysParam;
import com.ideassoft.core.ajax.AjaxResult;
import com.ideassoft.core.page.PageBuilder;
import com.ideassoft.core.page.Pagination;
import com.ideassoft.crm.service.AuditService;
import com.ideassoft.crm.service.CommonService;
import com.ideassoft.pmsmanage.service.PmsmanageService;
import com.ideassoft.util.HttpUtil;
import com.ideassoft.util.JSONUtil;

@Transactional
@Controller
public class AuditController {
	@Autowired
	private AuditService auditService;
	
	@Autowired
	PageBuilder pageBuilder;

	@Autowired
	private PmsmanageService pmsmanageService;

	@Autowired
	CommonService commonService;

	@RequestMapping("/loaddefaultauditGrid.do")
	public void loaddefaultauditGrid(String queryData, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Pagination pagination;
		Integer pageNum = Integer.parseInt(request.getParameter("page"));
		Integer rows = Integer.parseInt(request.getParameter("rows"));
		pagination = new Pagination(pageNum, rows);
		List<?> result = auditService.queryDefaultauditDialogData(queryData, pagination);
		JSONUtil.responseJSON(response, JSONUtil.buildReportJSONByPagination(result, pagination));
	}

	// 默认审核人修改
	@RequestMapping("/auditUserSure.do")
	public void auditUserSure(HttpServletRequest request, HttpServletResponse response, String auditId)
			throws Exception {
		SysParam sysParam = ((SysParam) (auditService.findOneByProperties(SysParam.class, "paramType", "AUDIT")));
		sysParam.setContent(auditId);
		auditService.save(sysParam);
		JSONUtil.responseJSON(response, new AjaxResult(1, null));
	}

	

	/*
	 * Pagination pagination = null;
	 * 
	 * Integer pageNum = Integer.parseInt(request.getParameter("page")); Integer
	 * rows = Integer.parseInt(request.getParameter("rows")); if (pageNum !=
	 * null && rows != null) { pagination = new Pagination(pageNum, rows); }
	 */

	

	/*
	 * @RequestMapping("/saleform.do") public void saleform(HttpServletRequest
	 * request, HttpServletResponse response, String SaleJson) throws Exception
	 * {
	 * 
	 * List list1 = Arrays.asList(SaleJson); list1 = new ArrayList(list1);
	 * ArrayList<String> als = new ArrayList<String>(list1); List data = new
	 * ArrayList(); String d = "saletype"; //String[] nameArray =
	 * request.getParameterValues("saletype"); //String[] s =
	 * request.getParameterValues("saletype"); JSONArray saletypearray = new
	 * JSONArray(SaleJson);
	 * 
	 * for (int i = 0; i < saletypearray.length(); i++) { JSONObject jsonsale =
	 * (JSONObject) saletypearray.get(i);
	 * //if(!((jsonsale.has("saletype"))&&(jsonsale.has("saletype2")))){
	 * if(!list1.contains(d)){ String content = jsonsale.getString("content");
	 * String paramname = jsonsale.getString("paramname"); SysParam sysParam =
	 * ((SysParam) (AuditService.findOneByProperties(SysParam.class,
	 * "paramType", "SALETYPE","content", content)));
	 * sysParam.setParamName(URLDecoder.decode(paramname, "UTF-8"));
	 * AuditService.save(sysParam); } else if(list1.contains(d)){ //else
	 * if(!((jsonsale.has("content"))&&(jsonsale.has("paramname")))){
	 * 
	 * data.add(URLDecoder.decode(jsonsale.getString("saletype"), "UTF-8"));
	 * JSONArray st = new JSONArray(s); for(int a = 0; a < st.length(); a++){
	 * 
	 * } }else
	 * if(((jsonsale.has("content"))&&(jsonsale.has("paramname")))&&list1
	 * .contains(d)){ String content = jsonsale.getString("content"); String
	 * paramname = jsonsale.getString("paramname"); SysParam sysParam =
	 * ((SysParam) (AuditService.findOneByProperties(SysParam.class,
	 * "paramType", "SALETYPE","content", content)));
	 * sysParam.setParamName(URLDecoder.decode(paramname, "UTF-8"));
	 * AuditService.save(sysParam);
	 * data.add(jsonsale.getString(URLDecoder.decode
	 * (jsonsale.getString("saletype"), "UTF-8")));
	 * 
	 * }
	 * 
	 * String logId = this.AuditService.getSequence("SEQ_NEW_PARAMID",null);
	 * String contentseq =
	 * this.AuditService.getSequence("SEQ_SALETYPE_CONTENT",null); SysParam
	 * sysparam = new SysParam(); sysparam.setParamId(logId);
	 * sysparam.setParamName(URLDecoder.decode(saletype,"UTF-8"));
	 * sysparam.setParamType("SALETYPE"); sysparam.setStatus("1");
	 * sysparam.setContent(contentseq);
	 * 
	 * Map<String, String> map = new HashMap<String, String>(); map.put(content,
	 * URLDecoder.decode(paramname, "UTF-8")); data.add(map); }
	 * URLDecoder.decode(, "UTF-8")
	 * 
	 * 
	 * JSONUtil.responseJSON(response, new AjaxResult(1, null)); }
	 */


	
/*	*//**
	 * 提交新增城市,20180531注释掉zhongll
	 * @param request
	 * @param response
 * @throws Exception 
	 * @throws Exception
	 *//*
	@SuppressWarnings("unchecked")
	@RequestMapping("/submitAddCity.do")
	public void submitAddCity(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String fileName = null;
		FileOutputStream fos = null;
		InputStream is = null;
		String headPicUrl = "";
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile srcFile = multipartRequest.getFile("file");
		String districtId = auditService.getSequence("SEQ_DISTRICT_ID");
		String streetId = auditService.getSequence("SEQ_STREET_ID");
		String othersId = auditService.getSequence("SEQ_OTHERS_ID");
		String orderNumber = request.getParameter("orderNumber");
		String status = request.getParameter("status");
		String hot = request.getParameter("hot");
		
		try {
			String webPath = RequestUtil.getWebPath(request);
			if (StringUtils.isEmpty(fileName)) {
				fileName = srcFile.getOriginalFilename();
				fileName = new Date().getTime()+fileName.substring(fileName.lastIndexOf("."));
			}
			File srcFolder = new File(webPath + File.separator + "upload");
			if (!srcFolder.exists()) {
				srcFolder.mkdirs();
			}
			headPicUrl = CommonConstants.Domain.DOMAINNAME + fileName;
			File tarFile = new File(srcFolder.getAbsolutePath() + File.separator + fileName);
			if (!tarFile.exists()) {
				tarFile.createNewFile();
			}
			fos = new FileOutputStream(tarFile);
			is = srcFile.getInputStream();
			int ln = 0;
			byte[] buf = new byte[1024];
			while ((ln = is.read(buf)) != -1) {
				fos.write(buf, 0, ln);
			}
			fos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				fos.close();
			}
			if (is != null) {
				is.close();
			}
		}
		
		String city = request.getParameter("city");
		String adminiName = request.getParameter("adminiName");
		String district = request.getParameter("district");
		String adminiCode = "";
		City cityByCode = (City) auditService.findOneByProperties(City.class, "adminiCode", city, "status", "1");
		if(cityByCode!=null){
			JSONUtil.responseJSON(response, new AjaxResult(2, "城市编码已存在"));
			return;
		}
		String rank = request.getParameter("rank");
		if (rank.equals("2")) {
			adminiCode = city.substring(0, 4) + districtId + "000000";
		} else if (rank.equals("3")) {
			adminiCode = district.substring(0, 6) + streetId + "000";
		} else {
			adminiCode = district.substring(0, 6) + othersId ;
		}
		List cityByOrder = auditService.findByProperties(City.class, "orderNumber", orderNumber, "status", "1");
		if(cityByOrder.size()>0){
			JSONUtil.responseJSON(response, new AjaxResult(-1, "推荐排序已存在"));
			return;
		}else{
			City cityInfo = new City();
			cityInfo.setAdminiCode(adminiCode);
			cityInfo.setAdminiName(adminiName);
			cityInfo.setOrderNumber(orderNumber);
			cityInfo.setRank(rank);
			cityInfo.setRecordTime(new Date());
			cityInfo.setStatus(status);
			cityInfo.setCityPicture(headPicUrl);
			cityInfo.setRemark(String.valueOf(ChineseCharacterUtil.convertHanzi2Pinyin(adminiName).charAt(0)).toLowerCase() + "#" + hot);
			LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
			Staff staff = loginUser.getStaff();
			String recordUser = staff.getStaffId();
			cityInfo.setRecordUser(recordUser);
			try {
				auditService.getHibernateTemplate().saveOrUpdate(cityInfo);
				JSONUtil.responseJSON(response, new AjaxResult(1, "添加成功"));
			} catch (Exception e) {
				JSONUtil.responseJSON(response, new AjaxResult(0, "添加失败"));
			}
		}
	}*/
	
}
