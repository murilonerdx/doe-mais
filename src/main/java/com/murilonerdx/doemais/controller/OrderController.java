package com.murilonerdx.doemais.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pedidos")
public class OrderController {
	
		@GetMapping
		public String index() {
			return "redirect:/pedidos";
		}
}
