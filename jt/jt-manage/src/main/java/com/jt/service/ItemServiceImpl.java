package com.jt.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.mapper.ItemDescMapper;
import com.jt.mapper.ItemMapper;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.vo.EasyUITable;
import com.jt.vo.itemDesc;

@Service
public class ItemServiceImpl implements ItemService{
	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private ItemDescMapper itemDescMapper;

	@Override
	public EasyUITable findItemById(Integer page, Integer rows) {
		int start=(page-1)*rows;
		List<Item>itemList =itemMapper.selectItemById(start,rows);
		int total=itemMapper.selectCount(null);
		return new EasyUITable(total,itemList);
	}

	@Override
	@Transactional//控制事务
	public void saveItem(Item item,ItemDesc itemDesc) {
		item.setStatus(1)
			.setCreated(new Date())
			.setUpdated(item.getCreated());
		//int i=1/0;
		itemMapper.insert(item);
		
		//获取id
		//有item商品 入库时,
		//完成商品详情的入库
		itemDesc.setItemId(item.getId())
				.setCreated(item.getCreated())
				.setUpdated(item.getCreated());
		itemDescMapper.insert(itemDesc);
	}

	@Override
	@Transactional
	public void updateItem(Item item,ItemDesc itemDesc) {
		item.setUpdated(new Date());
		itemMapper.updateById(item);
		
		itemDesc.setItemId(item.getId())
				.setUpdated(item.getUpdated());
		//根据主键itemID更新数据
		itemDescMapper.updateById(itemDesc);
	}
	/**
	 * 批量更新
	 */
	@Override
	public void updateStatus(Long[] ids, int status) {
		Item item=new Item();
		item.setStatus(status)
			.setUpdated(new Date());
		UpdateWrapper<Item> updateWrapper=new UpdateWrapper<>();
		List<Long> idlist=Arrays.asList(ids); 
		updateWrapper.in("id", idlist);
		itemMapper.update(item, updateWrapper);
		
	}
	/**
	 * delete from tb_item where id in()
	 */
	@Transactional
	@Override
	public void deleteItemById(Long[] ids) {
		List<Long> idList=Arrays.asList(ids);
		itemMapper.deleteBatchIds(idList);
		
		itemDescMapper.deleteItems(ids);
	}

	@Override
	public ItemDesc findItemDescById(Long itemId) {
		
		return itemDescMapper.selectById(itemId);
	}

	@Override
	public Item findItemById(Long itemId) {
		
		return itemMapper.selectById(itemId);
	}
	
}
