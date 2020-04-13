package com.vow.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.UnexpectedTypeException;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vow.dao.RoleRepository;
import com.vow.dao.UserRepository;
import com.vow.model.JwtResponse;
import com.vow.model.LoginForm;
import com.vow.model.ResponseVO;
import com.vow.model.SignUpForm;
import com.vow.model.User;
import com.vow.security.JwtProvider;
import com.vow.service.BaseService;
import com.vow.util.CommonUtil;
import com.vow.util.TwilioSMS;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class LoginController extends BaseService{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtProvider jwtProvider;

	@PostMapping("/signin")
	public ResponseEntity<?> login(@Valid @RequestBody LoginForm loginRequest) {
		logger.debug("LoginController login starts");
		ResponseVO responseVO = null;
		Map<String,Object> responseObjectMap = new HashMap<String,Object>();
		ResponseEntity responseEntity;
		try {
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					String.valueOf(loginRequest.getMobileno()), String.valueOf(loginRequest.getOtp())));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwt = jwtProvider.generateJwtToken(authentication);
			responseObjectMap.put("Token", jwt);
			responseVO = createServiceResponse(responseObjectMap);
			responseEntity = new ResponseEntity(responseVO, HttpStatus.OK);
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			responseVO = createServiceResponseError(responseObjectMap, e.getMessage());
			responseEntity = new ResponseEntity(responseVO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		logger.debug("LoginController login ends");
		return responseEntity;
	}

	@GetMapping("/validateOtp")
	public ResponseEntity<?> validateOtp(@Valid @RequestParam long mobileno) {
		logger.debug("LoginController validateOtp starts");
		ResponseVO responseVO = null;
		Map<String,Object> responseObjectMap = new HashMap<String,Object>();
		ResponseEntity responseEntity;
		String otp;
		try {
			if (userRepository.existsByMobileno(mobileno)) {
				otp = CommonUtil.generateOtp(6);
				User user = userRepository.findByMobileno(mobileno);
				user.setPassword(encoder.encode(otp));
				userRepository.save(user);
				TwilioSMS.SmsSend(String.valueOf(mobileno), otp);
				responseObjectMap.put("msg", "OTP Send Succesfully");
				responseVO = createServiceResponse(responseObjectMap);
				responseEntity = new ResponseEntity(responseVO, HttpStatus.OK);
			} else {
				responseVO = createServiceResponseError(responseObjectMap, "Mobile Number Not Registered");
				responseEntity = new ResponseEntity(responseVO, HttpStatus.BAD_REQUEST);				
			}
		}catch (Throwable e) {
			logger.error(e.getMessage(), e);
			responseVO = createServiceResponseError(responseObjectMap, e.getMessage());
			responseEntity = new ResponseEntity(responseVO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		logger.debug("LoginController validateOtp ends");
		return responseEntity;
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpForm signUpRequest) {
		logger.debug("LoginController registerUser starts");
		ResponseVO responseVO = null;
		Map<String,Object> responseObjectMap = new HashMap<String,Object>();
		ResponseEntity responseEntity;
		try {
			if (userRepository.existsByMobileno(signUpRequest.getMobileno())) {
				responseVO = createServiceResponseError(responseObjectMap, "MobileNo is already in use!");
				responseEntity = new ResponseEntity(responseVO, HttpStatus.BAD_REQUEST);	
				return responseEntity;
			}

			String otp = CommonUtil.generateOtp(6);
			String username = String.valueOf(signUpRequest.getMobileno());

			// Creating user's account
			User user = new User(signUpRequest.getFirstName(), signUpRequest.getLastName(), signUpRequest.getEmailId(),
					username, signUpRequest.getMobileno(), encoder.encode(otp));

			// Set<String> strRoles = signUpRequest.getRole();
			// Set<Role> roles = new HashSet<>();

			/*
			 * strRoles.forEach(role -> { switch (role) { case "admin": Role adminRole =
			 * roleRepository.findByName(RoleName.ROLE_ADMIN) .orElseThrow(() -> new
			 * RuntimeException("Fail! -> Cause: User Role not find."));
			 * roles.add(adminRole); break; default: Role userRole =
			 * roleRepository.findByName(RoleName.ROLE_USER) .orElseThrow(() -> new
			 * RuntimeException("Fail! -> Cause: User Role not find."));
			 * roles.add(userRole); } });
			 */

			// user.setRoles(roles);
			user = userRepository.save(user);
			if (user != null) {
				TwilioSMS.SmsSend(user.getUsername(), otp);
			}
			responseObjectMap.put("User",user);
			responseVO = createServiceResponse(responseObjectMap);
			responseEntity = new ResponseEntity(responseVO, HttpStatus.OK);
		}	
		catch (Throwable e) {
			logger.error(e.getMessage(), e);
			responseVO = createServiceResponseError(responseObjectMap, e.getMessage());
		responseEntity = new ResponseEntity(responseVO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		logger.debug("LoginController registerUser starts");
		return responseEntity;
	}
}