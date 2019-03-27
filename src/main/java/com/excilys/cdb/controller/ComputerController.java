package com.excilys.cdb.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ComputerController {
  
  @GetMapping({"/", "/dashboard","/dashBoard"})
  public void getRoot(@PathVariable(required=false) Map<String,String> paths) {
    
  }
  
  @PostMapping({"/", "/dashboard","/dashBoard"})
  public void postRoot(@PathVariable(required=false) Map<String,String> paths) {
    
  }
}
