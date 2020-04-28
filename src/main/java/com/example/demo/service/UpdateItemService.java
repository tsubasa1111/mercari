package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Item;
import com.example.demo.repository.ItemRepository;

@Service
public class UpdateItemService {
	
	@Autowired
	private ItemRepository itemRepository;
	
	public void update(Item item) {
		itemRepository.update(item);
	}

}
