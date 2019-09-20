package com.jt.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.ano.Cache_Find;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;

@RestController
@RequestMapping("/web/item")
public class WebItemController {
	/**
	 * manage.jt.com/web/item/findItemById?itemId=xxx
	 */
	@Autowired
	private ItemService itemService;
	@RequestMapping("findItemById")
	public Item findItemById(Long itemId) {
		return itemService.findItemById(itemId);
	}
	@RequestMapping("findItemDescById")
	@Cache_Find
	public ItemDesc findItemDescById(Long itemId) {
		return itemService.findItemDescById(itemId);
	}
}
