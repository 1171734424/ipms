package com.ideassoft.core.bean.pageConfig;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.Transient;

public class ModelConfig implements Serializable {
	
	@Transient
	private static final long serialVersionUID = 4907533799719322738L;

	/***模块ID***/
	private String modelId;
	
	/***模块名称***/
	private String modelName;
	
	/***是否展示***/
	private boolean show;
	
	/***模块图标***/
	private String icon;
	
	/***归属子系统***/
	private String subSystem;

	private Map<String, PageConfig> pageConfigs;

	public void setPageConfigs(Map<String, PageConfig> pageConfigs) {
		this.pageConfigs = pageConfigs;
	}

	public Map<String, PageConfig> getPageConfigs() {
		return pageConfigs;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	public String getModelId() {
		return modelId;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getModelName() {
		return modelName;
	}

	public void setShow(boolean show) {
		this.show = show;
	}

	public boolean isShow() {
		return show;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getIcon() {
		return icon;
	}

	public void setSubSystem(String subSystem) {
		this.subSystem = subSystem;
	}

	public String getSubSystem() {
		return subSystem;
	}
}
