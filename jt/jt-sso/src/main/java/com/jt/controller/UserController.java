package com.jt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.service.UserService;
import com.jt.vo.SysResult;

import javassist.expr.NewArray;
import redis.clients.jedis.JedisCluster;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private JedisCluster jedisCluster;
	@Autowired
	private UserService userService;
	
	@RequestMapping("/check/{param}/{type}")
	public JSONPObject checkUser(@PathVariable String param,
							   @PathVariable Integer type,
							   String callback) {
		JSONPObject jsonpObject;
		try {
			boolean flag=userService.findCheckUser(param,type);
			jsonpObject=new JSONPObject(callback,SysResult.success(flag));
		} catch (Exception e) {
			e.printStackTrace();
			jsonpObject=new JSONPObject(callback, SysResult.fail());
		}
		return jsonpObject;
		
	}
	
	
	/*
	 * @RequestMapping("/query/{ticket}") public JSONPObject
	 * findUserByTicket(@PathVariable String ticket,String callback) { String
	 * userJson=jedisCluster.get(ticket); JSONPObject jsonpObject=null;
	 * if(StringUtils.isEmpty(userJson)) { jsonpObject=new JSONPObject(callback, new
	 * SysResult().fail()); }else { jsonpObject=new JSONPObject(callback, new
	 * SysResult().success(userJson)); } return jsonpObject; }
	 */
	@RequestMapping("/query/{ticket}")
	public JSONPObject findUserByTicket(@PathVariable String ticket,String callback) {
		String userJson = jedisCluster.get(ticket);
		/* JSONPObject jsonpObject=null; */
		if(StringUtils.isEmpty(userJson)) {
			return new JSONPObject(callback, new SysResult().fail());
		}else {
			return new JSONPObject(callback, new SysResult().success(userJson));
		}
	}
	
}
