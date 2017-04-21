package com.Bluswa.tistory.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Bluswa.tistory.domain.Question;
import com.Bluswa.tistory.domain.QuestionRepository;
import com.Bluswa.tistory.domain.User;

@Controller
@RequestMapping("/questions")
public class QuestionController {
	
	@Autowired
	private QuestionRepository questionRepository;
	
	@GetMapping("/form")
	public String form(HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "/users/loginForm";
		}
		return "/qna/form";
	}
	
	@PostMapping("")
	public String create(String title, String contents, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "/user/loginForm";
		}
		
		User sessionUser = HttpSessionUtils.getUserFromSession(session);
		Question newQuestion = new Question(sessionUser, title, contents);
		questionRepository.save(newQuestion);				
		return "redirect:/";
	}
	
	@GetMapping("/{id}")
	public String show(@PathVariable Long id, Model model) {
		model.addAttribute("question", questionRepository.findOne(id));
//		answerRepository.findByQuestionId(); //5-5 19:00 이런방법도 있다 하지만 question.java에서 구현
		return "/qna/show"; 
	}
	
	@GetMapping("/{id}/form")
	public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
		try {
			Question question = questionRepository.findOne(id);
			hasPermission(session, question);
			model.addAttribute("question", question);
			return "/qna/updateForm";
		}
		catch (IllegalStateException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "/user/login";
		}
		/*if (!HttpSessionUtils.isLoginUser(session)) {
			return "/users/loginForm";
		}
		
		User loginUser = HttpSessionUtils.getUserFromSession(session);
		System.out.println("LonginUser : " + loginUser);
		Question question = questionRepository.findOne(id);
		System.out.println("LonginUser : " + question.isSameWriter(loginUser));
		if (!question.isSameWriter(loginUser)) {
			return "/users/loginForm";
		}
		
		model.addAttribute("question", question);
		return "/qna/updateForm";*/		//5-6 try부터 리팩토링 위 코드와 비교
	}
	
	private boolean hasPermission(HttpSession session, Question question) {
		if (!HttpSessionUtils.isLoginUser(session)){
			throw new IllegalStateException("로그인이 필요합니다.");
//			return "/user/loginForm"; //exception처리는 위로 대신함
		}
		
		User loginUser = HttpSessionUtils.getUserFromSession(session);
		if (!question.isSameWriter(loginUser)) {
			throw new IllegalStateException("작성자만 수정 가능합니다.");
		}
		
		return false;
	}
	
	@PutMapping("/{id}")
	public String update(@PathVariable Long id, String title, String contents, Model model, HttpSession session) {
		try {
			Question question = questionRepository.findOne(id);
			hasPermission(session, question);	// 5-6 7:00 중복코드 제거하면서 아래서 가지고 올라온부분
			question.update(title, contents);	// question.update가 없을때, ctrl + 1 메소드 생성, question.java에 자동으로 틀 만들어짐
			questionRepository.save(question);	
			return String.format("redirect:/questions/%d", id);// 5-6 중복코드 제거하면서 아래서 가지고 올라온부분
		}
		catch (IllegalStateException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "/user/login";
/*		if (!HttpSessionUtils.isLoginUser(session)) {
			return "/users/loginForm";
		}
		
		User loginUser = HttpSessionUtils.getUserFromSession(session);
		System.out.println("LonginUser : " + loginUser);
		Question question = questionRepository.findOne(id);
		if (!question.isSameWriter(loginUser)) {
			return "/users/loginForm";
		}
		
		question.update(title, contents); // question.update가 없을때, ctrl + 1 메소드 생성, question.java에 자동으로 틀 만들어짐
		questionRepository.save(question);
		return String.format("redirect:/questions/%d", id);*/
		}
	}
	
	@DeleteMapping("/{id}")
	public String delete(@PathVariable Long id, Model model, HttpSession session) {
		try {
		Question question = questionRepository.findOne(id);
		hasPermission(session, question);
		questionRepository.delete(id);
		return "redirect:/";
	}
	catch (IllegalStateException e) {
		model.addAttribute("errorMessage", e.getMessage());
		return "/user/login";
	}
/*		if (!HttpSessionUtils.isLoginUser(session)) {
			return "/users/loginForm";
		}
		
		User loginUser = HttpSessionUtils.getUserFromSession(session);
		Question question = questionRepository.findOne(id);
		if (!question.isSameWriter(loginUser)) {
			return "users/loginForm";
		}
		
		questionRepository.delete(id);
		return "redirect:/";*/
	}
}
