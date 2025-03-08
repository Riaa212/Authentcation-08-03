package com.example.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.security.domain.Emp;

public interface EmpRepo extends JpaRepository<Emp, Integer>
{
	Emp	findByUserName(String name);
	
	Optional<Emp> findByOtp(String otp);
}
