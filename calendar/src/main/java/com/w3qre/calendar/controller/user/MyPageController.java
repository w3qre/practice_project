package com.w3qre.calendar.controller.user;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.w3qre.calendar.service.user.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class MyPageController {

	private final UserService userService;
	
	public MyPageController (UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/mypage")
	public String mypage(
			@AuthenticationPrincipal org.springframework.security.core.userdetails.User principal,
			Model model)
	{
		model.addAttribute("username", principal.getUsername());
		return "mypage";
	}
	
	@PostMapping("/mypage/password")
	public String changePassword(
			@AuthenticationPrincipal org.springframework.security.core.userdetails.UserDetails principal,
			@RequestParam String currentPassword,
			@RequestParam String newPassword,
			@RequestParam String newPasswordConfirm)
	{
		userService.changePassword(principal.getUsername(), currentPassword, newPassword, newPasswordConfirm);
		return "redirect:/mypage?pw=ok";
	}
		
	@PostMapping("/mypage/withdraw")
	public String withdraw(
			@AuthenticationPrincipal org.springframework.security.core.userdetails.User principal,
			HttpServletRequest request,
			HttpServletResponse response)
	{
		userService.withdrawByUsername(principal.getUsername());
		new SecurityContextLogoutHandler().logout(request, response, null);
		return "redirect:/login?withdraw";
	}
}
