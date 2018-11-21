package com.ideassoft.pmsinhouse.service;

import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.ideassoft.bean.OperateLog;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.constants.SystemConstants;
import com.ideassoft.core.dao.GenericDAOImpl;
import com.ideassoft.util.IPUtil;
@Service
public class HouseCleanService extends GenericDAOImpl {

	
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
}
