package com.finstack.assist.leave.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.finstack.assist.leave.entity.DeletedLeave;
import com.finstack.assist.leave.entity.Leave;
import com.finstack.assist.leave.entity.Resource;
import com.finstack.assist.leave.entity.Role;
import com.finstack.assist.leave.entity.StatusEnum;
import com.finstack.assist.leave.entity.TransactionStatusEnum;
import com.finstack.assist.leave.exception.LeaveApproveException;
import com.finstack.assist.leave.exception.LeaveSeekerIsApproverException;
import com.finstack.assist.leave.exception.UnauthorizedException;
import com.finstack.assist.leave.jwtauthentication.configuration.service.JWTUtil;
import com.finstack.assist.leave.mapper.DeleteLeaveMapper;
import com.finstack.assist.leave.mapper.LeaveMapper;
import com.finstack.assist.leave.model.HolidayDTO;
import com.finstack.assist.leave.model.LeaveDTO;
import com.finstack.assist.leave.model.LeaveSummuryDTO;
import com.finstack.assist.leave.repository.DeletedLeavesRepository;
import com.finstack.assist.leave.repository.HolidayRepository;
import com.finstack.assist.leave.repository.LeaveRepository;
import com.finstack.assist.leave.repository.ResourceRepository;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class LeaveServiceImpl implements LeaveService {

    @Autowired
    ResourceRepository resourceRepository;

    @Autowired
    HolidayService holidayService;
    @Autowired
    LeaveRepository leaveRepository;

    @Autowired
    DeletedLeavesRepository deletedLeaveRepository;

    @Autowired
    LeaveMapper leaveMapper;

    @Autowired
    DeleteLeaveMapper deleteLeaveMapper;

    @Autowired
    JWTUtil jwtUtil;
    
    @Autowired
    ResourceService resourceService;

    @Value("${leave.weekends}")
    String weekends;
    @Value("${leave.addHolidaysToLeave}")
    boolean addHolidaysToLeave;
    @Value("${leave.max-no-of-holidays}")
    int maxNoOfHolidays;

	/*
	 * @Override public List<LeaveDTO> getAll() { return
	 * leaveMapper.leaveToLeaveDTOs(leaveRepository.findAll()); }
	 */

    @Override
    public LeaveDTO getLeaves(int id) {
        Leave leave = leaveRepository.getById(id);
        if (leave == null) throw new ResourceNotFoundException("Leave with id " + id + " does not exist");
        return leaveMapper.leaveToLeaveDTO(leave);
    }

    @Override
    public List<LeaveDTO> getLeavesByTransactionStatus(TransactionStatusEnum transactionStatus) {
        String emailAddress = jwtUtil.extractUsernameFromRequest();
        List<Leave> leave = null;
        if (emailAddress != null) {
            leave = leaveRepository.findByTransactionStatusAndResourceIdNot(transactionStatus, emailAddress);
        } else {
            leave = leaveRepository.findByTransactionStatus(transactionStatus);
        }
        if (leave.size() == 0) throw new ResourceNotFoundException("There are no pending leave request currently in the system");
        return leaveMapper.leaveToLeaveDTOs(leave);
    }
    
    @Override
    public List<LeaveDTO> getLeavesByResourceId(String id) {
        List<Leave> leave = null;
        System.out.println("roles is :" + jwtUtil.extractRolesFromRequest());
        String rolesValue = jwtUtil.extractRolesFromRequest();

        leave = leaveRepository.findByResourceIdAndApproverIdOrStatus(id, id, StatusEnum.WAITING);
        if (leave.size() == 0)
            throw new ResourceNotFoundException("Leave with resource " + id + " does not exist");
        return leaveMapper.leaveToLeaveDTOs(leave);

        //return leaveMapper.leaveToLeaveDTOs(leaveRepository.findByResourceId(id).orElseThrow(() -> new ResourceNotFoundException("Resource with id " + id + " not found")));
    }
    

    @Override
    public List<LeaveDTO> getLeavesByResourceIdAndTransactionStatus(String id, TransactionStatusEnum transactionStatus) {
        List<Leave> leave = leaveRepository.findByResourceIdAndTransactionStatus(id, transactionStatus);
        if (leave.size() == 0) throw new ResourceNotFoundException("There are no pending leave request currently in the system");
        return leaveMapper.leaveToLeaveDTOs(leave);
    }

    @Override
    public List<LeaveDTO> getLeavesByResourceIdAndStatus(String id, StatusEnum status) {
        List<Leave> leave = leaveRepository.findByResourceIdAndStatus(id, status);
        if (leave.size() == 0) throw new ResourceNotFoundException("There are no pending leave request currently in the system");
        return leaveMapper.leaveToLeaveDTOs(leave);
    }


    @Override
    public List<LeaveDTO> getLeavesByApproverId(String id) {
        return leaveMapper.leaveToLeaveDTOs(leaveRepository.findByApproverId(id).orElseThrow(() -> new ResourceNotFoundException("Approver with id " + id + " not found")));
    }

    @Override
    public List<LeaveDTO> getLeavesByApproverIdAndStatus(String id, StatusEnum status) {
        System.out.println("id is :" + id);
        System.out.println("status is :" + status);
        return leaveMapper.leaveToLeaveDTOs(leaveRepository.findByApproverIdAndStatus(id, status).orElseThrow(() -> new ResourceNotFoundException("Approver with id " + id + " not found")));
    }

    @Override
    public LeaveDTO addLeave(LeaveDTO leaveDTO) {
    	String resourceEmail = jwtUtil.extractUsernameFromRequest();
    	System.out.println("resourceEmail--"+resourceEmail);
    	if(!resourceEmail.equals(leaveDTO.getResourceId())) {
    		throw new UnauthorizedException("Resource is not Authorized to make the request");
    	}
    	//dto doesn't have resourceId, get it from resourceEmail
    	if(leaveDTO.getColleagueEmail().equals(leaveDTO.getResourceId())){
    		throw new UnauthorizedException("Leave appliar should not be a Colleague");
    	}
    	if(leaveDTO.getPayPercentage()>100) {
    		throw new UnauthorizedException("Paypercentage should not be greater than 100");
    	}
    	//check approverid has the approver_role
    	Resource approverResource = resourceService.findByEmail(leaveDTO.getApproverId());
    	List<Role> approverRoles = approverResource.getRoles();
    	System.out.println("!!!!! approverRoles - "+approverRoles);
    	List<String> rolesList=new ArrayList<String>();
    	approverRoles.forEach(role->{
    		rolesList.add(role.getName());
    	});
    	System.out.println("rolesList -"+rolesList);
    	//validate for leave_admin too?
    	if(rolesList == null || !rolesList.contains("LEAVE_APPROVER")) {
    		throw new UnauthorizedException("Approver does not have LEAVE_APPROVER role");
    	}
    	
    	
    	if(leaveDTO.getResourceId().equals(leaveDTO.getApproverId()))
    		throw new LeaveSeekerIsApproverException(leaveDTO.getLeaveSeekerName(), leaveDTO.getApproverName());
    	
    	Resource colleague = resourceService.findByEmail(leaveDTO.getColleagueEmail());
    	if(colleague == null) {
    		throw new ResourceNotFoundException("Colleague is not existed");
    	}

    	if(leaveDTO.getStartDate().isBefore(LocalDate.now())) throw new UnauthorizedException("Leave start date should not before the current date");

    	if (leaveDTO.getEndDate().isBefore(leaveDTO.getStartDate())) throw new UnauthorizedException("Leave end date should not before the leave start date");
    	
        Leave leave = leaveMapper.leaveDTOToLeave(leaveDTO);
        leave.setStatus(StatusEnum.WAITING);
        leave.setTransactionStatus(TransactionStatusEnum.PENDING);
        Long numberOfDays = calculateDays(leaveDTO.getStartDate(), leaveDTO.getEndDate());
        leave.setNumberOfDays(numberOfDays.intValue());
        leave.setCreationDetails(jwtUtil.extractUsernameFromRequest());
        LeaveDTO leaveToLeaveDTO = leaveMapper.leaveToLeaveDTO(leaveRepository.save(leave));
        leaveToLeaveDTO.setApproverName(approverResource.getFirstName());
        leaveToLeaveDTO.setLeaveSeekerName(resourceService.findByEmail(resourceEmail).getFirstName());
        return leaveToLeaveDTO;
    }

    @Override
    public LeaveSummuryDTO leaveSummary(String resourceId) {
        int numberOfDays = 0;


        List<LeaveDTO> leavesByResourceIdAndStatus = getLeavesByResourceIdAndStatus(resourceId, StatusEnum.APPROVED);
        for (LeaveDTO leaveDTO : leavesByResourceIdAndStatus) {
            numberOfDays += leaveDTO.getNumberOfDays();
        }

        int exceededLeaves = numberOfDays > maxNoOfHolidays ? numberOfDays - maxNoOfHolidays : 0;
        LeaveSummuryDTO dto = new LeaveSummuryDTO();
        dto.setExceededLeaves(exceededLeaves);
        dto.setLeavesTaken(numberOfDays);
        return dto;
    }

    @Override
    public LeaveDTO updateLeave(int leaveId, LeaveDTO leaveDTO) {
    	String resourceEmail = jwtUtil.extractUsernameFromRequest();
        Leave leave = leaveRepository.findById(leaveId).orElseThrow(() -> new ResourceNotFoundException("Leave wit id -> " + leaveId + " not found"));
       // leave.setApproverId(leaveDTO.getApproverId());
       //leave.setContactAddress(leaveDTO.getContactAddress());
        if(!resourceEmail.equals(leave.getResourceId())) {
        	throw new UnauthorizedException("Resource is not Authorized to make the request");
        }
        
        Resource approverResource = resourceService.findByEmail(leaveDTO.getApproverId());
    	List<Role> approverRoles = approverResource.getRoles();
    	List<String> rolesList=new ArrayList<String>();
    	approverRoles.forEach(role->{
    		rolesList.add(role.getName());
    	});
    	if(rolesList == null || !rolesList.contains("LEAVE_APPROVER")) {
    		throw new UnauthorizedException("Approver does not have LEAVE_APPROVER role");
    	}
    	
    	leaveDTO.setApproverName(approverResource.getFirstName());
    	leaveDTO.setLeaveSeekerName(resourceService.findByEmail(resourceEmail).getFirstName());
        
        if(leaveDTO.getColleagueEmail().equals(leaveDTO.getResourceId())){
    		throw new UnauthorizedException("Leave appliar should not be a Colleague");
    	}
        
    	Resource colleague = resourceService.findByEmail(leaveDTO.getColleagueEmail());
    	if(colleague == null) {
    		throw new ResourceNotFoundException("Colleague is not existed");
    	}
        
        leave.setStatus(StatusEnum.WAITING);
        leave.setTransactionStatus(TransactionStatusEnum.PENDING);
        leave.setContactPhone(leaveDTO.getContactPhone());
        //leave.setNumberOfDays(leaveDTO.getNumberOfDays());
        
        leave.setPayPercentage(leaveDTO.getPayPercentage());
        leave.setTicketsPaid(leaveDTO.isTicketsPaid());
        leave.setColleagueContact(leaveDTO.getColleagueContact());
        leave.setColleagueEmail(leaveDTO.getColleagueEmail());
        leave.setColleagueName(leaveDTO.getColleagueName());
        
        if(leaveDTO.getStartDate().isBefore(LocalDate.now())) throw new UnauthorizedException("Leave start date should not before the current date");

    	if (leaveDTO.getEndDate().isBefore(leaveDTO.getStartDate())) throw new UnauthorizedException("Leave end date should not before the leave start date");
        
        if(!leaveDTO.getStartDate().isEqual(leave.getStartDate()) || !leaveDTO.getEndDate().isEqual(leave.getEndDate())) {
        leaveDTO.setPreviousStartDate(leave.getStartDate());
        leave.setStartDate(leaveDTO.getStartDate());
        leaveDTO.setPreviousEndDate(leave.getEndDate());
        leave.setEndDate(leaveDTO.getEndDate());
        }
        
        
        if(!leaveDTO.getDescription().equals(leave.getDescription())) {
        leaveDTO.setPreviousDescription(leave.getDescription());
        leave.setDescription(leaveDTO.getDescription());
        }
        
        int prevLeaves = leave.getNumberOfDays();
        leave.setNumberOfDays(calculateDays(leave.getStartDate(), leave.getEndDate()).intValue());
        
        leaveDTO.setPreviousNumberOfDays(prevLeaves);
        leaveDTO.setId(leave.getId());
        leaveDTO.setStatus(leave.getStatus());
        leaveDTO.setTransactionStatus(leave.getTransactionStatus());
        leaveDTO.setNumberOfDays(leave.getNumberOfDays());
        
        
        
        leaveRepository.save(leave);
        
        return leaveDTO;
    }

    @Override
    public LeaveDTO deleteLeave(int id) {
    	
    	Leave leave =leaveRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Leave wit id -> " + id + " not found"));
        String resourceEmail = jwtUtil.extractUsernameFromRequest();
        if(!resourceEmail.equals(leave.getResourceId())) {
        	throw new UnauthorizedException("Resource is not Authorized to make this request");
        }
        
        LeaveDTO existedLeaveDTO = leaveMapper.leaveToLeaveDTO(leave);
        
        Resource seeker = resourceService.findByEmail(resourceEmail);
        Resource approver = resourceService.findByEmail(existedLeaveDTO.getApproverId());
        existedLeaveDTO.setLeaveSeekerName(seeker.getFirstName());
        existedLeaveDTO.setApproverName(approver.getFirstName());
        
        
        leave.setStatus(StatusEnum.DELETED);
        deletedLeaveRepository.save(deleteLeaveMapper.leaveToDeletedLeave(leave));
        leaveRepository.deleteById(id);
        
        return existedLeaveDTO;
    }

    @Override
    public LeaveDTO approveLeave(int leaveId, LeaveDTO leaveDTO) {
    	
    	Leave existingLeaveDetails =leaveRepository.findById(leaveId).orElseThrow(() -> new ResourceNotFoundException("Leave wit id -> " + leaveId + " not found"));
    	
    	String jwtUserEmail = jwtUtil.extractUsernameFromRequest();
    	if(existingLeaveDetails.getResourceId().equals(jwtUserEmail))
    		throw new LeaveSeekerIsApproverException(leaveDTO.getLeaveSeekerName(), leaveDTO.getApproverName());
        
    	if(!jwtUserEmail.equals(existingLeaveDetails.getApproverId())) {
    		throw new LeaveApproveException(jwtUserEmail, leaveDTO.getApproverId());
    	}

        existingLeaveDetails.setAuthorizationDetails(jwtUserEmail);
        existingLeaveDetails.setStatus(StatusEnum.APPROVED);
        existingLeaveDetails.setTransactionStatus(TransactionStatusEnum.MASTER);
        existingLeaveDetails.setApproverId(jwtUserEmail);
        existingLeaveDetails.setApproverComments(leaveDTO.getApproverComments());
        //approverName & SeekerName
        Resource seeker = resourceService.findByEmail(existingLeaveDetails.getResourceId());
        Resource approver = resourceService.findByEmail(existingLeaveDetails.getApproverId());

        Leave savedUser = leaveRepository.save(existingLeaveDetails);
       if(savedUser.isDeleteFlag()) leaveRepository.delete(savedUser);
       
	       LeaveDTO approvedLeaveDTO = leaveMapper.leaveToLeaveDTO(savedUser);
	       approvedLeaveDTO.setApproverName(approver.getFirstName());
	       approvedLeaveDTO.setLeaveSeekerName(seeker.getFirstName());
        return approvedLeaveDTO;
    }

    @Override
    public LeaveDTO rejectLeave(int leaveId, LeaveDTO leaveDTO) {
      
   	Leave existingLeaveDetails =leaveRepository.findById(leaveId).orElseThrow(() -> new ResourceNotFoundException("Leave wit id -> " + leaveId + " not found"));
    	
    	String jwtUserEmail = jwtUtil.extractUsernameFromRequest();
    	if(existingLeaveDetails.getResourceId().equals(jwtUserEmail))
    		throw new LeaveSeekerIsApproverException(leaveDTO.getLeaveSeekerName(), leaveDTO.getApproverName());
        
        
        if(!jwtUserEmail.equals(existingLeaveDetails.getApproverId())) {
        	throw new UnauthorizedException("The resource is not the Approver");
        }
        	
        existingLeaveDetails.setAuthorizationDetails(jwtUtil.extractUsernameFromRequest());
        existingLeaveDetails.setStatus(StatusEnum.REJECTED);
        existingLeaveDetails.setTransactionStatus(TransactionStatusEnum.PENDING);
        existingLeaveDetails.setApproverComments(leaveDTO.getApproverComments());
        //approverName & SeekerName
        Resource seeker = resourceService.findByEmail(existingLeaveDetails.getResourceId());
        Resource approver = resourceService.findByEmail(existingLeaveDetails.getApproverId());
        Leave savedUser = leaveRepository.save(existingLeaveDetails);
        if(savedUser.isDeleteFlag()) leaveRepository.delete(savedUser);
        
        LeaveDTO rejectLeaveDTO = leaveMapper.leaveToLeaveDTO(savedUser);
        rejectLeaveDTO.setApproverName(approver.getFirstName());
        rejectLeaveDTO.setLeaveSeekerName(seeker.getFirstName());
        return rejectLeaveDTO;
    }

    @Override
    public Long calculateDays(LocalDate startDate, LocalDate endDate) {
    	
    	Long leaves = ChronoUnit.DAYS.between(startDate, endDate) +1;
    	
    	if(!addHolidaysToLeave) {
    		return leaves;
    	}
    	
    	int year = 2024; //getCUrrentYear
    	
    	List<HolidayDTO> holidayDTOs = holidayService.getHolidaysOfYear(year);

		String[] weekendList = weekends.split(",");
		
		Map<String, DayOfWeek> weekdays = new HashMap<String, DayOfWeek>();
		weekdays.put("firstWeekend", DayOfWeek.valueOf(weekendList[0].toUpperCase()));
		weekdays.put("secondWeekend", DayOfWeek.valueOf(weekendList[1].toUpperCase()));
		System.out.println("***** firstWeekend= "+weekdays.get("firstWeekend"));
		System.out.println("***** secondWeekend= "+weekdays.get("secondWeekend"));
		
		LocalDate dayBeforeStartDate = startDate.minusDays(1);
		LocalDate dayAfterEndDate = endDate.plusDays(1);
		
		if(dayBeforeStartDate.getDayOfWeek()==weekdays.get("secondWeekend")) {
			leaves+=2;
		}else if (dayBeforeStartDate.getDayOfWeek() != weekdays.get("firstWeekend") && 
						dayBeforeStartDate.getDayOfWeek() != weekdays.get("secondWeekend")) {
			
			 Optional<HolidayDTO> optionalMatch = holidayDTOs.stream().filter(dto -> dayAfterEndDate.isEqual(dto.getStartDate())).findAny();
			 if(optionalMatch.isPresent()){
				leaves += optionalMatch.get().getNumberOfDays(); 
			 }
		}
		if (dayAfterEndDate.getDayOfWeek()==weekdays.get("firstWeekend")) {
			leaves+=2;
		}else if (dayAfterEndDate.getDayOfWeek() != weekdays.get("firstWeekend") && 
						dayAfterEndDate.getDayOfWeek() != weekdays.get("secondWeekend")) {
			
			 Optional<HolidayDTO> optionalMatch = holidayDTOs.stream().filter(dto -> startDate.minusDays(1).equals(dto.getEndDate())).findAny();
			 if(optionalMatch.isPresent()){
				leaves += optionalMatch.get().getNumberOfDays(); 
			 }
			
		}
		
		
		return leaves;
    }

	@Override
	public List<LeaveDTO> getAll(StatusEnum status, String resourceId, String approverId, LocalDate startDate,LocalDate endDate) {
		//Date sDate = startDate == null ? null : Date.valueOf(startDate);
		//Date eDate = endDate == null ? null : Date.valueOf(endDate);
		
		String resourceEmail = jwtUtil.extractUsernameFromRequest();
		String rolesFromRequest = jwtUtil.extractRolesFromRequest();
		List<String> rolesList = Arrays.asList(rolesFromRequest.split(","));
		// empty check
		if(resourceId != null && resourceId.isBlank()) resourceId = null;
		if(approverId != null && approverId.isBlank()) approverId = null;

		if(rolesList.contains("LEAVE_ADMIN")) {
			return leaveMapper.leaveToLeaveDTOs(leaveRepository.getAllLeaves(status,resourceId,approverId,startDate, endDate));
		}else if(rolesList.contains("LEAVE_APPROVER")) {
			 
			if(approverId!=null && !approverId.isEmpty() && !approverId.equals(resourceEmail)) {
				throw new UnauthorizedException("Resource is not Authorized to make the requesgt");
			}
			
			if(resourceId != null && !resourceId.isEmpty()) {
                if(resourceId.equals(resourceEmail)) {
                    approverId = null;
                }else{
                    approverId = resourceEmail;
                }


                return leaveMapper.leaveToLeaveDTOs(leaveRepository.getAllLeaves(status, resourceId, approverId, startDate, endDate));
			}

            resourceId = resourceEmail;
            approverId = resourceEmail;
            return leaveMapper.leaveToLeaveDTOs(leaveRepository.getAllLeavesOfApprover(status, resourceId, approverId, startDate, endDate));
		}else {
			if(resourceId != null && !resourceId.equals(resourceEmail) || approverId != null ) {
				throw new UnauthorizedException("Resource is not Authorized to make the requesgt");
			}
			
			return leaveMapper.leaveToLeaveDTOs(leaveRepository.getAllLeaves(status, resourceEmail, null, startDate, endDate));
		}
		
		
	}

	@Override
	public Page<LeaveDTO> getAllLeavesByPaging(StatusEnum status, String resourceId, String approverId,
			LocalDate startDate, LocalDate endDate, int page, int pageSize) {

		String resourceEmail = jwtUtil.extractUsernameFromRequest();
		String rolesFromRequest = jwtUtil.extractRolesFromRequest();
		List<String> rolesList = Arrays.asList(rolesFromRequest.split(","));
		// empty check
		if(resourceId != null && resourceId.isBlank()) resourceId = null;
		if(approverId != null && approverId.isBlank()) approverId = null;

		System.out.println("___ args :" +status+"\t"+resourceId+"\t"+approverId+"\t"+startDate+"\t"+endDate);
		if(rolesList.contains("LEAVE_ADMIN")) {
			Page<Leave> allLeavesByPaging = leaveRepository.getAllLeavesByPaging(status,resourceId,approverId,startDate, endDate,PageRequest.of(page, pageSize));
			System.out.println("allLeavesByPaging ----"+allLeavesByPaging.getSize());
			allLeavesByPaging.forEach(leave->System.out.println(leave.toString()));
			return leaveMapper.leavesToLeaveDTOsByPage(allLeavesByPaging);
		}else if(rolesList.contains("LEAVE_APPROVER")) {
			 
			if(approverId!=null && !approverId.isEmpty() && !approverId.equals(resourceEmail)) {
				throw new UnauthorizedException("Resource is not Authorized to make the requesgt");
			}
			
			if(resourceId != null && !resourceId.isEmpty()) {
                if(resourceId.equals(resourceEmail)) {
                    approverId = null;
                }else{
                    approverId = resourceEmail;
                }


                return leaveMapper.leavesToLeaveDTOsByPage(leaveRepository.getAllLeavesByPaging(status, resourceId, approverId, startDate, endDate,PageRequest.of(page, pageSize)));
			}

            resourceId = resourceEmail;
            approverId = resourceEmail;
            return leaveMapper.leavesToLeaveDTOsByPage(leaveRepository.getAllLeavesOfApproverByPaging(status, resourceId, approverId, startDate, endDate,PageRequest.of(page, pageSize)));
		}else {
			if((resourceId != null && !resourceId.equals(resourceEmail)) || approverId != null) {
				throw new UnauthorizedException("Resource is not Authorized to make the requesgt");
			}
			
			return leaveMapper.leavesToLeaveDTOsByPage(leaveRepository.getAllLeavesByPaging(status, resourceEmail, null, startDate, endDate,PageRequest.of(page, pageSize)));
		}
		
		
	
	}


//    @Override
//    public List<LeaveDTO> getPendingLeavesByApproverId(int id) {
//        List<Leave> leaves = leaveRepository.findByPendingApproverId(id,TransactionStatusEnum.PENDING);
//        if(leaves.size()==0) throw new RuntimeException("No pending customers exists in the system");
//        List<LeaveDTO> leaveDTOS = new ArrayList<>();
//        leaves.forEach((leave) -> leaveDTOS.add(leaveMapper.leaveToLeaveDTO(leave)));
//        return leaveDTOS;
//    }

    


}
