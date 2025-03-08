package com.example.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.security.domain.Emp;
import com.example.security.repository.EmpRepo;

@Component(value = "bean from custom user service")
public class CustomUserService implements UserDetailsService
{
	@Autowired
	private EmpRepo empRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Emp findByUserName = empRepo.findByUserName(username);
		
		if(findByUserName==null)
		{
		throw new UsernameNotFoundException("User not found..");	
		}
		
		return new CustomUser(findByUserName);
	}
}
