package com.excilys.cdb.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.exception.BadInputException;
import com.excilys.cdb.exception.DaoException;
import com.excilys.cdb.exception.ServiceException;
import com.excilys.cdb.mapper.CompanyMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.DaoCompany;
import com.excilys.cdb.validator.ServiceValidator;

@Service
public class CompanyService {
  private DaoCompany dao;
  private static Logger log = LoggerFactory.getLogger(CompanyService.class);

  public CompanyService(DaoCompany dao) {
    this.dao = dao;
  }

  /*
   * @see com.excilys.cdb.service.IService#getAll()
   */
  
  public Optional<List<CompanyDTO>> getAll() throws ServiceException {
    List<CompanyDTO> list = null;
    list = this.dao.getAll().get().stream().map(CompanyMapper::mapToDTO).collect(Collectors.toList());

    return Optional.ofNullable(list);
  }
  
  /*
   * (non-Javadoc)
   * 
   * @see com.excilys.cdb.service.IService#orderBy(java.lang.String, boolean)
   */
  
  public Optional<List<CompanyDTO>> orderBy(String name, boolean isDesc) throws ServiceException, DaoException {
    List<CompanyDTO> list;
    list = this.dao.getAllOrderedBy(name, isDesc).get().stream().map(CompanyMapper::mapToDTO)
        .collect(Collectors.toList());

    return Optional.ofNullable(list);
  }

  /*
   * @see com.excilys.cdb.service.IService#searchByName(java.lang.String)
   */
  
  public Optional<List<CompanyDTO>> searchByName(String name) throws ServiceException {
    List<CompanyDTO> filteredCompanies = null;
    filteredCompanies = this.dao.getAll().get().stream().filter(company -> company.getName().matches(name))
        .map(CompanyMapper::mapToDTO).collect(Collectors.toList());

    return Optional.ofNullable(filteredCompanies);
  }

  /*
   * @see com.excilys.cdb.service.IService#getOneById(java.lang.Long)
   */
  
  public Optional<CompanyDTO> getOneById(Long id) throws BadInputException {
    ServiceValidator.idValidator(id, "company");

    return Optional.ofNullable(CompanyMapper.mapToDTO(dao.getOneById(id).get()));
  }

  /*
   * @see com.excilys.cdb.service.IService#getOneByName(java.lang.String)
   */
  
  public Optional<CompanyDTO> getOneByName(String name) throws BadInputException {
    ServiceValidator.nameValidator(name, "company");

    return Optional.ofNullable(CompanyMapper.mapToDTO(dao.getOneByName(name).get()));
  }

  /*
   * @see com.excilys.cdb.service.IService#create(com.excilys.cdb.model.Entity)
   */
  public boolean create(CompanyDTO newEntity) throws ServiceException, DaoException {
    ServiceValidator.companyDTOValidator(newEntity);
    Company company = CompanyMapper.mapToCompany(newEntity);
    return this.dao.create(company);
  }
  
  /*
   * @see com.excilys.cdb.service.IService#updateById(java.lang.Long,
   * com.excilys.cdb.model.Entity)
   */
  
  public boolean updateById(CompanyDTO newEntity) throws ServiceException, DaoException {
    ServiceValidator.companyDTOValidator(newEntity);
    Company company = CompanyMapper.mapToCompany(newEntity);
    return this.dao.updateById(company);
  }

  /*
   * @see com.excilys.cdb.service.IService#deleteById(java.lang.Long)
   */
  
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
      return this.dao.deleteByName(name, service);
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
