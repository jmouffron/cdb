package com.excilys.cdb.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.exception.BadInputException;
import com.excilys.cdb.exception.PageException;
import com.excilys.cdb.exception.ServiceException;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.view.IndexPagination;
import com.excilys.cdb.view.Pagination;

@Controller
public class ComputerController {
  private static final String DASHBOARD = "dashboard";
  private static final String EDIT_COMPUTER = "editComputer";
  private static final String ADD_COMPUTER = "addComputer";
  private static final String ERROR = "500";
  
  private final Logger logger = LoggerFactory.getLogger(ComputerController.class);
  
  @Autowired
  ComputerService computerService;
  
  @Autowired
  MessageSource msgSrc;
  
  Locale locale;
  
  private static Map<String, String> columns;
  
  static {
    columns = new HashMap<>();

    columns.put("0", "pc_name");
    columns.put("1", "introduced");
    columns.put("2", "discontinued");
    columns.put("3", "cp_name"); 
  }
  
  @GetMapping({"/", "/dashboard","/dashBoard"})
  public String getDashBoard(@RequestParam(required=false) Map<String,String> paths, Model model) {
    logger.info( getClass().getName() + " has been called");
    Pagination pagination = new Pagination(computerService.getAll().get(), 0, IndexPagination.IDX_10);
    
    List<ComputerDTO> computers = null;
    IndexPagination entitiesPerPage;
    int startIndex;
    int page;
    boolean isDesc;

    String success = paths.get("success");
    String danger = paths.get("danger");
    
    String index = paths.get("startIndex");
    String perPage = paths.get("perPage");
    String newPage = paths.get("page");

    String searchName = paths.get("search");
    String order = paths.get("order");
    String toOrder = paths.get("toOrder");
    String selection = paths.get("selection");
    
    isDesc = (order == null || order.equals("")) ? false : true;
    entitiesPerPage = (perPage == null) ? IndexPagination.IDX_10 : IndexPagination.valueOf(perPage);
    startIndex = (index == null) ? 0 : Integer.parseInt(index);
    page = (newPage == null || newPage.equals("0")) ? 1 : Integer.parseInt(newPage);
    toOrder = (toOrder == null || toOrder.equals("") || !columns.containsKey(toOrder)) ? "pc_name"
        : columns.get(toOrder);
    
    if (searchName == null || searchName.isEmpty()) {
      searchName = "";
      try {
        computers = this.computerService.orderBy(toOrder, isDesc).get();
      } catch (ServiceException e) {
        model.addAttribute( "stackTrace" , e.getMessage() );
        return ERROR;
      }
    } else {
       try {
        computers = this.computerService.searchByNameOrdered(searchName, toOrder, isDesc).get();
      } catch (ServiceException e) {
        model.addAttribute( "stackTrace" , e.getMessage() );
        return ERROR;
      }
    }

    model.addAttribute( "search", searchName );
    model.addAttribute( "index", startIndex );
    model.addAttribute( "success", success );
    model.addAttribute( "danger", danger );
    model.addAttribute( "sel", selection );

    try {
      pagination.setPerPage(entitiesPerPage);
    } catch (PageException e) {
      model.addAttribute( "stackTrace" , e.getMessage() );
      return ERROR;
    }

    pagination.setElements(computers);
    pagination.navigate(page);
   
    try {
      model.addAttribute( "computerNumber", pagination.getSize() );
      model.addAttribute( "totalPages", pagination.getTotalPages() );
      model.addAttribute( "pages", pagination.getPages() );
      model.addAttribute( "computers", pagination.list().get() );
      model.addAttribute( "currentPage", pagination.getCurrentPage() );
    } catch (PageException e) {
      model.addAttribute( "stackTrace" , e.getMessage() );
      return ERROR;
    }

    return DASHBOARD;
  }
  
  @PostMapping({"/", "/dashboard","/dashBoard"})
  public String postDeleteComputer(@RequestParam(required=false) Map<String,String> paths, Model model) {
    logger.info( getClass().getName() + " has been called");
    
    
    String success = paths.get("success");
    String danger = paths.get("danger");
    String selection = paths.get("selection");
    
    logger.error(selection);

    if ( "".equals(selection) ) {
      logger.error("No correct computer name found!");
      model.addAttribute( "stackTrace", "No correct computer name found!");
      return ERROR;
    }
    List<Long> computerIdsToDelete = Arrays.asList(selection.split(",")).stream().map(Long::parseLong).collect(Collectors.toList());
    
    computerIdsToDelete.forEach( (Long id) -> {
      ComputerDTO computer = null;
      try {
        computer = this.computerService.getOneById(id).get();
      } catch (BadInputException e) {
        model.addAttribute("stackTrace", e.getMessage());
      }
  
      if (computer == null) {
        logger.error("Null computer found!");
        model.addAttribute("stackTrace","Null computer found!");
      }
      
      boolean isDeleted = false;
      
      try {
        isDeleted = this.computerService.deleteById(computer.getId());
      } catch (ServiceException e) {
        logger.error(e.getMessage());
        model.addAttribute("stackTrace", e.getMessage());
      }
      
      if (isDeleted) {
        model.addAttribute("success", "The computer " + computer.getName() + " has been correctly deleted!");
        model.addAttribute("danger", danger);
      }else {
        model.addAttribute("success", success);
        model.addAttribute("danger", "The computer" + computer.getName() + "couldn't be deleted!");
      }
    });

    return getDashBoard(paths, model);
  }
  
  @GetMapping({"/addComputer", "/addcomputer","/AddComputer", "/Addcomputer"})
  public String getAddComputer(@RequestParam(required=false) Map<String,String> paths, Model model) {
    logger.info( getClass().getName() + " has been called");
    return ADD_COMPUTER;
  }
  
  @PostMapping({"/addComputer", "/addcomputer","/AddComputer", "/Addcomputer"})
  public String postAddComputer(@RequestParam(required=false) Map<String,String> paths, Model model) {
    logger.info( getClass().getName() + " has been called");
    return ADD_COMPUTER;
  }
  
  @GetMapping({"/editComputer", "/editcomputer","/EditComputer", "/Editcomputer"})
  public String getEditComputer(@RequestParam(required=false) Map<String,String> paths, Model model) {
    logger.info( getClass().getName() + " has been called");
    return EDIT_COMPUTER;
  }
  
  @PostMapping({"/editComputer", "/editcomputer","/EditComputer", "/Editcomputer"})
  public String postEditComputer(@RequestParam(required=false) Map<String,String> paths, Model model) {
    logger.info( getClass().getName() + " has been called");
    return EDIT_COMPUTER;
  }
}
