package com.excilys.cdb.webapp.restcontroller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.excilys.cdb.binding.utils.JsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestControllerAdvice
@ControllerAdvice
public class RestfulErrorController {
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<String> badRequestHandler() throws JsonProcessingException {
		return new ResponseEntity<String>(JsonUtils.returnJsonFrom("ERROR 400: BAD REQUEST"), HttpStatus.BAD_REQUEST);
	}
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ResponseEntity<String> unauthorizedHandler() throws JsonProcessingException {
		return new ResponseEntity<String>(JsonUtils.returnJsonFrom("ERROR 401: UNAUTHORIZED"), HttpStatus.UNAUTHORIZED);
	}
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public ResponseEntity<String> forbiddenHandler() throws JsonProcessingException {
		return new ResponseEntity<String>(JsonUtils.returnJsonFrom("ERROR 403: FORBIDDEN"), HttpStatus.FORBIDDEN);
	}
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<String> notFoundHandler() throws JsonProcessingException {
		return new ResponseEntity<String>(JsonUtils.returnJsonFrom("ERROR 404: NOT FOUND"), HttpStatus.NOT_FOUND);
	}
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<String> internalErrorHandler() throws JsonProcessingException {
		return new ResponseEntity<String>(JsonUtils.returnJsonFrom("ERROR 500: INTERNAL SERVER ERROR"), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<String> goodrequestHandler() throws JsonProcessingException {
		return new ResponseEntity<String>(JsonUtils.returnJsonFrom("Creation succeeded !"), HttpStatus.CREATED);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleException(Exception exception) throws JsonProcessingException {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JsonUtils.returnJsonFrom(exception.getMessage()));
	}
}
