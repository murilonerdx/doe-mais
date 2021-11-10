package com.murilonerdx.doemais.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.murilonerdx.doemais.entities.Product;
import com.murilonerdx.doemais.repository.ProductRepository;

@Controller
@RequestMapping("/produtos")
public class ProductController {

    @Autowired
    private ProductRepository repository;


    @GetMapping
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("produtos");
        Iterable<Product> products = repository.findAll();
        modelAndView.addObject("product", products);
        return modelAndView;
    }

    @PostMapping("/cadastrar-produto")
    public String save(@Valid Product product, BindingResult result) {
        if (result.hasErrors()) return "produtos";
        repository.save(product);
        return "redirect:/produtos";
    }

}
