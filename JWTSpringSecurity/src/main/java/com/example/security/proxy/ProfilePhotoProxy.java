package com.example.security.proxy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfilePhotoProxy 
{

	private Integer id;
	private String fileName;
	private Long fileSize;
	private String fileId;
	private String contentType;
	
	private byte[] img;
}
