package com.vow.service;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.vow.model.ResponseVO;

public class BaseService {

	public ResponseVO createServiceResponse(final Map<String, Object> resObjects) {
		ResponseVO responseVO = new ResponseVO();
		responseVO.setStatusFlag(ResponseVO.Ok);
		Set<String> keys = resObjects.keySet();
		for (Iterator iterator = keys.iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			Object obj = resObjects.get(key);
			responseVO.addObject(obj, key);
		}

		return responseVO;
	}

	public ResponseVO createServiceResponseError(final Map<String, Object> resObjects, String msg) {
		ResponseVO responseVO = new ResponseVO();
		responseVO.setStatusFlag(ResponseVO.Error);
		responseVO.addObject(msg, "ErrorMessage");
		Set<String> keys = resObjects.keySet();
		for (Iterator iterator = keys.iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			Object obj = resObjects.get(key);
			responseVO.addObject(obj, key);
		}

		return responseVO;
	}

}
