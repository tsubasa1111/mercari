package com.example.demo.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserForm {

	/** 名前 */
	@NotBlank(message = "お名前を入力してください")
	private String name;
	/** メールアドレス */
	@Email(message = "メールアドレスが有効ではありません")
	@NotBlank(message = "メールアドレスを入力してください")
	private String email;
	/** パスワード */
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{6,32}$", message = "大文字、小文字、半角数字それぞれ含める必要があります。")
	@Size(min = 8, max = 32, message = "8文字以上32文字以内で設定する必要があります。")
	private String password;
	/** 権限 */
	private String authority;
	/** uuid */
	private String uuid;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserForm [name=" + name + ", email=" + email + ", password=" + password + ", authority=" + authority
				+ ", uuid=" + uuid + "]";
	}

}
