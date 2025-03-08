package com.example.security.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.security.config.JWTService;
import com.example.security.domain.Emp;
import com.example.security.domain.LoginRequest;
import com.example.security.domain.LoginResponse;
import com.example.security.proxy.EmpProxy;
import com.example.security.service.EmpService;

import io.jsonwebtoken.Claims;

@RestController
public class EmpController {

	@Autowired
	private EmpService serviceImpl;
	
	@Autowired
	private JWTService jwtService;
	
	@GetMapping("/")
	public String wlc()
	{
		return "wlc";
	}
	
	@GetMapping("/home")
	public String home()
	{
		return "home";
	}
	
	//save user
	@PostMapping("/saveUser")
	//@PreAuthorize(value = "hasRole('ROLE_ADMIN')")
	public String saveUser(@RequestBody EmpProxy emp)
	{
		serviceImpl.save(emp);
		return "saved";
	}
	
	@GetMapping("/generate")
	public String generateTocken(@RequestBody Emp emp)
	{
		return  serviceImpl.generateTocken(emp);
	}
	
	@GetMapping("/extractAll/{tocken}")
	public Claims extractAll(@PathVariable("tocken") String tocken)
	{
		return jwtService.excetrectAll(tocken);
	}

	@GetMapping("/isExpired/{tocken}")
	public boolean isExpired(@PathVariable("tocken") String tocken)
	{
		return jwtService.isExpired(tocken);
	}
	
	@GetMapping("/dateEx/{tocken}")
	public Date  extractExpirationTime(@PathVariable("tocken") String tocken)
	{
		return jwtService.extractExpirationTime(tocken);
	}
	
	@PostMapping("/loginReq")
	public LoginResponse login(@RequestBody LoginRequest loginRequest)
	{
		System.out.println(loginRequest.getPassword()+"\n"+loginRequest.getUserName());
		return serviceImpl.login(loginRequest);
	}
	
	@PostMapping("/uploadDynamicPath") //working
	public ResponseEntity<String> uploadDocumentToPath(@RequestParam("profileImage") MultipartFile file,@ModelAttribute("emp") EmpProxy employeeProxy) {
		return ResponseEntity.status(HttpStatus.OK).body(serviceImpl.uploadImgToDynamicPath(file,employeeProxy));
	}
	

	//download file from dynamic path
	
	@GetMapping("/getAllEmps")
	public ResponseEntity<List<EmpProxy>> getAllEmps()
	{
		return ResponseEntity.status(HttpStatus.OK).body(serviceImpl.getAllEmp());
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteById(@PathVariable("id") Integer id)
	{
		return ResponseEntity.status(HttpStatus.OK).body(serviceImpl.deleteById(id));
	}
	
	@GetMapping("/generateOTP")
	public String generateOTP()
	{
		return serviceImpl.generateOTP();
	}
	
	@GetMapping("/verify")
	public String verifyOtp(@RequestBody String otp)
	{
		return 	serviceImpl.verifyOTP(otp);
	}
	
	@GetMapping("/getHeader")
	public String  getHeader(@RequestHeader Map<String,String> header)
	{
		System.out.println("auth.."+header.get("Authorization"));
		return  header.get("Authorization");
		
	}
	
	@GetMapping("/validateTocken")
	public String validateTocken(@PathVariable("tocken") String tocken)
	{	
		return "";
	}
	
	@GetMapping("/validate/{token}")
	public boolean validateTockenUsername(@PathVariable("token") String tocken)
	{
		return jwtService.verifTockenInMemoryUserName(tocken);
	}
}
