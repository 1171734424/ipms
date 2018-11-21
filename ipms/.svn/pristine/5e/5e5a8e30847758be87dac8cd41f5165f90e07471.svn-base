package com.ideassoft.crm.service;


import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.ideassoft.bean.FreezeMember;
import com.ideassoft.bean.Member;
import com.ideassoft.bean.OperateLog;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.constants.SystemConstants;
import com.ideassoft.core.dao.GenericDAOImpl;
import com.ideassoft.util.IPUtil;

@Service
public class LogSer extends GenericDAOImpl {
	// 保存,修改,删除会员日志
	public void memberLog(LoginUser loginUser, Member bean, String type,HttpServletRequest request) throws UnknownHostException {
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
			operatelog.setOperModule(SystemConstants.LogModule.MEMBERINFO);
			if ("1".equals(type))
				operatelog.setContent(SystemConstants.LogModule.SAVE + bean.getMemberName());
			else if ("2".equals(type))
				operatelog.setContent(SystemConstants.LogModule.DEL + bean.getMemberName());
			else if ("3".equals(type))
				operatelog.setContent(SystemConstants.LogModule.UPDATE + bean.getMemberName());
			operatelog.setRecordUser(loginUser.getStaff().getStaffId());
			operatelog.setRecordTime(new Date());
			operatelog.setOperIp(operid);
			//operatelog.setRemark(bean.getRemark());
			operatelog.setBranchId(loginUser.getStaff().getBranchId());
			save(operatelog);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}
			
	// 储值卡充值
	public void changeMemberScoreAndReserveLog(LoginUser loginUser, String remark, String type,HttpServletRequest request)
			throws UnknownHostException {
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
			operatelog.setOperType("4");
			operatelog.setOperModule(SystemConstants.LogModule.MEMBERQUERY);
			if ("4".equals(type))
				operatelog.setContent(SystemConstants.LogModule.CHANGERANK);
			else
				operatelog.setContent(SystemConstants.LogModule.CHANGESCORE);
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
			
	// 保存,修改,删除冻结会员日志
	public void freezeMemberLog(LoginUser loginUser, FreezeMember bean, String type,HttpServletRequest request) throws UnknownHostException {
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
			operatelog.setOperModule(SystemConstants.LogModule.MEMBERINFO);
			if ("1".equals(type))
				operatelog.setContent(SystemConstants.LogModule.SAVE + bean.getMemberName());
			else if ("2".equals(type))
				operatelog.setContent(SystemConstants.LogModule.DEL + bean.getMemberName());
			else if ("3".equals(type))
				operatelog.setContent(SystemConstants.LogModule.UPDATE + bean.getMemberName());
			operatelog.setRecordUser(loginUser.getStaff().getStaffId());
			operatelog.setRecordTime(new Date());
			operatelog.setOperIp(operid);
			//operatelog.setRemark(bean.getRemark());
			operatelog.setBranchId(loginUser.getStaff().getBranchId());
			save(operatelog);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}
}
