package com.excilys.cdb.webapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.excilys.cdb.core.model.User;

@Controller
public class UserController {
		
	private final String REDIRECT_OP = "redirect:/computer/";
	private final String LOGIN = "/login/";
	
	@GetMapping("/login")
	public String getLogin(HttpServletRequest request, Model model) {
		return LOGIN;
	}
	
	@PostMapping("/login")
	public String postLogin(@AuthenticationPrincipal User user, HttpServletRequest request, Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth == null) {
			return LOGIN;
		}
		model.addAttribute("user", auth);
		return LOGIN;
	}
	
	@RequestMapping("/logout")  
    public String postlogoutPage(HttpServletRequest request, HttpServletResponse response) {  
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();  
        
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		
		return REDIRECT_OP;
	}
}
