package com.example.apigateway.jwtFilter;

import java.util.List;
import java.util.function.Predicate;

import org.bouncycastle.asn1.ocsp.Request;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class RouteValidator {

	RouteValidator()
	{
		System.out.println("is secured"+openApiEndPoints);
	}
	
	public static final List<String> openApiEndPoints=List.of(
			"/jwt/home",
			"/jwt/loginReq",
			"/eureka"
			);

	
	public Predicate<ServerHttpRequest> isSecured=
			requests ->{ return openApiEndPoints.
			stream().noneMatch(uri->requests.getURI().getPath().contains(uri));
					};
					//System.out.println(isSecured);
}
