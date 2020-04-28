package com.example.demo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.example.demo.domain.Sale;

/**
 * 
 *salesテーブルを操作するリポジトリ
 * 
 * @author namikitsubasa
 *
 */
@Repository
public class SaleRepository {
	
	private static final RowMapper<Sale> SALE_ROW_MAPPER = (rs, i) -> {
		Sale sale=new Sale();
		sale.setId(rs.getInt("id"));
		sale.setYear(rs.getInt("year"));
		sale.setMonth(rs.getInt("month"));
		sale.setSales(rs.getInt("sales"));
		return sale;
	};

	@Autowired
	private NamedParameterJdbcTemplate template;
	
	/**
	 * 
	 * 指定した年の売上げ情報を取得する
	 * 
	 * @param year 売上年
	 * @return 売上げ情報
	 */
	public List<Sale>showSaleOfMonth(Integer year){
		String sql="select * from sales where year =:year order by year";
		SqlParameterSource param = new MapSqlParameterSource().addValue("year", year);
		List<Sale> sale = template.query(sql, param, SALE_ROW_MAPPER);
		return sale;
	}
}
