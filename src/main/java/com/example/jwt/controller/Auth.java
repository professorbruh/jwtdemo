package com.example.jwt.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.jwt.models.AdminDetails;
import com.example.jwt.response.AdminDetailsResponse;
import com.example.jwt.response.CustomResponseForLogin;
import com.example.jwt.response.CustomResponseForNoUser;
import com.example.jwt.security.AdminDetailsImpl;
import com.example.jwt.security.JwtUtils;
import com.example.jwt.service.AuthService;

@RestController
public class Auth {
	
	@Autowired
	AuthService authSerivce;
	
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtUtils jwtUtils;
	
	@GetMapping("/hello")
	public String hello() {
		return "Hello";
	}
	
	
	@PostMapping("/createAdmin")
	public ResponseEntity<Object> createAdmin(@RequestBody AdminDetails adminDetails){
		
		AdminDetails fetchAdmin = authSerivce.fetchAdminByEmail(adminDetails.getEmailId());
		
		if(fetchAdmin == null) {
			AdminDetails admin = authSerivce.createAdmin(adminDetails);
			
			AdminDetailsResponse response = new AdminDetailsResponse(new Date(),"Admin Created Succesfully","200",admin);
			
			return new ResponseEntity<Object>(response,HttpStatus.OK);
		}else {
			
			CustomResponseForNoUser response = new CustomResponseForNoUser(new Date(),"Email id already exists","409");
			return new ResponseEntity<Object>(response,HttpStatus.OK);
			
		}	
		
	}
	
	
	
	@PostMapping("/adminLogin")
	public ResponseEntity<Object> adminLogin(@RequestBody AdminDetails adminDetails){
		
		AdminDetails fetchAdmin = authSerivce.fetchAdminByEmail(adminDetails.getEmailId());
		
		if(fetchAdmin != null) {
			if(encoder.matches(adminDetails.getPassword(),fetchAdmin.getPassword()) == true) {
				Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(adminDetails.getEmailId(), adminDetails.getPassword()));
				SecurityContextHolder.getContext().setAuthentication(authentication);
				
				String jwt = jwtUtils.generateJwtToken(authentication);
				
				AdminDetailsImpl details = (AdminDetailsImpl) authentication.getPrincipal();
				if(details != null) {
					CustomResponseForLogin response = new CustomResponseForLogin(new Date(),"Login Successfull","200",jwt,fetchAdmin.getFirstName(),fetchAdmin.getLastName(),fetchAdmin.getEmailId());
					return new ResponseEntity<Object>(response,HttpStatus.OK);
					
				}else {
					CustomResponseForNoUser response = new CustomResponseForNoUser(new Date(),"Erro in authenticaion","409");
					return new ResponseEntity<Object>(response,HttpStatus.OK);
					
				}

			}else {
				
				CustomResponseForNoUser response = new CustomResponseForNoUser(new Date(),"Invalid Credentials","409");
				return new ResponseEntity<Object>(response,HttpStatus.OK);
				
			}
			
		}else {
			CustomResponseForNoUser response = new CustomResponseForNoUser(new Date(),"Admin Not Found","409");
			return new ResponseEntity<Object>(response,HttpStatus.OK);
			
		}
		
	}


}
