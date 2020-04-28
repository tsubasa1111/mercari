package com.example.demo.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.domain.Category;
import com.example.demo.domain.Item;
import com.example.demo.form.CategoryForm;
import com.example.demo.service.AddOrEditOrDeleteCategoryService;
import com.example.demo.service.ShowCategoryListService;
import com.example.demo.service.ShowItemListService;

/**
 * 
 * カテゴリを追加するコントローラクラス.
 * 
 * @author namikitsubasa
 *
 */
@Controller
@RequestMapping("")
public class AddOrEditOrDeleteCategoryController {

	@Autowired
	private ShowCategoryListService showCategoryListService;

	@Autowired
	private ShowItemListService showItemListService;

	@Autowired
	private AddOrEditOrDeleteCategoryService editCategoryService;

	@ModelAttribute
	public CategoryForm setUpCategoryForm() {
		return new CategoryForm();
	}

	/**
	 *
	 * カテゴリを追加画面に遷移する
	 * 
	 * @param model
	 * @param parentSelectedName
	 * @param childSelectedName
	 * @param grandChildSelectedName
	 * @return カテゴリを追加画面
	 */
	@RequestMapping("/toAddCategory")
	public String toAddCategory(Model model, String parentSelectedName, String childSelectedName,
			String grandChildSelectedName) {

		List<Category> parentCategoryList = showCategoryListService.findParentCategory();
		List<Category> childCategoryList = null;
		Integer parentId = null;

		model.addAttribute("parentCategoryList", parentCategoryList);

		escape(parentSelectedName, childSelectedName, grandChildSelectedName);

		// 選択した親カテゴリによって、子カテゴリリストを変化させる
		if (!(StringUtils.isEmpty(parentSelectedName))
				&& showCategoryListService.findParentCategory(parentSelectedName) != null) {
			Category selectedParentCategory = showCategoryListService.findParentCategory(parentSelectedName);
			// parentnameIDからchildカテゴリ情報を取得
			childCategoryList = showCategoryListService.findChildCategoryByParent(selectedParentCategory.getId());
			parentId = selectedParentCategory.getId();
			model.addAttribute("parentSelectedName", parentSelectedName);
			model.addAttribute("childCategoryList", childCategoryList);
		} else {
			model.addAttribute("parentSelectedName", parentSelectedName);
		}

		List<Category> grandChildCategoryList = null;
		// 選択した子カテゴリによって、孫カテゴリリストを変化させる
		if (!(StringUtils.isEmpty(childSelectedName))
				&& showCategoryListService.findChildCategoryByChildCategoryNameAndParentCategoryId(childSelectedName, parentId) != null) {
			Category selectedChildCategory = showCategoryListService.findChildCategoryByChildCategoryNameAndParentCategoryId(childSelectedName, parentId);
			// childnameIDからchildカテゴリ情報を取得
			grandChildCategoryList = showCategoryListService
					.findGrandChildCategoryBychildCategoryId(selectedChildCategory.getId());
			model.addAttribute("childSelectedName", childSelectedName);
			model.addAttribute("grandChildCategoryList", grandChildCategoryList);
		} else {
			model.addAttribute("childSelectedName", childSelectedName);
		}

		if (!(StringUtils.isEmpty(grandChildSelectedName))) {
			model.addAttribute("grandChildSelectedName", grandChildSelectedName);
		}
		return "categoryAdd";

	}

