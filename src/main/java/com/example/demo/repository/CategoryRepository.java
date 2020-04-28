package com.example.demo.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Category;

/**
 * 
 * カテゴリテーブルを操作するリポジトリ.
 * 
 * @author namikitsubasa
 *
 */
@Repository
public class CategoryRepository {

	private static final RowMapper<Category> CATEGORY_ROW_MAPPER = (rs, i) -> {
		Category category = new Category();
		category.setId(rs.getInt("id"));
		category.setName(rs.getString("name"));
		category.setName_all(rs.getString("name_all"));
		category.setParent(rs.getInt("parent"));
		return category;
	};
	

	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * 
	 * parentカテゴリを全件取得
	 * 
	 * @return　parentカテゴリリスト
	 */
	public List<Category> findParentCategory(){
		String sql="select id,parent,name,name_all from category where parent isnull and name_all isnull order by name";
		List<Category> parentCategoryList=template.query(sql, CATEGORY_ROW_MAPPER);
		return parentCategoryList;
	}
	

	/**
	 * 
	 * parentカテゴリ名でparentカテゴリ情報取得.
	 * 
	 * @param parentSelectedName 指定されたparentカテゴリの名前
	 * @return　childカテゴリ情報
	 */
	public Category findParentCategoryByName(String parentSelectedName){
		String sql="select id,parent,name,name_all from category where parent isnull and name_all isnull and name=:parentSelectedName";
		try {
		SqlParameterSource param = new MapSqlParameterSource().addValue("parentSelectedName", parentSelectedName);
		Category parentCategory=template.queryForObject(sql,param, CATEGORY_ROW_MAPPER);
		return parentCategory;
		}catch(Exception e) {
			return null;
		}
	}
	
	/**
	 * 
	 * parentId検索によるchildカテゴリの取得
	 * 
	 * @return　childカテゴリリスト
	 */
	public List<Category> findChildCategoryByParent(Integer parent){
		String sql="select id,parent,name,name_all from category where parent is not null and name_all is null and parent=:parent order by name";
		
		try {
		SqlParameterSource param = new MapSqlParameterSource().addValue("parent", parent);
		List<Category> childCategoryList=template.query(sql,param, CATEGORY_ROW_MAPPER);
		return childCategoryList;
		}catch(Exception e) {
			return null;
		}
	}
	
	/**
	 * 
	 * childparentカテゴリ検索された際のchildeカテゴリ情報取得.
	 * 
	 * @param childSelectedName 指定されたparentカテゴリの名前
	 * @return　childカテゴリ情報
	 */
	public Category findChildCategoryByChildCategoryNameAndParentCategoryId(String childCategory,Integer parentCategoryId){
		String sql="select id,parent,name,name_all from category where parent is not null and name_all is null and name=:childSelectedName and parent=:parentCategoryId order by name";
		try{
		SqlParameterSource param = new MapSqlParameterSource().addValue("childSelectedName", childCategory).addValue("parentCategoryId", parentCategoryId);
		return template.queryForObject(sql,param, CATEGORY_ROW_MAPPER);
	}catch(Exception e) {
		return null;
	}
	}
	
	/**
	 * 
	 * 子カテゴリ名による子カテゴリの取得
	 * 
	 * @param childCategory
	 * @return
	 */
	public Category findChildCategoryByName(String childCategory){
		String sql="select id,parent,name,name_all from category where parent is not null and name_all is null and name=:childSelectedName order by name";
		try{
			SqlParameterSource param = new MapSqlParameterSource().addValue("childSelectedName", childCategory);
			return template.queryForObject(sql,param, CATEGORY_ROW_MAPPER);
		}catch(Exception e) {
			return null;
		}
	}
	
	/**
	 * 
	 * chiledCategoryId検索によるGrandChildカテゴリの取得
	 * 
	 * @return　GrandChildカテゴリリスト
	 */
	public List<Category> findGrandChildCategoryBychildCategoryId(Integer childCategoryId){
		String sql="select id,parent,name,name_all from category where name_all is not null and parent=:childCategoryId order by name";
		SqlParameterSource param = new MapSqlParameterSource().addValue("childCategoryId", childCategoryId);
		List<Category> grandChildCategoryList=template.query(sql,param, CATEGORY_ROW_MAPPER);
		return grandChildCategoryList;
	}
	
	
	/**
	 * 
	 * 選択したgrandchildカテゴリの情報.
	 * 
	 * @param grandChildSelectedName 選択されたgrandchildの名前
	 * @param parent
	 * @return　選択したgrandchildカテゴリの情報
	 */
	public Category findGrandChildCategoryByName(String grandChildSelectedName,Integer parent){
		String sql="select id,parent,name,name_all from category where  name_all is not null and name=:grandChildSelectedName and parent=:parent order by name";
		try {
		SqlParameterSource param = new MapSqlParameterSource().addValue("grandChildSelectedName", grandChildSelectedName).addValue("parent", parent);
		Category grandChildCategory=template.queryForObject(sql,param, CATEGORY_ROW_MAPPER);
		return grandChildCategory;
		}catch(Exception e) {
			return null;
		}
	}

