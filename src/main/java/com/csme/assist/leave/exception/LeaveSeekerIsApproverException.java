package com.csme.assist.leave.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LeaveSeekerIsApproverException extends RuntimeException{
	private String apporverName;
	private String seekerName;
	
	public LeaveSeekerIsApproverException(String seekerName , String apporverName) {
		super("Leave seeker " + seekerName + " should not be the approver " + apporverName );
		this.apporverName = apporverName;
		this.seekerName = seekerName;
	}

}
