package com.csme.assist.leave.aspect;

import java.util.Date;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.csme.assist.leave.exception.LeaveApproveException;
import com.csme.assist.leave.exception.LeaveSeekerIsApproverException;
import com.csme.assist.leave.exception.TrishankuException;
import com.csme.assist.leave.exception.UnauthorizedException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ExceptionAspect {
	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<TrishankuException> unauthorizedException(UnauthorizedException ex){
		return new ResponseEntity<>(new TrishankuException(new Date(), ex.getMessage(), ex.getLocalizedMessage()),HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(LeaveSeekerIsApproverException.class)
	public ResponseEntity<TrishankuException> leaveSeekerIsApproverException(LeaveSeekerIsApproverException ex){
		
		return new ResponseEntity<>(new TrishankuException(new Date(), ex.getMessage(), ex.getLocalizedMessage()), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(LeaveApproveException.class)
	public ResponseEntity<TrishankuException> leaveApproveException(LeaveApproveException ex){
		
		return new ResponseEntity<>(new TrishankuException(new Date(), ex.getMessage(), ex.getLocalizedMessage()), HttpStatus.BAD_REQUEST);
	}
	
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(new TrishankuException(new Date(), ex.getMessage(), ex.getLocalizedMessage()), HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> httpMessageNotReadableException(HttpMessageNotReadableException ex, WebRequest request) {
        return new ResponseEntity<>(new TrishankuException(new Date(), ex.getMessage(), ex.getLocalizedMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstantViolationException(final ConstraintViolationException ex) {
        log.info("in constriant violation exception");
        StringBuilder message = new StringBuilder();
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            message.append(violation.getMessage().concat(";"));
        }
        return new ResponseEntity<>(new TrishankuException(new Date(), ex.getMessage(), ex.getLocalizedMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
    }

/*    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<?> ResourceAlreadyExistsException(ResourceAlreadyExistsException ex, WebRequest request) {
        return new ResponseEntity<>(new AssistException(new Date(), ex.getMessage(), ex.getLocalizedMessage()), HttpStatus.CONFLICT);    }*/

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        StringBuffer message = new StringBuffer();
        // Get the error messages for invalid fields
        ex.getBindingResult()
                .getFieldErrors()
                .forEach(fieldError -> {
                    message.append(fieldError.getField().concat(" ").concat(fieldError.getDefaultMessage()));
                    message.append(";");

                });

        return new ResponseEntity<>(new TrishankuException(new Date(), message.toString(), ex.getLocalizedMessage()), HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionHandler(Exception ex, WebRequest request) {
        log.info(ex.getClass().getName());
        return new ResponseEntity<>(new TrishankuException(new Date(), ex.getMessage(), ex.getLocalizedMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }



}