package com.facturaelectronica.app.web.rest.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/home")
	public String inicializarHome() {
		return "/pages/home";
	}
}
