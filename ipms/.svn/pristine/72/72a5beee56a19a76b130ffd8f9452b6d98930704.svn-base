package com.ideassoft.pmsmanage.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
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






import com.ideassoft.basic.service.AuditBasicService;
import com.ideassoft.basic.service.HouseBasicService;
import com.ideassoft.bean.AdvertisePic;
import com.ideassoft.bean.Branch;
import com.ideassoft.bean.BranchPicture;
import com.ideassoft.bean.House;
import com.ideassoft.bean.OperateLog;
import com.ideassoft.bean.PettyCash;
import com.ideassoft.bean.Picture;
import com.ideassoft.bean.Post;
import com.ideassoft.bean.PriceApply;
import com.ideassoft.bean.PriceApplyDetail;
import com.ideassoft.bean.Room;
import com.ideassoft.bean.RoomKey;
import com.ideassoft.bean.RoomPicture;
import com.ideassoft.bean.RoomPrice;
import com.ideassoft.bean.RoomPriceId;
import com.ideassoft.bean.RoomStatus;
import com.ideassoft.bean.RoomType;
import com.ideassoft.bean.RoomTypeKey;
import com.ideassoft.bean.Staff;
import com.ideassoft.bean.SysParam;
import com.ideassoft.core.ajax.AjaxResult;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.constants.CommonConstants;
import com.ideassoft.core.constants.SystemConstants;
import com.ideassoft.core.jdbc.SqlBuilder;
import com.ideassoft.core.notifier.SmsSingleSender;
import com.ideassoft.core.page.Pagination;
import com.ideassoft.crm.service.AuditService;
import com.ideassoft.crm.service.TransferServcie;
import com.ideassoft.pmsmanage.service.PmsmanageService;
import com.ideassoft.priceRuleUtile.BasePriceUtile; 
import com.ideassoft.price.AdjustPriceController;
import com.ideassoft.util.DateUtil;
import com.ideassoft.util.HeartBeatClient;
import com.ideassoft.util.JSONUtil;
import com.ideassoft.util.RegExUtil;
import com.ideassoft.util.RequestUtil;

@Transactional
@Controller
public class PmsmanageController {
	@Autowired
	private PmsmanageService pmsmanageService;
	@Autowired
	private HouseBasicService houseBasicService;
	@Autowired
	private AuditBasicService auditService;


