package com.ideassoft.crm.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hsqldb.lib.StringUtil;
import org.springframework.stereotype.Service;

import com.ideassoft.bean.Branch;
import com.ideassoft.bean.CheckOut;
import com.ideassoft.bean.Department;
import com.ideassoft.bean.Member;
import com.ideassoft.bean.SysParam;
import com.ideassoft.core.dao.GenericDAOImpl;
import com.ideassoft.crm.templateMessage.CheckoutApplyNoticeMsg;
import com.ideassoft.crm.templateMessage.TemplateMessageUtil;
import com.ideassoft.portal.DataDealPortalService;
import com.ideassoft.portal.IDataDealPortal;

@Service
public class CommonService extends GenericDAOImpl {

	public List<?> findTransData(String beanName, Date checkPoint,
			Date tempPoint) throws Exception {
		String hql = " from " + beanName + " where recordTime between ? and ?";
		return findByHQL(hql, new Object[] { checkPoint, tempPoint });
	}

	public Integer countSubDepartments(String departId) {
		List<?> departs = findByProperties(Department.class, "superDepart",
				departId);
		return departs != null ? departs.size() : 0;
	}

	public String Token() {
		//DataDealPortalService service = new DataDealPortalService();
		//IDataDealPortal portal = service.getDataDealPortalPort();
		List<?> list = findBySQL("queryRepairToken", true);
		String token = (String) ((Map<?, ?>) list.get(0)).get("CONTENT");
	//	String token = portal.call(1, 1, "{ function: \"repairApplication.getToken\", data:{token:" + "1"  +"} }");
//		return (SysParam) findOneByProperties(SysParam.class, "paramName",
//				"微信token", "paramType", "TOKEN", "status", "1");
		return token;
	}

	public void Weixin(Member member, CheckOut checkOut, String status,Branch branch) {
		DataDealPortalService service = new DataDealPortalService();
		IDataDealPortal portal = service.getDataDealPortalPort();
		String token = portal.call(1, 1, "{ function: \"repairApplication.getToken\", data:{token:" + "1"  +"} }");
		if (!StringUtil.isEmpty(member.getOpenId()) || !"null".equals(member.getOpenId())) {
			CheckoutApplyNoticeMsg serviceMsg = new CheckoutApplyNoticeMsg();
			serviceMsg.setFirst("您好!您提交的退房申请" + status + "!");
			serviceMsg.setBranchName(branch.getBranchName());
			serviceMsg.setRoomId(checkOut.getRoomId());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			serviceMsg.setBookingDate(sdf.format(checkOut.getCheckoutTime()));
			serviceMsg.setRemark("如您有任何疑问，请联系"+branch.getPhone()+"!");
			TemplateMessageUtil.sendCheckoutApplyNoticeMsg(token, member.getOpenId(), serviceMsg);
		}
	}

}
