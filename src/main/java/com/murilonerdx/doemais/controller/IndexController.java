package com.murilonerdx.doemais.controller;

import com.murilonerdx.doemais.entities.Business;
import com.murilonerdx.doemais.repository.BusinessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(value={"/index", "/"})
public class IndexController {

	@Autowired
	BusinessRepository businessRepository;

	@GetMapping
	public ModelAndView index(HttpServletRequest request, Model model) {
		ModelAndView modelAndView = new ModelAndView("/index");
		List<Business> business = businessRepository.findAll();
		modelAndView.addObject("business", business);

		return modelAndView;
	}
}