	// 自定义房间管理删除
	@RequestMapping("/delRoom.do")
	public void delRoom(HttpServletRequest request, HttpServletResponse response, String theme, String branchid,
			String roomid, String roomtype, String roomstatus,String roomarea,String roomfloor,String roomremark) throws Exception {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginUser.getStaff();
		String recordUser = staff.getStaffId();
		String[] roomids = roomid.split(",");
		String[] branchids = branchid.split(",");
		String[] roomtypes = roomtype.split(",");
		String[] roomstatuss = roomstatus.split(",");
		String[] themeids = theme.split(",");
		for (int i = 0; i < roomids.length; i++) {
			Branch branchId = (Branch) pmsmanageService.findOneByProperties(Branch.class, "branchName", branchids[i]);
			String branchiD = branchId.getBranchId();
			String rooomiD = roomids[i];
			SysParam sysParamstatus = ((SysParam) (pmsmanageService.findOneByProperties(SysParam.class, "paramType",
					"ROOMSTATUS", "paramName", roomstatuss[i])));
			String statusdelete = sysParamstatus.getContent().toString();
			if (roomids[i] != null) {
				RoomKey roomKeyid = new RoomKey();
				roomKeyid.setRoomId(roomids[i]);
				roomKeyid.setBranchId(branchiD);
				Room roomdelete = ((Room) (pmsmanageService.findOneByProperties(Room.class, "roomKey.branchId",branchiD,"roomKey.roomId",rooomiD)));
				roomdelete.setStatus(CommonConstants.RoomStatus.RoomUnuse);
				roomdelete.setRecordUser(recordUser);
				roomdelete.setRecordTime(new Date());
				pmsmanageService.update(roomdelete);
				/*SysParam systemtype = (SysParam) PmsmanageService.findOneByProperties(SysParam.class, "paramType","SYSTEMTYPE", "status", "1");
				String stype = systemtype.getContent().toString();
				if(stype.equals(CommonConstants.SystemType.Branch)){
				int priority = 1;
				SysParam param = (SysParam) PmsmanageService.findOneByProperties(SysParam.class, "paramType","REMOTE_PATH", "status", "1");
				List<Room> roomList = new ArrayList<Room>();
				roomList.add(roomdelete);
				Map<String, Object> roomMap = new HashMap <String, Object>();
				roomMap.put("Room", roomList);
				TransferServcie.getInstance().transferData(priority, param.getContent(),JSONUtil.fromObject(roomMap).toString());
				  //}
				}*/
			}
			SysParam sysParam = ((SysParam) (pmsmanageService.findOneByProperties(SysParam.class, "paramType", "THEME",
					"paramName", themeids[i])));
			String themeid = sysParam.getContent().toString();
			List<?> reroomtype = pmsmanageService.getReroomtype(roomtypes[i], themeid, branchiD);
			String roomtypeid = ((Map<?, ?>) reroomtype.get(0)).get("ROOM_TYPE").toString();
			if (statusdelete.equals(CommonConstants.RoomStatus.RoomStop)) {
				RoomStatus roomStatus = ((RoomStatus) (pmsmanageService.findOneByProperties(RoomStatus.class,
						"branchId", branchiD, "roomType", roomtypeid)));
				String oldcount = roomStatus.getCount().toString();
				String oldstopnum = roomStatus.getStopnum().toString();
				if(Integer.parseInt(oldcount)>0){
				int x = Integer.parseInt(oldcount) - 1;
				Integer newcount = Integer.valueOf(x);
				roomStatus.setCount(newcount);
				}
				if(Integer.parseInt(oldstopnum)>0){
				int v = Integer.parseInt(oldstopnum) - 1;		
				Integer newstopnum = Integer.valueOf(v);
				roomStatus.setStopnum(newstopnum);
				}
				roomStatus.setRecordUser(staff.getStaffId());
				roomStatus.setRecordTime(new Date());
				pmsmanageService.save(roomStatus);

			} else if (statusdelete.equals(CommonConstants.RoomStatus.RoomUnuse)) {

			} else if ((statusdelete.equals(CommonConstants.RoomStatus.RoomExample))
					|| (statusdelete.equals(CommonConstants.RoomStatus.RoomStaff))||(statusdelete.equals(CommonConstants.RoomStatus.RoomRepair))) {
				RoomStatus roomStatus = ((RoomStatus) (pmsmanageService.findOneByProperties(RoomStatus.class,
						"branchId", branchiD, "roomType", roomtypeid)));
				String oldcount = roomStatus.getCount().toString();
				if(Integer.parseInt(oldcount)>0){
				int x = Integer.parseInt(oldcount) - 1;
				Integer newcount = Integer.valueOf(x);
				roomStatus.setCount(newcount);
				}
				roomStatus.setRecordUser(staff.getStaffId());
				roomStatus.setRecordTime(new Date());
				pmsmanageService.save(roomStatus);

			} else {

				RoomStatus roomStatus = ((RoomStatus) (pmsmanageService.findOneByProperties(RoomStatus.class,
						"branchId", branchiD, "roomType", roomtypeid)));
				String oldcount = roomStatus.getCount().toString();
				String oldsellnum = roomStatus.getSellnum().toString();
				if(Integer.parseInt(oldcount)>0){
				int x = Integer.parseInt(oldcount) - 1;
				Integer newcount = Integer.valueOf(x);
				roomStatus.setCount(newcount);
				}
				if(Integer.parseInt(oldsellnum)>0){
				int v = Integer.parseInt(oldsellnum) - 1;
				Integer newsellnum = Integer.valueOf(v);
				roomStatus.setSellnum(newsellnum);
				}
				roomStatus.setRecordUser(staff.getStaffId());
				roomStatus.setRecordTime(new Date());
				pmsmanageService.save(roomStatus);
			}
		}
		JSONUtil.responseJSON(response, new AjaxResult(1, null));
	}

	/*
	 * // 房租价申请before
	 * 
	 * @RequestMapping("/rpJudge.do") public void rpJudge(HttpServletRequest
	 * request, HttpServletResponse response, String Mnumb er) throws Exception {
	 * List<?> rpsetup = PmsmanageService.getRpsetup(); List<?> rpbranchid =
	 * PmsmanageService.getRpbranchid(); List<?> rproomtype =
	 * PmsmanageService.getRproomtype(); String discount = ((Map<?, ?>)
	 * rpsetup.get(0)).get("DISCOUNT")==null ?"":((Map<?, ?>)
	 * rpsetup.get(0)).get("DISCOUNT").toString(); String a = null; if (null ==
	 * discount || discount.equals("")) { a = "请先去配置会员等级等相关数据！"; }else if(null
	 * == rpbranchid || rpbranchid.size() == 0) { a = "请先去配置门店等相关数据！"; }else
	 * if(null == rproomtype || rproomtype.size() == 0) { a = "请先去配置房型等相关数据！"; }
	 * JSONUtil.responseJSON(response, new AjaxResult(1, a)); }
	 */

	
	
