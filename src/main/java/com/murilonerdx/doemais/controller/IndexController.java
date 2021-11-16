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

@Controller
@RequestMapping(value={"/index", "/"})
public class IndexController {

	@Autowired
	BusinessRepository businessRepository;

	@GetMapping
	public ModelAndView index(HttpServletRequest request, Model model) {
		ModelAndView modelAndView = new ModelAndView("/index");
		getModelAndView(request, model, modelAndView);


		return modelAndView;
	}

	public Model getModelAndView(HttpServletRequest request, Model mv, ModelAndView modelAndView) {
		int page = 0, size = 5;

		if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
			page = Integer.parseInt(request.getParameter("page")) - 1;
		}

		if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
			size = Integer.parseInt(request.getParameter("size"));
		}

		Page<Business> business = businessRepository.findAll(PageRequest.of(page, size));
		modelAndView.addObject("business", business);
		return mv.addAttribute("pag", business);
	}
}
