package com.ideassoft.pmsinhouse.controller;



import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedCaseInsensitiveMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ideassoft.pmsinhouse.service.HouseForwordService;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.jdbc.SqlBuilder;
import com.ideassoft.core.page.Pagination;
import com.ideassoft.util.RequestUtil;

@Transactional
@Controller
public class HouseForwardCtrl {
	@Autowired
	private HouseForwordService houseForwordService;
	
	/**
	 * 
	 * 民宿远期房态
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 * 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("houseForwardRoom.do")
	public ModelAndView houseForwardRoom(HttpServletRequest request, HttpServletResponse response, String madate,
			String edate) throws Exception {
		ModelAndView mv = new ModelAndView();
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String userid = loginUser.getStaff().getStaffId();
		Pagination pagination = SqlBuilder.buildPagination(request);
		pagination.setRows(15);
		List<Object> list = new ArrayList<Object>();
		List<Object> listdate = new ArrayList<Object>();
		List<Object> listRoom = new ArrayList<Object>();
		List<Object> listmap = new ArrayList<Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date mdate = sdf.parse(madate);
		Date eedate = sdf.parse(edate);
		long day = (eedate.getTime()- mdate.getTime())/(24*60*60*1000) + 1;
		Date date = null;
		List<?> rt = houseForwordService.queryHouse(userid, pagination);//huoqu suoyou 管理下的民宿houseID		
		List<Map<String, Object>> newlist = new ArrayList<Map<String, Object>>();
		for (int x = 0; x < rt.size(); x++) { 
			date = sdf.parse(madate);
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			Map<String, Object> el = new HashMap<String, Object>();
			el.put("houseId", ((Map)rt.get(x)).get("HOUSEID"));
			el.put("houseName", ((Map)rt.get(x)).get("HOUSENAME"));
			List<Object> datelist = new ArrayList<Object>();
			for (int y = 0; y < day; y++) {
				Map<String, Object> dateandstatues = new HashMap<String, Object>();
				String newdate = sdf.format(c.getTime());
				list = houseForwordService.findBySQL("newhouseforward", new String[] {  newdate,
						newdate,  ((Map<?,?>) rt.get(x)).get("houseid").toString(), newdate, newdate, ((Map<?,?>) rt.get(x)).get("houseid").toString(),newdate,newdate, ((Map<?,?>) rt.get(x)).get("houseid").toString(),newdate, ((Map<?,?>) rt.get(x)).get("houseid").toString() }, true);
				c.add(Calendar.DAY_OF_MONTH, 1);
				if (x == 0) {
					listdate.add(newdate);
				}
				String sta = "";
				for (int a = 0; a < list.size(); a++) {
					if(((Map<?,?>) list.get(a)).get("YD") != null){
						sta = "预定";
					}
					if(((Map<?,?>) list.get(a)).get("WX") != null){
						sta = "维修";
					}
					if(((Map<?,?>) list.get(a)).get("ZZ") != null){
						sta = "在住";
					}
					if(((Map<?,?>) list.get(a)).get("TS") != null){
						sta = "停售";
					}
					if("".equals(sta)){
						sta = "空房";
					}
					map.put(newdate, sta);
				}
				dateandstatues.put("date", newdate);
				dateandstatues.put("status", sta);
				datelist.add(dateandstatues);
			}
			el.put("dates", datelist);
			newlist.add(el);
			listRoom.add(((Map<?,?>) rt.get(x)).get("houseid").toString());
			listmap.add(map);
		}
		Calendar instance = Calendar.getInstance();
		instance.setTime(new SimpleDateFormat("yyyy/MM/dd").parse(madate));
		mv.addObject("listdate", listdate);
		mv.addObject("listroomtype", listRoom);
		mv.addObject("newlist", newlist);
		mv.addObject("pagination", pagination);
		mv.addObject("madate", madate);
		mv.addObject("edate", edate);
		mv.setViewName("page/ipmshouse/topMenu/room_forward/forward_housedetail");
		return mv;
	}
	   
}
