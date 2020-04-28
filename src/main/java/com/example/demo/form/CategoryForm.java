package com.example.demo.form;

/**
 * 
 * カテゴリ登録画面から送られてくるリクエストパラメータを格納するフォームクラス.
 * 
 * @author namikitsubasa
 *
 */
public class CategoryForm {

	private String parentCategory;
	private String childCategory;
	private String grandChildCategory;
	private String newParentCategory;
	private String newChildCategory;
	private String newGrandChildCategory;

	public String getParentCategory() {
		return parentCategory;
	}

	public void setParentCategory(String parentCategory) {
		this.parentCategory = parentCategory;
	}

	public String getChildCategory() {
		return childCategory;
	}

	public void setChildCategory(String childCategory) {
		this.childCategory = childCategory;
	}

	public String getGrandChildCategory() {
		return grandChildCategory;
	}

	public void setGrandChildCategory(String grandChildCategory) {
		this.grandChildCategory = grandChildCategory;
	}

	public String getNewParentCategory() {
		return newParentCategory;
	}

	public void setNewParentCategory(String newParentCategory) {
		this.newParentCategory = newParentCategory;
	}

	public String getNewChildCategory() {
		return newChildCategory;
	}

	public void setNewChildCategory(String newChildCategory) {
		this.newChildCategory = newChildCategory;
	}

	public String getNewGrandChildCategory() {
		return newGrandChildCategory;
	}

	public void setNewGrandChildCategory(String newGrandChildCategory) {
		this.newGrandChildCategory = newGrandChildCategory;
	}

	@Override
	public String toString() {
		return "CategoryForm [parentCategory=" + parentCategory + ", childCategory=" + childCategory
				+ ", grandChildCategory=" + grandChildCategory + ", newParentCategory=" + newParentCategory
				+ ", newChildCategory=" + newChildCategory + ", newGrandChildCategory=" + newGrandChildCategory + "]";
	}

}
