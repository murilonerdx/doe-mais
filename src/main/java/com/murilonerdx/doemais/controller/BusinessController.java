package com.murilonerdx.doemais.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.murilonerdx.doemais.entities.Business;
import com.murilonerdx.doemais.repository.BusinessRepository;

@Controller
public class BusinessController {
	
	  	@Autowired
	    private BusinessRepository repository;

	  	
	  	@GetMapping("/parceiros")
		public ModelAndView index() {
			ModelAndView modelAndView = new ModelAndView("parceiros");
			Iterable<Business> business = repository.findAll(); 
			modelAndView.addObject("business", business);
			return modelAndView;
		}
	
}
