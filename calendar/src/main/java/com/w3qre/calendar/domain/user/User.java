package com.w3qre.calendar.domain.user;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
// 모든 필드에 대해 getter 자동생성함 getId(), GetUsername() 같은것 직접 안써도됨.

@NoArgsConstructor
// JPA 사용시 필수 어노테이션. Lombok이 대신 만들어줌

@Entity
// DB 테이블과 매핑되는 엔티티

@Table(name = "users") // 테이블이름 users로 지정 ( 안쓰면 클래스명 따라감 )
public class User {

	// 기본 키, DB에서 AUTO_INCREMENT로 자동증가
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	// NOT NULL, unique=중복불가, 최대 50자 => 회원 식별용 
	@Column(nullable = false, unique = true, length = 50)
	private String username;
	
	// NOT NULL, BCrypt 해시 저장용 ex) 비번 그대로 저장이아니라 BCrypt 해시로 저장됨 
	@Column(nullable = false, length = 100)
	private String password; // BCrypt 保存　（パスワード）
	
	// nullable = Null 허용인데 false 니까 NOT NULL
	@Column(nullable = false, length = 50) // 권한 ROLE_USER
	private String roles;
	
	// 생성, 수정 시간
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	// 처음 저장되기직전 자동 실행, insert 시점에 시간 자동세팅
	@PrePersist
	void prePersist() {
		this.createdAt = LocalDateTime.now();
		this.updatedAt = this.createdAt;
	}
	
	// 회원가입용 생성자=Constructor, id, 시간은 JPA가 처리, 필요한 값만 받도록
	public User(String username, String password, String roles) {
		this.username = username;
		this.password = password;
		this.roles = roles;
	}
	
//	// Lombok이 아래 getter기능을 못하고있음 지금 그래서 쓴 getter 
//	public String getUsername() { return username; }
//	public String getPassword() { return password; }
//	public String getRoles() { return roles; }
//  고쳐서 얜 이제 필요없음
	
}

// 한마디로 이 코드는 성적관리 프로그램 거기서 getter/setter 작성하고, this.math = math; 처럼 파라미터값을 필드에 넣는형태
// DB 테이블과 1:1 대응되어서 users 테이블 만들었으니까 그대로 users entity 만들어야함
