package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.service.ShowSaleService;

@Controller
@RequestMapping("")
public class ShowSalesGraphController {
	
	@Autowired
	private ShowSaleService showSaleService;
	
	/**
	 * 
	 * 売上げ情報を表示するコントローラ.
	 * 
	 * @param year　売上げ年
	 * @param model
	 * @return　売上を表示する画面
	 */
	@RequestMapping("/showSalesGraph")
	public String showSalesGraph(Integer year,Model model) {
		StringBuilder sale=showSaleService.showSaleOfMonth(year);
		model.addAttribute("sale",sale);
		model.addAttribute("year",year);
		
		return"salesGraph";
	}

}