	/**
	 * 
	 * カテゴリを追加する
	 * 
	 * @param form
	 * @return 商品追加画面に遷移する
	 */
	@RequestMapping("/addCategory")
	public String addCategory(CategoryForm form,RedirectAttributes redirect) {
		Category category = new Category();
		
		if(StringUtils.isEmpty(form.getParentCategory()) || StringUtils.isEmpty(form.getChildCategory()) || StringUtils.isEmpty(form.getGrandChildCategory())){
			redirect.addFlashAttribute("error", "カテゴリ名を全て入力してください");
			System.out.println("aaa");
			return "redirect:/toAddCategory";
		}
		
		if (showCategoryListService.findParentCategory(form.getParentCategory()) == null) {
			category = editCategoryService.insertParentCategory(form.getParentCategory());
		} else {
			category = showCategoryListService.findParentCategory(form.getParentCategory());
		}

		if (showCategoryListService.findChildCategoryByChildCategoryNameAndParentCategoryId(form.getChildCategory(), category.getId()) == null) {
			category = editCategoryService.insertChildCategory(form.getChildCategory(), category.getId());
		} else {
			category = showCategoryListService.findChildCategory(form.getChildCategory());
		}

		if (showCategoryListService.findGrandChildCategory(form.getGrandChildCategory(), category.getId()) == null) {
			StringBuilder nameAll = new StringBuilder();
			nameAll.append(form.getParentCategory());
			nameAll.append('/');
			nameAll.append(form.getChildCategory());
			nameAll.append('/');
			nameAll.append(form.getGrandChildCategory());
			String name = nameAll.toString();
			editCategoryService.insertGrandChildCategory(form.getGrandChildCategory(), category.getId(), name);
		}

		return "add";

	}

	/**
	 * 
	 * カテゴリ編集画面に遷移する
	 * 
	 * @param model
	 * @param parentSelectedName
	 * @param childSelectedName
	 * @param grandChildSelectedName
	 * @return カテゴリ編集画面
	 */
	@RequestMapping("/toEditCategory")
	public String toEditCategory(Model model, String parentSelectedName, String childSelectedName,
			String grandChildSelectedName, String newParentCategory, String newChildCategory,
			String newGrandCHildCategory) {

		List<Category> parentCategoryList = showCategoryListService.findParentCategory();
		List<Category> childCategoryList = null;
		Integer parentId = null;

		model.addAttribute("parentCategoryList", parentCategoryList);
		escape(parentSelectedName, childSelectedName, grandChildSelectedName);

		// 選択した親カテゴリによって、子カテゴリリストを変化させる
		if (!(StringUtils.isEmpty(parentSelectedName)) && !("-Category1-".equals(parentSelectedName))) {
			Category selectedParentCategory = showCategoryListService.findParentCategory(parentSelectedName);
			// parentnameIDからchildカテゴリ情報を取得
			childCategoryList = showCategoryListService.findChildCategoryByParent(selectedParentCategory.getId());
			parentId = selectedParentCategory.getId();
			model.addAttribute("parentSelectedName", parentSelectedName);
			model.addAttribute("childCategoryList", childCategoryList);
			model.addAttribute("newParentCategory", newParentCategory);
		} else {
			model.addAttribute("parentSelectedName", parentSelectedName);
		}

		List<Category> grandChildCategoryList = null;
		// 選択した子カテゴリによって、孫カテゴリリストを変化させる
		if (!(StringUtils.isEmpty(childSelectedName) && !("-Category2-".equals(childSelectedName)))
				&& showCategoryListService.findChildCategoryByChildCategoryNameAndParentCategoryId(childSelectedName, parentId) != null) {
			Category selectedChildCategory = showCategoryListService.findChildCategoryByChildCategoryNameAndParentCategoryId(childSelectedName, parentId);
			// childnameIDからchildカテゴリ情報を取得
			grandChildCategoryList = showCategoryListService
					.findGrandChildCategoryBychildCategoryId(selectedChildCategory.getId());
			model.addAttribute("childSelectedName", childSelectedName);
			model.addAttribute("grandChildCategoryList", grandChildCategoryList);
			model.addAttribute("newChildCategory", newChildCategory);
		} else {
			model.addAttribute("childSelectedName", childSelectedName);
		}

		if (!(StringUtils.isEmpty(grandChildSelectedName)) && !("- Category3 -".equals(grandChildSelectedName))) {
			model.addAttribute("grandChildSelectedName", grandChildSelectedName);
			model.addAttribute("newGrandCHildCategory", newGrandCHildCategory);
		}
		

		return "categoryEdit";
	}

