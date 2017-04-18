package com.Bluswa.tistory.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	@GetMapping("")
	public String home() {
		return "index";	//리턴값이 중요
	}
}