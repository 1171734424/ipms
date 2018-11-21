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
@Table(name = "TB_P_PICTURE", schema = "UCR_PMS")
public class Picture implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3167068499943852382L;
	private String pictureId;
	private String pictureUrl;
	private String pictureStyle;
	private String status;
	private Date recordTime;
	private String recordUser;
	private String remark;

	@Id
	@Column(name = "PIC_ID", unique = true, nullable = false, length = 12)
	public String getPictureId() {
		return pictureId;
	}

	public void setPictureId(String pictureId) {
		this.pictureId = pictureId;
	}

	@Column(name = "PIC_URL", nullable = false, length = 200)
	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	@Column(name = "PIC_STYLE", nullable = false, length = 2)
	public String getPictureStyle() {
		return pictureStyle;
	}

	public void setPictureStyle(String pictureStyle) {
		this.pictureStyle = pictureStyle;
	}

	@Column(name = "STATUS", nullable = false, length = 2)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	@Column(name = "REMARK", length = 200)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
