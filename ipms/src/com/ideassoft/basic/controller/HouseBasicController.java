package com.ideassoft.basic.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hsqldb.lib.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ideassoft.basic.service.HouseBasicService;
import com.ideassoft.bean.Branch;
import com.ideassoft.bean.BranchKeywords;
import com.ideassoft.bean.BranchPicture;
import com.ideassoft.bean.City;
import com.ideassoft.bean.Goods;
import com.ideassoft.bean.House;
import com.ideassoft.bean.Location;
import com.ideassoft.bean.Picture;
import com.ideassoft.bean.PriceVolatility;
import com.ideassoft.bean.RoomType;
import com.ideassoft.bean.Staff;
import com.ideassoft.bean.SysParam;
import com.ideassoft.core.ajax.AjaxResult;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.constants.CommonConstants;
import com.ideassoft.core.constants.SystemConstants;
import com.ideassoft.crm.controller.ManageController;
import com.ideassoft.priceRuleUtile.BasePriceUtile;
import com.ideassoft.util.CloneUtil;
import com.ideassoft.util.JSONUtil;
import com.ideassoft.util.MD5Util;
import com.ideassoft.util.RequestUtil;

@Transactional
@Controller
public class HouseBasicController {
	@Autowired
	private HouseBasicService houseBasicService;
	
	private static Logger log = Logger.getLogger(ManageController.class);
	
	private static Integer cnt = 1;
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/copyHouse.do")
	public void copyHouse(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String houseIds = request.getParameter("houseId");
		String [] houseIdArr = houseIds.split(",");
		House newhouse = new House();
		BranchPicture nbp_clone = new BranchPicture();
		
		Picture pic_clone = new Picture();
		
		PriceVolatility pv_clone = new PriceVolatility();

		BranchKeywords bky_clone = new BranchKeywords();
		
		Location loc_clone = new Location();
		
		String newhouseid ="";

		

		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		
		try {
			for (String houseId : houseIdArr ){
				 newhouseid ="H"+houseBasicService.getSequence("SEQ_NEW_HOUSEID");
        		//复制house
				House house = (House)houseBasicService.findOneByProperties(House.class, "houseId", houseId);
				if(house != null){
					
					 newhouse = (House)CloneUtil.deepClone(house);
					 newhouse.setHouseId(newhouseid);
					 houseBasicService.save(newhouse);
				}
				//复制branchpicture
				List<BranchPicture> newbp = houseBasicService.findByProperties(BranchPicture.class, "branchId", houseId,"status" ,"1");
				if(newbp.size() > 0){
					for(BranchPicture nbp : newbp){
						String sequence = houseBasicService.getCloudSequence("SEQ_PIC_ID");
						
						String picId = sdf.format(new Date()) + sequence;
						nbp_clone = (BranchPicture)CloneUtil.deepClone(nbp);
						nbp_clone.setPictureId(picId);
						nbp_clone.setBranchId(newhouseid);
						houseBasicService.save(nbp_clone);
						//复制picture
						Picture newpic = (Picture)houseBasicService.findOneByProperties(Picture.class, "pictureId", nbp.getPictureId(),"status" ,"1");
						
						pic_clone = (Picture)CloneUtil.deepClone(newpic);
						pic_clone.setPictureId(picId);
						houseBasicService.save(pic_clone);
					}
				}
				//复制价格
				List<PriceVolatility> newpv = houseBasicService.findByProperties(PriceVolatility.class, "branchId", houseId);
				if(newpv.size() > 0){
					for(PriceVolatility npv : newpv){
						
						String seq = houseBasicService.getSequence("SEQ_NEW_VOLATILITY");

						pv_clone = (PriceVolatility)CloneUtil.deepClone(npv);
						pv_clone.setBranchId(newhouseid);
						pv_clone.setVolatilityId(seq);
						houseBasicService.save(pv_clone);

					}
				}
				
				
				//复制关键字
				String dateString = sdf.format(new Date());
				BranchKeywords bkw =(BranchKeywords) houseBasicService.findOneByProperties(BranchKeywords.class, "branchId", houseId,"status","1");
                if(bkw !=null){
                	bky_clone = (BranchKeywords)CloneUtil.deepClone(bkw);
                	bky_clone.setBranchId(newhouseid);
                	bky_clone.setKeywordsId(dateString+houseId+houseBasicService.getSequence("SEQ_BRANCHKEYWORDS_ID"));
                	houseBasicService.save(bky_clone);
                }
				//复制location
                
        		Location location =(Location) houseBasicService.findOneByProperties(Location.class, "branchId", houseId);
        		if(bkw !=null){
        			loc_clone = (Location)CloneUtil.deepClone(location);
        			loc_clone.setBranchId(newhouseid);
                	houseBasicService.save(loc_clone);
                }

			}
			JSONUtil.responseJSON(response, new AjaxResult(1, "操作成功！"));
		} catch (Exception e) {
			JSONUtil.responseJSON(response, new AjaxResult(0, "操作失败！"));
		}
		
	}
	//跳转新增民宿页面
	@SuppressWarnings("unchecked")
	@RequestMapping("/addHouse.do")
	public ModelAndView addHouse() throws Exception {
		ModelAndView mv = new ModelAndView("page/basic/house/addHouse");
		List<SysParam> beddescList = houseBasicService.findByProperties(SysParam.class,"paramType","BEDDESC");
		List<SysParam> positionList =  houseBasicService.findByProperties(SysParam.class,"paramType","ROOMPOSITION");
		List<SysParam> broadbandList = houseBasicService.findByProperties(SysParam.class,"paramType","BROADBAND");
		List<City> cityList = houseBasicService.queryCity();
		
		//List<?> cityInfo = houseBasicService.findByProperties(City.class, "rank", "1", "status", "1");
		List<Map<String, Object>> newCity = new ArrayList<Map<String, Object>>();
		
		for (int i = 0; i < cityList.size(); i++) {
			Map<String, Object> el = new HashMap<String, Object>();
			String pinYin = getAlpha(cityList.get(i).getAdminiName());
			el.put("py", pinYin);
			el.put("name", cityList.get(i).getAdminiName());
			el.put("code", cityList.get(i).getAdminiCode());
			newCity.add(el);
		}
		
		List<City> districtList = null;
		List<City> streetList = null;
		/*List<City> circletList = null;
		List<City> schoolList = null;//校区
		List<City> scenicList = null;//景点
		List<City> subwayList = null;//地铁站
*/
		List<?> rpstatus = houseBasicService.getStatus();
		List<?> themeList = houseBasicService.getThemeList();
		List<?> houseDeviceList = houseBasicService.getHouseDevice();
		List<?> tips = houseBasicService.getTips();
		String sql = "queryPlace";
		String cityAdminCode = cityList.get(0).getAdminiCode();
		String citySubCode = cityAdminCode.substring(0, 4) + "%";
		districtList = houseBasicService.findBySQL(sql, new String[] { citySubCode, CommonConstants.CityRank.DISTRICT },
				true);
		
        if(districtList != null && !districtList.isEmpty()){
    		String districtAdminCode = ((Map<?, ?>) districtList.get(0)).get("ADMINI_CODE").toString();
    		String districtSubCode = districtAdminCode.substring(0, 6) + "%";
    		streetList = houseBasicService.findBySQL(sql, new String[] { districtSubCode, CommonConstants.CityRank.STREET },
    				true);
    		/*
			String circleSubCode = districtSubCode;
    		circletList = manageService.findBySQL(sql, new String[] { circleSubCode, CommonConstants.CityRank.CIRCLE },
    				true);
    		//校区
    		schoolList = manageService.findBySQL(sql, new String[] { circleSubCode, CommonConstants.CityRank.SCHOOL },
    				true);
    		//景点
    		scenicList = manageService.findBySQL(sql, new String[] { circleSubCode, CommonConstants.CityRank.SCENIC },
    				true);
    		//地铁站
    		subwayList = manageService.findBySQL(sql, new String[] { circleSubCode, CommonConstants.CityRank.SUBWAY },
    				true);*/
    		
        }

		
		List<?> specialSerivce = houseBasicService.findByProperties(Goods.class, "categoryId", "00000001");

		
		mv.addObject("tips", tips);
		mv.addObject("houseDeviceList", houseDeviceList);
		mv.addObject("themeList", themeList);
		mv.addObject("beddescList", beddescList);
		mv.addObject("positionList", positionList);
		mv.addObject("broadbandList", broadbandList);
		mv.addObject("specialSerivce", specialSerivce);
		mv.addObject("cityList", cityList);
		mv.addObject("districtList", districtList);//区
		mv.addObject("streetList", streetList);
		mv.addObject("newCity", JSONUtil.fromObject(newCity));
		/*mv.addObject("circletList", circletList);
		mv.addObject("schoolList", schoolList);
		mv.addObject("scenicList", scenicList);
		mv.addObject("subwayList", subwayList);*/
		mv.addObject("rpstatus", rpstatus);
		return mv;
	}
	
