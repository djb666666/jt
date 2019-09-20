package com.jt.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.ano.Cache_Find;
import com.jt.mapper.ItemCatMapper;
import com.jt.pojo.ItemCat;
import com.jt.util.ObjectMapperUtil;
import com.jt.vo.EasyUITree;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.ShardedJedis;

@Service
public class ItemCatServiceImpl implements ItemCatService{
	@Autowired
	private ItemCatMapper itemCatMapper;
	//@Autowired
	private JedisCluster shardedJedis;

	@Override
	public String findItemCatNameById(Long itemCatId) {
		ItemCat itemcat = itemCatMapper.selectById(itemCatId);
		return itemcat.getName();
	}
	public List<ItemCat> findItemCatList(Long parentId){
		QueryWrapper<ItemCat> queryWrapper=new QueryWrapper<>();
		queryWrapper.eq("parent_id", parentId);
		List<ItemCat> itemCatList=itemCatMapper.selectList(queryWrapper);
		return itemCatList;
	}
	@Override
	public List<EasyUITree> findEasyTreeList(Long parentId) {
		List<ItemCat> itemCatList=findItemCatList(parentId);
		List<EasyUITree> treeList=new ArrayList<EasyUITree>();
		for(ItemCat itemCat:itemCatList) {
			EasyUITree easyUITree=new EasyUITree();
			String state=itemCat.getIsParent()?"closed":"open";
			easyUITree.setId(itemCat.getId())
			.setText(itemCat.getName())
			.setState(state);
			treeList.add(easyUITree);
		}
		return treeList;
	}

	@Override 
	public List<EasyUITree> findEasyUITreeCache(Long parentId) {
		List<EasyUITree> treeList=new ArrayList<>(); 
		String key ="Item_Cat_"+parentId; 
		String result=shardedJedis.get(key);
			if(StringUtils.isEmpty(result)) { 
				treeList=findEasyTreeList(parentId); 
				String value=ObjectMapperUtil.toJson(treeList); 
				shardedJedis.set(key,value);
				System.out.println("查询后台数据库"); }else {
				treeList=ObjectMapperUtil.toOBject(result,treeList.getClass());
				System.out.println("查询redis缓存");
				} 
			return treeList; 
			}


}
