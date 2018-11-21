package com.ideassoft.pmsinhouse.service;

import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.ideassoft.bean.OperateLog;
import com.ideassoft.bean.SysParam;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.constants.CommonConstants;
import com.ideassoft.core.constants.SystemConstants;
import com.ideassoft.core.dao.GenericDAOImpl;
import com.ideassoft.util.IPUtil;

@Service
public class MemberInHousePmsService extends GenericDAOImpl{

	
	// 会员积分金额变动
	public void houseMemberAccountchange(LoginUser loginUser, String status,double money,long score,HttpServletRequest request) throws UnknownHostException {
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
