package com.example.demo.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.User;

@Repository
public class UserRepository {
	
	private static final RowMapper<User> USER_ROW_MAPPER = (rs, i) -> {
		User user = new User();
		user.setId(rs.getInt("id"));
		user.setName(rs.getString("name"));
		user.setEmail(rs.getString("email"));
		user.setPassword(rs.getString("password"));
		user.setAuthority(rs.getInt("authority"));
		user.setUuid(rs.getString("uuid"));
		user.setRegisterDate(rs.getDate("register_date"));
		return user;
	};
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	/**
	 * 
	 *ユーザー情報を登録 
	 * 
	 * @param user
	 */
	public void insert(String name,String password,String email,Integer authority,String uuid, Date registerDate) {
		String sql="insert into users (name,email,password,authority,uuid,register_date) values(:name,:email,:password,:authority,:uuid,:registerDate)";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name",name).addValue("email", email).addValue("password", password).addValue("authority", authority).addValue("uuid", uuid).addValue("registerDate", registerDate);
		template.update(sql, param);
		
	}
	
	public List<User> findAll() {
		String sql="select id,name,email,password,authority,uuid,register_date from users ";
		List<User> userList=template.query(sql, USER_ROW_MAPPER);
		return userList;
	}
	
	

	/**
	 * 
	 * パスワードでユーザーを検索する
	 * 
	 * @param password
	 * @return
	 */
	public User findByPassword(String password) {
		SqlParameterSource param = new MapSqlParameterSource().addValue("password", password);
		String sql="select id,name,email,password,authority,uuid,register_date from users where password=:password";
		
		try {
		User user= template.queryForObject(sql, param, USER_ROW_MAPPER);
		return user;
		}catch(Exception e) {
			return null;
		}
		
	}
		/**
		 * 
		 * メールアドレスでユーザーを検索する
		 * 
		 * @param email　メールアドレス
		 * @return　ユーザー情報
		 */
		public User findByEmail(String email) {
			SqlParameterSource param = new MapSqlParameterSource().addValue("email", email);
			String sql="select id,name,email,password,authority,uuid,register_date from users where email=:email";
			try {
				User user= template.queryForObject(sql, param, USER_ROW_MAPPER);
				return user;
			}catch(Exception e) {
				return null;
			}
		}
			
			/**
			 * 
			 * ユーザー情報を更新する
			 * 
			 * @param user
			 */
			public void update(User user) {
				SqlParameterSource param = new BeanPropertySqlParameterSource(user);
				String sql="UPDATE users set name=:name,email=:email,password=:password,authority=:authority where uuid=:uuid";
					template.update(sql, param);
	
	}
			
			/**
			 * 
			 * email検索でユーザを削除する。
			 * 
			 * @param email
			 */
			public void delete(String email) {
				SqlParameterSource param = new MapSqlParameterSource().addValue("email", email);
				String sql="delete from users where email=:email";
				template.update(sql, param);
				
			}
}
