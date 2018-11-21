package com.ideassoft.crm.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.core.FileURIResolver;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.hsqldb.lib.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.ideassoft.bean.Bill;
import com.ideassoft.bean.Campaign;
import com.ideassoft.bean.Contrart;
import com.ideassoft.bean.CouponGroup;
import com.ideassoft.bean.Member;
import com.ideassoft.bean.MemberAccount;
import com.ideassoft.bean.MemberCoupon;
import com.ideassoft.bean.MemberRank;
import com.ideassoft.bean.RoomType;
import com.ideassoft.bean.SysParam;
import com.ideassoft.core.ajax.AjaxResult;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.constants.CommonConstants;
import com.ideassoft.core.constants.SystemConstants;
import com.ideassoft.core.notifier.SmsSingleSender;
import com.ideassoft.core.page.Pagination;
import com.ideassoft.crm.service.LogService;
import com.ideassoft.crm.service.MemberService;
import com.ideassoft.crm.service.TransferServcie;
import com.ideassoft.portal.DataDealPortalService;
import com.ideassoft.portal.IDataDealPortal;
import com.ideassoft.util.DateUtil;
import com.ideassoft.util.JSONUtil;
import com.ideassoft.util.MD5Util;
import com.ideassoft.util.ReflectUtil;
import com.ideassoft.util.RequestUtil;

@Transactional
@Controller
public class MemberController {

	@Autowired
	private MemberService memberService;

	@Autowired
	private LogService logService;

	

	


	// pms处新增会员购卡
	@RequestMapping("/payUpGradeMemberLevelInPms.do")
	public ModelAndView payUpGradeMemberLevelInPms(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/ipmspage/member/memberLevelInPms");
		return mv;
	}

	

	@RequestMapping("/queryMemberInfo.do")
	public ModelAndView queryMemberInfo(HttpServletRequest request,
			HttpServletResponse response, String msoidcard) {
		ModelAndView mv = new ModelAndView();
		String gendor = "";
		if (!StringUtil.isEmpty(msoidcard)) {
			if (msoidcard.length() > 11) {
				/*Member member = (Member) memberService.findOneByProperties(
						Member.class, "idcard", msoidcard);*/
				Member member = null;
				List<Member> memList = memberService.findByProperties(Member.class, "idcard", msoidcard);
				for(int i = 0;i<memList.size();i++){
					if(!"1".equals(memList.get(i).getMemberRank())){
						member = memList.get(i);
					}
				}
				MemberAccount memberAccount = (MemberAccount) memberService
						.findOneByProperties(MemberAccount.class, "memberId",
								member.getMemberId());
				MemberRank rank = (MemberRank) memberService
						.findOneByProperties(MemberRank.class, "memberRank",
								member.getMemberRank());
				switch (Integer.parseInt(member.getGendor())) {
				case 0:
					gendor = "女";
					mv.addObject("gendor", gendor);
				case 1:
					gendor = "男";
					mv.addObject("gendor", gendor);
				default:
				}
				mv.addObject("member", member);
				mv.addObject("memberAccount", memberAccount);
				mv.addObject("rank", rank);
				mv.setViewName("page/ipmspage/member/memberpage");
			} else {
				/*Member member = (Member) memberService.findOneByProperties(
						Member.class, "mobile", msoidcard);*/
				Member member = null;
				List<Member> memList = memberService.findByProperties(Member.class, "mobile", msoidcard);
				for(int i = 0;i<memList.size();i++){
					if(!"1".equals(memList.get(i).getMemberRank())){
						member = memList.get(i);
					}
				}
				MemberAccount memberAccount = (MemberAccount) memberService
						.findOneByProperties(MemberAccount.class, "memberId",
								member.getMemberId());
				MemberRank rank = (MemberRank) memberService
						.findOneByProperties(MemberRank.class, "memberRank",
								member.getMemberRank());
				switch (Integer.parseInt(member.getGendor())) {
				case 0:
					gendor = "女";
					mv.addObject("gendor", gendor);
					break;
				case 1:
					gendor = "男";
					mv.addObject("gendor", gendor);
					break;
				default:
				}
				mv.addObject("member", member);
				mv.addObject("memberAccount", memberAccount);
				mv.addObject("rank", rank);
				mv.setViewName("page/ipmspage/member/memberpage");
			}
		} else {
			mv.setViewName("page/ipmspage/member/memberpagenull");
		}
		return mv;
	}

	@RequestMapping("/changeApartmentRent.do")
	public void apartmentRent(HttpServletRequest request, HttpServletResponse response, String contractId) throws IOException {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Contrart contrart = (Contrart) memberService.findById(Contrart.class, contractId);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(contrart.getContrartEndTime());
		calendar.add(Calendar.MONTH, Integer.parseInt(contrart.getTypeOfPayment()));
		contrart.setContrartEndTime(calendar.getTime());
		contrart.setRecordTime(new Date());
		
		Bill bill = new Bill();
		String billId = DateUtil.currentDate("yyMMdd") + contrart.getBranchId() + "3" + memberService.getSequence("SEQ_NEW_BILL");
		bill.setBillId(billId);
		bill.setBranchId(contrart.getBranchId());
		bill.setCheckId(contrart.getContrartId());
		bill.setProjectId("2004");
		bill.setProjectName("房租");
		bill.setDescribe("交租");
		bill.setPayment("2");
		bill.setCost(0.0);
		bill.setPay(Double.parseDouble(contrart.getTypeOfPayment()) * Double.parseDouble(contrart.getUnitPrice()));
		bill.setStatus("1");
		bill.setRecordTime(new Date());
		bill.setRecordUser(loginUser.getStaff().getStaffId());
		
		logService.changeApartmentRent((LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY), contrart.getRoomId(), contrart.getTypeOfPayment(),request);
		memberService.saveIfo(bill);
		memberService.update(contrart);
		String result = "{\"code\":\"1\"}";
		JSONUtil.responseJSON(response, result);
	}

