package com.example.demo.domain;

/**
 * 
 * ブランド情報を格納するエンティティ
 * 
 * @author namikitsubasa
 *
 */
public class Brand {

	/**id*/
	private Integer Id;
	/**ブランド名*/
	private String name;

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Brand [Id=" + Id + ", name=" + name + "]";
	}

}
