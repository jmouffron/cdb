package com.excilys.cdb.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CompanyController {
  
  @GetMapping({"/deleteCompany", "/deletecompany"})
  public void getRoot(@RequestParam(required=false) Map<String,String> paths, Model model) {
    
  }
  
  @PostMapping({"/deleteCompany", "/deletecompany"})
  public void postRoot(@RequestParam(required=false) Map<String,String> paths, Model model) {
    
  }
}
