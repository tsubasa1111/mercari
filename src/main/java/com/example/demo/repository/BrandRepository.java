package com.example.demo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Brand;

/**
 * 
 * brandsテーブルを操作するリポジトリ.
 * 
 * @author namikitsubasa
 *
 */
@Repository
public class BrandRepository {
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	private static final RowMapper<Brand> ITEM_ROW_MAPPER = (rs, i) -> {
		Brand brand = new Brand();
		brand.setId(rs.getInt("id"));
		brand.setName(rs.getString("name"));
		return brand;
	};
	
	/**
	 * 
	 * ブランド名でブランド情報を取得する/ブランド情報がなければ登録して情報を取得.
	 * 
	 * @param id 
	 * @return ブランド情報
	 */
	public Brand loadOrInsert(String name) {
		String selectSql="select id,name from brands where name=:name";
		String insertSql="insert into brands (name) values(:name) returning id,name";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", name);
		try{
		Brand brand =template.queryForObject(selectSql, param, ITEM_ROW_MAPPER);
		return  brand;
		}catch(Exception e) {
			System.out.println(name);
		Brand brand =template.queryForObject(insertSql, param, ITEM_ROW_MAPPER);
		return brand;
		}
	}

}
