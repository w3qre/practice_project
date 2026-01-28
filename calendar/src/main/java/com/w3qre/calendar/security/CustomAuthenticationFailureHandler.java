package com.w3qre.calendar.security;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(
			HttpServletRequest request,
			HttpServletResponse response,
			AuthenticationException exception
			) throws IOException, ServletException {
	
		String errorMessage = "ログインに失敗しました。";
		
		if (exception instanceof BadCredentialsException) {
			errorMessage = "入力されたIDは登録されていません。";
		} else if (exception instanceof BadCredentialsException) {
			errorMessage = "パスワードが正しくありません。";
		}
		
		// 일본어때문에
		String encodedMessage = URLEncoder.encode(errorMessage, StandardCharsets.UTF_8);
		// 에러 메시지를 쿼리 파라미터로 전달
		response.sendRedirect("/login?error=" + encodedMessage);
	}

			
}
