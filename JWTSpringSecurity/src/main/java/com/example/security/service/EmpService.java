package com.example.security.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.security.domain.Emp;
import com.example.security.domain.LoginRequest;
import com.example.security.domain.LoginResponse;
import com.example.security.proxy.EmpProxy;

public interface EmpService 
{
	//save user
	public String save(EmpProxy emp);
	
	//generate jwt tocken
	public String generateTocken(Emp emp);
	
	//login
	public LoginResponse login(LoginRequest loginRequest);
	
	//get all employee
	public List<EmpProxy> getAllEmp();
	
	//upload with emp and image
	public String uploadImgToDynamicPath(MultipartFile file,EmpProxy empProxy);
	
	public String deleteById(Integer id);

	public String generateOTP();

	public String verifyOTP(String otp);
}
