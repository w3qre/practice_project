package com.w3qre.calendar.service.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.w3qre.calendar.domain.user.User;
import com.w3qre.calendar.repository.user.UserRepository;

import jakarta.transaction.Transactional;

@Service // Spring에 service담당 이라고 선언하는것
@Transactional // 메서드 실행 중 오류 발생시 DB 작업 전체 롤백
public class UserService {

	private final UserRepository userRepository; // user table 접근용
	private final PasswordEncoder passwordEncoder; // BCrpyt 비번 암호화용
	
	// 생성자 주입 : Spring이 userRepository, passwordEncoder를 넣어줌 ( 자동객체삽입 )
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository; // Repository 주입
		this.passwordEncoder = passwordEncoder; // BCrypt Encode 주입
	}
	
	// 회원가입 기능 메소드 メソッド
	public Long signup (String username,String password) {
		
		// 1. username 중복체크 기능
		if (userRepository.findByUsername(username).isPresent()) {
			throw new IllegalArgumentException ("既に存在するＩＤです。");
			
		}
		
		// 2. 비밀번호 BCrypt 해시처리
		String hashed = passwordEncoder.encode(password);
		
		// 3. User Entity 생성 + 저장
		User user = new User(username, hashed);
		
		// 4. DB에 저장 (insert 실행)
		User saved = userRepository.save(user);
		
		// 저장된 사용자 id 반환
		return saved.getId();
	}
	
}


//UserService는 회원가입 로직을 담당
//비밀번호를 BCrypt로 암호화한 뒤 users 테이블에 저장하는 역할
