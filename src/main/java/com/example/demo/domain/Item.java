package com.example.demo.domain;

/**
 * 
 * アイテムテーブルの情報を格納するエンティティ.
 * 
 * @author namikitsubasa
 *
 */
public class Item {

	/** id */
	private Integer id;
	/** 名前 */
	private String name;
	/** コンディション */
	private Integer condition;
	/** カテゴリ */
	private Integer category;
	/** ブランド */
	private String brand;
	/** 価格 */
	private Integer price;
	/** 発送方法 */
	private Integer shipping;
	/** 商品詳細 */
	private String description;
	/** 商品画像 */
	private String image;
	/** カテゴリ名 */
	private String nameAll;
	/** ブランド名 */
	private String brandName;
	/** セール価格 */
	private Integer salePrice;

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

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
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

	public String getNameAll() {
		return nameAll;
	}

	public void setNameAll(String nameAll) {
		this.nameAll = nameAll;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Integer salePrice) {
		this.salePrice = salePrice;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", name=" + name + ", condition=" + condition + ", category=" + category + ", brand="
				+ brand + ", price=" + price + ", shipping=" + shipping + ", description=" + description + ", image="
				+ image + ", nameAll=" + nameAll + ", brandName=" + brandName + ", salePrice=" + salePrice + "]";
	}

}