	// 房租价申请（第一版）rpBasicjudge.do
	@RequestMapping("/rpBasicApplyfirst.do")
	public ModelAndView rpBasicApplyfirst(HttpServletRequest request, HttpServletResponse response, String themename,
			String branchname, String rpkindname, String roomtypename, String status) throws Exception {
		ModelAndView mv = new ModelAndView();
		List<?> rppack = pmsmanageService.getRppack();
		List<?> rpstatus = pmsmanageService.getRpapplystatus();
		List<?> rpsetup = pmsmanageService.getRpsetup();
		SysParam sysParam = ((SysParam) (pmsmanageService.findOneByProperties(SysParam.class, "paramType", "THEME","paramName", themename)));
		String themeid = sysParam.getContent().toString();
		Branch branchId = (Branch) pmsmanageService.findOneByProperties(Branch.class, "branchName", branchname);
		String branchid = branchId.getBranchId().toString();
		SysParam sysParamkind = ((SysParam) (pmsmanageService.findOneByProperties(SysParam.class, "paramType","RPKIND", "paramName", rpkindname)));
		String rpking = sysParamkind.getContent().toString();
		SysParam sysParamstatus = ((SysParam) (pmsmanageService.findOneByProperties(SysParam.class, "paramType","RPSTATUS", "paramName", status)));
		String statusid = sysParamstatus.getContent().toString();
		String rpthemeid = themeid;
		List<?> rproomtype = pmsmanageService.getRpapplyroomtype(themeid, rpthemeid, branchid, rpking, statusid);
		mv.setViewName("page/pmsmanage/roomprice/rpapply");
		request.setAttribute("themename", themename);
		request.setAttribute("branchname", branchname);
		request.setAttribute("rpkindname", rpkindname);
		request.setAttribute("rproomtype", rproomtype);
		request.setAttribute("rppack", rppack);
		request.setAttribute("status", status);
		request.setAttribute("themeid", themeid);
		request.setAttribute("branchid", branchid);
		request.setAttribute("rpking", rpking);
		request.setAttribute("statusid", statusid);
		request.setAttribute("rpsetup", rpsetup);
		request.setAttribute("rpstatus", rpstatus);
		return mv;
	}
	




	// 房租价申请保存申请数据
	@RequestMapping("/rpOperatedetail.do")
	public void auditRp(HttpServletRequest request, HttpServletResponse response, String theme, String branchid,
			String rpkind, String operate, String status, String applyid, String rptypename, String rprice,
			String packid,String validstart, String validend, String validday,String ruleid) throws Exception {
		
		DateFormat dff = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
		Date validstartdata = null;
		if(validstart.equals("") == false){
			validstartdata = dff.parse(validstart);
		}
		Date validenddata = null;
		if(validend.equals("")==false){
			validenddata = dff.parse(validend);
		}
		
		
		
		
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String recorduser = staff.getStaffId();
		List<?> rpsetup = pmsmanageService.getRpsetup();
		/*SysParam systemtype = (SysParam) PmsmanageService.findOneByProperties(SysParam.class, "paramType","SYSTEMTYPE", "status", "1");
		String stype = systemtype.getContent().toString();*/
		for (int i = 0; i < rpsetup.size(); i++) {
			String rpid = ((Map<?, ?>) rpsetup.get(i)).get("PARAMNAME").toString();
			String rpname = ((Map<?, ?>) rpsetup.get(i)).get("PARAMDESC").toString();
			String discount = ((Map<?, ?>) rpsetup.get(i)).get("DISCOUNT").toString();
			String memberrank = ((Map<?, ?>) rpsetup.get(i)).get("CONTENT").toString();
			Double d = new Double(discount) / 100;
			Double p = new Double(rprice);
			Format f = new DecimalFormat("0.00");
			Double price = Double.parseDouble(f.format(p * d));
			String detailid = this.pmsmanageService.getSequence("SEQ_NEW_PRICEAPPLYDETAIL", null);
			PriceApplyDetail priceapplydetail = new PriceApplyDetail();
			priceapplydetail.setDetailId(detailid);
			priceapplydetail.setApplyId(applyid);
			priceapplydetail.setContent(rpid);
			priceapplydetail.setRoomType(rptypename);
			priceapplydetail.setRoomPrice(price);
			priceapplydetail.setRecordTime(new Date());
			pmsmanageService.save(priceapplydetail);
			/*if(stype.equals(CommonConstants.SystemType.Branch)){
				int priority = 1;
				SysParam param = (SysParam) PmsmanageService.findOneByProperties(SysParam.class, "paramType","REMOTE_PATH", "status", "1");
				List<PriceApplyDetail> priceapplydetailList = new ArrayList<PriceApplyDetail>();
				priceapplydetailList.add(priceapplydetail);
				Map<String, Object> priceapplydetailMap = new HashMap<String, Object>();
				priceapplydetailMap.put("PriceApplyDetail", priceapplydetailList);
				TransferServcie.getInstance().transferData(priority, param.getContent(),JSONUtil.fromObject(priceapplydetailMap).toString());
				}*/
			List<?> judgerpexit = pmsmanageService.getJudgerpexit(theme, branchid, rpid, rptypename, status, rpkind);
			PriceApply priceapplystatus = (PriceApply) pmsmanageService.findOneByProperties(PriceApply.class, "applyId",applyid);
			String statuspriceapply = priceapplystatus.getStatus().toString();
			if (null == judgerpexit || judgerpexit.size() == 0) {
				String rpstate =CommonConstants.RoomPriceState.unuse;
				if((status.equals(CommonConstants.RoomPriceStatus.basic))&&(statuspriceapply.equals(CommonConstants.RpApplyStatus.RpActive))){
					rpstate = CommonConstants.RoomPriceState.active;
				}
				RoomPrice rpriceapply = new RoomPrice();
				RoomPriceId roompriceid = new RoomPriceId();
				roompriceid.setBranchId(branchid);
				roompriceid.setRoomType(rptypename);
				roompriceid.setRpId(rpid);
				roompriceid.setRpKind(rpkind);
				roompriceid.setStatus(status);
				rpriceapply.setRoomPriceId(roompriceid);
				rpriceapply.setDiscount(d);
				rpriceapply.setMemberRank(memberrank);
				rpriceapply.setPackId(packid);
				rpriceapply.setRecordUser(recorduser);
				rpriceapply.setRoomPrice(price);
				rpriceapply.setRpName(rpname);
				rpriceapply.setRpType("1");
				rpriceapply.setState(rpstate);
				rpriceapply.setTheme(theme);
				rpriceapply.setRecordTime(new Date());
				pmsmanageService.save(rpriceapply);
				/*if(stype.equals(CommonConstants.SystemType.Branch)){
					int priority = 1;
					SysParam param = (SysParam) PmsmanageService.findOneByProperties(SysParam.class, "paramType","REMOTE_PATH", "status", "1");
					List<RoomPrice> roompricelList = new ArrayList<RoomPrice>();
					roompricelList.add(rpriceapply);
					Map<String, Object> roompriceMap = new HashMap<String, Object>();
					roompriceMap.put("RoomPrice", roompricelList);
					TransferServcie.getInstance().transferData(priority, param.getContent(),JSONUtil.fromObject(roompriceMap).toString());
					}*/
			}else{
				if((status.equals(CommonConstants.RoomPriceStatus.basic))&&(statuspriceapply.equals(CommonConstants.RpApplyStatus.RpActive))){
					RoomPrice basicroomorice = (RoomPrice) pmsmanageService.findOneByProperties(RoomPrice.class, "theme",theme, "roomPriceId.branchId", branchid, "roomPriceId.rpId", rpid, "roomPriceId.roomType", rptypename, "roomPriceId.rpKind", rpkind, "roomPriceId.status", status);
					RoomPriceId roompriceid = new RoomPriceId();
					roompriceid.setBranchId(branchid);
					roompriceid.setRoomType(rptypename);
					roompriceid.setRpId(rpid);
					roompriceid.setRpKind(rpkind);
					roompriceid.setStatus(status);
					basicroomorice.setRoomPriceId(roompriceid);
					basicroomorice.setDiscount(d);
					basicroomorice.setMemberRank(memberrank);
					basicroomorice.setPackId(packid);
					basicroomorice.setRecordUser(recorduser);
					basicroomorice.setRoomPrice(price);
					basicroomorice.setRpName(rpname);
					basicroomorice.setRpType("1");
					basicroomorice.setState(CommonConstants.RoomPriceState.active);
					basicroomorice.setTheme(theme);
					basicroomorice.setRecordTime(new Date());
					pmsmanageService.update(basicroomorice);
					/*if(stype.equals(CommonConstants.SystemType.Branch)){
						int priority = 1;
						SysParam param = (SysParam) PmsmanageService.findOneByProperties(SysParam.class, "paramType","REMOTE_PATH", "status", "1");
						List<RoomPrice> roompriceupdatelList = new ArrayList<RoomPrice>();
						roompriceupdatelList.add(basicroomorice);
						Map<String, Object> roompriceupdateMap = new HashMap<String, Object>();
						roompriceupdateMap.put("RoomPrice", roompriceupdatelList);
						TransferServcie.getInstance().transferData(priority, param.getContent(),JSONUtil.fromObject(roompriceupdateMap).toString());
						}*/
					
				}
				//将价格存到新的表中
				
				
				
				
//				AdjustPriceController.saveHotelPrice2newTable(branchid,validstartdata,validenddata,price,validday,ruleid,theme
//						,rpid,rptypename,rpkind);
//				
//				
			}
			
			
			
			
		}
	
		JSONUtil.responseJSON(response, new AjaxResult(1, null));
	}

	




