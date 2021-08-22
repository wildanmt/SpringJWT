package com.ownproject.configuration;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ownproject.util.ErrorMessage;

@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ErrorMessage resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
		ErrorMessage message = new ErrorMessage(
				new Date(), 
				ex.getMessage(), 
				request.getDescription(false), 
				HttpStatus.NOT_FOUND.value());
		return message;
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorMessage globalExceptionHandler(Exception ex, WebRequest request) {
		ErrorMessage message = new ErrorMessage(
				new Date(), 
				ex.getMessage(), 
				request.getDescription(true), 
				HttpStatus.INTERNAL_SERVER_ERROR.value());
		return message;
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorMessage argumentExceptionHandler(Exception ex, WebRequest request) {
		ErrorMessage message = new ErrorMessage(
				new Date(), 
				ex.getMessage(), 
				request.getDescription(true), 
				HttpStatus.BAD_REQUEST.value());
		return message;
	}
}
