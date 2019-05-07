package com.excilys.cdb.console.resource;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.excilys.cdb.binding.dto.UserDTO;


public class UserResource {

	private static final Logger logger = LoggerFactory.getLogger(UserResource.class);
	private static final String BASE_URI = "http://localhost:8080/api/users/";

	private static WebTarget baseTarget = ClientBuilder.newClient().target(BASE_URI);
	private static WebTarget get = baseTarget.path("{id}");
	private static WebTarget add = baseTarget.path("/add/");
	private static WebTarget put = baseTarget.path("/edit/{id}");
	private static WebTarget patch = baseTarget.path("/edit/{id}");
	private static WebTarget delete = baseTarget.path("/delete/{id}");

	private UserResource() {}

	public static ResponseEntity<UserDTO> getUser(Long id) {
		Optional<UserDTO> user = Optional.empty();
		try {
			user = Optional.ofNullable(
					get
						.resolveTemplate("id", id)
						.request()
						.get(UserDTO.class)
			);

			if (!user.isPresent()) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (WebApplicationException e) {
			logger.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
		}

		return ResponseEntity.ok().body(user.get());
	}
	
	public static Optional<List<UserDTO>> getAllUsers() {
		Optional<List<UserDTO>> users = Optional.empty();
		users = Optional.ofNullable(get.request().get(new GenericType<List<UserDTO>>() {}));
		return users;
	}

	public void postUser(UserDTO user) {
		add.request().post(user);
	}
}