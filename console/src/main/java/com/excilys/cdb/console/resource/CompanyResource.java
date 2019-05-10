package com.excilys.cdb.console.resource;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.ResponseProcessingException;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.binding.dto.CompanyDTO;

public class CompanyResource {
	private static final Logger logger = LoggerFactory.getLogger(CompanyResource.class);
	private static final String BASE_URI = "http://localhost:8080/api/companies/";
	
	private static Invocation.Builder httpBuilder;
	
	private static Client client = ClientBuilder.newClient();
	private CompanyResource() {}

	public static Optional<CompanyDTO> getCompany(Long id) {
		httpBuilder = client.target(BASE_URI).path("/"+ id).request(MediaType.APPLICATION_JSON);
		return Optional.ofNullable(httpBuilder.get(CompanyDTO.class));

	}

	public static Optional<List<CompanyDTO>> getAllCompanies() {
		httpBuilder = client.target(BASE_URI).request(MediaType.APPLICATION_JSON);
		return Optional.ofNullable(httpBuilder.get(List.class));
	}

	public static Optional<List<CompanyDTO>> getSortedCompanys(String order) {
		httpBuilder = client.target(BASE_URI).queryParam("order", order ).request(MediaType.APPLICATION_JSON);
		return Optional.ofNullable(httpBuilder.get(List.class));
	}

	public static CompanyDTO postCompany(CompanyDTO dto) {
		httpBuilder = client.target(BASE_URI).path("add").request(MediaType.APPLICATION_JSON);
		return httpBuilder.post(Entity.entity(dto, MediaType.APPLICATION_JSON), CompanyDTO.class);
	}

	public static CompanyDTO putCompany(CompanyDTO dto) {
		httpBuilder = client.target(BASE_URI).path("edit/{id}").resolveTemplate("id", dto.getId()).request(MediaType.APPLICATION_JSON);
		return httpBuilder.put(Entity.entity(dto, MediaType.APPLICATION_JSON), CompanyDTO.class);
	}

	public static void deleteCompany(long id) {
		httpBuilder = client.target(BASE_URI).path("delete/{id}").resolveTemplate("id", id).request(MediaType.APPLICATION_JSON);
		try {
			httpBuilder.delete();
		} catch (ResponseProcessingException e) {
			logger.error(e.getMessage());
		}
	}
}
