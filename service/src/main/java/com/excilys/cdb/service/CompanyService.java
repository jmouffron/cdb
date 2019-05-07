package com.excilys.cdb.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.excilys.cdb.binding.dto.CompanyDTO;
import com.excilys.cdb.binding.exception.BadInputException;
import com.excilys.cdb.binding.exception.DaoException;
import com.excilys.cdb.binding.exception.ServiceException;
import com.excilys.cdb.core.model.Company;
import com.excilys.cdb.persistence.DaoCompany;
import com.excilys.cdb.persistence.mapper.CompanyMapper;
import com.excilys.cdb.binding.validator.ServiceValidator;

@Service
public class CompanyService {
	private DaoCompany dao;
	private CompanyMapper corpMapper;
	private static Logger log = LoggerFactory.getLogger(CompanyService.class);

	public CompanyService(DaoCompany dao, CompanyMapper mapper) {
		this.dao = dao;
		this.corpMapper = mapper;
	}

	public Optional<List<CompanyDTO>> getAll() throws ServiceException {
		List<CompanyDTO> list = null;
		list = this.dao.getAll().get().stream().map(CompanyMapper::mapToDTO).collect(Collectors.toList());

		return Optional.ofNullable(list);
	}

	public Optional<List<CompanyDTO>> orderBy(String name, boolean isDesc) throws ServiceException, DaoException {
		List<CompanyDTO> list;
		list = this.dao.getAllOrderedBy(name, isDesc).get().stream().map(CompanyMapper::mapToDTO)
				.collect(Collectors.toList());

		return Optional.ofNullable(list);
	}

	public Optional<List<CompanyDTO>> searchByName(String name) throws ServiceException {
		List<CompanyDTO> filteredCompanies = null;
		filteredCompanies = this.dao.getAll().get().stream().filter(company -> company.getName().matches(name))
				.map(CompanyMapper::mapToDTO).collect(Collectors.toList());

		return Optional.ofNullable(filteredCompanies);
	}

	public Optional<CompanyDTO> getOneById(Long id) throws BadInputException {
		ServiceValidator.idValidator(id, "company");

		return Optional.ofNullable(CompanyMapper.mapToDTO(dao.getOneById(id).get()));
	}

	public Optional<CompanyDTO> getOneByName(String name) throws BadInputException {
		ServiceValidator.nameValidator(name, "company");

		return Optional.ofNullable(CompanyMapper.mapToDTO(dao.getOneByName(name).get()));
	}

	public boolean create(CompanyDTO newEntity) throws ServiceException, DaoException {
		ServiceValidator.companyDTOValidator(newEntity);
		Optional<Company> company = corpMapper.mapToCompany(newEntity, this.getDao());
		return this.dao.create(company.orElseThrow(ServiceException::new));
	}

	public boolean updateById(CompanyDTO newEntity) throws ServiceException, DaoException {
		ServiceValidator.companyDTOValidator(newEntity);
		Optional<Company> company = corpMapper.mapToCompany(newEntity, this.getDao());
		return this.dao.updateById(company.orElseThrow(ServiceException::new));
	}

	public boolean deleteById(Long id) throws ServiceException {
		ServiceValidator.idValidator(id, "Company");

		try {
			return this.dao.deleteById(id);
		} catch (DaoException e) {
			log.error(e.getMessage());
			throw new ServiceException("Couldn't delete by id!");
		}
	}

	public boolean deleteByName(String name, ComputerService service) throws ServiceException {
		ServiceValidator.nameValidator(name, "Company");

		try {
			return this.dao.deleteByName(name);
		} catch (DaoException e) {
			log.error(e.getMessage());
			throw new ServiceException("Couldn't delete by name!");
		}
	}

	public DaoCompany getDao() {
		return this.dao;
	}

	public void setDao(DaoCompany dao) {
		this.dao = dao;
	}

}
