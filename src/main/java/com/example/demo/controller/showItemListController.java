package com.example.demo.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.domain.Category;
import com.example.demo.domain.Item;
import com.example.demo.service.ShowCategoryListService;
import com.example.demo.service.ShowItemListService;

/**
 * 
 * 商品検索画面を表示するコントローラ.
 * 
 * @author namikitsubasa
 *
 */
@Controller
@RequestMapping("")
public class showItemListController {

	@Autowired
	private ShowItemListService showItemListService;

	@Autowired
	private ShowCategoryListService showCategoryListService;

	@Autowired
	private HttpSession session;

	String lastParentSelectedName = null;
	String lastChildSelectedName = null;
	String lastGrandChildSelectedName = null;

	/**
	 * 
	 * 商品一覧を表示する
	 * 
	 * @param offset     sql開始位置
	 * @param page       アクセスしたいページ番号
	 * @param model
	 * @param searchName 検索した商品名
	 * @param brand 検索したブランド名
	 * @param parentSelectedName 選択した親カテゴリ名
	 * @param childSelectedName 選択した子カテゴリ名
	 * @param grandChildSelectedName 選択した孫カテゴリ名
	 * @param grandChildId 孫カテゴリid
	 * @return 商品検索画面
	 */
	@RequestMapping(value = "/showItemList")
	public String showItemList(Integer offset, Integer page, Model model, String searchName, String brand,
			String parentSelectedName, String childSelectedName, String grandChildSelectedName, Integer grandChildId) {

		List<Category> parentCategoryList = showCategoryListService.findParentCategory();
		model.addAttribute("parentCategoryList", parentCategoryList);

		Integer parentCategoryId = null;
		List<Category> childCategoryList = null;
		// 選択した親カテゴリの子カテゴリを表示する
		if (!(StringUtils.isEmpty(parentSelectedName)) && !("-Category1-".equals(parentSelectedName))) {
			try {
				parentSelectedName = URLDecoder.decode(parentSelectedName, "UTF-8");
			} catch (UnsupportedEncodingException e) {
			}
			Category selectedParentCategory = showCategoryListService.findParentCategory(parentSelectedName);
			childCategoryList = showCategoryListService.findChildCategoryByParent(selectedParentCategory.getId());
			parentCategoryId = selectedParentCategory.getId();
			session.setAttribute("childCategoryList", childCategoryList);
		} else {
			childSelectedName = "-Category2-";
			session.removeAttribute("childCategoryList");
			session.removeAttribute("grandChildCategoryList");
		}

		List<Category> grandChildCategoryList = null;
		Integer childParentId = null;
		// 選択した子カテゴリの孫カテゴリを表示する
		if (!(StringUtils.isEmpty(childSelectedName)) && !("-Category2-".equals(childSelectedName))) {
			try {
				childSelectedName = URLDecoder.decode(childSelectedName, "UTF-8");
			} catch (UnsupportedEncodingException e) {
			}
			Category selectedChildCategory = showCategoryListService
					.findChildCategoryByChildCategoryNameAndParentCategoryId(childSelectedName, parentCategoryId);
			grandChildCategoryList = showCategoryListService
					.findGrandChildCategoryBychildCategoryId(selectedChildCategory.getId());
			childParentId = selectedChildCategory.getId();
			session.setAttribute("grandChildCategoryList", grandChildCategoryList);
		} else {
			grandChildSelectedName = "- Category3 -";
			session.removeAttribute("grandChildCategoryList");
		}

		// 選択した孫カテゴリの情報を取得する.
		if (!(StringUtils.isEmpty(grandChildSelectedName)) && !("- Category3 -".equals(grandChildSelectedName))) {
			try {
				grandChildSelectedName = URLDecoder.decode(grandChildSelectedName, "UTF-8");
			} catch (UnsupportedEncodingException e) {
			}
			Category selectedGrandChildCategory = showCategoryListService.findGrandChildCategory(grandChildSelectedName,
					childParentId);
			grandChildId = selectedGrandChildCategory.getId();
			session.setAttribute("grandChildId", grandChildId);
			model.addAttribute("selectedGrandChildCategory", selectedGrandChildCategory);
		}


		if (StringUtils.isEmpty(page) || page <= 1) {
			page = 1;
			offset = 0;
		} else {
			offset = (page - 1) * 30 + 1;
		}

		if (StringUtils.isEmpty(offset) || offset < 0) {
			offset = 0;
		}

		// null例外回避
		if (StringUtils.isEmpty(parentSelectedName)) {
			parentSelectedName = "-Category1-";
		}

		// カテゴリが変わったら表示ページを1ページ目にする
		if (!(parentSelectedName.equals(lastParentSelectedName)) || !(childSelectedName.equals(lastChildSelectedName))
				|| !(grandChildSelectedName.equals(lastGrandChildSelectedName))) {
			offset = 0;
		}
		lastParentSelectedName = parentSelectedName;
		lastChildSelectedName = childSelectedName;
		lastGrandChildSelectedName = grandChildSelectedName;

		// null例外回避
		if (StringUtils.isEmpty(searchName)) {
			searchName = "";
		}
		if (StringUtils.isEmpty(brand)) {
			brand = "";
		}

		List<Item> itemList = null;
		// 商品検索のメソッド
		itemList = showItem(model, itemList, grandChildSelectedName, childSelectedName, parentSelectedName, brand,
				offset, searchName, page);

		return "list";
	}

