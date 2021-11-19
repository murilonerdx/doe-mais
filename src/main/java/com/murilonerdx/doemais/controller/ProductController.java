package com.murilonerdx.doemais.controller;

import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.murilonerdx.doemais.entities.Business;
import com.murilonerdx.doemais.entities.Product;
import com.murilonerdx.doemais.entities.Userman;
import com.murilonerdx.doemais.entities.enums.OrderStatus;
import com.murilonerdx.doemais.repository.BusinessRepository;
import com.murilonerdx.doemais.repository.ProductRepository;

@Controller
public class ProductController {
	
	Business b;
	
	@Autowired
	private BusinessRepository businessRepository;

	@Autowired
	private ProductRepository productRepository;

	@GetMapping("/produtos")
	public ModelAndView index(Authentication auth, HttpServletRequest request, Model model) {
		ModelAndView modelAndView = new ModelAndView("produtos");
		Page<Product> products = getModelAndView(request, model, modelAndView);
		modelAndView.addObject("products", products);

		return modelAndView;
	}

	@PostMapping("/cadastrar")
	public String save(@Valid Product product, BindingResult result, @ModelAttribute("data") String data,  Authentication auth) {
		if(data != null){
			LocalDate dt= LocalDate.parse(data);
			product.setDueDate(dt);
			Userman u = (Userman) auth.getPrincipal();
			Business business = businessRepository.findUserId(u.getId());
			product.setBusiness(business);
			product.setStatus(OrderStatus.AVAILABLE);
			business.getProducts().add(product);
			businessRepository.save(business);
			return "redirect:/produtos";
		}
		return "redirect:/produtos";

	}
	
	@GetMapping("/cadastro-produto")
	public String redirect(Product p) {
		return "cadastro-produto";
	}

	public Page<Product> getModelAndView(HttpServletRequest request, Model mv, ModelAndView modelAndView) {
		int page = 0, size = 5;

		if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
			page = Integer.parseInt(request.getParameter("page")) - 1;
		}

		if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
			size = Integer.parseInt(request.getParameter("size"));
		}

		return productRepository.findAll(PageRequest.of(page, size));
	}
}