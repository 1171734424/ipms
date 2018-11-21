package com.ideassoft.pms.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.ideassoft.bean.RoomPrice;
import com.ideassoft.core.ajax.AjaxResult;
import com.ideassoft.core.dao.GenericDAOImpl;
import com.ideassoft.core.exception.DAOException;
import com.ideassoft.util.JSONUtil;

@Service
public class CheckInDirectSercive extends GenericDAOImpl {
	/**
	 * 随机生成身份证信息(模拟读卡器读取身份证信息)
	 */
	public Map<String, String> readGuestInfo() {
		String year = "";
		String month = Integer.toString((int) (Math.random() * 10 + 1));
		if (month.length() == 1) {
			month = "0" + month;
		}
		int age = (int) (Math.random() * 100 + 1);
		year = Integer.toString(2017 - age);
		if (age < 18) {
			age = 18;
			year = "1999";
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", "张三" + (int) (Math.random() * 10) + 1);
		map.put("age", Integer.toString(age));
		map.put("gender", "男");
		map.put("idnumber", "320721" + year + month + "282478");
		return map;

	}
	
	public List<?> getMemberdata(String Mnumber) throws Exception {
		List<?> result = findBySQL("selectmember", new String[] { Mnumber }, true);
		return result;
	}

	public void getRoomPriceForTrialMember(HttpServletResponse response,
			String roomtype, boolean priceFlag, String branchid,
			Map<String, String> map, String sql) throws DAOException {

		List<?> Msj = findBySQL(sql, new String[] { branchid, roomtype, "2" },
				true);
		if (Msj == null || Msj.size() == 0) {// 如果查不到就查基准价
			Msj = findBySQL(sql, new String[] { branchid, roomtype, "1" }, true);
			if (Msj == null || Msj.size() == 0) {
				JSONUtil.responseJSON(response, new AjaxResult(1,
						"请前往后台配置房价相关信息"));
			} else {
				map.put("price", ((Map) (Msj.get(0))).get("PRICE").toString());
				map.put("rpid", ((Map) (Msj.get(0))).get("RPID").toString());
				map.put("pricename", ((Map) (Msj.get(0))).get("NAME")
						.toString());
				map.put("priceFlag", String.valueOf(priceFlag));
				JSONUtil.responseJSON(response, new AjaxResult(1, "临时会员", map));
			}
		} else {
			map.put("price", ((Map) (Msj.get(0))).get("PRICE").toString());
			map.put("rpid", ((Map) (Msj.get(0))).get("RPID").toString());
			map.put("pricename", ((Map) (Msj.get(0))).get("NAME").toString());
			map.put("priceFlag", String.valueOf(priceFlag));
			JSONUtil.responseJSON(response, new AjaxResult(1, "临时会员", map));
		}
	}

	public void getRoomPriceForMember(HttpServletResponse response,
			String roomtype, String branchid, List<?> memberdate, String rpId,
			String rpkind) throws DAOException {
		RoomPrice roomprice = (RoomPrice) findOneByProperties(RoomPrice.class,
				"state", "5", "theme", "1", "roomPriceId.branchId", branchid,
				"roomPriceId.rpId", rpId, "roomPriceId.roomType", roomtype,
				"roomPriceId.rpKind", rpkind, "roomPriceId.status", "2");
		if (roomprice == null) {
			roomprice = (RoomPrice) findOneByProperties(RoomPrice.class,
					"state", "5", "theme", "1", "roomPriceId.branchId",
					branchid, "roomPriceId.rpId", rpId, "roomPriceId.roomType",
					roomtype, "roomPriceId.rpKind", rpkind,
					"roomPriceId.status", "1");
			if (roomprice == null) {
				JSONUtil.responseJSON(response, new AjaxResult(2,
						"请前往后台配置房价相关信息"));
				return;
			} else {
				if (roomprice.getRoomPrice() == null) {
					JSONUtil.responseJSON(response, new AjaxResult(3,
							"请前往后台配置基准价"));
					return;
				} else {
					Double price = roomprice.getRoomPrice();
					Map<String, Object> maps = new HashMap<String, Object>();
					maps.put("price", price);
					maps.put("memberdate", memberdate);
					JSONUtil.responseJSON(response, new AjaxResult(4, "会员",
							maps));

				}
			}
		} else {
			Double price = roomprice.getRoomPrice();
			Map<String, Object> maps = new HashMap<String, Object>();
			maps.put("price", price);
			maps.put("memberdate", memberdate);
			JSONUtil.responseJSON(response, new AjaxResult(4, "会员", maps));
		}

	}

}
