package com.ideassoft.bean;

import java.io.Serializable;


public class RightDefine implements Serializable {

	private static final long serialVersionUID = -1019626881916117940L;
	
	private String rightModel;
	
    private String url;
    
    private String rightType;

	public String getRightModel() {
		return rightModel;
	}

	public void setRightModel(String rightModel) {
		this.rightModel = rightModel;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRightType() {
		return rightType;
	}

	public void setRightType(String rightType) {
		this.rightType = rightType;
	}
  
}
