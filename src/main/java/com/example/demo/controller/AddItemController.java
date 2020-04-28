package com.example.demo.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Base64;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.domain.Brand;
import com.example.demo.domain.Category;
import com.example.demo.domain.Item;
import com.example.demo.form.ItemForm;
import com.example.demo.service.AddItemService;
import com.example.demo.service.ShowBrandService;
import com.example.demo.service.ShowCategoryListService;

/**
 * 
 * 商品を追加するコントローラ.
 * 
 * @author namikitsubasa
 *
 */
@Controller
@RequestMapping("/")
public class AddItemController {

	@Autowired
	private AddItemService addItemService;
	
	@Autowired
	private ShowCategoryListService showCategoryListService;
	
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
	 * 商品追加画面に遷移する
	 * 
	 * @param model
	 * @param parentSelectedName parentカテゴリ
	 * @param childSelectedName childカテゴリ
	 * @param grandChildSelectedName grandchildカテゴリ
	 * @param name 商品名
	 * @param price 価格
	 * @param brand　ブランド
	 * @param condition　コンディション
	 * @param description　商品詳細
	 * @return 商品追加画面
	 */
	@RequestMapping("/toAddItem")
	public String toAddItem(Model model,String parentSelectedName,
			String childSelectedName, String grandChildSelectedName,
			String name,String price,String brand,Integer condition,String description) {
		List<Category> parentCategoryList = showCategoryListService.findParentCategory();
		List<Category> childCategoryList = null;
		Integer parentId = null;
		if(StringUtils.isEmpty(condition)) {
			condition=1;
		}
		
		//カテゴリをjsで変える毎に/toAddItemへ遷移するため、入力情報を保持するためモデルに情報格納
		model.addAttribute("parentCategoryList", parentCategoryList);
		model.addAttribute("name",name);
		model.addAttribute("price",price);
		model.addAttribute("brand",brand);
		model.addAttribute("condition",condition);
		model.addAttribute("description",description);
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
		
		return "add";
	}

	/**
	 * 
	 * 商品を追加する.
	 * 
	 * @param form　リクエストパラメータ
	 * @param resultset エラーメッセージ
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
	 * @return
	 * 
	 * 商品追加後、商品検索画面に遷移
	 */
	
	//カテゴリ変える毎にメソッドが処理されるため、sessionスコープにエラーメッセージ格納
	@RequestMapping("/addItem")
	public String addItem (@Validated ItemForm form,BindingResult resultset,Model model,String parentSelectedName,
			String childSelectedName, String grandChildSelectedName, Integer grandChildId,
			String name,String price,String brandName,Integer condition,String description) throws IOException  {
		
		if(StringUtils.isEmpty(form.getName())) {
			FieldError nameError = new FieldError(resultset.getObjectName(), "name", "商品名を入力してください");
			resultset.addError(nameError);
			 session.setAttribute("nameError","商品名を入力してください");
		}else {
			session.removeAttribute("nameError");
		}
		
		if(StringUtils.isEmpty(form.getDescription())) {
			FieldError descriptionError = new FieldError(resultset.getObjectName(), "description", "商品情報を入力してください");
			resultset.addError(descriptionError);
			session.setAttribute("descriptionError","商品情報を入力してください");
		}else {
			session.removeAttribute("descriptionError");
		}
		
		if(StringUtils.isEmpty(form.getPrice()) || price(form.getPrice())==false) {
			FieldError priceError = new FieldError(resultset.getObjectName(), "price", "商品情報を入力してください");
			resultset.addError(priceError);
			session.setAttribute("priceError","商品価格を入力してください");
		}else {
			session.removeAttribute("priceError");
		}
		
		if("-Category1-".equals(form.getParentCategory()) || "-Category2-".equals(form.getChildCategory()) || "- Category3 -".equals(form.getGrandChildCategory())) {
		FieldError categoryError = new FieldError(resultset.getObjectName(), "category", "カテゴリーを選んでください");
		resultset.addError(categoryError);
		session.setAttribute("categoryError","カテゴリーを選んでください");
		}else {
			session.removeAttribute("categoryError");
		}
		
		// 画像ファイル形式チェック
		MultipartFile imageFile = form.getimageFile();
		String fileExtension = null;
		
		try {
			//getExtensionは拡張子を取得する
			fileExtension = imageFile.getOriginalFilename().substring(imageFile.getOriginalFilename().lastIndexOf("."));
			if (!".jpeg".equals(fileExtension) && !".png".equals(fileExtension)&& !".jpg".equals(fileExtension)) {
				resultset.rejectValue("imageFile", "", "拡張子は.jpgか.pngのみに対応しています");
			}
		} catch (Exception e) {
			resultset.rejectValue("imageFile", "", "拡張子は.jpgか.pngのみに対応しています");
		}
		
		if(resultset.hasErrors()) {
			return toAddItem(model, parentSelectedName, childSelectedName, grandChildSelectedName, name, price, brandName, condition, description);
		}
		
		Item item = new Item();
		StringBuilder category=new StringBuilder();
		BeanUtils.copyProperties(form, item);
		// 画像ファイルをBase64形式にエンコード
		String base64FileString = Base64.getEncoder().encodeToString(imageFile.getBytes());
		if (".jpeg".equals(fileExtension)) {
			base64FileString = "data:image/jpeg;base64," + base64FileString;
		} else if (".png".equals(fileExtension)) {
			base64FileString = "data:image/png;base64," + base64FileString;
		}else if(".jpg".equals(fileExtension)) {
			base64FileString = "data:image/jpg;base64," + base64FileString;
		}
		item.setImage(base64FileString);

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
		item.setCategory(searchedCategory.getId());
		addItemService.addItem(item);
		
		//登録完了できたらログをとる
		Logger logger = Logger.getLogger("addItem");
		try {
			FileHandler fHandler = new FileHandler("/Users/namikitsubasa/desktop/addItem.log",true);
			fHandler.setFormatter(new SimpleFormatter());
			logger.addHandler(fHandler);
			
			logger.setLevel(Level.INFO);
			logger.info(form.getName()+"を追加しました");
			throw new IllegalArgumentException();
		}catch (IllegalArgumentException e) {
			logger.log(Level.INFO, "例外のスローを捕捉", e);
		}catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			   e.printStackTrace();
		}
		
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
