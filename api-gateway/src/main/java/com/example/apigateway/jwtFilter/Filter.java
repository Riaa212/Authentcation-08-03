package com.example.apigateway.jwtFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.apigateway.jwtUtils.Utils;

import jakarta.ws.rs.core.HttpHeaders;

@Component
public class Filter extends AbstractGatewayFilterFactory<Filter.Config>
{
	
	@Autowired
	private RouteValidator routeValidator;
	
	@Autowired
	private RestTemplate template;
	
	@Autowired
	private Utils jwtutils;
	
	public Filter()
	{
		super(Config.class);
	}
	
	public static class Config{
	
	}
	
	@Override
	public GatewayFilter apply(Config config) {
		return (exchange,chain)->{
			if(routeValidator.isSecured.test(exchange.getRequest()))
			{
				if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION))
				{
					throw new RuntimeException("authorization header missing..");
				}
				String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
				//System.out.println("auth hedear from api gateway.."+authHeader);
				//System.out.println("auth header..."+authHeader);
				if(authHeader!=null && authHeader.startsWith("Bearer ")) //header prefix Bearer
				{
					authHeader = authHeader.substring(7);//remove bearer
					//System.out.println("auth header..."+authHeader);
					jwtutils.verifTockenInMemoryUserName(authHeader);
					System.out.println("Verified.."+jwtutils.verifTockenInMemoryUserName(authHeader));
				}
//				try {
//					//System.out.println("auth header..."+authHeader);
//					//jwtutils.genearteTocken(authHeader);
//					//System.out.println( "auth header..."+authHeader);
//					jwtutils.verifTockenInMemoryUserName(authHeader);
//					System.out.println("try block executed..");
//					//template.postForEntity(authHeader, "http://JWTSPRINGSECURITY//generate", String.class);
//				}
//				catch(Exception e)
//				{
//					System.out.println(""+e.getMessage());
//				}
			}
			return chain.filter(exchange);
		};
	} 
}
