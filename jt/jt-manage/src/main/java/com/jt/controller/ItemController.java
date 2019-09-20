package com.jt.controller;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;
import com.jt.vo.EasyUITable;
import com.jt.vo.SysResult;

@RequestMapping("/item")
@RestController
public class ItemController {
	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/query")
	public EasyUITable findItemById(Integer page,Integer rows) {
		return itemService.findItemById(page,rows);
	}
	/**
	 * 改进方法:定义全局异常处理机制
	 * 
	 * 实现商品描述的新增
	 * @param item
	 * @return
	 */
	@RequestMapping("/save")
	public SysResult saveItem(Item item,ItemDesc itemDesc) {
			itemService.saveItem(item,itemDesc);
			//int i=1/0;
			return SysResult.success();
	}
	
	@RequestMapping("/update")
	public SysResult updateItem(Item item,ItemDesc itemDesc) {
		itemService.updateItem(item,itemDesc);
		return SysResult.success();
	}
	/**
	 * 用户的传参使用","分割参数,则springmvc接受参数可以使用数组接受,有程序内部实现自动转化
	 * @param ids
	 * @return
	 */
	@RequestMapping("/instock")
	public SysResult instock(Long[] ids) {
		int status=2;//表示下架
		itemService.updateStatus(ids,status);
		return SysResult.success();
	}
	
	@RequestMapping("/reshelf")
	public SysResult reshelf(Long[] ids) {
		int status=1;//表示下架
		itemService.updateStatus(ids,status);
		return SysResult.success();
	}
	@RequestMapping("/delete")
	public SysResult deleteItem(Long []ids) {
		itemService.deleteItemById(ids);
		return SysResult.success();
	}
	
	/**
	 * 
	 * 根据的商品详情信息获取服务器数据
	 * 
	 */
	@RequestMapping("/query/item/desc/{itemId}")
	public SysResult findItemDescById(@PathVariable Long itemId) {
		ItemDesc itemDesc=itemService.findItemDescById(itemId);
		return SysResult.success(itemDesc);
				
	}
}


