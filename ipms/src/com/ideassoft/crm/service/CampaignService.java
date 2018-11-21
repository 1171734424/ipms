package com.ideassoft.crm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ideassoft.bean.Branch;
import com.ideassoft.bean.CampaignRule;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.constants.CommonConstants;
import com.ideassoft.core.dao.GenericDAOImpl;
import com.ideassoft.core.page.Pagination;

@Service
public class CampaignService extends GenericDAOImpl {
	

	public List<?> queryCouponGroupDetail(String groupId, Pagination pagination) {
		List<?> result = findBySQLWithPagination("couponDetail", new String[] { groupId }, pagination, true);
		return result;
	}

	public List<?> querybranchNameAndType(LoginUser loginUser, String branchName, String branchType, Pagination pagination) {
		String sql = "";
		List<?> result = null;
		 Branch branch = (Branch) this.findOneByProperties(Branch.class, "branchId", loginUser.getStaff().getBranchId());
		if(loginUser.getStaff().getStaffId().equals("100001") || ( branch != null && branch.getRank().equals(CommonConstants.SystemLevel.MarketCenter))){
			sql = "selectBranchIdType";
			result = findBySQLWithPagination(sql, new String[] { branchName, branchType },
					pagination, true);
		} else {
			sql = "selectBranchIdInSub";
			result = findBySQLWithPagination(sql, new String[] { loginUser.getStaff().getBranchId() , branchName, branchType},
					pagination, true);
		}
		
		return result;
	}

	public List<?> queryUsingPersonAndType(String campaignId) {
		List<?> result = findBySQL("queryPersonAndType", new String[] { campaignId }, true);
		return result != null && !result.isEmpty() ? result : null;
	}

	public void deleteCampaignRules(String campaignId) {
		List<?> result = this.findByProperties(CampaignRule.class, "campaignId", campaignId);
		for (int i = 0; i < result.size(); i++) {
			// this.delete((CampaignRule)(result.get(i)));
			((CampaignRule) (result.get(i))).setStatus("0");
			this.update((CampaignRule) (result.get(i)));
		}
	}



	public boolean isSameCampaignTwoCurrExist(String businessId, String startDate, String endDate, String usingPerson, Double operMoney)  {
		List<?> result = findBySQL("IsSameCampaignTwo", new String[] { businessId, usingPerson, operMoney.toString(), startDate, endDate,
				startDate, endDate, startDate, endDate },  true);
		if (result == null || result.size() == 0) {
			return false;
		} else {
			return true;
		}
	}


	public void updateCampaignStatus(String dataId) {
		String sql = "update TB_C_CAMPAIGNS set status = '0' where data_id='"+ dataId + "'";
		executeUpdateSQL(sql);
	}
}
