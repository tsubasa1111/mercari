package com.example.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.domain.User;
import com.example.demo.service.UserService;

@Controller
@RequestMapping("")
public class LoginLogoutController {

	@Autowired
	private UserService userService;

	@Autowired
	HttpSession session;

	@RequestMapping("/toLogin")
	public String toLogin() {

		return "login";
	}

	@RequestMapping("/login")
	public String login(String password, Model model, RedirectAttributes redirectAttributes) {
		System.out.println(password);
		User user = userService.findUser(password);
		if (user != null) {
			session.setAttribute("userName", user.getName());
			return "redirect:/showItemList";
		} else {
			redirectAttributes.addFlashAttribute("error", "入力情報が誤っています");
			return "redirect:/toLogin";
		}

	}

}
