package com.excilys.cdb.console.resource;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;

import com.excilys.cdb.binding.dto.UserDTO;

public class UserResource {
	private static final String BASE_URI = "http://localhost:8080/api/users/";
	
	private static Invocation.Builder httpBuilder;
	
	private static Client client = ClientBuilder.newClient();

	private UserResource() {}

	public static Optional<UserDTO> getUser(Long id) {
		httpBuilder = client.target(BASE_URI).path("/"+id).request(MediaType.APPLICATION_JSON);
		return Optional.ofNullable(httpBuilder.get(UserDTO.class));
	}

	public static Optional<List<UserDTO>> getAllUsers() {
		httpBuilder = client.target(BASE_URI).request(MediaType.APPLICATION_JSON);
		return Optional.ofNullable(httpBuilder.get(List.class));
	}
	
	public static Optional<List<UserDTO>> getSortedUsers(String order) {
		httpBuilder = client.target(BASE_URI).queryParam("order", order).request(MediaType.APPLICATION_JSON);
		return Optional.ofNullable(httpBuilder.get(List.class));
	}
	
	public static void postUser(UserDTO dto) {
		httpBuilder = client.target(BASE_URI).path("add").request(MediaType.APPLICATION_JSON);
		httpBuilder.post(Entity.entity(dto, MediaType.APPLICATION_JSON), UserDTO.class);
	}
	
	public static void putUser(UserDTO dto) {
		httpBuilder = client.target(BASE_URI).path("edit/"+ dto.getId()).request(MediaType.APPLICATION_JSON);
		httpBuilder.put(Entity.entity(dto, MediaType.APPLICATION_JSON), UserDTO.class);
	}
	
	public static void deleteUser(long id) {
		httpBuilder = client.target(BASE_URI).path("delete/"+ id).request(MediaType.APPLICATION_JSON);
		httpBuilder.delete();
	}
	
}