package com.facturaelectronica.app.web.rest.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

	@GetMapping("/")
	public String inicializarHome() {
		return "Principal";
	}
}
