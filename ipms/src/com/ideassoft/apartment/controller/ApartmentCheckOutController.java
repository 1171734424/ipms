package com.ideassoft.apartment.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ideassoft.apartment.service.ApartmentAuditService;
import com.ideassoft.apartment.service.ApartmentCheckOutService;
import com.ideassoft.apartment.service.ApartmentCommonService;
import com.ideassoft.apartment.service.ApartmentLogServiceInPms;
import com.ideassoft.bean.Branch;
import com.ideassoft.bean.CheckOut;
import com.ideassoft.bean.Contrart;
import com.ideassoft.bean.Member;
import com.ideassoft.bean.SysParam;
import com.ideassoft.core.annotation.interfaces.RightMethodControl;
import com.ideassoft.core.annotation.interfaces.RightModelControl;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.constants.CommonConstants;
import com.ideassoft.core.constants.RightConstants;
import com.ideassoft.core.constants.SystemConstants;
import com.ideassoft.core.notifier.SmsSingleSender;
import com.ideassoft.core.notifier.SmsSingleSenderResult;
import com.ideassoft.core.page.Pagination;
import com.ideassoft.util.JSONUtil;
import com.ideassoft.util.ReflectUtil;
import com.ideassoft.util.RegExUtil;
import com.ideassoft.util.RequestUtil;

@Transactional
@Controller
@RightModelControl(rightModel = RightConstants.RightModel.APARTMENT_CONTROL)
public class ApartmentCheckOutController {

	@Autowired
	private ApartmentCheckOutService apartmentCheckOutService;

	@Autowired
	private ApartmentCommonService commonService;

	@Autowired
	private ApartmentLogServiceInPms logServiceInPms;
	
	@Autowired
	private ApartmentAuditService auditService;

	/**
	 * 退房申请(公寓)-显示退房申请页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/apartmentCheckOut.do")
	@RightMethodControl(rightType = RightConstants.RightType.APA_CHECK)
	public ModelAndView apartmentCheckOut(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/apartment/apartmentLeftmenu/apartmentcheckout/apartmentcheckout");
		return mv;
	}
	
	/**
	 * 显示添加退房申请页面
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addCheckOut.do")
	public ModelAndView addCheckOut(HttpServletRequest request,String contractId,String contractName, String mphone,String roomtype,String roomid)
			throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.addObject("contractId",contractId);
		mv.addObject("contractName", contractName);
		mv.addObject("mphone", mphone);
		mv.addObject("roomtype", roomtype);
		mv.addObject("roomid", roomid);
		mv.setViewName("page/apartment/apartmentLeftmenu/apartmentcheckout/addCheckOut");
		return mv;
	}

	/**
	 * 退房申请(公寓)-查询已被审核同意过的退房申请
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/queryApartmentCheckOut.do")
	public ModelAndView queryApartmentCheckOut(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		Pagination pagination = (Pagination) ReflectUtil.setBeanFromRequest(request, Pagination.class);
		if (pagination != null && pagination.getPageNum() == null) {
			pagination.setPageNum(1);
			pagination.setRows(17);
		}
		String orderuser = request.getParameter("orderuser");
		String ordertime = request.getParameter("ordertime");
		String roomId = request.getParameter("roomId");
		String dispose = request.getParameter("dispose");
		LoginUser loginUser = (LoginUser) request.getSession(true)
				.getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		List<CheckOut> list = apartmentCheckOutService.findBySQLWithPagination("apartmentCheckOut", new String[] { branchId, orderuser,ordertime, roomId, dispose }, pagination, true);
		mv.addObject("list", list);
		mv.addObject("orderuser", orderuser);
		mv.addObject("ordertime", ordertime);
		mv.addObject("roomId", roomId);
		mv.addObject("pagination", pagination);
		mv.setViewName("page/apartment/apartmentLeftmenu/apartmentcheckout/apartmentcheckoutinfo");
		return mv;
	}
	
	/**
	 * 退房申请(公寓)-处理退房申请
	 * 
	 * @param request
	 * @param response
	 * @param 例如
	 *            :17090530000110045 合同表ID,17090730000110126 退房申请表ID
	 * @throws Exception
	 */
	@RequestMapping("/checkApartmentCheckOut.do")
	public void checkApartmentCheckOut(HttpServletRequest request,HttpServletResponse response, String Id) throws Exception {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String[] Ids = Id.split(",");
		//String contractId = Ids[0];
		String checkOutId = Ids[1];
		CheckOut checkOut = (CheckOut) apartmentCheckOutService.findOneByProperties(CheckOut.class, "checkOutId", checkOutId);
		checkOut.setDispose("1");
		checkOut.setRecordUser(loginUser.getStaff().getStaffId());
//		Contrart contrart = (Contrart) apartmentCheckOutService
//				.findOneByProperties(Contrart.class, "contrartId", contractId);
//		contrart.setStatus("2");
		apartmentCheckOutService.update(checkOut);
		//apartmentCheckOutService.update(contrart);
//		roomService.upateroomstatus(checkOut.getBranchId(), checkOut
//				.getRoomId(), "0");
		Branch branch = (Branch) apartmentCheckOutService.findOneByProperties(Branch.class, "branchId",checkOut.getBranchId() ); 
		Member member = (Member) apartmentCheckOutService.findById(
				Member.class, checkOut.getMemberId());
		logServiceInPms.apartmentCheckOut(loginUser, member,request);

//		SysParam param = (SysParam) apartmentCheckOutService.findOneByProperties(SysParam.class, "paramType",SystemConstants.ParamType.BRANCH_IP, "paramName", "1");
//		// 会员等级
//		int priority = 1;
//		priority = Integer.parseInt(member.getMemberRank());
//		List<CheckOut> list = new ArrayList<CheckOut>();
//		list.add(checkOut);
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("CheckOut", list);
//		TransferServcie.getInstance().transferData(priority, param.getContent(), JSONUtil.fromObject(map).toString());

		commonService.Weixin(member, checkOut, "已处理结束",branch);
		String result = "{\"code\":\"1\"}";
		JSONUtil.responseJSON(response, result);
	}

