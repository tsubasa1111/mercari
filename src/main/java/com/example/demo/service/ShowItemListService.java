package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Item;
import com.example.demo.repository.ItemRepository;

@Service
public class ShowItemListService {

	
	@Autowired
	private ItemRepository itemRepository;
	
	/**
	 * 商品情報を表示する.
	 * 
	 * @param offset 検索開始位置
	 * @return　商品情報
	 */
	public List<Item> showItemList(Integer offset,String searchName,String brand,Integer limit){
		return itemRepository.findAll(offset,searchName,brand,limit);
	}
	
	
	/**
	 * 
	 * カテゴリ(単数)、ブランド、商品名指定での検索
	 * 
	 * @param category カテゴリ番号(単数カテゴリ)
	 * @param offset
	 * @param searchName 商品名
	 * @param brand ブランド名
	 * @return　カテゴリ、ブランド、商品名指定での検索
	 */
	public List<Item> showItemListByGrandNameAndSearchedNameAndBrand(Integer category,Integer offset,String searchName,String brand,Integer limit){
		return itemRepository.findByGrandChildNameAndNameAndBrand(category, offset, searchName, brand,limit);
	}
	
	/**
	 * 
	 * idで商品を検索する
	 * 
	 * @param id 商品id
	 * @return 商品情報
	 */
	public Item showItemById(Integer id) {
		return itemRepository.load(id);
	}
	
	/**
	 * 
	 * sale商品検索
	 * 
	 * @param offset
	 * @param limit
	 * @return
	 */
	public List<Item> showSaleItem(){
		return itemRepository.findAllOfselItem();
	}
	

	/**
	 * 
	 * 親カテゴリ名で商品検索
	 * 
	 * @param parentCategoryName 親カテゴリ名
	 * @param offset　sql開始位置
	 * @param limit   検索件数
	 * @param searchedName　検索商品名
	 * @param brand　検索ブランド名
	 * @return　商品一覧
	 */
	public List<Item> showItemByParentCategoryName(String parentCategoryName,Integer offset,Integer limit,String searchedName,String brand){
		return itemRepository.findByParentCategoryName(parentCategoryName, offset, limit,searchedName,brand);
	}
	
	
	/**
	 * @param nameAll
	 * @param offset
	 * @param limit
	 * @param searchedName
	 * @param brand
	 * @return
	 */
	public List<Item> findByGrandChildCategoryBYChildName(String nameAll,Integer offset,Integer limit,String searchedName,String brand){
		return itemRepository.findByGrandChildCategoryBYChildName(nameAll, offset, limit, searchedName, brand);
	}
	

	
}
