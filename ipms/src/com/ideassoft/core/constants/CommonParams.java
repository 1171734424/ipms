package com.ideassoft.core.constants;

import java.util.HashMap;
import java.util.Map;

public class CommonParams {

	public static Map<String, Map<String, Object>> commonParams = new HashMap<String, Map<String, Object>>();
	
	public static void setCommonParams(String sessionId, String key, Object value) {
		if (commonParams.get(sessionId) == null) {
			commonParams.put(sessionId, new HashMap<String, Object>());
		}
		commonParams.get(sessionId).put(key, value);
	}

	public static Map<String, Object> getCommonParams(String sessionId) {
		return commonParams.get(sessionId);
	}
	
	public static void removeParam(String sessionId) {
		if (commonParams.get(sessionId) != null) {
			commonParams.remove(sessionId);
		}
	}

}
