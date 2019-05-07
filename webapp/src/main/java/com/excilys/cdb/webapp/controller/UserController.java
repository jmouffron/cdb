package com.excilys.cdb.webapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.excilys.cdb.binding.dto.UserDTO;
import com.excilys.cdb.core.model.User;
import com.excilys.cdb.service.UserService;

@Controller
public class UserController {

	private final String SIGNUP = "signup";
	private final String REDIRECT_OP = "redirect:/computer/";
	private final String LOGIN = "login";

	private final Logger logger = LoggerFactory.getLogger(getClass().getName());
	private UserService userService;

	public UserController(UserService service) {
		this.userService = service;
	}

	@GetMapping("/login")
	public String getLogin(HttpServletRequest request, Model model) {
		model.addAttribute("user", new User());
		return LOGIN;
	}

	@PostMapping("/login")
	public String postLogin(@AuthenticationPrincipal User user, HttpServletRequest request, Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null) {
			model.addAttribute("user", new UserDTO());
			return LOGIN;
		}
		model.addAttribute("user", auth);
		return REDIRECT_OP;
	}

	@RequestMapping("/logout")
	public String postlogoutPage(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}

		return LOGIN;
	}

	@GetMapping("/signup")
	public String getSignup(HttpServletRequest request, Model model) {
		model.addAttribute("user", new User());
		return SIGNUP;
	}

	@PostMapping("/signup")
	public String postSignup(@AuthenticationPrincipal User user, HttpServletRequest request, Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null) {
			model.addAttribute("user", new User());
			return SIGNUP;
		}
		logger.error("" + auth.getPrincipal());
		userService.create(auth.getPrincipal());
		model.addAttribute("user", auth);
		return REDIRECT_OP;
	}
}
