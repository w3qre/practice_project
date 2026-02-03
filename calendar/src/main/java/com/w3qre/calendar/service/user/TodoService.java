package com.w3qre.calendar.service.user;

import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.w3qre.calendar.domain.user.Todo;
import com.w3qre.calendar.repository.user.TodoRepository;

@Service
@Transactional
public class TodoService {
	private final TodoRepository todoRepository;
	public TodoService(TodoRepository todoRepository) {
		this.todoRepository = todoRepository;
	}	
	
	@Transactional(readOnly = true)
	public String getContent(String username, LocalDate date) {
		return todoRepository.findByUsernameAndDate(username, date)
				.map(Todo::getContent)
				.orElse("");
	}
	
	public String saveOrDelete(String username, LocalDate date, String content) {
		String trimmed = content == null ? "" : content.trim();
	
	// 내용을 입력하지 않고 저장 => 삭제
	if (trimmed.isEmpty()) {
		todoRepository.findByUsernameAndDate(username, date)
		.ifPresent(todoRepository::delete);
	return "";
	}
	
	// 50자 제한
	String limited = (trimmed.length() > 50) ? trimmed.substring(0,50) : trimmed;
	
	// 내용 있다면 수정, 없었으면 생성
	Todo todo = todoRepository.findByUsernameAndDate(username, date)
			.orElseGet(() -> new Todo(username, date, limited));
	
	todo.setContent(limited);
	todoRepository.save(todo);
	
	return limited;
	}
}
	

