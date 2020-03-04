package com.vow.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestRestAPIs {
	
	public class HomeController {
		@RequestMapping(value = "/")
		public String index() {
			System.out.println("swagger-ui.html");
			return "redirect:swagger-ui.html";
		}
	}
	
	@GetMapping("/api/test/user")
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	public String userAccess() {
		return ">>> User Contents!";
	}
	
	
	@GetMapping("/api/test/admin")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String adminAccess() {
		return ">>> Admin Contents";
	}
}