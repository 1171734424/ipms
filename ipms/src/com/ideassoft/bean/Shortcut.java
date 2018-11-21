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
@Table(name = "TP_C_SHORTCUT_COMMENT", schema = "UCR_PMS")
public class Shortcut implements java.io.Serializable {


	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1289612738994230038L;
	private String shortcutId;
	private String shortcutName;
	private String remark;
	private String status;
	private Date recordTime;
	
	public Shortcut() {
		
	}

	public Shortcut(String shortcutId, String shortcutName, String remark,
			String status, Date recordTime) {
		super();
		this.shortcutId = shortcutId;
		this.shortcutName = shortcutName;
		this.remark = remark;
		this.status = status;
		this.recordTime = recordTime;
	}

	@Id
	@Column(name = "SHORTCUT_ID", unique = true, nullable = false, length = 6)
	public String getShortcutId() {
		return shortcutId;
	}

	public void setShortcutId(String shortcutId) {
		this.shortcutId = shortcutId;
	}

	@Column(name = "SHORTCUT_NAME", nullable = false, length = 30)
	public String getShortcutName() {
		return shortcutName;
	}

	public void setShortcutName(String shortcutName) {
		this.shortcutName = shortcutName;
	}

	@Column(name = "STATUS", length = 2)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name = "REMARK", length = 50)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

}
