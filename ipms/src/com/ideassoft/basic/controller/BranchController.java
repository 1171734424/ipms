package com.ideassoft.basic.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.apache.commons.lang.StringUtils;
import org.hsqldb.lib.StringUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.ideassoft.basic.service.BranchBasicService;
import com.ideassoft.bean.Branch;
import com.ideassoft.bean.BranchKeywords;
import com.ideassoft.bean.BranchPicture;
import com.ideassoft.bean.CashBox;
import com.ideassoft.bean.City;
import com.ideassoft.bean.Clean;
import com.ideassoft.bean.Goods;
import com.ideassoft.bean.GoodsCategory;
import com.ideassoft.bean.House;
import com.ideassoft.bean.Location;
import com.ideassoft.bean.OperateLog;
import com.ideassoft.bean.Picture;
import com.ideassoft.bean.PriceApply;
import com.ideassoft.bean.PriceRules;
import com.ideassoft.bean.Room;
import com.ideassoft.bean.RoomKey;
import com.ideassoft.bean.RoomPicture;
import com.ideassoft.bean.RoomPrice;
import com.ideassoft.bean.RoomPriceId;
import com.ideassoft.bean.RoomStatus;
import com.ideassoft.bean.RoomType;
import com.ideassoft.bean.RoomTypeKey;
import com.ideassoft.bean.ShiftTime;
import com.ideassoft.bean.Staff;
import com.ideassoft.bean.SysParam;
import com.ideassoft.core.ajax.AjaxResult;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.constants.CommonConstants;
import com.ideassoft.core.constants.SystemConstants;
import com.ideassoft.core.exception.DAOException;
import com.ideassoft.core.notifier.SmsSingleSender;
import com.ideassoft.crm.service.TransferServcie;
import com.ideassoft.priceRuleUtile.BasePriceUtile;
import com.ideassoft.util.DateUtil;
import com.ideassoft.util.JSONUtil;
import com.ideassoft.util.RegExUtil;
import com.ideassoft.util.RequestUtil;

@Transactional
@Controller
public class BranchController {
	@Autowired
	private BranchBasicService branchBasicService;
	private static Integer cnt = 1;
	
	

	
	@RequestMapping("/select4Branch.do")
	public ModelAndView select4Branch(HttpServletRequest request, HttpServletResponse response ,String branchTheme) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/dialog/branch3Dialog");
		mv.addObject("dialogColumns", "branchId,branchName");
		mv.addObject("dialogTarget", "Branch");
		mv.addObject("dialogConditions", "status='1' and branchId <> '100001' and branchType =" +branchTheme);
		mv.addObject("dialogQuickAdd", null);
		mv.addObject("selectType", "dialog-radio");
		mv.addObject("colName", "branch_select");
		return mv;
	}
	@RequestMapping("/select4Branch2.do")
	public ModelAndView select4Branch2(HttpServletRequest request, HttpServletResponse response ,String branchTheme) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/dialog/branch4Dialog");
		mv.addObject("dialogColumns", "branchId,branchName");
		mv.addObject("dialogTarget", "Branch");
		mv.addObject("dialogConditions", "status='1' and branchId <> '100001' and branchType =" +branchTheme);
		mv.addObject("dialogQuickAdd", null);
		mv.addObject("selectType", "dialog-radio");
		mv.addObject("colName", "branch_select");
		return mv;
	}
	// 门店新增页面跳转
	@SuppressWarnings("unchecked")
	@RequestMapping("/addBranch.do")
	public ModelAndView addBranchPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String staffBranchRank = "0";
		ModelAndView mv = new ModelAndView("page/basic/branch/addBranch");
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String staffId = loginuser.getStaff().getStaffId();
		String staffBranchId = "";
		Branch staffBranch = null;
		if(!staffId.equals("1")){
			staffBranchId = loginuser.getStaff().getBranchId();
			staffBranch = (Branch) branchBasicService.findOneByProperties(Branch.class, "branchId", staffBranchId);
			staffBranchRank = staffBranch.getRank();
		}
		
		List<SysParam> branchRankList = branchBasicService.queryBranchType();
		if(!staffId.equalsIgnoreCase("1")){
			for(int i=0;i<branchRankList.size();i++){
				String branchRank = ((SysParam)branchRankList.get(i)).getParamName();
				if(branchRank.equals("0")){
					branchRankList.remove(i);
				}
			}
		}
		List<SysParam> themeList = branchBasicService.queryTheme();
		for (int i = 0; i < themeList.size(); i++) {
			if (themeList.get(i).getParamName().equals(CommonConstants.Branch.HOUSE)) {
				themeList.remove(i);
			}
		}
		if(staffBranch!=null){
			if(!staffBranch.getRank().equals("0")){
				for(int i=0; i<themeList.size();i++){
					if(!staffBranch.getBranchType().equals(themeList.get(i).getContent())){
						themeList.remove(i);
					}
				}
			}
		}
		List<City> districtList = null;
		List<City> streetList = null;
		//List<City> circletList = null;
		List<City> cityList = branchBasicService.queryCity();
		
		List<Map<String, Object>> newCity = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < cityList.size(); i++) {
			Map<String, Object> el = new HashMap<String, Object>();
			String pinYin = getAlpha(cityList.get(i).getAdminiName());
			el.put("py", pinYin);
			el.put("name", cityList.get(i).getAdminiName());
			el.put("code", cityList.get(i).getAdminiCode());
			newCity.add(el);
		}
		
		String sql = "queryPlace";
		String cityAdminCode = cityList.get(0).getAdminiCode();
		String citySubCode = cityAdminCode.substring(0, 4) + "%";
		List<Branch> uperBranchList = branchBasicService.findByProperties(Branch.class, "rank", "0");
		if(uperBranchList.size()>0){
			for(int i=0;i<branchRankList.size();i++){
				String branchRank = ((SysParam)branchRankList.get(i)).getParamName();
				if(branchRank.equals("0")){
					branchRankList.remove(i);
				}
			}
		}else{
			for(int i=0;i<branchRankList.size();i++){
				String branchRank = ((SysParam)branchRankList.get(i)).getParamName();
				if(!staffBranchRank.trim().equals(branchRank)){
					branchRankList.remove(i);
				}
			}
		}
		districtList = branchBasicService.findBySQL(sql, new String[] { citySubCode, CommonConstants.CityRank.DISTRICT },
				true);
		// districtAdminCode = null;
		if(districtList != null && !districtList.isEmpty()){
			String districtAdminCode = ((Map<?, ?>) districtList.get(0)).get("ADMINI_CODE").toString();
			String districtSubCode = districtAdminCode.substring(0, 6) + "%";
			streetList = branchBasicService.findBySQL(sql, new String[] { districtSubCode, CommonConstants.CityRank.STREET },
					true);
			
			/*String circleSubCode = districtSubCode;
			circletList = branchBasicService.findBySQL(sql, new String[] { circleSubCode, CommonConstants.CityRank.CIRCLE },
					true);*/
		}
		
		mv.addObject("branchRankList", branchRankList);
		mv.addObject("staffBranchRank", staffBranchRank);
		mv.addObject("staffId", staffId);
		mv.addObject("uperBranchList", uperBranchList);
		mv.addObject("branchThemList", themeList);
		mv.addObject("cityList", cityList);
		mv.addObject("districtList", districtList);
		mv.addObject("streetList", streetList);
		//mv.addObject("circletList", circletList);
		mv.addObject("newCity", JSONUtil.fromObject(newCity));
		return mv;

	}
	// 酒店更新页面跳转
	/*@SuppressWarnings("unchecked")
	@RequestMapping("/editBranch.do")
	public ModelAndView editBranch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("page/basic/branch/editBranch");
		String branchId = request.getParameter("branchId");
		List<City> districtList = null;
		List<City> streetList = null;
		List<City> circletList = null;
		List<SysParam> branchRankList = branchBasicService.queryBranchType();
		List<SysParam> themeList = branchBasicService.queryTheme();
		for (int i = 0; i < themeList.size(); i++) {
			if (themeList.get(i).getParamName().equals(CommonConstants.Branch.HOUSE)) {
				themeList.remove(i);
			}
		}
		Branch branch = (Branch) branchBasicService.findOneByProperties(Branch.class, "branchId", branchId);
		Location location = branchBasicService.queryBranchLocation(branchId);
		
		List<City> cityList = branchBasicService.queryCity();
		String sql = "queryPlace";
		String cityAdminCode = branch.getCity();
		City city = (City) branchBasicService.findOneByProperties(City.class, "adminiCode", cityAdminCode);
		
		String disAdminCode = branch.getDistrict();
		City district = (City) branchBasicService.findOneByProperties(City.class, "adminiCode", disAdminCode);
		
		String strAdminCode = branch.getDistrict();
		City street = (City) branchBasicService.findOneByProperties(City.class, "adminiCode", strAdminCode);
		
		String cirAdminCode = branch.getDistrict();
		City circle = (City) branchBasicService.findOneByProperties(City.class, "adminiCode", cirAdminCode);

		String citySubCode = cityAdminCode.substring(0, 3) + "%";
		districtList = branchBasicService.findBySQL(sql, new String[] { citySubCode, CommonConstants.CityRank.DISTRICT },
				true);

		String districtSubCode = disAdminCode.substring(0, 6) + "%";
		streetList = branchBasicService.findBySQL(sql, new String[] { districtSubCode, CommonConstants.CityRank.STREET },
				true);

		String circleSubCode = districtSubCode;
		circletList = branchBasicService.findBySQL(sql, new String[] { circleSubCode, CommonConstants.CityRank.CIRCLE },
				true);

		String branchAddress = branch.getAddress();
		String branchAddressArr[] = branchAddress.split("-");
		String addresshead = branchAddress;
		String addresslast = branchAddressArr[branchAddressArr.length - 1];
		String addresslastInput = addresslast.substring(0, addresslast.length() - 1);
		mv.addObject("district", district);
		mv.addObject("city", city);
		mv.addObject("street", street);
		mv.addObject("circle", circle);
		mv.addObject("addresshead", addresshead);
		mv.addObject("addresslast", addresslast);
		mv.addObject("addresslastInput", addresslastInput);
		mv.addObject("branch", branch);
		mv.addObject("location", location);
		mv.addObject("branchRankList", branchRankList);
		mv.addObject("branchThemList", themeList);
		mv.addObject("cityList", cityList);
		mv.addObject("districtList", districtList);
		mv.addObject("streetList", streetList);
		mv.addObject("circletList", circletList);
		return mv;
	}*/
	@SuppressWarnings("unchecked")
	@RequestMapping("/editBranch.do")
	public ModelAndView editBranch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("page/basic/branch/editBranch");
		String branchId = request.getParameter("branchId");
		List<SysParam> branchRankList = branchBasicService.queryBranchType();
		List<SysParam> themeList = branchBasicService.queryTheme();
		for (int i = 0; i < themeList.size(); i++) {
			if (themeList.get(i).getParamName().equals(CommonConstants.Branch.HOUSE)) {
				themeList.remove(i);
			}
		}
		Branch branch = (Branch) branchBasicService.findOneByProperties(Branch.class, "branchId", branchId);
		Location location = branchBasicService.queryBranchLocation(branchId);
		
		List<City> cityList = branchBasicService.queryCity();
		List<Map<String, Object>> newCity = new ArrayList<Map<String, Object>>();
		
		for (int i = 0; i < cityList.size(); i++) {
			Map<String, Object> el = new HashMap<String, Object>();
			String pinYin = getAlpha(cityList.get(i).getAdminiName());
			el.put("py", pinYin);
			el.put("name", cityList.get(i).getAdminiName());
			el.put("code", cityList.get(i).getAdminiCode());
			newCity.add(el);
		}
		
		String sql = "queryPlace";
		String cityAdminCode = branch.getCity();
		City city = (City) branchBasicService.findOneByProperties(City.class, "adminiCode", cityAdminCode);
		String cityAdminName = city.getAdminiName();
		//找出该城市下的所有区
		String citySubCode = cityAdminCode.substring(0, 4) + "%";
		List<City> districtList = branchBasicService.findBySQL(sql, new String[] { citySubCode, CommonConstants.CityRank.DISTRICT },true);
		
		String disAdminCode = branch.getDistrict();
		City district = (City) branchBasicService.findOneByProperties(City.class, "adminiCode", disAdminCode);
		
		String property = CommonConstants.CityRank.STREET;//默认为3，街道
		String strAdminCode = branch.getStreet();

		List<City> streetList = null;//(街道,商业圈都用这个)
		if(strAdminCode == null){
			String cirAdminCode = branch.getCircle();
			if(cirAdminCode != null){
				property = CommonConstants.CityRank.CIRCLE;
				String disSubCode = disAdminCode.substring(0, 6) + "%";
				streetList =  branchBasicService.findBySQL(sql, new String[] { disSubCode, CommonConstants.CityRank.CIRCLE},true);
			}
		}else{
			//表示留的资料是街道的，找出该区下的街道
			String disSubCode = disAdminCode.substring(0, 6) + "%";
			streetList =  branchBasicService.findBySQL(sql, new String[] { disSubCode, CommonConstants.CityRank.STREET },true);
		}

		String branchAddress = branch.getAddress();
		String branchAddressArr[] = branchAddress.split("-");
		String addresshead = branchAddress;
		String addresslast = branchAddressArr[branchAddressArr.length - 1];
		String addresslastInput = addresslast.substring(0, addresslast.length() - 1);
		mv.addObject("district", district);
		mv.addObject("city", city);
		mv.addObject("addresshead", addresshead);
		mv.addObject("addresslast", addresslast);
		mv.addObject("addresslastInput", addresslastInput);
		mv.addObject("branch", branch);
		mv.addObject("location", location);
		mv.addObject("branchRankList", branchRankList);
		mv.addObject("branchThemList", themeList);
		mv.addObject("districtList", districtList);
		mv.addObject("newCity", JSONUtil.fromObject(newCity));
		mv.addObject("property",property);
		mv.addObject("streetList", streetList);
		mv.addObject("cityAdminName", cityAdminName);
		mv.addObject("cityAdminCode", cityAdminCode);
		return mv;
	}
	
	@RequestMapping("/addBranchPic.do")
	@SuppressWarnings("unchecked")
	public ModelAndView addBranchPicPage(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView("page/basic/branch/addBranchPicNew");
		
		List<Picture> pictures = new ArrayList<Picture>();
		Picture picture = null;
		
		String branchId = request.getParameter("branchId");
		String scence = request.getParameter("scence");
		String picTrueStyle = null;
		BranchPicture tPicture = (BranchPicture) branchBasicService.findOneByProperties(BranchPicture.class, "style", "tt", "branchId", branchId);
		if(tPicture != null){
			picture = (Picture) branchBasicService.findOneByProperties(Picture.class, "pictureId", tPicture.getPictureId());
		}
		
		List<?> branchPictures = branchBasicService.findBySQL("queryPicBranch",new String[]{CommonConstants.PicStyle.BRANCH_HEAD_PIC, branchId},true);
		List<?> picBedroom = branchBasicService.findBySQL("queryPicBranch",new String[]{CommonConstants.PicStyle.BRANCH_BEDROOM_PIC, branchId},true);
		List<?> picLivingroom = branchBasicService.findBySQL("queryPicBranch",new String[]{CommonConstants.PicStyle.BRANCH_LIVEROOM_PIC, branchId},true);
		List<?> reclining = branchBasicService.findBySQL("queryPicBranch",new String[]{CommonConstants.PicStyle.BRANCH_RECLINING_PIC, branchId},true);
		List<?> pickitchen = branchBasicService.findBySQL("queryPicBranch",new String[]{CommonConstants.PicStyle.BRANCH_KITCHEN_PIC, branchId},true);
		List<?> pictoilet = branchBasicService.findBySQL("queryPicBranch",new String[]{CommonConstants.PicStyle.BRANCH_TOILET_PIC, branchId},true);

//		List<BranchPicture> branchPictures = branchBasicService.findByPropertiesWithSort(BranchPicture.class, "pictureId", "asc", "style", "dt", "branchId", branchId);
	/*	if(branchPictures.size() > 0){
			for(BranchPicture branchPicture : branchPictures){
				Picture dpicture = (Picture) branchBasicService.findOneByProperties(Picture.class, "pictureId", branchPicture.getPictureId());
				pictures.add(dpicture);
			}
		}*/
		
		mv.addObject("branchId", branchId);
		mv.addObject("picture", picture);
		mv.addObject("pictures", branchPictures);
		mv.addObject("picBedroom", picBedroom);
		mv.addObject("picLivingroom", picLivingroom);
		mv.addObject("reclining", reclining);
		mv.addObject("pickitchen", pickitchen);
		mv.addObject("pictoilet", pictoilet);
		mv.addObject("scence", scence);
		return mv;
	}
	@RequestMapping("/submitBranchPicsTwo.do")
	@SuppressWarnings("unchecked")
	public void submitBranchPicsTwo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String fileName = null;
		FileOutputStream fos = null;
		InputStream is = null;
		String headPicUrl = "";
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile srcFile = multipartRequest.getFile("file");
		
		String branchIdChoose = multipartRequest.getParameter("branchId");
		String index = multipartRequest.getParameter("index");
		//Branch branch = (Branch) manageService.findById(Branch.class, branchIdChoose);
		
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String staffId = loginuser.getStaff().getStaffId();
		
		List<BranchPicture> branchPics = branchBasicService.findByProperties(BranchPicture.class, "branchId",
				branchIdChoose, "style", CommonConstants.BranchPicStyle.BRANCH_INNEL_PIC, "status",
				CommonConstants.STATUS.NORMAL);
		if(branchPics.size() > 0 && index.equals("0")){
			for(BranchPicture branchPic : branchPics){
				String webPath = RequestUtil.getWebPath(request);
				String path = webPath.replace("\\","/") + "/upload/";
				Picture pic=(Picture) branchBasicService.findOneByProperties(Picture.class, "pictureId", branchPic.getPictureId());
				String name = pic.getPictureUrl().substring(pic.getPictureUrl().lastIndexOf("/") + 1);
				java.io.File myFilePath = new java.io.File(path + name);
			    myFilePath.delete();
			    branchBasicService.delete(branchPic);
			    branchBasicService.delete(pic);
			}
		}
		
