package com.w3qre.calendar.controller.user;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.w3qre.calendar.service.user.UserService;

@Controller
public class SignupController {

	private final UserService userService;
	
	public SignupController(UserService userService) {
		this.userService = userService;
		
	}
	
	@PostMapping("/signup")
	public String signup(
		@RequestParam String username, 
		@RequestParam String password,
		@RequestParam(name = "passwordConfirm") String passwordConfirm)
	{
		
		// 비번 확인 다르면 가입X
		if (!password.equals(passwordConfirm)) {
			String err = URLEncoder.encode("パスワードが一致しません。", StandardCharsets.UTF_8);
			return "redirect:/signup?error=" + err;
		}
		
	try {
		// 1. DB에 저장하는 로직
		userService.signup(username, password);
		
		// 2. 성공 메시지 ( 인코딩하깅 )
		String msg = URLEncoder.encode("新規登録が完了しました。", StandardCharsets.UTF_8);
	
		// 3. 가입 후 로그인 페이지로 이동 + success param
		return "redirect:/login?success=" + msg;
	
		} catch (IllegalArgumentException e) {
		// 중복 ID 검증
		String err = URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8);
		return "redirect:/signup?error=" + err;
		}
	}
}