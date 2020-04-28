package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Brand;
import com.example.demo.repository.BrandRepository;

/**
 * 
 * brandsテーブルを操作するサービス
 * 
 * @author namikitsubasa
 *
 */
@Service
public class ShowBrandService {

	@Autowired
	private BrandRepository brandRepository;
	
	/**
	 * 
	 * ブランド名でブランド情報を取得する
	 * 
	 * @param id
	 * @return
	 */
	public Brand findBrandByNameOrInsert(String name) {
		return brandRepository.loadOrInsert(name);
	}
	
}
