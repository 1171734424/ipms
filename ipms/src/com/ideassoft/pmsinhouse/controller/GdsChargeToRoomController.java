package com.ideassoft.pmsinhouse.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ideassoft.bean.Bill;
import com.ideassoft.bean.Branch;
import com.ideassoft.bean.Contrart;
import com.ideassoft.bean.Goods;
import com.ideassoft.bean.Order;
import com.ideassoft.bean.SaleLog;
import com.ideassoft.bean.Staff;
import com.ideassoft.bean.SysParam;
import com.ideassoft.core.ajax.AjaxResult;
import com.ideassoft.core.annotation.interfaces.RightMethodControl;
import com.ideassoft.core.annotation.interfaces.RightModelControl;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.constants.CommonConstants;
import com.ideassoft.core.constants.RightConstants;
import com.ideassoft.core.dao.GenericDAOImpl;
import com.ideassoft.core.jdbc.SqlBuilder;
import com.ideassoft.core.page.Pagination;
import com.ideassoft.pmsinhouse.service.GdsChargeToRoomService;
import com.ideassoft.util.JSONUtil;
import com.ideassoft.util.RequestUtil;


@Transactional
@Controller
@RightModelControl( rightModel = RightConstants.RightModel.HOUSE_CONTROL )
public class GdsChargeToRoomController {

	@Autowired
	private GdsChargeToRoomService gdsChargeToRoomService;
	
	@RequestMapping("/goodsInHouse.do")
	@RightMethodControl(rightType = RightConstants.RightType.GOODS_SELL )
	public ModelAndView goodsInHouse(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchid = loginuser.getStaff().getBranchId();
		List<?> categoryCondition = gdsChargeToRoomService.getCategorycondition(branchid);
		mv.setViewName("page/ipmshouse/leftmenu/goodssale/goods");
		request.setAttribute("categoryCondition", categoryCondition);
		return mv;
	}
	
	@RequestMapping("/goodsPageInHouse.do")
	public ModelAndView goodsPageInHouse(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchid = loginuser.getStaff().getBranchId();
		/*if (branchid.equals(SystemConstants.User.BRANCH_ID)) {
			branchid = "%";
		}*/
		List<?> categoryCondition = gdsChargeToRoomService.getCategorycondition(branchid);
		mv.setViewName("page/ipmshouse/leftmenu/goodssale/goodspage");
		request.setAttribute("categoryCondition", categoryCondition);
		return mv;
	}
	
	@RequestMapping("/gdmanageDataInHouse.do")
	public ModelAndView gdmanageDataInHouse(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchid = loginuser.getStaff().getBranchId();
		/*if (branchid.equals(SystemConstants.User.BRANCH_ID)) {
			branchid = "%";
		}*/
		List<?> categoryCondition = gdsChargeToRoomService.getCategorycondition(branchid);
		mv.setViewName("page/ipmshouse/leftmenu/goodssale/gdsmanage");
		request.setAttribute("categoryCondition", categoryCondition);
		return mv;
	}
	
	
	@RequestMapping("/gdsaleConditionInHouse.do")
	public ModelAndView gdsaleConditionInHouse(HttpServletRequest request, HttpServletResponse response, String goodsid,
			String goodsname, String categoryid,String gdscontent) throws Exception {
		ModelAndView mv = new ModelAndView();
		Pagination pagination = SqlBuilder.buildPagination(request);
		pagination.setRows(13);
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginuser.getStaff().getBranchId();
		/*if (branchId.equals(SystemConstants.User.BRANCH_ID)) {
			branchId = "%";
		}*/
		List<?> gdsaleCondition = gdsChargeToRoomService.getGdsaleconditionInHouse(goodsid, goodsname, categoryid, branchId,
				pagination);
		mv.setViewName("page/ipmshouse/leftmenu/goodssale/gdsalecondition");
		mv.addObject("gdsaleCondition", gdsaleCondition);
		mv.addObject("pagination", pagination);
		mv.addObject("gdscontent", gdscontent);
		mv.addObject("goodsid", goodsid);
		mv.addObject("goodsname", goodsname);
		mv.addObject("categoryid", categoryid);
		return mv;
	}
	
