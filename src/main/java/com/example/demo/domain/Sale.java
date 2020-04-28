package com.example.demo.domain;

/**
 * 
 * 売上げ情報を格納するエンティティ.
 * 
 * @author namikitsubasa
 *
 */
public class Sale {

	/**id*/
	private Integer id;
	/**売上年*/
	private Integer year;
	/**売上月*/
	private Integer month;
	/**売上高*/
	private Integer sales;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getSales() {
		return sales;
	}

	public void setSales(Integer sales) {
		this.sales = sales;
	}

	@Override
	public String toString() {
		return "Sale [id=" + id + ", year=" + year + ", month=" + month + ", sales=" + sales + "]";
	}

}
