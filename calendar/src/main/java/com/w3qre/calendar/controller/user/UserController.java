package com.w3qre.calendar.controller.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.w3qre.calendar.service.user.UserService;

@RestController // JSON을 반환  > @Controller는 HTML 반환하는 것 : ex)Thymeleaf
@RequestMapping("/api/users") // 이 컨트롤러의 기본 URL prefix -> 공통 URL 앞부분 만들기 그래서 /api/users/signup이 기본 url이됨
public class UserController {
	
	private final UserService userService; // 서비스 주입 
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping("/signup") // POST /api/users/signup
	public ResponseEntity<?> signup(@RequestBody SignupRequest req) { // JSON을 req로 받기
		Long id = userService.signup(req.getUsername(), req.getPassword()); // 서비스를 호출해서 DB에 저장하고 id를 받음
		return ResponseEntity.ok(id); // 성공시 저장된 id 반환
	}
	
	// 컨트롤러 내부에서 만 쓸 회원가입 요청 -> 이 요청이 이 컨트롤러에서만 쓰이기때문에 DTO가 더 많아지면 따로 분리하는게 편함
	static class SignupRequest {
		private String username; // JSON의 username
		private String password; // JSON의 password
		
		public String getUsername() { return username; } // JSON 바인딩용 getter
		public String getPassword() { return password; } // JSON 바인딩용 getter
		
		public void setUsername(String username) { this.username = username; } // JSON 바인딩용 setter
		public void setPassword(String password) { this.password = password; } // JSON 바인딩용 setter
	}

}

// JSON = JavaScript Object Notation = 데이터 주고받는 표준 텍스트 형식