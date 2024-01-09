package com.projeto.api.service.exceptions;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.projeto.api.resource.exception.StandardError;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice()
public class ResourceExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> ResourceNotFound(ResourceNotFoundException e, HttpServletRequest request){
      String error = "resource not found";
      HttpStatus status = HttpStatus.NOT_FOUND;
      StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
}
@ExceptionHandler(DataBaseException.class)
public ResponseEntity<StandardError> DataBaseException(DataBaseException e, HttpServletRequest request){
	String error = "DataBaseda found";
	HttpStatus status =  HttpStatus.BAD_REQUEST;
	StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
     }
	}
	
