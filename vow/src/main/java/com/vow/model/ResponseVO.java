package com.vow.model;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonValue;

public class ResponseVO {

	private static final long serialVersionUID = 1L;
	
	
	public static final String Ok = "Ok";

	public static final String Error = "Error";

	private String statusFlag;
	
	
	private Map<String, Object> paramObjectsMap = new HashMap<String, Object>();
	
	@JsonAnyGetter
	public final Map<String, Object> getParamObjectsMap() {
		return paramObjectsMap;
	}

	public final void setParamObjectsMap(final Map<String, Object> paramObjectsMap) {
		this.paramObjectsMap = paramObjectsMap;
	}

	public final void addObject(final Object obj, final String key) {
		paramObjectsMap.put(key, obj);
	}

	public final Object getObject(final String key) {
		return this.paramObjectsMap.get(key);
	}

	public void addAllObject(Map<String, Object> mapObj) {
		this.paramObjectsMap.putAll(mapObj);
	}

	public final void clearParamMap(final String key) {
		paramObjectsMap.remove(key);
	}

	public String getStatusFlag() {
		return statusFlag;
	}

	public void setStatusFlag(String statusFlag) {
		this.statusFlag = statusFlag;
	}

}