	//新增民宿
	@SuppressWarnings("unchecked")
	@RequestMapping("/saveHouse.do")
	public void saveHouse(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String housename = request.getParameter("housename");
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String staffId = loginuser.getStaff().getStaffId();
		String houseid = "H"+houseBasicService.getSequence("SEQ_NEW_HOUSEID");
		String staffid  = request.getParameter("staffid");
		//String roomtype  = request.getParameter("roomtype");
		//String  theme  = request.getParameter("theme");
		String roomName = request.getParameter("roomName");
		String longitude = request.getParameter("longitude");
		String latitude = request.getParameter("latitude");
		String houseNo = request.getParameter("houseNo");
		String floor = request.getParameter("floor");
		String beds = request.getParameter("beds");
		String bedDesc = request.getParameter("bedDesc");
		String position  = request.getParameter("position");
		String broadband = request.getParameter("broadband");
		String originalprice = request.getParameter("originalprice");
		String cleanprice = request.getParameter("cleanprice");
		String cashpledge = request.getParameter("cashpledge");
		if (cashpledge.equals("") || cashpledge == null) {
			cashpledge = "0";
		}
		String communityName = request.getParameter("communityName");
		String decStyle = request.getParameter("decStyle");
		String preLocation = request.getParameter("preLocation");
		/*String  aftLocation  = request.getParameter("aftLocation");*/
		String cityCode  = request.getParameter("cityCode");
		String district  = request.getParameter("district");
		String street  = request.getParameter("street");
		String property  = request.getParameter("property");
		String subway  = request.getParameter("subway");
		/*String circle = request.getParameter("circle");*/
		String remarkInput = request.getParameter("remarkInput");
		String area  = request.getParameter("area");
		String status  = request.getParameter("status");
		String address = preLocation; /*+ "-" + aftLocation +"号"*/
		String latlng = request.getParameter("latlng");
		String keyword = request.getParameter("keyword");
		String flag = request.getParameter("flag");
		String device = request.getParameter("device");
		String tip = request.getParameter("tip");
		String services = request.getParameter("services");
		String housedesc = request.getParameter("housedesc");
		String recommend = request.getParameter("recommend");
		String orderNo = request.getParameter("orderNo");
		String tipString = "";
		String deviceString = "";
		String deviceSign = "";
		String tipSign = "";
		List<?> houseDeviceList = houseBasicService.getHouseDevice();
		List<?> tips = houseBasicService.getTips();
		List<?> houseCheck = houseBasicService.findByProperties(House.class,"housename",housename);

		if(houseCheck.size()>0){
			JSONUtil.responseJSON(response, new AjaxResult(0, "民宿名称已存在！"));
		}else{
			
			if(!StringUtils.isEmpty(device) ){
				if (device.endsWith(",")) {
					deviceString = device.substring(0, device.length() - 1);
				} else {
					deviceString = device;
				}
				
				String deviceArray[] = deviceString.split(",");
				boolean deviceflag = true;
				for (int i = 0; i < houseDeviceList.size(); i++) {
					for (int j = 0; j < deviceArray.length; j++) {
						if (((Map<?, ?>) houseDeviceList.get(i)).get("PARAM_NAME").toString().equals(deviceArray[j])) {
							deviceSign += "1";
							deviceflag = false;
						}
					}
					if (deviceflag) {
						deviceSign += "0";
					}
					deviceflag = true;
				}
				
				
			}
			if(!StringUtils.isEmpty(tip)){
				if (tip.endsWith(",")) {
					tipString = tip.substring(0, tip.length() - 1);
				} else {
					tipString = tip;
				}
				String tipArray[] = tipString.split(",");
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

			}	
			try{
			// 当系统初始化时将所有房间价的基准价格save到房价汇总表中
			BasePriceUtile.addBasePrice(houseid, "3", originalprice);
			SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
			String dateString = sdf.format(new Date());
			BranchKeywords bky = new BranchKeywords();
			bky.setBranchId(houseid);
			bky.setKeywords(keyword);
			bky.setKeywordsId(dateString+houseid+houseBasicService.getSequence("SEQ_BRANCHKEYWORDS_ID"));
			bky.setRecordTime(new Date());
			bky.setRecordUser(staffId);
			bky.setRemark("");
			bky.setStatus(CommonConstants.STATUS.NORMAL);
			
			houseBasicService.save(bky);
			
			if(StringUtils.isEmpty(longitude) && StringUtils.isEmpty(latitude)){
				String lat[] = latlng.split(",");
				 longitude = lat[1];
				 latitude = lat[0];
			}
			
			Location loc = new Location();
			loc.setBranchId(houseid);
			loc.setLatitude(latitude.trim());
			loc.setLongitude(longitude.trim());
			loc.setRecordTime(new Date());
			loc.setRecordUser(staffId);
			loc.setStatus(CommonConstants.STATUS.NORMAL);
			
			houseBasicService.save(loc);
			
			House house = new House();
			house.setHouseId(houseid);
			house.setHousename(housename);
			house.setHouseNo(houseNo);
			house.setFloor(floor);
			house.setFlag(flag);
			house.setBeds(new Byte(beds));
			house.setBedDesc(bedDesc);
			house.setPosition(position);
			house.setBroadband(broadband);
			house.setInitprice(new Double(originalprice));
//			house.setCurrentprice(new Double(0));
			house.setCleanprice(new Double(cleanprice));
			house.setCashPledge(new Double(cashpledge));
			house.setCommunityName(communityName);
			house.setDecStyle(decStyle);
			house.setAddress(address);
			house.setCity(cityCode);
			house.setDistrict(district);
			house.setOrderNo(orderNo);
			if(CommonConstants.CityRank.STREET.equals(property)){
				house.setStreet(street);
			}else if(CommonConstants.CityRank.CIRCLE.equals(property)){
				house.setCircle(street);
			}else if(CommonConstants.CityRank.SCHOOL.equals(property)){
				house.setSchool(street);
			}else if(CommonConstants.CityRank.SCENIC.equals(property)){
				house.setScenic(street);
			}else if(CommonConstants.CityRank.SUBWAYLINE.equals(property)){
				house.setSubway(subway);
			}
			
			
			/*house.setCircle(circle);*/
			house.setRemark(remarkInput);
			house.setArea(new Short(area));
			house.setStatus(status);
			house.setRecordUser(staffId);
			house.setStaffId(staffid);
			house.setHouseType(roomName);
			house.setTips(tipSign);
			house.setServices(services);
			house.setLabel(deviceSign);
			house.setHouseDesc(housedesc);
			house.setIsRecommend(recommend);
			houseBasicService.save(house);
			houseBasicService.houseAddLog(loginuser, "0",request);
			JSONUtil.responseJSON(response, new AjaxResult(1, houseid));
		} catch (Exception e) {
			e.printStackTrace();
			houseBasicService.houseAddLog(loginuser, "1",request);
			JSONUtil.responseJSON(response, new AjaxResult(0, "录入失败！"));
		}
			
			
		}

		
	
	
	}
	//跳转民宿编辑页面
		@SuppressWarnings("unchecked")
		@RequestMapping("/editHouse.do")
		public ModelAndView editHouse(HttpServletRequest request, HttpServletResponse response) throws Exception {
				ModelAndView mv = new ModelAndView("page/basic/house/editHouse");
				String houseid = request.getParameter("houseid");
				LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
				String branchid = loginuser.getStaff().getBranchId();
				List<?> tips = houseBasicService.getTips();
				List<?> houseDeviceList = houseBasicService.getHouseDevice();
				House house = (House)houseBasicService.findOneByProperties(House.class,"houseId",houseid);
				//Staff staff = (Staff) manageService.findOneByProperties(Staff.class, "staffId", house.getStaffId());
				//String staffname = staff.getStaffName();
				String staffname = ((Map<?,?>)(houseBasicService.findHouseManagerName(houseid).get(0))).get("manager").toString();
				String lable = house.getLabel();
				String tip = house.getTips();
				String flag = house.getFlag();
				String services = house.getServices();
				
				String tipString = "";
				String deviceString = "";
				String  keyword = "";
				if(tip!=null){
					if(tip.length()<tips.size()&&!StringUtils.isEmpty(tip)){
						char tipArray[] = tip.toCharArray();
						
						for (int j = 0; j < tipArray.length; j++) {
							if ('1' == tipArray[j]){
								tipString += (j == (tipArray.length - 1) ? (((Map<?, ?>) tips.get(j)).get("CONTENT").toString())
										: ((Map<?, ?>) tips.get(j)).get("CONTENT").toString() + ",");
							}
						}
					}else if(tip.length()>=tips.size()&&!StringUtils.isEmpty(tip)){
						char tipArray[] = tip.toCharArray();
						
						for (int j = 0; j < tips.size(); j++) {
							if ('1' == tipArray[j]){
								tipString += (j == (tips.size() - 1) ? (((Map<?, ?>) tips.get(j)).get("CONTENT").toString())
										: ((Map<?, ?>) tips.get(j)).get("CONTENT").toString() + ",");
							}
						}
					}
				}
				
				/*if(!StringUtils.isEmpty(tip)){
					char tipArray[] = tip.toCharArray();
					
					for (int j = 0; j < tipArray.length; j++) {
						if ('1' == tipArray[j]){
							tipString += (j == (tipArray.length - 1) ? (((Map<?, ?>) tips.get(j)).get("CONTENT").toString())
									: ((Map<?, ?>) tips.get(j)).get("CONTENT").toString() + ",");
						}
					}
					
				}*/
				if(tipString.endsWith(",")){
					tipString = tipString.substring(0,tipString.lastIndexOf(","));
					
				}
				if(!StringUtils.isEmpty(lable)){
					char deviceArray[] = lable.toCharArray();
					for (int j = 0; j < deviceArray.length; j++) {
						if (deviceArray[j] == '1') {
							deviceString += (j == (deviceArray.length - 1) ? ((((Map<?, ?>) houseDeviceList.get(j)).get("PARAM_NAME"))
									.toString()) : ((Map<?, ?>) houseDeviceList.get(j)).get("PARAM_NAME").toString() + ",");
						}
					}
					
				}
				if(deviceString.endsWith(",")){
					deviceString = deviceString.substring(0,deviceString.lastIndexOf(","));
				}
				String serviceName = "";
				if(!StringUtils.isEmpty(services)){
					String[] arrservice = services.split(",");
					for (String string : arrservice) {
						Goods good = (Goods) houseBasicService.findOneByProperties(Goods.class, "goodsId", string);
						if(good != null){
							serviceName = serviceName + good.getGoodsName() + ",";						
						}
					}
					serviceName = serviceName.substring(0, serviceName.length() - 1);
				}
				
				List<City> cityList = houseBasicService.queryCity();
				
				//List<?> cityInfo = houseBasicService.findByProperties(City.class, "rank", "1", "status", "1");
				List<Map<String, Object>> newCity = new ArrayList<Map<String, Object>>();
				
				for (int i = 0; i < cityList.size(); i++) {
					Map<String, Object> el = new HashMap<String, Object>();
					String pinYin = getAlpha(cityList.get(i).getAdminiName());
					el.put("py", pinYin);
					el.put("name", cityList.get(i).getAdminiName());
					el.put("code", cityList.get(i).getAdminiCode());
					newCity.add(el);
				}
				
				List<SysParam> positionList =  houseBasicService.findByProperties(SysParam.class,"paramType","ROOMPOSITION");
				List<SysParam> broadbandList = houseBasicService.findByProperties(SysParam.class,"paramType","BROADBAND");
				List<RoomType> roomtypeList = (List<RoomType>) houseBasicService.queryAllOfRoomType(houseid);
				List<SysParam> beddescList = houseBasicService.findByProperties(SysParam.class,"paramType","BEDDESC");
				List<?> rpstatus = houseBasicService.getStatus();
				String sql = "queryPlace";
				//找出该民宿所属的城市
				String cityAdminCode = house.getCity();
				City city = (City) houseBasicService.findOneByProperties(City.class, "adminiCode", cityAdminCode, "status", "1");
				String cityAdminName = city.getAdminiName();
				//找出该城市下的区
				String citySubCode = cityAdminCode.substring(0, 4) + "%";
				List<City> districtList = houseBasicService.findBySQL(sql, new String[] { citySubCode, CommonConstants.CityRank.DISTRICT },true);
				
				//找出该民宿所属的区		
				String disAdminCode = house.getDistrict();
				String property = CommonConstants.CityRank.STREET;//默认为3，街道
				String strAdminCode = house.getStreet();
				String normalCode = strAdminCode;
				List<City> streetList = null;//(街道,商业圈，学校，景点都用这个)
				if(strAdminCode == null){
					//街道没有就去看看商业圈
					String cirAdminCode = house.getCircle();
					if(cirAdminCode == null){
						//商业圈没有就去看看学校
						String schAdminCode = house.getSchool();
						if(schAdminCode == null){
							//学校没有就去看看景点
							String sceAdminCode = house.getScenic();
							if(sceAdminCode != null){
								normalCode = sceAdminCode;
								property = CommonConstants.CityRank.SCENIC;
								String districtSubCode = disAdminCode.substring(0, 6) + "%";
								streetList =  houseBasicService.findBySQL(sql, new String[] { citySubCode, CommonConstants.CityRank.SCENIC},true);
							}
						}else{
							//表示留的资料是学校的，找出该区下的学校
							normalCode = schAdminCode;
							property = CommonConstants.CityRank.SCHOOL;
							String districtSubCode = disAdminCode.substring(0, 6) + "%";
							streetList =  houseBasicService.findBySQL(sql, new String[] { citySubCode, CommonConstants.CityRank.SCHOOL},true);
						}
					}else{
						//表示留的资料是商业圈的，找出该区下的商业圈
						normalCode = cirAdminCode;
						property = CommonConstants.CityRank.CIRCLE;
						String districtSubCode = disAdminCode.substring(0, 6) + "%";
						streetList =  houseBasicService.findBySQL(sql, new String[] { citySubCode, CommonConstants.CityRank.CIRCLE},true);
					}
				}else{
					//表示留的资料是街道的，找出该区下的街道
					String districtSubCode = disAdminCode.substring(0, 6) + "%";
					streetList =  houseBasicService.findBySQL(sql, new String[] { citySubCode, CommonConstants.CityRank.STREET },true);
				}
				String subAdminCode = house.getSubway();
				//找出该城市下的地铁线
				List<City> subwayLineList = houseBasicService.findBySQL(sql, new String[] { citySubCode, CommonConstants.CityRank.SUBWAYLINE },true);
				List<City> subwayList = null;
				if(subAdminCode != null){
					normalCode = subAdminCode.substring(0, 6) + "000000";;
					property = CommonConstants.CityRank.SUBWAYLINE;
					String subSubCode = subAdminCode.substring(0, 6) + "%";
					subwayList = houseBasicService.findBySQL(sql, new String[] { subSubCode, CommonConstants.CityRank.SUBWAYSTATION },true);
				}
				/*List<City> districtList = null;
				List<City> streetList = null;
				List<City> circletList = null;
				List<City> schoolList = null;//校区
				List<City> scenicList = null;//景点
				List<City> subwayList = null;//地铁站
				String sql = "queryPlace";
				String cityAdminCode = house.getCity();
				City city = (City) manageService.findOneByProperties(City.class, "adminiCode", cityAdminCode);
				
				String disAdminCode = house.getDistrict();
				City district = (City) manageService.findOneByProperties(City.class, "adminiCode", disAdminCode);
				
				String strAdminCode = house.getStreet();
				City street = (City) manageService.findOneByProperties(City.class, "adminiCode", strAdminCode);
				
				String cirAdminCode = house.getCircle();
				City circle = (City) manageService.findOneByProperties(City.class, "adminiCode", cirAdminCode);
		
				String citySubCode = cityAdminCode.substring(0, 3) + "%";
				districtList = manageService.findBySQL(sql, new String[] { citySubCode, CommonConstants.CityRank.DISTRICT },
						true);
		
				String districtSubCode = disAdminCode.substring(0, 6) + "%";
				streetList = manageService.findBySQL(sql, new String[] { districtSubCode, CommonConstants.CityRank.STREET },
						true);
		
				String circleSubCode = districtSubCode;
				circletList = manageService.findBySQL(sql, new String[] { circleSubCode, CommonConstants.CityRank.CIRCLE },
						true);
	    		//校区
	    		schoolList = manageService.findBySQL(sql, new String[] { circleSubCode, CommonConstants.CityRank.SCHOOL },
	    				true);
	    		//景点
	    		scenicList = manageService.findBySQL(sql, new String[] { circleSubCode, CommonConstants.CityRank.SCENIC },
	    				true);
	    		//地铁站
	    		subwayList = manageService.findBySQL(sql, new String[] { circleSubCode, CommonConstants.CityRank.SUBWAYSTATION },
	    				true);
				*/
				Location location =(Location) houseBasicService.findOneByProperties(Location.class, "branchId", houseid);
				String houseAddress = house.getAddress();
				/*String houseAddressArr[] = houseAddress.split("-");*/
				String addresshead = houseAddress;
				/*String addresslast = houseAddressArr[houseAddressArr.length - 1];
				String addresslastInput = addresslast.substring(0, addresslast.length() - 1);*/
				
				BranchKeywords bkw =(BranchKeywords) houseBasicService.findOneByProperties(BranchKeywords.class, "branchId", houseid,"status","1");
	              if(bkw != null){
	            	 keyword =  bkw.getKeywords();
	            	  
	              }
	              
	            List<?> specialSerivce = houseBasicService.findByProperties(Goods.class, "categoryId", "00000001");
				
	            mv.addObject("keyword", keyword);
				mv.addObject("staffname", staffname);
				mv.addObject("location", location);
				mv.addObject("rpstatus", rpstatus);
				mv.addObject("roomtypeList", roomtypeList);
				mv.addObject("beddescList", beddescList);
				mv.addObject("broadbandList", broadbandList);
				mv.addObject("positionList", positionList);
				/*mv.addObject("district", district);*/
				mv.addObject("disAdminCode", disAdminCode);
				mv.addObject("cityAdminName", cityAdminName);
				mv.addObject("newCity", JSONUtil.fromObject(newCity));
				/*mv.addObject("city", city);*/
				mv.addObject("cityAdminCode", cityAdminCode);
				mv.addObject("property",property);
				mv.addObject("normalCode",normalCode);//街道，商业圈，学校，景区，地铁线都用这个
				mv.addObject("subAdminCode",subAdminCode);//地铁站用这个
				/*mv.addObject("street", street);
				mv.addObject("circle", circle);*/
				mv.addObject("addresshead", addresshead);
				/*mv.addObject("addresslast", addresslast);
				mv.addObject("addresslastInput", addresslastInput);*/
				mv.addObject("house", house);
				mv.addObject("cityList", cityList);
				mv.addObject("districtList", districtList);
				mv.addObject("streetList", streetList);
				/*mv.addObject("circletList", circletList);*/
				mv.addObject("tipString", tipString);
				mv.addObject("deviceString", deviceString);
				mv.addObject("tips", tips);
				mv.addObject("serviceId", house.getServices());
				mv.addObject("serviceName", serviceName);
				mv.addObject("specialSerivce", specialSerivce);
				mv.addObject("houseDeviceList", houseDeviceList);
				/*mv.addObject("schoolList", schoolList);
				mv.addObject("scenicList", scenicList);*/
				mv.addObject("subwayLineList", subwayLineList);
				mv.addObject("subwayList", subwayList);
				mv.addObject("houseflag", flag);
				mv.addObject("houseid", houseid);
				return mv;
			}
		
