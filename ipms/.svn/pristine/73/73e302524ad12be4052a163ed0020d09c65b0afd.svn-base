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
@Table(name = "TB_P_PROMOTION", schema = "UCR_PMS")
public class Promotion implements java.io.Serializable {

	private static final long serialVersionUID = 7424797237856292041L;

	private String promotionId;
	private Date  startTime;
	private Date  endTime;
	private String contentDesc;
	private Date recordTime;
	private String recordUser;
	private String status;
	private String remark;
	
	public Promotion() {

	}


	public Promotion(String promotionId, Date startTime, Date endTime,
			String contentDesc, Date recordTime, String recordUser,
			String status, String remark) {
		super();
		this.promotionId = promotionId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.contentDesc = contentDesc;
		this.recordTime = recordTime;
		this.recordUser = recordUser;
		this.status = status;
		this.remark = remark;
	}



	@Id
	@Column(name = "PROMOTION_ID", unique = true, nullable = false, length = 12)
	public String getPromotionId() {
		return promotionId;
	}


	public void setPromotionId(String promotionId) {
		this.promotionId = promotionId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "START_TIME", nullable = false, length = 7)
	public Date getStartTime() {
		return startTime;
	}


	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "END_TIME", nullable = false, length = 7)
	public Date getEndTime() {
		return endTime;
	}
	
	
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	
	@Column(name = "CONTENT_DESC", nullable = false, length = 200)
	public String getContentDesc() {
		return contentDesc;
	}
	
	
	public void setContentDesc(String contentDesc) {
		this.contentDesc = contentDesc;
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
	
	
	@Column(name = "RECORD_USER", nullable = false, length = 8)
	public String getRecordUser() {
		return recordUser;
	}
	
	public void setRecordUser(String recordUser) {
		this.recordUser = recordUser;
	}


	@Column(name = "STATUS", nullable = false, length = 2)
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
	
	
}
