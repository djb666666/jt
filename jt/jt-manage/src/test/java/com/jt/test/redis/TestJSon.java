package com.jt.test.redis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.catalina.mapper.Mapper;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.pojo.ItemDesc;

public class TestJSon {
	@Test
	public void toJSon() throws Exception {
		ItemDesc itemDesc=new ItemDesc();
		itemDesc.setItemId(1000L);
		itemDesc.setItemDesc("aaa");
		ObjectMapper objectMapper=new ObjectMapper();
		String json = objectMapper.writeValueAsString(itemDesc);
		System.out.println(json);
	}
	@Test
	public void testList() throws Exception {
		ItemDesc itemDesc1=new ItemDesc();
		itemDesc1.setItemId(1003L);
		itemDesc1.setItemDesc("aaa");
		ItemDesc itemDesc2=new ItemDesc();
		itemDesc2.setItemId(1001L);
		itemDesc2.setItemDesc("aaa");
		ItemDesc itemDesc3=new ItemDesc();
		itemDesc3.setItemId(1002L);
		itemDesc3.setItemDesc("aaa");
		List<ItemDesc> list =new ArrayList<ItemDesc>();
		list.add(itemDesc1);
		list.add(itemDesc2);
		list.add(itemDesc3);
		ObjectMapper objectMapper=new ObjectMapper();
		String json=objectMapper.writeValueAsString(list);
		System.out.println(json);
		
		//将json转化为对象
		List<ItemDesc> mapperItemDesc = 
				objectMapper.readValue(json,list.getClass());
		System.out.println(mapperItemDesc);
	}
}