	/*
	 * 自定义房型图片新增页面跳转
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/addRoomTypePicture.do")
	public ModelAndView addRoomTypePicture(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("page/pmsmanage/roomType/addRoomTypePicture");
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginuser.getStaff().getBranchId();
		Branch branch = (Branch) pmsmanageService.findOneByProperties(Branch.class, "branchId", branchId);
		String branchRank = branch.getRank();
		mv.addObject("branchRank", branchRank);
		String theme = request.getParameter("theme");
		String branchIdTrue = request.getParameter("branchId");

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
			branchList = pmsmanageService.findAll(House.class);
		} else {
			branchList = pmsmanageService.findByProperties(Branch.class, "branchType", themeId);
		}
		String roomType = request.getParameter("roomType");
		mv.addObject("roomType", roomType);
		mv.addObject("branchIdTrue", branchIdTrue);
		mv.addObject("theme", theme);
		mv.addObject("LoginBranchId", branchId);
		mv.addObject("branchList", branchList);
		return mv;
	}


	@RequestMapping("/validateRoomType.do")
	public void validateRoomType(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String branchId = request.getParameter("brId");
		String roomType = request.getParameter("roomType");
		List<?> roomTypeList = pmsmanageService.findByProperties(RoomType.class, "roomTypeKey.branchId", branchId,
				"roomTypeKey.roomType", roomType,"status","1");
		if (roomTypeList.size() == 0) {
			JSONUtil.responseJSON(response, new AjaxResult(0, null));
		} else {
			JSONUtil.responseJSON(response, new AjaxResult(1, null));
		}
	}

	// 房型头图新增
	@RequestMapping("/addRoomTypePic.do")
	public void addBranchHeadPicData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		SysParam sysParam = (SysParam) pmsmanageService.findOneByProperties(SysParam.class, "paramType", "SYSTEMTYPE");
		String systemType = sysParam.getContent();
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String staffId = loginuser.getStaff().getStaffId();
		String branchId = loginuser.getStaff().getBranchId();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile srcFile = multipartRequest.getFile("branchHeadPic");
		String roomType = multipartRequest.getParameter("roomType");
		String originalFilename = srcFile.getOriginalFilename();
		String branchIdChoose = multipartRequest.getParameter("branchId");
		if (branchIdChoose == null) {
			branchIdChoose = branchId;
		}
		String fileName = null;
		FileOutputStream fos = null;
		InputStream is = null;
		String headPicUrl = "";
		SysParam param = (SysParam) pmsmanageService.findOneByProperties(SysParam.class, "paramType",
				SystemConstants.ParamType.BRANCH_IP, "paramName", "1");
		RoomPicture roomPic = (RoomPicture) pmsmanageService.findOneByProperties(RoomPicture.class, "branchId",
				branchIdChoose, "style", CommonConstants.RoomPicStyle.ROOMTYPE_HEAD_PIC, "status",
				CommonConstants.STATUS.NORMAL, "roomType", roomType);
		int priorityFile = 1;
		int priority = 1;

		if (roomPic != null) {
			roomPic.setStatus(CommonConstants.STATUS.WASTED);
			pmsmanageService.update(roomPic);
			
			/*if(CommonConstants.SystemType.Branch.equals(systemType.trim())){
				List<RoomPicture> headPictureList = new ArrayList<RoomPicture>();
				headPictureList.add(roomPic);
				Map<String, Object> headPictureMap = new HashMap<String, Object>();
				headPictureMap.put("RoomPicture", headPictureList);
				TransferServcie.getInstance().transferData(priority, param.getContent(),
						JSONUtil.fromObject(headPictureMap).toString());
			}*/

