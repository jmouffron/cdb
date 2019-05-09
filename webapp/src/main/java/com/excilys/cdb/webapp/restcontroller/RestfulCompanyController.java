package com.excilys.cdb.webapp.restcontroller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.excilys.cdb.binding.dto.CompanyDTO;
import com.excilys.cdb.binding.exception.DaoException;
import com.excilys.cdb.binding.exception.ServiceException;
import com.excilys.cdb.persistence.mapper.CompanyMapper;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.view.Pagination;

@RestController
@RequestMapping("/api/companies")
public class RestfulCompanyController {
	
	private CompanyService companyService;

	private Pagination pagination;


	public RestfulCompanyController(CompanyService companyService, Pagination pagination,
			CompanyMapper mapper) {
		this.companyService = companyService;
		this.pagination = pagination;
	}
	
	@GetMapping("/")
	public ResponseEntity<List<CompanyDTO>> getAllCompanies(
			@RequestParam(name = "search", required = false) String search,
			@RequestParam(name = "isDesc", required = false, defaultValue = "true") boolean isDesc,
			@RequestParam(name = "toOrder", required = false, defaultValue= "name") String toOrder
	){
		Optional<List<CompanyDTO>> companies;
		if(toOrder != null ) {
			try {
				companies = Optional.ofNullable(companyService.orderBy(toOrder, isDesc).orElseThrow(ServiceException::new));
			} catch (DaoException | ServiceException e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}else {
			try {
				companies = Optional.ofNullable(companyService.getAll().orElseThrow(ServiceException::new));
			} catch (ServiceException e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		if(!companies.isPresent()) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(companies.get(), HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<CompanyDTO> getCompany(@Valid @PathVariable long id) {
		if(id<1) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Optional<CompanyDTO> dto = Optional.empty();
		try {
			dto = companyService.getOneById(id);
		} catch (ServiceException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(dto.isPresent()) {
			return new ResponseEntity<>(dto.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
	
	@PostMapping("/add")
	public ResponseEntity<CompanyDTO> addCompany(@Valid @RequestBody CompanyDTO dto, BindingResult res) {
		if(res.hasErrors()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		try {
			companyService.create(dto);
		} catch (DaoException | ServiceException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping("/edit/{id}")
	public ResponseEntity<CompanyDTO> putCompany(@Valid @RequestBody CompanyDTO dto, BindingResult res, @PathVariable long id) {
		if(res.hasErrors()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		try {
			companyService.updateById(dto);
		} catch (DaoException | ServiceException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PatchMapping("/edit/{id}")
	public ResponseEntity<CompanyDTO> patchCompany(@Valid @RequestBody CompanyDTO dto, BindingResult res, @PathVariable long id){
		return putCompany(dto,res,id);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<CompanyDTO> deleteCompany (@PathVariable long id){
		try {
			companyService.deleteById(id);
		} catch (ServiceException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
}

