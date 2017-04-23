package com.Bluswa.tistory.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Bluswa.tistory.domain.Answer;
import com.Bluswa.tistory.domain.AnswerRepository;
import com.Bluswa.tistory.domain.Question;
import com.Bluswa.tistory.domain.QuestionRepository;
import com.Bluswa.tistory.domain.User;

@Controller
@RequestMapping("/questions/{questionId}/answers")	///questions/{questionId}는 없어도 되지만
public class AnswerController {
	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private AnswerRepository answerRepository;
	
	@PostMapping("")
	public String create(@PathVariable Long questionId, String contents, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "/users/loginForm";
		}
		
		User loginUser = HttpSessionUtils.getUserFromSession(session);
		Question question = questionRepository.findOne(questionId);
		Answer answer = new Answer(loginUser, question, contents);
		answerRepository.save(answer);
		return String.format("redirect:/questions/%d", questionId);
	}

}
