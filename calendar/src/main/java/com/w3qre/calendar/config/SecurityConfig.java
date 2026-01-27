package com.w3qre.calendar.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	// 비밀번호를 그대로 평문저장하면 보안위험이 있으니 BCrypt 해시로 저장하는겨
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http 
			.csrf(csrf -> csrf.disable()) // (공부용) REST 테스트 편하게 하려고 꺼둠. 나중에 켜도 됨.
			.authorizeHttpRequests(auth -> auth
					.requestMatchers("/api/users/signup").permitAll() // 회원가입 누구나

					// 로그인 정적자원허용
					.requestMatchers("/login", "/css/**", "/js/**", "/images/**").permitAll() 
					.anyRequest().authenticated() // 그외 로그인 필요
				)
				.formLogin(form -> form
						.loginPage("/login") // 기본 로그인 페이지
						.permitAll()
				)
				.logout(Customizer.withDefaults());
		
		return http.build();
	}
}


// 얘는 곧 BCrypt를 Spring이 관리하게 등록하는 설정파일임 