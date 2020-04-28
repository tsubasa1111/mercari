package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Item;
import com.example.demo.repository.ItemRepository;

/**
 * 
 * 商品を追加するサービス.
 * 
 * @author namikitsubasa
 *
 */
@Service
public class AddItemService {
	
	@Autowired
	private ItemRepository itemRepository;
	
	public void addItem(Item item) {
		itemRepository.insert(item);
	}

}
