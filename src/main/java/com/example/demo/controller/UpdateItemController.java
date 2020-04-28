package com.example.demo.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.domain.Brand;
import com.example.demo.domain.Category;
import com.example.demo.domain.Item;
import com.example.demo.form.ItemForm;
import com.example.demo.service.ShowBrandService;
import com.example.demo.service.ShowCategoryListService;
import com.example.demo.service.ShowItemListService;
import com.example.demo.service.UpdateItemService;

@Controller
@RequestMapping("")
public class UpdateItemController {
	
	@Autowired
	private UpdateItemService updateItemService;
	
	@Autowired
	private ShowCategoryListService showCategoryListService;
	
	@Autowired
	private ShowItemListService showItemListService;
	
	@Autowired
	private ShowBrandService showBrandService;
	
	@Autowired
	private HttpSession session;

	@ModelAttribute
	public ItemForm setUpItemForm() {
		return new ItemForm();
	}
	
	
	/**
	 * 
	 * 商品編集画面へ遷移
	 * 
	 * @param model
	 * @param parentSelectedName
	 * @param childSelectedName
	 * @param grandChildSelectedName
	 * @param name 入力された商品名
	 * @param price　入力された金額
	 * @param brand　入力されたブランド
	 * @param condition　入力されたコンディション
	 * @param description　入力された商品情報
	 * @param id
	 * @return　商品編集画面
	 */
	@RequestMapping("/toUpdateItem")
	public String toUpdateItem(Model model,String parentSelectedName,
			String childSelectedName, String grandChildSelectedName,
			String name,String price,String brand,Integer condition,String description,Integer id) {
		List<Category> parentCategoryList = showCategoryListService.findParentCategory();
		List<Category> childCategoryList = null;
		Integer parentId = null;
		Item item=new Item();
		
		//画面遷移後に一度のみ登録ずみの商品情報をデフォルトでinputタグに記入
		if(!(StringUtils.isEmpty(id))) {
		item=showItemListService.showItemById(id);
		String[] categoryName=item.getNameAll().split("/",0);
		model.addAttribute("parentCategory", categoryName[0]);
		model.addAttribute("childCategory", categoryName[1]);
		model.addAttribute("grandChildCategory", categoryName[2]);
		model.addAttribute("item", item);
		session.setAttribute("id",id);
		//jsで再度メソッドが呼ばれた時に実行される(入力情報をinputタグに保持する)
		}else {
			item.setName(name);
			item.setPrice(Integer.parseInt(price));
			item.setBrand(brand);
			item.setCondition(condition);
			item.setDescription(description);
			model.addAttribute("item",item);
		}
		model.addAttribute("parentCategoryList", parentCategoryList);
		// 選択した親カテゴリによって、子カテゴリリストを変化させる
		if (!(StringUtils.isEmpty(parentSelectedName)) && !("-Category1-".equals(parentSelectedName))) {
			try {
				parentSelectedName = URLDecoder.decode(parentSelectedName, "UTF-8");
			} catch (UnsupportedEncodingException e) {
			}
			Category selectedParentCategory = showCategoryListService.findParentCategory(parentSelectedName);
			// parentnameIDからchildカテゴリ情報を取得
			childCategoryList = showCategoryListService.findChildCategoryByParent(selectedParentCategory.getId());
			parentId = selectedParentCategory.getId();
			model.addAttribute("parentSelectedName", parentSelectedName);
			model.addAttribute("childCategoryList", childCategoryList);
		}

		List<Category> grandChildCategoryList = null;
		// 選択した子カテゴリによって、孫カテゴリリストを変化させる
		if (!(StringUtils.isEmpty(childSelectedName)) && !("-Category2-".equals(childSelectedName))) {
			try {
				childSelectedName = URLDecoder.decode(childSelectedName, "UTF-8");
			} catch (UnsupportedEncodingException e) {
			}
			Category selectedChildCategory = showCategoryListService.findChildCategoryByChildCategoryNameAndParentCategoryId(childSelectedName, parentId);
			// childnameIDからchildカテゴリ情報を取得
			grandChildCategoryList = showCategoryListService
					.findGrandChildCategoryBychildCategoryId(selectedChildCategory.getId());
			model.addAttribute("childSelectedName", childSelectedName);
			model.addAttribute("grandChildCategoryList", grandChildCategoryList);
		}

		if (!(StringUtils.isEmpty(grandChildSelectedName)) && !("- Category3 -".equals(grandChildSelectedName))) {
			model.addAttribute("grandChildSelectedName", grandChildSelectedName);
		}
		
		return "edit";
	}
	
	
	
	/**
	 * 
	 * 商品情報をアップデートし商品検索画面に遷移
	 * 
	 * @param form 入力された商品情報
	 * @param model
	 * @param parentSelectedName
	 * @param childSelectedName
	 * @param grandChildSelectedName
	 * @param grandChildId
	 * @param name
	 * @param price
	 * @param brand
	 * @param condition
	 * @param description
	 * @param id
	 * @return　商品検索画面
	 */
	@RequestMapping("/updateItem")
	public String updateItem(ItemForm form,Model model,String parentSelectedName,
			String childSelectedName, String grandChildSelectedName, Integer grandChildId,
			String name,String price,String brand,Integer condition,String description,Integer id) {
		Item item = new Item();
		StringBuilder category=new StringBuilder();
		BeanUtils.copyProperties(form, item);
		if(StringUtils.isEmpty(form.getSalePrice())) {
			form.setSalePrice(null);
		}else {
		item.setSalePrice(Integer.parseInt(form.getSalePrice()));
		}
		Brand addedbrand=showBrandService.findBrandByNameOrInsert(item.getBrandName());
		item.setBrand(String.valueOf(addedbrand.getId()));
		
		item.setPrice(Integer.parseInt(form.getPrice()));
		category.append(form.getParentCategory());
		category.append("/");
		category.append(form.getChildCategory());
		category.append("/");
		category.append(form.getGrandChildCategory());
		String nameAll=category.toString();
		Category searchedCategory=showCategoryListService.showCategoryByNameAll(nameAll);
		item.setId(form.getId());
		item.setCategory(searchedCategory.getId());
		System.out.println(item.getSalePrice());
		updateItemService.update(item);
		return "redirect:/showItemList";
		
	}
	
	/**
	 * 
	 * 入力された料金が数字でない場合はエラーを起こす.
	 * 
	 * @param price　料金
	 * @return
	 */
	public boolean price(String price) {
		try {
			Integer.parseInt(price);
			return true;
		}catch(Exception e) {
			return false;
		}
	}

}
