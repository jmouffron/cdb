package com.excilys.cdb.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ComputerController {
  private static final String ROOT_URL = "/views";
  private static final String DASHBOARD = ROOT_URL + "dashboard";
  private static final String EDIT_COMPUTER = ROOT_URL + "editComputer";
  private static final String ADD_COMPUTER = ROOT_URL + "addComputer";
  
  @GetMapping({"/", "/dashboard","/dashBoard"})
  public String getDashBoard(@PathVariable(required=false) Map<String,String> paths) {
    return DASHBOARD;
  }
  
  @PostMapping({"/", "/dashboard","/dashBoard"})
  public String postDeleteComputer(@PathVariable(required=false) Map<String,String> paths) {
    return DASHBOARD;
  }
  
  @GetMapping({"/addComputer", "/addcomputer","/AddComputer", "/Addcomputer"})
  public String getAddComputer(@PathVariable(required=false) Map<String,String> paths) {
    return ADD_COMPUTER;
  }
  
  @PostMapping({"/addComputer", "/addcomputer","/AddComputer", "/Addcomputer"})
  public String postAddComputer(@PathVariable(required=false) Map<String,String> paths) {
    return ADD_COMPUTER;
  }
  
  @GetMapping({"/addComputer", "/addcomputer","/AddComputer", "/Addcomputer"})
  public String getEditComputer(@PathVariable(required=false) Map<String,String> paths) {
    return EDIT_COMPUTER;
  }
  
  @PostMapping({"/editComputer", "/editcomputer","/EditComputer", "/Editcomputer"})
  public String postEditComputer(@PathVariable(required=false) Map<String,String> paths) {
    return EDIT_COMPUTER;
  }
}
