package com.csme.assist.leave.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LeaveApproveException extends RuntimeException{
	
	private String jwtEmailId;
	private String resourceId;
	
	public LeaveApproveException( String jwtEmailId, String resourceId) {

	super("Login resource with email ID: "+jwtEmailId+ "is not the same resource email ID: "+resourceId);
	this.jwtEmailId = jwtEmailId;
	this.resourceId = resourceId;
	}

}
