package com.ideassoft.pmsinhouse.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hsqldb.lib.StringUtil;
import org.springframework.stereotype.Service;

import com.ideassoft.core.constants.SystemConstants;
import com.ideassoft.core.dao.GenericDAOImpl;
import com.ideassoft.core.page.Pagination;

@Service
public class ReportFormService extends GenericDAOImpl {
	public List<?> queryAllOfBranch() {
		String hql = "from Branch";
		return findByHQL(hql, new String[] {});
	}

	public List<?> queryAllOfRoomType(String branchId) {
		String hql = "from RoomType where roomTypeKey.branchId = '"+ branchId+"'";
		return findByHQL(hql, new String[] {});
	}

	public List<?> queryAllOfTemplateType(String paramType) {
		String hql = "from SysParam where paramType = ?";
		return findByHQL(hql, new String[] { paramType });
	}
	public List<?> queryRoomingGuestByCon(Pagination pagination, String branchId, String roomType, String roomId,
			String checkUser) throws Exception {
		if (branchId.equals(SystemConstants.User.BRANCH_ID)) {
			branchId = null;
		}
		List<?> result = findBySQLWithPagination("queryRoomingByCon", new String[] { branchId,branchId, roomType, roomId,
				checkUser }, pagination, true);
		return result;
	}

	public List<?> queryRoomingGuests(String branchId, Pagination pagination) throws Exception {
		if (branchId.equals(SystemConstants.User.BRANCH_ID)) {
			branchId = null;
		}
		List<?> result = findBySQLWithPagination("queryRoomingGuests", new String[] { branchId }, pagination, true);
		return result;
	}

	public List<?> queryMemberDetail(String checkUser) throws Exception {
		List<?> result = findBySQL("queryMemberDetail", new String[] { checkUser }, true);
		return result;
	}

	public List<?> queryChangeRoomByCon(Pagination pagination, String branchId, String checkId, String status,
			String checkUser, String startTime, String endTime, String recordUser) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Calendar c = Calendar.getInstance();
		if (branchId.equals(SystemConstants.User.BRANCH_ID)) {
			branchId = null;
		}

		if (StringUtil.isEmpty(startTime) || StringUtil.isEmpty(endTime)) {
			startTime = sdf.format(new Date())+" 04:00:00";
			c.setTime(new Date());
			c.set(Calendar.DATE, c.get(Calendar.DATE) + 1);
			endTime = sdf.format(c.getTime())+ " 04:00:00";
		} else {
			if(startTime.equals(endTime)){
				c.setTime(sdf.parse(startTime));
				c.set(Calendar.DATE, c.get(Calendar.DATE) + 1);
				endTime = sdf.format(c.getTime());
			}	
			startTime = startTime.trim() +" 04:00:00";
			endTime = endTime.trim() +" 04:00:00";
		}