	/**
	 * 
	 * 商品検索を行う
	 * 
	 * @param model
	 * @param itemList               商品リスト
	 * @param grandChildSelectedName 孫カテゴリ
	 * @param childSelectedName      子カテゴリ
	 * @param parentSelectedName     親カテゴリ
	 * @param brand                  ブランド名
	 * @param offset                 sql開始位置
	 * @param searchName             検索商品名
	 * @param page                   アクセスしたいページ番号
	 * @return 商品検索結果
	 */
	public List<Item> showItem(Model model, List<Item> itemList, String grandChildSelectedName,
			String childSelectedName, String parentSelectedName, String brand, Integer offset, String searchName,
			Integer page) {

		Integer totalNumOfPage = 0;
		Integer totalNumOfItem = 0;

		// 孫カテゴリまで指定した場合
		if (!("- Category3 -".equals(grandChildSelectedName))) {
			itemList = showItemListService.showItemListByGrandNameAndSearchedNameAndBrand(
					(Integer) session.getAttribute("grandChildId"), offset, searchName, brand, 30);
			totalNumOfItem = showItemListService.showItemListByGrandNameAndSearchedNameAndBrand(
					(Integer) session.getAttribute("grandChildId"), 0, searchName, brand, 300).size();
			// 子カテゴリまで指定した場合
		} else if (!("-Category2-".equals(childSelectedName))) {
			StringBuilder str = new StringBuilder();
			str.append(parentSelectedName);
			str.append("/");
			str.append(childSelectedName);
			String nameAll = str.toString();
			itemList = showItemListService.findByGrandChildCategoryBYChildName(nameAll, offset, 30, searchName, brand);
			totalNumOfItem = showItemListService
					.findByGrandChildCategoryBYChildName(nameAll, offset, 300, searchName, brand).size();
			// 親カテゴリのみ指定した場合
		} else if (!("-Category1-".equals(parentSelectedName))) {
			itemList = showItemListService.showItemByParentCategoryName(parentSelectedName, offset, 30, searchName,
					brand);
			totalNumOfItem = showItemListService
					.showItemByParentCategoryName(parentSelectedName, offset, 300, searchName, brand).size();
		} else {
			// カテゴリ指定なし
			itemList = showItemListService.showItemList(offset, searchName, brand, 30);
			// 全件検索は重くなるので300件に絞ってページ数を表示.
			totalNumOfItem = showItemListService.showItemList(0, searchName, brand, 300).size();
		}

		// 1ページあたり30件表示する
		if (totalNumOfItem % 30 == 0) {
			totalNumOfPage = totalNumOfItem / 30;
		} else {
			totalNumOfPage = (totalNumOfItem / 30) + 1;
		}

		
		if (totalNumOfPage < page) {
			page = totalNumOfPage;
		}

		String escapedParentSelectedName = "";
		String escapedChildSelectedName = "";
		String escapedGrandChildSelectedName = "";
		try {
			escapedParentSelectedName = URLEncoder.encode(parentSelectedName, "UTF-8");
			escapedChildSelectedName = URLEncoder.encode(childSelectedName, "UTF-8");
			escapedGrandChildSelectedName = URLEncoder.encode(grandChildSelectedName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
		}
		model.addAttribute("escapedParentSelectedName", escapedParentSelectedName);
		model.addAttribute("escapedChildSelectedName", escapedChildSelectedName);
		model.addAttribute("escapedGrandChildSelectedName", escapedGrandChildSelectedName);
		model.addAttribute("totalNumOfPage", totalNumOfPage);
		model.addAttribute("selectedParentCategoryBysearch", parentSelectedName);
		model.addAttribute("selectedChildCategoryBysearch", childSelectedName);
		model.addAttribute("selectedGrandChildCategoryBysearch", grandChildSelectedName);
		model.addAttribute("itemList", itemList);
		model.addAttribute("offset", offset);
		model.addAttribute("page", page);
		model.addAttribute("searchName", searchName);
		model.addAttribute("brand", brand);
		return itemList;
	}
	
}
