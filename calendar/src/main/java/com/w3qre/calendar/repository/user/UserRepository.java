package com.w3qre.calendar.repository.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.w3qre.calendar.domain.user.User;

public interface UserRepository extends JpaRepository<User, Long> {

	// username으로 사용자 조회
	Optional<User> findByUsername(String username);
	
}

// interface 로 쓰는이유 : Spring Data JPA가 구현체를 자동 생성하기 때문에 규칙만 선언해줘도 됨
// 실행 시점에 프록시 객체가 붙음 