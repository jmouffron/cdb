package com.excilys.cdb.console.resource;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.binding.dto.CompanyDTO;

public class CompanyResource {
	private static final Logger logger = LoggerFactory.getLogger(CompanyResource.class);
	private static final String BASE_URI = "http://localhost:8080/api/companies";

	private static WebTarget baseTarget = ClientBuilder.newClient().target(BASE_URI);
	private static WebTarget get = baseTarget.path("{id}");
	private static WebTarget add = baseTarget.path("/add/");
	private static WebTarget put = baseTarget.path("/edit/{id}");
	private static WebTarget patch = baseTarget.path("/edit/{id}");
	private static WebTarget delete = baseTarget.path("/delete/{id}");

	private CompanyResource() {
	}

	public static Optional<CompanyDTO> getCompany(Long id) {
		Optional<CompanyDTO> company = Optional.empty();
		company = Optional.ofNullable(get.resolveTemplate("id", id).request().get(CompanyDTO.class));
		return company;
	}

	public static Optional<List<CompanyDTO>> getAllCompanies() {
		Optional<List<CompanyDTO>> companies = Optional.empty();
		companies = Optional.ofNullable(get.request().get(new GenericType<List<CompanyDTO>>() {}));
		return companies;
	}

	public static Optional<List<CompanyDTO>> getSortedCompanys(String order) {
		Optional<List<CompanyDTO>> companies = Optional.empty();
		companies = Optional.ofNullable(get.queryParam("order", order ).request().get(new GenericType<List<CompanyDTO>>() {}));
		return companies;
	}

	public static void postCompany(CompanyDTO dto) {
		add.request().post(dto);
	}

	public static void putCompany(CompanyDTO dto) {
		put.request().put(dto);
	}

	public static void patchCompany(CompanyDTO dto) {
		patch.request().patch(dto);
	}

	public static void deleteCompany(long id) {
		delete.request().delete();
	}
}
