package com.jt.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.remoting.exchange.Response;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.pojo.User;
import com.jt.service.DubboUserService;
import com.jt.vo.SysResult;

import redis.clients.jedis.JedisCluster;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private JedisCluster jedisCluster;
	@Reference(timeout = 3000,check = true)
	private DubboUserService userService;
	
	/**
	 * 实现用户页面跳转
	 * http://www.jt.com/user/register.html
	 * http://www.jt.com/user/login.html
	 */
	@RequestMapping("/{moduleName}")
	public String login(@PathVariable String moduleName) {
		
		return moduleName;
	}
	
	/**
	 * 实现用户信息新增
	 */
	@RequestMapping("/doRegister")
	@ResponseBody
	public SysResult insertUser(User user) {
		
		userService.insertUser(user);
		return SysResult.success();
	}
	@RequestMapping("/doLogin")
	@ResponseBody
	public SysResult doLogin(User user,HttpServletResponse response) {
		String ticket=userService.doLogin(user);
		if(StringUtils.isEmpty(ticket)) {
			return SysResult.fail();
		}
		Cookie cookie=new Cookie("JT_TICKET", ticket);
		cookie.setDomain("jt.com");
		cookie.setPath("/");
		cookie.setMaxAge(7*24*3600);
		response.addCookie(cookie);
		return SysResult.success();
	}
	
	@RequestMapping("/logout")
	public String doLogOut(HttpServletRequest request,HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		String ticket=null;
		if(cookies.length>0) {
			for(Cookie cookie:cookies) {
				if("JT_TICKET".equals(cookie.getName())) {
					ticket=cookie.getValue();
					break;
				}
			}
		}
		
		if(!StringUtils.isEmpty(ticket)) {
			jedisCluster.del(ticket);
		}
		
		Cookie cookie =new Cookie("JT_TICKET", "");
		cookie.setMaxAge(0);
		cookie.setDomain("jt.com");
		cookie.setPath("/");
		response.addCookie(cookie);
		return "redirect:/";
	}
	
	
	
	
	//com.jt.pojo.User 不能转化为com.jt.pojo.User
	//{username:_username,password:_password}
	/*
	 * @RequestMapping("/doLogin")
	 * 
	 * @ResponseBody public SysResult doLogin(User user,HttpServletResponse
	 * response) { //1.获取服务器加密秘钥 String ticket = userService.doLogin(user);
	 * if(StringUtils.isEmpty(ticket)) { return SysResult.fail(); }
	 * //2.需要将数据保存到cookie中 Cookie cookie = new Cookie("JT_TICKET", ticket);
	 * cookie.setMaxAge(7*24*3600); //设定cookie的使用权限. cookie.setPath("/");
	 * //设定cookie共享!!!! cookie.setDomain("jt.com"); //将cookie写入浏览器
	 * response.addCookie(cookie); return SysResult.success(); }
	 */
	
	/*
	 * @RequestMapping("/logout") public String queryUser(HttpServletRequest
	 * request,HttpServletResponse response) { Cookie[] cookies =
	 * request.getCookies(); String ticket=null; if(cookies.length>0) { for(Cookie
	 * cooike:cookies) { if("JT_TICKET".equals(cooike.getName())) {
	 * ticket=cooike.getValue(); break; } } } if(ticket!=null) {
	 * jedisCluster.del(ticket); }
	 * 
	 * Cookie cookie=new Cookie("JT_TICKET", ""); cookie.setMaxAge(0);
	 * cookie.setDomain("jt.com"); cookie.setPath("/"); response.addCookie(cookie);
	 * return "redirect:/"; }
	 */
	
	
	
}
