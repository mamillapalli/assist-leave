package com.csme.assist.leave.mapper;

import org.mapstruct.Mapper;

import com.csme.assist.leave.entity.DeletedLeave;
import com.csme.assist.leave.entity.Leave;


@Mapper
public interface DeleteLeaveMapper {
	
	 Leave deletedLeaveToLeave(DeletedLeave deletedLeave);
	 
	    DeletedLeave leaveToDeletedLeave(Leave leave);
	    
	    


}