			String picId = roomPic.getPictureId();
			Picture pic = (Picture) pmsmanageService.findOneByProperties(Picture.class, "pictureId", picId);
			if (pic != null) {
				pic.setStatus(CommonConstants.STATUS.WASTED);
				pmsmanageService.update(pic);
				
				/*if(CommonConstants.SystemType.Branch.equals(systemType.trim())){
					List<Picture> headPictureWastedList = new ArrayList<Picture>();
					headPictureWastedList.add(pic);
					Map<String, Object> headPictureWastedMap = new HashMap<String, Object>();
					headPictureWastedMap.put("Picture", headPictureWastedList);
					TransferServcie.getInstance().transferData(priority, param.getContent(),
							JSONUtil.fromObject(headPictureWastedMap).toString());
				}*/
			}

		}
		if ("".equals(originalFilename.trim())) {

		} else {
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
				headPicUrl = CommonConstants.Domain.DOMAINNAME + File.separator + fileName;
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
		}
		// 文件同步
		/*SysParam paramFile = (SysParam) pmsmanageService.findOneByProperties(SysParam.class, "paramType",
				SystemConstants.ParamType.BRANCH_IP, "paramName", "FILE");

		// priority = Integer.parseInt(member.getMemberRank());
		List<String> fileNameList = new ArrayList<String>();
		fileNameList.add(fileName);
		Map<String, Object> fileNameMap = new HashMap<String, Object>();
		fileNameMap.put("FileName", fileNameList);
		TransferServcie.getInstance().transferFile(priorityFile, paramFile.getContent(),
				JSONUtil.fromObject(fileNameMap).toString());*/

		// 房型头图数据新增
		String seqName = "SEQ_PIC_ID";
		String sequence = pmsmanageService.getCloudSequence(seqName);
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
		pmsmanageService.save(rp);
		
		/*if(CommonConstants.SystemType.Branch.equals(systemType.trim())){
			// priority = Integer.parseInt(member.getMemberRank());
			List<RoomPicture> headPictureList = new ArrayList<RoomPicture>();
			headPictureList.add(rp);
			Map<String, Object> headPictureMap = new HashMap<String, Object>();
			headPictureMap.put("RoomPicture", headPictureList);
			TransferServcie.getInstance().transferData(priority, param.getContent(),
					JSONUtil.fromObject(headPictureMap).toString());
		}*/

		Picture picture = new Picture();
		picture.setPictureId(picId);
		picture.setPictureStyle(picTrueStyle);
		picture.setRecordTime(new Date());
		picture.setRecordUser(staffId);
		picture.setStatus(status);
		picture.setPictureUrl(headPicUrl);
		pmsmanageService.save(picture);
		
		/*if(CommonConstants.SystemType.Branch.equals(systemType.trim())){
			// priority = Integer.parseInt(member.getMemberRank());
			List<Picture> headPictureTrueList = new ArrayList<Picture>();
			headPictureTrueList.add(picture);
			Map<String, Object> headTruePictureMap = new HashMap<String, Object>();
			headTruePictureMap.put("Picture", headPictureTrueList);
			TransferServcie.getInstance().transferData(priority, param.getContent(),
					JSONUtil.fromObject(headTruePictureMap).toString());
		}*/

		JSONUtil.responseJSON(response, new AjaxResult(1, null));
	}

	/*
	 * 房型内景图片新增（批量）
	 */
	@SuppressWarnings({ "unused", "static-access" })
	@RequestMapping("/submitPics.do")
	public void addToomTypePics(HttpServletRequest request, HttpServletResponse response) throws Exception {
		SysParam sysParam = (SysParam) pmsmanageService.findOneByProperties(SysParam.class, "paramType", "SYSTEMTYPE");
		String systemType = sysParam.getContent();
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String staffId = loginuser.getStaff().getStaffId();
		String branchId = loginuser.getStaff().getBranchId();
		Branch branch = (Branch) pmsmanageService.findOneByProperties(Branch.class, "branchId", branchId);
		String branchRank = branch.getRank();
		if(branchRank.equals("0")){
			JSONUtil.responseJSON(response, new AjaxResult(0, null));
			return;
		}
		String branchIdChoose = request.getParameter("branchId");
		String imgBase = request.getParameter("imgBase");
		String imgFormat = request.getParameter("imgFormat");
		String lookIndex = request.getParameter("lookIndex");
		String IMG_ROUTE = request.getParameter("IMG_ROUTE");
		String roomType = request.getParameter("roomType");
		if (branchIdChoose == null) {
			branchIdChoose = branchId;
		}
		String base64StringArr[] = imgBase.split(",");
		String base64String = base64StringArr[1];
		String subString[] = IMG_ROUTE.split("\\\\");
		String filename = "";
		if (subString.length > 1) {
			filename = subString[subString.length - 1];
				String filenameMillis = String.valueOf(System.currentTimeMillis());
			filename = filenameMillis+filename.substring(filename.lastIndexOf("."));
		}

		String tempSavePath = RequestUtil.getWebPath(request);
		// 临时文件路径
		File srcFolder = new File(tempSavePath + File.separator + "upload");
		if (!srcFolder.exists()) {
			srcFolder.mkdirs();
		}
		String targetFileRule = srcFolder.getAbsolutePath() + File.separator + filename;
		String url = CommonConstants.Domain.DOMAINNAME+File.separator + filename;
		File tarFile = new File(targetFileRule);
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
		String sequence = pmsmanageService.getCloudSequence(seqName);
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
		pmsmanageService.save(rp);

		SysParam param = (SysParam) pmsmanageService.findOneByProperties(SysParam.class, "paramType",
				SystemConstants.ParamType.BRANCH_IP, "paramName", "1");
		
		
		int priority = 2;
		/*if(CommonConstants.SystemType.Branch.equals(systemType.trim())){
			// priority = Integer.parseInt(member.getMemberRank());
			List<RoomPicture> list = new ArrayList<RoomPicture>();
			list.add(rp);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("RoomPicture", list);
			TransferServcie.getInstance().transferData(priority, param.getContent(), JSONUtil.fromObject(map).toString());
		}*/

		Picture picture = new Picture();
		picture.setPictureId(picId);
		picture.setPictureStyle(picTrueStyle);
		picture.setRecordTime(new Date());
		picture.setRecordUser(staffId);
		picture.setStatus(status);
		picture.setPictureUrl(url);
		pmsmanageService.save(picture);
		
		/*if(CommonConstants.SystemType.Branch.equals(systemType.trim())){
			// priority = Integer.parseInt(member.getMemberRank());
			List<Picture> list1 = new ArrayList<Picture>();
			list1.add(picture);
			Map<String, Object> mapPicture = new HashMap<String, Object>();
			mapPicture.put("Picture", list1);
			TransferServcie.getInstance().transferData(priority, param.getContent(),
					JSONUtil.fromObject(mapPicture).toString());
		}*/

		// 文件同步
		/*SysParam paramFile = (SysParam) pmsmanageService.findOneByProperties(SysParam.class, "paramType",
				SystemConstants.ParamType.BRANCH_IP, "paramName", "FILE");

		int priorityFile = 1;
		// priority = Integer.parseInt(member.getMemberRank());
		List<String> fileNameList = new ArrayList<String>();
		fileNameList.add(filename);
		Map<String, Object> fileNameMap = new HashMap<String, Object>();
		fileNameMap.put("FileName", fileNameList);
		TransferServcie.getInstance().transferFile(priorityFile, paramFile.getContent(),
				JSONUtil.fromObject(fileNameMap).toString());
*/
		JSONUtil.responseJSON(response, new AjaxResult(1, null));

	}



	/*
	 * // 自定义房租价删除
	 * 
	 * @RequestMapping("/delRoomPrice.do") public void
	 * delRoomPrice(HttpServletRequest request, HttpServletResponse response,
	 * String theme, String branchid, String roomtype, String rpid) throws
	 * UnknownHostException { LoginUser loginUser = (LoginUser)
	 * request.getSession
	 * (true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY); Staff staff =
	 * loginUser.getStaff(); String recordUser = staff.getStaffId(); String[]
	 * themeids = theme.split(","); String[] branchids = branchid.split(",");
	 * String[] roomtypes = roomtype.split(","); String[] rpids =
	 * rpid.split(","); for (int i = 0; i < rpids.length; i++) { SysParam
	 * sysParam = ((SysParam)
	 * (PmsmanageService.findOneByProperties(SysParam.class, "paramType",
	 * "THEME", "paramName", themeids[i]))); String themeid =
	 * sysParam.getContent().toString(); Branch branchId = (Branch)
	 * PmsmanageService.findOneByProperties(Branch.class, "branchName",
	 * branchids[i]); String branchiD = branchId.getBranchId(); RoomType
	 * roomType = ((RoomType)
	 * (PmsmanageService.findOneByProperties(RoomType.class, "roomName",
	 * roomtypes[i], "theme", themeid))); String roomtypeid =
	 * roomType.getRoomTypeKey().getRoomType().toString(); String rpId =
	 * rpids[i]; PmsmanageService.upateroomprice(themeid, branchiD, roomtypeid,
	 * rpId, recordUser);
	 * 
	 * } JSONUtil.responseJSON(response, new AjaxResult(1, null)); }
	 */

	// 自定义金柜删除
	@RequestMapping("/delCash.do")
	public void delCash(HttpServletRequest request, HttpServletResponse response, String branchId, String cashBox)
			throws UnknownHostException {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginUser.getStaff();
		String recordUser = staff.getStaffId();
		String[] branchids = branchId.split(",");
		String[] cashboxs = cashBox.split(",");
		for (int i = 0; i < branchids.length; i++) {
			SysParam sysParam = ((SysParam) (pmsmanageService.findOneByProperties(SysParam.class, "paramType",
					"CASHBOX", "paramName", cashboxs[i])));
			String cashboxid = sysParam.getContent().toString();
			Branch BranchId = (Branch) pmsmanageService.findOneByProperties(Branch.class, "branchName", branchids[i]);
			String branchiD = BranchId.getBranchId();
			pmsmanageService.upatecashbox(cashboxid, branchiD, recordUser);

		}
		JSONUtil.responseJSON(response, new AjaxResult(1, null));
	}

	@RequestMapping("/rpInitialize.do")
	public void rpInitialize(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String branchId = loginuser.getStaff().getBranchId();
		Branch branchid = (Branch) pmsmanageService.findOneByProperties(Branch.class, "branchId", branchId);
		String theme = branchid.getBranchType();
		List<?> rpInitializejudge = pmsmanageService.getRpInitializejudge(branchId, theme);
		String a = null;
		List<?> rpsetup = pmsmanageService.getRpsetup();
		List<?> rpbranchid = pmsmanageService.getRpbranchid();
		List<?> rproomtype = pmsmanageService.getRproomtype(branchId);
		String discountdata = ((Map<?, ?>) rpsetup.get(0)).get("DISCOUNT") == null ? "" : ((Map<?, ?>) rpsetup.get(0))
				.get("DISCOUNT").toString();
		if (null == discountdata || discountdata.equals("")) {
			a = "请先去配置会员等级等相关数据！";
		} else if (null == rpbranchid || rpbranchid.size() == 0) {
			a = "请先去配置门店等相关数据！";
		} else if (null == rproomtype || rproomtype.size() == 0) {
			a = "请先去配置房型等相关数据！";
		} else if (null == rpInitializejudge || rpInitializejudge.size() == 0) {
			List<?> rproomtypeInitialize = pmsmanageService.getRproomtypeInitialize(theme, branchId);
			String rpKind = null;
			if (theme.equals("1")) {
				rpKind = "1";
				for (int i = 0; i < rproomtypeInitialize.size(); i++) {
					String roomtype = ((Map<?, ?>) rproomtypeInitialize.get(i)).get("ROOMTYPE").toString();
					List<?> rpidInitialize = auditService.getRpidInitialize();
					Double romprice = Double.parseDouble("200");
					for (int j = 0; j < rpidInitialize.size(); j++) {
						String discount = ((Map<?, ?>) rpidInitialize.get(j)).get("DISCOUNT").toString();
						String rpid = ((Map<?, ?>) rpidInitialize.get(j)).get("PARAMNAME").toString();
						String rpname = ((Map<?, ?>) rpidInitialize.get(j)).get("PARAMDESC").toString();
						String memberrank = ((Map<?, ?>) rpidInitialize.get(j)).get("CONTENT").toString();
						Double d = new Double(discount) / 100;
						Format f = new DecimalFormat("0.00");
						Double dscount = Double.parseDouble(f.format(d));
						RoomPrice rprice = new RoomPrice();
						RoomPriceId roompriceid = new RoomPriceId();
						roompriceid.setBranchId(branchId);
						roompriceid.setRoomType(roomtype);
						roompriceid.setRpId(rpid);
						roompriceid.setRpKind("2");
						roompriceid.setStatus("1");
						rprice.setRoomPriceId(roompriceid);
						rprice.setRpName(rpname);
						rprice.setRoomPrice(romprice * dscount);
						rprice.setRpType("1");
						rprice.setDiscount(dscount);
						rprice.setRecordTime(new Date());
						rprice.setRecordUser(staff.getStaffId());
						rprice.setMemberRank(memberrank);
						rprice.setTheme(theme);
						rprice.setState("5");
						pmsmanageService.save(rprice);

					}
				}

			} else if (theme.equals("2")) {
				rpKind = "3";
			} else if (theme.equals("3")) {

			}
			for (int i = 0; i < rproomtypeInitialize.size(); i++) {
				String roomtype = ((Map<?, ?>) rproomtypeInitialize.get(i)).get("ROOMTYPE").toString();
				List<?> rpidInitialize = auditService.getRpidInitialize();
				Double romprice = Double.parseDouble("200");
				for (int j = 0; j < rpidInitialize.size(); j++) {
					String discount = ((Map<?, ?>) rpidInitialize.get(j)).get("DISCOUNT").toString();
					String rpid = ((Map<?, ?>) rpidInitialize.get(j)).get("PARAMNAME").toString();
					String rpname = ((Map<?, ?>) rpidInitialize.get(j)).get("PARAMDESC").toString();
					String memberrank = ((Map<?, ?>) rpidInitialize.get(j)).get("CONTENT").toString();
					Double d = new Double(discount) / 100;
					Format f = new DecimalFormat("0.00");
					Double dscount = Double.parseDouble(f.format(d));
					RoomPrice rprice = new RoomPrice();
					RoomPriceId roompriceid = new RoomPriceId();
					roompriceid.setBranchId(branchId);
					roompriceid.setRoomType(roomtype);
					roompriceid.setRpId(rpid);
					roompriceid.setRpKind(rpKind);
					roompriceid.setStatus("1");
					rprice.setRoomPriceId(roompriceid);
					rprice.setRpName(rpname);
					rprice.setRoomPrice(romprice * dscount);
					rprice.setRpType("1");
					rprice.setDiscount(dscount);
					rprice.setRecordTime(new Date());
					rprice.setRecordUser(staff.getStaffId());
					rprice.setMemberRank(memberrank);
					rprice.setTheme(theme);
					rprice.setState("5");
					pmsmanageService.save(rprice);
				}
			}

		} else {
			a = "当前门店房价数据已配置，无须初始化";
		}
		JSONUtil.responseJSON(response, new AjaxResult(1, a));
	}



	// 备用金投缴页面
	@RequestMapping("/turnPettywithPay.do")
	public ModelAndView turnPettywithPay(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		Pagination pagination = SqlBuilder.buildPagination(request);
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginuser.getStaff().getBranchId();
		Staff staff = loginuser.getStaff();
		String recordUser = staff.getStaffId();
		Staff staffdata = (Staff) pmsmanageService.findOneByProperties(Staff.class, "staffId", recordUser);
		String usepost = staffdata.getPost().toString();
		Post post = (Post) pmsmanageService.findOneByProperties(Post.class, "postId", usepost);
		String postname = post.getPostName();
		if (branchId.equals(SystemConstants.User.BRANCH_ID)) {
			branchId = "%";
		}
		if ((recordUser.equals(SystemConstants.User.ADMIN_ID)) || postname.equals(CommonConstants.Postgrade.STORMANAGE)) {
			recordUser = "%";
		}
		List<?> pettypaydata = pmsmanageService.getPettypaydata(pagination, branchId, recordUser);
		mv.setViewName("/page/pmsmanage/pettypay/pettypay");
		mv.addObject("pettypaydata", pettypaydata);
		mv.addObject("pagination", pagination);
		return mv;
	}

	// 投缴
	@RequestMapping("/payData.do")
	public void payData(HttpServletRequest request, HttpServletResponse response, String logid, String currpay,
			String voucherpay) throws Exception {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		PettyCash updatepetty = (PettyCash) pmsmanageService.findOneByProperties(PettyCash.class, "logId", logid);
		updatepetty.setVoucher(voucherpay);
		updatepetty.setState("1");
		OperateLog operatelog = new OperateLog();
		String sequences = this.pmsmanageService.getSequence("SEQ_OPERATELOG_ID", null);
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		String strdate = sdf.format(new Date());
		InetAddress address = InetAddress.getLocalHost();// 获取的是本地的IP地址
		// //PC-20140317PXKX/192.168.0.121
		String operid = address.getHostAddress();// 192.168.0.121
		// String operid = InetAddress.getLocalHost().toString();// IP地址
		String logId = strdate + loginUser.getStaff().getBranchId() + sequences;
		String content = loginUser.getStaff().getStaffName() + "操作投缴，金额为：" + currpay;
		operatelog.setLogId(logId);
		operatelog.setOperType("pettypay");
		operatelog.setOperModule("投缴");
		operatelog.setContent(content);
		operatelog.setRecordUser(loginUser.getStaff().getStaffId());
		operatelog.setRecordTime(new Date());
		operatelog.setOperIp(operid);
		operatelog.setBranchId(loginUser.getStaff().getBranchId());
		pmsmanageService.save(operatelog);
		JSONUtil.responseJSON(response, new AjaxResult(1, null));
	}
	
	
	

	

}
