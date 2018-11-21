package com.ideassoft.apartment.service;

import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.ideassoft.bean.Member;
import com.ideassoft.bean.OperateLog;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.constants.SystemConstants;
import com.ideassoft.core.dao.GenericDAOImpl;
import com.ideassoft.util.IPUtil;

@Service
public class ApartmentLogServiceInPms extends GenericDAOImpl {
	
	// 会员积分金额变动
		public void houseMemberAccount(LoginUser loginUser, String status,double money,long score,HttpServletRequest request) throws UnknownHostException {
			try {
				
				String[] statuss = SystemConstants.LogModule.CLEANAPPLYSTATUS;
				String sequences = getSequence("SEQ_OPERATELOG_ID");
				SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
				String strdate = sdf.format(new Date());
				//String operid = InetAddress.getLocalHost().toString();// IP地址
				String operid = IPUtil.getIpAddr(request);
				operid = (String) operid.subSequence(operid.indexOf("/") + 1, operid.length());
				String logid = strdate + loginUser.getStaff().getBranchId() + sequences;
				OperateLog operatelog = new OperateLog();
				operatelog.setLogId(logid);
				operatelog.setOperType("13");
				operatelog.setOperModule(SystemConstants.LogModule.HOUSEMEMBERACCOUNT);
				operatelog.setContent(loginUser.getStaff().getStaffName() + SystemConstants.LogModule.HOUSEMEMBERACCOUNT +money +"积分设置"+score+","+statuss[Integer.parseInt(status)]);
				operatelog.setRecordUser(loginUser.getStaff().getStaffId());
				operatelog.setRecordTime(new Date());
				operatelog.setOperIp(operid);
				// operatelog.setRemark(remark);
				operatelog.setBranchId(loginUser.getStaff().getBranchId());
				save(operatelog);
			} catch (RuntimeException e) {
				e.printStackTrace();
			}
		}

