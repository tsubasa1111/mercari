package com.example.demo.controller;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.domain.User;
import com.example.demo.form.UserForm;
import com.example.demo.service.UserService;

/**
 * 
 * ユーザー情報をコントロールするコントローラ.
 * 
 * @author namikitsubasa
 *
 */
@Controller
@RequestMapping("")
public class UserRegisterOrEditOrDeleteController {
	
	@Autowired
	private UserService userService;
	
	@ModelAttribute
	public UserForm setUpUserForm() {
		return new UserForm();
	}
	
	
	
	/**
	 * 
	 * ユーザー情報に画面遷移する
	 * 
	 * @return
	 */
	@RequestMapping("/toRegister")
	public String toRegister() {
		return "register";
	}
	
	
	/**
	 * 
	 * ユーザー情報を登録する
	 * 
	 * @param form 入力情報
	 * @param resultset　エラー情報を格納
	 * @return ログイン画面に遷移
	 */
	@RequestMapping("/register")
	public String register(@Validated UserForm form,BindingResult resultset) {
		
		if(resultset.hasErrors()) {
			return toRegister();
		}
		UUID num = UUID.randomUUID();
		String uuid = num.toString();
		
		if(StringUtils.isEmpty(form.getAuthority())){
			form.setAuthority("0");
		}
		
		LocalDateTime ldt = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String format = ldt.format(formatter);
		Date date = Date.valueOf(format);
		
		userService.registerUser(form.getName(),form.getPassword(),form.getEmail(),Integer.parseInt(form.getAuthority()),uuid,date);
		return "redirect:toLogin";
	}
	
	
	/**
	 * 
	 * 編集するユーザー検索画面に遷移
	 * 
	 * @return　編集するユーザー検索画面
	 */
	@RequestMapping("/toSearchEditUser")
	public String toSearchEditUser() {

		return "toSearchEditUser";
	}
	
	/**
	 * 
	 * ユーザー編集画面に遷移
	 * 
	 * @param form ユーザー情報
	 * @param model
	 * @return ユーザー編集画面
	 */
	@RequestMapping("/toUserEdit")
	public String toUserEdit(UserForm form,Model model) {
		User user=userService.findUserByEmail(form.getEmail());
		
		if(StringUtils.isEmpty(user)) {
			model.addAttribute("userError","該当のユーザーがいませんでした。");
			return  "/toSearchEditUser";
		}
		model.addAttribute("name",user.getName());
		model.addAttribute("email",user.getEmail());
		model.addAttribute("password",user.getPassword());
		model.addAttribute("authority",user.getAuthority());
		model.addAttribute("uuid",user.getUuid());
		
		return "userEdit";
	}
	
	
	/**
	 * 
	 * ユーザー情報を編集する
	 * 
	 * @param form　ユーザー情報
	 * @return　編集するユーザー検索画面に遷移
	 */
	@RequestMapping("/edit")
	public String edit(UserForm form) {
		
		if(StringUtils.isEmpty(form.getAuthority())){
			form.setAuthority("0");
		}
		User user = new User();
		BeanUtils.copyProperties(form, user);
		user.setAuthority(Integer.parseInt(form.getAuthority()));
		userService.updateUser(user);
		return "redirect:/toSearchEditUser";
	}
	
	
	
	
	@RequestMapping("/toDeleteUser")
	public String toDeleteUser() {
		return "deleteUser";
	}
	
	
	@RequestMapping("/delete")
	public String deleteUser(UserForm form,RedirectAttributes redirectAttributes) {
		System.out.println("aaa");
		if(userService.findUserByEmail(form.getEmail())==null){
			System.out.println("bbb");
			redirectAttributes.addFlashAttribute("userError", "ユーザーが見つかりませんでした");
		}else {
		userService.deleteUser(form.getEmail());
		redirectAttributes.addFlashAttribute("user", "ユーザーを削除しました");
		}
		
		return "redirect:toDeleteUser";
	}

}
