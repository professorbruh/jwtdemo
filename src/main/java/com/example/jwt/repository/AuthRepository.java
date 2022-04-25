package com.example.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.jwt.models.AdminDetails;

@Repository
public interface AuthRepository extends JpaRepository<AdminDetails, Long>{

	
	@Query("SELECT a FROM AdminDetails a WHERE a.emailId=?1")
	AdminDetails fetchAdminByEmail(String emailId);

}
