package com.ideassoft.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ideassoft.core.annotation.interfaces.ColumnDefaultValue;

@Entity
@Table(name = "TB_P_CITY", schema = "UCR_PMS")
public class City implements java.io.Serializable {

	private static final long serialVersionUID = 7424797237856292041L;

	private String adminiCode;
	private String adminiName;
	private String rank;
	private String recordUser;
	private Date recordTime;
	private String status;
	private String remark;
	private String orderNumber;
	private String cityPicture;
	private String recommendNumber;
	private String recommendNum;
	private String pictures;
	
	public City() {

	}

	public City(String adminiCode, String adminiName, String rank, String recordUser, Date recordTime, String status,
			String remark,String cityPicture,String recommendNumber,String orderNumber, String pictures) {
		super();
		this.adminiCode = adminiCode;
		this.adminiName = adminiName;
		this.rank = rank;
		this.recordUser = recordUser;
		this.recordTime = recordTime;
		this.status = status;
		this.remark = remark;
		this.cityPicture = cityPicture;
		this.recommendNumber = recommendNumber;
		this.orderNumber = orderNumber;
		this.pictures = pictures;
	}

	@Id
	@Column(name = "ADMINI_CODE", unique = true, nullable = false, length = 12)
	public String getAdminiCode() {
		return adminiCode;
	}

	public void setAdminiCode(String adminiCode) {
		this.adminiCode = adminiCode;
	}

	@Column(name = "ADMINI_NAME", nullable = false, length = 200)
	public String getAdminiName() {
		return adminiName;
	}

	public void setAdminiName(String adminiName) {
		this.adminiName = adminiName;
	}

	@Column(name = "RANK", nullable = false, length = 1)
	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	@Column(name = "RECORD_USER", nullable = false, length = 8)
	public String getRecordUser() {
		return recordUser;
	}

	public void setRecordUser(String recordUser) {
		this.recordUser = recordUser;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RECORD_TIME", insertable = false, updatable = true)
	@ColumnDefaultValue(value = "sysdate", insertable = false)
	public Date getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	@Column(name = "STATUS", nullable = false, length = 1)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "REMARK", nullable = true, length = 200)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(name = "CITY_PICTURE", nullable = true, length = 100)
	public String getCityPicture() {
		return cityPicture;
	}

	public void setCityPicture(String cityPicture) {
		this.cityPicture = cityPicture;
	}

	
	@Column(name = "RECOMMEND_NUMBER", nullable = true, length = 4)
	public String getRecommendNumber() {
		return recommendNumber;
	}

	public void setRecommendNumber(String recommendNumber) {
		this.recommendNumber = recommendNumber;
	}
	
	@Column(name = "ORDER_NUMBER", nullable = true, length = 4)
	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	
	@Column(name = "RECOMMEND_NUM", nullable = true, length = 8)
	public String getRecommendNum() {
		return recommendNum;
	}

	public void setRecommendNum(String recommendNum) {
		this.recommendNum = recommendNum;
	}

	@Column(name = "PICTURES", length = 1000)
	public String getPictures() {
		return pictures;
	}

	public void setPictures(String pictures) {
		this.pictures = pictures;
	}
	
	
}