		//民宿更新
		@RequestMapping("/updateHouse.do")
		public void updateHouse(HttpServletRequest request, HttpServletResponse response) throws Exception {
			LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
			String staffId = loginuser.getStaff().getStaffId();
			//String staffHouseId = loginuser.getStaff().getBranchId();
			String housename = request.getParameter("housename");
			String houseid = request.getParameter("houseid");
			String roomName = request.getParameter("roomName");
			String houseNo = request.getParameter("houseNo");
			String floor = request.getParameter("floor");
			String beds = request.getParameter("beds");
			String bedDesc = request.getParameter("bedDesc");
			String  position  = request.getParameter("position");
			String broadband = request.getParameter("broadband");
			//String currentprice = request.getParameter("currentprice");
			String cleanprice = request.getParameter("cleanprice");
			String cashpledge = request.getParameter("cashpledge");
			if (cashpledge.equals("") || cashpledge == null) {
				cashpledge = "0";
			}
			String communityName = request.getParameter("communityName");
			String decStyle = request.getParameter("decStyle");
			String preLocation = request.getParameter("preLocation");
			/*String  aftLocation  = request.getParameter("aftLocation");*/
			String  cityCode  = request.getParameter("cityCode");
			String district  = request.getParameter("district");
			String street  = request.getParameter("street");
			String property  = request.getParameter("property");
			String subway  = request.getParameter("subway");
			/*String circle = request.getParameter("circle");*/
			String remarkInput = request.getParameter("remarkInput");
			String area  = request.getParameter("area");
			String status  = request.getParameter("status");
			String address = preLocation;
			//String latlng = request.getParameter("latlng");
			String longitude = request.getParameter("longitude");
			String latitude = request.getParameter("latitude");
			String flag = request.getParameter("flag");
			//String originalprice = request.getParameter("originalprice");
			String housedesc = request.getParameter("housedesc");
			String staffid = request.getParameter("staffid");
			String device = request.getParameter("device");
			String tip = request.getParameter("tip");
			String services = request.getParameter("services");
			String keyword = request.getParameter("keyword");
			String recommend = request.getParameter("recommend");
			String orderNo = request.getParameter("orderNo");
			String tipString = "";
			String deviceString = "";
			String deviceSign = "";
			String tipSign = "";
			List<?> houseDeviceList = houseBasicService.getHouseDevice();
			List<?> tips = houseBasicService.getTips();
			if(!StringUtils.isEmpty(device) ){
				if (device.endsWith(",")) {
					deviceString = device.substring(0, device.length() - 1);
				} else {
					deviceString = device;
				}
				
				String deviceArray[] = deviceString.split(",");
				boolean deviceflag = true;
				for (int i = 0; i < houseDeviceList.size(); i++) {
					for (int j = 0; j < deviceArray.length; j++) {
						if (((Map<?, ?>) houseDeviceList.get(i)).get("PARAM_NAME").toString().equals(deviceArray[j])) {
							deviceSign += "1";
							deviceflag = false;
						}
					}
					if (deviceflag) {
						deviceSign += "0";
					}
					deviceflag = true;
				}
				
				
			}
			if(!StringUtils.isEmpty(tip)){
				if (tip.endsWith(",")) {
					tipString = tip.substring(0, tip.length() - 1);
				} else {
					tipString = tip;
				}
				String tipArray[] = tipString.split(",");
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
			}
			try{
				
				Location loc =(Location) houseBasicService.findOneByProperties(Location.class, "branchId", houseid);
				
				loc.setLatitude(latitude.trim());
				loc.setLongitude(longitude.trim());
				loc.setRecordTime(new Date());
				loc.setRecordUser(staffId);
				loc.setStatus(CommonConstants.STATUS.NORMAL);
				houseBasicService.update(loc);
				
				
				BranchKeywords bkw =(BranchKeywords) houseBasicService.findOneByProperties(BranchKeywords.class, "branchId", houseid,"status","1");
				
				bkw.setKeywords(keyword);
				bkw.setRecordTime(new Date());
				bkw.setRecordUser(staffId);
				
				houseBasicService.update(bkw);
				
				House house = (House)houseBasicService.findOneByProperties(House.class, "houseId", houseid);
				house.setHousename(housename);
				house.setHouseNo(houseNo);
				house.setFloor(floor);
				house.setBeds(new Byte(beds));
				house.setBedDesc(bedDesc);
				house.setPosition(position);
				house.setBroadband(broadband);
			//	house.setCurrentprice(new Double(currentprice));
				house.setCleanprice(new Double(cleanprice));
				house.setCashPledge(new Double(cashpledge));
				house.setCommunityName(communityName);
				house.setDecStyle(decStyle);
				house.setAddress(address);
				house.setCity(cityCode);
				house.setDistrict(district);
				house.setStreet(street);
				/*house.setCircle(circle);*/
				if(CommonConstants.CityRank.STREET.equals(property)){
					house.setStreet(street);
					house.setCircle("");
					house.setSchool("");
					house.setScenic("");
					house.setSubway("");
				}else if(CommonConstants.CityRank.CIRCLE.equals(property)){
					house.setStreet("");
					house.setCircle(street);
					house.setSchool("");
					house.setScenic("");
					house.setSubway("");
				}else if(CommonConstants.CityRank.SCHOOL.equals(property)){
					house.setStreet("");
					house.setCircle("");
					house.setSchool(street);
					house.setScenic("");
					house.setSubway("");
				}else if(CommonConstants.CityRank.SCENIC.equals(property)){
					house.setStreet("");
					house.setCircle("");
					house.setSchool("");
					house.setScenic(street);
					house.setSubway("");
				}else if(CommonConstants.CityRank.SUBWAYLINE.equals(property)){
					house.setStreet("");
					house.setCircle("");
					house.setSchool("");
					house.setScenic("");
					house.setSubway(subway);
				}
				house.setRemark(remarkInput);
				house.setArea(new Short(area));
				house.setStatus(status);
				//house.setRecordUser(staffId);
				house.setStaffId(staffid);
				house.setFlag(flag);
				house.setTips(tipSign);
				house.setServices(services);
				house.setLabel(deviceSign);
				house.setHouseDesc(housedesc);
				//house.setInitprice(new Double(originalprice));
				house.setHouseType(roomName);
				house.setIsRecommend(recommend);
				house.setOrderNo(orderNo);
				houseBasicService.update(house);
				houseBasicService.houseEditLog(loginuser, "0",request);
				JSONUtil.responseJSON(response, new AjaxResult(1, "修改成功！"));
			} catch (Exception e) {
				e.printStackTrace();
				houseBasicService.houseEditLog(loginuser, "1",request);
				JSONUtil.responseJSON(response, new AjaxResult(0, "修改失败！"));
			}
			
			
		}
		/**根据城市查区，街道，学校，景区，商业圈，地铁线路，地铁站
		 * 20180604zhongll
		 * @param request
		 * @param response
		 * @throws Exception
		 */
		@SuppressWarnings({ "rawtypes", "unchecked" })
		@RequestMapping("/queryPlaceByCity.do")
		public void queryPlaceByCity(HttpServletRequest request, HttpServletResponse response) throws Exception {
			String cityAdminiCode = request.getParameter("cityAdminiCode");
			String sql = "queryPlace";
			//1先查区
			List districtList = new ArrayList();
			List streetList = new ArrayList();
			String cityCode = cityAdminiCode.substring(0, 4) + "%";
			districtList = houseBasicService.findBySQL(sql,
					new String[] { cityCode, CommonConstants.CityRank.DISTRICT }, true);
			if(districtList.size() > 0){
				//2.查街道
				String districtCode = (String)((Map<?,?>)districtList.get(0)).get("ADMINI_CODE");
				districtCode = districtCode.substring(0, 6) + "%";
				streetList = houseBasicService.findBySQL(sql,
						new String[] { districtCode, CommonConstants.CityRank.STREET }, true);
			}
			Map<String, List> map = new HashMap();
			map.put("districtList", districtList);
			map.put("streetList", streetList);
		
			JSONUtil.responseJSON(response, new AjaxResult(1, null, map));
		}
		/**根据区和属性查（街道，学校，景区，商业圈，地铁线路），如果是地铁线路，再查出地铁站
		 * 20180604zhongll
		 * @param request
		 * @param response
		 * @throws Exception
		 */
		@SuppressWarnings({ "unchecked", "rawtypes" })
		@RequestMapping("/queryPlaceByProperty.do")
		public void queryPlaceByProperty(HttpServletRequest request, HttpServletResponse response) throws Exception {
			String districtAdminiCode = request.getParameter("districtAdminiCode");
			String rank = request.getParameter("rank");
			Map<String, List> map = new HashMap();
			List list = new ArrayList();
			List subwayLineList = new ArrayList();
			List subwayList = new ArrayList();
			String sql = "queryPlace";
			if(districtAdminiCode !=null && !districtAdminiCode.equals("")){
				if(!"7".equals(rank)){
					String districtSubCode = districtAdminiCode.substring(0, 6) + "%";
					list = houseBasicService.findBySQL(sql,
							new String[] { districtSubCode, rank }, true);
//					/map.put("list", list);
				}else{
					String cityCode = districtAdminiCode.substring(0, 4) + "%";
					subwayLineList = houseBasicService.findBySQL(sql,
							new String[] { cityCode, rank }, true);
					if(subwayLineList.size() > 0){
						String subwayLineCode = (String)((Map<?,?>)subwayLineList.get(0)).get("ADMINI_CODE");
						subwayLineCode = subwayLineCode.substring(0, 6) + "%";
						subwayList = houseBasicService.findBySQL(sql,
								new String[] { subwayLineCode, CommonConstants.CityRank.SUBWAYSTATION }, true);
					}
					/*map.put("subwayLineList", subwayLineList);
					map.put("subwayList", subwayList);*/
				}
			}
			map.put("list", list);
			map.put("subwayLineList", subwayLineList);
			map.put("subwayList", subwayList);
			JSONUtil.responseJSON(response, new AjaxResult(1, null, map));
		
			
		}
		
