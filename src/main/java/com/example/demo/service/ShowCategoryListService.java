package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Category;
import com.example.demo.repository.CategoryRepository;

/**
 * 
 * カテゴリテーブルを操作するサービスクラス.
 * 
 * @author namikitsubasa
 *
 */
@Service
public class ShowCategoryListService {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	/**
	 * 
	 * parentカテゴリを取得
	 * 
	 * @return　parentカテゴリリスト
	 */
	public List<Category> findParentCategory(){
		return categoryRepository.findParentCategory();
	}
	
	/**
	 * 
	 * parentカテゴリ名によるparentカテゴリの取得
	 * 
	 * @return　parentカテゴリリスト
	 */
	public Category findParentCategory(String parentSelectedName){
		return categoryRepository.findParentCategoryByName(parentSelectedName);
	}
	

	/**
	 * 
	 * childカテゴリ名とparentIdでchildカテゴリを検索
	 * 
	 * @param childCategory
	 * @param parent
	 * @return
	 */
	public Category findChildCategoryByChildCategoryNameAndParentCategoryId(String childCategory,Integer parentCategoryId){
		return categoryRepository.findChildCategoryByChildCategoryNameAndParentCategoryId(childCategory,parentCategoryId);
	}
	
	/**
	 * 
	 * childparentカテゴリ名によるchildparentカテゴリの取得
	 * 
	 * @return　parentカテゴリリスト
	 */
	public Category findChildCategory(String childCategory){
		return categoryRepository.findChildCategoryByName(childCategory);
	}
	
	/**
	 * 
	 * parentカテゴリ名によるchildparentカテゴリの取得
	 * 
	 * @param parent
	 * @return childparentカテゴリリスト
	 */
	public List<Category> findChildCategoryByParent(Integer parent){
		return categoryRepository.findChildCategoryByParent(parent);
	}
	
	/**
	 * 
	 * parentカテゴリ名によるgrandchildparentカテゴリの取得
	 * 
	 * @param parent
	 * @return　grandchildparentカテゴリリスト
	 */
	public List<Category> findGrandChildCategoryBychildCategoryId(Integer childCategoryId){
		return categoryRepository.findGrandChildCategoryBychildCategoryId(childCategoryId);
	}
	
	/**
	 * 
	 * parentカテゴリに属する全てのgrandchildカテゴリを検索
	 * 
	 * @param name parentカテゴリ名
	 * @return grandchildカテゴリ情報
	 */
	public List<Category> showGrandChildCategoryIdByParentName(String name){
		return categoryRepository.findGrandChildCategoryIdByParentName(name);
	}

	/**
	 * 
	 * grandChildカテゴリ名によるgrandchildparentカテゴリの取得
	 * 
	 * @return　parentカテゴリリスト
	 */
	public Category findGrandChildCategory(String grandChildSelectedName,Integer parent){
		return categoryRepository.findGrandChildCategoryByName(grandChildSelectedName, parent);
	}
	
	/**
	 * 
	 * name_allで商品検索.
	 * 
	 * @param nameAll
	 * @return
	 */
	public Category showCategoryByNameAll(String nameAll){
		return categoryRepository.load(nameAll);
	}
	
	
}
