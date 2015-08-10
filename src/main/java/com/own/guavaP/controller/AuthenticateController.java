package com.own.guavaP.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class AuthenticateController {
@RequestMapping(value="/status")
public String status(Model model){
	model.addAttribute("test", "欢迎");
	return "status";
}
}
