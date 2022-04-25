package com.example.jwt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Jenkins {
	
	
	@GetMapping("jenkinsAPI")
	public String jenkinsFunction() {
		return "This is jenkins API";
	}
	
	@GetMapping("jenkinsAPI2")
	public String jenkinsFunction2() {
		return "This is jenkins API 2";
	}

}
