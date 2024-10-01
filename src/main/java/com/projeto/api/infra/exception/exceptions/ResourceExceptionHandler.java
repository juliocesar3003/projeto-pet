package com.projeto.api.infra.exception.exceptions;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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
	 @ExceptionHandler(CustomIllegalArgumentException.class)
	 private ResponseEntity<String> handleCustomIllegalArgumentException(CustomIllegalArgumentException exception){
		return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
	 }

	@ExceptionHandler(CustomACessDeniedException.class)
	private ResponseEntity<String> handleCustomACessDeniedException(CustomACessDeniedException exception){
		return  ResponseEntity.status(HttpStatus.FORBIDDEN).body(exception.getMessage());
	}
	@ExceptionHandler(CustomDeleteporStageError.class)
	private ResponseEntity<String> handleCustomACessDeniedException(CustomDeleteporStageError exception){
		return  ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
	}
	}
	
