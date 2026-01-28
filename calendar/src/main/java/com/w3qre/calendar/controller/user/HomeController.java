package com.w3qre.calendar.controller.user;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

	@GetMapping("/")
	public String home(
			@RequestParam(required = false) String ym, // 예: 2026-01
			Authentication authentication, Model model) {
		
		// 로그인한 사용자 = username
		String username = authentication.getName();
		model.addAttribute("username", username);
		
		YearMonth currentYm = (ym == null || ym.isBlank())
				? YearMonth.now()
				: YearMonth.parse(ym);
		
		// 달력 시작일
		LocalDate firstDayOfMonth = currentYm.atDay(1);
		LocalDate start = firstDayOfMonth;
		while (start.getDayOfWeek() != DayOfWeek.SUNDAY) {
			start = start.minusDays(1);
		}
		
		List<LocalDate> days = new ArrayList<>();
		for (int i = 0; i < 42; i++) {
			days.add(start.plusDays(i));
		}
		
		model.addAttribute("currentYm", currentYm); 
		System.out.println("DEBUG currentYm=" + currentYm); // 체크용
		model.addAttribute("prevYm", currentYm.minusMonths(1)); 
		model.addAttribute("nextYm", currentYm.plusMonths(1)); 
		model.addAttribute("days", days); 
		model.addAttribute("today", LocalDate.now());  
		
		return "home"; // templates/home.html
	}
	
	
}