	//批量售卖
	@RequestMapping("/gdsmulPageInHouse.do")
	public ModelAndView gdsmulPageInHouse(HttpServletRequest request, String saletype, String newstr) throws Exception {
		ModelAndView mv = new ModelAndView();
		List<?> gdsproject = gdsChargeToRoomService.getGdsprojectInHouse();
		List<?> gdsprojectpay = gdsChargeToRoomService.getGdsprojectInHouse();
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchid = loginuser.getStaff().getBranchId();
		Branch branchData = (Branch) gdsChargeToRoomService.findOneByProperties(Branch.class, "branchId", branchid);
		String loginstaff = loginuser.getStaff().getStaffId().toString();
		List<?> workbill = gdsChargeToRoomService.getWorkbillInHouse(branchid, loginstaff);
		mv.setViewName("page/ipmshouse/leftmenu/goodssale/gdsmulpage");
		List<Goods> listgoods = new ArrayList<Goods>();
		JSONArray newstrr = new JSONArray(newstr);
		for (int i = 0; i < newstrr.length(); i++) {
			String s = newstrr.getString(i);
			Goods goods = (Goods) gdsChargeToRoomService.findOneByProperties(Goods.class, "goodsId", s);
			listgoods.add(goods);
		}
		//List<?> house = leftmenuService.findByProperties(House.class, "staffId", loginuser.getStaff().getStaffId());
		List<?> house = gdsChargeToRoomService.findBySQL("fHouseWithStaffid",new String[]{loginuser.getStaff().getStaffId(),loginuser.getStaff().getStaffId()}, true);

		String systemtheme = "hotel";
		if (branchData.getBranchType().equals(CommonConstants.Branch.APARTMENTID)) {
			systemtheme = "depart";
		}
		if (house.size() > 0) {
			systemtheme = "house";
		}
		request.setAttribute("systemtheme", systemtheme);
		request.setAttribute("listgoods", listgoods);
		request.setAttribute("saletype", saletype);
		request.setAttribute("gdsproject", gdsproject);
		request.setAttribute("gdsprojectpay", gdsprojectpay);
		request.setAttribute("workbill", workbill);
		// System.out.println();
		return mv;
	}
	
	@RequestMapping("/gdmanageConditionInHouse.do")
	public ModelAndView gdmanageConditionInHouse(HttpServletRequest request, HttpServletResponse response, String goodsid,
			String goodsname, String categoryid, String status) throws Exception {
		ModelAndView mv = new ModelAndView();
		Pagination pagination = SqlBuilder.buildPagination(request);
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginuser.getStaff().getBranchId();
		List<?> gdsmanageCondition = gdsChargeToRoomService.getGdmanageconditionInHouse(goodsid, goodsname, categoryid, status,
				branchId, pagination);
		mv.setViewName("page/ipmshouse/leftmenu/goodssale/gdsmanagecondition");
		mv.addObject("gdsmanageCondition", gdsmanageCondition);
		mv.addObject("goodsid", goodsid);
		mv.addObject("goodsname", goodsname);
		mv.addObject("categoryid", categoryid);
		mv.addObject("status", status);
		mv.addObject("pagination", pagination);
		return mv;
	}
	
	@RequestMapping("/goodsManageInHouse.do")
	public ModelAndView goodsManageInHouse(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		Pagination pagination = SqlBuilder.buildPagination(request);
		pagination.setRows(13);
		// @SuppressWarnings("unchecked")
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginuser.getStaff().getBranchId();
		/*if (branchId.equals(SystemConstants.User.BRANCH_ID)) {
			branchId = "%";
		}*/
		List<?> goodsSale = gdsChargeToRoomService.getGoodssaleInHouse(pagination, branchId);
		mv.setViewName("page/ipmshouse/leftmenu/goodssale/gdsmanagedata");
		mv.addObject("goodsSale", goodsSale);
		mv.addObject("pagination", pagination);
		return mv;
	}
	
