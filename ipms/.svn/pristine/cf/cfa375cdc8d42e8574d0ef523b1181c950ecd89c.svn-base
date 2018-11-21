package com.ideassoft.basic.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.ideassoft.core.dao.GenericDAOImpl;
import com.ideassoft.core.page.Pagination;
@Service
public class StatisticsBasicService extends GenericDAOImpl {
		

		// 房间汇总房单详情查询
		public List<?> getcheck(String roomtype, String startdate, String enddate, Pagination pagination) throws Exception {
			return findBySQLWithPagination("roomcheckdetail", new String[] { startdate, enddate, startdate, enddate,
					enddate, enddate, startdate, startdate, enddate, startdate, startdate, enddate, enddate, startdate,
					roomtype, startdate, enddate, startdate, enddate }, pagination, true);
		}
		
		// 房间销售明细订单详情查询
		public List<?> getroomorders(String roomtype, String startdate, String enddate, Pagination pagination) throws Exception {
			return findBySQLWithPagination("roomoredrdetail", new String[] { roomtype, startdate, enddate }, pagination, true);
		}
		
		// 客户来源分析查看详情
		public List<?> showSourceInformation(String t, String branchid, Pagination pagination) throws Exception {
			List<?> result = findBySQLWithPagination("showSourceDetail", new String[] { t, branchid }, pagination, true);
			return result;
		}
		
		
		// 会员层次统计详情
		public List<?> showMemberInformation(String t, Pagination pagination) {
			List<?> result = findBySQLWithPagination("showMemberDetail", new String[] { t }, pagination, true);
			return result;
		}
		
		// 会员消费详情
		public List<?> showConsumeInformation(String memberID, Pagination pagination) {
			List<?> result = findBySQLWithPagination("showConsumeDetail", new String[] { memberID }, pagination, true);
			return result;
		}
}
