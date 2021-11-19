package com.murilonerdx.doemais.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.murilonerdx.doemais.entities.Ong;
import com.murilonerdx.doemais.services.OngService;

@Controller
public class OngController {

    @Autowired
    private OngService service;

    @GetMapping("/cadastro-onu")
    public String index(Ong ong) {
        return "cadastro-onu";
    }

    @PostMapping("/cadastro-onu")
    public String save(@Valid Ong ong, BindingResult result, RedirectAttributes redirect, Authentication auth) {
        if(result.hasErrors()) return "cadastro-onu";
        service.create(ong);
        return "redirect:/login";
    }
}