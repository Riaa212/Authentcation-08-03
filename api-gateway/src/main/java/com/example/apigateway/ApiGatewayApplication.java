package com.example.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {

	/**
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder,GatewayFilter[] filter)
	{
		return builder.routes().route("",r->r.path("/myRoute/**")
				.filters(f->f.filters(filter))
				.uri(""))
				.build();
	} **/

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

}
