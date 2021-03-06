package com.ideassoft.basic.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.hsqldb.lib.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.ideassoft.basic.service.GoodsBasicService;
import com.ideassoft.bean.City;
import com.ideassoft.bean.Goods;
import com.ideassoft.bean.GoodsCategory;
import com.ideassoft.bean.Member;
import com.ideassoft.bean.MemberAccount;
import com.ideassoft.bean.Staff;
import com.ideassoft.bean.SysParam;
import com.ideassoft.core.ajax.AjaxResult;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.constants.CommonConstants;
import com.ideassoft.util.DateUtil;
import com.ideassoft.util.JSONUtil;
import com.ideassoft.util.ReflectUtil;
import com.ideassoft.util.RequestUtil;

@Controller
public class GoodsManageController {

	private static final String initialService = null;
	@Autowired
	private GoodsBasicService goodsService;

	@RequestMapping("/addGoodsType.do")
	public void addGoodsType(HttpServletRequest request,
			HttpServletResponse response, String PARAMID) throws Exception {
		SysParam sysParam;
		if (null != PARAMID) {
			sysParam = (SysParam) ReflectUtil.setBeansFromJsonArray(request,
					"SysParam").get(0);
			sysParam.setParamType("SUPERCATEGORY");
			sysParam.setStatus("1");
			goodsService.update(sysParam);
		} else {
			sysParam = new SysParam();
			sysParam = (SysParam) ReflectUtil.setBeansFromJsonArray(request,
					"SysParam").get(0);
			sysParam.setParamId(goodsService.getSequence("SEQ_SYSPARAM_ID"));
			sysParam.setParamType("SUPERCATEGORY");
			sysParam.setStatus("1");
			goodsService.save(sysParam);
		}
	}

	@RequestMapping("/isUniqueGoodsType.do")
	public void isUniqueGoodsType(String colValue, String idInfo,
			HttpServletResponse response) throws Exception {
		SysParam sysParam = null;
		if (StringUtils.isEmpty(idInfo)) {
			sysParam = (SysParam) goodsService.findOneByProperties(
					SysParam.class, "paramType", "SUPERCATEGORY", "content",
					colValue, "status", "1");
			if (null != sysParam) {
				response.getWriter().write("{ \"result\":" + (false) + " }");
			}
		} else {
			response.getWriter().write("{ \"result\":" + (true) + " }");
		}
	}

	@RequestMapping("/delGoodsType.do")
	public void delGoodsType(HttpServletRequest request,
			HttpServletResponse response, String paramId) throws Exception {
		String[] paramIds = paramId.split(",");
		SysParam sysParam;
		List<Object> list = new ArrayList<Object>();
		for (int i = 0; i < paramIds.length; i++) {
			sysParam = (SysParam) goodsService.findById(SysParam.class,
					paramIds[i]);
			if (sysParam != null) {
				sysParam.setStatus("0");
				list.add(sysParam);
			}
		}

		if (!list.isEmpty()) {
			goodsService.saveOrUpdateAll(list);
		}

		String result = "{\"code\":\"1\"}";
		JSONUtil.responseJSON(response, result);
	}

	@RequestMapping("/addGoodsPicMark.do")
	public ModelAndView addGoodsPicMark(HttpServletRequest request)
			throws Exception {
		ModelAndView mv = new ModelAndView("page/basic/goods/loadCityPicMark");//

		String CATEGORYID = request.getParameter("CATEGORYID");
		GoodsCategory gc = (GoodsCategory) goodsService.findOneByProperties(
				GoodsCategory.class, "status", "1", "categoryId", CATEGORYID);
		String url = "";
		if (gc.getPicture() != null) {
			url = CommonConstants.Domain.DOMAINNAME + gc.getPicture();
		}
		mv.addObject("CATEGORYID", CATEGORYID);
		mv.addObject("url", url);
		return mv;
	}

