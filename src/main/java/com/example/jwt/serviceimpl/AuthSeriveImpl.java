package com.example.jwt.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.jwt.models.AdminDetails;
import com.example.jwt.repository.AuthRepository;
import com.example.jwt.service.AuthService;


@Service
public class AuthSeriveImpl implements AuthService{
	
	@Autowired
	AuthRepository authRepository;
	
	@Autowired
	PasswordEncoder encoder;

	@Override
	public AdminDetails createAdmin(AdminDetails adminDetails) {
		// TODO Auto-generated method stub
		
		adminDetails.setPassword(encoder.encode(adminDetails.getPassword()));
		
		return authRepository.save(adminDetails);
	}

	@Override
	public AdminDetails fetchAdminByEmail(String emailId) {
		// TODO Auto-generated method stub
		return authRepository.fetchAdminByEmail(emailId);
	}

	
	

}