	// 插入保洁记录日志
	public void updatecleanapplyrecordsuccess(LoginUser loginUser, String remark, String type,HttpServletRequest request) throws UnknownHostException {
		try {
			String[] status = SystemConstants.LogModule.CLEANAPPLYSTATUS;
			String sequences = getSequence("SEQ_OPERATELOG_ID");
			SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
			String strdate = sdf.format(new Date());
			//String operid = InetAddress.getLocalHost().toString();// IP地址
			String operid = IPUtil.getIpAddr(request);
			operid = (String) operid.subSequence(operid.indexOf("/") + 1, operid.length());
			String logid = strdate + loginUser.getStaff().getBranchId() + sequences;
			OperateLog operatelog = new OperateLog();
			operatelog.setLogId(logid);
			operatelog.setOperType(type);
			operatelog.setOperModule(SystemConstants.LogModule.CLEANAPPLY);
			operatelog.setContent(loginUser.getStaff().getStaffName() + SystemConstants.LogModule.CLEANAPPLY+status[0]);
			operatelog.setRecordUser(loginUser.getStaff().getStaffId());
			operatelog.setRecordTime(new Date());
			operatelog.setOperIp(operid);
			operatelog.setRemark(remark);
			operatelog.setBranchId(loginUser.getStaff().getBranchId());
			save(operatelog);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}
	
	public void updatecleanapplyrecordfailed(LoginUser loginUser, String remark, String type,HttpServletRequest request) throws UnknownHostException {
		try {
			String[] status = SystemConstants.LogModule.CLEANAPPLYSTATUS;
			String sequences = getSequence("SEQ_OPERATELOG_ID");
			SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
			String strdate = sdf.format(new Date());
			//String operid = InetAddress.getLocalHost().toString();// IP地址
			String operid = IPUtil.getIpAddr(request);
			operid = (String) operid.subSequence(operid.indexOf("/") + 1, operid.length());
			String logid = strdate + loginUser.getStaff().getBranchId() + sequences;
			OperateLog operatelog = new OperateLog();
			operatelog.setLogId(logid);
			operatelog.setOperType(type);
			operatelog.setOperModule(SystemConstants.LogModule.CLEANAPPLY);
			operatelog.setContent(loginUser.getStaff().getStaffName() + SystemConstants.LogModule.CLEANAPPLY+status[1]);
			operatelog.setRecordUser(loginUser.getStaff().getStaffId());
			operatelog.setRecordTime(new Date());
			operatelog.setOperIp(operid);
			operatelog.setRemark(remark);
			operatelog.setBranchId(loginUser.getStaff().getBranchId());
			save(operatelog);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}

	// 撤销保洁申请日志
	public void deletecleanapplyrecordfailed(LoginUser loginUser, String remark, String type,HttpServletRequest request) throws UnknownHostException {
		try {
			String[] status = SystemConstants.LogModule.CLEANAPPLYSTATUS;
			String sequences = getSequence("SEQ_OPERATELOG_ID");
			SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
			String strdate = sdf.format(new Date());
			//String operid = InetAddress.getLocalHost().toString();// IP地址
			String operid = IPUtil.getIpAddr(request);
			operid = (String) operid.subSequence(operid.indexOf("/") + 1, operid.length());
			String logid = strdate + loginUser.getStaff().getBranchId() + sequences;
			OperateLog operatelog = new OperateLog();
			operatelog.setLogId(logid);
			operatelog.setOperType(type);
			operatelog.setOperModule(SystemConstants.LogModule.DELETECLEANAPPLY);
			operatelog.setContent(loginUser.getStaff().getStaffName() + SystemConstants.LogModule.DELETECLEANAPPLY+status[1]);
			operatelog.setRecordUser(loginUser.getStaff().getStaffId());
			operatelog.setRecordTime(new Date());
			operatelog.setOperIp(operid);
			operatelog.setRemark(remark);
			operatelog.setBranchId(loginUser.getStaff().getBranchId());
			save(operatelog);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}
	public void deletecleanapplyrecordsuccess(LoginUser loginUser, String remark, String type,HttpServletRequest request) throws UnknownHostException {
		try {
			String[] status = SystemConstants.LogModule.CLEANAPPLYSTATUS;
			String sequences = getSequence("SEQ_OPERATELOG_ID");
			SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
			String strdate = sdf.format(new Date());
			//String operid = InetAddress.getLocalHost().toString();// IP地址
			String operid = IPUtil.getIpAddr(request);
			operid = (String) operid.subSequence(operid.indexOf("/") + 1, operid.length());
			String logid = strdate + loginUser.getStaff().getBranchId() + sequences;
			OperateLog operatelog = new OperateLog();
			operatelog.setLogId(logid);
			operatelog.setOperType(type);
			operatelog.setOperModule(SystemConstants.LogModule.DELETECLEANAPPLY);
			operatelog.setContent(loginUser.getStaff().getStaffName() + SystemConstants.LogModule.DELETECLEANAPPLY+status[0]);
			operatelog.setRecordUser(loginUser.getStaff().getStaffId());
			operatelog.setRecordTime(new Date());
			operatelog.setOperIp(operid);
			operatelog.setRemark(remark);
			operatelog.setBranchId(loginUser.getStaff().getBranchId());
			save(operatelog);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}

	// 新增保洁申请日志
	public void addcleanapplyrecord(LoginUser loginUser, String remark, String type,HttpServletRequest request) throws UnknownHostException {
		try {
			String sequences = getSequence("SEQ_OPERATELOG_ID");
			SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
			String strdate = sdf.format(new Date());
			//String operid = InetAddress.getLocalHost().toString();// IP地址
			String operid = IPUtil.getIpAddr(request);
			operid = (String) operid.subSequence(operid.indexOf("/") + 1, operid.length());
			String logid = strdate + loginUser.getStaff().getBranchId() + sequences;
			OperateLog operatelog = new OperateLog();
			operatelog.setLogId(logid);
			operatelog.setOperType(type);
			operatelog.setOperModule(SystemConstants.LogModule.ADDCLEANAPPLY);
			operatelog.setContent(loginUser.getStaff().getStaffName() + SystemConstants.LogModule.ADDCLEANAPPLY);
			operatelog.setRecordUser(loginUser.getStaff().getStaffId());
			operatelog.setRecordTime(new Date());
			operatelog.setOperIp(operid);
			operatelog.setRemark(remark);
			operatelog.setBranchId(loginUser.getStaff().getBranchId());
			save(operatelog);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}
	
	// 保洁退款
		public void cleanapplyRefund(LoginUser loginUser, String remark, String type,HttpServletRequest request) throws UnknownHostException {
			try {
				String sequences = getSequence("SEQ_OPERATELOG_ID");
				SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
				String strdate = sdf.format(new Date());
				//String operid = InetAddress.getLocalHost().toString();// IP地址
				String operid = IPUtil.getIpAddr(request);
				operid = (String) operid.subSequence(operid.indexOf("/") + 1, operid.length());
				String logid = strdate + loginUser.getStaff().getBranchId() + sequences;
				OperateLog operatelog = new OperateLog();
				operatelog.setLogId(logid);
				operatelog.setOperType(type);
				operatelog.setOperModule(SystemConstants.LogModule.ADDCLEANAPPLY);
				operatelog.setContent(loginUser.getStaff().getStaffName() + SystemConstants.LogModule.CLEANREFUND);
				operatelog.setRecordUser(loginUser.getStaff().getStaffId());
				operatelog.setRecordTime(new Date());
				operatelog.setOperIp(operid);
				operatelog.setRemark(remark);
				operatelog.setBranchId(loginUser.getStaff().getBranchId());
				save(operatelog);
			} catch (RuntimeException e) {
				e.printStackTrace();
			}
		}


	// 预约公寓
	public void apartmentReserved(LoginUser loginUser, String status, Member member,HttpServletRequest request) throws UnknownHostException {
		try {
			String[] statuss = SystemConstants.LogModule.APARTMENTRESERVEDSTATUS;
			String sequences = getSequence("SEQ_OPERATELOG_ID");
			SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
			String strdate = sdf.format(new Date());
			//String operid = InetAddress.getLocalHost().toString();// IP地址
			String operid = IPUtil.getIpAddr(request);
			operid = (String) operid.subSequence(operid.indexOf("/") + 1, operid.length());
			String logid = strdate + loginUser.getStaff().getBranchId() + sequences;
			OperateLog operatelog = new OperateLog();
			operatelog.setLogId(logid);
			operatelog.setOperType("9");
			operatelog.setOperModule(SystemConstants.LogModule.APARTMENTRESERVED);
			operatelog.setContent(member.getMemberName() + ":" +  SystemConstants.LogModule.APARTMENTRESERVED 
					+ "被" + statuss[Integer.parseInt(status)]);
			operatelog.setRecordUser(loginUser.getStaff().getStaffId());
			operatelog.setRecordTime(new Date());
			operatelog.setOperIp(operid);
			// operatelog.setRemark(remark);
			operatelog.setBranchId(loginUser.getStaff().getBranchId());
			save(operatelog);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}

	// 公寓退房
	public void apartmentCheckOut(LoginUser loginUser, Member member,HttpServletRequest request) throws UnknownHostException {
		try {
			String sequences = getSequence("SEQ_OPERATELOG_ID");
			SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
			String strdate = sdf.format(new Date());
			//String operid = InetAddress.getLocalHost().toString();// IP地址
			String operid = IPUtil.getIpAddr(request);
			operid = (String) operid.subSequence(operid.indexOf("/") + 1, operid.length());
			String logid = strdate + loginUser.getStaff().getBranchId() + sequences;
			OperateLog operatelog = new OperateLog();
			operatelog.setLogId(logid);
			operatelog.setOperType("8");
			operatelog.setOperModule(SystemConstants.LogModule.APARTMENTCHECKOUT);
			operatelog.setContent(member.getMemberName() + SystemConstants.LogModule.APARTMENTCHECKOUT + "已被处理!");
			operatelog.setRecordUser(loginUser.getStaff().getStaffId());
			operatelog.setRecordTime(new Date());
			operatelog.setOperIp(operid);
			// operatelog.setRemark(remark);
			operatelog.setBranchId(loginUser.getStaff().getBranchId());
			save(operatelog);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}
	
	
	// 民宿录入
	public void houseAddLog(LoginUser loginUser, String status,HttpServletRequest request) throws UnknownHostException {
		try {
			String[] statuss = SystemConstants.LogModule.CLEANAPPLYSTATUS;
			String sequences = getSequence("SEQ_OPERATELOG_ID");
			SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
			String strdate = sdf.format(new Date());
			//String operid = InetAddress.getLocalHost().toString();// IP地址
			String operid = IPUtil.getIpAddr(request);
			operid = (String) operid.subSequence(operid.indexOf("/") + 1, operid.length());
			String logid = strdate + loginUser.getStaff().getBranchId() + sequences;
			OperateLog operatelog = new OperateLog();
			operatelog.setLogId(logid);
			operatelog.setOperType("13");
			operatelog.setOperModule(SystemConstants.LogModule.HOUSEADD);
			operatelog.setContent(loginUser.getStaff().getStaffName() + SystemConstants.LogModule.HOUSEADD + statuss[Integer.parseInt(status)]);
			operatelog.setRecordUser(loginUser.getStaff().getStaffId());
			operatelog.setRecordTime(new Date());
			operatelog.setOperIp(operid);
			// operatelog.setRemark(remark);
			operatelog.setBranchId(loginUser.getStaff().getBranchId());
			save(operatelog);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}
	
	// 民宿编辑
	public void houseEditLog(LoginUser loginUser, String status,HttpServletRequest request) throws UnknownHostException {
		try {
			String[] statuss = SystemConstants.LogModule.CLEANAPPLYSTATUS;
			String sequences = getSequence("SEQ_OPERATELOG_ID");
			SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
			String strdate = sdf.format(new Date());
			//String operid = InetAddress.getLocalHost().toString();// IP地址
			String operid = IPUtil.getIpAddr(request);
			operid = (String) operid.subSequence(operid.indexOf("/") + 1, operid.length());
			String logid = strdate + loginUser.getStaff().getBranchId() + sequences;
			OperateLog operatelog = new OperateLog();
			operatelog.setLogId(logid);
			operatelog.setOperType("13");
			operatelog.setOperModule(SystemConstants.LogModule.HOUSEEDIT);
			operatelog.setContent(loginUser.getStaff().getStaffName() + SystemConstants.LogModule.HOUSEEDIT + statuss[Integer.parseInt(status)]);
			operatelog.setRecordUser(loginUser.getStaff().getStaffId());
			operatelog.setRecordTime(new Date());
			operatelog.setOperIp(operid);
			// operatelog.setRemark(remark);
			operatelog.setBranchId(loginUser.getStaff().getBranchId());
			save(operatelog);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}
	
	// 合同录入
	public void contrartadd(LoginUser loginUser, String status,HttpServletRequest request) throws UnknownHostException {
		try {
			String[] statuss = SystemConstants.LogModule.CLEANAPPLYSTATUS;
			String sequences = getSequence("SEQ_OPERATELOG_ID");
			SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
			String strdate = sdf.format(new Date());
			//String operid = InetAddress.getLocalHost().toString();// IP地址
			String operid = IPUtil.getIpAddr(request);
			operid = (String) operid.subSequence(operid.indexOf("/") + 1, operid.length());
			String logid = strdate + loginUser.getStaff().getBranchId() + sequences;
			OperateLog operatelog = new OperateLog();
			operatelog.setLogId(logid);
			operatelog.setOperType("14");
			operatelog.setOperModule(SystemConstants.LogModule.CONTRARTADD);
			operatelog.setContent(loginUser.getStaff().getStaffName() + SystemConstants.LogModule.CONTRARTADD + statuss[Integer.parseInt(status)]);
			operatelog.setRecordUser(loginUser.getStaff().getStaffId());
			operatelog.setRecordTime(new Date());
			operatelog.setOperIp(operid);
			// operatelog.setRemark(remark);
			operatelog.setBranchId(loginUser.getStaff().getBranchId());
			save(operatelog);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}
	
	// 合同删除
	public void contrartdelete(LoginUser loginUser,HttpServletRequest request) throws UnknownHostException {
		try {
			//String[] statuss = SystemConstants.LogModule.CLEANAPPLYSTATUS;
			String sequences = getSequence("SEQ_OPERATELOG_ID");
			SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
			String strdate = sdf.format(new Date());
			//String operid = InetAddress.getLocalHost().toString();// IP地址
			String operid = IPUtil.getIpAddr(request);
			operid = (String) operid.subSequence(operid.indexOf("/") + 1, operid.length());
			String logid = strdate + loginUser.getStaff().getBranchId() + sequences;
			OperateLog operatelog = new OperateLog();
			operatelog.setLogId(logid);
			operatelog.setOperType("14");
			operatelog.setOperModule(SystemConstants.LogModule.CONTRARTEDIT);
			operatelog.setContent(loginUser.getStaff().getStaffName() + SystemConstants.LogModule.CONTRARTEDIT );
			operatelog.setRecordUser(loginUser.getStaff().getStaffId());
			operatelog.setRecordTime(new Date());
			operatelog.setOperIp(operid);
			// operatelog.setRemark(remark);
			operatelog.setBranchId(loginUser.getStaff().getBranchId());
			save(operatelog);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}

}
