package com.ideassoft.core.page;

import java.util.Map;


public interface IPageBuilder {
	
	public abstract void buildBegin(String modelId, String pageId) throws Exception;
	
	public abstract void buildConditions(String modelId, String pageId) throws Exception;

	public abstract void buildRequiredData(String modelId, String pageId, Map<String, Object> commonParams) throws Exception;
	
	public abstract Map<String, Object> buildData(String modelId, String pageId, 
			Map<String, Object> params, Map<String, Object> commonParams) throws Exception;

	public abstract void buildEnd(String modelId, String pageId) throws Exception;

	public abstract void construct(String modelId, String pageId, Map<String, Object> commonParams);
	
}