		/**根据地铁线查站
		 * 20180604zhongll
		 * @param request
		 * @param response
		 * @throws Exception
		 */
		@SuppressWarnings({ "unchecked", "rawtypes" })
		@RequestMapping("/queryPlaceBySubwayLine.do")
		public void queryPlaceBySubwayLine(HttpServletRequest request, HttpServletResponse response) throws Exception {
			String subwayLineCode = request.getParameter("subwayLineCode");
			List subwayList = new ArrayList();
			String sql = "queryPlace";
			if(subwayLineCode !=null && !subwayLineCode.equals("")){
				subwayLineCode = subwayLineCode.substring(0, 6) + "%";
				subwayList = houseBasicService.findBySQL(sql,
						new String[] { subwayLineCode, CommonConstants.CityRank.SUBWAYSTATION }, true);	
			}

			Map<String, List> map = new HashMap();
			map.put("subwayList", subwayList);
			JSONUtil.responseJSON(response, new AjaxResult(1, null, map));
		}
		// 自定义房间管理新增页面跳转
		@RequestMapping("/roomAdd.do")
		public ModelAndView roomAdd(HttpServletRequest request, HttpServletResponse response) throws Exception {
			ModelAndView mv = new ModelAndView();
			mv.setViewName("/page/basic/branch/roomadd");
			LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
			String branchId = loginuser.getStaff().getBranchId();
			Branch branchid = (Branch) houseBasicService.findOneByProperties(Branch.class, "branchId", branchId);
			String branchname = branchid.getBranchName().toString();
			String theme = branchid.getBranchType();
			SysParam sysParam = ((SysParam) (houseBasicService.findOneByProperties(SysParam.class, "paramType", "THEME",
					"content", theme)));
			Branch br =  (Branch)houseBasicService.findOneByProperties(Branch.class, "branchId", branchId);
			String branchRank = br.getRank();
                       
		
			mv.addObject("branchRank",branchRank);
			String themename = sysParam.getParamName().toString();
			List<?> rproomtype = houseBasicService.getRproomtype(branchId);
		//	List<?> rpstatus = houseBasicService.getStatus();
			List<?> rpstatus = houseBasicService.getStatusNew();
			request.setAttribute("branchname", branchname);
			request.setAttribute("themename", themename);
			request.setAttribute("rproomtype", rproomtype);
			request.setAttribute("rpstatus", rpstatus);
			request.setAttribute("branchId", branchId);
			request.setAttribute("theme", theme);
			return mv;
		}
		@RequestMapping("/judgeRoomAdd.do")
		public void judgeRoomAdd(HttpServletRequest request, HttpServletResponse response) throws Exception {
			LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
			String branchId = loginuser.getStaff().getBranchId();
			List<?> rproomtype = houseBasicService.getRproomtype(branchId);
			String a = null;
			if (null == rproomtype || rproomtype.size() == 0) {
				a = "当前门店未配置房型，请先去配置房型";
			}
			JSONUtil.responseJSON(response, new AjaxResult(1, a));
		}
		
