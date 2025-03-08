package com.example.security.domain;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

	private String userName;
	private String tocken;
	private List<SimpleGrantedAuthority> role;
	
}
