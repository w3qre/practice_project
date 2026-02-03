package com.w3qre.calendar.repository.user;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.w3qre.calendar.domain.user.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long> {
	Optional<Todo> findByUsernameAndDate(String username, LocalDate date);
}