		@RequestMapping("/selectRoomtype4RoomAdd.do")
		public void selectRoomtype4RoomAdd(HttpServletRequest request, HttpServletResponse response,String branchid) throws Exception {
		 
			List<?> rproomtype = houseBasicService.getRproomtype(branchid);
			String a = null;
			if (null == rproomtype || rproomtype.size() == 0) {
				a = "当前门店未配置房型，请先去配置房型";
			}
			//JSONArray ja = new JSONArray(rproomtype);
			
			JSONUtil.responseJSON(response, new AjaxResult(1,a,rproomtype));
		}
		
		@RequestMapping("/houseDelete.do")
		@ResponseBody
		public String houseDelete(HttpServletRequest request) throws Exception {
			LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
			String loginuserBranchId = loginuser.getStaff().getBranchId();
			Branch loginuserbranch = (Branch) houseBasicService.findOneByProperties(Branch.class, "branchId", loginuserBranchId);
			String branchRank = loginuserbranch.getRank();
			String doneResult = String.valueOf(SystemConstants.PortalResultCode.DONE);
			if(branchRank.equals("1")){
				return String.valueOf(SystemConstants.PortalResultCode.EXIST);
			}else{
				try {
					String branchId = request.getParameter("branchId");
					String branchIds[] = branchId.split(",");
					for(int i = 0;i<branchIds.length;i++){
						String delBranchId = branchIds[i];
						House house = (House)houseBasicService.findOneByProperties(House.class, "houseId", delBranchId);
						house.setStatus("0");
						house.setRecordTime(new Date());
						houseBasicService.deleteAllHousePrice(delBranchId);
						houseBasicService.update(house);
					}
				} catch (Exception e) {
					log.error("delete data occurs error! beanName[HOUSE], data[ + HOUSE], " + e.getMessage());
					doneResult = String.valueOf(SystemConstants.PortalResultCode.FAILED);
				}
				return doneResult;
				
			}
		}
		/**根据区的变化以及属性（街道，学校，景区，商业圈，地铁站）选择
		 * 20180604zhongll
		 * @param request
		 * @param response
		 * @throws Exception
		 */
		@SuppressWarnings({ "unchecked", "rawtypes" })
		@RequestMapping("/queryPlaceByDistrict.do")
		public void queryPlaceByDistrict(HttpServletRequest request, HttpServletResponse response) throws Exception {
			String districtAdminiCode = request.getParameter("districtAdminiCode");
			String rank = request.getParameter("rank");
			List list = new ArrayList();
			String sql = "queryPlace";
			if(districtAdminiCode !=null && !districtAdminiCode.equals("")){
				String districtSubCode = districtAdminiCode.substring(0, 6) + "%";
				list = houseBasicService.findBySQL(sql,
						new String[] { districtSubCode, rank }, true);
			}

			Map<String, List> map = new HashMap();
			map.put("list", list);
			JSONUtil.responseJSON(response, new AjaxResult(1, null, map));
		}
		
		
		@RequestMapping("/addHouse2staff.do")
		public ModelAndView addHouse2staff(HttpServletRequest request, HttpServletResponse response) throws Exception {
			ModelAndView mv = new ModelAndView();
			mv.setViewName("/page/basic/house/addStaffCRM");
			
			return mv;
		}

