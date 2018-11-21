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
@Table(name = "TB_C_PROBRAND", schema = "UCR_PMS")
public class ProBrand implements java.io.Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 3872992715416888208L;
	private String contentId;
	private String title;
	private String pictureId;
	private String content;
	private String types;
	private String commitType;
	private String status;
	private Date recordTime;
	private String recordUser;
	private String adminiCode;
	private String goodsId;
	private String houseId;
	private String subTitle;
	private String contentDesc;
	private String author;

	@Id
	@Column(name = "CONTENT_ID", unique = true, nullable = false, length = 12)
	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	@Column(name = "TITLE", nullable = false, length = 60)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "PICTURE_ID", nullable = true, length = 200)
	public String getPictureId() {
		return pictureId;
	}

	public void setPictureId(String pictureId) {
		this.pictureId = pictureId;
	}

	@Column(name = "CONTENT", nullable = false, length = 4000)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "TYPES", length = 12)
	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	@Column(name = "COMMIT_TYPE", length = 1)
	public String getCommitType() {
		return commitType;
	}

	public void setCommitType(String commitType) {
		this.commitType = commitType;
	}

	@Column(name = "STATUS", length = 2)
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

	@Column(name = "RECORD_USER", length = 8)
	public String getRecordUser() {
		return recordUser;
	}

	public void setRecordUser(String recordUser) {
		this.recordUser = recordUser;
	}

	@Column(name = "ADMINI_CODE", nullable = true, length = 12)
	public String getAdminiCode() {
		return adminiCode;
	}

	public void setAdminiCode(String adminiCode) {
		this.adminiCode = adminiCode;
	}
	
	@Column(name = "GOODS_ID", nullable = true, length = 8)
	public String getGoodsId() {
		return this.goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	
	@Column(name = "HOUSE_ID", nullable = true, length = 6)
	public String getHouseId() {
		return this.houseId;
	}

	public void setHouseId(String houseId) {
		this.houseId = houseId;
	}
	
	@Column(name = "SUB_TITLE", nullable = true, length = 60)
	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
	
	@Column(name = "CONTENT_DESC", nullable = true, length = 60)
	public String getContentDesc() {
		return contentDesc;
	}

	public void setContentDesc(String contentDesc) {
		this.contentDesc = contentDesc;
	}
	
	@Column(name = "AUTHOR", nullable = true, length = 40)
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	
}
