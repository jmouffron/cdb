package com.excilys.cdb.console.resource;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.GenericType;
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
		Optional<CompanyDTO> company = Optional.empty();
		httpBuilder = client.target(BASE_URI).path("/"+ id).request(MediaType.APPLICATION_JSON);
		company = Optional.ofNullable(httpBuilder.get(CompanyDTO.class));
		return company;
	}

	public static Optional<List<CompanyDTO>> getAllCompanies() {
		Optional<List<CompanyDTO>> companies = Optional.empty();
		httpBuilder = client.target(BASE_URI).request(MediaType.APPLICATION_JSON);
		companies = Optional.ofNullable(httpBuilder.get(new GenericType<List<CompanyDTO>>() {}));
		return companies;
	}

	public static Optional<List<CompanyDTO>> getSortedCompanys(String order) {
		Optional<List<CompanyDTO>> companies = Optional.empty();
		httpBuilder = client.target(BASE_URI).queryParam("order", order ).request(MediaType.APPLICATION_JSON);
		companies = Optional.ofNullable(httpBuilder.get(new GenericType<List<CompanyDTO>>() {}));
		return companies;
	}

	public static void postCompany(CompanyDTO dto) {
		httpBuilder = client.target(BASE_URI).path("add").request(MediaType.APPLICATION_JSON);
		httpBuilder.post(Entity.entity(dto, MediaType.APPLICATION_JSON));
	}

	public static void putCompany(CompanyDTO dto) {
		httpBuilder = client.target(BASE_URI).path("edit/"+ dto.getId()).request(MediaType.APPLICATION_JSON);
		httpBuilder.put(Entity.entity(dto, MediaType.APPLICATION_JSON));
	}

	public static void deleteCompany(long id) {
		httpBuilder = client.target(BASE_URI).path("delete/"+ id).request(MediaType.APPLICATION_JSON);
		httpBuilder.delete();
	}
}
