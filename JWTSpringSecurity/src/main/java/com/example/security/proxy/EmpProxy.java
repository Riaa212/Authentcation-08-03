package com.example.security.proxy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpProxy {

	private Integer id;
	private String userName;
	private String password;
	private String email;
	private String city;
	private String role;
	private Boolean isActive;
	
	private ProfilePhotoProxy profilePhoto;
}
