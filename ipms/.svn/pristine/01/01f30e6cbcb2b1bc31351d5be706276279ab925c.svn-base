package com.ideassoft.pmsmanage.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ideassoft.bean.CashKey;
import com.ideassoft.bean.RoomKey;
import com.ideassoft.bean.RoomPriceId;
import com.ideassoft.core.dao.GenericDAOImpl;
import com.ideassoft.core.page.Pagination;

@Service
public class PmsmanageService extends GenericDAOImpl {
	
	/*public void upateroomstatus(String branchiD, String rooomiD, String statusdelete, String recordUser) {
		RoomKey roomKey = new RoomKey();
		roomKey.setRoomId(rooomiD);
		roomKey.setBranchId(branchiD);
		String hql1 = "update Room set status = :STATUS,recordUser = :RECORDUSER where roomKey = :ROOMKEY and  status = :OLDSTATUS";
		this.executeUpdateHQL(hql1, new String[] { "STATUS", "ROOMKEY", "RECORDUSER" ,"OLDSTATUS"}, new Object[] { "F", roomKey,
				recordUser, statusdelete});
	}*/

	/*
	 * public List<?> getRpsetup() throws Exception { return
	 * findBySQL("rpsetup", true); }
	 */
	public List<?> getHouseList() throws Exception {
		return findBySQL("getHouseList", true);
	}
	public List<?> getApartmentDevice() throws Exception {
		return findBySQL("apartmentDevice", true);
	}
	public List<?> getHotelDevice() throws Exception {
		return findBySQL("hotelDevice", true);
	}
	public List<?> getRoomBroadband() throws Exception {
		return findBySQL("roomBroadbandParam", true);
	}
	public List<?> getRoomPosition() throws Exception {
		return findBySQL("roomPositionDesc", true);
	}
	public List<?> getRoomBedType() throws Exception {
		return findBySQL("roomBedType", true);
	}
	
	public List<?> getRpbranchid() throws Exception {
		return findBySQL("rpbranchid", true);
	}

	public List<?> getRptheme() throws Exception {
		return findBySQL("rptheme", true);
	}

	public List<?> getRpkind() throws Exception {
		return findBySQL("rpkind", true);
	}

	public List<?> getRproomtype(String branchId) throws Exception {
		List<?> result = findBySQL("rproomtype", new String[] { branchId}, true);
		return result;
	}

	public List<?> getRppack() throws Exception {
		return findBySQL("rppack", true);
	}

	public List<?> getRpsetup() throws Exception {
		return findBySQL("rpsetup", true);
	}


	public List<?> getRpdata(String branchid, String theme, String roomtype) throws Exception {
		List<?> result = findBySQL("rpdatajudge", new String[] { branchid, theme, roomtype }, true);
		return result;
	}

	public List<?> getShiftcontent(String branchid) throws Exception {
		//return findBySQL("shiftcontent", true);
		return findBySQL("newshiftcontent",new String[]{branchid} ,true);
	}

	public List<?> getUniqueroom(String branchid, String roomid) throws Exception {
		List<?> result = findBySQL("uniqueroom", new String[] { branchid, roomid }, true);
		return result;
	}

	public List<?> getExitroomstatus(String branchid, String rproomtype) throws Exception {
		List<?> result = findBySQL("exitroomstatus", new String[] { branchid, rproomtype }, true);
		return result;
	}

	public List<?> getRename(String theme, String branchid, String roomtype, String status) throws Exception {
		List<?> result = findBySQL("roomeditrename", new String[] { theme, branchid, roomtype, status }, true);
		return result;
	}

	public void upateroomedit(String theme, String branchid, String rproomtype, String floor, String roomid,
			Short areadata, String status, String oldstatus, String remark, String editUser) {
		RoomKey roomKey = new RoomKey();
		roomKey.setRoomId(roomid);
		roomKey.setBranchId(branchid);
		String hql1 = "update Room set roomType = :ROOMTYPE,floor = :FLOOR,area = :AREA, remark = :REMARK,recordUser = :RECORDUSER ,status = :STATUS where roomKey = :ROOMKEY  and theme = :THEME";
		this.executeUpdateHQL(hql1, new String[] { "ROOMTYPE", "FLOOR", "AREA", "REMARK", "RECORDUSER", "STATUS","ROOMKEY",
				"THEME"  }, new Object[] { rproomtype, floor, areadata, remark, editUser, status,roomKey, theme
				 });
	}

