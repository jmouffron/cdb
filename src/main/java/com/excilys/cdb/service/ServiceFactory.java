package com.excilys.cdb.service;

public class ServiceFactory{

  public static final String COMPUTER_SERVICE = "computer";
  public static final String COMPANY_SERVICE = "company";
  private ComputerService computerService;
  private CompanyService companyService;
  
  private ServiceFactory() {}
  
  private ServiceFactory(ComputerService service) {
    super();
    this.computerService = service;
  }

  private ServiceFactory(CompanyService service) {
    super();
    this.companyService = service;
  }
  
  private ServiceFactory(ComputerService computerService, CompanyService companyService) {
    super();
    this.computerService = computerService;
    this.companyService = companyService;
  }
  
  public ComputerService getComputerService(){
    return this.computerService;
  }
  
  public CompanyService getCompanyService(){
    return this.companyService;
  }

}