	//商品售卖挂房账
	@RequestMapping("/gdsRoompayInHouse.do")
	public void gdsRoompayInHouse(HttpServletRequest request, HttpServletResponse response, String gdscheckid,
			String totalprice, String gdsroomid, String gdsid, String totalnumber, String gdsprice) throws Exception {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		SysParam sysParamrpay = ((SysParam) (gdsChargeToRoomService.findOneByProperties(SysParam.class, "paramType",
				"PROJECT", "paramName", "商品售卖")));
		String content = sysParamrpay.getContent().toString();
		Staff staff = loginuser.getStaff();
		Order order = (Order) gdsChargeToRoomService.findById(Order.class, gdscheckid);
		Contrart contract = (Contrart) gdsChargeToRoomService.findById(Contrart.class, gdscheckid);
		String branchid = order != null ? order.getBranchId() : contract.getBranchId();
		String logId = this.gdsChargeToRoomService.getSequence("SEQ_NEW_BILL", null);
		Date day = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyMMdd");
		String t = df.format(day);
		String datId = t + branchid + logId;
		Double cost = Double.parseDouble(totalprice);
		Bill bill = new Bill();
		bill.setBillId(datId);
		bill.setBranchId(branchid);
		bill.setCheckId(gdscheckid);
		bill.setProjectId(content);
		bill.setProjectName("商品售卖");
		bill.setDescribe("商品");
		bill.setCost(cost);
		bill.setPay((double) 0);
		bill.setPayment("0");
		bill.setStatus("1");
		bill.setRecordUser(staff.getStaffId());
		gdsChargeToRoomService.save(bill);
		Goods goods = ((Goods) (gdsChargeToRoomService.findOneByProperties(Goods.class, "goodsId", gdsid)));
		// String goodsname = goods.getGoodsName();
		String categoryid = goods.getCategoryId();
		// GoodsCategory goodscategory = ((GoodsCategory)
		// (LeftmenuService.findOneByProperties(GoodsCategory.class,
		// "categoryId", categoryid)));
		// String categoryname = goodscategory.getCategoryName();
		String salelogId = this.gdsChargeToRoomService.getSequence("SEQ_NEW_SALELOG", null);
		String saledatId = t + branchid + salelogId;
		Integer amount = Integer.valueOf(totalnumber);
		Double price = Double.parseDouble(gdsprice);
		SaleLog salelog = new SaleLog();
		salelog.setLogId(saledatId);
		salelog.setBranchId(branchid);
		salelog.setDebts("2");
		salelog.setCheckId(gdscheckid);
		salelog.setRoomId(gdsroomid);
		salelog.setCategoryId(categoryid);
		salelog.setGoodsName(gdsid);
		salelog.setAmount(amount);
		salelog.setPrice(price);
		salelog.setPayType("1");
		salelog.setRecordUser(staff.getStaffId());
		gdsChargeToRoomService.save(salelog);
		JSONUtil.responseJSON(response, new AjaxResult(1, null));

	} 
	
	@RequestMapping("/gdsDownInHouse.do")
	public void gdsDownInHouse(HttpServletRequest request, HttpServletResponse response, String gdsid) throws Exception {
		Goods goods = ((Goods) (gdsChargeToRoomService.findOneByProperties(Goods.class, "goodsId", gdsid)));
		goods.setStatus("2");
		gdsChargeToRoomService.save(goods);
		JSONUtil.responseJSON(response, new AjaxResult(1, null));
	}

	@RequestMapping("/gdsUpInHouse.do")
	public void gdsUpInHouse(HttpServletRequest request, HttpServletResponse response, String gdsid) throws Exception {
		Goods goods = ((Goods) (gdsChargeToRoomService.findOneByProperties(Goods.class, "goodsId", gdsid)));
		goods.setStatus("1");
		gdsChargeToRoomService.save(goods);
		JSONUtil.responseJSON(response, new AjaxResult(1, null));
	}
	
