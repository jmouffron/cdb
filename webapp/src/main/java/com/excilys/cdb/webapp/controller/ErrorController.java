package com.excilys.cdb.webapp.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;

import com.excilys.cdb.webapp.enums.HttpCode;

@Controller
public class ErrorController {
	
	@GetMapping("/403")
	public String get403(HttpServletRequest req, Model model) {
		return HttpCode.FORBIDDEN.getCode();
	}
	
	@GetMapping("/404")
	public String get404(HttpServletRequest req, Model model) {
		return HttpCode.NOT_FOUND.getCode();
	}
	
	@GetMapping("/500")
	public String get500(HttpServletRequest req, Model model) {
		return HttpCode.SERVER_ERROR.getCode();
	}
	
	@ExceptionHandler(RuntimeException.class)
	public String exceptionHandler(HttpServletRequest req, Model model, RuntimeException e) {
		return HttpCode.EXCEPTION.getCode();
	}
}
