package com.w3qre.calendar.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

	// 비밀번호를 그대로 평문저장하면 보안위험이 있으니 BCrypt 해시로 저장하는겨
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}

// 얘는 곧 BCrypt를 Spring이 관리하게 등록하는 설정파일임 