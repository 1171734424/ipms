package com.ideassoft.apartment.service;

import org.springframework.stereotype.Service;

import com.ideassoft.bean.Aptorder;
import com.ideassoft.bean.AptorderDetail;
import com.ideassoft.core.dao.GenericDAOImpl;

@Service
public class ApartmentRentService extends GenericDAOImpl {
	
	public void AptOrderCheckIn(String contrartId){
		AptorderDetail aptorderDetail = (AptorderDetail) this.findOneByProperties(AptorderDetail.class, "contrartId", contrartId);
		if(null != aptorderDetail){
			Aptorder aptorder = (Aptorder) this.findOneByProperties(Aptorder.class, "orderId", aptorderDetail.getAptorderId());
			aptorder.setStatus("3");
			this.update(aptorder);
		}
	}
	
	public void AptOrderCheckOut(String contrartId){
		AptorderDetail aptorderDetail = (AptorderDetail) this.findOneByProperties(AptorderDetail.class, "contrartId", contrartId);
		if(aptorderDetail != null){
			Aptorder aptorder = (Aptorder) this.findOneByProperties(Aptorder.class, "orderId", aptorderDetail.getAptorderId());
			aptorder.setStatus("4");
			this.update(aptorder);
		}
	}

}