	// 判断挂房账时是否有房号，如果没有直接弹出提示框，如果有正常进入！
	@RequestMapping("/selectgdsRoomInHouse.do")
	public  void selectgdsRoomInHouse(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginuser.getStaff().getBranchId();
		List<?> gdsroomId;
		Branch branchInfo = (Branch) gdsChargeToRoomService.findOneByProperties(Branch.class, "branchId", branchId);
		if (branchInfo.getRank().equals(CommonConstants.SystemLevel.MarketCenter)) {
			gdsroomId = gdsChargeToRoomService.findBySQL("querylivehouse", new String[] { loginuser.getStaff().getStaffId()}, true);
		} else if (branchInfo.getBranchType().equals(CommonConstants.Branch.APARTMENTID)) {
			gdsroomId = gdsChargeToRoomService.findBySQL("gdsroomIdapart", new String[] { branchId,branchId, branchId,branchId}, true);
		} else {
			gdsroomId = gdsChargeToRoomService.getGdsroomidInHouse(branchId,CommonConstants.SystemTheme.HOTEL);
		}
		String a = "没有在住房间!";
		if (gdsroomId.size() <= 0) {
			JSONUtil.responseJSON(response, new AjaxResult(-1, a));
		}else{
			a = "有在住房间！";
			JSONUtil.responseJSON(response, new AjaxResult(1,a));
		}
	}
	
	@RequestMapping("/gdsroomSelectInHouse.do")
	public ModelAndView gdsroomSelectInHouse(HttpServletRequest request, HttpServletResponse response, String totalprice) throws Exception {
		ModelAndView mv = new ModelAndView();
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginuser.getStaff().getBranchId();
		// if (branchId.equals(SystemConstants.User.BRANCH_ID)) {
		// branchId = "%";
		// }
		List<?> gdsroomId;
		Branch branchInfo = (Branch) gdsChargeToRoomService.findOneByProperties(Branch.class, "branchId", branchId);
		if (branchInfo.getRank().equals(CommonConstants.SystemLevel.MarketCenter)) {//民宿
			gdsroomId = gdsChargeToRoomService.findBySQL("querylivehouse", new String[] { loginuser.getStaff().getStaffId()}, true);
			mv.addObject("theme", CommonConstants.Branch.HOUSEID);
		} else if (branchInfo.getBranchType().equals(CommonConstants.Branch.APARTMENTID)) {
			gdsroomId = gdsChargeToRoomService.findBySQL("gdsroomIdapart", new String[] { branchId,branchId, branchId,branchId}, true);
			mv.addObject("theme", CommonConstants.Branch.APARTMENTID);

		} else {//酒店
			gdsroomId = gdsChargeToRoomService.getGdsroomidInHouse(branchId,CommonConstants.SystemTheme.HOTEL);
			mv.addObject("theme", CommonConstants.Branch.HOTELID);

		}
		mv.setViewName("page/ipmshouse/leftmenu/goodssale/gdsroomselect");
		mv.addObject("allCost", totalprice);
		mv.addObject("gdsroomId", gdsroomId);
		return mv;
	}
	
	//单品售卖
	@RequestMapping("/gdsPageInHouse.do")
	public ModelAndView gdsPageInHouse(HttpServletRequest request, String gdsid, String gdsname, String gdsprice)
			throws Exception {
		ModelAndView mv = new ModelAndView();
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String loginstaff = loginuser.getStaff().getStaffId().toString();
		Branch branchData = (Branch) gdsChargeToRoomService.findOneByProperties(Branch.class, "branchId", loginuser.getStaff().getBranchId());
		List<?> gdsproject = gdsChargeToRoomService.getGdsprojectInHouse();
		List<?> gdsprojectpay = gdsChargeToRoomService.getGdsprojectpayInHouse();
		String branchid = loginuser.getStaff().getBranchId();
		List<?> workbill = gdsChargeToRoomService.getWorkbillInHouse(branchid, loginstaff);
		//List<?> house = leftmenuService.findByProperties(House.class, "staffId", loginuser.getStaff().getStaffId());
		List<?> house = gdsChargeToRoomService.findBySQL("fHouseWithStaffid",new String[]{loginuser.getStaff().getStaffId(),loginuser.getStaff().getStaffId()}, true);

		mv.setViewName("page/ipmshouse/leftmenu/goodssale/gdspage");
		String systemtheme = "house";
		
		request.setAttribute("systemtheme", systemtheme);
		request.setAttribute("gdsid", gdsid);
		request.setAttribute("gdsname", gdsname);
		request.setAttribute("gdsprice", gdsprice);
		request.setAttribute("gdsproject", gdsproject);
		request.setAttribute("gdsprojectpay", gdsprojectpay);
		request.setAttribute("workbill", workbill);
		return mv;
	}
}
