package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.domain.Item;
import com.example.demo.service.ShowItemListService;

/**
 * 
 * 商品詳細を表示するコントローラ.
 * 
 * @author namikitsubasa
 *
 */
@Controller
@RequestMapping("")
public class ShowItemDetailController {
	
	
	@Autowired
	private ShowItemListService showItemListService;
	
	/**
	 * 
	 * 商品詳細画面に遷移する.
	 * 
	 * @param id 商品id
	 * @param model
	 * @param offset 
	 * @param searchName　検索商品名
	 * @param parentCategory 
	 * @param childCategory
	 * @param grandChildCategory
	 * @param brand 検索ブランド
	 * @param page　閲覧ページ
	 * @return
	 */
	//商品検索画面に戻った時に、検索条件が初期化されないよう詳細画面前に持っていた情報を受け取る
	@RequestMapping("/showItemDetail")
	public String showItemDetail(Integer id,Model model,Integer offset,String searchName,String parentCategory,String childCategory,
			String grandChildCategory,String brand,Integer page) {
		Item item=showItemListService.showItemById(id);
		model.addAttribute("searchName", searchName);
		model.addAttribute("offset", offset);
		model.addAttribute("selectedParentCategoryBysearch", parentCategory);
		model.addAttribute("selectedChildCategoryBysearch", childCategory);
		model.addAttribute("selectedGrandChildCategoryBysearch", grandChildCategory);
		model.addAttribute("page", page);
		model.addAttribute("brand", brand);
		model.addAttribute("item", item);
		return "detail";
	}


}