	/**
	 * 
	 * カテゴリを編集する
	 * 
	 * @param form
	 * @return 商品一覧画面に遷移する
	 */
	@RequestMapping("/editCategory")
	public String editCategory(CategoryForm form, Model model) {

		if (!(StringUtils.isEmpty(form.getNewParentCategory()))) {
			editCategoryService.updateParentCategory(form.getParentCategory(), form.getNewParentCategory());
		}
		if (!(StringUtils.isEmpty(form.getNewChildCategory()))) {
			editCategoryService.updateChildCategory(form.getChildCategory(), form.getNewChildCategory());
		}

		if (!(StringUtils.isEmpty(form.getNewGrandChildCategory()))) {
			editCategoryService.updateGrandChildCategory(form.getGrandChildCategory(), form.getNewGrandChildCategory());
		}
		return "redirect:/showItemList";
	}

	
	@RequestMapping("/toDeleteCategory")
	public String toDeleteCategory(Model model, String parentSelectedName, String childSelectedName,
			String grandChildSelectedName) {

		List<Category> parentCategoryList = showCategoryListService.findParentCategory();
		List<Category> childCategoryList = null;
		Integer parentId = null;

		
		model.addAttribute("parentCategoryList", parentCategoryList);
		model.addAttribute("childSelectedName", childSelectedName);

		escape(parentSelectedName, childSelectedName, grandChildSelectedName);

		// 選択した親カテゴリによって、子カテゴリリストを変化させる
		if (!(StringUtils.isEmpty(parentSelectedName)) && !("-Category1-".equals(parentSelectedName))) {
			Category selectedParentCategory = showCategoryListService.findParentCategory(parentSelectedName);
			// parentnameIDからchildカテゴリ情報を取得
			childCategoryList = showCategoryListService.findChildCategoryByParent(selectedParentCategory.getId());
			parentId = selectedParentCategory.getId();
			model.addAttribute("parentSelectedName", parentSelectedName);
			model.addAttribute("childCategoryList", childCategoryList);
		} else {
			model.addAttribute("parentSelectedName", parentSelectedName);
		}

		List<Category> grandChildCategoryList = null;
		// 選択した子カテゴリによって、孫カテゴリリストを変化させる
		if (!(StringUtils.isEmpty(childSelectedName) && !("-Category2-".equals(childSelectedName)))
				&& showCategoryListService.findChildCategoryByChildCategoryNameAndParentCategoryId(childSelectedName, parentId) != null) {
			Category selectedChildCategory = showCategoryListService.findChildCategoryByChildCategoryNameAndParentCategoryId(childSelectedName, parentId);
			// childnameIDからchildカテゴリ情報を取得
			grandChildCategoryList = showCategoryListService
					.findGrandChildCategoryBychildCategoryId(selectedChildCategory.getId());
			model.addAttribute("childSelectedName", childSelectedName);
			model.addAttribute("grandChildCategoryList", grandChildCategoryList);
		} else {
			model.addAttribute("childSelectedName", childSelectedName);
		}

		if (!(StringUtils.isEmpty(grandChildSelectedName)) && !("- Category3 -".equals(grandChildSelectedName))) {
			model.addAttribute("grandChildSelectedName", grandChildSelectedName);
		}

		return "categoryDelete";
	}

	
	/**
	 * 
	 * 親カテゴリを指定してカテゴリを削除する(属する子、孫カテゴリも削除)
	 * 該当商品があった場合は、削除しない
	 * 
	 * @param form
	 * @param model
	 * @param redirectAttributes
	 * @return　削除画面にリダイレクトで画面遷移
	 */
	@RequestMapping("/deleteParentCategory")
	public String deleteParentCategory(CategoryForm form, Model model, RedirectAttributes redirectAttributes) {

		List<Item> itemList=showItemListService.showItemByParentCategoryName(form.getParentCategory(), 0, 10,"", "");

		
		if ("-Category1-".equals(form.getParentCategory())) {
			redirectAttributes.addFlashAttribute("categoryError", "カテゴリを選んでください。");
		} else if (itemList.isEmpty()) {
			editCategoryService.deleteParentAndChildAndGrandChildCategory(form.getParentCategory());
			System.out.println("消した");
		} else {
			redirectAttributes.addFlashAttribute("includeItem", "該当のカテゴリに所属している商品があります。");
		}
		return "redirect:/toDeleteCategory";
	}

	
	/**
	 * 
	 * 子カテゴリを指定してカテゴリを削除する(属する孫カテゴリも削除)
	 * 該当商品があった場合は、削除しない
	 * 
	 * @param form
	 * @param inputParentCategory
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping("/deleteChildCategory")
	public String deleteChildCategory(CategoryForm form,String inputParentCategory, RedirectAttributes redirectAttributes) {
		
		if(StringUtils.isEmpty(inputParentCategory) || "-Category2-".equals(form.getChildCategory())) {
			redirectAttributes.addFlashAttribute("childcategoryError", "カテゴリを選んでください。");
			return "redirect:/toDeleteCategory";
		}
		
		Category parentCategory=showCategoryListService.findParentCategory(inputParentCategory);
		
		StringBuilder str = new StringBuilder();
		str.append(inputParentCategory);
		str.append("/");
		str.append(form.getChildCategory());
		String nameAll=str.toString();
		List<Item> itemList=showItemListService.findByGrandChildCategoryBYChildName(nameAll, 0, 10, "", "");

		
		if (itemList.isEmpty()) {
//			editCategoryService.deletechildAndGrandChildCategory(form.getChildCategory(),parentCategory.getId());
			System.out.println("消した");
		} else {
			redirectAttributes.addFlashAttribute("childCategoryincludeItem", "該当のカテゴリに所属している商品があります。");
		}
		return "redirect:/toDeleteCategory";
		
	}

	
	@RequestMapping("/deleteGrandChildCategory")
	public String deleteGrandChildCategory(CategoryForm form,String inputParentCategory,String inputChildCategory,RedirectAttributes redirectAttributes) {
		
		if(StringUtils.isEmpty(inputParentCategory) || StringUtils.isEmpty(inputChildCategory) || "- Category3 -".equals(form.getGrandChildCategory())) {
			redirectAttributes.addFlashAttribute("grandChildcategoryError", "カテゴリを選んでください。");
			return "redirect:/toDeleteCategory";
		}
		
		StringBuilder name = new StringBuilder();
		name.append(inputParentCategory);
		name.append('/');
		name.append(inputChildCategory);
		name.append('/');
		name.append(form.getGrandChildCategory());
		String nameAll=name.toString();
		Category category=showCategoryListService.showCategoryByNameAll(nameAll);
		
			List<Item> itemList = showItemListService.showItemListByGrandNameAndSearchedNameAndBrand(
					category.getId(), 0, "", "", 10);
		
			if (CollectionUtils.isEmpty(itemList)) {
//			editCategoryService.deleteGrandChildCategoryByNameAll(nameAll);
			System.out.println("消した");
		} else {
			redirectAttributes.addFlashAttribute("grandChildCategoryincludeItem", "該当のカテゴリに所属している商品があります。");
		}
		
		return "redirect:/toDeleteCategory";
	
	}
	
	/**
	 * 
	 * エスケープ処理
	 * 
	 * @param parentSelectedName　親
	 * @param childSelectedName　　子
	 * @param grandChildSelectedName　孫
	 */
	public void escape(String parentSelectedName,String childSelectedName,String grandChildSelectedName) {
		if (!(StringUtils.isEmpty(parentSelectedName))) {
			try {
				parentSelectedName = URLDecoder.decode(parentSelectedName, "UTF-8");
			} catch (UnsupportedEncodingException e) {
			}
		}
		
		if (!(StringUtils.isEmpty(childSelectedName))) {
			try {
				childSelectedName = URLDecoder.decode(childSelectedName, "UTF-8");
			} catch (UnsupportedEncodingException e) {
			}
		}
		
		if (!(StringUtils.isEmpty(grandChildSelectedName))) {
			grandChildSelectedName = grandChildSelectedName.replace("andand", "&");
		}
	}
	
}