		/*
		 * 门店内景图片新增（批量）
		 */
		@SuppressWarnings({ "static-access", "unused" })
		@RequestMapping("/submitBranchPics.do")
		public void addToomTypePics(HttpServletRequest request, HttpServletResponse response) throws Exception {
			SysParam sysParam = (SysParam) houseBasicService.findOneByProperties(SysParam.class, "paramType", "SYSTEMTYPE");
			String systemType = sysParam.getContent();
			String branchIdChoose = request.getParameter("branchId");
			LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
			String staffId = loginuser.getStaff().getStaffId();
			String branchId = loginuser.getStaff().getBranchId();
			String imgBase = request.getParameter("imgBase");
			String imgFormat = request.getParameter("imgFormat");
			String lookIndex = request.getParameter("lookIndex");
			String IMG_ROUTE = request.getParameter("IMG_ROUTE");
			String base64StringArr[] = imgBase.split(",");
			String base64String = base64StringArr[1];
			String subString[] = IMG_ROUTE.split("\\\\");
			String filename = "";
			if (subString.length > 1) {
				filename = subString[subString.length - 1];
					String filenameTime = String.valueOf(System.currentTimeMillis());
					synchronized (cnt) {
						if (++cnt > 9) {
							cnt = 1;
						}
						filenameTime += cnt;
					}
				filename = filenameTime+filename.substring(filename.lastIndexOf("."));
			}

			String tempSavePath = RequestUtil.getWebPath(request);
			// 临时文件路径
			File srcFolder = new File(tempSavePath + File.separator + "upload");
			if (!srcFolder.exists()) {
				srcFolder.mkdirs();
			}
			String targetFileRule = srcFolder.getAbsolutePath() + File.separator + filename;
			File tarFile = new File(targetFileRule);
			String url = CommonConstants.Domain.DOMAINNAME + filename;
			if (!tarFile.exists()) {
				tarFile.createNewFile();
			}

			try {
				Base64 base64 = new Base64();
				byte[] bytes = base64.decodeBase64(new String(base64String).getBytes());

				ByteArrayInputStream in = new ByteArrayInputStream(bytes);
				byte[] buffer = new byte[1024];
				FileOutputStream out = new FileOutputStream(tarFile);
				int bytesum = 0;
				int byteread = 0;
				while ((byteread = in.read(buffer)) != -1) {
					bytesum += byteread;
					out.write(buffer, 0, byteread); // 文件写操作
					out.flush();
				}
				out.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
			
			String seqName = "SEQ_PIC_ID";
			String sequence = houseBasicService.getCloudSequence(seqName);
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
			houseBasicService.save(bp);
			
			/*SysParam param = (SysParam) houseBasicService.findOneByProperties(SysParam.class, "paramType",
					SystemConstants.ParamType.BRANCH_IP, "paramName", "1");

			int priority = 2;
			if(CommonConstants.SystemType.Branch.equals(systemType.trim())){
				// priority = Integer.parseInt(member.getMemberRank());
				List<BranchPicture> list = new ArrayList<BranchPicture>();
				list.add(bp);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("BranchPicture", list);
				TransferServcie.getInstance().transferData(priority, param.getContent(), JSONUtil.fromObject(map).toString());
			}*/
			
			Picture picture = new Picture();
			picture.setPictureId(picId);
			picture.setPictureStyle(picTrueStyle);
			picture.setRecordTime(new Date());
			picture.setRecordUser(staffId);
			picture.setStatus(status);
			picture.setPictureUrl(url);
			houseBasicService.save(picture);
			
			/*if(CommonConstants.SystemType.Branch.equals(systemType.trim())){
				List<Picture> list1 = new ArrayList<Picture>();
				list1.add(picture);
				Map<String, Object> mapPicture = new HashMap<String, Object>();
				mapPicture.put("Picture", list1);
				TransferServcie.getInstance().transferData(priority, param.getContent(),
						JSONUtil.fromObject(mapPicture).toString());
			}*/

//			文件同步
/*		SysParam paramFile = (SysParam) houseBasicService.findOneByProperties(SysParam.class, "paramType",
					SystemConstants.ParamType.BRANCH_IP, "paramName", "FILE");

			int priorityFile = 1;
			// priority = Integer.parseInt(member.getMemberRank());
			List<String> fileNameList = new ArrayList<String>();
			fileNameList.add(filename);
			Map<String, Object> fileNameMap = new HashMap<String, Object>();
			fileNameMap.put("FileName", fileNameList);
			TransferServcie.getInstance().transferFile(priorityFile, paramFile.getContent(),
					JSONUtil.fromObject(fileNameMap).toString());*/
			
			JSONUtil.responseJSON(response, new AjaxResult(1, null));

		}
		
		/**
		 * 查询民宿账号
		 * @param request
		 * @param response
		 * @return
		 */
		@RequestMapping("/selectHouseAccount.do")
		public ModelAndView selectHouseAccount(HttpServletRequest request, HttpServletResponse response) {
			ModelAndView mv = new ModelAndView();
			mv.setViewName("page/dialog/houseAccountDialog");
			mv.addObject("dialogColumns", "staffId,staffName");
			mv.addObject("dialogTarget", "Staff");
			mv.addObject("dialogConditions", "status='1' and branchId =$BRANCHID ");
			mv.addObject("dialogQuickAdd", null);
			mv.addObject("selectType", "dialog-radio");
			mv.addObject("colName", "staffname");
			return mv;
		}
		
		@SuppressWarnings("unchecked")
		@RequestMapping("/staffUpdate2addHouse.do")
		public void staffUpdate2addHouse(String STAFFNAME, String LOGINNAME, String BRANCHID_CUSTOM_VALUE, String DEPARTID_CUSTOM_VALUE,
				String POST_CUSTOM_VALUE, String GENDOR, String IDCARD, String MOBILE, String ENTRYTIME, String STATUS,
				String EMAIL, String ADDRESS, String REMARK, HttpServletRequest request, HttpServletResponse response) 
						throws Exception {
		    String a = "";
		    List<Staff> loginnamecheck =  houseBasicService.findByProperties(Staff.class, "loginName", LOGINNAME,"status","1");
		    List<Staff> idcardcheck =  houseBasicService.findByProperties(Staff.class, "idcard", IDCARD,"status","1");
		    List<Staff> mobilecheck =  houseBasicService.findByProperties(Staff.class, "mobile", MOBILE,"status","1");
		    
		   if(loginnamecheck.size()>0){
			   JSONUtil.responseJSON(response, new AjaxResult(0, "登录名已存在！"));
		   }else if(idcardcheck.size()>0){
			   JSONUtil.responseJSON(response, new AjaxResult(0, "身份证已存在！"));
		   }else if(mobilecheck.size()>0){
			   JSONUtil.responseJSON(response, new AjaxResult(0, "手机号已存在！"));
		   }else{
				LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
				String staffId = houseBasicService.getSequence("SEQ_STAFF_ID", null);
				String pwd = MD5Util.getCryptogram("888888");
				DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
				
				//根据身份证截取生日
				SimpleDateFormat sdf2=new SimpleDateFormat("yyyyMMdd");
				String birthstr = IDCARD.substring(6, 14);
				Date birthday = sdf2.parse(birthstr);
				Date entrytime;
				if (ENTRYTIME != null && !StringUtil.isEmpty(ENTRYTIME.trim())) {
					entrytime = df.parse(ENTRYTIME);
				} else {
					entrytime = null;
				}
				Staff staff = new Staff();
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
				staff.setStaffId(staffId);
				staff.setPassword(pwd);
				staff.setStaffName(STAFFNAME);
				staff.setLoginName(LOGINNAME);
				staff.setBranchId(BRANCHID_CUSTOM_VALUE);
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
				houseBasicService.save(staff);
				
				JSONUtil.responseJSON(response, new AjaxResult(1, "新增成功！"));
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
}