	@RequestMapping("/addNewCityPictureMark.do")
	@SuppressWarnings("unchecked")
	public void addNewCityPictureMark(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String fileName = null;
		FileOutputStream fos = null;
		InputStream is = null;
		// MultipartFile srcFile = multipartRequest.getFile("file");
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if (multipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			String CATEGORYID = multiRequest.getParameter("CATEGORYID");
			Iterator<String> iter = multiRequest.getFileNames();
			while (iter.hasNext()) {
				List<MultipartFile> list = multiRequest.getFiles(iter.next());
				for (MultipartFile file : list) {
					String myFileName = file.getOriginalFilename();
					if (myFileName != null && !"".equals(myFileName)) {

						try {

							String webPath = RequestUtil.getWebPath(request);

							fileName = file.getOriginalFilename();
							fileName = new Date().getTime()
									+ fileName.substring(fileName
											.lastIndexOf("."));

							File srcFolder = new File(webPath + File.separator
									+ "upload");
							if (!srcFolder.exists()) {
								srcFolder.mkdirs();
							}
							File tarFile = new File(srcFolder.getAbsolutePath()
									+ File.separator + fileName);
							if (!tarFile.exists()) {
								tarFile.createNewFile();
							}
							fos = new FileOutputStream(tarFile);
							is = file.getInputStream();
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

						if (fileName != null && !"".equals(fileName)) {
							GoodsCategory gc = (GoodsCategory) goodsService
									.findOneByProperties(GoodsCategory.class,
											"status", "1", "categoryId",
											CATEGORYID);
							if (gc != null) {
								gc.setPicture(fileName);
								goodsService.update(gc);
							}
						}

					}
				}

			}

			JSONUtil.responseJSON(response, new AjaxResult(1, "操作成功!"));

		}
	}

	@RequestMapping("/addGoodsPic.do")
	@SuppressWarnings("unchecked")
	public ModelAndView addGoodsPic(HttpServletRequest request)
			throws Exception {
		ModelAndView mv = new ModelAndView("page/basic/goods/goodsPicNew");

		String adminiCode = request.getParameter("adminiCode");
		Goods goods = (Goods) goodsService.findOneByProperties(Goods.class,
				"status", "1", "goodsId", adminiCode);

		List pictures = new ArrayList();
		if (goods.getPicture() != null) {
			String[] picturesArray = goods.getPicture().split(",");
			for (int i = 0; i < picturesArray.length; i++) {
				// Picture pic = (Picture)
				// manageService.findOneByProperties(Picture.class, "status",
				// "1", "adminiCode", adminiCode);
				// pictures.add(picturesArray[i]);
				String url = CommonConstants.Domain.DOMAINNAME
						+ picturesArray[i];
				pictures.add(url);

			}
		}
		mv.addObject("operateType", "goodspic");
		mv.addObject("pic_str", goods.getPicture());
		mv.addObject("adminiCode", adminiCode);
		mv.addObject("cityInfo", goods);
		mv.addObject("pictures", pictures);
		return mv;
	}

	// 商品分类删除
	@RequestMapping("/delCategory.do")
	public void delCategory(HttpServletRequest request,
			HttpServletResponse response, String categoryid)
			throws UnknownHostException {
		LoginUser loginUser = (LoginUser) request.getSession(true)
				.getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginUser.getStaff();
		String recordUser = staff.getStaffId();
		String[] categoryids = categoryid.split(",");
		for (int i = 0; i < categoryids.length; i++) {
			if (categoryids[i] != null) {
				GoodsCategory goodscategory = ((GoodsCategory) (goodsService
						.findOneByProperties(GoodsCategory.class, "categoryId",
								categoryids[i])));
				goodscategory.setStatus("0");
				goodscategory.setRecordUser(recordUser);
				goodscategory.setRecordTime(new Date());
			}
		}
		JSONUtil.responseJSON(response, new AjaxResult(1, null));
	}

	// 商品删除
	@RequestMapping("/delGoods.do")
	public void delGoods(HttpServletRequest request,
			HttpServletResponse response, String goodsid)
			throws UnknownHostException {
		LoginUser loginUser = (LoginUser) request.getSession(true)
				.getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginUser.getStaff();
		String recordUser = staff.getStaffId();
		String[] goodsids = goodsid.split(",");
		for (int i = 0; i < goodsids.length; i++) {
			if (goodsids[i] != null) {
				Goods goods = ((Goods) (goodsService.findOneByProperties(
						Goods.class, "goodsId", goodsids[i])));
				goods.setStatus("0");
				goods.setRecordUser(recordUser);
				goods.setRecordTime(new Date());
			}
		}
		JSONUtil.responseJSON(response, new AjaxResult(1, null));
	}

	@RequestMapping("/addNewGoodPicture.do")
	@SuppressWarnings("unchecked")
	public void addNewCityPicture(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String fileName = null;
		FileOutputStream fos = null;
		InputStream is = null;
		// MultipartFile srcFile = multipartRequest.getFile("file");
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if (multipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			String pic_str = multiRequest.getParameter("pic_str");
			String adminiCode = multiRequest.getParameter("adminiCode");
			String operateType = multiRequest.getParameter("operateType");
			Iterator<String> iter = multiRequest.getFileNames();
			while (iter.hasNext()) {
				List<MultipartFile> list = multiRequest.getFiles(iter.next());
				for (MultipartFile file : list) {
					String myFileName = file.getOriginalFilename();
					if (myFileName != null && !"".equals(myFileName)) {

						try {

							String webPath = RequestUtil.getWebPath(request);

							fileName = file.getOriginalFilename();
							fileName = new Date().getTime()
									+ fileName.substring(fileName
											.lastIndexOf("."));

							File srcFolder = new File(webPath + File.separator
									+ "upload");
							if (!srcFolder.exists()) {
								srcFolder.mkdirs();
							}
							File tarFile = new File(srcFolder.getAbsolutePath()
									+ File.separator + fileName);
							if (!tarFile.exists()) {
								tarFile.createNewFile();
							}
							fos = new FileOutputStream(tarFile);
							is = file.getInputStream();
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
						if ("".equals(pic_str)) {
							pic_str = fileName;
						} else {
							pic_str = pic_str + "," + fileName;

						}

					}
				}

			}
			if ("citypic".equals(operateType)) {
				City cityInfo = (City) goodsService.findOneByProperties(
						City.class, "adminiCode", adminiCode, "status", "1");
				if (cityInfo != null) {
					cityInfo.setPictures(pic_str);
					goodsService.update(cityInfo);
				}
			} else {
				Goods goods = (Goods) goodsService.findOneByProperties(
						Goods.class, "goodsId", adminiCode, "status", "1");
				if (goods != null) {
					goods.setPicture(pic_str);
					goodsService.update(goods);
				}
			}
			JSONUtil.responseJSON(response, new AjaxResult(1, "操作成功!"));

		}
	}

	/**
	 * 商品添加时门店选择框
	 * 
	 * @param request
	 * @param response
	 * @param branchTheme
	 * @return
	 */
	@RequestMapping("/selectGoodsBranch.do")
	public ModelAndView selectGoodsBranch(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/dialog/branch4Dialog");
		mv.addObject("dialogColumns", "branchId,branchName");
		mv.addObject("dialogTarget", "Branch");
		mv.addObject("dialogConditions", "status='1'");
		mv.addObject("dialogQuickAdd", null);
		mv.addObject("selectType", "dialog");
		mv.addObject("colName", "branch_select");
		return mv;
	}

	/**
	 * 商品添加时商品大类选择框
	 * 
	 * @param request
	 * @param response
	 * @param branchTheme
	 * @return
	 */
	@RequestMapping("/selectGoodsType.do")
	public ModelAndView selectGoodsType(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/dialog/branch4Dialog");
		mv.addObject("dialogColumns", "content,paramName");
		mv.addObject("dialogTarget", "SysParam");
		mv.addObject("dialogConditions",
				"PARAM_TYPE = 'SUPERCATEGORY' and STATUS = '1'");
		mv.addObject("dialogQuickAdd", null);
		mv.addObject("selectType", "dialog-radio");
		mv.addObject("colName", "superCategory_select");
		return mv;
	}

	/**
	 * 添加商品类别页面
	 * 
	 * @param request
	 * @param response
	 * @return 添加页面
	 */
	@RequestMapping("/addCrmGoodsType.do")
	public ModelAndView addCrmGoodsType(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/basic/goods/goodsType");
		return mv;
	}

	/**
	 * 保存新添加的商品分类
	 * 
	 * @param request
	 * @param response
	 * @param categoryName 类别名 
	 * @param branchId 门店速度
	 * @param chargeRoom 是否自营
	 * @param superCategory 大类名
	 * @param status 状态
	 * @param remark 备注
	 */
	@RequestMapping("/saveCrmGoodsType.do")
	public void saveCrmGoodsType (HttpServletRequest request,
			HttpServletResponse response,String categoryName, String branchId,
			String chargeRoom, String superCategory, String status,
			String remark){
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String recordUser = staff.getStaffId();
		String[] branchIds = null;
		if (!StringUtils.isEmpty(branchId)) {
			branchIds = branchId.split(",");
		}
		if (branchIds.length > 0) {
			for (int i = 0; i < branchIds.length; i++) {
				GoodsCategory gc = new GoodsCategory();
				gc.setBranchId(branchIds[i]);
				gc.setCategoryId(goodsService.getSequence("SEQ_NEW_GOODSCATEGORY",null));
				gc.setCategoryName(categoryName);
				gc.setChargeRoom(chargeRoom);
				gc.setRecordTime(new Date());
				gc.setRecordUser(recordUser);
				gc.setRemark(remark);
				gc.setStatus(status);
				gc.setSuperCategory(superCategory);
				goodsService.save(gc);
			}
			JSONUtil.responseJSON(response, new AjaxResult(1, null));
		} else {
			JSONUtil.responseJSON(response, new AjaxResult(-1, null));
		}
	}
	
	/**
	 * 修改商品类别页面
	 * 
	 * @param request
	 * @param response
	 * @param categoryName 商品种类名称
	 * @param branchId 门店编码
	 * @param chargeRoom 是否自营
	 * @param superCategory 商品大类
	 * @param status 状态
	 * @param remark 备注
	 * @param categoryId 类别数据编号
	 * @return 编辑页面
	 */
	@RequestMapping("/editCrmGoodsType.do")
	public ModelAndView editCrmGoodsType(HttpServletRequest request,
			HttpServletResponse response, String categoryName, String branchId,
			String chargeRoom, String superCategory, String status,
			String remark, String categoryId) {
		GoodsCategory gc = (GoodsCategory) goodsService.findOneByProperties(GoodsCategory.class, "categoryId", categoryId);
		ModelAndView mv = new ModelAndView();
		mv.addObject("gc",gc);
		mv.addObject("categoryName",categoryName);
		mv.addObject("branchId",branchId);
		mv.addObject("chargeRoom",chargeRoom);
		mv.addObject("superCategory",superCategory);
		mv.addObject("status",status);
		mv.addObject("remark",remark);
		mv.addObject("categoryId",categoryId);
		mv.setViewName("page/basic/goods/editGoodsType");
		return mv;
	}
	
	/**
	 * 更新已有的商品分类
	 * 
	 * @param request
	 * @param response
	 * @param categoryName 类别名 
	 * @param branchId 门店速度
	 * @param chargeRoom 是否自营
	 * @param superCategory 大类名
	 * @param status 状态
	 * @param remark 备注
	 */
	@RequestMapping("/editSaveCrmGoodsType.do")
	public void editSaveCrmGoodsType (HttpServletRequest request,
			HttpServletResponse response,String categoryName, String branchId,
			String chargeRoom, String superCategory, String status,
			String remark,String categoryId){
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String recordUser = staff.getStaffId();
		
		GoodsCategory gc = (GoodsCategory) goodsService.findOneByProperties(GoodsCategory.class, "categoryId", categoryId);
		gc.setBranchId(branchId);
		gc.setCategoryName(categoryName);
		gc.setChargeRoom(chargeRoom);
		gc.setRecordTime(new Date());
		gc.setRecordUser(recordUser);
		gc.setRemark(remark);
		gc.setStatus(status);
		gc.setSuperCategory(superCategory);
		goodsService.update(gc);
		JSONUtil.responseJSON(response, new AjaxResult(1, null));
		
	}
	
	/**
	 * 商品添加时商品大类选择框
	 * 
	 * @param request
	 * @param response
	 * @param branchTheme
	 * @return
	 */
	@RequestMapping("/selectCategoryIdType.do")
	public ModelAndView selectCategoryIdType(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/dialog/branch4Dialog");
		mv.addObject("dialogColumns", "categoryId,categoryName");
		mv.addObject("dialogTarget", "GoodsCategory");
		mv.addObject("dialogConditions", "status = '1'");
		mv.addObject("dialogQuickAdd", null);
		mv.addObject("selectType", "dialog-radio");
		mv.addObject("colName", "categoryId_select");
		return mv;
	}
	
	/**
	 * 商品添加时供应商选择框
	 * 
	 * @param request
	 * @param response
	 * @param branchTheme
	 * @return
	 */
	@RequestMapping("/selectSupplierId.do")
	public ModelAndView selectSupplierId(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/dialog/branch4Dialog");
		mv.addObject("dialogColumns", "supplierId,supplierName");
		mv.addObject("dialogTarget", "Supplier");
		mv.addObject("dialogConditions", "status = '1'");
		mv.addObject("dialogQuickAdd", null);
		mv.addObject("selectType", "dialog-radio");
		mv.addObject("colName", "supplierId_select");
		return mv;
	}
	
	/**
	 * 商品添加时售卖类型选择框
	 * 
	 * @param request
	 * @param response
	 * @param branchTheme
	 * @return
	 */
	@RequestMapping("/selectSaleType.do")
	public ModelAndView selectSaleType(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/dialog/branch4Dialog");
		mv.addObject("dialogColumns", "content,paramName");
		mv.addObject("dialogTarget", "SysParam");
		mv.addObject("dialogConditions", "PARAM_TYPE = 'SALETYPE' and STATUS = '1'");
		mv.addObject("dialogQuickAdd", null);
		mv.addObject("selectType", "dialog-radio");
		mv.addObject("colName", "saleType_select");
		return mv;
	}
	
	/**
	 * 商品添加时状态选择框
	 * 
	 * @param request
	 * @param response
	 * @param branchTheme
	 * @return
	 */
	@RequestMapping("/selectStatus.do")
	public ModelAndView selectStatus(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/dialog/branch4Dialog");
		mv.addObject("dialogColumns", "content,paramName");
		mv.addObject("dialogTarget", "SysParam");
		mv.addObject("dialogConditions", "PARAM_TYPE = 'GOODSSTATUS' and STATUS = '1'");
		mv.addObject("dialogQuickAdd", null);
		mv.addObject("selectType", "dialog-radio");
		mv.addObject("colName", "status_select");
		return mv;
	}
	
	/**
	 * 添加商品资料页面
	 * 
	 * @param request
	 * @param response
	 * @return 添加页面
	 */
	@RequestMapping("/addCrmGoodsManage.do")
	public ModelAndView addCrmGoodsManage(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/basic/goods/addCrmGoodsManage");
		return mv;
	}
	
	/**
	 * 保存商品资料功能
	 * 
	 * @param request
	 * @param response
	 * @param goodsName 商品名称
	 * @param categoryId 商品类别
	 * @param branchId 门店
	 * @param supplierId 供应商
	 * @param price 价格
	 * @param specifications 规格
	 * @param unit 单位
	 * @param saleType 售货类型
	 * @param status 状态
	 * @param integral 积分兑换
	 * @param productionDate 生成日期
	 * @param remark 备注
	 */
	@RequestMapping("/saveCrmGoodsManage.do")
	public void saveCrmGoodsManage (HttpServletRequest request,
			HttpServletResponse response,String goodsName, String categoryId,
			String branchId, String supplierId, String price,
			String specifications, String unit, String saleType,
			String status, String integral, String productionDate,
			String remark){
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String recordUser = staff.getStaffId();
		Date newProductionDate = null;
		if(!StringUtil.isEmpty(productionDate)){
			newProductionDate = DateUtil.s2d(productionDate, "yyyy/MM/dd");
		}
		String[] branchIds = null;
		if (!StringUtils.isEmpty(branchId)) {
			branchIds = branchId.split(",");
		}
		if (branchIds.length > 0) {
			for (int i = 0; i < branchIds.length; i++) {
				Goods gc = new Goods();
				gc.setBranchId(branchIds[i]);
				gc.setCategoryId(categoryId);
				gc.setGoodsId(goodsService.getSequence("SEQ_NEW_GOODS",null));
				gc.setGoodsName(goodsName);
				if(StringUtil.isEmpty(integral)){
					gc.setIntegral(0);
				}else{
					gc.setIntegral(Integer.valueOf(integral));
				}
				gc.setPrice(Double.valueOf(price));
				if(newProductionDate != null){
					gc.setProductionDate(newProductionDate);
				}
				gc.setRecordTime(new Date());
				gc.setRecordUser(recordUser);
				gc.setRemark(remark);
				gc.setSaleType(saleType);
				gc.setSpecifications(specifications);
				gc.setStatus(status);
				gc.setSupplierId(supplierId);
				gc.setUnit(unit);
				goodsService.save(gc);
			}
			JSONUtil.responseJSON(response, new AjaxResult(1, null));
		} else {
			JSONUtil.responseJSON(response, new AjaxResult(-1, null));
		}
	}
	
	/**
	 * 保存商品资料功能
	 * 
	 * @param request
	 * @param response
	 * @param goodsName 商品名称
	 * @param categoryId 商品类别
	 * @param branchId 门店
	 * @param supplierId 供应商
	 * @param price 价格
	 * @param specifications 规格
	 * @param unit 单位
	 * @param saleType 售货类型
	 * @param status 状态
	 * @param integral 积分兑换
	 * @param productionDate 生成日期
	 * @param remark 备注
	 * @param goodsId 商品编号
	 */
	@RequestMapping("/editCrmGoodsManage.do")
	public ModelAndView editCrmGoodsManage (HttpServletRequest request,
			HttpServletResponse response,String goodsName, String categoryId,
			String branchId, String supplierId, String price,
			String specifications, String unit, String saleType,
			String status, String integral, String productionDate,
			String remark, String goodsId){
		Goods gc = (Goods) goodsService.findOneByProperties(Goods.class, "goodsId", goodsId);
		ModelAndView mv = new ModelAndView();
		mv.addObject("gc",gc);
		mv.addObject("goodsName",goodsName);
		mv.addObject("categoryId",categoryId);
		mv.addObject("branchId",branchId);
		mv.addObject("supplierId",supplierId);
		mv.addObject("price",price);
		mv.addObject("specifications",specifications);
		mv.addObject("unit",unit);
		mv.addObject("saleType",saleType);
		mv.addObject("status",status);
		mv.addObject("integral",integral);
		mv.addObject("productionDate",productionDate);
		mv.addObject("remark",remark);
		mv.addObject("goodsId",goodsId);
		mv.setViewName("page/basic/goods/editCrmGoodsManage");
		return mv;
	}
	
	/**
	 * 保存编辑后的商品资料信息
	 * 
	 * @param request
	 * @param response
	 * @param goodsName 商品名称
	 * @param categoryId 商品类别
	 * @param branchId 门店
	 * @param supplierId 供应商
	 * @param price 价格
	 * @param specifications 规格
	 * @param unit 单位
	 * @param saleType 售货类型
	 * @param status 状态
	 * @param integral 积分兑换
	 * @param productionDate 生产时间
	 * @param remark 备注
	 * @param goodsId 商品编号
	 */
	@RequestMapping("/saveEditCrmGoodsManage.do")
	public void saveEditCrmGoodsManage (HttpServletRequest request,
			HttpServletResponse response,String goodsName, String categoryId,
			String branchId, String supplierId, String price,
			String specifications, String unit, String saleType,
			String status, String integral, String productionDate,
			String remark, String goodsId){
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String recordUser = staff.getStaffId();
		Date newProductionDate = null;
		if(!StringUtil.isEmpty(productionDate)){
			newProductionDate = DateUtil.s2d(productionDate, "yyyy/MM/dd");
		}
		Goods gc = (Goods) goodsService.findOneByProperties(Goods.class, "goodsId", goodsId);
		gc.setBranchId(branchId);
		gc.setCategoryId(categoryId);
		gc.setGoodsName(goodsName);
		if(StringUtil.isEmpty(integral)){
			gc.setIntegral(0);
		}else{
			gc.setIntegral(Integer.valueOf(integral));
		}
		gc.setPrice(Double.valueOf(price));
		gc.setProductionDate(newProductionDate);
		gc.setRecordTime(new Date());
		gc.setRecordUser(recordUser);
		gc.setRemark(remark);
		gc.setSaleType(saleType);
		gc.setSpecifications(specifications);
		gc.setStatus(status);
		gc.setSupplierId(supplierId);
		gc.setUnit(unit);
		goodsService.update(gc);
			
		JSONUtil.responseJSON(response, new AjaxResult(1, null));
		
	}
	
	
	@RequestMapping("/submitGoodsPicturesNew.do")
	@SuppressWarnings("unchecked")
	public void submitGoodsPicturesNew(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String fileName = null;
		FileOutputStream fos = null;
		InputStream is = null;
		String headPicUrl = "";
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile srcFile = multipartRequest.getFile("file");
		
		String adminiCode = multipartRequest.getParameter("adminiCode");
		Goods goods = (Goods) goodsService.findOneByProperties(Goods.class,
				"status", "1", "goodsId", adminiCode);
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
		
		if (goods.getPicture() == null ) {
			goods.setPicture(fileName);
			goodsService.update(goods);
		} else if (goods.getPicture() != null) {
			String goodFileName = goods.getPicture();
			goodFileName = goodFileName + "," + fileName;
			goods.setPicture(goodFileName);
			goodsService.update(goods);
		}

		JSONUtil.responseJSON(response, new AjaxResult(1, "操作成功"));

	}
	
	@RequestMapping("/delPicsInGoods.do")
	@SuppressWarnings("unchecked")
	public void delPicsInGoods(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String adminiCode = request.getParameter("adminiCode");
		String pictureId = request.getParameter("pictureId");
		String[] filenamelist = pictureId.split("/");
		Goods goods = (Goods) goodsService.findOneByProperties(Goods.class,"status", "1", "goodsId", adminiCode);
		if (goods.getPicture() != null) {
			String goodspic = goods.getPicture().replace(filenamelist[filenamelist.length-1], "").replace(",,", ",");
			if(goodspic.indexOf(",") == 0){
				goodspic = goodspic.substring(1, goodspic.length());
			}
			if(goodspic.lastIndexOf(",") == goodspic.length()-1){
				goodspic = goodspic.substring(0, goodspic.length() -1);
			}
			if(goodspic != null){
				goodspic = goodspic.replace(",,", ",");
			}
			goods.setPicture(goodspic);
			String webPath = RequestUtil.getWebPath(request);
			String path = webPath.replace("\\","/") + "/upload/";
			java.io.File myFilePath = new java.io.File(path + filenamelist[filenamelist.length-1]);
		    myFilePath.delete();
		    goodsService.update(goods);   
			JSONUtil.responseJSON(response, new AjaxResult(1, "操作成功!"));
		} else {
			JSONUtil.responseJSON(response, new AjaxResult(0, "操作失败!"));
		}
		
	}
}