	public void upateroomprice(String themeid, String branchiD, String roomtypeid, String rpId, String recordUser) {
		RoomPriceId roompriceid = new RoomPriceId();
		roompriceid.setBranchId(branchiD);
		roompriceid.setRoomType(roomtypeid);
		roompriceid.setRpId(rpId);
		String hql1 = "update RoomPrice set status = :STATUS,recordUser = :RECORDUSER where roomPriceId = :ROOMPRICEID and theme = :THEME";
		this.executeUpdateHQL(hql1, new String[] { "STATUS", "RECORDUSER", "ROOMPRICEID", "THEME" }, new Object[] {
				"0", recordUser, roompriceid, themeid });
	}

	public void upatecashbox(String cashboxid, String branchiD, String recordUser) {
		CashKey cashkey = new CashKey();
		cashkey.setBranchId(branchiD);
		cashkey.setCashBox(cashboxid);
		String hql1 = "update CashBox set status = :STATUS,recordUser = :RECORDUSER where cashKey = :CASHKEY ";
		this.executeUpdateHQL(hql1, new String[] { "STATUS", "RECORDUSER", "CASHKEY" }, new Object[] { "0", recordUser,
				cashkey });
	}

	public List<?> getRproomtypeInitialize(String theme,String branchId) throws Exception {
		List<?> result = findBySQL("roomtypeInitialize", new String[] { theme ,branchId}, true);
		return result;
	}

	public List<?> getRpInitializejudge(String branchId, String theme) throws Exception {
		List<?> result = findBySQL("rpidInitializejudge", new String[] { branchId, theme }, true);
		return result;
	}

	public List<?> getRpapplyroomtype(String themeid, String rpthemeid, String branchid, String rpking, String statusid)
			throws Exception {
		List<?> result = findBySQL("rpapplyroomtype", new String[] { themeid, rpthemeid, branchid, branchid,rpking, statusid },
				true);
		return result;
	}

	public List<?> getRpapplystatus() throws Exception {
		return findBySQL("rpapplystatus", true);
	}

	public List<?> getJudgerpexit(String theme, String branchid, String rpid, String rptypename, String status,
			String rpkind) throws Exception {
		List<?> result = findBySQL("judgerpexit", new String[] { theme, branchid, rpid, rptypename, status, rpkind },
				true);
		return result;
	}

	public void updatePricenow(String theme, String branchid, String rpid, String rptypename, String status,
			String rpkind, Double d, String memberrank, String packid, String recorduser, Double price) {
		RoomPriceId roompriceid = new RoomPriceId();
		roompriceid.setBranchId(branchid);
		roompriceid.setRoomType(rptypename);
		roompriceid.setRpId(rpid);
		roompriceid.setRpKind(rpkind);
		roompriceid.setStatus(status);
		String hql1 = "update RoomPrice set roomPrice = :ROOMPRICE,packId = :PACKID,discount = :DISCOUNT,memberRank = :MEMBERRANK,state = :STATE,recordUser = :RECORDUSER where roomPriceId = :ROOMPRICEID and theme = :THEME ";
		this.executeUpdateHQL(hql1, new String[] { "ROOMPRICE", "PACKID", "DISCOUNT", "MEMBERRANK", "STATE",
				"RECORDUSER", "ROOMPRICEID", "THEME" }, new Object[] { price, packid, d, memberrank, "5", recorduser,
				roompriceid, theme });
	}
	
	
	public List<?> getPettypaydata(Pagination pagination,String branchId, String recordUser) throws Exception {
		List<?> result = findBySQLWithPagination("pettypaydata", new String[] { branchId, recordUser }, pagination, 
				true);
		return result;
	}

	public List<?> getUnusedata(String branchiD, String rooomiD) throws Exception {
		List<?> result = findBySQL("unuseroomdata", new String[] { branchiD, rooomiD }, true);
		return result;
	}
	
	/*public void droproomstatus(String branchiD, String rooomiD) {
		RoomKey roomKey = new RoomKey();
		roomKey.setRoomId(rooomiD);
		roomKey.setBranchId(branchiD);
		String hql1 = "delete  Room  where roomKey = :ROOMKEY and status = :STATUS" ;
		this.executeUpdateHQL(hql1, new String[] { "ROOMKEY","STATUS" }, new Object[] {  roomKey,"F" });
	}*/
	
	
	public List<?> getReroomtype(String roomtype, String themeid, String branchid) throws Exception {
		List<?> result = findBySQL("reroomtype", new String[] { roomtype, themeid, branchid },
				true);
		return result;
	}
	
	public List<?> getReroomtypenew(String roomtype, String themeid, String branchid) throws Exception {
		List<?> result = findBySQL("reroomtypenew", new String[] { roomtype, themeid, branchid },
				true);
		return result;
	}
	
}
