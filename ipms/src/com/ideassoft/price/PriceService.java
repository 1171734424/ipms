package com.ideassoft.price;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.ideassoft.core.dao.GenericDAOImpl;

@Service
public class PriceService extends GenericDAOImpl{
	
//	public List<?> getPrice(String branchId, String theme, String roomType, String rpId, String memoTime, String level){
//		StringBuffer sql= new StringBuffer();
//		sql.append("select *");
//		sql.append("from (select t.*,row_number() over(partition by t.priority order by t.recordtime desc) rn from TB_P_PRICE_VOLATILITY t)");
//		sql.append("where branch_id = '").append(branchId).append("' and rn = '").append(level).append("' ");
//		if(!StringUtils.isEmpty(theme)){
//			sql.append("and theme = '").append(theme).append("' ");
//		}
//		if(!StringUtils.isEmpty(roomType)){
//			sql.append("and room_type = '").append(roomType).append("' ");			
//		}
//		if(!StringUtils.isEmpty(rpId)){
//			sql.append("and rp_id = '").append(rpId).append("' ");			
//		}
//		if(!StringUtils.isEmpty(memoTime)){
//			sql.append("and to_date('").append(memoTime).append("', 'yyyy-MM-dd HH24:mi:ss') >= start_time ");			
//		}
//		if(!StringUtils.isEmpty(memoTime)){
//			sql.append("and to_date('").append(memoTime).append("', 'yyyy-MM-dd HH24:mi:ss') <= end_time ");			
//		}
//		
//		return this.findBySQL(sql.toString());
//	}
	
	public List<?> getPrice(String branchId, String theme, String roomType, String rpId, String priceType, String memoTime){
		StringBuffer sql= new StringBuffer();
		sql.append("select *");
		sql.append("from (select t.*,row_number() over(partition by t.priority order by t.recordtime desc) rn from TB_P_PRICE_VOLATILITY t)");
		sql.append("where branch_id = '").append(branchId).append("' ");
		if(!StringUtils.isEmpty(theme)){
			sql.append("and theme = '").append(theme).append("' ");
		}
		if(!StringUtils.isEmpty(roomType)){
			sql.append("and room_type = '").append(roomType).append("' ");			
		}
		if(!StringUtils.isEmpty(rpId)){
			sql.append("and rp_id = '").append(rpId).append("' ");			
		}
		if(!StringUtils.isEmpty(priceType)){
			sql.append("and price_type = '").append(priceType).append("' ");			
		}
		if(!StringUtils.isEmpty(memoTime)){
			sql.append("and to_date('").append(memoTime).append("', 'yyyy-MM-dd HH24:mi:ss') >= start_time ");			
		}
		if(!StringUtils.isEmpty(memoTime)){
			sql.append("and to_date('").append(memoTime).append("', 'yyyy-MM-dd HH24:mi:ss') <= end_time ");			
		}
		
		return this.findBySQL(sql.toString());
	}
	
	public static void main(String args[]){
	}
	

}
