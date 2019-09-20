package com.jt.service;

import org.apache.ibatis.annotations.Select;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.vo.EasyUITable;

public interface ItemService {
	
	EasyUITable findItemById(Integer page, Integer rows);

	void saveItem(Item item,ItemDesc itemDesc);

	void updateItem(Item item,ItemDesc itemDesc);

	void updateStatus(Long[] ids, int status);

	void deleteItemById(Long[] ids);

	ItemDesc findItemDescById(Long itemId);

	Item findItemById(Long itemId);
	
}
