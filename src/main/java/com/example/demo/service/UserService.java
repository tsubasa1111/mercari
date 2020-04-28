package com.example.demo.service;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	/**
	 * 
	 * ユーザーを登録する
	 * 
	 * @param name　名前
	 * @param passowrd　パスワード
	 * @param email　メールアドレス
	 * @param authority　権限
	 * @param uuid　uuid
	 * @param registerDate 登録日
	 */
	public void registerUser(String name,String passowrd,String email,Integer authority,String uuid, Date registerDate) {
		userRepository.insert(name, passowrd, email, authority, uuid, registerDate);
	}
	
	/**
	 * 
	 * パスワードでユーザー検索
	 * 
	 * @param passowrd パスワード
	 * @return
	 */
	public User findUser(String passowrd) {
		return userRepository.findByPassword(passowrd);
	}
	
	/**
	 * 
	 * メールアドレスでユーザーを検索する
	 * 
	 * @param email　メールアドレス
	 * @return　ユーザー情報
	 */
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	/**
	 * 
	 * ユーザー情報を更新する
	 * 
	 * @param user ユーザー情報
	 */
	public void updateUser(User user) {
		userRepository.update(user);
	}
	
	/**
	 * 
	 * ユーザーを削除する
	 * 
	 * @param email
	 */
	public void deleteUser(String email) {
		userRepository.delete(email);
	}
}
