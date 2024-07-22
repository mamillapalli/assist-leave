package com.finstack.assist.leave.mapper;

import org.mapstruct.Mapper;

import com.finstack.assist.leave.entity.DeletedLeave;
import com.finstack.assist.leave.entity.Leave;


@Mapper
public interface DeleteLeaveMapper {
	
	 Leave deletedLeaveToLeave(DeletedLeave deletedLeave);
	 
	    DeletedLeave leaveToDeletedLeave(Leave leave);
	    
	    


}
