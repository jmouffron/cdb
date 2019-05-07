package com.excilys.cdb.webapp.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.excilys.cdb.webapp.enums.HttpCode;

@Controller
public class ErrorController {
	
	@GetMapping("/401")
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public String get401(HttpServletRequest req, Model model) {
		return HttpCode.UNAUTHORIZED.getCode();
	}
	
	@GetMapping("/403")
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public String get403(HttpServletRequest req, Model model) {
		return HttpCode.FORBIDDEN.getCode();
	}
	
	@GetMapping("/404")
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String get404(HttpServletRequest req, Model model) {
		return HttpCode.NOT_FOUND.getCode();
	}
	
	@GetMapping("/500")
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public String get500(HttpServletRequest req, Model model) {
		return HttpCode.SERVER_ERROR.getCode();
	}
	
	@ExceptionHandler(RuntimeException.class)
	public String exceptionHandler(HttpServletRequest req, Model model, RuntimeException e) {
		return HttpCode.EXCEPTION.getCode();
	}
}
