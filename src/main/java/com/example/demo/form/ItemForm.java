package com.example.demo.form;

import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * Item情報を受け取るformクラス.
 * 
 * @author namikitsubasa
 *
 */
public class ItemForm {

	/** id */
	private Integer id;
	/** 名前 */
	private String name;
	/** コンディション */
	private Integer condition;
	/** カテゴリ */
	private Integer category;
	/** ブランドid */
	private String brand;
	/** ブランド名 */
	private String brandName;
	/** 価格 */
	private String price;
	/** 発送方法 */
	private Integer shipping;
	/** 商品詳細 */
	private String description;
	/** 親カテゴリ */
	private String parentCategory;
	/** 子カテゴリ */
	private String childCategory;
	/** 孫カテゴリ */
	private String grandChildCategory;
	/** 商品画像 */
	private MultipartFile imageFile;
	/** セール価格 */
	private String salePrice;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCondition() {
		return condition;
	}

	public void setCondition(Integer condition) {
		this.condition = condition;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Integer getShipping() {
		return shipping;
	}

	public void setShipping(Integer shipping) {
		this.shipping = shipping;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

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

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public MultipartFile getimageFile() {
		return imageFile;
	}

	public void setimageFile(MultipartFile imageFile) {
		this.imageFile = imageFile;
	}

	public MultipartFile getImageFile() {
		return imageFile;
	}

	public void setImageFile(MultipartFile imageFile) {
		this.imageFile = imageFile;
	}

	public String getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(String salePrice) {
		this.salePrice = salePrice;
	}

	@Override
	public String toString() {
		return "ItemForm [id=" + id + ", name=" + name + ", condition=" + condition + ", category=" + category
				+ ", brand=" + brand + ", brandName=" + brandName + ", price=" + price + ", shipping=" + shipping
				+ ", description=" + description + ", parentCategory=" + parentCategory + ", childCategory="
				+ childCategory + ", grandChildCategory=" + grandChildCategory + ", imageFile=" + imageFile
				+ ", salePrice=" + salePrice + "]";
	}

}
