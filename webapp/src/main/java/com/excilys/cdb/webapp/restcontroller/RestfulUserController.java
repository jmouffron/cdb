package com.excilys.cdb.webapp.restcontroller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.cdb.binding.dto.UserDTO;
import com.excilys.cdb.binding.exception.DaoException;
import com.excilys.cdb.binding.exception.EmailExistException;
import com.excilys.cdb.service.UserService;

@RestController
@RequestMapping("/api/")
public class RestfulUserController {
	
	UserService userService;
	
	public RestfulUserController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping("/login")
	public ResponseEntity<UserDetails> login(@Valid @RequestBody UserDTO dto, BindingResult res){
		UserDetails user = userService.loadUserByUsername(dto.getUsername());
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	@PostMapping("/logout")
	public ResponseEntity<UserDetails> logout(@Valid @RequestBody @AuthenticationPrincipal Authentication auth, BindingResult res){
		if(res.hasErrors()) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);	
		}
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(null, null, auth);
			return new ResponseEntity<>(null, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);	
	}
	
	@PostMapping("/signup")
	public ResponseEntity<UserDetails> signup(@Valid @RequestBody UserDTO dto, BindingResult res){
		try {
			userService.create(dto);
		} catch (DaoException | EmailExistException e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(null, HttpStatus.OK);
	}
	
}
