package com.pushman.web;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

@ControllerAdvice
public class GlobalInitBinder {
  Log log = LogFactory.getLog(this.getClass());

  @InitBinder
  public void initBinder(WebDataBinder binder) {
//    log.info("GlobalInitBinder()....");
//    System.out.println("GlobalInitBinder()....");
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    dateFormat.setLenient(false);
    
    binder.registerCustomEditor(Date.class,
        new CustomDateEditor(dateFormat,false));
  }
  
  
}
