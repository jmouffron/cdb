package com.excilys.cdb.webapp.restcontroller;

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.excilys.cdb.binding.dto.ComputerDTO;
import com.excilys.cdb.binding.exception.BadInputException;
import com.excilys.cdb.binding.exception.DaoException;
import com.excilys.cdb.binding.exception.PageException;
import com.excilys.cdb.binding.exception.ServiceException;
import com.excilys.cdb.binding.validator.ComputerDTOValidator;
import com.excilys.cdb.binding.validator.ModelListValidator;
import com.excilys.cdb.core.model.Computer;
import com.excilys.cdb.core.model.ErrorJson;
import com.excilys.cdb.persistence.mapper.ComputerMapper;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.service.view.IndexPagination;
import com.excilys.cdb.service.view.Pagination;

@RestController
@RequestMapping("/api/computers/")
public class RestfulComputerController {
	
	private ComputerService computerService;

	private CompanyService companyService;

	private ComputerMapper pcMapper;

	private Pagination pagination;

	public RestfulComputerController(ComputerService computerService, CompanyService companyService, Pagination pagination,
			ComputerMapper mapper) {
		this.computerService = computerService;
		this.companyService = companyService;
		this.pagination = pagination;
		this.pcMapper = mapper;
	}
	
	@InitBinder("dto")
	protected void initDTOBinder(WebDataBinder binder) {
		binder.setValidator(new ComputerDTOValidator());
	}

	@InitBinder("computersDestroyed")
	protected void initListDTOBinder(WebDataBinder binder) {
		binder.setValidator(new ModelListValidator());
	}
	
	@GetMapping("/")
	public ResponseEntity<List<ComputerDTO>> getAllComputers(
			@RequestParam(value="page",required=false) Integer page,
			@RequestParam(value="perPage", required = false) IndexPagination perPage,
			@RequestParam(value="isDesc", required = false) boolean isDesc,
			@RequestParam(value="order", required = false) String order
	){
		Optional<List<ComputerDTO>> computers = Optional.empty();
		if(order != null && perPage == null && page == null) {
			
		} else if(perPage != null && page != null) {
			try {
				pagination.setPerPage(perPage);
				pagination.setElements(computerService.getAll().get());
				pagination.navigate(page);
				computers = pagination.list();
			} catch (PageException | DaoException e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else if(perPage != null && page != null && order != null) {
			try {
				pagination.setPerPage(perPage);
				pagination.setElements(computerService.orderBy(order, true).get());
				pagination.navigate(page);
				computers = pagination.list();
			} catch (PageException | DaoException | ServiceException e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else if(page != null) {
			try {
				pagination.setPerPage(IndexPagination.IDX_10);
				pagination.setElements(computerService.getAll().get());
				pagination.navigate(page);
				computers = pagination.list();
			} catch (PageException | DaoException e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			try {
				computers = computerService.getAll();
			} catch (DaoException e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		if(!computers.isPresent()) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(computers.get(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getComputerById(@Validated @PathVariable("id") long id){
		Optional<ComputerDTO> computer = Optional.empty();
		try {
			computer = computerService.getOneById(id);
		} catch (BadInputException e) {
			ErrorJson error = ErrorJson.of(HttpStatus.BAD_REQUEST);
			return new ResponseEntity<ErrorJson>(error, HttpStatus.BAD_REQUEST);
		}

		if(!computer.isPresent()) {
			ErrorJson error = ErrorJson.of(HttpStatus.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<ErrorJson>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ComputerDTO>(computer.get(), HttpStatus.OK);
	}
	
	@PostMapping("/add")
	public ResponseEntity<ComputerDTO> addComputer(@Valid @RequestBody ComputerDTO dto, BindingResult res) {
		if(res.hasErrors()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Optional<Computer> computer;
		try {
			computer = pcMapper.mapToComputer(dto, companyService.getDao());
		} catch (BadInputException e1) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(!computer.isPresent()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		try {
			computerService.create(computer.get());
		} catch (DaoException | ServiceException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PutMapping("/edit/{id}")
	public ResponseEntity<ComputerDTO> putComputer(@Valid @RequestBody ComputerDTO dto, BindingResult res, @PathVariable long id) {
		if(res.hasErrors()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Optional<Computer> computer;
		try {
			computer = pcMapper.mapToComputer(dto, companyService.getDao());
		} catch (BadInputException e1) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(!computer.isPresent()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		try {
			computerService.updateById(computer.get());
		} catch (DaoException | ServiceException e) {
			return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PatchMapping("/edit/{id}")
	public ResponseEntity<ComputerDTO> patchComputer(@Valid @RequestBody ComputerDTO dto, BindingResult res, @PathVariable long id){
		return putComputer(dto,res,id);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<ComputerDTO> deleteComputer (@PathVariable long id){
		try {
			computerService.deleteById(id);
		} catch (DaoException | ServiceException e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(null, HttpStatus.OK);
	}
	
}
