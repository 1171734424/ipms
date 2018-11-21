package com.ideassoft.bean.imports;

import java.io.Serializable;

import com.ideassoft.core.bean.pageConfig.ColumnConfig;

public class ImportConfig implements Serializable {

	private static final long serialVersionUID = 8770636148137245143L;

	private Integer titleNo;

	private String titleName;

	private ColumnConfig columnConfig;

	private String sheetName;

	public ImportConfig() {

	}

	public ImportConfig(Integer titleNo, String titleName, ColumnConfig columnConfig) {
		this.titleNo = titleNo;
		this.titleName = titleName;
		this.columnConfig = columnConfig;
	}

	public ImportConfig(Integer titleNo, String titleName, ColumnConfig columnConfig, String sheetName) {
		this.titleNo = titleNo;
		this.titleName = titleName;
		this.sheetName = sheetName;
		this.columnConfig = columnConfig;
	}

	public Integer getTitleNo() {
		return titleNo;
	}

	public String getTitleName() {
		return titleName;
	}

	public void setTitleNo(Integer titleNo) {
		this.titleNo = titleNo;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setColumnConfig(ColumnConfig columnConfig) {
		this.columnConfig = columnConfig;
	}

	public ColumnConfig getColumnConfig() {
		return columnConfig;
	}
}
