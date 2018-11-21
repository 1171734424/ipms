package com.ideassoft.basic.service;



import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.ideassoft.bean.City;
import com.ideassoft.bean.Location;
import com.ideassoft.bean.ShiftTime;
import com.ideassoft.bean.SysParam;
import com.ideassoft.core.constants.CommonConstants;
import com.ideassoft.core.dao.GenericDAOImpl;

@Service
public class BranchBasicService extends GenericDAOImpl {
	public List<SysParam> queryBranchType(){
		String paramType="BRANCHRANK";
		return this.findByProperties(SysParam.class, "paramType",paramType);
	}
	public List<?> getReroomtype(String roomtype, String themeid, String branchid) throws Exception {
		List<?> result = findBySQL("reroomtype", new String[] { roomtype, themeid, branchid },
				true);
		return result;
	}
	public List<?> getUnusedata(String branchiD, String rooomiD) throws Exception {
		List<?> result = findBySQL("unuseroomdata", new String[] { branchiD, rooomiD }, true);
		return result;
	}
	public List<?> getExitroomstatus(String branchid, String rproomtype) throws Exception {
		List<?> result = findBySQL("exitroomstatus", new String[] { branchid, rproomtype }, true);
		return result;
	}
	public List<?> getUniqueroom(String branchid, String roomid) throws Exception {
		List<?> result = findBySQL("uniqueroom", new String[] { branchid, roomid }, true);
		return result;
	}
	public List<SysParam> queryTheme(){
		String paramType="THEME";
		return this.findByProperties(SysParam.class, "paramType",paramType);
	}
	
	public List<City> queryCity(){
		String cityRank="1";
		return this.findByProperties(City.class, "rank",cityRank,"status","1");
	}
	
	public Location queryBranchLocation(String branchId){
		return (Location) this.findOneByProperties(Location.class, "branchId",branchId);
	}
	public List<?> getRpbranchid() throws Exception {
		return findBySQL("rpbranchid", true);
	}
	public List<?> getRptheme() throws Exception {
		return findBySQL("rptheme", true);
	}
	public List<?> getRproomtype(String branchId) throws Exception {
		List<?> result = findBySQL("rproomtype", new String[] { branchId}, true);
		return result;
	}
	public List<?> getStatus() throws Exception {
		return findBySQL("rpstatus", true);
	}
	public List<?> getRpapplystatus() throws Exception {
		return findBySQL("rpapplystatus", true);
	}
	public List<?> getRppack() throws Exception {
		return findBySQL("rppack", true);
	}
	public List<?> getRpsetup() throws Exception {
		return findBySQL("rpsetup", true);
	}
	public List<?> getRpapplyroomtype(String themeid, String rpthemeid, String branchid, String rpking, String statusid)
			throws Exception {
		List<?> result = findBySQL("rpapplyroomtype", new String[] { themeid, rpthemeid, branchid, branchid,rpking, statusid },
				true);
		return result;
	}
	public List<?> getRoomBedType() throws Exception {
		return findBySQL("roomBedType", true);
	}
	public List<?> getHouseDevice() throws Exception {
		return findBySQL("houseDevice", true);
	}
	public List<?> getRoomPosition() throws Exception {
		return findBySQL("roomPositionDesc", true);
	}
	public List<?> getRoomBroadband() throws Exception {
		return findBySQL("roomBroadbandParam", true);
	}
	public List<?> getThemeList() throws Exception {
		return findBySQL("getThemeList", true);
	}
	public List<?> getApartmentDevice() throws Exception {
		return findBySQL("apartmentDevice", true);
	}
	public List<?> getHotelDevice() throws Exception {
		return findBySQL("hotelDevice", true);
	}
	public List<?> getTips() throws Exception {
		return findBySQL("getTips", true);
	}
	public List<?> getHouseList() throws Exception {
		return findBySQL("getHouseList", true);
	}
	public List<?> getShiftcontent(String branchid) throws Exception {
		//return findBySQL("shiftcontent", true);
		return findBySQL("newshiftcontent",new String[]{branchid} ,true);
	}
	// 客服电话
	public SysParam queryOneCustomerPhone(String branchId) {
		SysParam result = (SysParam)findOneByProperties(SysParam.class, "paramType", "SERVICETEL", "paramName", branchId, "orderNo", "1");
		return result != null ? result : null;
	}
	// 检查当前门店是否有排班
	public Boolean queryShiftTimeInDB(String branchId) {
		String sql = "select * from TB_P_SHIFTTIME where status = '1' and branch_id='"+ branchId + "'";
		List<?> result = findBySQL(sql);
		return (result != null && result.size() == 3) ? true : false;
	}
	// 查询默认可预约房间数
	public List<?> getDefaultParam(String branchId) throws Exception {
		List<?> result = findBySQL("getBranchParam", new String[] { branchId }, true);
		return result;
	}
	// 排班是否有效
	public List<ShiftTime> errorShiftTimeInDB(String branchId) {
		//String sql = "select * from TB_P_SHIFTTIME where status = 1 and branch_id='"+ branchId + "'";
		//List<ShiftTime> result = (List<ShiftTime>)findBySQL(sql);
		List<ShiftTime> result = findByProperties(ShiftTime.class, "status", "1", "branchId", branchId);
		return (result != null && result.size() > 0) ? result : null;
	}
	// 查询申请审核参宿是否有值
	public SysParam queryRrecordsInSysParam(String paramType, String paramValue, String paramType2, String paramValue2) {
		SysParam result = (SysParam)findOneByProperties(SysParam.class, paramType, paramValue, paramType2, paramValue2);
		return result != null ?  result : null;
	}
	//获取序列是在云端还是在本地	
	public String getCloudOrLocalSequence(String sequenceName){
		String sequence = "";
		if(checkSystemExistCloud()){
			sequence = getSequence(sequenceName, null);
		} else {
			sequence = getCloudSequence(sequenceName, null);
		}
		return sequence;
	}
	//查询当前系统是布置在云端还是本地端啊
	@Cacheable(value = "longCache", key = "'isC1oud'")
	public boolean checkSystemExistCloud(){
		SysParam result = (SysParam)findOneByProperties(SysParam.class, "paramType", "SYSTEMTYPE", "status", "1");
		if(result != null){
			if(result.getContent().equals(CommonConstants.SystemLevel.CouldMarketCenter)){
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}	
	}
}
