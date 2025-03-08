package com.example.apigateway.global.exception;

import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.jsonwebtoken.ExpiredJwtException;

@RestControllerAdvice
public class GlobalException {


	@org.springframework.web.bind.annotation.ExceptionHandler(value = ExpiredJwtException.class)
	public ErrorResponse expiredjwtTocken(ExpiredJwtException expiredJwtException)
	{
		return new ErrorResponse("jwt tocken expired..","404");
	}
}
