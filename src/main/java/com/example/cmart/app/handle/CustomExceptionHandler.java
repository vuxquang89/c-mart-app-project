package com.example.cmart.app.handle;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler{

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
		
		/*
		Map<String, Object> responseBody = new LinkedHashMap<String, Object>();
		responseBody.put("timestamp", new Date());
		responseBody.put("status", status.value());		
		
		List<String> errors = new ArrayList<String>();
		for(FieldError fieldError : fieldErrors) {
			
			errors.add(fieldError.getDefaultMessage());
		}
		
		responseBody.put("errors", errors);
		
		return new ResponseEntity<Object>(responseBody, headers, status);
		*/
		
		CustomErrorResponse errorResponse = new CustomErrorResponse();
		errorResponse.setTimestamp(new Date());
		errorResponse.setStatus(String.valueOf(status.value()));
				
		List<String> errors = new ArrayList<String>();
		for(FieldError fieldError : fieldErrors) {
			
			errors.add(fieldError.getDefaultMessage());
		}
		
		errorResponse.setMessages(errors);
		
		return new ResponseEntity<Object>(errorResponse, headers, status);
	}
	
	/*
	 * validation field table entity
	 * https://www.tabnine.com/code/java/methods/javax.validation.ConstraintViolation/getMessage
	 */
	@ExceptionHandler ({ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleConstraintViolationException(
            ConstraintViolationException e) {
		
		
		CustomErrorResponse errorResponse = new CustomErrorResponse();
		errorResponse.setTimestamp(new Date());
		errorResponse.setStatus(HttpStatus.BAD_REQUEST.toString());
		
		List<String> errors = new ArrayList<String>();

		for(Iterator<ConstraintViolation<?>> it = e.getConstraintViolations().iterator(); it.hasNext();) {
			ConstraintViolation<?> violation = it.next();
			errors.add(violation.getMessage());		      
		}
		
		errorResponse.setMessages(errors);
		
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
		
		/*
		Map<String, String> errorMap = new HashMap<>();
		StringBuilder sb = new StringBuilder("Bean state is invalid: ");
		for(Iterator<ConstraintViolation<?>> it = e.getConstraintViolations().iterator(); it.hasNext();) {
			ConstraintViolation<?> violation = it.next();
		      sb.append(violation.getPropertyPath()).append(" - ").append(violation.getMessage());
		      if (it.hasNext()) {
		        sb.append("; ");
		      }
		}
		
		String details = e.getConstraintViolations().iterator().next().getMessage();
		System.out.println(details);
		//System.out.println(e.getMessage().);
		errorMap.put("status", HttpStatus.BAD_REQUEST.toString());
		errorMap.put("error", sb.toString());
		
		return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
		*/
        
    }
}
