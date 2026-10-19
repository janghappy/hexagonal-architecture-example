package com.example.hexagonal_architecture_example.adapter.in.web.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.hexagonal_architecture_example.domain.MemberNotFoundException;

@RestControllerAdvice
class GlobalExceptionHandler {

	@ExceptionHandler(MemberNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handleNotFound(MemberNotFoundException e) {
		return e.getMessage();
	}

	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String handleBadRequest(IllegalArgumentException e) {
		return e.getMessage();
	}

}