	/**
	 * 退房申请(公寓)-显示添加退房申请页面
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addApartCheckOut.do")
	public ModelAndView addApartCheckOut(HttpServletRequest request,String contractId,String contractName, String mphone,String roomtype,String roomid)
			throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.addObject("contractId",contractId);
		mv.addObject("contractName", contractName);
		mv.addObject("mphone", mphone);
		mv.addObject("roomtype", roomtype);
		mv.addObject("roomid", roomid);
		mv.setViewName("page/apartment/apartmentLeftmenu/apartmentcheckout/addCheckOut");
		return mv;
	}
	
	/**
	 * 根据门店号,房间号查询当前房间入住人的信息
	 * 
	 * @param request
	 * @param response
	 * @param roomId
	 *            房间号
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/queryContractRoomId.do")
	public void queryContract(HttpServletRequest request,
			HttpServletResponse response, String roomId) throws Exception {
		LoginUser loginUser = (LoginUser) request.getSession(true)
				.getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		List<Contrart> list = apartmentCheckOutService.findBySQL(
				"apartmentInfo", new String[] {
						loginUser.getStaff().getBranchId(), "", roomId }, true);
		JSONUtil.responseJSON(response, list);
	}

	/**
	 * 添加退房申请
	 * 
	 * @param request
	 * @param response
	 * @param contractId
	 *            合同表ID
	 * @param remark
	 *            备注
	 * @param checkOuttime
	 *            退房时间
	 * @throws Exception
	 */
	@RequestMapping("/addContractInfo.do")
	public void queryContractInfo(HttpServletRequest request,
			HttpServletResponse response, String contractId, String remark,
			String checkOuttime) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		SimpleDateFormat outTime = new SimpleDateFormat("yyyy/MM/dd");
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchid =  loginUser.getStaff().getBranchId().toString();
		Contrart contrart = (Contrart) apartmentCheckOutService.findOneByProperties(Contrart.class, "contrartId", contractId);
		SysParam sysParam = ((SysParam) (auditService.findOneByProperties(SysParam.class, "paramType", "AUDITCheckOut","parameter1",branchid)));
		String postone = sysParam.getParamName().toString();
		Date date = new Date();
		String strdate = sdf.format(date);
		CheckOut checkOut = new CheckOut();
		checkOut.setCheckOutId(strdate + loginUser.getStaff().getBranchId() + "3" + apartmentCheckOutService.getSequence("SEQ_CHECKOUT_ID"));
		checkOut.setContractId(contractId);
		checkOut.setMemberId(contrart.getMemberId());
		checkOut.setBranchId(loginUser.getStaff().getBranchId());
		checkOut.setSource("3");
		checkOut.setRoomId(contrart.getRoomId());
		checkOut.setStatus("1");
		checkOut.setApplicationTime(date);
		checkOut.setRemark(remark);
		checkOut.setRecordTime(date);
		checkOut.setRecordUser(loginUser.getStaff().getStaffId());
		checkOut.setDispose("0");
		checkOut.setCheckoutTime(outTime.parse(checkOuttime));
		checkOut.setPost(postone);
		checkOut.setAuditRemark("待审核");
		apartmentCheckOutService.save(checkOut);
		String roomid = contrart.getRoomId().toString();
		String memberid = contrart.getMemberId().toString();
		String auditremark = "待审核";
		List<?> poststaffphone = auditService.getPoststaffphone(postone,branchid);
		if (null != poststaffphone &&!poststaffphone.isEmpty()) {
			for (int j = 0; j < poststaffphone.size(); j++) {
				String mobile = ((Map<?, ?>) poststaffphone.get(j)).get("MOBILE").toString();
				String staffname = ((Map<?, ?>) poststaffphone.get(j)).get("STAFFNAME").toString();
				String audittypename = CommonConstants.MessageTextDefinition.CheckOut;
				if(mobile.matches(RegExUtil.MOBILE)){
					Member membername = (Member) auditService.findOneByProperties(Member.class, "memberId", memberid,"status","1");
					String customername = membername.getMemberName().toString();
					SmsSingleSender singleSender = new SmsSingleSender(SystemConstants.note.APPID, SystemConstants.note.APPKEY);
					ArrayList<String> params = new ArrayList<String>();
					params.add(staffname);
					params.add(roomid);
					params.add(customername);
					params.add(audittypename);
					params.add(auditremark);
					//message.add("尊敬的员工:"+staffname+",您好，【"+roomid+"】房客户【"+customername+"】发起了"+audittypename+"申请,请尽快登录系统查看详情,并进行处理(当前处理进度:【"+auditremark+"】)");
					SmsSingleSenderResult singleSenderResult = singleSender.sendWithParam(SystemConstants.note.COUNTRY, mobile, 47260, params, "", "", "");				
				}
			
			}
		}
		String result = "{\"code\":\"1\"}";
		JSONUtil.responseJSON(response, result);
	}
}
