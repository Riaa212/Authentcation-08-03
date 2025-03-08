package com.example.apigateway.config;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

//@Configuration
public class JwtConfig {
	
	/**
	@Bean
	public GlobalFilter customFilter()
	{
		return new GlobalFilter() {
			
			@Override
			public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
				
				String  apiKey= exchange.getRequest().getHeaders().getFirst("api-key");
	
				if (apiKey == null || !apiKey.equals(apiKey)) {
					exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
					return exchange.getResponse().setComplete();		
				}
				System.err.println("Filter");
				return chain.filter(exchange);
			}
		};
	}**/
}
