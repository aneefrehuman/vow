package com.vow.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vow.model.ResponseVO;
import com.vow.service.BaseService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class DashboardController extends BaseService{
	
	@GetMapping("/api/dashboard")	
	  public  ResponseEntity<?> getDashboard() {
		ResponseVO responseVO = null;
		Map<String,Object> responseObjectMap = new HashMap<String,Object>();
		ResponseEntity responseEntity;
		responseObjectMap.put("msg", "Redirect to dashboard page");
		responseVO = createServiceResponse(responseObjectMap);
		responseEntity = new ResponseEntity(responseVO, HttpStatus.OK);
        return responseEntity;
    }

}
