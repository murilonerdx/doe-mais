package com.murilonerdx.doemais.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.murilonerdx.doemais.entities.Userman;

@Controller
@RequestMapping
public class LoginController {

    @GetMapping("/login")
    public String loginPage(Userman user) {
        return "login";
    }
}