	/**
	 * parentカテゴリに属する全てのgrandchildカテゴリを検索
	 * 
	 * @param name parentカテゴリ名
	 * @return grandchildカテゴリ情報
	 */
	public List<Category> findGrandChildCategoryIdByParentName(String parentCategory){
		String sql="select id,parent,name,name_all from category where name_all is not null and name_all like :name order by id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", parentCategory+"%");
		List<Category> grandChildCategoryList=template.query(sql,param, CATEGORY_ROW_MAPPER);
		return grandChildCategoryList;
	}
	
	/**
	 * 
	 * 子カテゴリのidでgrandchildカテゴリを検索する
	 * 
	 * @param childCategoryId
	 * @return grandchildカテゴリ情報
	 */
	public List<Category> findGrandChildCategoryByChildCategoryId(Integer childCategoryId){
		String sql="select id,parent,name,name_all from category where name_all is not null and parent =:childCategoryId";
		SqlParameterSource param = new MapSqlParameterSource().addValue("childCategoryId", childCategoryId);
		return template.query(sql,param, CATEGORY_ROW_MAPPER);
	}
	
	/**
	 * name_allでカテゴリ検索
	 * 
	 * @param nameAll
	 * @return
	 */
	public Category load(String nameAll){
		String sql="select id,parent,name,name_all from category where name_all=:nameAll";
		SqlParameterSource param = new MapSqlParameterSource().addValue("nameAll", nameAll);
		Category parentCategory=template.queryForObject(sql,param, CATEGORY_ROW_MAPPER);
		return parentCategory;
	}
	
	/**
	 * 
	 * parentカテゴリを登録する
	 * 
	 * @param parentCategory
	 * @return
	 */
	public Category insertParentCategory(String parentCategory) {
		String sql="insert into category(name) values(:name) returning id,name,name_all,parent";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", parentCategory);
		return template.queryForObject(sql,param, CATEGORY_ROW_MAPPER);
	}
	
	
	/**
	 * 
	 * childCategoryを登録する
	 * 
	 * @param childCategory
	 * @param parent
	 * @return
	 */
	public Category insertChildCategory(String childCategory,Integer parent) {
		String sql="insert into category(parent,name) values(:parent,:name) returning id,name,name_all,parent";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", childCategory).addValue("parent", parent);
		return template.queryForObject(sql,param, CATEGORY_ROW_MAPPER);
	}
	
	
	/**
	 * grandChildCategoryを登録する
	 * 
	 * @param grandChildCategory
	 * @param parent
	 * @return
	 */
	public Category insertGrandChildCategory(String grandChildCategory,Integer parent,String nameAll) {
		String sql="insert into category(parent,name,name_all) values(:parent,:name,:name_all) returning id,name,name_all,parent";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", grandChildCategory).addValue("parent", parent).addValue("name_all", nameAll);
		return template.queryForObject(sql,param, CATEGORY_ROW_MAPPER);
	}
	
	/**
	 * 
	 * 親カテゴリを変更する
	 * 
	 * @param oldParentCategory 登録中の名前
	 * @param newParentCategory　変更後の名前
	 */
	public void updateParentCategory(String oldParentCategory,String newParentCategory) {
		String sql="update category set name=:newname where parent isnull and name_all isnull and name=:oldname";
		SqlParameterSource param = new MapSqlParameterSource().addValue("newname", newParentCategory).addValue("oldname", oldParentCategory);
		template.update(sql, param);
	}
	
	/**
	 * 
	 * 子カテゴリを変更する
	 * 
	 * @param oldParentCategory 登録中の名前
	 * @param newParentCategory　変更後の名前
	 */
	public void updateChildCategory(String oldChildCategory,String newChildCategory) {
		String sql="update category set name=:newname where parent is not null and name_all isnull and name=:oldname";
		SqlParameterSource param = new MapSqlParameterSource().addValue("newname", newChildCategory).addValue("oldname", oldChildCategory);
		template.update(sql, param);
	}
	
	/**
	 * 
	 * 孫カテゴリを変更する
	 * 
	 * @param oldParentCategory 登録中の名前
	 * @param newParentCategory　変更後の名前
	 */
	public void updateGrandParentCategory(String oldGrandChildCategory,String newGrandChildCategory) {
		String sql="update category set name=:newname where parent is not null and name_all is not null and name=:oldname";
		SqlParameterSource param = new MapSqlParameterSource().addValue("newname", newGrandChildCategory).addValue("oldname", oldGrandChildCategory);
		template.update(sql, param);
	}
	
	/**
	 * 
	 * 指定した親カテゴリに属する子、孫カテゴリを削除する。(親も削除)
	 * 
	 * @param parentCategory
	 */
	public void deleteParentAndChildAndGrandChildCategory(String parentCategory) {
		String sql="with deleted as(delete from category where name=:parentCategory and parent is null and name_all is null returning id) delete from category where parent in(select id from deleted) or name_all like :likeName;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("parentCategory", parentCategory).addValue("likeName", parentCategory+"%");
		template.update(sql, param);
	}
	
	/**
	 * 
	 * 指定された子カテゴリに属する孫カテゴリを削除(孫も含む)
	 * 
	 * @param childwCategory
	 */
	public void deleteChildAndGrandChildCategory(String childCategory,Integer parentCategoryId) {
		String sql="with deleted as (delete from category where name=:childCategory and parent=:parentCategoryId and parent is not null and name_all isnull returning id) delete from category where parent in(select id from deleted)";
		SqlParameterSource param = new MapSqlParameterSource().addValue("childCategory", childCategory).addValue("parentCategoryId", parentCategoryId);
		template.update(sql, param);
	}
	
	/**
	 * 
	 * 指定された孫カテゴリを削除する
	 * 
	 * @param grandChildwCategory
	 */
	public void deleteGrandChildCategoryByNameAll(String nameAll) {
		String sql="delete from category where name_all=:nameAll";
		SqlParameterSource param = new MapSqlParameterSource().addValue("nameAll", nameAll);
		template.update(sql, param);
	}
	
	
}
