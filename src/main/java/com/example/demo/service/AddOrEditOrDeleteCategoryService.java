package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Category;
import com.example.demo.repository.CategoryRepository;

@Service
public class AddOrEditOrDeleteCategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	public Category insertParentCategory(String parentCategory) {
		return categoryRepository.insertParentCategory(parentCategory);
	}
	
	public Category insertChildCategory(String childCategory,Integer parent) {
		return categoryRepository.insertChildCategory(childCategory, parent);
	}
	
	public Category insertGrandChildCategory(String grandChildCategory,Integer parent,String nameAll) {
		return categoryRepository.insertGrandChildCategory(grandChildCategory, parent,nameAll);
	}
	
	/**
	 * 
	 * 親カテゴリを更新
	 * 
	 * @param oldParentCategory
	 * @param newParentCategory
	 */
	public void updateParentCategory(String oldParentCategory,String newParentCategory) {
		categoryRepository.updateParentCategory(oldParentCategory, newParentCategory);
	}
	
	/**
	 * 
	 * 子カテゴリを更新
	 * 
	 * @param oldParentCategory
	 * @param newParentCategory
	 */
	public void updateChildCategory(String oldParentCategory,String newParentCategory) {
		categoryRepository.updateChildCategory(oldParentCategory, newParentCategory);
	}
	
	
	/**
	 * 
	 * 孫カテゴリを更新
	 * 
	 * @param oldParentCategory
	 * @param newParentCategory
	 */
	public void updateGrandChildCategory(String oldParentCategory,String newParentCategory) {
		categoryRepository.updateGrandParentCategory(oldParentCategory, newParentCategory);
	}
	
	public void deleteParentAndChildAndGrandChildCategory(String parentCategory) {
		categoryRepository.deleteParentAndChildAndGrandChildCategory(parentCategory);
	}
	
	public void deletechildAndGrandChildCategory(String childCategory,Integer parentCategoryId) {
		categoryRepository.deleteChildAndGrandChildCategory(childCategory,parentCategoryId);
	}
	public void deleteGrandChildCategoryByNameAll(String nameAll) {
		categoryRepository.deleteGrandChildCategoryByNameAll(nameAll);
	}

}
