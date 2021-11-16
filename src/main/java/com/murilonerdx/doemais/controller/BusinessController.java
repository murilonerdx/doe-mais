package com.murilonerdx.doemais.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.murilonerdx.doemais.entities.Business;
import com.murilonerdx.doemais.repository.BusinessRepository;
import com.murilonerdx.doemais.services.BusinessService;

@Controller
public class BusinessController {

          @Autowired
          private BusinessRepository repository;
          
          @Autowired
          private BusinessService service;

        @GetMapping("/parceiros")
  		public ModelAndView index() {
  		ModelAndView modelAndView = new ModelAndView("/parceiros");
  		Iterable<Business> business = repository.findAll();
  		modelAndView.addObject("business", business);
  		return modelAndView;
      	}



          @PostMapping("/cadastro")
        public String save(@Valid Business business, BindingResult result, RedirectAttributes redirect, Authentication auth) {
            if(result.hasErrors()) return "cadastro";
            service.create(business);
            return "login";
        }

          @GetMapping("/cadastro")
          public String index1(Business business) {
              return "cadastro";
          }
}