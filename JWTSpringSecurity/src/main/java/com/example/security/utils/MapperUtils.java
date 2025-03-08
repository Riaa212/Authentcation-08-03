package com.example.security.utils;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.security.domain.Emp;
import com.example.security.proxy.EmpProxy;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class MapperUtils {

	@Autowired
	private ObjectMapper mapper;
	
	
	public Emp convertEmpProxyToEntity(EmpProxy empProxy)
	{
		return mapper.convertValue(empProxy, Emp.class);
	}
	
	//convert list of employee entity to proxy
	public List<EmpProxy> convertListOfEmpToproxy(List<Emp> emps)
	{
		return emps.stream().map(a->mapper.convertValue(a,EmpProxy.class)).toList();
	}
	
	//this method is going to generate unique file name
	public static String getUniqueFileName(String OriginalName)
	{
		String uuid=UUID.randomUUID()+OriginalName.substring(OriginalName.lastIndexOf("."));
		 return uuid;
	}
	
}
