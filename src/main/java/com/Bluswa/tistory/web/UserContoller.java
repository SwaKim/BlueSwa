package com.Bluswa.tistory.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Bluswa.tistory.domain.User;
import com.Bluswa.tistory.domain.UserRepository;

@Controller
@RequestMapping("/users")
public class UserContoller {
	
	@Autowired
	private UserRepository userRepository;
	
	@PostMapping("")
	public String create(User user) { //자바 그이하내용
		System.out.println("user : " + user);
		userRepository.save(user);
		return "redirect:/users";
	}
	
	@GetMapping("")
	public String list(Model model) {
		model.addAttribute("users", userRepository.findAll());
		return "/user/list";
	}
}
