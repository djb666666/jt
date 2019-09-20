package com.jt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jt.ano.Cache_Find;
import com.jt.service.ItemCatService;
import com.jt.vo.EasyUITable;
import com.jt.vo.EasyUITree;

@RequestMapping("/item/cat")
@RestController
public class ItemCatController {
	@Autowired
	private ItemCatService itemCatService;
	
	@RequestMapping("/queryItemName")
	public String findItemCatNameById(Long itemCatId) {
		return itemCatService.findItemCatNameById(itemCatId);
	}
	/**
	 * required:是否需要参数的名称
	 * name:要绑定到的请求参数的名称。
	 * @param parentId
	 * @return
	 */
	
	/*
	 * @RequestMapping("/list")
	 * 
	 * @Cache_Find public List<EasyUITree>findItemCatList(@RequestParam(defaultValue
	 * = "0",name = "id") Long parentId) { return
	 * itemCatService.findEasyUITreeCache(parentId); }
	 */
	@RequestMapping("/list")
	@Cache_Find
	public List<EasyUITree> findItemCatList(@RequestParam(defaultValue="0",name ="id") Long parentId){
		//查询一级商品分类信息
		return itemCatService.findEasyTreeList(parentId);
		//return itemCatService.findEasyUITreeCache(parentId);
	}
	
}
