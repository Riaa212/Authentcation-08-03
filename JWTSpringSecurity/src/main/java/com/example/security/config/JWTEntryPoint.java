package com.example.security.config;

import java.io.IOException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTEntryPoint implements AuthenticationEntryPoint{

	//@Autowired
	//private  HandlerExceptionResolver resolver;
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
			
			//resolver.resolveException(request, response, null, authException);
			response.setContentType("application/json");
	        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	        response.getOutputStream().println("{ \"error\": \"" + authException.getMessage() + "\" }");
	}
}
