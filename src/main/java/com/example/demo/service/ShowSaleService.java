package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.example.demo.domain.Sale;
import com.example.demo.repository.SaleRepository;

/**
 * 
 * sales情報を操作するサービスクラス.
 * 
 * @author namikitsubasa
 *
 */
@Service
public class ShowSaleService {

	@Autowired
	private SaleRepository saleRepository;

	/**
	 * 
	 * 指定した年の売上げ情報を取得する.
	 * 
	 * @param year 指定した年
	 * @return　売上げ情報
	 */
	static int count=0;
	static Sale sale=null;
	public StringBuilder showSaleOfMonth(Integer year) {
		StringBuilder saleOfMonth = new StringBuilder();
		List<Sale> saleList = saleRepository.showSaleOfMonth(year);
		
		saleList.forEach(s->{Sale sale=s;
		saleOfMonth.append(sale.getSales());
		count++;
		if(count != saleList.size()) {
			saleOfMonth.append(",");
		}
		});
		sale=null;
		count=0;
		return saleOfMonth;
	}
}
