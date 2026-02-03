package com.w3qre.calendar.controller.user;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.w3qre.calendar.service.user.TodoService;

@RestController
@RequestMapping("/api/todo")
public class TodoApiController {
	
	private final TodoService todoService;
	
	public TodoApiController(TodoService todoService) {
		this.todoService = todoService;
	}
	
	@GetMapping
	public Map<String, Object> get(@RequestParam String date,
		@AuthenticationPrincipal UserDetails user) {
		
		LocalDate d = LocalDate.parse(date);
		String content = todoService.getContent(user.getUsername(), d);
		return Map.of("date", date, "content", content);
	}
	
	@PostMapping
	public Map<String, Object> save(@RequestBody TodoSaveRequest req,
		@AuthenticationPrincipal UserDetails user) {
		
		LocalDate d = LocalDate.parse(req.date());
		String saved = todoService.saveOrDelete(user.getUsername(), d, req.content());
		return Map.of("date", req.date(), "content", saved);
	}
	
	public record TodoSaveRequest(String date, String content) {}
}