		List<?> result = findBySQLWithPagination("ChangeRoomTableByCon", new String[] { branchId, branchId, branchId, checkId, status,
				checkUser, startTime, endTime, recordUser }, pagination, true);
		return result;
	}

	public List<?> queryChangeRoomTable(String branchId, Pagination pagination) throws Exception {
		if (branchId.equals(SystemConstants.User.BRANCH_ID)) {
			branchId = null;
		}
		List<?> result = findBySQLWithPagination("ChangeRoomTable", new String[] { branchId }, pagination, true);
		return result;
	}

	public List<?> queryRoomGuestDetail(String checkId) throws Exception {
		String sql = "select d.checkuser_id,d.checkuser_name,d.idcard,decode(d.gender,'1','男','0','女') gendor,d.mobile,decode(d.status,'1','有效','0','已删除') liveStatus,d.remark,d.record_user,d.record_time,"+
	       " d.check_id,decode(d.checkuser_type,'1','主客人','0','普通客人') checkusertype,t.member_id,t.member_name,t.login_name,decode(transtitles(t.member_rank,'TP_C_MEMBERRANK'," +
           " 'MEMBER_RANK','RANK_NAME'),'','非会员',transtitles(t.member_rank,'TP_C_MEMBERRANK','MEMBER_RANK','RANK_NAME')) memberRank,"+
	       " t.birthday,t.email,t.valid_time,t.invalid_time,decode(t.status,'1','有效','0','已删除') memberStatus from TB_C_MEMBER t,tb_c_checkuser d where t.idcard(+) = d.idcard and d.checkin_type = '2' and d.check_id ='"
		   + checkId + "'";
		List<?> result = findBySQL(sql);
		return result;
	}

	public List<?> accountRecordDetailByCon(Pagination pagination, String startTime, String endTime, String branchId,
			String checkId, String recordType, String accountStatus, String payMent, String recordUser,
			String projectType) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Calendar c = Calendar.getInstance();
		if (branchId.equals(SystemConstants.User.BRANCH_ID))
			branchId = "%";
		if (StringUtil.isEmpty(startTime) || StringUtil.isEmpty(endTime)) {
			startTime = sdf.format(new Date()) + " 04:00";
			c.setTime(new Date());
			c.set(Calendar.DATE, c.get(Calendar.DATE) + 1);
			endTime = sdf.format(c.getTime())+ " 04:00";
		} else {
			if(startTime.equals(endTime)){
				c.setTime(sdf.parse(startTime));
				c.set(Calendar.DATE, c.get(Calendar.DATE) + 1);
				endTime = sdf.format(c.getTime());
			}	
			startTime = startTime.trim() +" 04:00";
			endTime = endTime.trim() +" 04:00";
		}

		List<?> result = findBySQLWithPagination("accountDetailQuery", new String[] { branchId, recordType, checkId,
				endTime, startTime, projectType, recordUser }, pagination, true);
		return result;
	}

	public List<?> accountRecordDetailSumByCon(String startTime, String endTime, String branchId, String checkId,
			String recordType, String accountStatus, String payMent, String recordUser, String projectType) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Calendar c = Calendar.getInstance();
		if (branchId.equals(SystemConstants.User.BRANCH_ID))
			branchId = "%";
		if (StringUtil.isEmpty(startTime) || StringUtil.isEmpty(endTime)) {
			startTime = sdf.format(new Date()) + " 04:00";
			c.setTime(new Date());
			c.set(Calendar.DATE, c.get(Calendar.DATE) + 1);
			endTime = sdf.format(c.getTime())+ " 04:00";
		} else {
			if(startTime.equals(endTime)){
				c.setTime(sdf.parse(startTime));
				c.set(Calendar.DATE, c.get(Calendar.DATE) + 1);
				endTime = sdf.format(c.getTime());
			}	
			startTime = startTime.trim() +" 04:00";
			endTime = endTime.trim() +" 04:00";
		}

		List<?> result = findBySQL("accountDetailSum", new String[] { branchId, recordType, checkId, endTime,
				startTime, projectType, recordUser }, true);
		return result;
	}

	public List<?> accountRecordTotal(Pagination pagination, String startTime, String endTime, String branchId) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Calendar c = Calendar.getInstance();
		Integer pageNum = 0;
		Integer rows = 0;
		if (branchId.equals(SystemConstants.User.BRANCH_ID))
			branchId = "%";
		if (StringUtil.isEmpty(startTime) || StringUtil.isEmpty(endTime)) {
			startTime = sdf.format(new Date()) + " 04:00";
			c.setTime(new Date());
			c.set(Calendar.DATE, c.get(Calendar.DATE) + 1);
			endTime = sdf.format(c.getTime())+ " 04:00";
		} else {
			if(startTime.equals(endTime)){
				try {
					c.setTime(sdf.parse(startTime));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				c.set(Calendar.DATE, c.get(Calendar.DATE) + 1);
				endTime = sdf.format(c.getTime());
			}	
			startTime = startTime.trim() +" 04:00";
			endTime = endTime.trim() +" 04:00";
		}


		if (pagination != null) {
			pageNum = pagination.getPageNum();
			rows = pagination.getRows();
		}
		String params = pageNum + "," + rows + "," + startTime + "," + endTime + "," + branchId;
		List<?> result = callProcByPagination("pk_test.account_gather_sum", params, pagination);
		return result;
	}

	public List<?> accountRecordTotalSum(String startTime, String endTime, String branchId) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Calendar c = Calendar.getInstance();
		if (branchId.equals(SystemConstants.User.BRANCH_ID)) {
			branchId = "%";
		}
		if (StringUtil.isEmpty(startTime) || StringUtil.isEmpty(endTime)) {
			startTime = sdf.format(new Date()) +" 04:00";
			c.setTime(new Date());
			c.set(Calendar.DATE, c.get(Calendar.DATE) + 1);
			endTime = sdf.format(c.getTime())+ " 04:00";
		} else {
			if(startTime.equals(endTime)){
				try {
					c.setTime(sdf.parse(startTime));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				c.set(Calendar.DATE, c.get(Calendar.DATE) + 1);
				endTime = sdf.format(c.getTime());
			}	
			startTime = startTime.trim() +" 04:00";
			endTime = endTime.trim() +" 04:00";
		}
	 
		
		List<?> result = findBySQL("accountSum", new String[] { branchId, endTime, startTime }, true);
		return result;
	}

	public List<?> cancelOutDetail(Pagination pagination, String startTime, String endTime, String recordUser,
			String branchId) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Calendar c = Calendar.getInstance();
		if (branchId.equals(SystemConstants.User.BRANCH_ID)) {
			branchId = "%";
		}
		if (StringUtil.isEmpty(startTime) || StringUtil.isEmpty(endTime)) {
			startTime = sdf.format(new Date()) + " 04:00";
			c.setTime(new Date());
			c.set(Calendar.DATE, c.get(Calendar.DATE) + 1);
			endTime = sdf.format(c.getTime())+ " 04:00";
		} else {
			if(startTime.equals(endTime)){
				try {
					c.setTime(sdf.parse(startTime));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				c.set(Calendar.DATE, c.get(Calendar.DATE) + 1);
				endTime = sdf.format(c.getTime());
			}	
			startTime = startTime.trim() +" 04:00";
			endTime = endTime.trim() +" 04:00";
		}
		
		List<?> result = findBySQLWithPagination("cancelOutDetail", new String[] { branchId, recordUser, endTime,
				startTime }, pagination, true);
		return result;
	}

	public List<?> goodsSaleDetail(Pagination pagination, String startTime, String endTime, String recordUser,
			String branchId, String goodsName, String goodsKind) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Calendar c = Calendar.getInstance();
		if (branchId.equals(SystemConstants.User.BRANCH_ID)) {
			branchId = "%";
		}
		
		if (StringUtil.isEmpty(startTime) || StringUtil.isEmpty(endTime)) {
			startTime = sdf.format(new Date())+ " 04:00";
			c.setTime(new Date());
			c.set(Calendar.DATE, c.get(Calendar.DATE) + 1);
			endTime = sdf.format(c.getTime())+ " 04:00";
		} else {
			if(startTime.equals(endTime)){
				try {
					c.setTime(sdf.parse(startTime));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				c.set(Calendar.DATE, c.get(Calendar.DATE) + 1);
				endTime = sdf.format(c.getTime());
			}	
			startTime = startTime.trim() +" 04:00";
			endTime = endTime.trim() +" 04:00";
		}
		
		List<?> result = findBySQLWithPagination("goodsSaleDetail", new String[] { endTime, startTime, recordUser,
				goodsName, goodsKind, branchId }, pagination, true);
		return result;
	}

	public List<?> ttvDetail(Pagination pagination, String branchId, String startTime, String endTime,
			String recordUser, String status) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Calendar c = Calendar.getInstance();
		if (branchId.equals(SystemConstants.User.BRANCH_ID)) {
			branchId = null;
		}
		if (StringUtil.isEmpty(startTime) || StringUtil.isEmpty(endTime)) {
			startTime = sdf.format(new Date())+ " 04:00";
			c.setTime(new Date());
			c.set(Calendar.DATE, c.get(Calendar.DATE) + 1);
			endTime = sdf.format(c.getTime())+ " 04:00";
		} else {
			if(startTime.equals(endTime)){
				try {
					c.setTime(sdf.parse(startTime));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				c.set(Calendar.DATE, c.get(Calendar.DATE) + 1);
				endTime = sdf.format(c.getTime());
			}	
			startTime = startTime.trim() +" 04:00";
			endTime = endTime.trim() +" 04:00";
		}

		
		List<?> result = findBySQLWithPagination("ttvDetail", new String[] { endTime, startTime, branchId, recordUser,
				status }, pagination, true);
		return result;
	}

	public List<?> queryBillStatusList() {
		List<?> result = this.findBySQL("querySysparamList", new String[] { "BILLSTATUS" }, true);
		return result == null ? null : result;
	}

	public List<?> querypayMentList() {
		List<?> result = this.findBySQL("querySysparamList", new String[] { "PAYMENT" }, true);
		return result == null ? null : result;
	}

	public List<?> cancelOutSum(Pagination pagination, String startTime, String endTime, String recordUser,
			String branchId) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Calendar c = Calendar.getInstance();
		if (branchId.equals(SystemConstants.User.BRANCH_ID)) {
			branchId = "%";
		}
		
		if (StringUtil.isEmpty(startTime) || StringUtil.isEmpty(endTime)) {
			startTime = sdf.format(new Date())+ " 04:00";
			c.setTime(new Date());
			c.set(Calendar.DATE, c.get(Calendar.DATE) + 1);
			endTime = sdf.format(c.getTime())+ " 04:00";
		} else {
			if(startTime.equals(endTime)){
				try {
					c.setTime(sdf.parse(startTime));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				c.set(Calendar.DATE, c.get(Calendar.DATE) + 1);
				endTime = sdf.format(c.getTime());
			}	
			startTime = startTime.trim() +" 04:00";
			endTime = endTime.trim() +" 04:00";
		}
		
		List<?> result = findBySQL("cancelOutSum", new String[] { branchId, recordUser, endTime, startTime }, true);
		return result;
	}

	public List<?> goodsSaleSum(Pagination pagination, String startTime, String endTime, String recordUser,
			String branchId, String goodsName, String goodsKind) {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String sql = "select sum(s.amount) sumAmount,sum(s.totalPrice) sumTotalPrice from (select k.logId, k.debts, k.checkId,k.oldBranchId, k.roomId,k.goodId, k.goodsName, k.categoryId, k.specifications, k.unit, k.saleType, k.price, k.amount,k.totalPrice, k.payType, k.recordTime, k.recordUser, k.remark from (select  s.log_id logId,"
				+ " s.branch_id oldBranchId, transTitles(s.branch_id,'tb_c_branch','branch_id','branch_name') branchId, decode(s.debts,'1','工作账','2','房账') debts, s.check_id checkId, s.room_id roomId, s.goods_name goodId,"
				+ " c.goods_name goodsName, transTitles(s.category_id,'tp_c_goodscategory','category_id','category_name') categoryId, c.specifications specifications, c.unit unit,"
				+ " decode(c.sale_type,'1','店内销售','2','网上商城','3','积分商城') saleType, s.price price, s.amount amount,(s.price*s.amount) totalPrice, decode(s.pay_type,'1','挂房账','2','现金','3','银行卡','4','微信','5','支付宝') payType,"
				+ " to_char(s.record_time, 'yyyy/MM/dd HH24:mi') recordTime, decode(s.record_user,'1','admin',transTitles(s.record_user,'tb_c_staff','staff_id','staff_name')) recordUser, s.remark remark from tb_p_salelog s,tb_c_goods c where s.goods_name = c.goods_id) k";
		if (StringUtil.isEmpty(startTime) || StringUtil.isEmpty(startTime)) {
			startTime = sdf.format(new Date()) + " 04:00:00";
			c.setTime(new Date());
			c.set(Calendar.DATE, c.get(Calendar.DATE) + 1);
			endTime = sdf.format(c.getTime()) + " 04:00:00";
		} else {
			if(startTime.equals(endTime)){
				try {
					c.setTime(sdf.parse(startTime));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				c.set(Calendar.DATE, c.get(Calendar.DATE) + 1);
				endTime = sdf.format(c.getTime());
			}	
			startTime = startTime.trim() +" 04:00:00";
			endTime = endTime.trim() +" 04:00:00";
		}
		if (endTime != null && !"".equals(endTime)) {
			sql = sql + " where to_date(k.recordTime, 'yyyy/MM/dd HH24:mi:ss') <=  to_date('" + endTime
					+ "', 'yyyy/MM/dd HH24:mi:ss')";
		}
		if (startTime != null && !"".equals(startTime)) {
			sql = sql + " and to_date(k.recordTime, 'yyyy/MM/dd HH24:mi:ss') >= to_date('" + startTime
					+ "', 'yyyy/MM/dd HH24:mi:ss')";
		}
		if (recordUser != null && !"".equals(recordUser)) {
			sql = sql + " and k.recordUser like '%" + recordUser + "%'";
		}

		if (goodsName != null && !"".equals(goodsName)) {
			sql = sql + " and k.goodsName like '%" + goodsName + "%'";
		}

		if (goodsKind != null && !"".equals(goodsKind)) {
			sql = sql + " and k.categoryId like '%" + goodsKind + "%'";
		}
		if (branchId != null && !"".equals(branchId) && branchId != SystemConstants.User.BRANCH_ID) {
			sql = sql + " and k.oldBranchId like '%" + branchId + "%'";
		}

		sql = sql + " order by k.recordTime desc )s";
		List<?> result = findBySQLWithPagination(sql, pagination);
		return result;
	}

	public List<?> queryAllOfGoodsType() {
		List<?> result = this.findBySQL("querygoodsType", true);
		return result == null ? null : result;
	}

	public List<?> ttvDetailSum(Pagination pagination, String branchId, String startTime, String endTime,
			String recordUser, String status) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Calendar c = Calendar.getInstance();
		if (StringUtil.isEmpty(startTime) || StringUtil.isEmpty(endTime)) {
			startTime = sdf.format(new Date()) + " 04:00";
			c.setTime(new Date());
			c.set(Calendar.DATE, c.get(Calendar.DATE) + 1);
			endTime = sdf.format(c.getTime())+ " 04:00";
		} else {
			if(startTime.equals(endTime)){
				try {
					c.setTime(sdf.parse(startTime));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				c.set(Calendar.DATE, c.get(Calendar.DATE) + 1);
				endTime = sdf.format(c.getTime());
			}	
			startTime = startTime.trim() + " 04:00";
			endTime = endTime.trim() + " 04:00";
		}

		if (branchId.equals(SystemConstants.User.BRANCH_ID)) {
			branchId = null;
		}
		List<?> result = findBySQLWithPagination("ttvDetailSum", new String[] { endTime, startTime, branchId,
				recordUser, status }, pagination, true);
		return result;
	}
}
