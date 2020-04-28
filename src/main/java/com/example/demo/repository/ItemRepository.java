package com.example.demo.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Item;

/**
 * 
 * アイテムテーブルを操作するレポジトリ.
 * 
 * @author namikitsubasa
 *
 */
@Repository
public class ItemRepository {

	private static final RowMapper<Item> ITEM_ROW_MAPPER = (rs, i) -> {
		Item item = new Item();
		item.setId(rs.getInt("i_id"));
		item.setName(rs.getString("i_name"));
		item.setCondition(rs.getInt("condition"));
		item.setCategory(rs.getInt("category"));
		item.setBrand(rs.getString("brand"));
		item.setPrice(rs.getInt("price"));
		item.setShipping(rs.getInt("shipping"));
		item.setDescription(rs.getString("description"));
		item.setNameAll(rs.getString("name_all"));
		item.setBrandName(rs.getString("b_name"));
		item.setImage(rs.getString("image"));
		item.setSalePrice(rs.getInt("sale_price"));
		return item;
	};

	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * 
	 * 全件検索(価格の昇順、名前順)
	 * 
	 * @param offset     検索開始位置
	 * @param searchName 商品名
	 * @param brand      ブランド名
	 * @return 商品一覧
	 */
	public List<Item> findAll(Integer offset, String searchName, String brand, Integer limit) {
		String sql = "select i.id i_id,i.name i_name,condition,category,brand,price,shipping,description,name_all,image,sale_price,b.name b_name from items i inner join category c on i.category=c.id inner join brands b on cast(i.brand as Integer)=b.id  where i.name ilike :searchName and cast(i.brand as Integer) in (select id from brands where name ilike :brand) order by price,i.name limit :limit offset :offset;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("offset", offset)
				.addValue("searchName", "%" + searchName + "%").addValue("brand", "%" + brand + "%")
				.addValue("limit", limit);
		List<Item> itemList = template.query(sql, param, ITEM_ROW_MAPPER);
		return itemList;
	}

	/**
	 * 
	 * 親カテゴリで商品を検索する
	 * 
	 * @param parentCategory
	 * @return
	 */
	public List<Item> findByParentCategoryName(String parentCategoryName, Integer offset, Integer limit,
			String searchedName, String brand) {
		String sql = "select i.id i_id,i.name i_name,condition,category,brand,price,shipping,description,name_all,image,sale_price,b.name b_name from items i inner join category c on i.category=c.id inner join brands b on cast(i.brand as Integer)=b.id where i.name ilike :searchedName and cast(i.brand as Integer) in (select id from brands where name ilike :brand) and category  in(select id from category where name_all ilike :parentCategoryName) order by price,i.name limit :limit offset :offset";
		SqlParameterSource param = new MapSqlParameterSource().addValue("parentCategoryName", parentCategoryName + "%")
				.addValue("offset", offset).addValue("limit", limit).addValue("searchedName", "%" + searchedName + "%")
				.addValue("brand", "%" + brand + "%");
		List<Item> itemList = template.query(sql, param, ITEM_ROW_MAPPER);
		return itemList;
	}

	/**
	 * 
	 * 子カテゴリで商品を検索する
	 * 
	 * @param parentCategory
	 * @return
	 */
	public List<Item> findByGrandChildCategoryBYChildName(String nameAll, Integer offset, Integer limit,
			String searchedName, String brand) {
		String sql = "select i.id i_id,i.name i_name,condition,category,brand,price,shipping,description,name_all,image,sale_price,b.name b_name from items i inner join category c on i.category=c.id inner join brands b on cast(i.brand as Integer)=b.id where i.name ilike :searchedName and cast(i.brand as Integer) in (select id from brands where name ilike :brand) and category  in(select id from category where name_all ilike :nameAll) order by price,i.name limit :limit offset :offset";
		SqlParameterSource param = new MapSqlParameterSource().addValue("nameAll", nameAll + "%")
				.addValue("offset", offset).addValue("limit", limit).addValue("searchedName", "%" + searchedName + "%")
				.addValue("brand", "%" + brand + "%");
		List<Item> itemList = template.query(sql, param, ITEM_ROW_MAPPER);
		return itemList;
	}

	/**
	 * 
	 * 孫カテゴリで商品を検索する
	 * 
	 * @param category   カテゴリ番号(単数カテゴリ)
	 * @param offset
	 * @param searchName 商品名
	 * @param brand      ブランド名
	 * @return カテゴリ、ブランド、商品名指定での検索
	 */
	public List<Item> findByGrandChildNameAndNameAndBrand(Integer category, Integer offset, String searchName,
			String brand, Integer limit) {
		String sql = "select i.id i_id,i.name i_name,condition,category,brand,price,shipping,description,name_all,image,sale_price,b.name b_name from items i inner join category c on i.category=c.id inner join brands b on cast(i.brand as Integer)=b.id where category=:category and i.name ilike :searchName and cast(i.brand as Integer) in (select id from brands where name ilike :brand) order by price,i.name limit :limit offset :offset;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("category", category).addValue("offset", offset)
				.addValue("searchName", "%" + searchName + "%").addValue("brand", "%" + brand + "%")
				.addValue("limit", limit);
		List<Item> itemList = template.query(sql, param, ITEM_ROW_MAPPER);
		return itemList;
	}


	/**
	 * 
	 * idで商品を検索する
	 * 
	 * @param id 商品id
	 * @return
	 */
	public Item load(Integer id) {
		String sql = "select i.id i_id,i.name i_name,condition,category,brand,price,shipping,description,name_all,image,sale_price,b.name b_name from items i inner join category c  on i.category=c.id  inner join brands b on cast(i.brand as Integer)=b.id where i.id=:id";
		try {
			SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
			Item item = template.queryForObject(sql, param, ITEM_ROW_MAPPER);
			return item;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 
	 * sale商品検索
	 * 
	 * @param offset
	 * @param limit
	 * @return
	 */
	public List<Item> findAllOfselItem() {
		String sql = "select i.id i_id,i.name i_name,condition,category,brand,price,shipping,description,name_all,image,sale_price,b.name b_name from items i inner join category c on i.category=c.id inner join brands b on cast(i.brand as Integer)=b.id  where sale_price >=1 order by price,i.name";
		SqlParameterSource param = new MapSqlParameterSource();
		List<Item> itemList = template.query(sql, param, ITEM_ROW_MAPPER);
		return itemList;
	}

	/**
	 * 
	 * 新商品をinsertする
	 * 
	 * @param item itemオブジェクト
	 * @return
	 */
	public void insert(Item item) {
		String sql = "insert into items (name,condition,category,brand,price,shipping,description,image) values(:name,:condition,:category,:brand,:price,:shipping,:description,:image) ";
		SqlParameterSource param = new BeanPropertySqlParameterSource(item);
		template.update(sql, param);
	}

	/**
	 * 
	 * 商品をupdateする
	 * 
	 * @param item itemオブジェクト
	 */
	public void update(Item item) {
		String sql = "update items set name=:name,condition=:condition,category=:category,brand=:brand,price=:price,shipping=:shipping,description=:description,sale_price=:salePrice where id=:id";
		SqlParameterSource param = new BeanPropertySqlParameterSource(item);
		template.update(sql, param);
	}

}
