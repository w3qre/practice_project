package com.w3qre.calendar.service.user;

import java.util.Arrays;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.w3qre.calendar.domain.user.User;
import com.w3qre.calendar.repository.user.UserRepository;

@Service // 스프링 Bean 등록 ( Security가 이걸 찾아서 씀 )
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository; // DB 조회용
	
	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
		
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// 로그인 시도한 username으로 users 테이블에서 조회
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
	
	
	// Spring Security가 이해하는 UserDetails 형태로 변환후 반환
	return org.springframework.security.core.userdetails.User
			.withUsername(user.getUsername())
			.password(user.getPassword())
			.authorities(
					Arrays.stream(user.getRoles().split(","))
					.map(String::trim)
					.map(SimpleGrantedAuthority::new)
					.toList()
			)
			.disabled(user.isDeleted())
			.build();
	}

}
