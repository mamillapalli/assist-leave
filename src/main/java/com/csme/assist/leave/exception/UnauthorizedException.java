package com.csme.assist.leave.exception;

public class UnauthorizedException extends RuntimeException{
	
	public UnauthorizedException(){
		super();
	}
	
	public UnauthorizedException(String message){
		super(message);
	}
}
