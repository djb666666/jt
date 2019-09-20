package com.jt.controller.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.pojo.ItemCat;

@RestController
public class JsonPController {
	@RequestMapping("/web/testJSONP")
	public JSONPObject jsonp(String callback) {
		ItemCat itemCat = new ItemCat(); 
		itemCat.setId(10086L);
		itemCat.setName("jsonp测试调用!!!!");
		return new JSONPObject(callback, itemCat);
	}
}
