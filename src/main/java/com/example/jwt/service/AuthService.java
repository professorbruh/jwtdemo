package com.example.jwt.service;

import com.example.jwt.models.AdminDetails;

public interface AuthService {

	AdminDetails createAdmin(AdminDetails adminDetails);

	AdminDetails fetchAdminByEmail(String emailId);

}