//		if (!StringUtil.isEmpty(branch.getBranchIp())) {
//			SysParam paramFile = (SysParam) manageService.findOneByProperties(SysParam.class, "paramType",
//					SystemConstants.ParamType.BRANCH_IP, "paramName", "FILE");
//			int priorityFile = 1;
//			List<String> fileNameList = new ArrayList<String>();
//			fileNameList.add(fileName);
//			Map<String, Object> fileNameMap = new HashMap<String, Object>();
//			fileNameMap.put("FileName", fileNameList);
//			TransferServcie.getInstance().transferFile(priorityFile, paramFile.getContent(),
//					JSONUtil.fromObject(fileNameMap).toString());
//		}
		try {
			String webPath = RequestUtil.getWebPath(request);
			if (StringUtils.isEmpty(fileName)) {
				fileName = srcFile.getOriginalFilename();
				String filenameTime = String.valueOf(System.currentTimeMillis());
				synchronized (cnt) {
					if (++cnt > 9) {
						cnt = 1;
					}
					filenameTime += cnt;
				}
				fileName = filenameTime+fileName.substring(fileName.lastIndexOf("."));
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
		
		String sequence = branchBasicService.getCloudSequence("SEQ_PIC_ID");
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		String picId = sdf.format(new Date()) + sequence;
		String picStyle = CommonConstants.BranchPicStyle.BRANCH_INNEL_PIC;
		String picTrueStyle = CommonConstants.PicStyle.BRANCH_INNEL_PIC;
		String status = CommonConstants.STATUS.NORMAL;
		BranchPicture bp = new BranchPicture();
		bp.setBranchId(branchIdChoose);
		bp.setPictureId(picId);
		bp.setRecordTime(new Date());
		bp.setRecordUser(staffId);
		bp.setStatus(status);
		bp.setStyle(picStyle);
		branchBasicService.save(bp);
		
		Picture picture = new Picture();
		picture.setPictureId(picId);
		picture.setPictureStyle(picTrueStyle);
		picture.setRecordTime(new Date());
		picture.setRecordUser(staffId);
		picture.setStatus(status);
		picture.setPictureUrl(headPicUrl);
		branchBasicService.save(picture);

		JSONUtil.responseJSON(response, new AjaxResult(1, null));

	}
	@RequestMapping("/delPicsTwo.do")
	public void delPicsTwo (HttpServletRequest request, HttpServletResponse response, String pictureId){
		BranchPicture branchPic = (BranchPicture) branchBasicService.findById(BranchPicture.class, pictureId);
		if (branchPic != null) {
			String picId=branchPic.getPictureId();
			Picture pic=(Picture) branchBasicService.findOneByProperties(Picture.class, "pictureId", picId);
			if(pic != null){
				String webPath = RequestUtil.getWebPath(request);
				String path = webPath.replace("\\","/") + "/upload/";
				String name = pic.getPictureUrl().substring(pic.getPictureUrl().lastIndexOf("/") + 1);
				java.io.File myFilePath = new java.io.File(path + name);
			    myFilePath.delete();
			    branchBasicService.delete(pic);
			}
			branchBasicService.delete(branchPic);
		}
	}
	
	@SuppressWarnings("unused")
	@RequestMapping("/addBranchHeadPicDataTwo.do")
	public void addBranchHeadPicDataTwo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String fileName = null;
		FileOutputStream fos = null;
		InputStream is = null;
		String headPicUrl = "";
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile srcFile = multipartRequest.getFile("file");
		
		String branchIdChoose = multipartRequest.getParameter("branchId");
		Branch branch = (Branch) branchBasicService.findById(Branch.class, branchIdChoose);
		
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String staffId = loginuser.getStaff().getStaffId();
		
		BranchPicture branchPic = (BranchPicture) branchBasicService.findOneByProperties(BranchPicture.class, "branchId",
				branchIdChoose, "style", CommonConstants.BranchPicStyle.BRANCH_HEAD_PIC, "status", CommonConstants.STATUS.NORMAL);
		if (branchPic != null) {
			branchBasicService.delete(branchPic);
			String picId=branchPic.getPictureId();
			Picture pic=(Picture) branchBasicService.findOneByProperties(Picture.class, "pictureId", picId);
			if(pic != null){
				String webPath = RequestUtil.getWebPath(request);
				String path = webPath.replace("\\","/") + "/upload/";
				String name = pic.getPictureUrl().substring(pic.getPictureUrl().lastIndexOf("/") + 1);
				java.io.File myFilePath = new java.io.File(path + name);
			    myFilePath.delete();
			    branchBasicService.delete(pic);
			}
		}
//		if (!StringUtil.isEmpty(branch.getBranchIp())) {
//			SysParam paramFile = (SysParam) manageService.findOneByProperties(SysParam.class, "paramType",
//					SystemConstants.ParamType.BRANCH_IP, "paramName", "FILE");
//			int priorityFile = 1;
//			List<String> fileNameList = new ArrayList<String>();
//			fileNameList.add(fileName);
//			Map<String, Object> fileNameMap = new HashMap<String, Object>();
//			fileNameMap.put("FileName", fileNameList);
//			TransferServcie.getInstance().transferFile(priorityFile, paramFile.getContent(),
//					JSONUtil.fromObject(fileNameMap).toString());
//		}
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
		
		// 门店头图数据新增
		String sequence = branchBasicService.getCloudSequence("SEQ_PIC_ID");
		SimpleDateFormat sdf = new SimpleDateFormat("yymmdd");
		String picId = sdf.format(new Date()) + sequence;
		String picStyle = CommonConstants.BranchPicStyle.BRANCH_HEAD_PIC;
		String picTrueStyle = CommonConstants.PicStyle.BRANCH_HEAD_PIC;
		String status = CommonConstants.STATUS.NORMAL;
		BranchPicture bp = new BranchPicture();
		bp.setBranchId(branchIdChoose);
		bp.setPictureId(picId);
		bp.setRecordTime(new Date());
		bp.setRecordUser(staffId);
		bp.setStatus(status);
		bp.setStyle(picStyle);
		branchBasicService.save(bp);
		
		Picture picture = new Picture();
		picture.setPictureId(picId);
		picture.setPictureStyle(picTrueStyle);
		picture.setRecordTime(new Date());
		picture.setRecordUser(staffId);
		picture.setStatus(status);
		picture.setPictureUrl(headPicUrl);
		branchBasicService.save(picture);
		
		JSONUtil.responseJSON(response, new AjaxResult(1, null));
	}
	// 酒店新增
	@SuppressWarnings("unused")
	@RequestMapping("/saveBranch.do")
	@ResponseBody
	public String saveBranch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String staffBranchRank = "0";
		String branchId = null;
		SysParam param = (SysParam) branchBasicService.findOneByProperties(SysParam.class, "paramType",
				SystemConstants.ParamType.BRANCH_IP, "paramName", "1");
		int priority = 1;
		SysParam sysParam = (SysParam) branchBasicService.findOneByProperties(SysParam.class, "paramType", "SYSTEMTYPE");
		String systemType = sysParam.getContent();
		String branchIdChoose = request.getParameter("branchId");
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String staffId = loginuser.getStaff().getStaffId();
		String staffBranchId = loginuser.getStaff().getBranchId();
		Branch staffBranch = (Branch)branchBasicService.findOneByProperties(Branch.class, "branchId", staffBranchId);
		if(staffBranch != null){
			staffBranchRank = staffBranch.getRank();
		}
		if(staffBranchRank.equals("1")){
			return String.valueOf(SystemConstants.PortalResultCode.NULL);
		}
		String branchName = request.getParameter("branchName");
		String branchType = request.getParameter("branchType");
		String branchRank = request.getParameter("branchRank");
		String homePhone = request.getParameter("homePhone");
		String postCode = request.getParameter("postCode");
		String connectPerson = request.getParameter("connectPerson");
		String phone = request.getParameter("phone");
		String preLocation = request.getParameter("preLocation");
		/*String aftLocation = request.getParameter("aftLocation");*/
		String  cityCode  = request.getParameter("cityCode");
		String district  = request.getParameter("district");
		String street  = request.getParameter("street");
		String property  = request.getParameter("property");
		String subway  = request.getParameter("subway");
		//String circle = request.getParameter("circle");
		String remarkInput = request.getParameter("remarkInput");
		String latlng = request.getParameter("latlng");
		String longitude = request.getParameter("longitude");
		String latitude = request.getParameter("latitude");
		//String ip = request.getParameter("ip");
		String recommend = request.getParameter("recommend");
		//String branchURL = "http://"+ip+":8080/ipms";
		String flag = request.getParameter("flag");
		String keyword = request.getParameter("keyword");
		String address = preLocation ;/*+ "-" + aftLocation + "号";*/
		
		// 如果是总店，则更新客服电话
		if(branchRank.equals("0")){
			branchId = branchBasicService.getSequence("SEQ_NEW_BRANCHID");
			SysParam sysparam = (SysParam)branchBasicService.findOneByProperties(SysParam.class, "paramType", "SERVICETEL");
			sysparam.setContent(homePhone);
			sysparam.setParamName(branchId);
			branchBasicService.update(sysparam);
		}else{ // 如果是分店则新增客服电话
			branchId = branchBasicService.getCloudSequence("SEQ_NEW_BRANCHID");
			SysParam sp = new SysParam();
			String paramId = this.branchBasicService.getCloudOrLocalSequence("SEQ_SYSPARAM_ID");
			sp.setParamId(paramId);
			sp.setParamType("SERVICETEL");
			sp.setParamName(branchId);
			sp.setContent(homePhone);
			sp.setParamDesc("客服电话");
			sp.setOrderNo("1");
			sp.setStatus("1");
			branchBasicService.getHibernateTemplate().saveOrUpdate(sp);
		}
		
		
		//如果添加的是公寓，要加sysparam 表 预插入默认保洁次数 为2
		
		/*if("2".equals(branchType)) {
			String sys_id  = branchBasicService.getSequence("SEQ_SYSPARAM_ID");
			SysParam sys = new SysParam(sys_id, "CLEANAMOUNT", "*", branchId, "2",
					"", "1","","");
			branchBasicService.save(sys);
		}*/
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		String dateString = sdf.format(new Date());
		BranchKeywords bky = new BranchKeywords();
		bky.setBranchId(branchId);
		bky.setKeywords(keyword);
		bky.setKeywordsId(dateString+branchId+branchBasicService.getSequence("SEQ_BRANCHKEYWORDS_ID"));
		bky.setRecordTime(new Date());
		bky.setRecordUser(staffId);
		bky.setRemark("");
		bky.setStatus(CommonConstants.STATUS.NORMAL);
		branchBasicService.save(bky);
		
		// 当门店类型为酒店时添加班次
		if(branchType.equals("1")){
			//当前门店的班次的录入
			Boolean isRightShift = branchBasicService.queryShiftTimeInDB(branchId);
			if(!isRightShift){
				List<ShiftTime> errorShiftTime = branchBasicService.errorShiftTimeInDB(branchId);
				if(errorShiftTime != null && errorShiftTime.size() > 0){
					for(int i = 0; i < errorShiftTime.size(); i++){
						branchBasicService.delete(errorShiftTime.get(i));
					}
				}
				List<ShiftTime> st = new ArrayList();
				Date date = new Date();
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
				String time = sdf1.format(date);
				String sequences1 = branchBasicService.getCloudOrLocalSequence("SEQ_SYSPARAM_ORDERNO_ID");
				String orderno1 = branchBasicService.getCloudOrLocalSequence("SEQ_SYSPARAM_ORDERNO");
				String sequences2 = branchBasicService.getCloudOrLocalSequence("SEQ_SYSPARAM_ORDERNO_ID");
				String orderno2 = branchBasicService.getCloudOrLocalSequence("SEQ_SYSPARAM_ORDERNO");
				String sequences3 = branchBasicService.getCloudOrLocalSequence("SEQ_SYSPARAM_ORDERNO_ID");
				String orderno3 = branchBasicService.getCloudOrLocalSequence("SEQ_SYSPARAM_ORDERNO");
				st.add(new ShiftTime(time+branchId+sequences1, branchId, "08:00:00","14:00:00",orderno1, "早班", CommonConstants.STATUS.NORMAL, null, null));
				st.add(new ShiftTime(time+branchId+sequences2, branchId, "14:00:00","23:00:00",orderno2, "中班", CommonConstants.STATUS.NORMAL, null, null));
				st.add(new ShiftTime(time+branchId+sequences3, branchId, "23:00:00","08:00:00",orderno3, "晚班", CommonConstants.STATUS.NORMAL, null, null));
				branchBasicService.saveOrUpdateAll(st);
			}
		}
		// 查询维修申请审核参宿是否有值,如果没有则想参宿表中添加参宿
		SysParam RepairAudit = this.branchBasicService.queryRrecordsInSysParam("paramType","AUDITRepair","parameter1",branchId);
		if(RepairAudit == null){
			SysParam sp = new SysParam();
			String paramId = this.branchBasicService.getCloudOrLocalSequence("SEQ_SYSPARAM_ID");
			sp.setParamId(paramId);
			sp.setParamType("AUDITRepair");
			sp.setParamName("0008");
			sp.setParamDesc("*");
			sp.setContent("*");
			sp.setStatus("1");
			sp.setRemark("*");
			sp.setParameter1(branchId);
			branchBasicService.getHibernateTemplate().saveOrUpdate(sp);
		}else{
			RepairAudit.setParamName("0008");
			branchBasicService.getHibernateTemplate().saveOrUpdate(RepairAudit);
		}
		// 查询退租申请参宿是否有值,如果没有则想参宿表中添加参宿
		SysParam CheckOutAUDIT = this.branchBasicService.queryRrecordsInSysParam("paramType","AUDITCheckOut","parameter1",branchId);
		if(CheckOutAUDIT == null){
			SysParam sp = new SysParam();
			String paramId = this.branchBasicService.getCloudOrLocalSequence("SEQ_SYSPARAM_ID");
			sp.setParamId(paramId);
			sp.setParamType("AUDITCheckOut");
			sp.setParamName("0008");
			sp.setParamDesc("*");
			sp.setContent("*");
			sp.setStatus("1");
			sp.setRemark("*");
			sp.setParameter1(branchId);
			branchBasicService.getHibernateTemplate().saveOrUpdate(sp);
		}else{
			CheckOutAUDIT.setParamName("0008");
			branchBasicService.getHibernateTemplate().saveOrUpdate(CheckOutAUDIT);
		}
		// 判断门店类型，如果是酒店则添加钱柜，如果是公寓则添加保洁次数及费用
		if(branchType.equals("1")){
			//保存当前酒店的账户
			CashBox cashBox = new CashBox();
			cashBox.setDataId(branchBasicService.getCloudOrLocalSequence("SEQ_NEW_CASHBOX"));
			cashBox.setBranchId(branchId);
			cashBox.setCashCount(1000.00);
			cashBox.setRecordTime(new Date());
			cashBox.setRecordUser(staffId);
			cashBox.setStatus("1");
			branchBasicService.getHibernateTemplate().saveOrUpdate(cashBox);
		}else{
			// 保存系统参数
			// 保存当前公寓的每日清洁的数量 
			SysParam sysParam1 = new SysParam();
			sysParam1.setParamId(this.branchBasicService.getCloudOrLocalSequence("SEQ_SYSPARAM_ID"));
			sysParam1.setParamDesc(branchId);
			sysParam1.setParamType("CLEANAMOUNT");
			sysParam1.setParamName("*");
			sysParam1.setStatus("1");
			sysParam1.setContent("2");
			sysParam1.setParameter1("预置保洁次数");
			branchBasicService.getHibernateTemplate().saveOrUpdate(sysParam1);
			// 保存当前公寓的保洁类型
			GoodsCategory gc = new GoodsCategory();
			gc.setBranchId(branchId);
			String categoryId = branchBasicService.getCloudOrLocalSequence("SEQ_NEW_GOODSCATEGORY");
			gc.setCategoryId(categoryId);
			gc.setCategoryName("保洁");
			gc.setChargeRoom("1");
			gc.setRecordTime(new Date());
			gc.setRecordUser(staffId);
			gc.setStatus("1");
			gc.setSuperCategory("2");
			branchBasicService.getHibernateTemplate().saveOrUpdate(gc);
			// 保存当前公寓保洁商品
			Goods gd = new Goods();
			gd.setGoodsId(branchBasicService.getCloudOrLocalSequence("SEQ_NEW_GOODS"));
			gd.setCategoryId(categoryId);
			gd.setPrice(100.00);
			gd.setBranchId(branchId);
			gd.setGoodsName("保洁费");
			gd.setProductionDate(new Date());
			gd.setRecordTime(new Date());
			gd.setRecordUser(staffId);
			gd.setSaleType("1");
			gd.setSpecifications("次");
			gd.setStatus("1");
			gd.setSupplierId("10000001");
			gd.setUnit("次");
			branchBasicService.getHibernateTemplate().saveOrUpdate(gd);
			// 调用保存公寓保洁次数方法
			//生成保洁预排表
			// cleanTableUp(request, response);
			
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyMMdd");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");
			String todayTime = sdf2.format(new Date());
			List<?> judge = branchBasicService.findByProperties(Clean.class, "recordTime", sdf2.parse(todayTime));
			SysParam cleanCount = (SysParam)branchBasicService.findOneByProperties(SysParam.class, "paramDesc", branchId, "paramType","CLEANAMOUNT");
			
			if(cleanCount != null){
				Clean clean=null;
				String timeArea = null;
				String sequences = null;
				List<Clean> cleanList = new ArrayList<Clean>();
				List<?> restAmount = branchBasicService.getDefaultParam(branchId);
				Calendar todayCal = Calendar.getInstance();
				todayCal.set(Calendar.DATE, 1);//本月1号
				Date currentFirst = todayCal.getTime();//本月1号
				todayCal.roll(Calendar.DATE, -1);// 本月最后一天
				int dayOFMonth = todayCal.get(Calendar.DATE);//当月天数
				if (judge == null || judge.size() <= 0) {//数据库里没数据，要把本月的数据也插进去
					Boolean flag2 = true;
					for (int m = 0; m < dayOFMonth; m++) {
						todayCal.setTime(currentFirst);
						todayCal.add(Calendar.DAY_OF_MONTH, m);
						Date nextDate2 = todayCal.getTime();
						String newstrdate = sdf1.format(nextDate2);
						String newstrdate2 = sdf2.format(nextDate2);
						Date cleandate2 = sdf2.parse(newstrdate2);
						for (int n = 0; n < 2; n++) {
							clean = new Clean();
							sequences = this.branchBasicService.getCloudOrLocalSequence("SEQ_CLEAN_ID");
							clean.setCleanId(newstrdate + branchId + sequences);
							clean.setBranchId(branchId);
							clean.setCleanDate(cleandate2);
							//clean.setRoomId("");
							//clean.setCleanPerson("");
							//clean.setRecordUser("");
							clean.setRecordTime(new Date());
							//clean.setCleanApplyId("");
							if (flag2) {
								timeArea = "0";
								flag2 = false;
							} else {
								timeArea = "1";
								flag2 = true;
							}
							clean.setTimeArea(timeArea);
							clean.setRestAmount(Integer.parseInt(((Map<?, ?>) restAmount.get(0)).get("CONTENT").toString()));
							cleanList.add(clean);
							branchBasicService.save(clean);
						}
					}
				
				}
				
			}
		}
		
		/*if(CommonConstants.SystemType.Branch.equals(systemType.trim())){
			List<BranchKeywords> keywordsList = new ArrayList<BranchKeywords>();
			keywordsList.add(bky);
			Map<String, Object> keywordsMap = new HashMap<String, Object>();
			keywordsMap.put("BranchKeywords", keywordsList);
			TransferServcie.getInstance().transferData(priority, param.getContent(),
					JSONUtil.fromObject(keywordsMap).toString());
		}
		if(CommonConstants.SystemType.Cloud.equals(systemType.trim())){
			List<BranchKeywords> keywordsList = new ArrayList<BranchKeywords>();
			keywordsList.add(bky);
			Map<String, Object> keywordsMap = new HashMap<String, Object>();
			keywordsMap.put("BranchKeywords", keywordsList);
			TransferServcie.getInstance().transferData(priority, branchURL,
					JSONUtil.fromObject(keywordsMap).toString());
		}*/
		

		String lat[] = latlng.split(",");
		if(longitude.equals("")||latitude.equals("")){
			longitude = lat[1];
			latitude = lat[0];
		}
		Location loc = new Location();
		loc.setBranchId(branchId);
		loc.setLatitude(latitude);
		loc.setLongitude(longitude);
		loc.setRecordTime(new Date());
		loc.setRecordUser(staffId);
		loc.setStatus(CommonConstants.STATUS.NORMAL);
		branchBasicService.save(loc);
		
		/*if(CommonConstants.SystemType.Branch.equals(systemType.trim())){
			List<Location> locationList = new ArrayList<Location>();
			locationList.add(loc);
			Map<String, Object> locationMap = new HashMap<String, Object>();
			locationMap.put("Location", locationList);
			TransferServcie.getInstance().transferData(priority, param.getContent(),
					JSONUtil.fromObject(locationMap).toString());
		}
		if(CommonConstants.SystemType.Cloud.equals(systemType.trim())){
			List<Location> locationList = new ArrayList<Location>();
			locationList.add(loc);
			Map<String, Object> locationMap = new HashMap<String, Object>();
			locationMap.put("Location", locationList);
			TransferServcie.getInstance().transferData(priority, branchURL,
					JSONUtil.fromObject(locationMap).toString());
		}*/

		Branch branch = new Branch();
		branch.setAddress(address);
		branch.setBranchId(branchId);
		branch.setBranchName(branchName);
		branch.setBranchType(branchType);
		branch.setCity(cityCode);
		branch.setContacts(connectPerson);
		branch.setDistrict(district);
		if(CommonConstants.CityRank.STREET.equals(property)){
			branch.setStreet(street);
		}else if(CommonConstants.CityRank.CIRCLE.equals(property)){
			branch.setCircle(street);
		}
		branch.setMobile(phone);
		branch.setPhone(homePhone);
		branch.setPostcode(postCode);
		branch.setRank(branchRank);
		branch.setRecordTime(new Date());
		branch.setRecordUser(staffId);
		branch.setRemark(remarkInput);
		branch.setStatus(CommonConstants.STATUS.NORMAL);
		branch.setFlag(flag);
		//branch.setBranchIp(ip);
		branch.setIsRecommend(recommend);
		branchBasicService.save(branch);
		
		/*if(CommonConstants.SystemType.Branch.equals(systemType.trim())){
			// priority = Integer.parseInt(member.getMemberRank());
			List<Branch> branchList = new ArrayList<Branch>();
			branchList.add(branch);
			Map<String, Object> branchMap = new HashMap<String, Object>();
			branchMap.put("Branch", branchList);
			TransferServcie.getInstance().transferData(priority, param.getContent(),
					JSONUtil.fromObject(branchMap).toString());
		}
/*		if(CommonConstants.SystemType.Cloud.equals(systemType.trim())){
			List<Branch> branchList = new ArrayList<Branch>();
			branchList.add(branch);
			Map<String, Object> branchMap = new HashMap<String, Object>();
			branchMap.put("Branch", branchList);
			TransferServcie.getInstance().transferData(priority, branchURL,
					JSONUtil.fromObject(branchMap).toString());
		}*/
		
		return String.valueOf(SystemConstants.PortalResultCode.DONE);

	}
	
	// 自定义房间管理新增
	@RequestMapping("/adddataRoom.do")
	public void adddataRoom(HttpServletRequest request, HttpServletResponse response, String theme, String branchid,
			String rproomtype, String floor, String roomid, String area, String status, String remark) throws Exception {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String branchId = loginuser.getStaff().getBranchId();
		String recorduser = staff.getStaffId();
		String recorduserName = staff.getStaffName();
		List<?> uniqueroom = branchBasicService.getUniqueroom(branchid, roomid);
		SysParam sysroomStatus = ((SysParam) (branchBasicService.findOneByProperties(SysParam.class, "paramType",
				"ROOMSTATUS", "content", status, "status", "1")));
		String statusname = sysroomStatus.getParamName().toString();
		String a = null;
		if (null == uniqueroom || uniqueroom.size() == 0) {
			RoomKey roomKey = new RoomKey();
			roomKey.setBranchId(branchid);
			roomKey.setRoomId(roomid);
			Short areadata = null;
			if (area.equals("")) {
			} else {
				areadata = new Short(area);
			}
			List<?> unusedata = branchBasicService.getUnusedata(branchid, roomid);
			if (unusedata.size() > 0) {
				Room roomupdate = ((Room) (branchBasicService.findOneByProperties(Room.class, "roomKey.branchId",branchid,"roomKey.roomId",roomid)));
				roomupdate.setArea(areadata);
				roomupdate.setFloor(floor);
				roomupdate.setRecordUser(staff.getStaffId());
				roomupdate.setRemark(remark);
				roomupdate.setRoomType(rproomtype);
				roomupdate.setStatus(status);
				roomupdate.setTheme(theme);
				roomupdate.setRecordTime(new Date());
				branchBasicService.update(roomupdate);
				/*SysParam systemtype = (SysParam) PmsmanageService.findOneByProperties(SysParam.class, "paramType","SYSTEMTYPE", "status", "1");
				String stype = systemtype.getContent().toString();
				if(stype.equals(CommonConstants.SystemType.Branch)){
				int priority = 1;
				SysParam param = (SysParam) PmsmanageService.findOneByProperties(SysParam.class, "paramType","REMOTE_PATH", "status", "1");
				List<Room> roomupList = new ArrayList<Room>();
				roomupList.add(roomupdate);
				Map<String, Object> roomupMap = new HashMap <String, Object>();
				roomupMap.put("Room", roomupList);
				TransferServcie.getInstance().transferData(priority, param.getContent(),JSONUtil.fromObject(roomupMap).toString());
				}*/
				
			}else{
			Room room = new Room();
			room.setArea(areadata);
			room.setFloor(floor);
			room.setRecordUser(staff.getStaffId());
			room.setRemark(remark);
			room.setRoomKey(roomKey);
			room.setRoomType(rproomtype);
			room.setTheme(theme);
			room.setStatus(status);
			branchBasicService.save(room);
			/*int priority = 1;
			SysParam param = (SysParam) PmsmanageService.findOneByProperties(SysParam.class, "paramType","REMOTE_PATH", "status", "1");
			List<Room> roomList = new ArrayList<Room>();
			roomList.add(room);
			Map<String, Object> roomMap = new HashMap<String, Object>();
			roomMap.put("Room", roomList);
			TransferServcie.getInstance().transferData(priority, param.getContent(),JSONUtil.fromObject(roomMap).toString());*/
			}
			if (status.equals(CommonConstants.RoomStatus.RoomStop)) {
				List<?> exitroomstatus = branchBasicService.getExitroomstatus(branchid, rproomtype);
				if (null == exitroomstatus || exitroomstatus.size() == 0) {
					String logId = this.branchBasicService.getSequence("SEQ_NEW_ROOMSTATUS", null);
					Date day = new Date();
					SimpleDateFormat df = new SimpleDateFormat("yyMMdd");
					String t = df.format(day);
					String datId = t + branchId + logId;
					RoomStatus roomstatus = new RoomStatus();
					roomstatus.setLogId(datId);
					roomstatus.setBranchId(branchid);
					roomstatus.setRoomType(rproomtype);
					roomstatus.setCount(1);
					roomstatus.setRecordUser(recorduser);
					roomstatus.setSellnum(0);
					roomstatus.setStopnum(1);
					roomstatus.setNightnum(0);
					roomstatus.setValidnum(0);
					roomstatus.setInvalidnum(0);
					roomstatus.setCampaigns(0);
					branchBasicService.save(roomstatus);
					OperateLog operlog = new OperateLog();
					operlog.setBranchId(branchid);
					operlog.setLogId(DateUtil.currentDate("yyMMdd") + branchid+ branchBasicService.getSequence("SEQ_OPERATELOG_ID"));
					String operid = InetAddress.getLocalHost().toString();// IP地址
					operid = (String) operid.subSequence(operid.indexOf("/") + 1, operid.length());
					operlog.setOperIp(operid);
					operlog.setOperType(SystemConstants.OperType.roomStatus);
					operlog.setOperModule("房态操作");
					operlog.setRecordUser(recorduser);
					operlog.setContent(recorduserName + "新增房型为[" + rproomtype + "]状态为[" + statusname + "]房号为["+roomid+"]的房间");
					branchBasicService.save(operlog);
				} else {
					RoomStatus roomStatus = ((RoomStatus) (branchBasicService.findOneByProperties(RoomStatus.class,
							"branchId", branchid, "roomType", rproomtype)));
					String oldcount = roomStatus.getCount().toString();
					String oldstopnum = roomStatus.getStopnum().toString();
					int x = Integer.parseInt(oldcount) + 1;
					int v = Integer.parseInt(oldstopnum) + 1;
					Integer newcount = Integer.valueOf(x);
					Integer newstopnum = Integer.valueOf(v);
					roomStatus.setCount(newcount);
					roomStatus.setStopnum(newstopnum);
					roomStatus.setRecordUser(staff.getStaffId());
					roomStatus.setRecordTime(new Date());
					branchBasicService.save(roomStatus);
					OperateLog operlog = new OperateLog();
					operlog.setBranchId(branchid);
					operlog.setLogId(DateUtil.currentDate("yyMMdd") + branchid
							+ branchBasicService.getSequence("SEQ_OPERATELOG_ID"));
					String operid = InetAddress.getLocalHost().toString();// IP地址
					operid = (String) operid.subSequence(operid.indexOf("/") + 1, operid.length());
					operlog.setOperIp(operid);
					operlog.setOperType(SystemConstants.OperType.roomStatus);
					operlog.setOperModule("房态操作");
					operlog.setRecordUser(recorduser);
					operlog.setContent(recorduserName + "新增房型为[" + rproomtype + "]状态为[" + statusname + "]房号为["+roomid+"]的房间");
					branchBasicService.save(operlog);
				}

			} else if (status.equals(CommonConstants.RoomStatus.RoomUnuse)) {
				

			} else if ((status.equals(CommonConstants.RoomStatus.RoomExample))
					|| (status.equals(CommonConstants.RoomStatus.RoomStaff))|| (status.equals(CommonConstants.RoomStatus.RoomRepair))) {
				List<?> exitroomstatus = branchBasicService.getExitroomstatus(branchid, rproomtype);
				if (null == exitroomstatus || exitroomstatus.size() == 0) {
					String logId = this.branchBasicService.getSequence("SEQ_NEW_ROOMSTATUS", null);
					Date day = new Date();
					SimpleDateFormat df = new SimpleDateFormat("yyMMdd");
					String t = df.format(day);
					String datId = t + branchId + logId;
					RoomStatus roomstatus = new RoomStatus();
					roomstatus.setLogId(datId);
					roomstatus.setBranchId(branchid);
					roomstatus.setRoomType(rproomtype);
					roomstatus.setCount(1);
					roomstatus.setRecordUser(staff.getStaffId());
					roomstatus.setSellnum(0);
					roomstatus.setStopnum(0);
					roomstatus.setNightnum(0);
					roomstatus.setValidnum(0);
					roomstatus.setInvalidnum(0);
					roomstatus.setCampaigns(0);
					branchBasicService.save(roomstatus);
					OperateLog operlog = new OperateLog();
					operlog.setBranchId(branchid);
					operlog.setLogId(DateUtil.currentDate("yyMMdd") + branchid
							+ branchBasicService.getSequence("SEQ_OPERATELOG_ID"));
					String operid = InetAddress.getLocalHost().toString();// IP地址
					operid = (String) operid.subSequence(operid.indexOf("/") + 1, operid.length());
					operlog.setOperIp(operid);
					operlog.setOperType(SystemConstants.OperType.roomStatus);
					operlog.setOperModule("房态操作");
					operlog.setRecordUser(recorduser);
					operlog.setContent(recorduserName + "新增房型为[" + rproomtype + "]状态为[" + statusname + "]房号为["+roomid+"]的房间");
					branchBasicService.save(operlog);
				} else {
					RoomStatus roomStatus = ((RoomStatus) (branchBasicService.findOneByProperties(RoomStatus.class,
							"branchId", branchid, "roomType", rproomtype)));
					String oldcount = roomStatus.getCount().toString();
					int x = Integer.parseInt(oldcount) + 1;
					Integer newcount = Integer.valueOf(x);
					roomStatus.setCount(newcount);
					roomStatus.setRecordUser(staff.getStaffId());
					roomStatus.setRecordTime(new Date());
					branchBasicService.save(roomStatus);
					OperateLog operlog = new OperateLog();
					operlog.setBranchId(branchid);
					operlog.setLogId(DateUtil.currentDate("yyMMdd") + branchid
							+ branchBasicService.getSequence("SEQ_OPERATELOG_ID"));
					String operid = InetAddress.getLocalHost().toString();// IP地址
					operid = (String) operid.subSequence(operid.indexOf("/") + 1, operid.length());
					operlog.setOperIp(operid);
					operlog.setOperType(SystemConstants.OperType.roomStatus);
					operlog.setOperModule("房态操作");
					operlog.setRecordUser(recorduser);
					operlog.setContent(recorduserName + "新增房型为[" + rproomtype + "]状态为[" + statusname + "]房号为["+roomid+"]的房间");
					branchBasicService.save(operlog);
				}
			} else {
				List<?> exitroomstatus = branchBasicService.getExitroomstatus(branchid, rproomtype);
				if (null == exitroomstatus || exitroomstatus.size() == 0) {
					String logId = this.branchBasicService.getSequence("SEQ_NEW_ROOMSTATUS", null);
					Date day = new Date();
					SimpleDateFormat df = new SimpleDateFormat("yyMMdd");
					String t = df.format(day);
					String datId = t + branchId + logId;
					RoomStatus roomstatus = new RoomStatus();
					roomstatus.setLogId(datId);
					roomstatus.setBranchId(branchid);
					roomstatus.setRoomType(rproomtype);
					roomstatus.setCount(1);
					roomstatus.setRecordUser(staff.getStaffId());
					roomstatus.setSellnum(1);
					roomstatus.setStopnum(0);
					roomstatus.setNightnum(0);
					roomstatus.setValidnum(0);
					roomstatus.setInvalidnum(0);
					roomstatus.setCampaigns(0);
					branchBasicService.save(roomstatus);
					OperateLog operlog = new OperateLog();
					operlog.setBranchId(branchid);
					operlog.setLogId(DateUtil.currentDate("yyMMdd") + branchid
							+ branchBasicService.getSequence("SEQ_OPERATELOG_ID"));
					String operid = InetAddress.getLocalHost().toString();// IP地址
					operid = (String) operid.subSequence(operid.indexOf("/") + 1, operid.length());
					operlog.setOperIp(operid);
					operlog.setOperType(SystemConstants.OperType.roomStatus);
					operlog.setOperModule("房态操作");
					operlog.setRecordUser(recorduser);
					operlog.setContent(recorduserName + "新增房型为[" + rproomtype + "]状态为[" + statusname + "]房号为["+roomid+"]的房间");
					branchBasicService.save(operlog);
				} else {
					RoomStatus roomStatus = ((RoomStatus) (branchBasicService.findOneByProperties(RoomStatus.class,
							"branchId", branchid, "roomType", rproomtype)));
					String oldcount = roomStatus.getCount().toString();
					String oldsellnum = roomStatus.getSellnum().toString();
					int x = Integer.parseInt(oldcount) + 1;
					int v = Integer.parseInt(oldsellnum) + 1;
					Integer newcount = Integer.valueOf(x);
					Integer newsellnum = Integer.valueOf(v);
					roomStatus.setCount(newcount);
					roomStatus.setSellnum(newsellnum);
					roomStatus.setRecordUser(staff.getStaffId());
					roomStatus.setRecordTime(new Date());
					branchBasicService.save(roomStatus);
					OperateLog operlog = new OperateLog();
					operlog.setBranchId(branchid);
					operlog.setLogId(DateUtil.currentDate("yyMMdd") + branchid
							+ branchBasicService.getSequence("SEQ_OPERATELOG_ID"));
					String operid = InetAddress.getLocalHost().toString();// IP地址
					operid = (String) operid.subSequence(operid.indexOf("/") + 1, operid.length());
					operlog.setOperIp(operid);
					operlog.setOperType(SystemConstants.OperType.roomStatus);
					operlog.setOperModule("房态操作");
					operlog.setRecordUser(recorduser);
					operlog.setContent(recorduserName + "新增房型为[" + rproomtype + "]状态为[" + statusname + "]房号为["+roomid+"]的房间");
					branchBasicService.save(operlog);
				}
			}
		} else {

			a = "该门店该房号的数据已存在，请重新添加";
		}

		JSONUtil.responseJSON(response, new AjaxResult(1, a));
	}
	
	
	// 自定义房间管理编辑页面跳转
	@RequestMapping("/roomEdit.do")
	public ModelAndView roomEdit(HttpServletRequest request, HttpServletResponse response, String theme, String branch,
			String roomtype, String floor, String roomid, String area, String status, String remark) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/page/basic/branch/roomedit");
		SysParam sysParam = ((SysParam) (branchBasicService.findOneByProperties(SysParam.class, "paramType", "THEME",
				"paramName", theme)));
		Branch branchName = ((Branch) (branchBasicService.findOneByProperties(Branch.class, "branchName", branch)));
		String themeid = sysParam.getContent().toString();
		String branchid = branchName.getBranchId().toString();
		List<?> reroomtype = branchBasicService.getReroomtype(roomtype, themeid, branchid);
		SysParam sysParamstatus = ((SysParam) (branchBasicService.findOneByProperties(SysParam.class, "paramType",
				"ROOMSTATUS", "paramName", status)));
		String statusedit = sysParamstatus.getContent().toString();
		String roomtypeid = ((Map<?, ?>) reroomtype.get(0)).get("ROOM_TYPE").toString();
		List<?> rpbranchid = branchBasicService.getRpbranchid();
		List<?> rptheme = branchBasicService.getRptheme();
		List<?> rproomtype = branchBasicService.getRproomtype(branchid);
		List<?> rpstatus = branchBasicService.getStatus();
		request.setAttribute("rpbranchid", rpbranchid);
		request.setAttribute("rptheme", rptheme);
		request.setAttribute("rproomtype", rproomtype);
		request.setAttribute("statusedit", statusedit);
		request.setAttribute("rpstatus", rpstatus);
		request.setAttribute("themeid", themeid);
		request.setAttribute("theme", theme);
		request.setAttribute("branch", branch);
		request.setAttribute("branchid", branchid);
		request.setAttribute("roomtypeid", roomtypeid);
		request.setAttribute("floor", floor);
		request.setAttribute("roomid", roomid);
		request.setAttribute("area", area);
		request.setAttribute("roomid", roomid);
		request.setAttribute("remark", remark);
		return mv;
	}
	
	// 自定义房间管理编辑
	@RequestMapping("/editdataRoom.do")
	public void editdataRoom(HttpServletRequest request, HttpServletResponse response, String theme, String branchid,
			String rproomtype,String oldroomtype, String floor, String roomid, String area, String status, String oldstatus, String remark)
			throws Exception {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String editUser = staff.getStaffId();
		Short areadata = null;
		if (area.equals("")) {
		} else {
			areadata = new Short(area);
		}
		Room roomedit = new Room();
		roomedit.setRoomType(rproomtype);
		roomedit.setArea(areadata);
		roomedit.setFloor(floor);
		roomedit.setRecordUser(editUser);
		roomedit.setRemark(remark);
		roomedit.setRoomKey(new RoomKey(branchid, roomid));
		roomedit.setStatus(status);
		roomedit.setTheme(theme);
		roomedit.setRecordTime(new Date());
		branchBasicService.update(roomedit); 
		/*SysParam systemtype = (SysParam) pmsmanageService.findOneByProperties(SysParam.class, "paramType","SYSTEMTYPE", "status", "1");
		String stype = systemtype.getContent().toString();
		if(stype.equals(CommonConstants.SystemType.Branch)){
		int priority = 1;
		SysParam param = (SysParam) PmsmanageService.findOneByProperties(SysParam.class, "paramType","REMOTE_PATH", "status", "1");
		List<Room> roomList = new ArrayList<Room>();
		roomList.add(roomedit);
		Map<String, Object> roomMap = new HashMap<String, Object>();
		roomMap.put("Room", roomList);
		TransferServcie.getInstance().transferData(priority, param.getContent(),JSONUtil.fromObject(roomMap).toString());
		}*/
		SysParam oldsysroomStatus = ((SysParam) (branchBasicService.findOneByProperties(SysParam.class, "paramType","ROOMSTATUS", "content", oldstatus, "status", "1")));
		String oldstatusname = oldsysroomStatus.getParamName().toString();
		if (oldstatus.equals(CommonConstants.RoomStatus.RoomStop)) {
			RoomStatus roomStatus = ((RoomStatus) (branchBasicService.findOneByProperties(RoomStatus.class, "branchId",branchid, "roomType", oldroomtype)));
			String oldstopnum = roomStatus.getStopnum().toString();
			if(Integer.parseInt(oldstopnum)>0){
			int v = Integer.parseInt(oldstopnum) - 1;
			Integer newstopnum = Integer.valueOf(v);
			roomStatus.setStopnum(newstopnum);
			}
			roomStatus.setRecordUser(staff.getStaffId());
			roomStatus.setRecordTime(new Date());
			if(rproomtype.equals(oldroomtype) == false){
				String oldcount = roomStatus.getCount().toString();
				if(Integer.parseInt(oldcount)>0){
				int x = Integer.parseInt(oldcount) - 1;
				Integer newcount = Integer.valueOf(x);
				roomStatus.setCount(newcount);
				}
			}
			branchBasicService.save(roomStatus);

		} else if((oldstatus.equals(CommonConstants.RoomStatus.RoomExample))
				|| (oldstatus.equals(CommonConstants.RoomStatus.RoomStaff))||(oldstatus.equals(CommonConstants.RoomStatus.RoomRepair))){
			   if(rproomtype.equals(oldroomtype) == false){
				RoomStatus roomStatus = ((RoomStatus) (branchBasicService.findOneByProperties(RoomStatus.class, "branchId",
						branchid, "roomType", oldroomtype)));
				roomStatus.setRecordUser(staff.getStaffId());
				String oldcount = roomStatus.getCount().toString();
				if(Integer.parseInt(oldcount)>0){
				int x = Integer.parseInt(oldcount) - 1;
				Integer newcount = Integer.valueOf(x);
				roomStatus.setCount(newcount);
				}
				roomStatus.setRecordTime(new Date());
				branchBasicService.save(roomStatus);	
			}
			
			
		}else if((oldstatus.equals(CommonConstants.RoomStatus.RoomUnuse))){
			
		}else {

			RoomStatus roomStatus = ((RoomStatus) (branchBasicService.findOneByProperties(RoomStatus.class, "branchId",
					branchid, "roomType", oldroomtype)));
			String oldsellnum = roomStatus.getSellnum().toString();
			if(Integer.parseInt(oldsellnum)>0){
			int v = Integer.parseInt(oldsellnum) - 1;
			Integer newsellnum = Integer.valueOf(v);
			roomStatus.setSellnum(newsellnum);
			}
			roomStatus.setRecordUser(staff.getStaffId());
			roomStatus.setRecordTime(new Date());
			if(rproomtype.equals(oldroomtype) == false){
				String oldcount = roomStatus.getCount().toString();
				if(Integer.parseInt(oldcount)>0){
				int x = Integer.parseInt(oldcount) - 1;
				Integer newcount = Integer.valueOf(x);
				roomStatus.setCount(newcount);
				}
			}
			branchBasicService.save(roomStatus);
		}
		SysParam sysroomStatus = ((SysParam) (branchBasicService.findOneByProperties(SysParam.class, "paramType",
				"ROOMSTATUS", "content", status, "status", "1")));
		String statusname = sysroomStatus.getParamName().toString();
		if (status.equals(CommonConstants.RoomStatus.RoomStop)) {

			RoomStatus roomStatus = ((RoomStatus) (branchBasicService.findOneByProperties(RoomStatus.class, "branchId",
					branchid, "roomType", rproomtype)));
			String oldstopnum = roomStatus.getStopnum().toString();
			int v = Integer.parseInt(oldstopnum) + 1;
			Integer newstopnum = Integer.valueOf(v);
			roomStatus.setStopnum(newstopnum);
			roomStatus.setRecordUser(staff.getStaffId());
			roomStatus.setRecordTime(new Date());
			if((rproomtype.equals(oldroomtype) == false)||(oldstatus.equals(CommonConstants.RoomStatus.RoomUnuse))){
				String oldcount = roomStatus.getCount().toString();
				int x = Integer.parseInt(oldcount) + 1;
				Integer newcount = Integer.valueOf(x);
				roomStatus.setCount(newcount);
				
			}
			branchBasicService.save(roomStatus);
			if ((status.equals(oldstatus) == false)||(rproomtype.equals(oldroomtype) == false)) {
				String message = null;
				 if((status.equals(oldstatus) == false)&&(rproomtype.equals(oldroomtype) == true)){
				   message =editUser + "将房间[" + roomid + "]的状态由[" + oldstatusname + "]改为" + statusname;
				 }else  if ((status.equals(oldstatus) == true)&&(rproomtype.equals(oldroomtype) == false)){
				   message =editUser + "将房间[" + roomid + "]的房型由[" + oldroomtype + "]改为" + rproomtype;
				 }else  if ((status.equals(oldstatus) == false)&&(rproomtype.equals(oldroomtype) == false)){
				   message =editUser + "将房间[" + roomid + "]的房型由[" + oldroomtype + "]改为" + rproomtype+",状态由[" + oldstatusname + "]改为" + statusname;
				 }
				OperateLog operlog = new OperateLog();
				operlog.setBranchId(branchid);
				operlog.setLogId(DateUtil.currentDate("yyMMdd") + branchid
						+ branchBasicService.getSequence("SEQ_OPERATELOG_ID"));
				String operid = InetAddress.getLocalHost().toString();// IP地址
				operid = (String) operid.subSequence(operid.indexOf("/") + 1, operid.length());
				operlog.setOperIp(operid);
				operlog.setOperType(SystemConstants.OperType.roomStatus);
				operlog.setOperModule("房态操作");
				operlog.setRecordUser(editUser);
				operlog.setContent(message);
				branchBasicService.save(operlog);
			

			}

		} else if (status.equals(CommonConstants.RoomStatus.RoomUnuse)) {
			if((rproomtype.equals(oldroomtype) == true)&&(oldstatus.equals((CommonConstants.RoomStatus.RoomUnuse))==false)){
				RoomStatus roomStatus = ((RoomStatus) (branchBasicService.findOneByProperties(RoomStatus.class, "branchId",
						branchid, "roomType", rproomtype)));
				roomStatus.setRecordUser(staff.getStaffId());
				String oldcount = roomStatus.getCount().toString();
				if(Integer.parseInt(oldcount)>0){
				int x = Integer.parseInt(oldcount) - 1;
				Integer newcount = Integer.valueOf(x);
				roomStatus.setCount(newcount);
				}
				roomStatus.setRecordTime(new Date());
				branchBasicService.save(roomStatus);	
			}

		} else if((status.equals(CommonConstants.RoomStatus.RoomExample))
				|| (status.equals(CommonConstants.RoomStatus.RoomStaff))|| (status.equals(CommonConstants.RoomStatus.RoomRepair))){
			if((rproomtype.equals(oldroomtype) == false)||(oldstatus.equals(CommonConstants.RoomStatus.RoomUnuse))){
				RoomStatus roomStatus = ((RoomStatus) (branchBasicService.findOneByProperties(RoomStatus.class, "branchId",
						branchid, "roomType", rproomtype)));
				roomStatus.setRecordUser(staff.getStaffId());
				String oldcount = roomStatus.getCount().toString();
				int x = Integer.parseInt(oldcount) + 1;
				Integer newcount = Integer.valueOf(x);
				roomStatus.setCount(newcount);
				roomStatus.setRecordTime(new Date());
				branchBasicService.save(roomStatus);	
			}
			
			
		}else {

			RoomStatus roomStatus = ((RoomStatus) (branchBasicService.findOneByProperties(RoomStatus.class, "branchId",
					branchid, "roomType", rproomtype)));
			
			String oldsellnum = roomStatus.getSellnum().toString();
			int v = Integer.parseInt(oldsellnum) + 1;
			Integer newsellnum = Integer.valueOf(v);
			roomStatus.setSellnum(newsellnum);
			roomStatus.setRecordUser(staff.getStaffId());
			roomStatus.setRecordTime(new Date());
			if((rproomtype.equals(oldroomtype) == false)||(oldstatus.equals(CommonConstants.RoomStatus.RoomUnuse))){
				String oldcount = roomStatus.getCount().toString();
				int x = Integer.parseInt(oldcount) + 1;
				Integer newcount = Integer.valueOf(x);
				roomStatus.setCount(newcount);
				
			}
			branchBasicService.save(roomStatus);
			if ((status.equals(oldstatus) == false)||(rproomtype.equals(oldroomtype) == false)) {
				String message = null;
				 if((status.equals(oldstatus) == false)&&(rproomtype.equals(oldroomtype) == true)){
				   message =editUser + "将房间[" + roomid + "]的状态由[" + oldstatusname + "]改为[" + statusname+"]";
				 }else  if ((status.equals(oldstatus) == true)&&(rproomtype.equals(oldroomtype) == false)){
				   message =editUser + "将房间[" + roomid + "]的房型由[" + oldroomtype + "]改为[" + rproomtype+"]";
				 }else  if ((status.equals(oldstatus) == false)&&(rproomtype.equals(oldroomtype) == false)){
				   message =editUser + "将房间[" + roomid + "]的房型由[" + oldroomtype + "]改为[" + rproomtype+"],状态由[" + oldstatusname + "]改为[" + statusname+"]";
				 }	 
				OperateLog operlog = new OperateLog();
				operlog.setBranchId(branchid);
				operlog.setLogId(DateUtil.currentDate("yyMMdd") + branchid
						+ branchBasicService.getSequence("SEQ_OPERATELOG_ID"));
				String operid = InetAddress.getLocalHost().toString();// IP地址
				operid = (String) operid.subSequence(operid.indexOf("/") + 1, operid.length());
				operlog.setOperIp(operid);
				operlog.setOperType(SystemConstants.OperType.roomStatus);
				operlog.setOperModule("房态操作");
				operlog.setRecordUser(editUser);
				operlog.setContent(message);
				branchBasicService.save(operlog);

			}

		}

		JSONUtil.responseJSON(response, new AjaxResult(1, null));
	}
	
	@RequestMapping("/rpBasicjudge.do")
	public void rpBasicjudge(HttpServletRequest request, HttpServletResponse response,String hourprice) throws Exception {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchid = loginuser.getStaff().getBranchId();
		Branch branch = (Branch) branchBasicService.findOneByProperties(Branch.class, "branchId", branchid,"status","1");
		String branchtype = branch.getBranchType().toString();
		String a = null;
		boolean isString = hourprice instanceof String;
		if(isString){
			if ((branchtype.equals(CommonConstants.Branch.HOTELID))==false) {
				a = "公寓无时租调整！";
			}
		}		
		/*SysParam systemtype = (SysParam) pmsmanageService.findOneByProperties(SysParam.class, "paramType", "SYSTEMTYPE",
				"status", "1");
		String stype = systemtype.getContent().toString();
		if (stype.equals(CommonConstants.SystemType.Cloud)) {
			a = "总店系统不可进行房价/时租申请操作！";
		}*/
		JSONUtil.responseJSON(response, new AjaxResult(1, a));
	}
	
	 @RequestMapping("/rpPageApply.do")
		public ModelAndView rpPageApply(HttpServletRequest request, HttpServletResponse response,String hourprice) throws Exception {
	ModelAndView mv = new ModelAndView();
	LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
	String branchid = loginUser.getStaff().getBranchId().toString();
	Branch branch = (Branch) branchBasicService.findOneByProperties(Branch.class, "branchId", branchid,"status","1");
	String themeid = branch.getBranchType().toString();
	String branchname = branch.getBranchName().toString();
	SysParam sysParam = ((SysParam) (branchBasicService.findOneByProperties(SysParam.class, "paramType", "THEME","content", themeid)));
	String themename = sysParam.getParamName().toString();
	String rpking = null;
	boolean isString = hourprice instanceof String;
	if(isString){
		rpking = hourprice;
	}else{
	if(themeid.equals(CommonConstants.Branch.HOTELID)){
		rpking = CommonConstants.RoomRpkind.hotelprice;
	}else if(themeid.equals(CommonConstants.Branch.APARTMENTID)){
		rpking = CommonConstants.RoomRpkind.departmentprice;
	}
	}
	SysParam sysParamkind = ((SysParam) (branchBasicService.findOneByProperties(SysParam.class, "paramType","RPKIND", "content", rpking,"status","1")));
	String rpkindname = sysParamkind.getParamName().toString();
	List<?> rpstatus = branchBasicService.getRpapplystatus();
	mv.setViewName("page/basic/branch/rppage");
	request.setAttribute("themename", themename);
	request.setAttribute("branchname", branchname);
	request.setAttribute("rpkindname", rpkindname);
	request.setAttribute("rpstatus", rpstatus);
	return mv;
}
	 
		// 房租价申请（第二版）
		@RequestMapping("/rpBasicApply.do")
		public ModelAndView rpBasicApply(HttpServletRequest request, HttpServletResponse response,String hourprice,String status) throws Exception {
			ModelAndView mv = new ModelAndView();
			LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
			String branchid = loginUser.getStaff().getBranchId().toString();
			Branch branch = (Branch) branchBasicService.findOneByProperties(Branch.class, "branchId", branchid,"status","1");
			String themeid = branch.getBranchType().toString();
			String branchname = branch.getBranchName().toString();
			List<?> rppack = branchBasicService.getRppack();
			List<?> rpstatus = branchBasicService.getRpapplystatus();
			List<?> rpsetup = branchBasicService.getRpsetup();
			SysParam sysParam = ((SysParam) (branchBasicService.findOneByProperties(SysParam.class, "paramType", "THEME","content", themeid)));
			String themename = sysParam.getParamName().toString();
			String rpking = null;
			boolean isString = hourprice instanceof String;
			if(isString){
				rpking = hourprice;
			}else{
			if(themeid.equals(CommonConstants.Branch.HOTELID)){
				rpking = CommonConstants.RoomRpkind.hotelprice;
			}else if(themeid.equals(CommonConstants.Branch.APARTMENTID)){
				rpking = CommonConstants.RoomRpkind.departmentprice;
			}
			}
			SysParam sysParamkind = ((SysParam) (branchBasicService.findOneByProperties(SysParam.class, "paramType","RPKIND", "content", rpking,"status","1")));
			String rpkindname = sysParamkind.getParamName().toString();
			boolean isStrings = status instanceof String;

			if((isStrings)==false){
				status = CommonConstants.RoomPriceStatus.basic;
			}
			String rpthemeid = themeid;
			List<?> rproomtype = null;
			rproomtype = branchBasicService.getRpapplyroomtype(themeid, rpthemeid, branchid, rpking, status);
			if(null == rproomtype || rproomtype.size() == 0){
			    rproomtype = branchBasicService.getRpapplyroomtype(themeid, rpthemeid, branchid, rpking,CommonConstants.RoomPriceStatus.basic);
			}
			System.out.println(rproomtype);
			mv.setViewName("page/basic/branch/rpapply");
			request.setAttribute("themename", themename);
			request.setAttribute("branchname", branchname);
			request.setAttribute("rpkindname", rpkindname);
			request.setAttribute("rproomtype", rproomtype);
			request.setAttribute("rppack", rppack);
			request.setAttribute("status", status);
			request.setAttribute("themeid", themeid);
			request.setAttribute("branchid", branchid);
			request.setAttribute("rpking", rpking);
			request.setAttribute("statusid", status);
			request.setAttribute("rpsetup", rpsetup);
			request.setAttribute("rpstatus", rpstatus);
			return mv;
		}
		
		// 房租价申请操作
		@RequestMapping("/rpOperate.do")
		public void rpOperate(HttpServletRequest request, HttpServletResponse response, String theme, String branchid,
				String rpkind, String validstart, String validend, String validday, String filterday, String operate,
				String status, String remark) throws Exception {
			LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
			com.ideassoft.portal.DataDealPortalService service = new com.ideassoft.portal.DataDealPortalService();
			com.ideassoft.portal.IDataDealPortal portal = service.getDataDealPortalPort();
			Staff staff = loginUser.getStaff();
			String logId = this.branchBasicService.getSequence("SEQ_NEW_PRICEAPPLY", null);
			Date day = new Date();
			SimpleDateFormat df = new SimpleDateFormat("yyMMdd");
			String t = df.format(day);
			String applyid = t + branchid + logId;
			String a = applyid;
			DateFormat dff = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
			Date validstartdata = null;
			if(validstart.equals("") == false){
				validstartdata = dff.parse(validstart);
			}
			Date validenddata = null;
			if(validend.equals("")==false){
				validenddata = dff.parse(validend);
			}
			String pastatus = null;
			Branch branch = ((Branch) (branchBasicService.findOneByProperties(Branch.class, "branchId", branchid)));
			 String rpauditswitch = branch.getFlag() == null ? "" : branch.getFlag().toString();
			/*String rpauditswitch = portal.call(1, 1, "{ function: \"audit.RoompriceAuditswitch\", data:{branchid:"+branchid+" } }");*/
			String postone = null;
			String recorduser = staff.getStaffId().toString();
			SysParam atsysParam = ((SysParam) (branchBasicService.findOneByProperties(SysParam.class, "paramType", "RPKIND",
					"content", rpkind)));
			String applytypename = atsysParam.getParamName().toString();
			SysParam aksysParam = ((SysParam) (branchBasicService.findOneByProperties(SysParam.class, "paramType",
					"RPSTATUS", "content", status)));
			String applykindname = aksysParam.getParamName().toString();
			Branch branchName = ((Branch) (branchBasicService.findOneByProperties(Branch.class, "branchId", branchid)));
			String branchname = branchName.getBranchName().toString();
			Staff staffName = ((Staff) (branchBasicService.findOneByProperties(Staff.class, "staffId", recorduser)));
			String staffname = staffName.getStaffName();
			String postcloud = null;
			String priceapplyremark = "";
			String Auditremark ="";
//			if(HeartBeatClient.heartbeat){
				if (operate.equals(CommonConstants.TakeEffect.Immediately)) {
					if (rpauditswitch.equals(CommonConstants.RpBranchSwitch.RbsOpen)) {
						   Auditremark = "待审核";
						   pastatus = CommonConstants.RpApplyStatus.RpAudit;
						   priceapplyremark = CommonConstants.RpApplyStatus.RpUse;
		                   postcloud = portal.call(1, 1, "{ function: \"audit.roompriceAuditpostphone\", data:{branchid:"+branchid+" } }");
		                   postone = portal.call(1, 1, "{ function: \"audit.roompriceAuditpost\", data:{branchid:"+branchid+" } }");
						   JSONArray postcloudarray = new JSONArray(postcloud);
						if(postcloudarray.length()>0){
							 for(int i=0;i<postcloudarray.length();i++){
								  JSONObject postcloudarraychild = postcloudarray.getJSONObject(i);
								  String mobile = postcloudarraychild.getString("MOBILE").toString();
								  if (mobile.matches(RegExUtil.MOBILE)) {
										SmsSingleSender singleSender = new SmsSingleSender(SystemConstants.note.APPID,SystemConstants.note.APPKEY);
										ArrayList<String> params = new ArrayList<String>();
										String auditremark = "待审核";
										params.add(postcloudarraychild.getString("STAFFNAME").toString());
										params.add(branchname);
										params.add(staffname);
										params.add(applytypename);
										params.add(applykindname);
										params.add(auditremark);
										singleSender.sendWithParam(SystemConstants.note.COUNTRY, mobile, 47258, params, "", "", "");
									}
							 }
							
						}
						/*List<?> poststaffphone = AuditService.getPoststaffphone(postone, branchid);
						if (null != poststaffphone && !poststaffphone.isEmpty()) {
							for (int j = 0; j < poststaffphone.size(); j++) {
								String mobile = ((Map<?, ?>) poststaffphone.get(j)).get("MOBILE").toString();
								if (mobile.matches(RegExUtil.MOBILE)) {
									SmsSingleSender singleSender = new SmsSingleSender(SystemConstants.note.APPID,
											SystemConstants.note.APPKEY);
									ArrayList<String> params = new ArrayList<String>();
									String auditremark = "待审核";
									params.add(staffname);
									params.add(branchname);
									params.add(staffname);
									params.add(applytypename);
									params.add(applykindname);
									params.add(auditremark);
									singleSender.sendWithParam(SystemConstants.note.COUNTRY, mobile, 47258, params, "", "", "");
								}

							}
						}*/
					} else if (rpauditswitch.equals(CommonConstants.RpBranchSwitch.RbsClose)) {
						 if(status.equals(CommonConstants.RoomPriceStatus.basic)){
							 pastatus = CommonConstants.RpApplyStatus.RpActive;
						 }else{
						     pastatus = CommonConstants.RpApplyStatus.RpUse;
						 }
					}else{
						 if(status.equals(CommonConstants.RoomPriceStatus.basic)){
							 pastatus = CommonConstants.RpApplyStatus.RpActive;
						 }else{
						     pastatus = CommonConstants.RpApplyStatus.RpUse;
						 }
					}

				} else {

					if (rpauditswitch.equals(CommonConstants.RpBranchSwitch.RbsOpen)) {
						       Auditremark = "待审核";
						       pastatus = CommonConstants.RpApplyStatus.RpAudit;
						       priceapplyremark = CommonConstants.RpApplyStatus.RpUnuse;
						       postcloud = portal.call(1, 1, "{ function: \"audit.roompriceAuditpostphone\", data:{branchid:"+branchid+" } }");
			                   postone = portal.call(1, 1, "{ function: \"audit.roompriceAuditpost\", data:{branchid:"+branchid+" } }");
							JSONArray postcloudarray = new JSONArray(postcloud);
							if(postcloudarray.length()>0){
								 for(int i=0;i<postcloudarray.length();i++){
									  JSONObject postcloudarraychild = postcloudarray.getJSONObject(i);
									  String mobile = postcloudarraychild.getString("MOBILE").toString();
									  if (mobile.matches(RegExUtil.MOBILE)) {
											SmsSingleSender singleSender = new SmsSingleSender(SystemConstants.note.APPID,SystemConstants.note.APPKEY);
											ArrayList<String> params = new ArrayList<String>();
											String auditremark = "待审核";
											params.add(postcloudarraychild.getString("MOBILE").toString());
											params.add(branchname);
											params.add(staffname);
											params.add(applytypename);
											params.add(applykindname);
											params.add(auditremark);
											singleSender.sendWithParam(SystemConstants.note.COUNTRY, mobile, 47258, params, "", "", "");
										}
								 }
								
							}
						/*List<?> poststaffphone = AuditService.getPoststaffphone(postone, branchid);
						if (null != poststaffphone && !poststaffphone.isEmpty()) {
							for (int j = 0; j < poststaffphone.size(); j++) {
								String mobile = ((Map<?, ?>) poststaffphone.get(j)).get("MOBILE").toString();
								String auditername = ((Map<?, ?>) poststaffphone.get(j)).get("STAFFNAME").toString();
								if (mobile.matches(RegExUtil.MOBILE)) {
									SmsSingleSender singleSender = new SmsSingleSender(SystemConstants.note.APPID,
											SystemConstants.note.APPKEY);
									ArrayList<String> params = new ArrayList<String>();
									String auditremark = "待审核";
									params.add(auditername);
									params.add(branchname);
									params.add(staffname);
									params.add(applytypename);
									params.add(applykindname);
									params.add(auditremark);
									singleSender.sendWithParam(SystemConstants.note.COUNTRY, mobile, 47258, params, "", "", "");
								}

							}
						}*/
					} else if (rpauditswitch.equals(CommonConstants.RpBranchSwitch.RbsClose)) {
						pastatus = CommonConstants.RpApplyStatus.RpUnuse;
					} else {
						pastatus = CommonConstants.RpApplyStatus.RpUnuse;
					}
				}
				String premark = remark + priceapplyremark;
				PriceApply priceapply = new PriceApply();
				priceapply.setApplyId(applyid);
				priceapply.setBranchId(branchid);
				priceapply.setApplyType(rpkind);
				priceapply.setValidStart(validstartdata);
				priceapply.setValidEnd(validenddata);
				priceapply.setValidDay(validday);
				priceapply.setFilterDay(filterday);
				priceapply.setStatus(pastatus);
				priceapply.setApplyTime(new Date());
				priceapply.setRecordUser(staff.getStaffId());
				priceapply.setRemark(premark);
				priceapply.setPost(postone);
				priceapply.setAuditRemark(Auditremark);
				priceapply.setApplyKind(status);
				priceapply.setRecordTime(new Date());
				branchBasicService.save(priceapply);
				/*SysParam systemtype = (SysParam) PmsmanageService.findOneByProperties(SysParam.class, "paramType","SYSTEMTYPE", "status", "1");
				String stype = systemtype.getContent().toString();
				if(stype.equals(CommonConstants.SystemType.Branch)){
					int priority = 1;
					SysParam param = (SysParam) PmsmanageService.findOneByProperties(SysParam.class, "paramType","REMOTE_PATH", "status", "1");
					List<PriceApply> priceapplyList = new ArrayList<PriceApply>();
					priceapplyList.add(priceapply);
					Map<String, Object> priceapplyMap = new HashMap<String, Object>();
					priceapplyMap.put("PriceApply", priceapplyList);
					TransferServcie.getInstance().transferData(priority, param.getContent(),JSONUtil.fromObject(priceapplyMap).toString());
					}*/
				
			/*}else{
				a = "本地网络未连接，请网络通畅后提交";
			}*/
			JSONUtil.responseJSON(response, new AjaxResult(1, a));
		}
		
		/*
		 * 自定义房型新增页面跳转
		 */
		@RequestMapping("/addRoomType.do")
		public ModelAndView addRoomType(HttpServletRequest request, HttpServletResponse response) throws Exception {
			ModelAndView mv = new ModelAndView();
			mv.setViewName("/page/basic/branch/roomType");
			SysParam sysParam = (SysParam) branchBasicService.findOneByProperties(SysParam.class, "paramType", "SYSTEMTYPE");
			String systemType = sysParam.getContent();
			LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
			String branchId = loginuser.getStaff().getBranchId();
			Branch branch = (Branch) branchBasicService.findOneByProperties(Branch.class, "branchId", branchId);
			String branchRank = branch.getRank();
			String branchName = branch.getBranchName();
			mv.addObject("branchRank",branchRank);
			mv.addObject("branchName",branchName);
			Branch branchid = (Branch) branchBasicService.findOneByProperties(Branch.class, "branchId", branchId);
			String theme = branchid.getBranchType();
			List<?> roomBedDesc = branchBasicService.getRoomBedType();
			List<?> roomPositionList = branchBasicService.getRoomPosition();
			List<?> roomBroadbandList = branchBasicService.getRoomBroadband();
			List<?> themeList = branchBasicService.getThemeList();
			for(int i = 0;i<themeList.size();i++){
				if(((Map<?,?>)themeList.get(i)).get("CONTENT").equals("3")){
					themeList.remove(i);
				}
			}
			List<?> hotelDeviceList = branchBasicService.getHotelDevice();
			List<?> houseDeviceList = branchBasicService.getHouseDevice();
			List<?> apartmentDeviceList = branchBasicService.getApartmentDevice();
			List<?> tips = branchBasicService.getTips();
			List<?> houseList = branchBasicService.getHouseList();
			mv.addObject("systemType",systemType);
			mv.addObject("theme", theme);
			mv.addObject("loginUserBranchId", branchId);
			mv.addObject("themeList", themeList);
			mv.addObject("roomBedDescList", roomBedDesc);
			mv.addObject("roomBroadbandList", roomBroadbandList);
			mv.addObject("roomPositionList", roomPositionList);
			mv.addObject("hotelDeviceList", hotelDeviceList);
			mv.addObject("houseDeviceList", houseDeviceList);
			mv.addObject("tips", tips);
			mv.addObject("houseList", houseList);
			mv.addObject("apartmentDeviceList", apartmentDeviceList);
			return mv;
		}
		/*
		 * 
		 * 自定义房型数据新增
		 */
		@SuppressWarnings("unused")
		@RequestMapping("/addRoomTypeData.do")
		public void addToomTypeData(HttpServletRequest request, HttpServletResponse response) throws Exception {
			SysParam param = (SysParam) branchBasicService.findOneByProperties(SysParam.class, "paramType",
					SystemConstants.ParamType.BRANCH_IP, "paramName", "1");
			
			int priority = 1;
			String fileName = null;
			FileOutputStream fos = null;
			InputStream is = null;
			SysParam sysParam = (SysParam) branchBasicService.findOneByProperties(SysParam.class, "paramType", "SYSTEMTYPE");
			String systemType = sysParam.getContent();
			LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
			String staffId = loginuser.getStaff().getStaffId();
			String branchId = loginuser.getStaff().getBranchId();
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			String roomType = multipartRequest.getParameter("roomType");
			String roomName = multipartRequest.getParameter("roomName");
			String theme = multipartRequest.getParameter("themeId");
			byte roomBed = Byte.parseByte(multipartRequest.getParameter("roomBed"));
			String bedType = multipartRequest.getParameter("bedType");
			String broadband = multipartRequest.getParameter("broadband");
			String roomDesc = multipartRequest.getParameter("roomDesc");
			String position = multipartRequest.getParameter("position");
			String device = multipartRequest.getParameter("device");
			String tip = multipartRequest.getParameter("tip");
			String remark = multipartRequest.getParameter("remark");
			String branch_select_CUSTOM_VALUE = multipartRequest.getParameter("branch_select_CUSTOM_VALUE");
			if(branch_select_CUSTOM_VALUE != null && !("".equals(branch_select_CUSTOM_VALUE))){
				branchId = branch_select_CUSTOM_VALUE;
			}
			
			String rtp = multipartRequest.getParameter("roomTypePrice");
			double roomTypePrice = 0;
			
			String pr = multipartRequest.getParameter("roomTypeHoursPrice");
			double roomTypeHoursPrice = 0;
			
			String branchIdChoose = multipartRequest.getParameter("branchId");
			String houseId = multipartRequest.getParameter("houseId");
			List<?> hotelDeviceList = branchBasicService.getHotelDevice();
			List<?> apartmentDeviceList = branchBasicService.getApartmentDevice();
			List<?> houseDeviceList = branchBasicService.getHouseDevice();
			List<?> tips = branchBasicService.getTips();
			String deviceString = "";
			String tipString = "";
			String headPicUrl = "";
			if(!(CommonConstants.Branch.HOUSEID.equals(theme.trim()))){
				List<?> rpsetup = branchBasicService.getRpsetup();
				SysParam systemtype = (SysParam) branchBasicService.findOneByProperties(SysParam.class, "paramType","SYSTEMTYPE", "status", "1");
				String stype = systemtype.getContent().toString();
				for (int i = 0; i < rpsetup.size(); i++) {
					String rpid = ((Map<?, ?>) rpsetup.get(i)).get("PARAMNAME").toString();
					String rpname = ((Map<?, ?>) rpsetup.get(i)).get("PARAMDESC").toString();
					String discount = ((Map<?, ?>) rpsetup.get(i)).get("DISCOUNT").toString();
					String memberrank = ((Map<?, ?>) rpsetup.get(i)).get("CONTENT").toString();
					Double d = new Double(discount) / 100;
					if(!rtp.equals("")){
						roomTypePrice = Double.parseDouble(rtp);
					}
					Double p = roomTypePrice;
					Format f = new DecimalFormat("0.00");
					Double price = Double.parseDouble(f.format(p * d));
					
					RoomPrice rp = new RoomPrice();
					rp.setDiscount(d);
					rp.setMemberRank(memberrank);
					rp.setRecordTime(new Date());
					rp.setRecordUser(staffId);
					rp.setRoomPrice(price);
					RoomPriceId rpId = new RoomPriceId();
					rpId.setBranchId(branchId);
					rpId.setRoomType(roomType);
					rpId.setRpId(rpid);
					if (CommonConstants.Branch.HOTELID.equals(theme.trim())) {
						rpId.setRpKind(CommonConstants.DefaultRoomPrice.DEFAULT_RPKIND_COMMON);
					} else {
						rpId.setRpKind(CommonConstants.DefaultRoomPrice.DEFAULT_RPKIND_MONTH);
					}
					rpId.setStatus(CommonConstants.DefaultRoomPrice.DEFAULT_RP_TYPE);
					rp.setRoomPriceId(rpId);
					rp.setRpName(rpname);
					rp.setRpType(CommonConstants.DefaultRoomPrice.DEFAULT_RP_TYPE);
					rp.setState(CommonConstants.DefaultRoomPrice.DEFAULT_RP_STATE);
					rp.setTheme(theme);
					List<RoomPrice> listUpdateRp = new ArrayList<RoomPrice>();
					listUpdateRp.add(rp);
					branchBasicService.saveOrUpdateAll(listUpdateRp);
					
					/*if(CommonConstants.SystemType.Branch.equals(systemType.trim())){
						List<RoomPrice> commonRoomPrice = new ArrayList<RoomPrice>();
						commonRoomPrice.add(rp);
						Map<String, Object> commonRoomPriceMap = new HashMap<String, Object>();
						commonRoomPriceMap.put("RoomPrice", commonRoomPrice);
						TransferServcie.getInstance().transferData(priority, param.getContent(),
								JSONUtil.fromObject(commonRoomPriceMap).toString());
					}*/
				}
				// 调用价格调整方法，当系统初始化时将所有公寓房间价的基准价格save到房价汇总表中
				System.out.println(theme);
				System.out.println(rtp);
				if(CommonConstants.SubBranchType.APART.equals(theme)){
					BasePriceUtile.addApartmentBasePrice(branchId, roomType, CommonConstants.SubBranchType.APART, rtp);
				}else{
					BasePriceUtile.addHotelBasePrice(branchId, roomType, CommonConstants.SubBranchType.HOTEL, rtp, pr);
				}
			}

			if (CommonConstants.Branch.HOTELID.equals(theme.trim())) {
				List<?> rpsetup = branchBasicService.getRpsetup();
				SysParam systemtype = (SysParam) branchBasicService.findOneByProperties(SysParam.class, "paramType","SYSTEMTYPE", "status", "1");
				String stype = systemtype.getContent().toString();
				for (int i = 0; i < rpsetup.size(); i++) {
					String rpid = ((Map<?, ?>) rpsetup.get(i)).get("PARAMNAME").toString();
					String rpname = ((Map<?, ?>) rpsetup.get(i)).get("PARAMDESC").toString();
					String discount = ((Map<?, ?>) rpsetup.get(i)).get("DISCOUNT").toString();
					String memberrank = ((Map<?, ?>) rpsetup.get(i)).get("CONTENT").toString();
					Double d = new Double(discount) / 100;
					if (!pr.equals("")) {
						roomTypeHoursPrice = Double.parseDouble(pr);
					}
					Double p = roomTypeHoursPrice;
					Format f = new DecimalFormat("0.00");
					Double price = Double.parseDouble(f.format(p * d));
					
					RoomPrice rp = new RoomPrice();
					rp.setDiscount(d);
					rp.setMemberRank(memberrank);
					rp.setRecordTime(new Date());
					rp.setRecordUser(staffId);
					rp.setRoomPrice(price);
					RoomPriceId rpId = new RoomPriceId();
					rpId.setBranchId(branchId);
					rpId.setRoomType(roomType);
					rpId.setRpId(rpid);
					rpId.setRpKind(CommonConstants.DefaultRoomPrice.DEFAULT_RPKIND_HOURS);
					rpId.setStatus(CommonConstants.DefaultRoomPrice.DEFAULT_RP_TYPE);
					rp.setRoomPriceId(rpId);
					rp.setRpName(rpname);
					rp.setRpType(CommonConstants.DefaultRoomPrice.DEFAULT_RP_TYPE);
					rp.setState(CommonConstants.DefaultRoomPrice.DEFAULT_RP_STATE);
					rp.setTheme(theme);
					List listUpdateRp = new ArrayList();
					listUpdateRp.add(rp);
					branchBasicService.saveOrUpdateAll(listUpdateRp);
					
					/*if(CommonConstants.SystemType.Branch.equals(systemType.trim())){
						List<RoomPrice> commonRoomPrice = new ArrayList<RoomPrice>();
						commonRoomPrice.add(rp);
						Map<String, Object> commonRoomPriceMap = new HashMap<String, Object>();
						commonRoomPriceMap.put("RoomPrice", commonRoomPrice);
						TransferServcie.getInstance().transferData(priority, param.getContent(),
								JSONUtil.fromObject(commonRoomPriceMap).toString());
					}*/
				}
				
			}

			if (device.endsWith(",")) {
				deviceString = device.substring(0, device.length() - 1);
			} else {
				deviceString = device;
			}
			if (tip.endsWith(",")) {
				tipString = tip.substring(0, tip.length() - 1);
			} else {
				tipString = tip;
			}
			String deviceArray[] = deviceString.split(",");
			String tipArray[] = tipString.split(",");
			String deviceSign = "";
			String tipSign = "";
			List<?> deviceList = null;
			if ("1".equals(theme.trim())) {
				deviceList = hotelDeviceList;
			} else if ("2".equals(theme.trim())) {
				deviceList = apartmentDeviceList;
			} else {
				deviceList = houseDeviceList;
			}
			boolean deviceflag = true;
			for (int i = 0; i < deviceList.size(); i++) {
				for (int j = 0; j < deviceArray.length; j++) {
					if (((Map<?, ?>) deviceList.get(i)).get("PARAM_NAME").toString().equals(deviceArray[j])) {
						deviceSign += "1";
						deviceflag = false;
					}
				}
				if (deviceflag) {
					deviceSign += "0";
				}
				deviceflag = true;
			}

			boolean tipflag = true;
			for (int i = 0; i < tips.size(); i++) {
				for (int j = 0; j < tipArray.length; j++) {
					if (((Map<?, ?>) tips.get(i)).get("CONTENT").toString().equals(tipArray[j])) {
						tipSign += "1";
						tipflag = false;
					}
				}
				if (tipflag) {
					tipSign += "0";
				}
				tipflag = true;
			}
			RoomType type = new RoomType();
			
			type.setBedDesc(bedType);
			type.setBroadband(broadband);
			type.setRecordTime(new Date());
			type.setRecordUser(staffId);
			type.setRemark(remark);
			type.setRoomBed(roomBed);
			type.setRoomDesc(roomDesc);
			type.setRoomLabel(deviceSign);
			type.setRoomName(roomName);
			type.setRoomPosition(position);
			if(theme.equals("3")){
				branchId = houseId;
			}
			type.setRoomTypeKey(new RoomTypeKey(branchId, roomType));
			type.setStatus(CommonConstants.STATUS.NORMAL);
			type.setTheme(theme);
			type.setTips(tipSign);
			List updateList = new ArrayList();
			updateList.add(type);
			branchBasicService.saveOrUpdateAll(updateList);

			// priority = Integer.parseInt(member.getMemberRank());
			/*if(CommonConstants.SystemType.Branch.equals(systemType.trim())){
				List<RoomType> listRoomType = new ArrayList<RoomType>();
				listRoomType.add(type);
				Map<String, Object> mapRoomType = new HashMap<String, Object>();
				mapRoomType.put("RoomType", listRoomType);
				TransferServcie.getInstance().transferData(priority, param.getContent(),
						JSONUtil.fromObject(mapRoomType).toString());
			}*/

			JSONUtil.responseJSON(response, new AjaxResult(1, null));

		}
		
		@RequestMapping("/editRoomType.do")
		public ModelAndView editRoomType(HttpServletRequest request, HttpServletResponse response) throws Exception {
			ModelAndView mv = new ModelAndView();
			mv.setViewName("/page/basic/branch/roomTypeEdit");
			LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
			String loginuserBranchId = loginuser.getStaff().getBranchId();
			Branch branch = (Branch) branchBasicService.findOneByProperties(Branch.class, "branchId", loginuserBranchId);
			String branchRank = branch.getRank();
			mv.addObject("branchRank",branchRank);
			String branchName = "";
			String roomType = request.getParameter("roomType");
			String roomName = request.getParameter("roomName");
			String theme = request.getParameter("theme");
			String roomBed = request.getParameter("roomBed");
			String bedDesc = request.getParameter("bedDesc");
			String broadband = request.getParameter("broadband");
			String roomDesc = request.getParameter("roomDesc");
			String roomPosition = request.getParameter("roomPosition");
			String status = request.getParameter("status");
			String recordTime = request.getParameter("recordTime");
			String remark = request.getParameter("remark");
			String roomLabel = request.getParameter("roomLabel");
			String tip = request.getParameter("tip");
			String branchId = request.getParameter("branchId");
			if(branchId != null && !"".equals(branchId) ){
				Branch br =  (Branch)branchBasicService.findOneByProperties(Branch.class, "branchId", branchId);
                       branchName = br.getBranchName();
                       mv.addObject("branchName",branchName);
			}
			List<?> roomBedDesc = branchBasicService.getRoomBedType();
			List<?> roomPositionList = branchBasicService.getRoomPosition();
			List<?> roomBroadbandList = branchBasicService.getRoomBroadband();
			List<?> hotelDeviceList = branchBasicService.getHotelDevice();
			List<?> houseDeviceList = branchBasicService.getHouseDevice();
			List<?> apartmentDeviceList = branchBasicService.getApartmentDevice();
			List<?> themeList = branchBasicService.getThemeList();
			for(int i = 0;i<themeList.size();i++){
				if(((Map<?,?>)themeList.get(i)).get("CONTENT").equals("3")){
					themeList.remove(i);
				}
			}
			List<?> tips = branchBasicService.getTips();
			String tipString = "";
			String deviceString = "";
			char tipArray[] = tip.toCharArray();
			for (int j = 0; j < tipArray.length; j++) {
				if (tipArray[j] == '1') {
					tipString += (j == (tipArray.length - 1) ? (((Map<?, ?>) tips.get(j)).get("CONTENT").toString())
							: ((Map<?, ?>) tips.get(j)).get("CONTENT").toString() + ",");
				}
			}
			List<?> deviceList = null;
			if (theme.equals("酒店")) {
				deviceList = hotelDeviceList;
			} else if (theme.equals("公寓")) {
				deviceList = apartmentDeviceList;
			} else {
				// deviceList=apartmentDeviceList;
				deviceList = houseDeviceList;
			}

			// theme.equals("酒店")?deviceList=hotelDeviceList:(theme.equals("公寓")?(deviceList=apartmentDeviceList):(deviceList=houseDeviceList));
			char deviceArray[] = roomLabel.toCharArray();
			for (int j = 0; j < deviceArray.length; j++) {
				if (deviceArray[j] == '1') {
					deviceString += (j == (deviceArray.length - 1) ? ((((Map<?, ?>) deviceList.get(j)).get("PARAM_NAME"))
							.toString()) : ((Map<?, ?>) deviceList.get(j)).get("PARAM_NAME").toString() + ",");
				}
			}
			mv.addObject("roomType", roomType);
			mv.addObject("roomName", roomName);
			mv.addObject("theme", theme);
			mv.addObject("roomBed", roomBed);
			mv.addObject("bedDesc", bedDesc);
			mv.addObject("broadband", broadband);
			mv.addObject("roomDesc", roomDesc);
			mv.addObject("roomPosition", roomPosition);
			mv.addObject("status", status);
			mv.addObject("recordTime", recordTime);
			mv.addObject("remark", remark);
			mv.addObject("roomLabel", roomLabel);
			mv.addObject("tip", tip);
			mv.addObject("branchId", branchId);
			mv.addObject("tipString", tipString);
			mv.addObject("deviceString", deviceString);
			mv.addObject("roomBedDescList", roomBedDesc);
			mv.addObject("roomPositionList", roomPositionList);
			mv.addObject("roomBroadbandList", roomBroadbandList);
			mv.addObject("hotelDeviceList", hotelDeviceList);
			mv.addObject("themeList", themeList);
			mv.addObject("tips", tips);
			mv.addObject("houseDeviceList", houseDeviceList);
			mv.addObject("apartmentDeviceList", apartmentDeviceList);
			mv.addObject("loginuserBranchId", loginuserBranchId);
			

			return mv;
		}
		
		// 自定义房型管理删除
		@RequestMapping("/delRoomType.do")
		@ResponseBody
		public String delRoomType(HttpServletRequest request, HttpServletResponse response, String roomType,String branchId,String theme)
				throws UnknownHostException {
//			SysParam sysParam = (SysParam) PmsmanageService.findOneByProperties(SysParam.class, "paramType", "SYSTEMTYPE");
//			String systemType = sysParam.getContent();
			LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
			String loginuserBranchId = loginuser.getStaff().getBranchId();
			Branch loginuserbranch = (Branch) branchBasicService.findOneByProperties(Branch.class, "branchId", loginuserBranchId);
			String branchRank = loginuserbranch.getRank();
			SysParam param = (SysParam) branchBasicService.findOneByProperties(SysParam.class, "paramType",
					SystemConstants.ParamType.BRANCH_IP, "paramName", "1");
			int priority = 1;
			String[] roomTypes = roomType.split(",");
			String[] branchIds = branchId.split(",");
			String[] themes = theme.split(",");
			boolean operationFlag = true;
			if(branchRank.equals("0")){
				for(int i = 0;i<themes.length;i++){
					if(!themes[i].equals(CommonConstants.Branch.HOUSE)){
						operationFlag = false;
						break;
					}
				}
			}
			/*if(operationFlag ==false){
				return String.valueOf(CommonConstants.PortalResultCode.NULL);
			}*/
			boolean flag = false;
			for (int i = 0; i < roomTypes.length; i++) {
				String branchIdNew = branchIds[i];
				RoomType roomTypeNew = (RoomType) branchBasicService.findOneByProperties(RoomType.class, "roomTypeKey.roomType", roomTypes[i], "roomTypeKey.branchId",branchIdNew);
				roomTypeNew.setStatus("0");
				// list.add(roomTypeNew);
				roomTypeNew.setRecordTime(new Date());
				branchBasicService.update(roomTypeNew);
				List<Room> newRoom = branchBasicService.findByProperties(Room.class, "roomKey.branchId", branchIdNew, "roomType", roomTypes[i]);
				for (int j = 0; j < newRoom.size(); j++) {
					newRoom.get(j).setStatus("0");
					branchBasicService.update(newRoom.get(j));
				}
				List<RoomPrice> roomPriceList = branchBasicService.findByProperties(RoomPrice.class, "roomPriceId.branchId", branchIdNew, "roomPriceId.roomType",roomTypes[i]);
				for(int j = 0;j<roomPriceList.size();j++){
					RoomPrice roomPrice = roomPriceList.get(j);
					roomPrice.setState("0");
					branchBasicService.update(roomPrice);
				}
				/*if(CommonConstants.SystemType.Branch.equals(systemType.trim())){
					List<RoomType> roomTypeList = new ArrayList<RoomType>();
					roomTypeList.add(roomTypeNew);
					Map<String, Object> roomTypeMap = new HashMap<String, Object>();
					roomTypeMap.put("RoomType", roomTypeList);
					TransferServcie.getInstance().transferData(priority, param.getContent(),
							JSONUtil.fromObject(roomTypeMap).toString());
				}*/
				if (i == roomTypes.length - 1) {
					flag = true;
				}
			}
			if (flag == true) {
				return String.valueOf(CommonConstants.PortalResultCode.DONE);
			} else {
				return String.valueOf(CommonConstants.PortalResultCode.FAILED);
			}

			// JSONUtil.responseJSON(response, new AjaxResult(1, null));
		}
		
		@SuppressWarnings("unchecked")
		@RequestMapping("/addRoomTypePictureTwo.do")
		public ModelAndView addRoomTypePictureTwo(HttpServletRequest request, HttpServletResponse response) throws Exception {
			ModelAndView mv = new ModelAndView("page/basic/branch/addRoomTypePictureNew");
			String roomType = request.getParameter("roomType");
			String branchId = request.getParameter("branchId");
			String theme = request.getParameter("theme");
			String scence = request.getParameter("scence");

			String themeId = "";
			List<Branch> branchList;
			if (theme.trim().equals(CommonConstants.Branch.HOTEL)) {
				themeId = CommonConstants.Branch.HOTELID;
			} else if (theme.trim().equals(CommonConstants.Branch.APARTMENT)) {
				themeId = CommonConstants.Branch.APARTMENTID;
			} else if (theme.trim().equals(CommonConstants.Branch.HOUSE)) {
				themeId = CommonConstants.Branch.HOUSEID;
			}
			if (themeId.trim().equals(CommonConstants.Branch.HOUSEID)) {
				branchList = branchBasicService.findAll(House.class);
			} else {
				branchList = branchBasicService.findByProperties(Branch.class, "branchType", themeId);
			}
			
			List<Picture> picturea = new ArrayList<Picture>();
			List<Picture> pictures = new ArrayList<Picture>();
			Picture picture = null;
			
			RoomPicture tPicture = (RoomPicture) branchBasicService.findOneByProperties(RoomPicture.class, "style", "tt", "branchId", branchId, "roomType", roomType);
			if(tPicture != null){
				picture = (Picture) branchBasicService.findOneByProperties(Picture.class, "pictureId", tPicture.getPictureId());
				picturea.add(picture);
			}
			
			List<RoomPicture> branchPictures = branchBasicService.findByPropertiesWithSort(RoomPicture.class, "pictureId", "asc", "style", "qt", "branchId", branchId, "roomType", roomType);
			if(branchPictures.size() > 0){
				for(RoomPicture branchPicture : branchPictures){
					Picture dpicture = (Picture) branchBasicService.findOneByProperties(Picture.class, "pictureId", branchPicture.getPictureId());
					pictures.add(dpicture);
				}
			}
			
			mv.addObject("roomType", roomType);
			mv.addObject("theme", theme);
			mv.addObject("scence", scence);
			mv.addObject("branchId", branchId);
			mv.addObject("branchList", branchList);
			mv.addObject("picture", picturea);
			mv.addObject("pictures", pictures);
			return mv;
		}
		
		/*
		 * 
		 * 房型状态编辑
		 */

		@SuppressWarnings("unused")
		@RequestMapping("/editRoomTypeData.do")
		public void editToomTypeData(HttpServletRequest request, HttpServletResponse response) throws Exception {
			SysParam param = (SysParam) branchBasicService.findOneByProperties(SysParam.class, "paramType",
					SystemConstants.ParamType.BRANCH_IP, "paramName", "1");
			LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
			String staffId = loginuser.getStaff().getStaffId();
			String branchId = loginuser.getStaff().getBranchId();
			Branch branch = (Branch) branchBasicService.findOneByProperties(Branch.class, "branchId", branchId);
			String branchRank = branch.getRank();
			String roomType = request.getParameter("roomType");
			String roomName = request.getParameter("roomName");
			String theme = request.getParameter("theme");
			byte roomBed = Byte.parseByte(request.getParameter("roomBed"));
			String bedType = request.getParameter("bedType");
			String broadband = request.getParameter("broadband");
			String roomDesc = request.getParameter("roomDesc");
			String position = request.getParameter("position");
			String device = request.getParameter("device");
			String tip = request.getParameter("tip");
			String remark = request.getParameter("remark");
			String chooseBrnchId = request.getParameter("branchId");
			SysParam sysParam = (SysParam) branchBasicService.findOneByProperties(SysParam.class, "paramType", "SYSTEMTYPE");
			String systemType = sysParam.getContent();
			List<?> hotelDeviceList = branchBasicService.getHotelDevice();
			List<?> apartmentDeviceList = branchBasicService.getApartmentDevice();
			List<?> houseDeviceList = branchBasicService.getHouseDevice();
			List<?> tips = branchBasicService.getTips();
			/*if(!theme.equals(CommonConstants.Branch.HOUSEID)){
				if(branchRank.equals("0")){
					JSONUtil.responseJSON(response, new AjaxResult(0, null));
					return;
				}
			}*/
			int priority = 1;
			String deviceString = "";
			String tipString = "";
			if (device.endsWith(",")) {
				deviceString = device.substring(0, device.length() - 1);
			} else {
				deviceString = device;
			}
			if (tip.endsWith(",")) {
				tipString = tip.substring(0, tip.length() - 1);
			} else {
				tipString = tip;
			}
			String deviceArray[] = deviceString.split(",");
			String tipArray[] = tipString.split(",");
			String deviceSign = "";
			String tipSign = "";
			List<?> deviceList = null;
			if ("1".equals(theme.trim())) {
				deviceList = hotelDeviceList;
			} else if ("2".equals(theme.trim())) {
				deviceList = apartmentDeviceList;
			} else {
				deviceList = houseDeviceList;
			}
			boolean deviceflag = true;
			for (int i = 0; i < deviceList.size(); i++) {
				for (int j = 0; j < deviceArray.length; j++) {
					if (((Map<?, ?>) deviceList.get(i)).get("PARAM_NAME").toString().equals(deviceArray[j])) {
						deviceSign += "1";
						deviceflag = false;
					}
				}
				if (deviceflag) {
					deviceSign += "0";
				}
				deviceflag = true;
			}

			boolean tipflag = true;
			for (int i = 0; i < tips.size(); i++) {
				for (int j = 0; j < tipArray.length; j++) {
					if (((Map<?, ?>) tips.get(i)).get("CONTENT").toString().equals(tipArray[j])) {
						tipSign += "1";
						tipflag = false;
					}
				}
				if (tipflag) {
					tipSign += "0";
				}
				tipflag = true;
			}
			RoomType type = new RoomType();
			type.setBedDesc(bedType);
			type.setBroadband(broadband);
			type.setRecordTime(new Date());
			type.setRecordUser(staffId);
			type.setRemark(remark);
			type.setRoomBed(roomBed);
			type.setRoomDesc(roomDesc);
			type.setRoomLabel(deviceSign);
			type.setRoomName(roomName);
			type.setRoomPosition(position);
			type.setRoomTypeKey(new RoomTypeKey(chooseBrnchId, roomType));
			type.setStatus(CommonConstants.STATUS.NORMAL);
			type.setTheme(theme);
			type.setTips(tipSign);
			branchBasicService.update(type);
			/*if(CommonConstants.SystemType.Branch.equals(systemType.trim())){
				List<RoomType> listRoomType = new ArrayList<RoomType>();
				listRoomType.add(type);
				Map<String, Object> mapRoomType = new HashMap<String, Object>();
				mapRoomType.put("RoomType", listRoomType);
				TransferServcie.getInstance().transferData(priority, param.getContent(),
						JSONUtil.fromObject(mapRoomType).toString());
			}*/
			
			JSONUtil.responseJSON(response, new AjaxResult(1, null));

		}
		
		@RequestMapping("/delRoomPicsTwo.do")
		public void delRoomPicsTwo(HttpServletRequest request, HttpServletResponse response, String pictureId){
			RoomPicture branchPic = (RoomPicture) branchBasicService.findById(RoomPicture.class, pictureId);
			if (branchPic != null) {
				String picId=branchPic.getPictureId();
				Picture pic=(Picture) branchBasicService.findOneByProperties(Picture.class, "pictureId", picId);
				if(pic != null){
					String webPath = RequestUtil.getWebPath(request);
					String path = webPath.replace("\\","/") + "/upload/";
					String name = pic.getPictureUrl().substring(pic.getPictureUrl().lastIndexOf("/") + 1);
					java.io.File myFilePath = new java.io.File(path + name);
				    myFilePath.delete();
				    branchBasicService.delete(pic);
				}
				branchBasicService.delete(branchPic);
			}
		}
		
		@RequestMapping("/addRoomHeadPicDataTwo.do")
		public void addRoomHeadPicDataTwo(HttpServletRequest request, HttpServletResponse response) throws Exception {
			
			String fileName = null;
			FileOutputStream fos = null;
			InputStream is = null;
			String headPicUrl = "";
			
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile srcFile = multipartRequest.getFile("file");
			String roomType = multipartRequest.getParameter("roomType");
			String branchIdChoose = multipartRequest.getParameter("branchId");
			
			LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
			String staffId = loginuser.getStaff().getStaffId();
			
			RoomPicture branchPic = (RoomPicture) branchBasicService.findOneByProperties(RoomPicture.class, "branchId",
					branchIdChoose, "style", CommonConstants.RoomPicStyle.ROOMTYPE_HEAD_PIC, "status", CommonConstants.STATUS.NORMAL, "roomType", roomType);
			if (branchPic != null) {
				branchBasicService.delete(branchPic);
				String picId=branchPic.getPictureId();
				Picture pic=(Picture) branchBasicService.findOneByProperties(Picture.class, "pictureId", picId);
				if(pic != null){
					String webPath = RequestUtil.getWebPath(request);
					String path = webPath.replace("\\","/") + "/upload/";
					String name = pic.getPictureUrl().substring(pic.getPictureUrl().lastIndexOf("/") + 1);
					java.io.File myFilePath = new java.io.File(path + name);
				    myFilePath.delete();
				    branchBasicService.delete(pic);
				}
			}
//			if (!StringUtil.isEmpty(branch.getBranchIp())) {
//				SysParam paramFile = (SysParam) manageService.findOneByProperties(SysParam.class, "paramType",
//						SystemConstants.ParamType.BRANCH_IP, "paramName", "FILE");
//				int priorityFile = 1;
//				List<String> fileNameList = new ArrayList<String>();
//				fileNameList.add(fileName);
//				Map<String, Object> fileNameMap = new HashMap<String, Object>();
//				fileNameMap.put("FileName", fileNameList);
//				TransferServcie.getInstance().transferFile(priorityFile, paramFile.getContent(),
//						JSONUtil.fromObject(fileNameMap).toString());
//			}
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
			
			// 门店头图数据新增
			String seqName = "SEQ_PIC_ID";
			String sequence = branchBasicService.getCloudSequence(seqName);
			SimpleDateFormat sdf = new SimpleDateFormat("yymmdd");
			String picId = sdf.format(new Date()) + sequence;
			String picStyle = CommonConstants.RoomPicStyle.ROOMTYPE_HEAD_PIC;
			String picTrueStyle = CommonConstants.PicStyle.BRANCH_HEAD_PIC;
			String status = CommonConstants.STATUS.NORMAL;
			RoomPicture rp = new RoomPicture();
			rp.setBranchId(branchIdChoose);
			rp.setPictureId(picId);
			rp.setRecordTime(new Date());
			rp.setRecordUser(staffId);
			rp.setRoomType(roomType);
			rp.setStatus(status);
			rp.setStyle(picStyle);
			branchBasicService.save(rp);
			
			Picture picture = new Picture();
			picture.setPictureId(picId);
			picture.setPictureStyle(picTrueStyle);
			picture.setRecordTime(new Date());
			picture.setRecordUser(staffId);
			picture.setStatus(status);
			picture.setPictureUrl(headPicUrl);
			branchBasicService.save(picture);
			
			JSONUtil.responseJSON(response, new AjaxResult(1, null));
		}
		
		@RequestMapping("/submitRoomPicsTwo.do")
		@SuppressWarnings("unchecked")
		public void submitRoomPicsTwo(HttpServletRequest request, HttpServletResponse response) throws Exception {
			
			String fileName = null;
			FileOutputStream fos = null;
			InputStream is = null;
			String headPicUrl = "";
			
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile srcFile = multipartRequest.getFile("file");
			
			String branchIdChoose = multipartRequest.getParameter("branchId");
			String index = multipartRequest.getParameter("index");
			String roomType = multipartRequest.getParameter("roomType");
			
			LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
			String staffId = loginuser.getStaff().getStaffId();
			
			List<RoomPicture> branchPics = branchBasicService.findByProperties(RoomPicture.class, "branchId",
					branchIdChoose, "style", CommonConstants.RoomPicStyle.ROOMTYPE_INNEL_PIC, "status",
					CommonConstants.STATUS.NORMAL, "roomType", roomType);
			if(branchPics.size() > 0 && index.equals("0")){
				for(RoomPicture branchPic : branchPics){
					String webPath = RequestUtil.getWebPath(request);
					String path = webPath.replace("\\","/") + "/upload/";
					Picture pic=(Picture) branchBasicService.findOneByProperties(Picture.class, "pictureId", branchPic.getPictureId());
					String name = pic.getPictureUrl().substring(pic.getPictureUrl().lastIndexOf("/") + 1);
					java.io.File myFilePath = new java.io.File(path + name);
				    myFilePath.delete();
				    branchBasicService.delete(branchPic);
				    branchBasicService.delete(pic);
				}
			}
			
//			if (!StringUtil.isEmpty(branch.getBranchIp())) {
//				SysParam paramFile = (SysParam) manageService.findOneByProperties(SysParam.class, "paramType",
//						SystemConstants.ParamType.BRANCH_IP, "paramName", "FILE");
//				int priorityFile = 1;
//				List<String> fileNameList = new ArrayList<String>();
//				fileNameList.add(fileName);
//				Map<String, Object> fileNameMap = new HashMap<String, Object>();
//				fileNameMap.put("FileName", fileNameList);
//				TransferServcie.getInstance().transferFile(priorityFile, paramFile.getContent(),
//						JSONUtil.fromObject(fileNameMap).toString());
//			}
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
			
			String seqName = "SEQ_PIC_ID";
			String sequence = branchBasicService.getCloudSequence(seqName);
			SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
			String picId = sdf.format(new Date()) + sequence;
			String picStyle = CommonConstants.RoomPicStyle.ROOMTYPE_INNEL_PIC;
			String picTrueStyle = CommonConstants.PicStyle.BRANCH_INNEL_PIC;
			String status = CommonConstants.STATUS.NORMAL;
			RoomPicture rp = new RoomPicture();
			rp.setBranchId(branchIdChoose);
			rp.setPictureId(picId);
			rp.setRecordTime(new Date());
			rp.setRecordUser(staffId);
			rp.setRoomType(roomType);
			rp.setStatus(status);
			rp.setStyle(picStyle);
			branchBasicService.save(rp);

			Picture picture = new Picture();
			picture.setPictureId(picId);
			picture.setPictureStyle(picTrueStyle);
			picture.setRecordTime(new Date());
			picture.setRecordUser(staffId);
			picture.setStatus(status);
			picture.setPictureUrl(headPicUrl);
			branchBasicService.save(picture);

			JSONUtil.responseJSON(response, new AjaxResult(1, null));

		}
		// 班次页面跳转
		@RequestMapping("/turnToShiftTime.do")
		public ModelAndView turnToShiftTime(HttpServletRequest request, HttpServletResponse response) throws Exception {
			ModelAndView mv = new ModelAndView();
			mv.setViewName("/page/basic/branch/shifttime");
			LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
			String branchid = loginUser.getStaff().getBranchId();
			List<?> shiftcontent = branchBasicService.getShiftcontent(branchid);
			String systemType = null;
			if (loginUser.getStaff().getStaffName().equals("admin")) {
				systemType = "1";
			} else {
				Branch branchData = (Branch) branchBasicService.findOneByProperties(Branch.class, "branchId", branchid);
				/*SysParam sysParam = (SysParam) pmsmanageService.findOneByProperties(SysParam.class, "paramType", "SYSTEMTYPE");
				String systemType = sysParam.getContent();*/
				systemType = branchData.getRank();
			}
			request.setAttribute("adminid", SystemConstants.User.ADMIN_ID);
			request.setAttribute("staff", JSONUtil.fromObject(loginUser.getStaff()));
			request.setAttribute("shiftcontent", shiftcontent);
			request.setAttribute("systype", systemType);
			return mv;
		}
		@RequestMapping("/deleteshift.do")
		public void deleteshift(HttpServletRequest request, HttpServletResponse response,String shiftid)throws Exception{
			 LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
				String branchid = loginUser.getStaff().getBranchId();
			 ShiftTime shiftTime = (ShiftTime)branchBasicService.findOneByProperties(ShiftTime.class, "branchId", branchid, "shiftTimeId",shiftid,"status","1");
			if(null != shiftTime){
				shiftTime.setStatus("0");
				shiftTime.setRecordTime(new Date());
				try {
					branchBasicService.update(shiftTime);
						JSONUtil.responseJSON(response, new AjaxResult(0, "删除成功!"));
					} catch (Exception e) {
						e.printStackTrace();
						JSONUtil.responseJSON(response, new AjaxResult(1, "删除失败!"));
					}
			}
		}
		
		@RequestMapping("/addorupdateshift.do")
		public void addorupdateshift(HttpServletRequest request, HttpServletResponse response,String shiftarr)throws Exception{
	        Boolean flag = true;
	       
			JSONArray travelarray = new JSONArray(shiftarr);
			 LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
				String branchid = loginUser.getStaff().getBranchId();
			for (int i = 0; i < travelarray.length(); i++) {
				JSONObject shiftodj = (JSONObject) travelarray.get(i);
				String paramname = URLDecoder.decode(shiftodj.getString("paramname"), "UTF-8");
				String starttime = URLDecoder.decode(shiftodj.getString("starttime"), "UTF-8");
				String endtime = URLDecoder.decode(shiftodj.getString("endtime"), "UTF-8");
				String shiftid = URLDecoder.decode(shiftodj.getString("shiftid"), "UTF-8");
				if(!StringUtil.isEmpty(shiftid)){
					//修改
					 
					 ShiftTime shiftTime = (ShiftTime)branchBasicService.findOneByProperties(ShiftTime.class, "branchId", branchid, "shiftTimeId",shiftid,"status","1");
					//SysParam sysparam	=(SysParam)shiftService.findOneByProperties(SysParam.class,"orderNo",bigshiftid,"paramType","SHIFTTIME" );
					if(null!=shiftTime){
						try{
							shiftTime.setEndtime(endtime);
							shiftTime.setStarttime(starttime);
							shiftTime.setShiftname(paramname);
							shiftTime.setRecordTime(new Date());
							branchBasicService.update(shiftTime);
						
						}catch(Exception e){
							e.printStackTrace();
							flag = false;
							break;
						}
					}
				}else{
					//新增
					//List<?> maxorderno =shiftService.findBySQL("findmaxorderno", true);
					//if(null!=maxorderno){
						try{
							ShiftTime shiftTime = new ShiftTime();
						Date date = new Date();
						SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
						String time = sdf.format(date);
						String sequences = branchBasicService.getCloudSequence("SEQ_SYSPARAM_ORDERNO_ID");
						//String orderno = shiftService.getCloudSequence("SEQ_SYSPARAM_ORDERNO");
						shiftTime.setShiftTimeId(time+branchid+sequences);
						shiftTime.setBranchId(branchid);
						shiftTime.setEndtime(endtime);
						shiftTime.setStarttime(starttime);
						shiftTime.setShiftname(paramname);
						//shiftTime.setOrderno(orderno);
						shiftTime.setStatus("1");
						shiftTime.setRecordTime(new Date());
						branchBasicService.save(shiftTime);
					}catch(Exception e){
						e.printStackTrace();
						flag = false;
						break;
					}
					//}
				}
			}
				
				if(flag){
					JSONUtil.responseJSON(response, new AjaxResult(0, "操作成功!"));
				}else{
					
					JSONUtil.responseJSON(response, new AjaxResult(0, "操作失败!"));
				}
			
		}
		
		@RequestMapping("/delCashBox.do")
		public void delCashBox (HttpServletRequest request, HttpServletResponse response, String dataId){
			LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
			Branch branch = (Branch) branchBasicService.findById(Branch.class, loginuser.getStaff().getBranchId());
			String[] data = dataId.split(",");
			if(branch.getRank().equals(CommonConstants.BranchRank.TOP)){
				for (int i = 0; i < data.length; i++) {
					CashBox cashBox = (CashBox) branchBasicService.findById(CashBox.class, data[i]);
					cashBox.setStatus("0");
					branchBasicService.update(cashBox);
				}
				JSONUtil.responseJSON(response, new AjaxResult(1,"删除成功"));
			} else {
				JSONUtil.responseJSON(response, new AjaxResult(1,"没有权限删除"));
			}
		}
		
		/**
		 * 批量删除价格规则信息
		 * @param request 请求数据
		 * @param response 响应数据
		 * @param rulesId 价格规则Id
		 */
		@RequestMapping("/delPriceRules.do")
		public void delPriceRules(HttpServletRequest request, HttpServletResponse response, String rulesId) {
			// 验证回传数据是否为空或为null
			if (!"".equals(rulesId) && rulesId != null) {
				String[] rulesIds = rulesId.split(",");
				for (int i = 0; i < rulesIds.length; i++) {
					if (rulesIds[i] != null) {
						PriceRules priceRules = ((PriceRules) (branchBasicService.findOneByProperties(PriceRules.class,
								"rulesId", rulesIds[i])));
						branchBasicService.delete(priceRules);
					}
				}
				JSONUtil.responseJSON(response, new AjaxResult(1, null));
			} else {
				JSONUtil.responseJSON(response, new AjaxResult(-1, null));
			}
		}
		
		/* 获得汉语拼音首字母
	     *
	     * @param chines 汉字
	     * @return
	     */
	    public static String getAlpha(String chines) {
	        String pinyinName = "";
	        char[] nameChar = chines.toCharArray();
	        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
	        defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
	        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
	        for (int i = 0; i < nameChar.length; i++) {
	            if (nameChar[i] > 128) {
	                try {
	                    pinyinName += PinyinHelper.toHanyuPinyinStringArray(
	                            nameChar[i], defaultFormat)[0].charAt(0);
	                } catch (BadHanyuPinyinOutputFormatCombination e) {
	                    e.printStackTrace();
	                }
	            } else {
	                pinyinName += nameChar[i];
	            }
	        }
	        return pinyinName;
	    }

	    @SuppressWarnings("unused")
		@RequestMapping("/addBranchHeadPic.do")
		public void addBranchHeadPic(HttpServletRequest request, HttpServletResponse response, String branchId, String scence) throws Exception {
			
			String fileName = null;
			FileOutputStream fos = null;
			InputStream is = null;
			String headPicUrl = "";
			String picStyle = null;
			String picTrueStyle = null;
			
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile srcFile = multipartRequest.getFile("file");
			
			
			LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
			String staffId = loginuser.getStaff().getStaffId();
			//头图删除其已有的数据
			if(scence.equals("0")){
				@SuppressWarnings("unchecked")
				List<BranchPicture> branchPics = branchBasicService.findByProperties(BranchPicture.class, "branchId",
						branchId, "style", CommonConstants.BranchPicStyle.BRANCH_HEAD_PIC, "status",
						CommonConstants.STATUS.NORMAL);
				if(branchPics.size() > 0){
					for(BranchPicture branchPic : branchPics){
						String webPath = RequestUtil.getWebPath(request);
						String path = webPath.replace("\\","/") + "/upload/";
						Picture pic=(Picture) branchBasicService.findOneByProperties(Picture.class, "pictureId", branchPic.getPictureId());
						String name = pic.getPictureUrl().substring(pic.getPictureUrl().lastIndexOf("/") + 1);
						java.io.File myFilePath = new java.io.File(path + name);
					    myFilePath.delete();
					    branchBasicService.delete(branchPic);
					    branchBasicService.delete(pic);
					}
				}
			}

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

			if(scence.equals("0")){
				picStyle = CommonConstants.BranchPicStyle.BRANCH_HEAD_PIC;
				picTrueStyle = CommonConstants.PicStyle.BRANCH_HEAD_PIC;
			} else if(scence.equals("1")){
				picStyle = CommonConstants.PicStyle.BRANCH_BEDROOM_PIC;
				picTrueStyle = CommonConstants.PicStyle.BRANCH_BEDROOM_PIC;
			} else if(scence.equals("2")){
				picStyle = CommonConstants.PicStyle.BRANCH_LIVEROOM_PIC;
				picTrueStyle = CommonConstants.PicStyle.BRANCH_LIVEROOM_PIC;
			} else if(scence.equals("3")){
				picStyle = CommonConstants.PicStyle.BRANCH_RECLINING_PIC;
				picTrueStyle = CommonConstants.PicStyle.BRANCH_RECLINING_PIC;
			} else if(scence.equals("4")){
				picStyle = CommonConstants.PicStyle.BRANCH_KITCHEN_PIC;
				picTrueStyle = CommonConstants.PicStyle.BRANCH_KITCHEN_PIC;
			} else if(scence.equals("5")){
				picStyle = CommonConstants.PicStyle.BRANCH_TOILET_PIC;
				picTrueStyle = CommonConstants.PicStyle.BRANCH_TOILET_PIC;
			}
			
			
			String sequence = branchBasicService.getSequence("SEQ_PIC_ID");
			SimpleDateFormat sdf = new SimpleDateFormat("yymmdd");
			String picId = sdf.format(new Date()) + sequence;
			
			String status = CommonConstants.STATUS.NORMAL;
			BranchPicture bp = new BranchPicture();
			bp.setBranchId(branchId);
			bp.setPictureId(picId);
			bp.setRecordTime(new Date());
			bp.setRecordUser(staffId);
			bp.setStatus(status);
			bp.setStyle(picStyle);
			branchBasicService.save(bp);
			
			Picture picture = new Picture();
			picture.setPictureId(picId);
			picture.setPictureStyle(picTrueStyle);
			picture.setRecordTime(new Date());
			picture.setRecordUser(staffId);
			picture.setStatus(status);
			picture.setPictureUrl(headPicUrl);
			branchBasicService.save(picture);
			
			JSONUtil.responseJSON(response, new AjaxResult(1, null));
		}
	    
	    
	    /**
	     * 
	     * @Description
	     * @author ideas_mengl
	     * @date   2018年8月3日上午11:48:17
	     * @param request
	     * @param response
	     * @return
	     */
	    @RequestMapping("/addTipsCRM.do")
	   public ModelAndView addTips(HttpServletRequest request, HttpServletResponse response){
	    	
		   ModelAndView mv = new ModelAndView();
		   mv.setViewName("page/crm/parameter/addTips");
		   return mv;
	   }
	    
	    @RequestMapping("/addTipsContent.do")
	    public void addTipsContent(HttpServletRequest request, HttpServletResponse response){
	    	
	    	try {
				String content = request.getParameter("content");
				String orderNo = branchBasicService.getSequence("SEQ_NEWTIPS_ID");
				String paramId = branchBasicService.getSequence("SEQ_SYSPARAM_ID");
				SysParam param = new SysParam();
				param.setParamId(paramId);
			/*	param.setParamType(CommonConstants.tips.PARAM_TYPE);
				param.setParamName(CommonConstants.tips.PARAM_NAME);
				param.setContent(content);
				param.setStatus(CommonConstants.tips.STATUS_VALID);*/
				param.setOrderNo(orderNo);
				branchBasicService.save(param);
				JSONUtil.responseJSON(response, new AjaxResult(1, "保存成功!"));
			} catch (Exception e) {
				e.printStackTrace();
				JSONUtil.responseJSON(response, new AjaxResult(0,"操作失败,稍后再试!"));
			}
	    }
	    
	    @SuppressWarnings("unused")
		@RequestMapping("/addRoomTypePicNew.do")
		public void addRoomTypePicNew(HttpServletRequest request, HttpServletResponse response, String branchId, String scence, String roomType) throws Exception {
			
			/*String fileName = null;
			FileOutputStream fos = null;
			InputStream is = null;
			String headPicUrl = "";
			String picStyle = null;
			String picTrueStyle = null;
			
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile srcFile = multipartRequest.getFile("file");
			
			
			LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
			String staffId = loginuser.getStaff().getStaffId();
			//头图删除其已有的数据
			if(scence.equals("0")){
				@SuppressWarnings("unchecked")
				List<BranchPicture> branchPics = branchBasicService.findByProperties(BranchPicture.class, "branchId",
						branchId, "style", CommonConstants.BranchPicStyle.BRANCH_HEAD_PIC, "status",
						CommonConstants.STATUS.NORMAL);
				if(branchPics.size() > 0){
					for(BranchPicture branchPic : branchPics){
						String webPath = RequestUtil.getWebPath(request);
						String path = webPath.replace("\\","/") + "/upload/";
						Picture pic=(Picture) branchBasicService.findOneByProperties(Picture.class, "pictureId", branchPic.getPictureId());
						String name = pic.getPictureUrl().substring(pic.getPictureUrl().lastIndexOf("/") + 1);
						java.io.File myFilePath = new java.io.File(path + name);
					    myFilePath.delete();
					    branchBasicService.delete(branchPic);
					    branchBasicService.delete(pic);
					}
				}
			}

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

			if(scence.equals("0")){
				picStyle = CommonConstants.BranchPicStyle.BRANCH_HEAD_PIC;
				picTrueStyle = CommonConstants.PicStyle.BRANCH_HEAD_PIC;
			} else if(scence.equals("1")){
				picStyle = CommonConstants.PicStyle.BRANCH_BEDROOM_PIC;
				picTrueStyle = CommonConstants.PicStyle.BRANCH_BEDROOM_PIC;
			} else if(scence.equals("2")){
				picStyle = CommonConstants.PicStyle.BRANCH_LIVEROOM_PIC;
				picTrueStyle = CommonConstants.PicStyle.BRANCH_LIVEROOM_PIC;
			} else if(scence.equals("3")){
				picStyle = CommonConstants.PicStyle.BRANCH_RECLINING_PIC;
				picTrueStyle = CommonConstants.PicStyle.BRANCH_RECLINING_PIC;
			} else if(scence.equals("4")){
				picStyle = CommonConstants.PicStyle.BRANCH_KITCHEN_PIC;
				picTrueStyle = CommonConstants.PicStyle.BRANCH_KITCHEN_PIC;
			} else if(scence.equals("5")){
				picStyle = CommonConstants.PicStyle.BRANCH_TOILET_PIC;
				picTrueStyle = CommonConstants.PicStyle.BRANCH_TOILET_PIC;
			}
			
			
			String sequence = branchBasicService.getSequence("SEQ_PIC_ID");
			SimpleDateFormat sdf = new SimpleDateFormat("yymmdd");
			String picId = sdf.format(new Date()) + sequence;
			
			String status = CommonConstants.STATUS.NORMAL;
			BranchPicture bp = new BranchPicture();
			bp.setBranchId(branchId);
			bp.setPictureId(picId);
			bp.setRecordTime(new Date());
			bp.setRecordUser(staffId);
			bp.setStatus(status);
			bp.setStyle(picStyle);
			branchBasicService.save(bp);
			
			Picture picture = new Picture();
			picture.setPictureId(picId);
			picture.setPictureStyle(picTrueStyle);
			picture.setRecordTime(new Date());
			picture.setRecordUser(staffId);
			picture.setStatus(status);
			picture.setPictureUrl(headPicUrl);
			branchBasicService.save(picture);
			
			JSONUtil.responseJSON(response, new AjaxResult(1, null));*/
	    	
	    	String fileName = null;
			FileOutputStream fos = null;
			InputStream is = null;
			String headPicUrl = "";
			String picStyle = null;
			String picTrueStyle = null;
			
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile srcFile = multipartRequest.getFile("file");
			//String roomType = multipartRequest.getParameter("roomType");
			//String branchIdChoose = multipartRequest.getParameter("branchId");
			
			LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
			String staffId = loginuser.getStaff().getStaffId();
			if(scence.equals("0")){
				RoomPicture branchPic = (RoomPicture) branchBasicService.findOneByProperties(RoomPicture.class, "branchId",
						branchId, "style", CommonConstants.RoomPicStyle.ROOMTYPE_HEAD_PIC, "status", CommonConstants.STATUS.NORMAL, "roomType", roomType);
				if (branchPic != null) {
					branchBasicService.delete(branchPic);
					String picId=branchPic.getPictureId();
					Picture pic=(Picture) branchBasicService.findOneByProperties(Picture.class, "pictureId", picId);
					if(pic != null){
						String webPath = RequestUtil.getWebPath(request);
						String path = webPath.replace("\\","/") + "/upload/";
						String name = pic.getPictureUrl().substring(pic.getPictureUrl().lastIndexOf("/") + 1);
						java.io.File myFilePath = new java.io.File(path + name);
					    myFilePath.delete();
					    branchBasicService.delete(pic);
					}
				}
			}
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
			
			
			if(scence.equals("0")){
				picStyle = CommonConstants.RoomPicStyle.ROOMTYPE_HEAD_PIC;
				picTrueStyle = CommonConstants.PicStyle.BRANCH_HEAD_PIC;
			} else if(scence.equals("1")){
				picStyle = CommonConstants.RoomPicStyle.ROOMTYPE_INNEL_PIC;
				picTrueStyle = CommonConstants.PicStyle.BRANCH_INNEL_PIC;
			} 
			// 门店头图数据新增
			String seqName = "SEQ_PIC_ID";
			String sequence = branchBasicService.getSequence(seqName);
			SimpleDateFormat sdf = new SimpleDateFormat("yymmdd");
			String picId = sdf.format(new Date()) + sequence;
			String status = CommonConstants.STATUS.NORMAL;
			RoomPicture rp = new RoomPicture();
			rp.setBranchId(branchId);
			rp.setPictureId(picId);
			rp.setRecordTime(new Date());
			rp.setRecordUser(staffId);
			rp.setRoomType(roomType);
			rp.setStatus(status);
			rp.setStyle(picStyle);
			branchBasicService.save(rp);
			
			Picture picture = new Picture();
			picture.setPictureId(picId);
			picture.setPictureStyle(picTrueStyle);
			picture.setRecordTime(new Date());
			picture.setRecordUser(staffId);
			picture.setStatus(status);
			picture.setPictureUrl(headPicUrl);
			branchBasicService.save(picture);
			
			JSONUtil.responseJSON(response, new AjaxResult(1, null));
		}
	    
	    
	    @RequestMapping("/delPicsInRoomType.do")
		public void delPicsInRoomType(HttpServletRequest request, HttpServletResponse response, String pictureId){
	    	RoomPicture roomPic = (RoomPicture) branchBasicService.findById(RoomPicture.class, pictureId);
			if (roomPic != null) {
				String picId=roomPic.getPictureId();
				Picture pic=(Picture) branchBasicService.findOneByProperties(Picture.class, "pictureId", picId);
				if(pic != null){
					String webPath = RequestUtil.getWebPath(request);
					String path = webPath.replace("\\","/") + "/upload/";
					String name = pic.getPictureUrl().substring(pic.getPictureUrl().lastIndexOf("/") + 1);
					java.io.File myFilePath = new java.io.File(path + name);
				    myFilePath.delete();
				    branchBasicService.delete(pic);
				}
				branchBasicService.delete(roomPic);
			}
		}
	    
}
