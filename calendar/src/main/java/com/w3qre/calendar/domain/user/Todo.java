package com.w3qre.calendar.domain.user;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table
	(name = "todo", 
	uniqueConstraints = @UniqueConstraint(name = "uk_todo_user_date", columnNames = {"username", "todo_date"}))
public class Todo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 50)
	private String username;
	
	@Column(name = "todo_date", nullable = false)
	private LocalDate date;
	
	@Column(nullable = false, length = 50)
	private String content;
	
	protected Todo() {}
	
	public Todo(String username, LocalDate date, String content) {
		this.username = username;
		this.date = date;
		this.content = content; 
	}
	
	public void setContent(String content) { this.content = content; }
	
	public String getContent() { return content; }
	public LocalDate getDate() { return date; }
}
