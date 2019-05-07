package com.excilys.cdb.console.resource;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.binding.dto.ComputerDTO;

public class ComputerResource {

	private static final Logger logger = LoggerFactory.getLogger(ComputerResource.class);
	private static final String BASE_URI = "http://localhost:8080/api/computers";

	private static WebTarget baseTarget = ClientBuilder.newClient().target(BASE_URI);
	private static WebTarget get = baseTarget.path("{id}");
	private static WebTarget add = baseTarget.path("/add/");
	private static WebTarget put = baseTarget.path("/edit/{id}");
	private static WebTarget patch = baseTarget.path("/edit/{id}");
	private static WebTarget delete = baseTarget.path("/delete/{id}");

	private ComputerResource() {}

	public static Optional<ComputerDTO> getComputer(Long id) {
		Optional<ComputerDTO> computer = Optional.empty();
		computer = Optional.ofNullable(get.resolveTemplate("id", id).request().get(ComputerDTO.class));
		return computer;
	}

	public static Optional<List<ComputerDTO>> getAllComputers() {
		Optional<List<ComputerDTO>> computers = Optional.empty();
		computers = Optional.ofNullable(get.request().get(new GenericType<List<ComputerDTO>>() {
		}));
		return computers;
	}
	
	public static Optional<List<ComputerDTO>> getSortedComputers(String order) {
		Optional<List<ComputerDTO>> computers = Optional.empty();
		computers = Optional.ofNullable(get.queryParam("order", order).request().get(new GenericType<List<ComputerDTO>>() {}));
		return computers;
	}
	
	public static void postComputer(ComputerDTO dto) {
		add.request().post(dto);
	}
	
	public static void putComputer(ComputerDTO dto) {
		put.request().put(dto);
	}
	
	public static void patchComputer(ComputerDTO dto) {
		patch.request().patch(dto);
	}
	
	public static void deleteComputer(long id) {
		delete.request().delete();
	}

}