	//@RequestMapping("/updateContrart.do")
	public ModelAndView updateContrart(HttpServletRequest request,
			String contractId) throws Exception {
		ModelAndView mv = new ModelAndView();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Contrart contrart = (Contrart) memberService.findById(Contrart.class,
				contractId);
		String starttime = sdf.format(contrart.getStartTime());
		String endtime = sdf.format(contrart.getEndTime());
		String contrartEndTime = sdf.format(contrart.getContrartEndTime());
		Member member = (Member) memberService.findById(Member.class, contrart
				.getMemberId());
		RoomType roomType = (RoomType) memberService.findOneByProperties(
				RoomType.class, "roomType", contrart.getRoomType());
		mv.addObject("contrart", contrart);
		mv.addObject("contrartId", contractId);
		mv.addObject("starttime", starttime);
		mv.addObject("contrartEndTime", contrartEndTime);
		mv.addObject("endtime", endtime);
		mv.addObject("memberName", member.getMemberName());
		mv.addObject("roomType", roomType.getRoomName());
		mv.setViewName("page/crm/member/addContrart");
		return mv;
	}
	
	// 查合同人
	@RequestMapping("/contrartselectmember.do")
	public ModelAndView contrartselectmember(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/crm/member/addContrartmemberDialog");
		return mv;
	}
	
	//合同人详情页数据
	@RequestMapping("/querycontrartmemberinfo.do")
	public void querycontrartmemberinfo(HttpServletRequest request, HttpServletResponse response, String groupId)
			throws Exception {
		Pagination pagination;
		Integer pageNum = Integer.parseInt(request.getParameter("page"));
		Integer rows = Integer.parseInt(request.getParameter("rows"));
		pagination = new Pagination(pageNum, rows);
		List<?> result = memberService.findBySQLWithPagination("allmemberinfo", new String[] {groupId,groupId}, pagination, true);
		JSONUtil.responseJSON(response, JSONUtil.buildReportJSONByPagination(result, pagination));
	}	
	
	/**
	 * 检验身份证唯一
	 * @param request
	 * @param response
	 * @param idcard
	 */
	@RequestMapping("/uniqueIdcard.do")
	public void uniqueIdcard (HttpServletRequest request, HttpServletResponse response, String idcard) {
		//Member member = (Member) memberService.findOneByProperties(Member.class, "idcard", idcard, "status", "1");
		List<?> memberList = memberService.findBySQL("selmeb4contrart", new String[] {idcard},true);
		if(memberList.size() > 0){
			JSONUtil.responseJSON(response, new AjaxResult(0, "身份证重复!"));
		} else {
			JSONUtil.responseJSON(response, new AjaxResult(1, "可以注册"));
		}
	}
	
	
	@RequestMapping("/checkMemberImage.do")
	public void checkMemberImage (HttpServletRequest request, HttpServletResponse response, String memberId) throws IOException {
		Member member = (Member) memberService.findById(Member.class, memberId);
		if(null != member){
			String webPath = RequestUtil.getWebPath(request);
			String path = webPath.replace("\\","/") + "/upload/";
			String name = member.getPhotos().substring(member.getPhotos().lastIndexOf("/") + 1);
			java.io.File myFilePath = new java.io.File(path + name);
			if (myFilePath.exists()) {
				myFilePath.delete();
			}
			String headPicUrl = moHoSaveMemberImage(request, response);
			member.setPhotos(headPicUrl);
			memberService.update(member);
			JSONUtil.responseJSON(response, new AjaxResult(1, headPicUrl));
		} else {
			JSONUtil.responseJSON(response, new AjaxResult(0, "头像替换失败"));
		}

	}
	
	@RequestMapping("/checkMemberImageMoho.do")
	public void checkMemberImageMoho (HttpServletRequest request, HttpServletResponse response, String memberId) throws IOException {
		Member member = (Member) memberService.findById(Member.class, memberId);
		String name = "";
		if(null != member){
			String webPath = RequestUtil.getWebPath(request);
			String path = webPath.replace("\\","/") + "/upload/";
			if (member.getPhotos() == null) {
				SimpleDateFormat sf = new SimpleDateFormat("yyMMdd");
				name = sf.format(new Date()) + memberService.getSequence("SEQ_PIC_ID");
			} else {
				name = member.getPhotos().substring(member.getPhotos().lastIndexOf("/") + 1);
			}
			java.io.File myFilePath = new java.io.File(path + name);
			if (myFilePath.exists()) {
				myFilePath.delete();
			}
			String headPicUrl = moHoSaveMemberImage(request, response);
			member.setPhotos(headPicUrl);
			memberService.update(member);
			JSONUtil.responseJSON(response, new AjaxResult(1, headPicUrl));
		} else {
			JSONUtil.responseJSON(response, new AjaxResult(0, "头像替换失败"));
		}
	}
	
	public String moHoSaveMemberImage(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String fileName = null;
		FileOutputStream fos = null;
		InputStream is = null;
		String headPicUrl = "";
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile srcFile = multipartRequest.getFile("file");
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
		return headPicUrl;
	}
}