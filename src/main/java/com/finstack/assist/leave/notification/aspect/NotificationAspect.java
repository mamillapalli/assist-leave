package com.finstack.assist.leave.notification.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.finstack.assist.leave.notification.entity.NotificationEvent;
import com.finstack.assist.leave.notification.service.NotificationService;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class NotificationAspect {

    @Autowired
    NotificationService notificationService;

    /**
     * Pointcut that matches all Spring beans in the application's main packages.
     */
    @Pointcut(

            //Point cut on Invoice management
            "within(com.finstack.assist.leave.service.LeaveService+) && execution(* addLeave(..))" +
                    "|| within(com.finstack.assist.leave.service.LeaveService+) && execution(* updateLeave(..))" +
                    "|| within(com.finstack.assist.leave.service.LeaveService+) && execution(* approveLeave(..))" +
                    "|| within(com.finstack.assist.leave.service.LeaveService+) && execution(* rejectLeave(..))" +
                    "|| within(com.finstack.assist.leave.service.LeaveService+) && execution(* deleteLeave(..))"
                    
    )

    public void notificationPointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    /**
     * Advice that logs when a method is entered and exited.
     *
     * @param joinPoint join point for advice
     * @return result
     * @throws Throwable throws IllegalArgumentException
     */
    @Around("notificationPointcut()")
    public Object recordNotificationEvent(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            Object result = joinPoint.proceed();

            try {
                switch (joinPoint.getSignature().getName()) {
                    case "addLeave":
                        notificationService.addLeave(result, NotificationEvent.LEAVE_CREATION_APPROVER);
                        notificationService.addLeave(result, NotificationEvent.LEAVE_CREATION_SEEKER);
                        break;
                    case "updateLeave":
                        notificationService.updateLeave(result, NotificationEvent.LEAVE_MODIFICATION_APPROVER);
                        notificationService.updateLeave(result, NotificationEvent.LEAVE_MODIFICATION_SEEKER);
                        break;
                    case "approveLeave":
                        notificationService.approveLeave(result, NotificationEvent.LEAVE_APPROVAL_APPROVER);
                        notificationService.approveLeave(result, NotificationEvent.LEAVE_APPROVAL_SEEKER);
                        break;
                    case "rejectLeave":
                        notificationService.rejectLeave(result, NotificationEvent.LEAVE_REJECTION_APPROVER);
                        notificationService.rejectLeave(result, NotificationEvent.LEAVE_REJECTION_SEEKER);
                        break;
                    case "deleteLeave":
                    	
                    	notificationService.deleteLeave(result, NotificationEvent.LEAVE_DELETION_APPROVER);
                    	notificationService.deleteLeave(result, NotificationEvent.LEAVE_DELETION_SEEKER);

                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            if (log.isDebugEnabled()) {
                log.debug("Exit: {}.{}() with result = {}", joinPoint.getSignature().getDeclaringTypeName(),
                        joinPoint.getSignature().getName(), result);
                log.debug("joinPoint.getSignature().getDeclaringTypeName() ==>" + joinPoint.getSignature().getDeclaringTypeName());
                log.debug("joinPoint.getSignature().getName() ==>" + joinPoint.getSignature().getName());

            }
            return result;
        } catch (IllegalArgumentException e) {
            log.error("Illegal argument: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()),
                    joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
            throw e;
        }
    }
}